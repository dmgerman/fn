begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003  Nizar N. Batada, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
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
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

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
name|Insets
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
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_class
DECL|class|SidePane
specifier|public
class|class
name|SidePane
extends|extends
name|JPanel
block|{
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|sp
name|JScrollPane
name|sp
decl_stmt|;
DECL|field|close
name|JButton
name|close
init|=
operator|new
name|JButton
argument_list|(
literal|"X"
argument_list|)
decl_stmt|;
DECL|field|parent
name|JSplitPane
name|parent
decl_stmt|;
DECL|field|mainPanel
name|JPanel
name|mainPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|pan
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|method|SidePane (JSplitPane _parent)
specifier|public
name|SidePane
parameter_list|(
name|JSplitPane
name|_parent
parameter_list|)
block|{
name|parent
operator|=
name|_parent
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|lightGray
argument_list|)
expr_stmt|;
comment|//(Color.white);
name|mainPanel
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|lightGray
argument_list|)
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|mainPanel
argument_list|,
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_NEVER
argument_list|,
name|JScrollPane
operator|.
name|HORIZONTAL_SCROLLBAR_AS_NEEDED
argument_list|)
expr_stmt|;
comment|//super.add(sp, BorderLayout.CENTER);
name|super
operator|.
name|add
argument_list|(
name|mainPanel
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|pan
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|lightGray
argument_list|)
expr_stmt|;
name|super
operator|.
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
block|}
DECL|method|add (Component c)
specifier|public
name|Component
name|add
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
if|if
condition|(
name|pan
operator|!=
literal|null
condition|)
name|mainPanel
operator|.
name|remove
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|NORTH
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|c
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
name|pan
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|pan
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|lightGray
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridheight
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|pan
argument_list|,
name|con
argument_list|)
expr_stmt|;
comment|//mainPanel.add(pan);
return|return
name|c
return|;
block|}
DECL|method|remove (Component c)
specifier|public
name|void
name|remove
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
name|mainPanel
operator|.
name|remove
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

