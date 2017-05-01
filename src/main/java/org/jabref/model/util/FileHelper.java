begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.util
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
package|;
end_package

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
name|Locale
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
name|metadata
operator|.
name|FileDirectoryPreferences
import|;
end_import

begin_class
DECL|class|FileHelper
specifier|public
class|class
name|FileHelper
block|{
comment|/**      * Returns the extension of a file or Optional.empty() if the file does not have one (no . in name).      *      * @param file      * @return The extension, trimmed and in lowercase.      */
DECL|method|getFileExtension (Path file)
specifier|public
specifier|static
name|Optional
argument_list|<
name|String
argument_list|>
name|getFileExtension
parameter_list|(
name|Path
name|file
parameter_list|)
block|{
return|return
name|getFileExtension
argument_list|(
name|file
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns the extension of a file name or Optional.empty() if the file does not have one (no "." in name).      *      * @param fileName      * @return The extension (without leading dot), trimmed and in lowercase.      */
DECL|method|getFileExtension (String fileName)
specifier|public
specifier|static
name|Optional
argument_list|<
name|String
argument_list|>
name|getFileExtension
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|int
name|dotPosition
init|=
name|fileName
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|dotPosition
operator|>
literal|0
operator|)
operator|&&
operator|(
name|dotPosition
operator|<
operator|(
name|fileName
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
operator|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fileName
operator|.
name|substring
argument_list|(
name|dotPosition
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
comment|/**      * Converts a relative filename to an absolute one, if necessary. Returns an empty optional if the file does not      * exist.<br/>      *<p>      * Uses<ul>      *<li>the default directory associated with the extension of the file</li>      *<li>the standard file directory</li>      *<li>the directory of the BIB file</li>      *</ul>      *      * @param databaseContext The database this file belongs to.      * @param name     The filename, may also be a relative path to the file      */
DECL|method|expandFilename (final BibDatabaseContext databaseContext, String name, FileDirectoryPreferences fileDirectoryPreferences)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Path
argument_list|>
name|expandFilename
parameter_list|(
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|String
name|name
parameter_list|,
name|FileDirectoryPreferences
name|fileDirectoryPreferences
parameter_list|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|extension
init|=
name|getFileExtension
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|// Find the default directory for this field type, if any:
name|List
argument_list|<
name|String
argument_list|>
name|directories
init|=
name|databaseContext
operator|.
name|getFileDirectories
argument_list|(
name|extension
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|fileDirectoryPreferences
argument_list|)
decl_stmt|;
comment|// Include the standard "file" directory:
name|List
argument_list|<
name|String
argument_list|>
name|fileDir
init|=
name|databaseContext
operator|.
name|getFileDirectories
argument_list|(
name|fileDirectoryPreferences
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|searchDirectories
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|dir
range|:
name|directories
control|)
block|{
if|if
condition|(
operator|!
name|searchDirectories
operator|.
name|contains
argument_list|(
name|dir
argument_list|)
condition|)
block|{
name|searchDirectories
operator|.
name|add
argument_list|(
name|dir
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|aFileDir
range|:
name|fileDir
control|)
block|{
if|if
condition|(
operator|!
name|searchDirectories
operator|.
name|contains
argument_list|(
name|aFileDir
argument_list|)
condition|)
block|{
name|searchDirectories
operator|.
name|add
argument_list|(
name|aFileDir
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|expandFilename
argument_list|(
name|name
argument_list|,
name|searchDirectories
argument_list|)
return|;
block|}
comment|/**      * Converts a relative filename to an absolute one, if necessary. Returns      * null if the file does not exist.      *<p>      * Will look in each of the given dirs starting from the beginning and      * returning the first found file to match if any.      *      * @deprecated use {@link #expandFilenameAsPath(String, List)} instead      */
annotation|@
name|Deprecated
DECL|method|expandFilename (String name, List<String> directories)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Path
argument_list|>
name|expandFilename
parameter_list|(
name|String
name|name
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|directories
parameter_list|)
block|{
for|for
control|(
name|String
name|dir
range|:
name|directories
control|)
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|result
init|=
name|expandFilename
argument_list|(
name|name
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|dir
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|result
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|expandFilenameAsPath (String name, List<Path> directories)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Path
argument_list|>
name|expandFilenameAsPath
parameter_list|(
name|String
name|name
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|directories
parameter_list|)
block|{
for|for
control|(
name|Path
name|directory
range|:
name|directories
control|)
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|result
init|=
name|expandFilename
argument_list|(
name|name
argument_list|,
name|directory
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|result
return|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|/**      * Converts a relative filename to an absolute one, if necessary. Returns      * an empty optional if the file does not exist.      */
DECL|method|expandFilename (String filename, Path directory)
specifier|private
specifier|static
name|Optional
argument_list|<
name|Path
argument_list|>
name|expandFilename
parameter_list|(
name|String
name|filename
parameter_list|,
name|Path
name|directory
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|filename
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|directory
argument_list|)
expr_stmt|;
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|file
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|file
argument_list|)
return|;
block|}
name|Path
name|resolvedFile
init|=
name|directory
operator|.
name|resolve
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|resolvedFile
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|resolvedFile
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

