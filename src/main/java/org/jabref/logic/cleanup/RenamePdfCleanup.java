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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
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
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|strings
operator|.
name|StringUtil
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

begin_class
DECL|class|RenamePdfCleanup
specifier|public
class|class
name|RenamePdfCleanup
implements|implements
name|CleanupJob
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
name|RenamePdfCleanup
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
DECL|field|onlyRelativePaths
specifier|private
specifier|final
name|boolean
name|onlyRelativePaths
decl_stmt|;
DECL|field|fileNamePattern
specifier|private
specifier|final
name|String
name|fileNamePattern
decl_stmt|;
DECL|field|layoutPreferences
specifier|private
specifier|final
name|LayoutFormatterPreferences
name|layoutPreferences
decl_stmt|;
DECL|field|fileDirectoryPreferences
specifier|private
specifier|final
name|FileDirectoryPreferences
name|fileDirectoryPreferences
decl_stmt|;
DECL|field|unsuccessfulRenames
specifier|private
name|int
name|unsuccessfulRenames
decl_stmt|;
DECL|field|singleFieldCleanup
specifier|private
name|LinkedFile
name|singleFieldCleanup
decl_stmt|;
DECL|method|RenamePdfCleanup (boolean onlyRelativePaths, BibDatabaseContext databaseContext, String fileNamePattern, LayoutFormatterPreferences layoutPreferences, FileDirectoryPreferences fileDirectoryPreferences)
specifier|public
name|RenamePdfCleanup
parameter_list|(
name|boolean
name|onlyRelativePaths
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|String
name|fileNamePattern
parameter_list|,
name|LayoutFormatterPreferences
name|layoutPreferences
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
name|onlyRelativePaths
operator|=
name|onlyRelativePaths
expr_stmt|;
name|this
operator|.
name|fileNamePattern
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fileNamePattern
argument_list|)
expr_stmt|;
name|this
operator|.
name|layoutPreferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|layoutPreferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|fileDirectoryPreferences
operator|=
name|fileDirectoryPreferences
expr_stmt|;
block|}
DECL|method|RenamePdfCleanup (boolean onlyRelativePaths, BibDatabaseContext databaseContext, String fileNamePattern, LayoutFormatterPreferences layoutPreferences, FileDirectoryPreferences fileDirectoryPreferences, LinkedFile singleField)
specifier|public
name|RenamePdfCleanup
parameter_list|(
name|boolean
name|onlyRelativePaths
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|String
name|fileNamePattern
parameter_list|,
name|LayoutFormatterPreferences
name|layoutPreferences
parameter_list|,
name|FileDirectoryPreferences
name|fileDirectoryPreferences
parameter_list|,
name|LinkedFile
name|singleField
parameter_list|)
block|{
name|this
argument_list|(
name|onlyRelativePaths
argument_list|,
name|databaseContext
argument_list|,
name|fileNamePattern
argument_list|,
name|layoutPreferences
argument_list|,
name|fileDirectoryPreferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|singleFieldCleanup
operator|=
name|singleField
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
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|newFileList
decl_stmt|;
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|oldFileList
decl_stmt|;
if|if
condition|(
name|singleFieldCleanup
operator|!=
literal|null
condition|)
block|{
name|oldFileList
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|singleFieldCleanup
argument_list|)
expr_stmt|;
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
name|x
lambda|->
operator|!
name|x
operator|.
name|equals
argument_list|(
name|singleFieldCleanup
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
name|oldFileList
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
name|oldLinkedFile
range|:
name|oldFileList
control|)
block|{
name|String
name|realOldFilename
init|=
name|oldLinkedFile
operator|.
name|getLink
argument_list|()
decl_stmt|;
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|realOldFilename
argument_list|)
condition|)
block|{
continue|continue;
comment|//Skip empty filenames
block|}
if|if
condition|(
name|onlyRelativePaths
operator|&&
name|Paths
operator|.
name|get
argument_list|(
name|realOldFilename
argument_list|)
operator|.
name|isAbsolute
argument_list|()
condition|)
block|{
name|newFileList
operator|.
name|add
argument_list|(
name|oldLinkedFile
argument_list|)
expr_stmt|;
continue|continue;
block|}
comment|//old path and old filename
name|Optional
argument_list|<
name|Path
argument_list|>
name|expandedOldFile
init|=
name|oldLinkedFile
operator|.
name|findIn
argument_list|(
name|databaseContext
argument_list|,
name|fileDirectoryPreferences
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
operator|!
name|expandedOldFile
operator|.
name|isPresent
argument_list|()
operator|)
operator|||
operator|(
name|expandedOldFile
operator|.
name|get
argument_list|()
operator|.
name|getParent
argument_list|()
operator|==
literal|null
operator|)
condition|)
block|{
comment|// something went wrong. Just skip this entry
name|newFileList
operator|.
name|add
argument_list|(
name|oldLinkedFile
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|String
name|targetFileName
init|=
name|getTargetFileName
argument_list|(
name|oldLinkedFile
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|Path
name|newPath
init|=
name|expandedOldFile
operator|.
name|get
argument_list|()
operator|.
name|getParent
argument_list|()
operator|.
name|resolve
argument_list|(
name|targetFileName
argument_list|)
decl_stmt|;
name|String
name|expandedOldFilePath
init|=
name|expandedOldFile
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|boolean
name|pathsDifferOnlyByCase
init|=
name|newPath
operator|.
name|toString
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|expandedOldFilePath
argument_list|)
operator|&&
operator|!
name|newPath
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|expandedOldFilePath
argument_list|)
decl_stmt|;
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|newPath
argument_list|)
operator|&&
operator|!
name|pathsDifferOnlyByCase
condition|)
block|{
comment|// we do not overwrite files
comment|// Since File.exists is sometimes not case-sensitive, the check pathsDifferOnlyByCase ensures that we
comment|// nonetheless rename files to a new name which just differs by case.
comment|// TODO: we could check here if the newPath file is linked with the current entry. And if not, we could add a link
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"There already exists a file with that name "
operator|+
name|newPath
operator|.
name|getFileName
argument_list|()
operator|+
literal|" so I won't rename it"
argument_list|)
expr_stmt|;
name|newFileList
operator|.
name|add
argument_list|(
name|oldLinkedFile
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
name|newPath
argument_list|)
condition|)
block|{
name|Files
operator|.
name|createDirectories
argument_list|(
name|newPath
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
literal|"Could not create necessary target directories for renaming"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|boolean
name|renameSuccessful
init|=
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|expandedOldFilePath
argument_list|)
argument_list|,
name|newPath
argument_list|,
literal|true
argument_list|)
decl_stmt|;
if|if
condition|(
name|renameSuccessful
condition|)
block|{
name|changed
operator|=
literal|true
expr_stmt|;
comment|// Change the path for this entry
name|String
name|description
init|=
name|oldLinkedFile
operator|.
name|getDescription
argument_list|()
decl_stmt|;
name|String
name|type
init|=
name|oldLinkedFile
operator|.
name|getFileType
argument_list|()
decl_stmt|;
comment|// We use the file directory (if none is set - then bib file) to create relative file links.
comment|// The .get() is legal without check because the method will always return a value.
name|Path
name|settingsDir
init|=
name|databaseContext
operator|.
name|getFirstExistingFileDir
argument_list|(
name|fileDirectoryPreferences
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|newFileList
operator|.
name|add
argument_list|(
operator|new
name|LinkedFile
argument_list|(
name|description
argument_list|,
name|settingsDir
operator|.
name|relativize
argument_list|(
name|newPath
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|,
name|type
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|unsuccessfulRenames
operator|++
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
comment|//we put an undo of the field content here
comment|//the file is not being renamed back, which leads to inconsistencies
comment|//if we put a null undo object here, the change by "doMakePathsRelative" would overwrite the field value nevertheless.
return|return
name|change
operator|.
name|map
argument_list|(
name|Collections
operator|::
name|singletonList
argument_list|)
operator|.
name|orElseGet
argument_list|(
name|Collections
operator|::
name|emptyList
argument_list|)
return|;
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
DECL|method|getTargetFileName (LinkedFile flEntry, BibEntry entry)
specifier|public
name|String
name|getTargetFileName
parameter_list|(
name|LinkedFile
name|flEntry
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|String
name|realOldFilename
init|=
name|flEntry
operator|.
name|getLink
argument_list|()
decl_stmt|;
name|String
name|targetFileName
init|=
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
name|databaseContext
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|,
name|layoutPreferences
argument_list|)
operator|.
name|trim
argument_list|()
operator|+
literal|'.'
operator|+
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|realOldFilename
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"pdf"
argument_list|)
decl_stmt|;
comment|// Only create valid file names
return|return
name|FileUtil
operator|.
name|getValidFileName
argument_list|(
name|targetFileName
argument_list|)
return|;
block|}
DECL|method|getUnsuccessfulRenames ()
specifier|public
name|int
name|getUnsuccessfulRenames
parameter_list|()
block|{
return|return
name|unsuccessfulRenames
return|;
block|}
comment|/**Check to see if a file already exists in the target directory.  Search is not case sensitive.     *     * @param flEntry     * @param entry     * @return First identified path that matches an existing file.  This name can be used in subsequent calls to override the existing file.     */
DECL|method|fileAlreadyExists (LinkedFile flEntry, BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|fileAlreadyExists
parameter_list|(
name|LinkedFile
name|flEntry
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|String
name|targetFileName
init|=
name|getTargetFileName
argument_list|(
name|flEntry
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|Path
name|targetFilePath
init|=
name|flEntry
operator|.
name|findIn
argument_list|(
name|databaseContext
argument_list|,
name|fileDirectoryPreferences
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getParent
argument_list|()
operator|.
name|resolve
argument_list|(
name|targetFileName
argument_list|)
decl_stmt|;
name|Path
name|oldFilePath
init|=
name|flEntry
operator|.
name|findIn
argument_list|(
name|databaseContext
argument_list|,
name|fileDirectoryPreferences
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
comment|//Check if file already exists in directory with different case.
comment|//This is necessary because other entries may have such a file.
name|Optional
argument_list|<
name|Path
argument_list|>
name|matchedByDiffCase
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|stream
init|=
name|Files
operator|.
name|list
argument_list|(
name|oldFilePath
operator|.
name|getParent
argument_list|()
argument_list|)
init|)
block|{
name|matchedByDiffCase
operator|=
name|stream
operator|.
name|filter
argument_list|(
name|name
lambda|->
name|name
operator|.
name|toString
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|targetFilePath
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
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
literal|"Could not get the list of files in target directory"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
return|return
name|matchedByDiffCase
return|;
block|}
block|}
end_class

end_unit

