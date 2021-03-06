begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.help
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|help
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|inject
operator|.
name|Inject
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|FXML
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
name|ButtonType
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
name|TextArea
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
name|ClipBoardManager
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
name|util
operator|.
name|BaseDialog
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
name|ControlHelper
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
name|logic
operator|.
name|util
operator|.
name|BuildInfo
import|;
end_import

begin_import
import|import
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|ViewLoader
import|;
end_import

begin_class
DECL|class|AboutDialogView
specifier|public
class|class
name|AboutDialogView
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|copyVersionButton
annotation|@
name|FXML
specifier|private
name|ButtonType
name|copyVersionButton
decl_stmt|;
DECL|field|textAreaVersions
annotation|@
name|FXML
specifier|private
name|TextArea
name|textAreaVersions
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|clipBoardManager
annotation|@
name|Inject
specifier|private
name|ClipBoardManager
name|clipBoardManager
decl_stmt|;
DECL|field|buildInfo
annotation|@
name|Inject
specifier|private
name|BuildInfo
name|buildInfo
decl_stmt|;
DECL|field|viewModel
specifier|private
name|AboutDialogViewModel
name|viewModel
decl_stmt|;
DECL|method|AboutDialogView ()
specifier|public
name|AboutDialogView
parameter_list|()
block|{
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"About JabRef"
argument_list|)
argument_list|)
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
operator|.
name|setAsDialogPane
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|setAction
argument_list|(
name|copyVersionButton
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
name|copyVersionToClipboard
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getViewModel ()
specifier|public
name|AboutDialogViewModel
name|getViewModel
parameter_list|()
block|{
return|return
name|viewModel
return|;
block|}
annotation|@
name|FXML
DECL|method|initialize ()
specifier|private
name|void
name|initialize
parameter_list|()
block|{
name|viewModel
operator|=
operator|new
name|AboutDialogViewModel
argument_list|(
name|dialogService
argument_list|,
name|clipBoardManager
argument_list|,
name|buildInfo
argument_list|)
expr_stmt|;
name|textAreaVersions
operator|.
name|setText
argument_list|(
name|viewModel
operator|.
name|getVersionInfo
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|copyVersionToClipboard ()
specifier|private
name|void
name|copyVersionToClipboard
parameter_list|()
block|{
name|viewModel
operator|.
name|copyVersionToClipboard
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openJabrefWebsite ()
specifier|private
name|void
name|openJabrefWebsite
parameter_list|()
block|{
name|viewModel
operator|.
name|openJabrefWebsite
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openExternalLibrariesWebsite ()
specifier|private
name|void
name|openExternalLibrariesWebsite
parameter_list|()
block|{
name|viewModel
operator|.
name|openExternalLibrariesWebsite
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openGithub ()
specifier|private
name|void
name|openGithub
parameter_list|()
block|{
name|viewModel
operator|.
name|openGithub
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openChangeLog ()
specifier|public
name|void
name|openChangeLog
parameter_list|()
block|{
name|viewModel
operator|.
name|openChangeLog
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openLicense ()
specifier|public
name|void
name|openLicense
parameter_list|()
block|{
name|viewModel
operator|.
name|openLicense
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|openDonation ()
specifier|public
name|void
name|openDonation
parameter_list|()
block|{
name|viewModel
operator|.
name|openDonation
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

