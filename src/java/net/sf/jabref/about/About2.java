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
comment|// function : new about dialog
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
name|BorderLayout
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
name|event
operator|.
name|ActionEvent
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
name|WindowEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_class
DECL|class|About2
specifier|public
class|class
name|About2
extends|extends
name|JDialog
implements|implements
name|ActionListener
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
comment|// AboutDialog constructor
DECL|method|About2 ( JFrame parent )
specifier|public
name|About2
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"About JabRef"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
comment|//    setDefaultCloseOperation( EXIT_ON_CLOSE ) ;
name|JPanel
name|contentPanel
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
decl_stmt|;
name|contentPanel
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|white
argument_list|)
expr_stmt|;
comment|//    content.setBorder( new EmptyBorder( 12, 12, 12, 12 ) ) ;
name|setContentPane
argument_list|(
name|contentPanel
argument_list|)
expr_stmt|;
name|contentPanel
operator|.
name|add
argument_list|(
name|BorderLayout
operator|.
name|CENTER
argument_list|,
operator|new
name|ExtendedInfoPanel
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setBackground
argument_list|(
name|Color
operator|.
name|white
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|setLocationRelativeTo
argument_list|(
name|parent
argument_list|)
expr_stmt|;
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|processWindowEvent ( WindowEvent e )
specifier|protected
name|void
name|processWindowEvent
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|super
operator|.
name|processWindowEvent
argument_list|(
name|e
argument_list|)
expr_stmt|;
if|if
condition|(
name|e
operator|.
name|getID
argument_list|()
operator|==
name|WindowEvent
operator|.
name|WINDOW_CLOSING
condition|)
block|{
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
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
name|String
name|cmd
init|=
name|e
operator|.
name|getActionCommand
argument_list|()
decl_stmt|;
if|if
condition|(
name|cmd
operator|.
name|equals
argument_list|(
literal|"close"
argument_list|)
condition|)
block|{
name|setVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
comment|//      System.exit( 0 ) ;
block|}
elseif|else
if|if
condition|(
name|cmd
operator|.
name|equals
argument_list|(
literal|"license"
argument_list|)
condition|)
block|{
comment|//      showLicense() ;
block|}
block|}
comment|// ----------------------------------------------------------------------------
block|}
end_class

end_unit

