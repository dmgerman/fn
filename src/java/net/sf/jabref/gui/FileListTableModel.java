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
name|Globals
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
name|AbstractTableModel
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
name|TableModelEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_comment
comment|/**  * Data structure to contain a list of file links, parseable from a coded string.  * Doubles as a table model for the file list editor. */
end_comment

begin_class
DECL|class|FileListTableModel
specifier|public
class|class
name|FileListTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|list
specifier|private
specifier|final
name|ArrayList
name|list
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
DECL|method|FileListTableModel ()
specifier|public
name|FileListTableModel
parameter_list|()
block|{     }
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
synchronized|synchronized
init|(
name|list
init|)
block|{
return|return
name|list
operator|.
name|size
argument_list|()
return|;
block|}
block|}
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|3
return|;
block|}
DECL|method|getColumnClass (int columnIndex)
specifier|public
name|Class
name|getColumnClass
parameter_list|(
name|int
name|columnIndex
parameter_list|)
block|{
return|return
name|String
operator|.
name|class
return|;
block|}
DECL|method|getValueAt (int rowIndex, int columnIndex)
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|rowIndex
parameter_list|,
name|int
name|columnIndex
parameter_list|)
block|{
synchronized|synchronized
init|(
name|list
init|)
block|{
name|FileListEntry
name|entry
init|=
operator|(
name|FileListEntry
operator|)
name|list
operator|.
name|get
argument_list|(
name|rowIndex
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|columnIndex
condition|)
block|{
case|case
literal|0
case|:
return|return
name|entry
operator|.
name|getDescription
argument_list|()
return|;
case|case
literal|1
case|:
return|return
name|entry
operator|.
name|getLink
argument_list|()
return|;
default|default:
return|return
name|entry
operator|.
name|getType
argument_list|()
operator|!=
literal|null
condition|?
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|""
return|;
block|}
block|}
block|}
DECL|method|getEntry (int index)
specifier|public
name|FileListEntry
name|getEntry
parameter_list|(
name|int
name|index
parameter_list|)
block|{
synchronized|synchronized
init|(
name|list
init|)
block|{
return|return
operator|(
name|FileListEntry
operator|)
name|list
operator|.
name|get
argument_list|(
name|index
argument_list|)
return|;
block|}
block|}
DECL|method|removeEntry (int index)
specifier|public
name|void
name|removeEntry
parameter_list|(
name|int
name|index
parameter_list|)
block|{
synchronized|synchronized
init|(
name|list
init|)
block|{
name|list
operator|.
name|remove
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|fireTableRowsDeleted
argument_list|(
name|index
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addEntry (int index, FileListEntry entry)
specifier|public
name|void
name|addEntry
parameter_list|(
name|int
name|index
parameter_list|,
name|FileListEntry
name|entry
parameter_list|)
block|{
synchronized|synchronized
init|(
name|list
init|)
block|{
name|list
operator|.
name|add
argument_list|(
name|index
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|fireTableRowsInserted
argument_list|(
name|index
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setValueAt (Object aValue, int rowIndex, int columnIndex)
specifier|public
name|void
name|setValueAt
parameter_list|(
name|Object
name|aValue
parameter_list|,
name|int
name|rowIndex
parameter_list|,
name|int
name|columnIndex
parameter_list|)
block|{     }
comment|/**      * Set up the table contents based on the flat string representation of the file list      * @param value The string representation      */
DECL|method|setContent (String value)
specifier|public
name|void
name|setContent
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|value
operator|=
literal|""
expr_stmt|;
name|ArrayList
name|newList
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|ArrayList
name|thisEntry
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
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
name|value
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|value
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'\\'
operator|)
condition|)
block|{
name|escaped
operator|=
literal|true
expr_stmt|;
continue|continue;
block|}
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|':'
operator|)
condition|)
block|{
name|thisEntry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|';'
operator|)
condition|)
block|{
name|thisEntry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
name|newList
operator|.
name|add
argument_list|(
name|decodeEntry
argument_list|(
name|thisEntry
argument_list|)
argument_list|)
expr_stmt|;
name|thisEntry
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
else|else
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|thisEntry
operator|.
name|add
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|thisEntry
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
name|newList
operator|.
name|add
argument_list|(
name|decodeEntry
argument_list|(
name|thisEntry
argument_list|)
argument_list|)
expr_stmt|;
synchronized|synchronized
init|(
name|list
init|)
block|{
name|list
operator|.
name|clear
argument_list|()
expr_stmt|;
name|list
operator|.
name|addAll
argument_list|(
name|newList
argument_list|)
expr_stmt|;
block|}
name|fireTableChanged
argument_list|(
operator|new
name|TableModelEvent
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|decodeEntry (ArrayList contents)
specifier|private
name|FileListEntry
name|decodeEntry
parameter_list|(
name|ArrayList
name|contents
parameter_list|)
block|{
return|return
operator|new
name|FileListEntry
argument_list|(
name|getElementIfAvailable
argument_list|(
name|contents
argument_list|,
literal|0
argument_list|)
argument_list|,
name|getElementIfAvailable
argument_list|(
name|contents
argument_list|,
literal|1
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByName
argument_list|(
name|getElementIfAvailable
argument_list|(
name|contents
argument_list|,
literal|2
argument_list|)
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getElementIfAvailable (ArrayList contents, int index)
specifier|private
name|String
name|getElementIfAvailable
parameter_list|(
name|ArrayList
name|contents
parameter_list|,
name|int
name|index
parameter_list|)
block|{
if|if
condition|(
name|index
operator|<
name|contents
operator|.
name|size
argument_list|()
condition|)
return|return
operator|(
name|String
operator|)
name|contents
operator|.
name|get
argument_list|(
name|index
argument_list|)
return|;
else|else
return|return
literal|""
return|;
block|}
comment|/**      * Transform the file list shown in the table into a flat string representable      * as a BibTeX field:      * @return String representation.      */
DECL|method|getStringRepresentation ()
specifier|public
name|String
name|getStringRepresentation
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
name|iterator
init|=
name|list
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|FileListEntry
name|entry
init|=
operator|(
name|FileListEntry
operator|)
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|encodeEntry
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Transform the file list shown in the table into a HTML string representation      * suitable for displaying the contents in a tooltip.      * @return Tooltip representation.      */
DECL|method|getToolTipHTMLRepresentation ()
specifier|public
name|String
name|getToolTipHTMLRepresentation
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|"<html>"
argument_list|)
decl_stmt|;
for|for
control|(
name|Iterator
name|iterator
init|=
name|list
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|FileListEntry
name|entry
init|=
operator|(
name|FileListEntry
operator|)
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|entry
operator|.
name|getDescription
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|" ("
argument_list|)
operator|.
name|append
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
if|if
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|append
argument_list|(
literal|"</html>"
argument_list|)
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|encodeEntry (FileListEntry entry)
specifier|private
name|String
name|encodeEntry
parameter_list|(
name|FileListEntry
name|entry
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|encodeString
argument_list|(
name|entry
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|encodeString
argument_list|(
name|entry
operator|.
name|getLink
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|encodeString
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
operator|!=
literal|null
condition|?
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
else|:
literal|""
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|encodeString (String s)
specifier|private
name|String
name|encodeString
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
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
name|s
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|char
name|c
init|=
name|s
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|c
operator|==
literal|';'
operator|)
operator|||
operator|(
name|c
operator|==
literal|':'
operator|)
operator|||
operator|(
name|c
operator|==
literal|'\\'
operator|)
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|print ()
specifier|public
name|void
name|print
parameter_list|()
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"----"
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
name|iterator
init|=
name|list
operator|.
name|iterator
argument_list|()
init|;
name|iterator
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|FileListEntry
name|fileListEntry
init|=
operator|(
name|FileListEntry
operator|)
name|iterator
operator|.
name|next
argument_list|()
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|fileListEntry
argument_list|)
expr_stmt|;
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"----"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

