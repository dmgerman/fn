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
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_comment
comment|/** Intermediate data class holds information about an alternative */
end_comment

begin_class
DECL|class|Alternative
class|class
name|Alternative
block|{
comment|// Tracking alternative linked list
DECL|field|head
name|AlternativeElement
name|head
decl_stmt|;
comment|// head of alt element list
DECL|field|tail
name|AlternativeElement
name|tail
decl_stmt|;
comment|// last element added
comment|// Syntactic predicate block if non-null
DECL|field|synPred
specifier|protected
name|SynPredBlock
name|synPred
decl_stmt|;
comment|// Semantic predicate action if non-null
DECL|field|semPred
specifier|protected
name|String
name|semPred
decl_stmt|;
comment|// Exception specification if non-null
DECL|field|exceptionSpec
specifier|protected
name|ExceptionSpec
name|exceptionSpec
decl_stmt|;
comment|// Init action if non-null;
DECL|field|cache
specifier|protected
name|Lookahead
index|[]
name|cache
decl_stmt|;
comment|// lookahead for alt.  Filled in by
comment|// deterministic() only!!!!!!!  Used for
comment|// code gen after calls to deterministic()
comment|// and used by deterministic for (...)*, (..)+,
comment|// and (..)? blocks.  1..k
DECL|field|lookaheadDepth
specifier|protected
name|int
name|lookaheadDepth
decl_stmt|;
comment|// each alt has different look depth possibly.
comment|// depth can be NONDETERMINISTIC too.
comment|// 0..n-1
comment|// If non-null, Tree specification ala -> A B C (not implemented)
DECL|field|treeSpecifier
specifier|protected
name|Token
name|treeSpecifier
init|=
literal|null
decl_stmt|;
comment|// True of AST generation is on for this alt
DECL|field|doAutoGen
specifier|private
name|boolean
name|doAutoGen
decl_stmt|;
DECL|method|Alternative ()
specifier|public
name|Alternative
parameter_list|()
block|{ 	}
DECL|method|Alternative (AlternativeElement firstElement)
specifier|public
name|Alternative
parameter_list|(
name|AlternativeElement
name|firstElement
parameter_list|)
block|{
name|addElement
argument_list|(
name|firstElement
argument_list|)
expr_stmt|;
block|}
DECL|method|addElement (AlternativeElement e)
specifier|public
name|void
name|addElement
parameter_list|(
name|AlternativeElement
name|e
parameter_list|)
block|{
comment|// Link the element into the list
if|if
condition|(
name|head
operator|==
literal|null
condition|)
block|{
name|head
operator|=
name|tail
operator|=
name|e
expr_stmt|;
block|}
else|else
block|{
name|tail
operator|.
name|next
operator|=
name|e
expr_stmt|;
name|tail
operator|=
name|e
expr_stmt|;
block|}
block|}
DECL|method|atStart ()
specifier|public
name|boolean
name|atStart
parameter_list|()
block|{
return|return
name|head
operator|==
literal|null
return|;
block|}
DECL|method|getAutoGen ()
specifier|public
name|boolean
name|getAutoGen
parameter_list|()
block|{
comment|// Don't build an AST if there is a tree-rewrite-specifier
return|return
name|doAutoGen
operator|&&
name|treeSpecifier
operator|==
literal|null
return|;
block|}
DECL|method|getTreeSpecifier ()
specifier|public
name|Token
name|getTreeSpecifier
parameter_list|()
block|{
return|return
name|treeSpecifier
return|;
block|}
DECL|method|setAutoGen (boolean doAutoGen_)
specifier|public
name|void
name|setAutoGen
parameter_list|(
name|boolean
name|doAutoGen_
parameter_list|)
block|{
name|doAutoGen
operator|=
name|doAutoGen_
expr_stmt|;
block|}
block|}
end_class

end_unit

