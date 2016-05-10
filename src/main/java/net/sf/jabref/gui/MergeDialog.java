begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|GridBagConstraints
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
name|ActionEvent
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
name|JCheckBox
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
name|gui
operator|.
name|keyboard
operator|.
name|KeyBinding
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_comment
comment|/**  *<p>Title: MergeDialog</p>  *<p>Description: Asks for details about merge database operation.</p>  *<p>Copyright: Copyright (c) 2003</p>  * @author Morten O. Alver  */
end_comment

begin_class
DECL|class|MergeDialog
specifier|public
class|class
name|MergeDialog
extends|extends
name|JDialog
block|{
DECL|field|panel1
specifier|private
specifier|final
name|JPanel
name|panel1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|borderLayout1
specifier|private
specifier|final
name|BorderLayout
name|borderLayout1
init|=
operator|new
name|BorderLayout
argument_list|()
decl_stmt|;
DECL|field|jPanel1
specifier|private
specifier|final
name|JPanel
name|jPanel1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|jPanel2
specifier|private
specifier|final
name|JPanel
name|jPanel2
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|cancel
specifier|private
specifier|final
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|entries
specifier|private
specifier|final
name|JCheckBox
name|entries
init|=
operator|new
name|JCheckBox
argument_list|()
decl_stmt|;
DECL|field|strings
specifier|private
specifier|final
name|JCheckBox
name|strings
init|=
operator|new
name|JCheckBox
argument_list|()
decl_stmt|;
DECL|field|gridBagLayout1
specifier|private
specifier|final
name|GridBagLayout
name|gridBagLayout1
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|groups
specifier|private
specifier|final
name|JCheckBox
name|groups
init|=
operator|new
name|JCheckBox
argument_list|()
decl_stmt|;
DECL|field|selector
specifier|private
specifier|final
name|JCheckBox
name|selector
init|=
operator|new
name|JCheckBox
argument_list|()
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
decl_stmt|;
DECL|method|MergeDialog (JabRefFrame frame, String title, boolean modal)
specifier|public
name|MergeDialog
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|String
name|title
parameter_list|,
name|boolean
name|modal
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|title
argument_list|,
name|modal
argument_list|)
expr_stmt|;
name|jbInit
argument_list|()
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
DECL|method|isOkPressed ()
specifier|public
name|boolean
name|isOkPressed
parameter_list|()
block|{
return|return
name|okPressed
return|;
block|}
DECL|method|jbInit ()
specifier|private
name|void
name|jbInit
parameter_list|()
block|{
name|panel1
operator|.
name|setLayout
argument_list|(
name|borderLayout1
argument_list|)
expr_stmt|;
name|ok
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|okPressed
operator|=
literal|true
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
argument_list|()
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|setLayout
argument_list|(
name|gridBagLayout1
argument_list|)
expr_stmt|;
name|entries
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|entries
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import entries"
argument_list|)
argument_list|)
expr_stmt|;
name|strings
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|strings
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import strings"
argument_list|)
argument_list|)
expr_stmt|;
name|groups
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import group definitions"
argument_list|)
argument_list|)
expr_stmt|;
name|selector
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import word selector definitions"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|setResizable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|panel1
argument_list|)
expr_stmt|;
name|panel1
operator|.
name|add
argument_list|(
name|jPanel2
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|ok
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|cancel
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|panel1
operator|.
name|add
argument_list|(
name|jPanel1
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|add
argument_list|(
name|entries
argument_list|,
operator|new
name|GridBagConstraints
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|0.0
argument_list|,
literal|0.0
argument_list|,
name|GridBagConstraints
operator|.
name|WEST
argument_list|,
name|GridBagConstraints
operator|.
name|HORIZONTAL
argument_list|,
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|add
argument_list|(
name|strings
argument_list|,
operator|new
name|GridBagConstraints
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|0.0
argument_list|,
literal|0.0
argument_list|,
name|GridBagConstraints
operator|.
name|WEST
argument_list|,
name|GridBagConstraints
operator|.
name|NONE
argument_list|,
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|add
argument_list|(
name|groups
argument_list|,
operator|new
name|GridBagConstraints
argument_list|(
literal|0
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|0.0
argument_list|,
literal|0.0
argument_list|,
name|GridBagConstraints
operator|.
name|WEST
argument_list|,
name|GridBagConstraints
operator|.
name|NONE
argument_list|,
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel1
operator|.
name|add
argument_list|(
name|selector
argument_list|,
operator|new
name|GridBagConstraints
argument_list|(
literal|0
argument_list|,
literal|3
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|0.0
argument_list|,
literal|0.0
argument_list|,
name|GridBagConstraints
operator|.
name|WEST
argument_list|,
name|GridBagConstraints
operator|.
name|NONE
argument_list|,
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|jPanel1
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|jPanel1
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE_DIALOG
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|importEntries ()
specifier|public
name|boolean
name|importEntries
parameter_list|()
block|{
return|return
name|entries
operator|.
name|isSelected
argument_list|()
return|;
block|}
DECL|method|importGroups ()
specifier|public
name|boolean
name|importGroups
parameter_list|()
block|{
return|return
name|groups
operator|.
name|isSelected
argument_list|()
return|;
block|}
DECL|method|importStrings ()
specifier|public
name|boolean
name|importStrings
parameter_list|()
block|{
return|return
name|strings
operator|.
name|isSelected
argument_list|()
return|;
block|}
DECL|method|importSelectorWords ()
specifier|public
name|boolean
name|importSelectorWords
parameter_list|()
block|{
return|return
name|selector
operator|.
name|isSelected
argument_list|()
return|;
block|}
block|}
end_class

end_unit

