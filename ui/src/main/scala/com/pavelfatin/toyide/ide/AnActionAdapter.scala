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

package com.pavelfatin.toyide.ide

import javax.swing.{KeyStroke, SwingUtilities}

import com.pavelfatin.toyide.editor.AnAction

import scala.swing.Action

private class AnActionAdapter(title: String, key: Char, anAction: AnAction) extends Action(title) {
  mnemonic    = key
  accelerator = Some(KeyStroke.getKeyStroke(anAction.keys.head))
  enabled     = anAction.enabled

  anAction.onChange {
    SwingUtilities.invokeLater(new Runnable {
      def run(): Unit = {
        enabled = anAction.enabled
      }
    })
  }

  def apply(): Unit = anAction()
}