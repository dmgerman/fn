begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.database
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|Defaults
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
name|bibtexkeypattern
operator|.
name|GlobalBibtexKeyPattern
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
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|shared
operator|.
name|DBMSSynchronizer
import|;
end_import

begin_comment
comment|/**  * Represents everything related to a BIB file.  *<p>  * The entries are stored in BibDatabase, the other data in MetaData and the options relevant for this file in Defaults.  */
end_comment

begin_class
DECL|class|BibDatabaseContext
specifier|public
class|class
name|BibDatabaseContext
block|{
DECL|field|database
specifier|private
specifier|final
name|BibDatabase
name|database
decl_stmt|;
DECL|field|defaults
specifier|private
specifier|final
name|Defaults
name|defaults
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
comment|/** The file where this database was last saved to. */
DECL|field|file
specifier|private
name|File
name|file
decl_stmt|;
DECL|field|dbmsSynchronizer
specifier|private
name|DBMSSynchronizer
name|dbmsSynchronizer
decl_stmt|;
DECL|field|location
specifier|private
name|DatabaseLocation
name|location
decl_stmt|;
DECL|method|BibDatabaseContext ()
specifier|public
name|BibDatabaseContext
parameter_list|()
block|{
name|this
argument_list|(
operator|new
name|Defaults
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (Defaults defaults)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|Defaults
name|defaults
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|,
name|defaults
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (BibDatabase database)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
operator|new
name|Defaults
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (BibDatabase database, Defaults defaults)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|Defaults
name|defaults
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|defaults
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (BibDatabase database, MetaData metaData, Defaults defaults)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|Defaults
name|defaults
parameter_list|)
block|{
name|this
operator|.
name|defaults
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|defaults
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|this
operator|.
name|location
operator|=
name|DatabaseLocation
operator|.
name|LOCAL
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (BibDatabase database, MetaData metaData)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
operator|new
name|Defaults
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (BibDatabase database, MetaData metaData, File file, Defaults defaults, DatabaseLocation location)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|File
name|file
parameter_list|,
name|Defaults
name|defaults
parameter_list|,
name|DatabaseLocation
name|location
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|defaults
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|location
argument_list|)
expr_stmt|;
name|this
operator|.
name|setDatabaseFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
if|if
condition|(
name|location
operator|==
name|DatabaseLocation
operator|.
name|LOCAL
condition|)
block|{
name|convertToLocalDatabase
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|BibDatabaseContext (BibDatabase database, MetaData metaData, File file, Defaults defaults)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|File
name|file
parameter_list|,
name|Defaults
name|defaults
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|file
argument_list|,
name|defaults
argument_list|,
name|DatabaseLocation
operator|.
name|LOCAL
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (BibDatabase database, MetaData metaData, File file)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|File
name|file
parameter_list|)
block|{
name|this
argument_list|(
name|database
argument_list|,
name|metaData
argument_list|,
name|file
argument_list|,
operator|new
name|Defaults
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|BibDatabaseContext (Defaults defaults, DatabaseLocation location, Character keywordSeparator, GlobalBibtexKeyPattern globalCiteKeyPattern)
specifier|public
name|BibDatabaseContext
parameter_list|(
name|Defaults
name|defaults
parameter_list|,
name|DatabaseLocation
name|location
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|,
name|GlobalBibtexKeyPattern
name|globalCiteKeyPattern
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
name|defaults
argument_list|)
expr_stmt|;
if|if
condition|(
name|location
operator|==
name|DatabaseLocation
operator|.
name|SHARED
condition|)
block|{
name|convertToSharedDatabase
argument_list|(
name|keywordSeparator
argument_list|,
name|globalCiteKeyPattern
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getMode ()
specifier|public
name|BibDatabaseMode
name|getMode
parameter_list|()
block|{
name|Optional
argument_list|<
name|BibDatabaseMode
argument_list|>
name|mode
init|=
name|metaData
operator|.
name|getMode
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|mode
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|BibDatabaseMode
name|inferredMode
init|=
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|database
argument_list|)
decl_stmt|;
name|BibDatabaseMode
name|newMode
init|=
name|BibDatabaseMode
operator|.
name|BIBTEX
decl_stmt|;
if|if
condition|(
operator|(
name|defaults
operator|.
name|mode
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
operator|)
operator|||
operator|(
name|inferredMode
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
operator|)
condition|)
block|{
name|newMode
operator|=
name|BibDatabaseMode
operator|.
name|BIBLATEX
expr_stmt|;
block|}
name|this
operator|.
name|setMode
argument_list|(
name|newMode
argument_list|)
expr_stmt|;
return|return
name|newMode
return|;
block|}
return|return
name|mode
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|setMode (BibDatabaseMode bibDatabaseMode)
specifier|public
name|void
name|setMode
parameter_list|(
name|BibDatabaseMode
name|bibDatabaseMode
parameter_list|)
block|{
name|metaData
operator|.
name|setMode
argument_list|(
name|bibDatabaseMode
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get the file where this database was last saved to or loaded from, if any.      *      * @return Optional of the relevant File, or Optional.empty() if none is defined.      * @deprecated use {@link #getDatabasePath()} instead      */
annotation|@
name|Deprecated
DECL|method|getDatabaseFile ()
specifier|public
name|Optional
argument_list|<
name|File
argument_list|>
name|getDatabaseFile
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|file
argument_list|)
return|;
block|}
DECL|method|setDatabaseFile (File file)
specifier|public
name|void
name|setDatabaseFile
parameter_list|(
name|File
name|file
parameter_list|)
block|{
name|this
operator|.
name|file
operator|=
name|file
expr_stmt|;
block|}
DECL|method|getDatabasePath ()
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|getDatabasePath
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|file
argument_list|)
operator|.
name|map
argument_list|(
name|File
operator|::
name|toPath
argument_list|)
return|;
block|}
DECL|method|clearDatabaseFile ()
specifier|public
name|void
name|clearDatabaseFile
parameter_list|()
block|{
name|this
operator|.
name|file
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|getDatabase ()
specifier|public
name|BibDatabase
name|getDatabase
parameter_list|()
block|{
return|return
name|database
return|;
block|}
DECL|method|getMetaData ()
specifier|public
name|MetaData
name|getMetaData
parameter_list|()
block|{
return|return
name|metaData
return|;
block|}
DECL|method|setMetaData (MetaData metaData)
specifier|public
name|void
name|setMetaData
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|this
operator|.
name|metaData
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
DECL|method|isBiblatexMode ()
specifier|public
name|boolean
name|isBiblatexMode
parameter_list|()
block|{
return|return
name|getMode
argument_list|()
operator|==
name|BibDatabaseMode
operator|.
name|BIBLATEX
return|;
block|}
DECL|method|getFileDirectories (FileDirectoryPreferences preferences)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getFileDirectories
parameter_list|(
name|FileDirectoryPreferences
name|preferences
parameter_list|)
block|{
return|return
name|getFileDirectories
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|,
name|preferences
argument_list|)
return|;
block|}
comment|/**      * Returns the first existing file directory from  {@link #getFileDirectories(FileDirectoryPreferences)}      * @param preferences The FileDirectoryPreferences      * @return Optional of Path      */
DECL|method|getFirstExistingFileDir (FileDirectoryPreferences preferences)
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|getFirstExistingFileDir
parameter_list|(
name|FileDirectoryPreferences
name|preferences
parameter_list|)
block|{
return|return
name|getFileDirectories
argument_list|(
name|preferences
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|s
lambda|->
operator|!
name|s
operator|.
name|isEmpty
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|p
lambda|->
name|Paths
operator|.
name|get
argument_list|(
name|p
argument_list|)
argument_list|)
operator|.
name|filter
argument_list|(
name|Files
operator|::
name|exists
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
comment|//Filter for empty string, as this would be expanded to the jar-directory with Paths.get()
block|}
comment|/**     * Look up the directories set up for the given field type for this database.     * If no directory is set up, return that defined in global preferences.     * There can be up to three directory definitions for these files:     * the database's metadata can specify a general directory and/or a user-specific directory     * or the preferences can specify one.     *<p>     * The settings are prioritized in the following order and the first defined setting is used:     * 1. metadata user-specific directory     * 2. metadata general directory     * 3. preferences directory     * 4. BIB file directory     *     * @param fieldName The field type      * @param preferences The fileDirectory preferences      * @return The default directory for this field type.     */
DECL|method|getFileDirectories (String fieldName, FileDirectoryPreferences preferences)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getFileDirectories
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|FileDirectoryPreferences
name|preferences
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fileDirs
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// 1. metadata user-specific directory
name|Optional
argument_list|<
name|String
argument_list|>
name|userFileDirectory
init|=
name|metaData
operator|.
name|getUserFileDirectory
argument_list|(
name|preferences
operator|.
name|getUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|userFileDirectory
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|fileDirs
operator|.
name|add
argument_list|(
name|getFileDirectoryPath
argument_list|(
name|userFileDirectory
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// 2. metadata general directory
name|Optional
argument_list|<
name|String
argument_list|>
name|metaDataDirectory
init|=
name|metaData
operator|.
name|getDefaultFileDirectory
argument_list|()
decl_stmt|;
if|if
condition|(
name|metaDataDirectory
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|fileDirs
operator|.
name|add
argument_list|(
name|getFileDirectoryPath
argument_list|(
name|metaDataDirectory
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// 3. preferences directory
name|preferences
operator|.
name|getFileDirectory
argument_list|(
name|fieldName
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|path
lambda|->
name|fileDirs
operator|.
name|add
argument_list|(
name|path
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// 4. BIB file directory
name|getDatabasePath
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|dbPath
lambda|->
block|{
name|String
name|parentDir
init|=
name|dbPath
operator|.
name|getParent
argument_list|()
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
comment|// Check if we should add it as primary file dir (first in the list) or not:
if|if
condition|(
name|preferences
operator|.
name|isBibLocationAsPrimary
argument_list|()
condition|)
block|{
name|fileDirs
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|parentDir
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fileDirs
operator|.
name|add
argument_list|(
name|parentDir
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|fileDirs
return|;
block|}
DECL|method|getFileDirectoryPath (String directoryName)
specifier|private
name|String
name|getFileDirectoryPath
parameter_list|(
name|String
name|directoryName
parameter_list|)
block|{
name|String
name|dir
init|=
name|directoryName
decl_stmt|;
comment|// If this directory is relative, we try to interpret it as relative to
comment|// the file path of this BIB file:
name|Optional
argument_list|<
name|File
argument_list|>
name|databaseFile
init|=
name|getDatabaseFile
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|new
name|File
argument_list|(
name|dir
argument_list|)
operator|.
name|isAbsolute
argument_list|()
operator|&&
name|databaseFile
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|String
name|relDir
decl_stmt|;
if|if
condition|(
literal|"."
operator|.
name|equals
argument_list|(
name|dir
argument_list|)
condition|)
block|{
comment|// if dir is only "current" directory, just use its parent (== real current directory) as path
name|relDir
operator|=
name|databaseFile
operator|.
name|get
argument_list|()
operator|.
name|getParent
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|relDir
operator|=
name|databaseFile
operator|.
name|get
argument_list|()
operator|.
name|getParent
argument_list|()
operator|+
name|File
operator|.
name|separator
operator|+
name|dir
expr_stmt|;
block|}
comment|// If this directory actually exists, it is very likely that the
comment|// user wants us to use it:
if|if
condition|(
operator|new
name|File
argument_list|(
name|relDir
argument_list|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|dir
operator|=
name|relDir
expr_stmt|;
block|}
block|}
return|return
name|dir
return|;
block|}
DECL|method|getDBMSSynchronizer ()
specifier|public
name|DBMSSynchronizer
name|getDBMSSynchronizer
parameter_list|()
block|{
return|return
name|this
operator|.
name|dbmsSynchronizer
return|;
block|}
DECL|method|clearDBMSSynchronizer ()
specifier|public
name|void
name|clearDBMSSynchronizer
parameter_list|()
block|{
name|this
operator|.
name|dbmsSynchronizer
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|getLocation ()
specifier|public
name|DatabaseLocation
name|getLocation
parameter_list|()
block|{
return|return
name|this
operator|.
name|location
return|;
block|}
DECL|method|convertToSharedDatabase (Character keywordSeparator, GlobalBibtexKeyPattern globalCiteKeyPattern)
specifier|public
name|void
name|convertToSharedDatabase
parameter_list|(
name|Character
name|keywordSeparator
parameter_list|,
name|GlobalBibtexKeyPattern
name|globalCiteKeyPattern
parameter_list|)
block|{
name|this
operator|.
name|dbmsSynchronizer
operator|=
operator|new
name|DBMSSynchronizer
argument_list|(
name|this
argument_list|,
name|keywordSeparator
argument_list|,
name|globalCiteKeyPattern
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|.
name|registerListener
argument_list|(
name|dbmsSynchronizer
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|.
name|registerListener
argument_list|(
name|dbmsSynchronizer
argument_list|)
expr_stmt|;
name|this
operator|.
name|location
operator|=
name|DatabaseLocation
operator|.
name|SHARED
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"BibDatabaseContext{"
operator|+
literal|"file="
operator|+
name|file
operator|+
literal|", location="
operator|+
name|location
operator|+
literal|'}'
return|;
block|}
DECL|method|convertToLocalDatabase ()
specifier|public
name|void
name|convertToLocalDatabase
parameter_list|()
block|{
if|if
condition|(
name|Objects
operator|.
name|nonNull
argument_list|(
name|dbmsSynchronizer
argument_list|)
operator|&&
operator|(
name|location
operator|==
name|DatabaseLocation
operator|.
name|SHARED
operator|)
condition|)
block|{
name|this
operator|.
name|database
operator|.
name|unregisterListener
argument_list|(
name|dbmsSynchronizer
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|.
name|unregisterListener
argument_list|(
name|dbmsSynchronizer
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|location
operator|=
name|DatabaseLocation
operator|.
name|LOCAL
expr_stmt|;
block|}
DECL|method|getEntries ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getEntries
parameter_list|()
block|{
return|return
name|database
operator|.
name|getEntries
argument_list|()
return|;
block|}
block|}
end_class

end_unit

