begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.components
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|components
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
name|Dimension
import|;
end_import

begin_class
DECL|class|JPanelYBoxPreferredSize
specifier|public
class|class
name|JPanelYBoxPreferredSize
extends|extends
name|JPanelYBox
block|{
DECL|method|JPanelYBoxPreferredSize ()
specifier|public
name|JPanelYBoxPreferredSize
parameter_list|()
block|{
comment|// nothing special
block|}
DECL|method|JPanelYBoxPreferredSize (Component c)
specifier|public
name|JPanelYBoxPreferredSize
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getMaximumSize ()
specifier|public
name|Dimension
name|getMaximumSize
parameter_list|()
block|{
return|return
name|getPreferredSize
argument_list|()
return|;
block|}
block|}
end_class

end_unit

