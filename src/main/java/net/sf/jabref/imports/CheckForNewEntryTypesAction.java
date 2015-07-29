begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
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
name|BasePanel
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
name|BibtexEntryType
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
name|Globals
import|;
end_import

begin_comment
comment|/**  * This action checks whether any new custom entry types were loaded from this  * bib file. If so, an offer to remember these entry types is given.  */
end_comment

begin_class
DECL|class|CheckForNewEntryTypesAction
specifier|public
class|class
name|CheckForNewEntryTypesAction
implements|implements
name|PostOpenAction
block|{
annotation|@
name|Override
DECL|method|isActionNecessary (ParserResult pr)
specifier|public
name|boolean
name|isActionNecessary
parameter_list|(
name|ParserResult
name|pr
parameter_list|)
block|{
comment|// See if any custom entry types were imported, but disregard those we already know:
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|pr
operator|.
name|getEntryTypes
argument_list|()
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|typeName
init|=
operator|(
name|i
operator|.
name|next
argument_list|()
operator|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|get
argument_list|(
name|typeName
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
return|return
operator|!
name|pr
operator|.
name|getEntryTypes
argument_list|()
operator|.
name|isEmpty
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|performAction (BasePanel panel, ParserResult pr)
specifier|public
name|void
name|performAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|)
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Custom entry types found in file"
argument_list|)
operator|+
literal|": "
argument_list|)
decl_stmt|;
name|Object
index|[]
name|types
init|=
name|pr
operator|.
name|getEntryTypes
argument_list|()
operator|.
name|keySet
argument_list|()
operator|.
name|toArray
argument_list|()
decl_stmt|;
name|Arrays
operator|.
name|sort
argument_list|(
name|types
argument_list|)
expr_stmt|;
for|for
control|(
name|Object
name|type
range|:
name|types
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|type
argument_list|)
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
block|}
name|String
name|s
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|s
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|s
operator|.
name|length
argument_list|()
operator|-
literal|2
argument_list|)
operator|+
literal|".\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remember these entry types?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Custom entry types"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
comment|// Import
for|for
control|(
name|BibtexEntryType
name|typ
range|:
name|pr
operator|.
name|getEntryTypes
argument_list|()
operator|.
name|values
argument_list|()
control|)
block|{
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|put
argument_list|(
name|typ
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|,
name|typ
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

