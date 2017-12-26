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

package com.pavelfatin.toyide.languages.toy.inspection

import org.junit.Test
import com.pavelfatin.toyide.inspection.MarkData
import com.pavelfatin.toyide.Helpers._

class PrefixedApplicationTest extends InspectionTestBase(PrefixApplication) {
  @Test
  def applicable(): Unit = {
    assertMatches(marksIn("println(-1);")) {
      case Nil =>
    }
  }

  @Test
  def unknownExpressionType(): Unit = {
    assertMatches(marksIn("println(-v);")) {
      case Nil =>
    }
  }

  @Test
  def inapplicable(): Unit = {
    val Message = PrefixApplication.Message("-", "boolean")

    assertMatches(marksIn("println(-true);")) {
      case MarkData(Text("-true"), Message) :: Nil =>
    }
  }
}