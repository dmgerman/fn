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

begin_class
DECL|class|TablePrefsTab
class|class
name|TablePrefsTab
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
DECL|field|_sel
specifier|private
name|Boolean
index|[]
name|_sel
decl_stmt|;
DECL|field|colorCodes
DECL|field|autoResizeMode
DECL|field|secDesc
DECL|field|terDesc
specifier|private
name|JCheckBox
name|colorCodes
decl_stmt|,
name|autoResizeMode
decl_stmt|,
name|secDesc
decl_stmt|,
name|terDesc
decl_stmt|,
DECL|field|namesAsIs
DECL|field|namesFf
DECL|field|namesFl
DECL|field|antialias
name|namesAsIs
decl_stmt|,
name|namesFf
decl_stmt|,
name|namesFl
decl_stmt|,
name|antialias
decl_stmt|;
DECL|field|gbl
specifier|private
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
specifier|private
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
specifier|private
name|JComboBox
DECL|field|secSort
name|secSort
init|=
operator|new
name|JComboBox
argument_list|(
name|GUIGlobals
operator|.
name|ALL_FIELDS
argument_list|)
decl_stmt|,
DECL|field|terSort
name|terSort
init|=
operator|new
name|JComboBox
argument_list|(
name|GUIGlobals
operator|.
name|ALL_FIELDS
argument_list|)
decl_stmt|;
DECL|field|tableFields
specifier|private
name|JTextArea
name|tableFields
init|=
operator|new
name|JTextArea
argument_list|()
decl_stmt|;
comment|//"", 80, 5);
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
specifier|private
name|int
name|rowCount
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
DECL|method|TablePrefsTab (JabRefPreferences prefs)
specifier|public
name|TablePrefsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|colorCodes
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Color codes for required and optional fields"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"tableColorCodesOn"
argument_list|)
argument_list|)
expr_stmt|;
name|antialias
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use antialiasing font"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"antialias"
argument_list|)
argument_list|)
expr_stmt|;
name|autoResizeMode
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fit table horizontally on screen"
argument_list|)
argument_list|,
operator|(
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"autoResizeMode"
argument_list|)
operator|==
name|JTable
operator|.
name|AUTO_RESIZE_ALL_COLUMNS
operator|)
argument_list|)
expr_stmt|;
name|namesAsIs
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show names unchanged"
argument_list|)
argument_list|)
expr_stmt|;
name|namesFf
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show 'Firstname Lastname'"
argument_list|)
argument_list|)
expr_stmt|;
name|namesFl
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Show 'Lastname, Firstname'"
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|namesAsIs
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|namesFf
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|namesFl
argument_list|)
expr_stmt|;
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesAsIs"
argument_list|)
condition|)
name|namesAsIs
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
block|{
if|if
condition|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"namesFf"
argument_list|)
condition|)
name|namesFf
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
else|else
name|namesFl
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|secDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"secDescending"
argument_list|)
argument_list|)
expr_stmt|;
name|terDesc
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Descending"
argument_list|)
argument_list|,
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"terDescending"
argument_list|)
argument_list|)
expr_stmt|;
name|tableFields
operator|.
name|setText
argument_list|(
name|Util
operator|.
name|stringArrayToDelimited
argument_list|(
name|_prefs
operator|.
name|getStringArray
argument_list|(
literal|"columnNames"
argument_list|)
argument_list|,
literal|";"
argument_list|)
argument_list|)
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
operator|new
name|Integer
argument_list|(
name|tr
operator|.
name|length
argument_list|)
operator|.
name|toString
argument_list|()
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
name|JLabel
name|lab
decl_stmt|;
name|JPanel
name|upper
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|sort
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|namesp
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|upper
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|sort
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|namesp
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|upper
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Table appearance"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|sort
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Sort options"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|namesp
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Format of author and editor names"
argument_list|)
argument_list|)
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
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|colorCodes
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|upper
operator|.
name|add
argument_list|(
name|colorCodes
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|autoResizeMode
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|upper
operator|.
name|add
argument_list|(
name|autoResizeMode
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|antialias
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|upper
operator|.
name|add
argument_list|(
name|antialias
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
name|upper
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|upper
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
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|namesAsIs
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|namesp
operator|.
name|add
argument_list|(
name|namesAsIs
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|namesFf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|namesp
operator|.
name|add
argument_list|(
name|namesFf
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|namesFl
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|namesp
operator|.
name|add
argument_list|(
name|namesFl
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
name|namesp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|namesp
argument_list|)
expr_stmt|;
comment|// Set the correct value for the primary sort JComboBox.
name|String
name|sec
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"secSort"
argument_list|)
decl_stmt|,
name|ter
init|=
name|prefs
operator|.
name|get
argument_list|(
literal|"terSort"
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
name|GUIGlobals
operator|.
name|ALL_FIELDS
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|sec
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|ALL_FIELDS
index|[
name|i
index|]
argument_list|)
condition|)
name|secSort
operator|.
name|setSelectedIndex
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|ter
operator|.
name|equals
argument_list|(
name|GUIGlobals
operator|.
name|ALL_FIELDS
index|[
name|i
index|]
argument_list|)
condition|)
name|terSort
operator|.
name|setSelectedIndex
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Secondary sort criterion"
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
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|sort
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|secSort
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|sort
operator|.
name|add
argument_list|(
name|secSort
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
name|secDesc
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|sort
operator|.
name|add
argument_list|(
name|secDesc
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
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
literal|"Tertiary sort criterion"
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|sort
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
comment|//con.insets = new Insets(0,5,0,0);
name|gbl
operator|.
name|setConstraints
argument_list|(
name|terSort
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|sort
operator|.
name|add
argument_list|(
name|terSort
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
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
name|terDesc
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|sort
operator|.
name|add
argument_list|(
name|terDesc
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sort
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|sort
argument_list|)
expr_stmt|;
name|JPanel
name|tabPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tabPanel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|tabPanel
argument_list|)
expr_stmt|;
name|tabPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|tabPanel
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
comment|//colSetup.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
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
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
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
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|NORTHWEST
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|tabPanel
operator|.
name|add
argument_list|(
name|sp
argument_list|)
expr_stmt|;
name|tabPanel
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createTitledBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Visible fields"
argument_list|)
argument_list|)
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
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tlb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|tabPanel
operator|.
name|add
argument_list|(
name|tlb
argument_list|)
expr_stmt|;
comment|//Component glue = Box.createHorizontalGlue();
comment|//con.weightx = 1;
comment|//con.gridwidth = GridBagConstraints.REMAINDER;
comment|//gbl.setConstraints(glue, con);
comment|//tabPanel.add(glue);
comment|//colSetup.getInputMap
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
operator|-
name|i
operator|<
name|tableRows
operator|.
name|size
argument_list|()
condition|)
name|tableRows
operator|.
name|remove
argument_list|(
name|rows
index|[
name|i
index|]
operator|-
name|i
argument_list|)
expr_stmt|;
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
DECL|method|getChoices ()
specifier|private
name|String
index|[]
name|getChoices
parameter_list|()
block|{
comment|// First we count how many checkboxes the user has selected.
name|int
name|count
init|=
literal|0
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
name|_sel
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|_sel
index|[
name|i
index|]
operator|.
name|booleanValue
argument_list|()
condition|)
name|count
operator|++
expr_stmt|;
comment|// Then we build the byte array.
name|String
index|[]
name|choices
init|=
operator|new
name|String
index|[
name|count
index|]
decl_stmt|;
name|count
operator|=
literal|0
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|_sel
operator|.
name|length
condition|;
name|i
operator|++
control|)
if|if
condition|(
name|_sel
index|[
name|i
index|]
operator|.
name|booleanValue
argument_list|()
condition|)
block|{
name|choices
index|[
name|count
index|]
operator|=
name|GUIGlobals
operator|.
name|ALL_FIELDS
index|[
name|i
index|]
expr_stmt|;
name|count
operator|++
expr_stmt|;
block|}
return|return
name|choices
return|;
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
comment|/*String[] cols = tableFields.getText().replaceAll("\\s+","") 	    .replaceAll("\\n+","").toLowerCase().split(";"); 	if (cols.length> 0) for (int i=0; i<cols.length; i++) 	    cols[i] = cols[i].trim(); 	    else cols = null;*/
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
comment|//_prefs.putStringArray("columnNames", cols);
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"tableColorCodesOn"
argument_list|,
name|colorCodes
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"namesAsIs"
argument_list|,
name|namesAsIs
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"namesFf"
argument_list|,
name|namesFf
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"antialias"
argument_list|,
name|antialias
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"autoResizeMode"
argument_list|,
name|autoResizeMode
operator|.
name|isSelected
argument_list|()
condition|?
name|JTable
operator|.
name|AUTO_RESIZE_ALL_COLUMNS
else|:
name|JTable
operator|.
name|AUTO_RESIZE_OFF
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"secDescending"
argument_list|,
name|secDesc
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"terDescending"
argument_list|,
name|terDesc
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"secSort"
argument_list|,
name|GUIGlobals
operator|.
name|ALL_FIELDS
index|[
name|secSort
operator|.
name|getSelectedIndex
argument_list|()
index|]
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"terSort"
argument_list|,
name|GUIGlobals
operator|.
name|ALL_FIELDS
index|[
name|terSort
operator|.
name|getSelectedIndex
argument_list|()
index|]
argument_list|)
expr_stmt|;
block|}
block|}
end_class

begin_comment
comment|/* 	Boolean[] sel = new Boolean[GUIGlobals.ALL_FIELDS.length]; 	boolean found; 	_choices = GUIGlobals.ALL_FIELDS; 	_sel = sel; 	String[] columns = prefs.getStringArray("columnNames"); 	for (int i=0; i<_choices.length; i++) { 	    found = false; 	    for (int j=0; j<columns.length; j++) 		if (columns[j].equals(_choices[i])) 		    found = true; 	    if (found) 		sel[i] = new Boolean(true); 	    else 		sel[i] = new Boolean(false); 	}  	TableModel tm = new AbstractTableModel() { 		public int getRowCount() { return (_choices.length-1)/2; } 		public int getColumnCount() { return 4; } 		public Object getValueAt(int row, int column) { 		    switch (column) { 		    case 0: 			return _choices[row]; 		    case 1: 			return _sel[row]; 		    case 2: 			return _choices[getRowCount()+row]; 		    case 3: 			return _sel[getRowCount()+row]; 		    } 		    return null; // Unreachable. 		} 		public Class getColumnClass(int column) { 		    if ((column == 0) || (column == 2)) return String.class; 		    else return Boolean.class; 		} 		public boolean isCellEditable(int row, int col) { 		    if ((col == 1) || (col == 3)) return true; 		    else return false; 		} 		public void setValueAt(Object aValue, int rowIndex, int columnIndex) { 		    if (columnIndex == 1) 			_sel[rowIndex] = (Boolean)aValue; 		    if (columnIndex == 3) 			_sel[getRowCount()+rowIndex] = (Boolean)aValue; 		}  	    };  	JTable table = new JTable(tm); 	table.setRowSelectionAllowed(false); 	table.setColumnSelectionAllowed(false); 	//table.getInputMap().put(GUIGlobals.exitDialog, "close"); 	//table.getActionMap().put("close", new CancelAction()); 	JPanel 	    tablePanel = new JPanel(), 	    innerTablePanel = new JPanel();  	table.setShowVerticalLines(false); 	innerTablePanel.setBorder(BorderFactory.createEtchedBorder()); 	//innerTablePanel.setBorder(BorderFactory.createLoweredBevelBorder()); 	innerTablePanel.add(table); 	tablePanel.add(innerTablePanel);   	TableColumnModel cm = table.getColumnModel(); 	cm.getColumn(0).setPreferredWidth(90); 	cm.getColumn(1).setPreferredWidth(25); 	cm.getColumn(2).setPreferredWidth(90); 	cm.getColumn(3).setPreferredWidth(25); 	*/
end_comment

end_unit

