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
name|java
operator|.
name|awt
operator|.
name|Component
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
name|FocusEvent
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
name|FocusListener
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

begin_class
DECL|class|GlobalFocusListener
specifier|public
class|class
name|GlobalFocusListener
implements|implements
name|FocusListener
block|{
DECL|field|focused
specifier|private
name|Component
name|focused
decl_stmt|;
DECL|method|GlobalFocusListener ()
specifier|public
name|GlobalFocusListener
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|focusGained (FocusEvent e)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|!
name|e
operator|.
name|isTemporary
argument_list|()
condition|)
block|{
name|focused
operator|=
operator|(
name|Component
operator|)
name|e
operator|.
name|getSource
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|focusLost (FocusEvent e)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{     }
DECL|method|getFocused ()
specifier|public
name|JComponent
name|getFocused
parameter_list|()
block|{
return|return
operator|(
name|JComponent
operator|)
name|focused
return|;
block|}
DECL|method|setFocused (Component c)
specifier|public
name|void
name|setFocused
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
name|focused
operator|=
name|c
expr_stmt|;
block|}
block|}
end_class

end_unit

