begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
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
name|model
operator|.
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|KeywordGroup
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
name|groups
operator|.
name|SearchGroup
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_class
DECL|class|GroupDescriptions
specifier|public
class|class
name|GroupDescriptions
block|{
DECL|method|getDescriptionForPreview (String field, String expr, boolean caseSensitive, boolean regExp)
specifier|public
specifier|static
name|String
name|getDescriptionForPreview
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|expr
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|)
block|{
name|String
name|header
init|=
name|regExp
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains entries whose<b>%0</b> field contains the regular expression<b>%1</b>"
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains entries whose<b>%0</b> field contains the keyword<b>%1</b>"
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|caseSensitiveText
init|=
name|caseSensitive
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"case sensitive"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"case insensitive"
argument_list|)
decl_stmt|;
name|String
name|footer
init|=
name|regExp
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entries cannot be manually assigned to or removed from this group."
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Additionally, entries whose<b>%0</b> field does not contain "
operator|+
literal|"<b>%1</b> can be assigned manually to this group by selecting them "
operator|+
literal|"then using either drag and drop or the context menu. "
operator|+
literal|"This process adds the term<b>%1</b> to "
operator|+
literal|"each entry's<b>%0</b> field. "
operator|+
literal|"Entries can be removed manually from this group by selecting them "
operator|+
literal|"then using the context menu. "
operator|+
literal|"This process removes the term<b>%1</b> from "
operator|+
literal|"each entry's<b>%0</b> field."
argument_list|,
name|field
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|expr
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s (%s). %s"
argument_list|,
name|header
argument_list|,
name|caseSensitiveText
argument_list|,
name|footer
argument_list|)
return|;
block|}
DECL|method|getShortDescriptionKeywordGroup (KeywordGroup keywordGroup, boolean showDynamic)
specifier|public
specifier|static
name|String
name|getShortDescriptionKeywordGroup
parameter_list|(
name|KeywordGroup
name|keywordGroup
parameter_list|,
name|boolean
name|showDynamic
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|showDynamic
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|keywordGroup
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</i>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|keywordGroup
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</b> - "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"dynamic group"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|keywordGroup
operator|.
name|getSearchField
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</b> "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"contains"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|keywordGroup
operator|.
name|getSearchExpression
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</b>)"
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|keywordGroup
operator|.
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|INCLUDING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"includes subgroups"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|REFINING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"refines supergroup"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getDescriptionForPreview ()
specifier|public
specifier|static
name|String
name|getDescriptionForPreview
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains entries based on manual assignment. "
operator|+
literal|"Entries can be assigned to this group by selecting them "
operator|+
literal|"then using either drag and drop or the context menu. "
operator|+
literal|"Entries can be removed from this group by selecting them "
operator|+
literal|"then using the context menu."
argument_list|)
return|;
block|}
DECL|method|getShortDescriptionExplicitGroup (ExplicitGroup explicitGroup)
specifier|public
specifier|static
name|String
name|getShortDescriptionExplicitGroup
parameter_list|(
name|ExplicitGroup
name|explicitGroup
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
operator|.
name|append
argument_list|(
name|explicitGroup
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"</b> - "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"static group"
argument_list|)
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|explicitGroup
operator|.
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|INCLUDING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"includes subgroups"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|REFINING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"refines supergroup"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|getShortDescriptionAllEntriesGroup ()
specifier|public
specifier|static
name|String
name|getShortDescriptionAllEntriesGroup
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"<b>All Entries</b> (this group cannot be edited or removed)"
argument_list|)
return|;
block|}
DECL|method|getShortDescription (SearchGroup searchGroup, boolean showDynamic)
specifier|public
specifier|static
name|String
name|getShortDescription
parameter_list|(
name|SearchGroup
name|searchGroup
parameter_list|,
name|boolean
name|showDynamic
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|showDynamic
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|searchGroup
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</i>"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|searchGroup
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</b> - "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"dynamic group"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|" ("
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"search expression"
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<b>"
argument_list|)
operator|.
name|append
argument_list|(
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|searchGroup
operator|.
name|getSearchExpression
argument_list|()
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"</b>)"
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|searchGroup
operator|.
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|INCLUDING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"includes subgroups"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|REFINING
case|:
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"refines supergroup"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

