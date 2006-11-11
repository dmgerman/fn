begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.bst
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|AuthorList
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
name|AuthorList
operator|.
name|Author
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
name|bst
operator|.
name|VM
operator|.
name|BstFunction
import|;
end_import

begin_comment
comment|/**  * From Bibtex:  *   * "The |built_in| function {\.{format.name\$}} pops the  * top three literals (they are a string, an integer, and a string  * literal, in that order). The last string literal represents a  * name list (each name corresponding to a person), the integer  * literal specifies which name to pick from this list, and the  * first string literal specifies how to format this name, as  * described in the \BibTeX\ documentation. Finally, this function  * pushes the formatted name. If any of the types is incorrect, it  * complains and pushes the null string."  *   * All the pain is encapsulated in BibtexNameFormatter. :-)  *   */
end_comment

begin_class
DECL|class|FormatNameFunction
specifier|public
class|class
name|FormatNameFunction
implements|implements
name|BstFunction
block|{
DECL|field|vm
name|VM
name|vm
decl_stmt|;
DECL|method|FormatNameFunction (VM vm)
specifier|public
name|FormatNameFunction
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
literal|3
condition|)
block|{
throw|throw
operator|new
name|VMException
argument_list|(
literal|"Not enough operands on stack for operation format.name$"
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
name|Object
name|o2
init|=
name|stack
operator|.
name|pop
argument_list|()
decl_stmt|;
name|Object
name|o3
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
name|String
operator|)
operator|&&
operator|!
operator|(
name|o2
operator|instanceof
name|Integer
operator|)
operator|&&
operator|!
operator|(
name|o3
operator|instanceof
name|String
operator|)
condition|)
block|{
comment|// warning("A string is needed for change.case$");
name|stack
operator|.
name|push
argument_list|(
literal|""
argument_list|)
expr_stmt|;
return|return;
block|}
name|String
name|format
init|=
operator|(
name|String
operator|)
name|o1
decl_stmt|;
name|Integer
name|name
init|=
operator|(
name|Integer
operator|)
name|o2
decl_stmt|;
name|String
name|names
init|=
operator|(
name|String
operator|)
name|o3
decl_stmt|;
if|if
condition|(
name|names
operator|!=
literal|null
condition|)
block|{
name|AuthorList
name|a
init|=
name|AuthorList
operator|.
name|getAuthorList
argument_list|(
name|names
argument_list|)
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|intValue
argument_list|()
operator|>
name|a
operator|.
name|size
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|VMException
argument_list|(
literal|"Author Out of Bounds. Number "
operator|+
name|name
operator|+
literal|" invalid for "
operator|+
name|names
argument_list|)
throw|;
block|}
name|Author
name|author
init|=
name|a
operator|.
name|getAuthor
argument_list|(
name|name
operator|.
name|intValue
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
name|stack
operator|.
name|push
argument_list|(
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|author
argument_list|,
name|format
argument_list|,
name|vm
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|stack
operator|.
name|push
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

