begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 Raik Nagel     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_comment
comment|// function : set of animated lines
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified :
end_comment

begin_package
DECL|package|net.sf.jabref.about
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|about
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
DECL|class|TextBlock
specifier|public
class|class
name|TextBlock
implements|implements
name|Iterable
argument_list|<
name|AboutTextLine
argument_list|>
block|{
DECL|field|textLines
specifier|private
name|Vector
argument_list|<
name|AboutTextLine
argument_list|>
name|textLines
decl_stmt|;
DECL|field|headLine
specifier|private
name|AboutTextLine
name|headLine
decl_stmt|;
DECL|field|visible
specifier|private
name|boolean
name|visible
decl_stmt|;
DECL|method|TextBlock ()
specifier|public
name|TextBlock
parameter_list|()
block|{
name|textLines
operator|=
operator|new
name|Vector
argument_list|<
name|AboutTextLine
argument_list|>
argument_list|()
expr_stmt|;
name|visible
operator|=
literal|false
expr_stmt|;
block|}
comment|// ---------------------------------------------------------------------------
DECL|method|add (AboutTextLine line)
specifier|public
name|void
name|add
parameter_list|(
name|AboutTextLine
name|line
parameter_list|)
block|{
name|textLines
operator|.
name|add
argument_list|(
name|line
argument_list|)
expr_stmt|;
block|}
DECL|method|iterator ()
specifier|public
name|Iterator
argument_list|<
name|AboutTextLine
argument_list|>
name|iterator
parameter_list|()
block|{
return|return
name|textLines
operator|.
name|iterator
argument_list|()
return|;
block|}
comment|// ---------------------------------------------------------------------------
DECL|method|setHeading (AboutTextLine head)
specifier|public
name|void
name|setHeading
parameter_list|(
name|AboutTextLine
name|head
parameter_list|)
block|{
name|headLine
operator|=
name|head
expr_stmt|;
block|}
DECL|method|getHeading ()
specifier|public
name|AboutTextLine
name|getHeading
parameter_list|()
block|{
return|return
name|headLine
return|;
block|}
comment|// ---------------------------------------------------------------------------
DECL|method|isVisible ()
specifier|public
name|boolean
name|isVisible
parameter_list|()
block|{
return|return
name|visible
return|;
block|}
DECL|method|setVisible (boolean pVisible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|pVisible
parameter_list|)
block|{
name|this
operator|.
name|visible
operator|=
name|pVisible
expr_stmt|;
block|}
block|}
end_class

end_unit

