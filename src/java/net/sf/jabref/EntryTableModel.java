begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003 Nizar N. Batada, Morten O. Alver   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
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
name|beans
operator|.
name|PropertyChangeListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|beans
operator|.
name|PropertyChangeEvent
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
name|export
operator|.
name|LatexFieldFormatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringTokenizer
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
name|imports
operator|.
name|ImportFormatReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_class
DECL|class|EntryTableModel
specifier|public
class|class
name|EntryTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|db
name|BibtexDatabase
name|db
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|columns
name|String
index|[]
name|columns
decl_stmt|;
comment|// Contains the current column names.
DECL|field|sorter
specifier|private
name|EntrySorter
name|sorter
decl_stmt|;
comment|//private Object[] entryIDs; // Temporary
comment|// Constants used to define how a cell should be rendered.
DECL|field|REQUIRED
DECL|field|OPTIONAL
specifier|public
specifier|static
specifier|final
name|int
name|REQUIRED
init|=
literal|1
decl_stmt|,
name|OPTIONAL
init|=
literal|2
decl_stmt|,
DECL|field|REQ_STRING
name|REQ_STRING
init|=
literal|1
decl_stmt|,
DECL|field|REQ_NUMBER
name|REQ_NUMBER
init|=
literal|2
decl_stmt|,
DECL|field|OPT_STRING
name|OPT_STRING
init|=
literal|3
decl_stmt|,
DECL|field|OTHER
name|OTHER
init|=
literal|3
decl_stmt|,
comment|//PDF_COL = 1, // The column displaying icons for linked pdfs.
DECL|field|ICON_COL
name|ICON_COL
init|=
literal|8
decl_stmt|;
comment|// Constant to indicate that an icon cell renderer should be used.
specifier|public
specifier|static
specifier|final
name|String
index|[]
DECL|field|PDF
name|PDF
init|=
block|{
literal|"pdf"
block|,
literal|"ps"
block|}
decl_stmt|,
DECL|field|URL_
name|URL_
init|=
block|{
literal|"url"
block|,
literal|"doi"
block|}
decl_stmt|;
DECL|field|padleft
specifier|public
name|int
name|padleft
init|=
operator|-
literal|1
decl_stmt|;
comment|// padleft indicates how many columns (starting from left) are
comment|// special columns (number column or icon column).
DECL|field|iconCols
specifier|private
name|HashMap
name|iconCols
init|=
operator|new
name|HashMap
argument_list|()
decl_stmt|;
DECL|field|nameCols
name|int
index|[]
name|nameCols
init|=
literal|null
decl_stmt|;
DECL|field|namesAsIs
DECL|field|namesFf
name|boolean
name|namesAsIs
decl_stmt|,
name|namesFf
decl_stmt|;
comment|//ImageIcon pdfIcon = new ImageIcon(GUIGlobals.pdfSmallIcon);
DECL|method|EntryTableModel (JabRefFrame frame_, BasePanel panel_, BibtexDatabase db_)
specifier|public
name|EntryTableModel
parameter_list|(
name|JabRefFrame
name|frame_
parameter_list|,
name|BasePanel
name|panel_
parameter_list|,
name|BibtexDatabase
name|db_
parameter_list|)
block|{
name|panel
operator|=
name|panel_
expr_stmt|;
name|frame
operator|=
name|frame_
expr_stmt|;
name|db
operator|=
name|db_
expr_stmt|;
name|columns
operator|=
name|panel
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"columnNames"
argument_list|)
expr_stmt|;
comment|// This must be done again if the column
comment|// preferences get changed.
name|remap
argument_list|()
expr_stmt|;
block|}
DECL|method|getColumnName (int col)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
return|return
name|GUIGlobals
operator|.
name|NUMBER_COL
return|;
block|}
elseif|else
if|if
condition|(
name|getIconTypeForColumn
argument_list|(
name|col
argument_list|)
operator|!=
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
comment|//	else {
return|return
name|Util
operator|.
name|nCase
argument_list|(
name|columns
index|[
name|col
operator|-
name|padleft
index|]
argument_list|)
return|;
comment|//}
block|}
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
comment|//Util.pr("rc "+sorter.getEntryCount());
return|return
name|sorter
operator|.
name|getEntryCount
argument_list|()
return|;
comment|//entryIDs.length;  // Temporary?
block|}
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
name|padleft
operator|+
name|columns
operator|.
name|length
return|;
block|}
DECL|method|getColumnClass (int column)
specifier|public
name|Class
name|getColumnClass
parameter_list|(
name|int
name|column
parameter_list|)
block|{
comment|//return (getIconTypeForColumn(column) != null ? Icon.class : String.class);
return|return
operator|(
name|getIconTypeForColumn
argument_list|(
name|column
argument_list|)
operator|!=
literal|null
condition|?
name|JLabel
operator|.
name|class
else|:
name|String
operator|.
name|class
operator|)
return|;
block|}
DECL|method|getValueAt (int row, int col)
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
comment|// Return the field named frame.prefs.columnNames[col] from the Entry
comment|// corresponding to the row.
name|Object
name|o
decl_stmt|;
name|BibtexEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
decl_stmt|;
name|String
index|[]
name|iconType
init|=
name|getIconTypeForColumn
argument_list|(
name|col
argument_list|)
decl_stmt|;
comment|// If non-null, indicates an icon column's type.
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
name|o
operator|=
literal|""
operator|+
operator|(
name|row
operator|+
literal|1
operator|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|iconType
operator|!=
literal|null
condition|)
block|{
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
operator|<
literal|0
condition|)
return|return
literal|null
return|;
comment|// Ok, so we are going to display an icon. Find out which one, and return it:
return|return
name|GUIGlobals
operator|.
name|getTableIcon
argument_list|(
name|iconType
index|[
name|hasField
index|]
argument_list|)
return|;
block|}
comment|//  if (col == 1)
comment|//  o = be.getType().getName();
comment|//else {
elseif|else
if|if
condition|(
name|columns
index|[
name|col
operator|-
name|padleft
index|]
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|TYPE_HEADER
argument_list|)
condition|)
block|{
name|o
operator|=
name|be
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
comment|//else if (columns[col-PADLEFT].equals(GUIGlobals.NUMBER_COL)) {
comment|//  o = ""+(row+1);
comment|//}
else|else
block|{
name|o
operator|=
name|be
operator|.
name|getField
argument_list|(
name|columns
index|[
name|col
operator|-
name|padleft
index|]
argument_list|)
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
name|nameCols
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|col
operator|-
name|padleft
operator|==
name|nameCols
index|[
name|i
index|]
condition|)
block|{
if|if
condition|(
name|o
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|namesAsIs
condition|)
block|{
return|return
name|o
return|;
block|}
else|else
block|{
if|if
condition|(
name|namesFf
condition|)
block|{
return|return
name|ImportFormatReader
operator|.
name|fixAuthor
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|ImportFormatReader
operator|.
name|fixAuthor_lastnameFirst
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
return|;
block|}
block|}
block|}
block|}
block|}
comment|//}
return|return
name|o
return|;
block|}
comment|/**    * This method returns a string array indicating the types of icons to be displayed in the given column.    * It returns null if the column is not an icon column, and thereby also serves to identify icon    * columns.    */
DECL|method|getIconTypeForColumn (int col)
specifier|public
name|String
index|[]
name|getIconTypeForColumn
parameter_list|(
name|int
name|col
parameter_list|)
block|{
name|Object
name|o
init|=
name|iconCols
operator|.
name|get
argument_list|(
operator|new
name|Integer
argument_list|(
name|col
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
condition|)
return|return
operator|(
name|String
index|[]
operator|)
name|o
return|;
else|else
return|return
literal|null
return|;
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
comment|//if ((col == 0)  || (col == 1)) return OTHER;
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
return|return
name|OTHER
return|;
block|}
if|if
condition|(
name|getIconTypeForColumn
argument_list|(
name|col
argument_list|)
operator|!=
literal|null
condition|)
block|{
return|return
name|ICON_COL
return|;
block|}
name|BibtexEntryType
name|type
init|=
operator|(
name|db
operator|.
name|getEntryById
argument_list|(
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
operator|)
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
name|columns
index|[
name|col
operator|-
name|padleft
index|]
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
name|columns
index|[
name|col
operator|-
name|padleft
index|]
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
name|columns
index|[
name|col
operator|-
name|padleft
index|]
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
DECL|method|isComplete (int row)
specifier|public
name|boolean
name|isComplete
parameter_list|(
name|int
name|row
parameter_list|)
block|{
name|BibtexEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|be
operator|.
name|hasAllRequiredFields
argument_list|()
return|;
block|}
DECL|method|hasCrossRef (int row)
specifier|public
name|boolean
name|hasCrossRef
parameter_list|(
name|int
name|row
parameter_list|)
block|{
name|BibtexEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|(
name|be
operator|.
name|getField
argument_list|(
literal|"crossref"
argument_list|)
operator|!=
literal|null
operator|)
return|;
block|}
DECL|method|nonZeroField (int row, String field)
specifier|public
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
comment|// Returns true iff the entry has a nonzero value in its
comment|// 'search' field.
name|BibtexEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|o
init|=
call|(
name|String
call|)
argument_list|(
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|(
operator|(
name|o
operator|!=
literal|null
operator|)
operator|&&
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
DECL|method|hasField (int row, String field)
specifier|public
name|boolean
name|hasField
parameter_list|(
name|int
name|row
parameter_list|,
name|String
name|field
parameter_list|)
block|{
comment|// Returns true iff the entry has a nonzero value in its
comment|// 'search' field.
name|BibtexEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|(
name|be
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|!=
literal|null
operator|)
return|;
block|}
DECL|method|remap ()
specifier|public
name|void
name|remap
parameter_list|()
block|{
comment|// Set the icon columns, indicating the number of special columns to the left.
comment|// We currently assume one icon column plus the number column.
name|iconCols
operator|.
name|clear
argument_list|()
expr_stmt|;
name|iconCols
operator|.
name|put
argument_list|(
operator|new
name|Integer
argument_list|(
literal|1
argument_list|)
argument_list|,
name|PDF
argument_list|)
expr_stmt|;
name|iconCols
operator|.
name|put
argument_list|(
operator|new
name|Integer
argument_list|(
literal|2
argument_list|)
argument_list|,
name|URL_
argument_list|)
expr_stmt|;
comment|// Add 1 to the number of icon columns to get padleft.
name|padleft
operator|=
literal|1
operator|+
name|iconCols
operator|.
name|size
argument_list|()
expr_stmt|;
comment|// Set up the int[] nameCols, to mark which columns should be
comment|// treated as lists of names. This is to provide a correct presentation
comment|// of names as efficiently as possible.
name|Vector
name|tmp
init|=
operator|new
name|Vector
argument_list|(
literal|2
argument_list|,
literal|1
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|columns
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|columns
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"author"
argument_list|)
operator|||
name|columns
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
literal|"editor"
argument_list|)
condition|)
block|{
name|tmp
operator|.
name|add
argument_list|(
operator|new
name|Integer
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|nameCols
operator|=
operator|new
name|int
index|[
name|tmp
operator|.
name|size
argument_list|()
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
name|nameCols
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|nameCols
index|[
name|i
index|]
operator|=
operator|(
operator|(
name|Integer
operator|)
name|tmp
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|intValue
argument_list|()
expr_stmt|;
block|}
name|namesAsIs
operator|=
name|panel
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesAsIs"
argument_list|)
expr_stmt|;
name|namesFf
operator|=
name|panel
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesFf"
argument_list|)
expr_stmt|;
comment|// Build a vector of prioritized search objectives,
comment|// then pick the 3 first.
name|Vector
name|fields
init|=
operator|new
name|Vector
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
decl_stmt|,
name|directions
init|=
operator|new
name|Vector
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
decl_stmt|;
comment|// For testing MARKED feature:
name|fields
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|MARKED
argument_list|)
expr_stmt|;
name|directions
operator|.
name|add
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|panel
operator|.
name|showingGroup
condition|)
block|{
comment|// Group search has the highest priority if active.
name|fields
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|GROUPSEARCH
argument_list|)
expr_stmt|;
name|directions
operator|.
name|add
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|panel
operator|.
name|showingSearchResults
condition|)
block|{
comment|// Normal search has priority over regular sorting.
name|fields
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|SEARCH
argument_list|)
expr_stmt|;
name|directions
operator|.
name|add
argument_list|(
operator|new
name|Boolean
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Then the sort options:
name|directions
operator|.
name|add
argument_list|(
operator|new
name|Boolean
argument_list|(
name|frame
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"priDescending"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|directions
operator|.
name|add
argument_list|(
operator|new
name|Boolean
argument_list|(
name|frame
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"secDescending"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|directions
operator|.
name|add
argument_list|(
operator|new
name|Boolean
argument_list|(
name|frame
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"terDescending"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|fields
operator|.
name|add
argument_list|(
name|frame
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"priSort"
argument_list|)
argument_list|)
expr_stmt|;
name|fields
operator|.
name|add
argument_list|(
name|frame
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"secSort"
argument_list|)
argument_list|)
expr_stmt|;
name|fields
operator|.
name|add
argument_list|(
name|frame
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"terSort"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Then pick the three highest ranking ones, and go.
if|if
condition|(
name|directions
operator|.
name|size
argument_list|()
operator|<
literal|4
condition|)
block|{
name|sorter
operator|=
name|db
operator|.
name|getSorter
argument_list|(
operator|new
name|EntryComparator
argument_list|(
operator|(
operator|(
name|Boolean
operator|)
name|directions
operator|.
name|elementAt
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|,
operator|(
operator|(
name|Boolean
operator|)
name|directions
operator|.
name|elementAt
argument_list|(
literal|1
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|,
operator|(
operator|(
name|Boolean
operator|)
name|directions
operator|.
name|elementAt
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|,
operator|(
name|String
operator|)
name|fields
operator|.
name|elementAt
argument_list|(
literal|0
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|fields
operator|.
name|elementAt
argument_list|(
literal|1
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|fields
operator|.
name|elementAt
argument_list|(
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sorter
operator|=
name|db
operator|.
name|getSorter
argument_list|(
operator|new
name|EntryComparator
argument_list|(
operator|(
operator|(
name|Boolean
operator|)
name|directions
operator|.
name|elementAt
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|,
operator|(
operator|(
name|Boolean
operator|)
name|directions
operator|.
name|elementAt
argument_list|(
literal|1
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|,
operator|(
operator|(
name|Boolean
operator|)
name|directions
operator|.
name|elementAt
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|,
operator|(
operator|(
name|Boolean
operator|)
name|directions
operator|.
name|elementAt
argument_list|(
literal|3
argument_list|)
operator|)
operator|.
name|booleanValue
argument_list|()
argument_list|,
operator|(
name|String
operator|)
name|fields
operator|.
name|elementAt
argument_list|(
literal|0
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|fields
operator|.
name|elementAt
argument_list|(
literal|1
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|fields
operator|.
name|elementAt
argument_list|(
literal|2
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|fields
operator|.
name|elementAt
argument_list|(
literal|3
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isCellEditable (int row, int col)
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
if|if
condition|(
name|col
operator|<
name|padleft
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// getColumnClass will throw a NullPointerException if there is no
comment|// entry in FieldTypes.GLOBAL_FIELD_TYPES for the column.
try|try
block|{
if|if
condition|(
operator|!
name|getColumnName
argument_list|(
name|col
argument_list|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|TYPE_HEADER
argument_list|)
condition|)
block|{
comment|//	    getColumnClass(col);
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ex
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|method|setValueAt (Object value, int row, int col)
specifier|public
name|void
name|setValueAt
parameter_list|(
name|Object
name|value
parameter_list|,
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
comment|// Called by the table cell editor when the user has edited a
comment|// field. From here the edited value is stored.
name|BibtexEntry
name|be
init|=
name|db
operator|.
name|getEntryById
argument_list|(
name|getNameFromNumber
argument_list|(
name|row
argument_list|)
argument_list|)
decl_stmt|;
name|boolean
name|set
init|=
literal|false
decl_stmt|;
name|String
name|toSet
init|=
literal|null
decl_stmt|,
name|fieldName
init|=
name|getColumnName
argument_list|(
name|col
argument_list|)
decl_stmt|,
name|text
decl_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|text
operator|=
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|toSet
operator|=
name|text
expr_stmt|;
name|Object
name|o
decl_stmt|;
if|if
condition|(
operator|(
operator|(
name|o
operator|=
name|be
operator|.
name|getField
argument_list|(
name|fieldName
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|)
operator|==
literal|null
operator|)
operator|||
operator|(
operator|(
name|o
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|o
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|toSet
argument_list|)
operator|)
condition|)
block|{
name|set
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|be
operator|.
name|getField
argument_list|(
name|fieldName
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|set
operator|=
literal|true
expr_stmt|;
block|}
block|}
if|if
condition|(
name|set
condition|)
block|{
try|try
block|{
if|if
condition|(
name|toSet
operator|!=
literal|null
condition|)
block|{
operator|(
operator|new
name|LatexFieldFormatter
argument_list|()
operator|)
operator|.
name|format
argument_list|(
name|toSet
argument_list|,
name|GUIGlobals
operator|.
name|isStandardField
argument_list|(
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Store this change in the UndoManager to facilitate undo.
name|Object
name|oldVal
init|=
name|be
operator|.
name|getField
argument_list|(
name|fieldName
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|undo
operator|.
name|UndoableFieldChange
argument_list|(
name|be
argument_list|,
name|fieldName
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|oldVal
argument_list|,
name|toSet
argument_list|)
argument_list|)
expr_stmt|;
comment|// .. ok.
name|be
operator|.
name|setField
argument_list|(
name|fieldName
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|toSet
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
comment|// Should the table also be scheduled for repaint?
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|ex
parameter_list|)
block|{
comment|//frame.output("Invalid field format. Use '#' only in pairs wrapping "
comment|//	  +"string names.");
name|frame
operator|.
name|output
argument_list|(
literal|"Invalid field format: "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getNameFromNumber (int number)
specifier|public
name|String
name|getNameFromNumber
parameter_list|(
name|int
name|number
parameter_list|)
block|{
comment|// Return the name of the Entry corresponding to the row. The
comment|// Entry will be retrieved from a DatabaseQuery. This is just
comment|// a temporary implementation.
return|return
name|sorter
operator|.
name|getIdAt
argument_list|(
name|number
argument_list|)
return|;
comment|//entryIDs[number].toString();
block|}
DECL|method|getNumberFromName (String name)
specifier|public
name|int
name|getNumberFromName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
comment|// Not very fast. Intended for use only in highlighting erronous
comment|// entry if save fails.
name|int
name|res
init|=
operator|-
literal|1
decl_stmt|,
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|i
operator|<
name|sorter
operator|.
name|getEntryCount
argument_list|()
operator|)
operator|&&
operator|(
name|res
operator|<
literal|0
operator|)
condition|)
block|{
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
name|sorter
operator|.
name|getIdAt
argument_list|(
name|i
argument_list|)
argument_list|)
condition|)
block|{
name|res
operator|=
name|i
expr_stmt|;
block|}
name|i
operator|++
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
block|}
end_class

end_unit

