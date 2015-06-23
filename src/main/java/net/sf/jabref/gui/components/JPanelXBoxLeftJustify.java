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
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_class
DECL|class|JPanelXBoxLeftJustify
class|class
name|JPanelXBoxLeftJustify
extends|extends
name|JPanelXBox
block|{
DECL|method|JPanelXBoxLeftJustify ()
specifier|private
name|JPanelXBoxLeftJustify
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|add
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
comment|// padding
block|}
DECL|method|JPanelXBoxLeftJustify (Component c)
specifier|public
name|JPanelXBoxLeftJustify
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|add (Component c)
specifier|public
name|Component
name|add
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
return|return
name|super
operator|.
name|add
argument_list|(
name|c
argument_list|,
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|getComponentCount
argument_list|()
operator|-
literal|1
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

