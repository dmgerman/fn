begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// $ANTLR 2.7.4: "Lexer.g" -> "SearchExpressionLexer.java"$
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
name|io
operator|.
name|InputStream
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
name|TokenStreamRecognitionException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|CharStreamException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|CharStreamIOException
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
name|java
operator|.
name|io
operator|.
name|Reader
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
name|CharScanner
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|InputBuffer
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|ByteBuffer
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|CharBuffer
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
name|CommonToken
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
name|NoViableAltForCharException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|MismatchedCharException
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
name|ANTLRHashString
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|LexerSharedInputState
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
name|SemanticException
import|;
end_import

begin_class
DECL|class|SearchExpressionLexer
specifier|public
class|class
name|SearchExpressionLexer
extends|extends
name|antlr
operator|.
name|CharScanner
implements|implements
name|SearchExpressionLexerTokenTypes
implements|,
name|TokenStream
block|{
DECL|method|SearchExpressionLexer (InputStream in)
specifier|public
name|SearchExpressionLexer
parameter_list|(
name|InputStream
name|in
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|ByteBuffer
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|SearchExpressionLexer (Reader in)
specifier|public
name|SearchExpressionLexer
parameter_list|(
name|Reader
name|in
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|CharBuffer
argument_list|(
name|in
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|SearchExpressionLexer (InputBuffer ib)
specifier|public
name|SearchExpressionLexer
parameter_list|(
name|InputBuffer
name|ib
parameter_list|)
block|{
name|this
argument_list|(
operator|new
name|LexerSharedInputState
argument_list|(
name|ib
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|SearchExpressionLexer (LexerSharedInputState state)
specifier|public
name|SearchExpressionLexer
parameter_list|(
name|LexerSharedInputState
name|state
parameter_list|)
block|{
name|super
argument_list|(
name|state
argument_list|)
expr_stmt|;
name|caseSensitiveLiterals
operator|=
literal|false
expr_stmt|;
name|setCaseSensitive
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|literals
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
name|literals
operator|.
name|put
argument_list|(
operator|new
name|ANTLRHashString
argument_list|(
literal|"matches"
argument_list|,
name|this
argument_list|)
argument_list|,
operator|new
name|Integer
argument_list|(
literal|8
argument_list|)
argument_list|)
expr_stmt|;
name|literals
operator|.
name|put
argument_list|(
operator|new
name|ANTLRHashString
argument_list|(
literal|"or"
argument_list|,
name|this
argument_list|)
argument_list|,
operator|new
name|Integer
argument_list|(
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|literals
operator|.
name|put
argument_list|(
operator|new
name|ANTLRHashString
argument_list|(
literal|"and"
argument_list|,
name|this
argument_list|)
argument_list|,
operator|new
name|Integer
argument_list|(
literal|4
argument_list|)
argument_list|)
expr_stmt|;
name|literals
operator|.
name|put
argument_list|(
operator|new
name|ANTLRHashString
argument_list|(
literal|"not"
argument_list|,
name|this
argument_list|)
argument_list|,
operator|new
name|Integer
argument_list|(
literal|6
argument_list|)
argument_list|)
expr_stmt|;
name|literals
operator|.
name|put
argument_list|(
operator|new
name|ANTLRHashString
argument_list|(
literal|"contains"
argument_list|,
name|this
argument_list|)
argument_list|,
operator|new
name|Integer
argument_list|(
literal|7
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|nextToken ()
specifier|public
name|Token
name|nextToken
parameter_list|()
throws|throws
name|TokenStreamException
block|{
name|Token
name|theRetToken
init|=
literal|null
decl_stmt|;
name|tryAgain
label|:
for|for
control|(
init|;
condition|;
control|)
block|{
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_ttype
init|=
name|Token
operator|.
name|INVALID_TYPE
decl_stmt|;
name|resetText
argument_list|()
expr_stmt|;
try|try
block|{
comment|// for char stream error handling
try|try
block|{
comment|// for lexical error handling
switch|switch
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
condition|)
block|{
case|case
literal|'\t'
case|:
case|case
literal|' '
case|:
block|{
name|mWS
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
expr_stmt|;
break|break;
block|}
case|case
literal|'('
case|:
block|{
name|mLPAREN
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
expr_stmt|;
break|break;
block|}
case|case
literal|')'
case|:
block|{
name|mRPAREN
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
expr_stmt|;
break|break;
block|}
case|case
literal|'!'
case|:
block|{
name|mNEQUAL
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
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
literal|'='
operator|)
operator|&&
operator|(
name|LA
argument_list|(
literal|2
argument_list|)
operator|==
literal|'='
operator|)
condition|)
block|{
name|mEEQUAL
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
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
literal|'"'
operator|)
operator|&&
operator|(
operator|(
name|LA
argument_list|(
literal|2
argument_list|)
operator|>=
literal|'\u0003'
operator|&&
name|LA
argument_list|(
literal|2
argument_list|)
operator|<=
literal|'\u00ff'
operator|)
operator|)
condition|)
block|{
name|mSTRING
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
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
literal|'='
operator|)
operator|&&
operator|(
literal|true
operator|)
condition|)
block|{
name|mEQUAL
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
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
literal|'"'
operator|)
operator|&&
operator|(
literal|true
operator|)
condition|)
block|{
name|mQUOTE
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
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
condition|)
block|{
name|mFIELDTYPE
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|theRetToken
operator|=
name|_returnToken
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|EOF_CHAR
condition|)
block|{
name|uponEOF
argument_list|()
expr_stmt|;
name|_returnToken
operator|=
name|makeToken
argument_list|(
name|Token
operator|.
name|EOF_TYPE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
throw|throw
operator|new
name|NoViableAltForCharException
argument_list|(
operator|(
name|char
operator|)
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|,
name|getFilename
argument_list|()
argument_list|,
name|getLine
argument_list|()
argument_list|,
name|getColumn
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
if|if
condition|(
name|_returnToken
operator|==
literal|null
condition|)
continue|continue
name|tryAgain
continue|;
comment|// found SKIP token
name|_ttype
operator|=
name|_returnToken
operator|.
name|getType
argument_list|()
expr_stmt|;
name|_returnToken
operator|.
name|setType
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
return|return
name|_returnToken
return|;
block|}
catch|catch
parameter_list|(
name|RecognitionException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|TokenStreamRecognitionException
argument_list|(
name|e
argument_list|)
throw|;
block|}
block|}
catch|catch
parameter_list|(
name|CharStreamException
name|cse
parameter_list|)
block|{
if|if
condition|(
name|cse
operator|instanceof
name|CharStreamIOException
condition|)
block|{
throw|throw
operator|new
name|TokenStreamIOException
argument_list|(
operator|(
operator|(
name|CharStreamIOException
operator|)
name|cse
operator|)
operator|.
name|io
argument_list|)
throw|;
block|}
else|else
block|{
throw|throw
operator|new
name|TokenStreamException
argument_list|(
name|cse
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
block|}
DECL|method|mWS (boolean _createToken)
specifier|public
specifier|final
name|void
name|mWS
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|WS
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
block|{
switch|switch
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
condition|)
block|{
case|case
literal|' '
case|:
block|{
name|match
argument_list|(
literal|' '
argument_list|)
expr_stmt|;
break|break;
block|}
case|case
literal|'\t'
case|:
block|{
name|match
argument_list|(
literal|'\t'
argument_list|)
expr_stmt|;
break|break;
block|}
default|default:
block|{
throw|throw
operator|new
name|NoViableAltForCharException
argument_list|(
operator|(
name|char
operator|)
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|,
name|getFilename
argument_list|()
argument_list|,
name|getLine
argument_list|()
argument_list|,
name|getColumn
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
name|_ttype
operator|=
name|Token
operator|.
name|SKIP
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mLPAREN (boolean _createToken)
specifier|public
specifier|final
name|void
name|mLPAREN
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|LPAREN
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
name|match
argument_list|(
literal|'('
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mRPAREN (boolean _createToken)
specifier|public
specifier|final
name|void
name|mRPAREN
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|RPAREN
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
name|match
argument_list|(
literal|')'
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mEQUAL (boolean _createToken)
specifier|public
specifier|final
name|void
name|mEQUAL
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|EQUAL
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
name|match
argument_list|(
literal|"="
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mEEQUAL (boolean _createToken)
specifier|public
specifier|final
name|void
name|mEEQUAL
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|EEQUAL
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
name|match
argument_list|(
literal|"=="
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mNEQUAL (boolean _createToken)
specifier|public
specifier|final
name|void
name|mNEQUAL
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|NEQUAL
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
name|match
argument_list|(
literal|"!="
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mQUOTE (boolean _createToken)
specifier|public
specifier|final
name|void
name|mQUOTE
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|QUOTE
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
name|match
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mSTRING (boolean _createToken)
specifier|public
specifier|final
name|void
name|mSTRING
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|STRING
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
name|_saveIndex
operator|=
name|text
operator|.
name|length
argument_list|()
expr_stmt|;
name|mQUOTE
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|text
operator|.
name|setLength
argument_list|(
name|_saveIndex
argument_list|)
expr_stmt|;
block|{
name|_loop11
label|:
do|do
block|{
if|if
condition|(
operator|(
name|_tokenSet_1
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|)
condition|)
block|{
name|matchNot
argument_list|(
literal|'"'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
break|break
name|_loop11
break|;
block|}
block|}
do|while
condition|(
literal|true
condition|)
do|;
block|}
name|_saveIndex
operator|=
name|text
operator|.
name|length
argument_list|()
expr_stmt|;
name|mQUOTE
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|text
operator|.
name|setLength
argument_list|(
name|_saveIndex
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mLETTER (boolean _createToken)
specifier|protected
specifier|final
name|void
name|mLETTER
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|LETTER
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
block|{
name|match
argument_list|(
name|_tokenSet_0
argument_list|)
expr_stmt|;
block|}
name|_ttype
operator|=
name|testLiteralsTable
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|,
name|_ttype
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
DECL|method|mFIELDTYPE (boolean _createToken)
specifier|public
specifier|final
name|void
name|mFIELDTYPE
parameter_list|(
name|boolean
name|_createToken
parameter_list|)
throws|throws
name|RecognitionException
throws|,
name|CharStreamException
throws|,
name|TokenStreamException
block|{
name|int
name|_ttype
decl_stmt|;
name|Token
name|_token
init|=
literal|null
decl_stmt|;
name|int
name|_begin
init|=
name|text
operator|.
name|length
argument_list|()
decl_stmt|;
name|_ttype
operator|=
name|FIELDTYPE
expr_stmt|;
name|int
name|_saveIndex
decl_stmt|;
block|{
name|int
name|_cnt16
init|=
literal|0
decl_stmt|;
name|_loop16
label|:
do|do
block|{
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
condition|)
block|{
name|mLETTER
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|_cnt16
operator|>=
literal|1
condition|)
block|{
break|break
name|_loop16
break|;
block|}
else|else
block|{
throw|throw
operator|new
name|NoViableAltForCharException
argument_list|(
operator|(
name|char
operator|)
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|,
name|getFilename
argument_list|()
argument_list|,
name|getLine
argument_list|()
argument_list|,
name|getColumn
argument_list|()
argument_list|)
throw|;
block|}
block|}
name|_cnt16
operator|++
expr_stmt|;
block|}
do|while
condition|(
literal|true
condition|)
do|;
block|}
name|_ttype
operator|=
name|testLiteralsTable
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
if|if
condition|(
name|_createToken
operator|&&
name|_token
operator|==
literal|null
operator|&&
name|_ttype
operator|!=
name|Token
operator|.
name|SKIP
condition|)
block|{
name|_token
operator|=
name|makeToken
argument_list|(
name|_ttype
argument_list|)
expr_stmt|;
name|_token
operator|.
name|setText
argument_list|(
operator|new
name|String
argument_list|(
name|text
operator|.
name|getBuffer
argument_list|()
argument_list|,
name|_begin
argument_list|,
name|text
operator|.
name|length
argument_list|()
operator|-
name|_begin
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|_returnToken
operator|=
name|_token
expr_stmt|;
block|}
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
operator|new
name|long
index|[
literal|8
index|]
decl_stmt|;
name|data
index|[
literal|0
index|]
operator|=
operator|-
literal|2305846337813348872L
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<=
literal|3
condition|;
name|i
operator|++
control|)
block|{
name|data
index|[
name|i
index|]
operator|=
operator|-
literal|1L
expr_stmt|;
block|}
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
operator|new
name|long
index|[
literal|8
index|]
decl_stmt|;
name|data
index|[
literal|0
index|]
operator|=
operator|-
literal|17179869192L
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<=
literal|3
condition|;
name|i
operator|++
control|)
block|{
name|data
index|[
name|i
index|]
operator|=
operator|-
literal|1L
expr_stmt|;
block|}
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
block|}
end_class

end_unit

