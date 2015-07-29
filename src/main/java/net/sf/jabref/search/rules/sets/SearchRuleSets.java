begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.search.rules.sets
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
operator|.
name|rules
operator|.
name|sets
package|;
end_package

begin_class
DECL|class|SearchRuleSets
specifier|public
class|class
name|SearchRuleSets
block|{
DECL|enum|RuleSetType
specifier|public
enum|enum
name|RuleSetType
block|{
DECL|enumConstant|AND
name|AND
block|,
DECL|enumConstant|OR
name|OR
block|}
DECL|method|build (RuleSetType ruleSet)
specifier|public
specifier|static
name|SearchRuleSet
name|build
parameter_list|(
name|RuleSetType
name|ruleSet
parameter_list|)
block|{
if|if
condition|(
name|ruleSet
operator|==
name|RuleSetType
operator|.
name|AND
condition|)
block|{
return|return
operator|new
name|AndSearchRuleSet
argument_list|()
return|;
block|}
else|else
block|{
return|return
operator|new
name|OrSearchRuleSet
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

