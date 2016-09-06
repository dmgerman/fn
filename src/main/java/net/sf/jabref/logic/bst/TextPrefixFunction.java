begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.bst
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bst
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Stack
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
name|bst
operator|.
name|VM
operator|.
name|BstEntry
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
name|bst
operator|.
name|VM
operator|.
name|BstFunction
import|;
end_import

begin_comment
comment|/**  The |built_in| function {\.{text.prefix\$}} pops the top two literals  (the integer literal |pop_lit1| and a string literal, in that order).  It pushes the substring of the (at most) |pop_lit1| consecutive text  characters starting from the beginning of the string.  This function  is similar to {\.{substring\$}}, but this one considers an accented  character (or more precisely, a ``special character''$\!$, even if  it's missing its matching |right_brace|) to be a single text character  (rather than however many |ASCII_code| characters it actually  comprises), and this function doesn't consider braces to be text  characters; furthermore, this function appends any needed matching  |right_brace|s.  If any of the types is incorrect, it complains and  pushes the null string.  *  */
end_comment

begin_class
DECL|class|TextPrefixFunction
specifier|public
class|class
name|TextPrefixFunction
implements|implements
name|BstFunction
block|{
DECL|field|vm
specifier|private
specifier|final
name|VM
name|vm
decl_stmt|;
DECL|method|TextPrefixFunction (VM vm)
specifier|public
name|TextPrefixFunction
parameter_list|(
name|VM
name|vm
parameter_list|)
block|{
name|this
operator|.
name|vm
operator|=
name|vm
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute (BstEntry context)
specifier|public
name|void
name|execute
parameter_list|(
name|BstEntry
name|context
parameter_list|)
block|{
name|Stack
argument_list|<
name|Object
argument_list|>
name|stack
init|=
name|vm
operator|.
name|getStack
argument_list|()
decl_stmt|;
if|if
condition|(
name|stack
operator|.
name|size
argument_list|()
operator|<
literal|2
condition|)
block|{
throw|throw
operator|new
name|VMException
argument_list|(
literal|"Not enough operands on stack for operation text.prefix$"
argument_list|)
throw|;
block|}
name|Object
name|o1
init|=
name|stack
operator|.
name|pop
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|o1
operator|instanceof
name|Integer
operator|)
condition|)
block|{
name|vm
operator|.
name|warn
argument_list|(
literal|"An integer is needed as first parameter to text.prefix$"
argument_list|)
expr_stmt|;
name|stack
operator|.
name|push
argument_list|(
literal|""
argument_list|)
expr_stmt|;
return|return;
block|}
name|Object
name|o2
init|=
name|stack
operator|.
name|pop
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|o2
operator|instanceof
name|String
operator|)
condition|)
block|{
name|vm
operator|.
name|warn
argument_list|(
literal|"A string is needed as second parameter to text.prefix$"
argument_list|)
expr_stmt|;
name|stack
operator|.
name|push
argument_list|(
literal|""
argument_list|)
expr_stmt|;
return|return;
block|}
name|stack
operator|.
name|push
argument_list|(
name|BibtexTextPrefix
operator|.
name|textPrefix
argument_list|(
operator|(
name|Integer
operator|)
name|o1
argument_list|,
operator|(
name|String
operator|)
name|o2
argument_list|,
name|vm
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

