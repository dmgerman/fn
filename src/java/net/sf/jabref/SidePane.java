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
name|Dimension
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
DECL|field|PREFERRED_SIZE
name|Dimension
name|PREFERRED_SIZE
init|=
operator|new
name|Dimension
argument_list|(
name|GUIGlobals
operator|.
name|SPLIT_PANE_DIVIDER_LOCATION
argument_list|,
literal|100
argument_list|)
decl_stmt|;
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
comment|//JButton close = new JButton("X");
comment|//JSplitPane parent;
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
DECL|method|SidePane ()
specifier|public
name|SidePane
parameter_list|()
block|{
comment|//	parent = _parent;
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
comment|//setBackground(GUIGlobals.lightGray);//(Color.white);
comment|//mainPanel.setBackground(GUIGlobals.lightGray);
comment|/*sp = new JScrollPane 	    (mainPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER, 	    JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);*/
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
comment|//pan.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.red));
comment|//mainPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.yellow));
comment|//setBorder(BorderFactory.createMatteBorder(1,1,1,1, Color.green));
comment|//pan.setBackground(GUIGlobals.lightGray);
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
DECL|method|setComponents (Vector comps)
specifier|public
name|void
name|setComponents
parameter_list|(
name|Vector
name|comps
parameter_list|)
block|{
name|mainPanel
operator|.
name|removeAll
argument_list|()
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
literal|0
argument_list|,
literal|0
argument_list|,
literal|3
argument_list|,
literal|1
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
literal|0
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|comps
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|Component
name|c
init|=
operator|(
name|Component
operator|)
name|comps
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
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
comment|//System.out.println(c.getPreferredSize().toString());
block|}
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|Component
name|bx
init|=
name|Box
operator|.
name|createVerticalGlue
argument_list|()
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|bx
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|mainPanel
operator|.
name|add
argument_list|(
name|bx
argument_list|)
expr_stmt|;
name|revalidate
argument_list|()
expr_stmt|;
name|repaint
argument_list|()
expr_stmt|;
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
DECL|method|getMaximumSize ()
specifier|public
name|Dimension
name|getMaximumSize
parameter_list|()
block|{
return|return
name|PREFERRED_SIZE
return|;
block|}
DECL|method|getPreferredSize ()
specifier|public
name|Dimension
name|getPreferredSize
parameter_list|()
block|{
return|return
name|PREFERRED_SIZE
return|;
block|}
block|}
end_class

end_unit

