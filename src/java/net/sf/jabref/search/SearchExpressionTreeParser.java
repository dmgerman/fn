begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_comment
comment|// $ANTLR : "TreeParser.g" -> "SearchExpressionTreeParser.java"$
end_comment

begin_package
DECL|package|net.sf.jabref.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
package|;
end_package

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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|PatternSyntaxException
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
name|export
operator|.
name|layout
operator|.
name|format
operator|.
name|RemoveLatexCommands
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|MismatchedTokenException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|NoViableAltException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|RecognitionException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|AST
import|;
end_import

begin_class
annotation|@
name|SuppressWarnings
argument_list|(
block|{
literal|"unused"
block|}
argument_list|)
DECL|class|SearchExpressionTreeParser
specifier|public
class|class
name|SearchExpressionTreeParser
extends|extends
name|antlr
operator|.
name|TreeParser
implements|implements
name|SearchExpressionTreeParserTokenTypes
block|{
comment|// Formatter used on every field before searching. Removes Latex commands that
comment|// may interfere with the search:
DECL|field|removeLatexCommands
specifier|static
name|RemoveLatexCommands
name|removeLatexCommands
init|=
operator|new
name|RemoveLatexCommands
argument_list|()
decl_stmt|;
DECL|field|MATCH_EXACT
specifier|private
specifier|static
specifier|final
name|int
name|MATCH_EXACT
init|=
literal|0
decl_stmt|;
DECL|field|MATCH_CONTAINS
specifier|private
specifier|static
specifier|final
name|int
name|MATCH_CONTAINS
init|=
literal|1
decl_stmt|;
DECL|field|MATCH_DOES_NOT_CONTAIN
specifier|private
specifier|static
specifier|final
name|int
name|MATCH_DOES_NOT_CONTAIN
init|=
literal|2
decl_stmt|;
DECL|field|bibtexEntry
specifier|private
name|BibtexEntry
name|bibtexEntry
decl_stmt|;
DECL|field|searchKeys
specifier|private
name|Object
index|[]
name|searchKeys
decl_stmt|;
DECL|field|PSEUDOFIELD_TYPE
specifier|private
specifier|static
specifier|final
name|int
name|PSEUDOFIELD_TYPE
init|=
literal|1
decl_stmt|;
DECL|method|apply (AST ast, BibtexEntry bibtexEntry)
specifier|public
name|int
name|apply
parameter_list|(
name|AST
name|ast
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
throws|throws
name|antlr
operator|.
name|RecognitionException
block|{
name|this
operator|.
name|bibtexEntry
operator|=
name|bibtexEntry
expr_stmt|;
comment|// specification of fields to search is done in the search expression itself
name|this
operator|.
name|searchKeys
operator|=
name|bibtexEntry
operator|.
name|getAllFields
argument_list|()
operator|.
name|toArray
argument_list|()
expr_stmt|;
return|return
name|tSearchExpression
argument_list|(
name|ast
argument_list|)
condition|?
literal|1
else|:
literal|0
return|;
block|}
DECL|method|SearchExpressionTreeParser ()
specifier|public
name|SearchExpressionTreeParser
parameter_list|()
block|{
name|tokenNames
operator|=
name|_tokenNames
expr_stmt|;
block|}
DECL|method|tSearchExpression (AST _t)
specifier|public
specifier|final
name|boolean
name|tSearchExpression
parameter_list|(
name|AST
name|_t
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|PatternSyntaxException
block|{
name|boolean
name|ret
init|=
literal|false
decl_stmt|;
name|AST
name|tSearchExpression_AST_in
init|=
operator|(
name|_t
operator|==
name|ASTNULL
operator|)
condition|?
literal|null
else|:
name|_t
decl_stmt|;
name|boolean
name|a
init|=
literal|false
decl_stmt|,
name|b
init|=
literal|false
decl_stmt|;
try|try
block|{
comment|// for error handling
if|if
condition|(
name|_t
operator|==
literal|null
condition|)
name|_t
operator|=
name|ASTNULL
expr_stmt|;
switch|switch
condition|(
name|_t
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|And
case|:
block|{
name|AST
name|__t87
init|=
name|_t
decl_stmt|;
name|AST
name|tmp1_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|And
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getFirstChild
argument_list|()
expr_stmt|;
name|a
operator|=
name|tSearchExpression
argument_list|(
name|_t
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_retTree
expr_stmt|;
block|{
if|if
condition|(
name|_t
operator|==
literal|null
condition|)
name|_t
operator|=
name|ASTNULL
expr_stmt|;
if|if
condition|(
operator|(
operator|(
operator|(
name|_t
operator|.
name|getType
argument_list|()
operator|>=
name|And
operator|&&
name|_t
operator|.
name|getType
argument_list|()
operator|<=
name|ExpressionSearch
operator|)
operator|)
operator|)
operator|&&
operator|(
name|a
operator|)
condition|)
block|{
name|b
operator|=
name|tSearchExpression
argument_list|(
name|_t
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_retTree
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
operator|(
name|_t
operator|.
name|getType
argument_list|()
operator|>=
name|LITERAL_and
operator|&&
name|_t
operator|.
name|getType
argument_list|()
operator|<=
name|ExpressionSearch
operator|)
operator|)
condition|)
block|{
name|AST
name|tmp2_AST_in
init|=
name|_t
decl_stmt|;
if|if
condition|(
name|_t
operator|==
literal|null
condition|)
throw|throw
operator|new
name|MismatchedTokenException
argument_list|()
throw|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|NoViableAltException
argument_list|(
name|_t
argument_list|)
throw|;
block|}
block|}
name|_t
operator|=
name|__t87
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|ret
operator|=
name|a
operator|&&
name|b
expr_stmt|;
break|break;
block|}
case|case
name|Or
case|:
block|{
name|AST
name|__t89
init|=
name|_t
decl_stmt|;
name|AST
name|tmp3_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|Or
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getFirstChild
argument_list|()
expr_stmt|;
name|a
operator|=
name|tSearchExpression
argument_list|(
name|_t
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_retTree
expr_stmt|;
block|{
if|if
condition|(
name|_t
operator|==
literal|null
condition|)
name|_t
operator|=
name|ASTNULL
expr_stmt|;
if|if
condition|(
operator|(
operator|(
operator|(
name|_t
operator|.
name|getType
argument_list|()
operator|>=
name|And
operator|&&
name|_t
operator|.
name|getType
argument_list|()
operator|<=
name|ExpressionSearch
operator|)
operator|)
operator|)
operator|&&
operator|(
operator|!
name|a
operator|)
condition|)
block|{
name|b
operator|=
name|tSearchExpression
argument_list|(
name|_t
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_retTree
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
operator|(
name|_t
operator|.
name|getType
argument_list|()
operator|>=
name|LITERAL_and
operator|&&
name|_t
operator|.
name|getType
argument_list|()
operator|<=
name|ExpressionSearch
operator|)
operator|)
condition|)
block|{
name|AST
name|tmp4_AST_in
init|=
name|_t
decl_stmt|;
if|if
condition|(
name|_t
operator|==
literal|null
condition|)
throw|throw
operator|new
name|MismatchedTokenException
argument_list|()
throw|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|NoViableAltException
argument_list|(
name|_t
argument_list|)
throw|;
block|}
block|}
name|_t
operator|=
name|__t89
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|ret
operator|=
name|a
operator|||
name|b
expr_stmt|;
break|break;
block|}
case|case
name|Not
case|:
block|{
name|AST
name|__t91
init|=
name|_t
decl_stmt|;
name|AST
name|tmp5_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|Not
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getFirstChild
argument_list|()
expr_stmt|;
name|a
operator|=
name|tSearchExpression
argument_list|(
name|_t
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_retTree
expr_stmt|;
name|_t
operator|=
name|__t91
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|ret
operator|=
operator|!
name|a
expr_stmt|;
break|break;
block|}
case|case
name|ExpressionSearch
case|:
block|{
name|ret
operator|=
name|tExpressionSearch
argument_list|(
name|_t
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_retTree
expr_stmt|;
break|break;
block|}
default|default:
block|{
throw|throw
operator|new
name|NoViableAltException
argument_list|(
name|_t
argument_list|)
throw|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|RecognitionException
name|ex
parameter_list|)
block|{
name|reportError
argument_list|(
name|ex
argument_list|)
expr_stmt|;
if|if
condition|(
name|_t
operator|!=
literal|null
condition|)
block|{
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
block|}
block|}
name|_retTree
operator|=
name|_t
expr_stmt|;
return|return
name|ret
return|;
block|}
DECL|method|tExpressionSearch (AST _t)
specifier|public
specifier|final
name|boolean
name|tExpressionSearch
parameter_list|(
name|AST
name|_t
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|PatternSyntaxException
block|{
name|boolean
name|ret
init|=
literal|false
decl_stmt|;
name|AST
name|var_f
init|=
literal|null
decl_stmt|;
name|AST
name|var_v
init|=
literal|null
decl_stmt|;
name|int
name|matchType
init|=
literal|0
decl_stmt|;
try|try
block|{
comment|// for error handling
name|AST
name|__t94
init|=
name|_t
decl_stmt|;
name|AST
name|tmp6_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|ExpressionSearch
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getFirstChild
argument_list|()
expr_stmt|;
name|var_f
operator|=
name|_t
expr_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|RegularExpression
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|matchType
operator|=
name|tSearchType
argument_list|(
name|_t
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_retTree
expr_stmt|;
name|var_v
operator|=
name|_t
expr_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|RegularExpression
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|Pattern
name|fieldSpec
init|=
operator|(
operator|(
name|RegExNode
operator|)
name|var_f
operator|)
operator|.
name|getPattern
argument_list|()
decl_stmt|;
name|Pattern
name|valueSpec
init|=
operator|(
operator|(
name|RegExNode
operator|)
name|var_v
operator|)
operator|.
name|getPattern
argument_list|()
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
name|PSEUDOFIELD_TYPE
operator|&&
operator|!
name|ret
condition|;
operator|++
name|i
control|)
block|{
name|String
name|content
decl_stmt|;
switch|switch
condition|(
name|i
operator|-
name|searchKeys
operator|.
name|length
operator|+
literal|1
condition|)
block|{
case|case
name|PSEUDOFIELD_TYPE
case|:
if|if
condition|(
operator|!
name|fieldSpec
operator|.
name|matcher
argument_list|(
literal|"entrytype"
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
continue|continue;
name|content
operator|=
name|bibtexEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
expr_stmt|;
break|break;
default|default:
comment|// regular field
if|if
condition|(
operator|!
name|fieldSpec
operator|.
name|matcher
argument_list|(
name|searchKeys
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|matches
argument_list|()
condition|)
continue|continue;
name|String
name|field
init|=
name|bibtexEntry
operator|.
name|getField
argument_list|(
name|searchKeys
index|[
name|i
index|]
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|content
operator|=
name|field
operator|!=
literal|null
condition|?
name|removeLatexCommands
operator|.
name|format
argument_list|(
name|field
argument_list|)
else|:
literal|null
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
continue|continue;
comment|// paranoia
name|Matcher
name|matcher
init|=
name|valueSpec
operator|.
name|matcher
argument_list|(
name|content
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|matchType
condition|)
block|{
case|case
name|MATCH_CONTAINS
case|:
name|ret
operator|=
name|matcher
operator|.
name|find
argument_list|()
expr_stmt|;
break|break;
case|case
name|MATCH_EXACT
case|:
name|ret
operator|=
name|matcher
operator|.
name|matches
argument_list|()
expr_stmt|;
break|break;
case|case
name|MATCH_DOES_NOT_CONTAIN
case|:
name|ret
operator|=
operator|!
name|matcher
operator|.
name|find
argument_list|()
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
name|noSuchField
operator|&&
name|matchType
operator|==
name|MATCH_DOES_NOT_CONTAIN
condition|)
name|ret
operator|=
literal|true
expr_stmt|;
comment|// special case
name|_t
operator|=
name|__t94
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|RecognitionException
name|ex
parameter_list|)
block|{
name|reportError
argument_list|(
name|ex
argument_list|)
expr_stmt|;
if|if
condition|(
name|_t
operator|!=
literal|null
condition|)
block|{
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
block|}
block|}
name|_retTree
operator|=
name|_t
expr_stmt|;
return|return
name|ret
return|;
block|}
DECL|method|tSearchType (AST _t)
specifier|public
specifier|final
name|int
name|tSearchType
parameter_list|(
name|AST
name|_t
parameter_list|)
throws|throws
name|RecognitionException
block|{
name|int
name|matchType
init|=
literal|0
decl_stmt|;
name|AST
name|tSearchType_AST_in
init|=
operator|(
name|_t
operator|==
name|ASTNULL
operator|)
condition|?
literal|null
else|:
name|_t
decl_stmt|;
try|try
block|{
comment|// for error handling
if|if
condition|(
name|_t
operator|==
literal|null
condition|)
name|_t
operator|=
name|ASTNULL
expr_stmt|;
switch|switch
condition|(
name|_t
operator|.
name|getType
argument_list|()
condition|)
block|{
case|case
name|LITERAL_contains
case|:
block|{
name|AST
name|tmp7_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|LITERAL_contains
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|matchType
operator|=
name|MATCH_CONTAINS
expr_stmt|;
break|break;
block|}
case|case
name|LITERAL_matches
case|:
block|{
name|AST
name|tmp8_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|LITERAL_matches
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|matchType
operator|=
name|MATCH_EXACT
expr_stmt|;
break|break;
block|}
case|case
name|EQUAL
case|:
block|{
name|AST
name|tmp9_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|EQUAL
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|matchType
operator|=
name|MATCH_CONTAINS
expr_stmt|;
break|break;
block|}
case|case
name|EEQUAL
case|:
block|{
name|AST
name|tmp10_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|EEQUAL
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|matchType
operator|=
name|MATCH_EXACT
expr_stmt|;
break|break;
block|}
case|case
name|NEQUAL
case|:
block|{
name|AST
name|tmp11_AST_in
init|=
name|_t
decl_stmt|;
name|match
argument_list|(
name|_t
argument_list|,
name|NEQUAL
argument_list|)
expr_stmt|;
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
name|matchType
operator|=
name|MATCH_DOES_NOT_CONTAIN
expr_stmt|;
break|break;
block|}
default|default:
block|{
throw|throw
operator|new
name|NoViableAltException
argument_list|(
name|_t
argument_list|)
throw|;
block|}
block|}
block|}
catch|catch
parameter_list|(
name|RecognitionException
name|ex
parameter_list|)
block|{
name|reportError
argument_list|(
name|ex
argument_list|)
expr_stmt|;
if|if
condition|(
name|_t
operator|!=
literal|null
condition|)
block|{
name|_t
operator|=
name|_t
operator|.
name|getNextSibling
argument_list|()
expr_stmt|;
block|}
block|}
name|_retTree
operator|=
name|_t
expr_stmt|;
return|return
name|matchType
return|;
block|}
DECL|field|_tokenNames
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|_tokenNames
init|=
block|{
literal|"<0>"
block|,
literal|"EOF"
block|,
literal|"<2>"
block|,
literal|"NULL_TREE_LOOKAHEAD"
block|,
literal|"\"and\""
block|,
literal|"\"or\""
block|,
literal|"\"not\""
block|,
literal|"\"contains\""
block|,
literal|"\"matches\""
block|,
literal|"white space"
block|,
literal|"'('"
block|,
literal|"')'"
block|,
literal|"'='"
block|,
literal|"'=='"
block|,
literal|"'!='"
block|,
literal|"'\\\"'"
block|,
literal|"a text literal"
block|,
literal|"a letter"
block|,
literal|"a field type"
block|,
literal|"RegularExpression"
block|,
literal|"And"
block|,
literal|"Or"
block|,
literal|"Not"
block|,
literal|"ExpressionSearch"
block|}
decl_stmt|;
block|}
end_class

end_unit

