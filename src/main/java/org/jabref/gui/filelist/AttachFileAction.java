begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.filelist
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|filelist
package|;
end_package

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
name|Globals
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
name|UndoableFieldChange
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
name|util
operator|.
name|FileDialogConfiguration
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
name|FieldChange
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
name|LinkedFile
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|AttachFileAction
specifier|public
class|class
name|AttachFileAction
extends|extends
name|SimpleCommand
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|AttachFileAction (BasePanel panel, DialogService dialogService)
specifier|public
name|AttachFileAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
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
if|if
condition|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires exactly one item to be selected."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|BibEntry
name|entry
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|newFile
lambda|->
block|{
name|LinkedFile
name|newLinkedFile
init|=
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
name|newFile
operator|.
name|toString
argument_list|()
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|LinkedFileEditDialogView
name|dialog
init|=
operator|new
name|LinkedFileEditDialogView
argument_list|(
name|newLinkedFile
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|LinkedFile
argument_list|>
name|editedLinkeDfile
init|=
name|dialog
operator|.
name|showAndWait
argument_list|()
decl_stmt|;
name|editedLinkeDfile
operator|.
name|ifPresent
argument_list|(
name|file
lambda|->
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|fieldChange
init|=
name|entry
operator|.
name|addFile
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|fieldChange
operator|.
name|ifPresent
argument_list|(
name|change
lambda|->
block|{
name|UndoableFieldChange
name|ce
init|=
operator|new
name|UndoableFieldChange
argument_list|(
name|change
argument_list|)
decl_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

