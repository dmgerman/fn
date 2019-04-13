begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.search
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
package|;
end_package

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Field
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|animation
operator|.
name|KeyFrame
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|animation
operator|.
name|KeyValue
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|animation
operator|.
name|Timeline
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
name|css
operator|.
name|PseudoClass
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|Event
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|geometry
operator|.
name|Pos
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
name|ContentDisplay
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
name|Skin
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ToggleButton
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
name|ToolBar
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
name|control
operator|.
name|cell
operator|.
name|TextFieldListCell
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
name|layout
operator|.
name|BorderPane
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
name|layout
operator|.
name|Priority
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
name|TextFlow
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|util
operator|.
name|Duration
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
name|autocompleter
operator|.
name|AppendPersonNamesStrategy
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
name|AutoCompleteFirstNameMode
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
name|autocompleter
operator|.
name|AutoCompletionTextInputBinding
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
name|PersonNameStringConverter
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
name|keyboard
operator|.
name|KeyBindingRepository
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
name|maintable
operator|.
name|MainTable
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
name|DefaultTaskExecutor
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
name|search
operator|.
name|SearchQuery
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
name|search
operator|.
name|SearchQueryHighlightObservable
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
name|Author
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
name|SearchPreferences
import|;
end_import

begin_import
import|import
name|impl
operator|.
name|org
operator|.
name|controlsfx
operator|.
name|skin
operator|.
name|AutoCompletePopup
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
name|textfield
operator|.
name|AutoCompletionBinding
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

begin_import
import|import
name|org
operator|.
name|reactfx
operator|.
name|util
operator|.
name|FxTimer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|reactfx
operator|.
name|util
operator|.
name|Timer
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
DECL|class|GlobalSearchBar
specifier|public
class|class
name|GlobalSearchBar
extends|extends
name|HBox
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
name|GlobalSearchBar
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|SEARCH_DELAY
specifier|private
specifier|static
specifier|final
name|int
name|SEARCH_DELAY
init|=
literal|400
decl_stmt|;
DECL|field|CLASS_NO_RESULTS
specifier|private
specifier|static
specifier|final
name|PseudoClass
name|CLASS_NO_RESULTS
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"emptyResult"
argument_list|)
decl_stmt|;
DECL|field|CLASS_RESULTS_FOUND
specifier|private
specifier|static
specifier|final
name|PseudoClass
name|CLASS_RESULTS_FOUND
init|=
name|PseudoClass
operator|.
name|getPseudoClass
argument_list|(
literal|"emptyResult"
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|searchField
specifier|private
specifier|final
name|TextField
name|searchField
init|=
name|SearchTextField
operator|.
name|create
argument_list|()
decl_stmt|;
DECL|field|caseSensitive
specifier|private
specifier|final
name|ToggleButton
name|caseSensitive
decl_stmt|;
DECL|field|regularExp
specifier|private
specifier|final
name|ToggleButton
name|regularExp
decl_stmt|;
DECL|field|searchModeButton
specifier|private
specifier|final
name|Button
name|searchModeButton
init|=
operator|new
name|Button
argument_list|()
decl_stmt|;
DECL|field|currentResults
specifier|private
specifier|final
name|Label
name|currentResults
init|=
operator|new
name|Label
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|searchQueryHighlightObservable
specifier|private
specifier|final
name|SearchQueryHighlightObservable
name|searchQueryHighlightObservable
init|=
operator|new
name|SearchQueryHighlightObservable
argument_list|()
decl_stmt|;
DECL|field|searchWorker
specifier|private
name|SearchWorker
name|searchWorker
decl_stmt|;
DECL|field|searchDisplayMode
specifier|private
name|SearchDisplayMode
name|searchDisplayMode
decl_stmt|;
DECL|method|GlobalSearchBar (JabRefFrame frame)
specifier|public
name|GlobalSearchBar
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|SearchPreferences
name|searchPreferences
init|=
operator|new
name|SearchPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
decl_stmt|;
name|searchDisplayMode
operator|=
name|searchPreferences
operator|.
name|getSearchMode
argument_list|()
expr_stmt|;
comment|// fits the standard "found x entries"-message thus hinders the searchbar to jump around while searching if the frame width is too small
name|currentResults
operator|.
name|setPrefWidth
argument_list|(
literal|150
argument_list|)
expr_stmt|;
name|KeyBindingRepository
name|keyBindingRepository
init|=
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
decl_stmt|;
name|searchField
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
name|keyBindingRepository
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
if|if
condition|(
name|keyBinding
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|KeyBinding
operator|.
name|CLOSE
argument_list|)
condition|)
block|{
comment|// Clear search and select first entry, if available
name|clearSearch
argument_list|()
expr_stmt|;
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getMainTable
argument_list|()
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|selectFirst
argument_list|()
expr_stmt|;
name|event
operator|.
name|consume
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|regularExp
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|REG_EX
operator|.
name|asToggleButton
argument_list|()
expr_stmt|;
name|regularExp
operator|.
name|setSelected
argument_list|(
name|searchPreferences
operator|.
name|isRegularExpression
argument_list|()
argument_list|)
expr_stmt|;
name|regularExp
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
literal|"regular expression"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|regularExp
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|searchPreferences
operator|.
name|setRegularExpression
argument_list|(
name|regularExp
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|performSearch
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|caseSensitive
operator|=
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|CASE_SENSITIVE
operator|.
name|asToggleButton
argument_list|()
expr_stmt|;
name|caseSensitive
operator|.
name|setSelected
argument_list|(
name|searchPreferences
operator|.
name|isCaseSensitive
argument_list|()
argument_list|)
expr_stmt|;
name|caseSensitive
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
literal|"Case sensitive"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|caseSensitive
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
block|{
name|searchPreferences
operator|.
name|setCaseSensitive
argument_list|(
name|caseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|performSearch
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|updateSearchModeButtonText
argument_list|()
expr_stmt|;
name|searchModeButton
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|toggleSearchModeAndSearch
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|initialSize
init|=
literal|400
decl_stmt|;
name|int
name|expandedSize
init|=
literal|700
decl_stmt|;
name|searchField
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"search-field"
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|setMinWidth
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|setMaxWidth
argument_list|(
name|initialSize
argument_list|)
expr_stmt|;
name|HBox
operator|.
name|setHgrow
argument_list|(
name|searchField
argument_list|,
name|Priority
operator|.
name|ALWAYS
argument_list|)
expr_stmt|;
name|Timer
name|searchTask
init|=
name|FxTimer
operator|.
name|create
argument_list|(
name|java
operator|.
name|time
operator|.
name|Duration
operator|.
name|ofMillis
argument_list|(
name|SEARCH_DELAY
argument_list|)
argument_list|,
parameter_list|()
lambda|->
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Run search "
operator|+
name|searchField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|performSearch
argument_list|()
expr_stmt|;
block|}
argument_list|)
decl_stmt|;
name|searchField
operator|.
name|textProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
name|searchTask
operator|.
name|restart
argument_list|()
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|searchField
operator|.
name|focusedProperty
argument_list|()
argument_list|,
name|isFocused
lambda|->
block|{
if|if
condition|(
name|isFocused
condition|)
block|{
name|KeyValue
name|widthValue
init|=
operator|new
name|KeyValue
argument_list|(
name|searchField
operator|.
name|maxWidthProperty
argument_list|()
argument_list|,
name|expandedSize
argument_list|)
decl_stmt|;
name|KeyFrame
name|keyFrame
init|=
operator|new
name|KeyFrame
argument_list|(
name|Duration
operator|.
name|millis
argument_list|(
literal|600
argument_list|)
argument_list|,
name|widthValue
argument_list|)
decl_stmt|;
name|Timeline
name|timeline
init|=
operator|new
name|Timeline
argument_list|(
name|keyFrame
argument_list|)
decl_stmt|;
name|timeline
operator|.
name|play
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|KeyValue
name|widthValue
init|=
operator|new
name|KeyValue
argument_list|(
name|searchField
operator|.
name|maxWidthProperty
argument_list|()
argument_list|,
name|initialSize
argument_list|)
decl_stmt|;
name|KeyFrame
name|keyFrame
init|=
operator|new
name|KeyFrame
argument_list|(
name|Duration
operator|.
name|millis
argument_list|(
literal|400
argument_list|)
argument_list|,
name|widthValue
argument_list|)
decl_stmt|;
name|Timeline
name|timeline
init|=
operator|new
name|Timeline
argument_list|(
name|keyFrame
argument_list|)
decl_stmt|;
name|timeline
operator|.
name|play
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|this
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|searchField
argument_list|,
name|currentResults
argument_list|)
expr_stmt|;
name|this
operator|.
name|setAlignment
argument_list|(
name|Pos
operator|.
name|CENTER_LEFT
argument_list|)
expr_stmt|;
block|}
DECL|method|toggleSearchModeAndSearch ()
specifier|private
name|void
name|toggleSearchModeAndSearch
parameter_list|()
block|{
name|int
name|nextSearchMode
init|=
operator|(
name|searchDisplayMode
operator|.
name|ordinal
argument_list|()
operator|+
literal|1
operator|)
operator|%
name|SearchDisplayMode
operator|.
name|values
argument_list|()
operator|.
name|length
decl_stmt|;
name|searchDisplayMode
operator|=
name|SearchDisplayMode
operator|.
name|values
argument_list|()
index|[
name|nextSearchMode
index|]
expr_stmt|;
operator|new
name|SearchPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
operator|.
name|setSearchMode
argument_list|(
name|searchDisplayMode
argument_list|)
expr_stmt|;
name|updateSearchModeButtonText
argument_list|()
expr_stmt|;
name|performSearch
argument_list|()
expr_stmt|;
block|}
DECL|method|updateSearchModeButtonText ()
specifier|private
name|void
name|updateSearchModeButtonText
parameter_list|()
block|{
name|searchModeButton
operator|.
name|setText
argument_list|(
name|searchDisplayMode
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|searchModeButton
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|searchDisplayMode
operator|.
name|getToolTipText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|endSearch ()
specifier|public
name|void
name|endSearch
parameter_list|()
block|{
name|BasePanel
name|currentBasePanel
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|currentBasePanel
operator|!=
literal|null
condition|)
block|{
name|clearSearch
argument_list|()
expr_stmt|;
name|MainTable
name|mainTable
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getMainTable
argument_list|()
decl_stmt|;
name|mainTable
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
comment|//SwingUtilities.invokeLater(() -> mainTable.ensureVisible(mainTable.getSelectedRow()));
block|}
block|}
comment|/**      * Focuses the search field if it is not focused.      */
DECL|method|focus ()
specifier|public
name|void
name|focus
parameter_list|()
block|{
if|if
condition|(
operator|!
name|searchField
operator|.
name|isFocused
argument_list|()
condition|)
block|{
name|searchField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
name|searchField
operator|.
name|selectAll
argument_list|()
expr_stmt|;
block|}
DECL|method|clearSearch ()
specifier|private
name|void
name|clearSearch
parameter_list|()
block|{
name|currentResults
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|searchQueryHighlightObservable
operator|.
name|reset
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|stateManager
operator|.
name|clearSearchQuery
argument_list|()
expr_stmt|;
block|}
DECL|method|performSearch ()
specifier|public
name|void
name|performSearch
parameter_list|()
block|{
name|BasePanel
name|currentBasePanel
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|currentBasePanel
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|searchWorker
operator|!=
literal|null
condition|)
block|{
name|searchWorker
operator|.
name|cancel
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// An empty search field should cause the search to be cleared.
if|if
condition|(
name|searchField
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|clearSearch
argument_list|()
expr_stmt|;
return|return;
block|}
name|SearchQuery
name|searchQuery
init|=
name|getSearchQuery
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|searchQuery
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|informUserAboutInvalidSearchQuery
argument_list|()
expr_stmt|;
return|return;
block|}
name|Globals
operator|.
name|stateManager
operator|.
name|setSearchQuery
argument_list|(
name|searchQuery
argument_list|)
expr_stmt|;
comment|// TODO: Remove search worker as this is doing the work twice now
name|searchWorker
operator|=
operator|new
name|SearchWorker
argument_list|(
name|currentBasePanel
argument_list|,
name|searchQuery
argument_list|,
name|searchDisplayMode
argument_list|)
expr_stmt|;
name|searchWorker
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
DECL|method|informUserAboutInvalidSearchQuery ()
specifier|private
name|void
name|informUserAboutInvalidSearchQuery
parameter_list|()
block|{
name|searchField
operator|.
name|pseudoClassStateChanged
argument_list|(
name|CLASS_NO_RESULTS
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|searchQueryHighlightObservable
operator|.
name|reset
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|stateManager
operator|.
name|clearSearchQuery
argument_list|()
expr_stmt|;
name|String
name|illegalSearch
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search failed: illegal search expression"
argument_list|)
decl_stmt|;
name|currentResults
operator|.
name|setText
argument_list|(
name|illegalSearch
argument_list|)
expr_stmt|;
block|}
DECL|method|setAutoCompleter (AutoCompleteSuggestionProvider<Author> searchCompleter)
specifier|public
name|void
name|setAutoCompleter
parameter_list|(
name|AutoCompleteSuggestionProvider
argument_list|<
name|Author
argument_list|>
name|searchCompleter
parameter_list|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getAutoCompletePreferences
argument_list|()
operator|.
name|shouldAutoComplete
argument_list|()
condition|)
block|{
name|AutoCompletionTextInputBinding
argument_list|<
name|Author
argument_list|>
name|autoComplete
init|=
name|AutoCompletionTextInputBinding
operator|.
name|autoComplete
argument_list|(
name|searchField
argument_list|,
name|searchCompleter
argument_list|,
operator|new
name|PersonNameStringConverter
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|,
name|AutoCompleteFirstNameMode
operator|.
name|BOTH
argument_list|)
argument_list|,
operator|new
name|AppendPersonNamesStrategy
argument_list|()
argument_list|)
decl_stmt|;
name|AutoCompletePopup
argument_list|<
name|Author
argument_list|>
name|popup
init|=
name|getPopup
argument_list|(
name|autoComplete
argument_list|)
decl_stmt|;
name|popup
operator|.
name|setSkin
argument_list|(
operator|new
name|SearchPopupSkin
argument_list|<>
argument_list|(
name|popup
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * The popup has private access in {@link AutoCompletionBinding}, so we use reflection to access it.      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|getPopup (AutoCompletionBinding<T> autoCompletionBinding)
specifier|private
parameter_list|<
name|T
parameter_list|>
name|AutoCompletePopup
argument_list|<
name|T
argument_list|>
name|getPopup
parameter_list|(
name|AutoCompletionBinding
argument_list|<
name|T
argument_list|>
name|autoCompletionBinding
parameter_list|)
block|{
try|try
block|{
comment|// TODO: reflective access, should be removed (Java 9)
name|Field
name|privatePopup
init|=
name|AutoCompletionBinding
operator|.
name|class
operator|.
name|getDeclaredField
argument_list|(
literal|"autoCompletionPopup"
argument_list|)
decl_stmt|;
name|privatePopup
operator|.
name|setAccessible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
operator|(
name|AutoCompletePopup
argument_list|<
name|T
argument_list|>
operator|)
name|privatePopup
operator|.
name|get
argument_list|(
name|autoCompletionBinding
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
decl||
name|NoSuchFieldException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Could not get access to auto completion popup"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
operator|new
name|AutoCompletePopup
argument_list|<>
argument_list|()
return|;
block|}
block|}
DECL|method|getSearchQueryHighlightObservable ()
specifier|public
name|SearchQueryHighlightObservable
name|getSearchQueryHighlightObservable
parameter_list|()
block|{
return|return
name|searchQueryHighlightObservable
return|;
block|}
DECL|method|getSearchQuery ()
specifier|private
name|SearchQuery
name|getSearchQuery
parameter_list|()
block|{
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
name|this
operator|.
name|searchField
operator|.
name|getText
argument_list|()
argument_list|,
name|this
operator|.
name|caseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|,
name|this
operator|.
name|regularExp
operator|.
name|isSelected
argument_list|()
argument_list|)
decl_stmt|;
name|this
operator|.
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|setCurrentSearchQuery
argument_list|(
name|searchQuery
argument_list|)
expr_stmt|;
return|return
name|searchQuery
return|;
block|}
DECL|method|updateResults (int matched, TextFlow description, boolean grammarBasedSearch)
specifier|public
name|void
name|updateResults
parameter_list|(
name|int
name|matched
parameter_list|,
name|TextFlow
name|description
parameter_list|,
name|boolean
name|grammarBasedSearch
parameter_list|)
block|{
if|if
condition|(
name|matched
operator|==
literal|0
condition|)
block|{
name|currentResults
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No results found."
argument_list|)
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|pseudoClassStateChanged
argument_list|(
name|CLASS_NO_RESULTS
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|currentResults
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Found %0 results."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|matched
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|pseudoClassStateChanged
argument_list|(
name|CLASS_RESULTS_FOUND
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|grammarBasedSearch
condition|)
block|{
comment|// TODO: switch Icon color
comment|// searchIcon.setIcon(IconTheme.JabRefIcon.ADVANCED_SEARCH.getIcon());
block|}
else|else
block|{
comment|// TODO: switch Icon color
comment|//searchIcon.setIcon(IconTheme.JabRefIcon.SEARCH.getIcon());
block|}
name|Tooltip
name|tooltip
init|=
operator|new
name|Tooltip
argument_list|()
decl_stmt|;
name|tooltip
operator|.
name|setContentDisplay
argument_list|(
name|ContentDisplay
operator|.
name|GRAPHIC_ONLY
argument_list|)
expr_stmt|;
name|tooltip
operator|.
name|setGraphic
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|tooltip
operator|.
name|setMaxHeight
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|setTooltip
argument_list|(
name|tooltip
argument_list|)
expr_stmt|;
block|}
DECL|method|setSearchTerm (String searchTerm)
specifier|public
name|void
name|setSearchTerm
parameter_list|(
name|String
name|searchTerm
parameter_list|)
block|{
if|if
condition|(
name|searchTerm
operator|.
name|equals
argument_list|(
name|searchField
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
return|return;
block|}
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|searchField
operator|.
name|setText
argument_list|(
name|searchTerm
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|class|SearchPopupSkin
specifier|private
class|class
name|SearchPopupSkin
parameter_list|<
name|T
parameter_list|>
implements|implements
name|Skin
argument_list|<
name|AutoCompletePopup
argument_list|<
name|T
argument_list|>
argument_list|>
block|{
DECL|field|control
specifier|private
specifier|final
name|AutoCompletePopup
argument_list|<
name|T
argument_list|>
name|control
decl_stmt|;
DECL|field|suggestionList
specifier|private
specifier|final
name|ListView
argument_list|<
name|T
argument_list|>
name|suggestionList
decl_stmt|;
DECL|field|container
specifier|private
specifier|final
name|BorderPane
name|container
decl_stmt|;
DECL|method|SearchPopupSkin (AutoCompletePopup<T> control)
specifier|public
name|SearchPopupSkin
parameter_list|(
name|AutoCompletePopup
argument_list|<
name|T
argument_list|>
name|control
parameter_list|)
block|{
name|this
operator|.
name|control
operator|=
name|control
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|=
operator|new
name|ListView
argument_list|<>
argument_list|(
name|control
operator|.
name|getSuggestions
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"auto-complete-popup"
argument_list|)
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|.
name|getStylesheets
argument_list|()
operator|.
name|add
argument_list|(
name|AutoCompletionBinding
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"autocompletion.css"
argument_list|)
operator|.
name|toExternalForm
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|.
name|prefHeightProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|Bindings
operator|.
name|min
argument_list|(
name|control
operator|.
name|visibleRowCountProperty
argument_list|()
argument_list|,
name|Bindings
operator|.
name|size
argument_list|(
name|this
operator|.
name|suggestionList
operator|.
name|getItems
argument_list|()
argument_list|)
argument_list|)
operator|.
name|multiply
argument_list|(
literal|24
argument_list|)
operator|.
name|add
argument_list|(
literal|18
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|.
name|setCellFactory
argument_list|(
name|TextFieldListCell
operator|.
name|forListView
argument_list|(
name|control
operator|.
name|getConverter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|.
name|prefWidthProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|control
operator|.
name|prefWidthProperty
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|.
name|maxWidthProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|control
operator|.
name|maxWidthProperty
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|.
name|minWidthProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|control
operator|.
name|minWidthProperty
argument_list|()
argument_list|)
expr_stmt|;
name|ToolBar
name|toolBar
init|=
operator|new
name|ToolBar
argument_list|(
name|regularExp
argument_list|,
name|caseSensitive
argument_list|,
name|searchModeButton
argument_list|)
decl_stmt|;
name|this
operator|.
name|container
operator|=
operator|new
name|BorderPane
argument_list|()
expr_stmt|;
name|this
operator|.
name|container
operator|.
name|setCenter
argument_list|(
name|suggestionList
argument_list|)
expr_stmt|;
name|this
operator|.
name|container
operator|.
name|setBottom
argument_list|(
name|toolBar
argument_list|)
expr_stmt|;
name|this
operator|.
name|registerEventListener
argument_list|()
expr_stmt|;
block|}
DECL|method|registerEventListener ()
specifier|private
name|void
name|registerEventListener
parameter_list|()
block|{
name|this
operator|.
name|suggestionList
operator|.
name|setOnMouseClicked
argument_list|(
parameter_list|(
name|me
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|me
operator|.
name|getButton
argument_list|()
operator|==
name|MouseButton
operator|.
name|PRIMARY
condition|)
block|{
name|this
operator|.
name|onSuggestionChosen
argument_list|(
name|this
operator|.
name|suggestionList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|this
operator|.
name|suggestionList
operator|.
name|setOnKeyPressed
argument_list|(
parameter_list|(
name|ke
parameter_list|)
lambda|->
block|{
switch|switch
condition|(
name|ke
operator|.
name|getCode
argument_list|()
condition|)
block|{
case|case
name|TAB
case|:
case|case
name|ENTER
case|:
name|this
operator|.
name|onSuggestionChosen
argument_list|(
name|this
operator|.
name|suggestionList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|ESCAPE
case|:
if|if
condition|(
name|this
operator|.
name|control
operator|.
name|isHideOnEscape
argument_list|()
condition|)
block|{
name|this
operator|.
name|control
operator|.
name|hide
argument_list|()
expr_stmt|;
block|}
break|break;
default|default:
break|break;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|onSuggestionChosen (T suggestion)
specifier|private
name|void
name|onSuggestionChosen
parameter_list|(
name|T
name|suggestion
parameter_list|)
block|{
if|if
condition|(
name|suggestion
operator|!=
literal|null
condition|)
block|{
name|Event
operator|.
name|fireEvent
argument_list|(
name|this
operator|.
name|control
argument_list|,
operator|new
name|AutoCompletePopup
operator|.
name|SuggestionEvent
argument_list|<>
argument_list|(
name|suggestion
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|getNode ()
specifier|public
name|Node
name|getNode
parameter_list|()
block|{
return|return
name|this
operator|.
name|container
return|;
block|}
annotation|@
name|Override
DECL|method|getSkinnable ()
specifier|public
name|AutoCompletePopup
argument_list|<
name|T
argument_list|>
name|getSkinnable
parameter_list|()
block|{
return|return
name|this
operator|.
name|control
return|;
block|}
annotation|@
name|Override
DECL|method|dispose ()
specifier|public
name|void
name|dispose
parameter_list|()
block|{         }
block|}
block|}
end_class

end_unit

