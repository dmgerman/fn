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
name|identifier
operator|.
name|Identifier
import|;
end_import

begin_comment
comment|/**  * Looks for article identifier based on already present bibliographic information.  */
end_comment

begin_interface
DECL|interface|IdFetcher
specifier|public
interface|interface
name|IdFetcher
parameter_list|<
name|T
extends|extends
name|Identifier
parameter_list|>
extends|extends
name|WebFetcher
block|{
comment|/**      * Looks for an identifier based on the information stored in the given {@link BibEntry}.      *      * @param entry the {@link BibEntry} for which an identifier should be found      * @return the identifier (if an ID was found, otherwise an empty {@link Optional})      */
DECL|method|findIdentifier (BibEntry entry)
name|Optional
argument_list|<
name|T
argument_list|>
name|findIdentifier
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
throws|throws
name|FetcherException
function_decl|;
comment|/**      * Returns the name of the identifier that is returned by this fetcher.      */
DECL|method|getIdentifierName ()
name|String
name|getIdentifierName
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

