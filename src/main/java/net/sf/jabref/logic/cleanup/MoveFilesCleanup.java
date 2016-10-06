begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|TypedBibEntry
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
name|FieldChange
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
name|cleanup
operator|.
name|CleanupJob
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

begin_class
DECL|class|MoveFilesCleanup
specifier|public
class|class
name|MoveFilesCleanup
implements|implements
name|CleanupJob
block|{
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
DECL|method|MoveFilesCleanup (BibDatabaseContext databaseContext, FileDirectoryPreferences fileDirectoryPreferences)
specifier|public
name|MoveFilesCleanup
parameter_list|(
name|BibDatabaseContext
name|databaseContext
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
if|if
condition|(
operator|!
name|databaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|getDefaultFileDirectory
argument_list|()
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
name|String
argument_list|>
name|paths
init|=
name|databaseContext
operator|.
name|getFileDirectory
argument_list|(
name|fileDirectoryPreferences
argument_list|)
decl_stmt|;
name|String
name|defaultFileDirectory
init|=
name|databaseContext
operator|.
name|getMetaData
argument_list|()
operator|.
name|getDefaultFileDirectory
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|File
argument_list|>
name|targetDirectory
init|=
name|FileUtil
operator|.
name|expandFilename
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
name|TypedBibEntry
name|typedEntry
init|=
operator|new
name|TypedBibEntry
argument_list|(
name|entry
argument_list|,
name|databaseContext
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|fileList
init|=
name|typedEntry
operator|.
name|getFiles
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|newFileList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|boolean
name|changed
init|=
literal|false
decl_stmt|;
for|for
control|(
name|ParsedFileField
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
name|File
argument_list|>
name|oldFile
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|oldFileName
argument_list|,
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
name|oldFile
operator|.
name|get
argument_list|()
operator|.
name|exists
argument_list|()
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
name|File
name|targetFile
init|=
operator|new
name|File
argument_list|(
name|targetDirectory
operator|.
name|get
argument_list|()
argument_list|,
name|oldFile
operator|.
name|get
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|targetFile
operator|.
name|exists
argument_list|()
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
name|oldFile
operator|.
name|get
argument_list|()
operator|.
name|renameTo
argument_list|(
name|targetFile
argument_list|)
expr_stmt|;
name|String
name|newFileName
init|=
name|targetFile
operator|.
name|getName
argument_list|()
decl_stmt|;
name|ParsedFileField
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
name|newFileName
argument_list|)
condition|)
block|{
name|newFileEntry
operator|=
operator|new
name|ParsedFileField
argument_list|(
name|fileEntry
operator|.
name|getDescription
argument_list|()
argument_list|,
name|newFileName
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
name|typedEntry
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

