begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|*
import|;
end_import

begin_class
DECL|class|EntryTable
specifier|public
class|class
name|EntryTable
extends|extends
name|JTable
block|{
DECL|field|PREFERRED_WIDTH
DECL|field|PREFERRED_HEIGHT
specifier|final
name|int
name|PREFERRED_WIDTH
init|=
literal|400
decl_stmt|,
name|PREFERRED_HEIGHT
init|=
literal|30
decl_stmt|;
DECL|field|sp
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
operator|(
name|JTable
operator|)
name|this
argument_list|)
decl_stmt|;
DECL|field|rightClickMenu
name|JPopupMenu
name|rightClickMenu
init|=
literal|null
decl_stmt|;
DECL|field|tableModel
name|EntryTableModel
name|tableModel
decl_stmt|;
DECL|field|prefs
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|showingSearchResults
specifier|protected
name|boolean
name|showingSearchResults
init|=
literal|false
decl_stmt|,
DECL|field|showingGroup
name|showingGroup
init|=
literal|false
decl_stmt|;
DECL|field|ths
specifier|private
name|EntryTable
name|ths
init|=
name|this
decl_stmt|;
DECL|field|antialiasing
specifier|private
name|boolean
name|antialiasing
init|=
literal|true
decl_stmt|,
DECL|field|ctrlClick
name|ctrlClick
init|=
literal|false
decl_stmt|;
comment|//RenderingHints renderingHints;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|previewListener
specifier|private
name|ListSelectionListener
name|previewListener
init|=
literal|null
decl_stmt|;
DECL|method|EntryTable (EntryTableModel tm_, BasePanel panel_, JabRefPreferences prefs_)
specifier|public
name|EntryTable
parameter_list|(
name|EntryTableModel
name|tm_
parameter_list|,
name|BasePanel
name|panel_
parameter_list|,
name|JabRefPreferences
name|prefs_
parameter_list|)
block|{
name|super
argument_list|(
name|tm_
argument_list|)
expr_stmt|;
name|this
operator|.
name|tableModel
operator|=
name|tm_
expr_stmt|;
name|panel
operator|=
name|panel_
expr_stmt|;
comment|// Add the global focus listener, so a menu item can see if this table was focused when
comment|// an action was called.
name|addFocusListener
argument_list|(
name|Globals
operator|.
name|focusListener
argument_list|)
expr_stmt|;
comment|//renderingHints = g2.getRenderingHints();
comment|//renderingHints.put(RenderingHints.KEY_ANTIALIASING,
comment|//		   RenderingHints.VALUE_ANTIALIAS_ON);
comment|//renderingHints.put(RenderingHints.KEY_RENDERING,
comment|//		   RenderingHints.VALUE_RENDER_QUALITY);
name|prefs
operator|=
name|prefs_
expr_stmt|;
name|antialiasing
operator|=
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"antialias"
argument_list|)
expr_stmt|;
name|ctrlClick
operator|=
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"ctrlClick"
argument_list|)
expr_stmt|;
name|getTableHeader
argument_list|()
operator|.
name|setReorderingAllowed
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// To prevent color bugs. Must be fixed.
name|setGridColor
argument_list|(
name|GUIGlobals
operator|.
name|gridColor
argument_list|)
expr_stmt|;
name|setShowVerticalLines
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setShowHorizontalLines
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//setColumnSelectionAllowed(true);
name|setColumnSelectionAllowed
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setRowSelectionAllowed
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setAutoResizeMode
argument_list|(
name|prefs
operator|.
name|getInt
argument_list|(
literal|"autoResizeMode"
argument_list|)
argument_list|)
expr_stmt|;
name|DefaultCellEditor
name|dce
init|=
operator|new
name|DefaultCellEditor
argument_list|(
operator|new
name|JTextField
argument_list|()
argument_list|)
decl_stmt|;
name|dce
operator|.
name|setClickCountToStart
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|setDefaultEditor
argument_list|(
name|String
operator|.
name|class
argument_list|,
name|dce
argument_list|)
expr_stmt|;
name|getTableHeader
argument_list|()
operator|.
name|addMouseListener
argument_list|(
operator|new
name|MouseAdapter
argument_list|()
block|{
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|int
name|col
init|=
name|getTableHeader
argument_list|()
operator|.
name|columnAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|col
operator|>=
name|tableModel
operator|.
name|padleft
condition|)
block|{
comment|// A valid column, but not the first.
name|String
name|s
init|=
name|tableModel
operator|.
name|getColumnName
argument_list|(
name|col
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|s
operator|.
name|equals
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
literal|"priSort"
argument_list|)
argument_list|)
condition|)
name|prefs
operator|.
name|put
argument_list|(
literal|"priSort"
argument_list|,
name|s
argument_list|)
expr_stmt|;
comment|// ... or change sort direction
else|else
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"priDescending"
argument_list|,
operator|!
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"priDescending"
argument_list|)
argument_list|)
expr_stmt|;
name|tableModel
operator|.
name|remap
argument_list|()
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|addMouseListener
argument_list|(
operator|new
name|TableClickListener
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add the listener that responds to clicks on the table.
name|addSelectionListener
argument_list|()
expr_stmt|;
comment|// Add the listener that responds to new entry selection.
comment|// (to update entry editor or preview)
name|setWidths
argument_list|()
expr_stmt|;
name|sp
operator|.
name|getViewport
argument_list|()
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|tableBackground
argument_list|)
expr_stmt|;
name|updateFont
argument_list|()
expr_stmt|;
block|}
comment|/**        * A ListSelectionListener for updating the preview panel when the user selects an        * entry. Should only be active when preview is enabled.        */
DECL|method|addSelectionListener ()
specifier|public
name|void
name|addSelectionListener
parameter_list|()
block|{
if|if
condition|(
name|previewListener
operator|==
literal|null
condition|)
name|previewListener
operator|=
operator|new
name|ListSelectionListener
argument_list|()
block|{
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
name|int
name|row
init|=
name|getSelectedRow
argument_list|()
decl_stmt|;
comment|//e.getFirstIndex();
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
name|panel
operator|.
name|updateWiewToSelected
argument_list|(
name|panel
operator|.
name|database
argument_list|()
operator|.
name|getEntryById
argument_list|(
name|tableModel
operator|.
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
expr_stmt|;
name|getSelectionModel
argument_list|()
operator|.
name|addListSelectionListener
argument_list|(
name|previewListener
argument_list|)
expr_stmt|;
block|}
comment|/**        * Remove the preview listener.        */
DECL|method|disablePreviewListener ()
specifier|public
name|void
name|disablePreviewListener
parameter_list|()
block|{
name|getSelectionModel
argument_list|()
operator|.
name|removeListSelectionListener
argument_list|(
name|previewListener
argument_list|)
expr_stmt|;
block|}
DECL|method|setWidths ()
specifier|public
name|void
name|setWidths
parameter_list|()
block|{
comment|// Setting column widths:
name|int
name|ncWidth
init|=
name|prefs
operator|.
name|getInt
argument_list|(
literal|"numberColWidth"
argument_list|)
decl_stmt|;
name|String
index|[]
name|widths
init|=
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"columnWidths"
argument_list|)
decl_stmt|;
name|TableColumnModel
name|cm
init|=
name|getColumnModel
argument_list|()
decl_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|ncWidth
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|tableModel
operator|.
name|padleft
condition|;
name|i
operator|++
control|)
name|cm
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
name|tableModel
operator|.
name|padleft
init|;
name|i
operator|<
name|getModel
argument_list|()
operator|.
name|getColumnCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
name|cm
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|widths
index|[
name|i
operator|-
name|tableModel
operator|.
name|padleft
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|Globals
operator|.
name|logger
argument_list|(
literal|"Exception while setting column widths. Choosing default."
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|GUIGlobals
operator|.
name|DEFAULT_FIELD_LENGTH
argument_list|)
expr_stmt|;
block|}
comment|//cm.getColumn(i).setPreferredWidth(GUIGlobals.getPreferredFieldLength(getModel().getColumnName(i)));
block|}
block|}
DECL|method|getPane ()
specifier|public
name|JScrollPane
name|getPane
parameter_list|()
block|{
return|return
name|sp
return|;
block|}
DECL|method|setShowingSearchResults (boolean search, boolean group)
specifier|public
name|void
name|setShowingSearchResults
parameter_list|(
name|boolean
name|search
parameter_list|,
name|boolean
name|group
parameter_list|)
block|{
name|showingSearchResults
operator|=
name|search
expr_stmt|;
name|showingGroup
operator|=
name|group
expr_stmt|;
block|}
DECL|method|setRightClickMenu (JPopupMenu rcm)
specifier|public
name|void
name|setRightClickMenu
parameter_list|(
name|JPopupMenu
name|rcm
parameter_list|)
block|{
name|rightClickMenu
operator|=
name|rcm
expr_stmt|;
block|}
comment|/**    * This class handles clicks on the EntryTable that should trigger specific    * events, like opening an entry editor, the context menu or a pdf file.    */
DECL|class|TableClickListener
class|class
name|TableClickListener
extends|extends
name|MouseAdapter
block|{
DECL|method|mouseClicked (MouseEvent e)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
comment|// First find the column on which the user has clicked.
name|int
name|col
init|=
name|columnAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
comment|// A double click on an entry should open the entry's editor.
if|if
condition|(
operator|(
name|col
operator|==
literal|0
operator|)
operator|&&
operator|(
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
operator|)
condition|)
block|{
try|try
block|{
name|panel
operator|.
name|runCommand
argument_list|(
literal|"edit"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|// Check if the user has right-clicked. If so, open the right-click menu.
if|if
condition|(
operator|(
name|e
operator|.
name|getButton
argument_list|()
operator|==
name|MouseEvent
operator|.
name|BUTTON3
operator|)
operator|||
operator|(
name|ctrlClick
operator|&&
operator|(
name|e
operator|.
name|getButton
argument_list|()
operator|==
name|MouseEvent
operator|.
name|BUTTON1
operator|)
operator|&&
name|e
operator|.
name|isControlDown
argument_list|()
operator|)
condition|)
block|{
name|rightClickMenu
operator|=
operator|new
name|RightClickMenu
argument_list|(
name|panel
argument_list|,
name|panel
operator|.
name|metaData
argument_list|)
expr_stmt|;
name|rightClickMenu
operator|.
name|show
argument_list|(
name|ths
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Check if the user has clicked on an icon cell to open url or pdf.
if|if
condition|(
name|tableModel
operator|.
name|getCellStatus
argument_list|(
literal|0
argument_list|,
name|col
argument_list|)
operator|==
name|EntryTableModel
operator|.
name|ICON_COL
condition|)
block|{
comment|// Get the row number also:
specifier|final
name|int
name|row
init|=
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
name|Object
name|value
init|=
name|getValueAt
argument_list|(
name|row
argument_list|,
name|col
argument_list|)
decl_stmt|;
if|if
condition|(
name|value
operator|==
literal|null
condition|)
return|return;
comment|// No icon here, so we do nothing.
comment|/*Util.pr("eouaeou");           JButton button = (JButton)value;            MouseEvent buttonEvent =               (MouseEvent)SwingUtilities.convertMouseEvent(ths, e, button);           button.dispatchEvent(buttonEvent);           // This is necessary so that when a button is pressed and released           // it gets rendered properly.  Otherwise, the button may still appear           // pressed down when it has been released.           ths.repaint();            */
comment|// Get the icon type. Corresponds to the field name.
specifier|final
name|String
index|[]
name|iconType
init|=
name|tableModel
operator|.
name|getIconTypeForColumn
argument_list|(
name|col
argument_list|)
decl_stmt|;
name|int
name|hasField
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|iconType
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
if|if
condition|(
name|tableModel
operator|.
name|hasField
argument_list|(
name|row
argument_list|,
name|iconType
index|[
name|i
index|]
argument_list|)
condition|)
name|hasField
operator|=
name|i
expr_stmt|;
if|if
condition|(
name|hasField
operator|==
operator|-
literal|1
condition|)
return|return;
specifier|final
name|String
name|fieldName
init|=
name|iconType
index|[
name|hasField
index|]
decl_stmt|;
comment|// Open it now. We do this in a thread, so the program won't freeze during the wait.
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"External viewer called"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
name|BibtexEntry
name|be
init|=
name|panel
operator|.
name|database
argument_list|()
operator|.
name|getEntryById
argument_list|(
name|tableModel
operator|.
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|be
operator|==
literal|null
condition|)
block|{
name|Globals
operator|.
name|logger
argument_list|(
literal|"Error: could not find entry."
argument_list|)
expr_stmt|;
return|return;
block|}
name|Object
name|link
init|=
name|be
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
name|iconType
operator|==
literal|null
condition|)
block|{
name|Globals
operator|.
name|logger
argument_list|(
literal|"Error: no link to "
operator|+
name|fieldName
operator|+
literal|"."
argument_list|)
expr_stmt|;
return|return;
comment|// There is an icon, but the field is not set.
block|}
try|try
block|{
name|Util
operator|.
name|openExternalViewer
argument_list|(
operator|(
name|String
operator|)
name|link
argument_list|,
name|fieldName
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{}
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|getCellRenderer (int row, int column)
specifier|public
name|TableCellRenderer
name|getCellRenderer
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
comment|// This method asks the table model whether the given cell represents a
comment|// required or optional field, and returns the appropriate renderer.
name|int
name|score
init|=
operator|-
literal|3
decl_stmt|;
name|TableCellRenderer
name|renderer
decl_stmt|;
name|int
name|status
decl_stmt|;
try|try
block|{
comment|// This try clause is here to contain a bug.
name|status
operator|=
name|tableModel
operator|.
name|getCellStatus
argument_list|(
name|row
argument_list|,
name|column
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ArrayIndexOutOfBoundsException
name|ex
parameter_list|)
block|{
name|Globals
operator|.
name|logger
argument_list|(
literal|"Error happened in getCellRenderer method of EntryTable, for cell ("
operator|+
name|row
operator|+
literal|","
operator|+
name|column
operator|+
literal|")."
argument_list|)
expr_stmt|;
return|return
name|defRenderer
return|;
comment|// This should not occur.
block|}
comment|// For testing MARKED feature:
if|if
condition|(
name|tableModel
operator|.
name|hasField
argument_list|(
name|row
argument_list|,
name|Globals
operator|.
name|MARKED
argument_list|)
condition|)
block|{
return|return
name|markedRenderer
return|;
block|}
if|if
condition|(
operator|!
name|showingSearchResults
operator|||
name|tableModel
operator|.
name|nonZeroField
argument_list|(
name|row
argument_list|,
name|Globals
operator|.
name|SEARCH
argument_list|)
condition|)
name|score
operator|++
expr_stmt|;
if|if
condition|(
operator|!
name|showingGroup
operator|||
name|tableModel
operator|.
name|nonZeroField
argument_list|(
name|row
argument_list|,
name|Globals
operator|.
name|GROUPSEARCH
argument_list|)
condition|)
name|score
operator|+=
literal|2
expr_stmt|;
comment|// Now, a grayed out renderer is for entries with -1, and
comment|// a very grayed out one for entries with -2
if|if
condition|(
name|score
operator|<
operator|-
literal|1
condition|)
name|renderer
operator|=
name|veryGrayedOutRenderer
expr_stmt|;
elseif|else
if|if
condition|(
name|score
operator|==
operator|-
literal|1
condition|)
name|renderer
operator|=
name|grayedOutRenderer
expr_stmt|;
elseif|else
if|if
condition|(
operator|!
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"tableColorCodesOn"
argument_list|)
condition|)
name|renderer
operator|=
name|defRenderer
expr_stmt|;
elseif|else
if|if
condition|(
name|column
operator|==
literal|0
condition|)
block|{
comment|// Return a renderer with red background if the entry is incomplete.
if|if
condition|(
name|tableModel
operator|.
name|isComplete
argument_list|(
name|row
argument_list|)
condition|)
name|renderer
operator|=
name|defRenderer
expr_stmt|;
else|else
block|{
if|if
condition|(
name|tableModel
operator|.
name|hasCrossRef
argument_list|(
name|row
argument_list|)
condition|)
name|renderer
operator|=
name|maybeIncRenderer
expr_stmt|;
else|else
name|renderer
operator|=
name|incRenderer
expr_stmt|;
block|}
comment|//return (tableModel.isComplete(row) ? defRenderer: incRenderer);
block|}
comment|//else if (status == EntryTableModel.ICON_COL)
comment|//  renderer = iconRenderer;
elseif|else
if|if
condition|(
name|status
operator|==
name|EntryTableModel
operator|.
name|REQUIRED
condition|)
name|renderer
operator|=
name|reqRenderer
expr_stmt|;
elseif|else
if|if
condition|(
name|status
operator|==
name|EntryTableModel
operator|.
name|OPTIONAL
condition|)
name|renderer
operator|=
name|optRenderer
expr_stmt|;
else|else
name|renderer
operator|=
name|defRenderer
expr_stmt|;
comment|//Util.pr("("+row+","+column+"). "+status+" "+renderer.toString());
return|return
name|renderer
return|;
comment|/* 	int test = row - 4*(row/4); 	if (test<= 1) 	    return renderer; 	else { 	    return renderer.darker(); 	    }*/
block|}
DECL|method|scrollTo (int y)
specifier|public
name|void
name|scrollTo
parameter_list|(
name|int
name|y
parameter_list|)
block|{
name|JScrollBar
name|scb
init|=
name|sp
operator|.
name|getVerticalScrollBar
argument_list|()
decl_stmt|;
name|scb
operator|.
name|setValue
argument_list|(
name|y
operator|*
name|scb
operator|.
name|getUnitIncrement
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getSelectedEntries ()
specifier|public
name|BibtexEntry
index|[]
name|getSelectedEntries
parameter_list|()
block|{
name|BibtexEntry
index|[]
name|bes
init|=
literal|null
decl_stmt|;
name|int
index|[]
name|rows
init|=
name|getSelectedRows
argument_list|()
decl_stmt|;
comment|//int[] cols = getSelectedColumns();
comment|// Entries are selected if only the first or multiple
comment|// columns are selected.
comment|//if (((cols.length == 1)&& (cols[0] == 0)) ||
comment|//(cols.length> 1)) { // entryTable.getColumnCount())) {
if|if
condition|(
name|rows
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|bes
operator|=
operator|new
name|BibtexEntry
index|[
name|rows
operator|.
name|length
index|]
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|rows
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|bes
index|[
name|i
index|]
operator|=
name|tableModel
operator|.
name|db
operator|.
name|getEntryById
argument_list|(
name|tableModel
operator|.
name|getNameFromNumber
argument_list|(
name|rows
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|bes
return|;
block|}
comment|// The following classes define the renderers used to render required
comment|// and optional fields in the table. The purpose of these renderers is
comment|// to visualize which fields are needed for each entry.
DECL|field|defRenderer
specifier|private
name|Renderer
name|defRenderer
init|=
operator|new
name|Renderer
argument_list|(
name|GUIGlobals
operator|.
name|tableBackground
argument_list|)
decl_stmt|,
DECL|field|reqRenderer
name|reqRenderer
init|=
operator|new
name|Renderer
argument_list|(
name|GUIGlobals
operator|.
name|tableReqFieldBackground
argument_list|)
decl_stmt|,
DECL|field|optRenderer
name|optRenderer
init|=
operator|new
name|Renderer
argument_list|(
name|GUIGlobals
operator|.
name|tableOptFieldBackground
argument_list|)
decl_stmt|,
DECL|field|incRenderer
name|incRenderer
init|=
operator|new
name|Renderer
argument_list|(
name|GUIGlobals
operator|.
name|tableIncompleteEntryBackground
argument_list|)
decl_stmt|,
DECL|field|grayedOutRenderer
name|grayedOutRenderer
init|=
operator|new
name|Renderer
argument_list|(
name|GUIGlobals
operator|.
name|grayedOutBackground
argument_list|,
name|GUIGlobals
operator|.
name|grayedOutText
argument_list|)
decl_stmt|,
DECL|field|veryGrayedOutRenderer
name|veryGrayedOutRenderer
init|=
operator|new
name|Renderer
argument_list|(
name|GUIGlobals
operator|.
name|veryGrayedOutBackground
argument_list|,
name|GUIGlobals
operator|.
name|veryGrayedOutText
argument_list|)
decl_stmt|,
DECL|field|maybeIncRenderer
name|maybeIncRenderer
init|=
operator|new
name|Renderer
argument_list|(
name|GUIGlobals
operator|.
name|maybeIncompleteEntryBackground
argument_list|)
decl_stmt|,
DECL|field|markedRenderer
name|markedRenderer
init|=
operator|new
name|Renderer
argument_list|(
name|GUIGlobals
operator|.
name|markedEntryBackground
argument_list|)
decl_stmt|;
DECL|class|Renderer
specifier|private
class|class
name|Renderer
extends|extends
name|DefaultTableCellRenderer
block|{
comment|//private DefaultTableCellRenderer darker;
DECL|method|Renderer (Color c)
specifier|public
name|Renderer
parameter_list|(
name|Color
name|c
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|setBackground
argument_list|(
name|c
argument_list|)
expr_stmt|;
comment|/* 	    darker = new DefaultTableCellRenderer(); 	    double adj = 0.9; 	    darker.setBackground(new Color((int)((double)c.getRed()*adj), 					   (int)((double)c.getGreen()*adj), 					   (int)((double)c.getBlue()*adj))); 	    */
block|}
DECL|method|Renderer (Color c, Color fg)
specifier|public
name|Renderer
parameter_list|(
name|Color
name|c
parameter_list|,
name|Color
name|fg
parameter_list|)
block|{
name|this
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|setForeground
argument_list|(
name|fg
argument_list|)
expr_stmt|;
block|}
comment|/* For enabling the renderer to handle icons. */
DECL|method|setValue (Object value)
specifier|protected
name|void
name|setValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|instanceof
name|Icon
condition|)
block|{
name|setIcon
argument_list|(
operator|(
name|Icon
operator|)
name|value
argument_list|)
expr_stmt|;
name|super
operator|.
name|setValue
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setIcon
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|super
operator|.
name|setValue
argument_list|(
name|value
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|paintComponent (Graphics g)
specifier|public
name|void
name|paintComponent
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
comment|//Util.pr("her");
name|Graphics2D
name|g2
init|=
operator|(
name|Graphics2D
operator|)
name|g
decl_stmt|;
comment|//Font f = g2.getFont();//new Font("Plain", Font.PLAIN, 24);
comment|//g2.setColor(getBackground());
comment|//g2.fill(g2.getClipBounds());
comment|//g2.setColor(getForeground());
comment|//g2.setFont(f);
if|if
condition|(
name|antialiasing
condition|)
block|{
name|RenderingHints
name|rh
init|=
name|g2
operator|.
name|getRenderingHints
argument_list|()
decl_stmt|;
name|rh
operator|.
name|put
argument_list|(
name|RenderingHints
operator|.
name|KEY_ANTIALIASING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_ANTIALIAS_ON
argument_list|)
expr_stmt|;
name|rh
operator|.
name|put
argument_list|(
name|RenderingHints
operator|.
name|KEY_RENDERING
argument_list|,
name|RenderingHints
operator|.
name|VALUE_RENDER_QUALITY
argument_list|)
expr_stmt|;
name|g2
operator|.
name|setRenderingHints
argument_list|(
name|rh
argument_list|)
expr_stmt|;
block|}
comment|//g2.drawString(getText(), 3, f.getSize());
name|super
operator|.
name|paintComponent
argument_list|(
name|g2
argument_list|)
expr_stmt|;
block|}
comment|//public DefaultTableCellRenderer darker() { return darker; }
block|}
comment|/* public TableCellRenderer iconRenderer = new IconCellRenderer();         //new JTableButtonRenderer(getDefaultRenderer(JButton.class));     class IconCellRenderer extends DefaultTableCellRenderer {         protected void setValue(Object value) {             if (value instanceof Icon) {                 setIcon((Icon)value);                 super.setValue(null);             } else {                 setIcon(null);                 super.setValue(value);             }         }     }      class JTableButtonRenderer implements TableCellRenderer {       private TableCellRenderer __defaultRenderer;        public JTableButtonRenderer(TableCellRenderer renderer) {         __defaultRenderer = renderer;       }        public Component getTableCellRendererComponent(JTable table, Object value,                                                      boolean isSelected,                                                      boolean hasFocus,                                                      int row, int column)       {         if(value instanceof Component)           return (Component)value;         return __defaultRenderer.getTableCellRendererComponent(       table, value, isSelected, hasFocus, row, column);       }     }*/
DECL|method|ensureVisible (int row)
specifier|public
name|void
name|ensureVisible
parameter_list|(
name|int
name|row
parameter_list|)
block|{
name|JScrollBar
name|vert
init|=
name|sp
operator|.
name|getVerticalScrollBar
argument_list|()
decl_stmt|;
name|int
name|y
init|=
name|row
operator|*
name|getRowHeight
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|y
operator|<
name|vert
operator|.
name|getValue
argument_list|()
operator|)
operator|||
operator|(
name|y
operator|>
name|vert
operator|.
name|getValue
argument_list|()
operator|+
name|vert
operator|.
name|getVisibleAmount
argument_list|()
operator|)
condition|)
name|scrollToCenter
argument_list|(
name|row
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
DECL|method|scrollToCenter ( int rowIndex, int vColIndex)
specifier|public
name|void
name|scrollToCenter
parameter_list|(
name|int
name|rowIndex
parameter_list|,
name|int
name|vColIndex
parameter_list|)
block|{
if|if
condition|(
operator|!
operator|(
name|this
operator|.
name|getParent
argument_list|()
operator|instanceof
name|JViewport
operator|)
condition|)
block|{
return|return;
block|}
name|JViewport
name|viewport
init|=
operator|(
name|JViewport
operator|)
name|this
operator|.
name|getParent
argument_list|()
decl_stmt|;
comment|// This rectangle is relative to the table where the
comment|// northwest corner of cell (0,0) is always (0,0).
name|Rectangle
name|rect
init|=
name|this
operator|.
name|getCellRect
argument_list|(
name|rowIndex
argument_list|,
name|vColIndex
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|// The location of the view relative to the table
name|Rectangle
name|viewRect
init|=
name|viewport
operator|.
name|getViewRect
argument_list|()
decl_stmt|;
comment|// Translate the cell location so that it is relative
comment|// to the view, assuming the northwest corner of the
comment|// view is (0,0).
name|rect
operator|.
name|setLocation
argument_list|(
name|rect
operator|.
name|x
operator|-
name|viewRect
operator|.
name|x
argument_list|,
name|rect
operator|.
name|y
operator|-
name|viewRect
operator|.
name|y
argument_list|)
expr_stmt|;
comment|// Calculate location of rect if it were at the center of view
name|int
name|centerX
init|=
operator|(
name|viewRect
operator|.
name|width
operator|-
name|rect
operator|.
name|width
operator|)
operator|/
literal|2
decl_stmt|;
name|int
name|centerY
init|=
operator|(
name|viewRect
operator|.
name|height
operator|-
name|rect
operator|.
name|height
operator|)
operator|/
literal|2
decl_stmt|;
comment|// Fake the location of the cell so that scrollRectToVisible
comment|// will move the cell to the center
if|if
condition|(
name|rect
operator|.
name|x
operator|<
name|centerX
condition|)
block|{
name|centerX
operator|=
operator|-
name|centerX
expr_stmt|;
block|}
if|if
condition|(
name|rect
operator|.
name|y
operator|<
name|centerY
condition|)
block|{
name|centerY
operator|=
operator|-
name|centerY
expr_stmt|;
block|}
name|rect
operator|.
name|translate
argument_list|(
name|centerX
argument_list|,
name|centerY
argument_list|)
expr_stmt|;
comment|// Scroll the area into view.
name|viewport
operator|.
name|scrollRectToVisible
argument_list|(
name|rect
argument_list|)
expr_stmt|;
name|revalidate
argument_list|()
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
block|}
comment|/**    * updateFont    */
DECL|method|updateFont ()
specifier|public
name|void
name|updateFont
parameter_list|()
block|{
name|setFont
argument_list|(
name|GUIGlobals
operator|.
name|CURRENTFONT
argument_list|)
expr_stmt|;
name|setRowHeight
argument_list|(
name|GUIGlobals
operator|.
name|TABLE_ROW_PADDING
operator|+
name|GUIGlobals
operator|.
name|CURRENTFONT
operator|.
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

