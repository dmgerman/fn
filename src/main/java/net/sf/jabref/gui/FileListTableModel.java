begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|io
operator|.
name|FileUtil
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
name|logic
operator|.
name|util
operator|.
name|strings
operator|.
name|StringUtil
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
name|model
operator|.
name|entry
operator|.
name|FileField
import|;
end_import

begin_comment
comment|/**  * Data structure to contain a list of file links, parseable from a coded string.  * Doubles as a table model for the file list editor.  */
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
argument_list|<>
argument_list|()
decl_stmt|;
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
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
block|}
annotation|@
name|Override
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
block|{
comment|// Do nothing
block|}
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
block|{
name|value
operator|=
literal|""
expr_stmt|;
block|}
name|List
argument_list|<
name|FileField
operator|.
name|ParsedFileField
argument_list|>
name|fields
init|=
name|FileField
operator|.
name|parse
argument_list|(
name|value
argument_list|)
decl_stmt|;
name|ArrayList
argument_list|<
name|FileListEntry
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|FileField
operator|.
name|ParsedFileField
name|entry
range|:
name|fields
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|firstOnly
condition|)
block|{
return|return
name|decodeEntry
argument_list|(
name|entry
argument_list|,
name|deduceUnknownTypes
argument_list|)
return|;
block|}
else|else
block|{
name|files
operator|.
name|add
argument_list|(
name|decodeEntry
argument_list|(
name|entry
argument_list|,
name|deduceUnknownTypes
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|files
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
operator|(
name|entry
operator|==
literal|null
operator|)
operator|||
operator|(
name|entry
operator|.
name|getType
argument_list|()
operator|==
literal|null
operator|)
condition|)
block|{
return|return
literal|null
return|;
block|}
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
DECL|method|decodeEntry (FileField.ParsedFileField entry, boolean deduceUnknownType)
specifier|private
name|FileListEntry
name|decodeEntry
parameter_list|(
name|FileField
operator|.
name|ParsedFileField
name|entry
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
name|entry
operator|.
name|fileType
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
comment|// on mime type:
name|type
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByMimeType
argument_list|(
name|entry
operator|.
name|fileType
argument_list|)
expr_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
comment|// No type could be found from mime type on the extension:
name|Optional
argument_list|<
name|String
argument_list|>
name|extension
init|=
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
name|entry
operator|.
name|link
argument_list|)
decl_stmt|;
if|if
condition|(
name|extension
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|ExternalFileType
name|typeGuess
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|extension
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|typeGuess
operator|!=
literal|null
condition|)
block|{
name|type
operator|=
name|typeGuess
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
operator|new
name|FileListEntry
argument_list|(
name|entry
operator|.
name|description
argument_list|,
name|entry
operator|.
name|link
argument_list|,
name|type
argument_list|)
return|;
block|}
comment|/**      * Transform the file list shown in the table into a flat string representable      * as a BibTeX field:      * @return String representation.      */
DECL|method|getStringRepresentation ()
specifier|public
name|String
name|getStringRepresentation
parameter_list|()
block|{
name|String
index|[]
index|[]
name|array
init|=
operator|new
name|String
index|[
name|list
operator|.
name|size
argument_list|()
index|]
index|[]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|FileListEntry
name|entry
range|:
name|list
control|)
block|{
name|array
index|[
name|i
index|]
operator|=
name|entry
operator|.
name|getStringArrayRepresentation
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
return|return
name|StringUtil
operator|.
name|encodeStringArray
argument_list|(
name|array
argument_list|)
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
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<br>"
argument_list|)
expr_stmt|;
block|}
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
name|FileListEntry
name|fileListEntry
range|:
name|list
control|)
block|{
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

