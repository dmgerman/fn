begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|*
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
name|*
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
name|*
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
name|factories
operator|.
name|*
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
name|*
import|;
end_import

begin_class
DECL|class|TableColumnsTab
class|class
name|TableColumnsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_choices
specifier|private
name|String
index|[]
name|_choices
decl_stmt|;
DECL|field|tableChanged
specifier|private
name|boolean
name|tableChanged
init|=
literal|false
decl_stmt|;
DECL|field|colSetup
specifier|private
name|JTable
name|colSetup
decl_stmt|;
DECL|field|rowCount
DECL|field|ncWidth
specifier|private
name|int
name|rowCount
init|=
operator|-
literal|1
decl_stmt|,
name|ncWidth
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|tableRows
specifier|private
name|Vector
name|tableRows
init|=
operator|new
name|Vector
argument_list|(
literal|10
argument_list|)
decl_stmt|;
DECL|field|font
DECL|field|menuFont
specifier|private
name|Font
name|font
init|=
name|GUIGlobals
operator|.
name|CURRENTFONT
decl_stmt|,
name|menuFont
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|oldMenuFontSize
specifier|private
name|int
name|oldMenuFontSize
decl_stmt|;
DECL|class|TableRow
class|class
name|TableRow
block|{
DECL|field|name
name|String
name|name
decl_stmt|;
DECL|field|length
name|int
name|length
decl_stmt|;
DECL|method|TableRow (String name)
specifier|public
name|TableRow
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|length
operator|=
name|GUIGlobals
operator|.
name|DEFAULT_FIELD_LENGTH
expr_stmt|;
block|}
DECL|method|TableRow (int length)
specifier|public
name|TableRow
parameter_list|(
name|int
name|length
parameter_list|)
block|{
name|this
operator|.
name|length
operator|=
name|length
expr_stmt|;
name|name
operator|=
literal|""
expr_stmt|;
block|}
DECL|method|TableRow (String name, int length)
specifier|public
name|TableRow
parameter_list|(
name|String
name|name
parameter_list|,
name|int
name|length
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
name|length
operator|=
name|length
expr_stmt|;
block|}
block|}
comment|/**      * Customization of external program paths.      *      * @param prefs a<code>JabRefPreferences</code> value      */
DECL|method|TableColumnsTab (JabRefPreferences prefs, JabRefFrame frame)
specifier|public
name|TableColumnsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
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
literal|2
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
operator|==
literal|0
condition|)
return|return
operator|(
name|column
operator|==
literal|0
condition|?
name|GUIGlobals
operator|.
name|NUMBER_COL
else|:
literal|""
operator|+
name|ncWidth
operator|)
return|;
name|row
operator|--
expr_stmt|;
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
name|TableRow
name|tr
init|=
operator|(
name|TableRow
operator|)
name|rowContent
decl_stmt|;
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
operator|(
operator|(
name|tr
operator|.
name|length
operator|>
literal|0
operator|)
condition|?
name|Integer
operator|.
name|toString
argument_list|(
name|tr
operator|.
name|length
argument_list|)
else|:
literal|""
operator|)
return|;
block|}
return|return
literal|null
return|;
comment|// Unreachable.
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
operator|(
name|col
operator|==
literal|0
condition|?
name|Globals
operator|.
name|lang
argument_list|(
literal|"Field name"
argument_list|)
else|:
name|Globals
operator|.
name|lang
argument_list|(
literal|"Column width"
argument_list|)
operator|)
return|;
block|}
specifier|public
name|Class
name|getColumnClass
parameter_list|(
name|int
name|column
parameter_list|)
block|{
if|if
condition|(
name|column
operator|==
literal|0
condition|)
return|return
name|String
operator|.
name|class
return|;
else|else
return|return
name|Integer
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
operator|!
operator|(
operator|(
name|row
operator|==
literal|0
operator|)
operator|&&
operator|(
name|col
operator|==
literal|0
operator|)
operator|)
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
name|tableRows
operator|.
name|add
argument_list|(
operator|new
name|TableRow
argument_list|(
literal|""
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|row
operator|==
literal|0
operator|)
operator|&&
operator|(
name|col
operator|==
literal|1
operator|)
condition|)
block|{
name|ncWidth
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|TableRow
name|rowContent
init|=
operator|(
name|TableRow
operator|)
name|tableRows
operator|.
name|elementAt
argument_list|(
name|row
operator|-
literal|1
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
if|if
condition|(
operator|(
operator|(
name|String
operator|)
name|getValueAt
argument_list|(
name|row
argument_list|,
literal|1
argument_list|)
operator|)
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|setValueAt
argument_list|(
literal|""
operator|+
name|GUIGlobals
operator|.
name|DEFAULT_FIELD_LENGTH
argument_list|,
name|row
argument_list|,
literal|1
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|value
operator|==
literal|null
condition|)
name|rowContent
operator|.
name|length
operator|=
operator|-
literal|1
expr_stmt|;
else|else
name|rowContent
operator|.
name|length
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|value
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
name|colSetup
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
name|colSetup
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
literal|80
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
comment|//, 4dlu, fill:60dlu, 4dlu, fill:pref",
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
name|JLabel
name|lab
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
name|colSetup
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
name|colSetup
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
comment|//tlb.setRollover(true);
comment|//tlb.setLayout(gbl);
name|AddRowAction
name|ara
init|=
operator|new
name|AddRowAction
argument_list|()
decl_stmt|;
name|DeleteRowAction
name|dra
init|=
operator|new
name|DeleteRowAction
argument_list|()
decl_stmt|;
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
name|ara
argument_list|)
expr_stmt|;
name|tlb
operator|.
name|add
argument_list|(
name|dra
argument_list|)
expr_stmt|;
comment|//tlb.addSeparator();
comment|//tlb.add(new UpdateWidthsAction());
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
literal|"Entry table columns"
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
comment|//	lab = new JLabel("<HTML>("+Globals.lang("this button will update the column width settings<BR>"
comment|//						+"to match the current widths in your table")+")</HTML>");
comment|//        lab = new JLabel("<HTML>("+Globals.lang("this_button_will_update") +")</HTML>") ;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|JButton
name|button
init|=
operator|new
name|JButton
argument_list|(
operator|new
name|UpdateWidthsAction
argument_list|()
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|button
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
comment|//builder.append(lab);
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
name|_prefs
operator|.
name|getStringArray
argument_list|(
literal|"columnNames"
argument_list|)
decl_stmt|,
name|lengths
init|=
name|_prefs
operator|.
name|getStringArray
argument_list|(
literal|"columnWidths"
argument_list|)
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
name|lengths
operator|.
name|length
condition|)
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
name|Integer
operator|.
name|parseInt
argument_list|(
name|lengths
index|[
name|i
index|]
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
else|else
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
name|rowCount
operator|=
name|tableRows
operator|.
name|size
argument_list|()
operator|+
literal|5
expr_stmt|;
name|ncWidth
operator|=
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"numberColWidth"
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
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|delRowIconFile
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
name|colSetup
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
name|int
name|offs
init|=
literal|0
decl_stmt|;
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
operator|(
name|rows
index|[
name|i
index|]
operator|<=
name|tableRows
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|(
name|rows
index|[
name|i
index|]
operator|!=
literal|0
operator|)
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
operator|-
literal|1
argument_list|)
expr_stmt|;
name|offs
operator|++
expr_stmt|;
block|}
block|}
name|rowCount
operator|-=
name|offs
expr_stmt|;
if|if
condition|(
name|rows
operator|.
name|length
operator|>
literal|1
condition|)
name|colSetup
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
name|colSetup
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|colSetup
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
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|addIconFile
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
name|colSetup
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
name|colSetup
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|colSetup
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
argument_list|(
name|GUIGlobals
operator|.
name|DEFAULT_FIELD_LENGTH
argument_list|)
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
name|colSetup
operator|.
name|clearSelection
argument_list|()
expr_stmt|;
name|colSetup
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|colSetup
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
DECL|class|UpdateWidthsAction
class|class
name|UpdateWidthsAction
extends|extends
name|AbstractAction
block|{
DECL|method|UpdateWidthsAction ()
specifier|public
name|UpdateWidthsAction
parameter_list|()
block|{
comment|//super(Globals.lang("Update to current column widths"));
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Update to current column widths"
argument_list|)
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|sheetIcon
argument_list|)
argument_list|)
expr_stmt|;
comment|//putValue(SHORT_DESCRIPTION, Globals.lang("Update to current column widths"));
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
name|BasePanel
name|panel
init|=
name|frame
operator|.
name|basePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
return|return;
name|TableColumnModel
name|colMod
init|=
name|panel
operator|.
name|mainTable
operator|.
name|getColumnModel
argument_list|()
decl_stmt|;
name|colSetup
operator|.
name|setValueAt
argument_list|(
literal|""
operator|+
name|colMod
operator|.
name|getColumn
argument_list|(
literal|0
argument_list|)
operator|.
name|getWidth
argument_list|()
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|colMod
operator|.
name|getColumnCount
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
try|try
block|{
name|String
name|name
init|=
name|panel
operator|.
name|mainTable
operator|.
name|getColumnName
argument_list|(
name|i
argument_list|)
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|int
name|width
init|=
name|colMod
operator|.
name|getColumn
argument_list|(
name|i
argument_list|)
operator|.
name|getWidth
argument_list|()
decl_stmt|;
comment|//Util.pr(":"+((String)colSetup.getValueAt(i-1, 0)).toLowerCase());
comment|//Util.pr("-"+name);
if|if
condition|(
operator|(
name|i
operator|<=
name|tableRows
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|(
operator|(
operator|(
name|String
operator|)
name|colSetup
operator|.
name|getValueAt
argument_list|(
name|i
argument_list|,
literal|0
argument_list|)
operator|)
operator|.
name|toLowerCase
argument_list|()
operator|)
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
name|colSetup
operator|.
name|setValueAt
argument_list|(
literal|""
operator|+
name|width
argument_list|,
name|i
argument_list|,
literal|1
argument_list|)
expr_stmt|;
else|else
block|{
comment|// Doesn't match; search for a matching col in our table
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|colSetup
operator|.
name|getRowCount
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|j
operator|<
name|tableRows
operator|.
name|size
argument_list|()
operator|)
operator|&&
operator|(
operator|(
operator|(
name|String
operator|)
name|colSetup
operator|.
name|getValueAt
argument_list|(
name|j
argument_list|,
literal|0
argument_list|)
operator|)
operator|.
name|toLowerCase
argument_list|()
operator|)
operator|.
name|equals
argument_list|(
name|name
argument_list|)
condition|)
block|{
name|colSetup
operator|.
name|setValueAt
argument_list|(
literal|""
operator|+
name|width
argument_list|,
name|j
argument_list|,
literal|1
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
name|colSetup
operator|.
name|revalidate
argument_list|()
expr_stmt|;
name|colSetup
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Store changes to table preferences. This method is called when      * the user clicks Ok.      *      */
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
if|if
condition|(
name|colSetup
operator|.
name|isEditing
argument_list|()
condition|)
block|{
name|int
name|col
init|=
name|colSetup
operator|.
name|getEditingColumn
argument_list|()
decl_stmt|,
name|row
init|=
name|colSetup
operator|.
name|getEditingRow
argument_list|()
decl_stmt|;
name|colSetup
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
comment|//_prefs.putStringArray("columnNames", getChoices());
comment|/*String[] cols = tableFields.getText().replaceAll("\\s+","")             .replaceAll("\\n+","").toLowerCase().split(";");         if (cols.length> 0) for (int i=0; i<cols.length; i++)             cols[i] = cols[i].trim();             else cols = null;*/
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
operator|(
operator|(
name|TableRow
operator|)
name|tableRows
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|name
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
else|else
name|i
operator|++
expr_stmt|;
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
decl_stmt|,
name|widths
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
name|int
index|[]
name|nWidths
init|=
operator|new
name|int
index|[
name|tableRows
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"numberColWidth"
argument_list|,
name|ncWidth
argument_list|)
expr_stmt|;
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
operator|(
name|TableRow
operator|)
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
name|nWidths
index|[
name|i
index|]
operator|=
name|tr
operator|.
name|length
expr_stmt|;
name|widths
index|[
name|i
index|]
operator|=
literal|""
operator|+
name|tr
operator|.
name|length
expr_stmt|;
comment|//Util.pr(names[i]+"   "+widths[i]);
block|}
comment|// Finally, we store the new preferences.
name|_prefs
operator|.
name|putStringArray
argument_list|(
literal|"columnNames"
argument_list|,
name|names
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putStringArray
argument_list|(
literal|"columnWidths"
argument_list|,
name|widths
argument_list|)
expr_stmt|;
block|}
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
block|}
end_class

begin_comment
comment|/*         Boolean[] sel = new Boolean[GUIGlobals.ALL_FIELDS.length];         boolean found;         _choices = GUIGlobals.ALL_FIELDS;         _sel = sel;         String[] columns = prefs.getStringArray("columnNames");         for (int i=0; i<_choices.length; i++) {             found = false;             for (int j=0; j<columns.length; j++)                 if (columns[j].equals(_choices[i]))                     found = true;             if (found)                 sel[i] = new Boolean(true);             else                 sel[i] = new Boolean(false);         }          TableModel tm = new AbstractTableModel() {                 public int getRowCount() { return (_choices.length-1)/2; }                 public int getColumnCount() { return 4; }                 public Object getValueAt(int row, int column) {                     switch (column) {                     case 0:                         return _choices[row];                     case 1:                         return _sel[row];                     case 2:                         return _choices[getRowCount()+row];                     case 3:                         return _sel[getRowCount()+row];                     }                     return null; // Unreachable.                 }                 public Class getColumnClass(int column) {                     if ((column == 0) || (column == 2)) return String.class;                     else return Boolean.class;                 }                 public boolean isCellEditable(int row, int col) {                     if ((col == 1) || (col == 3)) return true;                     else return false;                 }                 public void setValueAt(Object aValue, int rowIndex, int columnIndex) {                     if (columnIndex == 1)                         _sel[rowIndex] = (Boolean)aValue;                     if (columnIndex == 3)                         _sel[getRowCount()+rowIndex] = (Boolean)aValue;                 }              };          JTable table = new JTable(tm);         table.setRowSelectionAllowed(false);         table.setColumnSelectionAllowed(false);         //table.getInputMap().put(GUIGlobals.exitDialog, "close");         //table.getActionMap().put("close", new CancelAction());         JPanel             tablePanel = new JPanel(),             innerTablePanel = new JPanel();          table.setShowVerticalLines(false);         innerTablePanel.setBorder(BorderFactory.createEtchedBorder());         //innerTablePanel.setBorder(BorderFactory.createLoweredBevelBorder());         innerTablePanel.add(table);         tablePanel.add(innerTablePanel);           TableColumnModel cm = table.getColumnModel();         cm.getColumn(0).setPreferredWidth(90);         cm.getColumn(1).setPreferredWidth(25);         cm.getColumn(2).setPreferredWidth(90);         cm.getColumn(3).setPreferredWidth(25);         */
end_comment

end_unit

