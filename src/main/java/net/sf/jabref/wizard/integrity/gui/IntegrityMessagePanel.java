begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2004 R. Nagel  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_comment
comment|// created by : r.nagel 09.12.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : shows the IntegrityMessages produced by IntegrityCheck
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//     todo : several entries not supported
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
end_comment

begin_package
DECL|package|net.sf.jabref.wizard.integrity.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|integrity
operator|.
name|gui
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
name|Component
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
name|javax
operator|.
name|swing
operator|.
name|DefaultListCellRenderer
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ImageIcon
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
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
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
name|JTextField
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
name|ListSelectionListener
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
name|BasePanel
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
name|IconTheme
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
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
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
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
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
name|undo
operator|.
name|UndoableFieldChange
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
name|wizard
operator|.
name|integrity
operator|.
name|IntegrityCheck
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
name|wizard
operator|.
name|integrity
operator|.
name|IntegrityMessage
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
name|wizard
operator|.
name|text
operator|.
name|gui
operator|.
name|HintListModel
import|;
end_import

begin_class
DECL|class|IntegrityMessagePanel
specifier|public
class|class
name|IntegrityMessagePanel
extends|extends
name|JPanel
implements|implements
name|ListSelectionListener
implements|,
name|KeyListener
implements|,
name|ActionListener
block|{
DECL|field|warnings
specifier|private
specifier|final
name|JList
name|warnings
decl_stmt|;
DECL|field|warningData
specifier|private
specifier|final
name|HintListModel
name|warningData
decl_stmt|;
DECL|field|validChecker
specifier|private
specifier|final
name|IntegrityCheck
name|validChecker
decl_stmt|;
DECL|field|content
specifier|private
specifier|final
name|JTextField
name|content
decl_stmt|;
DECL|field|applyButton
specifier|private
specifier|final
name|JButton
name|applyButton
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|method|IntegrityMessagePanel (BasePanel basePanel)
specifier|public
name|IntegrityMessagePanel
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|validChecker
operator|=
operator|new
name|IntegrityCheck
argument_list|()
expr_stmt|;
comment|// errors, warnings, hints
comment|// JList --------------------------------------------------------------
name|warningData
operator|=
operator|new
name|HintListModel
argument_list|()
expr_stmt|;
name|warnings
operator|=
operator|new
name|JList
argument_list|(
name|warningData
argument_list|)
expr_stmt|;
name|warnings
operator|.
name|setCellRenderer
argument_list|(
operator|new
name|IntegrityListRenderer
argument_list|()
argument_list|)
expr_stmt|;
name|warnings
operator|.
name|addListSelectionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|JScrollPane
name|paneScrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|warnings
argument_list|)
decl_stmt|;
name|paneScrollPane
operator|.
name|setVerticalScrollBarPolicy
argument_list|(
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_ALWAYS
argument_list|)
expr_stmt|;
name|paneScrollPane
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|540
argument_list|,
literal|255
argument_list|)
argument_list|)
expr_stmt|;
name|paneScrollPane
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|10
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
comment|// Fix Panel ---------------------------------------------------------
name|JPanel
name|fixPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
comment|//    BoxLayout box = new BoxLayout(fixPanel, BoxLayout.LINE_AXIS) ;
name|JLabel
name|label1
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field_content"
argument_list|)
argument_list|)
decl_stmt|;
name|content
operator|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
expr_stmt|;
name|content
operator|.
name|addKeyListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|applyButton
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Apply"
argument_list|)
argument_list|)
expr_stmt|;
name|applyButton
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|applyButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|JButton
name|fixButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Suggest"
argument_list|)
argument_list|)
decl_stmt|;
name|fixButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|fixPanel
operator|.
name|add
argument_list|(
name|label1
argument_list|)
expr_stmt|;
name|fixPanel
operator|.
name|add
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|fixPanel
operator|.
name|add
argument_list|(
name|applyButton
argument_list|)
expr_stmt|;
name|fixPanel
operator|.
name|add
argument_list|(
name|fixButton
argument_list|)
expr_stmt|;
comment|// Main Panel --------------------------------------------------------
name|this
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|paneScrollPane
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|fixPanel
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
block|}
comment|// ------------------------------------------------------------------------
DECL|method|updateView (BibtexEntry entry)
specifier|public
name|void
name|updateView
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
name|warningData
operator|.
name|clear
argument_list|()
expr_stmt|;
name|IntegrityMessage
operator|.
name|setPrintMode
argument_list|(
name|IntegrityMessage
operator|.
name|SINLGE_MODE
argument_list|)
expr_stmt|;
name|warningData
operator|.
name|setData
argument_list|(
name|validChecker
operator|.
name|checkBibtexEntry
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|updateView (BibtexDatabase base)
specifier|public
name|void
name|updateView
parameter_list|(
name|BibtexDatabase
name|base
parameter_list|)
block|{
name|warningData
operator|.
name|clear
argument_list|()
expr_stmt|;
name|IntegrityMessage
operator|.
name|setPrintMode
argument_list|(
name|IntegrityMessage
operator|.
name|FULL_MODE
argument_list|)
expr_stmt|;
name|warningData
operator|.
name|setData
argument_list|(
name|validChecker
operator|.
name|checkBibtexDatabase
argument_list|(
name|base
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// ------------------------------------------------------------------------
comment|//This method is required by ListSelectionListener.
annotation|@
name|Override
DECL|method|valueChanged (ListSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getValueIsAdjusting
argument_list|()
condition|)
block|{
name|Object
name|obj
init|=
name|warnings
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|;
name|String
name|str
init|=
literal|""
decl_stmt|;
if|if
condition|(
name|obj
operator|!=
literal|null
condition|)
block|{
name|IntegrityMessage
name|msg
init|=
operator|(
name|IntegrityMessage
operator|)
name|obj
decl_stmt|;
name|BibtexEntry
name|entry
init|=
name|msg
operator|.
name|getEntry
argument_list|()
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
name|str
operator|=
name|entry
operator|.
name|getField
argument_list|(
name|msg
operator|.
name|getFieldName
argument_list|()
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|highlightEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
comment|// make the "invalid" field visible  ....
comment|//          EntryEditor editor = basePanel.getCurrentEditor() ;
comment|//          editor.
block|}
block|}
name|content
operator|.
name|setText
argument_list|(
name|str
argument_list|)
expr_stmt|;
name|applyButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
comment|// --------------------------------------------------------------------------
comment|// This methods are required by KeyListener
annotation|@
name|Override
DECL|method|keyPressed (KeyEvent e)
specifier|public
name|void
name|keyPressed
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|keyReleased (KeyEvent e)
specifier|public
name|void
name|keyReleased
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{
name|applyButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getKeyCode
argument_list|()
operator|==
name|KeyEvent
operator|.
name|VK_ENTER
condition|)
block|{
name|applyButton
operator|.
name|doClick
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|keyTyped (KeyEvent e)
specifier|public
name|void
name|keyTyped
parameter_list|(
name|KeyEvent
name|e
parameter_list|)
block|{     }
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|Object
name|obj
init|=
name|e
operator|.
name|getSource
argument_list|()
decl_stmt|;
if|if
condition|(
name|obj
operator|==
name|applyButton
condition|)
block|{
name|Object
name|data
init|=
name|warnings
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|data
operator|!=
literal|null
condition|)
block|{
name|IntegrityMessage
name|msg
init|=
operator|(
name|IntegrityMessage
operator|)
name|data
decl_stmt|;
name|BibtexEntry
name|entry
init|=
name|msg
operator|.
name|getEntry
argument_list|()
decl_stmt|;
if|if
condition|(
name|entry
operator|!=
literal|null
condition|)
block|{
comment|//          System.out.println("update") ;
name|String
name|oldContent
init|=
name|entry
operator|.
name|getField
argument_list|(
name|msg
operator|.
name|getFieldName
argument_list|()
argument_list|)
decl_stmt|;
name|UndoableFieldChange
name|edit
init|=
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|msg
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|oldContent
argument_list|,
name|content
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|msg
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|content
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|edit
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|msg
operator|.
name|setFixed
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//          updateView(entry) ;
name|warningData
operator|.
name|valueUpdated
argument_list|(
name|warnings
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|applyButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
comment|// ---------------------------------------------------------------------------
comment|// ---------------------------------------------------------------------------
DECL|class|IntegrityListRenderer
class|class
name|IntegrityListRenderer
extends|extends
name|DefaultListCellRenderer
block|{
DECL|field|warnIcon
specifier|final
name|ImageIcon
name|warnIcon
init|=
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"integrityWarn"
argument_list|)
decl_stmt|;
DECL|field|infoIcon
specifier|final
name|ImageIcon
name|infoIcon
init|=
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"integrityInfo"
argument_list|)
decl_stmt|;
DECL|field|failIcon
specifier|final
name|ImageIcon
name|failIcon
init|=
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"integrityFail"
argument_list|)
decl_stmt|;
DECL|field|fixedIcon
specifier|final
name|ImageIcon
name|fixedIcon
init|=
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"complete"
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getListCellRendererComponent ( JList list, Object value, int index, boolean iss, boolean chf)
specifier|public
name|Component
name|getListCellRendererComponent
parameter_list|(
name|JList
name|list
parameter_list|,
name|Object
name|value
parameter_list|,
comment|// value to display
name|int
name|index
parameter_list|,
comment|// cell index
name|boolean
name|iss
parameter_list|,
comment|// is the cell selected
name|boolean
name|chf
parameter_list|)
comment|// the list and the cell have the focus
block|{
name|super
operator|.
name|getListCellRendererComponent
argument_list|(
name|list
argument_list|,
name|value
argument_list|,
name|index
argument_list|,
name|iss
argument_list|,
name|chf
argument_list|)
expr_stmt|;
if|if
condition|(
name|value
operator|!=
literal|null
condition|)
block|{
name|IntegrityMessage
name|msg
init|=
operator|(
name|IntegrityMessage
operator|)
name|value
decl_stmt|;
if|if
condition|(
name|msg
operator|.
name|getFixed
argument_list|()
condition|)
block|{
name|setIcon
argument_list|(
name|fixedIcon
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|int
name|id
init|=
name|msg
operator|.
name|getType
argument_list|()
decl_stmt|;
if|if
condition|(
name|id
operator|<
literal|1000
condition|)
block|{
name|setIcon
argument_list|(
name|infoIcon
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|id
operator|<
literal|2000
condition|)
block|{
name|setIcon
argument_list|(
name|warnIcon
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setIcon
argument_list|(
name|failIcon
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|this
return|;
block|}
block|}
block|}
end_class

end_unit

