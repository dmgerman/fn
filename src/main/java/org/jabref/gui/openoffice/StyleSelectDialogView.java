begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
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
name|ContextMenu
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
name|MenuItem
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
name|layout
operator|.
name|VBox
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
name|PreviewPanel
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|icon
operator|.
name|IconTheme
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
name|ValueTableCellFactory
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
name|openoffice
operator|.
name|OOBibStyle
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
name|openoffice
operator|.
name|StyleLoader
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
name|TestEntry
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
DECL|class|StyleSelectDialogView
specifier|public
class|class
name|StyleSelectDialogView
extends|extends
name|BaseDialog
argument_list|<
name|OOBibStyle
argument_list|>
block|{
DECL|field|edit
specifier|private
specifier|final
name|MenuItem
name|edit
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Edit"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|reload
specifier|private
specifier|final
name|MenuItem
name|reload
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reload"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|loader
specifier|private
specifier|final
name|StyleLoader
name|loader
decl_stmt|;
DECL|field|colName
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|StyleSelectItemViewModel
argument_list|,
name|String
argument_list|>
name|colName
decl_stmt|;
DECL|field|tvStyles
annotation|@
name|FXML
specifier|private
name|TableView
argument_list|<
name|StyleSelectItemViewModel
argument_list|>
name|tvStyles
decl_stmt|;
DECL|field|colJournals
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|StyleSelectItemViewModel
argument_list|,
name|String
argument_list|>
name|colJournals
decl_stmt|;
DECL|field|colFile
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|StyleSelectItemViewModel
argument_list|,
name|String
argument_list|>
name|colFile
decl_stmt|;
DECL|field|colDeleteIcon
annotation|@
name|FXML
specifier|private
name|TableColumn
argument_list|<
name|StyleSelectItemViewModel
argument_list|,
name|Boolean
argument_list|>
name|colDeleteIcon
decl_stmt|;
DECL|field|add
annotation|@
name|FXML
specifier|private
name|Button
name|add
decl_stmt|;
DECL|field|vbox
annotation|@
name|FXML
specifier|private
name|VBox
name|vbox
decl_stmt|;
DECL|field|preferencesService
annotation|@
name|Inject
specifier|private
name|PreferencesService
name|preferencesService
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|viewModel
specifier|private
name|StyleSelectDialogViewModel
name|viewModel
decl_stmt|;
DECL|field|previewArticle
specifier|private
name|PreviewPanel
name|previewArticle
decl_stmt|;
DECL|field|previewBook
specifier|private
name|PreviewPanel
name|previewBook
decl_stmt|;
DECL|method|StyleSelectDialogView (StyleLoader loader)
specifier|public
name|StyleSelectDialogView
parameter_list|(
name|StyleLoader
name|loader
parameter_list|)
block|{
name|this
operator|.
name|loader
operator|=
name|loader
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
name|setResultConverter
argument_list|(
name|button
lambda|->
block|{
if|if
condition|(
name|button
operator|==
name|ButtonType
operator|.
name|OK
condition|)
block|{
name|viewModel
operator|.
name|storePrefs
argument_list|()
expr_stmt|;
return|return
name|tvStyles
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|getStyle
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
argument_list|)
expr_stmt|;
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Style selection"
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
name|StyleSelectDialogViewModel
argument_list|(
name|dialogService
argument_list|,
name|loader
argument_list|,
name|preferencesService
argument_list|)
expr_stmt|;
name|previewArticle
operator|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
operator|new
name|BibDatabaseContext
argument_list|()
argument_list|,
name|preferencesService
operator|.
name|getKeyBindingRepository
argument_list|()
argument_list|,
name|preferencesService
operator|.
name|getPreviewPreferences
argument_list|()
argument_list|,
name|dialogService
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
name|previewArticle
operator|.
name|setEntry
argument_list|(
name|TestEntry
operator|.
name|getTestEntry
argument_list|()
argument_list|)
expr_stmt|;
name|vbox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|previewArticle
argument_list|)
expr_stmt|;
name|previewBook
operator|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
operator|new
name|BibDatabaseContext
argument_list|()
argument_list|,
name|preferencesService
operator|.
name|getKeyBindingRepository
argument_list|()
argument_list|,
name|preferencesService
operator|.
name|getPreviewPreferences
argument_list|()
argument_list|,
name|dialogService
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
name|previewBook
operator|.
name|setEntry
argument_list|(
name|TestEntry
operator|.
name|getTestEntryBook
argument_list|()
argument_list|)
expr_stmt|;
name|vbox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|previewBook
argument_list|)
expr_stmt|;
name|colName
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|nameProperty
argument_list|()
argument_list|)
expr_stmt|;
name|colJournals
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|journalsProperty
argument_list|()
argument_list|)
expr_stmt|;
name|colFile
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|fileProperty
argument_list|()
argument_list|)
expr_stmt|;
name|colDeleteIcon
operator|.
name|setCellValueFactory
argument_list|(
name|cellData
lambda|->
name|cellData
operator|.
name|getValue
argument_list|()
operator|.
name|internalStyleProperty
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ValueTableCellFactory
argument_list|<
name|StyleSelectItemViewModel
argument_list|,
name|Boolean
argument_list|>
argument_list|()
operator|.
name|withGraphic
argument_list|(
name|internalStyle
lambda|->
block|{
if|if
condition|(
operator|!
name|internalStyle
condition|)
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|DELETE_ENTRY
operator|.
name|getGraphicNode
argument_list|()
return|;
block|}
return|return
literal|null
return|;
block|}
argument_list|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
name|item
lambda|->
block|{
return|return
name|evt
lambda|->
name|viewModel
operator|.
name|deleteStyle
argument_list|()
return|;
block|}
argument_list|)
operator|.
name|withTooltip
argument_list|(
name|item
lambda|->
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove style"
argument_list|)
return|;
block|}
argument_list|)
operator|.
name|install
argument_list|(
name|colDeleteIcon
argument_list|)
expr_stmt|;
name|edit
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|viewModel
operator|.
name|editStyle
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ViewModelTableRowFactory
argument_list|<
name|StyleSelectItemViewModel
argument_list|>
argument_list|()
operator|.
name|withOnMouseClickedEvent
argument_list|(
parameter_list|(
name|item
parameter_list|,
name|event
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|event
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
condition|)
block|{
name|viewModel
operator|.
name|viewStyle
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
operator|.
name|withContextMenu
argument_list|(
name|item
lambda|->
name|createContextMenu
argument_list|()
argument_list|)
operator|.
name|install
argument_list|(
name|tvStyles
argument_list|)
expr_stmt|;
name|tvStyles
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldvalue
parameter_list|,
name|newvalue
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|newvalue
operator|==
literal|null
condition|)
block|{
name|viewModel
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|setValue
argument_list|(
name|oldvalue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|viewModel
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|setValue
argument_list|(
name|newvalue
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|tvStyles
operator|.
name|setItems
argument_list|(
name|viewModel
operator|.
name|stylesProperty
argument_list|()
argument_list|)
expr_stmt|;
name|add
operator|.
name|setGraphic
argument_list|(
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|ADD
operator|.
name|getGraphicNode
argument_list|()
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|viewModel
operator|.
name|selectedItemProperty
argument_list|()
argument_list|,
name|style
lambda|->
block|{
name|tvStyles
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|style
argument_list|)
expr_stmt|;
name|previewArticle
operator|.
name|setLayout
argument_list|(
name|style
operator|.
name|getStyle
argument_list|()
operator|.
name|getReferenceFormat
argument_list|(
literal|"default"
argument_list|)
argument_list|)
expr_stmt|;
name|previewBook
operator|.
name|setLayout
argument_list|(
name|style
operator|.
name|getStyle
argument_list|()
operator|.
name|getReferenceFormat
argument_list|(
literal|"default"
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|createContextMenu ()
specifier|private
name|ContextMenu
name|createContextMenu
parameter_list|()
block|{
name|ContextMenu
name|contextMenu
init|=
operator|new
name|ContextMenu
argument_list|()
decl_stmt|;
name|contextMenu
operator|.
name|getItems
argument_list|()
operator|.
name|addAll
argument_list|(
name|edit
argument_list|,
name|reload
argument_list|)
expr_stmt|;
return|return
name|contextMenu
return|;
block|}
annotation|@
name|FXML
DECL|method|addStyleFile ()
specifier|private
name|void
name|addStyleFile
parameter_list|()
block|{
name|viewModel
operator|.
name|addStyleFile
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

