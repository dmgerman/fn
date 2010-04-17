begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
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
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ImageIcon
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
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|BibtexFields
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
name|FocusRequester
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
name|HelpAction
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
name|SidePaneComponent
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
name|SidePaneManager
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
name|gui
operator|.
name|ImportInspectionDialog
import|;
end_import

begin_comment
comment|/**  *<p>Title:</p>  *<p>Description:</p>  *<p>Copyright: Copyright (c) 2003</p>  *<p>Company:</p>  *  * @author not attributable  * @version 1.0  */
end_comment

begin_class
DECL|class|GeneralFetcher
specifier|public
class|class
name|GeneralFetcher
extends|extends
name|SidePaneComponent
implements|implements
name|ActionListener
block|{
DECL|field|tf
name|JTextField
name|tf
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
DECL|field|pan
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|gbl
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|go
DECL|field|helpBut
name|JButton
name|go
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Fetch"
argument_list|)
argument_list|)
decl_stmt|,
name|helpBut
init|=
operator|new
name|JButton
argument_list|(
DECL|field|reset
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
decl_stmt|,
name|reset
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Reset"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|help
name|HelpAction
name|help
decl_stmt|;
DECL|field|fetcher
name|EntryFetcher
name|fetcher
decl_stmt|;
DECL|field|sidePaneManager
name|SidePaneManager
name|sidePaneManager
decl_stmt|;
DECL|field|action
name|Action
name|action
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|GeneralFetcher (SidePaneManager p0, JabRefFrame frame, final EntryFetcher fetcher)
specifier|public
name|GeneralFetcher
parameter_list|(
name|SidePaneManager
name|p0
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|,
specifier|final
name|EntryFetcher
name|fetcher
parameter_list|)
block|{
name|super
argument_list|(
name|p0
argument_list|,
name|fetcher
operator|.
name|getIcon
argument_list|()
argument_list|,
name|fetcher
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|sidePaneManager
operator|=
name|p0
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|fetcher
operator|=
name|fetcher
expr_stmt|;
name|action
operator|=
operator|new
name|FetcherAction
argument_list|()
expr_stmt|;
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|Globals
operator|.
name|helpDiag
argument_list|,
name|fetcher
operator|.
name|getHelpPage
argument_list|()
argument_list|,
literal|"Help"
argument_list|)
expr_stmt|;
name|helpBut
operator|.
name|addActionListener
argument_list|(
name|help
argument_list|)
expr_stmt|;
name|helpBut
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
name|tf
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|1
argument_list|,
name|tf
operator|.
name|getPreferredSize
argument_list|()
operator|.
name|height
argument_list|)
argument_list|)
expr_stmt|;
name|tf
operator|.
name|setName
argument_list|(
literal|"tf"
argument_list|)
expr_stmt|;
comment|// add action to reset-button. resets tf and requests focus
name|reset
operator|.
name|addActionListener
argument_list|(
operator|new
name|AbstractAction
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
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
name|tf
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|JPanel
name|main
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|2
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|insets
operator|=
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
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|tf
argument_list|)
expr_stmt|;
comment|// Go Button
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|go
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|go
argument_list|)
expr_stmt|;
comment|// Reset Button
if|if
condition|(
name|fetcher
operator|.
name|getHelpPage
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
block|}
else|else
block|{
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
block|}
name|reset
operator|.
name|setName
argument_list|(
literal|"reset"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|reset
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|reset
argument_list|)
expr_stmt|;
comment|// Help Button
if|if
condition|(
name|fetcher
operator|.
name|getHelpPage
argument_list|()
operator|!=
literal|null
condition|)
block|{
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
name|helpBut
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|helpBut
argument_list|)
expr_stmt|;
block|}
name|JPanel
name|pan
init|=
name|fetcher
operator|.
name|getOptionsPanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|pan
operator|!=
literal|null
condition|)
block|{
name|gbl
operator|.
name|setConstraints
argument_list|(
name|pan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|main
operator|.
name|add
argument_list|(
name|pan
argument_list|)
expr_stmt|;
block|}
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|go
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|tf
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|setHelpResourceOwner (Class c)
specifier|public
name|void
name|setHelpResourceOwner
parameter_list|(
name|Class
name|c
parameter_list|)
block|{
name|help
operator|.
name|setResourceOwner
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|getTextField ()
specifier|public
name|JTextField
name|getTextField
parameter_list|()
block|{
return|return
name|tf
return|;
block|}
DECL|method|getAction ()
specifier|public
name|Action
name|getAction
parameter_list|()
block|{
return|return
name|action
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
if|if
condition|(
name|tf
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
specifier|final
name|ImportInspectionDialog
name|dialog
init|=
operator|new
name|ImportInspectionDialog
argument_list|(
name|frame
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
argument_list|,
name|BibtexFields
operator|.
name|DEFAULT_INSPECTION_FIELDS
argument_list|,
name|fetcher
operator|.
name|getTitle
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|addCallBack
argument_list|(
name|fetcher
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|dialog
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
operator|new
name|Thread
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
name|fetcher
operator|.
name|processQuery
argument_list|(
name|tf
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
argument_list|,
name|dialog
argument_list|,
name|frame
argument_list|)
condition|)
block|{
name|dialog
operator|.
name|entryListComplete
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|dialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
DECL|class|FetcherAction
class|class
name|FetcherAction
extends|extends
name|AbstractAction
block|{
DECL|method|FetcherAction ()
specifier|public
name|FetcherAction
parameter_list|()
block|{
name|super
argument_list|(
name|fetcher
operator|.
name|getTitle
argument_list|()
argument_list|,
operator|new
name|ImageIcon
argument_list|(
name|fetcher
operator|.
name|getIcon
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|fetcher
operator|.
name|getKeyName
argument_list|()
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|fetcher
operator|.
name|getKeyName
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|)
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
name|fetcher
operator|.
name|getKeyName
argument_list|()
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
name|String
name|fetcherTitle
init|=
name|fetcher
operator|.
name|getTitle
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|sidePaneManager
operator|.
name|hasComponent
argument_list|(
name|fetcherTitle
argument_list|)
condition|)
block|{
name|sidePaneManager
operator|.
name|register
argument_list|(
name|fetcherTitle
argument_list|,
name|GeneralFetcher
operator|.
name|this
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|sidePaneManager
operator|.
name|toggle
argument_list|(
name|fetcherTitle
argument_list|)
expr_stmt|;
if|if
condition|(
name|sidePaneManager
operator|.
name|isComponentVisible
argument_list|(
name|fetcherTitle
argument_list|)
condition|)
block|{
operator|new
name|FocusRequester
argument_list|(
name|getTextField
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

