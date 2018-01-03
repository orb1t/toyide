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

import org.junit.Test

abstract class ExpressionTestBase {
  @Test
  def literals(): Unit = {
    assertOutput("print(\"foo\");", "foo")

    assertOutput("print(123);", "123")

    assertOutput("print(true);", "true")
    assertOutput("print(false);", "false")
  }

  @Test
  def booleanAnd(): Unit = {
    assertOutput("print(true && true);", "true")
    assertOutput("print(true && false);", "false")
    assertOutput("print(false && true);", "false")
    assertOutput("print(false && false);", "false")
  }

  @Test
  def booleanAndLazyEvaluation(): Unit = {
    assertOutput("def f(): boolean = { print(1); return true; } print(true && f());", "1true")
    assertOutput("def f(): boolean = { print(1); return true; } print(false && f());", "false")
  }

  @Test
  def booleanOr(): Unit = {
    assertOutput("print(true || true);", "true")
    assertOutput("print(true || false);", "true")
    assertOutput("print(false || true);", "true")
    assertOutput("print(false || false);", "false")
  }

  @Test
  def booleanOrLazyEvaluation(): Unit = {
    assertOutput("def f(): boolean = { print(1); return true; } print(true || f());", "true")
    assertOutput("def f(): boolean = { print(1); return true; } print(false || f());", "1true")
  }

  @Test
  def integerGt(): Unit = {
    assertOutput("print(1 > 2);", "false")
    assertOutput("print(2 > 1);", "true")
    assertOutput("print(2 > 2);", "false")
  }

  @Test
  def integerGtEq(): Unit = {
    assertOutput("print(1 >= 2);", "false")
    assertOutput("print(2 >= 1);", "true")
    assertOutput("print(2 >= 2);", "true")
  }

  @Test
  def integerLt(): Unit = {
    assertOutput("print(1 < 2);", "true")
    assertOutput("print(2 < 1);", "false")
    assertOutput("print(2 < 2);", "false")
  }

  @Test
  def integerLtEq(): Unit = {
    assertOutput("print(1 <= 2);", "true")
    assertOutput("print(2 <= 1);", "false")
    assertOutput("print(2 <= 2);", "true")
  }

  @Test
  def stringEq(): Unit = {
    assertOutput("print(\"foo\" == \"foo\");", "true")
    assertOutput("print(\"foo\" == \"bar\");", "false")
  }

  @Test
  def stringEqNonConstant(): Unit = {
    assertOutput("print(\"foo\" + 1 == \"foo2\");", "false")
    assertOutput("print(\"foo\" + 2 == \"foo2\");", "true")
  }

  @Test
  def integerEq(): Unit = {
    assertOutput("print(1 == 1);", "true")
    assertOutput("print(1 == 2);", "false")
  }

  @Test
  def booleanEq(): Unit = {
    assertOutput("print(true == true);", "true")
    assertOutput("print(false == false);", "true")
    assertOutput("print(true == false);", "false")
    assertOutput("print(false == true);", "false")
  }

  @Test
  def stringNotEq(): Unit = {
    assertOutput("print(\"foo\" != \"foo\");", "false")
    assertOutput("print(\"foo\" != \"bar\");", "true")
  }

  @Test
  def stringNotEqNonConstant(): Unit = {
    assertOutput("print(\"foo\" + 2 != \"foo2\");", "false")
    assertOutput("print(\"foo\" + 1 != \"foo2\");", "true")
  }

  @Test
  def integerNotEq(): Unit = {
    assertOutput("print(1 != 1);", "false")
    assertOutput("print(1 != 2);", "true")
  }

  @Test
  def booleanNotEq(): Unit = {
    assertOutput("print(true != true);", "false")
    assertOutput("print(false != false);", "false")
    assertOutput("print(true != false);", "true")
    assertOutput("print(false != true);", "true")
  }

  @Test
  def integerCalculations(): Unit = {
    assertOutput("print(1 + 2);", "3")
    assertOutput("print(3 - 2);", "1")
    assertOutput("print(2 * 3);", "6")
    assertOutput("print(6 / 3);", "2")
    assertOutput("print(6 % 2);", "0")
    assertOutput("print(6 % 4);", "2")
  }

  @Test
  def prefixExpression(): Unit = {
    assertOutput("print(+3);", "3")
    assertOutput("print(-3);", "-3")
    assertOutput("print(--3);", "3")

    assertOutput("print(!true);", "false")
    assertOutput("print(!false);", "true")
    assertOutput("print(!!true);", "true")
  }

  @Test
  def stringConcatenation(): Unit = {
    assertOutput("print(\"foo\" + \"bar\");", "foobar")
    assertOutput("print(\"foo\" + 1);", "foo1")
    assertOutput("print(\"foo\" + true);", "footrue")
  }

  @Test
  def group(): Unit = {
    assertOutput("print((1 + 2));", "3")
  }

  @Test
  def complexExpression(): Unit = {
    assertOutput("print(1 + 2 * 3 * (4 + 5));", "55")
  }

  @Test
  def evaluationOrder(): Unit = {
    assertOutput("""
      def a(): integer = { print(1); return 1; }
      def b(): integer = { print(2); return 2; }
      print(a() + b());
    """, "123")
  }

  protected def assertOutput(code: String, expected: String): Unit
}