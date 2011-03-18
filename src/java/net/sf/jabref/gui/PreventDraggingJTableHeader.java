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
name|javax
operator|.
name|swing
operator|.
name|JTable
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
comment|/**  * Related to<code>MainTable</code> class.<br/>  * Prevents dragging of the first header column ("#"). Prevents dragging of  * unnamed (aka special) header columns. This is needed to prevent the user from  * putting the gui table in an inconsistent state.<br/>  *   * This might not be the best way to solve this problem. Overriding  *<code>getDraggedColumn</code> produces some ugly gui dragging artifacts if a  * user attempts to drag something before the first columns.  *   * @author Daniel Waeber  * @author Fabian Bieker  * @since 12/2008  */
end_comment

begin_class
DECL|class|PreventDraggingJTableHeader
specifier|public
class|class
name|PreventDraggingJTableHeader
extends|extends
name|JTableHeader
block|{
DECL|method|PreventDraggingJTableHeader (TableColumnModel cm)
specifier|public
name|PreventDraggingJTableHeader
parameter_list|(
name|TableColumnModel
name|cm
parameter_list|)
block|{
name|super
argument_list|(
name|cm
argument_list|)
expr_stmt|;
block|}
comment|/**      * Overridden to prevent dragging of first column ("#") and special (unnamed)      * columns.      */
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
comment|// prevent dragging of unnamed (aka special) columns
if|if
condition|(
name|isUnnamed
argument_list|(
name|column
argument_list|)
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
comment|/**      * Overridden to prevent dragging of an other column before the first      * columns ("#" and the unnamed ones).      * */
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
name|preventDragBeforeIndex
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
argument_list|,
name|getSpecialColumnsCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|column
return|;
block|}
comment|/**      * Note: used to prevent dragging of other columns before the special      * columns.      *       * @return count of special columns      */
DECL|method|getSpecialColumnsCount ()
specifier|private
name|int
name|getSpecialColumnsCount
parameter_list|()
block|{
name|int
name|count
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"fileColumn"
argument_list|)
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"pdfColumn"
argument_list|)
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"urlColumn"
argument_list|)
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"citeseerColumn"
argument_list|)
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"arxivColumn"
argument_list|)
condition|)
block|{
name|count
operator|++
expr_stmt|;
block|}
return|return
name|count
return|;
block|}
DECL|method|isUnnamed (TableColumn column)
specifier|private
specifier|static
name|boolean
name|isUnnamed
parameter_list|(
name|TableColumn
name|column
parameter_list|)
block|{
return|return
name|column
operator|.
name|getHeaderValue
argument_list|()
operator|==
literal|null
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|column
operator|.
name|getHeaderValue
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Transform model index<code>mColIndex</code> to a view based index and      * prevent dragging before model index<code>toIndex</code> (inclusive).      */
DECL|method|preventDragBeforeIndex (JTable table, int mColIndex, int toIndex)
specifier|private
specifier|static
name|void
name|preventDragBeforeIndex
parameter_list|(
name|JTable
name|table
parameter_list|,
name|int
name|mColIndex
parameter_list|,
name|int
name|toIndex
parameter_list|)
block|{
for|for
control|(
name|int
name|c
init|=
literal|0
init|;
name|c
operator|<
name|table
operator|.
name|getColumnCount
argument_list|()
condition|;
name|c
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
name|c
argument_list|)
decl_stmt|;
comment|// found the element in the view ...
comment|// ... and check if it should not be dragged
if|if
condition|(
name|col
operator|.
name|getModelIndex
argument_list|()
operator|==
name|mColIndex
operator|&&
name|c
operator|<=
name|toIndex
condition|)
block|{
comment|// Util.pr("prevented! viewIndex = " + c + " modelIndex = "
comment|// + mColIndex + " toIndex = " + toIndex);
comment|// prevent dragging (move it back ...)
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|moveColumn
argument_list|(
name|toIndex
argument_list|,
name|toIndex
operator|+
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

