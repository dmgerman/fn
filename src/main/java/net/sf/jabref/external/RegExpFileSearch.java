begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|*
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
name|StringUtil
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
name|Util
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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FilenameFilter
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
name|Matcher
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
name|*
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Apr 12, 2008  * Time: 1:46:44 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|RegExpFileSearch
specifier|public
class|class
name|RegExpFileSearch
block|{
DECL|field|EXT_MARKER
specifier|private
specifier|final
specifier|static
name|String
name|EXT_MARKER
init|=
literal|"__EXTENSION__"
decl_stmt|;
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|BibtexEntry
name|entry
init|=
operator|new
name|BibtexEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
literal|"raffel01"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2001"
argument_list|)
expr_stmt|;
name|ArrayList
argument_list|<
name|String
argument_list|>
name|extensions
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|extensions
operator|.
name|add
argument_list|(
literal|"pdf"
argument_list|)
expr_stmt|;
name|extensions
operator|.
name|add
argument_list|(
literal|"ps"
argument_list|)
expr_stmt|;
name|extensions
operator|.
name|add
argument_list|(
literal|"txt"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|dirs
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
name|dirs
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
literal|"/home/alver/Desktop/Tromso_2008"
argument_list|)
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|RegExpFileSearch
operator|.
name|findFiles
argument_list|(
name|entry
argument_list|,
name|extensions
argument_list|,
name|dirs
argument_list|,
literal|"**/[bibtexkey].*\\\\.[extension]"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Search for file links for a set of entries using regexp. Lists of extensions and directories      * are given.      * @param entries The entries to search for.      * @param extensions The extensions that are acceptable.      * @param directories The root directories to search.      * @param regExp The expression deciding which names are acceptable.      * @return A map linking each given entry to a list of files matching the given criteria.      */
DECL|method|findFilesForSet (Collection<BibtexEntry> entries, Collection<String> extensions, List<File> directories, String regExp)
specifier|public
specifier|static
name|Map
argument_list|<
name|BibtexEntry
argument_list|,
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
name|findFilesForSet
parameter_list|(
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
parameter_list|,
name|Collection
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|List
argument_list|<
name|File
argument_list|>
name|directories
parameter_list|,
name|String
name|regExp
parameter_list|)
block|{
name|Map
argument_list|<
name|BibtexEntry
argument_list|,
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
name|res
init|=
operator|new
name|HashMap
argument_list|<
name|BibtexEntry
argument_list|,
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
name|res
operator|.
name|put
argument_list|(
name|entry
argument_list|,
name|RegExpFileSearch
operator|.
name|findFiles
argument_list|(
name|entry
argument_list|,
name|extensions
argument_list|,
name|directories
argument_list|,
name|regExp
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
comment|/**      * Method for searching for files using regexp. A list of extensions and directories can be      * given.      * @param entry The entry to search for.      * @param extensions The extensions that are acceptable.      * @param directories The root directories to search.      * @param regularExpression The expression deciding which names are acceptable.      * @return A list of files paths matching the given criteria.      */
DECL|method|findFiles (BibtexEntry entry, Collection<String> extensions, Collection<File> directories, String regularExpression)
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findFiles
parameter_list|(
name|BibtexEntry
name|entry
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
parameter_list|,
name|String
name|regularExpression
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|extensions
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'|'
argument_list|)
expr_stmt|;
block|}
block|}
name|String
name|extensionRegExp
init|=
literal|'('
operator|+
name|sb
operator|.
name|toString
argument_list|()
operator|+
literal|')'
decl_stmt|;
return|return
name|RegExpFileSearch
operator|.
name|findFile
argument_list|(
name|entry
argument_list|,
literal|null
argument_list|,
name|directories
argument_list|,
name|regularExpression
argument_list|,
name|extensionRegExp
argument_list|,
literal|true
argument_list|)
return|;
block|}
comment|/**      * Searches the given directory and file name pattern for a file for the      * bibtexentry.      *      * Used to fix:      *      * http://sourceforge.net/tracker/index.php?func=detail&aid=1503410&group_id=92314&atid=600309      *      * Requirements:      *  - Be able to find the associated PDF in a set of given directories.      *  - Be able to return a relative path or absolute path.      *  - Be fast.      *  - Allow for flexible naming schemes in the PDFs.      *      * Syntax scheme for file:      *<ul>      *<li>* Any subDir</li>      *<li>** Any subDir (recursiv)</li>      *<li>[key] Key from bibtex file and database</li>      *<li>.* Anything else is taken to be a Regular expression.</li>      *</ul>      *      * @param entry      *            non-null      * @param database      *            non-null      * @param dirs      *            A set of root directories to start the search from. Paths are      *            returned relative to these directories if relative is set to      *            true. These directories will not be expanded or anything. Use      *            the file attribute for this.      * @param file      *            non-null      *      * @param relative      *            whether to return relative file paths or absolute ones      *      * @return Will return the first file found to match the given criteria or      *         null if none was found.      */
DECL|method|findFile (BibtexEntry entry, BibtexDatabase database, Collection<File> dirs, String file, String extensionRegExp, boolean relative)
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findFile
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|Collection
argument_list|<
name|File
argument_list|>
name|dirs
parameter_list|,
name|String
name|file
parameter_list|,
name|String
name|extensionRegExp
parameter_list|,
name|boolean
name|relative
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|File
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|directory
range|:
name|dirs
control|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|tmp
init|=
name|RegExpFileSearch
operator|.
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|directory
operator|.
name|getPath
argument_list|()
argument_list|,
name|file
argument_list|,
name|extensionRegExp
argument_list|,
name|relative
argument_list|)
decl_stmt|;
if|if
condition|(
name|tmp
operator|!=
literal|null
condition|)
block|{
name|res
operator|.
name|addAll
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|res
return|;
block|}
comment|/**      * Internal Version of findFile, which also accepts a current directory to      * base the search on.      *      */
DECL|method|findFile (BibtexEntry entry, BibtexDatabase database, String directory, String file, String extensionRegExp, boolean relative)
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findFile
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|String
name|directory
parameter_list|,
name|String
name|file
parameter_list|,
name|String
name|extensionRegExp
parameter_list|,
name|boolean
name|relative
parameter_list|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|res
decl_stmt|;
name|File
name|root
decl_stmt|;
if|if
condition|(
name|directory
operator|==
literal|null
condition|)
block|{
name|root
operator|=
operator|new
name|File
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|root
operator|=
operator|new
name|File
argument_list|(
name|directory
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|root
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
literal|null
return|;
block|}
name|res
operator|=
name|RegExpFileSearch
operator|.
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|root
argument_list|,
name|file
argument_list|,
name|extensionRegExp
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|res
operator|.
name|isEmpty
argument_list|()
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
name|res
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
comment|/**                      * [ 1601651 ] PDF subdirectory - missing first character                      *                      * http://sourceforge.net/tracker/index.php?func=detail&aid=1601651&group_id=92314&atid=600306                      */
comment|// Changed by M. Alver 2007.01.04:
comment|// Remove first character if it is a directory separator character:
name|String
name|tmp
init|=
name|res
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getCanonicalPath
argument_list|()
operator|.
name|substring
argument_list|(
name|root
operator|.
name|getCanonicalPath
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|tmp
operator|.
name|length
argument_list|()
operator|>
literal|1
operator|)
operator|&&
operator|(
name|tmp
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
operator|==
name|File
operator|.
name|separatorChar
operator|)
condition|)
block|{
name|tmp
operator|=
name|tmp
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
name|res
operator|.
name|set
argument_list|(
name|i
argument_list|,
operator|new
name|File
argument_list|(
name|tmp
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
name|res
return|;
block|}
comment|/**      * The actual work-horse. Will find absolute filepaths starting from the      * given directory using the given regular expression string for search.      */
DECL|method|findFile (BibtexEntry entry, BibtexDatabase database, File directory, String file, String extensionRegExp)
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findFile
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|File
name|directory
parameter_list|,
name|String
name|file
parameter_list|,
name|String
name|extensionRegExp
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|File
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|startsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|directory
operator|=
operator|new
name|File
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
name|file
operator|=
name|file
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
comment|// Escape handling...
name|Matcher
name|m
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"([^\\\\])\\\\([^\\\\])"
argument_list|)
operator|.
name|matcher
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|StringBuffer
name|s
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
name|m
operator|.
name|find
argument_list|()
condition|)
block|{
name|m
operator|.
name|appendReplacement
argument_list|(
name|s
argument_list|,
name|m
operator|.
name|group
argument_list|(
literal|1
argument_list|)
operator|+
literal|'/'
operator|+
name|m
operator|.
name|group
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|m
operator|.
name|appendTail
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|file
operator|=
name|s
operator|.
name|toString
argument_list|()
expr_stmt|;
name|String
index|[]
name|fileParts
init|=
name|file
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileParts
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
name|res
return|;
block|}
if|if
condition|(
name|fileParts
operator|.
name|length
operator|>
literal|1
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
operator|(
name|fileParts
operator|.
name|length
operator|-
literal|1
operator|)
condition|;
name|i
operator|++
control|)
block|{
name|String
name|dirToProcess
init|=
name|fileParts
index|[
name|i
index|]
decl_stmt|;
name|dirToProcess
operator|=
name|Util
operator|.
name|expandBrackets
argument_list|(
name|dirToProcess
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
expr_stmt|;
if|if
condition|(
name|dirToProcess
operator|.
name|matches
argument_list|(
literal|"^.:$"
argument_list|)
condition|)
block|{
comment|// Windows Drive Letter
name|directory
operator|=
operator|new
name|File
argument_list|(
name|dirToProcess
operator|+
literal|'/'
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|dirToProcess
operator|.
name|equals
argument_list|(
literal|"."
argument_list|)
condition|)
block|{
comment|// Stay in current directory
continue|continue;
block|}
if|if
condition|(
name|dirToProcess
operator|.
name|equals
argument_list|(
literal|".."
argument_list|)
condition|)
block|{
name|directory
operator|=
operator|new
name|File
argument_list|(
name|directory
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
name|dirToProcess
operator|.
name|equals
argument_list|(
literal|"*"
argument_list|)
condition|)
block|{
comment|// Do for all direct subdirs
name|File
index|[]
name|subDirs
init|=
name|directory
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|subDirs
operator|!=
literal|null
condition|)
block|{
name|String
name|restOfFileString
init|=
name|StringUtil
operator|.
name|join
argument_list|(
name|fileParts
argument_list|,
literal|"/"
argument_list|,
name|i
operator|+
literal|1
argument_list|,
name|fileParts
operator|.
name|length
argument_list|)
decl_stmt|;
for|for
control|(
name|File
name|subDir
range|:
name|subDirs
control|)
block|{
if|if
condition|(
name|subDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|res
operator|.
name|addAll
argument_list|(
name|RegExpFileSearch
operator|.
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|subDir
argument_list|,
name|restOfFileString
argument_list|,
name|extensionRegExp
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// Do for all direct and indirect subdirs
if|if
condition|(
name|dirToProcess
operator|.
name|equals
argument_list|(
literal|"**"
argument_list|)
condition|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|toDo
init|=
operator|new
name|LinkedList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
name|toDo
operator|.
name|add
argument_list|(
name|directory
argument_list|)
expr_stmt|;
name|String
name|restOfFileString
init|=
name|StringUtil
operator|.
name|join
argument_list|(
name|fileParts
argument_list|,
literal|"/"
argument_list|,
name|i
operator|+
literal|1
argument_list|,
name|fileParts
operator|.
name|length
argument_list|)
decl_stmt|;
while|while
condition|(
operator|!
name|toDo
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// Get all subdirs of each of the elements found in toDo
name|File
index|[]
name|subDirs
init|=
name|toDo
operator|.
name|remove
argument_list|(
literal|0
argument_list|)
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|subDirs
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
name|toDo
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|subDirs
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|File
name|subDir
range|:
name|subDirs
control|)
block|{
if|if
condition|(
operator|!
name|subDir
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|res
operator|.
name|addAll
argument_list|(
name|RegExpFileSearch
operator|.
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|subDir
argument_list|,
name|restOfFileString
argument_list|,
name|extensionRegExp
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// End process directory information
block|}
comment|// Last step: check if the given file can be found in this directory
name|String
name|filePart
init|=
name|fileParts
index|[
name|fileParts
operator|.
name|length
operator|-
literal|1
index|]
operator|.
name|replaceAll
argument_list|(
literal|"\\[extension\\]"
argument_list|,
name|RegExpFileSearch
operator|.
name|EXT_MARKER
argument_list|)
decl_stmt|;
name|String
name|filenameToLookFor
init|=
name|Util
operator|.
name|expandBrackets
argument_list|(
name|filePart
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
operator|.
name|replaceAll
argument_list|(
name|RegExpFileSearch
operator|.
name|EXT_MARKER
argument_list|,
name|extensionRegExp
argument_list|)
decl_stmt|;
specifier|final
name|Pattern
name|toMatch
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|'^'
operator|+
name|filenameToLookFor
operator|.
name|replaceAll
argument_list|(
literal|"\\\\\\\\"
argument_list|,
literal|"\\\\"
argument_list|)
operator|+
literal|'$'
argument_list|,
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
decl_stmt|;
name|File
index|[]
name|matches
init|=
name|directory
operator|.
name|listFiles
argument_list|(
operator|new
name|FilenameFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|arg0
parameter_list|,
name|String
name|arg1
parameter_list|)
block|{
return|return
name|toMatch
operator|.
name|matcher
argument_list|(
name|arg1
argument_list|)
operator|.
name|matches
argument_list|()
return|;
block|}
block|}
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|matches
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|matches
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
block|{
name|Collections
operator|.
name|addAll
argument_list|(
name|res
argument_list|,
name|matches
argument_list|)
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
block|}
end_class

end_unit

