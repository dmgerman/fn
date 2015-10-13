begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Toolkit
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Clipboard
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|ClipboardOwner
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|Transferable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
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
name|gui
operator|.
name|worker
operator|.
name|AbstractWorker
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
name|gui
operator|.
name|BasePanel
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
name|database
operator|.
name|BibtexDatabase
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
name|BibtexEntry
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
name|gui
operator|.
name|JabRefFrame
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
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Dec 12, 2006  * Time: 6:22:25 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|ExportToClipboardAction
specifier|public
class|class
name|ExportToClipboardAction
extends|extends
name|AbstractWorker
block|{
DECL|field|message
specifier|private
name|String
name|message
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|BibtexDatabase
name|database
decl_stmt|;
DECL|method|ExportToClipboardAction (JabRefFrame frame, BibtexDatabase database)
specifier|public
name|ExportToClipboardAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|BasePanel
name|panel
init|=
name|frame
operator|.
name|basePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"No entries selected."
argument_list|)
expr_stmt|;
name|getCallBack
argument_list|()
operator|.
name|update
argument_list|()
expr_stmt|;
return|return;
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|IExportFormat
argument_list|>
name|m
init|=
name|ExportFormats
operator|.
name|getExportFormats
argument_list|()
decl_stmt|;
name|IExportFormat
index|[]
name|formats
init|=
operator|new
name|ExportFormat
index|[
name|m
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|String
index|[]
name|array
init|=
operator|new
name|String
index|[
name|formats
operator|.
name|length
index|]
decl_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|;
for|for
control|(
name|IExportFormat
name|format
range|:
name|m
operator|.
name|values
argument_list|()
control|)
block|{
name|formats
index|[
name|piv
index|]
operator|=
name|format
expr_stmt|;
name|array
index|[
name|piv
index|]
operator|=
name|format
operator|.
name|getDisplayName
argument_list|()
expr_stmt|;
name|piv
operator|++
expr_stmt|;
block|}
name|JList
argument_list|<
name|String
argument_list|>
name|list
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|array
argument_list|)
decl_stmt|;
name|list
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|frame
argument_list|,
name|list
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select format"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|NO_OPTION
condition|)
block|{
return|return;
block|}
name|IExportFormat
name|format
init|=
name|formats
index|[
name|list
operator|.
name|getSelectedIndex
argument_list|()
index|]
decl_stmt|;
comment|// Set the global variable for this database's file directory before exporting,
comment|// so formatters can resolve linked files correctly.
comment|// (This is an ugly hack!)
name|Globals
operator|.
name|prefs
operator|.
name|fileDirForDatabase
operator|=
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
expr_stmt|;
comment|// Also store the database's file in a global variable:
name|Globals
operator|.
name|prefs
operator|.
name|databaseFile
operator|=
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|metaData
argument_list|()
operator|.
name|getFile
argument_list|()
expr_stmt|;
comment|/*final boolean custom = (list.getSelectedIndex()>= Globals.STANDARD_EXPORT_COUNT);         String dir = null;         if (custom) {             int index = list.getSelectedIndex() - Globals.STANDARD_EXPORT_COUNT;             dir = (String) (Globals.prefs.customExports.getElementAt(index)[1]);             File f = new File(dir);             lfName = f.getName();             lfName = lfName.substring(0, lfName.indexOf("."));             // Remove filename - we want the directory only.             dir = f.getParent() + System.getProperty("file.separator");         }         final String format = lfName,                 directory = dir;         */
name|File
name|tmp
init|=
literal|null
decl_stmt|;
name|Reader
name|reader
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|// To simplify the exporter API we simply do a normal export to a temporary
comment|// file, and read the contents afterwards:
name|tmp
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
literal|"jabrefCb"
argument_list|,
literal|".tmp"
argument_list|)
expr_stmt|;
name|tmp
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
name|BibtexEntry
index|[]
name|bes
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
name|HashSet
argument_list|<
name|String
argument_list|>
name|entries
init|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|(
name|bes
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|be
range|:
name|bes
control|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|be
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Write to file:
name|format
operator|.
name|performExport
argument_list|(
name|database
argument_list|,
name|panel
operator|.
name|metaData
argument_list|()
argument_list|,
name|tmp
operator|.
name|getPath
argument_list|()
argument_list|,
name|panel
operator|.
name|getEncoding
argument_list|()
argument_list|,
name|entries
argument_list|)
expr_stmt|;
comment|// Read the file and put the contents on the clipboard:
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|tmp
argument_list|)
argument_list|,
name|panel
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|s
decl_stmt|;
while|while
condition|(
operator|(
name|s
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|s
argument_list|)
expr_stmt|;
block|}
name|ClipboardOwner
name|owner
init|=
operator|new
name|ClipboardOwner
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|lostOwnership
parameter_list|(
name|Clipboard
name|clipboard
parameter_list|,
name|Transferable
name|content
parameter_list|)
block|{                 }
block|}
decl_stmt|;
comment|//StringSelection ss = new StringSelection(sw.toString());
name|RtfSelection
name|rs
init|=
operator|new
name|RtfSelection
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getSystemClipboard
argument_list|()
operator|.
name|setContents
argument_list|(
name|rs
argument_list|,
name|owner
argument_list|)
expr_stmt|;
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entries exported to clipboard"
argument_list|)
operator|+
literal|": "
operator|+
name|bes
operator|.
name|length
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
comment|//To change body of catch statement use File | Settings | File Templates.
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error exporting to clipboard"
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
comment|// Clean up:
if|if
condition|(
name|tmp
operator|!=
literal|null
condition|)
block|{
name|tmp
operator|.
name|delete
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|reader
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|reader
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
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
block|}
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|frame
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

