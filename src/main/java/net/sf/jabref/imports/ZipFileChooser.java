begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2005 Andreas Rudert   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|awt
operator|.
name|HeadlessException
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
name|text
operator|.
name|SimpleDateFormat
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
name|FocusRequester
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
import|;
end_import

begin_comment
comment|/**  * Dialog to allow users to choose a file contained in a ZIP file.  *   * @author andreas_sf at rudert-home dot de  */
end_comment

begin_class
DECL|class|ZipFileChooser
class|class
name|ZipFileChooser
extends|extends
name|JDialog
block|{
comment|/**      * Table model for the ZIP archive contents.      *       *<p>Contains one row for each entry.      * Does not contain rows for directory entries.</p>      *       *<p>The columns contain information about ZIIP file entries:      *<ol><li>      *   name {@link String}      *</li><li>      *   time of last modification {@link Date}      *</li><li>      *   size (uncompressed) {@link Long}      *</li></ol></p>      */
DECL|class|ZipFileChooserTableModel
class|class
name|ZipFileChooserTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|columnNames
specifier|private
specifier|final
name|String
index|[]
name|columnNames
init|=
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Name"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Last modified"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Size"
argument_list|)
block|}
decl_stmt|;
DECL|field|rows
specifier|private
name|ZipEntry
index|[]
name|rows
decl_stmt|;
DECL|field|zipFile
specifier|private
name|ZipFile
name|zipFile
decl_stmt|;
DECL|method|ZipFileChooserTableModel (ZipFile zipFile, ZipEntry[] rows)
name|ZipFileChooserTableModel
parameter_list|(
name|ZipFile
name|zipFile
parameter_list|,
name|ZipEntry
index|[]
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
name|length
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
name|length
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
index|[
name|col
index|]
return|;
block|}
comment|/**          * Zip-File entry at the given row index.          *           * @param rowIndex  row index          * @return  Zip file entry          */
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
index|[
name|rowIndex
index|]
return|;
block|}
comment|/**          * Zip file which contains all entries of this model.          *           * @return zip file          */
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
name|SimpleDateFormat
operator|.
name|getDateTimeInstance
argument_list|()
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|(
name|entry
operator|.
name|getTime
argument_list|()
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
comment|/** table of Zip entries */
DECL|field|table
specifier|private
specifier|final
name|JTable
name|table
decl_stmt|;
comment|/** shortcut to preferences */
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
init|=
name|Globals
operator|.
name|prefs
decl_stmt|;
comment|/** this */
DECL|field|zipFileChooser
specifier|private
specifier|final
name|ZipFileChooser
name|zipFileChooser
decl_stmt|;
comment|/** import customization dialog, owner of this dialog */
DECL|field|importCustomizationDialog
specifier|private
specifier|final
name|ImportCustomizationDialog
name|importCustomizationDialog
decl_stmt|;
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
comment|/**      * Entries that can be selected with this dialog.      *       * @param zipFile  Zip-File      * @return  entries that can be selected      */
DECL|method|getSelectableZipEntries (ZipFile zipFile)
specifier|private
name|ZipEntry
index|[]
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
argument_list|<
name|ZipEntry
argument_list|>
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
while|while
condition|(
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|ZipEntry
name|entry
init|=
name|e
operator|.
name|nextElement
argument_list|()
decl_stmt|;
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
operator|.
name|toArray
argument_list|(
operator|new
name|ZipEntry
index|[
name|entries
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
comment|/**      * New Zip file chooser.      *       * @param owner  Owner of the file chooser      * @param zipFile  Zip-Fle to choose from, must be readable      * @throws HeadlessException      */
DECL|method|ZipFileChooser (ImportCustomizationDialog owner, ZipFile zipFile)
specifier|public
name|ZipFileChooser
parameter_list|(
name|ImportCustomizationDialog
name|owner
parameter_list|,
name|ZipFile
name|zipFile
parameter_list|)
throws|throws
name|HeadlessException
block|{
name|super
argument_list|(
name|owner
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
name|this
operator|.
name|importCustomizationDialog
operator|=
name|owner
expr_stmt|;
name|this
operator|.
name|zipFileChooser
operator|=
name|this
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
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
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
name|okButton
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
operator|!=
operator|-
literal|1
condition|)
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
name|CustomImportList
operator|.
name|Importer
name|importer
init|=
name|prefs
operator|.
name|customImports
operator|.
expr|new
name|Importer
argument_list|()
decl_stmt|;
name|importer
operator|.
name|setBasePath
argument_list|(
name|model
operator|.
name|getZipFile
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
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
name|replaceAll
argument_list|(
literal|"/"
argument_list|,
literal|"."
argument_list|)
decl_stmt|;
name|importer
operator|.
name|setClassName
argument_list|(
name|className
argument_list|)
expr_stmt|;
try|try
block|{
name|ImportFormat
name|importFormat
init|=
name|importer
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|importer
operator|.
name|setName
argument_list|(
name|importFormat
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
name|importer
operator|.
name|setCliId
argument_list|(
name|importFormat
operator|.
name|getCLIId
argument_list|()
argument_list|)
expr_stmt|;
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
name|Exception
name|exc
parameter_list|)
block|{
name|exc
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|zipFileChooser
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not instantiate %0 %1"
argument_list|,
name|importer
operator|.
name|getName
argument_list|()
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
else|else
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|zipFileChooser
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
block|}
block|}
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
name|table
operator|=
operator|new
name|JTable
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
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
comment|//im.put(prefs.getKey("Close dialog"), "close");
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
name|Util
operator|.
name|placeDialog
argument_list|(
name|this
argument_list|,
name|owner
argument_list|)
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|table
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

