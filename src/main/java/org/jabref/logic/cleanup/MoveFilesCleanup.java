begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.cleanup
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
package|;
end_package

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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Arrays
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
name|stream
operator|.
name|Collectors
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
name|FieldChange
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
name|cleanup
operator|.
name|CleanupJob
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
name|LinkedFile
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
name|metadata
operator|.
name|FileDirectoryPreferences
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

begin_class
DECL|class|MoveFilesCleanup
specifier|public
class|class
name|MoveFilesCleanup
implements|implements
name|CleanupJob
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
name|MoveFilesCleanup
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|fileDirectoryPreferences
specifier|private
specifier|final
name|FileDirectoryPreferences
name|fileDirectoryPreferences
decl_stmt|;
DECL|field|fileDirPattern
specifier|private
specifier|final
name|String
name|fileDirPattern
decl_stmt|;
DECL|field|singleFileFieldCleanup
specifier|private
name|LinkedFile
name|singleFileFieldCleanup
decl_stmt|;
DECL|method|MoveFilesCleanup (BibDatabaseContext databaseContext, String fileDirPattern, FileDirectoryPreferences fileDirectoryPreferences)
specifier|public
name|MoveFilesCleanup
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|String
name|fileDirPattern
parameter_list|,
name|FileDirectoryPreferences
name|fileDirectoryPreferences
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|databaseContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|fileDirPattern
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fileDirPattern
argument_list|)
expr_stmt|;
name|this
operator|.
name|fileDirectoryPreferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fileDirectoryPreferences
argument_list|)
expr_stmt|;
block|}
DECL|method|MoveFilesCleanup (BibDatabaseContext databaseContext, String fileDirPattern, FileDirectoryPreferences fileDirectoryPreferences, LinkedFile field)
specifier|public
name|MoveFilesCleanup
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|String
name|fileDirPattern
parameter_list|,
name|FileDirectoryPreferences
name|fileDirectoryPreferences
parameter_list|,
name|LinkedFile
name|field
parameter_list|)
block|{
name|this
argument_list|(
name|databaseContext
argument_list|,
name|fileDirPattern
argument_list|,
name|fileDirectoryPreferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|singleFileFieldCleanup
operator|=
name|field
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|cleanup (BibEntry entry)
specifier|public
name|List
argument_list|<
name|FieldChange
argument_list|>
name|cleanup
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|firstExistingFileDir
init|=
name|databaseContext
operator|.
name|getFirstExistingFileDir
argument_list|(
name|fileDirectoryPreferences
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|firstExistingFileDir
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|List
argument_list|<
name|Path
argument_list|>
name|paths
init|=
name|databaseContext
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|fileDirectoryPreferences
argument_list|)
decl_stmt|;
name|String
name|defaultFileDirectory
init|=
name|firstExistingFileDir
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|targetDirectory
init|=
name|FileHelper
operator|.
name|expandFilenameAsPath
argument_list|(
name|defaultFileDirectory
argument_list|,
name|paths
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|targetDirectory
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|fileList
decl_stmt|;
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|newFileList
decl_stmt|;
if|if
condition|(
name|singleFileFieldCleanup
operator|!=
literal|null
condition|)
block|{
name|fileList
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|singleFileFieldCleanup
argument_list|)
expr_stmt|;
comment|//Add all other except the current selected file
name|newFileList
operator|=
name|entry
operator|.
name|getFiles
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|name
lambda|->
operator|!
name|name
operator|.
name|equals
argument_list|(
name|singleFileFieldCleanup
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|newFileList
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|fileList
operator|=
name|entry
operator|.
name|getFiles
argument_list|()
expr_stmt|;
block|}
name|boolean
name|changed
init|=
literal|false
decl_stmt|;
for|for
control|(
name|LinkedFile
name|fileEntry
range|:
name|fileList
control|)
block|{
name|String
name|oldFileName
init|=
name|fileEntry
operator|.
name|getLink
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|oldFile
init|=
name|fileEntry
operator|.
name|findIn
argument_list|(
name|paths
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|oldFile
operator|.
name|isPresent
argument_list|()
operator|||
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|oldFile
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
name|newFileList
operator|.
name|add
argument_list|(
name|fileEntry
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|targetDirName
init|=
literal|""
decl_stmt|;
if|if
condition|(
operator|!
name|fileDirPattern
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|targetDirName
operator|=
name|FileUtil
operator|.
name|createDirNameFromPattern
argument_list|(
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|,
name|fileDirPattern
argument_list|)
expr_stmt|;
block|}
name|Path
name|newTargetFile
init|=
name|targetDirectory
operator|.
name|get
argument_list|()
operator|.
name|resolve
argument_list|(
name|targetDirName
argument_list|)
operator|.
name|resolve
argument_list|(
name|oldFile
operator|.
name|get
argument_list|()
operator|.
name|getFileName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|newTargetFile
argument_list|)
condition|)
block|{
comment|// We do not overwrite already existing files
name|newFileList
operator|.
name|add
argument_list|(
name|fileEntry
argument_list|)
expr_stmt|;
continue|continue;
block|}
try|try
block|{
if|if
condition|(
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|newTargetFile
argument_list|)
condition|)
block|{
name|Files
operator|.
name|createDirectories
argument_list|(
name|newTargetFile
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
literal|"Could no create necessary target directoires for renaming"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|oldFile
operator|.
name|get
argument_list|()
argument_list|,
name|newTargetFile
argument_list|,
literal|true
argument_list|)
condition|)
block|{
name|changed
operator|=
literal|true
expr_stmt|;
name|String
name|newEntryFilePath
init|=
name|Paths
operator|.
name|get
argument_list|(
name|defaultFileDirectory
argument_list|)
operator|.
name|relativize
argument_list|(
name|newTargetFile
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|LinkedFile
name|newFileEntry
init|=
name|fileEntry
decl_stmt|;
if|if
condition|(
operator|!
name|oldFileName
operator|.
name|equals
argument_list|(
name|newTargetFile
operator|.
name|toString
argument_list|()
argument_list|)
condition|)
block|{
name|newFileEntry
operator|=
operator|new
name|LinkedFile
argument_list|(
name|fileEntry
operator|.
name|getDescription
argument_list|()
argument_list|,
name|newEntryFilePath
argument_list|,
name|fileEntry
operator|.
name|getFileType
argument_list|()
argument_list|)
expr_stmt|;
name|changed
operator|=
literal|true
expr_stmt|;
block|}
name|newFileList
operator|.
name|add
argument_list|(
name|newFileEntry
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|changed
condition|)
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|entry
operator|.
name|setFiles
argument_list|(
name|newFileList
argument_list|)
decl_stmt|;
if|if
condition|(
name|change
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|Collections
operator|.
name|singletonList
argument_list|(
name|change
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
end_class

end_unit

