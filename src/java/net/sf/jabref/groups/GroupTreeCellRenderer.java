begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  All programs in this directory and subdirectories are published under the   GNU General Public License as described below.   This program is free software; you can redistribute it and/or modify it   under the terms of the GNU General Public License as published by the Free   Software Foundation; either version 2 of the License, or (at your option)   any later version.   This program is distributed in the hope that it will be useful, but WITHOUT   ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or   FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for   more details.   You should have received a copy of the GNU General Public License along   with this program; if not, write to the Free Software Foundation, Inc., 59   Temple Place, Suite 330, Boston, MA 02111-1307 USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|ImageIcon
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
name|tree
operator|.
name|DefaultTreeCellRenderer
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
name|GUIGlobals
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
name|Util
import|;
end_import

begin_comment
comment|/**  * Renders a GroupTreeNode using its group's getName() method, rather that its  * toString() method.  *   * @author jzieren  */
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
specifier|protected
name|Object
name|highlight1Cell
init|=
literal|null
decl_stmt|;
DECL|field|highlight2Cells
specifier|protected
name|Object
index|[]
name|highlight2Cells
init|=
literal|null
decl_stmt|;
DECL|field|highlight3Cells
specifier|protected
name|Object
index|[]
name|highlight3Cells
init|=
literal|null
decl_stmt|;
DECL|field|highlightBorderCell
specifier|protected
name|Object
name|highlightBorderCell
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|ImageIcon
DECL|field|groupRefiningIcon
name|groupRefiningIcon
init|=
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"groupRefining"
argument_list|)
decl_stmt|,
DECL|field|groupIncludingIcon
name|groupIncludingIcon
init|=
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"groupIncluding"
argument_list|)
decl_stmt|,
DECL|field|groupRegularIcon
name|groupRegularIcon
init|=
literal|null
decl_stmt|;
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
if|if
condition|(
name|value
operator|==
name|highlight1Cell
condition|)
name|selected
operator|=
literal|true
expr_stmt|;
comment|// show as selected
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
comment|// this is sometimes called from deep within somewhere, with a dummy
comment|// value (probably for layout etc.), so we've got to check here!
if|if
condition|(
operator|!
operator|(
name|value
operator|instanceof
name|GroupTreeNode
operator|)
condition|)
return|return
name|c
return|;
name|AbstractGroup
name|group
init|=
operator|(
operator|(
name|GroupTreeNode
operator|)
name|value
operator|)
operator|.
name|getGroup
argument_list|()
decl_stmt|;
if|if
condition|(
name|group
operator|==
literal|null
operator|||
operator|!
operator|(
name|c
operator|instanceof
name|JLabel
operator|)
condition|)
return|return
name|c
return|;
comment|// sanity check
name|JLabel
name|label
init|=
operator|(
name|JLabel
operator|)
name|c
decl_stmt|;
if|if
condition|(
name|highlightBorderCell
operator|!=
literal|null
operator|&&
name|highlightBorderCell
operator|==
name|value
condition|)
name|label
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createLineBorder
argument_list|(
name|Color
operator|.
name|BLACK
argument_list|)
argument_list|)
expr_stmt|;
else|else
name|label
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|italics
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"groupShowDynamic"
argument_list|)
operator|&&
name|group
operator|.
name|isDynamic
argument_list|()
decl_stmt|;
name|boolean
name|red
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|highlight2Cells
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|highlight2Cells
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
if|if
condition|(
name|highlight2Cells
index|[
name|i
index|]
operator|==
name|value
condition|)
block|{
comment|// label.setForeground(Color.RED);
name|red
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
name|boolean
name|underline
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|highlight3Cells
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|highlight3Cells
operator|.
name|length
condition|;
operator|++
name|i
control|)
block|{
if|if
condition|(
name|highlight3Cells
index|[
name|i
index|]
operator|==
name|value
condition|)
block|{
name|underline
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
block|}
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
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
name|sb
operator|.
name|append
argument_list|(
literal|"<font color=\"#FF0000\">"
argument_list|)
expr_stmt|;
if|if
condition|(
name|underline
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"<u>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|italics
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"<i>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|Util
operator|.
name|quoteForHTML
argument_list|(
name|group
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|italics
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"</i>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|underline
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"</u>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|red
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|"</font>"
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"</html>"
argument_list|)
expr_stmt|;
specifier|final
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
name|label
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|label
operator|.
name|setToolTipText
argument_list|(
literal|"<html>"
operator|+
name|group
operator|.
name|getShortDescription
argument_list|()
operator|+
literal|"</html>"
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"groupShowIcons"
argument_list|)
condition|)
block|{
switch|switch
condition|(
name|group
operator|.
name|getHierarchicalContext
argument_list|()
condition|)
block|{
case|case
name|AbstractGroup
operator|.
name|REFINING
case|:
if|if
condition|(
name|label
operator|.
name|getIcon
argument_list|()
operator|!=
name|groupRefiningIcon
condition|)
name|label
operator|.
name|setIcon
argument_list|(
name|groupRefiningIcon
argument_list|)
expr_stmt|;
break|break;
case|case
name|AbstractGroup
operator|.
name|INCLUDING
case|:
if|if
condition|(
name|label
operator|.
name|getIcon
argument_list|()
operator|!=
name|groupIncludingIcon
condition|)
name|label
operator|.
name|setIcon
argument_list|(
name|groupIncludingIcon
argument_list|)
expr_stmt|;
break|break;
default|default:
if|if
condition|(
name|label
operator|.
name|getIcon
argument_list|()
operator|!=
name|groupRegularIcon
condition|)
name|label
operator|.
name|setIcon
argument_list|(
name|groupRegularIcon
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
else|else
block|{
name|label
operator|.
name|setIcon
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
return|return
name|c
return|;
block|}
comment|/**      * For use when dragging: The sepcified cell is always rendered as selected.      *       * @param cell      *            The cell over which the user is currently dragging.      */
DECL|method|setHighlight1Cell (Object cell)
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
comment|/**      * Highlights the specified cells (in red), or disables highlight if cells ==      * null.      */
DECL|method|setHighlight2Cells (Object[] cells)
name|void
name|setHighlight2Cells
parameter_list|(
name|Object
index|[]
name|cells
parameter_list|)
block|{
name|this
operator|.
name|highlight2Cells
operator|=
name|cells
expr_stmt|;
block|}
comment|/**      * Highlights the specified cells (by unterlining), or disables highlight if      * cells == null.      */
DECL|method|setHighlight3Cells (Object[] cells)
name|void
name|setHighlight3Cells
parameter_list|(
name|Object
index|[]
name|cells
parameter_list|)
block|{
name|this
operator|.
name|highlight3Cells
operator|=
name|cells
expr_stmt|;
block|}
comment|/**      * Highlights the specified cells (by drawing a border around it),       * or disables highlight if highlightBorderCell == null.      */
DECL|method|setHighlightBorderCell (Object highlightBorderCell)
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

