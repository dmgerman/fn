begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util.io
package|package
name|org
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|UncheckedIOException
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
name|CopyOption
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
name|FileSystems
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
name|StandardCopyOption
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
name|StandardOpenOption
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
name|java
operator|.
name|util
operator|.
name|Stack
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|bibtexkeypattern
operator|.
name|BracketedPattern
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
name|BibDatabase
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
name|util
operator|.
name|OptionalUtil
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
DECL|class|FileUtil
specifier|public
class|class
name|FileUtil
block|{
DECL|field|IS_POSIX_COMPILANT
specifier|public
specifier|static
specifier|final
name|boolean
name|IS_POSIX_COMPILANT
init|=
name|FileSystems
operator|.
name|getDefault
argument_list|()
operator|.
name|supportedFileAttributeViews
argument_list|()
operator|.
name|contains
argument_list|(
literal|"posix"
argument_list|)
decl_stmt|;
DECL|field|MAXIMUM_FILE_NAME_LENGTH
specifier|public
specifier|static
specifier|final
name|int
name|MAXIMUM_FILE_NAME_LENGTH
init|=
literal|255
decl_stmt|;
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
name|FileUtil
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|FileUtil ()
specifier|private
name|FileUtil
parameter_list|()
block|{     }
comment|/**      * Returns the extension of a file name or Optional.empty() if the file does not have one (no "." in name).      *      * @return the extension (without leading dot), trimmed and in lowercase.      */
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
comment|/**      * Returns the extension of a file or Optional.empty() if the file does not have one (no . in name).      *      * @return the extension (without leading dot), trimmed and in lowercase.      */
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
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns the name part of a file name (i.e., everything in front of last ".").      */
DECL|method|getBaseName (String fileNameWithExtension)
specifier|public
specifier|static
name|String
name|getBaseName
parameter_list|(
name|String
name|fileNameWithExtension
parameter_list|)
block|{
return|return
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|io
operator|.
name|Files
operator|.
name|getNameWithoutExtension
argument_list|(
name|fileNameWithExtension
argument_list|)
return|;
block|}
comment|/**      * Returns a valid filename for most operating systems.      *      * Currently, only the length is restricted to 255 chars, see MAXIMUM_FILE_NAME_LENGTH.      */
DECL|method|getValidFileName (String fileName)
specifier|public
specifier|static
name|String
name|getValidFileName
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|String
name|nameWithoutExtension
init|=
name|getBaseName
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
if|if
condition|(
name|nameWithoutExtension
operator|.
name|length
argument_list|()
operator|>
name|MAXIMUM_FILE_NAME_LENGTH
condition|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|extension
init|=
name|getFileExtension
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
name|String
name|shortName
init|=
name|nameWithoutExtension
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|MAXIMUM_FILE_NAME_LENGTH
argument_list|)
decl_stmt|;
name|LOGGER
operator|.
name|info
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Truncated the too long filename '%s' (%d characters) to '%s'."
argument_list|,
name|fileName
argument_list|,
name|fileName
operator|.
name|length
argument_list|()
argument_list|,
name|shortName
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|extension
operator|.
name|map
argument_list|(
name|s
lambda|->
name|shortName
operator|+
literal|"."
operator|+
name|s
argument_list|)
operator|.
name|orElse
argument_list|(
name|shortName
argument_list|)
return|;
block|}
return|return
name|fileName
return|;
block|}
comment|/**      * Adds an extension to the given file name. The original extension is not replaced. That means, "demo.bib", ".sav"      * gets "demo.bib.sav" and not "demo.sav"      *      * @param path      the path to add the extension to      * @param extension the extension to add      * @return the with the modified file name      */
DECL|method|addExtension (Path path, String extension)
specifier|public
specifier|static
name|Path
name|addExtension
parameter_list|(
name|Path
name|path
parameter_list|,
name|String
name|extension
parameter_list|)
block|{
return|return
name|path
operator|.
name|resolveSibling
argument_list|(
name|path
operator|.
name|getFileName
argument_list|()
operator|+
name|extension
argument_list|)
return|;
block|}
comment|/**      * Creates the minimal unique path substring for each file among multiple file paths.      *      * @param paths the file paths      * @return the minimal unique path substring for each file path      */
DECL|method|uniquePathSubstrings (List<String> paths)
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|uniquePathSubstrings
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|paths
parameter_list|)
block|{
name|List
argument_list|<
name|Stack
argument_list|<
name|String
argument_list|>
argument_list|>
name|stackList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|paths
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
comment|// prepare data structures
for|for
control|(
name|String
name|path
range|:
name|paths
control|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|directories
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|path
operator|.
name|split
argument_list|(
name|Pattern
operator|.
name|quote
argument_list|(
name|File
operator|.
name|separator
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|Stack
argument_list|<
name|String
argument_list|>
name|stack
init|=
operator|new
name|Stack
argument_list|<>
argument_list|()
decl_stmt|;
name|stack
operator|.
name|addAll
argument_list|(
name|directories
argument_list|)
expr_stmt|;
name|stackList
operator|.
name|add
argument_list|(
name|stack
argument_list|)
expr_stmt|;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|pathSubstrings
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|Collections
operator|.
name|nCopies
argument_list|(
name|paths
operator|.
name|size
argument_list|()
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
comment|// compute shortest folder substrings
while|while
condition|(
operator|!
name|stackList
operator|.
name|stream
argument_list|()
operator|.
name|allMatch
argument_list|(
name|Vector
operator|::
name|isEmpty
argument_list|)
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|stackList
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|tempString
init|=
name|pathSubstrings
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|tempString
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|stackList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|pathSubstrings
operator|.
name|set
argument_list|(
name|i
argument_list|,
name|stackList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|pop
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|!
name|stackList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|pathSubstrings
operator|.
name|set
argument_list|(
name|i
argument_list|,
name|stackList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|pop
argument_list|()
operator|+
name|File
operator|.
name|separator
operator|+
name|tempString
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|stackList
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
name|tempString
init|=
name|pathSubstrings
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|Collections
operator|.
name|frequency
argument_list|(
name|pathSubstrings
argument_list|,
name|tempString
argument_list|)
operator|==
literal|1
condition|)
block|{
name|stackList
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
name|pathSubstrings
return|;
block|}
comment|/**      * Copies a file.      *      * @param pathToSourceFile      Path Source file      * @param pathToDestinationFile Path Destination file      * @param replaceExisting       boolean Determines whether the copy goes on even if the file exists.      * @return boolean Whether the copy succeeded, or was stopped due to the file already existing.      */
DECL|method|copyFile (Path pathToSourceFile, Path pathToDestinationFile, boolean replaceExisting)
specifier|public
specifier|static
name|boolean
name|copyFile
parameter_list|(
name|Path
name|pathToSourceFile
parameter_list|,
name|Path
name|pathToDestinationFile
parameter_list|,
name|boolean
name|replaceExisting
parameter_list|)
block|{
comment|// Check if the file already exists.
if|if
condition|(
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|pathToSourceFile
argument_list|)
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Path to the source file doesn't exist."
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|pathToDestinationFile
argument_list|)
operator|&&
operator|!
name|replaceExisting
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Path to the destination file exists but the file shouldn't be replaced."
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
try|try
block|{
comment|// Preserve Hard Links with OpenOption defaults included for clarity
name|Files
operator|.
name|write
argument_list|(
name|pathToDestinationFile
argument_list|,
name|Files
operator|.
name|readAllBytes
argument_list|(
name|pathToSourceFile
argument_list|)
argument_list|,
name|StandardOpenOption
operator|.
name|CREATE
argument_list|,
name|StandardOpenOption
operator|.
name|WRITE
argument_list|,
name|StandardOpenOption
operator|.
name|TRUNCATE_EXISTING
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
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
literal|"Copying Files failed."
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Renames a given file      *      * @param fromFile The source filename to rename      * @param toFile   The target fileName      * @return True if the rename was successful, false if an exception occurred      */
DECL|method|renameFile (Path fromFile, Path toFile)
specifier|public
specifier|static
name|boolean
name|renameFile
parameter_list|(
name|Path
name|fromFile
parameter_list|,
name|Path
name|toFile
parameter_list|)
block|{
return|return
name|renameFile
argument_list|(
name|fromFile
argument_list|,
name|toFile
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|/**      * Renames a given file      *      * @param fromFile        The source filename to rename      * @param toFile          The target fileName      * @param replaceExisting Wether to replace existing files or not      * @return True if the rename was successful, false if an exception occurred      * @deprecated Use {@link Files#move(Path, Path, CopyOption...)} instead and handle exception properly      */
annotation|@
name|Deprecated
DECL|method|renameFile (Path fromFile, Path toFile, boolean replaceExisting)
specifier|public
specifier|static
name|boolean
name|renameFile
parameter_list|(
name|Path
name|fromFile
parameter_list|,
name|Path
name|toFile
parameter_list|,
name|boolean
name|replaceExisting
parameter_list|)
block|{
try|try
block|{
return|return
name|renameFileWithException
argument_list|(
name|fromFile
argument_list|,
name|toFile
argument_list|,
name|replaceExisting
argument_list|)
return|;
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
literal|"Renaming Files failed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
comment|/**      * @deprecated Directly use {@link Files#move(Path, Path, CopyOption...)}      */
annotation|@
name|Deprecated
DECL|method|renameFileWithException (Path fromFile, Path toFile, boolean replaceExisting)
specifier|public
specifier|static
name|boolean
name|renameFileWithException
parameter_list|(
name|Path
name|fromFile
parameter_list|,
name|Path
name|toFile
parameter_list|,
name|boolean
name|replaceExisting
parameter_list|)
throws|throws
name|IOException
block|{
if|if
condition|(
name|replaceExisting
condition|)
block|{
return|return
name|Files
operator|.
name|move
argument_list|(
name|fromFile
argument_list|,
name|fromFile
operator|.
name|resolveSibling
argument_list|(
name|toFile
argument_list|)
argument_list|,
name|StandardCopyOption
operator|.
name|REPLACE_EXISTING
argument_list|)
operator|!=
literal|null
return|;
block|}
else|else
block|{
return|return
name|Files
operator|.
name|move
argument_list|(
name|fromFile
argument_list|,
name|fromFile
operator|.
name|resolveSibling
argument_list|(
name|toFile
argument_list|)
argument_list|)
operator|!=
literal|null
return|;
block|}
block|}
comment|/**      * Converts an absolute file to a relative one, if possible. Returns the parameter file itself if no shortening is      * possible.      *<p>      * This method works correctly only if directories are sorted decent in their length i.e. /home/user/literature/important before /home/user/literature.      *      * @param file the file to be shortened      * @param directories directories to check      */
DECL|method|relativize (Path file, List<Path> directories)
specifier|public
specifier|static
name|Path
name|relativize
parameter_list|(
name|Path
name|file
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|directories
parameter_list|)
block|{
if|if
condition|(
operator|!
name|file
operator|.
name|isAbsolute
argument_list|()
condition|)
block|{
return|return
name|file
return|;
block|}
for|for
control|(
name|Path
name|directory
range|:
name|directories
control|)
block|{
if|if
condition|(
name|file
operator|.
name|startsWith
argument_list|(
name|directory
argument_list|)
condition|)
block|{
return|return
name|directory
operator|.
name|relativize
argument_list|(
name|file
argument_list|)
return|;
block|}
block|}
return|return
name|file
return|;
block|}
comment|/**      * Returns the list of linked files. The files have the absolute filename      *      * @param bes      list of BibTeX entries      * @param fileDirs list of directories to try for expansion      * @return list of files. May be empty      */
DECL|method|getListOfLinkedFiles (List<BibEntry> bes, List<Path> fileDirs)
specifier|public
specifier|static
name|List
argument_list|<
name|Path
argument_list|>
name|getListOfLinkedFiles
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bes
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|fileDirs
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bes
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fileDirs
argument_list|)
expr_stmt|;
return|return
name|bes
operator|.
name|stream
argument_list|()
operator|.
name|flatMap
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|getFiles
argument_list|()
operator|.
name|stream
argument_list|()
argument_list|)
operator|.
name|flatMap
argument_list|(
name|file
lambda|->
name|OptionalUtil
operator|.
name|toStream
argument_list|(
name|file
operator|.
name|findIn
argument_list|(
name|fileDirs
argument_list|)
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
return|;
block|}
comment|/**      * Determines filename provided by an entry in a database      *      * @param database        the database, where the entry is located      * @param entry           the entry to which the file should be linked to      * @param fileNamePattern the filename pattern      * @return a suggested fileName      */
DECL|method|createFileNameFromPattern (BibDatabase database, BibEntry entry, String fileNamePattern)
specifier|public
specifier|static
name|String
name|createFileNameFromPattern
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|String
name|fileNamePattern
parameter_list|)
block|{
name|String
name|targetName
init|=
name|BracketedPattern
operator|.
name|expandBrackets
argument_list|(
name|fileNamePattern
argument_list|,
literal|';'
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
name|targetName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|targetName
operator|=
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|"default"
argument_list|)
expr_stmt|;
block|}
comment|//Removes illegal characters from filename
name|targetName
operator|=
name|FileNameCleaner
operator|.
name|cleanFileName
argument_list|(
name|targetName
argument_list|)
expr_stmt|;
return|return
name|targetName
return|;
block|}
comment|/**      * Determines filename provided by an entry in a database      *      * @param database        the database, where the entry is located      * @param entry           the entry to which the file should be linked to      * @param fileNamePattern the filename pattern      * @return a suggested fileName      */
DECL|method|createDirNameFromPattern (BibDatabase database, BibEntry entry, String fileNamePattern)
specifier|public
specifier|static
name|String
name|createDirNameFromPattern
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|String
name|fileNamePattern
parameter_list|)
block|{
name|String
name|targetName
init|=
name|BracketedPattern
operator|.
name|expandBrackets
argument_list|(
name|fileNamePattern
argument_list|,
literal|';'
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
decl_stmt|;
if|if
condition|(
name|targetName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|targetName
operator|=
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
literal|"default"
argument_list|)
expr_stmt|;
block|}
comment|//Removes illegal characters from filename
name|targetName
operator|=
name|FileNameCleaner
operator|.
name|cleanDirectoryName
argument_list|(
name|targetName
argument_list|)
expr_stmt|;
return|return
name|targetName
return|;
block|}
comment|/**      * Finds a file inside a directory structure. Will also look for the file inside nested directories.      *      * @param filename      the name of the file that should be found      * @param rootDirectory the rootDirectory that will be searched      * @return the path to the first file that matches the defined conditions      */
DECL|method|find (String filename, Path rootDirectory)
specifier|public
specifier|static
name|Optional
argument_list|<
name|Path
argument_list|>
name|find
parameter_list|(
name|String
name|filename
parameter_list|,
name|Path
name|rootDirectory
parameter_list|)
block|{
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|pathStream
init|=
name|Files
operator|.
name|walk
argument_list|(
name|rootDirectory
argument_list|)
init|)
block|{
return|return
name|pathStream
operator|.
name|filter
argument_list|(
name|Files
operator|::
name|isRegularFile
argument_list|)
operator|.
name|filter
argument_list|(
name|f
lambda|->
name|f
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|equals
argument_list|(
name|filename
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|UncheckedIOException
decl||
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error trying to locate the file "
operator|+
name|filename
operator|+
literal|" inside the directory "
operator|+
name|rootDirectory
argument_list|)
expr_stmt|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|/**      * Finds a file inside a list of directory structures. Will also look for the file inside nested directories.      *      * @param filename    the name of the file that should be found      * @param directories the directories that will be searched      * @return a list including all found paths to files that match the defined conditions      */
DECL|method|find (String filename, List<Path> directories)
specifier|public
specifier|static
name|List
argument_list|<
name|Path
argument_list|>
name|find
parameter_list|(
name|String
name|filename
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|directories
parameter_list|)
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Path
name|dir
range|:
name|directories
control|)
block|{
name|FileUtil
operator|.
name|find
argument_list|(
name|filename
argument_list|,
name|dir
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|files
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
return|return
name|files
return|;
block|}
comment|/**      * Creates a string representation of the given path that should work on all systems.      * This method should be used when a path needs to be stored in the bib file or preferences.      */
DECL|method|toPortableString (Path path)
specifier|public
specifier|static
name|String
name|toPortableString
parameter_list|(
name|Path
name|path
parameter_list|)
block|{
return|return
name|path
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|'\\'
argument_list|,
literal|'/'
argument_list|)
return|;
block|}
comment|/**      * Test if the file is a bib file by simply checking the extension to be ".bib"      * @param file The file to check      * @return True if file extension is ".bib", false otherwise      */
DECL|method|isBibFile (Path file)
specifier|public
specifier|static
name|boolean
name|isBibFile
parameter_list|(
name|Path
name|file
parameter_list|)
block|{
return|return
name|getFileExtension
argument_list|(
name|file
argument_list|)
operator|.
name|filter
argument_list|(
name|type
lambda|->
literal|"bib"
operator|.
name|equals
argument_list|(
name|type
argument_list|)
argument_list|)
operator|.
name|isPresent
argument_list|()
return|;
block|}
block|}
end_class

end_unit

