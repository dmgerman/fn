begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.actions
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
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
name|util
operator|.
name|Collection
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
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|JOptionPane
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
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoableEdit
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableFieldChange
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
name|FormBuilder
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
comment|/**  * An Action for launching mass field.  *  * Functionality:  * * Defaults to selected entries, or all entries if none are selected.  * * Input field name  * * Either set field, or clear field.  */
end_comment

begin_class
DECL|class|MassSetFieldAction
specifier|public
class|class
name|MassSetFieldAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|diag
specifier|private
name|JDialog
name|diag
decl_stmt|;
DECL|field|all
specifier|private
name|JRadioButton
name|all
decl_stmt|;
DECL|field|selected
specifier|private
name|JRadioButton
name|selected
decl_stmt|;
DECL|field|clear
specifier|private
name|JRadioButton
name|clear
decl_stmt|;
DECL|field|set
specifier|private
name|JRadioButton
name|set
decl_stmt|;
DECL|field|rename
specifier|private
name|JRadioButton
name|rename
decl_stmt|;
DECL|field|field
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|field
decl_stmt|;
DECL|field|text
specifier|private
name|JTextField
name|text
decl_stmt|;
DECL|field|renameTo
specifier|private
name|JTextField
name|renameTo
decl_stmt|;
DECL|field|canceled
specifier|private
name|boolean
name|canceled
init|=
literal|true
decl_stmt|;
DECL|field|overwrite
specifier|private
name|JCheckBox
name|overwrite
decl_stmt|;
DECL|method|MassSetFieldAction (JabRefFrame frame)
specifier|public
name|MassSetFieldAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Set/clear/rename fields"
argument_list|)
operator|+
literal|"..."
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
DECL|method|createDialog ()
specifier|private
name|void
name|createDialog
parameter_list|()
block|{
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set/clear/rename fields"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|field
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|()
expr_stmt|;
name|field
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|text
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|text
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|renameTo
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|renameTo
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
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
name|all
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"All entries"
argument_list|)
argument_list|)
expr_stmt|;
name|selected
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Selected entries"
argument_list|)
argument_list|)
expr_stmt|;
name|clear
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear fields"
argument_list|)
argument_list|)
expr_stmt|;
name|set
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set fields"
argument_list|)
argument_list|)
expr_stmt|;
name|rename
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename field to"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|rename
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move contents of a field into a field with a different name"
argument_list|)
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|allFields
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
operator|.
name|getAllVisibleFields
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|f
range|:
name|allFields
control|)
block|{
name|field
operator|.
name|addItem
argument_list|(
name|f
argument_list|)
expr_stmt|;
block|}
name|set
operator|.
name|addChangeListener
argument_list|(
name|e
lambda|->
comment|// Entering a text is only relevant if we are setting, not clearing:
name|text
operator|.
name|setEnabled
argument_list|(
name|set
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|clear
operator|.
name|addChangeListener
argument_list|(
name|e
lambda|->
comment|// Overwrite protection makes no sense if we are clearing the field:
name|overwrite
operator|.
name|setEnabled
argument_list|(
operator|!
name|clear
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|rename
operator|.
name|addChangeListener
argument_list|(
name|e
lambda|->
comment|// Entering a text is only relevant if we are renaming
name|renameTo
operator|.
name|setEnabled
argument_list|(
name|rename
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|overwrite
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Overwrite existing field values"
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
name|all
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|bg
operator|=
operator|new
name|ButtonGroup
argument_list|()
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|clear
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|set
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|rename
argument_list|)
expr_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:100dlu:grow"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref, 2dlu, pref"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field name"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field name"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|field
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Include entries"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|all
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|7
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|selected
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|9
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|addSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"New field value"
argument_list|)
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|11
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|set
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|text
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|13
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|clear
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|15
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|rename
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|17
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|renameTo
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|17
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|overwrite
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|19
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
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
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|builder
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
name|bb
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
name|builder
operator|.
name|getPanel
argument_list|()
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
name|bb
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
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
comment|// Check that any field name is set
name|String
name|fieldText
init|=
operator|(
name|String
operator|)
name|field
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|fieldText
operator|==
literal|null
operator|)
operator|||
name|fieldText
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|diag
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must enter at least one field name"
argument_list|)
argument_list|,
literal|""
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
comment|// Do not close the dialog.
block|}
comment|// Check if the user tries to rename multiple fields:
if|if
condition|(
name|rename
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|String
index|[]
name|fields
init|=
name|getFieldNames
argument_list|(
name|fieldText
argument_list|)
decl_stmt|;
if|if
condition|(
name|fields
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|diag
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You can only rename one field at a time"
argument_list|)
argument_list|,
literal|""
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
comment|// Do not close the dialog.
block|}
block|}
name|canceled
operator|=
literal|false
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|Action
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
name|canceled
operator|=
literal|true
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|builder
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
name|builder
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
block|}
DECL|method|prepareDialog (boolean selection)
specifier|private
name|void
name|prepareDialog
parameter_list|(
name|boolean
name|selection
parameter_list|)
block|{
name|selected
operator|.
name|setEnabled
argument_list|(
name|selection
argument_list|)
expr_stmt|;
if|if
condition|(
name|selection
condition|)
block|{
name|selected
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|all
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// Make sure one of the following ones is selected:
if|if
condition|(
operator|!
name|set
operator|.
name|isSelected
argument_list|()
operator|&&
operator|!
name|clear
operator|.
name|isSelected
argument_list|()
operator|&&
operator|!
name|rename
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|set
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
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
name|BasePanel
name|bp
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|bp
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|bp
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
comment|// Lazy creation of the dialog:
if|if
condition|(
name|diag
operator|==
literal|null
condition|)
block|{
name|createDialog
argument_list|()
expr_stmt|;
block|}
name|canceled
operator|=
literal|true
expr_stmt|;
name|prepareDialog
argument_list|(
operator|!
name|entries
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|diag
operator|!=
literal|null
condition|)
block|{
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|canceled
condition|)
block|{
return|return;
block|}
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entryList
decl_stmt|;
comment|// If all entries should be treated, change the entries array:
if|if
condition|(
name|all
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|entryList
operator|=
name|bp
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|entryList
operator|=
name|entries
expr_stmt|;
block|}
name|String
name|toSet
init|=
name|text
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
name|toSet
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|toSet
operator|=
literal|null
expr_stmt|;
block|}
name|String
index|[]
name|fields
init|=
name|getFieldNames
argument_list|(
operator|(
operator|(
name|String
operator|)
name|field
operator|.
name|getSelectedItem
argument_list|()
operator|)
operator|.
name|trim
argument_list|()
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set field"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|rename
operator|.
name|isSelected
argument_list|()
condition|)
block|{
if|if
condition|(
name|fields
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|diag
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You can only rename one field at a time"
argument_list|)
argument_list|,
literal|""
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
comment|// Do not close the dialog.
block|}
else|else
block|{
name|ce
operator|.
name|addEdit
argument_list|(
name|MassSetFieldAction
operator|.
name|massRenameField
argument_list|(
name|entryList
argument_list|,
name|fields
index|[
literal|0
index|]
argument_list|,
name|renameTo
operator|.
name|getText
argument_list|()
argument_list|,
name|overwrite
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
for|for
control|(
name|String
name|field1
range|:
name|fields
control|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
name|MassSetFieldAction
operator|.
name|massSetField
argument_list|(
name|entryList
argument_list|,
name|field1
argument_list|,
name|set
operator|.
name|isSelected
argument_list|()
condition|?
name|toSet
else|:
literal|null
argument_list|,
name|overwrite
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|bp
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|bp
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
comment|/**      * Set a given field to a given value for all entries in a Collection. This method DOES NOT update any UndoManager,      * but returns a relevant CompoundEdit that should be registered by the caller.      *      * @param entries         The entries to set the field for.      * @param field           The name of the field to set.      * @param text            The value to set. This value can be null, indicating that the field should be cleared.      * @param overwriteValues Indicate whether the value should be set even if an entry already has the field set.      * @return A CompoundEdit for the entire operation.      */
DECL|method|massSetField (Collection<BibEntry> entries, String field, String text, boolean overwriteValues)
specifier|private
specifier|static
name|UndoableEdit
name|massSetField
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|text
parameter_list|,
name|boolean
name|overwriteValues
parameter_list|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set field"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|oldVal
init|=
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|// If we are not allowed to overwrite values, check if there is a
comment|// nonempty
comment|// value already for this entry:
if|if
condition|(
operator|!
name|overwriteValues
operator|&&
operator|(
name|oldVal
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
operator|!
name|oldVal
operator|.
name|get
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|text
operator|==
literal|null
condition|)
block|{
name|entry
operator|.
name|clearField
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|oldVal
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|text
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
return|return
name|ce
return|;
block|}
comment|/**      * Move contents from one field to another for a Collection of entries.      *      * @param entries         The entries to do this operation for.      * @param field           The field to move contents from.      * @param newField        The field to move contents into.      * @param overwriteValues If true, overwrites any existing values in the new field. If false, makes no change for      *                        entries with existing value in the new field.      * @return A CompoundEdit for the entire operation.      */
DECL|method|massRenameField (Collection<BibEntry> entries, String field, String newField, boolean overwriteValues)
specifier|private
specifier|static
name|UndoableEdit
name|massRenameField
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|String
name|field
parameter_list|,
name|String
name|newField
parameter_list|,
name|boolean
name|overwriteValues
parameter_list|)
block|{
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename field"
argument_list|)
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|valToMove
init|=
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
decl_stmt|;
comment|// If there is no value, do nothing:
if|if
condition|(
operator|(
operator|!
name|valToMove
operator|.
name|isPresent
argument_list|()
operator|)
operator|||
name|valToMove
operator|.
name|get
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
comment|// If we are not allowed to overwrite values, check if there is a
comment|// non-empty value already for this entry for the new field:
name|Optional
argument_list|<
name|String
argument_list|>
name|valInNewField
init|=
name|entry
operator|.
name|getField
argument_list|(
name|newField
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|overwriteValues
operator|&&
operator|(
name|valInNewField
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
operator|!
name|valInNewField
operator|.
name|get
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
continue|continue;
block|}
name|entry
operator|.
name|setField
argument_list|(
name|newField
argument_list|,
name|valToMove
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|newField
argument_list|,
name|valInNewField
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|valToMove
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|clearField
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|field
argument_list|,
name|valToMove
operator|.
name|get
argument_list|()
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
return|return
name|ce
return|;
block|}
DECL|method|getFieldNames (String s)
specifier|private
specifier|static
name|String
index|[]
name|getFieldNames
parameter_list|(
name|String
name|s
parameter_list|)
block|{
return|return
name|s
operator|.
name|split
argument_list|(
literal|"[\\s;,]"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

