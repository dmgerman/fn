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

begin_class
DECL|class|TreeSpecifierNode
class|class
name|TreeSpecifierNode
block|{
DECL|field|parent
specifier|private
name|TreeSpecifierNode
name|parent
init|=
literal|null
decl_stmt|;
DECL|field|firstChild
specifier|private
name|TreeSpecifierNode
name|firstChild
init|=
literal|null
decl_stmt|;
DECL|field|nextSibling
specifier|private
name|TreeSpecifierNode
name|nextSibling
init|=
literal|null
decl_stmt|;
DECL|field|tok
specifier|private
name|Token
name|tok
decl_stmt|;
DECL|method|TreeSpecifierNode (Token tok_)
name|TreeSpecifierNode
parameter_list|(
name|Token
name|tok_
parameter_list|)
block|{
name|tok
operator|=
name|tok_
expr_stmt|;
block|}
DECL|method|getFirstChild ()
specifier|public
name|TreeSpecifierNode
name|getFirstChild
parameter_list|()
block|{
return|return
name|firstChild
return|;
block|}
DECL|method|getNextSibling ()
specifier|public
name|TreeSpecifierNode
name|getNextSibling
parameter_list|()
block|{
return|return
name|nextSibling
return|;
block|}
comment|// Accessors
DECL|method|getParent ()
specifier|public
name|TreeSpecifierNode
name|getParent
parameter_list|()
block|{
return|return
name|parent
return|;
block|}
DECL|method|getToken ()
specifier|public
name|Token
name|getToken
parameter_list|()
block|{
return|return
name|tok
return|;
block|}
DECL|method|setFirstChild (TreeSpecifierNode child)
specifier|public
name|void
name|setFirstChild
parameter_list|(
name|TreeSpecifierNode
name|child
parameter_list|)
block|{
name|firstChild
operator|=
name|child
expr_stmt|;
name|child
operator|.
name|parent
operator|=
name|this
expr_stmt|;
block|}
comment|// Structure-building
DECL|method|setNextSibling (TreeSpecifierNode sibling)
specifier|public
name|void
name|setNextSibling
parameter_list|(
name|TreeSpecifierNode
name|sibling
parameter_list|)
block|{
name|nextSibling
operator|=
name|sibling
expr_stmt|;
name|sibling
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
block|}
end_class

end_unit

