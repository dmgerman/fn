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
comment|//
end_comment

begin_comment
comment|// ANTLR C# Code Generator by Kunle Odutola : kunle UNDERSCORE odutola AT hotmail DOT com
end_comment

begin_comment
comment|//
end_comment

begin_class
DECL|class|CSharpBlockFinishingInfo
class|class
name|CSharpBlockFinishingInfo
block|{
DECL|field|postscript
name|String
name|postscript
decl_stmt|;
comment|// what to generate to terminate block
DECL|field|generatedSwitch
name|boolean
name|generatedSwitch
decl_stmt|;
comment|// did block finish with "default:" of switch?
DECL|field|generatedAnIf
name|boolean
name|generatedAnIf
decl_stmt|;
comment|/** When generating an if or switch, end-of-token lookahead sets 	 *  will become the else or default clause, don't generate an 	 *  error clause in this case. 	 */
DECL|field|needAnErrorClause
name|boolean
name|needAnErrorClause
decl_stmt|;
DECL|method|CSharpBlockFinishingInfo ()
specifier|public
name|CSharpBlockFinishingInfo
parameter_list|()
block|{
name|postscript
operator|=
literal|null
expr_stmt|;
name|generatedSwitch
operator|=
name|generatedSwitch
operator|=
literal|false
expr_stmt|;
name|needAnErrorClause
operator|=
literal|true
expr_stmt|;
block|}
DECL|method|CSharpBlockFinishingInfo (String ps, boolean genS, boolean generatedAnIf, boolean n)
specifier|public
name|CSharpBlockFinishingInfo
parameter_list|(
name|String
name|ps
parameter_list|,
name|boolean
name|genS
parameter_list|,
name|boolean
name|generatedAnIf
parameter_list|,
name|boolean
name|n
parameter_list|)
block|{
name|postscript
operator|=
name|ps
expr_stmt|;
name|generatedSwitch
operator|=
name|genS
expr_stmt|;
name|this
operator|.
name|generatedAnIf
operator|=
name|generatedAnIf
expr_stmt|;
name|needAnErrorClause
operator|=
name|n
expr_stmt|;
block|}
block|}
end_class

end_unit

