begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.help
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|help
package|;
end_package

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
name|Icon
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
name|KeyStroke
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|IconTheme
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
name|actions
operator|.
name|MnemonicAwareAction
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
name|desktop
operator|.
name|JabRefDesktop
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * This Action keeps a reference to a URL. When activated, it shows the help  * Dialog unless it is already visible, and shows the URL in it.  */
end_comment

begin_class
DECL|class|HelpAction
specifier|public
class|class
name|HelpAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|HelpAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|helpPage
specifier|private
name|HelpFile
name|helpPage
decl_stmt|;
DECL|method|HelpAction (String title, String tooltip, HelpFile helpPage, KeyStroke key)
specifier|public
name|HelpAction
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|tooltip
parameter_list|,
name|HelpFile
name|helpPage
parameter_list|,
name|KeyStroke
name|key
parameter_list|)
block|{
name|this
argument_list|(
name|title
argument_list|,
name|tooltip
argument_list|,
name|helpPage
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|HELP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|key
argument_list|)
expr_stmt|;
block|}
DECL|method|HelpAction (String title, String tooltip, HelpFile helpPage, Icon icon)
specifier|private
name|HelpAction
parameter_list|(
name|String
name|title
parameter_list|,
name|String
name|tooltip
parameter_list|,
name|HelpFile
name|helpPage
parameter_list|,
name|Icon
name|icon
parameter_list|)
block|{
name|super
argument_list|(
name|icon
argument_list|)
expr_stmt|;
name|this
operator|.
name|helpPage
operator|=
name|helpPage
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|tooltip
argument_list|)
expr_stmt|;
block|}
DECL|method|HelpAction (String tooltip, HelpFile helpPage)
specifier|public
name|HelpAction
parameter_list|(
name|String
name|tooltip
parameter_list|,
name|HelpFile
name|helpPage
parameter_list|)
block|{
name|this
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|tooltip
argument_list|,
name|helpPage
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|HELP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|HelpAction (HelpFile helpPage, Icon icon)
specifier|public
name|HelpAction
parameter_list|(
name|HelpFile
name|helpPage
parameter_list|,
name|Icon
name|icon
parameter_list|)
block|{
name|this
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|helpPage
argument_list|,
name|icon
argument_list|)
expr_stmt|;
block|}
DECL|method|HelpAction (HelpFile helpPage)
specifier|public
name|HelpAction
parameter_list|(
name|HelpFile
name|helpPage
parameter_list|)
block|{
name|this
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|,
name|helpPage
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|HELP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getHelpButton ()
specifier|public
name|JButton
name|getHelpButton
parameter_list|()
block|{
name|JButton
name|button
init|=
operator|new
name|JButton
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|button
operator|.
name|setText
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|button
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|24
argument_list|,
literal|24
argument_list|)
argument_list|)
expr_stmt|;
name|button
operator|.
name|setToolTipText
argument_list|(
name|getValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|button
return|;
block|}
DECL|method|setHelpFile (HelpFile urlPart)
specifier|public
name|void
name|setHelpFile
parameter_list|(
name|HelpFile
name|urlPart
parameter_list|)
block|{
name|this
operator|.
name|helpPage
operator|=
name|urlPart
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
name|String
name|url
init|=
literal|"http://help.jabref.org/"
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LANGUAGE
argument_list|)
operator|+
literal|"/"
operator|+
name|helpPage
operator|.
name|getPageName
argument_list|()
decl_stmt|;
name|JabRefDesktop
operator|.
name|openBrowserShowPopup
argument_list|(
name|url
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

