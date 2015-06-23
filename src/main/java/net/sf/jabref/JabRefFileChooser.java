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
name|event
operator|.
name|MouseEvent
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
name|MouseListener
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
name|JFileChooser
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|plaf
operator|.
name|ComponentUI
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|plaf
operator|.
name|basic
operator|.
name|BasicFileChooserUI
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|plaf
operator|.
name|metal
operator|.
name|MetalFileChooserUI
import|;
end_import

begin_comment
comment|//======================================================================
end_comment

begin_comment
comment|// this class is a work around for the problem with regular filechooser:
end_comment

begin_comment
comment|// single clicking will no longer put into edit mode
end_comment

begin_comment
comment|//======================================================================
end_comment

begin_class
DECL|class|JabRefFileChooser
specifier|public
class|class
name|JabRefFileChooser
extends|extends
name|JFileChooser
block|{
DECL|method|JabRefFileChooser ()
specifier|public
name|JabRefFileChooser
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|JabRefFileChooser (File file)
specifier|public
name|JabRefFileChooser
parameter_list|(
name|File
name|file
parameter_list|)
block|{
name|super
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
comment|/*public int showOpenDialog(Component parent) throws HeadlessException {         if (lastSize != null) {             setSize(lastSize);             System.out.println("Setting size: "+lastSize);         }         int answer = super.showOpenDialog(parent);         lastSize = getSize();         return answer;     }*/
comment|/*public int showSaveDialog(Component parent) throws HeadlessException {         if (lastSize != null) {             setSize(lastSize);             System.out.println("Setting size: "+lastSize);         }         int answer = super.showSaveDialog(parent);         lastSize = getSize();         return answer;     }*/
comment|//========================================================
comment|//
comment|//========================================================
annotation|@
name|Override
DECL|method|setUI (ComponentUI newUI)
specifier|protected
name|void
name|setUI
parameter_list|(
name|ComponentUI
name|newUI
parameter_list|)
block|{
if|if
condition|(
name|Globals
operator|.
name|osName
operator|.
name|equals
argument_list|(
name|Globals
operator|.
name|MAC
argument_list|)
condition|)
block|{
name|super
operator|.
name|setUI
argument_list|(
name|newUI
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|super
operator|.
name|setUI
argument_list|(
operator|new
name|JabRefUI
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|//========================================================
comment|//
comment|//========================================================
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|JabRefFileChooser
name|fc
init|=
operator|new
name|JabRefFileChooser
argument_list|()
decl_stmt|;
name|int
name|returnVal
init|=
name|fc
operator|.
name|showOpenDialog
argument_list|(
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|returnVal
operator|==
name|JFileChooser
operator|.
name|APPROVE_OPTION
condition|)
block|{
name|fc
operator|.
name|getSelectedFile
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

begin_class
DECL|class|JabRefUI
class|class
name|JabRefUI
extends|extends
name|MetalFileChooserUI
block|{
DECL|method|JabRefUI (JFileChooser filechooser)
specifier|public
name|JabRefUI
parameter_list|(
name|JFileChooser
name|filechooser
parameter_list|)
block|{
name|super
argument_list|(
name|filechooser
argument_list|)
expr_stmt|;
block|}
DECL|class|DoubleClickListener
specifier|protected
class|class
name|DoubleClickListener
extends|extends
name|BasicFileChooserUI
operator|.
name|DoubleClickListener
block|{
DECL|field|list
specifier|final
name|JList
name|list
decl_stmt|;
DECL|method|DoubleClickListener (JList list)
specifier|public
name|DoubleClickListener
parameter_list|(
name|JList
name|list
parameter_list|)
block|{
name|super
argument_list|(
name|list
argument_list|)
expr_stmt|;
name|this
operator|.
name|list
operator|=
name|list
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|mouseEntered (MouseEvent e)
specifier|public
name|void
name|mouseEntered
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
comment|//System.out.println("mouse entered");
name|MouseListener
index|[]
name|l
init|=
name|list
operator|.
name|getMouseListeners
argument_list|()
decl_stmt|;
for|for
control|(
name|MouseListener
name|aL
range|:
name|l
control|)
block|{
if|if
condition|(
name|aL
operator|instanceof
name|SingleClickListener
condition|)
block|{
name|list
operator|.
name|removeMouseListener
argument_list|(
name|aL
argument_list|)
expr_stmt|;
block|}
block|}
name|super
operator|.
name|mouseEntered
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|createDoubleClickListener (JFileChooser fc, JList list)
specifier|protected
name|MouseListener
name|createDoubleClickListener
parameter_list|(
name|JFileChooser
name|fc
parameter_list|,
name|JList
name|list
parameter_list|)
block|{
return|return
operator|new
name|DoubleClickListener
argument_list|(
name|list
argument_list|)
return|;
block|}
block|}
end_class

end_unit

