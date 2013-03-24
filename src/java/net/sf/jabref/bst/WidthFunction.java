begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.bst
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bst
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Stack
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bst
operator|.
name|VM
operator|.
name|BstEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bst
operator|.
name|VM
operator|.
name|BstFunction
import|;
end_import

begin_comment
comment|/**  * The |built_in| function {\.{width\$}} pops the top (string) literal and  * pushes the integer that represents its width in units specified by the  * |char_width| array. This function takes the literal literally; that is, it  * assumes each character in the string is to be printed as is, regardless of  * whether the character has a special meaning to \TeX, except that special  * characters (even without their |right_brace|s) are handled specially. If the  * literal isn't a string, it complains and pushes~0.  *   *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|WidthFunction
specifier|public
class|class
name|WidthFunction
implements|implements
name|BstFunction
block|{
DECL|field|vm
name|VM
name|vm
decl_stmt|;
DECL|method|WidthFunction (VM vm)
specifier|public
name|WidthFunction
parameter_list|(
name|VM
name|vm
parameter_list|)
block|{
name|this
operator|.
name|vm
operator|=
name|vm
expr_stmt|;
block|}
DECL|method|execute (BstEntry context)
specifier|public
name|void
name|execute
parameter_list|(
name|BstEntry
name|context
parameter_list|)
block|{
name|Stack
argument_list|<
name|Object
argument_list|>
name|stack
init|=
name|vm
operator|.
name|getStack
argument_list|()
decl_stmt|;
if|if
condition|(
name|stack
operator|.
name|size
argument_list|()
operator|<
literal|1
condition|)
block|{
throw|throw
operator|new
name|VMException
argument_list|(
literal|"Not enough operands on stack for operation width$"
argument_list|)
throw|;
block|}
name|Object
name|o1
init|=
name|stack
operator|.
name|pop
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
operator|(
name|o1
operator|instanceof
name|String
operator|)
condition|)
block|{
name|vm
operator|.
name|warn
argument_list|(
literal|"A string is needed for change.case$"
argument_list|)
expr_stmt|;
name|stack
operator|.
name|push
argument_list|(
operator|new
name|Integer
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|stack
operator|.
name|push
argument_list|(
operator|new
name|Integer
argument_list|(
name|BibtexWidth
operator|.
name|width
argument_list|(
operator|(
name|String
operator|)
name|o1
argument_list|,
name|vm
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

