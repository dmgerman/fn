begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.plugin
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|plugin
package|;
end_package

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|SortedList
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
name|EventList
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
name|BasicEventList
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
name|gui
operator|.
name|TableFormat
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
name|swing
operator|.
name|DefaultEventTableModel
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
name|java
operator|.
name|awt
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|GUIGlobals
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|net
operator|.
name|URLDownload
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
name|help
operator|.
name|HelpAction
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
name|plugin
operator|.
name|PluginInstaller
operator|.
name|NameAndVersion
import|;
end_import

begin_comment
comment|/**  *  * @author alver  */
end_comment

begin_class
DECL|class|ManagePluginsDialog
class|class
name|ManagePluginsDialog
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|diag
specifier|private
specifier|final
name|JDialog
name|diag
decl_stmt|;
DECL|field|plugins
specifier|private
name|SortedList
argument_list|<
name|NameAndVersion
argument_list|>
name|plugins
decl_stmt|;
DECL|field|table
specifier|private
specifier|final
name|JTable
name|table
decl_stmt|;
DECL|field|tableOther
specifier|private
specifier|final
name|JTable
name|tableOther
decl_stmt|;
DECL|method|ManagePluginsDialog (JabRefFrame frame)
specifier|public
name|ManagePluginsDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plugin manager"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|JButton
name|help
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
name|help
operator|.
name|addActionListener
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|Globals
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|pluginHelp
argument_list|,
literal|"Help"
argument_list|)
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plugins installed in your user plugin directory (%0):"
argument_list|,
name|PluginCore
operator|.
name|userPluginDir
operator|.
name|getPath
argument_list|()
argument_list|)
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
comment|// Table for user dir plugins:
name|table
operator|=
operator|new
name|JTable
argument_list|()
expr_stmt|;
comment|// Table for other plugiuns
name|tableOther
operator|=
operator|new
name|JTable
argument_list|()
expr_stmt|;
name|tableOther
operator|.
name|setRowSelectionAllowed
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|tableOther
operator|.
name|setColumnSelectionAllowed
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|tableOther
operator|.
name|setCellSelectionEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|buildList
argument_list|()
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
literal|200
argument_list|)
argument_list|)
expr_stmt|;
name|tableOther
operator|.
name|setPreferredScrollableViewportSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|500
argument_list|,
literal|100
argument_list|)
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
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plugins installed in other locations:"
argument_list|)
argument_list|)
expr_stmt|;
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
name|pan
operator|.
name|add
argument_list|(
operator|new
name|JScrollPane
argument_list|(
name|tableOther
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
name|install
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Install plugin"
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|addButton
argument_list|(
name|install
argument_list|)
expr_stmt|;
name|JButton
name|download
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download plugin"
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|addButton
argument_list|(
name|download
argument_list|)
expr_stmt|;
name|JButton
name|remove
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete"
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|addButton
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|JButton
name|close
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
name|b
operator|.
name|addButton
argument_list|(
name|close
argument_list|)
expr_stmt|;
name|b
operator|.
name|addRelatedGap
argument_list|()
expr_stmt|;
name|b
operator|.
name|addButton
argument_list|(
name|help
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
name|install
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
name|installPlugin
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|download
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
name|downloadPlugin
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Action
name|closeListener
init|=
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
name|arg0
parameter_list|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|close
operator|.
name|addActionListener
argument_list|(
name|closeListener
argument_list|)
expr_stmt|;
name|remove
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
name|removeSelected
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|b
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|b
operator|.
name|getPanel
argument_list|()
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
name|prefs
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
name|closeListener
argument_list|)
expr_stmt|;
block|}
DECL|method|removeSelected ()
specifier|private
name|void
name|removeSelected
parameter_list|()
block|{
name|int
index|[]
name|sel
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|sel
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|String
name|message
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete the %0 selected plugins?"
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|sel
operator|.
name|length
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|title
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete plugins"
argument_list|)
decl_stmt|;
if|if
condition|(
name|sel
operator|.
name|length
operator|==
literal|1
condition|)
block|{
name|message
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete the selected plugin?"
argument_list|)
expr_stmt|;
name|title
operator|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete plugin"
argument_list|)
expr_stmt|;
block|}
name|int
name|reply
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|message
argument_list|,
name|title
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|reply
operator|!=
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
return|return;
block|}
name|boolean
name|success
init|=
literal|true
decl_stmt|;
for|for
control|(
name|int
name|aSel
range|:
name|sel
control|)
block|{
name|NameAndVersion
name|nav
init|=
name|plugins
operator|.
name|get
argument_list|(
name|aSel
argument_list|)
decl_stmt|;
name|success
operator|=
name|PluginInstaller
operator|.
name|deletePlugin
argument_list|(
name|nav
argument_list|)
operator|&
name|success
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|success
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|sel
operator|.
name|length
operator|>
literal|1
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plugins will be deleted next time JabRef starts up."
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plugin will be deleted next time JabRef starts up."
argument_list|)
argument_list|,
name|sel
operator|.
name|length
operator|>
literal|1
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete plugins"
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete plugin"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|buildList
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|buildList ()
specifier|private
name|void
name|buildList
parameter_list|()
block|{
name|plugins
operator|=
operator|new
name|SortedList
argument_list|<
name|NameAndVersion
argument_list|>
argument_list|(
name|PluginInstaller
operator|.
name|findInstalledPlugins
argument_list|()
argument_list|)
expr_stmt|;
comment|// Move those plugins that are not installed in the user plugin dir to another list:
name|EventList
argument_list|<
name|NameAndVersion
argument_list|>
name|outsideUserDir
init|=
operator|new
name|BasicEventList
argument_list|<
name|NameAndVersion
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|NameAndVersion
argument_list|>
name|i
init|=
name|plugins
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|NameAndVersion
name|nav
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|nav
operator|.
name|inUserDirectory
condition|)
block|{
name|outsideUserDir
operator|.
name|add
argument_list|(
name|nav
argument_list|)
expr_stmt|;
name|i
operator|.
name|remove
argument_list|()
expr_stmt|;
block|}
block|}
name|SortedList
argument_list|<
name|NameAndVersion
argument_list|>
name|pluginsOther
init|=
operator|new
name|SortedList
argument_list|<
name|NameAndVersion
argument_list|>
argument_list|(
name|outsideUserDir
argument_list|)
decl_stmt|;
name|TableFormat
argument_list|<
name|NameAndVersion
argument_list|>
name|tableFormatOther
init|=
operator|new
name|PluginTableFormat
argument_list|()
decl_stmt|;
name|DefaultEventTableModel
argument_list|<
name|NameAndVersion
argument_list|>
name|tableModel
init|=
operator|new
name|DefaultEventTableModel
argument_list|<
name|NameAndVersion
argument_list|>
argument_list|(
name|pluginsOther
argument_list|,
name|tableFormatOther
argument_list|)
decl_stmt|;
name|tableOther
operator|.
name|setModel
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|tableOther
operator|.
name|getColumnModel
argument_list|()
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
name|tableOther
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|tableOther
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|2
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|TableFormat
argument_list|<
name|NameAndVersion
argument_list|>
name|tableFormat
init|=
operator|new
name|PluginTableFormat
argument_list|()
decl_stmt|;
name|DefaultEventTableModel
argument_list|<
name|NameAndVersion
argument_list|>
name|tableModelOther
init|=
operator|new
name|DefaultEventTableModel
argument_list|<
name|NameAndVersion
argument_list|>
argument_list|(
name|plugins
argument_list|,
name|tableFormat
argument_list|)
decl_stmt|;
name|table
operator|.
name|setModel
argument_list|(
name|tableModelOther
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
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
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|1
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|table
operator|.
name|getColumnModel
argument_list|()
operator|.
name|getColumn
argument_list|(
literal|2
argument_list|)
operator|.
name|setPreferredWidth
argument_list|(
literal|50
argument_list|)
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
name|diag
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
DECL|method|installPlugin ()
specifier|private
name|void
name|installPlugin
parameter_list|()
block|{
name|String
name|filename
init|=
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|)
argument_list|)
argument_list|,
literal|".jar"
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
name|filename
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|f
operator|.
name|exists
argument_list|()
condition|)
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
literal|"File not found"
argument_list|)
operator|+
literal|"."
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plugin installer"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|installFromFile
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|downloadPlugin ()
specifier|private
name|void
name|downloadPlugin
parameter_list|()
block|{
name|String
name|url
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Enter download URL"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|url
operator|==
literal|null
condition|)
block|{
return|return;
block|}
try|try
block|{
name|installFromURL
argument_list|(
operator|new
name|URL
argument_list|(
name|url
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e
parameter_list|)
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
literal|"Invalid URL"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plugin installer"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|installFromURL (URL url)
specifier|private
name|void
name|installFromURL
parameter_list|(
name|URL
name|url
parameter_list|)
block|{
try|try
block|{
name|File
name|tmpFile
init|=
name|File
operator|.
name|createTempFile
argument_list|(
literal|"jabref-plugin"
argument_list|,
literal|".jar"
argument_list|)
decl_stmt|;
name|tmpFile
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
name|URLDownload
name|ud
init|=
name|URLDownload
operator|.
name|buildMonitoredDownload
argument_list|(
name|frame
argument_list|,
name|url
argument_list|)
decl_stmt|;
name|ud
operator|.
name|downloadToFile
argument_list|(
name|tmpFile
argument_list|)
expr_stmt|;
name|String
name|path
init|=
name|url
operator|.
name|getPath
argument_list|()
decl_stmt|;
name|int
name|pos
init|=
name|path
operator|.
name|lastIndexOf
argument_list|(
literal|'/'
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|pos
operator|>=
literal|0
operator|)
operator|&&
operator|(
name|pos
operator|<
operator|(
name|path
operator|.
name|length
argument_list|()
operator|-
literal|1
operator|)
operator|)
condition|)
block|{
name|path
operator|=
name|path
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
name|PluginInstaller
operator|.
name|installPlugin
argument_list|(
name|frame
argument_list|,
name|tmpFile
argument_list|,
name|path
argument_list|)
expr_stmt|;
name|tmpFile
operator|.
name|delete
argument_list|()
expr_stmt|;
name|buildList
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|installFromFile (File file)
specifier|private
name|void
name|installFromFile
parameter_list|(
name|File
name|file
parameter_list|)
block|{
name|PluginInstaller
operator|.
name|installPlugin
argument_list|(
name|frame
argument_list|,
name|file
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|buildList
argument_list|()
expr_stmt|;
block|}
DECL|class|PluginTableFormat
specifier|private
class|class
name|PluginTableFormat
implements|implements
name|TableFormat
argument_list|<
name|NameAndVersion
argument_list|>
block|{
annotation|@
name|Override
DECL|method|getColumnCount ()
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|3
return|;
block|}
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
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Plugin name"
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|col
operator|==
literal|1
condition|)
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Version"
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Status"
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|getColumnValue (NameAndVersion nav, int col)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|NameAndVersion
name|nav
parameter_list|,
name|int
name|col
parameter_list|)
block|{
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
return|return
name|nav
operator|.
name|name
return|;
block|}
elseif|else
if|if
condition|(
name|col
operator|==
literal|1
condition|)
block|{
if|if
condition|(
operator|!
name|nav
operator|.
name|version
operator|.
name|equals
argument_list|(
name|PluginInstaller
operator|.
name|VersionNumber
operator|.
name|ZERO
argument_list|)
condition|)
block|{
return|return
name|nav
operator|.
name|version
operator|.
name|toString
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unknown"
argument_list|)
return|;
block|}
block|}
else|else
block|{
name|int
name|status
init|=
name|nav
operator|.
name|getStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|status
operator|==
literal|0
condition|)
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Not loaded"
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|status
operator|==
literal|1
condition|)
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Loaded"
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
return|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

