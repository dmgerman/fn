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
name|search
operator|.
name|SearchBaseVisitor
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
name|search
operator|.
name|SearchParser
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
name|util
operator|.
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|antlr
operator|.
name|v4
operator|.
name|runtime
operator|.
name|tree
operator|.
name|ParseTree
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
import|;
end_import

begin_class
DECL|class|GrammarBasedSearchRuleDescriber
specifier|public
class|class
name|GrammarBasedSearchRuleDescriber
implements|implements
name|SearchDescriber
block|{
DECL|field|caseSensitive
specifier|private
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|regExp
specifier|private
specifier|final
name|boolean
name|regExp
decl_stmt|;
DECL|field|parseTree
specifier|private
specifier|final
name|ParseTree
name|parseTree
decl_stmt|;
DECL|method|GrammarBasedSearchRuleDescriber (boolean caseSensitive, boolean regExp, ParseTree parseTree)
specifier|public
name|GrammarBasedSearchRuleDescriber
parameter_list|(
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regExp
parameter_list|,
name|ParseTree
name|parseTree
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
name|parseTree
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|parseTree
argument_list|)
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
name|StringBuilder
name|stringBuilder
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
comment|// describe advanced search expression
name|stringBuilder
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"This search contains entries in which"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|' '
argument_list|)
operator|.
name|append
argument_list|(
operator|new
name|SearchBaseVisitor
argument_list|<
name|String
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|visitStart
parameter_list|(
name|SearchParser
operator|.
name|StartContext
name|context
parameter_list|)
block|{
return|return
name|visit
argument_list|(
name|context
operator|.
name|expression
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|visitUnaryExpression
parameter_list|(
name|SearchParser
operator|.
name|UnaryExpressionContext
name|context
parameter_list|)
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s %s"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"not"
argument_list|)
argument_list|,
name|visit
argument_list|(
name|context
operator|.
name|expression
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|visitParenExpression
parameter_list|(
name|SearchParser
operator|.
name|ParenExpressionContext
name|context
parameter_list|)
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"%s"
argument_list|,
name|context
operator|.
name|expression
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|visitBinaryExpression
parameter_list|(
name|SearchParser
operator|.
name|BinaryExpressionContext
name|context
parameter_list|)
block|{
if|if
condition|(
literal|"AND"
operator|.
name|equalsIgnoreCase
argument_list|(
name|context
operator|.
name|operator
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"(%s %s %s)"
argument_list|,
name|visit
argument_list|(
name|context
operator|.
name|left
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"and"
argument_list|)
argument_list|,
name|visit
argument_list|(
name|context
operator|.
name|right
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|String
operator|.
name|format
argument_list|(
literal|"(%s %s %s)"
argument_list|,
name|visit
argument_list|(
name|context
operator|.
name|left
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"or"
argument_list|)
argument_list|,
name|visit
argument_list|(
name|context
operator|.
name|right
argument_list|)
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|visitComparison
parameter_list|(
name|SearchParser
operator|.
name|ComparisonContext
name|context
parameter_list|)
block|{
specifier|final
name|String
name|field
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|context
operator|.
name|left
operator|.
name|getText
argument_list|()
argument_list|,
literal|'"'
argument_list|)
decl_stmt|;
specifier|final
name|String
name|value
init|=
name|StringUtil
operator|.
name|unquote
argument_list|(
name|context
operator|.
name|right
operator|.
name|getText
argument_list|()
argument_list|,
literal|'"'
argument_list|)
decl_stmt|;
specifier|final
name|GrammarBasedSearchRule
operator|.
name|ComparisonOperator
name|operator
init|=
name|GrammarBasedSearchRule
operator|.
name|ComparisonOperator
operator|.
name|build
argument_list|(
name|context
operator|.
name|operator
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|boolean
name|regExpFieldSpec
init|=
operator|!
name|Pattern
operator|.
name|matches
argument_list|(
literal|"\\w+"
argument_list|,
name|field
argument_list|)
decl_stmt|;
specifier|final
name|String
name|termQuoted
init|=
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|value
argument_list|)
decl_stmt|;
specifier|final
name|String
name|fieldSpecQuoted
init|=
name|regExpFieldSpec
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"any field that matches the regular expression<b>%0</b>"
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|field
argument_list|)
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"the field<b>%0</b>"
argument_list|,
name|StringUtil
operator|.
name|quoteForHTML
argument_list|(
name|field
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|operator
operator|==
name|GrammarBasedSearchRule
operator|.
name|ComparisonOperator
operator|.
name|CONTAINS
condition|)
block|{
if|if
condition|(
name|regExp
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 contains the regular expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
block|}
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 contains the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|operator
operator|==
name|GrammarBasedSearchRule
operator|.
name|ComparisonOperator
operator|.
name|EXACT
condition|)
block|{
if|if
condition|(
name|regExp
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 matches the regular expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
block|}
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 matches the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|operator
operator|==
name|GrammarBasedSearchRule
operator|.
name|ComparisonOperator
operator|.
name|DOES_NOT_CONTAIN
condition|)
block|{
if|if
condition|(
name|regExp
condition|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 doesn't contain the regular expression<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
block|}
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 doesn't contain the term<b>%1</b>"
argument_list|,
name|fieldSpecQuoted
argument_list|,
name|termQuoted
argument_list|)
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"CANNOT HAPPEN!"
argument_list|)
throw|;
block|}
block|}
block|}
operator|.
name|visit
argument_list|(
name|parseTree
argument_list|)
argument_list|)
expr_stmt|;
name|stringBuilder
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
operator|.
name|append
argument_list|(
name|caseSensitive
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"The search is case sensitive."
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"The search is case insensitive."
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|stringBuilder
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

