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

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|List
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|Stack
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|NoSuchElementException
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
name|LLCell
import|;
end_import

begin_comment
comment|/**An enumeration of a LList.  Maintains a cursor through the list.  * bad things would happen if the list changed via another thread  * while we were walking this list.  */
end_comment

begin_class
DECL|class|LLEnumeration
specifier|final
class|class
name|LLEnumeration
implements|implements
name|Enumeration
block|{
DECL|field|cursor
name|LLCell
name|cursor
decl_stmt|;
DECL|field|list
name|LList
name|list
decl_stmt|;
comment|/**Create an enumeration attached to a LList*/
DECL|method|LLEnumeration (LList l)
specifier|public
name|LLEnumeration
parameter_list|(
name|LList
name|l
parameter_list|)
block|{
name|list
operator|=
name|l
expr_stmt|;
name|cursor
operator|=
name|list
operator|.
name|head
expr_stmt|;
block|}
comment|/** Return true/false depending on whether there are more      * elements to enumerate.      */
DECL|method|hasMoreElements ()
specifier|public
name|boolean
name|hasMoreElements
parameter_list|()
block|{
if|if
condition|(
name|cursor
operator|!=
literal|null
condition|)
return|return
literal|true
return|;
else|else
return|return
literal|false
return|;
block|}
comment|/**Get the next element in the enumeration.  Destructive in that      * the returned element is removed from the enumeration.  This      * does not affect the list itself.      * @return the next object in the enumeration.      */
DECL|method|nextElement ()
specifier|public
name|Object
name|nextElement
parameter_list|()
block|{
if|if
condition|(
operator|!
name|hasMoreElements
argument_list|()
condition|)
throw|throw
operator|new
name|NoSuchElementException
argument_list|()
throw|;
name|LLCell
name|p
init|=
name|cursor
decl_stmt|;
name|cursor
operator|=
name|cursor
operator|.
name|next
expr_stmt|;
return|return
name|p
operator|.
name|data
return|;
block|}
block|}
end_class

end_unit

