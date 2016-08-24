begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
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
name|Map
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtexkeypattern
operator|.
name|BibtexKeyPatternUtil
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
name|strings
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
name|entry
operator|.
name|BibEntry
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

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Apr 12, 2008  * Time: 1:46:44 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|RegExpFileSearch
specifier|public
class|class
name|RegExpFileSearch
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
name|RegExpFileSearch
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|EXT_MARKER
specifier|private
specifier|static
specifier|final
name|String
name|EXT_MARKER
init|=
literal|"__EXTENSION__"
decl_stmt|;
DECL|field|ESCAPE_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|ESCAPE_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"([^\\\\])\\\\([^\\\\])"
argument_list|)
decl_stmt|;
DECL|field|SQUARE_BRACKETS_PATTERN
specifier|private
specifier|static
specifier|final
name|Pattern
name|SQUARE_BRACKETS_PATTERN
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"\\[.*?\\]"
argument_list|)
decl_stmt|;
comment|/**      * Search for file links for a set of entries using regexp. Lists of extensions and directories      * are given.      * @param entries The entries to search for.      * @param extensions The extensions that are acceptable.      * @param directories The root directories to search.      * @param regExp The expression deciding which names are acceptable.      * @return A map linking each given entry to a list of files matching the given criteria.      */
DECL|method|findFilesForSet (List<BibEntry> entries, List<String> extensions, List<File> directories, String regExp)
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
name|findFilesForSet
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|List
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
name|BibEntry
argument_list|,
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
name|res
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
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
DECL|method|findFiles (BibEntry entry, List<String> extensions, List<File> directories, String regularExpression)
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findFiles
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
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
name|regularExpression
parameter_list|)
block|{
name|String
name|extensionRegExp
init|=
literal|'('
operator|+
name|String
operator|.
name|join
argument_list|(
literal|"|"
argument_list|,
name|extensions
argument_list|)
operator|+
literal|')'
decl_stmt|;
return|return
name|findFile
argument_list|(
name|entry
argument_list|,
name|directories
argument_list|,
name|regularExpression
argument_list|,
name|extensionRegExp
argument_list|)
return|;
block|}
comment|/**      * Searches the given directory and filename pattern for a file for the      * BibTeX entry.      *      * Used to fix:      *      * http://sourceforge.net/tracker/index.php?func=detail&aid=1503410&group_id=92314&atid=600309      *      * Requirements:      *  - Be able to find the associated PDF in a set of given directories.      *  - Be able to return a relative path or absolute path.      *  - Be fast.      *  - Allow for flexible naming schemes in the PDFs.      *      * Syntax scheme for file:      *<ul>      *<li>* Any subDir</li>      *<li>** Any subDir (recursive)</li>      *<li>[key] Key from BibTeX file and database</li>      *<li>.* Anything else is taken to be a Regular expression.</li>      *</ul>      *      * @param entry      *            non-null      * @param dirs      *            A set of root directories to start the search from. Paths are      *            returned relative to these directories if relative is set to      *            true. These directories will not be expanded or anything. Use      *            the file attribute for this.      * @param file      *            non-null      *      * @param relative      *            whether to return relative file paths or absolute ones      *      * @return Will return the first file found to match the given criteria or      *         null if none was found.      */
DECL|method|findFile (BibEntry entry, List<File> dirs, String file, String extensionRegExp)
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findFile
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
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
parameter_list|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<>
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
name|res
operator|.
name|addAll
argument_list|(
name|findFile
argument_list|(
name|entry
argument_list|,
name|directory
operator|.
name|getPath
argument_list|()
argument_list|,
name|file
argument_list|,
name|extensionRegExp
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
comment|/**      * Internal Version of findFile, which also accepts a current directory to      * base the search on.      *      */
DECL|method|findFile (BibEntry entry, String directory, String file, String extensionRegExp)
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findFile
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|String
name|directory
parameter_list|,
name|String
name|file
parameter_list|,
name|String
name|extensionRegExp
parameter_list|)
block|{
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
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|List
argument_list|<
name|File
argument_list|>
name|fileList
init|=
name|RegExpFileSearch
operator|.
name|findFile
argument_list|(
name|entry
argument_list|,
name|root
argument_list|,
name|file
argument_list|,
name|extensionRegExp
argument_list|)
decl_stmt|;
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
name|File
name|tmpFile
range|:
name|fileList
control|)
block|{
try|try
block|{
comment|/**                  * [ 1601651 ] PDF subdirectory - missing first character                  *                  * http://sourceforge.net/tracker/index.php?func=detail&aid=1601651&group_id=92314&atid=600306                  */
comment|// Changed by M. Alver 2007.01.04:
comment|// Remove first character if it is a directory separator character:
name|String
name|tmp
init|=
name|tmpFile
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
name|result
operator|.
name|add
argument_list|(
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
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem searching"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
comment|/**      * The actual work-horse. Will find absolute filepaths starting from the      * given directory using the given regular expression string for search.      */
DECL|method|findFile (BibEntry entry, File directory, String file, String extensionRegExp)
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findFile
parameter_list|(
name|BibEntry
name|entry
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
name|List
argument_list|<
name|File
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|File
name|actualDirectory
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
name|actualDirectory
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
else|else
block|{
name|actualDirectory
operator|=
name|directory
expr_stmt|;
block|}
comment|// Escape handling...
name|Matcher
name|m
init|=
name|ESCAPE_PATTERN
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
name|expandBrackets
argument_list|(
name|dirToProcess
argument_list|,
name|entry
argument_list|,
literal|null
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
name|actualDirectory
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
literal|"."
operator|.
name|equals
argument_list|(
name|dirToProcess
argument_list|)
condition|)
block|{
comment|// Stay in current directory
continue|continue;
block|}
if|if
condition|(
literal|".."
operator|.
name|equals
argument_list|(
name|dirToProcess
argument_list|)
condition|)
block|{
name|actualDirectory
operator|=
operator|new
name|File
argument_list|(
name|actualDirectory
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
continue|continue;
block|}
if|if
condition|(
literal|"*"
operator|.
name|equals
argument_list|(
name|dirToProcess
argument_list|)
condition|)
block|{
comment|// Do for all direct subdirs
name|File
index|[]
name|subDirs
init|=
name|actualDirectory
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
name|findFile
argument_list|(
name|entry
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
literal|"**"
operator|.
name|equals
argument_list|(
name|dirToProcess
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
argument_list|<>
argument_list|()
decl_stmt|;
name|toDo
operator|.
name|add
argument_list|(
name|actualDirectory
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
name|findFile
argument_list|(
name|entry
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
name|replace
argument_list|(
literal|"[extension]"
argument_list|,
name|EXT_MARKER
argument_list|)
decl_stmt|;
name|String
name|filenameToLookFor
init|=
name|expandBrackets
argument_list|(
name|filePart
argument_list|,
name|entry
argument_list|,
literal|null
argument_list|)
operator|.
name|replaceAll
argument_list|(
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
name|actualDirectory
operator|.
name|listFiles
argument_list|(
parameter_list|(
name|arg0
parameter_list|,
name|arg1
parameter_list|)
lambda|->
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
comment|/**      * Takes a string that contains bracketed expression and expands each of these using getFieldAndFormat.      *<p>      * Unknown Bracket expressions are silently dropped.      *      * @param bracketString      * @param entry      * @param database      * @return      */
DECL|method|expandBrackets (String bracketString, BibEntry entry, BibDatabase database)
specifier|public
specifier|static
name|String
name|expandBrackets
parameter_list|(
name|String
name|bracketString
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
name|Matcher
name|m
init|=
name|SQUARE_BRACKETS_PATTERN
operator|.
name|matcher
argument_list|(
name|bracketString
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
name|String
name|replacement
init|=
name|getFieldAndFormat
argument_list|(
name|m
operator|.
name|group
argument_list|()
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|m
operator|.
name|appendReplacement
argument_list|(
name|s
argument_list|,
name|replacement
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
return|return
name|s
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Accepts a string like [author:lower] or [title:abbr] or [auth], whereas the first part signifies the bibtex-field      * to get, or the key generator field marker to use, while the others are the modifiers that will be applied.      *      * @param fieldAndFormat      * @param entry      * @param database      * @return      */
DECL|method|getFieldAndFormat (String fieldAndFormat, BibEntry entry, BibDatabase database)
specifier|public
specifier|static
name|String
name|getFieldAndFormat
parameter_list|(
name|String
name|fieldAndFormat
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|BibDatabase
name|database
parameter_list|)
block|{
name|String
name|strippedFieldAndFormat
init|=
name|StringUtil
operator|.
name|stripBrackets
argument_list|(
name|fieldAndFormat
argument_list|)
decl_stmt|;
name|int
name|colon
init|=
name|strippedFieldAndFormat
operator|.
name|indexOf
argument_list|(
literal|':'
argument_list|)
decl_stmt|;
name|String
name|beforeColon
decl_stmt|;
name|String
name|afterColon
decl_stmt|;
if|if
condition|(
name|colon
operator|==
operator|-
literal|1
condition|)
block|{
name|beforeColon
operator|=
name|strippedFieldAndFormat
expr_stmt|;
name|afterColon
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|beforeColon
operator|=
name|strippedFieldAndFormat
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|colon
argument_list|)
expr_stmt|;
name|afterColon
operator|=
name|strippedFieldAndFormat
operator|.
name|substring
argument_list|(
name|colon
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
name|beforeColon
operator|=
name|beforeColon
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|beforeColon
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|""
return|;
block|}
comment|// If no field value was found, try to interpret it as a key generator field marker:
name|String
name|fieldValue
init|=
name|BibDatabase
operator|.
name|getResolvedField
argument_list|(
name|beforeColon
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
operator|.
name|orElse
argument_list|(
name|BibtexKeyPatternUtil
operator|.
name|makeLabel
argument_list|(
name|entry
argument_list|,
name|beforeColon
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldValue
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
if|if
condition|(
operator|(
name|afterColon
operator|==
literal|null
operator|)
operator|||
name|afterColon
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|fieldValue
return|;
block|}
name|String
index|[]
name|parts
init|=
name|afterColon
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
name|fieldValue
operator|=
name|BibtexKeyPatternUtil
operator|.
name|applyModifiers
argument_list|(
name|fieldValue
argument_list|,
name|parts
argument_list|,
literal|0
argument_list|)
expr_stmt|;
return|return
name|fieldValue
return|;
block|}
block|}
end_class

end_unit

