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
name|Collections
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

begin_comment
comment|/**  * Preference Tab for XMP.  *   * Allows the user to enable and configure the XMP privacy filter.  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|XmpPrefsTab
class|class
name|XmpPrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|tableChanged
name|boolean
name|tableChanged
init|=
literal|false
decl_stmt|;
DECL|field|rowCount
name|int
name|rowCount
decl_stmt|;
DECL|field|table
specifier|final
name|JTable
name|table
decl_stmt|;
DECL|field|privacyFilterCheckBox
specifier|final
name|JCheckBox
name|privacyFilterCheckBox
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Do not write the following fields to XMP Metadata:"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|tableRows
specifier|final
name|Vector
argument_list|<
name|Object
argument_list|>
name|tableRows
init|=
operator|new
name|Vector
argument_list|<
name|Object
argument_list|>
argument_list|(
literal|10
argument_list|)
decl_stmt|;
comment|/**      * Customization of external program paths.      */
DECL|method|XmpPrefsTab ()
specifier|public
name|XmpPrefsTab
parameter_list|()
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
specifier|public
name|int
name|getRowCount
parameter_list|()
block|{
return|return
name|rowCount
return|;
block|}
specifier|public
name|int
name|getColumnCount
parameter_list|()
block|{
return|return
literal|1
return|;
block|}
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
return|return
literal|""
return|;
name|Object
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
name|rowContent
operator|==
literal|null
condition|)
return|return
literal|""
return|;
return|return
name|rowContent
return|;
block|}
specifier|public
name|String
name|getColumnName
parameter_list|(
name|int
name|col
parameter_list|)
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field to filter"
argument_list|)
return|;
block|}
specifier|public
name|Class
argument_list|<
name|?
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
if|if
condition|(
name|tableRows
operator|.
name|size
argument_list|()
operator|<=
name|row
condition|)
block|{
name|tableRows
operator|.
name|setSize
argument_list|(
name|row
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
name|tableRows
operator|.
name|setElementAt
argument_list|(
name|value
argument_list|,
name|row
argument_list|)
expr_stmt|;
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
name|tablePanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|tablePanel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|JScrollPane
name|scrollPane
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
name|scrollPane
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
name|tablePanel
operator|.
name|add
argument_list|(
name|scrollPane
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|JToolBar
name|toolbar
init|=
operator|new
name|JToolBar
argument_list|(
name|SwingConstants
operator|.
name|VERTICAL
argument_list|)
decl_stmt|;
name|toolbar
operator|.
name|setFloatable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|toolbar
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|toolbar
operator|.
name|add
argument_list|(
operator|new
name|AddRowAction
argument_list|()
argument_list|)
expr_stmt|;
name|toolbar
operator|.
name|add
argument_list|(
operator|new
name|DeleteRowAction
argument_list|()
argument_list|)
expr_stmt|;
name|tablePanel
operator|.
name|add
argument_list|(
name|toolbar
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
comment|// Build Prefs Tabs
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"XMP Export Privacy Settings"
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
name|privacyFilterCheckBox
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
name|tablePanel
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
return|return;
for|for
control|(
name|int
name|i
init|=
name|rows
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
name|rows
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
name|rows
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
name|rowCount
operator|-=
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
name|table
operator|.
name|clearSelection
argument_list|()
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
name|tableChanged
operator|=
literal|true
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
operator|<
name|tableRows
operator|.
name|size
argument_list|()
condition|)
name|tableRows
operator|.
name|add
argument_list|(
name|rows
index|[
name|i
index|]
operator|+
name|i
argument_list|,
literal|""
argument_list|)
expr_stmt|;
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
name|table
operator|.
name|clearSelection
argument_list|()
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
name|tableChanged
operator|=
literal|true
expr_stmt|;
block|}
block|}
comment|/**      * Load settings from the preferences and initialize the table.      */
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
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|XMP_PRIVACY_FILTERS
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|addAll
argument_list|(
name|tableRows
argument_list|,
name|names
argument_list|)
expr_stmt|;
name|rowCount
operator|=
name|tableRows
operator|.
name|size
argument_list|()
operator|+
literal|5
expr_stmt|;
name|privacyFilterCheckBox
operator|.
name|setSelected
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getBoolean
argument_list|(
literal|"useXmpPrivacyFilter"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Store changes to table preferences. This method is called when the user      * clicks Ok.      *       */
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
comment|// table setup table. This needs to be done either if changes were made, or
comment|// if the checkbox is checked and no field values have been stored previously:
if|if
condition|(
name|tableChanged
operator|||
operator|(
name|privacyFilterCheckBox
operator|.
name|isSelected
argument_list|()
operator|&&
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
name|JabRefPreferences
operator|.
name|XMP_PRIVACY_FILTERS
argument_list|)
operator|)
condition|)
block|{
comment|// First we remove all rows with empty names.
for|for
control|(
name|int
name|i
init|=
name|tableRows
operator|.
name|size
argument_list|()
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
name|tableRows
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|tableRows
operator|.
name|removeElementAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
comment|// Finally, we store the new preferences.
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|putStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|XMP_PRIVACY_FILTERS
argument_list|,
name|tableRows
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|tableRows
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|putBoolean
argument_list|(
literal|"useXmpPrivacyFilter"
argument_list|,
name|privacyFilterCheckBox
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
literal|"XMP metadata"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

