begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
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
name|ButtonGroup
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
name|JRadioButton
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
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
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
name|org
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
name|org
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
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
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_comment
comment|/**  * Dialog for adding citation with page number info.  */
end_comment

begin_class
DECL|class|AdvancedCiteDialog
class|class
name|AdvancedCiteDialog
block|{
DECL|field|defaultInPar
specifier|private
specifier|static
name|boolean
name|defaultInPar
init|=
literal|true
decl_stmt|;
DECL|field|okPressed
specifier|private
name|boolean
name|okPressed
decl_stmt|;
DECL|field|diag
specifier|private
specifier|final
name|JDialog
name|diag
decl_stmt|;
DECL|field|pageInfo
specifier|private
specifier|final
name|JTextField
name|pageInfo
init|=
operator|new
name|JTextField
argument_list|(
literal|15
argument_list|)
decl_stmt|;
DECL|method|AdvancedCiteDialog (JabRefFrame parent)
specifier|public
name|AdvancedCiteDialog
parameter_list|(
name|JabRefFrame
name|parent
parameter_list|)
block|{
name|diag
operator|=
operator|new
name|JDialog
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cite special"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|JRadioButton
name|inPar
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cite selected entries between parenthesis"
argument_list|)
argument_list|)
decl_stmt|;
name|JRadioButton
name|inText
init|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cite selected entries with in-text citation"
argument_list|)
argument_list|)
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|inPar
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|inText
argument_list|)
expr_stmt|;
if|if
condition|(
name|defaultInPar
condition|)
block|{
name|inPar
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|inText
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|inPar
operator|.
name|addChangeListener
argument_list|(
name|changeEvent
lambda|->
name|defaultInPar
operator|=
name|inPar
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref"
argument_list|,
literal|"pref, 4dlu, pref, 4dlu, pref"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|inPar
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|inText
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Extra information (e.g. page number)"
argument_list|)
operator|+
literal|":"
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|pageInfo
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|padding
argument_list|(
literal|"10dlu, 10dlu, 10dlu, 10dlu"
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
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
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
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
literal|"OK"
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
name|padding
argument_list|(
literal|"5dlu, 5dlu, 5dlu, 5dlu"
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
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|ActionListener
name|okAction
init|=
name|actionEvent
lambda|->
block|{
name|okPressed
operator|=
literal|true
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
name|pageInfo
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
name|inPar
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
name|inText
operator|.
name|addActionListener
argument_list|(
name|okAction
argument_list|)
expr_stmt|;
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
name|actionEvent
parameter_list|)
block|{
name|okPressed
operator|=
literal|false
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|cancelAction
argument_list|)
expr_stmt|;
name|builder
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
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|cancelAction
argument_list|)
expr_stmt|;
block|}
DECL|method|showDialog ()
specifier|public
name|void
name|showDialog
parameter_list|()
block|{
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|diag
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|canceled ()
specifier|public
name|boolean
name|canceled
parameter_list|()
block|{
return|return
operator|!
name|okPressed
return|;
block|}
DECL|method|getPageInfo ()
specifier|public
name|String
name|getPageInfo
parameter_list|()
block|{
return|return
name|pageInfo
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
return|;
block|}
DECL|method|isInParenthesisCite ()
specifier|public
name|boolean
name|isInParenthesisCite
parameter_list|()
block|{
return|return
name|defaultInPar
return|;
block|}
block|}
end_class

end_unit

