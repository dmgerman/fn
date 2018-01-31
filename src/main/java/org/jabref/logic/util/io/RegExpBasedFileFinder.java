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
name|nio
operator|.
name|file
operator|.
name|attribute
operator|.
name|BasicFileAttributes
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
name|function
operator|.
name|BiPredicate
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
name|regex
operator|.
name|PatternSyntaxException
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|RegExpBasedFileFinder
class|class
name|RegExpBasedFileFinder
implements|implements
name|FileFinder
block|{
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
DECL|field|regExp
specifier|private
specifier|final
name|String
name|regExp
decl_stmt|;
DECL|field|keywordDelimiter
specifier|private
specifier|final
name|Character
name|keywordDelimiter
decl_stmt|;
comment|/**      * @param regExp The expression deciding which names are acceptable.      */
DECL|method|RegExpBasedFileFinder (String regExp, Character keywordDelimiter)
name|RegExpBasedFileFinder
parameter_list|(
name|String
name|regExp
parameter_list|,
name|Character
name|keywordDelimiter
parameter_list|)
block|{
name|this
operator|.
name|regExp
operator|=
name|regExp
expr_stmt|;
name|this
operator|.
name|keywordDelimiter
operator|=
name|keywordDelimiter
expr_stmt|;
block|}
comment|/**      * Takes a string that contains bracketed expression and expands each of these using getFieldAndFormat.      *<p>      * Unknown Bracket expressions are silently dropped.      */
DECL|method|expandBrackets (String bracketString, BibEntry entry, BibDatabase database, Character keywordDelimiter)
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
parameter_list|,
name|Character
name|keywordDelimiter
parameter_list|)
block|{
name|Matcher
name|matcher
init|=
name|SQUARE_BRACKETS_PATTERN
operator|.
name|matcher
argument_list|(
name|bracketString
argument_list|)
decl_stmt|;
name|StringBuffer
name|expandedStringBuffer
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
while|while
condition|(
name|matcher
operator|.
name|find
argument_list|()
condition|)
block|{
name|String
name|replacement
init|=
name|BracketedPattern
operator|.
name|expandBrackets
argument_list|(
name|matcher
operator|.
name|group
argument_list|()
argument_list|,
name|keywordDelimiter
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
decl_stmt|;
name|matcher
operator|.
name|appendReplacement
argument_list|(
name|expandedStringBuffer
argument_list|,
name|replacement
argument_list|)
expr_stmt|;
block|}
name|matcher
operator|.
name|appendTail
argument_list|(
name|expandedStringBuffer
argument_list|)
expr_stmt|;
return|return
name|expandedStringBuffer
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Method for searching for files using regexp. A list of extensions and directories can be      * given.      *      * @param entry       The entry to search for.      * @param extensions  The extensions that are acceptable.      * @param directories The root directories to search.      * @return A list of files paths matching the given criteria.      */
annotation|@
name|Override
DECL|method|findAssociatedFiles (BibEntry entry, List<Path> directories, List<String> extensions)
specifier|public
name|List
argument_list|<
name|Path
argument_list|>
name|findAssociatedFiles
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|directories
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|)
throws|throws
name|IOException
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
name|extensionRegExp
argument_list|)
return|;
block|}
comment|/**      * Searches the given directory and filename pattern for a file for the      * BibTeX entry.      *      * Used to fix:      *      * http://sourceforge.net/tracker/index.php?func=detail&aid=1503410&group_id=92314&atid=600309      *      * Requirements:      * - Be able to find the associated PDF in a set of given directories.      * - Be able to return a relative path or absolute path.      * - Be fast.      * - Allow for flexible naming schemes in the PDFs.      *      * Syntax scheme for file:      *<ul>      *<li>* Any subDir</li>      *<li>** Any subDir (recursive)</li>      *<li>[key] Key from BibTeX file and database</li>      *<li>.* Anything else is taken to be a Regular expression.</li>      *</ul>      *      * @param entry non-null      * @param dirs  A set of root directories to start the search from. Paths are      *              returned relative to these directories if relative is set to      *              true. These directories will not be expanded or anything. Use      *              the file attribute for this.      * @return Will return the first file found to match the given criteria or      * null if none was found.      */
DECL|method|findFile (BibEntry entry, List<Path> dirs, String extensionRegExp)
specifier|private
name|List
argument_list|<
name|Path
argument_list|>
name|findFile
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|dirs
parameter_list|,
name|String
name|extensionRegExp
parameter_list|)
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|Path
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
name|Path
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
argument_list|,
name|regExp
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
comment|/**      * The actual work-horse. Will find absolute filepaths starting from the      * given directory using the given regular expression string for search.      */
DECL|method|findFile (final BibEntry entry, final Path directory, final String file, final String extensionRegExp)
specifier|private
name|List
argument_list|<
name|Path
argument_list|>
name|findFile
parameter_list|(
specifier|final
name|BibEntry
name|entry
parameter_list|,
specifier|final
name|Path
name|directory
parameter_list|,
specifier|final
name|String
name|file
parameter_list|,
specifier|final
name|String
name|extensionRegExp
parameter_list|)
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|resultFiles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|String
name|fileName
init|=
name|file
decl_stmt|;
name|Path
name|actualDirectory
decl_stmt|;
if|if
condition|(
name|fileName
operator|.
name|startsWith
argument_list|(
literal|"/"
argument_list|)
condition|)
block|{
name|actualDirectory
operator|=
name|Paths
operator|.
name|get
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
name|fileName
operator|=
name|fileName
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
name|fileName
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
name|fileName
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
name|fileName
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
name|resultFiles
return|;
block|}
for|for
control|(
name|int
name|index
init|=
literal|0
init|;
name|index
operator|<
operator|(
name|fileParts
operator|.
name|length
operator|-
literal|1
operator|)
condition|;
name|index
operator|++
control|)
block|{
name|String
name|dirToProcess
init|=
name|fileParts
index|[
name|index
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
argument_list|,
name|keywordDelimiter
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
name|Paths
operator|.
name|get
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
name|actualDirectory
operator|.
name|getParent
argument_list|()
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
name|toFile
argument_list|()
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
name|index
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
name|resultFiles
operator|.
name|addAll
argument_list|(
name|findFile
argument_list|(
name|entry
argument_list|,
name|subDir
operator|.
name|toPath
argument_list|()
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
name|index
operator|+
literal|1
argument_list|,
name|fileParts
operator|.
name|length
argument_list|)
decl_stmt|;
specifier|final
name|Path
name|rootDirectory
init|=
name|actualDirectory
decl_stmt|;
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
name|actualDirectory
argument_list|)
init|)
block|{
comment|// We only want to transverse directory (and not the current one; this is already done below)
for|for
control|(
name|Path
name|path
range|:
name|pathStream
operator|.
name|filter
argument_list|(
name|element
lambda|->
name|isSubDirectory
argument_list|(
name|rootDirectory
argument_list|,
name|element
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
control|)
block|{
name|resultFiles
operator|.
name|addAll
argument_list|(
name|findFile
argument_list|(
name|entry
argument_list|,
name|path
argument_list|,
name|restOfFileString
argument_list|,
name|extensionRegExp
argument_list|)
argument_list|)
block|;                     }
block|}
catch|catch
parameter_list|(
name|UncheckedIOException
name|ioe
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
name|ioe
argument_list|)
throw|;
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
argument_list|,
name|keywordDelimiter
argument_list|)
operator|.
name|replaceAll
argument_list|(
name|EXT_MARKER
argument_list|,
name|extensionRegExp
argument_list|)
decl_stmt|;
try|try
block|{
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
name|BiPredicate
argument_list|<
name|Path
argument_list|,
name|BasicFileAttributes
argument_list|>
name|matcher
init|=
parameter_list|(
name|path
parameter_list|,
name|attributes
parameter_list|)
lambda|->
name|toMatch
operator|.
name|matcher
argument_list|(
name|path
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|matches
argument_list|()
decl_stmt|;
name|resultFiles
operator|.
name|addAll
argument_list|(
name|collectFilesWithMatcher
argument_list|(
name|actualDirectory
argument_list|,
name|matcher
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UncheckedIOException
decl||
name|PatternSyntaxException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Could not look for "
operator|+
name|filenameToLookFor
argument_list|,
name|e
argument_list|)
throw|;
block|}
return|return
name|resultFiles
return|;
block|}
DECL|method|collectFilesWithMatcher (Path actualDirectory, BiPredicate<Path, BasicFileAttributes> matcher)
specifier|private
name|List
argument_list|<
name|Path
argument_list|>
name|collectFilesWithMatcher
parameter_list|(
name|Path
name|actualDirectory
parameter_list|,
name|BiPredicate
argument_list|<
name|Path
argument_list|,
name|BasicFileAttributes
argument_list|>
name|matcher
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
name|find
argument_list|(
name|actualDirectory
argument_list|,
literal|1
argument_list|,
name|matcher
argument_list|)
init|)
block|{
return|return
name|pathStream
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
catch|catch
parameter_list|(
name|UncheckedIOException
decl||
name|IOException
name|ioe
parameter_list|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
DECL|method|isSubDirectory (Path rootDirectory, Path path)
specifier|private
name|boolean
name|isSubDirectory
parameter_list|(
name|Path
name|rootDirectory
parameter_list|,
name|Path
name|path
parameter_list|)
block|{
return|return
operator|!
name|rootDirectory
operator|.
name|equals
argument_list|(
name|path
argument_list|)
operator|&&
name|Files
operator|.
name|isDirectory
argument_list|(
name|path
argument_list|)
return|;
block|}
block|}
end_class

end_unit

