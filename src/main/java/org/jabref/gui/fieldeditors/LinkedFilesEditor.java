begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
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
name|collections
operator|.
name|ObservableList
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|ActionEvent
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
name|Node
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Parent
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
name|ListView
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
name|ProgressBar
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
name|SeparatorMenuItem
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
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|ClipboardContent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|DragEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|Dragboard
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|KeyEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|MouseButton
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|MouseEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|input
operator|.
name|TransferMode
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
name|HBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
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
name|DragAndDropDataFormats
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
name|autocompleter
operator|.
name|AutoCompleteSuggestionProvider
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
name|CopySingleFileAction
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
name|keyboard
operator|.
name|KeyBinding
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
name|ViewModelListCellFactory
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
name|integrity
operator|.
name|FieldCheckers
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
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|materialdesignicons
operator|.
name|MaterialDesignIcon
import|;
end_import

begin_import
import|import
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|materialdesignicons
operator|.
name|utils
operator|.
name|MaterialDesignIconFactory
import|;
end_import

begin_class
DECL|class|LinkedFilesEditor
specifier|public
class|class
name|LinkedFilesEditor
extends|extends
name|HBox
implements|implements
name|FieldEditorFX
block|{
DECL|field|viewModel
annotation|@
name|FXML
specifier|private
specifier|final
name|LinkedFilesEditorViewModel
name|viewModel
decl_stmt|;
DECL|field|listView
annotation|@
name|FXML
specifier|private
name|ListView
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|listView
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|method|LinkedFilesEditor (String fieldName, DialogService dialogService, BibDatabaseContext databaseContext, TaskExecutor taskExecutor, AutoCompleteSuggestionProvider<?> suggestionProvider, FieldCheckers fieldCheckers, JabRefPreferences preferences)
specifier|public
name|LinkedFilesEditor
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|,
name|FieldCheckers
name|fieldCheckers
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|viewModel
operator|=
operator|new
name|LinkedFilesEditorViewModel
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|dialogService
argument_list|,
name|databaseContext
argument_list|,
name|taskExecutor
argument_list|,
name|fieldCheckers
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|ViewLoader
operator|.
name|view
argument_list|(
name|this
argument_list|)
operator|.
name|root
argument_list|(
name|this
argument_list|)
operator|.
name|load
argument_list|()
expr_stmt|;
name|ViewModelListCellFactory
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|cellFactory
init|=
operator|new
name|ViewModelListCellFactory
argument_list|<
name|LinkedFileViewModel
argument_list|>
argument_list|()
operator|.
name|withTooltip
argument_list|(
name|LinkedFileViewModel
operator|::
name|getDescription
argument_list|)
operator|.
name|withGraphic
argument_list|(
name|LinkedFilesEditor
operator|::
name|createFileDisplay
argument_list|)
operator|.
name|withContextMenu
argument_list|(
name|this
operator|::
name|createContextMenuForFile
argument_list|)
operator|.
name|withOnMouseClickedEvent
argument_list|(
name|this
operator|::
name|handleItemMouseClick
argument_list|)
operator|.
name|setOnDragDetected
argument_list|(
name|this
operator|::
name|handleOnDragDetected
argument_list|)
operator|.
name|setOnDragDropped
argument_list|(
name|this
operator|::
name|handleOnDragDropped
argument_list|)
operator|.
name|setOnDragOver
argument_list|(
name|this
operator|::
name|handleOnDragOver
argument_list|)
decl_stmt|;
name|listView
operator|.
name|setCellFactory
argument_list|(
name|cellFactory
argument_list|)
expr_stmt|;
name|Bindings
operator|.
name|bindContentBidirectional
argument_list|(
name|listView
operator|.
name|itemsProperty
argument_list|()
operator|.
name|get
argument_list|()
argument_list|,
name|viewModel
operator|.
name|filesProperty
argument_list|()
argument_list|)
expr_stmt|;
name|setUpKeyBindings
argument_list|()
expr_stmt|;
block|}
DECL|method|handleOnDragOver (LinkedFileViewModel originalItem, DragEvent event)
specifier|private
name|void
name|handleOnDragOver
parameter_list|(
name|LinkedFileViewModel
name|originalItem
parameter_list|,
name|DragEvent
name|event
parameter_list|)
block|{
if|if
condition|(
operator|(
name|event
operator|.
name|getGestureSource
argument_list|()
operator|!=
name|originalItem
operator|)
operator|&&
name|event
operator|.
name|getDragboard
argument_list|()
operator|.
name|hasContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|LINKED_FILE
argument_list|)
condition|)
block|{
name|event
operator|.
name|acceptTransferModes
argument_list|(
name|TransferMode
operator|.
name|MOVE
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|handleOnDragDetected (@uppressWarningsR) LinkedFileViewModel linkedFile, MouseEvent event)
specifier|private
name|void
name|handleOnDragDetected
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
name|LinkedFileViewModel
name|linkedFile
parameter_list|,
name|MouseEvent
name|event
parameter_list|)
block|{
name|LinkedFile
name|selectedItem
init|=
name|listView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|getFile
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedItem
operator|!=
literal|null
condition|)
block|{
name|ClipboardContent
name|content
init|=
operator|new
name|ClipboardContent
argument_list|()
decl_stmt|;
name|Dragboard
name|dragboard
init|=
name|listView
operator|.
name|startDragAndDrop
argument_list|(
name|TransferMode
operator|.
name|MOVE
argument_list|)
decl_stmt|;
comment|//We have to use the model class here, as the content of the dragboard must be serializable
name|content
operator|.
name|put
argument_list|(
name|DragAndDropDataFormats
operator|.
name|LINKED_FILE
argument_list|,
name|selectedItem
argument_list|)
expr_stmt|;
name|dragboard
operator|.
name|setContent
argument_list|(
name|content
argument_list|)
expr_stmt|;
block|}
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
DECL|method|handleOnDragDropped (LinkedFileViewModel originalItem, DragEvent event)
specifier|private
name|void
name|handleOnDragDropped
parameter_list|(
name|LinkedFileViewModel
name|originalItem
parameter_list|,
name|DragEvent
name|event
parameter_list|)
block|{
name|Dragboard
name|dragboard
init|=
name|event
operator|.
name|getDragboard
argument_list|()
decl_stmt|;
name|boolean
name|success
init|=
literal|false
decl_stmt|;
name|ObservableList
argument_list|<
name|LinkedFileViewModel
argument_list|>
name|items
init|=
name|listView
operator|.
name|itemsProperty
argument_list|()
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|dragboard
operator|.
name|hasContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|LINKED_FILE
argument_list|)
condition|)
block|{
name|LinkedFile
name|linkedFile
init|=
operator|(
name|LinkedFile
operator|)
name|dragboard
operator|.
name|getContent
argument_list|(
name|DragAndDropDataFormats
operator|.
name|LINKED_FILE
argument_list|)
decl_stmt|;
name|LinkedFileViewModel
name|transferedItem
init|=
literal|null
decl_stmt|;
name|int
name|draggedIdx
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|items
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|items
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|.
name|getFile
argument_list|()
operator|.
name|equals
argument_list|(
name|linkedFile
argument_list|)
condition|)
block|{
name|draggedIdx
operator|=
name|i
expr_stmt|;
name|transferedItem
operator|=
name|items
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|int
name|thisIdx
init|=
name|items
operator|.
name|indexOf
argument_list|(
name|originalItem
argument_list|)
decl_stmt|;
name|items
operator|.
name|set
argument_list|(
name|draggedIdx
argument_list|,
name|originalItem
argument_list|)
expr_stmt|;
name|items
operator|.
name|set
argument_list|(
name|thisIdx
argument_list|,
name|transferedItem
argument_list|)
expr_stmt|;
name|success
operator|=
literal|true
expr_stmt|;
block|}
name|event
operator|.
name|setDropCompleted
argument_list|(
name|success
argument_list|)
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
DECL|method|createFileDisplay (LinkedFileViewModel linkedFile)
specifier|private
specifier|static
name|Node
name|createFileDisplay
parameter_list|(
name|LinkedFileViewModel
name|linkedFile
parameter_list|)
block|{
name|Node
name|icon
init|=
name|linkedFile
operator|.
name|getTypeIcon
argument_list|()
operator|.
name|getGraphicNode
argument_list|()
decl_stmt|;
name|icon
operator|.
name|setOnMouseClicked
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|open
argument_list|()
argument_list|)
expr_stmt|;
name|Text
name|link
init|=
operator|new
name|Text
argument_list|()
decl_stmt|;
name|link
operator|.
name|textProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|linkProperty
argument_list|()
argument_list|)
expr_stmt|;
name|Text
name|desc
init|=
operator|new
name|Text
argument_list|()
decl_stmt|;
name|desc
operator|.
name|textProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|descriptionProperty
argument_list|()
argument_list|)
expr_stmt|;
name|ProgressBar
name|progressIndicator
init|=
operator|new
name|ProgressBar
argument_list|()
decl_stmt|;
name|progressIndicator
operator|.
name|progressProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|downloadProgressProperty
argument_list|()
argument_list|)
expr_stmt|;
name|progressIndicator
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|downloadOngoingProperty
argument_list|()
argument_list|)
expr_stmt|;
name|HBox
name|info
init|=
operator|new
name|HBox
argument_list|(
literal|8
argument_list|)
decl_stmt|;
name|info
operator|.
name|setStyle
argument_list|(
literal|"-fx-padding: 0.5em 0 0.5em 0;"
argument_list|)
expr_stmt|;
comment|// To align with buttons below which also have 0.5em padding
name|info
operator|.
name|getChildren
argument_list|()
operator|.
name|setAll
argument_list|(
name|icon
argument_list|,
name|link
argument_list|,
name|desc
argument_list|,
name|progressIndicator
argument_list|)
expr_stmt|;
name|Button
name|acceptAutoLinkedFile
init|=
name|MaterialDesignIconFactory
operator|.
name|get
argument_list|()
operator|.
name|createIconButton
argument_list|(
name|MaterialDesignIcon
operator|.
name|BRIEFCASE_CHECK
argument_list|)
decl_stmt|;
name|acceptAutoLinkedFile
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
literal|"This file was found automatically. Do you want to link it to this entry?"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|acceptAutoLinkedFile
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|isAutomaticallyFoundProperty
argument_list|()
argument_list|)
expr_stmt|;
name|acceptAutoLinkedFile
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|acceptAsLinked
argument_list|()
argument_list|)
expr_stmt|;
name|acceptAutoLinkedFile
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"icon-button"
argument_list|)
expr_stmt|;
name|Button
name|writeXMPMetadata
init|=
name|MaterialDesignIconFactory
operator|.
name|get
argument_list|()
operator|.
name|createIconButton
argument_list|(
name|MaterialDesignIcon
operator|.
name|IMPORT
argument_list|)
decl_stmt|;
name|writeXMPMetadata
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
literal|"Write BibTeXEntry as XMP-metadata to PDF."
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|writeXMPMetadata
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|linkedFile
operator|.
name|canWriteXMPMetadataProperty
argument_list|()
argument_list|)
expr_stmt|;
name|writeXMPMetadata
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|writeXMPMetadata
argument_list|()
argument_list|)
expr_stmt|;
name|writeXMPMetadata
operator|.
name|getStyleClass
argument_list|()
operator|.
name|setAll
argument_list|(
literal|"icon-button"
argument_list|)
expr_stmt|;
name|HBox
name|container
init|=
operator|new
name|HBox
argument_list|(
literal|10
argument_list|)
decl_stmt|;
name|container
operator|.
name|setPrefHeight
argument_list|(
name|Double
operator|.
name|NEGATIVE_INFINITY
argument_list|)
expr_stmt|;
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|info
argument_list|,
name|acceptAutoLinkedFile
argument_list|,
name|writeXMPMetadata
argument_list|)
expr_stmt|;
return|return
name|container
return|;
block|}
DECL|method|setUpKeyBindings ()
specifier|private
name|void
name|setUpKeyBindings
parameter_list|()
block|{
name|listView
operator|.
name|addEventFilter
argument_list|(
name|KeyEvent
operator|.
name|KEY_PRESSED
argument_list|,
name|event
lambda|->
block|{
name|Optional
argument_list|<
name|KeyBinding
argument_list|>
name|keyBinding
init|=
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|mapToKeyBinding
argument_list|(
name|event
argument_list|)
decl_stmt|;
if|if
condition|(
name|keyBinding
operator|.
name|isPresent
argument_list|()
condition|)
block|{
switch|switch
condition|(
name|keyBinding
operator|.
name|get
argument_list|()
condition|)
block|{
case|case
name|DELETE_ENTRY
case|:
name|LinkedFileViewModel
name|selectedItem
init|=
name|listView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedItem
operator|!=
literal|null
condition|)
block|{
name|viewModel
operator|.
name|deleteFile
argument_list|(
name|selectedItem
argument_list|)
expr_stmt|;
block|}
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
break|break;
default|default:
comment|// Pass other keys to children
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|getViewModel ()
specifier|public
name|LinkedFilesEditorViewModel
name|getViewModel
parameter_list|()
block|{
return|return
name|viewModel
return|;
block|}
annotation|@
name|Override
DECL|method|bindToEntry (BibEntry entry)
specifier|public
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|viewModel
operator|.
name|bindToEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getNode ()
specifier|public
name|Parent
name|getNode
parameter_list|()
block|{
return|return
name|this
return|;
block|}
annotation|@
name|FXML
DECL|method|addNewFile (ActionEvent event)
specifier|private
name|void
name|addNewFile
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|addNewFile
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|fetchFulltext (ActionEvent event)
specifier|private
name|void
name|fetchFulltext
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|fetchFulltext
argument_list|()
expr_stmt|;
block|}
annotation|@
name|FXML
DECL|method|addFromURL (ActionEvent event)
specifier|private
name|void
name|addFromURL
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|viewModel
operator|.
name|addFromURL
argument_list|()
expr_stmt|;
block|}
DECL|method|createContextMenuForFile (LinkedFileViewModel linkedFile)
specifier|private
name|ContextMenu
name|createContextMenuForFile
parameter_list|(
name|LinkedFileViewModel
name|linkedFile
parameter_list|)
block|{
name|ContextMenu
name|menu
init|=
operator|new
name|ContextMenu
argument_list|()
decl_stmt|;
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
name|edit
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|edit
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|openFile
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open"
argument_list|)
argument_list|)
decl_stmt|;
name|openFile
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|open
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|openFolder
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open folder"
argument_list|)
argument_list|)
decl_stmt|;
name|openFolder
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|openFolder
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|download
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Download file"
argument_list|)
argument_list|)
decl_stmt|;
name|download
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|download
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|renameFile
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename file to defined pattern"
argument_list|)
argument_list|)
decl_stmt|;
name|renameFile
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|renameToSuggestion
argument_list|()
argument_list|)
expr_stmt|;
name|renameFile
operator|.
name|setDisable
argument_list|(
name|linkedFile
operator|.
name|getFile
argument_list|()
operator|.
name|isOnlineLink
argument_list|()
operator|||
name|linkedFile
operator|.
name|isGeneratedNameSameAsOriginal
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|renameFileName
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename file to a given name"
argument_list|)
argument_list|)
decl_stmt|;
name|renameFileName
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|askForNameAndRename
argument_list|()
argument_list|)
expr_stmt|;
name|renameFileName
operator|.
name|setDisable
argument_list|(
name|linkedFile
operator|.
name|getFile
argument_list|()
operator|.
name|isOnlineLink
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|moveFile
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file to file directory"
argument_list|)
argument_list|)
decl_stmt|;
name|moveFile
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|moveToDefaultDirectory
argument_list|()
argument_list|)
expr_stmt|;
name|moveFile
operator|.
name|setDisable
argument_list|(
name|linkedFile
operator|.
name|getFile
argument_list|()
operator|.
name|isOnlineLink
argument_list|()
operator|||
name|linkedFile
operator|.
name|isGeneratedPathSameAsOriginal
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|renameAndMoveFile
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file to file directory and rename file"
argument_list|)
argument_list|)
decl_stmt|;
name|renameAndMoveFile
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|linkedFile
operator|.
name|moveToDefaultDirectoryAndRename
argument_list|()
argument_list|)
expr_stmt|;
name|renameAndMoveFile
operator|.
name|setDisable
argument_list|(
name|linkedFile
operator|.
name|getFile
argument_list|()
operator|.
name|isOnlineLink
argument_list|()
operator|||
name|linkedFile
operator|.
name|isGeneratedPathSameAsOriginal
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|copyLinkedFiles
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy linked file to folder..."
argument_list|)
argument_list|)
decl_stmt|;
name|copyLinkedFiles
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
operator|new
name|CopySingleFileAction
argument_list|(
name|linkedFile
operator|.
name|getFile
argument_list|()
argument_list|,
name|dialogService
argument_list|,
name|databaseContext
argument_list|)
operator|.
name|copyFile
argument_list|()
argument_list|)
expr_stmt|;
name|copyLinkedFiles
operator|.
name|setDisable
argument_list|(
name|linkedFile
operator|.
name|getFile
argument_list|()
operator|.
name|isOnlineLink
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|deleteFile
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Permanently delete local file"
argument_list|)
argument_list|)
decl_stmt|;
name|deleteFile
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|deleteFile
argument_list|(
name|linkedFile
argument_list|)
argument_list|)
expr_stmt|;
name|deleteFile
operator|.
name|setDisable
argument_list|(
name|linkedFile
operator|.
name|getFile
argument_list|()
operator|.
name|isOnlineLink
argument_list|()
argument_list|)
expr_stmt|;
name|MenuItem
name|deleteLink
init|=
operator|new
name|MenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove link"
argument_list|)
argument_list|)
decl_stmt|;
name|deleteLink
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|viewModel
operator|.
name|removeFileLink
argument_list|(
name|linkedFile
argument_list|)
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|edit
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|addAll
argument_list|(
name|openFile
argument_list|,
name|openFolder
argument_list|)
expr_stmt|;
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|SeparatorMenuItem
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|linkedFile
operator|.
name|getFile
argument_list|()
operator|.
name|isOnlineLink
argument_list|()
condition|)
block|{
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|download
argument_list|)
expr_stmt|;
block|}
name|menu
operator|.
name|getItems
argument_list|()
operator|.
name|addAll
argument_list|(
name|renameFile
argument_list|,
name|renameFileName
argument_list|,
name|moveFile
argument_list|,
name|renameAndMoveFile
argument_list|,
name|copyLinkedFiles
argument_list|,
name|deleteLink
argument_list|,
name|deleteFile
argument_list|)
expr_stmt|;
return|return
name|menu
return|;
block|}
DECL|method|handleItemMouseClick (LinkedFileViewModel linkedFile, MouseEvent event)
specifier|private
name|void
name|handleItemMouseClick
parameter_list|(
name|LinkedFileViewModel
name|linkedFile
parameter_list|,
name|MouseEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getButton
argument_list|()
operator|.
name|equals
argument_list|(
name|MouseButton
operator|.
name|PRIMARY
argument_list|)
operator|&&
operator|(
name|event
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
operator|)
condition|)
block|{
comment|// Double click -> edit
name|linkedFile
operator|.
name|edit
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|getWeight ()
specifier|public
name|double
name|getWeight
parameter_list|()
block|{
return|return
literal|2
return|;
block|}
block|}
end_class

end_unit

