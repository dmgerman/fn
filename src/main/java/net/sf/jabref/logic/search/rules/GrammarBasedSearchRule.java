begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.search.rules
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
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
name|search
operator|.
name|SearchLexer
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
name|org
operator|.
name|antlr
operator|.
name|v4
operator|.
name|runtime
operator|.
name|*
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
name|misc
operator|.
name|ParseCancellationException
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
name|regex
operator|.
name|Matcher
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

begin_comment
comment|/**  * The search query must be specified in an expression that is acceptable by the Search.g4 grammar.  */
end_comment

begin_class
DECL|class|GrammarBasedSearchRule
specifier|public
class|class
name|GrammarBasedSearchRule
implements|implements
name|SearchRule
block|{
DECL|class|ThrowingErrorListener
specifier|public
specifier|static
class|class
name|ThrowingErrorListener
extends|extends
name|BaseErrorListener
block|{
DECL|field|INSTANCE
specifier|public
specifier|static
specifier|final
name|ThrowingErrorListener
name|INSTANCE
init|=
operator|new
name|ThrowingErrorListener
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|syntaxError (Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e)
specifier|public
name|void
name|syntaxError
parameter_list|(
name|Recognizer
argument_list|<
name|?
argument_list|,
name|?
argument_list|>
name|recognizer
parameter_list|,
name|Object
name|offendingSymbol
parameter_list|,
name|int
name|line
parameter_list|,
name|int
name|charPositionInLine
parameter_list|,
name|String
name|msg
parameter_list|,
name|RecognitionException
name|e
parameter_list|)
throws|throws
name|ParseCancellationException
block|{
throw|throw
operator|new
name|ParseCancellationException
argument_list|(
literal|"line "
operator|+
name|line
operator|+
literal|":"
operator|+
name|charPositionInLine
operator|+
literal|" "
operator|+
name|msg
argument_list|)
throw|;
block|}
block|}
DECL|field|caseSensitiveSearch
specifier|private
specifier|final
name|boolean
name|caseSensitiveSearch
decl_stmt|;
DECL|field|regExpSearch
specifier|private
specifier|final
name|boolean
name|regExpSearch
decl_stmt|;
DECL|field|tree
specifier|private
name|ParseTree
name|tree
decl_stmt|;
DECL|field|query
specifier|private
name|String
name|query
decl_stmt|;
DECL|method|GrammarBasedSearchRule (boolean caseSensitiveSearch, boolean regExpSearch)
specifier|public
name|GrammarBasedSearchRule
parameter_list|(
name|boolean
name|caseSensitiveSearch
parameter_list|,
name|boolean
name|regExpSearch
parameter_list|)
throws|throws
name|RecognitionException
block|{
name|this
operator|.
name|caseSensitiveSearch
operator|=
name|caseSensitiveSearch
expr_stmt|;
name|this
operator|.
name|regExpSearch
operator|=
name|regExpSearch
expr_stmt|;
block|}
DECL|method|isValid (boolean caseSensitive, boolean regExp, String query)
specifier|public
specifier|static
name|boolean
name|isValid
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
return|return
operator|new
name|GrammarBasedSearchRule
argument_list|(
name|caseSensitive
argument_list|,
name|regExp
argument_list|)
operator|.
name|validateSearchStrings
argument_list|(
name|query
argument_list|)
return|;
block|}
DECL|method|isCaseSensitiveSearch ()
specifier|public
name|boolean
name|isCaseSensitiveSearch
parameter_list|()
block|{
return|return
name|this
operator|.
name|caseSensitiveSearch
return|;
block|}
DECL|method|isRegExpSearch ()
specifier|public
name|boolean
name|isRegExpSearch
parameter_list|()
block|{
return|return
name|this
operator|.
name|regExpSearch
return|;
block|}
DECL|method|getTree ()
specifier|public
name|ParseTree
name|getTree
parameter_list|()
block|{
return|return
name|this
operator|.
name|tree
return|;
block|}
DECL|method|getQuery ()
specifier|public
name|String
name|getQuery
parameter_list|()
block|{
return|return
name|this
operator|.
name|query
return|;
block|}
DECL|method|init (String query)
specifier|private
name|void
name|init
parameter_list|(
name|String
name|query
parameter_list|)
throws|throws
name|ParseCancellationException
block|{
if|if
condition|(
name|this
operator|.
name|query
operator|!=
literal|null
operator|&&
name|this
operator|.
name|query
operator|.
name|equals
argument_list|(
name|query
argument_list|)
condition|)
block|{
return|return;
block|}
name|SearchLexer
name|lexer
init|=
operator|new
name|SearchLexer
argument_list|(
operator|new
name|ANTLRInputStream
argument_list|(
name|query
argument_list|)
argument_list|)
decl_stmt|;
name|lexer
operator|.
name|removeErrorListeners
argument_list|()
expr_stmt|;
comment|// no infos on file system
name|lexer
operator|.
name|addErrorListener
argument_list|(
name|ThrowingErrorListener
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
name|SearchParser
name|parser
init|=
operator|new
name|SearchParser
argument_list|(
operator|new
name|CommonTokenStream
argument_list|(
name|lexer
argument_list|)
argument_list|)
decl_stmt|;
name|parser
operator|.
name|removeErrorListeners
argument_list|()
expr_stmt|;
comment|// no infos on file system
name|parser
operator|.
name|addErrorListener
argument_list|(
name|ThrowingErrorListener
operator|.
name|INSTANCE
argument_list|)
expr_stmt|;
name|parser
operator|.
name|setErrorHandler
argument_list|(
operator|new
name|BailErrorStrategy
argument_list|()
argument_list|)
expr_stmt|;
comment|// ParseCancellationException on parse errors
name|tree
operator|=
name|parser
operator|.
name|start
argument_list|()
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
DECL|method|applyRule (String query, BibtexEntry bibtexEntry)
specifier|public
name|boolean
name|applyRule
parameter_list|(
name|String
name|query
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
return|return
operator|new
name|BibtexSearchVisitor
argument_list|(
name|caseSensitiveSearch
argument_list|,
name|regExpSearch
argument_list|,
name|bibtexEntry
argument_list|)
operator|.
name|visit
argument_list|(
name|tree
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|validateSearchStrings (String query)
specifier|public
name|boolean
name|validateSearchStrings
parameter_list|(
name|String
name|query
parameter_list|)
block|{
try|try
block|{
name|init
argument_list|(
name|query
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|ParseCancellationException
name|e
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|enum|ComparisonOperator
specifier|public
enum|enum
name|ComparisonOperator
block|{
DECL|enumConstant|EXACT
DECL|enumConstant|CONTAINS
DECL|enumConstant|DOES_NOT_CONTAIN
name|EXACT
block|,
name|CONTAINS
block|,
name|DOES_NOT_CONTAIN
block|;
DECL|method|build (String value)
specifier|public
specifier|static
name|ComparisonOperator
name|build
parameter_list|(
name|String
name|value
parameter_list|)
block|{
if|if
condition|(
name|value
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"CONTAINS"
argument_list|)
operator|||
name|value
operator|.
name|equals
argument_list|(
literal|"="
argument_list|)
condition|)
block|{
return|return
name|CONTAINS
return|;
block|}
elseif|else
if|if
condition|(
name|value
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"MATCHES"
argument_list|)
operator|||
name|value
operator|.
name|equals
argument_list|(
literal|"=="
argument_list|)
condition|)
block|{
return|return
name|EXACT
return|;
block|}
else|else
block|{
return|return
name|DOES_NOT_CONTAIN
return|;
block|}
block|}
block|}
DECL|class|Comparator
specifier|public
specifier|static
class|class
name|Comparator
block|{
DECL|field|operator
specifier|private
specifier|final
name|ComparisonOperator
name|operator
decl_stmt|;
DECL|field|fieldPattern
specifier|private
specifier|final
name|Pattern
name|fieldPattern
decl_stmt|;
DECL|field|valuePattern
specifier|private
specifier|final
name|Pattern
name|valuePattern
decl_stmt|;
DECL|method|Comparator (String field, String value, ComparisonOperator operator, boolean caseSensitive, boolean regex)
specifier|public
name|Comparator
parameter_list|(
name|String
name|field
parameter_list|,
name|String
name|value
parameter_list|,
name|ComparisonOperator
name|operator
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regex
parameter_list|)
block|{
name|this
operator|.
name|operator
operator|=
name|operator
expr_stmt|;
name|this
operator|.
name|fieldPattern
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
name|regex
condition|?
name|field
else|:
literal|"\\Q"
operator|+
name|field
operator|+
literal|"\\E"
argument_list|,
name|caseSensitive
condition|?
literal|0
else|:
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
expr_stmt|;
name|this
operator|.
name|valuePattern
operator|=
name|Pattern
operator|.
name|compile
argument_list|(
name|regex
condition|?
name|value
else|:
literal|"\\Q"
operator|+
name|value
operator|+
literal|"\\E"
argument_list|,
name|caseSensitive
condition|?
literal|0
else|:
name|Pattern
operator|.
name|CASE_INSENSITIVE
argument_list|)
expr_stmt|;
block|}
DECL|method|compare (BibtexEntry entry)
specifier|public
name|boolean
name|compare
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
comment|// specification of fields to search is done in the search expression itself
name|String
index|[]
name|searchKeys
init|=
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|entry
operator|.
name|getFieldNames
argument_list|()
operator|.
name|size
argument_list|()
index|]
argument_list|)
decl_stmt|;
name|boolean
name|noSuchField
init|=
literal|true
decl_stmt|;
comment|// this loop iterates over all regular keys, then over pseudo keys like "type"
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|searchKeys
operator|.
name|length
operator|+
literal|1
condition|;
name|i
operator|++
control|)
block|{
name|String
name|content
decl_stmt|;
if|if
condition|(
name|i
operator|-
name|searchKeys
operator|.
name|length
operator|==
literal|0
condition|)
block|{
comment|// PSEUDOFIELD_TYPE
if|if
condition|(
operator|!
name|fieldPattern
operator|.
name|matcher
argument_list|(
literal|"entrytype"
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|content
operator|=
name|entry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|String
name|searchKey
init|=
name|searchKeys
index|[
name|i
index|]
decl_stmt|;
if|if
condition|(
operator|!
name|fieldPattern
operator|.
name|matcher
argument_list|(
name|searchKey
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|content
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|searchKey
argument_list|)
expr_stmt|;
block|}
name|noSuchField
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|content
operator|==
literal|null
condition|)
block|{
continue|continue;
comment|// paranoia
block|}
if|if
condition|(
name|matchInField
argument_list|(
name|content
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
name|noSuchField
operator|&&
name|operator
operator|==
name|ComparisonOperator
operator|.
name|DOES_NOT_CONTAIN
return|;
block|}
DECL|method|matchInField (String content)
specifier|public
name|boolean
name|matchInField
parameter_list|(
name|String
name|content
parameter_list|)
block|{
name|Matcher
name|matcher
init|=
name|valuePattern
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
decl_stmt|;
if|if
condition|(
name|operator
operator|==
name|ComparisonOperator
operator|.
name|CONTAINS
condition|)
block|{
return|return
name|matcher
operator|.
name|find
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|operator
operator|==
name|ComparisonOperator
operator|.
name|EXACT
condition|)
block|{
return|return
name|matcher
operator|.
name|matches
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|operator
operator|==
name|ComparisonOperator
operator|.
name|DOES_NOT_CONTAIN
condition|)
block|{
return|return
operator|!
name|matcher
operator|.
name|find
argument_list|()
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|(
literal|"MUST NOT HAPPEN"
argument_list|)
throw|;
block|}
block|}
block|}
comment|/**      * Search results in boolean. It may be later on converted to an int.      */
DECL|class|BibtexSearchVisitor
specifier|static
class|class
name|BibtexSearchVisitor
extends|extends
name|SearchBaseVisitor
argument_list|<
name|Boolean
argument_list|>
block|{
DECL|field|caseSensitive
specifier|private
specifier|final
name|boolean
name|caseSensitive
decl_stmt|;
DECL|field|regex
specifier|private
specifier|final
name|boolean
name|regex
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibtexEntry
name|entry
decl_stmt|;
DECL|method|BibtexSearchVisitor (boolean caseSensitive, boolean regex, BibtexEntry bibtexEntry)
specifier|public
name|BibtexSearchVisitor
parameter_list|(
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regex
parameter_list|,
name|BibtexEntry
name|bibtexEntry
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
name|regex
operator|=
name|regex
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|bibtexEntry
expr_stmt|;
block|}
DECL|method|comparison (String field, ComparisonOperator operator, String value)
specifier|public
name|boolean
name|comparison
parameter_list|(
name|String
name|field
parameter_list|,
name|ComparisonOperator
name|operator
parameter_list|,
name|String
name|value
parameter_list|)
block|{
return|return
operator|new
name|Comparator
argument_list|(
name|field
argument_list|,
name|value
argument_list|,
name|operator
argument_list|,
name|caseSensitive
argument_list|,
name|regex
argument_list|)
operator|.
name|compare
argument_list|(
name|entry
argument_list|)
return|;
block|}
DECL|method|visitStart (SearchParser.StartContext ctx)
annotation|@
name|Override
specifier|public
name|Boolean
name|visitStart
parameter_list|(
name|SearchParser
operator|.
name|StartContext
name|ctx
parameter_list|)
block|{
return|return
name|visit
argument_list|(
name|ctx
operator|.
name|expression
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|visitComparison (SearchParser.ComparisonContext ctx)
specifier|public
name|Boolean
name|visitComparison
parameter_list|(
name|SearchParser
operator|.
name|ComparisonContext
name|ctx
parameter_list|)
block|{
return|return
name|comparison
argument_list|(
name|ctx
operator|.
name|left
operator|.
name|getText
argument_list|()
argument_list|,
name|ComparisonOperator
operator|.
name|build
argument_list|(
name|ctx
operator|.
name|operator
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|ctx
operator|.
name|right
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|visitUnaryExpression (SearchParser.UnaryExpressionContext ctx)
specifier|public
name|Boolean
name|visitUnaryExpression
parameter_list|(
name|SearchParser
operator|.
name|UnaryExpressionContext
name|ctx
parameter_list|)
block|{
return|return
operator|!
name|visit
argument_list|(
name|ctx
operator|.
name|expression
argument_list|()
argument_list|)
return|;
comment|// negate
block|}
annotation|@
name|Override
DECL|method|visitParenExpression (SearchParser.ParenExpressionContext ctx)
specifier|public
name|Boolean
name|visitParenExpression
parameter_list|(
name|SearchParser
operator|.
name|ParenExpressionContext
name|ctx
parameter_list|)
block|{
return|return
name|visit
argument_list|(
name|ctx
operator|.
name|expression
argument_list|()
argument_list|)
return|;
comment|// ignore parenthesis
block|}
annotation|@
name|Override
DECL|method|visitBinaryExpression (SearchParser.BinaryExpressionContext ctx)
specifier|public
name|Boolean
name|visitBinaryExpression
parameter_list|(
name|SearchParser
operator|.
name|BinaryExpressionContext
name|ctx
parameter_list|)
block|{
if|if
condition|(
name|ctx
operator|.
name|operator
operator|.
name|getText
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"AND"
argument_list|)
condition|)
block|{
return|return
name|visit
argument_list|(
name|ctx
operator|.
name|left
argument_list|)
operator|&&
name|visit
argument_list|(
name|ctx
operator|.
name|right
argument_list|)
return|;
comment|// and
block|}
else|else
block|{
return|return
name|visit
argument_list|(
name|ctx
operator|.
name|left
argument_list|)
operator|||
name|visit
argument_list|(
name|ctx
operator|.
name|right
argument_list|)
return|;
comment|// or
block|}
block|}
block|}
block|}
end_class

end_unit

