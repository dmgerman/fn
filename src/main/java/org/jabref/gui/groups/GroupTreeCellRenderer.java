begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
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
name|Color
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
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
name|java
operator|.
name|util
operator|.
name|Objects
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
name|Icon
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTree
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|border
operator|.
name|Border
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
name|DefaultTreeCellRenderer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|GroupTreeNode
import|;
end_import

begin_comment
comment|/**  * Renders a GroupTreeNode using its group's getName() method, rather that its toString() method.  *  * @author jzieren  */
end_comment

begin_class
DECL|class|GroupTreeCellRenderer
specifier|public
class|class
name|GroupTreeCellRenderer
extends|extends
name|DefaultTreeCellRenderer
block|{
comment|/** The cell over which the user is currently dragging */
DECL|field|highlight1Cell
specifier|private
name|Object
name|highlight1Cell
decl_stmt|;
DECL|field|overlappingGroups
specifier|private
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|overlappingGroups
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|matchingGroups
specifier|private
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|matchingGroups
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|highlightBorderCell
specifier|private
name|Object
name|highlightBorderCell
decl_stmt|;
annotation|@
name|Override
DECL|method|getTreeCellRendererComponent (JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean tmpHasFocus)
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
name|sel
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
name|tmpHasFocus
parameter_list|)
block|{
comment|// show as selected
name|selected
operator|=
operator|(
name|Objects
operator|.
name|equals
argument_list|(
name|highlight1Cell
argument_list|,
name|value
argument_list|)
operator|)
operator|||
name|sel
expr_stmt|;
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
name|tmpHasFocus
argument_list|)
decl_stmt|;
comment|// this is sometimes called from deep within somewhere, with a dummy
comment|// value (probably for layout etc.), so we've got to check here!
if|if
condition|(
operator|!
operator|(
name|value
operator|instanceof
name|GroupTreeNodeViewModel
operator|)
condition|)
block|{
return|return
name|c
return|;
block|}
if|if
condition|(
operator|!
operator|(
name|c
operator|instanceof
name|JLabel
operator|)
condition|)
block|{
return|return
name|c
return|;
comment|// sanity check
block|}
name|GroupTreeNodeViewModel
name|viewModel
init|=
operator|(
name|GroupTreeNodeViewModel
operator|)
name|value
decl_stmt|;
name|JLabel
name|label
init|=
operator|(
name|JLabel
operator|)
name|c
decl_stmt|;
name|Border
name|border
decl_stmt|;
if|if
condition|(
name|Objects
operator|.
name|equals
argument_list|(
name|highlightBorderCell
argument_list|,
name|value
argument_list|)
condition|)
block|{
name|border
operator|=
name|BorderFactory
operator|.
name|createLineBorder
argument_list|(
name|Color
operator|.
name|BLACK
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|border
operator|=
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|label
operator|.
name|getBorder
argument_list|()
operator|!=
name|border
condition|)
block|{
name|label
operator|.
name|setBorder
argument_list|(
name|border
argument_list|)
expr_stmt|;
block|}
name|Boolean
name|red
init|=
name|printInRed
argument_list|(
name|viewModel
argument_list|)
operator|&&
operator|!
name|selected
decl_stmt|;
comment|// do not print currently selected node in red
name|Boolean
name|underlined
init|=
name|printUnderlined
argument_list|(
name|viewModel
argument_list|)
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|(
literal|60
argument_list|)
decl_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"<html>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|red
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<font color=\"#FF0000\">"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|underlined
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<u>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|viewModel
operator|.
name|printInItalics
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"Group Name"
argument_list|)
expr_stmt|;
if|if
condition|(
name|viewModel
operator|.
name|printInItalics
argument_list|()
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"</i>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|underlined
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"</u>"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|red
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"</font>"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
literal|"</html>"
argument_list|)
expr_stmt|;
name|String
name|text
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|label
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
name|text
argument_list|)
condition|)
block|{
name|label
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
name|label
operator|.
name|setToolTipText
argument_list|(
name|viewModel
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
name|Icon
name|icon
init|=
name|viewModel
operator|.
name|getIcon
argument_list|()
decl_stmt|;
if|if
condition|(
name|label
operator|.
name|getIcon
argument_list|()
operator|!=
name|icon
condition|)
block|{
name|label
operator|.
name|setIcon
argument_list|(
name|icon
argument_list|)
expr_stmt|;
block|}
return|return
name|c
return|;
block|}
DECL|method|printInRed (GroupTreeNodeViewModel viewModel)
specifier|private
name|boolean
name|printInRed
parameter_list|(
name|GroupTreeNodeViewModel
name|viewModel
parameter_list|)
block|{
if|if
condition|(
name|viewModel
operator|.
name|isAllEntriesGroup
argument_list|()
condition|)
block|{
comment|// Do not print all entries group in red
return|return
literal|false
return|;
block|}
return|return
name|overlappingGroups
operator|.
name|contains
argument_list|(
name|viewModel
operator|.
name|getNode
argument_list|()
argument_list|)
return|;
block|}
DECL|method|printUnderlined (GroupTreeNodeViewModel viewModel)
specifier|private
name|boolean
name|printUnderlined
parameter_list|(
name|GroupTreeNodeViewModel
name|viewModel
parameter_list|)
block|{
if|if
condition|(
name|viewModel
operator|.
name|isAllEntriesGroup
argument_list|()
condition|)
block|{
comment|// Do not underline all entries group
return|return
literal|false
return|;
block|}
return|return
name|matchingGroups
operator|.
name|contains
argument_list|(
name|viewModel
operator|.
name|getNode
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * For use when dragging: The specified cell is always rendered as selected.      *      * @param cell The cell over which the user is currently dragging.      */
DECL|method|setHighlight1Cell (Object cell)
specifier|public
name|void
name|setHighlight1Cell
parameter_list|(
name|Object
name|cell
parameter_list|)
block|{
name|this
operator|.
name|highlight1Cell
operator|=
name|cell
expr_stmt|;
block|}
comment|/**      * Highlights the specified groups in red.      */
DECL|method|setOverlappingGroups (List<GroupTreeNode> nodes)
specifier|public
name|void
name|setOverlappingGroups
parameter_list|(
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|nodes
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|nodes
argument_list|)
expr_stmt|;
name|this
operator|.
name|overlappingGroups
operator|=
name|nodes
expr_stmt|;
block|}
comment|/**      * Highlights the specified groups by underlining.      */
DECL|method|setMatchingGroups (List<GroupTreeNode> nodes)
specifier|public
name|void
name|setMatchingGroups
parameter_list|(
name|List
argument_list|<
name|GroupTreeNode
argument_list|>
name|nodes
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|nodes
argument_list|)
expr_stmt|;
name|this
operator|.
name|matchingGroups
operator|=
name|nodes
expr_stmt|;
block|}
comment|/**      * Highlights the specified cells (by drawing a border around it), or disables highlight if highlightBorderCell ==      * null.      */
DECL|method|setHighlightBorderCell (Object highlightBorderCell)
specifier|public
name|void
name|setHighlightBorderCell
parameter_list|(
name|Object
name|highlightBorderCell
parameter_list|)
block|{
name|this
operator|.
name|highlightBorderCell
operator|=
name|highlightBorderCell
expr_stmt|;
block|}
block|}
end_class

end_unit
