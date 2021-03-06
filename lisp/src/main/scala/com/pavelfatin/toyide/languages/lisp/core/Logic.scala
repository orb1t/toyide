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

package com.pavelfatin.toyide.languages.lisp.core

import com.pavelfatin.toyide.Output
import com.pavelfatin.toyide.languages.lisp.value.{BooleanValue, Environment, Expression}

object And extends CoreFunction("and", isLazy = true) {
  def apply(arguments: Seq[Expression], environment: Environment, output: Output): BooleanValue = {
    val isFalse = arguments.toIterator.map(_.eval(environment, output)).exists {
      case BooleanValue(b) => !b
      case _ => expected("b1 b2 ...", arguments, environment)
    }
    BooleanValue(!isFalse)
  }
}

object Or extends CoreFunction("or", isLazy = true) {
  def apply(arguments: Seq[Expression], environment: Environment, output: Output): BooleanValue = {
    val isTrue = arguments.toIterator.map(_.eval(environment, output)).exists {
      case BooleanValue(b) => b
      case _ => expected("b1 b2 ...", arguments, environment)
    }
    BooleanValue(isTrue)
  }
}

object Not extends CoreFunction("not") {
  def apply(arguments: Seq[Expression], environment: Environment, output: Output): BooleanValue = arguments match {
    case Seq(BooleanValue(b)) => BooleanValue(!b)
    case _ => expected("b", arguments, environment)
  }
}
