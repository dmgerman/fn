begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|GUIGlobals
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
name|plugin
operator|.
name|PluginCore
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
name|plugin
operator|.
name|core
operator|.
name|JabRefPlugin
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
name|plugin
operator|.
name|core
operator|.
name|generated
operator|.
name|_JabRefPlugin
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
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
DECL|field|applications
specifier|public
specifier|static
name|List
argument_list|<
name|PushToApplication
argument_list|>
name|applications
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|pushActions
specifier|private
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
DECL|field|menuButton
specifier|private
name|JButton
name|pushButton
decl_stmt|,
name|menuButton
decl_stmt|;
DECL|field|selected
specifier|private
name|int
name|selected
init|=
literal|0
decl_stmt|;
DECL|field|popup
specifier|private
name|JPopupMenu
name|popup
init|=
literal|null
decl_stmt|;
DECL|field|actions
specifier|private
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
argument_list|<
name|PushToApplication
argument_list|,
name|PushToApplicationAction
argument_list|>
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
name|URL
name|ARROW_ICON
init|=
name|GUIGlobals
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"/images/secondary_sorted_reverse.png"
argument_list|)
decl_stmt|;
DECL|field|mAction
specifier|private
name|MenuAction
name|mAction
init|=
operator|new
name|MenuAction
argument_list|()
decl_stmt|;
DECL|field|optPopup
specifier|private
name|JPopupMenu
name|optPopup
init|=
operator|new
name|JPopupMenu
argument_list|()
decl_stmt|;
DECL|field|settings
specifier|private
name|JMenuItem
name|settings
init|=
operator|new
name|JMenuItem
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|)
decl_stmt|;
comment|/**      * Set up the current available choices:      */
static|static
block|{
name|applications
operator|=
operator|new
name|ArrayList
argument_list|<
name|PushToApplication
argument_list|>
argument_list|()
expr_stmt|;
name|JabRefPlugin
name|jabrefPlugin
init|=
name|JabRefPlugin
operator|.
name|getInstance
argument_list|(
name|PluginCore
operator|.
name|getManager
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|jabrefPlugin
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|_JabRefPlugin
operator|.
name|PushToApplicationExtension
argument_list|>
name|plugins
init|=
name|jabrefPlugin
operator|.
name|getPushToApplicationExtensions
argument_list|()
decl_stmt|;
for|for
control|(
name|_JabRefPlugin
operator|.
name|PushToApplicationExtension
name|extension
range|:
name|plugins
control|)
block|{
name|applications
operator|.
name|add
argument_list|(
name|extension
operator|.
name|getPushToApp
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToLyx
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToEmacs
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToWinEdt
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToLatexEditor
argument_list|()
argument_list|)
expr_stmt|;
name|applications
operator|.
name|add
argument_list|(
operator|new
name|PushToVim
argument_list|()
argument_list|)
expr_stmt|;
comment|// Finally, sort the entries:
comment|//Collections.sort(applications, new PushToApplicationComparator());
block|}
block|}
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
operator|new
name|ImageIcon
argument_list|(
name|ARROW_ICON
argument_list|)
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
name|Globals
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
name|comp
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createLineBorder
argument_list|(
name|Color
operator|.
name|gray
argument_list|)
argument_list|)
expr_stmt|;
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
else|else
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
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
argument_list|(
name|Globals
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
name|addGridded
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
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
else|else
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
class|class
name|MenuButtonActionListener
implements|implements
name|ActionListener
block|{
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
name|buildPopupMenu
argument_list|()
expr_stmt|;
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
DECL|method|MenuAction ()
specifier|public
name|MenuAction
parameter_list|()
block|{
name|putValue
argument_list|(
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
name|NAME
argument_list|,
name|Globals
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
block|}
DECL|class|PushButtonMouseListener
class|class
name|PushButtonMouseListener
extends|extends
name|MouseAdapter
block|{
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
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
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
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
block|}
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
name|processPopupTrigger
argument_list|(
name|event
argument_list|)
expr_stmt|;
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
comment|/**      * Comparator for sorting the selection according to name.      */
DECL|class|PushToApplicationComparator
specifier|static
class|class
name|PushToApplicationComparator
implements|implements
name|Comparator
argument_list|<
name|PushToApplication
argument_list|>
block|{
DECL|method|compare (PushToApplication one, PushToApplication two)
specifier|public
name|int
name|compare
parameter_list|(
name|PushToApplication
name|one
parameter_list|,
name|PushToApplication
name|two
parameter_list|)
block|{
return|return
name|one
operator|.
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|two
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

