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
name|BasePanel
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
name|WrapLayout
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
name|*
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
name|HelpFiles
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
name|gui
operator|.
name|util
operator|.
name|JTextFieldWithUnfocusedText
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
name|worker
operator|.
name|AbstractWorker
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
name|logic
operator|.
name|search
operator|.
name|SearchQuery
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
name|SearchQueryHighlightObservable
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
name|util
operator|.
name|OS
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
name|BibEntry
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
name|Log
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
name|javax
operator|.
name|swing
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
name|KeyAdapter
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
name|KeyEvent
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
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|SearchBar
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|NO_RESULTS_COLOR
specifier|public
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
specifier|public
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
specifier|public
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
DECL|field|openCurrentResultsInDialog
specifier|private
specifier|final
name|JButton
name|openCurrentResultsInDialog
decl_stmt|;
DECL|field|globalSearch
specifier|private
specifier|final
name|JButton
name|globalSearch
decl_stmt|;
DECL|field|searchModeButton
specifier|private
specifier|final
name|JButton
name|searchModeButton
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|searchQueryHighlightObservable
specifier|private
specifier|final
name|SearchQueryHighlightObservable
name|searchQueryHighlightObservable
decl_stmt|;
DECL|field|searchField
specifier|private
specifier|final
name|JTextFieldWithUnfocusedText
name|searchField
decl_stmt|;
DECL|field|searchMode
specifier|private
name|SearchMode
name|searchMode
init|=
name|getSearchModeFromSettings
argument_list|()
decl_stmt|;
DECL|field|caseSensitive
specifier|private
specifier|final
name|JCheckBox
name|caseSensitive
decl_stmt|;
DECL|field|regularExp
specifier|private
specifier|final
name|JCheckBox
name|regularExp
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
specifier|private
name|AutoCompleteSupport
argument_list|<
name|String
argument_list|>
name|autoCompleteSupport
decl_stmt|;
DECL|field|searchIcon
specifier|private
specifier|final
name|JLabel
name|searchIcon
decl_stmt|;
comment|/**      * Initializes the search bar.      *      * @param basePanel the base panel      */
DECL|method|SearchBar (BasePanel basePanel)
specifier|public
name|SearchBar
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|basePanel
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|basePanel
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchQueryHighlightObservable
operator|=
operator|new
name|SearchQueryHighlightObservable
argument_list|()
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
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Case sensitive"
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
name|SEARCH_CASE_SENSITIVE
argument_list|)
argument_list|)
expr_stmt|;
name|caseSensitive
operator|.
name|addItemListener
argument_list|(
name|ae
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
name|caseSensitive
operator|.
name|addItemListener
argument_list|(
name|ae
lambda|->
name|updatePreferences
argument_list|()
argument_list|)
expr_stmt|;
name|regularExp
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"regular expression"
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
expr_stmt|;
name|regularExp
operator|.
name|addItemListener
argument_list|(
name|ae
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
name|regularExp
operator|.
name|addItemListener
argument_list|(
name|ae
lambda|->
name|updatePreferences
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
name|basePanel
operator|.
name|frame
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search results in database %0 for %1"
argument_list|,
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
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
name|localize
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|basePanel
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
name|basePanel
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
argument_list|)
expr_stmt|;
name|openCurrentResultsInDialog
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// Init controls
name|setLayout
argument_list|(
operator|new
name|WrapLayout
argument_list|(
name|FlowLayout
operator|.
name|LEFT
argument_list|)
argument_list|)
expr_stmt|;
name|searchIcon
operator|=
operator|new
name|JLabel
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
name|this
operator|.
name|add
argument_list|(
name|searchIcon
argument_list|)
expr_stmt|;
name|this
operator|.
name|searchField
operator|=
name|initSearchField
argument_list|()
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|searchField
operator|.
name|putClientProperty
argument_list|(
literal|"JTextField.variant"
argument_list|,
literal|"search"
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|add
argument_list|(
name|searchField
argument_list|)
expr_stmt|;
name|JButton
name|clearSearchButton
init|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|CLOSE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
decl_stmt|;
name|clearSearchButton
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear"
argument_list|)
argument_list|)
expr_stmt|;
name|clearSearchButton
operator|.
name|addActionListener
argument_list|(
parameter_list|(
name|l
parameter_list|)
lambda|->
name|endSearch
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|clearSearchButton
argument_list|)
expr_stmt|;
name|searchModeButton
operator|=
operator|new
name|JButton
argument_list|()
expr_stmt|;
name|updateSearchModeButtonText
argument_list|()
expr_stmt|;
name|searchModeButton
operator|.
name|addActionListener
argument_list|(
parameter_list|(
name|l
parameter_list|)
lambda|->
name|toggleSearchModeAndSearch
argument_list|()
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
name|clearSearchButton
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
name|openCurrentResultsInDialog
argument_list|)
expr_stmt|;
name|globalSearch
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search globally"
argument_list|)
argument_list|)
expr_stmt|;
name|globalSearch
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search in all open databases"
argument_list|)
argument_list|)
expr_stmt|;
name|globalSearch
operator|.
name|addActionListener
argument_list|(
name|l
lambda|->
block|{
name|AbstractWorker
name|worker
init|=
operator|new
name|GlobalSearchWorker
argument_list|(
name|basePanel
operator|.
name|frame
argument_list|()
argument_list|,
name|getSearchQuery
argument_list|()
argument_list|)
decl_stmt|;
name|worker
operator|.
name|run
argument_list|()
expr_stmt|;
name|worker
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|globalSearch
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
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
name|HelpFiles
operator|.
name|searchHelp
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
name|paintBackgroundWhite
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|paintBackgroundWhite (Container container)
specifier|private
name|void
name|paintBackgroundWhite
parameter_list|(
name|Container
name|container
parameter_list|)
block|{
name|container
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|WHITE
argument_list|)
expr_stmt|;
for|for
control|(
name|Component
name|component
range|:
name|container
operator|.
name|getComponents
argument_list|()
control|)
block|{
name|component
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|WHITE
argument_list|)
expr_stmt|;
if|if
condition|(
name|component
operator|instanceof
name|Container
condition|)
block|{
name|paintBackgroundWhite
argument_list|(
operator|(
name|Container
operator|)
name|component
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|getSearchModeFromSettings ()
specifier|private
specifier|static
name|SearchMode
name|getSearchModeFromSettings
parameter_list|()
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SEARCH_MODE_FILTER
argument_list|)
condition|)
block|{
return|return
name|SearchMode
operator|.
name|FILTER
return|;
block|}
elseif|else
if|if
condition|(
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
condition|)
block|{
return|return
name|SearchMode
operator|.
name|FLOAT
return|;
block|}
else|else
block|{
return|return
name|SearchMode
operator|.
name|FILTER
return|;
block|}
block|}
DECL|method|toggleSearchModeAndSearch ()
specifier|private
name|void
name|toggleSearchModeAndSearch
parameter_list|()
block|{
name|this
operator|.
name|searchMode
operator|=
name|searchMode
operator|==
name|SearchMode
operator|.
name|FILTER
condition|?
name|SearchMode
operator|.
name|FLOAT
else|:
name|SearchMode
operator|.
name|FILTER
expr_stmt|;
name|updatePreferences
argument_list|()
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
name|searchMode
operator|.
name|getDisplayName
argument_list|()
argument_list|)
expr_stmt|;
name|searchModeButton
operator|.
name|setToolTipText
argument_list|(
name|searchMode
operator|.
name|getToolTipText
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Initializes the search text field      */
DECL|method|initSearchField ()
specifier|private
name|JTextFieldWithUnfocusedText
name|initSearchField
parameter_list|()
block|{
name|JTextFieldWithUnfocusedText
name|searchField
init|=
operator|new
name|JTextFieldWithUnfocusedText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search"
argument_list|)
operator|+
literal|"..."
argument_list|)
decl_stmt|;
name|searchField
operator|.
name|setColumns
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|addKeyListener
argument_list|(
operator|new
name|KeyAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|keyReleased
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getExtendedKeyCode
argument_list|()
operator|==
name|KeyEvent
operator|.
name|VK_ESCAPE
condition|)
block|{
name|endSearch
argument_list|()
expr_stmt|;
block|}
block|}
block|}
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
name|JTextFieldChangeListenerUtil
operator|.
name|addChangeListener
argument_list|(
name|searchField
argument_list|,
name|e
lambda|->
name|performSearch
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|searchField
return|;
block|}
DECL|method|endSearch ()
specifier|private
name|void
name|endSearch
parameter_list|()
block|{
comment|// first focus request is necessary so that the UI stays nice
name|basePanel
operator|.
name|mainTable
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
name|clearSearch
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|mainTable
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
comment|/**      * Save current settings.      */
DECL|method|updatePreferences ()
specifier|public
name|void
name|updatePreferences
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
name|searchMode
operator|==
name|SearchMode
operator|.
name|FLOAT
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
name|SEARCH_MODE_FILTER
argument_list|,
name|searchMode
operator|==
name|SearchMode
operator|.
name|FILTER
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
name|hasFocus
argument_list|()
condition|)
block|{
name|searchField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Clears the current search. This includes resetting the search text.      */
DECL|method|clearSearch ()
specifier|private
name|void
name|clearSearch
parameter_list|()
block|{
name|searchField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|WHITE
argument_list|)
expr_stmt|;
name|searchQueryHighlightObservable
operator|.
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
name|basePanel
operator|.
name|stopShowingFloatSearch
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|getFilterSearchToggle
argument_list|()
operator|.
name|stop
argument_list|()
expr_stmt|;
name|globalSearch
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|openCurrentResultsInDialog
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|searchIcon
operator|.
name|setIcon
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
block|}
comment|/**      * Performs a new search based on the current search query.      */
DECL|method|performSearch ()
specifier|private
name|void
name|performSearch
parameter_list|()
block|{
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
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Searching "
operator|+
name|searchQuery
operator|+
literal|" in "
operator|+
name|basePanel
operator|.
name|getTabTitle
argument_list|()
argument_list|)
expr_stmt|;
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
name|SearchWorker
name|worker
init|=
operator|new
name|SearchWorker
argument_list|(
name|basePanel
argument_list|,
name|searchQuery
argument_list|,
name|searchMode
argument_list|)
decl_stmt|;
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
DECL|method|informUserAboutInvalidSearchQuery ()
specifier|private
name|void
name|informUserAboutInvalidSearchQuery
parameter_list|()
block|{
name|searchField
operator|.
name|setBackground
argument_list|(
name|NO_RESULTS_COLOR
argument_list|)
expr_stmt|;
name|searchQueryHighlightObservable
operator|.
name|fireSearchlistenerEvent
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|globalSearch
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|openCurrentResultsInDialog
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|stopShowingFloatSearch
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|getFilterSearchToggle
argument_list|()
operator|.
name|stop
argument_list|()
expr_stmt|;
name|searchIcon
operator|.
name|setIcon
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|SEARCH
operator|.
name|getSmallIcon
argument_list|()
operator|.
name|createWithNewColor
argument_list|(
name|NO_RESULTS_COLOR
argument_list|)
argument_list|)
expr_stmt|;
name|searchIcon
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search failed: illegal search expression"
argument_list|)
argument_list|)
expr_stmt|;
name|currentResults
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search failed: illegal search expression"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Sets the autocompleter used in the search field.      *      * @param searchCompleter the autocompleter      */
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
comment|// nothing found
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
name|this
operator|.
name|searchField
operator|.
name|setBackground
argument_list|(
name|NO_RESULTS_COLOR
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// specific set found, could be all
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
name|this
operator|.
name|searchField
operator|.
name|setBackground
argument_list|(
name|RESULTS_FOUND_COLOR
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
if|if
condition|(
name|grammarBasedSearch
condition|)
block|{
name|searchIcon
operator|.
name|setIcon
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|SEARCH
operator|.
name|getSmallIcon
argument_list|()
operator|.
name|createWithNewColor
argument_list|(
name|ADVANCED_SEARCH_COLOR
argument_list|)
argument_list|)
expr_stmt|;
name|searchIcon
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Advanced search active."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|searchIcon
operator|.
name|setIcon
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
name|searchIcon
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Normal search active."
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|globalSearch
operator|.
name|setEnabled
argument_list|(
literal|true
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
block|}
end_class

end_unit

