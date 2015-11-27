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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|SearchRule
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
name|SearchRules
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
name|describer
operator|.
name|SearchDescriber
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
name|describer
operator|.
name|SearchDescribers
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_class
DECL|class|SearchQuery
specifier|public
class|class
name|SearchQuery
block|{
DECL|field|query
specifier|public
specifier|final
name|String
name|query
decl_stmt|;
DECL|field|caseSensitive
specifier|public
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|regularExpression
specifier|public
specifier|final
name|boolean
name|regularExpression
decl_stmt|;
DECL|field|rule
specifier|private
specifier|final
name|SearchRule
name|rule
decl_stmt|;
DECL|field|description
specifier|public
specifier|final
name|String
name|description
decl_stmt|;
DECL|method|SearchQuery (String query, boolean caseSensitive, boolean regularExpression)
specifier|public
name|SearchQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regularExpression
parameter_list|)
block|{
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|this
operator|.
name|regularExpression
operator|=
name|regularExpression
expr_stmt|;
name|this
operator|.
name|rule
operator|=
name|getSearchRule
argument_list|()
expr_stmt|;
name|this
operator|.
name|description
operator|=
name|getSearchDescriber
argument_list|()
operator|.
name|getDescription
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"\"%s\" (%s, %s)"
argument_list|,
name|query
argument_list|,
name|getCaseSensitiveDescription
argument_list|()
argument_list|,
name|getRegularExpressionDescription
argument_list|()
argument_list|)
return|;
block|}
DECL|method|isMatch (BibtexEntry entry)
specifier|public
name|boolean
name|isMatch
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|this
operator|.
name|rule
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|entry
argument_list|)
return|;
block|}
DECL|method|isValidQuery ()
specifier|public
name|boolean
name|isValidQuery
parameter_list|()
block|{
return|return
name|this
operator|.
name|rule
operator|.
name|validateSearchStrings
argument_list|(
name|query
argument_list|)
return|;
block|}
DECL|method|isContainsBasedSearch ()
specifier|public
name|boolean
name|isContainsBasedSearch
parameter_list|()
block|{
return|return
name|this
operator|.
name|rule
operator|instanceof
name|ContainBasedSearchRule
return|;
block|}
DECL|method|getSearchRule ()
specifier|private
name|SearchRule
name|getSearchRule
parameter_list|()
block|{
return|return
name|SearchRules
operator|.
name|getSearchRuleByQuery
argument_list|(
name|query
argument_list|,
name|caseSensitive
argument_list|,
name|regularExpression
argument_list|)
return|;
block|}
DECL|method|getSearchDescriber ()
specifier|private
name|SearchDescriber
name|getSearchDescriber
parameter_list|()
block|{
return|return
name|SearchDescribers
operator|.
name|getSearchDescriberFor
argument_list|(
name|getSearchRule
argument_list|()
argument_list|,
name|query
argument_list|)
return|;
block|}
DECL|method|getCaseSensitiveDescription ()
specifier|private
name|String
name|getCaseSensitiveDescription
parameter_list|()
block|{
if|if
condition|(
name|caseSensitive
condition|)
block|{
return|return
literal|"case sensitive"
return|;
block|}
else|else
block|{
return|return
literal|"case insensitive"
return|;
block|}
block|}
DECL|method|getRegularExpressionDescription ()
specifier|private
name|String
name|getRegularExpressionDescription
parameter_list|()
block|{
if|if
condition|(
name|regularExpression
condition|)
block|{
return|return
literal|"regular expression"
return|;
block|}
else|else
block|{
return|return
literal|"plain text"
return|;
block|}
block|}
DECL|method|isGrammarBasedSearch ()
specifier|public
name|boolean
name|isGrammarBasedSearch
parameter_list|()
block|{
return|return
name|this
operator|.
name|rule
operator|instanceof
name|GrammarBasedSearchRule
return|;
block|}
block|}
end_class

end_unit

