begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.worker
package|package
name|org
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
name|nio
operator|.
name|file
operator|.
name|Path
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
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|BaseAction
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|BackgroundTask
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|BibEntryWriter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|LatexFieldFormatter
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
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
implements|implements
name|BaseAction
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|SendAsEMailAction
operator|.
name|class
argument_list|)
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
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|this
operator|::
name|sendEmail
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|frame
operator|::
name|output
argument_list|)
operator|.
name|onFailure
argument_list|(
name|e
lambda|->
block|{
name|String
name|message
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error creating email"
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
name|message
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
argument_list|)
operator|.
name|executeWith
argument_list|(
name|Globals
operator|.
name|TASK_EXECUTOR
argument_list|)
expr_stmt|;
block|}
DECL|method|sendEmail ()
specifier|private
name|String
name|sendEmail
parameter_list|()
throws|throws
name|Exception
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
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error creating email"
argument_list|)
return|;
block|}
name|BasePanel
name|panel
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Base panel is not available."
argument_list|)
throw|;
block|}
if|if
condition|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires one or more entries to be selected."
argument_list|)
return|;
block|}
name|StringWriter
name|sw
init|=
operator|new
name|StringWriter
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bes
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
comment|// write the entries using sw, which is used later to form the email content
name|BibEntryWriter
name|bibtexEntryWriter
init|=
operator|new
name|BibEntryWriter
argument_list|(
operator|new
name|LatexFieldFormatter
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
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
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMode
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
name|warn
argument_list|(
literal|"Problem creating BibTeX file for mailing."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|List
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
name|Path
argument_list|>
name|fileList
init|=
name|FileUtil
operator|.
name|getListOfLinkedFiles
argument_list|(
name|bes
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|Path
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
name|toAbsolutePath
argument_list|()
operator|.
name|toString
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
name|toAbsolutePath
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
literal|"Cannot open file"
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
init|=
operator|new
name|URI
argument_list|(
literal|"mailto"
argument_list|,
name|mailTo
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|Desktop
name|desktop
init|=
name|Desktop
operator|.
name|getDesktop
argument_list|()
decl_stmt|;
name|desktop
operator|.
name|mail
argument_list|(
name|uriMailTo
argument_list|)
expr_stmt|;
return|return
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
name|size
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

