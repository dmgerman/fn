begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.sql
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
package|;
end_package

begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.  This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or  (at your option) any later version.   This program is distributed in the hope that it will be useful,  but WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  GNU General Public License for more details.   You should have received a copy of the GNU General Public License along  with this program; if not, write to the Free Software Foundation, Inc.,  51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

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
name|awt
operator|.
name|event
operator|.
name|KeyEvent
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
name|KeyListener
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
name|awt
operator|.
name|event
operator|.
name|MouseListener
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
name|Vector
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
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
name|JLabel
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
name|KeyStroke
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
name|DefaultTableModel
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
name|JabRefFrame
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_comment
comment|/**  *   * @author ifsteinm  */
end_comment

begin_class
DECL|class|DBImportExportDialog
specifier|public
class|class
name|DBImportExportDialog
implements|implements
name|MouseListener
implements|,
name|KeyListener
block|{
DECL|field|diag
specifier|private
name|JDialog
name|diag
decl_stmt|;
DECL|field|table
specifier|private
name|JTable
name|table
decl_stmt|;
comment|// IMPORT
DECL|field|listOfDBs
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|listOfDBs
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|moreThanOne
specifier|public
name|boolean
name|moreThanOne
init|=
literal|false
decl_stmt|;
comment|// EXPORT
DECL|field|selectedDB
specifier|public
name|String
name|selectedDB
init|=
literal|""
decl_stmt|;
DECL|field|hasDBSelected
specifier|public
name|boolean
name|hasDBSelected
init|=
literal|false
decl_stmt|;
DECL|field|removeAction
specifier|public
name|boolean
name|removeAction
init|=
literal|false
decl_stmt|;
DECL|field|selectedInt
specifier|public
name|int
name|selectedInt
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|dialogType
specifier|private
name|DialogType
name|dialogType
decl_stmt|;
DECL|method|getDialogType ()
specifier|public
name|DialogType
name|getDialogType
parameter_list|()
block|{
return|return
name|dialogType
return|;
block|}
DECL|method|setDialogType (DialogType dialogType)
specifier|public
name|void
name|setDialogType
parameter_list|(
name|DialogType
name|dialogType
parameter_list|)
block|{
name|this
operator|.
name|dialogType
operator|=
name|dialogType
expr_stmt|;
block|}
DECL|enum|DialogType
specifier|public
enum|enum
name|DialogType
block|{
DECL|enumConstant|IMPORTER
DECL|enumConstant|EXPORTER
name|IMPORTER
argument_list|(
literal|"IMPORTER"
argument_list|)
block|,
name|EXPORTER
argument_list|(
literal|"EXPORTER"
argument_list|)
block|;
DECL|field|dialogType
specifier|private
name|String
name|dialogType
decl_stmt|;
DECL|method|DialogType (String dialogType)
specifier|private
name|DialogType
parameter_list|(
name|String
name|dialogType
parameter_list|)
block|{
name|this
operator|.
name|dialogType
operator|=
name|dialogType
expr_stmt|;
block|}
DECL|method|getDialogType ()
specifier|public
name|String
name|getDialogType
parameter_list|()
block|{
return|return
name|this
operator|.
name|dialogType
return|;
block|}
block|}
DECL|method|DBImportExportDialog (JabRefFrame frame, Vector<Vector<String>> rows, DialogType dialogType)
specifier|public
name|DBImportExportDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|Vector
argument_list|<
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|>
name|rows
parameter_list|,
name|DialogType
name|dialogType
parameter_list|)
block|{
name|this
operator|.
name|dialogType
operator|=
name|dialogType
expr_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|columns
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|columns
operator|.
name|add
argument_list|(
literal|"Databases"
argument_list|)
expr_stmt|;
name|table
operator|=
operator|new
name|JTable
argument_list|()
expr_stmt|;
name|DefaultTableModel
name|model
init|=
operator|new
name|DefaultTableModel
argument_list|(
name|rows
argument_list|,
name|columns
argument_list|)
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
return|return
literal|false
return|;
block|}
block|}
decl_stmt|;
name|table
operator|.
name|setModel
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|String
name|dialogTitle
decl_stmt|;
name|String
name|dialogTopMessage
decl_stmt|;
name|int
name|tableSelectionModel
decl_stmt|;
if|if
condition|(
name|dialogType
operator|.
name|equals
argument_list|(
name|DialogType
operator|.
name|EXPORTER
argument_list|)
condition|)
block|{
name|dialogTitle
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"SQL Database Exporter"
argument_list|)
expr_stmt|;
name|dialogTopMessage
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Select target SQL database:"
argument_list|)
expr_stmt|;
name|tableSelectionModel
operator|=
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
expr_stmt|;
name|table
operator|.
name|getInputMap
argument_list|(
name|JTable
operator|.
name|WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
argument_list|)
operator|.
name|put
argument_list|(
operator|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_ENTER
argument_list|,
literal|0
argument_list|)
operator|)
argument_list|,
literal|"exportAction"
argument_list|)
expr_stmt|;
name|table
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"exportAction"
argument_list|,
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
name|exportAction
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|dialogType
operator|=
name|dialogType
expr_stmt|;
name|dialogTitle
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"SQL Database Importer"
argument_list|)
expr_stmt|;
name|dialogTopMessage
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Please select which JabRef databases do you want to import:"
argument_list|)
expr_stmt|;
name|tableSelectionModel
operator|=
name|ListSelectionModel
operator|.
name|MULTIPLE_INTERVAL_SELECTION
expr_stmt|;
name|table
operator|.
name|getInputMap
argument_list|(
name|JTable
operator|.
name|WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
argument_list|)
operator|.
name|put
argument_list|(
operator|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_ENTER
argument_list|,
literal|0
argument_list|)
operator|)
argument_list|,
literal|"importAction"
argument_list|)
expr_stmt|;
name|table
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"importAction"
argument_list|,
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
name|importAction
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|dialogTitle
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|dialogTopMessage
argument_list|)
decl_stmt|;
name|lab
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|lab
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|table
operator|.
name|setSelectionMode
argument_list|(
name|tableSelectionModel
argument_list|)
expr_stmt|;
name|table
operator|.
name|setPreferredScrollableViewportSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|100
argument_list|,
literal|100
argument_list|)
argument_list|)
expr_stmt|;
name|table
operator|.
name|setTableHeader
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|table
operator|.
name|setRowSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|table
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|pan
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|pan
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|b
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|b
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|JButton
name|importButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|exportButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|dialogType
operator|.
name|equals
argument_list|(
name|DialogType
operator|.
name|IMPORTER
argument_list|)
condition|)
name|b
operator|.
name|addButton
argument_list|(
name|importButton
argument_list|)
expr_stmt|;
else|else
name|b
operator|.
name|addButton
argument_list|(
name|exportButton
argument_list|)
expr_stmt|;
name|b
operator|.
name|addRelatedGap
argument_list|()
expr_stmt|;
name|JButton
name|cancelButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|addButton
argument_list|(
name|cancelButton
argument_list|)
expr_stmt|;
name|b
operator|.
name|addRelatedGap
argument_list|()
expr_stmt|;
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
literal|"Remove Selected"
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|addButton
argument_list|(
name|removeButton
argument_list|)
expr_stmt|;
name|b
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|b
operator|.
name|getPanel
argument_list|()
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|b
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|table
operator|.
name|addMouseListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|importButton
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
name|arg0
parameter_list|)
block|{
name|importAction
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|exportButton
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
name|arg0
parameter_list|)
block|{
name|exportAction
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancelButton
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
name|arg0
parameter_list|)
block|{
name|moreThanOne
operator|=
literal|false
expr_stmt|;
name|hasDBSelected
operator|=
literal|false
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
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
name|arg0
parameter_list|)
block|{
name|moreThanOne
operator|=
literal|false
expr_stmt|;
name|hasDBSelected
operator|=
literal|true
expr_stmt|;
name|selectedInt
operator|=
name|table
operator|.
name|getSelectedRow
argument_list|()
expr_stmt|;
name|selectedDB
operator|=
operator|(
name|String
operator|)
name|table
operator|.
name|getValueAt
argument_list|(
name|selectedInt
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|int
name|areYouSure
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|diag
argument_list|,
literal|"Are you sure you want to remove the already\nexistent SQL DBs?"
argument_list|)
decl_stmt|;
if|if
condition|(
name|areYouSure
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
name|removeAction
operator|=
literal|true
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|getDiag ()
specifier|public
name|JDialog
name|getDiag
parameter_list|()
block|{
return|return
name|this
operator|.
name|diag
return|;
block|}
DECL|method|exportAction ()
specifier|private
name|void
name|exportAction
parameter_list|()
block|{
name|selectedInt
operator|=
name|table
operator|.
name|getSelectedRow
argument_list|()
expr_stmt|;
name|selectedDB
operator|=
operator|(
name|String
operator|)
name|table
operator|.
name|getValueAt
argument_list|(
name|selectedInt
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|hasDBSelected
operator|=
literal|true
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|importAction ()
specifier|private
name|void
name|importAction
parameter_list|()
block|{
name|int
index|[]
name|selectedInt
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|aSelectedInt
range|:
name|selectedInt
control|)
block|{
name|listOfDBs
operator|.
name|add
argument_list|(
operator|(
name|String
operator|)
name|table
operator|.
name|getValueAt
argument_list|(
name|aSelectedInt
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|moreThanOne
operator|=
literal|true
expr_stmt|;
block|}
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|mouseClicked (MouseEvent e)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|(
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
operator|)
operator|&&
name|this
operator|.
name|dialogType
operator|.
name|equals
argument_list|(
name|DialogType
operator|.
name|EXPORTER
argument_list|)
condition|)
block|{
name|this
operator|.
name|exportAction
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|mouseEntered (MouseEvent arg0)
specifier|public
name|void
name|mouseEntered
parameter_list|(
name|MouseEvent
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|mouseExited (MouseEvent arg0)
specifier|public
name|void
name|mouseExited
parameter_list|(
name|MouseEvent
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|mousePressed (MouseEvent arg0)
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|mouseReleased (MouseEvent arg0)
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|keyPressed (KeyEvent arg0)
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|keyReleased (KeyEvent arg0)
specifier|public
name|void
name|keyReleased
parameter_list|(
name|KeyEvent
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
annotation|@
name|Override
DECL|method|keyTyped (KeyEvent arg0)
specifier|public
name|void
name|keyTyped
parameter_list|(
name|KeyEvent
name|arg0
parameter_list|)
block|{
comment|// TODO Auto-generated method stub
block|}
block|}
end_class

end_unit

