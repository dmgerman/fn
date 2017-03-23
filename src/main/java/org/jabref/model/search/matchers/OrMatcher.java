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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|SearchMatcher
import|;
end_import

begin_comment
comment|/**  * Subclass of MatcherSet that ANDs or ORs between its rules, returning 0 or  * 1.  */
end_comment

begin_class
DECL|class|OrMatcher
specifier|public
class|class
name|OrMatcher
extends|extends
name|MatcherSet
block|{
annotation|@
name|Override
DECL|method|isMatch (BibEntry bibEntry)
specifier|public
name|boolean
name|isMatch
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
name|int
name|score
init|=
literal|0
decl_stmt|;
comment|// We let each rule add a maximum of 1 to the score.
for|for
control|(
name|SearchMatcher
name|rule
range|:
name|matchers
control|)
block|{
if|if
condition|(
name|rule
operator|.
name|isMatch
argument_list|(
name|bibEntry
argument_list|)
condition|)
block|{
name|score
operator|++
expr_stmt|;
block|}
block|}
comment|// OR rule demands score> 0.
return|return
name|score
operator|>
literal|0
return|;
block|}
block|}
end_class

end_unit

