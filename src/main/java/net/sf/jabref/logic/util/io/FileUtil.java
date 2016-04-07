begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|Globals
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
name|MetaData
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
name|java
operator|.
name|io
operator|.
name|BufferedInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedOutputStream
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
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileOutputStream
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
name|*
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

begin_class
DECL|class|FileUtil
specifier|public
class|class
name|FileUtil
block|{
DECL|field|FILE_SEPARATOR
specifier|private
specifier|static
specifier|final
name|String
name|FILE_SEPARATOR
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
decl_stmt|;
DECL|field|SLASH
specifier|private
specifier|static
specifier|final
name|Pattern
name|SLASH
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
DECL|field|BACKSLASH
specifier|private
specifier|static
specifier|final
name|Pattern
name|BACKSLASH
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\\\"
argument_list|)
decl_stmt|;
comment|/**      * Returns the extension of a file or Optional.empty() if the file does not have one (no . in name).      *      * @param file      * @return The extension, trimmed and in lowercase.      */
DECL|method|getFileExtension (File file)
specifier|public
specifier|static
name|Optional
argument_list|<
name|String
argument_list|>
name|getFileExtension
parameter_list|(
name|File
name|file
parameter_list|)
block|{
return|return
name|getFileExtension
argument_list|(
name|file
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns the extension of a file name or Optional.empty() if the file does not have one (no . in name).      *      * @param fileName      * @return The extension, trimmed and in lowercase.      */
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
name|pos
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
name|pos
operator|>
literal|0
operator|)
operator|&&
operator|(
name|pos
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
name|pos
operator|+
literal|1
argument_list|)
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
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
comment|/**      * Copies a file.      *      * @param source         File Source file      * @param dest           File Destination file      * @param deleteIfExists boolean Determines whether the copy goes on even if the file      *                       exists.      * @return boolean Whether the copy succeeded, or was stopped due to the      * file already existing.      * @throws IOException      */
DECL|method|copyFile (File source, File dest, boolean deleteIfExists)
specifier|public
specifier|static
name|boolean
name|copyFile
parameter_list|(
name|File
name|source
parameter_list|,
name|File
name|dest
parameter_list|,
name|boolean
name|deleteIfExists
parameter_list|)
throws|throws
name|IOException
block|{
comment|// Check if the file already exists.
if|if
condition|(
name|dest
operator|.
name|exists
argument_list|()
operator|&&
operator|!
name|deleteIfExists
condition|)
block|{
return|return
literal|false
return|;
block|}
try|try
init|(
name|BufferedInputStream
name|in
init|=
operator|new
name|BufferedInputStream
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|source
argument_list|)
argument_list|)
init|;
name|BufferedOutputStream
name|out
operator|=
operator|new
name|BufferedOutputStream
argument_list|(
operator|new
name|FileOutputStream
argument_list|(
name|dest
argument_list|)
argument_list|)
init|)
block|{
name|int
name|el
decl_stmt|;
while|while
condition|(
operator|(
name|el
operator|=
name|in
operator|.
name|read
argument_list|()
operator|)
operator|>=
literal|0
condition|)
block|{
name|out
operator|.
name|write
argument_list|(
name|el
argument_list|)
expr_stmt|;
block|}
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
comment|/**      * @param fileName      * @param destFilename      * @return      */
DECL|method|renameFile (String fileName, String destFilename)
specifier|public
specifier|static
name|boolean
name|renameFile
parameter_list|(
name|String
name|fileName
parameter_list|,
name|String
name|destFilename
parameter_list|)
block|{
comment|// File (or directory) with old name
name|File
name|fromFile
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
comment|// File (or directory) with new name
name|File
name|toFile
init|=
operator|new
name|File
argument_list|(
name|destFilename
argument_list|)
decl_stmt|;
comment|// Rename file (or directory)
return|return
name|fromFile
operator|.
name|renameTo
argument_list|(
name|toFile
argument_list|)
return|;
block|}
comment|/**      * Converts a relative filename to an absolute one, if necessary. Returns      * null if the file does not exist.<br/>      *<p>      * Uses<ul>      *<li>the default directory associated with the extension of the file</li>      *<li>the standard file directory</li>      *<li>the directory of the bib file</li>      *</ul>      *      * @param databaseContext The database this file belongs to.      * @param name     The filename, may also be a relative path to the file      */
DECL|method|expandFilename (final BibDatabaseContext databaseContext, String name)
specifier|public
specifier|static
name|Optional
argument_list|<
name|File
argument_list|>
name|expandFilename
parameter_list|(
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|String
name|name
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
name|getFileDirectory
argument_list|(
name|extension
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
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
name|getFileDirectory
argument_list|()
decl_stmt|;
comment|// Include the directory of the bib file:
name|ArrayList
argument_list|<
name|String
argument_list|>
name|al
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
name|al
operator|.
name|contains
argument_list|(
name|dir
argument_list|)
condition|)
block|{
name|al
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
name|al
operator|.
name|contains
argument_list|(
name|aFileDir
argument_list|)
condition|)
block|{
name|al
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
name|al
argument_list|)
return|;
block|}
comment|/**      * Converts a relative filename to an absolute one, if necessary. Returns      * null if the file does not exist.      *<p>      * Will look in each of the given dirs starting from the beginning and      * returning the first found file to match if any.      */
DECL|method|expandFilename (String name, List<String> directories)
specifier|public
specifier|static
name|Optional
argument_list|<
name|File
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
if|if
condition|(
name|dir
operator|!=
literal|null
condition|)
block|{
name|Optional
argument_list|<
name|File
argument_list|>
name|result
init|=
name|expandFilename
argument_list|(
name|name
argument_list|,
name|dir
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
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
comment|/**      * Converts a relative filename to an absolute one, if necessary. Returns      * null if the file does not exist.      */
DECL|method|expandFilename (String filename, String dir)
specifier|private
specifier|static
name|Optional
argument_list|<
name|File
argument_list|>
name|expandFilename
parameter_list|(
name|String
name|filename
parameter_list|,
name|String
name|dir
parameter_list|)
block|{
if|if
condition|(
operator|(
name|filename
operator|==
literal|null
operator|)
operator|||
name|filename
operator|.
name|isEmpty
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
name|String
name|name
init|=
name|filename
decl_stmt|;
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
operator|||
operator|(
name|dir
operator|==
literal|null
operator|)
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
if|if
condition|(
name|dir
operator|.
name|endsWith
argument_list|(
name|FILE_SEPARATOR
argument_list|)
condition|)
block|{
name|name
operator|=
name|dir
operator|+
name|name
expr_stmt|;
block|}
else|else
block|{
name|name
operator|=
name|dir
operator|+
name|FILE_SEPARATOR
operator|+
name|name
expr_stmt|;
block|}
comment|// fix / and \ problems:
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|name
operator|=
name|SLASH
operator|.
name|matcher
argument_list|(
name|name
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|name
operator|=
name|BACKSLASH
operator|.
name|matcher
argument_list|(
name|name
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"/"
argument_list|)
expr_stmt|;
block|}
name|File
name|fileInDir
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileInDir
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fileInDir
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
comment|/**      * Converts an absolute filename to a relative one, if necessary.      * Returns the parameter fileName itself if no shortening is possible      *<p>      * This method works correctly only if dirs are sorted decent in their length      * i.e. /home/user/literature/important before /home/user/literature      *      * @param fileName the filename to be shortened      * @param dirs     directories to check.      */
DECL|method|shortenFileName (File fileName, List<String> dirs)
specifier|public
specifier|static
name|File
name|shortenFileName
parameter_list|(
name|File
name|fileName
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|dirs
parameter_list|)
block|{
if|if
condition|(
operator|(
name|fileName
operator|==
literal|null
operator|)
operator|||
operator|!
name|fileName
operator|.
name|isAbsolute
argument_list|()
operator|||
operator|(
name|dirs
operator|==
literal|null
operator|)
condition|)
block|{
return|return
name|fileName
return|;
block|}
for|for
control|(
name|String
name|dir
range|:
name|dirs
control|)
block|{
if|if
condition|(
name|dir
operator|!=
literal|null
condition|)
block|{
name|File
name|result
init|=
name|shortenFileName
argument_list|(
name|fileName
argument_list|,
name|dir
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|result
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|result
operator|.
name|equals
argument_list|(
name|fileName
argument_list|)
condition|)
block|{
return|return
name|result
return|;
block|}
block|}
block|}
return|return
name|fileName
return|;
block|}
DECL|method|shortenFileName (File fileName, String directory)
specifier|private
specifier|static
name|File
name|shortenFileName
parameter_list|(
name|File
name|fileName
parameter_list|,
name|String
name|directory
parameter_list|)
block|{
if|if
condition|(
operator|(
name|fileName
operator|==
literal|null
operator|)
operator|||
operator|!
name|fileName
operator|.
name|isAbsolute
argument_list|()
operator|||
operator|(
name|directory
operator|==
literal|null
operator|)
condition|)
block|{
return|return
name|fileName
return|;
block|}
name|String
name|dir
init|=
name|directory
decl_stmt|;
name|String
name|longName
decl_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
comment|// case-insensitive matching on Windows
name|longName
operator|=
name|fileName
operator|.
name|toString
argument_list|()
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|dir
operator|=
name|dir
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|longName
operator|=
name|fileName
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|dir
operator|.
name|endsWith
argument_list|(
name|FILE_SEPARATOR
argument_list|)
condition|)
block|{
name|dir
operator|=
name|dir
operator|.
name|concat
argument_list|(
name|FILE_SEPARATOR
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|longName
operator|.
name|startsWith
argument_list|(
name|dir
argument_list|)
condition|)
block|{
comment|// result is based on original name, not on lower-cased name
name|String
name|newName
init|=
name|fileName
operator|.
name|toString
argument_list|()
operator|.
name|substring
argument_list|(
name|dir
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|File
argument_list|(
name|newName
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|fileName
return|;
block|}
block|}
DECL|method|findAssociatedFiles (Collection<BibEntry> entries, Collection<String> extensions, Collection<File> directories)
specifier|public
specifier|static
name|Map
argument_list|<
name|BibEntry
argument_list|,
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
name|findAssociatedFiles
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|Collection
argument_list|<
name|File
argument_list|>
name|directories
parameter_list|)
block|{
name|HashMap
argument_list|<
name|BibEntry
argument_list|,
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
name|result
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// First scan directories
name|Set
argument_list|<
name|File
argument_list|>
name|filesWithExtension
init|=
name|FileFinder
operator|.
name|findFiles
argument_list|(
name|extensions
argument_list|,
name|directories
argument_list|)
decl_stmt|;
comment|// Initialize Result-Set
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|result
operator|.
name|put
argument_list|(
name|entry
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|boolean
name|exactOnly
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_EXACT_KEY_ONLY
argument_list|)
decl_stmt|;
comment|// Now look for keys
name|nextFile
label|:
for|for
control|(
name|File
name|file
range|:
name|filesWithExtension
control|)
block|{
name|String
name|name
init|=
name|file
operator|.
name|getName
argument_list|()
decl_stmt|;
name|int
name|dot
init|=
name|name
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
decl_stmt|;
comment|// First, look for exact matches:
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|String
name|citeKey
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|citeKey
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|citeKey
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|(
name|dot
operator|>
literal|0
operator|)
operator|&&
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|dot
argument_list|)
operator|.
name|equals
argument_list|(
name|citeKey
argument_list|)
condition|)
block|{
name|result
operator|.
name|get
argument_list|(
name|entry
argument_list|)
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
continue|continue
name|nextFile
continue|;
block|}
block|}
comment|// If we get here, we didn't find any exact matches. If non-exact
comment|// matches are allowed, try to find one:
if|if
condition|(
operator|!
name|exactOnly
condition|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|String
name|citeKey
init|=
name|entry
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|citeKey
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|citeKey
operator|.
name|isEmpty
argument_list|()
operator|&&
name|name
operator|.
name|startsWith
argument_list|(
name|citeKey
argument_list|)
condition|)
block|{
name|result
operator|.
name|get
argument_list|(
name|entry
argument_list|)
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
continue|continue
name|nextFile
continue|;
block|}
block|}
block|}
block|}
return|return
name|result
return|;
block|}
comment|/**      * Returns the list of linked files. The files have the absolute filename      *      * @param bes list of BibTeX entries      * @param fileDirs list of directories to try for expansion      *      * @return list of files. May be empty      */
DECL|method|getListOfLinkedFiles (List<BibEntry> bes, List<String> fileDirs)
specifier|public
specifier|static
name|List
argument_list|<
name|File
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
name|String
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
name|List
argument_list|<
name|File
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|bes
control|)
block|{
name|List
argument_list|<
name|ParsedFileField
argument_list|>
name|fileList
init|=
name|FileField
operator|.
name|parse
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|ParsedFileField
name|file
range|:
name|fileList
control|)
block|{
name|expandFilename
argument_list|(
name|file
operator|.
name|getLink
argument_list|()
argument_list|,
name|fileDirs
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|result
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

