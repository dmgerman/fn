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
DECL|class|FileFinder
specifier|public
class|class
name|FileFinder
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
name|FileFinder
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|FileFinder ()
specifier|private
name|FileFinder
parameter_list|()
block|{     }
DECL|method|findFiles (List<String> extensions, List<File> directories)
specifier|public
specifier|static
name|Set
argument_list|<
name|Path
argument_list|>
name|findFiles
parameter_list|(
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
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|directories
argument_list|,
literal|"Directories must not be null!"
argument_list|)
expr_stmt|;
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
name|isDirectoryAndContainsExtension
init|=
parameter_list|(
name|path
parameter_list|,
name|attr
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
operator|.
name|toFile
argument_list|()
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
name|File
name|directory
range|:
name|directories
control|)
block|{
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|files
init|=
name|Files
operator|.
name|find
argument_list|(
name|directory
operator|.
name|toPath
argument_list|()
argument_list|,
name|Integer
operator|.
name|MAX_VALUE
argument_list|,
name|isDirectoryAndContainsExtension
argument_list|)
init|)
block|{
name|result
operator|.
name|addAll
argument_list|(
name|files
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
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem in finding files"
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
block|}
end_class

end_unit

