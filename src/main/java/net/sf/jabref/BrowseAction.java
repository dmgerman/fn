begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|*
import|;
end_import

begin_comment
comment|/**  * Action used to produce a "Browse" button for one of the text fields.  */
end_comment

begin_class
DECL|class|BrowseAction
specifier|public
class|class
name|BrowseAction
extends|extends
name|AbstractAction
implements|implements
name|ActionListener
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3007593430933681310L
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JFrame
name|frame
decl_stmt|;
DECL|field|comp
specifier|private
specifier|final
name|JTextField
name|comp
decl_stmt|;
DECL|field|dir
specifier|private
specifier|final
name|boolean
name|dir
decl_stmt|;
DECL|field|focusTarget
specifier|private
specifier|final
name|JComponent
name|focusTarget
decl_stmt|;
DECL|method|buildForDir (JFrame frame, JTextField tc)
specifier|public
specifier|static
name|BrowseAction
name|buildForDir
parameter_list|(
name|JFrame
name|frame
parameter_list|,
name|JTextField
name|tc
parameter_list|)
block|{
return|return
operator|new
name|BrowseAction
argument_list|(
name|frame
argument_list|,
name|tc
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|method|buildForDir (JTextField tc)
specifier|public
specifier|static
name|BrowseAction
name|buildForDir
parameter_list|(
name|JTextField
name|tc
parameter_list|)
block|{
return|return
operator|new
name|BrowseAction
argument_list|(
literal|null
argument_list|,
name|tc
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|method|buildForFile (JTextField tc)
specifier|public
specifier|static
name|BrowseAction
name|buildForFile
parameter_list|(
name|JTextField
name|tc
parameter_list|)
block|{
return|return
operator|new
name|BrowseAction
argument_list|(
literal|null
argument_list|,
name|tc
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|method|buildForFile (JTextField tc, JComponent focusTarget)
specifier|public
specifier|static
name|BrowseAction
name|buildForFile
parameter_list|(
name|JTextField
name|tc
parameter_list|,
name|JComponent
name|focusTarget
parameter_list|)
block|{
return|return
operator|new
name|BrowseAction
argument_list|(
literal|null
argument_list|,
name|tc
argument_list|,
literal|false
argument_list|,
name|focusTarget
argument_list|)
return|;
block|}
DECL|method|buildForDir (JTextField tc, JComponent focusTarget)
specifier|public
specifier|static
name|BrowseAction
name|buildForDir
parameter_list|(
name|JTextField
name|tc
parameter_list|,
name|JComponent
name|focusTarget
parameter_list|)
block|{
return|return
operator|new
name|BrowseAction
argument_list|(
literal|null
argument_list|,
name|tc
argument_list|,
literal|true
argument_list|,
name|focusTarget
argument_list|)
return|;
block|}
DECL|method|BrowseAction (JFrame frame, JTextField tc, boolean dir, JComponent focusTarget)
specifier|private
name|BrowseAction
parameter_list|(
name|JFrame
name|frame
parameter_list|,
name|JTextField
name|tc
parameter_list|,
name|boolean
name|dir
parameter_list|,
name|JComponent
name|focusTarget
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|dir
operator|=
name|dir
expr_stmt|;
name|this
operator|.
name|comp
operator|=
name|tc
expr_stmt|;
name|this
operator|.
name|focusTarget
operator|=
name|focusTarget
expr_stmt|;
block|}
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
name|chosen
init|=
name|askUser
argument_list|()
decl_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|File
name|newFile
init|=
operator|new
name|File
argument_list|(
name|chosen
argument_list|)
decl_stmt|;
name|comp
operator|.
name|setText
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|focusTarget
operator|!=
literal|null
condition|)
block|{
operator|new
name|FocusRequester
argument_list|(
name|focusTarget
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|askUser ()
specifier|private
name|String
name|askUser
parameter_list|()
block|{
if|if
condition|(
name|dir
condition|)
block|{
return|return
name|FileDialogs
operator|.
name|getNewDir
argument_list|(
name|frame
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

