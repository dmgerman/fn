begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2004 R. Nagel  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

begin_comment
comment|// created by : r.nagel 23.08.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
end_comment

begin_package
DECL|package|net.sf.jabref.wizard.auximport.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|auximport
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
name|javax
operator|.
name|swing
operator|.
name|border
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
name|*
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|auximport
operator|.
name|*
import|;
end_import

begin_class
DECL|class|FromAuxDialog
specifier|public
class|class
name|FromAuxDialog
extends|extends
name|JDialog
block|{
DECL|field|panel1
specifier|private
name|JPanel
name|panel1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|borderLayout1
specifier|private
name|BorderLayout
name|borderLayout1
init|=
operator|new
name|BorderLayout
argument_list|()
decl_stmt|;
DECL|field|statusPanel
specifier|private
name|JPanel
name|statusPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|jPanel2
specifier|private
name|JPanel
name|jPanel2
init|=
operator|new
name|JPanel
argument_list|()
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
DECL|field|okButton
specifier|private
name|JButton
name|okButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|cancelButton
specifier|private
name|JButton
name|cancelButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|generateButton
specifier|private
name|JButton
name|generateButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|titledBorder1
specifier|private
name|TitledBorder
name|titledBorder1
decl_stmt|;
DECL|field|dbChooser
specifier|private
name|JComboBox
name|dbChooser
init|=
operator|new
name|JComboBox
argument_list|()
decl_stmt|;
DECL|field|auxFileField
specifier|private
name|JTextField
name|auxFileField
decl_stmt|;
DECL|field|browseAuxFileButton
specifier|private
name|JButton
name|browseAuxFileButton
decl_stmt|;
DECL|field|notFoundList
specifier|private
name|JList
name|notFoundList
decl_stmt|;
DECL|field|statusInfos
specifier|private
name|JTextArea
name|statusInfos
decl_stmt|;
comment|// all open databases from JabRefFrame
DECL|field|parentTabbedPane
specifier|private
name|JTabbedPane
name|parentTabbedPane
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
init|=
literal|false
decl_stmt|;
DECL|field|auxParser
specifier|private
name|AuxSubGenerator
name|auxParser
decl_stmt|;
DECL|method|FromAuxDialog ( JabRefFrame frame, String title, boolean modal, JTabbedPane viewedDBs )
specifier|public
name|FromAuxDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|String
name|title
parameter_list|,
name|boolean
name|modal
parameter_list|,
name|JTabbedPane
name|viewedDBs
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|title
argument_list|,
name|modal
argument_list|)
expr_stmt|;
name|parentTabbedPane
operator|=
name|viewedDBs
expr_stmt|;
name|auxParser
operator|=
operator|new
name|AuxSubGenerator
argument_list|(
literal|null
argument_list|)
expr_stmt|;
try|try
block|{
name|jbInit
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|jbInit ( JabRefFrame parent )
specifier|private
name|void
name|jbInit
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|)
block|{
name|panel1
operator|.
name|setLayout
argument_list|(
name|borderLayout1
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|FromAuxDialog_ok_actionAdapter
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|cancelButton
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
expr_stmt|;
name|cancelButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|FromAuxDialog_Cancel_actionAdapter
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Generate"
argument_list|)
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|FromAuxDialog_generate_actionAdapter
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|initOptionsPanel
argument_list|(
name|parent
argument_list|)
expr_stmt|;
name|initStatusPanel
argument_list|()
expr_stmt|;
name|this
operator|.
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"AUX file import"
argument_list|)
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|panel1
argument_list|)
expr_stmt|;
name|panel1
operator|.
name|add
argument_list|(
name|optionsPanel
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|panel1
operator|.
name|add
argument_list|(
name|jPanel2
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|generateButton
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|okButton
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|cancelButton
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|panel1
operator|.
name|add
argument_list|(
name|statusPanel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|statusPanel
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|statusPanel
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
name|parent
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
argument_list|)
expr_stmt|;
block|}
DECL|method|initOptionsPanel (JabRefFrame parent)
specifier|private
name|void
name|initOptionsPanel
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|)
block|{
comment|// collect the names of all open databases
name|int
name|len
init|=
name|parentTabbedPane
operator|.
name|getTabCount
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|t
init|=
literal|0
init|;
name|t
operator|<
name|len
condition|;
name|t
operator|++
control|)
block|{
name|dbChooser
operator|.
name|addItem
argument_list|(
operator|(
name|String
operator|)
name|parentTabbedPane
operator|.
name|getTitleAt
argument_list|(
name|t
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// panel view
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|0
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|TitledBorder
name|border
init|=
operator|new
name|TitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createLineBorder
argument_list|(
operator|new
name|Color
argument_list|(
literal|153
argument_list|,
literal|153
argument_list|,
literal|153
argument_list|)
argument_list|,
literal|2
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Options"
argument_list|)
argument_list|)
decl_stmt|;
name|optionsPanel
operator|.
name|setBorder
argument_list|(
name|border
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
comment|// Database
name|JLabel
name|lab1
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Reference database"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|lab1
operator|.
name|setHorizontalAlignment
argument_list|(
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|lab1
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|dbChooser
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|dbChooser
argument_list|)
expr_stmt|;
comment|// AUX
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|15
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|lab1
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"LaTeX AUX file"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|lab1
operator|.
name|setHorizontalAlignment
argument_list|(
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|lab1
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|auxFileField
operator|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
literal|25
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|auxFileField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|auxFileField
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|15
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|browseAuxFileButton
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|browseAuxFileButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|auxFileField
argument_list|,
name|parent
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|browseAuxFileButton
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|optionsPanel
operator|.
name|add
argument_list|(
name|browseAuxFileButton
argument_list|)
expr_stmt|;
block|}
DECL|method|initStatusPanel ()
specifier|private
name|void
name|initStatusPanel
parameter_list|()
block|{
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|0
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|titledBorder1
operator|=
operator|new
name|TitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createLineBorder
argument_list|(
operator|new
name|Color
argument_list|(
literal|153
argument_list|,
literal|153
argument_list|,
literal|153
argument_list|)
argument_list|,
literal|2
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Results"
argument_list|)
argument_list|)
expr_stmt|;
name|statusPanel
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|statusPanel
operator|.
name|setBorder
argument_list|(
name|titledBorder1
argument_list|)
expr_stmt|;
name|JLabel
name|lab1
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unknown bibtex entries"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|lab1
operator|.
name|setHorizontalAlignment
argument_list|(
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|statusPanel
operator|.
name|add
argument_list|(
name|lab1
argument_list|)
expr_stmt|;
name|lab1
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Messages"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|lab1
operator|.
name|setHorizontalAlignment
argument_list|(
name|SwingConstants
operator|.
name|LEFT
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|statusPanel
operator|.
name|add
argument_list|(
name|lab1
argument_list|)
expr_stmt|;
name|notFoundList
operator|=
operator|new
name|JList
argument_list|()
expr_stmt|;
name|JScrollPane
name|listScrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|notFoundList
argument_list|)
decl_stmt|;
name|listScrollPane
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|250
argument_list|,
literal|120
argument_list|)
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridheight
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|15
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|listScrollPane
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|statusPanel
operator|.
name|add
argument_list|(
name|listScrollPane
argument_list|)
expr_stmt|;
name|statusInfos
operator|=
operator|new
name|JTextArea
argument_list|(
literal|""
argument_list|,
literal|5
argument_list|,
literal|20
argument_list|)
expr_stmt|;
name|statusInfos
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|statusInfos
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|statusInfos
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|statusPanel
operator|.
name|add
argument_list|(
name|statusInfos
argument_list|)
expr_stmt|;
block|}
comment|// ---------------------------------------------------------------------------
DECL|method|ok_actionPerformed ( ActionEvent e )
name|void
name|ok_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|okPressed
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|Cancel_actionPerformed ( ActionEvent e )
name|void
name|Cancel_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|generate_actionPerformed ( ActionEvent e )
name|void
name|generate_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|generateButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|BasePanel
name|bp
init|=
operator|(
name|BasePanel
operator|)
name|parentTabbedPane
operator|.
name|getComponentAt
argument_list|(
name|dbChooser
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
decl_stmt|;
name|notFoundList
operator|.
name|removeAll
argument_list|()
expr_stmt|;
name|statusInfos
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|BibtexDatabase
name|refBase
init|=
name|bp
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|String
name|auxName
init|=
name|auxFileField
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|auxName
operator|!=
literal|null
condition|)
if|if
condition|(
operator|(
name|refBase
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|auxName
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
block|{
name|auxParser
operator|.
name|clear
argument_list|()
expr_stmt|;
name|notFoundList
operator|.
name|setListData
argument_list|(
name|auxParser
operator|.
name|generate
argument_list|(
name|auxName
argument_list|,
name|refBase
argument_list|)
argument_list|)
expr_stmt|;
name|statusInfos
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"keys in database"
argument_list|)
operator|+
literal|" "
operator|+
name|refBase
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
name|statusInfos
operator|.
name|append
argument_list|(
literal|"\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"found in aux file"
argument_list|)
operator|+
literal|" "
operator|+
name|auxParser
operator|.
name|getFoundKeysInAux
argument_list|()
argument_list|)
expr_stmt|;
name|statusInfos
operator|.
name|append
argument_list|(
literal|"\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"resolved"
argument_list|)
operator|+
literal|" "
operator|+
name|auxParser
operator|.
name|getResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|statusInfos
operator|.
name|append
argument_list|(
literal|"\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"not found"
argument_list|)
operator|+
literal|" "
operator|+
name|auxParser
operator|.
name|getNotResolvedKeysCount
argument_list|()
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// the generated database contains no entries -> no active ok-button
if|if
condition|(
name|auxParser
operator|.
name|getGeneratedDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
operator|<
literal|1
condition|)
block|{
name|statusInfos
operator|.
name|append
argument_list|(
literal|"\n"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"empty database"
argument_list|)
argument_list|)
expr_stmt|;
name|okButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|generateButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|okPressed ()
specifier|public
name|boolean
name|okPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
DECL|method|getGenerateDB ()
specifier|public
name|BibtexDatabase
name|getGenerateDB
parameter_list|()
block|{
return|return
name|auxParser
operator|.
name|getGeneratedDatabase
argument_list|()
return|;
block|}
comment|// ---------------------------------------------------------------------------
comment|/**    * Action used to produce a "Browse" button for one of the text fields.    */
DECL|class|BrowseAction
class|class
name|BrowseAction
extends|extends
name|AbstractAction
block|{
DECL|field|comp
specifier|private
name|JTextField
name|comp
decl_stmt|;
DECL|field|_frame
specifier|private
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|method|BrowseAction ( JTextField tc, JabRefFrame frame)
specifier|public
name|BrowseAction
parameter_list|(
name|JTextField
name|tc
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|_frame
operator|=
name|frame
expr_stmt|;
name|comp
operator|=
name|tc
expr_stmt|;
block|}
DECL|method|actionPerformed ( ActionEvent e )
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|String
name|chosen
init|=
literal|null
decl_stmt|;
name|chosen
operator|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|_frame
argument_list|,
literal|null
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
literal|".aux"
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|File
name|newFile
init|=
operator|new
name|File
argument_list|(
name|chosen
argument_list|)
decl_stmt|;
name|comp
operator|.
name|setText
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

begin_comment
comment|// ----------- helper class -------------------
end_comment

begin_class
DECL|class|FromAuxDialog_ok_actionAdapter
class|class
name|FromAuxDialog_ok_actionAdapter
implements|implements
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
block|{
DECL|field|adaptee
name|FromAuxDialog
name|adaptee
decl_stmt|;
DECL|method|FromAuxDialog_ok_actionAdapter ( FromAuxDialog adaptee )
name|FromAuxDialog_ok_actionAdapter
parameter_list|(
name|FromAuxDialog
name|adaptee
parameter_list|)
block|{
name|this
operator|.
name|adaptee
operator|=
name|adaptee
expr_stmt|;
block|}
DECL|method|actionPerformed ( ActionEvent e )
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|adaptee
operator|.
name|ok_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
end_class

begin_class
DECL|class|FromAuxDialog_Cancel_actionAdapter
class|class
name|FromAuxDialog_Cancel_actionAdapter
implements|implements
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
block|{
DECL|field|adaptee
name|FromAuxDialog
name|adaptee
decl_stmt|;
DECL|method|FromAuxDialog_Cancel_actionAdapter ( FromAuxDialog adaptee )
name|FromAuxDialog_Cancel_actionAdapter
parameter_list|(
name|FromAuxDialog
name|adaptee
parameter_list|)
block|{
name|this
operator|.
name|adaptee
operator|=
name|adaptee
expr_stmt|;
block|}
DECL|method|actionPerformed ( ActionEvent e )
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|adaptee
operator|.
name|Cancel_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
end_class

begin_class
DECL|class|FromAuxDialog_generate_actionAdapter
class|class
name|FromAuxDialog_generate_actionAdapter
implements|implements
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
block|{
DECL|field|adaptee
name|FromAuxDialog
name|adaptee
decl_stmt|;
DECL|method|FromAuxDialog_generate_actionAdapter ( FromAuxDialog adaptee )
name|FromAuxDialog_generate_actionAdapter
parameter_list|(
name|FromAuxDialog
name|adaptee
parameter_list|)
block|{
name|this
operator|.
name|adaptee
operator|=
name|adaptee
expr_stmt|;
block|}
DECL|method|actionPerformed ( ActionEvent e )
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|adaptee
operator|.
name|generate_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

