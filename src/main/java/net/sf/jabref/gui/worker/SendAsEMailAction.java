begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.worker
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|worker
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Desktop
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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringWriter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URI
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
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
name|List
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
name|exporter
operator|.
name|LatexFieldFormatter
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
name|bibtex
operator|.
name|BibtexEntryWriter
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
name|gui
operator|.
name|desktop
operator|.
name|JabRefDesktop
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
name|util
operator|.
name|Util
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Sends the selected entry as email - by Oliver Kopp  *  * It uses the mailto:-mechanism  *  * Microsoft Outlook does not support attachments via mailto  * Therefore, the folder(s), where the file(s) belonging to the entry are stored,  * are opened. This feature is disabled by default and can be switched on at  * preferences/external programs  */
end_comment

begin_class
DECL|class|SendAsEMailAction
specifier|public
class|class
name|SendAsEMailAction
extends|extends
name|AbstractWorker
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SendAsEMailAction
operator|.
name|class
argument_list|)
decl_stmt|;
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
DECL|method|SendAsEMailAction (JabRefFrame frame)
specifier|public
name|SendAsEMailAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
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
if|if
condition|(
operator|!
name|Desktop
operator|.
name|isDesktopSupported
argument_list|()
condition|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error creating email"
argument_list|)
expr_stmt|;
return|return;
block|}
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
return|return;
block|}
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|BibtexEntry
index|[]
name|bes
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
comment|// write the entries using sw, which is used later to form the email content
name|BibtexEntryWriter
name|bibtexEntryWriter
init|=
operator|new
name|BibtexEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|bes
control|)
block|{
try|try
block|{
name|bibtexEntryWriter
operator|.
name|write
argument_list|(
name|entry
argument_list|,
name|sw
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem creating Bibtex file for mailing."
argument_list|)
expr_stmt|;
block|}
block|}
name|ArrayList
argument_list|<
name|String
argument_list|>
name|attachments
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// open folders is needed to indirectly support email programs, which cannot handle
comment|//   the unofficial "mailto:attachment" property
name|boolean
name|openFolders
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OPEN_FOLDERS_OF_ATTACHED_FILES
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|fileList
init|=
name|Util
operator|.
name|getListOfLinkedFiles
argument_list|(
name|bes
argument_list|,
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
argument_list|)
decl_stmt|;
for|for
control|(
name|File
name|f
range|:
name|fileList
control|)
block|{
name|attachments
operator|.
name|add
argument_list|(
name|f
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|openFolders
condition|)
block|{
try|try
block|{
name|JabRefDesktop
operator|.
name|openFolderAndSelectFile
argument_list|(
name|f
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Could not open file"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|String
name|mailTo
init|=
literal|"?Body="
operator|.
name|concat
argument_list|(
name|sw
operator|.
name|getBuffer
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|mailTo
operator|=
name|mailTo
operator|.
name|concat
argument_list|(
literal|"&Subject="
argument_list|)
expr_stmt|;
name|mailTo
operator|=
name|mailTo
operator|.
name|concat
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EMAIL_SUBJECT
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|path
range|:
name|attachments
control|)
block|{
name|mailTo
operator|=
name|mailTo
operator|.
name|concat
argument_list|(
literal|"&Attachment=\""
argument_list|)
operator|.
name|concat
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|mailTo
operator|=
name|mailTo
operator|.
name|concat
argument_list|(
literal|"\""
argument_list|)
expr_stmt|;
block|}
name|URI
name|uriMailTo
decl_stmt|;
try|try
block|{
name|uriMailTo
operator|=
operator|new
name|URI
argument_list|(
literal|"mailto"
argument_list|,
name|mailTo
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e1
parameter_list|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error creating email"
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
name|message
argument_list|)
expr_stmt|;
return|return;
block|}
name|Desktop
name|desktop
init|=
name|Desktop
operator|.
name|getDesktop
argument_list|()
decl_stmt|;
try|try
block|{
name|desktop
operator|.
name|mail
argument_list|(
name|uriMailTo
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error creating email"
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
name|message
argument_list|)
expr_stmt|;
return|return;
block|}
name|message
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"%s: %d"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entries added to an email"
argument_list|)
argument_list|,
name|bes
operator|.
name|length
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

