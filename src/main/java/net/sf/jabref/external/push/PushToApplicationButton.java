begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.external.push
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
operator|.
name|push
package|;
end_package

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
name|ButtonBarBuilder
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
name|IconTheme
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
name|gui
operator|.
name|actions
operator|.
name|MnemonicAwareAction
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
name|awt
operator|.
name|event
operator|.
name|MouseAdapter
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
name|MouseEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * Customized UI component for pushing to external applications. Has a selection popup  * menu to change the selected external application.  * This class implements the ActionListener interface. When actionPerformed() is  * invoked, the currently selected PushToApplication is activated. The actionPerformed()  * method can be called with a null argument.  */
end_comment

begin_class
DECL|class|PushToApplicationButton
specifier|public
class|class
name|PushToApplicationButton
implements|implements
name|ActionListener
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|pushActions
specifier|private
specifier|final
name|List
argument_list|<
name|PushToApplication
argument_list|>
name|pushActions
decl_stmt|;
DECL|field|comp
specifier|private
name|JPanel
name|comp
decl_stmt|;
DECL|field|pushButton
specifier|private
name|JButton
name|pushButton
decl_stmt|;
DECL|field|menuButton
specifier|private
name|JButton
name|menuButton
decl_stmt|;
DECL|field|selected
specifier|private
name|int
name|selected
decl_stmt|;
DECL|field|popup
specifier|private
name|JPopupMenu
name|popup
decl_stmt|;
DECL|field|actions
specifier|private
specifier|final
name|HashMap
argument_list|<
name|PushToApplication
argument_list|,
name|PushToApplicationAction
argument_list|>
name|actions
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|buttonDim
specifier|private
specifier|final
name|Dimension
name|buttonDim
init|=
operator|new
name|Dimension
argument_list|(
literal|23
argument_list|,
literal|23
argument_list|)
decl_stmt|;
DECL|field|ARROW_ICON
specifier|private
specifier|static
specifier|final
name|Icon
name|ARROW_ICON
init|=
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|DOWN
operator|.
name|getSmallIcon
argument_list|()
decl_stmt|;
DECL|field|mAction
specifier|private
specifier|final
name|MenuAction
name|mAction
init|=
operator|new
name|MenuAction
argument_list|()
decl_stmt|;
DECL|field|optPopup
specifier|private
specifier|final
name|JPopupMenu
name|optPopup
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|field|settings
specifier|private
specifier|final
name|JMenuItem
name|settings
init|=
operator|new
name|JMenuItem
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|)
decl_stmt|;
DECL|method|PushToApplicationButton (JabRefFrame frame, List<PushToApplication> pushActions)
specifier|public
name|PushToApplicationButton
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|List
argument_list|<
name|PushToApplication
argument_list|>
name|pushActions
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|pushActions
operator|=
name|pushActions
expr_stmt|;
name|init
argument_list|()
expr_stmt|;
block|}
DECL|method|init ()
specifier|private
name|void
name|init
parameter_list|()
block|{
name|comp
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|comp
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|menuButton
operator|=
operator|new
name|JButton
argument_list|(
name|PushToApplicationButton
operator|.
name|ARROW_ICON
argument_list|)
expr_stmt|;
name|menuButton
operator|.
name|setMargin
argument_list|(
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
argument_list|)
expr_stmt|;
name|menuButton
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
name|menuButton
operator|.
name|getIcon
argument_list|()
operator|.
name|getIconWidth
argument_list|()
argument_list|,
name|menuButton
operator|.
name|getIcon
argument_list|()
operator|.
name|getIconHeight
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|menuButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|MenuButtonActionListener
argument_list|()
argument_list|)
expr_stmt|;
name|menuButton
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select external application"
argument_list|)
argument_list|)
expr_stmt|;
name|pushButton
operator|=
operator|new
name|JButton
argument_list|()
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
literal|"pushToApplication"
argument_list|)
condition|)
block|{
name|String
name|appSelected
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"pushToApplication"
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|pushActions
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|PushToApplication
name|toApp
init|=
name|pushActions
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|toApp
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|appSelected
argument_list|)
condition|)
block|{
name|selected
operator|=
name|i
expr_stmt|;
break|break;
block|}
block|}
block|}
name|setSelected
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|addMouseListener
argument_list|(
operator|new
name|PushButtonMouseListener
argument_list|()
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|menuButton
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|comp
operator|.
name|setOpaque
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|comp
operator|.
name|add
argument_list|(
name|pushButton
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|comp
operator|.
name|add
argument_list|(
name|menuButton
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
comment|//comp.setBorder(BorderFactory.createLineBorder(Color.gray));
name|comp
operator|.
name|setMaximumSize
argument_list|(
name|comp
operator|.
name|getPreferredSize
argument_list|()
argument_list|)
expr_stmt|;
name|optPopup
operator|.
name|add
argument_list|(
name|settings
argument_list|)
expr_stmt|;
name|settings
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|PushToApplication
name|toApp
init|=
name|pushActions
operator|.
name|get
argument_list|(
name|selected
argument_list|)
decl_stmt|;
name|JPanel
name|options
init|=
name|toApp
operator|.
name|getSettingsPanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|options
operator|!=
literal|null
condition|)
block|{
name|PushToApplicationButton
operator|.
name|showSettingsDialog
argument_list|(
name|frame
argument_list|,
name|toApp
argument_list|,
name|options
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|buildPopupMenu
argument_list|()
expr_stmt|;
block|}
comment|/**      * Create a selection menu for the available "Push" options.      */
DECL|method|buildPopupMenu ()
specifier|private
name|void
name|buildPopupMenu
parameter_list|()
block|{
name|popup
operator|=
operator|new
name|JPopupMenu
argument_list|()
expr_stmt|;
name|int
name|j
init|=
literal|0
decl_stmt|;
for|for
control|(
name|PushToApplication
name|application
range|:
name|pushActions
control|)
block|{
name|JMenuItem
name|item
init|=
operator|new
name|JMenuItem
argument_list|(
name|application
operator|.
name|getApplicationName
argument_list|()
argument_list|,
name|application
operator|.
name|getIcon
argument_list|()
argument_list|)
decl_stmt|;
name|item
operator|.
name|setToolTipText
argument_list|(
name|application
operator|.
name|getTooltip
argument_list|()
argument_list|)
expr_stmt|;
name|item
operator|.
name|addActionListener
argument_list|(
operator|new
name|PopupItemActionListener
argument_list|(
name|j
argument_list|)
argument_list|)
expr_stmt|;
name|popup
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
name|j
operator|++
expr_stmt|;
block|}
block|}
comment|/**      * Update the PushButton to default to the given application.      * @param i The List index of the application to default to.      */
DECL|method|setSelected (int i)
specifier|private
name|void
name|setSelected
parameter_list|(
name|int
name|i
parameter_list|)
block|{
name|this
operator|.
name|selected
operator|=
name|i
expr_stmt|;
name|PushToApplication
name|toApp
init|=
name|pushActions
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|pushButton
operator|.
name|setIcon
argument_list|(
name|toApp
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|setToolTipText
argument_list|(
name|toApp
operator|.
name|getTooltip
argument_list|()
argument_list|)
expr_stmt|;
name|pushButton
operator|.
name|setPreferredSize
argument_list|(
name|buttonDim
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"pushToApplication"
argument_list|,
name|toApp
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|mAction
operator|.
name|setTitle
argument_list|(
name|toApp
operator|.
name|getApplicationName
argument_list|()
argument_list|)
expr_stmt|;
name|mAction
operator|.
name|setIcon
argument_list|(
name|toApp
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get the toolbar component for the push button.      * @return The component.      */
DECL|method|getComponent ()
specifier|public
name|Component
name|getComponent
parameter_list|()
block|{
return|return
name|comp
return|;
block|}
DECL|method|getMenuAction ()
specifier|public
name|Action
name|getMenuAction
parameter_list|()
block|{
return|return
name|mAction
return|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|PushToApplication
name|toApp
init|=
name|pushActions
operator|.
name|get
argument_list|(
name|selected
argument_list|)
decl_stmt|;
comment|// Lazy initialization of the push action:
name|PushToApplicationAction
name|action
init|=
name|actions
operator|.
name|get
argument_list|(
name|toApp
argument_list|)
decl_stmt|;
if|if
condition|(
name|action
operator|==
literal|null
condition|)
block|{
name|action
operator|=
operator|new
name|PushToApplicationAction
argument_list|(
name|frame
argument_list|,
name|toApp
argument_list|)
expr_stmt|;
name|actions
operator|.
name|put
argument_list|(
name|toApp
argument_list|,
name|action
argument_list|)
expr_stmt|;
block|}
name|action
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|toApp
argument_list|,
literal|0
argument_list|,
literal|"push"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|class|BooleanHolder
specifier|static
class|class
name|BooleanHolder
block|{
DECL|method|BooleanHolder (boolean value)
specifier|public
name|BooleanHolder
parameter_list|(
name|boolean
name|value
parameter_list|)
block|{
name|this
operator|.
name|value
operator|=
name|value
expr_stmt|;
block|}
DECL|field|value
specifier|public
name|boolean
name|value
decl_stmt|;
block|}
DECL|method|showSettingsDialog (Object parent, PushToApplication toApp, JPanel options)
specifier|public
specifier|static
name|void
name|showSettingsDialog
parameter_list|(
name|Object
name|parent
parameter_list|,
name|PushToApplication
name|toApp
parameter_list|,
name|JPanel
name|options
parameter_list|)
block|{
specifier|final
name|BooleanHolder
name|okPressed
init|=
operator|new
name|BooleanHolder
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|JDialog
name|dg
decl_stmt|;
if|if
condition|(
name|parent
operator|instanceof
name|JDialog
condition|)
block|{
name|dg
operator|=
operator|new
name|JDialog
argument_list|(
operator|(
name|JDialog
operator|)
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dg
operator|=
operator|new
name|JDialog
argument_list|(
operator|(
name|JFrame
operator|)
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
specifier|final
name|JDialog
name|diag
init|=
name|dg
decl_stmt|;
name|options
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
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|options
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
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
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
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
name|bb
operator|.
name|getPanel
argument_list|()
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
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|okPressed
operator|.
name|value
operator|=
literal|true
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|bb
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|bb
operator|.
name|getPanel
argument_list|()
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
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
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
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|4839826710086306753L
decl_stmt|;
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
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
if|if
condition|(
name|parent
operator|instanceof
name|JDialog
condition|)
block|{
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
operator|(
name|JDialog
operator|)
name|parent
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
operator|(
name|JFrame
operator|)
name|parent
argument_list|)
expr_stmt|;
block|}
comment|// Show the dialog:
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|// If the user pressed Ok, ask the PushToApplication implementation
comment|// to store its settings:
if|if
condition|(
name|okPressed
operator|.
name|value
condition|)
block|{
name|toApp
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|PopupItemActionListener
class|class
name|PopupItemActionListener
implements|implements
name|ActionListener
block|{
DECL|field|index
specifier|private
specifier|final
name|int
name|index
decl_stmt|;
DECL|method|PopupItemActionListener (int index)
specifier|public
name|PopupItemActionListener
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|this
operator|.
name|index
operator|=
name|index
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
comment|// Change the selection:
name|setSelected
argument_list|(
name|index
argument_list|)
expr_stmt|;
comment|// Invoke the selected operation (is that expected behaviour?):
comment|//PushToApplicationButton.this.actionPerformed(null);
comment|// It makes sense to transfer focus to the push button after the
comment|// menu closes:
name|pushButton
operator|.
name|requestFocus
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|MenuButtonActionListener
specifier|private
class|class
name|MenuButtonActionListener
implements|implements
name|ActionListener
block|{
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
comment|// Lazy initialization of the popup menu:
if|if
condition|(
name|popup
operator|==
literal|null
condition|)
block|{
name|buildPopupMenu
argument_list|()
expr_stmt|;
block|}
name|popup
operator|.
name|show
argument_list|(
name|comp
argument_list|,
literal|0
argument_list|,
name|menuButton
operator|.
name|getHeight
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|MenuAction
class|class
name|MenuAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
operator|-
literal|4339280220347418559L
decl_stmt|;
DECL|method|MenuAction ()
specifier|public
name|MenuAction
parameter_list|()
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Push to application"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setTitle (String appName)
specifier|public
name|void
name|setTitle
parameter_list|(
name|String
name|appName
parameter_list|)
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Push entries to external application (%0)"
argument_list|,
name|appName
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|PushToApplicationButton
operator|.
name|this
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
DECL|method|setIcon (Icon icon)
specifier|public
name|void
name|setIcon
parameter_list|(
name|Icon
name|icon
parameter_list|)
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|SMALL_ICON
argument_list|,
name|icon
argument_list|)
expr_stmt|;
block|}
block|}
DECL|class|PushButtonMouseListener
class|class
name|PushButtonMouseListener
extends|extends
name|MouseAdapter
block|{
annotation|@
name|Override
DECL|method|mousePressed (MouseEvent event)
specifier|public
name|void
name|mousePressed
parameter_list|(
name|MouseEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|mouseClicked (MouseEvent event)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|mouseReleased (MouseEvent event)
specifier|public
name|void
name|mouseReleased
parameter_list|(
name|MouseEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|event
operator|.
name|isPopupTrigger
argument_list|()
condition|)
block|{
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|processPopupTrigger (MouseEvent e)
specifier|private
name|void
name|processPopupTrigger
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
comment|// We only want to show the popup if a settings panel exists for the selected
comment|// item:
name|PushToApplication
name|toApp
init|=
name|pushActions
operator|.
name|get
argument_list|(
name|selected
argument_list|)
decl_stmt|;
if|if
condition|(
name|toApp
operator|.
name|getSettingsPanel
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|optPopup
operator|.
name|show
argument_list|(
name|pushButton
argument_list|,
name|e
operator|.
name|getX
argument_list|()
argument_list|,
name|e
operator|.
name|getY
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

