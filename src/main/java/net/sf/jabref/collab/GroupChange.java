begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
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
name|gui
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
name|gui
operator|.
name|groups
operator|.
name|GroupTreeNodeViewModel
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
name|gui
operator|.
name|groups
operator|.
name|UndoableModifySubtree
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
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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
name|groups
operator|.
name|AllEntriesGroup
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
name|groups
operator|.
name|GroupTreeNode
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
name|database
operator|.
name|BibDatabase
import|;
end_import

begin_class
DECL|class|GroupChange
class|class
name|GroupChange
extends|extends
name|Change
block|{
DECL|field|changedGroups
specifier|private
specifier|final
name|GroupTreeNode
name|changedGroups
decl_stmt|;
DECL|field|tmpGroupRoot
specifier|private
specifier|final
name|GroupTreeNode
name|tmpGroupRoot
decl_stmt|;
DECL|method|GroupChange (GroupTreeNode changedGroups, GroupTreeNode tmpGroupRoot)
specifier|public
name|GroupChange
parameter_list|(
name|GroupTreeNode
name|changedGroups
parameter_list|,
name|GroupTreeNode
name|tmpGroupRoot
parameter_list|)
block|{
name|super
argument_list|(
name|changedGroups
operator|==
literal|null
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"Removed all groups"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified groups tree"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|changedGroups
operator|=
name|changedGroups
expr_stmt|;
name|this
operator|.
name|tmpGroupRoot
operator|=
name|tmpGroupRoot
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BasePanel panel, BibDatabase secondary, NamedCompound undoEdit)
specifier|public
name|boolean
name|makeChange
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|BibDatabase
name|secondary
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
specifier|final
name|GroupTreeNode
name|root
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getGroups
argument_list|()
decl_stmt|;
specifier|final
name|UndoableModifySubtree
name|undo
init|=
operator|new
name|UndoableModifySubtree
argument_list|(
operator|new
name|GroupTreeNodeViewModel
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getGroups
argument_list|()
argument_list|)
argument_list|,
operator|new
name|GroupTreeNodeViewModel
argument_list|(
name|root
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modified groups"
argument_list|)
argument_list|)
decl_stmt|;
name|root
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
if|if
condition|(
name|changedGroups
operator|==
literal|null
condition|)
block|{
comment|// I think setting root to null is not possible
name|root
operator|.
name|setGroup
argument_list|(
operator|new
name|AllEntriesGroup
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// change root group, even though it'll be AllEntries anyway
name|root
operator|.
name|setGroup
argument_list|(
name|changedGroups
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|changedGroups
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|child
operator|.
name|copySubtree
argument_list|()
operator|.
name|moveTo
argument_list|(
name|root
argument_list|)
expr_stmt|;
block|}
comment|// the group tree is now appled to a different BibDatabase than it was created
comment|// for, which affects groups such as ExplicitGroup (which links to BibEntry objects).
comment|// We must traverse the tree and refresh all groups:
name|root
operator|.
name|refreshGroupsForNewDatabase
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|undoEdit
operator|.
name|addEdit
argument_list|(
name|undo
argument_list|)
expr_stmt|;
comment|// Update tmp database:
name|tmpGroupRoot
operator|.
name|removeAllChildren
argument_list|()
expr_stmt|;
if|if
condition|(
name|changedGroups
operator|!=
literal|null
condition|)
block|{
name|GroupTreeNode
name|copied
init|=
name|changedGroups
operator|.
name|copySubtree
argument_list|()
decl_stmt|;
name|tmpGroupRoot
operator|.
name|setGroup
argument_list|(
name|copied
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|GroupTreeNode
name|child
range|:
name|copied
operator|.
name|getChildren
argument_list|()
control|)
block|{
name|child
operator|.
name|copySubtree
argument_list|()
operator|.
name|moveTo
argument_list|(
name|tmpGroupRoot
argument_list|)
expr_stmt|;
block|}
block|}
name|tmpGroupRoot
operator|.
name|refreshGroupsForNewDatabase
argument_list|(
name|secondary
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|description ()
specifier|public
name|JComponent
name|description
parameter_list|()
block|{
return|return
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|toString
argument_list|()
operator|+
literal|'.'
operator|+
operator|(
name|changedGroups
operator|==
literal|null
condition|?
literal|""
else|:
literal|' '
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Accepting the change replaces the complete groups tree with the externally modified groups tree."
argument_list|)
operator|)
operator|+
literal|"</html>"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

