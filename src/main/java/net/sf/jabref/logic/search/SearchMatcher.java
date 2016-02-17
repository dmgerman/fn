begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
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

