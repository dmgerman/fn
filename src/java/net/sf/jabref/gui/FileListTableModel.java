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

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|ExternalFileType
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
name|external
operator|.
name|UnknownExternalFileType
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
argument_list|<
name|FileListEntry
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<
name|FileListEntry
argument_list|>
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
argument_list|<
name|String
argument_list|>
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
comment|/**      * Add an entry to the table model, and fire a change event. The change event      * is fired on the event dispatch thread.      * @param index The row index to insert the entry at.      * @param entry The entry to insert.      */
DECL|method|addEntry (final int index, final FileListEntry entry)
specifier|public
name|void
name|addEntry
parameter_list|(
specifier|final
name|int
name|index
parameter_list|,
specifier|final
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
if|if
condition|(
operator|!
name|SwingUtilities
operator|.
name|isEventDispatchThread
argument_list|()
condition|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|fireTableRowsInserted
argument_list|(
name|index
argument_list|,
name|index
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
else|else
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
name|setContent
argument_list|(
name|value
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setContentDontGuessTypes (String value)
specifier|public
name|void
name|setContentDontGuessTypes
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|setContent
argument_list|(
name|value
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|setContent (String value, boolean firstOnly, boolean deduceUnknownTypes)
specifier|private
name|FileListEntry
name|setContent
parameter_list|(
name|String
name|value
parameter_list|,
name|boolean
name|firstOnly
parameter_list|,
name|boolean
name|deduceUnknownTypes
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
argument_list|<
name|FileListEntry
argument_list|>
name|newList
init|=
operator|new
name|ArrayList
argument_list|<
name|FileListEntry
argument_list|>
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
argument_list|<
name|String
argument_list|>
name|thisEntry
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|boolean
name|inXmlChar
init|=
literal|false
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
comment|// Check if we are entering an XML special character construct such
comment|// as "&#44;", because we need to know in order to ignore the semicolon.
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
operator|(
name|c
operator|==
literal|'&'
operator|)
operator|&&
operator|!
name|inXmlChar
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|value
operator|.
name|length
argument_list|()
operator|>
name|i
operator|+
literal|1
operator|)
operator|&&
operator|(
name|value
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|==
literal|'#'
operator|)
condition|)
name|inXmlChar
operator|=
literal|true
expr_stmt|;
block|}
comment|// Check if we are exiting an XML special character construct:
elseif|else
if|if
condition|(
operator|!
name|escaped
operator|&&
name|inXmlChar
operator|&&
operator|(
name|c
operator|==
literal|';'
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|inXmlChar
operator|=
literal|false
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
operator|&&
operator|!
name|inXmlChar
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
if|if
condition|(
name|firstOnly
condition|)
return|return
name|decodeEntry
argument_list|(
name|thisEntry
argument_list|,
name|deduceUnknownTypes
argument_list|)
return|;
else|else
block|{
name|newList
operator|.
name|add
argument_list|(
name|decodeEntry
argument_list|(
name|thisEntry
argument_list|,
name|deduceUnknownTypes
argument_list|)
argument_list|)
expr_stmt|;
name|thisEntry
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
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
block|{
if|if
condition|(
name|firstOnly
condition|)
return|return
name|decodeEntry
argument_list|(
name|thisEntry
argument_list|,
name|deduceUnknownTypes
argument_list|)
return|;
else|else
name|newList
operator|.
name|add
argument_list|(
name|decodeEntry
argument_list|(
name|thisEntry
argument_list|,
name|deduceUnknownTypes
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
return|return
literal|null
return|;
block|}
comment|/**      * Convenience method for finding a label corresponding to the type of the      * first file link in the given field content. The difference between using      * this method and using setContent() on an instance of FileListTableModel      * is a slight optimization: with this method, parsing is discontinued after      * the first entry has been found.      * @param content The file field content, as fed to this class' setContent() method.      * @return A JLabel set up with no text and the icon of the first entry's file type,      *  or null if no entry was found or the entry had no icon.      */
DECL|method|getFirstLabel (String content)
specifier|public
specifier|static
name|JLabel
name|getFirstLabel
parameter_list|(
name|String
name|content
parameter_list|)
block|{
name|FileListTableModel
name|tm
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|FileListEntry
name|entry
init|=
name|tm
operator|.
name|setContent
argument_list|(
name|content
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|entry
operator|==
literal|null
operator|||
name|entry
operator|.
name|getType
argument_list|()
operator|==
literal|null
condition|)
return|return
literal|null
return|;
return|return
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getIconLabel
argument_list|()
return|;
block|}
DECL|method|decodeEntry (ArrayList<String> contents, boolean deduceUnknownType)
specifier|private
name|FileListEntry
name|decodeEntry
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
name|contents
parameter_list|,
name|boolean
name|deduceUnknownType
parameter_list|)
block|{
name|ExternalFileType
name|type
init|=
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
decl_stmt|;
if|if
condition|(
name|deduceUnknownType
operator|&&
operator|(
name|type
operator|instanceof
name|UnknownExternalFileType
operator|)
condition|)
block|{
comment|// No file type was recognized. Try to find a usable file type based
comment|// on the extension:
name|ExternalFileType
name|typeGuess
init|=
literal|null
decl_stmt|;
name|String
name|link
init|=
name|getElementIfAvailable
argument_list|(
name|contents
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|int
name|index
init|=
name|link
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|index
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|index
operator|<
name|link
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
condition|)
block|{
name|String
name|extension
init|=
name|link
operator|.
name|substring
argument_list|(
name|index
operator|+
literal|1
argument_list|)
decl_stmt|;
name|typeGuess
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|extension
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|typeGuess
operator|!=
literal|null
condition|)
name|type
operator|=
name|typeGuess
expr_stmt|;
block|}
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
name|type
argument_list|)
return|;
block|}
DECL|method|getElementIfAvailable (ArrayList<String> contents, int index)
specifier|private
name|String
name|getElementIfAvailable
parameter_list|(
name|ArrayList
argument_list|<
name|String
argument_list|>
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
argument_list|<
name|FileListEntry
argument_list|>
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
argument_list|<
name|FileListEntry
argument_list|>
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
argument_list|<
name|FileListEntry
argument_list|>
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

