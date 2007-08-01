begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2004 R. Nagel   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_comment
comment|// created by : r.nagel 04.11.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function : supports an underlying text for jcomponents
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// todo     :
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
end_comment

begin_comment
comment|//
end_comment

begin_package
DECL|package|net.sf.jabref.wizard.text.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|text
operator|.
name|gui
package|;
end_package

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
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Font
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Graphics
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

begin_class
DECL|class|OverlayPanel
specifier|public
class|class
name|OverlayPanel
extends|extends
name|JPanel
block|{
DECL|field|label
specifier|private
name|JLabel
name|label
decl_stmt|;
DECL|field|overlay
specifier|private
name|JComponent
name|overlay
decl_stmt|;
DECL|field|scroller
specifier|private
name|JScrollPane
name|scroller
decl_stmt|;
DECL|method|OverlayPanel (JComponent container, String text)
specifier|public
name|OverlayPanel
parameter_list|(
name|JComponent
name|container
parameter_list|,
name|String
name|text
parameter_list|)
block|{
name|OverlayLayout
name|layout
init|=
operator|new
name|OverlayLayout
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|this
operator|.
name|setLayout
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|overlay
operator|=
name|container
expr_stmt|;
name|label
operator|=
operator|new
name|JLabel
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|label
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
literal|"dialog"
argument_list|,
name|Font
operator|.
name|ITALIC
argument_list|,
literal|18
argument_list|)
argument_list|)
expr_stmt|;
comment|//    label.setForeground(Color.lightGray);
name|label
operator|.
name|setForeground
argument_list|(
operator|new
name|Color
argument_list|(
literal|224
argument_list|,
literal|220
argument_list|,
literal|220
argument_list|)
argument_list|)
expr_stmt|;
name|label
operator|.
name|setLocation
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|scroller
operator|=
operator|new
name|JScrollPane
argument_list|(
name|overlay
argument_list|)
expr_stmt|;
name|scroller
operator|.
name|setLocation
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|scroller
operator|.
name|setVerticalScrollBarPolicy
argument_list|(
name|JScrollPane
operator|.
name|VERTICAL_SCROLLBAR_ALWAYS
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|label
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|scroller
argument_list|)
expr_stmt|;
block|}
DECL|method|paint (Graphics g)
specifier|public
name|void
name|paint
parameter_list|(
name|Graphics
name|g
parameter_list|)
block|{
name|int
name|len
init|=
name|label
operator|.
name|getWidth
argument_list|()
decl_stmt|;
name|Dimension
name|dim
init|=
name|this
operator|.
name|getSize
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|dim
operator|.
name|height
operator|>
literal|25
operator|)
operator|&&
operator|(
name|dim
operator|.
name|width
operator|>
name|len
operator|+
literal|10
operator|)
condition|)
block|{
name|int
name|x
init|=
operator|(
name|dim
operator|.
name|width
operator|-
name|len
operator|)
operator|/
literal|2
decl_stmt|;
name|int
name|y
init|=
name|dim
operator|.
name|height
operator|/
literal|2
decl_stmt|;
name|label
operator|.
name|setLocation
argument_list|(
name|x
argument_list|,
name|y
argument_list|)
expr_stmt|;
block|}
name|super
operator|.
name|paint
argument_list|(
name|g
argument_list|)
expr_stmt|;
block|}
comment|/*   // it doesn't work well   public void addMouseListener(MouseListener listener)   {     overlay.addMouseListener(listener);     super.addMouseListener(listener);   } */
block|}
end_class

end_unit

