/*
 * Copyright (C) 2011 Pavel Fatin <http://pavelfatin.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
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