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
name|AST
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|ASTEnumeration
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|ASTEnumerator
import|;
end_import

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

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Writer
import|;
end_import

begin_comment
comment|/**  * A Child-Sibling Tree.  *  * A tree with PLUS at the root and with two children 3 and 4 is  * structured as:  *  *		PLUS  *		  |  *		  3 -- 4  *  * and can be specified easily in LISP notation as  *  * (PLUS 3 4)  *  * where every '(' starts a new subtree.  *  * These trees are particular useful for translators because of  * the flexibility of the children lists.  They are also very easy  * to walk automatically, whereas trees with specific children  * reference fields can't easily be walked automatically.  *  * This class contains the basic support for an AST.  * Most people will create ASTs that are subclasses of  * BaseAST or of CommonAST.  */
end_comment

begin_class
DECL|class|BaseAST
specifier|public
specifier|abstract
class|class
name|BaseAST
implements|implements
name|AST
implements|,
name|Serializable
block|{
DECL|field|down
specifier|protected
name|BaseAST
name|down
decl_stmt|;
DECL|field|right
specifier|protected
name|BaseAST
name|right
decl_stmt|;
DECL|field|verboseStringConversion
specifier|private
specifier|static
name|boolean
name|verboseStringConversion
init|=
literal|false
decl_stmt|;
DECL|field|tokenNames
specifier|private
specifier|static
name|String
index|[]
name|tokenNames
init|=
literal|null
decl_stmt|;
comment|/**Add a node to the end of the child list for this node */
DECL|method|addChild (AST node)
specifier|public
name|void
name|addChild
parameter_list|(
name|AST
name|node
parameter_list|)
block|{
if|if
condition|(
name|node
operator|==
literal|null
condition|)
return|return;
name|BaseAST
name|t
init|=
name|this
operator|.
name|down
decl_stmt|;
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
while|while
condition|(
name|t
operator|.
name|right
operator|!=
literal|null
condition|)
block|{
name|t
operator|=
name|t
operator|.
name|right
expr_stmt|;
block|}
name|t
operator|.
name|right
operator|=
operator|(
name|BaseAST
operator|)
name|node
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|down
operator|=
operator|(
name|BaseAST
operator|)
name|node
expr_stmt|;
block|}
block|}
DECL|method|doWorkForFindAll (Vector v, AST target, boolean partialMatch)
specifier|private
name|void
name|doWorkForFindAll
parameter_list|(
name|Vector
name|v
parameter_list|,
name|AST
name|target
parameter_list|,
name|boolean
name|partialMatch
parameter_list|)
block|{
name|AST
name|sibling
decl_stmt|;
comment|// Start walking sibling lists, looking for matches.
name|siblingWalk
label|:
for|for
control|(
name|sibling
operator|=
name|this
init|;
name|sibling
operator|!=
literal|null
condition|;
name|sibling
operator|=
name|sibling
operator|.
name|getNextSibling
argument_list|()
control|)
block|{
if|if
condition|(
operator|(
name|partialMatch
operator|&&
name|sibling
operator|.
name|equalsTreePartial
argument_list|(
name|target
argument_list|)
operator|)
operator|||
operator|(
operator|!
name|partialMatch
operator|&&
name|sibling
operator|.
name|equalsTree
argument_list|(
name|target
argument_list|)
operator|)
condition|)
block|{
name|v
operator|.
name|appendElement
argument_list|(
name|sibling
argument_list|)
expr_stmt|;
block|}
comment|// regardless of match or not, check any children for matches
if|if
condition|(
name|sibling
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
operator|(
operator|(
name|BaseAST
operator|)
name|sibling
operator|.
name|getFirstChild
argument_list|()
operator|)
operator|.
name|doWorkForFindAll
argument_list|(
name|v
argument_list|,
name|target
argument_list|,
name|partialMatch
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/** Is node t equal to this in terms of token type and text? */
DECL|method|equals (AST t)
specifier|public
name|boolean
name|equals
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
if|if
condition|(
name|t
operator|==
literal|null
condition|)
return|return
literal|false
return|;
return|return
name|this
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
name|t
operator|.
name|getText
argument_list|()
argument_list|)
operator|&&
name|this
operator|.
name|getType
argument_list|()
operator|==
name|t
operator|.
name|getType
argument_list|()
return|;
block|}
comment|/** Is t an exact structural and equals() match of this tree.  The  *  'this' reference is considered the start of a sibling list.  */
DECL|method|equalsList (AST t)
specifier|public
name|boolean
name|equalsList
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
name|AST
name|sibling
decl_stmt|;
comment|// the empty tree is not a match of any non-null tree.
if|if
condition|(
name|t
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// Otherwise, start walking sibling lists.  First mismatch, return false.
for|for
control|(
name|sibling
operator|=
name|this
init|;
name|sibling
operator|!=
literal|null
operator|&&
name|t
operator|!=
literal|null
condition|;
name|sibling
operator|=
name|sibling
operator|.
name|getNextSibling
argument_list|()
operator|,
name|t
operator|=
name|t
operator|.
name|getNextSibling
argument_list|()
control|)
block|{
comment|// as a quick optimization, check roots first.
if|if
condition|(
operator|!
name|sibling
operator|.
name|equals
argument_list|(
name|t
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// if roots match, do full list match test on children.
if|if
condition|(
name|sibling
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|sibling
operator|.
name|getFirstChild
argument_list|()
operator|.
name|equalsList
argument_list|(
name|t
operator|.
name|getFirstChild
argument_list|()
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
comment|// sibling has no kids, make sure t doesn't either
elseif|else
if|if
condition|(
name|t
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
if|if
condition|(
name|sibling
operator|==
literal|null
operator|&&
name|t
operator|==
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
comment|// one sibling list has more than the other
return|return
literal|false
return|;
block|}
comment|/** Is 'sub' a subtree of this list?      *  The siblings of the root are NOT ignored.      */
DECL|method|equalsListPartial (AST sub)
specifier|public
name|boolean
name|equalsListPartial
parameter_list|(
name|AST
name|sub
parameter_list|)
block|{
name|AST
name|sibling
decl_stmt|;
comment|// the empty tree is always a subset of any tree.
if|if
condition|(
name|sub
operator|==
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
comment|// Otherwise, start walking sibling lists.  First mismatch, return false.
for|for
control|(
name|sibling
operator|=
name|this
init|;
name|sibling
operator|!=
literal|null
operator|&&
name|sub
operator|!=
literal|null
condition|;
name|sibling
operator|=
name|sibling
operator|.
name|getNextSibling
argument_list|()
operator|,
name|sub
operator|=
name|sub
operator|.
name|getNextSibling
argument_list|()
control|)
block|{
comment|// as a quick optimization, check roots first.
if|if
condition|(
operator|!
name|sibling
operator|.
name|equals
argument_list|(
name|sub
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// if roots match, do partial list match test on children.
if|if
condition|(
name|sibling
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|sibling
operator|.
name|getFirstChild
argument_list|()
operator|.
name|equalsListPartial
argument_list|(
name|sub
operator|.
name|getFirstChild
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
block|}
if|if
condition|(
name|sibling
operator|==
literal|null
operator|&&
name|sub
operator|!=
literal|null
condition|)
block|{
comment|// nothing left to match in this tree, but subtree has more
return|return
literal|false
return|;
block|}
comment|// either both are null or sibling has more, but subtree doesn't
return|return
literal|true
return|;
block|}
comment|/** Is tree rooted at 'this' equal to 't'?  The siblings      *  of 'this' are ignored.      */
DECL|method|equalsTree (AST t)
specifier|public
name|boolean
name|equalsTree
parameter_list|(
name|AST
name|t
parameter_list|)
block|{
comment|// check roots first.
if|if
condition|(
operator|!
name|this
operator|.
name|equals
argument_list|(
name|t
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// if roots match, do full list match test on children.
if|if
condition|(
name|this
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|this
operator|.
name|getFirstChild
argument_list|()
operator|.
name|equalsList
argument_list|(
name|t
operator|.
name|getFirstChild
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
comment|// sibling has no kids, make sure t doesn't either
elseif|else
if|if
condition|(
name|t
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
comment|/** Is 't' a subtree of the tree rooted at 'this'?  The siblings      *  of 'this' are ignored.       */
DECL|method|equalsTreePartial (AST sub)
specifier|public
name|boolean
name|equalsTreePartial
parameter_list|(
name|AST
name|sub
parameter_list|)
block|{
comment|// the empty tree is always a subset of any tree.
if|if
condition|(
name|sub
operator|==
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
comment|// check roots first.
if|if
condition|(
operator|!
name|this
operator|.
name|equals
argument_list|(
name|sub
argument_list|)
condition|)
return|return
literal|false
return|;
comment|// if roots match, do full list partial match test on children.
if|if
condition|(
name|this
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|this
operator|.
name|getFirstChild
argument_list|()
operator|.
name|equalsListPartial
argument_list|(
name|sub
operator|.
name|getFirstChild
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
comment|/** Walk the tree looking for all exact subtree matches.  Return      *  an ASTEnumerator that lets the caller walk the list      *  of subtree roots found herein.      */
DECL|method|findAll (AST target)
specifier|public
name|ASTEnumeration
name|findAll
parameter_list|(
name|AST
name|target
parameter_list|)
block|{
name|Vector
name|roots
init|=
operator|new
name|Vector
argument_list|(
literal|10
argument_list|)
decl_stmt|;
name|AST
name|sibling
decl_stmt|;
comment|// the empty tree cannot result in an enumeration
if|if
condition|(
name|target
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|doWorkForFindAll
argument_list|(
name|roots
argument_list|,
name|target
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// find all matches recursively
return|return
operator|new
name|ASTEnumerator
argument_list|(
name|roots
argument_list|)
return|;
block|}
comment|/** Walk the tree looking for all subtrees.  Return      *  an ASTEnumerator that lets the caller walk the list      *  of subtree roots found herein.      */
DECL|method|findAllPartial (AST sub)
specifier|public
name|ASTEnumeration
name|findAllPartial
parameter_list|(
name|AST
name|sub
parameter_list|)
block|{
name|Vector
name|roots
init|=
operator|new
name|Vector
argument_list|(
literal|10
argument_list|)
decl_stmt|;
name|AST
name|sibling
decl_stmt|;
comment|// the empty tree cannot result in an enumeration
if|if
condition|(
name|sub
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|doWorkForFindAll
argument_list|(
name|roots
argument_list|,
name|sub
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|// find all matches recursively
return|return
operator|new
name|ASTEnumerator
argument_list|(
name|roots
argument_list|)
return|;
block|}
comment|/** Get the first child of this node; null if not children */
DECL|method|getFirstChild ()
specifier|public
name|AST
name|getFirstChild
parameter_list|()
block|{
return|return
name|down
return|;
block|}
comment|/** Get the next sibling in line after this one */
DECL|method|getNextSibling ()
specifier|public
name|AST
name|getNextSibling
parameter_list|()
block|{
return|return
name|right
return|;
block|}
comment|/** Get the token text for this node */
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
comment|/** Get the token type for this node */
DECL|method|getType ()
specifier|public
name|int
name|getType
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
DECL|method|initialize (int t, String txt)
specifier|public
specifier|abstract
name|void
name|initialize
parameter_list|(
name|int
name|t
parameter_list|,
name|String
name|txt
parameter_list|)
function_decl|;
DECL|method|initialize (AST t)
specifier|public
specifier|abstract
name|void
name|initialize
parameter_list|(
name|AST
name|t
parameter_list|)
function_decl|;
DECL|method|initialize (Token t)
specifier|public
specifier|abstract
name|void
name|initialize
parameter_list|(
name|Token
name|t
parameter_list|)
function_decl|;
comment|/** Remove all children */
DECL|method|removeChildren ()
specifier|public
name|void
name|removeChildren
parameter_list|()
block|{
name|down
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|setFirstChild (AST c)
specifier|public
name|void
name|setFirstChild
parameter_list|(
name|AST
name|c
parameter_list|)
block|{
name|down
operator|=
operator|(
name|BaseAST
operator|)
name|c
expr_stmt|;
block|}
DECL|method|setNextSibling (AST n)
specifier|public
name|void
name|setNextSibling
parameter_list|(
name|AST
name|n
parameter_list|)
block|{
name|right
operator|=
operator|(
name|BaseAST
operator|)
name|n
expr_stmt|;
block|}
comment|/** Set the token text for this node */
DECL|method|setText (String text)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|text
parameter_list|)
block|{
empty_stmt|;
block|}
comment|/** Set the token type for this node */
DECL|method|setType (int ttype)
specifier|public
name|void
name|setType
parameter_list|(
name|int
name|ttype
parameter_list|)
block|{
empty_stmt|;
block|}
DECL|method|setVerboseStringConversion (boolean verbose, String[] names)
specifier|public
specifier|static
name|void
name|setVerboseStringConversion
parameter_list|(
name|boolean
name|verbose
parameter_list|,
name|String
index|[]
name|names
parameter_list|)
block|{
name|verboseStringConversion
operator|=
name|verbose
expr_stmt|;
name|tokenNames
operator|=
name|names
expr_stmt|;
block|}
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
name|StringBuffer
name|b
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
comment|// if verbose and type name not same as text (keyword probably)
if|if
condition|(
name|verboseStringConversion
operator|&&
operator|!
name|getText
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|tokenNames
index|[
name|getType
argument_list|()
index|]
argument_list|)
operator|&&
operator|!
name|getText
argument_list|()
operator|.
name|equalsIgnoreCase
argument_list|(
name|Tool
operator|.
name|stripFrontBack
argument_list|(
name|tokenNames
index|[
name|getType
argument_list|()
index|]
argument_list|,
literal|"\""
argument_list|,
literal|"\""
argument_list|)
argument_list|)
condition|)
block|{
name|b
operator|.
name|append
argument_list|(
literal|'['
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
literal|",<"
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|tokenNames
index|[
name|getType
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
literal|">]"
argument_list|)
expr_stmt|;
return|return
name|b
operator|.
name|toString
argument_list|()
return|;
block|}
return|return
name|getText
argument_list|()
return|;
block|}
comment|/** Print out a child-sibling tree in LISP notation */
DECL|method|toStringList ()
specifier|public
name|String
name|toStringList
parameter_list|()
block|{
name|AST
name|t
init|=
name|this
decl_stmt|;
name|String
name|ts
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|t
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
name|ts
operator|+=
literal|" ("
expr_stmt|;
name|ts
operator|+=
literal|" "
operator|+
name|this
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|t
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|ts
operator|+=
operator|(
operator|(
name|BaseAST
operator|)
name|t
operator|.
name|getFirstChild
argument_list|()
operator|)
operator|.
name|toStringList
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|t
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
name|ts
operator|+=
literal|" )"
expr_stmt|;
if|if
condition|(
name|t
operator|.
name|getNextSibling
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|ts
operator|+=
operator|(
operator|(
name|BaseAST
operator|)
name|t
operator|.
name|getNextSibling
argument_list|()
operator|)
operator|.
name|toStringList
argument_list|()
expr_stmt|;
block|}
return|return
name|ts
return|;
block|}
DECL|method|toStringTree ()
specifier|public
name|String
name|toStringTree
parameter_list|()
block|{
name|AST
name|t
init|=
name|this
decl_stmt|;
name|String
name|ts
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|t
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
name|ts
operator|+=
literal|" ("
expr_stmt|;
name|ts
operator|+=
literal|" "
operator|+
name|this
operator|.
name|toString
argument_list|()
expr_stmt|;
if|if
condition|(
name|t
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|ts
operator|+=
operator|(
operator|(
name|BaseAST
operator|)
name|t
operator|.
name|getFirstChild
argument_list|()
operator|)
operator|.
name|toStringList
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|t
operator|.
name|getFirstChild
argument_list|()
operator|!=
literal|null
condition|)
name|ts
operator|+=
literal|" )"
expr_stmt|;
return|return
name|ts
return|;
block|}
DECL|method|decode (String text)
specifier|public
specifier|static
name|String
name|decode
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|char
name|c
decl_stmt|,
name|c1
decl_stmt|,
name|c2
decl_stmt|,
name|c3
decl_stmt|,
name|c4
decl_stmt|,
name|c5
decl_stmt|;
name|StringBuffer
name|n
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|text
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|==
literal|'&'
condition|)
block|{
name|c1
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
expr_stmt|;
name|c2
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|2
argument_list|)
expr_stmt|;
name|c3
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|3
argument_list|)
expr_stmt|;
name|c4
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|4
argument_list|)
expr_stmt|;
name|c5
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|5
argument_list|)
expr_stmt|;
if|if
condition|(
name|c1
operator|==
literal|'a'
operator|&&
name|c2
operator|==
literal|'m'
operator|&&
name|c3
operator|==
literal|'p'
operator|&&
name|c4
operator|==
literal|';'
condition|)
block|{
name|n
operator|.
name|append
argument_list|(
literal|"&"
argument_list|)
expr_stmt|;
name|i
operator|+=
literal|5
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c1
operator|==
literal|'l'
operator|&&
name|c2
operator|==
literal|'t'
operator|&&
name|c3
operator|==
literal|';'
condition|)
block|{
name|n
operator|.
name|append
argument_list|(
literal|"<"
argument_list|)
expr_stmt|;
name|i
operator|+=
literal|4
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c1
operator|==
literal|'g'
operator|&&
name|c2
operator|==
literal|'t'
operator|&&
name|c3
operator|==
literal|';'
condition|)
block|{
name|n
operator|.
name|append
argument_list|(
literal|">"
argument_list|)
expr_stmt|;
name|i
operator|+=
literal|4
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c1
operator|==
literal|'q'
operator|&&
name|c2
operator|==
literal|'u'
operator|&&
name|c3
operator|==
literal|'o'
operator|&&
name|c4
operator|==
literal|'t'
operator|&&
name|c5
operator|==
literal|';'
condition|)
block|{
name|n
operator|.
name|append
argument_list|(
literal|"\""
argument_list|)
expr_stmt|;
name|i
operator|+=
literal|6
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c1
operator|==
literal|'a'
operator|&&
name|c2
operator|==
literal|'p'
operator|&&
name|c3
operator|==
literal|'o'
operator|&&
name|c4
operator|==
literal|'s'
operator|&&
name|c5
operator|==
literal|';'
condition|)
block|{
name|n
operator|.
name|append
argument_list|(
literal|"'"
argument_list|)
expr_stmt|;
name|i
operator|+=
literal|6
expr_stmt|;
block|}
else|else
name|n
operator|.
name|append
argument_list|(
literal|"&"
argument_list|)
expr_stmt|;
block|}
else|else
name|n
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|String
argument_list|(
name|n
argument_list|)
return|;
block|}
DECL|method|encode (String text)
specifier|public
specifier|static
name|String
name|encode
parameter_list|(
name|String
name|text
parameter_list|)
block|{
name|char
name|c
decl_stmt|;
name|StringBuffer
name|n
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|text
operator|.
name|length
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|c
operator|=
name|text
operator|.
name|charAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|c
condition|)
block|{
case|case
literal|'&'
case|:
block|{
name|n
operator|.
name|append
argument_list|(
literal|"&amp;"
argument_list|)
expr_stmt|;
break|break;
block|}
case|case
literal|'<'
case|:
block|{
name|n
operator|.
name|append
argument_list|(
literal|"&lt;"
argument_list|)
expr_stmt|;
break|break;
block|}
case|case
literal|'>'
case|:
block|{
name|n
operator|.
name|append
argument_list|(
literal|"&gt;"
argument_list|)
expr_stmt|;
break|break;
block|}
case|case
literal|'"'
case|:
block|{
name|n
operator|.
name|append
argument_list|(
literal|"&quot;"
argument_list|)
expr_stmt|;
break|break;
block|}
case|case
literal|'\''
case|:
block|{
name|n
operator|.
name|append
argument_list|(
literal|"&apos;"
argument_list|)
expr_stmt|;
break|break;
block|}
default|default :
block|{
name|n
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
return|return
operator|new
name|String
argument_list|(
name|n
argument_list|)
return|;
block|}
DECL|method|xmlSerializeNode (Writer out)
specifier|public
name|void
name|xmlSerializeNode
parameter_list|(
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"<"
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" "
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"text=\""
operator|+
name|encode
argument_list|(
name|getText
argument_list|()
argument_list|)
operator|+
literal|"\" type=\""
operator|+
name|getType
argument_list|()
operator|+
literal|"\"/>"
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|buf
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|xmlSerializeRootOpen (Writer out)
specifier|public
name|void
name|xmlSerializeRootOpen
parameter_list|(
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|StringBuffer
name|buf
init|=
operator|new
name|StringBuffer
argument_list|(
literal|100
argument_list|)
decl_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"<"
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|" "
argument_list|)
expr_stmt|;
name|buf
operator|.
name|append
argument_list|(
literal|"text=\""
operator|+
name|encode
argument_list|(
name|getText
argument_list|()
argument_list|)
operator|+
literal|"\" type=\""
operator|+
name|getType
argument_list|()
operator|+
literal|"\">\n"
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|buf
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|xmlSerializeRootClose (Writer out)
specifier|public
name|void
name|xmlSerializeRootClose
parameter_list|(
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
name|out
operator|.
name|write
argument_list|(
literal|"</"
operator|+
name|getClass
argument_list|()
operator|.
name|getName
argument_list|()
operator|+
literal|">\n"
argument_list|)
expr_stmt|;
block|}
DECL|method|xmlSerialize (Writer out)
specifier|public
name|void
name|xmlSerialize
parameter_list|(
name|Writer
name|out
parameter_list|)
throws|throws
name|IOException
block|{
comment|// print out this node and all siblings
for|for
control|(
name|AST
name|node
init|=
name|this
init|;
name|node
operator|!=
literal|null
condition|;
name|node
operator|=
name|node
operator|.
name|getNextSibling
argument_list|()
control|)
block|{
if|if
condition|(
name|node
operator|.
name|getFirstChild
argument_list|()
operator|==
literal|null
condition|)
block|{
comment|// print guts (class name, attributes)
operator|(
operator|(
name|BaseAST
operator|)
name|node
operator|)
operator|.
name|xmlSerializeNode
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
else|else
block|{
operator|(
operator|(
name|BaseAST
operator|)
name|node
operator|)
operator|.
name|xmlSerializeRootOpen
argument_list|(
name|out
argument_list|)
expr_stmt|;
comment|// print children
operator|(
operator|(
name|BaseAST
operator|)
name|node
operator|.
name|getFirstChild
argument_list|()
operator|)
operator|.
name|xmlSerialize
argument_list|(
name|out
argument_list|)
expr_stmt|;
comment|// print end tag
operator|(
operator|(
name|BaseAST
operator|)
name|node
operator|)
operator|.
name|xmlSerializeRootClose
argument_list|(
name|out
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

