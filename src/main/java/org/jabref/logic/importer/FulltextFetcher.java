begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
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
name|net
operator|.
name|URL
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fetcher
operator|.
name|TrustLevel
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

begin_comment
comment|/**  * This interface is used for classes that try to resolve a full-text PDF url for a BibTex entry.  * Implementing classes should specialize on specific article sites.  * See e.g. @link{http://libguides.mit.edu/apis}.  */
end_comment

begin_interface
DECL|interface|FulltextFetcher
specifier|public
interface|interface
name|FulltextFetcher
block|{
comment|/**      * Tries to find a fulltext URL for a given BibTex entry.      *      * @param entry The Bibtex entry      * @return The fulltext PDF URL Optional, if found, or an empty Optional if not found.      * @throws NullPointerException if no BibTex entry is given      * @throws java.io.IOException if an IO operation has failed      * @throws FetcherException if a fetcher specific error occurred      */
DECL|method|findFullText (BibEntry entry)
name|Optional
argument_list|<
name|URL
argument_list|>
name|findFullText
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|IOException
throws|,
name|FetcherException
function_decl|;
comment|/**      * Returns the level of trust for this fetcher.      * We distinguish between publishers and meta search engines for example.      *      * @return The trust level of the fetcher, the higher the better      */
DECL|method|getTrustLevel ()
specifier|default
name|TrustLevel
name|getTrustLevel
parameter_list|()
block|{
return|return
name|TrustLevel
operator|.
name|UNKNOWN
return|;
block|}
block|}
end_interface

end_unit

