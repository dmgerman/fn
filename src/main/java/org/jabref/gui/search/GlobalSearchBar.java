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
name|awt
operator|.
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|FlowLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Font
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|Objects
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JToggleButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JToolBar
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|embed
operator|.
name|swing
operator|.
name|JFXPanel
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
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
name|Tooltip
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
name|GUIGlobals
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
name|OSXCompatibleToolbar
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
name|help
operator|.
name|HelpAction
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
name|maintable
operator|.
name|MainTableDataModel
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
name|help
operator|.
name|HelpFile
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
name|SearchPreferences
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
DECL|class|GlobalSearchBar
specifier|public
class|class
name|GlobalSearchBar
extends|extends
name|JPanel
block|{
DECL|field|NEUTRAL_COLOR
specifier|private
specifier|static
specifier|final
name|Color
name|NEUTRAL_COLOR
init|=
name|Color
operator|.
name|WHITE
decl_stmt|;
DECL|field|NO_RESULTS_COLOR
specifier|private
specifier|static
specifier|final
name|Color
name|NO_RESULTS_COLOR
init|=
operator|new
name|Color
argument_list|(
literal|232
argument_list|,
literal|202
argument_list|,
literal|202
argument_list|)
decl_stmt|;
DECL|field|RESULTS_FOUND_COLOR
specifier|private
specifier|static
specifier|final
name|Color
name|RESULTS_FOUND_COLOR
init|=
operator|new
name|Color
argument_list|(
literal|217
argument_list|,
literal|232
argument_list|,
literal|202
argument_list|)
decl_stmt|;
DECL|field|ADVANCED_SEARCH_COLOR
specifier|private
specifier|static
specifier|final
name|Color
name|ADVANCED_SEARCH_COLOR
init|=
operator|new
name|Color
argument_list|(
literal|102
argument_list|,
literal|255
argument_list|,
literal|255
argument_list|)
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
name|JToggleButton
name|caseSensitive
decl_stmt|;
DECL|field|regularExp
specifier|private
specifier|final
name|JToggleButton
name|regularExp
decl_stmt|;
DECL|field|searchModeButton
specifier|private
specifier|final
name|JButton
name|searchModeButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|currentResults
specifier|private
specifier|final
name|JLabel
name|currentResults
init|=
operator|new
name|JLabel
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
DECL|field|openCurrentResultsInDialog
specifier|private
name|JButton
name|openCurrentResultsInDialog
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|OPEN_IN_NEW_WINDOW
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|searchWorker
specifier|private
name|SearchWorker
name|searchWorker
decl_stmt|;
DECL|field|globalSearchWorker
specifier|private
name|GlobalSearchWorker
name|globalSearchWorker
decl_stmt|;
DECL|field|searchResultFrame
specifier|private
name|SearchResultFrame
name|searchResultFrame
decl_stmt|;
DECL|field|searchDisplayMode
specifier|private
name|SearchDisplayMode
name|searchDisplayMode
decl_stmt|;
comment|/**      * if this flag is set the searchbar won't be selected after the next search      */
DECL|field|dontSelectSearchBar
specifier|private
name|boolean
name|dontSelectSearchBar
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
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|150
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|currentResults
operator|.
name|setFont
argument_list|(
name|currentResults
operator|.
name|getFont
argument_list|()
operator|.
name|deriveFont
argument_list|(
name|Font
operator|.
name|BOLD
argument_list|)
argument_list|)
expr_stmt|;
name|JToggleButton
name|globalSearch
init|=
operator|new
name|JToggleButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|GLOBAL_SEARCH
operator|.
name|getSmallIcon
argument_list|()
argument_list|,
name|searchPreferences
operator|.
name|isGlobalSearch
argument_list|()
argument_list|)
decl_stmt|;
name|globalSearch
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search in all open libraries"
argument_list|)
argument_list|)
expr_stmt|;
comment|// default action to be performed for toggling globalSearch
name|AbstractAction
name|globalSearchStandardAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|searchPreferences
operator|.
name|setGlobalSearch
argument_list|(
name|globalSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|updateOpenCurrentResultsTooltip
argument_list|(
name|globalSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
comment|// additional action for global search shortcut
name|AbstractAction
name|globalSearchShortCutAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|globalSearch
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|globalSearchStandardAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|this
argument_list|,
literal|0
argument_list|,
literal|"fire standard action"
argument_list|)
argument_list|)
expr_stmt|;
name|focus
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|String
name|searchGlobalByKey
init|=
literal|"searchGlobalByKey"
decl_stmt|;
name|globalSearch
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|GLOBAL_SEARCH
argument_list|)
argument_list|,
name|searchGlobalByKey
argument_list|)
expr_stmt|;
name|globalSearch
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
name|searchGlobalByKey
argument_list|,
name|globalSearchShortCutAction
argument_list|)
expr_stmt|;
name|globalSearch
operator|.
name|addActionListener
argument_list|(
name|globalSearchStandardAction
argument_list|)
expr_stmt|;
name|openCurrentResultsInDialog
operator|.
name|setDisabledIcon
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|OPEN_IN_NEW_WINDOW
operator|.
name|getSmallIcon
argument_list|()
operator|.
name|createDisabledIcon
argument_list|()
argument_list|)
expr_stmt|;
name|openCurrentResultsInDialog
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
if|if
condition|(
name|globalSearch
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|performGlobalSearch
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|openLocalFindingsInExternalPanel
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|openCurrentResultsInDialog
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|updateOpenCurrentResultsTooltip
argument_list|(
name|globalSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|regularExp
operator|=
operator|new
name|JToggleButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|REG_EX
operator|.
name|getSmallIcon
argument_list|()
argument_list|,
name|searchPreferences
operator|.
name|isRegularExpression
argument_list|()
argument_list|)
expr_stmt|;
name|regularExp
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"regular expression"
argument_list|)
argument_list|)
expr_stmt|;
name|regularExp
operator|.
name|addActionListener
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
operator|new
name|JToggleButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|CASE_SENSITIVE
operator|.
name|getSmallIcon
argument_list|()
argument_list|,
name|searchPreferences
operator|.
name|isCaseSensitive
argument_list|()
argument_list|)
expr_stmt|;
name|caseSensitive
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Case sensitive"
argument_list|)
argument_list|)
expr_stmt|;
name|caseSensitive
operator|.
name|addActionListener
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
name|addActionListener
argument_list|(
name|event
lambda|->
name|toggleSearchModeAndSearch
argument_list|()
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|searchField
operator|.
name|textProperty
argument_list|()
argument_list|,
name|searchText
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
comment|/*         String endSearch = "endSearch";         searchField.getInputMap().put(Globals.getKeyPrefs().getKey(KeyBinding.CLEAR_SEARCH), endSearch);         searchField.getActionMap().put(endSearch, new AbstractAction() {             @Override             public void actionPerformed(ActionEvent event) {                 if (autoCompleteSupport.isVisible()) {                     autoCompleteSupport.setVisible(false);                 } else {                     endSearch();                 }             }         });         */
comment|/*         String acceptSearch = "acceptSearch";         searchField.getInputMap().put(Globals.getKeyPrefs().getKey(KeyBinding.ACCEPT), acceptSearch);         searchField.getActionMap().put(acceptSearch, new AbstractAction() {             @Override             public void actionPerformed(ActionEvent e) {                 autoCompleteSupport.setVisible(false);                 BasePanel currentBasePanel = frame.getCurrentBasePanel();                 Globals.getFocusListener().setFocused(currentBasePanel.getMainTable());                 currentBasePanel.getMainTable().requestFocus();             }         });         */
name|JFXPanel
name|container
init|=
operator|new
name|JFXPanel
argument_list|()
decl_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|container
operator|.
name|setScene
argument_list|(
operator|new
name|Scene
argument_list|(
name|searchField
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|FlowLayout
argument_list|(
name|FlowLayout
operator|.
name|RIGHT
argument_list|)
argument_list|)
expr_stmt|;
name|JToolBar
name|toolBar
init|=
operator|new
name|OSXCompatibleToolbar
argument_list|()
decl_stmt|;
name|toolBar
operator|.
name|setFloatable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|container
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|openCurrentResultsInDialog
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|globalSearch
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|regularExp
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|caseSensitive
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|searchModeButton
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|SEARCH
argument_list|)
argument_list|)
expr_stmt|;
name|toolBar
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|toolBar
operator|.
name|add
argument_list|(
name|currentResults
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|toolBar
argument_list|)
expr_stmt|;
block|}
DECL|method|performGlobalSearch ()
specifier|public
name|void
name|performGlobalSearch
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
operator|||
name|validateSearchResultFrame
argument_list|(
literal|true
argument_list|)
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|globalSearchWorker
operator|!=
literal|null
condition|)
block|{
name|globalSearchWorker
operator|.
name|cancel
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
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
name|focus
argument_list|()
expr_stmt|;
return|return;
block|}
name|globalSearchWorker
operator|=
operator|new
name|GlobalSearchWorker
argument_list|(
name|currentBasePanel
operator|.
name|frame
argument_list|()
argument_list|,
name|getSearchQuery
argument_list|()
argument_list|)
expr_stmt|;
name|globalSearchWorker
operator|.
name|execute
argument_list|()
expr_stmt|;
block|}
DECL|method|openLocalFindingsInExternalPanel ()
specifier|private
name|void
name|openLocalFindingsInExternalPanel
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
operator|||
name|validateSearchResultFrame
argument_list|(
literal|false
argument_list|)
condition|)
block|{
return|return;
block|}
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
name|focus
argument_list|()
expr_stmt|;
return|return;
block|}
name|SearchResultFrame
name|searchDialog
init|=
operator|new
name|SearchResultFrame
argument_list|(
name|currentBasePanel
operator|.
name|frame
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search results in library %0 for %1"
argument_list|,
name|currentBasePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|.
name|map
argument_list|(
name|File
operator|::
name|getName
argument_list|)
operator|.
name|orElse
argument_list|(
name|GUIGlobals
operator|.
name|UNTITLED_TITLE
argument_list|)
argument_list|,
name|this
operator|.
name|getSearchQuery
argument_list|()
operator|.
name|localize
argument_list|()
argument_list|)
argument_list|,
name|getSearchQuery
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|currentBasePanel
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|BibEntry
operator|::
name|isSearchHit
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|searchDialog
operator|.
name|addEntries
argument_list|(
name|entries
argument_list|,
name|currentBasePanel
argument_list|)
expr_stmt|;
name|searchDialog
operator|.
name|selectFirstEntry
argument_list|()
expr_stmt|;
name|searchDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|validateSearchResultFrame (boolean globalSearch)
specifier|private
name|boolean
name|validateSearchResultFrame
parameter_list|(
name|boolean
name|globalSearch
parameter_list|)
block|{
if|if
condition|(
name|searchResultFrame
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|searchResultFrame
operator|.
name|isGlobalSearch
argument_list|()
operator|==
name|globalSearch
operator|&&
name|isStillValidQuery
argument_list|(
name|searchResultFrame
operator|.
name|getSearchQuery
argument_list|()
argument_list|)
condition|)
block|{
name|searchResultFrame
operator|.
name|focus
argument_list|()
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
name|searchResultFrame
operator|.
name|dispose
argument_list|()
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
return|return
literal|false
return|;
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
name|setToolTipText
argument_list|(
name|searchDisplayMode
operator|.
name|getToolTipText
argument_list|()
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
argument_list|(
name|currentBasePanel
argument_list|)
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
name|Globals
operator|.
name|getFocusListener
argument_list|()
operator|.
name|setFocused
argument_list|(
name|mainTable
argument_list|)
expr_stmt|;
name|mainTable
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|mainTable
operator|.
name|ensureVisible
argument_list|(
name|mainTable
operator|.
name|getSelectedRow
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
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
DECL|method|clearSearch (BasePanel currentBasePanel)
specifier|private
name|void
name|clearSearch
parameter_list|(
name|BasePanel
name|currentBasePanel
parameter_list|)
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
name|openCurrentResultsInDialog
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|currentBasePanel
operator|!=
literal|null
condition|)
block|{
name|currentBasePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getTableModel
argument_list|()
operator|.
name|updateSearchState
argument_list|(
name|MainTableDataModel
operator|.
name|DisplayOption
operator|.
name|DISABLED
argument_list|)
expr_stmt|;
name|currentBasePanel
operator|.
name|setCurrentSearchQuery
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|dontSelectSearchBar
condition|)
block|{
name|dontSelectSearchBar
operator|=
literal|false
expr_stmt|;
return|return;
block|}
name|focus
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
argument_list|(
name|currentBasePanel
argument_list|)
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
name|BasePanel
name|currentBasePanel
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
name|currentBasePanel
operator|.
name|getMainTable
argument_list|()
operator|.
name|getTableModel
argument_list|()
operator|.
name|updateSearchState
argument_list|(
name|MainTableDataModel
operator|.
name|DisplayOption
operator|.
name|DISABLED
argument_list|)
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
name|openCurrentResultsInDialog
operator|.
name|setEnabled
argument_list|(
literal|false
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
literal|true
argument_list|,
literal|true
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
expr_stmt|;
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
DECL|method|isStillValidQuery (SearchQuery query)
specifier|public
name|boolean
name|isStillValidQuery
parameter_list|(
name|SearchQuery
name|query
parameter_list|)
block|{
return|return
name|query
operator|.
name|getQuery
argument_list|()
operator|.
name|equals
argument_list|(
name|this
operator|.
name|searchField
operator|.
name|getText
argument_list|()
argument_list|)
operator|&&
operator|(
name|query
operator|.
name|isRegularExpression
argument_list|()
operator|==
name|regularExp
operator|.
name|isSelected
argument_list|()
operator|)
operator|&&
operator|(
name|query
operator|.
name|isCaseSensitive
argument_list|()
operator|==
name|caseSensitive
operator|.
name|isSelected
argument_list|()
operator|)
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
DECL|method|updateResults (int matched, String description, boolean grammarBasedSearch)
specifier|public
name|void
name|updateResults
parameter_list|(
name|int
name|matched
parameter_list|,
name|String
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
name|searchField
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|description
argument_list|)
argument_list|)
expr_stmt|;
name|openCurrentResultsInDialog
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setSearchResultFrame (SearchResultFrame searchResultFrame)
specifier|public
name|void
name|setSearchResultFrame
parameter_list|(
name|SearchResultFrame
name|searchResultFrame
parameter_list|)
block|{
name|this
operator|.
name|searchResultFrame
operator|=
name|searchResultFrame
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
name|setDontSelectSearchBar
argument_list|()
expr_stmt|;
name|searchField
operator|.
name|setText
argument_list|(
name|searchTerm
argument_list|)
expr_stmt|;
block|}
DECL|method|setDontSelectSearchBar ()
specifier|public
name|void
name|setDontSelectSearchBar
parameter_list|()
block|{
name|this
operator|.
name|dontSelectSearchBar
operator|=
literal|true
expr_stmt|;
block|}
DECL|method|updateOpenCurrentResultsTooltip (boolean globalSearchEnabled)
specifier|private
name|void
name|updateOpenCurrentResultsTooltip
parameter_list|(
name|boolean
name|globalSearchEnabled
parameter_list|)
block|{
if|if
condition|(
name|globalSearchEnabled
condition|)
block|{
name|openCurrentResultsInDialog
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show global search results in a window"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|openCurrentResultsInDialog
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show search results in a window"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

