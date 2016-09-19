begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.search.rules
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|rules
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
name|BibEntry
import|;
end_import

begin_interface
DECL|interface|SearchRule
specifier|public
interface|interface
name|SearchRule
block|{
DECL|method|applyRule (String query, BibEntry bibEntry)
name|boolean
name|applyRule
parameter_list|(
name|String
name|query
parameter_list|,
name|BibEntry
name|bibEntry
parameter_list|)
function_decl|;
DECL|method|validateSearchStrings (String query)
name|boolean
name|validateSearchStrings
parameter_list|(
name|String
name|query
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

