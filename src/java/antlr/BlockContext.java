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

begin_comment
comment|/**BlockContext stores the information needed when creating an  * alternative (list of elements).  Entering a subrule requires  * that we save this state as each block of alternatives  * requires state such as "tail of current alternative."  */
end_comment

begin_class
DECL|class|BlockContext
class|class
name|BlockContext
block|{
DECL|field|block
name|AlternativeBlock
name|block
decl_stmt|;
comment|// current block of alternatives
DECL|field|altNum
name|int
name|altNum
decl_stmt|;
comment|// which alt are we accepting 0..n-1
DECL|field|blockEnd
name|BlockEndElement
name|blockEnd
decl_stmt|;
comment|// used if nested
DECL|method|addAlternativeElement (AlternativeElement e)
specifier|public
name|void
name|addAlternativeElement
parameter_list|(
name|AlternativeElement
name|e
parameter_list|)
block|{
name|currentAlt
argument_list|()
operator|.
name|addElement
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
DECL|method|currentAlt ()
specifier|public
name|Alternative
name|currentAlt
parameter_list|()
block|{
return|return
operator|(
name|Alternative
operator|)
name|block
operator|.
name|alternatives
operator|.
name|elementAt
argument_list|(
name|altNum
argument_list|)
return|;
block|}
DECL|method|currentElement ()
specifier|public
name|AlternativeElement
name|currentElement
parameter_list|()
block|{
return|return
name|currentAlt
argument_list|()
operator|.
name|tail
return|;
block|}
block|}
end_class

end_unit

