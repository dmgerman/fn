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
name|NoSuchElementException
import|;
end_import

begin_comment
comment|/** A simple stack definition; restrictive in that you cannot  * access arbitrary stack elements.  *  * @author Terence Parr  *<a href=http://www.MageLang.com>MageLang Institute</a>  */
end_comment

begin_interface
DECL|interface|Stack
specifier|public
interface|interface
name|Stack
block|{
DECL|method|height ()
specifier|public
name|int
name|height
parameter_list|()
function_decl|;
DECL|method|pop ()
specifier|public
name|Object
name|pop
parameter_list|()
throws|throws
name|NoSuchElementException
function_decl|;
DECL|method|push (Object o)
specifier|public
name|void
name|push
parameter_list|(
name|Object
name|o
parameter_list|)
function_decl|;
DECL|method|top ()
specifier|public
name|Object
name|top
parameter_list|()
throws|throws
name|NoSuchElementException
function_decl|;
block|}
end_interface

end_unit

