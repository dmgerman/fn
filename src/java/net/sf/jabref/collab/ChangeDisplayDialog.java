begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

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
name|tree
operator|.
name|DefaultMutableTreeNode
import|;
end_import

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
name|java
operator|.
name|awt
operator|.
name|Insets
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
name|TreeSelectionListener
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
name|TreeSelectionEvent
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
name|ChangeListener
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
name|ChangeEvent
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
name|ActionEvent
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
name|BibtexDatabase
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

begin_class
DECL|class|ChangeDisplayDialog
specifier|public
class|class
name|ChangeDisplayDialog
extends|extends
name|JDialog
implements|implements
name|TreeSelectionListener
block|{
DECL|field|secondary
specifier|private
name|BibtexDatabase
name|secondary
decl_stmt|;
DECL|field|root
name|DefaultMutableTreeNode
name|root
decl_stmt|;
DECL|field|tree
name|JTree
name|tree
decl_stmt|;
DECL|field|infoPanel
name|JPanel
name|infoPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|buttonPanel
name|buttonPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|infoBorder
name|infoBorder
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|ok
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
DECL|field|cancel
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
DECL|field|cb
name|JCheckBox
name|cb
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Accept change"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|rootInfo
name|JLabel
name|rootInfo
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select the tree nodes to view and accept or reject changes"
argument_list|)
operator|+
literal|"."
argument_list|)
decl_stmt|;
DECL|field|selected
name|Change
name|selected
init|=
literal|null
decl_stmt|;
DECL|field|infoShown
name|JComponent
name|infoShown
init|=
literal|null
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
init|=
literal|false
decl_stmt|;
DECL|method|ChangeDisplayDialog (JFrame owner, final BasePanel panel, BibtexDatabase secondary, final DefaultMutableTreeNode root)
specifier|public
name|ChangeDisplayDialog
parameter_list|(
name|JFrame
name|owner
parameter_list|,
specifier|final
name|BasePanel
name|panel
parameter_list|,
name|BibtexDatabase
name|secondary
parameter_list|,
specifier|final
name|DefaultMutableTreeNode
name|root
parameter_list|)
block|{
name|super
argument_list|(
name|owner
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"External changes"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|secondary
operator|=
name|secondary
expr_stmt|;
comment|// Just to be sure, put in an empty secondary base if none is given:
if|if
condition|(
name|secondary
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|secondary
operator|=
operator|new
name|BibtexDatabase
argument_list|()
expr_stmt|;
block|}
name|this
operator|.
name|root
operator|=
name|root
expr_stmt|;
name|tree
operator|=
operator|new
name|JTree
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|tree
operator|.
name|addTreeSelectionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|JSplitPane
name|pane
init|=
operator|new
name|JSplitPane
argument_list|()
decl_stmt|;
name|pane
operator|.
name|setLeftComponent
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|tree
argument_list|)
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setRightComponent
argument_list|(
name|infoBorder
argument_list|)
expr_stmt|;
name|cb
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|cb
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|infoPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|infoBorder
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|infoBorder
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|infoBorder
operator|.
name|add
argument_list|(
name|infoPanel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|setInfo
argument_list|(
name|rootInfo
argument_list|)
expr_stmt|;
name|infoPanel
operator|.
name|add
argument_list|(
name|cb
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|buttonPanel
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
name|pane
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
name|buttonPanel
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|cb
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|selected
operator|!=
literal|null
condition|)
name|selected
operator|.
name|setAccepted
argument_list|(
name|cb
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|ok
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
comment|// Perform all accepted changes:
comment|// Store all edits in an Undoable object:
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
literal|"Merged external changes"
argument_list|)
argument_list|)
decl_stmt|;
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|Enumeration
name|enumer
init|=
name|root
operator|.
name|children
argument_list|()
decl_stmt|;
name|boolean
name|anyDisabled
init|=
literal|false
decl_stmt|;
for|for
control|(
init|;
name|enumer
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|Change
name|c
init|=
operator|(
name|Change
operator|)
name|enumer
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|boolean
name|allAccepted
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|c
operator|.
name|isAcceptable
argument_list|()
operator|&&
name|c
operator|.
name|isAccepted
argument_list|()
condition|)
name|allAccepted
operator|=
name|c
operator|.
name|makeChange
argument_list|(
name|panel
argument_list|,
name|ChangeDisplayDialog
operator|.
name|this
operator|.
name|secondary
argument_list|,
name|ce
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|allAccepted
condition|)
name|anyDisabled
operator|=
literal|true
expr_stmt|;
block|}
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
if|if
condition|(
name|anyDisabled
condition|)
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|panel
operator|.
name|setUpdatedExternally
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
name|okPressed
operator|=
literal|true
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
DECL|method|isOkPressed ()
specifier|public
name|boolean
name|isOkPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
DECL|method|setInfo (JComponent comp)
specifier|private
name|void
name|setInfo
parameter_list|(
name|JComponent
name|comp
parameter_list|)
block|{
if|if
condition|(
name|infoShown
operator|!=
literal|null
condition|)
name|infoPanel
operator|.
name|remove
argument_list|(
name|infoShown
argument_list|)
expr_stmt|;
name|infoShown
operator|=
name|comp
expr_stmt|;
name|infoPanel
operator|.
name|add
argument_list|(
name|infoShown
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|infoPanel
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|infoPanel
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
comment|/**    * valueChanged    *    * @param e TreeSelectionEvent    */
DECL|method|valueChanged (TreeSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|TreeSelectionEvent
name|e
parameter_list|)
block|{
name|Object
name|o
init|=
name|tree
operator|.
name|getLastSelectedPathComponent
argument_list|()
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|Change
condition|)
block|{
name|selected
operator|=
operator|(
name|Change
operator|)
name|o
expr_stmt|;
name|setInfo
argument_list|(
name|selected
operator|.
name|description
argument_list|()
argument_list|)
expr_stmt|;
name|cb
operator|.
name|setSelected
argument_list|(
name|selected
operator|.
name|isAccepted
argument_list|()
argument_list|)
expr_stmt|;
name|cb
operator|.
name|setEnabled
argument_list|(
name|selected
operator|.
name|isAcceptable
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setInfo
argument_list|(
name|rootInfo
argument_list|)
expr_stmt|;
name|selected
operator|=
literal|null
expr_stmt|;
name|cb
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

