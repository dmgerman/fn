begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|util
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
name|javax
operator|.
name|swing
operator|.
name|event
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
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|CompoundEdit
import|;
end_import

begin_class
DECL|class|GroupSelector
specifier|public
class|class
name|GroupSelector
extends|extends
name|SidePaneComponent
implements|implements
name|ListSelectionListener
implements|,
name|ActionListener
block|{
specifier|public
specifier|static
specifier|final
name|int
DECL|field|DIM
name|DIM
init|=
literal|3
decl_stmt|,
comment|// The number of vector elements for each group.
DECL|field|OFFSET
name|OFFSET
init|=
literal|0
decl_stmt|;
comment|// The number of vector elements before first group.
DECL|field|newButton
name|JButton
name|newButton
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|newSmallIconFile
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|helpButton
name|helpButton
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|helpSmallIconFile
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|refresh
name|refresh
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|refreshSmallIconFile
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|autoGroup
name|autoGroup
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|autoGroupIcon
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|bgColor
name|Color
name|bgColor
init|=
name|Color
operator|.
name|white
decl_stmt|;
DECL|field|list
name|JList
name|list
decl_stmt|;
DECL|field|listModel
name|ListModel
name|listModel
decl_stmt|;
DECL|field|sp
name|JScrollPane
name|sp
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
DECL|field|groups
name|Vector
name|groups
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
decl_stmt|;
DECL|field|searchField
name|String
name|searchField
decl_stmt|;
DECL|field|gropt
name|JPopupMenu
name|gropt
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
name|JRadioButton
DECL|field|andCb
name|andCb
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Intersection"
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|,
DECL|field|orCb
name|orCb
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Union"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|,
DECL|field|floatCb
name|floatCb
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Float"
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|,
DECL|field|highlCb
name|highlCb
init|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Highlight"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
DECL|field|invCb
name|JCheckBox
name|invCb
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Inverted"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
DECL|field|bgr
name|ButtonGroup
name|bgr
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
DECL|field|manager
name|SidePaneManager
name|manager
decl_stmt|;
DECL|field|prefs
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|ths
name|GroupSelector
name|ths
decl_stmt|;
comment|/**      * The first element for each group defines which field to      * use for the quicksearch. The next two define the name and      * regexp for the group.      */
DECL|method|GroupSelector (JabRefFrame frame, BasePanel panel, Vector groupData, SidePaneManager manager, JabRefPreferences prefs)
specifier|public
name|GroupSelector
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|Vector
name|groupData
parameter_list|,
name|SidePaneManager
name|manager
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|super
argument_list|(
name|manager
argument_list|)
expr_stmt|;
name|ths
operator|=
name|this
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|groups
operator|=
name|groupData
expr_stmt|;
name|this
operator|.
name|manager
operator|=
name|manager
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|double
name|n
init|=
call|(
name|double
call|)
argument_list|(
name|groups
operator|.
name|size
argument_list|()
operator|-
name|OFFSET
argument_list|)
decl_stmt|;
while|while
condition|(
operator|(
name|n
operator|>
literal|0
operator|)
operator|&&
operator|(
name|n
operator|/
name|DIM
operator|!=
name|Math
operator|.
name|floor
argument_list|(
name|n
operator|/
name|DIM
argument_list|)
operator|)
condition|)
block|{
name|groups
operator|.
name|removeElementAt
argument_list|(
name|groups
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|n
operator|=
call|(
name|double
call|)
argument_list|(
name|groups
operator|.
name|size
argument_list|()
operator|-
name|OFFSET
argument_list|)
expr_stmt|;
comment|// If the number of elements is not divisible by DIM, we're
comment|// in trouble, so we must remove one or two elements.
block|}
name|Dimension
name|butDim
init|=
operator|new
name|Dimension
argument_list|(
literal|20
argument_list|,
literal|20
argument_list|)
decl_stmt|;
name|newButton
operator|.
name|setPreferredSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|newButton
operator|.
name|setMinimumSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|refresh
operator|.
name|setPreferredSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|refresh
operator|.
name|setMinimumSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|helpButton
operator|.
name|setPreferredSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|helpButton
operator|.
name|setMinimumSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|autoGroup
operator|.
name|setPreferredSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|autoGroup
operator|.
name|setMinimumSize
argument_list|(
name|butDim
argument_list|)
expr_stmt|;
name|newButton
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|refresh
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|andCb
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|orCb
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|invCb
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|autoGroup
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|newButton
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"New group"
argument_list|)
argument_list|)
expr_stmt|;
name|refresh
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Refresh view"
argument_list|)
argument_list|)
expr_stmt|;
name|andCb
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Display only entries belonging to all selected"
operator|+
literal|" groups."
argument_list|)
argument_list|)
expr_stmt|;
name|orCb
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Display all entries belonging to one or more "
operator|+
literal|"of the selected groups."
argument_list|)
argument_list|)
expr_stmt|;
name|autoGroup
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically create groups for database."
argument_list|)
argument_list|)
expr_stmt|;
name|bgr
operator|.
name|add
argument_list|(
name|andCb
argument_list|)
expr_stmt|;
name|bgr
operator|.
name|add
argument_list|(
name|orCb
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|SidePaneHeader
name|header
init|=
operator|new
name|SidePaneHeader
argument_list|(
literal|"Groups"
argument_list|,
name|GUIGlobals
operator|.
name|groupsIconFile
argument_list|,
name|this
argument_list|)
decl_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
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
literal|2
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|header
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|header
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weightx
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
name|gbl
operator|.
name|setConstraints
argument_list|(
name|newButton
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|newButton
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|refresh
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|refresh
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|autoGroup
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|autoGroup
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|HelpAction
name|helpAction
init|=
operator|new
name|HelpAction
argument_list|(
name|frame
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|groupsHelp
argument_list|,
literal|"Help on groups"
argument_list|)
decl_stmt|;
name|helpButton
operator|.
name|addActionListener
argument_list|(
name|helpAction
argument_list|)
expr_stmt|;
name|helpButton
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Help on groups"
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|helpButton
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|helpButton
argument_list|)
expr_stmt|;
name|list
operator|=
operator|new
name|JList
argument_list|()
expr_stmt|;
name|revalidateList
argument_list|()
expr_stmt|;
comment|//list.setSelectedIndex(0);
name|list
operator|.
name|addListSelectionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|list
operator|.
name|setPrototypeCellValue
argument_list|(
literal|"Suitable length"
argument_list|)
expr_stmt|;
comment|// The line above decides on the list's preferred width.
name|list
operator|.
name|setVisibleRowCount
argument_list|(
name|GUIGlobals
operator|.
name|GROUPS_VISIBLE_ROWS
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|MULTIPLE_INTERVAL_SELECTION
argument_list|)
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|list
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_AS_NEEDED
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|sp
argument_list|)
expr_stmt|;
name|JPanel
name|lower
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|lower
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|lower
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|andCb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|lower
operator|.
name|add
argument_list|(
name|andCb
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|orCb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|lower
operator|.
name|add
argument_list|(
name|orCb
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|invCb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|lower
operator|.
name|add
argument_list|(
name|invCb
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lower
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|lower
argument_list|)
expr_stmt|;
name|definePopup
argument_list|()
expr_stmt|;
block|}
DECL|method|definePopup ()
specifier|public
name|void
name|definePopup
parameter_list|()
block|{
name|gropt
operator|.
name|add
argument_list|(
name|modifyAction
argument_list|)
expr_stmt|;
name|gropt
operator|.
name|add
argument_list|(
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|String
name|gname
init|=
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|OFFSET
operator|+
name|DIM
operator|*
operator|(
name|list
operator|.
name|getSelectedIndex
argument_list|()
operator|-
literal|1
operator|)
operator|+
literal|1
argument_list|)
decl_stmt|;
name|int
name|conf
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove group"
argument_list|)
operator|+
literal|" '"
operator|+
name|gname
operator|+
literal|"'?"
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove group"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|conf
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
try|try
block|{
name|int
name|index
init|=
name|OFFSET
operator|+
name|DIM
operator|*
operator|(
name|list
operator|.
name|getSelectedIndex
argument_list|()
operator|-
literal|1
operator|)
decl_stmt|;
name|String
name|field
init|=
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|index
argument_list|)
decl_stmt|,
name|name
init|=
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|index
operator|+
literal|1
argument_list|)
decl_stmt|,
name|regexp
init|=
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|index
operator|+
literal|2
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|DIM
condition|;
name|i
operator|++
control|)
name|groups
operator|.
name|removeElementAt
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|revalidateList
argument_list|()
expr_stmt|;
comment|// Store undo information.
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableAddOrRemoveGroup
argument_list|(
name|ths
argument_list|,
name|groups
argument_list|,
name|index
argument_list|,
literal|false
argument_list|,
name|field
argument_list|,
name|name
argument_list|,
name|regexp
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
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
literal|"Removed group"
argument_list|)
operator|+
literal|" '"
operator|+
name|name
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ArrayIndexOutOfBoundsException
name|ex
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unexpected error when "
operator|+
literal|"trying to remove group."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|list
operator|.
name|addMouseListener
argument_list|(
operator|new
name|MouseAdapter
argument_list|()
block|{
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|int
name|index
init|=
name|list
operator|.
name|locationToIndex
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|==
literal|0
condition|)
name|list
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
name|int
name|index
init|=
name|list
operator|.
name|locationToIndex
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|e
operator|.
name|getButton
argument_list|()
operator|==
name|MouseEvent
operator|.
name|BUTTON3
condition|)
block|{
if|if
condition|(
operator|(
name|index
operator|>
literal|0
operator|)
comment|// Menu for index 0 also?
operator|&&
operator|(
name|index
operator|<
name|list
operator|.
name|getModel
argument_list|()
operator|.
name|getSize
argument_list|()
operator|)
condition|)
block|{
name|list
operator|.
name|setSelectedIndex
argument_list|(
name|index
argument_list|)
expr_stmt|;
name|gropt
operator|.
name|show
argument_list|(
name|list
argument_list|,
name|e
operator|.
name|getPoint
argument_list|()
operator|.
name|x
argument_list|,
name|list
operator|.
name|indexToLocation
argument_list|(
name|index
argument_list|)
operator|.
name|y
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
operator|(
name|index
operator|>
literal|0
operator|)
operator|&&
operator|(
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
operator|)
condition|)
block|{
comment|//list.setSelectedIndex(index);
name|modifyAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|list
argument_list|,
literal|0
argument_list|,
literal|"Modify"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|valueChanged (ListSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
operator|&&
operator|!
name|list
operator|.
name|isSelectionEmpty
argument_list|()
condition|)
block|{
name|int
index|[]
name|sel
init|=
name|list
operator|.
name|getSelectedIndices
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|sel
operator|.
name|length
operator|==
literal|1
operator|)
operator|&&
operator|(
name|sel
index|[
literal|0
index|]
operator|==
literal|0
operator|)
condition|)
block|{
comment|// Show all entries.
name|panel
operator|.
name|stopShowingGroup
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
literal|"Displaying no groups"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Show a search for the keyword of field sel*DIM+OFFSET-1
comment|// in the vector. The following is analogue with
comment|// the doSearch() method of SearchPane.
comment|// We use a search rule set that takes care of multiple groups,
comment|// in an AND or OR fashion, and optionally inverts it.
name|AndOrSearchRuleSet
name|searchRules
init|=
operator|new
name|AndOrSearchRuleSet
argument_list|(
name|andCb
operator|.
name|isSelected
argument_list|()
argument_list|,
name|invCb
operator|.
name|isSelected
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sel
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|sel
index|[
name|i
index|]
operator|>
literal|0
condition|)
block|{
name|SearchRule
name|rule
init|=
operator|new
name|QuickSearchRule
argument_list|(
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|sel
index|[
name|i
index|]
operator|*
name|DIM
operator|+
name|OFFSET
operator|-
literal|3
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|sel
index|[
name|i
index|]
operator|*
name|DIM
operator|+
name|OFFSET
operator|-
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|searchRules
operator|.
name|addRule
argument_list|(
name|rule
argument_list|)
expr_stmt|;
block|}
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
literal|"dummy"
argument_list|)
expr_stmt|;
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
name|panel
argument_list|,
name|Globals
operator|.
name|GROUPSEARCH
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|search
operator|.
name|start
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
literal|"Updated group selection"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
comment|//groups.elementAt(sel*DIM+OFFSET-DIM+1)+"'.");
block|}
block|}
block|}
DECL|method|revalidateList ()
specifier|public
name|void
name|revalidateList
parameter_list|()
block|{
name|revalidateList
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
DECL|method|revalidateList (int sel)
specifier|public
name|void
name|revalidateList
parameter_list|(
name|int
name|sel
parameter_list|)
block|{
name|Vector
name|newData
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
name|newData
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"All entries"
argument_list|)
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
operator|+
name|OFFSET
init|;
name|i
operator|<
name|groups
operator|.
name|size
argument_list|()
condition|;
name|i
operator|+=
name|DIM
control|)
block|{
name|newData
operator|.
name|add
argument_list|(
name|groups
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|list
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
name|list
operator|.
name|setListData
argument_list|(
name|newData
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectedIndex
argument_list|(
name|sel
argument_list|)
expr_stmt|;
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
name|refresh
condition|)
block|{
name|valueChanged
argument_list|(
operator|new
name|ListSelectionEvent
argument_list|(
name|list
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|newButton
condition|)
block|{
name|GroupDialog
name|gd
init|=
operator|new
name|GroupDialog
argument_list|(
name|frame
argument_list|,
name|groups
argument_list|,
operator|-
literal|1
argument_list|,
comment|//groups.size(),
name|prefs
operator|.
name|get
argument_list|(
literal|"groupsDefaultField"
argument_list|)
argument_list|)
decl_stmt|;
name|gd
operator|.
name|show
argument_list|()
expr_stmt|;
if|if
condition|(
name|gd
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|revalidateList
argument_list|(
operator|(
name|gd
operator|.
name|index
argument_list|()
operator|-
name|OFFSET
operator|)
operator|/
name|DIM
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|/*sp.getVerticalScrollBar().setValue 		  (sp.getVerticalScrollBar().getMaximum());*/
name|int
name|index
init|=
name|gd
operator|.
name|index
argument_list|()
decl_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableAddOrRemoveGroup
argument_list|(
name|this
argument_list|,
name|groups
argument_list|,
name|index
argument_list|,
literal|true
argument_list|,
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|index
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|index
operator|+
literal|1
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|index
operator|+
literal|2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
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
literal|"Created group"
argument_list|)
operator|+
literal|" '"
operator|+
name|gd
operator|.
name|name
argument_list|()
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|autoGroup
condition|)
block|{
name|AutoGroupDialog
name|gd
init|=
operator|new
name|AutoGroupDialog
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
name|this
argument_list|,
name|groups
argument_list|,
name|prefs
operator|.
name|get
argument_list|(
literal|"groupsDefaultField"
argument_list|)
argument_list|,
literal|" .,"
argument_list|)
decl_stmt|;
name|gd
operator|.
name|show
argument_list|()
expr_stmt|;
if|if
condition|(
name|gd
operator|.
name|okPressed
argument_list|()
condition|)
block|{  	    }
block|}
elseif|else
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|instanceof
name|JCheckBox
condition|)
block|{
name|valueChanged
argument_list|(
operator|new
name|ListSelectionEvent
argument_list|(
name|list
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|componentOpening ()
specifier|public
name|void
name|componentOpening
parameter_list|()
block|{
name|valueChanged
argument_list|(
operator|new
name|ListSelectionEvent
argument_list|(
name|list
argument_list|,
name|list
operator|.
name|getSelectedIndex
argument_list|()
argument_list|,
name|list
operator|.
name|getSelectedIndex
argument_list|()
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|componentClosing ()
specifier|public
name|void
name|componentClosing
parameter_list|()
block|{
name|panel
operator|.
name|stopShowingGroup
argument_list|()
expr_stmt|;
block|}
DECL|field|modifyAction
name|AbstractAction
name|modifyAction
init|=
operator|new
name|AbstractAction
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Modify"
argument_list|)
argument_list|)
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|int
name|index
init|=
name|OFFSET
operator|+
name|DIM
operator|*
operator|(
name|list
operator|.
name|getSelectedIndex
argument_list|()
operator|-
literal|1
operator|)
decl_stmt|,
name|groupIndex
init|=
name|list
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
name|GroupDialog
name|gd
init|=
operator|new
name|GroupDialog
argument_list|(
name|frame
argument_list|,
name|groups
argument_list|,
name|index
argument_list|,
name|prefs
operator|.
name|get
argument_list|(
literal|"groupsDefaultField"
argument_list|)
argument_list|)
decl_stmt|;
name|gd
operator|.
name|show
argument_list|()
expr_stmt|;
if|if
condition|(
name|gd
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|revalidateList
argument_list|(
operator|(
name|gd
operator|.
name|index
argument_list|()
operator|-
name|OFFSET
operator|)
operator|/
name|DIM
operator|+
literal|1
argument_list|)
expr_stmt|;
comment|// Store undo information.
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableModifyGroup
argument_list|(
name|ths
argument_list|,
name|groups
argument_list|,
name|gd
operator|.
name|index
argument_list|()
argument_list|,
name|gd
operator|.
name|field
argument_list|()
argument_list|,
name|gd
operator|.
name|name
argument_list|()
argument_list|,
name|gd
operator|.
name|regexp
argument_list|()
argument_list|,
name|gd
operator|.
name|oldField
argument_list|()
argument_list|,
name|gd
operator|.
name|oldName
argument_list|()
argument_list|,
name|gd
operator|.
name|oldRegexp
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
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
literal|"Modified group"
argument_list|)
operator|+
literal|" '"
operator|+
name|gd
operator|.
name|name
argument_list|()
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
DECL|method|findPos (Vector groups, String name)
specifier|public
specifier|static
name|int
name|findPos
parameter_list|(
name|Vector
name|groups
parameter_list|,
name|String
name|name
parameter_list|)
block|{
name|int
name|index
init|=
operator|-
literal|1
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|OFFSET
init|;
name|i
operator|<
name|groups
operator|.
name|size
argument_list|()
condition|;
name|i
operator|+=
name|DIM
control|)
block|{
if|if
condition|(
name|name
operator|.
name|toLowerCase
argument_list|()
operator|.
name|compareTo
argument_list|(
operator|(
operator|(
name|String
operator|)
name|groups
operator|.
name|elementAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
operator|)
operator|.
name|toLowerCase
argument_list|()
argument_list|)
operator|<
literal|0
condition|)
block|{
name|index
operator|=
name|i
expr_stmt|;
name|i
operator|=
name|groups
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|index
operator|==
operator|-
literal|1
condition|)
name|index
operator|=
name|groups
operator|.
name|size
argument_list|()
expr_stmt|;
return|return
name|index
return|;
block|}
comment|/**    * addGroups    *    * @param newGroups Vector of group information to insert.    */
DECL|method|addGroups (Vector newGroups, CompoundEdit ce)
specifier|public
name|void
name|addGroups
parameter_list|(
name|Vector
name|newGroups
parameter_list|,
name|CompoundEdit
name|ce
parameter_list|)
block|{
name|double
name|n
init|=
call|(
name|double
call|)
argument_list|(
name|newGroups
operator|.
name|size
argument_list|()
operator|-
name|OFFSET
argument_list|)
decl_stmt|;
while|while
condition|(
operator|(
name|n
operator|>
literal|0
operator|)
operator|&&
operator|(
name|n
operator|/
name|DIM
operator|!=
name|Math
operator|.
name|floor
argument_list|(
name|n
operator|/
name|DIM
argument_list|)
operator|)
condition|)
block|{
name|newGroups
operator|.
name|removeElementAt
argument_list|(
name|groups
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
name|n
operator|=
call|(
name|double
call|)
argument_list|(
name|groups
operator|.
name|size
argument_list|()
operator|-
name|OFFSET
argument_list|)
expr_stmt|;
comment|// If the number of elements is not divisible by DIM, we're
comment|// in trouble, so we must remove one or two elements.
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
operator|(
name|newGroups
operator|.
name|size
argument_list|()
operator|-
name|OFFSET
operator|)
operator|/
name|DIM
condition|;
name|i
operator|++
control|)
block|{
name|int
name|pos
init|=
name|OFFSET
operator|+
name|i
operator|*
name|DIM
decl_stmt|;
name|int
name|index
init|=
name|findPos
argument_list|(
name|groups
argument_list|,
operator|(
name|String
operator|)
name|newGroups
operator|.
name|elementAt
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|regexp
init|=
operator|(
name|String
operator|)
name|newGroups
operator|.
name|elementAt
argument_list|(
name|pos
operator|+
literal|2
argument_list|)
decl_stmt|,
name|name
init|=
operator|(
name|String
operator|)
name|newGroups
operator|.
name|elementAt
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
decl_stmt|,
name|field
init|=
operator|(
name|String
operator|)
name|newGroups
operator|.
name|elementAt
argument_list|(
name|pos
argument_list|)
decl_stmt|;
name|groups
operator|.
name|add
argument_list|(
name|index
argument_list|,
name|regexp
argument_list|)
expr_stmt|;
name|groups
operator|.
name|add
argument_list|(
name|index
argument_list|,
name|name
argument_list|)
expr_stmt|;
name|groups
operator|.
name|add
argument_list|(
name|index
argument_list|,
name|field
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableAddOrRemoveGroup
argument_list|(
name|this
argument_list|,
name|groups
argument_list|,
name|index
argument_list|,
literal|true
argument_list|,
name|field
argument_list|,
name|name
argument_list|,
name|regexp
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

