begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.collab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|collab
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
operator|.
name|comparator
operator|.
name|GroupDiff
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
operator|.
name|DefaultGroupsFactory
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabaseContext
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|GroupTreeNode
import|;
end_import

begin_class
DECL|class|GroupChangeViewModel
class|class
name|GroupChangeViewModel
extends|extends
name|DatabaseChangeViewModel
block|{
DECL|field|changedGroups
specifier|private
specifier|final
name|GroupTreeNode
name|changedGroups
decl_stmt|;
DECL|method|GroupChangeViewModel (GroupDiff diff)
specifier|public
name|GroupChangeViewModel
parameter_list|(
name|GroupDiff
name|diff
parameter_list|)
block|{
name|super
argument_list|(
name|diff
operator|.
name|getOriginalGroupRoot
argument_list|()
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
name|diff
operator|.
name|getNewGroupRoot
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|makeChange (BibDatabaseContext database, NamedCompound undoEdit)
specifier|public
name|void
name|makeChange
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|NamedCompound
name|undoEdit
parameter_list|)
block|{
name|GroupTreeNode
name|root
init|=
name|database
operator|.
name|getMetaData
argument_list|()
operator|.
name|getGroups
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|root
operator|==
literal|null
condition|)
block|{
name|root
operator|=
operator|new
name|GroupTreeNode
argument_list|(
name|DefaultGroupsFactory
operator|.
name|getAllEntriesGroup
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|getMetaData
argument_list|()
operator|.
name|setGroups
argument_list|(
name|root
argument_list|)
expr_stmt|;
block|}
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
name|database
operator|.
name|getMetaData
argument_list|()
operator|.
name|getGroups
argument_list|()
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
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
name|DefaultGroupsFactory
operator|.
name|getAllEntriesGroup
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
block|}
name|undoEdit
operator|.
name|addEdit
argument_list|(
name|undo
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|description ()
specifier|public
name|Node
name|description
parameter_list|()
block|{
return|return
operator|new
name|Label
argument_list|(
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
argument_list|)
return|;
block|}
block|}
end_class

end_unit

