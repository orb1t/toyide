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

package com.pavelfatin.toyide.editor.controller

import com.pavelfatin.toyide.formatter.Formatter
import com.pavelfatin.toyide.document.Document
import com.pavelfatin.toyide.editor.{AnAction, Terminal, Data}

private class Format(document: Document, terminal: Terminal, data: Data, formatter: Formatter, tabSize: Int) extends AnAction {
  def keys: Seq[String] = List("ctrl alt pressed L")

  def apply(): Unit = {
    data.compute()
    data.structure.foreach { root =>
      val text = formatter.format(root, terminal.selection, tabSize)
      terminal.offset = terminal.offset.min(text.length)
      document.text = text
    }
  }
}