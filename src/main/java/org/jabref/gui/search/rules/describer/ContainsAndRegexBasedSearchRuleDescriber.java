begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.search.rules.describer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|TextFlow
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|TooltipTextUtil
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|rules
operator|.
name|SentenceAnalyzer
import|;
end_import

begin_class
DECL|class|ContainsAndRegexBasedSearchRuleDescriber
specifier|public
class|class
name|ContainsAndRegexBasedSearchRuleDescriber
implements|implements
name|SearchDescriber
block|{
DECL|field|regExp
specifier|private
specifier|final
name|boolean
name|regExp
decl_stmt|;
DECL|field|caseSensitive
specifier|private
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|query
specifier|private
specifier|final
name|String
name|query
decl_stmt|;
DECL|method|ContainsAndRegexBasedSearchRuleDescriber (boolean caseSensitive, boolean regExp, String query)
specifier|public
name|ContainsAndRegexBasedSearchRuleDescriber
parameter_list|(
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|,
name|String
name|query
parameter_list|)
block|{
name|this
operator|.
name|caseSensitive
operator|=
name|caseSensitive
expr_stmt|;
name|this
operator|.
name|regExp
operator|=
name|regExp
expr_stmt|;
name|this
operator|.
name|query
operator|=
name|query
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|TextFlow
name|getDescription
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|words
init|=
operator|new
name|SentenceAnalyzer
argument_list|(
name|query
argument_list|)
operator|.
name|getWords
argument_list|()
decl_stmt|;
name|String
name|firstWord
init|=
name|words
operator|.
name|isEmpty
argument_list|()
condition|?
literal|""
else|:
name|words
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|String
name|temp
init|=
name|regExp
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"This search contains entries in which any field contains the regular expression<b>%0</b>"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"This search contains entries in which any field contains the term<b>%0</b>"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Text
argument_list|>
name|textList
init|=
name|TooltipTextUtil
operator|.
name|formatToTexts
argument_list|(
name|temp
argument_list|,
operator|new
name|TooltipTextUtil
operator|.
name|TextReplacement
argument_list|(
literal|"<b>%0</b>"
argument_list|,
name|firstWord
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|words
operator|.
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|unprocessedWords
init|=
name|words
operator|.
name|subList
argument_list|(
literal|1
argument_list|,
name|words
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|word
range|:
name|unprocessedWords
control|)
block|{
name|textList
operator|.
name|add
argument_list|(
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|" %s "
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"and"
argument_list|)
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|NORMAL
argument_list|)
argument_list|)
expr_stmt|;
name|textList
operator|.
name|add
argument_list|(
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
name|word
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|BOLD
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|textList
operator|.
name|add
argument_list|(
name|getCaseSensitiveDescription
argument_list|()
argument_list|)
expr_stmt|;
name|TextFlow
name|searchDescription
init|=
operator|new
name|TextFlow
argument_list|()
decl_stmt|;
name|searchDescription
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|textList
argument_list|)
expr_stmt|;
return|return
name|searchDescription
return|;
block|}
DECL|method|getCaseSensitiveDescription ()
specifier|private
name|Text
name|getCaseSensitiveDescription
parameter_list|()
block|{
if|if
condition|(
name|caseSensitive
condition|)
block|{
return|return
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|" (%s). "
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"case sensitive"
argument_list|)
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|NORMAL
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|TooltipTextUtil
operator|.
name|createText
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|" (%s). "
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"case insensitive"
argument_list|)
argument_list|)
argument_list|,
name|TooltipTextUtil
operator|.
name|TextType
operator|.
name|NORMAL
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

