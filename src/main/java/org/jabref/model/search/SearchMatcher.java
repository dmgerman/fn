begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.search
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
package|;
end_package

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
annotation|@
name|FunctionalInterface
DECL|interface|SearchMatcher
specifier|public
interface|interface
name|SearchMatcher
block|{
DECL|method|isMatch (BibEntry entry)
name|boolean
name|isMatch
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

