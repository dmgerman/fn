begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.search.matchers
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|matchers
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

begin_comment
comment|/**  * A set of matchers that returns true if all matcher match the given entry.  */
end_comment

begin_class
DECL|class|AndMatcher
specifier|public
class|class
name|AndMatcher
extends|extends
name|MatcherSet
block|{
annotation|@
name|Override
DECL|method|isMatch (BibEntry entry)
specifier|public
name|boolean
name|isMatch
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|matchers
operator|.
name|stream
argument_list|()
operator|.
name|allMatch
argument_list|(
name|rule
lambda|->
name|rule
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

