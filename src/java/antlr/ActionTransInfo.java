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
comment|/**  * This class contains information about how an action  * was translated (using the AST conversion rules).  */
end_comment

begin_class
DECL|class|ActionTransInfo
specifier|public
class|class
name|ActionTransInfo
block|{
DECL|field|assignToRoot
specifier|public
name|boolean
name|assignToRoot
init|=
literal|false
decl_stmt|;
comment|// somebody did a "#rule = "
DECL|field|refRuleRoot
specifier|public
name|String
name|refRuleRoot
init|=
literal|null
decl_stmt|;
comment|// somebody referenced #rule; string is translated var
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
literal|"assignToRoot:"
operator|+
name|assignToRoot
operator|+
literal|", refRuleRoot:"
operator|+
name|refRuleRoot
return|;
block|}
block|}
end_class

end_unit

