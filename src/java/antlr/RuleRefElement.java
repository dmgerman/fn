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
DECL|class|RuleRefElement
class|class
name|RuleRefElement
extends|extends
name|AlternativeElement
block|{
DECL|field|targetRule
specifier|protected
name|String
name|targetRule
decl_stmt|;
comment|// which rule is being called?
DECL|field|args
specifier|protected
name|String
name|args
init|=
literal|null
decl_stmt|;
comment|// were any args passed to rule?
DECL|field|idAssign
specifier|protected
name|String
name|idAssign
init|=
literal|null
decl_stmt|;
comment|// is the return type assigned to a variable?
DECL|field|label
specifier|protected
name|String
name|label
decl_stmt|;
DECL|method|RuleRefElement (Grammar g, Token t, int autoGenType_)
specifier|public
name|RuleRefElement
parameter_list|(
name|Grammar
name|g
parameter_list|,
name|Token
name|t
parameter_list|,
name|int
name|autoGenType_
parameter_list|)
block|{
name|super
argument_list|(
name|g
argument_list|,
name|t
argument_list|,
name|autoGenType_
argument_list|)
expr_stmt|;
name|targetRule
operator|=
name|t
operator|.
name|getText
argument_list|()
expr_stmt|;
comment|//		if ( Character.isUpperCase(targetRule.charAt(0)) ) { // lexer rule?
if|if
condition|(
name|t
operator|.
name|type
operator|==
name|ANTLRTokenTypes
operator|.
name|TOKEN_REF
condition|)
block|{
comment|// lexer rule?
name|targetRule
operator|=
name|CodeGenerator
operator|.
name|encodeLexerRuleName
argument_list|(
name|targetRule
argument_list|)
expr_stmt|;
block|}
block|}
comment|//	public RuleRefElement(Grammar g, String t, int line, int autoGenType_) {
comment|//		super(g, autoGenType_);
comment|//		targetRule = t;
comment|//		if ( Character.isUpperCase(targetRule.charAt(0)) ) { // lexer rule?
comment|//			targetRule = CodeGenerator.lexerRuleName(targetRule);
comment|//		}
comment|//		this.line = line;
comment|//	}
DECL|method|generate ()
specifier|public
name|void
name|generate
parameter_list|()
block|{
name|grammar
operator|.
name|generator
operator|.
name|gen
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|getArgs ()
specifier|public
name|String
name|getArgs
parameter_list|()
block|{
return|return
name|args
return|;
block|}
DECL|method|getIdAssign ()
specifier|public
name|String
name|getIdAssign
parameter_list|()
block|{
return|return
name|idAssign
return|;
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
DECL|method|setArgs (String a)
specifier|public
name|void
name|setArgs
parameter_list|(
name|String
name|a
parameter_list|)
block|{
name|args
operator|=
name|a
expr_stmt|;
block|}
DECL|method|setIdAssign (String id)
specifier|public
name|void
name|setIdAssign
parameter_list|(
name|String
name|id
parameter_list|)
block|{
name|idAssign
operator|=
name|id
expr_stmt|;
block|}
DECL|method|setLabel (String label_)
specifier|public
name|void
name|setLabel
parameter_list|(
name|String
name|label_
parameter_list|)
block|{
name|label
operator|=
name|label_
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
if|if
condition|(
name|args
operator|!=
literal|null
condition|)
return|return
literal|" "
operator|+
name|targetRule
operator|+
name|args
return|;
else|else
return|return
literal|" "
operator|+
name|targetRule
return|;
block|}
block|}
end_class

end_unit

