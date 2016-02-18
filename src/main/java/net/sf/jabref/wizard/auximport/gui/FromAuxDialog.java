begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2004 R. Nagel  Copyright (C) 2016 JabRef Contributors    All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_comment
comment|// A wizard dialog for generating a new sub database from existing TeX aux file
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// created by : r.nagel 23.08.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified : 18.04.2006 r.nagel
end_comment

begin_comment
comment|//            insert a "short info" section
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
name|BorderLayout
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
name|io
operator|.
name|File
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
name|ActionMap
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
name|InputMap
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
name|JComboBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
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
name|JFileChooser
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
name|JTabbedPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextArea
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
name|keyboard
operator|.
name|KeyBinding
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
name|BibDatabase
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
name|BibEntry
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
name|JabRefFrame
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
name|FileDialogs
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
name|maintable
operator|.
name|MainTable
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
name|auximport
operator|.
name|AuxSubGenerator
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
name|DefaultFormBuilder
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
name|layout
operator|.
name|FormLayout
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
DECL|field|statusPanel
specifier|private
specifier|final
name|JPanel
name|statusPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|buttons
specifier|private
specifier|final
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|selectInDBButton
specifier|private
specifier|final
name|JButton
name|selectInDBButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|generateButton
specifier|private
specifier|final
name|JButton
name|generateButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|cancelButton
specifier|private
specifier|final
name|JButton
name|cancelButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|parseButton
specifier|private
specifier|final
name|JButton
name|parseButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|dbChooser
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|dbChooser
init|=
operator|new
name|JComboBox
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|auxFileField
specifier|private
name|JTextField
name|auxFileField
decl_stmt|;
DECL|field|notFoundList
specifier|private
name|JList
argument_list|<
name|String
argument_list|>
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
specifier|final
name|JTabbedPane
name|parentTabbedPane
decl_stmt|;
DECL|field|generatePressed
specifier|private
name|boolean
name|generatePressed
decl_stmt|;
DECL|field|auxParser
specifier|private
specifier|final
name|AuxSubGenerator
name|auxParser
decl_stmt|;
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
name|FromAuxDialog
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|JabRefFrame
name|parent
decl_stmt|;
DECL|method|FromAuxDialog (JabRefFrame frame, String title, boolean modal, JTabbedPane viewedDBs)
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
name|parent
operator|=
name|frame
expr_stmt|;
name|auxParser
operator|=
operator|new
name|AuxSubGenerator
argument_list|()
expr_stmt|;
name|jbInit
argument_list|()
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setSize
argument_list|(
literal|600
argument_list|,
literal|500
argument_list|)
expr_stmt|;
block|}
DECL|method|jbInit ()
specifier|private
name|void
name|jbInit
parameter_list|()
block|{
name|JPanel
name|panel1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|panel1
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|selectInDBButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select"
argument_list|)
argument_list|)
expr_stmt|;
name|selectInDBButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|selectInDBButton
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
name|arg0
parameter_list|)
block|{
name|FromAuxDialog
operator|.
name|this
operator|.
name|select_actionPerformed
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Generate"
argument_list|)
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|setEnabled
argument_list|(
literal|false
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
name|cancelButton
operator|.
name|setText
argument_list|(
name|Localization
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
name|parseButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Parse"
argument_list|)
argument_list|)
expr_stmt|;
name|parseButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|FromAuxDialog_parse_actionAdapter
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|initPanels
argument_list|()
expr_stmt|;
comment|// insert the buttons
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|JPanel
name|buttonPanel
init|=
name|bb
operator|.
name|getPanel
argument_list|()
decl_stmt|;
name|buttonPanel
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
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|parseButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addRelatedGap
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|selectInDBButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|generateButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancelButton
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
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
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"AUX file import"
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|desc
init|=
operator|new
name|JLabel
argument_list|(
literal|"<html><h3>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"AUX file import"
argument_list|)
operator|+
literal|"</h3><p>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"This feature generates a new database based on which entries "
operator|+
literal|"are needed in an existing LaTeX document."
argument_list|)
operator|+
literal|"</p>"
operator|+
literal|"<p>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"You need to select one of your open databases from which to choose "
operator|+
literal|"entries, as well as the AUX file produced by LaTeX when compiling your document."
argument_list|)
operator|+
literal|"</p></html>"
argument_list|)
decl_stmt|;
name|desc
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
name|panel1
operator|.
name|add
argument_list|(
name|desc
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|JPanel
name|centerPane
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
decl_stmt|;
name|centerPane
operator|.
name|add
argument_list|(
name|buttons
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|centerPane
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
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|panel1
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|centerPane
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
name|buttonPanel
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
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
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
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
block|}
DECL|method|initPanels ()
specifier|private
name|void
name|initPanels
parameter_list|()
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
name|int
name|toSelect
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
name|len
condition|;
name|i
operator|++
control|)
block|{
name|dbChooser
operator|.
name|addItem
argument_list|(
name|parentTabbedPane
operator|.
name|getTitleAt
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|parent
operator|.
name|getBasePanelAt
argument_list|(
name|i
argument_list|)
operator|==
name|parent
operator|.
name|getCurrentBasePanel
argument_list|()
condition|)
block|{
name|toSelect
operator|=
name|i
expr_stmt|;
block|}
block|}
if|if
condition|(
name|toSelect
operator|>=
literal|0
condition|)
block|{
name|dbChooser
operator|.
name|setSelectedIndex
argument_list|(
name|toSelect
argument_list|)
expr_stmt|;
block|}
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
name|JButton
name|browseAuxFileButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
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
name|notFoundList
operator|=
operator|new
name|JList
argument_list|<>
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
comment|//listScrollPane.setPreferredSize(new Dimension(250, 120));
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
name|JScrollPane
name|statusScrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|statusInfos
argument_list|)
decl_stmt|;
comment|//statusScrollPane.setPreferredSize(new Dimension(250, 120));
comment|//statusInfos.setBorder(BorderFactory.createEtchedBorder());
name|statusInfos
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|DefaultFormBuilder
name|b
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref:grow, 4dlu, left:pref"
argument_list|,
literal|""
argument_list|)
argument_list|,
name|buttons
argument_list|)
decl_stmt|;
name|b
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Options"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reference database"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|dbChooser
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"LaTeX AUX file"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|auxFileField
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|browseAuxFileButton
argument_list|)
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
name|b
operator|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"fill:pref:grow, 4dlu, fill:pref:grow"
argument_list|,
literal|"pref, pref, fill:pref:grow"
argument_list|)
argument_list|,
name|statusPanel
argument_list|)
expr_stmt|;
name|b
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Result"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unknown bibtex entries"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Messages"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|listScrollPane
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|statusScrollPane
argument_list|)
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
block|}
DECL|method|generate_actionPerformed ()
name|void
name|generate_actionPerformed
parameter_list|()
block|{
name|generatePressed
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|cancel_actionPerformed ()
name|void
name|cancel_actionPerformed
parameter_list|()
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|select_actionPerformed ()
specifier|private
name|void
name|select_actionPerformed
parameter_list|()
block|{
name|BibDatabase
name|db
init|=
name|getGenerateDB
argument_list|()
decl_stmt|;
name|MainTable
name|mainTable
init|=
name|parent
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|mainTable
decl_stmt|;
name|BibDatabase
name|database
init|=
name|parent
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|mainTable
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
for|for
control|(
name|BibEntry
name|newEntry
range|:
name|db
operator|.
name|getEntries
argument_list|()
control|)
block|{
comment|// the entries are not the same objects as in the original database
comment|// therefore, we have to search for the entries in the original database
comment|// to be able to find them in the maintable
name|BibEntry
name|origEntry
init|=
name|database
operator|.
name|getEntryByKey
argument_list|(
name|newEntry
operator|.
name|getCiteKey
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|row
init|=
name|mainTable
operator|.
name|findEntry
argument_list|(
name|origEntry
argument_list|)
decl_stmt|;
name|mainTable
operator|.
name|addSelection
argument_list|(
name|row
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|parse_actionPerformed ()
name|void
name|parse_actionPerformed
parameter_list|()
block|{
name|parseButton
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
name|BibDatabase
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
operator|(
name|auxName
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|refBase
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|auxName
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|auxParser
operator|.
name|clear
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|list
init|=
name|auxParser
operator|.
name|generate
argument_list|(
name|auxName
argument_list|,
name|refBase
argument_list|)
decl_stmt|;
name|notFoundList
operator|.
name|setListData
argument_list|(
name|list
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|list
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|statusInfos
operator|.
name|append
argument_list|(
name|auxParser
operator|.
name|getInformation
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
name|selectInDBButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// the generated database contains no entries -> no active generate-button
if|if
condition|(
name|auxParser
operator|.
name|emptyGeneratedDatabase
argument_list|()
condition|)
block|{
name|statusInfos
operator|.
name|append
argument_list|(
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"empty database"
argument_list|)
argument_list|)
expr_stmt|;
name|generateButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|parseButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|generatePressed ()
specifier|public
name|boolean
name|generatePressed
parameter_list|()
block|{
return|return
name|generatePressed
return|;
block|}
DECL|method|getGenerateDB ()
specifier|public
name|BibDatabase
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
comment|/**      * Action used to produce a "Browse" button for one of the text fields.      */
DECL|class|BrowseAction
specifier|static
class|class
name|BrowseAction
extends|extends
name|AbstractAction
block|{
DECL|field|comp
specifier|private
specifier|final
name|JTextField
name|comp
decl_stmt|;
DECL|field|_frame
specifier|private
specifier|final
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|method|BrowseAction (JTextField tc, JabRefFrame frame)
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
name|Localization
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
name|String
name|chosen
init|=
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|_frame
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
decl_stmt|;
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
DECL|class|FromAuxDialog_generate_actionAdapter
class|class
name|FromAuxDialog_generate_actionAdapter
implements|implements
name|ActionListener
block|{
DECL|field|adaptee
specifier|private
specifier|final
name|FromAuxDialog
name|adaptee
decl_stmt|;
DECL|method|FromAuxDialog_generate_actionAdapter (FromAuxDialog adaptee)
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
name|adaptee
operator|.
name|generate_actionPerformed
argument_list|()
expr_stmt|;
block|}
block|}
end_class

begin_class
DECL|class|FromAuxDialog_Cancel_actionAdapter
class|class
name|FromAuxDialog_Cancel_actionAdapter
implements|implements
name|ActionListener
block|{
DECL|field|adaptee
specifier|private
specifier|final
name|FromAuxDialog
name|adaptee
decl_stmt|;
DECL|method|FromAuxDialog_Cancel_actionAdapter (FromAuxDialog adaptee)
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
name|adaptee
operator|.
name|cancel_actionPerformed
argument_list|()
expr_stmt|;
block|}
block|}
end_class

begin_class
DECL|class|FromAuxDialog_parse_actionAdapter
class|class
name|FromAuxDialog_parse_actionAdapter
implements|implements
name|ActionListener
block|{
DECL|field|adaptee
specifier|private
specifier|final
name|FromAuxDialog
name|adaptee
decl_stmt|;
DECL|method|FromAuxDialog_parse_actionAdapter (FromAuxDialog adaptee)
name|FromAuxDialog_parse_actionAdapter
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
name|adaptee
operator|.
name|parse_actionPerformed
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

