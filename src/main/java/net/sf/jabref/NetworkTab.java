begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2013 JabRef contributors.     This program is free software: you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation, either version 3 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License     along with this program.  If not, see<http://www.gnu.org/licenses/>. */
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
name|Insets
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
name|JCheckBox
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
name|JPanel
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
name|JTextField
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
DECL|class|NetworkTab
specifier|public
class|class
name|NetworkTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|useProxy
specifier|private
specifier|final
name|JCheckBox
name|useProxy
decl_stmt|;
DECL|field|defProxyHostname
specifier|private
specifier|final
name|JTextField
name|defProxyHostname
decl_stmt|;
DECL|field|defProxyPort
specifier|private
specifier|final
name|JTextField
name|defProxyPort
decl_stmt|;
DECL|field|_prefs
specifier|final
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
specifier|final
name|JabRefFrame
name|_frame
decl_stmt|;
comment|//    private HelpAction ownerHelp, timeStampHelp;
DECL|method|NetworkTab (JabRefFrame frame, JabRefPreferences prefs)
specifier|public
name|NetworkTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|_frame
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
name|useProxy
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use custom proxy configuration"
argument_list|)
argument_list|)
expr_stmt|;
name|defProxyHostname
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|defProxyHostname
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|defProxyPort
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|defProxyPort
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Insets
name|marg
init|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|12
argument_list|,
literal|3
argument_list|,
literal|0
argument_list|)
decl_stmt|;
name|useProxy
operator|.
name|setMargin
argument_list|(
name|marg
argument_list|)
expr_stmt|;
name|defProxyPort
operator|.
name|setMargin
argument_list|(
name|marg
argument_list|)
expr_stmt|;
comment|// We need a listener on useImportInspector to enable and disable the
comment|// import inspector related choices;
name|useProxy
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
comment|//useProxy.setEnabled(useProxy.isSelected());
name|defProxyHostname
operator|.
name|setEnabled
argument_list|(
name|useProxy
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|defProxyPort
operator|.
name|setEnabled
argument_list|(
name|useProxy
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:150dlu, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|//("right:pref, 10dlu, 50dlu, 5dlu, fill:60dlu", "");
comment|//("10dlu, left:50dlu, 4dlu, fill:pref", "");
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Network"
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
name|useProxy
argument_list|,
literal|5
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|JLabel
name|lap
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Host"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lap
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|defProxyHostname
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|JLabel
name|lap2
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Port"
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lap2
argument_list|)
expr_stmt|;
comment|//builder.append(new JPanel());
name|builder
operator|.
name|append
argument_list|(
name|defProxyPort
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
name|builder
operator|.
name|getPanel
argument_list|()
decl_stmt|;
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
name|useProxy
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"useProxy"
argument_list|)
argument_list|)
expr_stmt|;
comment|//_prefs.putBoolean("defaultAutoSort", defSorrrt.isSelected());
name|defProxyHostname
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"proxyHostname"
argument_list|)
argument_list|)
expr_stmt|;
name|defProxyPort
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"proxyPort"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useProxy"
argument_list|,
name|useProxy
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|//_prefs.putBoolean("defaultAutoSort", defSorrrt.isSelected());
name|_prefs
operator|.
name|put
argument_list|(
literal|"proxyHostname"
argument_list|,
name|defProxyHostname
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"proxyPort"
argument_list|,
name|defProxyPort
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
parameter_list|()
block|{
name|boolean
name|validSetting
decl_stmt|;
if|if
condition|(
name|useProxy
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|String
name|host
init|=
name|defProxyHostname
operator|.
name|getText
argument_list|()
decl_stmt|;
name|String
name|port
init|=
name|defProxyPort
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|host
operator|==
literal|null
operator|)
operator|||
operator|(
name|host
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
operator|||
operator|(
name|port
operator|==
literal|null
operator|)
operator|||
operator|(
name|port
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|validSetting
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|Integer
name|p
decl_stmt|;
try|try
block|{
name|p
operator|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|port
argument_list|)
expr_stmt|;
name|validSetting
operator|=
operator|(
name|p
operator|>
literal|0
operator|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|e
parameter_list|)
block|{
name|validSetting
operator|=
literal|false
expr_stmt|;
block|}
block|}
block|}
else|else
block|{
name|validSetting
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|validSetting
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Please specify both hostname and port"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Invalid setting"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
return|return
name|validSetting
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
literal|"Network"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

