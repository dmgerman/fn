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
DECL|class|StringLiteralSymbol
class|class
name|StringLiteralSymbol
extends|extends
name|TokenSymbol
block|{
DECL|field|label
specifier|protected
name|String
name|label
decl_stmt|;
comment|// was this string literal labeled?
DECL|method|StringLiteralSymbol (String r)
specifier|public
name|StringLiteralSymbol
parameter_list|(
name|String
name|r
parameter_list|)
block|{
name|super
argument_list|(
name|r
argument_list|)
expr_stmt|;
block|}
DECL|method|getLabel ()
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
DECL|method|setLabel (String label)
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label
parameter_list|)
block|{
name|this
operator|.
name|label
operator|=
name|label
expr_stmt|;
block|}
block|}
end_class

end_unit

