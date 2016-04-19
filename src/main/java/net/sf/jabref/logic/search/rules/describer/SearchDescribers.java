begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.search.rules.describer
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
name|rules
operator|.
name|describer
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
name|rules
operator|.
name|ContainBasedSearchRule
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
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|GrammarBasedSearchRule
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
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|RegexBasedSearchRule
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
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|SearchRule
import|;
end_import

begin_class
DECL|class|SearchDescribers
specifier|public
class|class
name|SearchDescribers
block|{
comment|/**      * Get the search describer for a given search rule and a given search query.      *      * @param searchRule the rule that encodes the search logic      * @param query      the search query      * @return the search describer to turn the search into something human understandable      */
DECL|method|getSearchDescriberFor (SearchRule searchRule, String query)
specifier|public
specifier|static
name|SearchDescriber
name|getSearchDescriberFor
parameter_list|(
name|SearchRule
name|searchRule
parameter_list|,
name|String
name|query
parameter_list|)
block|{
if|if
condition|(
name|searchRule
operator|instanceof
name|GrammarBasedSearchRule
condition|)
block|{
name|GrammarBasedSearchRule
name|grammarBasedSearchRule
init|=
operator|(
name|GrammarBasedSearchRule
operator|)
name|searchRule
decl_stmt|;
return|return
operator|new
name|GrammarBasedSearchRuleDescriber
argument_list|(
name|grammarBasedSearchRule
operator|.
name|isCaseSensitiveSearch
argument_list|()
argument_list|,
name|grammarBasedSearchRule
operator|.
name|isRegExpSearch
argument_list|()
argument_list|,
name|grammarBasedSearchRule
operator|.
name|getTree
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|searchRule
operator|instanceof
name|ContainBasedSearchRule
condition|)
block|{
name|ContainBasedSearchRule
name|containBasedSearchRule
init|=
operator|(
name|ContainBasedSearchRule
operator|)
name|searchRule
decl_stmt|;
return|return
operator|new
name|ContainsAndRegexBasedSearchRuleDescriber
argument_list|(
name|containBasedSearchRule
operator|.
name|isCaseSensitive
argument_list|()
argument_list|,
literal|false
argument_list|,
name|query
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|searchRule
operator|instanceof
name|RegexBasedSearchRule
condition|)
block|{
name|RegexBasedSearchRule
name|regexBasedSearchRule
init|=
operator|(
name|RegexBasedSearchRule
operator|)
name|searchRule
decl_stmt|;
return|return
operator|new
name|ContainsAndRegexBasedSearchRuleDescriber
argument_list|(
name|regexBasedSearchRule
operator|.
name|isCaseSensitive
argument_list|()
argument_list|,
literal|true
argument_list|,
name|query
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"Cannot find a describer for searchRule "
operator|+
name|searchRule
operator|+
literal|" and query "
operator|+
name|query
argument_list|)
throw|;
block|}
block|}
block|}
end_class

end_unit

