begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
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
name|groups
operator|.
name|structure
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|structure
operator|.
name|GroupHierarchyType
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
name|groups
operator|.
name|structure
operator|.
name|KeywordGroup
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
name|util
operator|.
name|Util
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
name|undo
operator|.
name|NamedCompound
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
name|DefaultFormBuilder
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
comment|/**  * Dialog for creating or modifying groups. Operates directly on the Vector  * containing group information.  */
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
DECL|field|nf
name|JLabel
name|nf
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field to group by"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|,
DECL|field|nr
name|nr
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Characters to ignore"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
specifier|private
specifier|final
name|JRadioButton
DECL|field|keywords
name|keywords
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|ok_pressed
specifier|private
name|boolean
name|ok_pressed
init|=
literal|false
decl_stmt|;
DECL|field|m_groupsRoot
specifier|private
specifier|final
name|GroupTreeNode
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
DECL|field|oldRemove
DECL|field|oldField
specifier|private
name|String
name|oldRemove
decl_stmt|,
name|oldField
decl_stmt|;
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
comment|/**      * @param groupsRoot      *            The original set of groups, which is required as undo      *            information when all groups are cleared.      */
DECL|method|AutoGroupDialog (JabRefFrame jabrefFrame, BasePanel basePanel, GroupSelector groupSelector, GroupTreeNode groupsRoot, String defaultField, String defaultRemove, String defaultDeliminator)
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
name|GroupTreeNode
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
name|Globals
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
name|ok_pressed
operator|=
literal|true
expr_stmt|;
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
name|Globals
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
init|=
literal|null
decl_stmt|;
name|String
name|field
init|=
name|field
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
name|Util
operator|.
name|findDeliminatedWordsInField
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|field
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
name|Util
operator|.
name|findAllWordsInField
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|field
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|remove
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
argument_list|<
name|String
argument_list|>
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
name|Util
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
name|field
operator|=
literal|"author"
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|editors
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
argument_list|<
name|String
argument_list|>
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
name|Util
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
name|field
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
name|field
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
name|Globals
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
name|autoGroupsRoot
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
name|Globals
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
name|Globals
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
name|frame
operator|.
name|prefs
argument_list|()
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
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
name|DefaultFormBuilder
name|b
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:20dlu, 4dlu, left:pref, 4dlu, fill:60dlu, 4dlu, fill:0dlu"
argument_list|,
literal|""
argument_list|)
argument_list|,
name|main
argument_list|)
decl_stmt|;
name|b
operator|.
name|append
argument_list|(
name|keywords
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field to group by"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Characters to ignore"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|nd
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|deliminator
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|authors
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|editors
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
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
comment|// Layout starts here.
comment|/*main.setLayout(gbl);         opt.setLayout(gbl);         main.setBorder(BorderFactory.createTitledBorder(BorderFactory                 .createEtchedBorder(), Globals.lang("Group properties")));         // Main panel:         con.weightx = 0;         con.gridwidth = 1;         con.insets = new Insets(3, 5, 3, 5);         con.anchor = GridBagConstraints.EAST;         con.fill = GridBagConstraints.NONE;         con.gridx = 0;         con.gridy = 0;         gbl.setConstraints(nf, con);         main.add(nf);         con.gridy = 1;         gbl.setConstraints(nr, con);         main.add(nr);         con.gridy = 2;         gbl.setConstraints(nd, con);         main.add(nd);         con.weightx = 1;         con.anchor = GridBagConstraints.WEST;         con.fill = GridBagConstraints.HORIZONTAL;         con.gridy = 0;         con.gridx = 1;         gbl.setConstraints(field, con);         main.add(field);         con.gridy = 1;         gbl.setConstraints(remove, con);         main.add(remove);         con.gridy = 2;         gbl.setConstraints(deliminator, con);         main.add(deliminator);         // Option buttons:         con.gridx = GridBagConstraints.RELATIVE;         con.gridy = GridBagConstraints.RELATIVE;         con.weightx = 1;         con.gridwidth = 1;         con.anchor = GridBagConstraints.EAST;         con.fill = GridBagConstraints.NONE;         gbl.setConstraints(ok, con);         opt.add(ok);         con.anchor = GridBagConstraints.WEST;         con.gridwidth = GridBagConstraints.REMAINDER;         gbl.setConstraints(cancel, con);         opt.add(cancel);*/
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
name|opt
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
comment|// pack();
name|updateComponents
argument_list|()
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|frame
argument_list|)
expr_stmt|;
block|}
DECL|method|okPressed ()
specifier|public
name|boolean
name|okPressed
parameter_list|()
block|{
return|return
name|ok_pressed
return|;
block|}
DECL|method|oldField ()
specifier|public
name|String
name|oldField
parameter_list|()
block|{
return|return
name|oldField
return|;
block|}
DECL|method|oldRemove ()
specifier|public
name|String
name|oldRemove
parameter_list|()
block|{
return|return
name|oldRemove
return|;
block|}
DECL|method|field ()
specifier|private
name|String
name|field
parameter_list|()
block|{
return|return
name|field
operator|.
name|getText
argument_list|()
return|;
block|}
DECL|method|remove ()
specifier|private
name|String
name|remove
parameter_list|()
block|{
return|return
name|remove
operator|.
name|getText
argument_list|()
return|;
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

