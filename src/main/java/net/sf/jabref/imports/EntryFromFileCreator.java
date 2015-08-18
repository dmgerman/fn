begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|StringTokenizer
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
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
name|JabRef
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
name|JabRefPreferences
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
comment|/**  * The interface EntryFromFileCreator does twice:<br>  * On the one hand, it defines a set of files, which it can deal with, on the  * other hand it provides the functionality to create a Bibtex entry out of a  * file. The interface extends the java.io.FileFilter to inherit a common way of  * defining file sets.  *   * @author Dan&Nosh  * @version 25.11.2008 | 23:39:03  *   */
end_comment

begin_class
DECL|class|EntryFromFileCreator
specifier|public
specifier|abstract
class|class
name|EntryFromFileCreator
implements|implements
name|java
operator|.
name|io
operator|.
name|FileFilter
block|{
DECL|field|externalFileType
specifier|final
name|ExternalFileType
name|externalFileType
decl_stmt|;
comment|/**      * Constructor.<br>      * Forces subclasses to provide an {@link ExternalFileType} instance, which      * they build on.      *       * @param externalFileType      */
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
name|BibtexEntry
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
comment|/**      * Name of this import format.      *       *<p>      * The name must be unique.      *</p>      *       * @return format name, must be unique and not<code>null</code>      */
DECL|method|getFormatName ()
specifier|public
specifier|abstract
name|String
name|getFormatName
parameter_list|()
function_decl|;
comment|/**      * Create one BibtexEntry containing information regarding the given File.      *       * @param f      * @param addPathTokensAsKeywords      * @return      */
DECL|method|createEntry (File f, boolean addPathTokensAsKeywords)
specifier|public
name|BibtexEntry
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
name|f
operator|==
literal|null
operator|||
operator|!
name|f
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
name|BibtexEntry
name|newEntry
init|=
name|createBibtexEntry
argument_list|(
name|f
argument_list|)
decl_stmt|;
if|if
condition|(
name|newEntry
operator|==
literal|null
condition|)
block|{
return|return
literal|null
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
argument_list|,
literal|"keywords"
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
name|newEntry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
operator|==
literal|null
condition|)
block|{
name|newEntry
operator|.
name|setField
argument_list|(
literal|"title"
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
comment|/**      * Splits the path to the file and builds a keywords String in the format      * that is used by Jabref.      *       * @param absolutePath      * @return      */
DECL|method|extractPathesToKeyWordsfield (String absolutePath)
specifier|private
name|String
name|extractPathesToKeyWordsfield
parameter_list|(
name|String
name|absolutePath
parameter_list|)
block|{
specifier|final
name|int
name|MIN_PATH_TOKEN_LENGTH
init|=
literal|4
decl_stmt|;
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
literal|""
operator|+
name|File
operator|.
name|separatorChar
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
literal|","
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
DECL|method|addFileInfo (BibtexEntry entry, File file)
specifier|private
name|void
name|addFileInfo
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|File
name|file
parameter_list|)
block|{
name|JabRefPreferences
name|jabRefPreferences
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|ExternalFileType
name|fileType
init|=
name|jabRefPreferences
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|externalFileType
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
name|String
index|[]
name|possibleFilePaths
init|=
name|JabRef
operator|.
name|jrf
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
literal|"file"
argument_list|,
name|model
operator|.
name|getStringRepresentation
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|appendToField (BibtexEntry entry, String field, String value)
name|void
name|appendToField
parameter_list|(
name|BibtexEntry
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
name|value
operator|==
literal|null
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|value
argument_list|)
condition|)
block|{
return|return;
block|}
name|String
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
operator|==
literal|null
condition|)
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
else|else
block|{
comment|// TODO: find Jabref constant for delimter
if|if
condition|(
operator|!
name|oVal
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
operator|+
literal|","
operator|+
name|value
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|addEntrysToEntry (BibtexEntry entry, List<BibtexEntry> entrys)
name|void
name|addEntrysToEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|BibtexEntry
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
name|BibtexEntry
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
DECL|method|addEntryDataToEntry (BibtexEntry entry, BibtexEntry e)
name|void
name|addEntryDataToEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexEntry
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
name|getAllFields
argument_list|()
control|)
block|{
name|appendToField
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|e
operator|.
name|getField
argument_list|(
name|field
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

