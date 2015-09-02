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
comment|/**  *   * The |built_in| function {\.{purify\$}} pops the top (string) literal, removes  * nonalphanumeric characters except for |white_space| and |sep_char| characters  * (these get converted to a |space|) and removes certain alphabetic characters  * contained in the control sequences associated with a special character, and  * pushes the resulting string. If the literal isn't a string, it complains and  * pushes the null string.  *   */
end_comment

begin_class
DECL|class|PurifyFunction
specifier|public
class|class
name|PurifyFunction
implements|implements
name|BstFunction
block|{
DECL|field|vm
specifier|private
specifier|final
name|VM
name|vm
decl_stmt|;
DECL|method|PurifyFunction (VM vm)
specifier|public
name|PurifyFunction
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
annotation|@
name|Override
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
literal|"Not enough operands on stack for operation purify$"
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
literal|"A string is needed for purify$"
argument_list|)
expr_stmt|;
name|stack
operator|.
name|push
argument_list|(
literal|""
argument_list|)
expr_stmt|;
return|return;
block|}
name|stack
operator|.
name|push
argument_list|(
name|BibtexPurify
operator|.
name|purify
argument_list|(
operator|(
name|String
operator|)
name|o1
argument_list|,
name|vm
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

