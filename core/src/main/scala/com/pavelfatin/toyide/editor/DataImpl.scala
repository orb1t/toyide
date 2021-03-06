/*
 * Copyright 2018 Pavel Fatin, https://pavelfatin.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pavelfatin.toyide.editor

import com.pavelfatin.toyide.document.Document
import com.pavelfatin.toyide.inspection.{Inspection, Mark}
import com.pavelfatin.toyide.lexer.{Lexer, Token}
import com.pavelfatin.toyide.node.Node
import com.pavelfatin.toyide.parser.Parser

private class DataImpl(document: Document, lexer: Lexer, parser: Parser, inspections: Seq[Inspection]) extends Data {
  def text: String = document.text

  var tokens    : Seq[Token]    = Nil
  var structure : Option[Node]  = None
  var errors    : Seq[Error]    = Nil

  var hasFatalErrors: Boolean = errors.exists(_.fatal)

  var pass: Pass = Pass.Text

  document.onChange { _ =>
    run(Pass.Text)
  }

  def hasNextPass: Boolean = pass.next.isDefined

  def nextPass(): Unit = {
    val next = pass.next.getOrElse(
      throw new IllegalStateException("Next pass is unavailable"))

    run(next)
  }

  def compute(): Unit =
    while (hasNextPass) {
      nextPass()
    }

  private def run(p: Pass): Unit = {
    pass = p

    val passErrors = p match {
      case Pass.Text        => runTextPass()
      case Pass.Lexer       => runLexerPass()
      case Pass.Parser      => runParserPass()
      case Pass.Inspections => runInspectionPass()
    }

    errors ++= passErrors
    hasFatalErrors = hasFatalErrors || passErrors.exists(_.fatal)

    notifyObservers(DataEvent(pass, passErrors))
  }

  private def runTextPass(): Seq[Error] = {
    tokens          = Nil
    structure       = None
    errors          = Nil
    hasFatalErrors  = false

    Nil
  }

  private def runLexerPass(): Seq[Error] = {
    tokens = lexer.analyze(document.characters).toSeq

    tokens.collect {
      case Token(_, span, Some(message)) => Error(span.interval, message)
    }
  }

  private def runParserPass(): Seq[Error] = {
    val root = parser.parse(tokens.iterator)

    structure = Some(root)

    root.elements.map(node => (node, node.problem)).collect {
      case (node, Some(message)) => Error(node.span.interval, message)
    }
  }

  private def runInspectionPass(): Seq[Error] = {
    val root = structure.getOrElse(
      throw new IllegalStateException("Running inspections prior to parser"))

    root.elements.flatMap(node => inspections.flatMap(_.inspect(node))).collect {
      case Mark(node, message, decoration, warning) => Error(node.span.interval, message, decoration, !warning)
    }
  }
}
