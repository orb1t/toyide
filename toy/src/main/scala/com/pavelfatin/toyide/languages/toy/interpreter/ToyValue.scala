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

package com.pavelfatin.toyide.languages.toy.interpreter

import com.pavelfatin.toyide.interpreter.DelegateValue
import com.pavelfatin.toyide.languages.toy.ToyType.{BooleanType, IntegerType, StringType}
import com.pavelfatin.toyide.node.NodeType

sealed trait ToyValue[A] extends DelegateValue[A]

object ToyValue {
  case class StringValue(content: String) extends ToyValue[String] {
    def valueType: NodeType = StringType
  }

  case class IntegerValue(content: Int) extends ToyValue[Int] {
    def valueType: NodeType = IntegerType
  }

  case class BooleanValue(content: Boolean) extends ToyValue[Boolean] {
    def valueType: NodeType = BooleanType
  }
}