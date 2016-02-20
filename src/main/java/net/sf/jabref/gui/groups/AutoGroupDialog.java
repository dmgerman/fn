begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|groups
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
name|ArrayList
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
name|CaretEvent
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
name|CaretListener
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
name|logic
operator|.
name|groups
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
name|util
operator|.
name|PositionWindow
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
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_comment
comment|/**  * Dialog for creating or modifying groups. Operates directly on the Vector containing group information.  */
end_comment

begin_class
DECL|class|AutoGroupDialog
class|class
name|AutoGroupDialog
extends|extends
name|JDialog
implements|implements
name|CaretListener
block|{
DECL|field|remove
specifier|private
specifier|final
name|JTextField
name|remove
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|field
specifier|private
specifier|final
name|JTextField
name|field
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|deliminator
specifier|private
specifier|final
name|JTextField
name|deliminator
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|;
DECL|field|keywords
specifier|private
specifier|final
name|JRadioButton
name|keywords
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate groups from keywords in a BibTeX field"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|authors
specifier|private
specifier|final
name|JRadioButton
name|authors
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate groups for author last names"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|editors
specifier|private
specifier|final
name|JRadioButton
name|editors
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate groups for editor last names"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|nd
specifier|private
specifier|final
name|JCheckBox
name|nd
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Use the following delimiter character(s):"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
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
DECL|field|m_groupsRoot
specifier|private
specifier|final
name|GroupTreeNodeViewModel
name|m_groupsRoot
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|gs
specifier|private
specifier|final
name|GroupSelector
name|gs
decl_stmt|;
comment|/**      * @param groupsRoot The original set of groups, which is required as undo information when all groups are cleared.      */
DECL|method|AutoGroupDialog (JabRefFrame jabrefFrame, BasePanel basePanel, GroupSelector groupSelector, GroupTreeNodeViewModel groupsRoot, String defaultField, String defaultRemove, String defaultDeliminator)
specifier|public
name|AutoGroupDialog
parameter_list|(
name|JabRefFrame
name|jabrefFrame
parameter_list|,
name|BasePanel
name|basePanel
parameter_list|,
name|GroupSelector
name|groupSelector
parameter_list|,
name|GroupTreeNodeViewModel
name|groupsRoot
parameter_list|,
name|String
name|defaultField
parameter_list|,
name|String
name|defaultRemove
parameter_list|,
name|String
name|defaultDeliminator
parameter_list|)
block|{
name|super
argument_list|(
name|jabrefFrame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically create groups"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|frame
operator|=
name|jabrefFrame
expr_stmt|;
name|gs
operator|=
name|groupSelector
expr_stmt|;
name|panel
operator|=
name|basePanel
expr_stmt|;
name|m_groupsRoot
operator|=
name|groupsRoot
expr_stmt|;
name|field
operator|.
name|setText
argument_list|(
name|defaultField
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setText
argument_list|(
name|defaultRemove
argument_list|)
expr_stmt|;
name|deliminator
operator|.
name|setText
argument_list|(
name|defaultDeliminator
argument_list|)
expr_stmt|;
name|nd
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|ActionListener
name|okListener
init|=
operator|new
name|ActionListener
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
name|dispose
argument_list|()
expr_stmt|;
name|GroupTreeNode
name|autoGroupsRoot
init|=
operator|new
name|GroupTreeNode
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically created groups"
argument_list|)
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|)
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|hs
decl_stmt|;
name|String
name|fieldText
init|=
name|field
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|keywords
operator|.
name|isSelected
argument_list|()
condition|)
block|{
if|if
condition|(
name|nd
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|hs
operator|=
name|GroupsUtil
operator|.
name|findDeliminatedWordsInField
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|field
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|deliminator
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|hs
operator|=
name|GroupsUtil
operator|.
name|findAllWordsInField
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|field
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|remove
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|authors
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|fields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|fields
operator|.
name|add
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|hs
operator|=
name|GroupsUtil
operator|.
name|findAuthorLastNames
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
name|fieldText
operator|=
literal|"author"
expr_stmt|;
block|}
else|else
block|{
comment|// editors.isSelected() as it is a radio button group.
name|List
argument_list|<
name|String
argument_list|>
name|fields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|2
argument_list|)
decl_stmt|;
name|fields
operator|.
name|add
argument_list|(
literal|"editor"
argument_list|)
expr_stmt|;
name|hs
operator|=
name|GroupsUtil
operator|.
name|findAuthorLastNames
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|fields
argument_list|)
expr_stmt|;
name|fieldText
operator|=
literal|"editor"
expr_stmt|;
block|}
for|for
control|(
name|String
name|keyword
range|:
name|hs
control|)
block|{
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
name|keyword
argument_list|,
name|fieldText
argument_list|,
name|keyword
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|)
decl_stmt|;
name|autoGroupsRoot
operator|.
name|add
argument_list|(
operator|new
name|GroupTreeNode
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|m_groupsRoot
operator|.
name|getNode
argument_list|()
operator|.
name|add
argument_list|(
name|autoGroupsRoot
argument_list|)
expr_stmt|;
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
literal|"Autogenerate groups"
argument_list|)
argument_list|)
decl_stmt|;
name|UndoableAddOrRemoveGroup
name|undo
init|=
operator|new
name|UndoableAddOrRemoveGroup
argument_list|(
name|gs
argument_list|,
name|m_groupsRoot
argument_list|,
operator|new
name|GroupTreeNodeViewModel
argument_list|(
name|autoGroupsRoot
argument_list|)
argument_list|,
name|UndoableAddOrRemoveGroup
operator|.
name|ADD_NODE
argument_list|)
decl_stmt|;
name|undo
operator|.
name|setRevalidate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
name|undo
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
comment|// a change always occurs
name|gs
operator|.
name|revalidateGroups
argument_list|()
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Created groups."
argument_list|)
argument_list|)
expr_stmt|;
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|remove
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|field
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|field
operator|.
name|addCaretListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|AbstractAction
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
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
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ActionMap
name|am
init|=
name|main
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|main
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
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|keywords
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|authors
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|editors
argument_list|)
expr_stmt|;
name|keywords
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|FormBuilder
name|b
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
decl_stmt|;
name|b
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:20dlu, 4dlu, left:pref, 4dlu, fill:60dlu"
argument_list|,
literal|"p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p, 2dlu, p"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|keywords
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field to group by"
argument_list|)
operator|+
literal|":"
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|field
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Characters to ignore"
argument_list|)
operator|+
literal|":"
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|remove
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|nd
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|deliminator
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|authors
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|9
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|editors
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|11
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|build
argument_list|()
expr_stmt|;
name|b
operator|.
name|border
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
name|JPanel
name|opt
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|opt
argument_list|)
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
name|main
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
name|opt
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
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|b
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|opt
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|updateComponents
argument_list|()
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|PositionWindow
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|frame
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|caretUpdate (CaretEvent e)
specifier|public
name|void
name|caretUpdate
parameter_list|(
name|CaretEvent
name|e
parameter_list|)
block|{
name|updateComponents
argument_list|()
expr_stmt|;
block|}
DECL|method|updateComponents ()
specifier|private
name|void
name|updateComponents
parameter_list|()
block|{
name|String
name|groupField
init|=
name|field
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
name|ok
operator|.
name|setEnabled
argument_list|(
name|groupField
operator|.
name|matches
argument_list|(
literal|"\\w+"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

