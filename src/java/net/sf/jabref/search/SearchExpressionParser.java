begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// $ANTLR 2.7.4: "Parser.g" -> "SearchExpressionParser.java"$
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
name|antlr
operator|.
name|TokenBuffer
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|TokenStreamException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|TokenStreamIOException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|ANTLRException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|LLkParser
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|Token
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|TokenStream
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
name|NoViableAltException
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
name|SemanticException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|ParserSharedInputState
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|BitSet
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|ASTFactory
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|ASTPair
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|ASTArray
import|;
end_import

begin_class
DECL|class|SearchExpressionParser
specifier|public
class|class
name|SearchExpressionParser
extends|extends
name|antlr
operator|.
name|LLkParser
implements|implements
name|SearchExpressionParserTokenTypes
block|{
DECL|field|caseSensitive
specifier|public
name|boolean
name|caseSensitive
init|=
literal|false
decl_stmt|;
DECL|method|SearchExpressionParser (TokenBuffer tokenBuf, int k)
specifier|protected
name|SearchExpressionParser
parameter_list|(
name|TokenBuffer
name|tokenBuf
parameter_list|,
name|int
name|k
parameter_list|)
block|{
name|super
argument_list|(
name|tokenBuf
argument_list|,
name|k
argument_list|)
expr_stmt|;
name|tokenNames
operator|=
name|_tokenNames
expr_stmt|;
name|buildTokenTypeASTClassMap
argument_list|()
expr_stmt|;
name|astFactory
operator|=
operator|new
name|ASTFactory
argument_list|(
name|getTokenTypeToASTClassMap
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|SearchExpressionParser (TokenBuffer tokenBuf)
specifier|public
name|SearchExpressionParser
parameter_list|(
name|TokenBuffer
name|tokenBuf
parameter_list|)
block|{
name|this
argument_list|(
name|tokenBuf
argument_list|,
literal|3
argument_list|)
expr_stmt|;
block|}
DECL|method|SearchExpressionParser (TokenStream lexer, int k)
specifier|protected
name|SearchExpressionParser
parameter_list|(
name|TokenStream
name|lexer
parameter_list|,
name|int
name|k
parameter_list|)
block|{
name|super
argument_list|(
name|lexer
argument_list|,
name|k
argument_list|)
expr_stmt|;
name|tokenNames
operator|=
name|_tokenNames
expr_stmt|;
name|buildTokenTypeASTClassMap
argument_list|()
expr_stmt|;
name|astFactory
operator|=
operator|new
name|ASTFactory
argument_list|(
name|getTokenTypeToASTClassMap
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|SearchExpressionParser (TokenStream lexer)
specifier|public
name|SearchExpressionParser
parameter_list|(
name|TokenStream
name|lexer
parameter_list|)
block|{
name|this
argument_list|(
name|lexer
argument_list|,
literal|3
argument_list|)
expr_stmt|;
block|}
DECL|method|SearchExpressionParser (ParserSharedInputState state)
specifier|public
name|SearchExpressionParser
parameter_list|(
name|ParserSharedInputState
name|state
parameter_list|)
block|{
name|super
argument_list|(
name|state
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|tokenNames
operator|=
name|_tokenNames
expr_stmt|;
name|buildTokenTypeASTClassMap
argument_list|()
expr_stmt|;
name|astFactory
operator|=
operator|new
name|ASTFactory
argument_list|(
name|getTokenTypeToASTClassMap
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|quotedRegularExpression ( boolean caseSensitive )
specifier|public
specifier|final
name|void
name|quotedRegularExpression
parameter_list|(
name|boolean
name|caseSensitive
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|returnAST
operator|=
literal|null
expr_stmt|;
name|ASTPair
name|currentAST
init|=
operator|new
name|ASTPair
argument_list|()
decl_stmt|;
name|AST
name|quotedRegularExpression_AST
init|=
literal|null
decl_stmt|;
name|Token
name|var_s
init|=
literal|null
decl_stmt|;
name|AST
name|var_s_AST
init|=
literal|null
decl_stmt|;
name|var_s
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|var_s_AST
operator|=
name|astFactory
operator|.
name|create
argument_list|(
name|var_s
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|var_s_AST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|STRING
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|quotedRegularExpression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|quotedRegularExpression_AST
operator|=
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
operator|new
name|RegExNode
argument_list|(
name|RegularExpression
argument_list|,
name|var_s
operator|.
name|getText
argument_list|()
argument_list|,
name|caseSensitive
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|quotedRegularExpression_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|quotedRegularExpression_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|quotedRegularExpression_AST
operator|!=
literal|null
operator|&&
name|quotedRegularExpression_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|quotedRegularExpression_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|quotedRegularExpression_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|quotedRegularExpression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|returnAST
operator|=
name|quotedRegularExpression_AST
expr_stmt|;
block|}
DECL|method|simpleRegularExpression ( boolean caseSensitive )
specifier|public
specifier|final
name|void
name|simpleRegularExpression
parameter_list|(
name|boolean
name|caseSensitive
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|returnAST
operator|=
literal|null
expr_stmt|;
name|ASTPair
name|currentAST
init|=
operator|new
name|ASTPair
argument_list|()
decl_stmt|;
name|AST
name|simpleRegularExpression_AST
init|=
literal|null
decl_stmt|;
name|Token
name|var_s
init|=
literal|null
decl_stmt|;
name|AST
name|var_s_AST
init|=
literal|null
decl_stmt|;
name|var_s
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|var_s_AST
operator|=
name|astFactory
operator|.
name|create
argument_list|(
name|var_s
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|var_s_AST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|FIELDTYPE
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|simpleRegularExpression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|simpleRegularExpression_AST
operator|=
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
operator|new
name|RegExNode
argument_list|(
name|RegularExpression
argument_list|,
name|var_s
operator|.
name|getText
argument_list|()
argument_list|,
name|caseSensitive
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|simpleRegularExpression_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|simpleRegularExpression_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|simpleRegularExpression_AST
operator|!=
literal|null
operator|&&
name|simpleRegularExpression_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|simpleRegularExpression_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|simpleRegularExpression_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|simpleRegularExpression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|returnAST
operator|=
name|simpleRegularExpression_AST
expr_stmt|;
block|}
DECL|method|searchExpression ()
specifier|public
specifier|final
name|void
name|searchExpression
parameter_list|()
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|returnAST
operator|=
literal|null
expr_stmt|;
name|ASTPair
name|currentAST
init|=
operator|new
name|ASTPair
argument_list|()
decl_stmt|;
name|AST
name|searchExpression_AST
init|=
literal|null
decl_stmt|;
name|condition
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|AST
name|tmp1_AST
init|=
literal|null
decl_stmt|;
name|tmp1_AST
operator|=
name|astFactory
operator|.
name|create
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|tmp1_AST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|Token
operator|.
name|EOF_TYPE
argument_list|)
expr_stmt|;
name|searchExpression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|returnAST
operator|=
name|searchExpression_AST
expr_stmt|;
block|}
DECL|method|condition ()
specifier|public
specifier|final
name|void
name|condition
parameter_list|()
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|returnAST
operator|=
literal|null
expr_stmt|;
name|ASTPair
name|currentAST
init|=
operator|new
name|ASTPair
argument_list|()
decl_stmt|;
name|AST
name|condition_AST
init|=
literal|null
decl_stmt|;
name|boolean
name|synPredMatched6
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|(
operator|(
name|_tokenSet_0
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|_tokenSet_1
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|2
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|_tokenSet_1
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|3
argument_list|)
argument_list|)
operator|)
operator|)
condition|)
block|{
name|int
name|_m6
init|=
name|mark
argument_list|()
decl_stmt|;
name|synPredMatched6
operator|=
literal|true
expr_stmt|;
name|inputState
operator|.
name|guessing
operator|++
expr_stmt|;
try|try
block|{
block|{
name|expression
argument_list|()
expr_stmt|;
name|match
argument_list|(
name|LITERAL_and
argument_list|)
expr_stmt|;
name|condition
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|RecognitionException
name|pe
parameter_list|)
block|{
name|synPredMatched6
operator|=
literal|false
expr_stmt|;
block|}
name|rewind
argument_list|(
name|_m6
argument_list|)
expr_stmt|;
name|inputState
operator|.
name|guessing
operator|--
expr_stmt|;
block|}
if|if
condition|(
name|synPredMatched6
condition|)
block|{
name|expression
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|LITERAL_and
argument_list|)
expr_stmt|;
name|condition
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|condition_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|condition_AST
operator|=
operator|(
name|AST
operator|)
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
name|astFactory
operator|.
name|create
argument_list|(
name|And
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|condition_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|condition_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|condition_AST
operator|!=
literal|null
operator|&&
name|condition_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|condition_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|condition_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|condition_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
block|}
else|else
block|{
name|boolean
name|synPredMatched8
init|=
literal|false
decl_stmt|;
if|if
condition|(
operator|(
operator|(
name|_tokenSet_0
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|_tokenSet_1
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|2
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|_tokenSet_1
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|3
argument_list|)
argument_list|)
operator|)
operator|)
condition|)
block|{
name|int
name|_m8
init|=
name|mark
argument_list|()
decl_stmt|;
name|synPredMatched8
operator|=
literal|true
expr_stmt|;
name|inputState
operator|.
name|guessing
operator|++
expr_stmt|;
try|try
block|{
block|{
name|expression
argument_list|()
expr_stmt|;
name|match
argument_list|(
name|LITERAL_or
argument_list|)
expr_stmt|;
name|condition
argument_list|()
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|RecognitionException
name|pe
parameter_list|)
block|{
name|synPredMatched8
operator|=
literal|false
expr_stmt|;
block|}
name|rewind
argument_list|(
name|_m8
argument_list|)
expr_stmt|;
name|inputState
operator|.
name|guessing
operator|--
expr_stmt|;
block|}
if|if
condition|(
name|synPredMatched8
condition|)
block|{
name|expression
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|LITERAL_or
argument_list|)
expr_stmt|;
name|condition
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|condition_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|condition_AST
operator|=
operator|(
name|AST
operator|)
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
name|astFactory
operator|.
name|create
argument_list|(
name|Or
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|condition_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|condition_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|condition_AST
operator|!=
literal|null
operator|&&
name|condition_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|condition_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|condition_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|condition_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|_tokenSet_0
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|_tokenSet_1
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|2
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|_tokenSet_1
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|3
argument_list|)
argument_list|)
operator|)
condition|)
block|{
name|expression
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|condition_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|NoViableAltException
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|,
name|getFilename
argument_list|()
argument_list|)
throw|;
block|}
block|}
name|returnAST
operator|=
name|condition_AST
expr_stmt|;
block|}
DECL|method|expression ()
specifier|public
specifier|final
name|void
name|expression
parameter_list|()
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|returnAST
operator|=
literal|null
expr_stmt|;
name|ASTPair
name|currentAST
init|=
operator|new
name|ASTPair
argument_list|()
decl_stmt|;
name|AST
name|expression_AST
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
condition|)
block|{
case|case
name|STRING
case|:
case|case
name|FIELDTYPE
case|:
block|{
name|expressionSearch
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|expression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
break|break;
block|}
case|case
name|LPAREN
case|:
block|{
name|match
argument_list|(
name|LPAREN
argument_list|)
expr_stmt|;
name|condition
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|RPAREN
argument_list|)
expr_stmt|;
name|expression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
break|break;
block|}
default|default:
if|if
condition|(
operator|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|LITERAL_not
operator|)
operator|&&
operator|(
name|LA
argument_list|(
literal|2
argument_list|)
operator|==
name|STRING
operator|||
name|LA
argument_list|(
literal|2
argument_list|)
operator|==
name|FIELDTYPE
operator|)
condition|)
block|{
name|match
argument_list|(
name|LITERAL_not
argument_list|)
expr_stmt|;
name|expressionSearch
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|expression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|expression_AST
operator|=
operator|(
name|AST
operator|)
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
name|astFactory
operator|.
name|create
argument_list|(
name|Not
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|expression_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|expression_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|expression_AST
operator|!=
literal|null
operator|&&
name|expression_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|expression_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|expression_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|expression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|LITERAL_not
operator|)
operator|&&
operator|(
name|LA
argument_list|(
literal|2
argument_list|)
operator|==
name|LPAREN
operator|)
condition|)
block|{
name|match
argument_list|(
name|LITERAL_not
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|LPAREN
argument_list|)
expr_stmt|;
name|condition
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|RPAREN
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|expression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|expression_AST
operator|=
operator|(
name|AST
operator|)
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
name|astFactory
operator|.
name|create
argument_list|(
name|Not
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|expression_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|expression_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|expression_AST
operator|!=
literal|null
operator|&&
name|expression_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|expression_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|expression_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|expression_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|NoViableAltException
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|,
name|getFilename
argument_list|()
argument_list|)
throw|;
block|}
block|}
name|returnAST
operator|=
name|expression_AST
expr_stmt|;
block|}
DECL|method|expressionSearch ()
specifier|public
specifier|final
name|void
name|expressionSearch
parameter_list|()
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|returnAST
operator|=
literal|null
expr_stmt|;
name|ASTPair
name|currentAST
init|=
operator|new
name|ASTPair
argument_list|()
decl_stmt|;
name|AST
name|expressionSearch_AST
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|STRING
operator|)
condition|)
block|{
name|quotedRegularExpression
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|compareType
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|quotedRegularExpression
argument_list|(
name|caseSensitive
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
name|astFactory
operator|.
name|create
argument_list|(
name|ExpressionSearch
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|expressionSearch_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|expressionSearch_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|expressionSearch_AST
operator|!=
literal|null
operator|&&
name|expressionSearch_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|expressionSearch_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|expressionSearch_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|FIELDTYPE
operator|)
operator|&&
operator|(
name|_tokenSet_2
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|2
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|LA
argument_list|(
literal|3
argument_list|)
operator|==
name|STRING
operator|)
condition|)
block|{
name|simpleRegularExpression
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|compareType
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|quotedRegularExpression
argument_list|(
name|caseSensitive
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
name|astFactory
operator|.
name|create
argument_list|(
name|ExpressionSearch
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|expressionSearch_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|expressionSearch_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|expressionSearch_AST
operator|!=
literal|null
operator|&&
name|expressionSearch_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|expressionSearch_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|expressionSearch_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|FIELDTYPE
operator|)
operator|&&
operator|(
name|_tokenSet_2
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|2
argument_list|)
argument_list|)
operator|)
operator|&&
operator|(
name|LA
argument_list|(
literal|3
argument_list|)
operator|==
name|FIELDTYPE
operator|)
condition|)
block|{
name|simpleRegularExpression
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|compareType
argument_list|()
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
name|simpleRegularExpression
argument_list|(
name|caseSensitive
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|returnAST
argument_list|)
expr_stmt|;
if|if
condition|(
name|inputState
operator|.
name|guessing
operator|==
literal|0
condition|)
block|{
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|astFactory
operator|.
name|make
argument_list|(
operator|(
operator|new
name|ASTArray
argument_list|(
literal|2
argument_list|)
operator|)
operator|.
name|add
argument_list|(
name|astFactory
operator|.
name|create
argument_list|(
name|ExpressionSearch
argument_list|)
argument_list|)
operator|.
name|add
argument_list|(
name|expressionSearch_AST
argument_list|)
argument_list|)
expr_stmt|;
name|currentAST
operator|.
name|root
operator|=
name|expressionSearch_AST
expr_stmt|;
name|currentAST
operator|.
name|child
operator|=
name|expressionSearch_AST
operator|!=
literal|null
operator|&&
name|expressionSearch_AST
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|?
name|expressionSearch_AST
operator|.
name|getFirstChild
argument_list|()
else|:
name|expressionSearch_AST
expr_stmt|;
name|currentAST
operator|.
name|advanceChildToEnd
argument_list|()
expr_stmt|;
block|}
name|expressionSearch_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|NoViableAltException
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|,
name|getFilename
argument_list|()
argument_list|)
throw|;
block|}
name|returnAST
operator|=
name|expressionSearch_AST
expr_stmt|;
block|}
DECL|method|compareType ()
specifier|public
specifier|final
name|void
name|compareType
parameter_list|()
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|returnAST
operator|=
literal|null
expr_stmt|;
name|ASTPair
name|currentAST
init|=
operator|new
name|ASTPair
argument_list|()
decl_stmt|;
name|AST
name|compareType_AST
init|=
literal|null
decl_stmt|;
switch|switch
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
condition|)
block|{
case|case
name|LITERAL_contains
case|:
block|{
name|AST
name|tmp10_AST
init|=
literal|null
decl_stmt|;
name|tmp10_AST
operator|=
name|astFactory
operator|.
name|create
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|tmp10_AST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|LITERAL_contains
argument_list|)
expr_stmt|;
name|compareType_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
break|break;
block|}
case|case
name|LITERAL_matches
case|:
block|{
name|AST
name|tmp11_AST
init|=
literal|null
decl_stmt|;
name|tmp11_AST
operator|=
name|astFactory
operator|.
name|create
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|tmp11_AST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|LITERAL_matches
argument_list|)
expr_stmt|;
name|compareType_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
break|break;
block|}
case|case
name|EQUAL
case|:
block|{
name|AST
name|tmp12_AST
init|=
literal|null
decl_stmt|;
name|tmp12_AST
operator|=
name|astFactory
operator|.
name|create
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|tmp12_AST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|EQUAL
argument_list|)
expr_stmt|;
name|compareType_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
break|break;
block|}
case|case
name|EEQUAL
case|:
block|{
name|AST
name|tmp13_AST
init|=
literal|null
decl_stmt|;
name|tmp13_AST
operator|=
name|astFactory
operator|.
name|create
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|tmp13_AST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|EEQUAL
argument_list|)
expr_stmt|;
name|compareType_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
break|break;
block|}
case|case
name|NEQUAL
case|:
block|{
name|AST
name|tmp14_AST
init|=
literal|null
decl_stmt|;
name|tmp14_AST
operator|=
name|astFactory
operator|.
name|create
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|astFactory
operator|.
name|addASTChild
argument_list|(
name|currentAST
argument_list|,
name|tmp14_AST
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|NEQUAL
argument_list|)
expr_stmt|;
name|compareType_AST
operator|=
operator|(
name|AST
operator|)
name|currentAST
operator|.
name|root
expr_stmt|;
break|break;
block|}
default|default:
block|{
throw|throw
operator|new
name|NoViableAltException
argument_list|(
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|,
name|getFilename
argument_list|()
argument_list|)
throw|;
block|}
block|}
name|returnAST
operator|=
name|compareType_AST
expr_stmt|;
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
literal|"\"equals\""
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
block|,
literal|"LITERAL_matches"
block|}
decl_stmt|;
DECL|method|buildTokenTypeASTClassMap ()
specifier|protected
name|void
name|buildTokenTypeASTClassMap
parameter_list|()
block|{
name|tokenTypeToASTClassMap
operator|=
literal|null
expr_stmt|;
block|}
empty_stmt|;
DECL|method|mk_tokenSet_0 ()
specifier|private
specifier|static
specifier|final
name|long
index|[]
name|mk_tokenSet_0
parameter_list|()
block|{
name|long
index|[]
name|data
init|=
block|{
literal|328768L
block|,
literal|0L
block|}
decl_stmt|;
return|return
name|data
return|;
block|}
DECL|field|_tokenSet_0
specifier|public
specifier|static
specifier|final
name|BitSet
name|_tokenSet_0
init|=
operator|new
name|BitSet
argument_list|(
name|mk_tokenSet_0
argument_list|()
argument_list|)
decl_stmt|;
DECL|method|mk_tokenSet_1 ()
specifier|private
specifier|static
specifier|final
name|long
index|[]
name|mk_tokenSet_1
parameter_list|()
block|{
name|long
index|[]
name|data
init|=
block|{
literal|17134784L
block|,
literal|0L
block|}
decl_stmt|;
return|return
name|data
return|;
block|}
DECL|field|_tokenSet_1
specifier|public
specifier|static
specifier|final
name|BitSet
name|_tokenSet_1
init|=
operator|new
name|BitSet
argument_list|(
name|mk_tokenSet_1
argument_list|()
argument_list|)
decl_stmt|;
DECL|method|mk_tokenSet_2 ()
specifier|private
specifier|static
specifier|final
name|long
index|[]
name|mk_tokenSet_2
parameter_list|()
block|{
name|long
index|[]
name|data
init|=
block|{
literal|16806016L
block|,
literal|0L
block|}
decl_stmt|;
return|return
name|data
return|;
block|}
DECL|field|_tokenSet_2
specifier|public
specifier|static
specifier|final
name|BitSet
name|_tokenSet_2
init|=
operator|new
name|BitSet
argument_list|(
name|mk_tokenSet_2
argument_list|()
argument_list|)
decl_stmt|;
block|}
end_class

end_unit

