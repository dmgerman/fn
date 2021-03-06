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
name|HashSet
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
name|Set
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
name|BibtexKeyGenerator
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

begin_class
DECL|class|CiteKeyBasedFileFinder
class|class
name|CiteKeyBasedFileFinder
implements|implements
name|FileFinder
block|{
DECL|field|exactKeyOnly
specifier|private
specifier|final
name|boolean
name|exactKeyOnly
decl_stmt|;
DECL|method|CiteKeyBasedFileFinder (boolean exactKeyOnly)
name|CiteKeyBasedFileFinder
parameter_list|(
name|boolean
name|exactKeyOnly
parameter_list|)
block|{
name|this
operator|.
name|exactKeyOnly
operator|=
name|exactKeyOnly
expr_stmt|;
block|}
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
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|directories
argument_list|)
expr_stmt|;
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|citeKeyOptional
init|=
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
decl_stmt|;
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|citeKeyOptional
argument_list|)
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|String
name|citeKey
init|=
name|citeKeyOptional
operator|.
name|get
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// First scan directories
name|Set
argument_list|<
name|Path
argument_list|>
name|filesWithExtension
init|=
name|findFilesByExtension
argument_list|(
name|directories
argument_list|,
name|extensions
argument_list|)
decl_stmt|;
comment|// Now look for keys
for|for
control|(
name|Path
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
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|nameWithoutExtension
init|=
name|FileUtil
operator|.
name|getBaseName
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|// First, look for exact matches
if|if
condition|(
name|nameWithoutExtension
operator|.
name|equals
argument_list|(
name|citeKey
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
continue|continue;
block|}
comment|// If we get here, we did not find any exact matches. If non-exact matches are allowed, try to find one
if|if
condition|(
operator|!
name|exactKeyOnly
operator|&&
name|matches
argument_list|(
name|name
argument_list|,
name|citeKey
argument_list|)
condition|)
block|{
name|result
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
operator|.
name|stream
argument_list|()
operator|.
name|sorted
argument_list|()
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
DECL|method|matches (String filename, String citeKey)
specifier|private
name|boolean
name|matches
parameter_list|(
name|String
name|filename
parameter_list|,
name|String
name|citeKey
parameter_list|)
block|{
name|boolean
name|startsWithKey
init|=
name|filename
operator|.
name|startsWith
argument_list|(
name|citeKey
argument_list|)
decl_stmt|;
if|if
condition|(
name|startsWithKey
condition|)
block|{
comment|// The file name starts with the key, that's already a good start
comment|// However, we do not want to match "JabRefa" for "JabRef" since this is probably a file belonging to another entry published in the same time / same name
name|char
name|charAfterKey
init|=
name|filename
operator|.
name|charAt
argument_list|(
name|citeKey
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|!
name|BibtexKeyGenerator
operator|.
name|APPENDIX_CHARACTERS
operator|.
name|contains
argument_list|(
name|Character
operator|.
name|toString
argument_list|(
name|charAfterKey
argument_list|)
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
comment|/**      * Returns a list of all files in the given directories which have one of the given extension.      */
DECL|method|findFilesByExtension (List<Path> directories, List<String> extensions)
specifier|private
name|Set
argument_list|<
name|Path
argument_list|>
name|findFilesByExtension
parameter_list|(
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
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|extensions
argument_list|,
literal|"Extensions must not be null!"
argument_list|)
expr_stmt|;
name|BiPredicate
argument_list|<
name|Path
argument_list|,
name|BasicFileAttributes
argument_list|>
name|isFileWithCorrectExtension
init|=
parameter_list|(
name|path
parameter_list|,
name|attributes
parameter_list|)
lambda|->
operator|!
name|Files
operator|.
name|isDirectory
argument_list|(
name|path
argument_list|)
operator|&&
name|extensions
operator|.
name|contains
argument_list|(
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|path
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|Path
argument_list|>
name|result
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
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
name|Files
operator|.
name|exists
argument_list|(
name|directory
argument_list|)
condition|)
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
name|directory
argument_list|,
name|Integer
operator|.
name|MAX_VALUE
argument_list|,
name|isFileWithCorrectExtension
argument_list|)
init|)
block|{
name|result
operator|.
name|addAll
argument_list|(
name|pathStream
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toSet
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UncheckedIOException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|IOException
argument_list|(
literal|"Problem in finding files"
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

