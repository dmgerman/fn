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
comment|/**A GrammarElement is a generic node in our  * data structure that holds a grammar in memory.  * This data structure can be used for static  * analysis or for dynamic analysis (during parsing).  * Every node must know which grammar owns it, how  * to generate code, and how to do analysis.  */
end_comment

begin_class
DECL|class|GrammarElement
specifier|abstract
class|class
name|GrammarElement
block|{
DECL|field|AUTO_GEN_NONE
specifier|public
specifier|static
specifier|final
name|int
name|AUTO_GEN_NONE
init|=
literal|1
decl_stmt|;
DECL|field|AUTO_GEN_CARET
specifier|public
specifier|static
specifier|final
name|int
name|AUTO_GEN_CARET
init|=
literal|2
decl_stmt|;
DECL|field|AUTO_GEN_BANG
specifier|public
specifier|static
specifier|final
name|int
name|AUTO_GEN_BANG
init|=
literal|3
decl_stmt|;
comment|/* 	 * Note that Java does static argument type matching to 	 * determine which function to execute on the receiver. 	 * Here, that implies that we cannot simply say 	 * grammar.generator.gen(this) in GrammarElement or 	 * only CodeGenerator.gen(GrammarElement ge) would 	 * ever be called. 	 */
DECL|field|grammar
specifier|protected
name|Grammar
name|grammar
decl_stmt|;
DECL|field|line
specifier|protected
name|int
name|line
decl_stmt|;
DECL|method|GrammarElement (Grammar g)
specifier|public
name|GrammarElement
parameter_list|(
name|Grammar
name|g
parameter_list|)
block|{
name|grammar
operator|=
name|g
expr_stmt|;
block|}
DECL|method|generate ()
specifier|public
name|void
name|generate
parameter_list|()
block|{
empty_stmt|;
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
literal|null
return|;
block|}
DECL|method|toString ()
specifier|public
specifier|abstract
name|String
name|toString
parameter_list|()
function_decl|;
block|}
end_class

end_unit

