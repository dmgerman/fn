begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|CardLayout
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
name|Iterator
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
name|JFileChooser
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
name|ListSelectionModel
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
name|export
operator|.
name|ExportFormats
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
name|groups
operator|.
name|GroupsPrefsTab
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
name|MainTable
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
comment|/**  * Preferences dialog. Contains a TabbedPane, and tabs will be defined in  * separate classes. Tabs MUST implement the PrefsTab interface, since this  * dialog will call the storeSettings() method of all tabs when the user presses  * ok.  *   * With this design, it should be very easy to add new tabs later.  *   */
end_comment

begin_class
DECL|class|PrefsDialog3
class|class
name|PrefsDialog3
extends|extends
name|JDialog
block|{
DECL|field|main
specifier|private
specifier|final
name|JPanel
name|main
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|PrefsDialog3 (JabRefFrame parent)
specifier|public
name|PrefsDialog3
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"JabRef preferences"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
specifier|final
name|JabRefPreferences
name|prefs
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|frame
operator|=
name|parent
expr_stmt|;
specifier|final
name|JList
name|chooser
decl_stmt|;
name|JButton
name|importPrefs
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|exportPrefs
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export preferences"
argument_list|)
argument_list|)
decl_stmt|;
name|main
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|JPanel
name|upper
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|lower
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|upper
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
name|lower
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
specifier|final
name|CardLayout
name|cardLayout
init|=
operator|new
name|CardLayout
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
name|cardLayout
argument_list|)
expr_stmt|;
comment|// ----------------------------------------------------------------
comment|// Add tabs to tabbed here. Remember, tabs must implement PrefsTab.
comment|// ----------------------------------------------------------------
name|ArrayList
argument_list|<
name|PrefsTab
argument_list|>
name|tabs
init|=
operator|new
name|ArrayList
argument_list|<
name|PrefsTab
argument_list|>
argument_list|()
decl_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|GeneralTab
argument_list|(
name|frame
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|NetworkTab
argument_list|(
name|frame
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|FileTab
argument_list|(
name|frame
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|FileSortTab
argument_list|(
name|frame
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|EntryEditorPrefsTab
argument_list|(
name|frame
argument_list|,
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|GroupsPrefsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|AppearancePrefsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|ExternalTab
argument_list|(
name|frame
argument_list|,
name|this
argument_list|,
name|prefs
argument_list|,
name|parent
operator|.
name|helpDiag
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|TablePrefsTab
argument_list|(
name|prefs
argument_list|,
name|parent
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|TableColumnsTab
argument_list|(
name|prefs
argument_list|,
name|parent
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|TabLabelPattern
argument_list|(
name|prefs
argument_list|,
name|parent
operator|.
name|helpDiag
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|PreviewPrefsTab
argument_list|(
name|prefs
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|NameFormatterTab
argument_list|(
name|parent
operator|.
name|helpDiag
argument_list|)
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|ImportSettingsTab
argument_list|()
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|XmpPrefsTab
argument_list|()
argument_list|)
expr_stmt|;
name|tabs
operator|.
name|add
argument_list|(
operator|new
name|AdvancedTab
argument_list|(
name|prefs
argument_list|,
name|parent
operator|.
name|helpDiag
argument_list|)
argument_list|)
expr_stmt|;
name|Iterator
argument_list|<
name|PrefsTab
argument_list|>
name|it
init|=
name|tabs
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[
name|tabs
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
comment|//ArrayList<Component> comps = new ArrayList<Component>();
while|while
condition|(
name|it
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|PrefsTab
name|tab
init|=
name|it
operator|.
name|next
argument_list|()
decl_stmt|;
name|names
index|[
name|i
index|]
operator|=
name|tab
operator|.
name|getTabName
argument_list|()
expr_stmt|;
name|i
operator|++
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
operator|(
name|Component
operator|)
name|tab
argument_list|,
name|tab
operator|.
name|getTabName
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|upper
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|chooser
operator|=
operator|new
name|JList
argument_list|(
name|names
argument_list|)
expr_stmt|;
name|chooser
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
comment|// Set a prototype value to control the width of the list:
name|chooser
operator|.
name|setPrototypeCellValue
argument_list|(
literal|"This should be wide enough"
argument_list|)
expr_stmt|;
name|chooser
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|chooser
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
comment|// Add the selection listener that will show the correct panel when
comment|// selection changes:
name|chooser
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|ListSelectionListener
argument_list|()
block|{
annotation|@
name|Override
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
return|return;
block|}
name|String
name|o
init|=
operator|(
name|String
operator|)
name|chooser
operator|.
name|getSelectedValue
argument_list|()
decl_stmt|;
name|cardLayout
operator|.
name|show
argument_list|(
name|main
argument_list|,
name|o
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JPanel
name|one
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|two
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|one
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|two
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|one
operator|.
name|add
argument_list|(
name|chooser
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|one
operator|.
name|add
argument_list|(
name|importPrefs
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|two
operator|.
name|add
argument_list|(
name|one
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|two
operator|.
name|add
argument_list|(
name|exportPrefs
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|upper
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|upper
operator|.
name|add
argument_list|(
name|two
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
name|upper
operator|.
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
name|cancel
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
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|OkAction
argument_list|()
argument_list|)
expr_stmt|;
name|CancelAction
name|cancelAction
init|=
operator|new
name|CancelAction
argument_list|()
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|lower
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|lower
argument_list|)
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
comment|//bb.addButton(ok);
comment|//bb.addButton(cancel);
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
comment|// lower.add(ok);
comment|// lower.add(cancel);
comment|// Key bindings:
name|Util
operator|.
name|bindCloseDialogKeyToCancelAction
argument_list|(
name|this
operator|.
name|getRootPane
argument_list|()
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
comment|// Import and export actions:
name|exportPrefs
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export preferences to file"
argument_list|)
argument_list|)
expr_stmt|;
name|importPrefs
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import preferences from file"
argument_list|)
argument_list|)
expr_stmt|;
name|exportPrefs
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
literal|".xml"
argument_list|,
name|JFileChooser
operator|.
name|SAVE_DIALOG
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
name|file
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
name|file
operator|.
name|exists
argument_list|()
operator|||
operator|(
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|PrefsDialog3
operator|.
name|this
argument_list|,
literal|'\''
operator|+
name|file
operator|.
name|getName
argument_list|()
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"exists. Overwrite file?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export preferences"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
operator|)
condition|)
block|{
try|try
block|{
name|prefs
operator|.
name|exportPreferences
argument_list|(
name|filename
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PrefsDialog3
operator|.
name|this
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not export preferences"
argument_list|)
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export preferences"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
comment|// ex.printStackTrace();
block|}
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|importPrefs
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
literal|".xml"
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
try|try
block|{
name|prefs
operator|.
name|importPreferences
argument_list|(
name|filename
argument_list|)
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
name|BibtexEntryType
operator|.
name|loadCustomEntryTypes
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|ExportFormats
operator|.
name|initAllExports
argument_list|()
expr_stmt|;
name|frame
operator|.
name|removeCachedEntryEditors
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|updateEntryEditorTabList
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|PrefsDialog3
operator|.
name|this
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not import preferences"
argument_list|)
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import preferences"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
comment|// ex.printStackTrace();
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|setValues
argument_list|()
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
comment|// setSize(440, 500);
comment|/** Look through component sizes to find which tab is to blame          *  when the dialog grows too large:         for (Component co : comps) {             System.out.println(co.getPreferredSize());         }*/
block|}
DECL|class|OkAction
class|class
name|OkAction
extends|extends
name|AbstractAction
block|{
DECL|method|OkAction ()
specifier|public
name|OkAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Ok"
argument_list|)
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
name|AbstractWorker
name|worker
init|=
operator|new
name|AbstractWorker
argument_list|()
block|{
name|boolean
name|ready
init|=
literal|true
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// First check that all tabs are ready to close:
name|int
name|count
init|=
name|main
operator|.
name|getComponentCount
argument_list|()
decl_stmt|;
name|Component
index|[]
name|comps
init|=
name|main
operator|.
name|getComponents
argument_list|()
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
name|count
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
operator|(
operator|(
name|PrefsTab
operator|)
name|comps
index|[
name|i
index|]
operator|)
operator|.
name|readyToClose
argument_list|()
condition|)
block|{
name|ready
operator|=
literal|false
expr_stmt|;
return|return;
comment|// If not, break off.
block|}
block|}
comment|// Then store settings and close:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|count
condition|;
name|i
operator|++
control|)
block|{
operator|(
operator|(
name|PrefsTab
operator|)
name|comps
index|[
name|i
index|]
operator|)
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|update
parameter_list|()
block|{
if|if
condition|(
operator|!
name|ready
condition|)
block|{
return|return;
block|}
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|MainTable
operator|.
name|updateRenderers
argument_list|()
expr_stmt|;
name|GUIGlobals
operator|.
name|updateEntryEditorColors
argument_list|()
expr_stmt|;
name|frame
operator|.
name|setupAllTables
argument_list|()
expr_stmt|;
name|frame
operator|.
name|groupSelector
operator|.
name|revalidateGroups
argument_list|()
expr_stmt|;
comment|// icons may have
comment|// changed
name|frame
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Preferences recorded."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|worker
operator|.
name|getWorker
argument_list|()
operator|.
name|run
argument_list|()
expr_stmt|;
name|worker
operator|.
name|getCallBack
argument_list|()
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
comment|// Update all field values in the tabs:
name|int
name|count
init|=
name|main
operator|.
name|getComponentCount
argument_list|()
decl_stmt|;
name|Component
index|[]
name|comps
init|=
name|main
operator|.
name|getComponents
argument_list|()
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
name|count
condition|;
name|i
operator|++
control|)
block|{
operator|(
operator|(
name|PrefsTab
operator|)
name|comps
index|[
name|i
index|]
operator|)
operator|.
name|setValues
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|CancelAction
class|class
name|CancelAction
extends|extends
name|AbstractAction
block|{
DECL|method|CancelAction ()
specifier|public
name|CancelAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Cancel"
argument_list|)
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
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

