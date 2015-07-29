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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
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
name|*
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
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|TableModel
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
name|layout
operator|.
name|format
operator|.
name|NameFormatter
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
name|help
operator|.
name|HelpDialog
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
DECL|class|NameFormatterTab
specifier|public
class|class
name|NameFormatterTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|NAME_FORMATTER_VALUE
specifier|public
specifier|static
specifier|final
name|String
name|NAME_FORMATTER_VALUE
init|=
literal|"nameFormatterFormats"
decl_stmt|;
DECL|field|NAME_FORMATER_KEY
specifier|public
specifier|static
specifier|final
name|String
name|NAME_FORMATER_KEY
init|=
literal|"nameFormatterNames"
decl_stmt|;
DECL|method|getNameFormatters ()
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getNameFormatters
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|result
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|String
index|[]
name|names
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATER_KEY
argument_list|)
decl_stmt|;
name|String
index|[]
name|formats
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATTER_VALUE
argument_list|)
decl_stmt|;
if|if
condition|(
name|names
operator|==
literal|null
condition|)
block|{
name|names
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
if|if
condition|(
name|formats
operator|==
literal|null
condition|)
block|{
name|formats
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|names
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|<
name|formats
operator|.
name|length
condition|)
block|{
name|result
operator|.
name|put
argument_list|(
name|names
index|[
name|i
index|]
argument_list|,
name|formats
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|put
argument_list|(
name|names
index|[
name|i
index|]
argument_list|,
name|NameFormatter
operator|.
name|DEFAULT_FORMAT
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
return|;
block|}
DECL|field|tableChanged
specifier|private
name|boolean
name|tableChanged
init|=
literal|false
decl_stmt|;
DECL|field|table
specifier|private
specifier|final
name|JTable
name|table
decl_stmt|;
DECL|field|rowCount
specifier|private
name|int
name|rowCount
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|tableRows
specifier|private
specifier|final
name|Vector
argument_list|<
name|TableRow
argument_list|>
name|tableRows
init|=
operator|new
name|Vector
argument_list|<>
argument_list|(
literal|10
argument_list|)
decl_stmt|;
DECL|class|TableRow
specifier|static
class|class
name|TableRow
block|{
DECL|field|name
name|String
name|name
decl_stmt|;
DECL|field|format
name|String
name|format
decl_stmt|;
DECL|method|TableRow ()
specifier|public
name|TableRow
parameter_list|()
block|{
name|this
argument_list|(
literal|""
argument_list|)
expr_stmt|;
block|}
DECL|method|TableRow (String name)
specifier|public
name|TableRow
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|NameFormatter
operator|.
name|DEFAULT_FORMAT
argument_list|)
expr_stmt|;
block|}
DECL|method|TableRow (String name, String format)
specifier|public
name|TableRow
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|format
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|this
operator|.
name|format
operator|=
name|format
expr_stmt|;
block|}
block|}
comment|/**      * Tab to create custom Name Formatters      *       */
DECL|method|NameFormatterTab (HelpDialog helpDialog)
specifier|public
name|NameFormatterTab
parameter_list|(
name|HelpDialog
name|helpDialog
parameter_list|)
block|{
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|TableModel
name|tm
init|=
operator|new
name|AbstractTableModel
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|rowCount
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|2
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getValueAt
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|column
parameter_list|)
block|{
if|if
condition|(
name|row
operator|>=
name|tableRows
operator|.
name|size
argument_list|()
condition|)
block|{
return|return
literal|""
return|;
block|}
name|TableRow
name|tr
init|=
name|tableRows
operator|.
name|elementAt
argument_list|(
name|row
argument_list|)
decl_stmt|;
if|if
condition|(
name|tr
operator|==
literal|null
condition|)
block|{
return|return
literal|""
return|;
block|}
switch|switch
condition|(
name|column
condition|)
block|{
case|case
literal|0
case|:
return|return
name|tr
operator|.
name|name
return|;
case|case
literal|1
case|:
return|return
name|tr
operator|.
name|format
return|;
block|}
return|return
literal|null
return|;
comment|// Unreachable.
block|}
annotation|@
name|Override
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
return|return
name|col
operator|==
literal|0
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"Formatter Name"
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Format String"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Class
argument_list|<
name|String
argument_list|>
name|getColumnClass
parameter_list|(
name|int
name|column
parameter_list|)
block|{
return|return
name|String
operator|.
name|class
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|isCellEditable
parameter_list|(
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|setValueAt
parameter_list|(
name|Object
name|value
parameter_list|,
name|int
name|row
parameter_list|,
name|int
name|col
parameter_list|)
block|{
name|tableChanged
operator|=
literal|true
expr_stmt|;
comment|// Make sure the vector is long enough.
while|while
condition|(
name|row
operator|>=
name|tableRows
operator|.
name|size
argument_list|()
condition|)
block|{
name|tableRows
operator|.
name|add
argument_list|(
operator|new
name|TableRow
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|TableRow
name|rowContent
init|=
name|tableRows
operator|.
name|elementAt
argument_list|(
name|row
argument_list|)
decl_stmt|;
if|if
condition|(
name|col
operator|==
literal|0
condition|)
block|{
name|rowContent
operator|.
name|name
operator|=
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|rowContent
operator|.
name|format
operator|=
name|value
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
name|table
operator|=
operator|new
name|JTable
argument_list|(
name|tm
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
literal|140
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
literal|400
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|tabPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|tabPanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
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
name|setPreferredScrollableViewportSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|250
argument_list|,
literal|200
argument_list|)
argument_list|)
expr_stmt|;
name|sp
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|250
argument_list|,
literal|300
argument_list|)
argument_list|)
expr_stmt|;
name|sp
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|600
argument_list|,
literal|300
argument_list|)
argument_list|)
expr_stmt|;
name|tabPanel
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
name|JToolBar
name|tlb
init|=
operator|new
name|JToolBar
argument_list|(
name|SwingConstants
operator|.
name|VERTICAL
argument_list|)
decl_stmt|;
name|tlb
operator|.
name|setFloatable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
operator|new
name|AddRowAction
argument_list|()
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
operator|new
name|DeleteRowAction
argument_list|()
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
operator|new
name|HelpAction
argument_list|(
name|helpDialog
argument_list|,
name|GUIGlobals
operator|.
name|nameFormatterHelp
argument_list|,
literal|"Help on Name Formatting"
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|tabPanel
operator|.
name|add
argument_list|(
name|tlb
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Special Name Formatters"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|tabPanel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|pan
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
name|pan
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
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|tableRows
operator|.
name|clear
argument_list|()
expr_stmt|;
name|String
index|[]
name|names
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATER_KEY
argument_list|)
decl_stmt|;
name|String
index|[]
name|formats
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATTER_VALUE
argument_list|)
decl_stmt|;
if|if
condition|(
name|names
operator|==
literal|null
condition|)
block|{
name|names
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
if|if
condition|(
name|formats
operator|==
literal|null
condition|)
block|{
name|formats
operator|=
operator|new
name|String
index|[]
block|{}
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|names
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|<
name|formats
operator|.
name|length
condition|)
block|{
name|tableRows
operator|.
name|add
argument_list|(
operator|new
name|TableRow
argument_list|(
name|names
index|[
name|i
index|]
argument_list|,
name|formats
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|tableRows
operator|.
name|add
argument_list|(
operator|new
name|TableRow
argument_list|(
name|names
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|rowCount
operator|=
name|tableRows
operator|.
name|size
argument_list|()
operator|+
literal|5
expr_stmt|;
block|}
DECL|class|DeleteRowAction
class|class
name|DeleteRowAction
extends|extends
name|AbstractAction
block|{
DECL|method|DeleteRowAction ()
specifier|public
name|DeleteRowAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Delete row"
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"remove"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Delete rows"
argument_list|)
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
name|tableChanged
operator|=
literal|true
expr_stmt|;
name|int
index|[]
name|selectedRows
init|=
name|table
operator|.
name|getSelectedRows
argument_list|()
decl_stmt|;
name|int
name|numberDeleted
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
name|selectedRows
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
if|if
condition|(
name|selectedRows
index|[
name|i
index|]
operator|<
name|tableRows
operator|.
name|size
argument_list|()
condition|)
block|{
name|tableRows
operator|.
name|remove
argument_list|(
name|selectedRows
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|numberDeleted
operator|++
expr_stmt|;
block|}
block|}
name|rowCount
operator|-=
name|numberDeleted
expr_stmt|;
if|if
condition|(
name|selectedRows
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|table
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
block|}
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
block|}
block|}
DECL|class|AddRowAction
class|class
name|AddRowAction
extends|extends
name|AbstractAction
block|{
DECL|method|AddRowAction ()
specifier|public
name|AddRowAction
parameter_list|()
block|{
name|super
argument_list|(
literal|"Add row"
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"add"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Insert rows"
argument_list|)
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
comment|// No rows selected, so we just add one at the end.
name|rowCount
operator|++
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
return|return;
block|}
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
if|if
condition|(
name|rows
index|[
name|i
index|]
operator|+
name|i
operator|-
literal|1
operator|<
name|tableRows
operator|.
name|size
argument_list|()
condition|)
block|{
name|tableRows
operator|.
name|add
argument_list|(
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|rows
index|[
name|i
index|]
operator|+
name|i
operator|-
literal|1
argument_list|)
argument_list|,
operator|new
name|TableRow
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|rowCount
operator|+=
name|rows
operator|.
name|length
expr_stmt|;
if|if
condition|(
name|rows
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|table
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
block|}
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
name|tableChanged
operator|=
literal|true
expr_stmt|;
block|}
block|}
comment|/**      * Store changes to table preferences. This method is called when the user      * clicks Ok.      *       */
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
if|if
condition|(
name|table
operator|.
name|isEditing
argument_list|()
condition|)
block|{
name|int
name|col
init|=
name|table
operator|.
name|getEditingColumn
argument_list|()
decl_stmt|;
name|int
name|row
init|=
name|table
operator|.
name|getEditingRow
argument_list|()
decl_stmt|;
name|table
operator|.
name|getCellEditor
argument_list|(
name|row
argument_list|,
name|col
argument_list|)
operator|.
name|stopCellEditing
argument_list|()
expr_stmt|;
block|}
comment|// Now we need to make sense of the contents the user has made to the
comment|// table setup table.
if|if
condition|(
name|tableChanged
condition|)
block|{
comment|// First we remove all rows with empty names.
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|tableRows
operator|.
name|size
argument_list|()
condition|)
block|{
if|if
condition|(
name|tableRows
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|.
name|name
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|tableRows
operator|.
name|removeElementAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|i
operator|++
expr_stmt|;
block|}
block|}
comment|// Then we make arrays
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[
name|tableRows
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|String
index|[]
name|formats
init|=
operator|new
name|String
index|[
name|tableRows
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|i
operator|=
literal|0
init|;
name|i
operator|<
name|tableRows
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|TableRow
name|tr
init|=
name|tableRows
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|names
index|[
name|i
index|]
operator|=
name|tr
operator|.
name|name
expr_stmt|;
name|formats
index|[
name|i
index|]
operator|=
name|tr
operator|.
name|format
expr_stmt|;
block|}
comment|// Finally, we store the new preferences.
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATER_KEY
argument_list|,
name|names
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putStringArray
argument_list|(
name|NameFormatterTab
operator|.
name|NAME_FORMATTER_VALUE
argument_list|,
name|formats
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Name formatter"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

