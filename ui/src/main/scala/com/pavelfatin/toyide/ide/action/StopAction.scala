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

import com.pavelfatin.toyide.Output
import com.pavelfatin.toyide.ide.Launcher

import scala.swing.Action

class StopAction(title0: String, mnemonic0: Char, shortcut: String,
                 launcher: Launcher, output: Output) extends Action(title0) {
  mnemonic = mnemonic0

  accelerator = Some(KeyStroke.getKeyStroke(shortcut))

  enabled = false

  launcher.onChange {
    enabled = launcher.active
  }

  def apply(): Unit = {
    launcher.stop()
    output.print("\nStopped")
  }
}