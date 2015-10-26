begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|java
operator|.
name|util
operator|.
name|Vector
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
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListSelectionEvent
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
name|TableColumnModelEvent
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
name|TableColumnModelListener
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
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * Listens for TableColumnModelEvents to keep track of changes made to the  * MainTable columns, like reordering or resizing.  *  * Changes to columns without a name and the "#" column are not saved. To have  * consistent behavior (e.g. as in TableColumnsTab).  *  * @author Fabian Bieker  * @author Daniel Waeber  * @since 12/2008  *  */
end_comment

begin_class
DECL|class|PersistenceTableColumnListener
specifier|public
class|class
name|PersistenceTableColumnListener
implements|implements
name|TableColumnModelListener
block|{
DECL|field|ACTIVATE_PREF_KEY
specifier|public
specifier|static
specifier|final
name|String
name|ACTIVATE_PREF_KEY
init|=
literal|"ActivatePersistenceTableColumnListener"
decl_stmt|;
DECL|field|DEFAULT_ENABLED
specifier|public
specifier|static
specifier|final
name|boolean
name|DEFAULT_ENABLED
init|=
literal|true
decl_stmt|;
DECL|field|simpleClassName
specifier|private
specifier|static
specifier|final
name|String
name|simpleClassName
init|=
name|PersistenceTableColumnListener
operator|.
name|class
operator|.
name|getSimpleName
argument_list|()
decl_stmt|;
comment|// needed to get column names / indices mapped from view to model
comment|// and to access the table model
DECL|field|mainTable
specifier|private
specifier|final
name|MainTable
name|mainTable
decl_stmt|;
comment|/**      * @param mainTable      */
DECL|method|PersistenceTableColumnListener (final MainTable mainTable)
specifier|public
name|PersistenceTableColumnListener
parameter_list|(
specifier|final
name|MainTable
name|mainTable
parameter_list|)
block|{
name|this
operator|.
name|mainTable
operator|=
name|mainTable
expr_stmt|;
block|}
comment|/**      * update columns names and their width, store it in the global prefs.      */
DECL|method|updateColumnPrefs ()
specifier|private
name|void
name|updateColumnPrefs
parameter_list|()
block|{
specifier|final
name|int
name|columnCount
init|=
name|mainTable
operator|.
name|getColumnCount
argument_list|()
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|storedColumns
init|=
operator|new
name|Vector
argument_list|<>
argument_list|(
name|columnCount
operator|-
literal|1
argument_list|)
decl_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|columnsWidths
init|=
operator|new
name|Vector
argument_list|<>
argument_list|(
name|columnCount
operator|-
literal|1
argument_list|)
decl_stmt|;
name|int
name|ncWidth
init|=
operator|-
literal|1
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
name|columnCount
condition|;
name|i
operator|++
control|)
block|{
specifier|final
name|String
name|name
init|=
name|mainTable
operator|.
name|getColumnName
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|name
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|name
operator|.
name|equals
argument_list|(
literal|"#"
argument_list|)
condition|)
block|{
comment|// TODO: get "#" from prefs?
name|ncWidth
operator|=
name|mainTable
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|getWidth
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|storedColumns
operator|.
name|add
argument_list|(
name|name
operator|.
name|toLowerCase
argument_list|()
argument_list|)
expr_stmt|;
name|columnsWidths
operator|.
name|add
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|mainTable
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|getWidth
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Finally, we store the new preferences.
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|COLUMN_NAMES
argument_list|,
name|storedColumns
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|storedColumns
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|COLUMN_WIDTHS
argument_list|,
name|columnsWidths
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|columnsWidths
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
comment|// width of the number ("#") column
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|NUMBER_COL_WIDTH
argument_list|,
name|ncWidth
argument_list|)
expr_stmt|;
block|}
comment|/**      * @see javax.swing.event.TableColumnModelListener#columnAdded(javax.swing.event.TableColumnModelEvent)      */
annotation|@
name|Override
DECL|method|columnAdded (TableColumnModelEvent e)
specifier|public
name|void
name|columnAdded
parameter_list|(
name|TableColumnModelEvent
name|e
parameter_list|)
block|{
assert|assert
name|e
operator|!=
literal|null
operator|:
name|PersistenceTableColumnListener
operator|.
name|simpleClassName
operator|+
literal|" received null event"
assert|;
name|updateColumnPrefs
argument_list|()
expr_stmt|;
block|}
comment|/**      * @see javax.swing.event.TableColumnModelListener#columnMarginChanged(javax.swing.event.ChangeEvent)      */
annotation|@
name|Override
DECL|method|columnMarginChanged (ChangeEvent e)
specifier|public
name|void
name|columnMarginChanged
parameter_list|(
name|ChangeEvent
name|e
parameter_list|)
block|{
assert|assert
name|e
operator|!=
literal|null
operator|:
name|PersistenceTableColumnListener
operator|.
name|simpleClassName
operator|+
literal|" received null event"
assert|;
name|updateColumnPrefs
argument_list|()
expr_stmt|;
block|}
comment|/**      * @see javax.swing.event.TableColumnModelListener#columnMoved(javax.swing.event.TableColumnModelEvent)      */
annotation|@
name|Override
DECL|method|columnMoved (TableColumnModelEvent e)
specifier|public
name|void
name|columnMoved
parameter_list|(
name|TableColumnModelEvent
name|e
parameter_list|)
block|{
assert|assert
name|e
operator|!=
literal|null
operator|:
name|PersistenceTableColumnListener
operator|.
name|simpleClassName
operator|+
literal|" received null event"
assert|;
comment|// not really moved, ignore ...
if|if
condition|(
name|e
operator|.
name|getFromIndex
argument_list|()
operator|==
name|e
operator|.
name|getToIndex
argument_list|()
condition|)
block|{
return|return;
block|}
name|updateColumnPrefs
argument_list|()
expr_stmt|;
block|}
comment|/**      * @see javax.swing.event.TableColumnModelListener#columnRemoved(javax.swing.event.TableColumnModelEvent)      */
annotation|@
name|Override
DECL|method|columnRemoved (TableColumnModelEvent e)
specifier|public
name|void
name|columnRemoved
parameter_list|(
name|TableColumnModelEvent
name|e
parameter_list|)
block|{
assert|assert
name|e
operator|!=
literal|null
operator|:
name|PersistenceTableColumnListener
operator|.
name|simpleClassName
operator|+
literal|" received null event"
assert|;
name|updateColumnPrefs
argument_list|()
expr_stmt|;
block|}
comment|/**      * @see javax.swing.event.TableColumnModelListener#columnSelectionChanged(javax.swing.event.ListSelectionEvent)      */
annotation|@
name|Override
DECL|method|columnSelectionChanged (ListSelectionEvent e)
specifier|public
name|void
name|columnSelectionChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
comment|// ignore
block|}
block|}
end_class

end_unit

