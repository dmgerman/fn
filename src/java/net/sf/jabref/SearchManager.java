begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 JabRef team  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
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
name|*
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

begin_class
DECL|class|SearchManager
class|class
name|SearchManager
extends|extends
name|JPanel
implements|implements
name|ActionListener
implements|,
name|KeyListener
implements|,
name|ItemListener
block|{
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|incSearcher
name|IncrementalSearcher
name|incSearcher
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|searchField
specifier|private
name|JTextField
name|searchField
init|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
literal|20
argument_list|)
decl_stmt|;
DECL|field|lab
specifier|private
name|JLabel
name|lab
init|=
comment|//new JLabel(Globals.lang("Search")+":");
operator|new
name|JLabel
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|searchIconFile
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|settings
specifier|private
name|JPopupMenu
name|settings
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|field|openset
specifier|private
name|JButton
name|openset
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|escape
name|escape
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Clear search"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|prefs
specifier|private
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|searchReq
DECL|field|searchOpt
DECL|field|searchGen
specifier|private
name|JCheckBoxMenuItem
name|searchReq
decl_stmt|,
name|searchOpt
decl_stmt|,
name|searchGen
decl_stmt|,
DECL|field|searchAll
DECL|field|caseSensitive
DECL|field|regExpSearch
name|searchAll
decl_stmt|,
name|caseSensitive
decl_stmt|,
name|regExpSearch
decl_stmt|;
DECL|field|increment
DECL|field|select
DECL|field|reorder
specifier|private
name|JCheckBox
name|increment
decl_stmt|,
name|select
decl_stmt|,
name|reorder
decl_stmt|;
DECL|field|types
specifier|private
name|ButtonGroup
name|types
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
DECL|field|ths
specifier|private
name|SearchManager
name|ths
init|=
name|this
decl_stmt|;
DECL|field|incSearchPos
specifier|private
name|int
name|incSearchPos
init|=
operator|-
literal|1
decl_stmt|;
comment|// To keep track of where we are in
comment|// an incremental search. -1 means
comment|// that the search is inactive.
DECL|method|SearchManager (JabRefFrame frame, JabRefPreferences prefs_)
specifier|public
name|SearchManager
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|JabRefPreferences
name|prefs_
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|prefs
operator|=
name|prefs_
expr_stmt|;
name|incSearcher
operator|=
operator|new
name|IncrementalSearcher
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|searchReq
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search required fields"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchReq"
argument_list|)
argument_list|)
expr_stmt|;
name|searchOpt
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search optional fields"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchOpt"
argument_list|)
argument_list|)
expr_stmt|;
name|searchGen
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search general fields"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchGen"
argument_list|)
argument_list|)
expr_stmt|;
name|searchAll
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Search all fields"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"searchAll"
argument_list|)
argument_list|)
expr_stmt|;
name|regExpSearch
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use regular expressions"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"regExpSearch"
argument_list|)
argument_list|)
expr_stmt|;
name|increment
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Incremental"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|select
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Highlight"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|reorder
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Float"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// Add an item listener that makes sure we only listen for key events
comment|// when incremental search is turned on.
name|increment
operator|.
name|addItemListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|reorder
operator|.
name|addItemListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
if|if
condition|(
name|searchAll
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|searchReq
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|searchOpt
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|searchGen
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|caseSensitive
operator|=
operator|new
name|JCheckBoxMenuItem
argument_list|(
literal|"Case sensitive"
argument_list|,
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|)
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|caseSensitive
argument_list|)
expr_stmt|;
name|settings
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|searchReq
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|searchOpt
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|searchGen
argument_list|)
expr_stmt|;
name|settings
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|searchAll
argument_list|)
expr_stmt|;
name|settings
operator|.
name|addSeparator
argument_list|()
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|regExpSearch
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|addFocusListener
argument_list|(
operator|new
name|FocusAdapter
argument_list|()
block|{
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
comment|// Reset incremental
comment|// search. This makes the
comment|// incremental search reset
comment|// once the user moves focus to
comment|// somewhere else.
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
name|searchField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|escape
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|openset
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|JButton
name|src
init|=
operator|(
name|JButton
operator|)
name|e
operator|.
name|getSource
argument_list|()
decl_stmt|;
name|settings
operator|.
name|show
argument_list|(
name|src
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|increment
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|select
argument_list|)
expr_stmt|;
name|types
operator|.
name|add
argument_list|(
name|reorder
argument_list|)
expr_stmt|;
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"incrementS"
argument_list|)
condition|)
name|increment
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
operator|!
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"selectS"
argument_list|)
condition|)
name|reorder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|3
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|searchField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|searchField
argument_list|)
expr_stmt|;
comment|//con.anchor = GridBagConstraints.CENTER;
comment|//con.insets = new Insets(0, 10, 0, 10);
comment|//gbl.setConstraints(searchButton,con);
comment|//getContentPane().add(searchButton) ;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
comment|//	con.gridheight = 2;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|openset
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|openset
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
comment|//	con.gridheight = 1;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|JPanel
name|empt
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|empt
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|empt
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|increment
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|increment
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|select
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|select
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|reorder
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|reorder
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|escape
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|escape
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Repeat incremental search"
argument_list|)
argument_list|,
literal|"repeat"
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"repeat"
argument_list|,
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
name|repeatIncremental
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Clear search"
argument_list|)
argument_list|,
literal|"escape"
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"escape"
argument_list|,
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|ths
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|escape
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|updatePrefs ()
specifier|protected
name|void
name|updatePrefs
parameter_list|()
block|{
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"searchReq"
argument_list|,
name|searchReq
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"searchOpt"
argument_list|,
name|searchOpt
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"searchGen"
argument_list|,
name|searchGen
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"searchAll"
argument_list|,
name|searchAll
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"incrementS"
argument_list|,
name|increment
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"selectS"
argument_list|,
name|select
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|,
name|caseSensitive
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"regExpSearch"
argument_list|,
name|regExpSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|startIncrementalSearch ()
specifier|public
name|void
name|startIncrementalSearch
parameter_list|()
block|{
name|increment
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
comment|/**      * Clears and focuses the search field if it is not      * focused. Otherwise, cycles to the next search type.           */
DECL|method|startSearch ()
specifier|public
name|void
name|startSearch
parameter_list|()
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
operator|&&
operator|(
name|incSearchPos
operator|>=
literal|0
operator|)
condition|)
block|{
name|repeatIncremental
argument_list|()
expr_stmt|;
return|return;
block|}
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
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|searchField
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
name|select
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|select
operator|.
name|isSelected
argument_list|()
condition|)
name|reorder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|increment
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|escape
condition|)
block|{
if|if
condition|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|!=
literal|null
condition|)
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|stopShowingSearchResults
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|searchField
operator|)
operator|&&
operator|!
name|increment
operator|.
name|isSelected
argument_list|()
operator|&&
operator|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
name|updatePrefs
argument_list|()
expr_stmt|;
comment|// Make sure the user's choices are recorded.
comment|// Setup search parameters common to both highlight and float.
name|Hashtable
name|searchOptions
init|=
operator|new
name|Hashtable
argument_list|()
decl_stmt|;
name|searchOptions
operator|.
name|put
argument_list|(
literal|"option"
argument_list|,
name|searchField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|SearchRuleSet
name|searchRules
init|=
operator|new
name|SearchRuleSet
argument_list|()
decl_stmt|;
name|SearchRule
name|rule1
decl_stmt|;
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"regExpSearch"
argument_list|)
condition|)
name|rule1
operator|=
operator|new
name|RegExpRule
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
else|else
name|rule1
operator|=
operator|new
name|SimpleSearchRule
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|searchRules
operator|.
name|addRule
argument_list|(
name|rule1
argument_list|)
expr_stmt|;
if|if
condition|(
name|reorder
operator|.
name|isSelected
argument_list|()
condition|)
block|{
comment|// Float search.
name|DatabaseSearch
name|search
init|=
operator|new
name|DatabaseSearch
argument_list|(
name|searchOptions
argument_list|,
name|searchRules
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
argument_list|,
name|DatabaseSearch
operator|.
name|SEARCH
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|search
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|select
operator|.
name|isSelected
argument_list|()
condition|)
block|{
comment|// Highlight search.
name|DatabaseSearch
name|search
init|=
operator|new
name|DatabaseSearch
argument_list|(
name|searchOptions
argument_list|,
name|searchRules
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
argument_list|,
name|DatabaseSearch
operator|.
name|SEARCH
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|search
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
comment|// Afterwards, select all text in the search field.
name|searchField
operator|.
name|select
argument_list|(
literal|0
argument_list|,
name|searchField
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|itemStateChanged (ItemEvent e)
specifier|public
name|void
name|itemStateChanged
parameter_list|(
name|ItemEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|increment
condition|)
block|{
if|if
condition|(
name|increment
operator|.
name|isSelected
argument_list|()
condition|)
name|searchField
operator|.
name|addKeyListener
argument_list|(
name|ths
argument_list|)
expr_stmt|;
else|else
name|searchField
operator|.
name|removeKeyListener
argument_list|(
name|ths
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|reorder
condition|)
block|{
comment|// If this search type is disabled, remove reordering from
comment|// all databases.
if|if
condition|(
operator|!
name|reorder
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|frame
operator|.
name|stopShowingSearchResults
argument_list|()
expr_stmt|;
block|}
block|}
block|}
DECL|method|repeatIncremental ()
specifier|private
name|void
name|repeatIncremental
parameter_list|()
block|{
name|incSearchPos
operator|++
expr_stmt|;
if|if
condition|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|!=
literal|null
condition|)
name|goIncremental
argument_list|()
expr_stmt|;
block|}
comment|/**      * Used for incremental search. Only activated when incremental      * is selected.      *      * The variable incSearchPos keeps track of which entry was last      * checked.      */
DECL|method|keyTyped (KeyEvent e)
specifier|public
name|void
name|keyTyped
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|isControlDown
argument_list|()
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|frame
operator|.
name|basePanel
argument_list|()
operator|!=
literal|null
condition|)
name|goIncremental
argument_list|()
expr_stmt|;
block|}
DECL|method|goIncremental ()
specifier|private
name|void
name|goIncremental
parameter_list|()
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|String
name|text
init|=
name|searchField
operator|.
name|getText
argument_list|()
decl_stmt|;
name|BasePanel
name|bp
init|=
name|frame
operator|.
name|basePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|incSearchPos
operator|>=
name|bp
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
literal|"'"
operator|+
name|text
operator|+
literal|"' : "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Incremental search failed. Repeat to search from top."
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
return|return;
block|}
if|if
condition|(
name|searchField
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
return|return;
if|if
condition|(
name|incSearchPos
operator|<
literal|0
condition|)
name|incSearchPos
operator|=
literal|0
expr_stmt|;
name|BibtexEntry
name|be
init|=
name|bp
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryById
argument_list|(
name|bp
operator|.
name|tableModel
operator|.
name|getNameFromNumber
argument_list|(
name|incSearchPos
argument_list|)
argument_list|)
decl_stmt|;
while|while
condition|(
operator|!
name|incSearcher
operator|.
name|search
argument_list|(
name|text
argument_list|,
name|be
argument_list|)
condition|)
block|{
name|incSearchPos
operator|++
expr_stmt|;
if|if
condition|(
name|incSearchPos
operator|<
name|bp
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
condition|)
name|be
operator|=
name|bp
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryById
argument_list|(
name|bp
operator|.
name|tableModel
operator|.
name|getNameFromNumber
argument_list|(
name|incSearchPos
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|frame
operator|.
name|output
argument_list|(
literal|"'"
operator|+
name|text
operator|+
literal|"' : "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Incremental search failed. Repeat to search from top."
argument_list|)
argument_list|)
expr_stmt|;
name|incSearchPos
operator|=
operator|-
literal|1
expr_stmt|;
return|return;
block|}
block|}
if|if
condition|(
name|incSearchPos
operator|>=
literal|0
condition|)
block|{
name|bp
operator|.
name|selectSingleEntry
argument_list|(
name|incSearchPos
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
literal|"'"
operator|+
name|text
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"found"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|keyPressed (KeyEvent e)
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{}
DECL|method|keyReleased (KeyEvent e)
specifier|public
name|void
name|keyReleased
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{}
block|}
end_class

end_unit

