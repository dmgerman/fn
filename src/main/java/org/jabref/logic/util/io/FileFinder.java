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
name|util
operator|.
name|List
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

begin_interface
DECL|interface|FileFinder
specifier|public
interface|interface
name|FileFinder
block|{
comment|/**      * Finds all files in the given directories that are probably associated with the given entries and have one of the      * passed extensions.      *      * @param entry       The entry to search files for.      * @param directories The root directories to search.      * @param extensions  The extensions that are acceptable.      */
DECL|method|findAssociatedFiles (BibEntry entry, List<Path> directories, List<String> extensions)
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
function_decl|;
block|}
end_interface

end_unit

