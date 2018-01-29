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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

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
name|javafx
operator|.
name|collections
operator|.
name|ListChangeListener
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ScrollPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|SelectionMode
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TableView
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|BasePanel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|EntryMarker
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|JabRefFrame
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
operator|.
name|ExternalFileTypes
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|keyboard
operator|.
name|KeyBindingRepository
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|renderer
operator|.
name|CompleteRenderer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|renderer
operator|.
name|GeneralRenderer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|renderer
operator|.
name|IncompleteRenderer
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
operator|.
name|ViewModelTableRowFactory
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|TypedBibEntry
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
name|database
operator|.
name|BibDatabaseContext
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|matchers
operator|.
name|Matcher
import|;
end_import

begin_class
DECL|class|MainTable
specifier|public
class|class
name|MainTable
extends|extends
name|TableView
argument_list|<
name|BibEntryTableViewModel
argument_list|>
block|{
DECL|field|defRenderer
specifier|private
specifier|static
name|GeneralRenderer
name|defRenderer
decl_stmt|;
DECL|field|reqRenderer
specifier|private
specifier|static
name|GeneralRenderer
name|reqRenderer
decl_stmt|;
DECL|field|optRenderer
specifier|private
specifier|static
name|GeneralRenderer
name|optRenderer
decl_stmt|;
DECL|field|resolvedRenderer
specifier|private
specifier|static
name|GeneralRenderer
name|resolvedRenderer
decl_stmt|;
DECL|field|grayedOutRenderer
specifier|private
specifier|static
name|GeneralRenderer
name|grayedOutRenderer
decl_stmt|;
DECL|field|veryGrayedOutRenderer
specifier|private
specifier|static
name|GeneralRenderer
name|veryGrayedOutRenderer
decl_stmt|;
DECL|field|markedRenderers
specifier|private
specifier|static
name|List
argument_list|<
name|GeneralRenderer
argument_list|>
name|markedRenderers
decl_stmt|;
DECL|field|incRenderer
specifier|private
specifier|static
name|IncompleteRenderer
name|incRenderer
decl_stmt|;
DECL|field|compRenderer
specifier|private
specifier|static
name|CompleteRenderer
name|compRenderer
decl_stmt|;
DECL|field|grayedOutNumberRenderer
specifier|private
specifier|static
name|CompleteRenderer
name|grayedOutNumberRenderer
decl_stmt|;
DECL|field|veryGrayedOutNumberRenderer
specifier|private
specifier|static
name|CompleteRenderer
name|veryGrayedOutNumberRenderer
decl_stmt|;
DECL|field|markedNumberRenderers
specifier|private
specifier|static
name|List
argument_list|<
name|CompleteRenderer
argument_list|>
name|markedNumberRenderers
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
comment|//private final boolean tableColorCodes;
comment|//private final boolean tableResolvedColorCodes;
DECL|field|pane
specifier|private
specifier|final
name|ScrollPane
name|pane
decl_stmt|;
comment|// needed to activate/deactivate the listener
DECL|field|tableColumnListener
specifier|private
name|PersistenceTableColumnListener
name|tableColumnListener
decl_stmt|;
DECL|field|model
specifier|private
specifier|final
name|MainTableDataModel
name|model
decl_stmt|;
comment|// Enum used to define how a cell should be rendered.
DECL|enum|CellRendererMode
specifier|private
enum|enum
name|CellRendererMode
block|{
DECL|enumConstant|REQUIRED
name|REQUIRED
block|,
DECL|enumConstant|RESOLVED
name|RESOLVED
block|,
DECL|enumConstant|OPTIONAL
name|OPTIONAL
block|,
DECL|enumConstant|OTHER
name|OTHER
block|}
static|static
block|{
comment|//MainTable.updateRenderers();
block|}
DECL|method|MainTable (MainTableDataModel model, JabRefFrame frame, BasePanel panel, BibDatabaseContext database, MainTablePreferences preferences, ExternalFileTypes externalFileTypes, KeyBindingRepository keyBindingRepository)
specifier|public
name|MainTable
parameter_list|(
name|MainTableDataModel
name|model
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|BibDatabaseContext
name|database
parameter_list|,
name|MainTablePreferences
name|preferences
parameter_list|,
name|ExternalFileTypes
name|externalFileTypes
parameter_list|,
name|KeyBindingRepository
name|keyBindingRepository
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|model
operator|=
name|model
expr_stmt|;
name|this
operator|.
name|getColumns
argument_list|()
operator|.
name|addAll
argument_list|(
operator|new
name|MainTableColumnFactory
argument_list|(
name|database
argument_list|,
name|preferences
operator|.
name|getColumnPreferences
argument_list|()
argument_list|,
name|externalFileTypes
argument_list|,
name|panel
operator|.
name|getUndoManager
argument_list|()
argument_list|)
operator|.
name|createColumns
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|setRowFactory
argument_list|(
operator|new
name|ViewModelTableRowFactory
argument_list|<
name|BibEntryTableViewModel
argument_list|>
argument_list|()
operator|.
name|withOnMouseClickedEvent
argument_list|(
parameter_list|(
name|entry
parameter_list|,
name|event
parameter_list|)
lambda|->
block|{
if|if
condition|(
name|event
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
condition|)
block|{
name|panel
operator|.
name|showAndEdit
argument_list|(
name|entry
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
operator|.
name|withContextMenu
argument_list|(
name|entry1
lambda|->
name|RightClickMenu
operator|.
name|create
argument_list|(
name|entry1
argument_list|,
name|keyBindingRepository
argument_list|,
name|panel
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|preferences
operator|.
name|resizeColumnsToFit
argument_list|()
condition|)
block|{
name|this
operator|.
name|setColumnResizePolicy
argument_list|(
operator|new
name|SmartConstrainedResizePolicy
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|SelectionMode
operator|.
name|MULTIPLE
argument_list|)
expr_stmt|;
name|this
operator|.
name|setItems
argument_list|(
name|model
operator|.
name|getEntriesFiltered
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO: Cannot add focus listener as it is expecting an swing component
comment|//addFocusListener(Globals.getFocusListener());
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|pane
operator|=
operator|new
name|ScrollPane
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setFitToHeight
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setFitToWidth
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|pane
operator|.
name|getStylesheets
argument_list|()
operator|.
name|add
argument_list|(
name|MainTable
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"MainTable.css"
argument_list|)
operator|.
name|toExternalForm
argument_list|()
argument_list|)
expr_stmt|;
comment|// TODO: Color
comment|//tableColorCodes = Globals.prefs.getBoolean(JabRefPreferences.TABLE_COLOR_CODES_ON);
comment|//tableResolvedColorCodes = Globals.prefs.getBoolean(JabRefPreferences.TABLE_RESOLVED_COLOR_CODES_ON);
comment|//pane.getViewport().setBackground(Globals.prefs.getColor(JabRefPreferences.TABLE_BACKGROUND));
comment|//setGridColor(Globals.prefs.getColor(JabRefPreferences.GRID_COLOR));
if|if
condition|(
operator|!
name|preferences
operator|.
name|showGrid
argument_list|()
condition|)
block|{
name|this
operator|.
name|setStyle
argument_list|(
literal|"-fx-table-cell-border-color: transparent;"
argument_list|)
expr_stmt|;
block|}
comment|// TODO: Tooltip for column header
comment|/*         @Override     public String getToolTipText(MouseEvent event) {         int index = columnModel.getColumnIndexAtX(event.getX());         int realIndex = columnModel.getColumn(index).getModelIndex();         MainTableColumn column = tableFormat.getTableColumn(realIndex);         return column.getDisplayName();     }          */
comment|// TODO: store column widths
comment|//this.tableColumnListener = new PersistenceTableColumnListener(this);
comment|//setWidths();
comment|// TODO: enable DnD
comment|//setDragEnabled(true);
comment|//TransferHandler xfer = new EntryTableTransferHandler(this, frame, panel);
comment|//setTransferHandler(xfer);
comment|//pane.setTransferHandler(xfer);
comment|// Todo: Set default sort order
comment|// setupComparatorChooser();
comment|// TODO: Float marked entries
comment|//model.updateMarkingState(Globals.prefs.getBoolean(JabRefPreferences.FLOAT_MARKED_ENTRIES));
comment|// TODO: Keybindings
comment|//Override 'selectNextColumnCell' and 'selectPreviousColumnCell' to move rows instead of cells on TAB
comment|/*         ActionMap actionMap = getActionMap();         InputMap inputMap = getInputMap();         actionMap.put("selectNextColumnCell", new AbstractAction() {             @Override             public void actionPerformed(ActionEvent e) {                 panel.selectNextEntry();             }         });         actionMap.put("selectPreviousColumnCell", new AbstractAction() {             @Override             public void actionPerformed(ActionEvent e) {                 panel.selectPreviousEntry();             }         });         actionMap.put("selectNextRow", new AbstractAction() {             @Override             public void actionPerformed(ActionEvent e) {                 panel.selectNextEntry();             }         });         actionMap.put("selectPreviousRow", new AbstractAction() {             @Override             public void actionPerformed(ActionEvent e) {                 panel.selectPreviousEntry();             }         });          String selectFirst = "selectFirst";         inputMap.put(Globals.getKeyPrefs().getKey(KeyBinding.SELECT_FIRST_ENTRY), selectFirst);         actionMap.put(selectFirst, new AbstractAction() {             @Override             public void actionPerformed(ActionEvent event) {                 panel.selectFirstEntry();             }         });          String selectLast = "selectLast";         inputMap.put(Globals.getKeyPrefs().getKey(KeyBinding.SELECT_LAST_ENTRY), selectLast);         actionMap.put(selectLast, new AbstractAction() {             @Override             public void actionPerformed(ActionEvent event) {                 panel.selectLastEntry();             }         });*/
block|}
DECL|method|addSelectionListener (ListChangeListener<? super BibEntryTableViewModel> listener)
specifier|public
name|void
name|addSelectionListener
parameter_list|(
name|ListChangeListener
argument_list|<
name|?
super|super
name|BibEntryTableViewModel
argument_list|>
name|listener
parameter_list|)
block|{
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|addListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
DECL|method|getPane ()
specifier|public
name|ScrollPane
name|getPane
parameter_list|()
block|{
return|return
name|pane
return|;
block|}
DECL|method|getTableModel ()
specifier|public
name|MainTableDataModel
name|getTableModel
parameter_list|()
block|{
return|return
name|model
return|;
block|}
comment|/*     // TODO: if the content of the cell is bigger than the cell itself render it as the tooltip     @Override     public String getToolTipText(MouseEvent e) {         String toolTipText = super.getToolTipText(e);         Point p = e.getPoint();         int col = columnAtPoint(p);         int row = rowAtPoint(p);          Rectangle bounds = getCellRect(row, col, false);         Dimension d = prepareRenderer(getCellRenderer(row, col), row, col).getPreferredSize();         // if the content of the cell is bigger than the cell itself render it as the tooltip (thus throwing the original tooltip away)         if ((d != null)&& (d.width> bounds.width)&& (getValueAt(row, col) != null)) {             toolTipText = getValueAt(row, col).toString();         }         return toolTipText;     }      // TODO: Support float mode     @Override     public TableCellRenderer getCellRenderer(int row, int column) {          int score = -3;         DefaultTableCellRenderer renderer = MainTable.defRenderer;          if ((model.getSearchState() != MainTableDataModel.DisplayOption.FLOAT)                 || matches(row, SearchMatcher.INSTANCE)) {             score++;         }         if ((model.getGroupingState() != MainTableDataModel.DisplayOption.FLOAT)                 || matches(row, GroupMatcher.INSTANCE)) {             score += 2;         }          // Now, a grayed out renderer is for entries with -1, and         // a very grayed out one for entries with -2         if (score< -1) {             if (column == 0) {                 MainTable.veryGrayedOutNumberRenderer.setNumber(row);                 renderer = MainTable.veryGrayedOutNumberRenderer;             } else {                 renderer = MainTable.veryGrayedOutRenderer;             }         }         else if (score == -1) {             if (column == 0) {                 MainTable.grayedOutNumberRenderer.setNumber(row);                 renderer = MainTable.grayedOutNumberRenderer;             } else {                 renderer = MainTable.grayedOutRenderer;             }         }          else if (column == 0) {             if (isComplete(row)) {                 MainTable.compRenderer.setNumber(row);                 int marking = isMarked(row);                 if (marking> 0) {                     marking = Math.min(marking, EntryMarker.MARK_COLOR_LEVELS);                     renderer = MainTable.markedNumberRenderers.get(marking - 1);                     MainTable.markedNumberRenderers.get(marking - 1).setNumber(row);                 } else {                     renderer = MainTable.compRenderer;                 }             } else {                 // Return a renderer with red background if the entry is incomplete.                 MainTable.incRenderer.setNumber(row);                 renderer = MainTable.incRenderer;             }         } else if (tableColorCodes || tableResolvedColorCodes) {             CellRendererMode status = getCellStatus(row, column, tableResolvedColorCodes);             if (status == CellRendererMode.REQUIRED) {                 renderer = MainTable.reqRenderer;             } else if (status == CellRendererMode.OPTIONAL) {                 renderer = MainTable.optRenderer;             } else if (status == CellRendererMode.RESOLVED) {                 renderer = MainTable.resolvedRenderer;             }         }          // For MARKED feature:         int marking = isMarked(row);         if ((column != 0)&& (marking> 0)) {             marking = Math.min(marking, EntryMarker.MARK_COLOR_LEVELS);             renderer = MainTable.markedRenderers.get(marking - 1);         }          return renderer;     }     */
DECL|method|getEntryAt (int row)
specifier|public
name|BibEntry
name|getEntryAt
parameter_list|(
name|int
name|row
parameter_list|)
block|{
return|return
name|model
operator|.
name|getEntriesFiltered
argument_list|()
operator|.
name|get
argument_list|(
name|row
argument_list|)
operator|.
name|getEntry
argument_list|()
return|;
block|}
DECL|method|getSelectedEntries ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getSelectedEntries
parameter_list|()
block|{
return|return
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItems
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|BibEntryTableViewModel
operator|::
name|getEntry
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * This method sets up what Comparators are used for the various table columns.      * The ComparatorChooser enables and disables such Comparators as the user clicks      * columns, but this is where the Comparators are defined. Also, the ComparatorChooser      * is initialized with the sort order defined in Preferences.      */
DECL|method|setupComparatorChooser ()
specifier|private
name|void
name|setupComparatorChooser
parameter_list|()
block|{
comment|// TODO: Proper sorting
comment|/*         // First column:         List<Comparator> comparators = comparatorChooser.getComparatorsForColumn(0);         comparators.clear();         comparators.add(new FirstColumnComparator(panel.getBibDatabaseContext()));          for (int i = 1; i< tableFormat.getColumnCount(); i++) {             MainTableColumn tableColumn = tableFormat.getTableColumn(i);              comparators = comparatorChooser.getComparatorsForColumn(i);             comparators.clear();              if (SpecialField.RANKING.getFieldName().equals(tableColumn.getColumnName())) {                 comparators.add(new RankingFieldComparator());             } else if (tableColumn.isIconColumn()) {                 comparators.add(new IconComparator(tableColumn.getBibtexFields()));             } else {                 comparators = comparatorChooser.getComparatorsForColumn(i);                 comparators.clear();                 comparators.add(new FieldComparator(tableFormat.getColumnName(i).toLowerCase(Locale.ROOT)));             }         }          // Set initial sort columns:          // Default sort order:         String[] sortFields = new String[] {                 Globals.prefs.get(JabRefPreferences.TABLE_PRIMARY_SORT_FIELD),                 Globals.prefs.get(JabRefPreferences.TABLE_SECONDARY_SORT_FIELD),                 Globals.prefs.get(JabRefPreferences.TABLE_TERTIARY_SORT_FIELD)         };         boolean[] sortDirections = new boolean[] {                 Globals.prefs.getBoolean(JabRefPreferences.TABLE_PRIMARY_SORT_DESCENDING),                 Globals.prefs.getBoolean(JabRefPreferences.TABLE_SECONDARY_SORT_DESCENDING),                 Globals.prefs.getBoolean(JabRefPreferences.TABLE_TERTIARY_SORT_DESCENDING)         }; // descending          model.getSortedForUserDefinedTableColumnSorting().getReadWriteLock().writeLock().lock();         try {             for (int i = 0; i< sortFields.length; i++) {                 int index = -1;                  // TODO where is this prefix set? //                if (!sortFields[i].startsWith(MainTableFormat.ICON_COLUMN_PREFIX))                 if (sortFields[i].startsWith("iconcol:")) {                     for (int j = 0; j< tableFormat.getColumnCount(); j++) {                         if (sortFields[i].equals(tableFormat.getColumnName(j))) {                             index = j;                             break;                         }                     }                 } else {                     index = tableFormat.getColumnIndex(sortFields[i]);                 }                 if (index>= 0) {                     comparatorChooser.appendComparator(index, 0, sortDirections[i]);                 }             }         } finally {             model.getSortedForUserDefinedTableColumnSorting().getReadWriteLock().writeLock().unlock();         }          // Add action listener so we can remember the sort order:         comparatorChooser.addSortActionListener(e -> {             // Get the information about the current sort order:             List<String> fields = getCurrentSortFields();             List<Boolean> order = getCurrentSortOrder();             // Update preferences:             int count = Math.min(fields.size(), order.size());             if (count>= 1) {                 Globals.prefs.put(JabRefPreferences.TABLE_PRIMARY_SORT_FIELD, fields.get(0));                 Globals.prefs.putBoolean(JabRefPreferences.TABLE_PRIMARY_SORT_DESCENDING, order.get(0));             }             if (count>= 2) {                 Globals.prefs.put(JabRefPreferences.TABLE_SECONDARY_SORT_FIELD, fields.get(1));                 Globals.prefs.putBoolean(JabRefPreferences.TABLE_SECONDARY_SORT_DESCENDING, order.get(1));             } else {                 Globals.prefs.put(JabRefPreferences.TABLE_SECONDARY_SORT_FIELD, "");                 Globals.prefs.putBoolean(JabRefPreferences.TABLE_SECONDARY_SORT_DESCENDING, false);             }             if (count>= 3) {                 Globals.prefs.put(JabRefPreferences.TABLE_TERTIARY_SORT_FIELD, fields.get(2));                 Globals.prefs.putBoolean(JabRefPreferences.TABLE_TERTIARY_SORT_DESCENDING, order.get(2));             } else {                 Globals.prefs.put(JabRefPreferences.TABLE_TERTIARY_SORT_FIELD, "");                 Globals.prefs.putBoolean(JabRefPreferences.TABLE_TERTIARY_SORT_DESCENDING, false);             }         }); */
block|}
comment|/*     // TODO: Reenable background coloring of fields (required/optional/...)     private CellRendererMode getCellStatus(int row, int col, boolean checkResolved) {         try {             BibEntry be = getEntryAt(row);             if (checkResolved&& tableFormat.getTableColumn(col).isResolved(be)) {                 return CellRendererMode.RESOLVED;             }             Optional<EntryType> type = EntryTypes.getType(be.getType(), panel.getBibDatabaseContext().getMode());             if (type.isPresent()) {                 String columnName = getColumnName(col).toLowerCase(Locale.ROOT);                 if (columnName.equals(BibEntry.KEY_FIELD) || type.get().getRequiredFieldsFlat().contains(columnName)) {                     return CellRendererMode.REQUIRED;                 }                 if (type.get().getOptionalFields().contains(columnName)) {                     return CellRendererMode.OPTIONAL;                 }             }             return CellRendererMode.OTHER;         } catch (NullPointerException ex) {             return CellRendererMode.OTHER;         }     }     */
DECL|method|findEntry (BibEntry entry)
specifier|public
name|Optional
argument_list|<
name|BibEntryTableViewModel
argument_list|>
name|findEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|model
operator|.
name|getEntriesFiltered
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|viewModel
lambda|->
name|viewModel
operator|.
name|getEntry
argument_list|()
operator|.
name|equals
argument_list|(
name|entry
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
return|;
block|}
DECL|method|matches (int row, Matcher<BibEntry> m)
specifier|private
name|boolean
name|matches
parameter_list|(
name|int
name|row
parameter_list|,
name|Matcher
argument_list|<
name|BibEntry
argument_list|>
name|m
parameter_list|)
block|{
return|return
name|getBibEntry
argument_list|(
name|row
argument_list|)
operator|.
name|map
argument_list|(
name|m
operator|::
name|matches
argument_list|)
operator|.
name|orElse
argument_list|(
literal|false
argument_list|)
return|;
block|}
DECL|method|isComplete (int row)
specifier|private
name|boolean
name|isComplete
parameter_list|(
name|int
name|row
parameter_list|)
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|bibEntry
init|=
name|getBibEntry
argument_list|(
name|row
argument_list|)
decl_stmt|;
if|if
condition|(
name|bibEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|TypedBibEntry
name|typedEntry
init|=
operator|new
name|TypedBibEntry
argument_list|(
name|bibEntry
operator|.
name|get
argument_list|()
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|typedEntry
operator|.
name|hasAllRequiredFields
argument_list|()
return|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|isMarked (int row)
specifier|private
name|int
name|isMarked
parameter_list|(
name|int
name|row
parameter_list|)
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|bibEntry
init|=
name|getBibEntry
argument_list|(
name|row
argument_list|)
decl_stmt|;
if|if
condition|(
name|bibEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|EntryMarker
operator|.
name|isMarked
argument_list|(
name|bibEntry
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|0
return|;
block|}
DECL|method|getBibEntry (int row)
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|getBibEntry
parameter_list|(
name|int
name|row
parameter_list|)
block|{
try|try
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|model
operator|.
name|getEntriesFiltered
argument_list|()
operator|.
name|get
argument_list|(
name|row
argument_list|)
operator|.
name|getEntry
argument_list|()
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|IndexOutOfBoundsException
name|e
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
comment|/**      * Repaints the table with the most recent font configuration      */
DECL|method|updateFont ()
specifier|public
name|void
name|updateFont
parameter_list|()
block|{
comment|/*         // TODO: Font& padding customization         setFont(GUIGlobals.currentFont);         int maxOfIconsAndFontSize = Math.max(GUIGlobals.currentFont.getSize(), Globals.prefs.getInt(JabRefPreferences.ICON_SIZE_SMALL));         setRowHeight(Globals.prefs.getInt(JabRefPreferences.TABLE_ROW_PADDING) + maxOfIconsAndFontSize);         // Update Table header with new settings         this.getTableHeader().setDefaultRenderer(new MainTableHeaderRenderer(this.getTableHeader().getDefaultRenderer()));         this.getTableHeader().resizeAndRepaint();         */
block|}
DECL|method|updateRenderers ()
specifier|public
specifier|static
name|void
name|updateRenderers
parameter_list|()
block|{
name|MainTable
operator|.
name|defRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_BACKGROUND
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_TEXT
argument_list|)
argument_list|)
expr_stmt|;
name|Color
name|sel
init|=
name|MainTable
operator|.
name|defRenderer
operator|.
name|getTableCellRendererComponent
argument_list|(
operator|new
name|JTable
argument_list|()
argument_list|,
literal|""
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
operator|.
name|getBackground
argument_list|()
decl_stmt|;
name|MainTable
operator|.
name|reqRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_REQ_FIELD_BACKGROUND
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_TEXT
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|optRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_OPT_FIELD_BACKGROUND
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_TEXT
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|resolvedRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_RESOLVED_FIELD_BACKGROUND
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_TEXT
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|incRenderer
operator|=
operator|new
name|IncompleteRenderer
argument_list|()
expr_stmt|;
name|MainTable
operator|.
name|compRenderer
operator|=
operator|new
name|CompleteRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_BACKGROUND
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|grayedOutNumberRenderer
operator|=
operator|new
name|CompleteRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|GRAYED_OUT_BACKGROUND
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|veryGrayedOutNumberRenderer
operator|=
operator|new
name|CompleteRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|VERY_GRAYED_OUT_BACKGROUND
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|grayedOutRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|GRAYED_OUT_BACKGROUND
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|GRAYED_OUT_TEXT
argument_list|)
argument_list|,
name|MainTable
operator|.
name|mixColors
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|GRAYED_OUT_BACKGROUND
argument_list|)
argument_list|,
name|sel
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|veryGrayedOutRenderer
operator|=
operator|new
name|GeneralRenderer
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|VERY_GRAYED_OUT_BACKGROUND
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|VERY_GRAYED_OUT_TEXT
argument_list|)
argument_list|,
name|MainTable
operator|.
name|mixColors
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|VERY_GRAYED_OUT_BACKGROUND
argument_list|)
argument_list|,
name|sel
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|markedRenderers
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|EntryMarker
operator|.
name|MARK_COLOR_LEVELS
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|markedNumberRenderers
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|EntryMarker
operator|.
name|MARK_COLOR_LEVELS
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|EntryMarker
operator|.
name|MARK_COLOR_LEVELS
condition|;
name|i
operator|++
control|)
block|{
name|Color
name|c
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|MARKED_ENTRY_BACKGROUND
operator|+
name|i
argument_list|)
decl_stmt|;
name|MainTable
operator|.
name|markedRenderers
operator|.
name|add
argument_list|(
operator|new
name|GeneralRenderer
argument_list|(
name|c
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_TEXT
argument_list|)
argument_list|,
name|MainTable
operator|.
name|mixColors
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getColor
argument_list|(
name|JabRefPreferences
operator|.
name|MARKED_ENTRY_BACKGROUND
operator|+
name|i
argument_list|)
argument_list|,
name|sel
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|markedNumberRenderers
operator|.
name|add
argument_list|(
operator|new
name|CompleteRenderer
argument_list|(
name|c
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|mixColors (Color one, Color two)
specifier|private
specifier|static
name|Color
name|mixColors
parameter_list|(
name|Color
name|one
parameter_list|,
name|Color
name|two
parameter_list|)
block|{
return|return
operator|new
name|Color
argument_list|(
operator|(
name|one
operator|.
name|getRed
argument_list|()
operator|+
name|two
operator|.
name|getRed
argument_list|()
operator|)
operator|/
literal|2
argument_list|,
operator|(
name|one
operator|.
name|getGreen
argument_list|()
operator|+
name|two
operator|.
name|getGreen
argument_list|()
operator|)
operator|/
literal|2
argument_list|,
operator|(
name|one
operator|.
name|getBlue
argument_list|()
operator|+
name|two
operator|.
name|getBlue
argument_list|()
operator|)
operator|/
literal|2
argument_list|)
return|;
block|}
block|}
end_class

end_unit

