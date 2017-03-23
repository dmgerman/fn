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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * Searches web resources for bibliographic information based on an identifier.  */
end_comment

begin_interface
DECL|interface|IdBasedFetcher
specifier|public
interface|interface
name|IdBasedFetcher
extends|extends
name|WebFetcher
block|{
comment|/**      * Looks for bibliographic information associated to the given identifier.      *      * @param identifier a string which uniquely identifies the item      * @return a {@link BibEntry} containing the bibliographic information (or an empty optional if no data was found)      * @throws FetcherException      */
DECL|method|performSearchById (String identifier)
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|performSearchById
parameter_list|(
name|String
name|identifier
parameter_list|)
throws|throws
name|FetcherException
function_decl|;
block|}
end_interface

end_unit

