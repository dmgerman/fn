begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.collections.impl
package|package
name|antlr
operator|.
name|collections
operator|.
name|impl
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_comment
comment|/**A linked list cell, which contains a ref to the object and next cell.  * The data,next members are public to this class, but not outside the  * collections.impl package.  *  * @author Terence Parr  *<a href=http://www.MageLang.com>MageLang Institute</a>  */
end_comment

begin_class
DECL|class|LLCell
class|class
name|LLCell
block|{
DECL|field|data
name|Object
name|data
decl_stmt|;
DECL|field|next
name|LLCell
name|next
decl_stmt|;
DECL|method|LLCell (Object o)
specifier|public
name|LLCell
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
name|data
operator|=
name|o
expr_stmt|;
block|}
block|}
end_class

end_unit

