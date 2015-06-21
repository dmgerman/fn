begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2004 R. Nagel   All programs in this directory and  subdirectories are published under the GNU General Public License as  described below.   This program is free software; you can redistribute it and/or modify  it under the terms of the GNU General Public License as published by  the Free Software Foundation; either version 2 of the License, or (at  your option) any later version.   This program is distributed in the hope that it will be useful, but  WITHOUT ANY WARRANTY; without even the implied warranty of  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU  General Public License for more details.   You should have received a copy of the GNU General Public License  along with this program; if not, write to the Free Software  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307  USA   Further information about the GNU GPL is available at:  http://www.gnu.org/copyleft/gpl.ja.html   */
end_comment

begin_comment
comment|// created by : r.nagel 14.09.2004
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// function :
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|// modified:
end_comment

begin_comment
comment|//
end_comment

begin_package
DECL|package|net.sf.jabref.wizard.integrity.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|wizard
operator|.
name|integrity
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|javax
operator|.
name|swing
operator|.
name|border
operator|.
name|EtchedBorder
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
DECL|class|IntegrityWizard
specifier|public
class|class
name|IntegrityWizard
extends|extends
name|JDialog
implements|implements
name|ActionListener
block|{
DECL|field|dbase
specifier|private
name|BibtexDatabase
name|dbase
decl_stmt|;
DECL|field|basePanel
specifier|private
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|closeButton
specifier|private
name|JButton
name|closeButton
decl_stmt|;
DECL|field|startButton
specifier|private
name|JButton
name|startButton
decl_stmt|;
DECL|field|warnPanel
specifier|private
name|IntegrityMessagePanel
name|warnPanel
decl_stmt|;
DECL|method|IntegrityWizard (JabRefFrame frame, BasePanel basePanel)
specifier|public
name|IntegrityWizard
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
literal|"dialog"
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// no modal
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|dbase
operator|=
name|basePanel
operator|.
name|database
argument_list|()
expr_stmt|;
try|try
block|{
name|jbInit
argument_list|()
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|jbInit ()
specifier|private
name|void
name|jbInit
parameter_list|()
block|{
comment|//    this.setModal( true ) ;
name|this
operator|.
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
comment|// messages
name|this
operator|.
name|setTitle
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Integrity_check"
argument_list|)
argument_list|)
expr_stmt|;
comment|//Globals.lang( "Plain_text_import" ) + " " + typeStr ) ;
comment|//warnPanel = new IntegrityMessagePanel() ;
comment|//this.setTitle( "Experimental feature - Integrity Check") ;//Globals.lang( "Plain_text_import" ) + " " + typeStr ) ;
name|warnPanel
operator|=
operator|new
name|IntegrityMessagePanel
argument_list|(
name|basePanel
argument_list|)
expr_stmt|;
comment|// ButtonPanel
name|JPanel
name|buttonPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|10
argument_list|,
literal|0
argument_list|,
literal|10
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
comment|// Buttons
name|startButton
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Scan"
argument_list|)
argument_list|)
expr_stmt|;
name|startButton
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|closeButton
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Close"
argument_list|)
argument_list|)
expr_stmt|;
name|closeButton
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
comment|// insert Buttons
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|startButton
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|startButton
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|closeButton
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|closeButton
argument_list|)
expr_stmt|;
comment|// ----------------------------------------------------------------------
comment|// add a short info, if available
name|JEditorPane
name|infoText
init|=
literal|null
decl_stmt|;
name|URL
name|infoURL
init|=
name|JabRef
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|GUIGlobals
operator|.
name|getLocaleHelpPath
argument_list|()
operator|+
name|GUIGlobals
operator|.
name|shortIntegrityCheck
argument_list|)
decl_stmt|;
if|if
condition|(
name|infoURL
operator|!=
literal|null
condition|)
try|try
block|{
name|infoText
operator|=
operator|new
name|JEditorPane
argument_list|()
expr_stmt|;
name|infoText
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|infoText
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|220
argument_list|,
literal|60
argument_list|)
argument_list|)
expr_stmt|;
name|infoText
operator|.
name|setMinimumSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|180
argument_list|,
literal|50
argument_list|)
argument_list|)
expr_stmt|;
name|infoText
operator|.
name|setPage
argument_list|(
name|infoURL
argument_list|)
expr_stmt|;
name|infoText
operator|.
name|setBackground
argument_list|(
name|GUIGlobals
operator|.
name|infoField
argument_list|)
expr_stmt|;
name|infoText
operator|.
name|setBorder
argument_list|(
operator|new
name|EtchedBorder
argument_list|(
name|EtchedBorder
operator|.
name|LOWERED
argument_list|)
argument_list|)
expr_stmt|;
comment|//        bottomPanel.add( infoText, BorderLayout.CENTER ) ;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|infoText
operator|=
literal|null
expr_stmt|;
block|}
comment|// -----------------------------------------------------------------------
comment|// content
name|Container
name|content
init|=
name|this
operator|.
name|getContentPane
argument_list|()
decl_stmt|;
name|content
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|infoText
operator|!=
literal|null
condition|)
comment|// only if some help available
block|{
name|content
operator|.
name|add
argument_list|(
name|infoText
argument_list|,
name|BorderLayout
operator|.
name|PAGE_START
argument_list|)
expr_stmt|;
block|}
name|content
operator|.
name|add
argument_list|(
name|warnPanel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|content
operator|.
name|add
argument_list|(
name|buttonPanel
argument_list|,
name|BorderLayout
operator|.
name|PAGE_END
argument_list|)
expr_stmt|;
block|}
comment|// ---------------------------------------------------------------------------
comment|// ---------------------------------------------------------------------------
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|Object
name|sender
init|=
name|e
operator|.
name|getSource
argument_list|()
decl_stmt|;
if|if
condition|(
name|sender
operator|==
name|closeButton
condition|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sender
operator|==
name|startButton
condition|)
block|{
name|startButton
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|Runnable
name|scanWork
init|=
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|warnPanel
operator|.
name|updateView
argument_list|(
name|dbase
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|scanWork
argument_list|)
expr_stmt|;
name|startButton
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

