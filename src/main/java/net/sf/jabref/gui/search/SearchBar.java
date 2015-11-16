begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.search
package|package
name|net
operator|.
name|sf
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
name|*
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
name|List
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|DocumentListener
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|autocompleter
operator|.
name|AutoCompleter
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompleteSupport
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
operator|.
name|SearchRule
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
operator|.
name|SearchRules
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|GrammarBasedSearchRule
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|util
operator|.
name|SentenceAnalyzer
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|gpl
operator|.
name|JSplitButton
operator|.
name|JSplitButton
import|;
end_import

begin_import
import|import
name|org
operator|.
name|gpl
operator|.
name|JSplitButton
operator|.
name|action
operator|.
name|SplitButtonActionListener
import|;
end_import

begin_comment
comment|/**  * The search bar at the top of the screen allowing the user to search his database.  */
end_comment

begin_class
DECL|class|SearchBar
specifier|public
class|class
name|SearchBar
extends|extends
name|JPanel
block|{
DECL|method|getSearchQuery ()
specifier|private
name|SearchQuery
name|getSearchQuery
parameter_list|()
block|{
return|return
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
return|;
block|}
DECL|method|updateResults (int matched, String description)
specifier|public
name|void
name|updateResults
parameter_list|(
name|int
name|matched
parameter_list|,
name|String
name|description
parameter_list|)
block|{
if|if
condition|(
name|matched
operator|==
literal|0
condition|)
block|{
name|this
operator|.
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
block|}
else|else
block|{
name|this
operator|.
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
block|}
name|this
operator|.
name|searchField
operator|.
name|setToolTipText
argument_list|(
literal|"<html>"
operator|+
name|description
operator|+
literal|"</html>"
argument_list|)
expr_stmt|;
block|}
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|searchField
specifier|private
name|JSearchTextField
name|searchField
decl_stmt|;
DECL|field|searchButton
specifier|private
name|JSplitButton
name|searchButton
decl_stmt|;
DECL|field|modeFloat
DECL|field|modeLiveFilter
DECL|field|modeGlobal
specifier|private
name|JRadioButtonMenuItem
name|modeFloat
decl_stmt|,
name|modeLiveFilter
decl_stmt|,
name|modeGlobal
decl_stmt|;
DECL|field|settings
specifier|private
name|JMenu
name|settings
decl_stmt|;
DECL|field|highlightWords
DECL|field|autoComplete
specifier|private
name|JCheckBoxMenuItem
name|highlightWords
decl_stmt|,
name|autoComplete
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
DECL|field|openCurrentResultsInDialog
specifier|private
specifier|final
name|JButton
name|openCurrentResultsInDialog
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
DECL|field|autoCompleteSupport
name|AutoCompleteSupport
argument_list|<
name|String
argument_list|>
name|autoCompleteSupport
decl_stmt|;
DECL|field|worker
name|SearchWorker
name|worker
decl_stmt|;
DECL|field|listeners
specifier|private
specifier|final
name|ArrayList
argument_list|<
name|SearchTextListener
argument_list|>
name|listeners
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * Initializes the search bar.      *      * @param frame the main window      */
DECL|method|SearchBar (JabRefFrame frame)
specifier|public
name|SearchBar
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
name|frame
expr_stmt|;
name|worker
operator|=
operator|new
name|SearchWorker
argument_list|(
name|frame
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
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_CASE_SENSITIVE
argument_list|)
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
name|ae
lambda|->
name|updatePrefs
argument_list|()
argument_list|)
expr_stmt|;
name|caseSensitive
operator|.
name|addActionListener
argument_list|(
name|ae
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
name|caseSensitive
operator|.
name|addChangeListener
argument_list|(
name|c
lambda|->
name|performSearch
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
name|REGEX
operator|.
name|getSmallIcon
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_REG_EXP
argument_list|)
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
literal|"Use regular expressions"
argument_list|)
argument_list|)
expr_stmt|;
name|regularExp
operator|.
name|addActionListener
argument_list|(
name|ae
lambda|->
name|updatePrefs
argument_list|()
argument_list|)
expr_stmt|;
name|regularExp
operator|.
name|addActionListener
argument_list|(
name|ae
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
name|regularExp
operator|.
name|addChangeListener
argument_list|(
name|c
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
name|openCurrentResultsInDialog
operator|=
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
expr_stmt|;
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
name|openCurrentResultsInDialog
operator|.
name|addActionListener
argument_list|(
name|ae
lambda|->
block|{
name|SearchResultsDialog
name|searchDialog
init|=
operator|new
name|SearchResultsDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search results in database %0 for %1"
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|getFile
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|this
operator|.
name|getSearchQuery
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|isSearchHit
argument_list|()
condition|)
block|{
name|searchDialog
operator|.
name|addEntry
argument_list|(
name|entry
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
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
argument_list|)
expr_stmt|;
comment|// Init controls
name|setLayout
argument_list|(
operator|new
name|GridBagLayout
argument_list|()
argument_list|)
expr_stmt|;
name|GridBagConstraints
name|c
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|c
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|c
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|c
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|initSearchField
argument_list|()
expr_stmt|;
name|c
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|c
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|searchField
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|initSearchButton
argument_list|()
expr_stmt|;
name|c
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|c
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|c
operator|.
name|gridx
operator|=
name|GridBagConstraints
operator|.
name|RELATIVE
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|searchButton
argument_list|,
name|c
argument_list|)
expr_stmt|;
name|JToolBar
name|toolBar
init|=
operator|new
name|JToolBar
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
name|addSeparator
argument_list|()
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
operator|new
name|HelpAction
argument_list|(
name|frame
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|searchHelp
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|toolBar
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|currentResults
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initializes the search button and its popup menu      */
DECL|method|initSearchButton ()
specifier|private
name|void
name|initSearchButton
parameter_list|()
block|{
comment|// Create search button
name|searchButton
operator|=
operator|new
name|JSplitButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|SEARCH
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|searchButton
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|50
argument_list|,
literal|25
argument_list|)
argument_list|)
expr_stmt|;
name|searchButton
operator|.
name|setBackground
argument_list|(
name|searchField
operator|.
name|getBackground
argument_list|()
argument_list|)
expr_stmt|;
name|searchButton
operator|.
name|setContentAreaFilled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|searchButton
operator|.
name|setOpaque
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|searchButton
operator|.
name|addSplitButtonActionListener
argument_list|(
operator|new
name|SplitButtonActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|buttonClicked
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|performSearch
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|splitButtonClicked
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{             }
block|}
argument_list|)
expr_stmt|;
name|searchButton
operator|.
name|setPopupMenu
argument_list|(
name|createPopupMenu
argument_list|()
argument_list|)
expr_stmt|;
name|updateSearchButtonText
argument_list|()
expr_stmt|;
block|}
DECL|method|createPopupMenu ()
specifier|private
name|JPopupMenu
name|createPopupMenu
parameter_list|()
block|{
comment|// Populate popup menu and add it to search button
name|JPopupMenu
name|menu
init|=
operator|new
name|JPopupMenu
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|initSearchModeMenu
argument_list|()
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|getSearchModeMenuItem
argument_list|(
name|SearchMode
operator|.
name|FILTER
argument_list|)
argument_list|)
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|getSearchModeMenuItem
argument_list|(
name|SearchMode
operator|.
name|FLOAT
argument_list|)
argument_list|)
expr_stmt|;
name|menu
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|getSearchModeMenuItem
argument_list|(
name|SearchMode
operator|.
name|GLOBAL
argument_list|)
argument_list|)
expr_stmt|;
name|menu
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|highlightWords
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Highlight Words"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_HIGHLIGHT_WORDS
argument_list|)
argument_list|)
expr_stmt|;
name|highlightWords
operator|.
name|addActionListener
argument_list|(
name|ae
lambda|->
name|updatePrefs
argument_list|()
argument_list|)
expr_stmt|;
name|autoComplete
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autocomplete names"
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_AUTO_COMPLETE
argument_list|)
argument_list|)
expr_stmt|;
name|autoComplete
operator|.
name|addActionListener
argument_list|(
name|ae
lambda|->
name|updatePrefs
argument_list|()
argument_list|)
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|highlightWords
argument_list|)
expr_stmt|;
name|menu
operator|.
name|add
argument_list|(
name|autoComplete
argument_list|)
expr_stmt|;
return|return
name|menu
return|;
block|}
comment|/**      * Initializes the popup menu items controlling the search mode      */
DECL|method|initSearchModeMenu ()
specifier|private
name|void
name|initSearchModeMenu
parameter_list|()
block|{
name|ButtonGroup
name|searchMethod
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
for|for
control|(
name|SearchMode
name|mode
range|:
name|SearchMode
operator|.
name|values
argument_list|()
control|)
block|{
comment|// Create menu items
switch|switch
condition|(
name|mode
condition|)
block|{
case|case
name|FLOAT
case|:
name|modeFloat
operator|=
operator|new
name|JRadioButtonMenuItem
argument_list|(
name|mode
operator|.
name|getDisplayName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_MODE_FLOAT
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|FILTER
case|:
name|modeLiveFilter
operator|=
operator|new
name|JRadioButtonMenuItem
argument_list|(
name|mode
operator|.
name|getDisplayName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_MODE_LIVE_FILTER
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|GLOBAL
case|:
name|modeGlobal
operator|=
operator|new
name|JRadioButtonMenuItem
argument_list|(
name|mode
operator|.
name|getDisplayName
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_MODE_GLOBAL
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
comment|// Set tooltips on menu items
name|getSearchModeMenuItem
argument_list|(
name|mode
argument_list|)
operator|.
name|setToolTipText
argument_list|(
name|mode
operator|.
name|getToolTipText
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add menu item to group
name|searchMethod
operator|.
name|add
argument_list|(
name|getSearchModeMenuItem
argument_list|(
name|mode
argument_list|)
argument_list|)
expr_stmt|;
comment|// Listen to selection changed events
name|getSearchModeMenuItem
argument_list|(
name|mode
argument_list|)
operator|.
name|addChangeListener
argument_list|(
name|e
lambda|->
name|updateSearchButtonText
argument_list|()
argument_list|)
expr_stmt|;
name|getSearchModeMenuItem
argument_list|(
name|mode
argument_list|)
operator|.
name|addChangeListener
argument_list|(
name|e
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Initializes the search text field      */
DECL|method|initSearchField ()
specifier|private
name|void
name|initSearchField
parameter_list|()
block|{
name|searchField
operator|=
operator|new
name|JSearchTextField
argument_list|()
expr_stmt|;
name|searchField
operator|.
name|setTextWhenNotFocused
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search..."
argument_list|)
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|0
argument_list|,
literal|8
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
comment|// Add autocompleter
name|autoCompleteSupport
operator|=
operator|new
name|AutoCompleteSupport
argument_list|<>
argument_list|(
name|searchField
argument_list|)
expr_stmt|;
name|autoCompleteSupport
operator|.
name|install
argument_list|()
expr_stmt|;
comment|// Add the global focus listener, so a menu item can see if this field was focused when an action was called.
name|searchField
operator|.
name|addFocusListener
argument_list|(
name|Globals
operator|.
name|focusListener
argument_list|)
expr_stmt|;
comment|// Search if user press enter
name|searchField
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
comment|// Subscribe to changes to the text in the search field in order to "live search"
comment|// TODO: With this implementation "onSearchTextChanged" gets called two times when setText() is invoked (once for removing the initial string and then again for inserting the new one). This happens for example when an autocompletion is accepted.
name|searchField
operator|.
name|getDocument
argument_list|()
operator|.
name|addDocumentListener
argument_list|(
operator|new
name|DocumentListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|insertUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SearchBar
operator|.
name|class
argument_list|)
operator|.
name|debug
argument_list|(
literal|"Text insert: "
operator|+
name|e
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|onSearchTextChanged
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|removeUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SearchBar
operator|.
name|class
argument_list|)
operator|.
name|debug
argument_list|(
literal|"Text remove: "
operator|+
name|e
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|onSearchTextChanged
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|changedUpdate
parameter_list|(
name|DocumentEvent
name|e
parameter_list|)
block|{
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SearchBar
operator|.
name|class
argument_list|)
operator|.
name|debug
argument_list|(
literal|"Text updated: "
operator|+
name|e
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the item in the popup menu of the search button corresponding to the given search mode      */
DECL|method|getSearchModeMenuItem (SearchMode mode)
specifier|private
name|JRadioButtonMenuItem
name|getSearchModeMenuItem
parameter_list|(
name|SearchMode
name|mode
parameter_list|)
block|{
switch|switch
condition|(
name|mode
condition|)
block|{
case|case
name|FLOAT
case|:
return|return
name|modeFloat
return|;
case|case
name|GLOBAL
case|:
return|return
name|modeGlobal
return|;
case|case
name|FILTER
case|:
return|return
name|modeLiveFilter
return|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * Switches to another search mode.      *      * @param mode the new search mode      */
DECL|method|setSearchMode (SearchMode mode)
specifier|private
name|void
name|setSearchMode
parameter_list|(
name|SearchMode
name|mode
parameter_list|)
block|{
name|getSearchModeMenuItem
argument_list|(
name|mode
argument_list|)
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the currently activated search mode.      *      * @return current search mode      */
DECL|method|getSearchMode ()
specifier|private
name|SearchMode
name|getSearchMode
parameter_list|()
block|{
if|if
condition|(
name|modeFloat
operator|.
name|isSelected
argument_list|()
condition|)
block|{
return|return
name|SearchMode
operator|.
name|FLOAT
return|;
block|}
if|if
condition|(
name|modeLiveFilter
operator|.
name|isSelected
argument_list|()
condition|)
block|{
return|return
name|SearchMode
operator|.
name|FILTER
return|;
block|}
if|if
condition|(
name|modeGlobal
operator|.
name|isSelected
argument_list|()
condition|)
block|{
return|return
name|SearchMode
operator|.
name|GLOBAL
return|;
block|}
return|return
name|SearchMode
operator|.
name|FILTER
return|;
block|}
comment|/**      * Adds a SearchTextListener to the search bar. The added listener is immediately informed about the current search.      * Subscribers will be notified about searches.      *      * @param l SearchTextListener to be added      */
DECL|method|addSearchListener (SearchTextListener l)
specifier|public
name|void
name|addSearchListener
parameter_list|(
name|SearchTextListener
name|l
parameter_list|)
block|{
if|if
condition|(
name|listeners
operator|.
name|contains
argument_list|(
name|l
argument_list|)
condition|)
block|{
return|return;
block|}
else|else
block|{
name|listeners
operator|.
name|add
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
comment|// fire event for the new subscriber
name|l
operator|.
name|searchText
argument_list|(
name|getSearchwords
argument_list|(
name|searchField
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Remove a SearchTextListener      *      * @param l SearchTextListener to be removed      */
DECL|method|removeSearchListener (SearchTextListener l)
specifier|public
name|void
name|removeSearchListener
parameter_list|(
name|SearchTextListener
name|l
parameter_list|)
block|{
name|listeners
operator|.
name|remove
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
comment|/**      * Parses the search query for valid words and returns a list these words. For example, "The great Vikinger" will      * give ["The","great","Vikinger"]      *      * @param searchText the search query      * @return list of words found in the search query      */
DECL|method|getSearchwords (String searchText)
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|getSearchwords
parameter_list|(
name|String
name|searchText
parameter_list|)
block|{
return|return
operator|(
operator|new
name|SentenceAnalyzer
argument_list|(
name|searchText
argument_list|)
operator|)
operator|.
name|getWords
argument_list|()
return|;
block|}
comment|/**      * Fires an event if a search was started (or cleared)      *      * @param searchText the search query      */
DECL|method|fireSearchlistenerEvent (String searchText)
specifier|private
name|void
name|fireSearchlistenerEvent
parameter_list|(
name|String
name|searchText
parameter_list|)
block|{
comment|// Parse the search string to words
name|List
argument_list|<
name|String
argument_list|>
name|words
decl_stmt|;
if|if
condition|(
operator|(
name|searchText
operator|==
literal|null
operator|)
operator|||
operator|(
name|searchText
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|words
operator|=
literal|null
expr_stmt|;
block|}
else|else
block|{
name|words
operator|=
name|getSearchwords
argument_list|(
name|searchText
argument_list|)
expr_stmt|;
block|}
comment|// Fire an event for every listener
for|for
control|(
name|SearchTextListener
name|s
range|:
name|listeners
control|)
block|{
name|s
operator|.
name|searchText
argument_list|(
name|words
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Save current settings.      */
DECL|method|updatePrefs ()
specifier|public
name|void
name|updatePrefs
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_MODE_FLOAT
argument_list|,
name|modeFloat
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_MODE_LIVE_FILTER
argument_list|,
name|modeLiveFilter
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_MODE_GLOBAL
argument_list|,
name|modeGlobal
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_CASE_SENSITIVE
argument_list|,
name|caseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_REG_EXP
argument_list|,
name|regularExp
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_HIGHLIGHT_WORDS
argument_list|,
name|highlightWords
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_AUTO_COMPLETE
argument_list|,
name|autoComplete
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Focuses the search field if it is not focused. Otherwise, cycles to the next search type.      */
DECL|method|focus ()
specifier|public
name|void
name|focus
parameter_list|()
block|{
if|if
condition|(
name|searchField
operator|.
name|hasFocus
argument_list|()
condition|)
block|{
switch|switch
condition|(
name|getSearchMode
argument_list|()
condition|)
block|{
case|case
name|FLOAT
case|:
name|setSearchMode
argument_list|(
name|SearchMode
operator|.
name|FILTER
argument_list|)
expr_stmt|;
break|break;
case|case
name|FILTER
case|:
name|setSearchMode
argument_list|(
name|SearchMode
operator|.
name|GLOBAL
argument_list|)
expr_stmt|;
break|break;
case|case
name|GLOBAL
case|:
name|setSearchMode
argument_list|(
name|SearchMode
operator|.
name|FLOAT
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
else|else
block|{
name|searchField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Reacts to the change of the search text. A change in the search query results in an immediate search in      * incremental or live filter search mode.      */
DECL|method|onSearchTextChanged ()
specifier|private
name|void
name|onSearchTextChanged
parameter_list|()
block|{
if|if
condition|(
operator|(
name|getSearchMode
argument_list|()
operator|==
name|SearchMode
operator|.
name|FILTER
operator|)
operator|||
operator|(
name|getSearchMode
argument_list|()
operator|==
name|SearchMode
operator|.
name|FLOAT
operator|)
condition|)
block|{
comment|// wait until the text is changed
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|this
operator|::
name|performSearch
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Clears (asynchronously) the current search. This includes resetting the search text.      */
DECL|method|clearSearch ()
specifier|private
name|void
name|clearSearch
parameter_list|()
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|worker
operator|.
name|restart
argument_list|()
expr_stmt|;
name|searchField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|fireSearchlistenerEvent
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|this
operator|.
name|currentResults
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
comment|/**      * Performs a new search based on the current search query.      */
DECL|method|performSearch ()
specifier|private
name|void
name|performSearch
parameter_list|()
block|{
name|String
name|searchText
init|=
name|searchField
operator|.
name|getText
argument_list|()
decl_stmt|;
comment|// Notify others about the search
name|fireSearchlistenerEvent
argument_list|(
name|searchText
argument_list|)
expr_stmt|;
comment|// An empty search field should cause the search to be cleared.
if|if
condition|(
name|searchText
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
if|if
condition|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return;
block|}
comment|// TODO: General observation: Should SearchRule not contain the search query? That is upon creation the search rule safes the searchText as a private field. Then also the other methods would act versus the saved query, i.e. validateSearchString() without argument. Or is there a way a search rule is created without a search text?
comment|// Search
name|SearchRule
name|searchRule
init|=
name|SearchRules
operator|.
name|getSearchRuleByQuery
argument_list|(
name|searchText
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_CASE_SENSITIVE
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_REG_EXP
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|searchRule
operator|.
name|validateSearchStrings
argument_list|(
name|searchText
argument_list|)
condition|)
block|{
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search failed: illegal search expression"
argument_list|)
argument_list|)
expr_stmt|;
name|clearSearch
argument_list|()
expr_stmt|;
return|return;
block|}
name|worker
operator|.
name|initSearch
argument_list|(
name|getSearchQuery
argument_list|()
argument_list|,
name|getSearchMode
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO: What is the purpose of implementing the AbstractWorker interface if we call the worker that stupidly?
name|worker
operator|.
name|getWorker
argument_list|()
operator|.
name|run
argument_list|()
expr_stmt|;
name|worker
operator|.
name|getCallBack
argument_list|()
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
comment|/**      * Updates the text on the search button to reflect the type of search that will happen on click.      */
DECL|method|updateSearchButtonText ()
specifier|private
name|void
name|updateSearchButtonText
parameter_list|()
block|{
if|if
condition|(
name|GrammarBasedSearchRule
operator|.
name|isValid
argument_list|(
name|caseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|,
name|regularExp
operator|.
name|isSelected
argument_list|()
argument_list|,
name|searchField
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
name|searchButton
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search specified field(s)"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|searchButton
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search all fields"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Sets the autocompleter used in the search field.      * @param searchCompleter the autocompleter      */
DECL|method|setAutoCompleter (AutoCompleter<String> searchCompleter)
specifier|public
name|void
name|setAutoCompleter
parameter_list|(
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|searchCompleter
parameter_list|)
block|{
name|this
operator|.
name|autoCompleteSupport
operator|.
name|setAutoCompleter
argument_list|(
name|searchCompleter
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

