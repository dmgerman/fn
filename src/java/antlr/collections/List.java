begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr.collections
package|package
name|antlr
operator|.
name|collections
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

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

begin_comment
comment|/**A simple List interface that describes operations  * on a list.  */
end_comment

begin_interface
DECL|interface|List
specifier|public
interface|interface
name|List
block|{
DECL|method|add (Object o)
specifier|public
name|void
name|add
parameter_list|(
name|Object
name|o
parameter_list|)
function_decl|;
comment|// can insert at head or append.
DECL|method|append (Object o)
specifier|public
name|void
name|append
parameter_list|(
name|Object
name|o
parameter_list|)
function_decl|;
DECL|method|elementAt (int index)
specifier|public
name|Object
name|elementAt
parameter_list|(
name|int
name|index
parameter_list|)
throws|throws
name|NoSuchElementException
function_decl|;
DECL|method|elements ()
specifier|public
name|Enumeration
name|elements
parameter_list|()
function_decl|;
DECL|method|includes (Object o)
specifier|public
name|boolean
name|includes
parameter_list|(
name|Object
name|o
parameter_list|)
function_decl|;
DECL|method|length ()
specifier|public
name|int
name|length
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

