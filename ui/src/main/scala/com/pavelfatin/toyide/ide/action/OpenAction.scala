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

package com.pavelfatin.toyide.ide.action

import javax.swing.KeyStroke
import javax.swing.filechooser.FileNameExtensionFilter

import com.pavelfatin.toyide.ide.EditorTab

import scala.swing.{Action, Component, FileChooser}

class OpenAction(title0: String, mnemonic0: Char, shortcut: String,
                         parent: Component, tab: EditorTab) extends Action(title0) {
  mnemonic = mnemonic0

  accelerator = Some(KeyStroke.getKeyStroke(shortcut))

  def apply(): Unit = {
    val chooser = new FileChooser()
    chooser.title = "Open"
    chooser.fileFilter = new FileNameExtensionFilter(tab.fileType.name, tab.fileType.extension)
    chooser.showOpenDialog(parent) match {
      case FileChooser.Result.Approve =>
        val file = chooser.selectedFile
        tab.text = IO.read(file)
        tab.file = Some(file)
      case _ =>
    }
  }
}