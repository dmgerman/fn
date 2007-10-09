begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  animated about dialog  Copyright (C) 2005 Raik Nagel<kiar@users.sourceforge.net> All rights reserved.  Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:  * Redistributions of source code must retain the above copyright notice,   this list of conditions and the following disclaimer. * Redistributions in binary form must reproduce the above copyright notice,   this list of conditions and the following disclaimer in the documentation   and/or other materials provided with the distribution. * Neither the name of the author nor the names of its contributors may be   used to endorse or promote products derived from this software without   specific prior written permission.  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */
end_comment

begin_comment
comment|// created by : r.nagel 05.05.2005
end_comment

begin_comment
comment|//
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

