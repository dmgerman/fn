begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
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
name|property
operator|.
name|ReadOnlyStringWrapper
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
name|TableColumn
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
name|TableView
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
name|Tooltip
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
name|ViewModelTableRowFactory
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
name|importer
operator|.
name|fileformat
operator|.
name|CustomImporter
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
name|journals
operator|.
name|JournalAbbreviationLoader
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
DECL|class|ImportCustomizationDialog
specifier|public
class|class
name|ImportCustomizationDialog
extends|extends
name|BaseDialog
argument_list|<
name|Void
argument_list|>
block|{
DECL|field|addButton
annotation|@
name|FXML
specifier|private
name|ButtonType
name|addButton
decl_stmt|;
DECL|field|removeButton
annotation|@
name|FXML
specifier|private
name|ButtonType
name|removeButton
decl_stmt|;
DECL|field|closeButton
annotation|@
name|FXML
specifier|private
name|ButtonType
name|closeButton
decl_stmt|;
DECL|field|importerTable
annotation|@
name|FXML
specifier|private
name|TableView
argument_list|<
name|CustomImporter
argument_list|>
name|importerTable
decl_stmt|;
DECL|field|nameColumn
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|CustomImporter
argument_list|,
name|String
argument_list|>
name|nameColumn
decl_stmt|;
DECL|field|classColumn
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|CustomImporter
argument_list|,
name|String
argument_list|>
name|classColumn
decl_stmt|;
DECL|field|basePathColumn
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|CustomImporter
argument_list|,
name|String
argument_list|>
name|basePathColumn
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|preferences
annotation|@
name|Inject
specifier|private
name|PreferencesService
name|preferences
decl_stmt|;
DECL|field|loader
annotation|@
name|Inject
specifier|private
name|JournalAbbreviationLoader
name|loader
decl_stmt|;
DECL|field|viewModel
specifier|private
name|ImportCustomizationDialogViewModel
name|viewModel
decl_stmt|;
DECL|method|ImportCustomizationDialog ()
specifier|public
name|ImportCustomizationDialog
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
literal|"Manage custom imports"
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
operator|(
operator|(
name|Button
operator|)
name|getDialogPane
argument_list|()
operator|.
name|lookupButton
argument_list|(
name|addButton
argument_list|)
operator|)
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add a (compiled) custom Importer class from a class path."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"The path need not be on the classpath of JabRef."
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|setAction
argument_list|(
name|addButton
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
name|viewModel
operator|.
name|addImporter
argument_list|()
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|setAction
argument_list|(
name|removeButton
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
name|viewModel
operator|.
name|removeSelectedImporter
argument_list|()
argument_list|)
expr_stmt|;
name|ControlHelper
operator|.
name|setAction
argument_list|(
name|closeButton
argument_list|,
name|getDialogPane
argument_list|()
argument_list|,
name|event
lambda|->
block|{
name|viewModel
operator|.
name|saveToPrefs
argument_list|()
expr_stmt|;
name|close
argument_list|()
expr_stmt|;
block|}
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
name|ImportCustomizationDialogViewModel
argument_list|(
name|preferences
argument_list|,
name|dialogService
argument_list|)
expr_stmt|;
name|importerTable
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
name|importerTable
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|importersProperty
argument_list|()
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|listBind
argument_list|(
name|viewModel
operator|.
name|selectedImportersProperty
argument_list|()
argument_list|,
name|importerTable
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
argument_list|)
expr_stmt|;
name|nameColumn
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
operator|new
name|ReadOnlyStringWrapper
argument_list|(
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|classColumn
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
operator|new
name|ReadOnlyStringWrapper
argument_list|(
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getClassName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|basePathColumn
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
operator|new
name|ReadOnlyStringWrapper
argument_list|(
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|getBasePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ViewModelTableRowFactory
argument_list|<
name|CustomImporter
argument_list|>
argument_list|()
operator|.
name|withTooltip
argument_list|(
name|CustomImporter
operator|::
name|getDescription
argument_list|)
operator|.
name|install
argument_list|(
name|importerTable
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

