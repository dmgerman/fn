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
comment|// function : about action, used in JabrefFrame
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

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|*
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
import|;
end_import

begin_class
DECL|class|NewAboutAction
specifier|public
class|class
name|NewAboutAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|type
specifier|private
name|String
name|type
init|=
literal|null
decl_stmt|;
comment|// The type of item to create.
DECL|field|keyStroke
specifier|private
name|KeyStroke
name|keyStroke
init|=
literal|null
decl_stmt|;
comment|// Used for the specific instances.
DECL|method|NewAboutAction ()
specifier|public
name|NewAboutAction
parameter_list|()
block|{
comment|// This action leads to a dialog asking for entry type.
name|super
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"about"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
literal|"About JabRef"
argument_list|)
expr_stmt|;
comment|//    putValue( ACCELERATOR_KEY, key ) ;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"About JabRef"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed ( ActionEvent e )
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|About2
name|dialog
init|=
operator|new
name|About2
argument_list|(
operator|(
name|JFrame
operator|)
literal|null
argument_list|)
decl_stmt|;
block|}
block|}
end_class

end_unit

