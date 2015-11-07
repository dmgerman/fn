begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
package|;
end_package

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
name|actions
operator|.
name|Actions
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
name|worker
operator|.
name|Worker
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
name|spin
operator|.
name|Spin
import|;
end_import

begin_comment
comment|/**  *  * @author alver  */
end_comment

begin_class
DECL|class|SaveAllAction
specifier|public
class|class
name|SaveAllAction
extends|extends
name|MnemonicAwareAction
implements|implements
name|Worker
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|databases
specifier|private
name|int
name|databases
decl_stmt|;
DECL|field|saved
specifier|private
name|int
name|saved
decl_stmt|;
comment|/** Creates a new instance of SaveAllAction */
DECL|method|SaveAllAction (JabRefFrame frame)
specifier|public
name|SaveAllAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|SAVE_ALL
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Save all"
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
literal|"Save all open databases"
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"Save all"
argument_list|)
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
name|databases
operator|=
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
expr_stmt|;
name|saved
operator|=
literal|0
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Saving all databases..."
argument_list|)
argument_list|)
expr_stmt|;
name|Spin
operator|.
name|off
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|run
argument_list|()
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save all finished."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|databases
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|<
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
condition|)
block|{
comment|//System.out.println("Base "+i);
name|BasePanel
name|panel
init|=
name|frame
operator|.
name|baseAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|panel
operator|.
name|getFile
argument_list|()
operator|==
literal|null
condition|)
block|{
name|frame
operator|.
name|showBaseAt
argument_list|(
name|i
argument_list|)
expr_stmt|;
block|}
name|panel
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|SAVE
argument_list|)
expr_stmt|;
comment|// TODO: can we find out whether the save was actually done or not?
name|saved
operator|++
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

