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
DECL|class|CommonToken
specifier|public
class|class
name|CommonToken
extends|extends
name|Token
block|{
comment|// most tokens will want line and text information
DECL|field|line
specifier|protected
name|int
name|line
decl_stmt|;
DECL|field|text
specifier|protected
name|String
name|text
init|=
literal|null
decl_stmt|;
DECL|field|col
specifier|protected
name|int
name|col
decl_stmt|;
DECL|method|CommonToken ()
specifier|public
name|CommonToken
parameter_list|()
block|{     }
DECL|method|CommonToken (int t, String txt)
specifier|public
name|CommonToken
parameter_list|(
name|int
name|t
parameter_list|,
name|String
name|txt
parameter_list|)
block|{
name|type
operator|=
name|t
expr_stmt|;
name|setText
argument_list|(
name|txt
argument_list|)
expr_stmt|;
block|}
DECL|method|CommonToken (String s)
specifier|public
name|CommonToken
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|text
operator|=
name|s
expr_stmt|;
block|}
DECL|method|getLine ()
specifier|public
name|int
name|getLine
parameter_list|()
block|{
return|return
name|line
return|;
block|}
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|text
return|;
block|}
DECL|method|setLine (int l)
specifier|public
name|void
name|setLine
parameter_list|(
name|int
name|l
parameter_list|)
block|{
name|line
operator|=
name|l
expr_stmt|;
block|}
DECL|method|setText (String s)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|text
operator|=
name|s
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"[\""
operator|+
name|getText
argument_list|()
operator|+
literal|"\",<"
operator|+
name|type
operator|+
literal|">,line="
operator|+
name|line
operator|+
literal|",col="
operator|+
name|col
operator|+
literal|"]"
return|;
block|}
comment|/** Return token's start column */
DECL|method|getColumn ()
specifier|public
name|int
name|getColumn
parameter_list|()
block|{
return|return
name|col
return|;
block|}
DECL|method|setColumn (int c)
specifier|public
name|void
name|setColumn
parameter_list|(
name|int
name|c
parameter_list|)
block|{
name|col
operator|=
name|c
expr_stmt|;
block|}
block|}
end_class

end_unit

