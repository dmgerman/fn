begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util.io
package|package
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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|Set
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
name|BibDatabase
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
name|BibDatabaseContext
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
name|model
operator|.
name|entry
operator|.
name|FieldName
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
name|ParsedFileField
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
name|metadata
operator|.
name|FileDirectoryPreferences
import|;
end_import

begin_comment
comment|/**  * Search class for files.<br>  *<br>  * This class provides some functionality to search in a {@link BibDatabase} for  * files.<br>   * @author Nosh&Dan  */
end_comment

begin_class
DECL|class|DatabaseFileLookup
specifier|public
class|class
name|DatabaseFileLookup
block|{
DECL|field|fileCache
specifier|private
specifier|final
name|Set
argument_list|<
name|File
argument_list|>
name|fileCache
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|possibleFilePaths
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|possibleFilePaths
decl_stmt|;
comment|/**      * Creates an instance by passing a {@link BibDatabase} which will be used for the searches.      *      * @param database A {@link BibDatabase}.      */
DECL|method|DatabaseFileLookup (BibDatabaseContext databaseContext, FileDirectoryPreferences fileDirectoryPreferences)
specifier|public
name|DatabaseFileLookup
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|FileDirectoryPreferences
name|fileDirectoryPreferences
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|possibleFilePaths
operator|=
name|Optional
operator|.
name|ofNullable
argument_list|(
name|databaseContext
operator|.
name|getFileDirectory
argument_list|(
name|fileDirectoryPreferences
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|databaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|fileCache
operator|.
name|addAll
argument_list|(
name|parseFileField
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Returns whether the File<code>file</code> is present in the database      * as an attached File to an {@link BibEntry}.<br>      *<br>      * To do this, the field specified by the key<b>file</b> will be searched      * for the provided file for every {@link BibEntry} in the database.<br>      *<br>      * For the matching, the absolute file paths will be used.      *      * @param file      *            A {@link File} Object.      * @return<code>true</code>, if the file Object is stored in at least one      *         entry in the database, otherwise<code>false</code>.      */
DECL|method|lookupDatabase (File file)
specifier|public
name|boolean
name|lookupDatabase
parameter_list|(
name|File
name|file
parameter_list|)
block|{
return|return
name|fileCache
operator|.
name|contains
argument_list|(
name|file
argument_list|)
return|;
block|}
DECL|method|parseFileField (BibEntry entry)
specifier|private
name|List
argument_list|<
name|File
argument_list|>
name|parseFileField
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|entries
init|=
name|FileField
operator|.
name|parse
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|fileLinks
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|ParsedFileField
name|field
range|:
name|entries
control|)
block|{
name|String
name|link
init|=
name|field
operator|.
name|getLink
argument_list|()
decl_stmt|;
comment|// Do not query external file links (huge performance leak)
if|if
condition|(
name|link
operator|.
name|contains
argument_list|(
literal|"//"
argument_list|)
condition|)
block|{
continue|continue;
block|}
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|link
argument_list|,
name|possibleFilePaths
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fileLinks
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
return|return
name|fileLinks
return|;
block|}
block|}
end_class

end_unit

