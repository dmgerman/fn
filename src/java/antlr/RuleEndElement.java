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
comment|/**Contains a list of all places that reference  * this enclosing rule.  Useful for FOLLOW computations.  */
end_comment

begin_class
DECL|class|RuleEndElement
class|class
name|RuleEndElement
extends|extends
name|BlockEndElement
block|{
DECL|field|cache
specifier|protected
name|Lookahead
index|[]
name|cache
decl_stmt|;
comment|// Each rule can cache it's lookahead computation.
comment|// The FOLLOW(rule) is stored in this cache.
comment|// 1..k
DECL|field|noFOLLOW
specifier|protected
name|boolean
name|noFOLLOW
decl_stmt|;
DECL|method|RuleEndElement (Grammar g)
specifier|public
name|RuleEndElement
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
name|cache
operator|=
operator|new
name|Lookahead
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
comment|//return " [RuleEnd]";
return|return
literal|""
return|;
block|}
block|}
end_class

end_unit

