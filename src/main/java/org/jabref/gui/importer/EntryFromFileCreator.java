begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
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
name|FileFilter
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
name|StringTokenizer
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
name|gui
operator|.
name|filelist
operator|.
name|FileListEntry
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
name|filelist
operator|.
name|FileListTableModel
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
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_comment
comment|/**  * The interface EntryFromFileCreator does twice:<br>  * On the one hand, it defines a set of files, which it can deal with, on the  * other hand it provides the functionality to create a Bibtex entry out of a  * file. The interface extends the java.io.FileFilter to inherit a common way of  * defining file sets.  *  * @author Dan&Nosh  * @version 25.11.2008 | 23:39:03  *  */
end_comment

begin_class
DECL|class|EntryFromFileCreator
specifier|public
specifier|abstract
class|class
name|EntryFromFileCreator
implements|implements
name|FileFilter
block|{
DECL|field|MIN_PATH_TOKEN_LENGTH
specifier|private
specifier|static
specifier|final
name|int
name|MIN_PATH_TOKEN_LENGTH
init|=
literal|4
decl_stmt|;
DECL|field|externalFileType
specifier|protected
specifier|final
name|ExternalFileType
name|externalFileType
decl_stmt|;
comment|/**      * Constructor.<br>      * Forces subclasses to provide an {@link ExternalFileType} instance, which      * they build on.      *      * @param externalFileType      */
DECL|method|EntryFromFileCreator (ExternalFileType externalFileType)
name|EntryFromFileCreator
parameter_list|(
name|ExternalFileType
name|externalFileType
parameter_list|)
block|{
name|this
operator|.
name|externalFileType
operator|=
name|externalFileType
expr_stmt|;
block|}
DECL|method|createBibtexEntry (File f)
specifier|protected
specifier|abstract
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|createBibtexEntry
parameter_list|(
name|File
name|f
parameter_list|)
function_decl|;
comment|/**      *<p>      * To support platform independence, a creator must define what types of      * files it accepts on it's own.      *</p>      *<p>      * Basically, accepting files which end with the file extension that is      * described in the nested {@link #externalFileType} would work on windows      * systems. This is also the recommended criterion, on which files should be      * accepted.      *</p>      *<p>      * However, defining what types of files this creator accepts, is a property      * of<i>entry creators</i>, that is left to the user.      *</p>      */
annotation|@
name|Override
DECL|method|accept (File f)
specifier|public
specifier|abstract
name|boolean
name|accept
parameter_list|(
name|File
name|f
parameter_list|)
function_decl|;
comment|/**      * Name of this import format.      *      *<p>      * The name must be unique.      *</p>      *      * @return format name, must be unique and not<code>null</code>      */
DECL|method|getFormatName ()
specifier|public
specifier|abstract
name|String
name|getFormatName
parameter_list|()
function_decl|;
comment|/**      * Create one BibEntry containing information regarding the given File.      *      * @param f      * @param addPathTokensAsKeywords      * @return      */
DECL|method|createEntry (File f, boolean addPathTokensAsKeywords)
specifier|public
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|createEntry
parameter_list|(
name|File
name|f
parameter_list|,
name|boolean
name|addPathTokensAsKeywords
parameter_list|)
block|{
if|if
condition|(
operator|(
name|f
operator|==
literal|null
operator|)
operator|||
operator|!
name|f
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|newEntry
init|=
name|createBibtexEntry
argument_list|(
name|f
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|newEntry
operator|.
name|isPresent
argument_list|()
operator|)
condition|)
block|{
return|return
name|newEntry
return|;
block|}
if|if
condition|(
name|addPathTokensAsKeywords
condition|)
block|{
name|appendToField
argument_list|(
name|newEntry
operator|.
name|get
argument_list|()
argument_list|,
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
name|extractPathesToKeyWordsfield
argument_list|(
name|f
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|newEntry
operator|.
name|get
argument_list|()
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|)
condition|)
block|{
name|newEntry
operator|.
name|get
argument_list|()
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
name|f
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|addFileInfo
argument_list|(
name|newEntry
operator|.
name|get
argument_list|()
argument_list|,
name|f
argument_list|)
expr_stmt|;
return|return
name|newEntry
return|;
block|}
comment|/** Returns the ExternalFileType that is imported here */
DECL|method|getExternalFileType ()
specifier|public
name|ExternalFileType
name|getExternalFileType
parameter_list|()
block|{
return|return
name|externalFileType
return|;
block|}
comment|/**      * Splits the path to the file and builds a keywords String in the format      * that is used by Jabref.      *      * @param absolutePath      * @return      */
DECL|method|extractPathesToKeyWordsfield (String absolutePath)
specifier|private
specifier|static
name|String
name|extractPathesToKeyWordsfield
parameter_list|(
name|String
name|absolutePath
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|StringTokenizer
name|st
init|=
operator|new
name|StringTokenizer
argument_list|(
name|absolutePath
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|File
operator|.
name|separatorChar
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
name|st
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
name|String
name|token
init|=
name|st
operator|.
name|nextToken
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|st
operator|.
name|hasMoreTokens
argument_list|()
condition|)
block|{
comment|// ignore last token. The filename ist not wanted as keyword.
break|break;
block|}
if|if
condition|(
name|token
operator|.
name|length
argument_list|()
operator|>=
name|MIN_PATH_TOKEN_LENGTH
condition|)
block|{
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// TODO: find Jabref constant for delimter
name|sb
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|token
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|addFileInfo (BibEntry entry, File file)
specifier|private
name|void
name|addFileInfo
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|File
name|file
parameter_list|)
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
name|externalFileType
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|possibleFilePaths
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getFileDirectories
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|File
name|shortenedFileName
init|=
name|FileUtil
operator|.
name|shortenFileName
argument_list|(
name|file
argument_list|,
name|possibleFilePaths
argument_list|)
decl_stmt|;
name|FileListEntry
name|fileListEntry
init|=
operator|new
name|FileListEntry
argument_list|(
literal|""
argument_list|,
name|shortenedFileName
operator|.
name|getPath
argument_list|()
argument_list|,
name|fileType
argument_list|)
decl_stmt|;
name|FileListTableModel
name|model
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|model
operator|.
name|addEntry
argument_list|(
literal|0
argument_list|,
name|fileListEntry
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|,
name|model
operator|.
name|getStringRepresentation
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|appendToField (BibEntry entry, String field, String value)
specifier|protected
name|void
name|appendToField
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
operator|(
name|value
operator|==
literal|null
operator|)
operator|||
name|value
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|Optional
argument_list|<
name|String
argument_list|>
name|oVal
init|=
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
if|if
condition|(
name|oVal
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// TODO: find Jabref constant for delimter
if|if
condition|(
operator|!
name|oVal
operator|.
name|get
argument_list|()
operator|.
name|contains
argument_list|(
name|value
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|oVal
operator|.
name|get
argument_list|()
operator|+
literal|","
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|value
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addEntrysToEntry (BibEntry entry, List<BibEntry> entrys)
specifier|protected
name|void
name|addEntrysToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entrys
parameter_list|)
block|{
if|if
condition|(
name|entrys
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|BibEntry
name|e
range|:
name|entrys
control|)
block|{
name|addEntryDataToEntry
argument_list|(
name|entry
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|addEntryDataToEntry (BibEntry entry, BibEntry e)
specifier|protected
name|void
name|addEntryDataToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibEntry
name|e
parameter_list|)
block|{
for|for
control|(
name|String
name|field
range|:
name|e
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
name|e
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldContent
lambda|->
name|appendToField
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|fieldContent
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|externalFileType
operator|==
literal|null
condition|)
block|{
return|return
literal|"(undefined)"
return|;
block|}
return|return
name|externalFileType
operator|.
name|getName
argument_list|()
operator|+
literal|" (."
operator|+
name|externalFileType
operator|.
name|getExtension
argument_list|()
operator|+
literal|")"
return|;
block|}
block|}
end_class

end_unit

