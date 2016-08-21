begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.search.matchers
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
operator|.
name|matchers
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
name|logic
operator|.
name|search
operator|.
name|SearchMatcher
import|;
end_import

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

begin_comment
comment|/**  * Subclass of MatcherSet that ANDs or ORs between its rules, returning 0 or  * 1.  */
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
comment|// Then an AND rule demands that score == number of rules
return|return
name|score
operator|==
name|matchers
operator|.
name|size
argument_list|()
return|;
block|}
block|}
end_class

end_unit

