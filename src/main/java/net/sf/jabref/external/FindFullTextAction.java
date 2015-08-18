begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|GUIGlobals
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
name|undo
operator|.
name|UndoableFieldChange
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
name|FileListEntry
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
name|FileListTableModel
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
name|BibtexEntry
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
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_comment
comment|/**  * Try to download fulltext PDF for selected entry(ies) by following URL or DOI link.  */
end_comment

begin_class
DECL|class|FindFullTextAction
specifier|public
class|class
name|FindFullTextAction
extends|extends
name|AbstractWorker
block|{
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|result
specifier|private
name|FindFullText
operator|.
name|FindResult
name|result
decl_stmt|;
DECL|method|FindFullTextAction (BasePanel basePanel)
specifier|public
name|FindFullTextAction
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|Throwable
block|{
name|basePanel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Looking for full text document..."
argument_list|)
argument_list|)
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
name|entry
operator|=
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
index|[
literal|0
index|]
expr_stmt|;
name|FindFullText
name|fft
init|=
operator|new
name|FindFullText
argument_list|()
decl_stmt|;
name|result
operator|=
name|fft
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
comment|//pdfURL = new URL("http://geog-www.sbs.ohio-state.edu/faculty/bmark/abbott_etal_ppp03.pdf");
if|if
condition|(
name|result
operator|.
name|url
operator|!=
literal|null
condition|)
block|{
comment|//System.out.println("PDF URL: "+result.url);
name|String
name|bibtexKey
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
name|String
index|[]
name|dirs
init|=
name|basePanel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|dirs
operator|.
name|length
operator|==
literal|0
condition|)
block|{
comment|// TODO: error message if file dir not defined
comment|//JOptionPane.showMessageDialog(frame, Globals.lang);
return|return;
block|}
name|DownloadExternalFile
name|def
init|=
operator|new
name|DownloadExternalFile
argument_list|(
name|basePanel
operator|.
name|frame
argument_list|()
argument_list|,
name|basePanel
operator|.
name|metaData
argument_list|()
argument_list|,
name|bibtexKey
argument_list|)
decl_stmt|;
try|try
block|{
name|def
operator|.
name|download
argument_list|(
name|result
operator|.
name|url
argument_list|,
operator|new
name|DownloadExternalFile
operator|.
name|DownloadCallback
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|downloadComplete
parameter_list|(
name|FileListEntry
name|file
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"finished"
argument_list|)
expr_stmt|;
name|FileListTableModel
name|tm
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|String
name|oldValue
init|=
name|entry
operator|.
name|getField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
name|tm
operator|.
name|setContent
argument_list|(
name|oldValue
argument_list|)
expr_stmt|;
name|tm
operator|.
name|addEntry
argument_list|(
name|tm
operator|.
name|getRowCount
argument_list|()
argument_list|,
name|file
argument_list|)
expr_stmt|;
name|String
name|newValue
init|=
name|tm
operator|.
name|getStringRepresentation
argument_list|()
decl_stmt|;
name|UndoableFieldChange
name|edit
init|=
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|,
name|oldValue
argument_list|,
name|newValue
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|edit
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|basePanel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Finished downloading full text document"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|message
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|result
operator|.
name|status
condition|)
block|{
case|case
name|FindFullText
operator|.
name|UNKNOWN_DOMAIN
case|:
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to find full text article. No search algorithm "
operator|+
literal|"defined for the '%0' web site."
argument_list|,
name|result
operator|.
name|host
argument_list|)
expr_stmt|;
break|break;
case|case
name|FindFullText
operator|.
name|WRONG_MIME_TYPE
case|:
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Found pdf link, but received the wrong MIME type. "
operator|+
literal|"This could indicate that you don't have access to the fulltext article."
argument_list|)
expr_stmt|;
break|break;
case|case
name|FindFullText
operator|.
name|LINK_NOT_FOUND
case|:
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to find full text document in the linked web page."
argument_list|)
expr_stmt|;
break|break;
case|case
name|FindFullText
operator|.
name|IO_EXCEPTION
case|:
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Connection error when trying to find full text document."
argument_list|)
expr_stmt|;
break|break;
case|case
name|FindFullText
operator|.
name|NO_URLS_DEFINED
case|:
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"This entry provides no URL or DOI links."
argument_list|)
expr_stmt|;
break|break;
block|}
name|basePanel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Full text article download failed"
argument_list|)
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|basePanel
operator|.
name|frame
argument_list|()
argument_list|,
name|message
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Full text article download failed"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

