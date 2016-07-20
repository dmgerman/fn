begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|exporter
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
name|event
operator|.
name|ActionEvent
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
name|Action
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
name|ScrollPaneConstants
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
name|gui
operator|.
name|help
operator|.
name|HelpFile
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
name|gui
operator|.
name|util
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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

begin_comment
comment|/**  *<p>Title:</p>  *<p>Description:</p>  *<p>Copyright: Copyright (c) 2003</p>  *<p>Company:</p>  * @author not attributable  * @version 1.0  */
end_comment

begin_class
DECL|class|ExportCustomizationDialog
specifier|public
class|class
name|ExportCustomizationDialog
extends|extends
name|JDialog
block|{
comment|// Column widths for export customization dialog table:
DECL|field|COL_0_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|COL_0_WIDTH
init|=
literal|50
decl_stmt|;
DECL|field|COL_1_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|COL_1_WIDTH
init|=
literal|200
decl_stmt|;
DECL|field|COL_2_WIDTH
specifier|private
specifier|static
specifier|final
name|int
name|COL_2_WIDTH
init|=
literal|30
decl_stmt|;
DECL|method|ExportCustomizationDialog (final JabRefFrame frame)
specifier|public
name|ExportCustomizationDialog
parameter_list|(
specifier|final
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Manage custom exports"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|DefaultEventTableModel
argument_list|<
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|tableModel
init|=
operator|new
name|DefaultEventTableModel
argument_list|<>
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|getSortedList
argument_list|()
argument_list|,
operator|new
name|ExportTableFormat
argument_list|()
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
name|COL_0_WIDTH
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
name|COL_1_WIDTH
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
name|COL_2_WIDTH
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
name|ScrollPaneConstants
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|ScrollPaneConstants
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
name|JButton
name|addExport
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add new"
argument_list|)
argument_list|)
decl_stmt|;
name|addExport
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|CustomExportDialog
name|ecd
init|=
operator|new
name|CustomExportDialog
argument_list|(
name|frame
argument_list|)
decl_stmt|;
name|ecd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|ecd
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|newFormat
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|ecd
operator|.
name|name
argument_list|()
argument_list|,
name|ecd
operator|.
name|layoutFile
argument_list|()
argument_list|,
name|ecd
operator|.
name|extension
argument_list|()
argument_list|)
decl_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|addFormat
argument_list|(
name|newFormat
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|store
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|modify
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Modify"
argument_list|)
argument_list|)
decl_stmt|;
name|modify
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
return|return;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|old
init|=
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|getSortedList
argument_list|()
operator|.
name|get
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|CustomExportDialog
name|ecd
init|=
operator|new
name|CustomExportDialog
argument_list|(
name|frame
argument_list|,
name|old
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|old
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|,
name|old
operator|.
name|get
argument_list|(
literal|2
argument_list|)
argument_list|)
decl_stmt|;
name|ecd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// ecd.show(); -> deprecated since 1.5
if|if
condition|(
name|ecd
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|old
operator|.
name|set
argument_list|(
literal|0
argument_list|,
name|ecd
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|old
operator|.
name|set
argument_list|(
literal|1
argument_list|,
name|ecd
operator|.
name|layoutFile
argument_list|()
argument_list|)
expr_stmt|;
name|old
operator|.
name|set
argument_list|(
literal|2
argument_list|,
name|ecd
operator|.
name|extension
argument_list|()
argument_list|)
expr_stmt|;
name|table
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|table
operator|.
name|repaint
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|store
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JButton
name|remove
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|)
decl_stmt|;
name|remove
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|int
index|[]
name|rows
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return;
block|}
name|List
argument_list|<
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<>
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
name|rows
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|getSortedList
argument_list|()
operator|.
name|get
argument_list|(
name|rows
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|List
argument_list|<
name|String
argument_list|>
name|list
range|:
name|entries
control|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|remove
argument_list|(
name|list
argument_list|)
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|store
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|Action
name|closeAction
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
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|JButton
name|close
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Close"
argument_list|)
argument_list|)
decl_stmt|;
name|close
operator|.
name|addActionListener
argument_list|(
name|closeAction
argument_list|)
expr_stmt|;
name|JButton
name|help
init|=
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|CUSTOM_EXPORTS
argument_list|)
operator|.
name|getHelpButton
argument_list|()
decl_stmt|;
comment|// Key bindings:
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ActionMap
name|am
init|=
name|main
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|main
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
name|closeAction
argument_list|)
expr_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|main
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
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|buttons
argument_list|)
decl_stmt|;
name|buttons
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
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|addExport
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|modify
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|close
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addUnrelatedGap
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|help
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|getContentPane
argument_list|()
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
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|buttons
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|table
argument_list|)
expr_stmt|;
block|}
DECL|class|ExportTableFormat
specifier|private
specifier|static
class|class
name|ExportTableFormat
implements|implements
name|TableFormat
argument_list|<
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
DECL|method|getColumnValue (List<String> strings, int i)
specifier|public
name|Object
name|getColumnValue
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|strings
parameter_list|,
name|int
name|i
parameter_list|)
block|{
return|return
name|strings
operator|.
name|get
argument_list|(
name|i
argument_list|)
return|;
block|}
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
switch|switch
condition|(
name|col
condition|)
block|{
case|case
literal|0
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export name"
argument_list|)
return|;
case|case
literal|1
case|:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Main layout file"
argument_list|)
return|;
default|default:
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Extension"
argument_list|)
return|;
block|}
block|}
block|}
block|}
end_class

end_unit

