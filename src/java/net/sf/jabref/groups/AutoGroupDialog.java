begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003 Morten O. Alver, Nizar N. Batada   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
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
name|java
operator|.
name|util
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|undo
operator|.
name|NamedCompound
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
block|{
DECL|field|remove
DECL|field|field
name|JTextField
name|remove
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|,
name|field
init|=
operator|new
name|JTextField
argument_list|(
literal|60
argument_list|)
decl_stmt|,
DECL|field|deliminator
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
DECL|field|nd
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
literal|"Use the following deliminator"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
DECL|field|ok
DECL|field|cancel
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
decl_stmt|,
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
DECL|field|main
DECL|field|opt
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|opt
init|=
operator|new
name|JPanel
argument_list|()
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
name|GroupTreeNode
name|m_groupsRoot
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|gs
specifier|private
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
name|ActionListener
name|okListener
init|=
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
comment|// Check that there are no empty strings.
if|if
condition|(
name|field
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"You must provide a field name "
operator|+
literal|"as basis for the group creation."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically create groups"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
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
literal|"Automatically created groups"
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|HashSet
name|hs
init|=
literal|null
decl_stmt|;
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
name|Iterator
name|i
init|=
name|hs
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|regExp
init|=
name|i
operator|.
name|next
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
name|regExp
argument_list|,
name|field
argument_list|()
argument_list|,
name|Util
operator|.
name|quoteMeta
argument_list|(
name|regExp
argument_list|)
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
name|AbstractAction
name|cancelAction
init|=
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
name|ok
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
comment|// Key bindings:
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
comment|// Layout starts here.
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|opt
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Group properties"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Main panel:
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
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
literal|3
argument_list|,
literal|5
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nf
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nr
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nr
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|nd
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|nd
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
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
name|gridy
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|field
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|remove
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|deliminator
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|deliminator
argument_list|)
expr_stmt|;
comment|// Option buttons:
name|con
operator|.
name|gridx
operator|=
name|GridBagConstraints
operator|.
name|RELATIVE
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
name|GridBagConstraints
operator|.
name|RELATIVE
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|ok
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
name|ok
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
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|cancel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
name|cancel
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
name|setSize
argument_list|(
literal|400
argument_list|,
literal|200
argument_list|)
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
specifier|public
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
specifier|public
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
block|}
end_class

end_unit

