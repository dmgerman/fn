begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.      This program is free software: you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation, either version 3 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License     along with this program.  If not, see<http://www.gnu.org/licenses/>.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.actions
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
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
name|awt
operator|.
name|event
operator|.
name|ActionListener
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
name|awt
operator|.
name|event
operator|.
name|KeyListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ActionMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ButtonGroup
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultListModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|InputMap
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
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JRadioButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|JabRefGUI
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
name|AutoCompleteListener
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
name|keyboard
operator|.
name|KeyBinding
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableFieldChange
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
name|model
operator|.
name|FieldChange
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
name|FieldName
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
name|preferences
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
name|specialfields
operator|.
name|Printed
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
name|specialfields
operator|.
name|Priority
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
name|specialfields
operator|.
name|Quality
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
name|specialfields
operator|.
name|Rank
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
name|specialfields
operator|.
name|ReadStatus
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
name|specialfields
operator|.
name|Relevance
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
name|specialfields
operator|.
name|SpecialFieldsUtils
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_comment
comment|/**  * An Action for launching keyword managing dialog  *  */
end_comment

begin_class
DECL|class|ManageKeywordsAction
specifier|public
class|class
name|ManageKeywordsAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|diag
specifier|private
name|JDialog
name|diag
decl_stmt|;
DECL|field|keywordListModel
specifier|private
name|DefaultListModel
argument_list|<
name|String
argument_list|>
name|keywordListModel
decl_stmt|;
DECL|field|intersectKeywords
specifier|private
name|JRadioButton
name|intersectKeywords
decl_stmt|;
DECL|field|mergeKeywords
specifier|private
name|JRadioButton
name|mergeKeywords
decl_stmt|;
DECL|field|canceled
specifier|private
name|boolean
name|canceled
decl_stmt|;
DECL|field|sortedKeywordsOfAllEntriesBeforeUpdateByUser
specifier|private
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|sortedKeywordsOfAllEntriesBeforeUpdateByUser
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|ManageKeywordsAction (JabRefFrame frame)
specifier|public
name|ManageKeywordsAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Manage keywords"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
DECL|method|createDialog ()
specifier|private
name|void
name|createDialog
parameter_list|()
block|{
if|if
condition|(
name|diag
operator|!=
literal|null
condition|)
block|{
return|return;
block|}
comment|// keyword to add
name|JTextField
name|keyword
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
name|keywordListModel
operator|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
expr_stmt|;
name|JList
argument_list|<
name|String
argument_list|>
name|keywordList
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|keywordListModel
argument_list|)
decl_stmt|;
name|keywordList
operator|.
name|setVisibleRowCount
argument_list|(
literal|8
argument_list|)
expr_stmt|;
name|JScrollPane
name|kPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|keywordList
argument_list|)
decl_stmt|;
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage keywords"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|add
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|remove
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|)
decl_stmt|;
name|keywordList
operator|.
name|setVisibleRowCount
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|intersectKeywords
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Display keywords appearing in ALL entries"
argument_list|)
argument_list|)
expr_stmt|;
name|mergeKeywords
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Display keywords appearing in ANY entry"
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonGroup
name|group
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|group
operator|.
name|add
argument_list|(
name|intersectKeywords
argument_list|)
expr_stmt|;
name|group
operator|.
name|add
argument_list|(
name|mergeKeywords
argument_list|)
expr_stmt|;
name|ActionListener
name|stateChanged
init|=
name|e
lambda|->
name|fillKeyWordList
argument_list|()
decl_stmt|;
name|intersectKeywords
operator|.
name|addActionListener
argument_list|(
name|stateChanged
argument_list|)
expr_stmt|;
name|mergeKeywords
operator|.
name|addActionListener
argument_list|(
name|stateChanged
argument_list|)
expr_stmt|;
name|intersectKeywords
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"fill:200dlu:grow, 4dlu, fill:pref"
argument_list|,
literal|"pref, 2dlu, pref, 1dlu, pref, 2dlu, fill:100dlu:grow, 4dlu, pref, 4dlu, pref, "
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keywords of selected entries"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|intersectKeywords
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|mergeKeywords
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|kPane
argument_list|)
operator|.
name|xywh
argument_list|(
literal|1
argument_list|,
literal|7
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|remove
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|9
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|keyword
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|add
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|11
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|canceled
operator|=
literal|false
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|Action
name|cancelAction
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
name|canceled
operator|=
literal|true
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
specifier|final
name|ActionListener
name|addActionListener
init|=
name|arg0
lambda|->
name|addButtonActionListener
argument_list|(
name|keyword
argument_list|)
decl_stmt|;
name|add
operator|.
name|addActionListener
argument_list|(
name|addActionListener
argument_list|)
expr_stmt|;
specifier|final
name|ActionListener
name|removeActionListenter
init|=
name|arg0
lambda|->
block|{
comment|// keywordList.getSelectedIndices(); does not work, therefore we operate on the values
name|List
argument_list|<
name|String
argument_list|>
name|values
init|=
name|keywordList
operator|.
name|getSelectedValuesList
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|val
range|:
name|values
control|)
block|{
name|keywordListModel
operator|.
name|removeElement
argument_list|(
name|val
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|remove
operator|.
name|addActionListener
argument_list|(
name|removeActionListenter
argument_list|)
expr_stmt|;
name|keywordList
operator|.
name|addKeyListener
argument_list|(
operator|new
name|KeyListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|keyTyped
parameter_list|(
name|KeyEvent
name|arg0
parameter_list|)
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
specifier|public
name|void
name|keyReleased
parameter_list|(
name|KeyEvent
name|arg0
parameter_list|)
block|{
comment|// Do nothing
block|}
annotation|@
name|Override
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|arg0
parameter_list|)
block|{
if|if
condition|(
name|arg0
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyEvent
operator|.
name|VK_DELETE
condition|)
block|{
name|removeActionListenter
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoComp
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getAutoCompleters
argument_list|()
operator|.
name|get
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
decl_stmt|;
name|AutoCompleteListener
name|acl
init|=
operator|new
name|AutoCompleteListener
argument_list|(
name|autoComp
argument_list|)
decl_stmt|;
name|keyword
operator|.
name|addKeyListener
argument_list|(
name|acl
argument_list|)
expr_stmt|;
name|keyword
operator|.
name|addFocusListener
argument_list|(
name|acl
argument_list|)
expr_stmt|;
name|keyword
operator|.
name|addKeyListener
argument_list|(
operator|new
name|KeyListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|keyTyped
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
comment|// Do nothing
block|}
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
comment|// Do nothing
block|}
annotation|@
name|Override
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyEvent
operator|.
name|VK_ENTER
condition|)
block|{
name|addActionListener
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
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
name|CLOSE_DIALOG
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
block|}
DECL|method|addButtonActionListener (JTextField keyword)
specifier|private
name|void
name|addButtonActionListener
parameter_list|(
name|JTextField
name|keyword
parameter_list|)
block|{
name|String
name|text
init|=
name|keyword
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|text
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|keywordListModel
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keywordListModel
operator|.
name|addElement
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|int
name|idx
init|=
literal|0
decl_stmt|;
name|String
name|element
init|=
name|keywordListModel
operator|.
name|getElementAt
argument_list|(
name|idx
argument_list|)
decl_stmt|;
while|while
condition|(
operator|(
name|idx
operator|<
name|keywordListModel
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|(
name|element
operator|.
name|compareTo
argument_list|(
name|text
argument_list|)
operator|<
literal|0
operator|)
condition|)
block|{
name|idx
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|idx
operator|==
name|keywordListModel
operator|.
name|size
argument_list|()
condition|)
block|{
comment|// list is empty or word is greater than last word in list
name|keywordListModel
operator|.
name|addElement
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|element
operator|.
name|compareTo
argument_list|(
name|text
argument_list|)
operator|==
literal|0
condition|)
block|{
comment|// nothing to do, word already in table
block|}
else|else
block|{
name|keywordListModel
operator|.
name|add
argument_list|(
name|idx
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
block|}
name|keyword
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|keyword
operator|.
name|requestFocusInWindow
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|BasePanel
name|bp
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|bp
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|bp
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|bp
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select at least one entry to manage keywords."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// Lazy creation of the dialog:
name|createDialog
argument_list|()
expr_stmt|;
name|canceled
operator|=
literal|true
expr_stmt|;
name|fillKeyWordList
argument_list|()
expr_stmt|;
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|canceled
condition|)
block|{
return|return;
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|keywordsToAdd
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|userSelectedKeywords
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
comment|// build keywordsToAdd and userSelectedKeywords in parallel
for|for
control|(
name|Enumeration
argument_list|<
name|String
argument_list|>
name|keywords
init|=
name|keywordListModel
operator|.
name|elements
argument_list|()
init|;
name|keywords
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|String
name|keyword
init|=
name|keywords
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|userSelectedKeywords
operator|.
name|add
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|sortedKeywordsOfAllEntriesBeforeUpdateByUser
operator|.
name|contains
argument_list|(
name|keyword
argument_list|)
condition|)
block|{
name|keywordsToAdd
operator|.
name|add
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
block|}
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|keywordsToRemove
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|kword
range|:
name|sortedKeywordsOfAllEntriesBeforeUpdateByUser
control|)
block|{
if|if
condition|(
operator|!
name|userSelectedKeywords
operator|.
name|contains
argument_list|(
name|kword
argument_list|)
condition|)
block|{
name|keywordsToRemove
operator|.
name|add
argument_list|(
name|kword
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|keywordsToAdd
operator|.
name|isEmpty
argument_list|()
operator|&&
name|keywordsToRemove
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// nothing to be done if nothing is new and nothing is obsolete
return|return;
block|}
if|if
condition|(
name|SpecialFieldsUtils
operator|.
name|keywordSyncEnabled
argument_list|()
operator|&&
operator|!
name|keywordsToAdd
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|synchronizeSpecialFields
argument_list|(
name|keywordsToAdd
argument_list|,
name|keywordsToRemove
argument_list|)
expr_stmt|;
block|}
name|NamedCompound
name|ce
init|=
name|updateKeywords
argument_list|(
name|bp
operator|.
name|getSelectedEntries
argument_list|()
argument_list|,
name|keywordsToAdd
argument_list|,
name|keywordsToRemove
argument_list|)
decl_stmt|;
name|bp
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|bp
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
DECL|method|updateKeywords (List<BibEntry> entries, Set<String> keywordsToAdd, Set<String> keywordsToRemove)
specifier|private
name|NamedCompound
name|updateKeywords
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keywordsToAdd
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keywordsToRemove
parameter_list|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Update keywords"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|keywords
init|=
name|entry
operator|.
name|getKeywords
argument_list|()
decl_stmt|;
comment|// update keywords
name|keywords
operator|.
name|removeAll
argument_list|(
name|keywordsToRemove
argument_list|)
expr_stmt|;
name|keywords
operator|.
name|addAll
argument_list|(
name|keywordsToAdd
argument_list|)
expr_stmt|;
comment|// put keywords back
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|entry
operator|.
name|putKeywords
argument_list|(
name|keywords
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|KEYWORD_SEPARATOR
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|change
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|change
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|SpecialFieldsUtils
operator|.
name|keywordSyncEnabled
argument_list|()
condition|)
block|{
name|SpecialFieldsUtils
operator|.
name|syncSpecialFieldsFromKeywords
argument_list|(
name|entry
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
return|return
name|ce
return|;
block|}
DECL|method|synchronizeSpecialFields (Set<String> keywordsToAdd, Set<String> keywordsToRemove)
specifier|private
name|void
name|synchronizeSpecialFields
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|keywordsToAdd
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|keywordsToRemove
parameter_list|)
block|{
comment|// we need to check whether a special field is added
comment|// for each field:
comment|//   check if something is added
comment|//   if yes, add all keywords of that special fields to the keywords to be removed
name|Set
argument_list|<
name|String
argument_list|>
name|clone
decl_stmt|;
comment|// Priority
name|clone
operator|=
name|createClone
argument_list|(
name|keywordsToAdd
argument_list|)
expr_stmt|;
name|clone
operator|.
name|retainAll
argument_list|(
name|Priority
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|clone
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keywordsToRemove
operator|.
name|addAll
argument_list|(
name|Priority
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Quality
name|clone
operator|=
name|createClone
argument_list|(
name|keywordsToAdd
argument_list|)
expr_stmt|;
name|clone
operator|.
name|retainAll
argument_list|(
name|Quality
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|clone
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keywordsToRemove
operator|.
name|addAll
argument_list|(
name|Quality
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Rank
name|clone
operator|=
name|createClone
argument_list|(
name|keywordsToAdd
argument_list|)
expr_stmt|;
name|clone
operator|.
name|retainAll
argument_list|(
name|Rank
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|clone
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keywordsToRemove
operator|.
name|addAll
argument_list|(
name|Rank
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Relevance
name|clone
operator|=
name|createClone
argument_list|(
name|keywordsToAdd
argument_list|)
expr_stmt|;
name|clone
operator|.
name|retainAll
argument_list|(
name|Relevance
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|clone
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keywordsToRemove
operator|.
name|addAll
argument_list|(
name|Relevance
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Read status
name|clone
operator|=
name|createClone
argument_list|(
name|keywordsToAdd
argument_list|)
expr_stmt|;
name|clone
operator|.
name|retainAll
argument_list|(
name|ReadStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|clone
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keywordsToRemove
operator|.
name|addAll
argument_list|(
name|ReadStatus
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Printed
name|clone
operator|=
name|createClone
argument_list|(
name|keywordsToAdd
argument_list|)
expr_stmt|;
name|clone
operator|.
name|retainAll
argument_list|(
name|Printed
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|clone
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keywordsToRemove
operator|.
name|addAll
argument_list|(
name|Printed
operator|.
name|getInstance
argument_list|()
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|createClone (Set<String> keywordsToAdd)
specifier|private
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|createClone
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|keywordsToAdd
parameter_list|)
block|{
return|return
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|keywordsToAdd
argument_list|)
return|;
block|}
DECL|method|fillKeyWordList ()
specifier|private
name|void
name|fillKeyWordList
parameter_list|()
block|{
name|BasePanel
name|bp
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|bp
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
comment|// fill dialog with values
name|keywordListModel
operator|.
name|clear
argument_list|()
expr_stmt|;
name|sortedKeywordsOfAllEntriesBeforeUpdateByUser
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|mergeKeywords
operator|.
name|isSelected
argument_list|()
condition|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|separatedKeywords
init|=
name|entry
operator|.
name|getKeywords
argument_list|()
decl_stmt|;
name|sortedKeywordsOfAllEntriesBeforeUpdateByUser
operator|.
name|addAll
argument_list|(
name|separatedKeywords
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
assert|assert
name|intersectKeywords
operator|.
name|isSelected
argument_list|()
assert|;
comment|// all keywords from first entry have to be added
name|BibEntry
name|firstEntry
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|separatedKeywords
init|=
name|firstEntry
operator|.
name|getKeywords
argument_list|()
decl_stmt|;
name|sortedKeywordsOfAllEntriesBeforeUpdateByUser
operator|.
name|addAll
argument_list|(
name|separatedKeywords
argument_list|)
expr_stmt|;
comment|// for the remaining entries, intersection has to be used
comment|// this approach ensures that one empty keyword list leads to an empty set of common keywords
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|entries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|separatedKeywords
operator|=
name|entry
operator|.
name|getKeywords
argument_list|()
expr_stmt|;
name|sortedKeywordsOfAllEntriesBeforeUpdateByUser
operator|.
name|retainAll
argument_list|(
name|separatedKeywords
argument_list|)
expr_stmt|;
block|}
block|}
for|for
control|(
name|String
name|s
range|:
name|sortedKeywordsOfAllEntriesBeforeUpdateByUser
control|)
block|{
name|keywordListModel
operator|.
name|addElement
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

