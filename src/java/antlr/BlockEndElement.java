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
comment|/**All alternative blocks are "terminated" by BlockEndElements unless  * they are rule blocks (in which case they use RuleEndElement).  */
end_comment

begin_class
DECL|class|BlockEndElement
class|class
name|BlockEndElement
extends|extends
name|AlternativeElement
block|{
DECL|field|lock
specifier|protected
name|boolean
index|[]
name|lock
decl_stmt|;
comment|// for analysis; used to avoid infinite loops
DECL|field|block
specifier|protected
name|AlternativeBlock
name|block
decl_stmt|;
comment|// ending blocks know what block they terminate
DECL|method|BlockEndElement (Grammar g)
specifier|public
name|BlockEndElement
parameter_list|(
name|Grammar
name|g
parameter_list|)
block|{
name|super
argument_list|(
name|g
argument_list|)
expr_stmt|;
name|lock
operator|=
operator|new
name|boolean
index|[
name|g
operator|.
name|maxk
operator|+
literal|1
index|]
expr_stmt|;
block|}
DECL|method|look (int k)
specifier|public
name|Lookahead
name|look
parameter_list|(
name|int
name|k
parameter_list|)
block|{
return|return
name|grammar
operator|.
name|theLLkAnalyzer
operator|.
name|look
argument_list|(
name|k
argument_list|,
name|this
argument_list|)
return|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
comment|//return " [BlkEnd]";
return|return
literal|""
return|;
block|}
block|}
end_class

end_unit

