begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.search.describer
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
name|rules
operator|.
name|util
operator|.
name|SentenceAnalyzer
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
name|util
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|String
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
operator|!
name|words
operator|.
name|isEmpty
argument_list|()
condition|?
name|words
operator|.
name|get
argument_list|(
literal|0
argument_list|)
else|:
literal|""
decl_stmt|;
name|String
name|searchDescription
init|=
name|regExp
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains entries in which any field contains the regular expression<b>%0</b>"
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|firstWord
argument_list|)
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains entries in which any field contains the term<b>%0</b>"
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|firstWord
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
name|List
argument_list|<
name|String
argument_list|>
name|unprocessedWordsInHtmlFormat
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|word
range|:
name|unprocessedWords
control|)
block|{
name|unprocessedWordsInHtmlFormat
operator|.
name|add
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"<b>%s</b>"
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|word
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|String
name|andSeparator
init|=
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
decl_stmt|;
name|String
index|[]
name|unprocessedWordsInHtmlFormatArray
init|=
name|unprocessedWordsInHtmlFormat
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|unprocessedWordsInHtmlFormat
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|searchDescription
operator|+=
name|StringUtil
operator|.
name|join
argument_list|(
name|unprocessedWordsInHtmlFormatArray
argument_list|,
name|andSeparator
argument_list|)
expr_stmt|;
block|}
name|String
name|caseSensitiveDescription
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
name|genericDescription
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entries cannot be manually assigned to or removed from this group."
argument_list|)
operator|+
literal|"<p><br>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Hint%c To search specific fields only, enter for example%c<p><tt>author%esmith and title%eelectrical</tt>"
argument_list|)
decl_stmt|;
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s (%s). %s"
argument_list|,
name|searchDescription
argument_list|,
name|caseSensitiveDescription
argument_list|,
name|genericDescription
argument_list|)
return|;
block|}
block|}
end_class

end_unit

