begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/RIGHTS.html  *  * $Id$  */
end_comment

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
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|ASTArray
import|;
end_import

begin_comment
comment|/**A generic ANTLR parser (LL(k) for k>=1) containing a bunch of  * utility routines useful at any lookahead depth.  We distinguish between  * the LL(1) and LL(k) parsers because of efficiency.  This may not be  * necessary in the near future.  *  * Each parser object contains the state of the parse including a lookahead  * cache (the form of which is determined by the subclass), whether or  * not the parser is in guess mode, where tokens come from, etc...  *  *<p>  * During<b>guess</b> mode, the current lookahead token(s) and token type(s)  * cache must be saved because the token stream may not have been informed  * to save the token (via<tt>mark</tt>) before the<tt>try</tt> block.  * Guessing is started by:  *<ol>  *<li>saving the lookahead cache.  *<li>marking the current position in the TokenBuffer.  *<li>increasing the guessing level.  *</ol>  *  * After guessing, the parser state is restored by:  *<ol>  *<li>restoring the lookahead cache.  *<li>rewinding the TokenBuffer.  *<li>decreasing the guessing level.  *</ol>  *  * @see antlr.Token  * @see antlr.TokenBuffer  * @see antlr.Tokenizer  * @see antlr.LL1Parser  * @see antlr.LLkParser  */
end_comment

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|debug
operator|.
name|MessageListener
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|debug
operator|.
name|ParserListener
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|debug
operator|.
name|ParserMatchListener
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|debug
operator|.
name|ParserTokenListener
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|debug
operator|.
name|SemanticPredicateListener
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|debug
operator|.
name|SyntacticPredicateListener
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|debug
operator|.
name|TraceListener
import|;
end_import

begin_class
DECL|class|Parser
specifier|public
specifier|abstract
class|class
name|Parser
block|{
DECL|field|inputState
specifier|protected
name|ParserSharedInputState
name|inputState
decl_stmt|;
comment|/** Nesting level of registered handlers */
comment|// protected int exceptionLevel = 0;
comment|/** Table of token type to token names */
DECL|field|tokenNames
specifier|protected
name|String
index|[]
name|tokenNames
decl_stmt|;
comment|/** AST return value for a rule is squirreled away here */
DECL|field|returnAST
specifier|protected
name|AST
name|returnAST
decl_stmt|;
comment|/** AST support code; parser and treeparser delegate to this object */
DECL|field|astFactory
specifier|protected
name|ASTFactory
name|astFactory
init|=
operator|new
name|ASTFactory
argument_list|()
decl_stmt|;
DECL|field|ignoreInvalidDebugCalls
specifier|private
name|boolean
name|ignoreInvalidDebugCalls
init|=
literal|false
decl_stmt|;
comment|/** Used to keep track of indentdepth for traceIn/Out */
DECL|field|traceDepth
specifier|protected
name|int
name|traceDepth
init|=
literal|0
decl_stmt|;
DECL|method|Parser ()
specifier|public
name|Parser
parameter_list|()
block|{
name|inputState
operator|=
operator|new
name|ParserSharedInputState
argument_list|()
expr_stmt|;
block|}
DECL|method|Parser (ParserSharedInputState state)
specifier|public
name|Parser
parameter_list|(
name|ParserSharedInputState
name|state
parameter_list|)
block|{
name|inputState
operator|=
name|state
expr_stmt|;
block|}
DECL|method|addMessageListener (MessageListener l)
specifier|public
name|void
name|addMessageListener
parameter_list|(
name|MessageListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"addMessageListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|addParserListener (ParserListener l)
specifier|public
name|void
name|addParserListener
parameter_list|(
name|ParserListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"addParserListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|addParserMatchListener (ParserMatchListener l)
specifier|public
name|void
name|addParserMatchListener
parameter_list|(
name|ParserMatchListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"addParserMatchListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|addParserTokenListener (ParserTokenListener l)
specifier|public
name|void
name|addParserTokenListener
parameter_list|(
name|ParserTokenListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"addParserTokenListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|addSemanticPredicateListener (SemanticPredicateListener l)
specifier|public
name|void
name|addSemanticPredicateListener
parameter_list|(
name|SemanticPredicateListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"addSemanticPredicateListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|addSyntacticPredicateListener (SyntacticPredicateListener l)
specifier|public
name|void
name|addSyntacticPredicateListener
parameter_list|(
name|SyntacticPredicateListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"addSyntacticPredicateListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|addTraceListener (TraceListener l)
specifier|public
name|void
name|addTraceListener
parameter_list|(
name|TraceListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"addTraceListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
comment|/**Get another token object from the token stream */
DECL|method|consume ()
specifier|public
specifier|abstract
name|void
name|consume
parameter_list|()
throws|throws
name|TokenStreamException
function_decl|;
comment|/** Consume tokens until one matches the given token */
DECL|method|consumeUntil (int tokenType)
specifier|public
name|void
name|consumeUntil
parameter_list|(
name|int
name|tokenType
parameter_list|)
throws|throws
name|TokenStreamException
block|{
while|while
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|!=
name|Token
operator|.
name|EOF_TYPE
operator|&&
name|LA
argument_list|(
literal|1
argument_list|)
operator|!=
name|tokenType
condition|)
block|{
name|consume
argument_list|()
expr_stmt|;
block|}
block|}
comment|/** Consume tokens until one matches the given token set */
DECL|method|consumeUntil (BitSet set)
specifier|public
name|void
name|consumeUntil
parameter_list|(
name|BitSet
name|set
parameter_list|)
throws|throws
name|TokenStreamException
block|{
while|while
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|!=
name|Token
operator|.
name|EOF_TYPE
operator|&&
operator|!
name|set
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|)
condition|)
block|{
name|consume
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|defaultDebuggingSetup (TokenStream lexer, TokenBuffer tokBuf)
specifier|protected
name|void
name|defaultDebuggingSetup
parameter_list|(
name|TokenStream
name|lexer
parameter_list|,
name|TokenBuffer
name|tokBuf
parameter_list|)
block|{
comment|// by default, do nothing -- we're not debugging
block|}
comment|/** Get the AST return value squirreled away in the parser */
DECL|method|getAST ()
specifier|public
name|AST
name|getAST
parameter_list|()
block|{
return|return
name|returnAST
return|;
block|}
DECL|method|getASTFactory ()
specifier|public
name|ASTFactory
name|getASTFactory
parameter_list|()
block|{
return|return
name|astFactory
return|;
block|}
DECL|method|getFilename ()
specifier|public
name|String
name|getFilename
parameter_list|()
block|{
return|return
name|inputState
operator|.
name|filename
return|;
block|}
DECL|method|getInputState ()
specifier|public
name|ParserSharedInputState
name|getInputState
parameter_list|()
block|{
return|return
name|inputState
return|;
block|}
DECL|method|setInputState (ParserSharedInputState state)
specifier|public
name|void
name|setInputState
parameter_list|(
name|ParserSharedInputState
name|state
parameter_list|)
block|{
name|inputState
operator|=
name|state
expr_stmt|;
block|}
DECL|method|getTokenName (int num)
specifier|public
name|String
name|getTokenName
parameter_list|(
name|int
name|num
parameter_list|)
block|{
return|return
name|tokenNames
index|[
name|num
index|]
return|;
block|}
DECL|method|getTokenNames ()
specifier|public
name|String
index|[]
name|getTokenNames
parameter_list|()
block|{
return|return
name|tokenNames
return|;
block|}
DECL|method|isDebugMode ()
specifier|public
name|boolean
name|isDebugMode
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
comment|/** Return the token type of the ith token of lookahead where i=1 	 * is the current token being examined by the parser (i.e., it 	 * has not been matched yet). 	 */
DECL|method|LA (int i)
specifier|public
specifier|abstract
name|int
name|LA
parameter_list|(
name|int
name|i
parameter_list|)
throws|throws
name|TokenStreamException
function_decl|;
comment|/**Return the ith token of lookahead */
DECL|method|LT (int i)
specifier|public
specifier|abstract
name|Token
name|LT
parameter_list|(
name|int
name|i
parameter_list|)
throws|throws
name|TokenStreamException
function_decl|;
comment|// Forwarded to TokenBuffer
DECL|method|mark ()
specifier|public
name|int
name|mark
parameter_list|()
block|{
return|return
name|inputState
operator|.
name|input
operator|.
name|mark
argument_list|()
return|;
block|}
comment|/**Make sure current lookahead symbol matches token type<tt>t</tt>. 	 * Throw an exception upon mismatch, which is catch by either the 	 * error handler or by the syntactic predicate. 	 */
DECL|method|match (int t)
specifier|public
name|void
name|match
parameter_list|(
name|int
name|t
parameter_list|)
throws|throws
name|MismatchedTokenException
throws|,
name|TokenStreamException
block|{
if|if
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|!=
name|t
condition|)
throw|throw
operator|new
name|MismatchedTokenException
argument_list|(
name|tokenNames
argument_list|,
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|,
name|t
argument_list|,
literal|false
argument_list|,
name|getFilename
argument_list|()
argument_list|)
throw|;
else|else
comment|// mark token as consumed -- fetch next token deferred until LA/LT
name|consume
argument_list|()
expr_stmt|;
block|}
comment|/**Make sure current lookahead symbol matches the given set 	 * Throw an exception upon mismatch, which is catch by either the 	 * error handler or by the syntactic predicate. 	 */
DECL|method|match (BitSet b)
specifier|public
name|void
name|match
parameter_list|(
name|BitSet
name|b
parameter_list|)
throws|throws
name|MismatchedTokenException
throws|,
name|TokenStreamException
block|{
if|if
condition|(
operator|!
name|b
operator|.
name|member
argument_list|(
name|LA
argument_list|(
literal|1
argument_list|)
argument_list|)
condition|)
throw|throw
operator|new
name|MismatchedTokenException
argument_list|(
name|tokenNames
argument_list|,
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|,
name|b
argument_list|,
literal|false
argument_list|,
name|getFilename
argument_list|()
argument_list|)
throw|;
else|else
comment|// mark token as consumed -- fetch next token deferred until LA/LT
name|consume
argument_list|()
expr_stmt|;
block|}
DECL|method|matchNot (int t)
specifier|public
name|void
name|matchNot
parameter_list|(
name|int
name|t
parameter_list|)
throws|throws
name|MismatchedTokenException
throws|,
name|TokenStreamException
block|{
if|if
condition|(
name|LA
argument_list|(
literal|1
argument_list|)
operator|==
name|t
condition|)
comment|// Throws inverted-sense exception
throw|throw
operator|new
name|MismatchedTokenException
argument_list|(
name|tokenNames
argument_list|,
name|LT
argument_list|(
literal|1
argument_list|)
argument_list|,
name|t
argument_list|,
literal|true
argument_list|,
name|getFilename
argument_list|()
argument_list|)
throw|;
else|else
comment|// mark token as consumed -- fetch next token deferred until LA/LT
name|consume
argument_list|()
expr_stmt|;
block|}
DECL|method|panic ()
specifier|public
specifier|static
name|void
name|panic
parameter_list|()
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Parser: panic"
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
DECL|method|removeMessageListener (MessageListener l)
specifier|public
name|void
name|removeMessageListener
parameter_list|(
name|MessageListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"removeMessageListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|removeParserListener (ParserListener l)
specifier|public
name|void
name|removeParserListener
parameter_list|(
name|ParserListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"removeParserListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|removeParserMatchListener (ParserMatchListener l)
specifier|public
name|void
name|removeParserMatchListener
parameter_list|(
name|ParserMatchListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"removeParserMatchListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|removeParserTokenListener (ParserTokenListener l)
specifier|public
name|void
name|removeParserTokenListener
parameter_list|(
name|ParserTokenListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"removeParserTokenListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|removeSemanticPredicateListener (SemanticPredicateListener l)
specifier|public
name|void
name|removeSemanticPredicateListener
parameter_list|(
name|SemanticPredicateListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"removeSemanticPredicateListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|removeSyntacticPredicateListener (SyntacticPredicateListener l)
specifier|public
name|void
name|removeSyntacticPredicateListener
parameter_list|(
name|SyntacticPredicateListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"removeSyntacticPredicateListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|removeTraceListener (TraceListener l)
specifier|public
name|void
name|removeTraceListener
parameter_list|(
name|TraceListener
name|l
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"removeTraceListener() is only valid if parser built for debugging"
argument_list|)
throw|;
block|}
comment|/** Parser error-reporting function can be overridden in subclass */
DECL|method|reportError (RecognitionException ex)
specifier|public
name|void
name|reportError
parameter_list|(
name|RecognitionException
name|ex
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|ex
argument_list|)
expr_stmt|;
block|}
comment|/** Parser error-reporting function can be overridden in subclass */
DECL|method|reportError (String s)
specifier|public
name|void
name|reportError
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|getFilename
argument_list|()
operator|==
literal|null
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"error: "
operator|+
name|s
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|getFilename
argument_list|()
operator|+
literal|": error: "
operator|+
name|s
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** Parser warning-reporting function can be overridden in subclass */
DECL|method|reportWarning (String s)
specifier|public
name|void
name|reportWarning
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|getFilename
argument_list|()
operator|==
literal|null
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"warning: "
operator|+
name|s
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|getFilename
argument_list|()
operator|+
literal|": warning: "
operator|+
name|s
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|rewind (int pos)
specifier|public
name|void
name|rewind
parameter_list|(
name|int
name|pos
parameter_list|)
block|{
name|inputState
operator|.
name|input
operator|.
name|rewind
argument_list|(
name|pos
argument_list|)
expr_stmt|;
block|}
comment|/** Specify an object with support code (shared by 	 *  Parser and TreeParser.  Normally, the programmer 	 *  does not play with this, using setASTNodeType instead. 	 */
DECL|method|setASTFactory (ASTFactory f)
specifier|public
name|void
name|setASTFactory
parameter_list|(
name|ASTFactory
name|f
parameter_list|)
block|{
name|astFactory
operator|=
name|f
expr_stmt|;
block|}
DECL|method|setASTNodeClass (String cl)
specifier|public
name|void
name|setASTNodeClass
parameter_list|(
name|String
name|cl
parameter_list|)
block|{
name|astFactory
operator|.
name|setASTNodeType
argument_list|(
name|cl
argument_list|)
expr_stmt|;
block|}
comment|/** Specify the type of node to create during tree building; use setASTNodeClass now  *  to be consistent with Token Object Type accessor.  */
DECL|method|setASTNodeType (String nodeType)
specifier|public
name|void
name|setASTNodeType
parameter_list|(
name|String
name|nodeType
parameter_list|)
block|{
name|setASTNodeClass
argument_list|(
name|nodeType
argument_list|)
expr_stmt|;
block|}
DECL|method|setDebugMode (boolean debugMode)
specifier|public
name|void
name|setDebugMode
parameter_list|(
name|boolean
name|debugMode
parameter_list|)
block|{
if|if
condition|(
operator|!
name|ignoreInvalidDebugCalls
condition|)
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"setDebugMode() only valid if parser built for debugging"
argument_list|)
throw|;
block|}
DECL|method|setFilename (String f)
specifier|public
name|void
name|setFilename
parameter_list|(
name|String
name|f
parameter_list|)
block|{
name|inputState
operator|.
name|filename
operator|=
name|f
expr_stmt|;
block|}
DECL|method|setIgnoreInvalidDebugCalls (boolean value)
specifier|public
name|void
name|setIgnoreInvalidDebugCalls
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|ignoreInvalidDebugCalls
operator|=
name|value
expr_stmt|;
block|}
comment|/** Set or change the input token buffer */
DECL|method|setTokenBuffer (TokenBuffer t)
specifier|public
name|void
name|setTokenBuffer
parameter_list|(
name|TokenBuffer
name|t
parameter_list|)
block|{
name|inputState
operator|.
name|input
operator|=
name|t
expr_stmt|;
block|}
DECL|method|traceIndent ()
specifier|public
name|void
name|traceIndent
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|traceDepth
condition|;
name|i
operator|++
control|)
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|" "
argument_list|)
expr_stmt|;
block|}
DECL|method|traceIn (String rname)
specifier|public
name|void
name|traceIn
parameter_list|(
name|String
name|rname
parameter_list|)
throws|throws
name|TokenStreamException
block|{
name|traceDepth
operator|+=
literal|1
expr_stmt|;
name|traceIndent
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"> "
operator|+
name|rname
operator|+
literal|"; LA(1)=="
operator|+
name|LT
argument_list|(
literal|1
argument_list|)
operator|.
name|getText
argument_list|()
operator|+
operator|(
operator|(
name|inputState
operator|.
name|guessing
operator|>
literal|0
operator|)
condition|?
literal|" [guessing]"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
block|}
DECL|method|traceOut (String rname)
specifier|public
name|void
name|traceOut
parameter_list|(
name|String
name|rname
parameter_list|)
throws|throws
name|TokenStreamException
block|{
name|traceIndent
argument_list|()
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"< "
operator|+
name|rname
operator|+
literal|"; LA(1)=="
operator|+
name|LT
argument_list|(
literal|1
argument_list|)
operator|.
name|getText
argument_list|()
operator|+
operator|(
operator|(
name|inputState
operator|.
name|guessing
operator|>
literal|0
operator|)
condition|?
literal|" [guessing]"
else|:
literal|""
operator|)
argument_list|)
expr_stmt|;
name|traceDepth
operator|-=
literal|1
expr_stmt|;
block|}
block|}
end_class

end_unit

