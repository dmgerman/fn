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

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|Vector
import|;
end_import

begin_class
DECL|class|RuleSymbol
class|class
name|RuleSymbol
extends|extends
name|GrammarSymbol
block|{
DECL|field|block
name|RuleBlock
name|block
decl_stmt|;
comment|// list of alternatives
DECL|field|defined
name|boolean
name|defined
decl_stmt|;
comment|// has the rule been defined yet?
DECL|field|references
name|Vector
name|references
decl_stmt|;
comment|// list of all nodes referencing this rule
comment|// not strictly needed by generic symbol table
comment|// but we will almost always analyze/gen code
DECL|field|access
name|String
name|access
decl_stmt|;
comment|// access specifier for this rule
DECL|field|comment
name|String
name|comment
decl_stmt|;
comment|// A javadoc comment if any.
DECL|method|RuleSymbol (String r)
specifier|public
name|RuleSymbol
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
name|references
operator|=
operator|new
name|Vector
argument_list|()
expr_stmt|;
block|}
DECL|method|addReference (RuleRefElement e)
specifier|public
name|void
name|addReference
parameter_list|(
name|RuleRefElement
name|e
parameter_list|)
block|{
name|references
operator|.
name|appendElement
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
DECL|method|getBlock ()
specifier|public
name|RuleBlock
name|getBlock
parameter_list|()
block|{
return|return
name|block
return|;
block|}
DECL|method|getReference (int i)
specifier|public
name|RuleRefElement
name|getReference
parameter_list|(
name|int
name|i
parameter_list|)
block|{
return|return
operator|(
name|RuleRefElement
operator|)
name|references
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
return|;
block|}
DECL|method|isDefined ()
specifier|public
name|boolean
name|isDefined
parameter_list|()
block|{
return|return
name|defined
return|;
block|}
DECL|method|numReferences ()
specifier|public
name|int
name|numReferences
parameter_list|()
block|{
return|return
name|references
operator|.
name|size
argument_list|()
return|;
block|}
DECL|method|setBlock (RuleBlock rb)
specifier|public
name|void
name|setBlock
parameter_list|(
name|RuleBlock
name|rb
parameter_list|)
block|{
name|block
operator|=
name|rb
expr_stmt|;
block|}
DECL|method|setDefined ()
specifier|public
name|void
name|setDefined
parameter_list|()
block|{
name|defined
operator|=
literal|true
expr_stmt|;
block|}
block|}
end_class

end_unit

