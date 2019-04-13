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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|beans
operator|.
name|binding
operator|.
name|BooleanBinding
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
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
name|DialogPane
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
name|TextArea
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
name|GridPane
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
name|JabRefGUI
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
name|util
operator|.
name|BackgroundTask
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
name|logic
operator|.
name|citationstyle
operator|.
name|CitationStyle
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
name|PreviewPreferences
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
DECL|class|PreviewPrefsTab
specifier|public
class|class
name|PreviewPrefsTab
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
name|PreviewPrefsTab
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|availableModel
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|Object
argument_list|>
name|availableModel
init|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
decl_stmt|;
DECL|field|chosenModel
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|Object
argument_list|>
name|chosenModel
init|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
decl_stmt|;
DECL|field|available
specifier|private
specifier|final
name|ListView
argument_list|<
name|Object
argument_list|>
name|available
init|=
operator|new
name|ListView
argument_list|<>
argument_list|(
name|availableModel
argument_list|)
decl_stmt|;
DECL|field|chosen
specifier|private
specifier|final
name|ListView
argument_list|<
name|Object
argument_list|>
name|chosen
init|=
operator|new
name|ListView
argument_list|<>
argument_list|(
name|chosenModel
argument_list|)
decl_stmt|;
DECL|field|btnRight
specifier|private
specifier|final
name|Button
name|btnRight
init|=
operator|new
name|Button
argument_list|(
literal|"Â»"
argument_list|)
decl_stmt|;
DECL|field|btnLeft
specifier|private
specifier|final
name|Button
name|btnLeft
init|=
operator|new
name|Button
argument_list|(
literal|"Â«"
argument_list|)
decl_stmt|;
DECL|field|btnUp
specifier|private
specifier|final
name|Button
name|btnUp
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Up"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|btnDown
specifier|private
specifier|final
name|Button
name|btnDown
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Down"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|gridPane
specifier|private
specifier|final
name|GridPane
name|gridPane
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
DECL|field|layout
specifier|private
specifier|final
name|TextArea
name|layout
init|=
operator|new
name|TextArea
argument_list|()
decl_stmt|;
DECL|field|btnTest
specifier|private
specifier|final
name|Button
name|btnTest
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Test"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|btnDefault
specifier|private
specifier|final
name|Button
name|btnDefault
init|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|scrollPane
specifier|private
specifier|final
name|ScrollPane
name|scrollPane
init|=
operator|new
name|ScrollPane
argument_list|(
name|layout
argument_list|)
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|externalFileTypes
specifier|private
specifier|final
name|ExternalFileTypes
name|externalFileTypes
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|method|PreviewPrefsTab (DialogService dialogService, ExternalFileTypes externalFileTypes, TaskExecutor taskExecutor)
specifier|public
name|PreviewPrefsTab
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|ExternalFileTypes
name|externalFileTypes
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|externalFileTypes
operator|=
name|externalFileTypes
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|taskExecutor
expr_stmt|;
name|setupLogic
argument_list|()
expr_stmt|;
name|setupGui
argument_list|()
expr_stmt|;
block|}
DECL|method|setupLogic ()
specifier|private
name|void
name|setupLogic
parameter_list|()
block|{
name|BooleanBinding
name|nothingSelectedFromChosen
init|=
name|Bindings
operator|.
name|isEmpty
argument_list|(
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
argument_list|)
decl_stmt|;
name|btnLeft
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|nothingSelectedFromChosen
argument_list|)
expr_stmt|;
name|btnDown
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|nothingSelectedFromChosen
argument_list|)
expr_stmt|;
name|btnUp
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|nothingSelectedFromChosen
argument_list|)
expr_stmt|;
name|btnRight
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|isEmpty
argument_list|(
name|available
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|btnRight
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
for|for
control|(
name|Object
name|object
range|:
name|available
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
control|)
block|{
name|availableModel
operator|.
name|remove
argument_list|(
name|object
argument_list|)
expr_stmt|;
name|chosenModel
operator|.
name|add
argument_list|(
name|object
argument_list|)
expr_stmt|;
block|}
name|storeSettings
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|btnLeft
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
for|for
control|(
name|Object
name|object
range|:
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
control|)
block|{
name|availableModel
operator|.
name|add
argument_list|(
name|object
argument_list|)
expr_stmt|;
name|chosenModel
operator|.
name|remove
argument_list|(
name|object
argument_list|)
expr_stmt|;
block|}
name|storeSettings
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|btnUp
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|List
argument_list|<
name|Integer
argument_list|>
name|newSelectedIndices
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|oldIndex
range|:
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedIndices
argument_list|()
control|)
block|{
name|boolean
name|alreadyTaken
init|=
name|newSelectedIndices
operator|.
name|contains
argument_list|(
name|oldIndex
operator|-
literal|1
argument_list|)
decl_stmt|;
name|int
name|newIndex
init|=
operator|(
operator|(
name|oldIndex
operator|>
literal|0
operator|)
operator|&&
operator|!
name|alreadyTaken
operator|)
condition|?
name|oldIndex
operator|-
literal|1
else|:
name|oldIndex
decl_stmt|;
name|chosenModel
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|chosenModel
operator|.
name|remove
argument_list|(
name|oldIndex
argument_list|)
argument_list|)
expr_stmt|;
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|newIndex
argument_list|)
expr_stmt|;
block|}
name|storeSettings
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|btnDown
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|List
argument_list|<
name|Integer
argument_list|>
name|newSelectedIndices
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|ObservableList
argument_list|<
name|Integer
argument_list|>
name|selectedIndices
init|=
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedIndices
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|selectedIndices
operator|.
name|size
argument_list|()
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|int
name|oldIndex
init|=
name|selectedIndices
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|boolean
name|alreadyTaken
init|=
name|newSelectedIndices
operator|.
name|contains
argument_list|(
name|oldIndex
operator|+
literal|1
argument_list|)
decl_stmt|;
name|int
name|newIndex
init|=
operator|(
operator|(
name|oldIndex
operator|<
operator|(
name|chosenModel
operator|.
name|size
argument_list|()
operator|-
literal|1
operator|)
operator|)
operator|&&
operator|!
name|alreadyTaken
operator|)
condition|?
name|oldIndex
operator|+
literal|1
else|:
name|oldIndex
decl_stmt|;
name|chosenModel
operator|.
name|add
argument_list|(
name|newIndex
argument_list|,
name|chosenModel
operator|.
name|remove
argument_list|(
name|oldIndex
argument_list|)
argument_list|)
expr_stmt|;
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
name|newIndex
argument_list|)
expr_stmt|;
block|}
name|storeSettings
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|btnDefault
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|layout
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getPreviewStyleDefault
argument_list|()
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|btnTest
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
try|try
block|{
name|PreviewPanel
name|testPane
init|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
operator|new
name|BibDatabaseContext
argument_list|()
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
argument_list|,
name|dialogService
argument_list|,
name|externalFileTypes
argument_list|)
decl_stmt|;
if|if
condition|(
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|testPane
operator|.
name|setFixedLayout
argument_list|(
name|layout
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|testPane
operator|.
name|setEntry
argument_list|(
name|TestEntry
operator|.
name|getTestEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|int
name|indexStyle
init|=
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
name|PreviewPreferences
name|preferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
decl_stmt|;
name|preferences
operator|=
operator|new
name|PreviewPreferences
argument_list|(
name|preferences
operator|.
name|getPreviewCycle
argument_list|()
argument_list|,
name|indexStyle
argument_list|,
name|preferences
operator|.
name|getPreviewPanelDividerPosition
argument_list|()
argument_list|,
name|preferences
operator|.
name|isPreviewPanelEnabled
argument_list|()
argument_list|,
name|preferences
operator|.
name|getPreviewStyle
argument_list|()
argument_list|,
name|preferences
operator|.
name|getPreviewStyleDefault
argument_list|()
argument_list|)
expr_stmt|;
name|testPane
operator|=
operator|new
name|PreviewPanel
argument_list|(
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getCurrentBasePanel
argument_list|()
argument_list|,
operator|new
name|BibDatabaseContext
argument_list|()
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|,
name|preferences
argument_list|,
name|dialogService
argument_list|,
name|externalFileTypes
argument_list|)
expr_stmt|;
name|testPane
operator|.
name|setEntry
argument_list|(
name|TestEntry
operator|.
name|getTestEntry
argument_list|()
argument_list|)
expr_stmt|;
name|testPane
operator|.
name|updateLayout
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
name|DialogPane
name|pane
init|=
operator|new
name|DialogPane
argument_list|()
decl_stmt|;
name|pane
operator|.
name|setContent
argument_list|(
name|testPane
argument_list|)
expr_stmt|;
name|dialogService
operator|.
name|showCustomDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|,
name|pane
argument_list|,
name|ButtonType
operator|.
name|OK
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
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|setupGui ()
specifier|private
name|void
name|setupGui
parameter_list|()
block|{
name|VBox
name|vBox
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|btnRight
operator|.
name|setPrefSize
argument_list|(
literal|80
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|btnLeft
operator|.
name|setPrefSize
argument_list|(
literal|80
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|btnUp
operator|.
name|setPrefSize
argument_list|(
literal|80
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|btnDown
operator|.
name|setPrefSize
argument_list|(
literal|80
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|vBox
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
name|btnRight
argument_list|,
name|btnLeft
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
argument_list|,
name|btnUp
argument_list|,
name|btnDown
argument_list|)
expr_stmt|;
name|Label
name|currentPreview
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Current Preview"
argument_list|)
argument_list|)
decl_stmt|;
name|currentPreview
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|currentPreview
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|available
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|vBox
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|chosen
argument_list|,
literal|3
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|btnTest
argument_list|,
literal|2
argument_list|,
literal|6
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|btnDefault
argument_list|,
literal|3
argument_list|,
literal|6
argument_list|)
expr_stmt|;
name|layout
operator|.
name|setPrefSize
argument_list|(
literal|600
argument_list|,
literal|300
argument_list|)
expr_stmt|;
name|gridPane
operator|.
name|add
argument_list|(
name|scrollPane
argument_list|,
literal|1
argument_list|,
literal|9
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getBuilder ()
specifier|public
name|Node
name|getBuilder
parameter_list|()
block|{
return|return
name|gridPane
return|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|PreviewPreferences
name|previewPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
decl_stmt|;
name|chosenModel
operator|.
name|clear
argument_list|()
expr_stmt|;
name|boolean
name|isPreviewChosen
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|style
range|:
name|previewPreferences
operator|.
name|getPreviewCycle
argument_list|()
control|)
block|{
comment|// in case the style is not a valid citation style file, an empty Optional is returned
name|Optional
argument_list|<
name|CitationStyle
argument_list|>
name|citationStyle
init|=
name|CitationStyle
operator|.
name|createCitationStyleFromFile
argument_list|(
name|style
argument_list|)
decl_stmt|;
if|if
condition|(
name|citationStyle
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|chosenModel
operator|.
name|add
argument_list|(
name|citationStyle
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|isPreviewChosen
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Preview is already in the list, something went wrong"
argument_list|)
expr_stmt|;
continue|continue;
block|}
name|isPreviewChosen
operator|=
literal|true
expr_stmt|;
name|chosenModel
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|availableModel
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|isPreviewChosen
condition|)
block|{
name|availableModel
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|BackgroundTask
operator|.
name|wrap
argument_list|(
parameter_list|()
lambda|->
name|CitationStyle
operator|.
name|discoverCitationStyles
argument_list|()
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|value
lambda|->
block|{
name|value
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|style
lambda|->
operator|!
name|previewPreferences
operator|.
name|getPreviewCycle
argument_list|()
operator|.
name|contains
argument_list|(
name|style
operator|.
name|getFilePath
argument_list|()
argument_list|)
argument_list|)
operator|.
name|sorted
argument_list|(
name|Comparator
operator|.
name|comparing
argument_list|(
name|CitationStyle
operator|::
name|getTitle
argument_list|)
argument_list|)
operator|.
name|forEach
argument_list|(
name|availableModel
operator|::
name|add
argument_list|)
argument_list|;
block|}
block|)
operator|.
name|onFailure
argument_list|(
name|ex
lambda|->
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"something went wrong while adding the discovered CitationStyles to the list "
argument_list|,
name|ex
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
literal|"Error adding discovered CitationStyles"
argument_list|)
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
argument_list|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
end_class

begin_expr_stmt
name|layout
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getPreviewStyle
argument_list|()
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
end_expr_stmt

begin_function
unit|}      @
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|styles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
name|chosenModel
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|chosenModel
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Object
name|obj
range|:
name|chosenModel
control|)
block|{
if|if
condition|(
name|obj
operator|instanceof
name|CitationStyle
condition|)
block|{
name|styles
operator|.
name|add
argument_list|(
operator|(
operator|(
name|CitationStyle
operator|)
name|obj
operator|)
operator|.
name|getFilePath
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|obj
operator|instanceof
name|String
condition|)
block|{
name|styles
operator|.
name|add
argument_list|(
literal|"Preview"
argument_list|)
expr_stmt|;
block|}
block|}
name|PreviewPreferences
name|previewPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
operator|.
name|getBuilder
argument_list|()
operator|.
name|withPreviewCycle
argument_list|(
name|styles
argument_list|)
operator|.
name|withPreviewStyle
argument_list|(
name|layout
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
operator|.
name|build
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|previewPreferences
operator|=
name|previewPreferences
operator|.
name|getBuilder
argument_list|()
operator|.
name|withPreviewCyclePosition
argument_list|(
name|chosen
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|storePreviewPreferences
argument_list|(
name|previewPreferences
argument_list|)
expr_stmt|;
comment|// update preview
for|for
control|(
name|BasePanel
name|basePanel
range|:
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
name|basePanel
operator|.
name|getPreviewPanel
argument_list|()
operator|.
name|updateLayout
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getPreviewPreferences
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
operator|!
name|chosenModel
operator|.
name|isEmpty
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

unit|}
end_unit

