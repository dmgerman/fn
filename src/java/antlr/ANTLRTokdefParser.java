begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/*  * ANTLR-generated file resulting from grammar tokdef.g  *   * Terence Parr, MageLang Institute  * ANTLR Version 2.7.0a2; 1989-1999  */
end_comment

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
name|TokenBuffer
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

begin_class
DECL|class|ANTLRTokdefParser
specifier|public
class|class
name|ANTLRTokdefParser
extends|extends
name|antlr
operator|.
name|LLkParser
implements|implements
name|ANTLRTokdefParserTokenTypes
block|{
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
literal|"ID"
block|,
literal|"STRING"
block|,
literal|"ASSIGN"
block|,
literal|"LPAREN"
block|,
literal|"RPAREN"
block|,
literal|"INT"
block|,
literal|"WS"
block|,
literal|"SL_COMMENT"
block|,
literal|"ML_COMMENT"
block|,
literal|"ESC"
block|,
literal|"DIGIT"
block|,
literal|"XDIGIT"
block|,
literal|"VOCAB"
block|}
decl_stmt|;
DECL|field|_tokenSet_0_data_
specifier|private
specifier|static
specifier|final
name|long
name|_tokenSet_0_data_
index|[]
init|=
block|{
literal|2L
block|,
literal|0L
block|}
decl_stmt|;
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
name|_tokenSet_0_data_
argument_list|)
decl_stmt|;
DECL|field|_tokenSet_1_data_
specifier|private
specifier|static
specifier|final
name|long
name|_tokenSet_1_data_
index|[]
init|=
block|{
literal|50L
block|,
literal|0L
block|}
decl_stmt|;
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
name|_tokenSet_1_data_
argument_list|)
decl_stmt|;
DECL|method|ANTLRTokdefParser (ParserSharedInputState state)
specifier|public
name|ANTLRTokdefParser
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
block|}
DECL|method|ANTLRTokdefParser (TokenBuffer tokenBuf)
specifier|public
name|ANTLRTokdefParser
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
DECL|method|ANTLRTokdefParser (TokenBuffer tokenBuf, int k)
specifier|protected
name|ANTLRTokdefParser
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
block|}
DECL|method|ANTLRTokdefParser (TokenStream lexer)
specifier|public
name|ANTLRTokdefParser
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
DECL|method|ANTLRTokdefParser (TokenStream lexer, int k)
specifier|protected
name|ANTLRTokdefParser
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
block|}
DECL|method|file ( ImportVocabTokenManager tm )
specifier|public
specifier|final
name|void
name|file
parameter_list|(
name|ImportVocabTokenManager
name|tm
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|Token
name|name
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|// for error handling
name|name
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|ID
argument_list|)
expr_stmt|;
block|{
name|_loop3
label|:
do|do
block|{
if|if
condition|(
operator|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|ID
operator|||
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|STRING
operator|)
condition|)
block|{
name|line
argument_list|(
name|tm
argument_list|)
expr_stmt|;
block|}
else|else
block|{
break|break
name|_loop3
break|;
block|}
block|}
do|while
condition|(
literal|true
condition|)
do|;
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
name|consume
argument_list|()
expr_stmt|;
name|consumeUntil
argument_list|(
name|_tokenSet_0
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|line ( ImportVocabTokenManager tm )
specifier|public
specifier|final
name|void
name|line
parameter_list|(
name|ImportVocabTokenManager
name|tm
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|TokenStreamException
block|{
name|Token
name|s1
init|=
literal|null
decl_stmt|;
name|Token
name|lab
init|=
literal|null
decl_stmt|;
name|Token
name|s2
init|=
literal|null
decl_stmt|;
name|Token
name|id
init|=
literal|null
decl_stmt|;
name|Token
name|para
init|=
literal|null
decl_stmt|;
name|Token
name|id2
init|=
literal|null
decl_stmt|;
name|Token
name|i
init|=
literal|null
decl_stmt|;
name|Token
name|t
init|=
literal|null
decl_stmt|;
name|Token
name|s
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|// for error handling
block|{
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
name|s1
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|STRING
argument_list|)
expr_stmt|;
name|s
operator|=
name|s1
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
name|ID
operator|)
operator|&&
operator|(
name|LA
argument_list|(
literal|2
argument_list|)
operator|==
name|ASSIGN
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
name|lab
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|ID
argument_list|)
expr_stmt|;
name|t
operator|=
name|lab
expr_stmt|;
name|match
argument_list|(
name|ASSIGN
argument_list|)
expr_stmt|;
name|s2
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|STRING
argument_list|)
expr_stmt|;
name|s
operator|=
name|s2
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
name|ID
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
name|id
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|ID
argument_list|)
expr_stmt|;
name|t
operator|=
name|id
expr_stmt|;
name|match
argument_list|(
name|LPAREN
argument_list|)
expr_stmt|;
name|para
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|STRING
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|RPAREN
argument_list|)
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
name|ID
operator|)
operator|&&
operator|(
name|LA
argument_list|(
literal|2
argument_list|)
operator|==
name|ASSIGN
operator|)
operator|&&
operator|(
name|LA
argument_list|(
literal|3
argument_list|)
operator|==
name|INT
operator|)
condition|)
block|{
name|id2
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|ID
argument_list|)
expr_stmt|;
name|t
operator|=
name|id2
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
name|match
argument_list|(
name|ASSIGN
argument_list|)
expr_stmt|;
name|i
operator|=
name|LT
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|match
argument_list|(
name|INT
argument_list|)
expr_stmt|;
name|Integer
name|value
init|=
name|Integer
operator|.
name|valueOf
argument_list|(
name|i
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
comment|// if literal found, define as a string literal
if|if
condition|(
name|s
operator|!=
literal|null
condition|)
block|{
name|tm
operator|.
name|define
argument_list|(
name|s
operator|.
name|getText
argument_list|()
argument_list|,
name|value
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
comment|// if label, then label the string and map label to token symbol also
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
name|StringLiteralSymbol
name|sl
init|=
operator|(
name|StringLiteralSymbol
operator|)
name|tm
operator|.
name|getTokenSymbol
argument_list|(
name|s
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|sl
operator|.
name|setLabel
argument_list|(
name|t
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|tm
operator|.
name|mapToTokenSymbol
argument_list|(
name|t
operator|.
name|getText
argument_list|()
argument_list|,
name|sl
argument_list|)
expr_stmt|;
block|}
block|}
comment|// define token (not a literal)
elseif|else
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
name|tm
operator|.
name|define
argument_list|(
name|t
operator|.
name|getText
argument_list|()
argument_list|,
name|value
operator|.
name|intValue
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|para
operator|!=
literal|null
condition|)
block|{
name|TokenSymbol
name|ts
init|=
name|tm
operator|.
name|getTokenSymbol
argument_list|(
name|t
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|ts
operator|.
name|setParaphrase
argument_list|(
name|para
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
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
name|consume
argument_list|()
expr_stmt|;
name|consumeUntil
argument_list|(
name|_tokenSet_1
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

