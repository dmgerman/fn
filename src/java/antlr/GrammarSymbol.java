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
comment|/**A GrammarSymbol is a generic symbol that can be  * added to the symbol table for a grammar.  */
end_comment

begin_class
DECL|class|GrammarSymbol
specifier|abstract
class|class
name|GrammarSymbol
block|{
DECL|field|id
specifier|protected
name|String
name|id
decl_stmt|;
DECL|method|GrammarSymbol ()
specifier|public
name|GrammarSymbol
parameter_list|()
block|{}
DECL|method|GrammarSymbol (String s)
specifier|public
name|GrammarSymbol
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|id
operator|=
name|s
expr_stmt|;
block|}
DECL|method|getId ()
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
DECL|method|setId (String s)
specifier|public
name|void
name|setId
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|id
operator|=
name|s
expr_stmt|;
block|}
block|}
end_class

end_unit

