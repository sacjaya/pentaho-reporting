/*
 * This program is free software; you can redistribute it and/or modify it under the
 * terms of the GNU Lesser General Public License, version 2.1 as published by the Free Software
 * Foundation.
 *
 * You should have received a copy of the GNU Lesser General Public License along with this
 * program; if not, you can obtain a copy at http://www.gnu.org/licenses/old-licenses/lgpl-2.1.html
 * or from the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY;
 * without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU Lesser General Public License for more details.
 *
 * Copyright (c) 2005-2011 Pentaho Corporation.  All rights reserved.
 */

package org.pentaho.reporting.designer.core.actions.elements.format;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.Action;

import org.pentaho.reporting.designer.core.actions.AbstractElementSelectionAction;
import org.pentaho.reporting.designer.core.actions.ActionMessages;
import org.pentaho.reporting.designer.core.actions.ToggleStateAction;
import org.pentaho.reporting.designer.core.editor.ReportRenderContext;
import org.pentaho.reporting.designer.core.model.selection.ReportSelectionModel;
import org.pentaho.reporting.designer.core.util.IconLoader;
import org.pentaho.reporting.designer.core.util.undo.CompoundUndoEntry;
import org.pentaho.reporting.designer.core.util.undo.StyleEditUndoEntry;
import org.pentaho.reporting.designer.core.util.undo.UndoEntry;
import org.pentaho.reporting.engine.classic.core.Element;
import org.pentaho.reporting.engine.classic.core.ElementAlignment;
import org.pentaho.reporting.engine.classic.core.style.ElementStyleKeys;
import org.pentaho.reporting.engine.classic.core.style.ElementStyleSheet;

/**
 * Todo: Document Me
 *
 * @author Thomas Morgner
 */
public final class TextAlignmentJustifyAction extends AbstractElementSelectionAction implements ToggleStateAction
{
  private boolean selected;

  public TextAlignmentJustifyAction()
  {
    putValue(Action.NAME, ActionMessages.getString("TextAlignmentJustifyAction.Text"));
    putValue(Action.SHORT_DESCRIPTION, ActionMessages.getString("TextAlignmentJustifyAction.Description"));
    putValue(Action.MNEMONIC_KEY, ActionMessages.getOptionalMnemonic("TextAlignmentJustifyAction.Mnemonic"));
    putValue(Action.SMALL_ICON, IconLoader.getInstance().getTextAlignJustifyCommand());
    putValue(Action.ACCELERATOR_KEY, ActionMessages.getOptionalKeyStroke("TextAlignmentJustifyAction.Accelerator"));
  }

  public boolean isSelected()
  {
    return selected;
  }

  public void setSelected(final boolean selected)
  {
    final boolean oldValue = this.selected;
    this.selected = selected;
    firePropertyChange(SELECTED, oldValue, selected);
  }

  protected void updateSelection()
  {
    super.updateSelection();

    final ReportSelectionModel selectionModel = getSelectionModel();
    if (selectionModel == null)
    {
      return;
    }
    final Element[] visualElements = selectionModel.getSelectedVisualElements();
    if (visualElements.length < 1 || visualElements.length > 1)
    {
      setSelected(false);
      return;
    }

    final Element element = visualElements[0];
    final ElementStyleSheet styleSheet = element.getStyle();
    setSelected(ElementAlignment.JUSTIFY.equals(styleSheet.getStyleProperty(ElementStyleKeys.ALIGNMENT)));
  }


  /**
   * Invoked when an action occurs.
   */
  public void actionPerformed(final ActionEvent e)
  {
    final ReportSelectionModel selectionModel = getSelectionModel();
    if (selectionModel == null)
    {
      return;
    }
    final Element[] visualElements = selectionModel.getSelectedVisualElements();
    if (visualElements.length == 0)
    {
      return;
    }
    final ReportRenderContext activeContext = getActiveContext();
    if (activeContext == null)
    {
      return;
    }

    final ArrayList<UndoEntry> undos = new ArrayList<UndoEntry>();
    for (int i = 0; i < visualElements.length; i++)
    {
      final Element element = visualElements[i];
      final ElementStyleSheet styleSheet = element.getStyle();
      undos.add(StyleEditUndoEntry.createConditional(element, ElementStyleKeys.ALIGNMENT, ElementAlignment.JUSTIFY));
      styleSheet.setStyleProperty(ElementStyleKeys.ALIGNMENT, ElementAlignment.JUSTIFY);
      element.notifyNodePropertiesChanged();
    }
    getActiveContext().getUndo().addChange(ActionMessages.getString("TextAlignmentJustifyAction.UndoName"),
        new CompoundUndoEntry(undos.toArray(new UndoEntry[undos.size()])));
  }
}
