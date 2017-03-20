begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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

begin_comment
comment|/**  * This is an abstract implementation of ParamLayoutFormatter, which provides some  * functionality for the handling of argument strings.  */
end_comment

begin_class
DECL|class|AbstractParamLayoutFormatter
specifier|public
specifier|abstract
class|class
name|AbstractParamLayoutFormatter
implements|implements
name|ParamLayoutFormatter
block|{
DECL|field|SEPARATOR
specifier|private
specifier|static
specifier|final
name|char
name|SEPARATOR
init|=
literal|','
decl_stmt|;
comment|/**      * Parse an argument string and return the parts of the argument. The parts are      * separated by commas, and escaped commas are reduced to literal commas.      * @param arg The argument string.      * @return A list of strings representing the parts of the argument.      */
DECL|method|parseArgument (String arg)
specifier|protected
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|parseArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|parts
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|StringBuilder
name|current
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|boolean
name|escaped
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|arg
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
name|AbstractParamLayoutFormatter
operator|.
name|SEPARATOR
operator|)
operator|&&
operator|!
name|escaped
condition|)
block|{
name|parts
operator|.
name|add
argument_list|(
name|current
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|current
operator|=
operator|new
name|StringBuilder
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|'\\'
condition|)
block|{
if|if
condition|(
name|escaped
condition|)
block|{
name|escaped
operator|=
literal|false
expr_stmt|;
name|current
operator|.
name|append
argument_list|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|escaped
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|escaped
condition|)
block|{
comment|// Handle newline and tab:
if|if
condition|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|'n'
condition|)
block|{
name|current
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|==
literal|'t'
condition|)
block|{
name|current
operator|.
name|append
argument_list|(
literal|'\t'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
operator|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|!=
literal|','
operator|)
operator|&&
operator|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
operator|!=
literal|'"'
operator|)
condition|)
block|{
name|current
operator|.
name|append
argument_list|(
literal|'\\'
argument_list|)
expr_stmt|;
block|}
name|current
operator|.
name|append
argument_list|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|escaped
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|current
operator|.
name|append
argument_list|(
name|arg
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|parts
operator|.
name|add
argument_list|(
name|current
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|parts
return|;
block|}
block|}
end_class

end_unit

