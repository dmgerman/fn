begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
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
name|GridBagConstraints
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
name|List
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
name|JOptionPane
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
name|model
operator|.
name|BibtexEntryType
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
name|model
operator|.
name|CustomEntryType
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|Util
import|;
end_import

begin_comment
comment|/**  * This class extends FieldSetComponent to provide some required functionality for the  * list of entry types in EntryCustomizationDialog2.  * @author alver  */
end_comment

begin_class
DECL|class|EntryTypeList
specifier|public
class|class
name|EntryTypeList
extends|extends
name|FieldSetComponent
implements|implements
name|ListSelectionListener
block|{
DECL|field|def
specifier|private
specifier|final
name|JButton
name|def
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
comment|/** Creates a new instance of EntryTypeList */
DECL|method|EntryTypeList (List<String> fields)
specifier|public
name|EntryTypeList
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry types"
argument_list|)
argument_list|,
name|fields
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|)
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
literal|2
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|VERTICAL
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|EAST
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|def
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|def
argument_list|)
expr_stmt|;
name|list
operator|.
name|addListSelectionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|def
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|def
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|addField (String s)
specifier|protected
name|void
name|addField
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|s
operator|=
name|s
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|forceLowerCase
condition|)
block|{
name|s
operator|=
name|s
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|s
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|||
name|listModel
operator|.
name|contains
argument_list|(
name|s
argument_list|)
condition|)
block|{
return|return;
block|}
name|String
name|testString
init|=
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|s
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|testString
operator|.
name|equals
argument_list|(
name|s
argument_list|)
operator|||
operator|(
name|s
operator|.
name|indexOf
argument_list|(
literal|'&'
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
comment|// Report error and exit.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entry type names are not allowed to contain white space or the following "
operator|+
literal|"characters"
argument_list|)
operator|+
literal|": # { } ~ , ^&"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
elseif|else
if|if
condition|(
name|s
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"comment"
argument_list|)
condition|)
block|{
comment|// Report error and exit.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"The name 'comment' can not be used as an entry type name."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|addFieldUncritically
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|removeSelected ()
specifier|protected
name|void
name|removeSelected
parameter_list|()
block|{
comment|//super.removeSelected();
name|int
index|[]
name|selected
init|=
name|list
operator|.
name|getSelectedIndices
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|changesMade
operator|=
literal|true
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
name|selected
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
name|typeName
init|=
operator|(
name|String
operator|)
name|listModel
operator|.
name|get
argument_list|(
name|selected
index|[
name|selected
operator|.
name|length
operator|-
literal|1
operator|-
name|i
index|]
argument_list|)
decl_stmt|;
name|BibtexEntryType
name|type
init|=
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|typeName
argument_list|)
decl_stmt|;
comment|// If it is a custom entry type, we can remove it. If type == null, it means
comment|// the user must have added it and not yet applied it, so we can remove it
comment|// in this case as well. If it is a standard type it cannot be removed.
if|if
condition|(
operator|(
name|type
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|type
operator|instanceof
name|CustomEntryType
operator|)
condition|)
block|{
name|listModel
operator|.
name|removeElementAt
argument_list|(
name|selected
index|[
name|selected
operator|.
name|length
operator|-
literal|1
operator|-
name|i
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// This shouldn't happen, since the Remove button should be disabled.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"This entry type cannot be removed."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove entry type"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|valueChanged (ListSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{      }
DECL|method|enable (String typeName, boolean isChanged)
specifier|public
name|void
name|enable
parameter_list|(
name|String
name|typeName
parameter_list|,
name|boolean
name|isChanged
parameter_list|)
block|{
comment|//String s = (String)list.getSelectedValue();
if|if
condition|(
name|BibtexEntryType
operator|.
name|getStandardType
argument_list|(
name|typeName
argument_list|)
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|isChanged
operator|||
operator|(
name|BibtexEntryType
operator|.
name|getType
argument_list|(
name|typeName
argument_list|)
operator|instanceof
name|CustomEntryType
operator|)
condition|)
block|{
name|def
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|def
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|remove
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|def
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addDefaultActionListener (ActionListener l)
specifier|public
name|void
name|addDefaultActionListener
parameter_list|(
name|ActionListener
name|l
parameter_list|)
block|{
name|def
operator|.
name|addActionListener
argument_list|(
name|l
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
comment|// Default button pressed.
if|if
condition|(
name|e
operator|.
name|getSource
argument_list|()
operator|==
name|def
condition|)
block|{
name|def
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|super
operator|.
name|actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|setEnabled (boolean en)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|en
parameter_list|)
block|{
name|super
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
name|def
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

