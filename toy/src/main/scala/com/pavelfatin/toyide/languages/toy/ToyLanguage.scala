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

package com.pavelfatin.toyide.languages.toy

import com.pavelfatin.toyide.editor.{Adviser, ColorScheme, Coloring}
import com.pavelfatin.toyide.formatter.Format
import com.pavelfatin.toyide.inspection.Inspection
import com.pavelfatin.toyide.languages.toy.ToyTokens._
import com.pavelfatin.toyide.languages.toy.inspection._
import com.pavelfatin.toyide.languages.toy.parser.ProgramParser
import com.pavelfatin.toyide.lexer.{Lexer, TokenKind}
import com.pavelfatin.toyide.parser.Parser
import com.pavelfatin.toyide.{Example, FileType, Language}

object ToyLanguage extends Language {
  def name = "Toy"

  def description = "C-like imperative language"

  def lexer: Lexer = ToyLexer

  def parser: Parser = ProgramParser

  def colorings: Map[String, Coloring] = Map(
    "Light" -> new ToyColoring(ColorScheme.LightColors),
    "Dark"  -> new ToyColoring(ColorScheme.DarkColors))

  def complements: Seq[(TokenKind, TokenKind)] = Seq((LBRACE, RBRACE), (LPAREN, RPAREN))

  def format: Format = ToyFormat

  def comment = "//"

  def inspections: Seq[Inspection] = Seq(ReturnOutsideFunction, DuplicateIdentifier, UnresolvedReference,
    VoidValue, Applicability, TypeMismatch, OperatorApplication, IntegerRange, PrefixApplication,
    MissingReturn, UnreachableStatement, UnusedDeclaration, PredefinedIdentifier, Optimization, DivisionByZero)

  def adviser: Adviser = ToyAdviser

  def fileType = FileType("Toy file", "toy")

  def examples: Seq[Example] = ToyExamples.Values
}