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

package com.pavelfatin.toyide.editor.painter

import java.awt.{Graphics, Rectangle}

import com.pavelfatin.toyide.editor.{CaretMovement, CaretVisibilityChanged, Coloring}

private class CaretPainter(context: PainterContext) extends AbstractPainter(context) {
  def id = "caret"

  terminal.onChange {
    case CaretMovement(from, to) =>
      notifyObservers(caretRectangleAt(from))
      notifyObservers(caretRectangleAt(to))
    case _ =>
  }

  canvas.onChange {
    case CaretVisibilityChanged(_) =>
      notifyObservers(caretRectangleAt(terminal.offset))
    case _ =>
  }

  override def paint(g: Graphics, bounds: Rectangle): Unit =
    if (canvas.caretVisible) {
      val rectangle = caretRectangleAt(terminal.offset).intersection(bounds)

      if (!rectangle.isEmpty) {
        g.setColor(coloring(Coloring.CaretForeground))
        fill(g, rectangle)
      }
    }
}