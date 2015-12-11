begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.maintable
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|maintable
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
name|table
operator|.
name|JTableHeader
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableCellRenderer
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableColumn
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableColumnModel
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
name|MouseEvent
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
comment|/**  * Related to<code>MainTable</code> class.<br/>  * Prevents dragging of the first header column ("#") and shows icons in the table header if an icon has to be set.  *  * This might not be the best way to solve this problem. Overriding  *<code>getDraggedColumn</code> produces some ugly gui dragging artifacts if a  * user attempts to drag something before the first columns.  *  * @author Daniel Waeber  * @author Fabian Bieker  * @since 12/2008  */
end_comment

begin_class
DECL|class|PreventDraggingJTableHeader
class|class
name|PreventDraggingJTableHeader
extends|extends
name|JTableHeader
implements|implements
name|TableCellRenderer
block|{
DECL|field|tableFormat
specifier|private
specifier|final
name|MainTableFormat
name|tableFormat
decl_stmt|;
DECL|field|delegate
specifier|private
specifier|final
name|TableCellRenderer
name|delegate
decl_stmt|;
DECL|method|PreventDraggingJTableHeader (JTable table, MainTableFormat tableFormat)
specifier|public
name|PreventDraggingJTableHeader
parameter_list|(
name|JTable
name|table
parameter_list|,
name|MainTableFormat
name|tableFormat
parameter_list|)
block|{
name|super
argument_list|(
name|table
operator|.
name|getColumnModel
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTable
argument_list|(
name|table
argument_list|)
expr_stmt|;
name|this
operator|.
name|tableFormat
operator|=
name|tableFormat
expr_stmt|;
name|this
operator|.
name|delegate
operator|=
name|table
operator|.
name|getTableHeader
argument_list|()
operator|.
name|getDefaultRenderer
argument_list|()
expr_stmt|;
name|setupTableHeaderIcons
argument_list|()
expr_stmt|;
block|}
DECL|method|setupTableHeaderIcons ()
specifier|private
name|void
name|setupTableHeaderIcons
parameter_list|()
block|{
name|Enumeration
argument_list|<
name|TableColumn
argument_list|>
name|columns
init|=
name|columnModel
operator|.
name|getColumns
argument_list|()
decl_stmt|;
while|while
condition|(
name|columns
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|TableColumn
name|column
init|=
name|columns
operator|.
name|nextElement
argument_list|()
decl_stmt|;
name|column
operator|.
name|setHeaderRenderer
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|MainTableColumn
name|mainTableColumn
init|=
name|tableFormat
operator|.
name|getTableColumn
argument_list|(
name|column
operator|.
name|getModelIndex
argument_list|()
argument_list|)
decl_stmt|;
name|column
operator|.
name|setHeaderValue
argument_list|(
name|mainTableColumn
operator|.
name|getHeaderLabel
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|getToolTipText (MouseEvent event)
specifier|public
name|String
name|getToolTipText
parameter_list|(
name|MouseEvent
name|event
parameter_list|)
block|{
name|int
name|index
init|=
name|columnModel
operator|.
name|getColumnIndexAtX
argument_list|(
name|event
operator|.
name|getX
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|realIndex
init|=
name|columnModel
operator|.
name|getColumn
argument_list|(
name|index
argument_list|)
operator|.
name|getModelIndex
argument_list|()
decl_stmt|;
name|MainTableColumn
name|column
init|=
name|tableFormat
operator|.
name|getTableColumn
argument_list|(
name|realIndex
argument_list|)
decl_stmt|;
return|return
name|column
operator|.
name|getDisplayName
argument_list|()
return|;
block|}
comment|/**      * Overridden to prevent dragging of first column ("#")      */
annotation|@
name|Override
DECL|method|setDraggedColumn (TableColumn column)
specifier|public
name|void
name|setDraggedColumn
parameter_list|(
name|TableColumn
name|column
parameter_list|)
block|{
if|if
condition|(
name|column
operator|!=
literal|null
condition|)
block|{
comment|// prevent dragging of "#"
if|if
condition|(
name|column
operator|.
name|getModelIndex
argument_list|()
operator|==
literal|0
condition|)
block|{
return|return;
block|}
block|}
name|super
operator|.
name|setDraggedColumn
argument_list|(
name|column
argument_list|)
expr_stmt|;
block|}
comment|/**      * Overridden to prevent dragging of an other column before the first column ("#").      */
annotation|@
name|Override
DECL|method|getDraggedColumn ()
specifier|public
name|TableColumn
name|getDraggedColumn
parameter_list|()
block|{
name|TableColumn
name|column
init|=
name|super
operator|.
name|getDraggedColumn
argument_list|()
decl_stmt|;
if|if
condition|(
name|column
operator|!=
literal|null
condition|)
block|{
name|PreventDraggingJTableHeader
operator|.
name|preventDragBeforeNumberColumn
argument_list|(
name|this
operator|.
name|getTable
argument_list|()
argument_list|,
name|column
operator|.
name|getModelIndex
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|column
return|;
block|}
annotation|@
name|Override
DECL|method|getTableCellRendererComponent (JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
specifier|public
name|Component
name|getTableCellRendererComponent
parameter_list|(
name|JTable
name|table
parameter_list|,
name|Object
name|value
parameter_list|,
name|boolean
name|isSelected
parameter_list|,
name|boolean
name|hasFocus
parameter_list|,
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
comment|// delegate to previously used TableCellRenderer which styles the component
name|Component
name|resultFromDelegate
init|=
name|delegate
operator|.
name|getTableCellRendererComponent
argument_list|(
name|table
argument_list|,
name|value
argument_list|,
name|isSelected
argument_list|,
name|hasFocus
argument_list|,
name|row
argument_list|,
name|column
argument_list|)
decl_stmt|;
comment|// Changing style is only possible if both value and resultFromDelegate are JLabels
if|if
condition|(
name|value
operator|instanceof
name|JLabel
operator|&&
name|resultFromDelegate
operator|instanceof
name|JLabel
condition|)
block|{
name|String
name|text
init|=
operator|(
operator|(
name|JLabel
operator|)
name|value
operator|)
operator|.
name|getText
argument_list|()
decl_stmt|;
name|Icon
name|icon
init|=
operator|(
operator|(
name|JLabel
operator|)
name|value
operator|)
operator|.
name|getIcon
argument_list|()
decl_stmt|;
if|if
condition|(
name|icon
operator|!=
literal|null
condition|)
block|{
operator|(
operator|(
name|JLabel
operator|)
name|resultFromDelegate
operator|)
operator|.
name|setIcon
argument_list|(
name|icon
argument_list|)
expr_stmt|;
operator|(
operator|(
name|JLabel
operator|)
name|resultFromDelegate
operator|)
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
else|else
block|{
operator|(
operator|(
name|JLabel
operator|)
name|resultFromDelegate
operator|)
operator|.
name|setText
argument_list|(
name|text
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|resultFromDelegate
return|;
block|}
comment|/**      * Transform model index<code>modelIndex</code> to a view based index and      * prevent dragging before model index<code>toIndex</code> (inclusive).      */
DECL|method|preventDragBeforeNumberColumn (JTable table, int modelIndex)
specifier|private
specifier|static
name|void
name|preventDragBeforeNumberColumn
parameter_list|(
name|JTable
name|table
parameter_list|,
name|int
name|modelIndex
parameter_list|)
block|{
for|for
control|(
name|int
name|columnIndex
init|=
literal|0
init|;
name|columnIndex
operator|<
name|table
operator|.
name|getColumnCount
argument_list|()
condition|;
name|columnIndex
operator|++
control|)
block|{
name|TableColumn
name|col
init|=
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
name|columnIndex
argument_list|)
decl_stmt|;
comment|// found the element in the view ...
comment|// ... and check if it should not be dragged
if|if
condition|(
operator|(
name|col
operator|.
name|getModelIndex
argument_list|()
operator|==
name|modelIndex
operator|)
operator|&&
operator|(
name|columnIndex
operator|<
literal|1
operator|)
condition|)
block|{
comment|// prevent dragging (move it back ...)
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|moveColumn
argument_list|(
name|columnIndex
argument_list|,
literal|1
argument_list|)
expr_stmt|;
return|return;
comment|// we are done now
block|}
block|}
block|}
block|}
end_class

end_unit

