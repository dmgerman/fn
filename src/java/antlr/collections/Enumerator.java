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

begin_interface
DECL|interface|Enumerator
specifier|public
interface|interface
name|Enumerator
block|{
comment|/**Return the element under the cursor; return null if !valid() or      * if called before first next() call.      */
DECL|method|cursor ()
specifier|public
name|Object
name|cursor
parameter_list|()
function_decl|;
comment|/**Return the next element in the enumeration; first call to next()      * returns the first element.      */
DECL|method|next ()
specifier|public
name|Object
name|next
parameter_list|()
function_decl|;
comment|/**Any more elements in the enumeration? */
DECL|method|valid ()
specifier|public
name|boolean
name|valid
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

