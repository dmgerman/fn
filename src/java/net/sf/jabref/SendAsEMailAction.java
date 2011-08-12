begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|HashSet
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
name|JOptionPane
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

begin_comment
comment|/**  * Sends the selected entry as email - by Oliver Kopp  *   * It uses the mailto:-mechanism  *   * Microsoft Outlook does not support attachments via mailto  * Therefore, the folder(s), where the file(s) belonging to the entry are stored,  * are opened. This feature is disabled by default and can be switched on at  * preferences/external programs     */
end_comment

begin_class
DECL|class|SendAsEMailAction
specifier|public
class|class
name|SendAsEMailAction
extends|extends
name|AbstractWorker
block|{
DECL|field|message
name|String
name|message
init|=
literal|null
decl_stmt|;
DECL|field|frame
specifier|private
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
name|Globals
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
return|return;
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"No entries selected"
argument_list|)
operator|+
literal|"."
expr_stmt|;
name|getCallBack
argument_list|()
operator|.
name|update
argument_list|()
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
name|LatexFieldFormatter
name|ff
init|=
operator|new
name|LatexFieldFormatter
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|attachments
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|HashSet
argument_list|<
name|File
argument_list|>
name|directories
init|=
operator|new
name|HashSet
argument_list|<
name|File
argument_list|>
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
literal|"openFoldersOfAttachedFiles"
argument_list|)
decl_stmt|;
comment|// Quick hack for Windows feature
comment|// If OS is windows, then class Desktop is not used, but a direct call to explorer.exe
name|String
name|osName
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.name"
argument_list|)
decl_stmt|;
name|boolean
name|isWindows
init|=
name|osName
operator|.
name|startsWith
argument_list|(
literal|"Windows"
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
name|entry
operator|.
name|write
argument_list|(
name|sw
argument_list|,
name|ff
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|FileListTableModel
name|tm
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|tm
operator|.
name|setContent
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
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
name|tm
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|FileListEntry
name|flEntry
init|=
name|tm
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|String
name|path
init|=
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
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
name|path
operator|=
name|path
operator|.
name|concat
argument_list|(
name|java
operator|.
name|io
operator|.
name|File
operator|.
name|separator
argument_list|)
expr_stmt|;
name|path
operator|=
name|path
operator|.
name|concat
argument_list|(
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
name|attachments
operator|.
name|add
argument_list|(
name|path
argument_list|)
expr_stmt|;
if|if
condition|(
name|openFolders
condition|)
block|{
name|File
name|fn
init|=
operator|new
name|File
argument_list|(
name|path
argument_list|)
decl_stmt|;
if|if
condition|(
name|isWindows
condition|)
block|{
name|String
name|command
init|=
literal|"explorer.exe /select,"
operator|.
name|concat
argument_list|(
name|fn
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
decl_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|directories
operator|.
name|add
argument_list|(
name|fn
operator|.
name|getParentFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
name|message
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error creating email"
argument_list|)
expr_stmt|;
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
literal|null
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
name|e1
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|message
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error creating email"
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
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|message
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error creating email"
argument_list|)
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|openFolders
condition|)
block|{
for|for
control|(
name|File
name|d
range|:
name|directories
control|)
block|{
try|try
block|{
name|desktop
operator|.
name|open
argument_list|(
name|d
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// TODO Auto-generated catch block
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|message
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"%s: %s"
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not open directory"
argument_list|)
argument_list|,
name|d
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|message
operator|=
name|String
operator|.
name|format
argument_list|(
literal|"%s: %d"
argument_list|,
name|Globals
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

