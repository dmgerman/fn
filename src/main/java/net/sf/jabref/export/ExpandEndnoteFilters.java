begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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
name|java
operator|.
name|io
operator|.
name|File
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
name|JFileChooser
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
name|gui
operator|.
name|FileDialogs
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
name|ResourceExtractor
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
DECL|class|ExpandEndnoteFilters
specifier|public
class|class
name|ExpandEndnoteFilters
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
DECL|field|file
specifier|private
name|File
name|file
decl_stmt|;
comment|/** Creates a new instance of ExpandEndnoteFilters */
DECL|method|ExpandEndnoteFilters (JabRefFrame frame)
specifier|public
name|ExpandEndnoteFilters
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
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
name|NAME
argument_list|,
literal|"Unpack EndNote filter set"
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
literal|"<HTML>Unpack the zip file containing import/export filters for Endnote,<BR>"
operator|+
literal|"for optimal interoperability with JabRef</HTML>"
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
name|String
name|filename
init|=
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
operator|new
name|File
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|)
argument_list|)
argument_list|,
literal|".zip"
argument_list|,
name|JFileChooser
operator|.
name|SAVE_DIALOG
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|filename
operator|==
literal|null
condition|)
block|{
return|return;
block|}
comment|//if (!filename.substring(4).equalsIgnoreCase(".zip"))
comment|//    filename += ".zip";
name|file
operator|=
operator|new
name|File
argument_list|(
name|filename
argument_list|)
expr_stmt|;
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|int
name|confirm
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
literal|'\''
operator|+
name|file
operator|.
name|getName
argument_list|()
operator|+
literal|"' "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"exists. Overwrite file?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unpack EndNote filter set"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|confirm
operator|!=
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
return|return;
block|}
block|}
comment|// Spin off the GUI thread, and run the run() method.
operator|(
operator|(
name|Worker
operator|)
name|Spin
operator|.
name|off
argument_list|(
name|this
argument_list|)
operator|)
operator|.
name|run
argument_list|()
expr_stmt|;
name|file
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Worker method.      */
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|String
name|FILENAME
init|=
literal|"/EndNote.zip"
decl_stmt|;
name|ResourceExtractor
name|re
init|=
operator|new
name|ResourceExtractor
argument_list|(
name|frame
argument_list|,
name|FILENAME
argument_list|,
name|file
argument_list|)
decl_stmt|;
name|re
operator|.
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
literal|"Unpacked file."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

