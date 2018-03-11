begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
import|;
end_import

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
name|javafx
operator|.
name|concurrent
operator|.
name|Task
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
name|copyfiles
operator|.
name|CopyFilesDialogView
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
name|copyfiles
operator|.
name|CopyFilesResultItemViewModel
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
name|copyfiles
operator|.
name|CopyFilesResultListDependency
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
name|copyfiles
operator|.
name|CopyFilesTask
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
name|DirectoryDialogConfiguration
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|CopyFilesAction
specifier|public
class|class
name|CopyFilesAction
extends|extends
name|SimpleCommand
block|{
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|databaseContext
specifier|private
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|entries
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|CopyFilesAction (JabRefFrame frame)
specifier|public
name|CopyFilesAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|frame
operator|.
name|getDialogService
argument_list|()
expr_stmt|;
block|}
DECL|method|startServiceAndshowProgessDialog (Task<List<CopyFilesResultItemViewModel>> exportService)
specifier|private
name|void
name|startServiceAndshowProgessDialog
parameter_list|(
name|Task
argument_list|<
name|List
argument_list|<
name|CopyFilesResultItemViewModel
argument_list|>
argument_list|>
name|exportService
parameter_list|)
block|{
name|dialogService
operator|.
name|showCanceableProgressDialogAndWait
argument_list|(
name|exportService
argument_list|)
expr_stmt|;
name|exportService
operator|.
name|run
argument_list|()
expr_stmt|;
name|exportService
operator|.
name|setOnSucceeded
argument_list|(
parameter_list|(
name|e
parameter_list|)
lambda|->
block|{
name|showDialog
argument_list|(
name|exportService
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|showDialog (List<CopyFilesResultItemViewModel> data)
specifier|private
name|void
name|showDialog
parameter_list|(
name|List
argument_list|<
name|CopyFilesResultItemViewModel
argument_list|>
name|data
parameter_list|)
block|{
if|if
condition|(
name|data
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy linked files to folder..."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No linked files found for export."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|CopyFilesDialogView
name|dlg
init|=
operator|new
name|CopyFilesDialogView
argument_list|(
name|databaseContext
argument_list|,
operator|new
name|CopyFilesResultListDependency
argument_list|(
name|data
argument_list|)
argument_list|)
decl_stmt|;
name|dlg
operator|.
name|show
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
name|DirectoryDialogConfiguration
name|dirDialogConfiguration
init|=
operator|new
name|DirectoryDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_WORKING_DIRECTORY
argument_list|)
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|entries
operator|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getSelectedEntries
argument_list|()
expr_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|exportPath
init|=
name|dialogService
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|dirDialogConfiguration
argument_list|)
decl_stmt|;
name|exportPath
operator|.
name|ifPresent
argument_list|(
name|path
lambda|->
block|{
name|databaseContext
operator|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
expr_stmt|;
name|Task
argument_list|<
name|List
argument_list|<
name|CopyFilesResultItemViewModel
argument_list|>
argument_list|>
name|exportTask
init|=
operator|new
name|CopyFilesTask
argument_list|(
name|databaseContext
argument_list|,
name|entries
argument_list|,
name|path
argument_list|)
decl_stmt|;
name|startServiceAndshowProgessDialog
argument_list|(
name|exportTask
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

