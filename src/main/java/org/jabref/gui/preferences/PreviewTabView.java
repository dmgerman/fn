begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
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
name|ReadOnlyListWrapper
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
name|Label
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
name|ScrollPane
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
name|actions
operator|.
name|ActionFactory
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
name|actions
operator|.
name|StandardActions
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
name|preview
operator|.
name|PreviewViewer
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
name|BindingsHelper
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
name|citationstyle
operator|.
name|CitationStylePreviewLayout
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
name|citationstyle
operator|.
name|PreviewLayout
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
name|citationstyle
operator|.
name|TextBasedPreviewLayout
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
name|org
operator|.
name|fxmisc
operator|.
name|richtext
operator|.
name|CodeArea
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|richtext
operator|.
name|LineNumberFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|PreviewTabView
specifier|public
class|class
name|PreviewTabView
extends|extends
name|VBox
implements|implements
name|PrefsTab
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|PreviewTabView
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|availableListView
annotation|@
name|FXML
specifier|private
name|ListView
argument_list|<
name|PreviewLayout
argument_list|>
name|availableListView
decl_stmt|;
DECL|field|chosenListView
annotation|@
name|FXML
specifier|private
name|ListView
argument_list|<
name|PreviewLayout
argument_list|>
name|chosenListView
decl_stmt|;
DECL|field|toRightButton
annotation|@
name|FXML
specifier|private
name|Button
name|toRightButton
decl_stmt|;
DECL|field|toLeftButton
annotation|@
name|FXML
specifier|private
name|Button
name|toLeftButton
decl_stmt|;
DECL|field|sortUpButton
annotation|@
name|FXML
specifier|private
name|Button
name|sortUpButton
decl_stmt|;
DECL|field|sortDownButton
annotation|@
name|FXML
specifier|private
name|Button
name|sortDownButton
decl_stmt|;
DECL|field|readOnlyLabel
annotation|@
name|FXML
specifier|private
name|Label
name|readOnlyLabel
decl_stmt|;
DECL|field|resetDefaultButton
annotation|@
name|FXML
specifier|private
name|Button
name|resetDefaultButton
decl_stmt|;
DECL|field|previewPane
annotation|@
name|FXML
specifier|private
name|ScrollPane
name|previewPane
decl_stmt|;
DECL|field|editArea
annotation|@
name|FXML
specifier|private
name|CodeArea
name|editArea
decl_stmt|;
DECL|field|contextMenu
specifier|private
specifier|final
name|ContextMenu
name|contextMenu
decl_stmt|;
DECL|field|taskExecutor
annotation|@
name|Inject
specifier|private
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|field|dialogService
annotation|@
name|Inject
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|lastKeyPressTime
specifier|private
name|long
name|lastKeyPressTime
decl_stmt|;
DECL|field|listSearchTerm
specifier|private
name|String
name|listSearchTerm
decl_stmt|;
DECL|field|viewModel
specifier|private
name|PreviewTabViewModel
name|viewModel
decl_stmt|;
DECL|class|EditAction
specifier|private
class|class
name|EditAction
extends|extends
name|SimpleCommand
block|{
DECL|field|command
specifier|private
specifier|final
name|StandardActions
name|command
decl_stmt|;
DECL|method|EditAction (StandardActions command)
specifier|public
name|EditAction
parameter_list|(
name|StandardActions
name|command
parameter_list|)
block|{
name|this
operator|.
name|command
operator|=
name|command
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
name|editArea
operator|!=
literal|null
condition|)
block|{
switch|switch
condition|(
name|command
condition|)
block|{
case|case
name|COPY
case|:
name|editArea
operator|.
name|copy
argument_list|()
expr_stmt|;
break|break;
case|case
name|CUT
case|:
name|editArea
operator|.
name|cut
argument_list|()
expr_stmt|;
break|break;
case|case
name|PASTE
case|:
name|editArea
operator|.
name|paste
argument_list|()
expr_stmt|;
break|break;
case|case
name|SELECT_ALL
case|:
name|editArea
operator|.
name|selectAll
argument_list|()
expr_stmt|;
break|break;
block|}
name|editArea
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|PreviewTabView (JabRefPreferences preferences)
specifier|public
name|PreviewTabView
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|contextMenu
operator|=
operator|new
name|ContextMenu
argument_list|()
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
block|}
DECL|method|initialize ()
specifier|public
name|void
name|initialize
parameter_list|()
block|{
name|viewModel
operator|=
operator|new
name|PreviewTabViewModel
argument_list|(
name|dialogService
argument_list|,
name|preferences
argument_list|,
name|taskExecutor
argument_list|)
expr_stmt|;
name|lastKeyPressTime
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
name|ActionFactory
name|factory
init|=
operator|new
name|ActionFactory
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|)
decl_stmt|;
name|contextMenu
operator|.
name|getItems
argument_list|()
operator|.
name|addAll
argument_list|(
name|factory
operator|.
name|createMenuItem
argument_list|(
name|StandardActions
operator|.
name|CUT
argument_list|,
operator|new
name|PreviewTabView
operator|.
name|EditAction
argument_list|(
name|StandardActions
operator|.
name|CUT
argument_list|)
argument_list|)
argument_list|,
name|factory
operator|.
name|createMenuItem
argument_list|(
name|StandardActions
operator|.
name|COPY
argument_list|,
operator|new
name|PreviewTabView
operator|.
name|EditAction
argument_list|(
name|StandardActions
operator|.
name|COPY
argument_list|)
argument_list|)
argument_list|,
name|factory
operator|.
name|createMenuItem
argument_list|(
name|StandardActions
operator|.
name|PASTE
argument_list|,
operator|new
name|PreviewTabView
operator|.
name|EditAction
argument_list|(
name|StandardActions
operator|.
name|PASTE
argument_list|)
argument_list|)
argument_list|,
name|factory
operator|.
name|createMenuItem
argument_list|(
name|StandardActions
operator|.
name|SELECT_ALL
argument_list|,
operator|new
name|PreviewTabView
operator|.
name|EditAction
argument_list|(
name|StandardActions
operator|.
name|SELECT_ALL
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|contextMenu
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"context-menu"
argument_list|)
expr_stmt|;
name|availableListView
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|availableListProperty
argument_list|()
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|availableSelectionModelProperty
argument_list|()
operator|.
name|setValue
argument_list|(
name|availableListView
operator|.
name|getSelectionModel
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|PreviewLayout
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|PreviewLayout
operator|::
name|getName
argument_list|)
operator|.
name|install
argument_list|(
name|availableListView
argument_list|)
expr_stmt|;
name|availableListView
operator|.
name|setOnKeyTyped
argument_list|(
name|event
lambda|->
name|jumpToSearchKey
argument_list|(
name|availableListView
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
name|availableListView
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
name|chosenListView
operator|.
name|itemsProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|chosenListProperty
argument_list|()
argument_list|)
expr_stmt|;
name|viewModel
operator|.
name|chosenSelectionModelProperty
argument_list|()
operator|.
name|setValue
argument_list|(
name|chosenListView
operator|.
name|getSelectionModel
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|PreviewLayout
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|PreviewLayout
operator|::
name|getName
argument_list|)
operator|.
name|install
argument_list|(
name|chosenListView
argument_list|)
expr_stmt|;
name|chosenListView
operator|.
name|setOnKeyTyped
argument_list|(
name|event
lambda|->
name|jumpToSearchKey
argument_list|(
name|chosenListView
argument_list|,
name|event
argument_list|)
argument_list|)
expr_stmt|;
name|chosenListView
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
name|toRightButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|availableSelectionModelProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|isNull
argument_list|()
argument_list|)
expr_stmt|;
name|toLeftButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|chosenSelectionModelProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|isNull
argument_list|()
argument_list|)
expr_stmt|;
name|sortUpButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|chosenSelectionModelProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|isNull
argument_list|()
argument_list|)
expr_stmt|;
name|sortDownButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|chosenSelectionModelProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|isNull
argument_list|()
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|setContent
argument_list|(
operator|new
name|PreviewViewer
argument_list|(
operator|new
name|BibDatabaseContext
argument_list|()
argument_list|,
name|dialogService
argument_list|,
name|Globals
operator|.
name|stateManager
argument_list|)
argument_list|)
expr_stmt|;
comment|// previewPane.setMaxWidth(650); // FixMe: PreviewViewer is too large
comment|// previewPane.getContent().maxWidth(650);
operator|(
operator|(
name|PreviewViewer
operator|)
name|previewPane
operator|.
name|getContent
argument_list|()
operator|)
operator|.
name|setEntry
argument_list|(
name|TestEntry
operator|.
name|getTestEntry
argument_list|()
argument_list|)
expr_stmt|;
operator|(
operator|(
name|PreviewViewer
operator|)
name|previewPane
operator|.
name|getContent
argument_list|()
operator|)
operator|.
name|setLayout
argument_list|(
name|viewModel
operator|.
name|getCurrentLayout
argument_list|()
argument_list|)
expr_stmt|;
name|previewPane
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|chosenSelectionModelProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|isNotNull
argument_list|()
argument_list|)
expr_stmt|;
name|editArea
operator|.
name|setParagraphGraphicFactory
argument_list|(
name|LineNumberFactory
operator|.
name|get
argument_list|(
name|editArea
argument_list|)
argument_list|)
expr_stmt|;
name|editArea
operator|.
name|textProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|obs
parameter_list|,
name|oldText
parameter_list|,
name|newText
parameter_list|)
lambda|->
name|editArea
operator|.
name|setStyleSpans
argument_list|(
literal|0
argument_list|,
name|viewModel
operator|.
name|computeHighlighting
argument_list|(
name|newText
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|editArea
operator|.
name|setContextMenu
argument_list|(
name|contextMenu
argument_list|)
expr_stmt|;
name|editArea
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|chosenSelectionModelProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|selectedItemProperty
argument_list|()
operator|.
name|isNotNull
argument_list|()
argument_list|)
expr_stmt|;
comment|// ToDo: Convert to bindings
name|BindingsHelper
operator|.
name|bindBidirectional
argument_list|(
name|editArea
operator|.
name|textProperty
argument_list|()
argument_list|,
operator|new
name|ReadOnlyListWrapper
argument_list|<>
argument_list|(
name|chosenListView
operator|.
name|selectionModelProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
argument_list|)
argument_list|,
name|layoutList
lambda|->
name|update
argument_list|()
argument_list|,
name|text
lambda|->
block|{
lambda|if (!viewModel.chosenSelectionModelProperty(
argument_list|)
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|isEmpty
argument_list|()
block|)
block|{
name|PreviewLayout
name|item
init|=
name|viewModel
operator|.
name|chosenSelectionModelProperty
argument_list|()
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
name|item
operator|instanceof
name|TextBasedPreviewLayout
condition|)
block|{
operator|(
operator|(
name|TextBasedPreviewLayout
operator|)
name|item
operator|)
operator|.
name|setText
argument_list|(
name|editArea
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|update
parameter_list|()
constructor_decl|;
block|}
end_class

begin_empty_stmt
unit|)
empty_stmt|;
end_empty_stmt

begin_comment
comment|// ToDo: Implement selectedIsEditableProperty-Logic in ViewModel
end_comment

begin_expr_stmt
name|readOnlyLabel
operator|.
name|visibleProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|selectedIsEditableProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|resetDefaultButton
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|selectedIsEditableProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|contextMenu
operator|.
name|getItems
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|selectedIsEditableProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_expr_stmt
name|contextMenu
operator|.
name|getItems
argument_list|()
operator|.
name|get
argument_list|(
literal|2
argument_list|)
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|viewModel
operator|.
name|selectedIsEditableProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
end_expr_stmt

begin_comment
comment|//editArea.editableProperty().bind(true); // FixMe: Cursor caret disappears
end_comment

begin_expr_stmt
name|update
argument_list|()
expr_stmt|;
end_expr_stmt

begin_function
unit|}      private
DECL|method|update ()
name|void
name|update
parameter_list|()
block|{
comment|// ToDo: convert to bindings
name|ObservableList
argument_list|<
name|PreviewLayout
argument_list|>
name|layoutList
init|=
name|chosenListView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
decl_stmt|;
if|if
condition|(
name|layoutList
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
operator|(
operator|(
name|PreviewViewer
operator|)
name|previewPane
operator|.
name|getContent
argument_list|()
operator|)
operator|.
name|setLayout
argument_list|(
name|viewModel
operator|.
name|getCurrentLayout
argument_list|()
argument_list|)
expr_stmt|;
name|editArea
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|String
name|previewText
decl_stmt|;
name|PreviewLayout
name|item
init|=
name|layoutList
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
try|try
block|{
operator|(
operator|(
name|PreviewViewer
operator|)
name|previewPane
operator|.
name|getContent
argument_list|()
operator|)
operator|.
name|setLayout
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|exception
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Parsing error."
argument_list|,
name|exception
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Parsing error"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Parsing error"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"illegal backslash expression"
argument_list|)
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|item
operator|instanceof
name|TextBasedPreviewLayout
condition|)
block|{
name|previewText
operator|=
operator|(
operator|(
name|TextBasedPreviewLayout
operator|)
name|item
operator|)
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|previewText
operator|=
operator|(
operator|(
name|CitationStylePreviewLayout
operator|)
name|item
operator|)
operator|.
name|getSource
argument_list|()
expr_stmt|;
block|}
name|editArea
operator|.
name|replaceText
argument_list|(
name|previewText
argument_list|)
expr_stmt|;
block|}
block|}
end_function

begin_function
DECL|method|jumpToSearchKey (ListView<PreviewLayout> list, KeyEvent keypressed)
specifier|public
name|void
name|jumpToSearchKey
parameter_list|(
name|ListView
argument_list|<
name|PreviewLayout
argument_list|>
name|list
parameter_list|,
name|KeyEvent
name|keypressed
parameter_list|)
block|{
if|if
condition|(
name|keypressed
operator|.
name|getCharacter
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|-
name|lastKeyPressTime
operator|<
literal|1000
condition|)
block|{
name|listSearchTerm
operator|+=
name|keypressed
operator|.
name|getCharacter
argument_list|()
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|listSearchTerm
operator|=
name|keypressed
operator|.
name|getCharacter
argument_list|()
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
name|lastKeyPressTime
operator|=
name|System
operator|.
name|currentTimeMillis
argument_list|()
expr_stmt|;
name|list
operator|.
name|getItems
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|item
lambda|->
name|item
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
name|listSearchTerm
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|item
lambda|->
name|list
operator|.
name|scrollTo
argument_list|(
name|item
argument_list|)
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|getBuilder ()
specifier|public
name|Node
name|getBuilder
parameter_list|()
block|{
return|return
name|this
return|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
comment|// Done by bindings
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|viewModel
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
return|return
name|viewModel
operator|.
name|validateSettings
argument_list|()
return|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry preview"
argument_list|)
return|;
block|}
end_function

begin_function
DECL|method|toRightButtonAction ()
specifier|public
name|void
name|toRightButtonAction
parameter_list|()
block|{
name|viewModel
operator|.
name|addToChosen
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
DECL|method|toLeftButtonAction ()
specifier|public
name|void
name|toLeftButtonAction
parameter_list|()
block|{
name|viewModel
operator|.
name|removeFromChosen
argument_list|()
expr_stmt|;
block|}
end_function

begin_comment
comment|// ToDo: Move selectionstuff to ViewModel
end_comment

begin_function
DECL|method|sortUpButtonAction ()
specifier|public
name|void
name|sortUpButtonAction
parameter_list|()
block|{
comment|// FixMe: previewPane loads first in chosenList if Preview is moved
name|List
argument_list|<
name|Integer
argument_list|>
name|newIndices
init|=
name|viewModel
operator|.
name|selectedInChosenUp
argument_list|(
name|chosenListView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedIndices
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|index
range|:
name|newIndices
control|)
block|{
name|chosenListView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
name|update
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
DECL|method|sortDownButtonAction ()
specifier|public
name|void
name|sortDownButtonAction
parameter_list|()
block|{
name|List
argument_list|<
name|Integer
argument_list|>
name|newIndices
init|=
name|viewModel
operator|.
name|selectedInChosenDown
argument_list|(
name|chosenListView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedIndices
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|index
range|:
name|newIndices
control|)
block|{
name|chosenListView
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|index
argument_list|)
expr_stmt|;
block|}
name|update
argument_list|()
expr_stmt|;
block|}
end_function

begin_function
DECL|method|resetDefaultButtonAction ()
specifier|public
name|void
name|resetDefaultButtonAction
parameter_list|()
block|{
comment|// not yet working
name|viewModel
operator|.
name|resetDefaultStyle
argument_list|()
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
block|}
end_function

unit|}
end_unit

