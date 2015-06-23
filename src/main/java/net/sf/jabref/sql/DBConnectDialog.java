begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.sql
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|sql
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
name|JFrame
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
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPasswordField
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

begin_comment
comment|/**  * Dialog box for collecting database connection strings from the user  *  * @author pattonlk  */
end_comment

begin_class
DECL|class|DBConnectDialog
specifier|public
class|class
name|DBConnectDialog
extends|extends
name|JDialog
block|{
comment|// labels
DECL|field|lblServerType
specifier|final
name|JLabel
name|lblServerType
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|lblServerHostname
specifier|final
name|JLabel
name|lblServerHostname
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|lblDatabase
specifier|final
name|JLabel
name|lblDatabase
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|lblUsername
specifier|final
name|JLabel
name|lblUsername
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|lblPassword
specifier|final
name|JLabel
name|lblPassword
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
comment|// input fields
DECL|field|cmbServerType
specifier|final
name|JComboBox
name|cmbServerType
init|=
operator|new
name|JComboBox
argument_list|()
decl_stmt|;
DECL|field|txtServerHostname
specifier|final
name|JTextField
name|txtServerHostname
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|txtDatabase
specifier|final
name|JTextField
name|txtDatabase
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|txtUsername
specifier|final
name|JTextField
name|txtUsername
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|pwdPassword
specifier|final
name|JPasswordField
name|pwdPassword
init|=
operator|new
name|JPasswordField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|btnConnect
specifier|final
name|JButton
name|btnConnect
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|btnCancel
specifier|final
name|JButton
name|btnCancel
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
comment|// array for holding components on left-hand and right-hand sides
DECL|field|lhs
specifier|final
name|ArrayList
argument_list|<
name|JLabel
argument_list|>
name|lhs
init|=
operator|new
name|ArrayList
argument_list|<
name|JLabel
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|rhs
specifier|final
name|ArrayList
argument_list|<
name|JComponent
argument_list|>
name|rhs
init|=
operator|new
name|ArrayList
argument_list|<
name|JComponent
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|dbStrings
name|DBStrings
name|dbStrings
init|=
operator|new
name|DBStrings
argument_list|()
decl_stmt|;
DECL|field|connectToDB
specifier|private
name|boolean
name|connectToDB
init|=
literal|false
decl_stmt|;
comment|/** Creates a new instance of DBConnectDialog */
DECL|method|DBConnectDialog (JFrame parent, DBStrings dbs)
specifier|public
name|DBConnectDialog
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|DBStrings
name|dbs
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
literal|"Connect to SQL database"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|this
operator|.
name|setLocationRelativeTo
argument_list|(
name|parent
argument_list|)
expr_stmt|;
name|dbStrings
operator|=
name|dbs
expr_stmt|;
comment|// build collections of components
name|lhs
operator|.
name|add
argument_list|(
name|lblServerType
argument_list|)
expr_stmt|;
name|lhs
operator|.
name|add
argument_list|(
name|lblServerHostname
argument_list|)
expr_stmt|;
name|lhs
operator|.
name|add
argument_list|(
name|lblDatabase
argument_list|)
expr_stmt|;
name|lhs
operator|.
name|add
argument_list|(
name|lblUsername
argument_list|)
expr_stmt|;
name|lhs
operator|.
name|add
argument_list|(
name|lblPassword
argument_list|)
expr_stmt|;
name|rhs
operator|.
name|add
argument_list|(
name|cmbServerType
argument_list|)
expr_stmt|;
name|rhs
operator|.
name|add
argument_list|(
name|txtServerHostname
argument_list|)
expr_stmt|;
name|rhs
operator|.
name|add
argument_list|(
name|txtDatabase
argument_list|)
expr_stmt|;
name|rhs
operator|.
name|add
argument_list|(
name|txtUsername
argument_list|)
expr_stmt|;
name|rhs
operator|.
name|add
argument_list|(
name|pwdPassword
argument_list|)
expr_stmt|;
comment|// setup label text
name|lblServerType
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Server Type :"
argument_list|)
argument_list|)
expr_stmt|;
name|lblServerHostname
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Server Hostname :"
argument_list|)
argument_list|)
expr_stmt|;
name|lblDatabase
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Database :"
argument_list|)
argument_list|)
expr_stmt|;
name|lblUsername
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Username :"
argument_list|)
argument_list|)
expr_stmt|;
name|lblPassword
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Password :"
argument_list|)
argument_list|)
expr_stmt|;
comment|// set label text alignment
for|for
control|(
name|JLabel
name|label
range|:
name|lhs
control|)
block|{
name|label
operator|.
name|setHorizontalAlignment
argument_list|(
name|JLabel
operator|.
name|RIGHT
argument_list|)
expr_stmt|;
block|}
comment|// set button text
name|btnConnect
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Connect"
argument_list|)
argument_list|)
expr_stmt|;
name|btnCancel
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
expr_stmt|;
comment|// init input fields to current DB strings
name|String
name|srvSel
init|=
name|dbStrings
operator|.
name|getServerType
argument_list|()
decl_stmt|;
name|String
index|[]
name|srv
init|=
name|dbStrings
operator|.
name|getServerTypes
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|aSrv
range|:
name|srv
control|)
block|{
name|cmbServerType
operator|.
name|addItem
argument_list|(
name|aSrv
argument_list|)
expr_stmt|;
block|}
name|cmbServerType
operator|.
name|setSelectedItem
argument_list|(
name|srvSel
argument_list|)
expr_stmt|;
name|txtServerHostname
operator|.
name|setText
argument_list|(
name|dbStrings
operator|.
name|getServerHostname
argument_list|()
argument_list|)
expr_stmt|;
name|txtDatabase
operator|.
name|setText
argument_list|(
name|dbStrings
operator|.
name|getDatabase
argument_list|()
argument_list|)
expr_stmt|;
name|txtUsername
operator|.
name|setText
argument_list|(
name|dbStrings
operator|.
name|getUsername
argument_list|()
argument_list|)
expr_stmt|;
name|pwdPassword
operator|.
name|setText
argument_list|(
name|dbStrings
operator|.
name|getPassword
argument_list|()
argument_list|)
expr_stmt|;
comment|// construct dialog
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"right:pref, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
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
comment|// add labels and input fields
name|builder
operator|.
name|append
argument_list|(
name|lblServerType
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|cmbServerType
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
name|lblServerHostname
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|txtServerHostname
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
name|lblDatabase
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|txtDatabase
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
name|lblUsername
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|txtUsername
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
name|lblPassword
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pwdPassword
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
comment|// add the panel to the CENTER of your dialog:
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
comment|// add buttons are added in a similar way:
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
name|btnConnect
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|btnCancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
comment|// add the buttons to the SOUTH of your dialog:
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
name|pack
argument_list|()
expr_stmt|;
name|ActionListener
name|connectAction
init|=
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
name|errorMessage
init|=
name|checkInput
argument_list|()
decl_stmt|;
if|if
condition|(
name|errorMessage
operator|==
literal|null
condition|)
block|{
name|storeSettings
argument_list|()
expr_stmt|;
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setConnectToDB
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|errorMessage
argument_list|,
literal|"Input Error"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
decl_stmt|;
name|btnConnect
operator|.
name|addActionListener
argument_list|(
name|connectAction
argument_list|)
expr_stmt|;
name|txtDatabase
operator|.
name|addActionListener
argument_list|(
name|connectAction
argument_list|)
expr_stmt|;
name|txtServerHostname
operator|.
name|addActionListener
argument_list|(
name|connectAction
argument_list|)
expr_stmt|;
name|txtUsername
operator|.
name|addActionListener
argument_list|(
name|connectAction
argument_list|)
expr_stmt|;
name|pwdPassword
operator|.
name|addActionListener
argument_list|(
name|connectAction
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
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
name|setConnectToDB
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|btnCancel
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
comment|/**      * Checks the user input, and ensures that required fields have entries      *      * @return       *      Appropriate error message to be displayed to user      */
DECL|method|checkInput ()
specifier|private
name|String
name|checkInput
parameter_list|()
block|{
name|String
index|[]
name|fields
init|=
block|{
literal|"Server Hostname"
block|,
literal|"Database"
block|,
literal|"Username"
block|}
decl_stmt|;
name|String
index|[]
name|errors
init|=
operator|new
name|String
index|[
name|fields
operator|.
name|length
index|]
decl_stmt|;
name|int
name|cnt
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|txtServerHostname
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
index|[
name|cnt
index|]
operator|=
name|fields
index|[
literal|0
index|]
expr_stmt|;
name|cnt
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|txtDatabase
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
index|[
name|cnt
index|]
operator|=
name|fields
index|[
literal|1
index|]
expr_stmt|;
name|cnt
operator|++
expr_stmt|;
block|}
if|if
condition|(
name|txtUsername
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|errors
index|[
name|cnt
index|]
operator|=
name|fields
index|[
literal|2
index|]
expr_stmt|;
name|cnt
operator|++
expr_stmt|;
block|}
name|String
name|errMsg
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Please specify the "
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|cnt
condition|)
block|{
case|case
literal|0
case|:
name|errMsg
operator|=
literal|null
expr_stmt|;
break|break;
case|case
literal|1
case|:
name|errMsg
operator|=
name|errMsg
operator|+
name|errors
index|[
literal|0
index|]
operator|+
literal|"."
expr_stmt|;
break|break;
case|case
literal|2
case|:
name|errMsg
operator|=
name|errMsg
operator|+
name|errors
index|[
literal|0
index|]
operator|+
literal|" and "
operator|+
name|errors
index|[
literal|1
index|]
operator|+
literal|"."
expr_stmt|;
break|break;
case|case
literal|3
case|:
name|errMsg
operator|=
name|errMsg
operator|+
name|errors
index|[
literal|0
index|]
operator|+
literal|", "
operator|+
name|errors
index|[
literal|1
index|]
operator|+
literal|", and "
operator|+
name|errors
index|[
literal|2
index|]
operator|+
literal|"."
expr_stmt|;
break|break;
default|default:
block|}
return|return
name|errMsg
return|;
block|}
comment|/**      * Save user input.      */
DECL|method|storeSettings ()
specifier|private
name|void
name|storeSettings
parameter_list|()
block|{
name|dbStrings
operator|.
name|setServerType
argument_list|(
name|cmbServerType
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|dbStrings
operator|.
name|setServerHostname
argument_list|(
name|txtServerHostname
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|dbStrings
operator|.
name|setDatabase
argument_list|(
name|txtDatabase
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|dbStrings
operator|.
name|setUsername
argument_list|(
name|txtUsername
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
comment|// Store these settings so they appear as default next time:
name|dbStrings
operator|.
name|storeToPreferences
argument_list|()
expr_stmt|;
name|char
index|[]
name|pwd
init|=
name|pwdPassword
operator|.
name|getPassword
argument_list|()
decl_stmt|;
name|String
name|tmp
init|=
literal|""
decl_stmt|;
for|for
control|(
name|char
name|aPwd
range|:
name|pwd
control|)
block|{
name|tmp
operator|=
name|tmp
operator|+
name|aPwd
expr_stmt|;
block|}
name|dbStrings
operator|.
name|setPassword
argument_list|(
name|tmp
argument_list|)
expr_stmt|;
name|Arrays
operator|.
name|fill
argument_list|(
name|pwd
argument_list|,
literal|'0'
argument_list|)
expr_stmt|;
block|}
DECL|method|getDBStrings ()
specifier|public
name|DBStrings
name|getDBStrings
parameter_list|()
block|{
return|return
name|dbStrings
return|;
block|}
DECL|method|setDBStrings (DBStrings dbStrings)
specifier|public
name|void
name|setDBStrings
parameter_list|(
name|DBStrings
name|dbStrings
parameter_list|)
block|{
name|this
operator|.
name|dbStrings
operator|=
name|dbStrings
expr_stmt|;
block|}
DECL|method|getConnectToDB ()
specifier|public
name|boolean
name|getConnectToDB
parameter_list|()
block|{
return|return
name|connectToDB
return|;
block|}
DECL|method|setConnectToDB (boolean connectToDB)
specifier|public
name|void
name|setConnectToDB
parameter_list|(
name|boolean
name|connectToDB
parameter_list|)
block|{
name|this
operator|.
name|connectToDB
operator|=
name|connectToDB
expr_stmt|;
block|}
block|}
end_class

end_unit

