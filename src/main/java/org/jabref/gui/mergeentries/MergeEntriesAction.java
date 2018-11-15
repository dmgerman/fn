begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.mergeentries
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|mergeentries
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|BasePanel
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
name|DialogService
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
name|JabRefFrame
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
name|actions
operator|.
name|SimpleCommand
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
name|gui
operator|.
name|undo
operator|.
name|UndoableInsertEntry
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
name|UndoableRemoveEntry
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_class
DECL|class|MergeEntriesAction
specifier|public
class|class
name|MergeEntriesAction
extends|extends
name|SimpleCommand
block|{
DECL|field|jabRefFrame
specifier|private
specifier|final
name|JabRefFrame
name|jabRefFrame
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|MergeEntriesAction (JabRefFrame jabRefFrame)
specifier|public
name|MergeEntriesAction
parameter_list|(
name|JabRefFrame
name|jabRefFrame
parameter_list|)
block|{
name|this
operator|.
name|jabRefFrame
operator|=
name|jabRefFrame
expr_stmt|;
name|dialogService
operator|=
name|jabRefFrame
operator|.
name|getDialogService
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
name|BasePanel
name|basePanel
init|=
name|jabRefFrame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
comment|// Check if there are two entries selected
name|List
argument_list|<
name|BibEntry
argument_list|>
name|selectedEntries
init|=
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedEntries
operator|.
name|size
argument_list|()
operator|!=
literal|2
condition|)
block|{
comment|// Inform the user to select entries first.
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You have to choose exactly two entries to merge."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// Store the two entries
name|BibEntry
name|one
init|=
name|selectedEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibEntry
name|two
init|=
name|selectedEntries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|MergeEntriesDialog
name|dlg
init|=
operator|new
name|MergeEntriesDialog
argument_list|(
name|one
argument_list|,
name|two
argument_list|,
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMode
argument_list|()
argument_list|)
decl_stmt|;
name|dlg
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|mergedEntry
init|=
name|dlg
operator|.
name|showAndWait
argument_list|()
decl_stmt|;
if|if
condition|(
name|mergedEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|basePanel
operator|.
name|insertEntry
argument_list|(
name|mergedEntry
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
comment|// Create a new entry and add it to the undo stack
comment|// Remove the other two entries and add them to the undo stack (which is not working...)
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merge entries"
argument_list|)
argument_list|)
decl_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|basePanel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|mergedEntry
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|basePanel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|one
argument_list|,
name|basePanel
argument_list|)
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|getDatabase
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|one
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableRemoveEntry
argument_list|(
name|basePanel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|two
argument_list|,
name|basePanel
argument_list|)
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|getDatabase
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|two
argument_list|)
expr_stmt|;
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Merged entries"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Canceled merging entries"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
