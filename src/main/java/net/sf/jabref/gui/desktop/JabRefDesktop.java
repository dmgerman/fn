begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.desktop
package|package
name|net
operator|.
name|sf
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
name|ExternalFileTypeEntryEditor
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
name|ExternalFileTypes
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
name|gui
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
name|os
operator|.
name|OSX
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
name|os
operator|.
name|Windows
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
name|logic
operator|.
name|util
operator|.
name|DOI
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
name|OS
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|javax
operator|.
name|swing
operator|.
name|*
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

begin_import
import|import
name|java
operator|.
name|awt
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
name|Collections
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
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|JabRefDesktop
operator|.
name|class
argument_list|)
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
comment|/**      * Open a http/pdf/ps viewer for the given link string.      */
DECL|method|openExternalViewer (MetaData metaData, String initialLink, String initialFieldName)
specifier|public
specifier|static
name|void
name|openExternalViewer
parameter_list|(
name|MetaData
name|metaData
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
literal|"ps"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
operator|||
literal|"pdf"
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
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|File
argument_list|>
name|file
init|=
name|FileUtil
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
name|file
operator|.
name|get
argument_list|()
operator|.
name|exists
argument_list|()
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
name|getCanonicalPath
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
name|getName
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
literal|"pdf"
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
literal|"ps"
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
literal|"doi"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|Optional
argument_list|<
name|DOI
argument_list|>
name|doiUrl
init|=
name|DOI
operator|.
name|build
argument_list|(
name|link
argument_list|)
decl_stmt|;
if|if
condition|(
name|doiUrl
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|link
operator|=
name|doiUrl
operator|.
name|get
argument_list|()
operator|.
name|getURLAsASCIIString
argument_list|()
expr_stmt|;
block|}
comment|// should be opened in browser
name|fieldName
operator|=
literal|"url"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
literal|"eprint"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
name|fieldName
operator|=
literal|"url"
expr_stmt|;
comment|// Check to see if link field already contains a well formated URL
if|if
condition|(
operator|!
name|link
operator|.
name|startsWith
argument_list|(
literal|"http://"
argument_list|)
condition|)
block|{
name|link
operator|=
name|Util
operator|.
name|ARXIV_LOOKUP_PREFIX
operator|+
name|link
expr_stmt|;
block|}
block|}
if|if
condition|(
literal|"url"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
comment|// html
try|try
block|{
name|openBrowser
argument_list|(
name|link
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
literal|"Error opening file '"
operator|+
name|link
operator|+
literal|"'"
argument_list|,
name|e
argument_list|)
expr_stmt|;
comment|// TODO: should we rethrow the exception?
comment|// In BasePanel.java, the exception is catched and a text output to the frame
comment|// throw e;
block|}
block|}
elseif|else
if|if
condition|(
literal|"ps"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
try|try
block|{
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|OSX
operator|.
name|openFile
argument_list|(
name|link
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|Windows
operator|.
name|openFile
argument_list|(
name|link
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ExternalFileType
name|type
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByExt
argument_list|(
literal|"ps"
argument_list|)
decl_stmt|;
name|String
name|viewer
init|=
name|type
operator|==
literal|null
condition|?
literal|"xdg-open"
else|:
name|type
operator|.
name|getOpenWith
argument_list|()
decl_stmt|;
name|String
index|[]
name|cmdArray
init|=
operator|new
name|String
index|[
literal|2
index|]
decl_stmt|;
name|cmdArray
index|[
literal|0
index|]
operator|=
name|viewer
expr_stmt|;
name|cmdArray
index|[
literal|1
index|]
operator|=
name|link
expr_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|cmdArray
argument_list|)
expr_stmt|;
block|}
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
literal|"An error occured on the command: "
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PDFVIEWER
argument_list|)
operator|+
literal|" #"
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
literal|"pdf"
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
try|try
block|{
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|OSX
operator|.
name|openFile
argument_list|(
name|link
argument_list|,
literal|"pdf"
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|Windows
operator|.
name|openFile
argument_list|(
name|link
argument_list|)
expr_stmt|;
comment|/*                      * String[] spl = link.split("\\\\"); StringBuffer sb = new                      * StringBuffer(); for (int i = 0; i< spl.length; i++) { if                      * (i> 0) sb.append("\\"); if (spl[i].indexOf(" ")>= 0)                      * spl[i] = "\"" + spl[i] + "\""; sb.append(spl[i]); }                      * //pr(sb.toString()); link = sb.toString();                      *                      * String cmd = "cmd.exe /c start " + link;                      *                      * Process child = Runtime.getRuntime().exec(cmd);                      */
block|}
else|else
block|{
name|Linux
operator|.
name|openFile
argument_list|(
name|link
argument_list|,
literal|"pdf"
argument_list|)
expr_stmt|;
block|}
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
literal|"An error occured on the command: "
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PDFVIEWER
argument_list|)
operator|+
literal|" #"
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
comment|/**      * Open an external file, attempting to use the correct viewer for it.      *      * @param metaData      *            The MetaData for the database this file belongs to.      * @param link      *            The filename.      * @return false if the link couldn't be resolved, true otherwise.      */
DECL|method|openExternalFileAnyFormat (final MetaData metaData, String link, final ExternalFileType fileType)
specifier|public
specifier|static
name|boolean
name|openExternalFileAnyFormat
parameter_list|(
specifier|final
name|MetaData
name|metaData
parameter_list|,
name|String
name|link
parameter_list|,
specifier|final
name|ExternalFileType
name|fileType
parameter_list|)
throws|throws
name|IOException
block|{
name|boolean
name|httpLink
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|REMOTE_LINK_PATTERN
operator|.
name|matcher
argument_list|(
name|link
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
name|httpLink
operator|=
literal|true
expr_stmt|;
block|}
comment|// For other platforms we'll try to find the file type:
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|link
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|httpLink
condition|)
block|{
name|Optional
argument_list|<
name|File
argument_list|>
name|tmp
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|metaData
argument_list|,
name|link
argument_list|)
decl_stmt|;
if|if
condition|(
name|tmp
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|file
operator|=
name|tmp
operator|.
name|get
argument_list|()
expr_stmt|;
block|}
block|}
comment|// Check if we have arrived at a file type, and either an http link or an existing file:
if|if
condition|(
operator|(
name|httpLink
operator|||
name|file
operator|.
name|exists
argument_list|()
operator|)
operator|&&
operator|(
name|fileType
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// Open the file:
name|String
name|filePath
init|=
name|httpLink
condition|?
name|link
else|:
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
name|openExternalFilePlatformIndependent
argument_list|(
name|fileType
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
return|return
literal|false
return|;
comment|// No file matched the name, or we didn't know the file type.
block|}
block|}
DECL|method|openExternalFilePlatformIndependent (ExternalFileType fileType, String filePath)
specifier|private
specifier|static
name|void
name|openExternalFilePlatformIndependent
parameter_list|(
name|ExternalFileType
name|fileType
parameter_list|,
name|String
name|filePath
parameter_list|)
throws|throws
name|IOException
block|{
comment|// For URLs, other solutions are
comment|//  * https://github.com/rajing/browserlauncher2, but it is not available in maven
comment|//  * a the solution combining http://stackoverflow.com/a/5226244/873282 and http://stackoverflow.com/a/28807079/873282
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
comment|// Use "-a<application>" if the app is specified, and just "open<filename>" otherwise:
name|String
index|[]
name|cmd
init|=
operator|(
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|?
operator|new
name|String
index|[]
block|{
literal|"/usr/bin/open"
block|,
literal|"-a"
block|,
name|fileType
operator|.
name|getOpenWith
argument_list|()
block|,
name|filePath
block|}
else|:
operator|new
name|String
index|[]
block|{
literal|"/usr/bin/open"
block|,
name|filePath
block|}
decl_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|cmd
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
if|if
condition|(
operator|(
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Application is specified. Use it:
name|Windows
operator|.
name|openFileWithApplication
argument_list|(
name|filePath
argument_list|,
name|fileType
operator|.
name|getOpenWith
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Windows
operator|.
name|openFile
argument_list|(
name|filePath
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// Use the given app if specified, and the universal "xdg-open" otherwise:
name|String
index|[]
name|openWith
decl_stmt|;
if|if
condition|(
operator|(
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|openWith
operator|=
name|fileType
operator|.
name|getOpenWith
argument_list|()
operator|.
name|split
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|openWith
operator|=
operator|new
name|String
index|[]
block|{
literal|"xdg-open"
block|}
expr_stmt|;
block|}
name|String
index|[]
name|cmdArray
init|=
operator|new
name|String
index|[
name|openWith
operator|.
name|length
operator|+
literal|1
index|]
decl_stmt|;
name|System
operator|.
name|arraycopy
argument_list|(
name|openWith
argument_list|,
literal|0
argument_list|,
name|cmdArray
argument_list|,
literal|0
argument_list|,
name|openWith
operator|.
name|length
argument_list|)
expr_stmt|;
name|cmdArray
index|[
name|cmdArray
operator|.
name|length
operator|-
literal|1
index|]
operator|=
name|filePath
expr_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|cmdArray
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openExternalFileUnknown (JabRefFrame frame, BibEntry entry, MetaData metaData, String link, UnknownExternalFileType fileType)
specifier|public
specifier|static
name|boolean
name|openExternalFileUnknown
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|link
parameter_list|,
name|UnknownExternalFileType
name|fileType
parameter_list|)
throws|throws
name|IOException
block|{
name|String
name|cancelMessage
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to open file."
argument_list|)
decl_stmt|;
name|String
index|[]
name|options
init|=
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Define '%0'"
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Change file type"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
decl_stmt|;
name|String
name|defOption
init|=
name|options
index|[
literal|0
index|]
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"This external link is of the type '%0', which is undefined. What do you want to do?"
argument_list|,
name|fileType
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Undefined file type"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
name|options
argument_list|,
name|defOption
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|CANCEL_OPTION
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|cancelMessage
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
comment|// User wants to define the new file type. Show the dialog:
name|ExternalFileType
name|newType
init|=
operator|new
name|ExternalFileType
argument_list|(
name|fileType
operator|.
name|getName
argument_list|()
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|"new"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|ExternalFileTypeEntryEditor
name|editor
init|=
operator|new
name|ExternalFileTypeEntryEditor
argument_list|(
name|frame
argument_list|,
name|newType
argument_list|)
decl_stmt|;
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|editor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
comment|// Get the old list of types, add this one, and update the list in prefs:
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|fileTypes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeSelection
argument_list|()
argument_list|)
decl_stmt|;
name|fileTypes
operator|.
name|add
argument_list|(
name|newType
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|setExternalFileTypes
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
comment|// Finally, open the file:
return|return
name|openExternalFileAnyFormat
argument_list|(
name|metaData
argument_list|,
name|link
argument_list|,
name|newType
argument_list|)
return|;
block|}
else|else
block|{
comment|// Cancelled:
name|frame
operator|.
name|output
argument_list|(
name|cancelMessage
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
else|else
block|{
comment|// User wants to change the type of this link.
comment|// First get a model of all file links for this entry:
name|FileListTableModel
name|tModel
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
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
name|tModel
operator|.
name|setContent
argument_list|(
name|oldValue
argument_list|)
expr_stmt|;
name|FileListEntry
name|flEntry
init|=
literal|null
decl_stmt|;
comment|// Then find which one we are looking at:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|tModel
operator|.
name|getRowCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|FileListEntry
name|iEntry
init|=
name|tModel
operator|.
name|getEntry
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|iEntry
operator|.
name|link
operator|.
name|equals
argument_list|(
name|link
argument_list|)
condition|)
block|{
name|flEntry
operator|=
name|iEntry
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|flEntry
operator|==
literal|null
condition|)
block|{
comment|// This shouldn't happen, so I'm not sure what to put in here:
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Could not find the file list entry "
operator|+
name|link
operator|+
literal|" in "
operator|+
name|entry
argument_list|)
throw|;
block|}
name|FileListEntryEditor
name|editor
init|=
operator|new
name|FileListEntryEditor
argument_list|(
name|frame
argument_list|,
name|flEntry
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|metaData
argument_list|)
decl_stmt|;
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|editor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
comment|// Store the changes and add an undo edit:
name|String
name|newValue
init|=
name|tModel
operator|.
name|getStringRepresentation
argument_list|()
decl_stmt|;
name|UndoableFieldChange
name|ce
init|=
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|Globals
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
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
comment|// Finally, open the link:
return|return
name|openExternalFileAnyFormat
argument_list|(
name|metaData
argument_list|,
name|flEntry
operator|.
name|link
argument_list|,
name|flEntry
operator|.
name|type
argument_list|)
return|;
block|}
else|else
block|{
comment|// Cancelled:
name|frame
operator|.
name|output
argument_list|(
name|cancelMessage
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
block|}
comment|/**      * Opens a file browser of the folder of the given file. If possible, the file is selected      * @param fileLink the location of the file      * @throws IOException      */
DECL|method|openFolderAndSelectFile (String fileLink)
specifier|public
specifier|static
name|void
name|openFolderAndSelectFile
parameter_list|(
name|String
name|fileLink
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|Windows
operator|.
name|openFolderAndSelectFile
argument_list|(
name|fileLink
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|LINUX
condition|)
block|{
name|Linux
operator|.
name|openFolderAndSelectFile
argument_list|(
name|fileLink
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|fileLink
argument_list|)
decl_stmt|;
name|Desktop
operator|.
name|getDesktop
argument_list|()
operator|.
name|open
argument_list|(
name|f
operator|.
name|getParentFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
name|ExternalFileType
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
name|getAbsolutePath
argument_list|()
decl_stmt|;
name|absolutePath
operator|=
name|absolutePath
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|absolutePath
operator|.
name|lastIndexOf
argument_list|(
name|File
operator|.
name|separator
argument_list|)
operator|+
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|LINUX
condition|)
block|{
name|Linux
operator|.
name|openConsole
argument_list|(
name|absolutePath
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|Windows
operator|.
name|openConsole
argument_list|(
name|absolutePath
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|OSX
operator|.
name|openConsole
argument_list|(
name|absolutePath
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Operating system is not supported by this feature."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

