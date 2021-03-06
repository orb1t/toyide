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

import com.pavelfatin.toyide.{ObservableEvents, Interval}

trait Terminal extends ObservableEvents[TerminalEvent] {
  var offset: Int

  var selection: Option[Interval]

  var hover: Option[Int]

  var highlights: Seq[Interval]

  def choose[A <: AnyRef](variants: Seq[A], query: String)(callback: A => Unit): Unit

  def edit(s: String, title: String)(callback: Option[String] => Unit): Unit
}