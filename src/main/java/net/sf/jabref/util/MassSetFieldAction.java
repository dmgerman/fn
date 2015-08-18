begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
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
name|Collection
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
name|event
operator|.
name|ChangeEvent
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
name|ChangeListener
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
name|undo
operator|.
name|NamedCompound
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
name|JTextField
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
DECL|field|cancelled
specifier|private
name|boolean
name|cancelled
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
literal|"Set/clear/rename fields"
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
name|JTextField
argument_list|()
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
literal|"Ok"
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
literal|"Rename field to:"
argument_list|)
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
name|set
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|e
parameter_list|)
block|{
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
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|clear
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|event
parameter_list|)
block|{
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
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|rename
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|e
parameter_list|)
block|{
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
expr_stmt|;
block|}
block|}
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
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:100dlu"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field name"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field name"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|field
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Include entries"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|all
argument_list|,
literal|3
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
name|selected
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"New field value"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|set
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|text
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
name|clear
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
name|rename
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|renameTo
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
name|overwrite
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
name|field
operator|.
name|getText
argument_list|()
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
name|cancelled
operator|=
literal|false
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
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
name|cancelled
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
name|basePanel
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
name|BibtexEntry
index|[]
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
name|cancelled
operator|=
literal|true
expr_stmt|;
name|prepareDialog
argument_list|(
name|entries
operator|.
name|length
operator|>
literal|0
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|diag
argument_list|,
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
if|if
condition|(
name|cancelled
condition|)
block|{
return|return;
block|}
name|Collection
argument_list|<
name|BibtexEntry
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
name|database
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
name|Arrays
operator|.
name|asList
argument_list|(
name|entries
argument_list|)
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
name|field
operator|.
name|getText
argument_list|()
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
comment|// TODO: message: can only rename a single field
block|}
else|else
block|{
name|ce
operator|.
name|addEdit
argument_list|(
name|Util
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
name|Util
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
name|undoManager
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
DECL|method|getFieldNames (String s)
specifier|private
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

