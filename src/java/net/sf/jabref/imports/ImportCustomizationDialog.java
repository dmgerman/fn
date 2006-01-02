begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2005 Andreas Rudert, based on ExportCustomizationDialog by ??   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
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
name|javax
operator|.
name|swing
operator|.
name|JDialog
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
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|ActionListener
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
name|io
operator|.
name|File
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

begin_comment
comment|/**  * Dialog to manage custom importers.  */
end_comment

begin_class
DECL|class|ImportCustomizationDialog
specifier|public
class|class
name|ImportCustomizationDialog
extends|extends
name|JDialog
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|addFromFolderButton
specifier|private
name|JButton
name|addFromFolderButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Add from folder"
argument_list|)
argument_list|)
decl_stmt|;
comment|// TODO: allow importers to be added from Jar-Files
comment|// private JButton addFromJarButton = new JButton(Globals.lang("Add from jar"));
DECL|field|showDescButton
specifier|private
name|JButton
name|showDescButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show description"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|removeButton
specifier|private
name|JButton
name|removeButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|closeButton
specifier|private
name|JButton
name|closeButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Close"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|helpButton
specifier|private
name|JButton
name|helpButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|optionsPanel
specifier|private
name|JPanel
name|optionsPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|mainPanel
specifier|private
name|JPanel
name|mainPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|customImporterTable
specifier|private
name|JTable
name|customImporterTable
decl_stmt|;
DECL|field|prefs
specifier|private
name|JabRefPreferences
name|prefs
init|=
name|Globals
operator|.
name|prefs
decl_stmt|;
comment|/*    *  (non-Javadoc)    * @see java.awt.Component#getSize()    */
DECL|method|getSize ()
specifier|public
name|Dimension
name|getSize
parameter_list|()
block|{
name|int
name|width
init|=
name|GUIGlobals
operator|.
name|IMPORT_DIALOG_COL_0_WIDTH
operator|+
name|GUIGlobals
operator|.
name|IMPORT_DIALOG_COL_1_WIDTH
operator|+
name|GUIGlobals
operator|.
name|IMPORT_DIALOG_COL_2_WIDTH
operator|+
name|GUIGlobals
operator|.
name|IMPORT_DIALOG_COL_3_WIDTH
decl_stmt|;
return|return
operator|new
name|Dimension
argument_list|(
name|width
argument_list|,
name|width
operator|/
literal|2
argument_list|)
return|;
block|}
comment|/**    *     * @param frame_    * @throws HeadlessException    */
DECL|method|ImportCustomizationDialog (JabRefFrame frame_)
specifier|public
name|ImportCustomizationDialog
parameter_list|(
name|JabRefFrame
name|frame_
parameter_list|)
throws|throws
name|HeadlessException
block|{
name|super
argument_list|(
name|frame_
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Manage custom imports"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|frame
operator|=
name|frame_
expr_stmt|;
name|addFromFolderButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
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
name|Globals
operator|.
name|getNewDir
argument_list|(
name|frame
argument_list|,
name|prefs
argument_list|,
operator|new
name|File
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
literal|"workingDirectory"
argument_list|)
argument_list|)
argument_list|,
literal|""
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select Classpath of New Importer"
argument_list|)
argument_list|,
name|JFileChooser
operator|.
name|CUSTOM_DIALOG
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|chosenFileStr
init|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
name|prefs
argument_list|,
name|importer
operator|.
name|getBasePath
argument_list|()
argument_list|,
literal|".class"
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select new ImportFormat Subclass"
argument_list|)
argument_list|,
name|JFileChooser
operator|.
name|CUSTOM_DIALOG
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|chosenFileStr
operator|!=
literal|null
condition|)
block|{
try|try
block|{
name|File
name|chosenFile
init|=
operator|new
name|File
argument_list|(
name|chosenFileStr
argument_list|)
decl_stmt|;
name|String
name|className
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|!
name|chosenFile
operator|.
name|equals
argument_list|(
name|importer
operator|.
name|getBasePath
argument_list|()
argument_list|)
condition|)
block|{
name|className
operator|=
name|chosenFile
operator|.
name|getName
argument_list|()
operator|+
operator|(
name|className
operator|!=
literal|null
condition|?
literal|"."
operator|+
name|className
else|:
literal|""
operator|)
expr_stmt|;
name|chosenFile
operator|=
name|chosenFile
operator|.
name|getParentFile
argument_list|()
expr_stmt|;
block|}
comment|// TODO: there should be a less system dependent way of removing the extension
name|className
operator|=
name|className
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|className
operator|.
name|lastIndexOf
argument_list|(
literal|'.'
argument_list|)
argument_list|)
expr_stmt|;
name|importer
operator|.
name|setClassName
argument_list|(
name|className
argument_list|)
expr_stmt|;
name|importer
operator|.
name|setName
argument_list|(
name|importer
operator|.
name|getInstance
argument_list|()
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
name|importer
operator|.
name|setCliId
argument_list|(
name|importer
operator|.
name|getInstance
argument_list|()
operator|.
name|getCLIId
argument_list|()
argument_list|)
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
name|frame
argument_list|,
literal|"Could not instantiate "
operator|+
name|chosenFileStr
operator|+
literal|":\n "
operator|+
name|exc
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|prefs
operator|.
name|customImports
operator|.
name|replaceImporter
argument_list|(
name|importer
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|importFormatReader
operator|.
name|resetImportFormats
argument_list|()
expr_stmt|;
name|customImporterTable
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|customImporterTable
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|frame
operator|.
name|setUpImportMenus
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|addFromFolderButton
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Add a (compiled) custom ImportFormat class from a class path. \nThe path need not be on the classpath of JabRef."
argument_list|)
argument_list|)
expr_stmt|;
comment|// TODO: select class from jar
comment|/*     addFromJarButton.addActionListener(new ActionListener() {      public void actionPerformed(ActionEvent e) {        String basePath = Globals.getNewFile(frame, prefs, new File(prefs.get("workingDirectory")), ".jar",             JFileChooser.OPEN_DIALOG, true);        String chosenFile = Globals.getNewFile(frame, prefs, new File(basePath), ".class",                                               JFileChooser.OPEN_DIALOG, true);         if (chosenFile != null) {          URL[] urls = new URL[1];          try {            urls[0] = new URL("file:/" + new File(basePath).getAbsolutePath());            URLClassLoader cl = new URLClassLoader(urls);            // TODO: allow classes in packages             String className = chosenFile.substring(basePath.length()+1, chosenFile.lastIndexOf('.')).replace('\\','.');            Class importFormatClass = Class.forName(className, true, cl);            ImportFormat importFormat = (ImportFormat)importFormatClass.newInstance();            System.out.println(importFormat.getFormatName());          } catch (Exception exc) {            exc.printStackTrace();          }           String[] newFormat = new String[] {ecd.name(), ecd.layoutFile(), ecd.extension() };          Globals.importFormatReader.resetImportFormats();          customImporterTable.revalidate();          customImporterTable.repaint();          frame.setUpImportMenus();        }       }     );     */
name|showDescButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
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
name|customImporterTable
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
name|CustomImportList
operator|.
name|Importer
name|importer
init|=
operator|(
operator|(
name|ImportTableModel
operator|)
name|customImporterTable
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|getImporter
argument_list|(
name|row
argument_list|)
decl_stmt|;
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|importFormat
operator|.
name|getDescription
argument_list|()
argument_list|)
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
name|frame
argument_list|,
literal|"Could not instantiate "
operator|+
name|importer
operator|.
name|getClassName
argument_list|()
operator|+
literal|": "
operator|+
name|exc
operator|.
name|getMessage
argument_list|()
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
name|frame
argument_list|,
name|Globals
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
name|removeButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
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
name|customImporterTable
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
name|customImporterTable
operator|.
name|removeRowSelectionInterval
argument_list|(
name|row
argument_list|,
name|row
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|customImports
operator|.
name|remove
argument_list|(
operator|(
operator|(
name|ImportTableModel
operator|)
name|customImporterTable
operator|.
name|getModel
argument_list|()
operator|)
operator|.
name|getImporter
argument_list|(
name|row
argument_list|)
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|importFormatReader
operator|.
name|resetImportFormats
argument_list|()
expr_stmt|;
name|customImporterTable
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|customImporterTable
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|frame
operator|.
name|setUpImportMenus
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
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
name|AbstractAction
name|closeAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
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
decl_stmt|;
name|closeButton
operator|.
name|addActionListener
argument_list|(
name|closeAction
argument_list|)
expr_stmt|;
name|helpButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|frame
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|importCustomizationHelp
argument_list|,
literal|"Help"
argument_list|)
argument_list|)
expr_stmt|;
name|ImportTableModel
name|tableModel
init|=
operator|new
name|ImportTableModel
argument_list|()
decl_stmt|;
name|customImporterTable
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
name|customImporterTable
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
name|GUIGlobals
operator|.
name|IMPORT_DIALOG_COL_0_WIDTH
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
name|GUIGlobals
operator|.
name|IMPORT_DIALOG_COL_1_WIDTH
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
name|GUIGlobals
operator|.
name|IMPORT_DIALOG_COL_2_WIDTH
argument_list|)
expr_stmt|;
name|cm
operator|.
name|getColumn
argument_list|(
literal|3
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
name|GUIGlobals
operator|.
name|IMPORT_DIALOG_COL_3_WIDTH
argument_list|)
expr_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|customImporterTable
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
name|customImporterTable
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|customImporterTable
operator|.
name|setPreferredScrollableViewportSize
argument_list|(
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|customImporterTable
operator|.
name|getRowCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|customImporterTable
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
name|ActionMap
name|am
init|=
name|mainPanel
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|mainPanel
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|frame
operator|.
name|prefs
argument_list|()
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
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
name|optionsPanel
operator|.
name|add
argument_list|(
name|addFromFolderButton
argument_list|)
expr_stmt|;
comment|// optionsPanel.add(addFromJarButton);
name|optionsPanel
operator|.
name|add
argument_list|(
name|showDescButton
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|removeButton
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|closeButton
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
name|optionsPanel
operator|.
name|add
argument_list|(
name|helpButton
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
name|frame
argument_list|)
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|customImporterTable
argument_list|)
expr_stmt|;
block|}
comment|/**    * Table model for the custom importer table.    */
DECL|class|ImportTableModel
class|class
name|ImportTableModel
extends|extends
name|AbstractTableModel
block|{
DECL|field|columnNames
specifier|private
name|String
index|[]
name|columnNames
init|=
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import name"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Command line id"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"ImportFormat class"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Contained in"
argument_list|)
block|}
decl_stmt|;
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
name|CustomImportList
operator|.
name|Importer
name|importer
init|=
name|getImporter
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
name|importer
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
name|importer
operator|.
name|getClidId
argument_list|()
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
name|importer
operator|.
name|getClassName
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|columnIndex
operator|==
literal|3
condition|)
block|{
name|value
operator|=
name|importer
operator|.
name|getBasePath
argument_list|()
expr_stmt|;
block|}
return|return
name|value
return|;
block|}
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
DECL|method|getRowCount ()
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|prefs
operator|.
name|customImports
operator|.
name|size
argument_list|()
return|;
block|}
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
DECL|method|getImporter (int rowIndex)
specifier|public
name|CustomImportList
operator|.
name|Importer
name|getImporter
parameter_list|(
name|int
name|rowIndex
parameter_list|)
block|{
name|CustomImportList
operator|.
name|Importer
index|[]
name|importers
init|=
operator|(
name|CustomImportList
operator|.
name|Importer
index|[]
operator|)
name|Globals
operator|.
name|prefs
operator|.
name|customImports
operator|.
name|toArray
argument_list|(
operator|new
name|CustomImportList
operator|.
name|Importer
index|[]
block|{}
argument_list|)
decl_stmt|;
return|return
name|importers
index|[
name|rowIndex
index|]
return|;
block|}
block|}
block|}
end_class

end_unit

