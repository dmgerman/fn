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

begin_comment
comment|/**The context needed to add root,child elements to a Tree.  There  * is only one alternative (i.e., a list of children).  We subclass to  * specialize. MakeGrammar.addElementToCurrentAlt will work correctly  * now for either a block of alts or a Tree child list.  *  * The first time addAlternativeElement is called, it sets the root element  * rather than adding it to one of the alternative lists.  Rather than have  * the grammar duplicate the rules for grammar atoms etc... we use the same  * grammar and same refToken behavior etc...  We have to special case somewhere  * and here is where we do it.  */
end_comment

begin_class
DECL|class|TreeBlockContext
class|class
name|TreeBlockContext
extends|extends
name|BlockContext
block|{
DECL|field|nextElementIsRoot
specifier|protected
name|boolean
name|nextElementIsRoot
init|=
literal|true
decl_stmt|;
DECL|method|addAlternativeElement (AlternativeElement e)
specifier|public
name|void
name|addAlternativeElement
parameter_list|(
name|AlternativeElement
name|e
parameter_list|)
block|{
name|TreeElement
name|tree
init|=
operator|(
name|TreeElement
operator|)
name|block
decl_stmt|;
if|if
condition|(
name|nextElementIsRoot
condition|)
block|{
name|tree
operator|.
name|root
operator|=
operator|(
name|GrammarAtom
operator|)
name|e
expr_stmt|;
name|nextElementIsRoot
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|super
operator|.
name|addAlternativeElement
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

