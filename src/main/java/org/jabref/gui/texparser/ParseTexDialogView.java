begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.texparser
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|texparser
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
name|beans
operator|.
name|binding
operator|.
name|Bindings
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
name|Button
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
name|CheckBoxTreeItem
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
name|ProgressIndicator
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
name|SelectionMode
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
name|TextField
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
name|gui
operator|.
name|util
operator|.
name|IconValidationDecorator
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
name|RecursiveTreeItem
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
name|TaskExecutor
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
name|ViewModelTreeCellFactory
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
name|preferences
operator|.
name|PreferencesService
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

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|visualization
operator|.
name|ControlsFxVisualizer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|CheckTreeView
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|ParseTexDialogView
specifier|public
class|class
name|ParseTexDialogView
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|validationVisualizer
specifier|private
specifier|final
name|ControlsFxVisualizer
name|validationVisualizer
decl_stmt|;
DECL|field|texDirectoryField
annotation|@
name|FXML
specifier|private
name|TextField
name|texDirectoryField
decl_stmt|;
DECL|field|browseButton
annotation|@
name|FXML
specifier|private
name|Button
name|browseButton
decl_stmt|;
DECL|field|searchButton
annotation|@
name|FXML
specifier|private
name|Button
name|searchButton
decl_stmt|;
DECL|field|progressIndicator
annotation|@
name|FXML
specifier|private
name|ProgressIndicator
name|progressIndicator
decl_stmt|;
DECL|field|fileTreeView
annotation|@
name|FXML
specifier|private
name|CheckTreeView
argument_list|<
name|FileNodeViewModel
argument_list|>
name|fileTreeView
decl_stmt|;
DECL|field|selectAllButton
annotation|@
name|FXML
specifier|private
name|Button
name|selectAllButton
decl_stmt|;
DECL|field|unselectAllButton
annotation|@
name|FXML
specifier|private
name|Button
name|unselectAllButton
decl_stmt|;
DECL|field|parseButtonType
annotation|@
name|FXML
specifier|private
name|ButtonType
name|parseButtonType
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|taskExecutor
annotation|@
name|Inject
specifier|private
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|preferencesService
annotation|@
name|Inject
specifier|private
name|PreferencesService
name|preferencesService
decl_stmt|;
DECL|field|viewModel
specifier|private
name|ParseTexDialogViewModel
name|viewModel
decl_stmt|;
DECL|method|ParseTexDialogView (BibDatabaseContext databaseContext)
specifier|public
name|ParseTexDialogView
parameter_list|(
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|validationVisualizer
operator|=
operator|new
name|ControlsFxVisualizer
argument_list|()
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search for Citations in LaTeX Files"
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
name|parseButtonType
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
name|viewModel
operator|.
name|parseButtonClicked
argument_list|()
argument_list|)
expr_stmt|;
name|Button
name|parseButton
init|=
operator|(
name|Button
operator|)
name|getDialogPane
argument_list|()
operator|.
name|lookupButton
argument_list|(
name|parseButtonType
argument_list|)
decl_stmt|;
name|parseButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|noFilesFoundProperty
argument_list|()
operator|.
name|or
argument_list|(
name|Bindings
operator|.
name|isEmpty
argument_list|(
name|viewModel
operator|.
name|getCheckedFileList
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
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
name|ParseTexDialogViewModel
argument_list|(
name|databaseContext
argument_list|,
name|dialogService
argument_list|,
name|taskExecutor
argument_list|,
name|preferencesService
argument_list|)
expr_stmt|;
name|fileTreeView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|SelectionMode
operator|.
name|MULTIPLE
argument_list|)
expr_stmt|;
name|fileTreeView
operator|.
name|showRootProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|successfulSearchProperty
argument_list|()
argument_list|)
expr_stmt|;
name|fileTreeView
operator|.
name|rootProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|viewModel
operator|.
name|rootProperty
argument_list|()
argument_list|,
name|fileNode
lambda|->
operator|new
name|RecursiveTreeItem
argument_list|<>
argument_list|(
name|fileNode
argument_list|,
name|FileNodeViewModel
operator|::
name|getChildren
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|fileTreeView
operator|.
name|rootProperty
argument_list|()
argument_list|,
name|root
lambda|->
block|{
operator|(
operator|(
name|CheckBoxTreeItem
argument_list|<
name|FileNodeViewModel
argument_list|>
operator|)
name|root
operator|)
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|root
operator|.
name|setExpanded
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|listBind
argument_list|(
name|viewModel
operator|.
name|getCheckedFileList
argument_list|()
argument_list|,
name|fileTreeView
operator|.
name|getCheckModel
argument_list|()
operator|.
name|getCheckedItems
argument_list|()
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
operator|new
name|ViewModelTreeCellFactory
argument_list|<
name|FileNodeViewModel
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|FileNodeViewModel
operator|::
name|getDisplayText
argument_list|)
operator|.
name|install
argument_list|(
name|fileTreeView
argument_list|)
expr_stmt|;
name|texDirectoryField
operator|.
name|textProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|texDirectoryProperty
argument_list|()
argument_list|)
expr_stmt|;
name|validationVisualizer
operator|.
name|setDecoration
argument_list|(
operator|new
name|IconValidationDecorator
argument_list|()
argument_list|)
expr_stmt|;
name|validationVisualizer
operator|.
name|initVisualization
argument_list|(
name|viewModel
operator|.
name|texDirectoryValidation
argument_list|()
argument_list|,
name|texDirectoryField
argument_list|)
expr_stmt|;
name|browseButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|searchInProgressProperty
argument_list|()
argument_list|)
expr_stmt|;
name|searchButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|texDirectoryValidation
argument_list|()
operator|.
name|validProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|selectAllButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|noFilesFoundProperty
argument_list|()
argument_list|)
expr_stmt|;
name|unselectAllButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|noFilesFoundProperty
argument_list|()
argument_list|)
expr_stmt|;
name|progressIndicator
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bindBidirectional
argument_list|(
name|viewModel
operator|.
name|searchInProgressProperty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|browseButtonClicked ()
specifier|private
name|void
name|browseButtonClicked
parameter_list|()
block|{
name|viewModel
operator|.
name|browseButtonClicked
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|searchButtonClicked ()
specifier|private
name|void
name|searchButtonClicked
parameter_list|()
block|{
name|viewModel
operator|.
name|searchButtonClicked
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|selectAll ()
specifier|private
name|void
name|selectAll
parameter_list|()
block|{
name|fileTreeView
operator|.
name|getCheckModel
argument_list|()
operator|.
name|checkAll
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|unselectAll ()
specifier|private
name|void
name|unselectAll
parameter_list|()
block|{
name|fileTreeView
operator|.
name|getCheckModel
argument_list|()
operator|.
name|clearChecks
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

