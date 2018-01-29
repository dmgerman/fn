begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.maintable
package|package
name|org
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
DECL|field|RECEIVED_NULL_EVENT
specifier|private
specifier|static
specifier|final
name|String
name|RECEIVED_NULL_EVENT
init|=
literal|" received null event"
decl_stmt|;
DECL|field|SIMPLE_CLASS_NAME
specifier|private
specifier|static
specifier|final
name|String
name|SIMPLE_CLASS_NAME
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
comment|/*         final int columnCount = mainTable.getColumnCount();         List<String> storedColumns = new ArrayList<>(columnCount - 1);         List<String> columnsWidths = new ArrayList<>(columnCount - 1);         int ncWidth = -1;          for (int i = 0; i< columnCount; i++) {             final String name = mainTable.getColumnName(i);             if ((name != null)&& !name.isEmpty()) {                 if (FieldName.NUMBER_COL.equals(name)) {                     ncWidth = mainTable.getColumnModel().getColumn(i).getWidth();                 } else {                     storedColumns.add(name.toLowerCase(Locale.ROOT));                     columnsWidths.add(String.valueOf(mainTable.getColumnModel().getColumn(i).getWidth()));                 }             }         }          // Finally, we store the new preferences.         Globals.prefs.putStringList(JabRefPreferences.COLUMN_NAMES, storedColumns);         Globals.prefs.putStringList(JabRefPreferences.COLUMN_WIDTHS, columnsWidths);          // width of the number ("#") column         Globals.prefs.putInt(JabRefPreferences.NUMBER_COL_WIDTH, ncWidth);         */
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
name|SIMPLE_CLASS_NAME
operator|+
name|RECEIVED_NULL_EVENT
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
name|SIMPLE_CLASS_NAME
operator|+
name|RECEIVED_NULL_EVENT
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
name|SIMPLE_CLASS_NAME
operator|+
name|RECEIVED_NULL_EVENT
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
name|SIMPLE_CLASS_NAME
operator|+
name|RECEIVED_NULL_EVENT
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

