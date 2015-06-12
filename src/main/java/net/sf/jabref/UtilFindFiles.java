begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|external
operator|.
name|ExternalFileType
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
name|labelPattern
operator|.
name|LabelPatternUtil
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
name|FilenameFilter
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

begin_class
DECL|class|UtilFindFiles
specifier|public
class|class
name|UtilFindFiles
block|{
comment|/** 	 * Searches the given directory and subdirectories for a pdf file with name 	 * as given + ".pdf" 	 */
DECL|method|findPdf (String key, String extension, String directory, OpenFileFilter off)
specifier|public
specifier|static
name|String
name|findPdf
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|extension
parameter_list|,
name|String
name|directory
parameter_list|,
name|OpenFileFilter
name|off
parameter_list|)
block|{
comment|// String filename = key + "."+extension;
comment|/* 		 * Simon Fischer's patch for replacing a regexp in keys before 		 * converting to filename: 		 * 		 * String regex = Globals.prefs.get("basenamePatternRegex"); if ((regex != 		 * null)&& (regex.trim().length()> 0)) { String replacement = 		 * Globals.prefs.get("basenamePatternReplacement"); key = 		 * key.replaceAll(regex, replacement); } 		 */
if|if
condition|(
operator|!
name|directory
operator|.
name|endsWith
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
argument_list|)
condition|)
name|directory
operator|+=
name|System
operator|.
name|getProperty
argument_list|(
literal|"file.separator"
argument_list|)
expr_stmt|;
name|String
name|found
init|=
name|findInDir
argument_list|(
name|key
argument_list|,
name|directory
argument_list|,
name|off
argument_list|,
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|found
operator|!=
literal|null
condition|)
return|return
name|found
operator|.
name|substring
argument_list|(
name|directory
operator|.
name|length
argument_list|()
argument_list|)
return|;
else|else
return|return
literal|null
return|;
block|}
DECL|method|findFiles (Collection<String> extensions, Collection<File> directories)
specifier|public
specifier|static
name|Set
argument_list|<
name|File
argument_list|>
name|findFiles
parameter_list|(
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
name|Set
argument_list|<
name|File
argument_list|>
name|result
init|=
operator|new
name|HashSet
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
name|directories
control|)
block|{
name|result
operator|.
name|addAll
argument_list|(
name|findFiles
argument_list|(
name|extensions
argument_list|,
name|directory
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|findFiles (Collection<String> extensions, File directory)
specifier|private
specifier|static
name|Collection
argument_list|<
name|?
extends|extends
name|File
argument_list|>
name|findFiles
parameter_list|(
name|Collection
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|File
name|directory
parameter_list|)
block|{
name|Set
argument_list|<
name|File
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
name|File
index|[]
name|children
init|=
name|directory
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|children
operator|==
literal|null
condition|)
return|return
name|result
return|;
comment|// No permission?
for|for
control|(
name|File
name|child
range|:
name|children
control|)
block|{
if|if
condition|(
name|child
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|result
operator|.
name|addAll
argument_list|(
name|findFiles
argument_list|(
name|extensions
argument_list|,
name|child
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|extension
init|=
name|Util
operator|.
name|getFileExtension
argument_list|(
name|child
argument_list|)
decl_stmt|;
if|if
condition|(
name|extension
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|extensions
operator|.
name|contains
argument_list|(
name|extension
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|child
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
return|return
name|result
return|;
block|}
comment|/** 	 * New version of findPdf that uses findFiles. 	 * 	 * The search pattern will be read from the preferences. 	 * 	 * The [extension]-tags in this pattern will be replace by the given 	 * extension parameter. 	 * 	 */
DECL|method|findPdf (BibtexEntry entry, String extension, String directory)
specifier|public
specifier|static
name|String
name|findPdf
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|String
name|extension
parameter_list|,
name|String
name|directory
parameter_list|)
block|{
return|return
name|findPdf
argument_list|(
name|entry
argument_list|,
name|extension
argument_list|,
operator|new
name|String
index|[]
block|{
name|directory
block|}
argument_list|)
return|;
block|}
comment|/** 	 * Convenience method for findPDF. Can search multiple PDF directories. 	 */
DECL|method|findPdf (BibtexEntry entry, String extension, String[] directories)
specifier|public
specifier|static
name|String
name|findPdf
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|String
name|extension
parameter_list|,
name|String
index|[]
name|directories
parameter_list|)
block|{
name|String
name|regularExpression
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_REG_EXP_SEARCH_KEY
argument_list|)
condition|)
block|{
name|regularExpression
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|regularExpression
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
expr_stmt|;
block|}
name|regularExpression
operator|=
name|regularExpression
operator|.
name|replaceAll
argument_list|(
literal|"\\[extension\\]"
argument_list|,
name|extension
argument_list|)
expr_stmt|;
return|return
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
literal|true
argument_list|)
return|;
block|}
comment|/**      * Convenience menthod for findPDF. Searches for a file of the given type.      * @param entry The BibtexEntry to search for a link for.      * @param fileType The file type to search for.      * @return The link to the file found, or null if not found.      */
DECL|method|findFile (BibtexEntry entry, ExternalFileType fileType, List<String> extraDirs)
specifier|public
specifier|static
name|String
name|findFile
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|ExternalFileType
name|fileType
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extraDirs
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|dirs
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|dirs
operator|.
name|addAll
argument_list|(
name|extraDirs
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
name|fileType
operator|.
name|getExtension
argument_list|()
operator|+
literal|"Directory"
argument_list|)
condition|)
block|{
name|dirs
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|fileType
operator|.
name|getExtension
argument_list|()
operator|+
literal|"Directory"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
index|[]
name|directories
init|=
name|dirs
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|dirs
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
return|return
name|findPdf
argument_list|(
name|entry
argument_list|,
name|fileType
operator|.
name|getExtension
argument_list|()
argument_list|,
name|directories
argument_list|)
return|;
block|}
comment|/** 	 * Searches the given directory and file name pattern for a file for the 	 * bibtexentry. 	 * 	 * Used to fix: 	 * 	 * http://sourceforge.net/tracker/index.php?func=detail&aid=1503410&group_id=92314&atid=600309 	 * 	 * Requirements: 	 *  - Be able to find the associated PDF in a set of given directories. 	 *  - Be able to return a relative path or absolute path. 	 *  - Be fast. 	 *  - Allow for flexible naming schemes in the PDFs. 	 * 	 * Syntax scheme for file: 	 *<ul> 	 *<li>* Any subDir</li> 	 *<li>** Any subDir (recursiv)</li> 	 *<li>[key] Key from bibtex file and database</li> 	 *<li>.* Anything else is taken to be a Regular expression.</li> 	 *</ul> 	 * 	 * @param entry 	 *            non-null 	 * @param database 	 *            non-null 	 * @param directory 	 *            A set of root directories to start the search from. Paths are 	 *            returned relative to these directories if relative is set to 	 *            true. These directories will not be expanded or anything. Use 	 *            the file attribute for this. 	 * @param file 	 *            non-null 	 * 	 * @param relative 	 *            whether to return relative file paths or absolute ones 	 * 	 * @return Will return the first file found to match the given criteria or 	 *         null if none was found. 	 */
DECL|method|findFile (BibtexEntry entry, BibtexDatabase database, String[] directory, String file, boolean relative)
specifier|public
specifier|static
name|String
name|findFile
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|String
index|[]
name|directory
parameter_list|,
name|String
name|file
parameter_list|,
name|boolean
name|relative
parameter_list|)
block|{
for|for
control|(
name|String
name|aDirectory
range|:
name|directory
control|)
block|{
name|String
name|result
init|=
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|aDirectory
argument_list|,
name|file
argument_list|,
name|relative
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
return|return
name|result
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/** 	 * Convenience function for absolute search. 	 * 	 * Uses findFile(BibtexEntry, BibtexDatabase, (String)null, String, false). 	 */
DECL|method|findFile (BibtexEntry entry, BibtexDatabase database, String file)
specifier|public
specifier|static
name|String
name|findFile
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|,
name|String
name|file
parameter_list|)
block|{
return|return
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
operator|(
name|String
operator|)
literal|null
argument_list|,
name|file
argument_list|,
literal|false
argument_list|)
return|;
block|}
comment|/** 	 * Internal Version of findFile, which also accepts a current directory to 	 * base the search on. 	 * 	 */
DECL|method|findFile (BibtexEntry entry, BibtexDatabase database, String directory, String file, boolean relative)
specifier|public
specifier|static
name|String
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
name|boolean
name|relative
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
return|return
literal|null
return|;
name|String
name|found
init|=
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|root
argument_list|,
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|directory
operator|==
literal|null
operator|||
operator|!
name|relative
condition|)
block|{
return|return
name|found
return|;
block|}
if|if
condition|(
name|found
operator|!=
literal|null
condition|)
block|{
try|try
block|{
comment|/** 				 * [ 1601651 ] PDF subdirectory - missing first character 				 * 				 * http://sourceforge.net/tracker/index.php?func=detail&aid=1601651&group_id=92314&atid=600306 				 */
comment|// Changed by M. Alver 2007.01.04:
comment|// Remove first character if it is a directory separator character:
name|String
name|tmp
init|=
name|found
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
name|tmp
operator|=
name|tmp
operator|.
name|substring
argument_list|(
literal|1
argument_list|)
expr_stmt|;
return|return
name|tmp
return|;
comment|//return found.substring(root.getCanonicalPath().length());
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
comment|/** 	 * The actual work-horse. Will find absolute filepaths starting from the 	 * given directory using the given regular expression string for search. 	 */
DECL|method|findFile (BibtexEntry entry, BibtexDatabase database, File directory, String file)
specifier|protected
specifier|static
name|String
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
parameter_list|)
block|{
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
literal|"/"
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
return|return
literal|null
return|;
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
name|fileParts
operator|.
name|length
operator|-
literal|1
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
literal|"/"
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
operator|==
literal|null
condition|)
return|return
literal|null
return|;
comment|// No permission?
name|String
name|restOfFileString
init|=
name|Util
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
name|String
name|result
init|=
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|subDir
argument_list|,
name|restOfFileString
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
return|return
name|result
return|;
block|}
block|}
return|return
literal|null
return|;
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
name|Util
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
comment|// Before checking the subdirs, we first check the current
comment|// dir
name|String
name|result
init|=
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|directory
argument_list|,
name|restOfFileString
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
return|return
name|result
return|;
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
comment|// No permission?
continue|continue;
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
continue|continue;
name|result
operator|=
name|findFile
argument_list|(
name|entry
argument_list|,
name|database
argument_list|,
name|subDir
argument_list|,
name|restOfFileString
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
return|return
name|result
return|;
block|}
block|}
comment|// We already did the currentDirectory
return|return
literal|null
return|;
block|}
specifier|final
name|Pattern
name|toMatch
init|=
name|Pattern
operator|.
name|compile
argument_list|(
name|dirToProcess
operator|.
name|replaceAll
argument_list|(
literal|"\\\\\\\\"
argument_list|,
literal|"\\\\"
argument_list|)
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
name|matches
operator|==
literal|null
operator|||
name|matches
operator|.
name|length
operator|==
literal|0
condition|)
return|return
literal|null
return|;
name|directory
operator|=
name|matches
index|[
literal|0
index|]
expr_stmt|;
if|if
condition|(
operator|!
name|directory
operator|.
name|exists
argument_list|()
condition|)
return|return
literal|null
return|;
block|}
comment|// End process directory information
block|}
comment|// Last step check if the given file can be found in this directory
name|String
name|filenameToLookFor
init|=
name|Util
operator|.
name|expandBrackets
argument_list|(
name|fileParts
index|[
name|fileParts
operator|.
name|length
operator|-
literal|1
index|]
argument_list|,
name|entry
argument_list|,
name|database
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
literal|"^"
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
literal|"$"
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
name|matches
operator|==
literal|null
operator|||
name|matches
operator|.
name|length
operator|==
literal|0
condition|)
return|return
literal|null
return|;
try|try
block|{
return|return
name|matches
index|[
literal|0
index|]
operator|.
name|getCanonicalPath
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
DECL|method|findInDir (String key, String dir, OpenFileFilter off, int count)
specifier|private
specifier|static
name|String
name|findInDir
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|dir
parameter_list|,
name|OpenFileFilter
name|off
parameter_list|,
name|int
name|count
parameter_list|)
block|{
if|if
condition|(
name|count
operator|>
literal|20
condition|)
return|return
literal|null
return|;
comment|// Make sure an infinite loop doesn't occur.
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|dir
argument_list|)
decl_stmt|;
name|File
index|[]
name|all
init|=
name|f
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|all
operator|==
literal|null
condition|)
return|return
literal|null
return|;
comment|// An error occured. We may not have
comment|// permission to list the files.
name|int
name|numFiles
init|=
name|all
operator|.
name|length
decl_stmt|;
for|for
control|(
name|File
name|curFile
range|:
name|all
control|)
block|{
if|if
condition|(
name|curFile
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|String
name|name
init|=
name|curFile
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|startsWith
argument_list|(
name|key
operator|+
literal|"."
argument_list|)
operator|&&
name|off
operator|.
name|accept
argument_list|(
name|name
argument_list|)
condition|)
return|return
name|curFile
operator|.
name|getPath
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|curFile
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|String
name|found
init|=
name|findInDir
argument_list|(
name|key
argument_list|,
name|curFile
operator|.
name|getPath
argument_list|()
argument_list|,
name|off
argument_list|,
name|count
operator|+
literal|1
argument_list|)
decl_stmt|;
if|if
condition|(
name|found
operator|!=
literal|null
condition|)
return|return
name|found
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

