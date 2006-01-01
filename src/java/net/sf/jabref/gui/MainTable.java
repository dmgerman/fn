begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
operator|.
name|EntryTableTransferHandler
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
name|TableCellRenderer
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
name|TableModel
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
name|TableColumnModel
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|SortedList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|EventList
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|event
operator|.
name|ListEventListener
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|swing
operator|.
name|EventSelectionModel
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|swing
operator|.
name|TableComparatorChooser
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Oct 12, 2005  * Time: 10:29:39 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|MainTable
specifier|public
class|class
name|MainTable
extends|extends
name|JTable
block|{
DECL|field|tableFormat
specifier|private
name|MainTableFormat
name|tableFormat
decl_stmt|;
DECL|field|list
specifier|private
name|SortedList
name|list
decl_stmt|;
DECL|field|tableColorCodes
specifier|private
name|boolean
name|tableColorCodes
decl_stmt|;
DECL|field|selectionModel
specifier|private
name|EventSelectionModel
name|selectionModel
decl_stmt|;
DECL|field|comparatorChooser
specifier|private
name|TableComparatorChooser
name|comparatorChooser
decl_stmt|;
DECL|field|pane
specifier|private
name|JScrollPane
name|pane
decl_stmt|;
DECL|field|REQUIRED
specifier|public
specifier|static
specifier|final
name|int
name|REQUIRED
init|=
literal|1
decl_stmt|,
DECL|field|OPTIONAL
name|OPTIONAL
init|=
literal|2
decl_stmt|,
DECL|field|OTHER
name|OTHER
init|=
literal|3
decl_stmt|;
static|static
block|{
name|updateRenderers
argument_list|()
expr_stmt|;
block|}
DECL|method|MainTable (TableModel tableModel, MainTableFormat tableFormat, SortedList list)
specifier|public
name|MainTable
parameter_list|(
name|TableModel
name|tableModel
parameter_list|,
name|MainTableFormat
name|tableFormat
parameter_list|,
name|SortedList
name|list
parameter_list|)
block|{
name|super
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|this
operator|.
name|tableFormat
operator|=
name|tableFormat
expr_stmt|;
name|this
operator|.
name|list
operator|=
name|list
expr_stmt|;
name|tableColorCodes
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"tableColorCodesOn"
argument_list|)
expr_stmt|;
name|selectionModel
operator|=
operator|new
name|EventSelectionModel
argument_list|(
name|list
argument_list|)
expr_stmt|;
name|setSelectionModel
argument_list|(
name|selectionModel
argument_list|)
expr_stmt|;
name|pane
operator|=
operator|new
name|JScrollPane
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|pane
operator|.
name|getViewport
argument_list|()
operator|.
name|setBackground
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableBackground"
argument_list|)
argument_list|)
expr_stmt|;
name|setGridColor
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"gridColor"
argument_list|)
argument_list|)
expr_stmt|;
name|comparatorChooser
operator|=
operator|new
name|TableComparatorChooser
argument_list|(
name|this
argument_list|,
name|list
argument_list|,
literal|true
argument_list|)
expr_stmt|;
specifier|final
name|EventList
name|selected
init|=
name|getSelected
argument_list|()
decl_stmt|;
comment|// enable DnD
name|setDragEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setTransferHandler
argument_list|(
operator|new
name|EntryTableTransferHandler
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|setupComparatorChooser
argument_list|()
expr_stmt|;
name|setWidths
argument_list|()
expr_stmt|;
block|}
DECL|method|addSelectionListener (ListEventListener listener)
specifier|public
name|void
name|addSelectionListener
parameter_list|(
name|ListEventListener
name|listener
parameter_list|)
block|{
name|getSelected
argument_list|()
operator|.
name|addListEventListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|getPane ()
specifier|public
name|JScrollPane
name|getPane
parameter_list|()
block|{
return|return
name|pane
return|;
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
name|int
name|score
init|=
literal|0
decl_stmt|;
comment|//-3;
name|TableCellRenderer
name|renderer
init|=
name|defRenderer
decl_stmt|;
name|int
name|status
init|=
name|getCellStatus
argument_list|(
name|row
argument_list|,
name|column
argument_list|)
decl_stmt|;
comment|/*if (!panel.coloringBySearchResults ||                 nonZeroField(row, Globals.SEARCH))             score++;         if (!panel.coloringByGroup ||                 nonZeroField(row, Globals.GROUPSEARCH))             score += 2;          // Now, a grayed out renderer is for entries with -1, and         // a very grayed out one for entries with -2         if (score< -1)             renderer = veryGrayedOutRenderer;         else if (score == -1)             renderer = grayedOutRenderer;          else*/
if|if
condition|(
name|tableColorCodes
condition|)
block|{
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
operator|!
name|isComplete
argument_list|(
name|row
argument_list|)
condition|)
block|{
name|incRenderer
operator|.
name|setNumber
argument_list|(
name|row
argument_list|)
expr_stmt|;
name|renderer
operator|=
name|incRenderer
expr_stmt|;
block|}
else|else
block|{
name|compRenderer
operator|.
name|setNumber
argument_list|(
name|row
argument_list|)
expr_stmt|;
if|if
condition|(
name|isMarked
argument_list|(
name|row
argument_list|)
condition|)
block|{
name|renderer
operator|=
name|markedNumberRenderer
expr_stmt|;
name|markedNumberRenderer
operator|.
name|setNumber
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
else|else
name|renderer
operator|=
name|compRenderer
expr_stmt|;
block|}
block|}
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
elseif|else
if|if
condition|(
name|status
operator|==
name|EntryTableModel
operator|.
name|BOOLEAN
condition|)
name|renderer
operator|=
name|getDefaultRenderer
argument_list|(
name|Boolean
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
comment|// For MARKED feature:
if|if
condition|(
operator|(
name|column
operator|!=
literal|0
operator|)
operator|&&
name|isMarked
argument_list|(
name|row
argument_list|)
condition|)
block|{
name|renderer
operator|=
name|markedRenderer
expr_stmt|;
block|}
return|return
name|renderer
return|;
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
name|Globals
operator|.
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
name|Globals
operator|.
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
name|tableFormat
operator|.
name|padleft
condition|;
name|i
operator|++
control|)
block|{
comment|// Lock the width of icon columns.
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
name|cm
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|setMinWidth
argument_list|(
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|setMaxWidth
argument_list|(
name|GUIGlobals
operator|.
name|WIDTH_ICON_COL
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
name|tableFormat
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
name|tableFormat
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
block|}
block|}
DECL|method|getEntryAt (int row)
specifier|public
name|BibtexEntry
name|getEntryAt
parameter_list|(
name|int
name|row
parameter_list|)
block|{
return|return
operator|(
name|BibtexEntry
operator|)
name|list
operator|.
name|get
argument_list|(
name|row
argument_list|)
return|;
block|}
DECL|method|getSelectedEntries ()
specifier|public
name|BibtexEntry
index|[]
name|getSelectedEntries
parameter_list|()
block|{
specifier|final
name|BibtexEntry
index|[]
name|BE_ARRAY
init|=
operator|new
name|BibtexEntry
index|[
literal|0
index|]
decl_stmt|;
return|return
operator|(
name|BibtexEntry
index|[]
operator|)
name|getSelected
argument_list|()
operator|.
name|toArray
argument_list|(
name|BE_ARRAY
argument_list|)
return|;
block|}
DECL|method|setupComparatorChooser ()
specifier|private
name|void
name|setupComparatorChooser
parameter_list|()
block|{
comment|// First column:
name|java
operator|.
name|util
operator|.
name|List
name|comparators
init|=
name|comparatorChooser
operator|.
name|getComparatorsForColumn
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|comparators
operator|.
name|clear
argument_list|()
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FirstColumnComparator
argument_list|()
argument_list|)
expr_stmt|;
comment|// Icon columns:
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|tableFormat
operator|.
name|padleft
condition|;
name|i
operator|++
control|)
block|{
name|comparators
operator|=
name|comparatorChooser
operator|.
name|getComparatorsForColumn
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|clear
argument_list|()
expr_stmt|;
name|String
index|[]
name|iconField
init|=
name|tableFormat
operator|.
name|getIconTypeForColumn
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|IconComparator
argument_list|(
name|iconField
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Remaining columns:
for|for
control|(
name|int
name|i
init|=
name|tableFormat
operator|.
name|padleft
init|;
name|i
operator|<
name|tableFormat
operator|.
name|getColumnCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|comparators
operator|=
name|comparatorChooser
operator|.
name|getComparatorsForColumn
argument_list|(
name|i
argument_list|)
expr_stmt|;
name|comparators
operator|.
name|clear
argument_list|()
expr_stmt|;
name|comparators
operator|.
name|add
argument_list|(
operator|new
name|FieldComparator
argument_list|(
name|tableFormat
operator|.
name|getColumnName
argument_list|(
name|i
argument_list|)
operator|.
name|toLowerCase
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Set initial sort columns:
comment|// Default sort order:
name|String
index|[]
name|sortFields
init|=
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"priSort"
argument_list|)
block|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"secSort"
argument_list|)
block|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"terSort"
argument_list|)
block|}
decl_stmt|;
name|boolean
index|[]
name|sortDirections
init|=
operator|new
name|boolean
index|[]
block|{
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"priDescending"
argument_list|)
block|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"secDescending"
argument_list|)
block|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"terDescending"
argument_list|)
block|}
decl_stmt|;
comment|// descending
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sortFields
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|int
name|index
init|=
name|tableFormat
operator|.
name|getColumnIndex
argument_list|(
name|sortFields
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|comparatorChooser
operator|.
name|appendComparator
argument_list|(
name|index
argument_list|,
literal|0
argument_list|,
name|sortDirections
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getCellStatus (int row, int col)
specifier|public
name|int
name|getCellStatus
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
try|try
block|{
name|BibtexEntry
name|be
init|=
operator|(
name|BibtexEntry
operator|)
name|list
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|BibtexEntryType
name|type
init|=
name|be
operator|.
name|getType
argument_list|()
decl_stmt|;
name|String
name|columnName
init|=
name|tableFormat
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
name|columnName
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|KEY_FIELD
argument_list|)
operator|||
name|type
operator|.
name|isRequired
argument_list|(
name|columnName
argument_list|)
condition|)
block|{
return|return
name|REQUIRED
return|;
block|}
if|if
condition|(
name|type
operator|.
name|isOptional
argument_list|(
name|columnName
argument_list|)
condition|)
block|{
return|return
name|OPTIONAL
return|;
block|}
return|return
name|OTHER
return|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ex
parameter_list|)
block|{
comment|//System.out.println("Exception: getCellStatus");
return|return
name|OTHER
return|;
block|}
block|}
DECL|method|getSelected ()
specifier|public
name|EventList
name|getSelected
parameter_list|()
block|{
return|return
name|selectionModel
operator|.
name|getSelected
argument_list|()
return|;
block|}
DECL|method|findEntry (BibtexEntry entry)
specifier|public
name|int
name|findEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|list
operator|.
name|indexOf
argument_list|(
name|entry
argument_list|)
return|;
block|}
DECL|method|getIconTypeForColumn (int column)
specifier|public
name|String
index|[]
name|getIconTypeForColumn
parameter_list|(
name|int
name|column
parameter_list|)
block|{
return|return
name|tableFormat
operator|.
name|getIconTypeForColumn
argument_list|(
name|column
argument_list|)
return|;
block|}
DECL|method|nonZeroField (int row, String field)
specifier|private
name|boolean
name|nonZeroField
parameter_list|(
name|int
name|row
parameter_list|,
name|String
name|field
parameter_list|)
block|{
name|BibtexEntry
name|be
init|=
operator|(
name|BibtexEntry
operator|)
name|list
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|Object
name|o
init|=
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
return|return
operator|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|!
name|o
operator|.
name|equals
argument_list|(
literal|"0"
argument_list|)
operator|)
return|;
block|}
DECL|method|isComplete (int row)
specifier|private
name|boolean
name|isComplete
parameter_list|(
name|int
name|row
parameter_list|)
block|{
try|try
block|{
name|BibtexEntry
name|be
init|=
operator|(
name|BibtexEntry
operator|)
name|list
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
return|return
name|be
operator|.
name|hasAllRequiredFields
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ex
parameter_list|)
block|{
comment|//System.out.println("Exception: isComplete");
return|return
literal|true
return|;
block|}
block|}
DECL|method|isMarked (int row)
specifier|private
name|boolean
name|isMarked
parameter_list|(
name|int
name|row
parameter_list|)
block|{
try|try
block|{
name|BibtexEntry
name|be
init|=
operator|(
name|BibtexEntry
operator|)
name|list
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
return|return
name|Util
operator|.
name|isMarked
argument_list|(
name|be
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ex
parameter_list|)
block|{
comment|//System.out.println("Exception: isMarked");
return|return
literal|false
return|;
block|}
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
name|pane
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
comment|/**      * updateFont      */
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
name|pane
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
DECL|method|scrollToCenter (int rowIndex, int vColIndex)
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
DECL|field|defRenderer
specifier|private
specifier|static
name|GeneralRenderer
name|defRenderer
decl_stmt|,
DECL|field|reqRenderer
name|reqRenderer
decl_stmt|,
DECL|field|optRenderer
name|optRenderer
decl_stmt|,
DECL|field|grayedOutRenderer
name|grayedOutRenderer
decl_stmt|,
DECL|field|veryGrayedOutRenderer
name|veryGrayedOutRenderer
decl_stmt|,
DECL|field|markedRenderer
name|markedRenderer
decl_stmt|;
DECL|field|incRenderer
specifier|private
specifier|static
name|IncompleteRenderer
name|incRenderer
decl_stmt|;
DECL|field|compRenderer
specifier|private
specifier|static
name|CompleteRenderer
name|compRenderer
decl_stmt|,
DECL|field|markedNumberRenderer
name|markedNumberRenderer
decl_stmt|;
DECL|method|updateRenderers ()
specifier|public
specifier|static
name|void
name|updateRenderers
parameter_list|()
block|{
name|boolean
name|antialiasing
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"antialias"
argument_list|)
decl_stmt|;
name|defRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableBackground"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableText"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
name|reqRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableReqFieldBackground"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableText"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
name|optRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableOptFieldBackground"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableText"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
name|incRenderer
operator|=
operator|new
name|IncompleteRenderer
argument_list|(
name|antialiasing
argument_list|)
expr_stmt|;
name|compRenderer
operator|=
operator|new
name|CompleteRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableBackground"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
name|markedNumberRenderer
operator|=
operator|new
name|CompleteRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"markedEntryBackground"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
name|grayedOutRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"grayedOutBackground"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"grayedOutText"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
name|veryGrayedOutRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"veryGrayedOutBackground"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"veryGrayedOutText"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
name|markedRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"markedEntryBackground"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"tableText"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
block|}
DECL|class|IncompleteRenderer
specifier|static
class|class
name|IncompleteRenderer
extends|extends
name|GeneralRenderer
block|{
DECL|method|IncompleteRenderer (boolean antialiasing)
specifier|public
name|IncompleteRenderer
parameter_list|(
name|boolean
name|antialiasing
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
literal|"incompleteEntryBackground"
argument_list|)
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
name|super
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"This entry is incomplete"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setNumber (int number)
specifier|protected
name|void
name|setNumber
parameter_list|(
name|int
name|number
parameter_list|)
block|{
name|super
operator|.
name|setValue
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|number
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setValue (Object value)
specifier|protected
name|void
name|setValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{          }
block|}
DECL|class|CompleteRenderer
specifier|static
class|class
name|CompleteRenderer
extends|extends
name|GeneralRenderer
block|{
DECL|method|CompleteRenderer (Color color, boolean antialiasing)
specifier|public
name|CompleteRenderer
parameter_list|(
name|Color
name|color
parameter_list|,
name|boolean
name|antialiasing
parameter_list|)
block|{
name|super
argument_list|(
name|color
argument_list|,
name|antialiasing
argument_list|)
expr_stmt|;
block|}
DECL|method|setNumber (int number)
specifier|protected
name|void
name|setNumber
parameter_list|(
name|int
name|number
parameter_list|)
block|{
name|super
operator|.
name|setValue
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|number
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setValue (Object value)
specifier|protected
name|void
name|setValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{          }
block|}
block|}
end_class

end_unit

