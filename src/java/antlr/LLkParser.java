begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
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

begin_comment
comment|/**An LL(k) parser.  *  * @see antlr.Token  * @see antlr.TokenBuffer  */
end_comment

begin_class
DECL|class|LLkParser
specifier|public
class|class
name|LLkParser
extends|extends
name|Parser
block|{
DECL|field|k
name|int
name|k
decl_stmt|;
DECL|method|LLkParser (int k_)
specifier|public
name|LLkParser
parameter_list|(
name|int
name|k_
parameter_list|)
block|{
name|k
operator|=
name|k_
expr_stmt|;
block|}
DECL|method|LLkParser (ParserSharedInputState state, int k_)
specifier|public
name|LLkParser
parameter_list|(
name|ParserSharedInputState
name|state
parameter_list|,
name|int
name|k_
parameter_list|)
block|{
name|super
argument_list|(
name|state
argument_list|)
expr_stmt|;
name|k
operator|=
name|k_
expr_stmt|;
block|}
DECL|method|LLkParser (TokenBuffer tokenBuf, int k_)
specifier|public
name|LLkParser
parameter_list|(
name|TokenBuffer
name|tokenBuf
parameter_list|,
name|int
name|k_
parameter_list|)
block|{
name|k
operator|=
name|k_
expr_stmt|;
name|setTokenBuffer
argument_list|(
name|tokenBuf
argument_list|)
expr_stmt|;
block|}
DECL|method|LLkParser (TokenStream lexer, int k_)
specifier|public
name|LLkParser
parameter_list|(
name|TokenStream
name|lexer
parameter_list|,
name|int
name|k_
parameter_list|)
block|{
name|k
operator|=
name|k_
expr_stmt|;
name|TokenBuffer
name|tokenBuf
init|=
operator|new
name|TokenBuffer
argument_list|(
name|lexer
argument_list|)
decl_stmt|;
name|setTokenBuffer
argument_list|(
name|tokenBuf
argument_list|)
expr_stmt|;
block|}
comment|/**Consume another token from the input stream.  Can only write sequentially!      * If you need 3 tokens ahead, you must consume() 3 times.      *<p>      * Note that it is possible to overwrite tokens that have not been matched.      * For example, calling consume() 3 times when k=2, means that the first token      * consumed will be overwritten with the 3rd.      */
DECL|method|consume ()
specifier|public
name|void
name|consume
parameter_list|()
block|{
name|inputState
operator|.
name|input
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
DECL|method|LA (int i)
specifier|public
name|int
name|LA
parameter_list|(
name|int
name|i
parameter_list|)
throws|throws
name|TokenStreamException
block|{
return|return
name|inputState
operator|.
name|input
operator|.
name|LA
argument_list|(
name|i
argument_list|)
return|;
block|}
DECL|method|LT (int i)
specifier|public
name|Token
name|LT
parameter_list|(
name|int
name|i
parameter_list|)
throws|throws
name|TokenStreamException
block|{
return|return
name|inputState
operator|.
name|input
operator|.
name|LT
argument_list|(
name|i
argument_list|)
return|;
block|}
DECL|method|trace (String ee, String rname)
specifier|private
name|void
name|trace
parameter_list|(
name|String
name|ee
parameter_list|,
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
name|print
argument_list|(
name|ee
operator|+
name|rname
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
literal|"; [guessing]"
else|:
literal|"; "
operator|)
argument_list|)
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
name|k
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|!=
literal|1
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|LT
argument_list|(
name|i
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"LA("
operator|+
name|i
operator|+
literal|")=="
operator|+
name|LT
argument_list|(
name|i
argument_list|)
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|out
operator|.
name|print
argument_list|(
literal|"LA("
operator|+
name|i
operator|+
literal|")==null"
argument_list|)
expr_stmt|;
block|}
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|""
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
name|trace
argument_list|(
literal|"> "
argument_list|,
name|rname
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
name|trace
argument_list|(
literal|"< "
argument_list|,
name|rname
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

