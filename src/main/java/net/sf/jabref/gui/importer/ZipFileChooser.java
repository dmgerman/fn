begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
package|;
end_package

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
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|ZoneId
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|ZonedDateTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|FormatStyle
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
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|zip
operator|.
name|ZipEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|zip
operator|.
name|ZipFile
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Box
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
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
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
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
name|AbstractTableModel
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
name|gui
operator|.
name|util
operator|.
name|GUIUtil
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
name|importer
operator|.
name|fileformat
operator|.
name|CustomImporter
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Dialog to allow users to choose a file contained in a ZIP file.  *  * @author andreas_sf at rudert-home dot de  */
end_comment

begin_class
DECL|class|ZipFileChooser
class|class
name|ZipFileChooser
extends|extends
name|JDialog
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|ZipFileChooser
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * New ZIP file chooser.      *      * @param owner  Owner of the file chooser      * @param zipFile  ZIP-Fle to choose from, must be readable      */
DECL|method|ZipFileChooser (ImportCustomizationDialog importCustomizationDialog, ZipFile zipFile)
specifier|public
name|ZipFileChooser
parameter_list|(
name|ImportCustomizationDialog
name|importCustomizationDialog
parameter_list|,
name|ZipFile
name|zipFile
parameter_list|)
block|{
name|super
argument_list|(
name|importCustomizationDialog
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select file from ZIP-archive"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|ZipFileChooserTableModel
name|tableModel
init|=
operator|new
name|ZipFileChooserTableModel
argument_list|(
name|zipFile
argument_list|,
name|getSelectableZipEntries
argument_list|(
name|zipFile
argument_list|)
argument_list|)
decl_stmt|;
name|JTable
name|table
init|=
operator|new
name|JTable
argument_list|(
name|tableModel
argument_list|)
decl_stmt|;
name|TableColumnModel
name|cm
init|=
name|table
operator|.
name|getColumnModel
argument_list|()
decl_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|150
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|2
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
decl_stmt|;
name|table
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|table
operator|.
name|setPreferredScrollableViewportSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|500
argument_list|,
literal|150
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|table
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
block|}
name|GUIUtil
operator|.
name|correctRowHeight
argument_list|(
name|table
argument_list|)
expr_stmt|;
comment|// cancel: no entry is selected
name|JButton
name|cancelButton
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
name|cancelButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
argument_list|()
argument_list|)
expr_stmt|;
comment|// ok: get selected class and check if it is instantiable as an importer
name|JButton
name|okButton
init|=
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
decl_stmt|;
name|okButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|int
name|row
init|=
name|table
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|==
operator|-
literal|1
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please select an importer."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ZipFileChooserTableModel
name|model
init|=
operator|(
name|ZipFileChooserTableModel
operator|)
name|table
operator|.
name|getModel
argument_list|()
decl_stmt|;
name|ZipEntry
name|tempZipEntry
init|=
name|model
operator|.
name|getZipEntry
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|String
name|className
init|=
name|tempZipEntry
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|tempZipEntry
operator|.
name|getName
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
operator|.
name|replace
argument_list|(
literal|"/"
argument_list|,
literal|"."
argument_list|)
decl_stmt|;
try|try
block|{
name|CustomImporter
name|importer
init|=
operator|new
name|CustomImporter
argument_list|(
name|model
operator|.
name|getZipFile
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|className
argument_list|)
decl_stmt|;
name|importCustomizationDialog
operator|.
name|addOrReplaceImporter
argument_list|(
name|importer
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|exc
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not instantiate importer: "
operator|+
name|className
argument_list|,
name|exc
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not instantiate %0 %1"
argument_list|,
name|className
operator|+
literal|":\n"
argument_list|,
name|exc
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|JPanel
name|mainPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
comment|//ActionMap am = mainPanel.getActionMap();
comment|//InputMap im = mainPanel.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
comment|//im.put(Globals.getKeyPrefs().getKey(KeyBinds.CLOSE_DIALOG), "close");
comment|//am.put("close", closeAction);
name|mainPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|mainPanel
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
name|JPanel
name|optionsPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|okButton
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|cancelButton
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createHorizontalStrut
argument_list|(
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|mainPanel
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
name|optionsPanel
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|this
operator|.
name|setSize
argument_list|(
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|this
operator|.
name|setLocationRelativeTo
argument_list|(
name|importCustomizationDialog
argument_list|)
expr_stmt|;
name|table
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
comment|/**      * Entries that can be selected with this dialog.      *      * @param zipFile  ZIP-File      * @return  entries that can be selected      */
DECL|method|getSelectableZipEntries (ZipFile zipFile)
specifier|private
specifier|static
name|List
argument_list|<
name|ZipEntry
argument_list|>
name|getSelectableZipEntries
parameter_list|(
name|ZipFile
name|zipFile
parameter_list|)
block|{
name|List
argument_list|<
name|ZipEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|Enumeration
argument_list|<
name|?
extends|extends
name|ZipEntry
argument_list|>
name|e
init|=
name|zipFile
operator|.
name|entries
argument_list|()
decl_stmt|;
for|for
control|(
name|ZipEntry
name|entry
range|:
name|Collections
operator|.
name|list
argument_list|(
name|e
argument_list|)
control|)
block|{
if|if
condition|(
operator|!
name|entry
operator|.
name|isDirectory
argument_list|()
operator|&&
name|entry
operator|.
name|getName
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".class"
argument_list|)
condition|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|entries
return|;
block|}
comment|/*      *  (non-Javadoc)      * @see java.awt.Component#getSize()      */
annotation|@
name|Override
DECL|method|getSize ()
specifier|public
name|Dimension
name|getSize
parameter_list|()
block|{
return|return
operator|new
name|Dimension
argument_list|(
literal|400
argument_list|,
literal|300
argument_list|)
return|;
block|}
comment|/**      * Table model for the ZIP archive contents.      *      *<p>Contains one row for each entry.      * Does not contain rows for directory entries.</p>      *      *<p>The columns contain information about ZIP file entries:      *<ol><li>      *   name {@link String}      *</li><li>      *   time of last modification {@link Date}      *</li><li>      *   size (uncompressed) {@link Long}      *</li></ol></p>      */
DECL|class|ZipFileChooserTableModel
specifier|private
specifier|static
class|class
name|ZipFileChooserTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|columnNames
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|columnNames
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Last modified"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Size"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|rows
specifier|private
specifier|final
name|List
argument_list|<
name|ZipEntry
argument_list|>
name|rows
decl_stmt|;
DECL|field|zipFile
specifier|private
specifier|final
name|ZipFile
name|zipFile
decl_stmt|;
DECL|method|ZipFileChooserTableModel (ZipFile zipFile, List<ZipEntry> rows)
name|ZipFileChooserTableModel
parameter_list|(
name|ZipFile
name|zipFile
parameter_list|,
name|List
argument_list|<
name|ZipEntry
argument_list|>
name|rows
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|rows
operator|=
name|rows
expr_stmt|;
name|this
operator|.
name|zipFile
operator|=
name|zipFile
expr_stmt|;
block|}
comment|/*          *  (non-Javadoc)          * @see javax.swing.table.TableModel#getColumnCount()          */
annotation|@
name|Override
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
name|columnNames
operator|.
name|size
argument_list|()
return|;
block|}
comment|/*          *  (non-Javadoc)          * @see javax.swing.table.TableModel#getRowCount()          */
annotation|@
name|Override
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|this
operator|.
name|rows
operator|.
name|size
argument_list|()
return|;
block|}
comment|/*          *  (non-Javadoc)          * @see javax.swing.table.TableModel#getColumnName(int)          */
annotation|@
name|Override
DECL|method|getColumnName (int col)
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
return|return
name|columnNames
operator|.
name|get
argument_list|(
name|col
argument_list|)
return|;
block|}
comment|/**          * ZIP-File entry at the given row index.          *          * @param rowIndex  row index          * @return  ZIP file entry          */
DECL|method|getZipEntry (int rowIndex)
specifier|public
name|ZipEntry
name|getZipEntry
parameter_list|(
name|int
name|rowIndex
parameter_list|)
block|{
return|return
name|this
operator|.
name|rows
operator|.
name|get
argument_list|(
name|rowIndex
argument_list|)
return|;
block|}
comment|/**          * ZIP file which contains all entries of this model.          *          * @return zip file          */
DECL|method|getZipFile ()
specifier|public
name|ZipFile
name|getZipFile
parameter_list|()
block|{
return|return
name|this
operator|.
name|zipFile
return|;
block|}
comment|/*          *  (non-Javadoc)          * @see javax.swing.table.TableModel#getValueAt(int, int)          */
annotation|@
name|Override
DECL|method|getValueAt (int rowIndex, int columnIndex)
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|rowIndex
parameter_list|,
name|int
name|columnIndex
parameter_list|)
block|{
name|Object
name|value
init|=
literal|null
decl_stmt|;
name|ZipEntry
name|entry
init|=
name|getZipEntry
argument_list|(
name|rowIndex
argument_list|)
decl_stmt|;
if|if
condition|(
name|columnIndex
operator|==
literal|0
condition|)
block|{
name|value
operator|=
name|entry
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|1
condition|)
block|{
name|value
operator|=
name|ZonedDateTime
operator|.
name|ofInstant
argument_list|(
operator|new
name|Date
argument_list|(
name|entry
operator|.
name|getTime
argument_list|()
argument_list|)
operator|.
name|toInstant
argument_list|()
argument_list|,
name|ZoneId
operator|.
name|systemDefault
argument_list|()
argument_list|)
operator|.
name|format
argument_list|(
name|DateTimeFormatter
operator|.
name|ofLocalizedDateTime
argument_list|(
name|FormatStyle
operator|.
name|MEDIUM
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|2
condition|)
block|{
name|value
operator|=
name|entry
operator|.
name|getSize
argument_list|()
expr_stmt|;
block|}
return|return
name|value
return|;
block|}
block|}
block|}
end_class

end_unit

