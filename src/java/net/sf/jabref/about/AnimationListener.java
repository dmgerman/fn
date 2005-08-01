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
comment|// function : listener for animation actions
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

begin_interface
DECL|interface|AnimationListener
specifier|public
interface|interface
name|AnimationListener
block|{
DECL|method|animationReady ()
specifier|public
name|void
name|animationReady
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

