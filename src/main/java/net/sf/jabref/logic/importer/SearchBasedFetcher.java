begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer
package|package
name|net
operator|.
name|sf
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

begin_comment
comment|/**  * Searches web resources for bibliographic information based on a free-text query.  * May return multiple search hits.  */
end_comment

begin_interface
DECL|interface|SearchBasedFetcher
specifier|public
interface|interface
name|SearchBasedFetcher
extends|extends
name|WebFetcher
block|{
comment|/**      * Looks for hits which are matched by the given free-text query.      *      * @param query search string      * @return a list of {@link BibEntry}, which are matched by the query (may be empty)      */
DECL|method|performSearch (String query)
name|List
argument_list|<
name|BibEntry
argument_list|>
name|performSearch
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|FetcherException
function_decl|;
block|}
end_interface

end_unit

