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

begin_class
DECL|class|AlternativeElement
specifier|abstract
class|class
name|AlternativeElement
extends|extends
name|GrammarElement
block|{
DECL|field|next
name|AlternativeElement
name|next
decl_stmt|;
DECL|field|autoGenType
specifier|protected
name|int
name|autoGenType
init|=
name|AUTO_GEN_NONE
decl_stmt|;
DECL|field|enclosingRuleName
specifier|protected
name|String
name|enclosingRuleName
decl_stmt|;
DECL|method|AlternativeElement (Grammar g)
specifier|public
name|AlternativeElement
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
block|}
DECL|method|AlternativeElement (Grammar g, int autoGenType_)
specifier|public
name|AlternativeElement
parameter_list|(
name|Grammar
name|g
parameter_list|,
name|int
name|autoGenType_
parameter_list|)
block|{
name|super
argument_list|(
name|g
argument_list|)
expr_stmt|;
name|autoGenType
operator|=
name|autoGenType_
expr_stmt|;
block|}
DECL|method|getAutoGenType ()
specifier|public
name|int
name|getAutoGenType
parameter_list|()
block|{
return|return
name|autoGenType
return|;
block|}
DECL|method|getLabel ()
specifier|public
name|String
name|getLabel
parameter_list|()
block|{
return|return
literal|null
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
block|{}
block|}
end_class

end_unit

