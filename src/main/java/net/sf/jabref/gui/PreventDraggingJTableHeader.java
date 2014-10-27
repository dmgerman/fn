begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
operator|.
name|SpecialFieldsUtils
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
comment|// in the most recent JabRef, the special columns have a one letter heading,
comment|// therefore, isUnnamed will always return "false"
comment|// to be safe, we keep this call nevertheless
comment|// (this is the null check for getHeaderValue())
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
comment|// prevent dragging of special field columns
name|String
name|headerValue
init|=
name|column
operator|.
name|getHeaderValue
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
if|if
condition|(
name|headerValue
operator|.
name|equals
argument_list|(
literal|"P"
argument_list|)
operator|||
name|headerValue
operator|.
name|equals
argument_list|(
literal|"Q"
argument_list|)
operator|||
name|headerValue
operator|.
name|equals
argument_list|(
literal|"R"
argument_list|)
condition|)
block|{
comment|// the letters are guessed. Don't know, where they are set in the code.
return|return;
block|}
comment|// other icon columns should also not be dragged
comment|// note that "P" is used for "PDF" and "Priority"
if|if
condition|(
name|headerValue
operator|.
name|equals
argument_list|(
literal|"F"
argument_list|)
operator|||
name|headerValue
operator|.
name|equals
argument_list|(
literal|"U"
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
literal|"arxivColumn"
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
literal|"extraFileColumns"
argument_list|)
condition|)
block|{
name|count
operator|+=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"listOfFileColumns"
argument_list|)
operator|.
name|length
expr_stmt|;
block|}
comment|// special field columns may also not be dragged
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SPECIALFIELDSENABLED
argument_list|)
condition|)
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_RANKING
argument_list|)
condition|)
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_RELEVANCE
argument_list|)
condition|)
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_QUALITY
argument_list|)
condition|)
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_PRIORITY
argument_list|)
condition|)
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_PRINTED
argument_list|)
condition|)
name|count
operator|++
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SHOWCOLUMN_READ
argument_list|)
condition|)
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

