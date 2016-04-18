begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
package|;
end_package

begin_class
DECL|class|MoveGroupChange
specifier|public
class|class
name|MoveGroupChange
block|{
DECL|field|oldParent
specifier|private
name|GroupTreeNode
name|oldParent
decl_stmt|;
DECL|field|oldChildIndex
specifier|private
name|int
name|oldChildIndex
decl_stmt|;
DECL|field|newParent
specifier|private
name|GroupTreeNode
name|newParent
decl_stmt|;
DECL|field|newChildIndex
specifier|private
name|int
name|newChildIndex
decl_stmt|;
comment|/**      * @param oldParent      * @param oldChildIndex      * @param newParent The new parent node to which the node will be moved.      * @param newChildIndex The child index at newParent to which the node will be moved.      */
DECL|method|MoveGroupChange (GroupTreeNode oldParent, int oldChildIndex, GroupTreeNode newParent, int newChildIndex)
specifier|public
name|MoveGroupChange
parameter_list|(
name|GroupTreeNode
name|oldParent
parameter_list|,
name|int
name|oldChildIndex
parameter_list|,
name|GroupTreeNode
name|newParent
parameter_list|,
name|int
name|newChildIndex
parameter_list|)
block|{
name|this
operator|.
name|oldParent
operator|=
name|oldParent
expr_stmt|;
name|this
operator|.
name|oldChildIndex
operator|=
name|oldChildIndex
expr_stmt|;
name|this
operator|.
name|newParent
operator|=
name|newParent
expr_stmt|;
name|this
operator|.
name|newChildIndex
operator|=
name|newChildIndex
expr_stmt|;
block|}
DECL|method|getOldParent ()
specifier|public
name|GroupTreeNode
name|getOldParent
parameter_list|()
block|{
return|return
name|oldParent
return|;
block|}
DECL|method|getOldChildIndex ()
specifier|public
name|int
name|getOldChildIndex
parameter_list|()
block|{
return|return
name|oldChildIndex
return|;
block|}
DECL|method|getNewParent ()
specifier|public
name|GroupTreeNode
name|getNewParent
parameter_list|()
block|{
return|return
name|newParent
return|;
block|}
DECL|method|getNewChildIndex ()
specifier|public
name|int
name|getNewChildIndex
parameter_list|()
block|{
return|return
name|newChildIndex
return|;
block|}
block|}
end_class

end_unit

