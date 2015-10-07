begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external.push
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|push
package|;
end_package

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
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA. User: alver Date: Jan 14, 2006 Time: 4:55:23 PM To change this template use File | Settings  * | File Templates.  */
end_comment

begin_class
DECL|class|PushToTeXstudio
specifier|public
class|class
name|PushToTeXstudio
extends|extends
name|AbstractPushToApplication
implements|implements
name|PushToApplication
block|{
annotation|@
name|Override
DECL|method|getApplicationName ()
specifier|public
name|String
name|getApplicationName
parameter_list|()
block|{
return|return
literal|"TeXstudio"
return|;
block|}
annotation|@
name|Override
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
block|{
return|return
name|IconTheme
operator|.
name|getImage
argument_list|(
literal|"texstudio"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getCommandLine (String keyString)
specifier|protected
name|String
index|[]
name|getCommandLine
parameter_list|(
name|String
name|keyString
parameter_list|)
block|{
return|return
operator|new
name|String
index|[]
block|{
name|commandPath
block|,
literal|"--insert-cite"
block|,
name|keyString
block|}
return|;
block|}
annotation|@
name|Override
DECL|method|initParameters ()
specifier|protected
name|void
name|initParameters
parameter_list|()
block|{
name|commandPathPreferenceKey
operator|=
name|JabRefPreferences
operator|.
name|TEXSTUDIO_PATH
expr_stmt|;
block|}
block|}
end_class

end_unit

