/*
 * Copyright (C) 2014 Pavel Fatin <http://pavelfatin.com>
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

package com.pavelfatin.toyide.languages.lisp.value

import com.pavelfatin.toyide.interpreter.{EvaluationException, Place}
import com.pavelfatin.toyide.languages.lisp.InterpreterTesting
import org.junit.Assert._
import org.junit.Test

class PlaceTest extends InterpreterTesting {
  @Test
  def undefinedSymbol(): Unit = {
    assertTrace("undefined", Place(Some("Test"), 0))
  }

  @Test
  def emptyApplication(): Unit = {
    assertTrace("()", Place(Some("Test"), 0))
  }

  @Test
  def applicationToValue(): Unit = {
    assertTrace("(1)", Place(Some("Test"), 0))
  }

  @Test
  def coreFunction(): Unit = {
    assertTrace("(error)", Place(Some("Test"), 0))
  }

  @Test
  def userFunction(): Unit = {
    assertTrace("((fn []\n(error)))", Place(Some("Test"), 1), Place(Some("Test"), 0))
    assertTrace("((fn f []\n(error)))", Place(Some("Test.f"), 1), Place(Some("Test"), 0))
  }

  @Test
  def macroFunction(): Unit = {
    assertTrace("((macro []\n(error)))", Place(Some("Test"), 1), Place(Some("Test"), 0))
    assertTrace("((macro m []\n(error)))", Place(Some("Test.m"), 1), Place(Some("Test"), 0))
  }

  @Test
  def functionLiteral(): Unit = {
    assertTrace("(\n#(error))", Place(Some("Test"), 1), Place(Some("Test"), 0))
  }

  @Test
  def listInsideFunctionLiteral(): Unit = {
    assertTrace("(#(\n(error)))", Place(Some("Test"), 1), Place(Some("Test"), 0))
  }

  @Test
  def apply(): Unit = {
    assertTrace("(apply error\n(list))", Place(Some("Test"), 0))
  }

  @Test
  def quote(): Unit = {
    assertTrace("(eval\n'(error))", Place(Some("Test"), 1), Place(Some("Test"), 0))
  }

  @Test
  def quasiQuote(): Unit = {
    assertTrace("(eval\n`(error))", Place(Some("Test"), 1), Place(Some("Test"), 0))
  }

  @Test
  def multiple(): Unit = {
    val code = "(def f (fn [] (error)))\n(def g (fn [] (f)))\n(g)"
    assertTrace(code, Place(Some("Test"), 0), Place(Some("Test"), 1), Place(Some("Test"), 2))
  }

  @Test
  def undefinedSymbolInsideFunctionLiteral(): Unit = {
    assertTrace("(#(\nfoo))", Place(Some("Test"), 1), Place(Some("Test"), 0))
  }

  private def assertTrace(code: String, places: Place*): Unit = {
    try {
      run(code)
      fail("No stack trace")
    } catch {
      case e: EvaluationException =>
        assertEquals(places.toList, e.trace.toList)
    }
  }
}