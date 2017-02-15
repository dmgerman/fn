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
name|BibtexCaseChanger
operator|.
name|FORMAT_MODE
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
comment|/**  * From the Bibtex manual:  *  * Pops the top two (string) literals; it changes the case of the second  * according to the specifications of the first, as follows. (Note: The word  * `letters' in the next sentence refers only to those at brace-level 0, the  * top-most brace level; no other characters are changed, except perhaps for  * \special characters", described in Section 4.) If the first literal is the  * string `t', it converts to lower case all letters except the very first  * character in the string, which it leaves alone, and except the first  * character following any colon and then nonnull white space, which it also  * leaves alone; if it's the string `l', it converts all letters to lower case;  * and if it's the string `u', it converts all letters to upper case. It then  * pushes this resulting string. If either type is incorrect, it complains and  * pushes the null string; however, if both types are correct but the  * specification string (i.e., the first string) isn't one of the legal ones, it  * merely pushes the second back onto the stack, after complaining. (Another  * note: It ignores case differences in the specification string; for example,  * the strings t and T are equivalent for the purposes of this built-in  * function.)  *  * Christopher: I think this should be another grammar! This parser is horrible.  *  */
end_comment

begin_class
DECL|class|ChangeCaseFunction
specifier|public
class|class
name|ChangeCaseFunction
implements|implements
name|BstFunction
block|{
DECL|field|vm
specifier|private
specifier|final
name|VM
name|vm
decl_stmt|;
DECL|method|ChangeCaseFunction (VM vm)
specifier|public
name|ChangeCaseFunction
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
literal|"Not enough operands on stack for operation change.case$"
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
operator|(
name|o1
operator|instanceof
name|String
operator|)
operator|&&
operator|(
operator|(
operator|(
name|String
operator|)
name|o1
operator|)
operator|.
name|length
argument_list|()
operator|==
literal|1
operator|)
operator|)
condition|)
block|{
throw|throw
operator|new
name|VMException
argument_list|(
literal|"A format string of length 1 is needed for change.case$"
argument_list|)
throw|;
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
throw|throw
operator|new
name|VMException
argument_list|(
literal|"A string is needed as second parameter for change.case$"
argument_list|)
throw|;
block|}
name|char
name|format
init|=
operator|(
operator|(
name|String
operator|)
name|o1
operator|)
operator|.
name|toLowerCase
argument_list|()
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|String
name|s
init|=
operator|(
name|String
operator|)
name|o2
decl_stmt|;
name|stack
operator|.
name|push
argument_list|(
name|BibtexCaseChanger
operator|.
name|changeCase
argument_list|(
name|s
argument_list|,
name|FORMAT_MODE
operator|.
name|getFormatModeForBSTFormat
argument_list|(
name|format
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

