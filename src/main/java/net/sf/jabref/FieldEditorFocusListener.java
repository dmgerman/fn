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

begin_comment
comment|/**  * Focus listener that changes the color of the text area when it has focus.  * Created by IntelliJ IDEA.  * User: alver  * Date: 18.mar.2005  * Time: 18:20:14  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|FieldEditorFocusListener
class|class
name|FieldEditorFocusListener
implements|implements
name|FocusListener
block|{
DECL|method|FieldEditorFocusListener ()
specifier|public
name|FieldEditorFocusListener
parameter_list|()
block|{     }
annotation|@
name|Override
DECL|method|focusGained (FocusEvent event)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getSource
argument_list|()
operator|instanceof
name|FieldEditor
condition|)
block|{
operator|(
operator|(
name|FieldEditor
operator|)
name|event
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|setActiveBackgroundColor
argument_list|()
expr_stmt|;
block|}
else|else
block|{
operator|(
operator|(
name|JComponent
operator|)
name|event
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|activeBackground
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|focusLost (FocusEvent event)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|getSource
argument_list|()
operator|instanceof
name|FieldEditor
condition|)
block|{
operator|(
operator|(
name|FieldEditor
operator|)
name|event
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|setValidBackgroundColor
argument_list|()
expr_stmt|;
block|}
else|else
block|{
operator|(
operator|(
name|JComponent
operator|)
name|event
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|validFieldBackgroundColor
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

