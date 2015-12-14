begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|BaseAction
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
name|KeyBinds
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
name|groups
operator|.
name|structure
operator|.
name|AbstractGroup
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
name|TreeSelectionListener
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
name|DefaultTreeModel
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
name|TreeNode
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
name|TreePath
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
name|TreeSelectionModel
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
name|Enumeration
import|;
end_import

begin_comment
comment|/**  * Created with IntelliJ IDEA.  * User: alver  * Date: 1/22/13  * Time: 6:24 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|GroupAddRemoveDialog
specifier|public
class|class
name|GroupAddRemoveDialog
implements|implements
name|BaseAction
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|add
specifier|private
specifier|final
name|boolean
name|add
decl_stmt|;
DECL|field|move
specifier|private
specifier|final
name|boolean
name|move
decl_stmt|;
DECL|field|selection
specifier|private
name|BibtexEntry
index|[]
name|selection
decl_stmt|;
DECL|field|tree
specifier|private
name|JTree
name|tree
decl_stmt|;
DECL|field|ok
specifier|private
name|JButton
name|ok
decl_stmt|;
DECL|method|GroupAddRemoveDialog (BasePanel panel, boolean add, boolean move)
specifier|public
name|GroupAddRemoveDialog
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|boolean
name|add
parameter_list|,
name|boolean
name|move
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|add
operator|=
name|add
expr_stmt|;
name|this
operator|.
name|move
operator|=
name|move
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
throws|throws
name|Throwable
block|{
name|GroupTreeNode
name|groups
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getGroups
argument_list|()
decl_stmt|;
if|if
condition|(
name|groups
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|selection
operator|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
expr_stmt|;
specifier|final
name|JDialog
name|diag
init|=
operator|new
name|JDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
comment|// @formatter:off
operator|(
name|add
condition|?
operator|(
name|move
condition|?
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move to group"
argument_list|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add to group"
argument_list|)
operator|)
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove from group"
argument_list|)
operator|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|// formatter:on
name|ok
operator|=
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
expr_stmt|;
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
name|tree
operator|=
operator|new
name|JTree
argument_list|(
name|groups
argument_list|)
expr_stmt|;
name|tree
operator|.
name|setCellRenderer
argument_list|(
operator|new
name|AddRemoveGroupTreeCellRenderer
argument_list|()
argument_list|)
expr_stmt|;
name|tree
operator|.
name|setVisibleRowCount
argument_list|(
literal|22
argument_list|)
expr_stmt|;
comment|//        tree.setPreferredSize(new Dimension(200, tree.getPreferredSize().height));
comment|//      The scrollbar appears when the preferred size of a component is greater than the size of the viewport. If one hard coded the preferred size, it will never change according to the expansion/collapse. Thus the scrollbar cannot appear accordingly.
comment|//tree.setSelectionModel(new VetoableTreeSelectionModel());
name|tree
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|TreeSelectionModel
operator|.
name|SINGLE_TREE_SELECTION
argument_list|)
expr_stmt|;
name|tree
operator|.
name|addTreeSelectionListener
argument_list|(
operator|new
name|SelectionListener
argument_list|()
argument_list|)
expr_stmt|;
comment|//STA add expand and collapse all buttons
name|JButton
name|jbExpandAll
init|=
operator|new
name|JButton
argument_list|(
literal|"Expand All"
argument_list|)
decl_stmt|;
name|jbExpandAll
operator|.
name|addActionListener
argument_list|(
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
name|expandAll
argument_list|(
name|tree
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|jbCollapseAll
init|=
operator|new
name|JButton
argument_list|(
literal|"Collapse All"
argument_list|)
decl_stmt|;
name|jbCollapseAll
operator|.
name|addActionListener
argument_list|(
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
name|expandAll
argument_list|(
name|tree
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|//END add expand and collapse all buttons
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
name|addButton
argument_list|(
name|jbExpandAll
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|jbCollapseAll
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
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
name|actionEvent
parameter_list|)
block|{
if|if
condition|(
name|doAddOrRemove
argument_list|()
condition|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
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
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|ok
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|tree
argument_list|)
decl_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|sp
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|sp
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
name|prefs
operator|.
name|getKey
argument_list|(
name|KeyBinds
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
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|sp
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
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// If "expand" is true, all nodes in the tree area expanded
comment|// otherwise all nodes in the tree are collapsed:
DECL|method|expandAll (final JTree tree, final boolean expand)
specifier|private
name|void
name|expandAll
parameter_list|(
specifier|final
name|JTree
name|tree
parameter_list|,
specifier|final
name|boolean
name|expand
parameter_list|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|TreeNode
name|root
init|=
operator|(
operator|(
name|TreeNode
operator|)
name|tree
operator|.
name|getModel
argument_list|()
operator|.
name|getRoot
argument_list|()
operator|)
decl_stmt|;
comment|// walk through the tree, beginning at the root:
name|expandAll
argument_list|(
name|tree
argument_list|,
operator|new
name|TreePath
argument_list|(
operator|(
operator|(
name|DefaultTreeModel
operator|)
name|tree
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|getPathToRoot
argument_list|(
name|root
argument_list|)
argument_list|)
argument_list|,
name|expand
argument_list|)
expr_stmt|;
name|tree
operator|.
name|requestFocusInWindow
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|expandAll (final JTree tree, final TreePath parent, final boolean expand)
specifier|private
name|void
name|expandAll
parameter_list|(
specifier|final
name|JTree
name|tree
parameter_list|,
specifier|final
name|TreePath
name|parent
parameter_list|,
specifier|final
name|boolean
name|expand
parameter_list|)
block|{
comment|// walk through the children:
name|TreeNode
name|node
init|=
operator|(
name|TreeNode
operator|)
name|parent
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
if|if
condition|(
name|node
operator|.
name|getChildCount
argument_list|()
operator|>=
literal|0
condition|)
block|{
for|for
control|(
name|Enumeration
name|e
init|=
name|node
operator|.
name|children
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|TreeNode
name|n
init|=
operator|(
name|TreeNode
operator|)
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|TreePath
name|path
init|=
name|parent
operator|.
name|pathByAddingChild
argument_list|(
name|n
argument_list|)
decl_stmt|;
name|expandAll
argument_list|(
name|tree
argument_list|,
name|path
argument_list|,
name|expand
argument_list|)
expr_stmt|;
block|}
block|}
comment|// "expand" / "collapse" occurs from bottom to top:
if|if
condition|(
name|expand
condition|)
block|{
name|tree
operator|.
name|expandPath
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tree
operator|.
name|collapsePath
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|SelectionListener
specifier|private
class|class
name|SelectionListener
implements|implements
name|TreeSelectionListener
block|{
annotation|@
name|Override
DECL|method|valueChanged (TreeSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|TreeSelectionEvent
name|e
parameter_list|)
block|{
name|GroupTreeNode
name|node
init|=
operator|(
name|GroupTreeNode
operator|)
name|e
operator|.
name|getNewLeadSelectionPath
argument_list|()
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
name|AbstractGroup
name|group
init|=
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|ok
operator|.
name|setEnabled
argument_list|(
name|checkGroupEnable
argument_list|(
name|group
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|doAddOrRemove ()
specifier|private
name|boolean
name|doAddOrRemove
parameter_list|()
block|{
name|GroupTreeNode
name|node
init|=
operator|(
name|GroupTreeNode
operator|)
name|tree
operator|.
name|getSelectionPath
argument_list|()
operator|.
name|getLastPathComponent
argument_list|()
decl_stmt|;
name|AbstractGroup
name|group
init|=
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
if|if
condition|(
name|checkGroupEnable
argument_list|(
name|group
argument_list|)
condition|)
block|{
if|if
condition|(
name|add
condition|)
block|{
name|AddToGroupAction
name|action
init|=
operator|new
name|AddToGroupAction
argument_list|(
name|node
argument_list|,
name|move
argument_list|,
name|panel
argument_list|)
decl_stmt|;
name|action
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|node
argument_list|,
literal|0
argument_list|,
literal|"add"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|RemoveFromGroupAction
name|action
init|=
operator|new
name|RemoveFromGroupAction
argument_list|(
name|node
argument_list|,
name|panel
argument_list|)
decl_stmt|;
name|action
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|node
argument_list|,
literal|0
argument_list|,
literal|"remove"
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Check if we can perform the action for this group. Determines whether      * the group should be shown in an enabled state, and if selecting it should      * leave the Ok button enabled.      * @param group The group to check      * @return true if this dialog's action can be performed on the group      */
DECL|method|checkGroupEnable (AbstractGroup group)
specifier|private
name|boolean
name|checkGroupEnable
parameter_list|(
name|AbstractGroup
name|group
parameter_list|)
block|{
return|return
operator|(
name|add
condition|?
name|group
operator|.
name|supportsAdd
argument_list|()
operator|&&
operator|!
name|group
operator|.
name|containsAll
argument_list|(
name|selection
argument_list|)
else|:
name|group
operator|.
name|supportsRemove
argument_list|()
operator|&&
name|group
operator|.
name|containsAny
argument_list|(
name|selection
argument_list|)
operator|)
return|;
block|}
comment|/*    private class VetoableTreeSelectionModel extends DefaultTreeSelectionModel {              @Override             public void addSelectionPath(TreePath path) {                 if (checkPath(path))                     super.addSelectionPath(path);             }              public void setSelectionPath(TreePath path){                 if (checkPath(path))                     super.setSelectionPath(path);              }              private boolean checkPath(TreePath path) {                 GroupTreeNode node = (GroupTreeNode)path.getLastPathComponent();                 AbstractGroup group = node.getGroup();                 return (add ? group.supportsAdd()&& !group.containsAll(GroupAddRemoveDialog.this.selection)                         : group.supportsRemove()&& group.containsAny(GroupAddRemoveDialog.this.selection));             }         }         {          } */
DECL|class|AddRemoveGroupTreeCellRenderer
class|class
name|AddRemoveGroupTreeCellRenderer
extends|extends
name|GroupTreeCellRenderer
block|{
annotation|@
name|Override
DECL|method|getTreeCellRendererComponent (JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus)
specifier|public
name|Component
name|getTreeCellRendererComponent
parameter_list|(
name|JTree
name|tree
parameter_list|,
name|Object
name|value
parameter_list|,
name|boolean
name|selected
parameter_list|,
name|boolean
name|expanded
parameter_list|,
name|boolean
name|leaf
parameter_list|,
name|int
name|row
parameter_list|,
name|boolean
name|hasFocus
parameter_list|)
block|{
name|Component
name|c
init|=
name|super
operator|.
name|getTreeCellRendererComponent
argument_list|(
name|tree
argument_list|,
name|value
argument_list|,
name|selected
argument_list|,
name|expanded
argument_list|,
name|leaf
argument_list|,
name|row
argument_list|,
name|hasFocus
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|node
init|=
operator|(
name|GroupTreeNode
operator|)
name|value
decl_stmt|;
name|AbstractGroup
name|group
init|=
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
if|if
condition|(
name|checkGroupEnable
argument_list|(
name|group
argument_list|)
condition|)
block|{
name|c
operator|.
name|setForeground
argument_list|(
name|Color
operator|.
name|black
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|c
operator|.
name|setForeground
argument_list|(
name|Color
operator|.
name|gray
argument_list|)
expr_stmt|;
block|}
return|return
name|c
return|;
block|}
block|}
block|}
end_class

end_unit

