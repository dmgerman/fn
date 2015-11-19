begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2015 Oliver Kopp     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.menus.help
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|menus
operator|.
name|help
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRef
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
name|javax
operator|.
name|swing
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
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_class
DECL|class|DonateAction
specifier|public
class|class
name|DonateAction
extends|extends
name|AbstractAction
block|{
DECL|field|donationLink
specifier|private
specifier|static
specifier|final
name|String
name|donationLink
init|=
literal|"https://github.com/JabRef/jabref/wiki/Donations"
decl_stmt|;
DECL|method|DonateAction ()
specifier|public
name|DonateAction
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Donate to JabRef"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Donate to JabRef"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SMALL_ICON
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|DONATE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|LARGE_ICON_KEY
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|DONATE
operator|.
name|getIcon
argument_list|()
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
try|try
block|{
name|JabRefDesktop
operator|.
name|openBrowser
argument_list|(
name|donationLink
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|JabRef
operator|.
name|jrf
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not open browser window."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

