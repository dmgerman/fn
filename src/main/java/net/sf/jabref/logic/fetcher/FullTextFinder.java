begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.fetcher
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|fetcher
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|Optional
import|;
end_import

begin_comment
comment|/**  * This interface is used for classes that try to resolve a full-text PDF url for a BibTex entry.  * Implementing classes should specialize on specific article sites.  * See e.g. @link{http://libguides.mit.edu/apis}.  */
end_comment

begin_interface
DECL|interface|FullTextFinder
specifier|public
interface|interface
name|FullTextFinder
block|{
comment|/**      * Tries to find a fulltext URL for a given BibTex entry.      *      * @param entry The Bibtex entry      * @return The fulltext PDF URL Optional, if found, or an empty Optional if not found.      * @throws NullPointerException if no BibTex entry is given      * @throws java.io.IOException      */
DECL|method|findFullText (BibtexEntry entry)
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullText
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
throws|throws
name|IOException
function_decl|;
block|}
end_interface

end_unit

