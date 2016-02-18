begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|AbstractUndoableEdit
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|groups
operator|.
name|structure
operator|.
name|ExplicitGroup
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  * @author jzieren  */
end_comment

begin_class
DECL|class|UndoableChangeAssignment
specifier|public
class|class
name|UndoableChangeAssignment
extends|extends
name|AbstractUndoableEdit
block|{
DECL|field|previousAssignments
specifier|private
specifier|final
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|previousAssignments
decl_stmt|;
DECL|field|newAssignments
specifier|private
specifier|final
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|newAssignments
decl_stmt|;
comment|/**      * The path to the edited node      */
DECL|field|pathToNode
specifier|private
name|int
index|[]
name|pathToNode
decl_stmt|;
comment|/**      * The root of the global groups tree      */
DECL|field|root
specifier|private
name|GroupTreeNode
name|root
decl_stmt|;
comment|/**      * @param node The node whose assignments were edited.      */
DECL|method|UndoableChangeAssignment (GroupTreeNode node, Set<BibEntry> previousAssignments, Set<BibEntry> newAssignments)
specifier|public
name|UndoableChangeAssignment
parameter_list|(
name|GroupTreeNode
name|node
parameter_list|,
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|previousAssignments
parameter_list|,
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|newAssignments
parameter_list|)
block|{
name|this
operator|.
name|previousAssignments
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|previousAssignments
argument_list|)
expr_stmt|;
name|this
operator|.
name|newAssignments
operator|=
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|newAssignments
argument_list|)
expr_stmt|;
name|this
operator|.
name|root
operator|=
operator|(
name|GroupTreeNode
operator|)
name|node
operator|.
name|getRoot
argument_list|()
expr_stmt|;
name|this
operator|.
name|pathToNode
operator|=
name|node
operator|.
name|getIndexedPath
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getUndoPresentationName ()
specifier|public
name|String
name|getUndoPresentationName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Undo"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"change assignment of entries"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getRedoPresentationName ()
specifier|public
name|String
name|getRedoPresentationName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Redo"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"change assignment of entries"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|undo ()
specifier|public
name|void
name|undo
parameter_list|()
block|{
name|super
operator|.
name|undo
argument_list|()
expr_stmt|;
name|GroupTreeNode
name|node
init|=
name|root
operator|.
name|getChildAt
argument_list|(
name|pathToNode
argument_list|)
decl_stmt|;
if|if
condition|(
name|node
operator|!=
literal|null
condition|)
block|{
name|ExplicitGroup
name|group
init|=
operator|(
name|ExplicitGroup
operator|)
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|group
operator|.
name|clearAssignments
argument_list|()
expr_stmt|;
for|for
control|(
specifier|final
name|BibEntry
name|entry
range|:
name|previousAssignments
control|)
block|{
name|group
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|redo ()
specifier|public
name|void
name|redo
parameter_list|()
block|{
name|super
operator|.
name|redo
argument_list|()
expr_stmt|;
name|GroupTreeNode
name|node
init|=
name|root
operator|.
name|getChildAt
argument_list|(
name|pathToNode
argument_list|)
decl_stmt|;
if|if
condition|(
name|node
operator|!=
literal|null
condition|)
block|{
name|ExplicitGroup
name|group
init|=
operator|(
name|ExplicitGroup
operator|)
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|group
operator|.
name|clearAssignments
argument_list|()
expr_stmt|;
for|for
control|(
specifier|final
name|BibEntry
name|entry
range|:
name|newAssignments
control|)
block|{
name|group
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

