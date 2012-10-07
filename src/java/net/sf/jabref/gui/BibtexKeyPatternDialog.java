begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|BorderLayout
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
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ActionMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|InputMap
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JComponent
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
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JRootPane
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
name|BasePanel
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefFrame
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
name|MetaData
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
name|Util
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
name|labelPattern
operator|.
name|LabelPattern
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
name|labelPattern
operator|.
name|LabelPatternPanel
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder2
import|;
end_import

begin_class
DECL|class|BibtexKeyPatternDialog
specifier|public
class|class
name|BibtexKeyPatternDialog
extends|extends
name|JDialog
block|{
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|labelPatternPanel
specifier|private
name|LabelPatternPanel
name|labelPatternPanel
decl_stmt|;
DECL|method|BibtexKeyPatternDialog (JabRefFrame parent, BasePanel panel)
specifier|public
name|BibtexKeyPatternDialog
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|,
name|BasePanel
name|panel
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
literal|"Bibtex key patterns"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|labelPatternPanel
operator|=
operator|new
name|LabelPatternPanel
argument_list|(
name|parent
operator|.
name|helpDiag
argument_list|)
expr_stmt|;
name|setPanel
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Used for updating an existing Dialog 	 *  	 * @param panel the panel to read the data from 	 */
DECL|method|setPanel (BasePanel panel)
specifier|public
name|void
name|setPanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|panel
operator|.
name|metaData
argument_list|()
expr_stmt|;
name|LabelPattern
name|keypatterns
init|=
name|metaData
operator|.
name|getLabelPattern
argument_list|()
decl_stmt|;
name|labelPatternPanel
operator|.
name|setValues
argument_list|(
name|keypatterns
argument_list|)
expr_stmt|;
block|}
DECL|method|init ()
specifier|private
specifier|final
name|void
name|init
parameter_list|()
block|{
name|getContentPane
argument_list|()
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|labelPatternPanel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
comment|// label of "cancel" is set later as the label is overwritten by assigning an action to the button
name|JPanel
name|lower
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|lower
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonBarBuilder2
name|bb
init|=
operator|new
name|ButtonBarBuilder2
argument_list|(
name|lower
argument_list|)
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|lower
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|this
operator|.
name|setDefaultCloseOperation
argument_list|(
name|DISPOSE_ON_CLOSE
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|500
argument_list|,
literal|600
argument_list|)
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|metaData
operator|.
name|setLabelPattern
argument_list|(
name|labelPatternPanel
operator|.
name|getLabelPattern
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
specifier|final
name|JDialog
name|dialog
init|=
name|this
decl_stmt|;
name|Action
name|cancelAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dialog
operator|.
name|dispatchEvent
argument_list|(
operator|new
name|WindowEvent
argument_list|(
name|dialog
argument_list|,
name|WindowEvent
operator|.
name|WINDOW_CLOSING
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|setAction
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
expr_stmt|;
name|Util
operator|.
name|bindCloseDialogKeyToCancelAction
argument_list|(
name|this
operator|.
name|getRootPane
argument_list|()
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|visible
condition|)
name|super
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

