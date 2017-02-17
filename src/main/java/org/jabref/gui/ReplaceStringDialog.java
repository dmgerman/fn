begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
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
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Insets
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
name|ButtonGroup
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
name|JCheckBox
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
name|JLabel
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
name|JRadioButton
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
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
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

begin_comment
comment|/**  * Dialog for replacing strings.  */
end_comment

begin_class
DECL|class|ReplaceStringDialog
class|class
name|ReplaceStringDialog
extends|extends
name|JDialog
block|{
DECL|field|fieldsField
specifier|private
specifier|final
name|JTextField
name|fieldsField
init|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
literal|30
argument_list|)
decl_stmt|;
DECL|field|fromField
specifier|private
specifier|final
name|JTextField
name|fromField
init|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
literal|30
argument_list|)
decl_stmt|;
DECL|field|toField
specifier|private
specifier|final
name|JTextField
name|toField
init|=
operator|new
name|JTextField
argument_list|(
literal|""
argument_list|,
literal|30
argument_list|)
decl_stmt|;
DECL|field|selOnly
specifier|private
specifier|final
name|JCheckBox
name|selOnly
init|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Limit to selected entries"
argument_list|)
argument_list|,
literal|false
argument_list|)
decl_stmt|;
DECL|field|allFi
specifier|private
specifier|final
name|JRadioButton
name|allFi
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"All fields"
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
DECL|field|field
specifier|private
specifier|final
name|JRadioButton
name|field
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Limit to fields"
argument_list|)
operator|+
literal|":"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
decl_stmt|;
DECL|field|fieldStrings
specifier|private
name|String
index|[]
name|fieldStrings
decl_stmt|;
DECL|field|fromString
specifier|private
name|String
name|fromString
decl_stmt|;
DECL|field|toString
specifier|private
name|String
name|toString
decl_stmt|;
DECL|method|ReplaceStringDialog (JabRefFrame parent)
specifier|public
name|ReplaceStringDialog
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Replace string"
argument_list|)
argument_list|,
literal|true
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
name|allFi
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|ActionListener
name|okListener
init|=
name|e
lambda|->
block|{
name|fromString
operator|=
name|fromField
operator|.
name|getText
argument_list|()
expr_stmt|;
name|toString
operator|=
name|toField
operator|.
name|getText
argument_list|()
expr_stmt|;
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|fromString
argument_list|)
condition|)
block|{
return|return;
block|}
name|okPressed
operator|=
literal|true
expr_stmt|;
name|fieldStrings
operator|=
name|fieldsField
operator|.
name|getText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
decl_stmt|;
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|toField
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|fieldsField
operator|.
name|addActionListener
argument_list|(
name|okListener
argument_list|)
expr_stmt|;
name|AbstractAction
name|cancelAction
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
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|JPanel
name|settings
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|ActionMap
name|am
init|=
name|settings
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|settings
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
name|cancelAction
argument_list|)
expr_stmt|;
comment|// Layout starts here.
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|settings
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|JPanel
name|opt
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|opt
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|settings
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Replace string"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|main
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Strings"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// Settings panel:
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridx
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
literal|3
argument_list|,
literal|5
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|selOnly
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|selOnly
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
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
literal|13
argument_list|,
literal|5
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|allFi
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|allFi
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
name|gridy
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|gridx
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
literal|3
argument_list|,
literal|5
argument_list|,
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|field
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
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
name|fieldsField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|settings
operator|.
name|add
argument_list|(
name|fieldsField
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
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|JLabel
name|fl
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search for"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|fl
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|fl
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|JLabel
name|tl
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Replace with"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tl
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|tl
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
name|gridx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|fromField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|fromField
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|toField
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|toField
argument_list|)
expr_stmt|;
comment|// Option buttons:
name|con
operator|.
name|gridx
operator|=
name|GridBagConstraints
operator|.
name|RELATIVE
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
name|GridBagConstraints
operator|.
name|RELATIVE
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
literal|1
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|ok
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
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
name|cancel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|opt
operator|.
name|add
argument_list|(
name|cancel
argument_list|)
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
name|NORTH
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|settings
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
name|opt
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|this
operator|.
name|setLocationRelativeTo
argument_list|(
name|parent
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
DECL|method|allFields ()
specifier|private
name|boolean
name|allFields
parameter_list|()
block|{
return|return
name|allFi
operator|.
name|isSelected
argument_list|()
return|;
block|}
DECL|method|selOnly ()
specifier|public
name|boolean
name|selOnly
parameter_list|()
block|{
return|return
name|selOnly
operator|.
name|isSelected
argument_list|()
return|;
block|}
comment|/**      * Does the actual operation on a Bibtex entry based on the      * settings specified in this same dialog. Returns the number of      * occurences replaced.      */
DECL|method|replace (BibEntry be, NamedCompound ce)
specifier|public
name|int
name|replace
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
name|int
name|counter
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|allFields
argument_list|()
condition|)
block|{
for|for
control|(
name|String
name|s
range|:
name|be
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|equals
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
condition|)
block|{
name|counter
operator|+=
name|replaceField
argument_list|(
name|be
argument_list|,
name|s
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
for|for
control|(
name|String
name|fld
range|:
name|fieldStrings
control|)
block|{
if|if
condition|(
operator|!
name|fld
operator|.
name|equals
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
condition|)
block|{
name|counter
operator|+=
name|replaceField
argument_list|(
name|be
argument_list|,
name|fld
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
block|}
return|return
name|counter
return|;
block|}
DECL|method|replaceField (BibEntry be, String fieldname, NamedCompound ce)
specifier|private
name|int
name|replaceField
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|String
name|fieldname
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
if|if
condition|(
operator|!
name|be
operator|.
name|hasField
argument_list|(
name|fieldname
argument_list|)
condition|)
block|{
return|return
literal|0
return|;
block|}
name|String
name|txt
init|=
name|be
operator|.
name|getField
argument_list|(
name|fieldname
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|ind
decl_stmt|;
name|int
name|piv
init|=
literal|0
decl_stmt|;
name|int
name|counter
init|=
literal|0
decl_stmt|;
name|int
name|len1
init|=
name|fromString
operator|.
name|length
argument_list|()
decl_stmt|;
while|while
condition|(
operator|(
name|ind
operator|=
name|txt
operator|.
name|indexOf
argument_list|(
name|fromString
argument_list|,
name|piv
argument_list|)
operator|)
operator|>=
literal|0
condition|)
block|{
name|counter
operator|++
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|txt
operator|.
name|substring
argument_list|(
name|piv
argument_list|,
name|ind
argument_list|)
argument_list|)
expr_stmt|;
comment|// Text leading up to s1
name|sb
operator|.
name|append
argument_list|(
name|toString
argument_list|)
expr_stmt|;
comment|// Insert s2
name|piv
operator|=
name|ind
operator|+
name|len1
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|txt
operator|.
name|substring
argument_list|(
name|piv
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|newStr
init|=
name|sb
operator|.
name|toString
argument_list|()
decl_stmt|;
name|be
operator|.
name|setField
argument_list|(
name|fieldname
argument_list|,
name|newStr
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|be
argument_list|,
name|fieldname
argument_list|,
name|txt
argument_list|,
name|newStr
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|counter
return|;
block|}
block|}
end_class

end_unit
