begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.desktop
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|desktop
package|;
end_package

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
name|Files
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|JabRefGUI
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
name|os
operator|.
name|DefaultDesktop
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
name|os
operator|.
name|Linux
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
name|os
operator|.
name|NativeDesktop
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
name|os
operator|.
name|OSX
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
name|os
operator|.
name|Windows
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
name|externalfiletype
operator|.
name|ExternalFileType
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|OS
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
name|database
operator|.
name|BibDatabaseContext
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
name|FieldName
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
name|identifier
operator|.
name|DOI
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
name|identifier
operator|.
name|Eprint
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
name|util
operator|.
name|FileHelper
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
comment|/**  * TODO: Replace by http://docs.oracle.com/javase/7/docs/api/java/awt/Desktop.html  * http://stackoverflow.com/questions/18004150/desktop-api-is-not-supported-on-the-current-platform  */
end_comment

begin_class
DECL|class|JabRefDesktop
specifier|public
class|class
name|JabRefDesktop
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
name|JabRefDesktop
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|NATIVE_DESKTOP
specifier|private
specifier|static
specifier|final
name|NativeDesktop
name|NATIVE_DESKTOP
init|=
name|getNativeDesktop
argument_list|()
decl_stmt|;
DECL|field|REMOTE_LINK_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|REMOTE_LINK_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"[a-z]+://.*"
argument_list|)
decl_stmt|;
DECL|method|JabRefDesktop ()
specifier|private
name|JabRefDesktop
parameter_list|()
block|{     }
comment|/**      * Open a http/pdf/ps viewer for the given link string.      */
DECL|method|openExternalViewer (BibDatabaseContext databaseContext, String initialLink, String initialFieldName)
specifier|public
specifier|static
name|void
name|openExternalViewer
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|String
name|initialLink
parameter_list|,
name|String
name|initialFieldName
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|link
init|=
name|initialLink
decl_stmt|;
name|String
name|fieldName
init|=
name|initialFieldName
decl_stmt|;
if|if
condition|(
name|FieldName
operator|.
name|PS
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
operator|||
name|FieldName
operator|.
name|PDF
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
comment|// Find the default directory for this field type:
name|List
argument_list|<
name|String
argument_list|>
name|dir
init|=
name|databaseContext
operator|.
name|getFileDirectories
argument_list|(
name|fieldName
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
init|=
name|FileHelper
operator|.
name|expandFilename
argument_list|(
name|link
argument_list|,
name|dir
argument_list|)
decl_stmt|;
comment|// Check that the file exists:
if|if
condition|(
operator|!
name|file
operator|.
name|isPresent
argument_list|()
operator|||
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"File not found ("
operator|+
name|fieldName
operator|+
literal|"): '"
operator|+
name|link
operator|+
literal|"'."
argument_list|)
throw|;
block|}
name|link
operator|=
name|file
operator|.
name|get
argument_list|()
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
comment|// Use the correct viewer even if pdf and ps are mixed up:
name|String
index|[]
name|split
init|=
name|file
operator|.
name|get
argument_list|()
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|split
argument_list|(
literal|"\\."
argument_list|)
decl_stmt|;
if|if
condition|(
name|split
operator|.
name|length
operator|>=
literal|2
condition|)
block|{
if|if
condition|(
literal|"pdf"
operator|.
name|equalsIgnoreCase
argument_list|(
name|split
index|[
name|split
operator|.
name|length
operator|-
literal|1
index|]
argument_list|)
condition|)
block|{
name|fieldName
operator|=
name|FieldName
operator|.
name|PDF
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"ps"
operator|.
name|equalsIgnoreCase
argument_list|(
name|split
index|[
name|split
operator|.
name|length
operator|-
literal|1
index|]
argument_list|)
operator|||
operator|(
operator|(
name|split
operator|.
name|length
operator|>=
literal|3
operator|)
operator|&&
literal|"ps"
operator|.
name|equalsIgnoreCase
argument_list|(
name|split
index|[
name|split
operator|.
name|length
operator|-
literal|2
index|]
argument_list|)
operator|)
condition|)
block|{
name|fieldName
operator|=
name|FieldName
operator|.
name|PS
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|DOI
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|openDoi
argument_list|(
name|link
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|EPRINT
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|link
operator|=
name|Eprint
operator|.
name|build
argument_list|(
name|link
argument_list|)
operator|.
name|map
argument_list|(
name|Eprint
operator|::
name|getURIAsASCIIString
argument_list|)
operator|.
name|orElse
argument_list|(
name|link
argument_list|)
expr_stmt|;
comment|// should be opened in browser
name|fieldName
operator|=
name|FieldName
operator|.
name|URL
expr_stmt|;
block|}
if|if
condition|(
name|FieldName
operator|.
name|URL
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|openBrowser
argument_list|(
name|link
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|PS
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
try|try
block|{
name|NATIVE_DESKTOP
operator|.
name|openFile
argument_list|(
name|link
argument_list|,
name|FieldName
operator|.
name|PS
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
name|error
argument_list|(
literal|"An error occurred on the command: "
operator|+
name|link
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|FieldName
operator|.
name|PDF
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
try|try
block|{
name|NATIVE_DESKTOP
operator|.
name|openFile
argument_list|(
name|link
argument_list|,
name|FieldName
operator|.
name|PDF
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
name|error
argument_list|(
literal|"An error occurred on the command: "
operator|+
name|link
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Message: currently only PDF, PS and HTML files can be opened by double clicking"
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openDoi (String doi)
specifier|private
specifier|static
name|void
name|openDoi
parameter_list|(
name|String
name|doi
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|link
init|=
name|DOI
operator|.
name|parse
argument_list|(
name|doi
argument_list|)
operator|.
name|map
argument_list|(
name|DOI
operator|::
name|getURIAsASCIIString
argument_list|)
operator|.
name|orElse
argument_list|(
name|doi
argument_list|)
decl_stmt|;
name|openBrowser
argument_list|(
name|link
argument_list|)
expr_stmt|;
block|}
comment|/**      * Open an external file, attempting to use the correct viewer for it.      *      * @param databaseContext      *            The database this file belongs to.      * @param link      *            The filename.      * @return false if the link couldn't be resolved, true otherwise.      */
DECL|method|openExternalFileAnyFormat (final BibDatabaseContext databaseContext, String link, final Optional<ExternalFileType> type)
specifier|public
specifier|static
name|boolean
name|openExternalFileAnyFormat
parameter_list|(
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|String
name|link
parameter_list|,
specifier|final
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|type
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|REMOTE_LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|link
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
name|openExternalFilePlatformIndependent
argument_list|(
name|type
argument_list|,
name|link
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
init|=
name|FileHelper
operator|.
name|expandFilename
argument_list|(
name|databaseContext
argument_list|,
name|link
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|isPresent
argument_list|()
operator|&&
name|Files
operator|.
name|exists
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
operator|&&
operator|(
name|type
operator|.
name|isPresent
argument_list|()
operator|)
condition|)
block|{
comment|// Open the file:
name|String
name|filePath
init|=
name|file
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|openExternalFilePlatformIndependent
argument_list|(
name|type
argument_list|,
name|filePath
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
comment|// No file matched the name, try to open it directly using the given app
if|if
condition|(
name|type
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|openExternalFilePlatformIndependent
argument_list|(
name|type
argument_list|,
name|link
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|// Run out of ideas what to do...
return|return
literal|false
return|;
block|}
block|}
DECL|method|openExternalFileAnyFormat (Path file, final BibDatabaseContext databaseContext, final Optional<ExternalFileType> type)
specifier|public
specifier|static
name|boolean
name|openExternalFileAnyFormat
parameter_list|(
name|Path
name|file
parameter_list|,
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
specifier|final
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|type
parameter_list|)
throws|throws
name|IOException
block|{
return|return
name|openExternalFileAnyFormat
argument_list|(
name|databaseContext
argument_list|,
name|file
operator|.
name|toString
argument_list|()
argument_list|,
name|type
argument_list|)
return|;
block|}
DECL|method|openExternalFilePlatformIndependent (Optional<ExternalFileType> fileType, String filePath)
specifier|private
specifier|static
name|void
name|openExternalFilePlatformIndependent
parameter_list|(
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|fileType
parameter_list|,
name|String
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|fileType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
name|application
init|=
name|fileType
operator|.
name|get
argument_list|()
operator|.
name|getOpenWithApplication
argument_list|()
decl_stmt|;
if|if
condition|(
name|application
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|NATIVE_DESKTOP
operator|.
name|openFile
argument_list|(
name|filePath
argument_list|,
name|fileType
operator|.
name|get
argument_list|()
operator|.
name|getExtension
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|NATIVE_DESKTOP
operator|.
name|openFileWithApplication
argument_list|(
name|filePath
argument_list|,
name|application
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Opens a file browser of the folder of the given file. If possible, the file is selected      * @param fileLink the location of the file      * @throws IOException      */
DECL|method|openFolderAndSelectFile (Path fileLink)
specifier|public
specifier|static
name|void
name|openFolderAndSelectFile
parameter_list|(
name|Path
name|fileLink
parameter_list|)
throws|throws
name|IOException
block|{
name|NATIVE_DESKTOP
operator|.
name|openFolderAndSelectFile
argument_list|(
name|fileLink
argument_list|)
expr_stmt|;
block|}
comment|/**      * Opens the given URL using the system browser      *      * @param url the URL to open      * @throws IOException      */
DECL|method|openBrowser (String url)
specifier|public
specifier|static
name|void
name|openBrowser
parameter_list|(
name|String
name|url
parameter_list|)
throws|throws
name|IOException
block|{
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|fileType
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByExt
argument_list|(
literal|"html"
argument_list|)
decl_stmt|;
name|openExternalFilePlatformIndependent
argument_list|(
name|fileType
argument_list|,
name|url
argument_list|)
expr_stmt|;
block|}
DECL|method|openBrowser (URI url)
specifier|public
specifier|static
name|void
name|openBrowser
parameter_list|(
name|URI
name|url
parameter_list|)
throws|throws
name|IOException
block|{
name|openBrowser
argument_list|(
name|url
operator|.
name|toASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Opens the url with the users standard Browser.      * If that fails a popup will be shown to instruct the user to open the link manually      * and the link gets copied to the clipboard      * @param url      */
DECL|method|openBrowserShowPopup (String url)
specifier|public
specifier|static
name|void
name|openBrowserShowPopup
parameter_list|(
name|String
name|url
parameter_list|)
block|{
try|try
block|{
name|openBrowser
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|exception
parameter_list|)
block|{
name|Globals
operator|.
name|clipboardManager
operator|.
name|setContent
argument_list|(
name|url
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not open browser"
argument_list|,
name|exception
argument_list|)
expr_stmt|;
name|String
name|couldNotOpenBrowser
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not open browser."
argument_list|)
decl_stmt|;
name|String
name|openManually
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please open %0 manually."
argument_list|,
name|url
argument_list|)
decl_stmt|;
name|String
name|copiedToClipboard
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"The link has been copied to the clipboard."
argument_list|)
decl_stmt|;
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|output
argument_list|(
name|couldNotOpenBrowser
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|couldNotOpenBrowser
operator|+
literal|"\n"
operator|+
name|openManually
operator|+
literal|"\n"
operator|+
name|copiedToClipboard
argument_list|,
name|couldNotOpenBrowser
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Opens a new console starting on the given file location      *      * If no command is specified in {@link Globals},      * the default system console will be executed.      *      * @param file Location the console should be opened at.      */
DECL|method|openConsole (File file)
specifier|public
specifier|static
name|void
name|openConsole
parameter_list|(
name|File
name|file
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|file
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|String
name|absolutePath
init|=
name|file
operator|.
name|toPath
argument_list|()
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|getParent
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|boolean
name|usingDefault
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_CONSOLE_APPLICATION
argument_list|)
decl_stmt|;
if|if
condition|(
name|usingDefault
condition|)
block|{
name|NATIVE_DESKTOP
operator|.
name|openConsole
argument_list|(
name|absolutePath
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|command
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CONSOLE_COMMAND
argument_list|)
decl_stmt|;
name|command
operator|=
name|command
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|command
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|command
operator|=
name|command
operator|.
name|replaceAll
argument_list|(
literal|"\\s+"
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
comment|// normalize white spaces
name|String
index|[]
name|subcommands
init|=
name|command
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
decl_stmt|;
comment|// replace the placeholder if used
name|String
name|commandLoggingText
init|=
name|command
operator|.
name|replace
argument_list|(
literal|"%DIR"
argument_list|,
name|absolutePath
argument_list|)
decl_stmt|;
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Executing command \"%0\"..."
argument_list|,
name|commandLoggingText
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Executing command \""
operator|+
name|commandLoggingText
operator|+
literal|"\"..."
argument_list|)
expr_stmt|;
try|try
block|{
operator|new
name|ProcessBuilder
argument_list|(
name|subcommands
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|exception
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Open console"
argument_list|,
name|exception
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error occured while executing the command \"%0\"."
argument_list|,
name|commandLoggingText
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open console"
argument_list|)
operator|+
literal|" - "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|output
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// TODO: Move to OS.java
DECL|method|getNativeDesktop ()
specifier|public
specifier|static
name|NativeDesktop
name|getNativeDesktop
parameter_list|()
block|{
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
return|return
operator|new
name|Windows
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
return|return
operator|new
name|OSX
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|LINUX
condition|)
block|{
return|return
operator|new
name|Linux
argument_list|()
return|;
block|}
return|return
operator|new
name|DefaultDesktop
argument_list|()
return|;
block|}
block|}
end_class

end_unit

