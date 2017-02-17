begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
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
name|BorderFactory
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
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JProgressBar
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Timer
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

begin_comment
comment|/**  * Dialog shown when closing of application needs to wait for a save operation to finish.  */
end_comment

begin_class
DECL|class|WaitForSaveOperation
specifier|public
class|class
name|WaitForSaveOperation
implements|implements
name|ActionListener
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|diag
specifier|private
specifier|final
name|JDialog
name|diag
decl_stmt|;
DECL|field|t
specifier|private
specifier|final
name|Timer
name|t
init|=
operator|new
name|Timer
argument_list|(
literal|500
argument_list|,
name|this
argument_list|)
decl_stmt|;
DECL|field|canceled
specifier|private
name|boolean
name|canceled
decl_stmt|;
DECL|method|WaitForSaveOperation (JabRefFrame frame)
specifier|public
name|WaitForSaveOperation
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
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
name|JProgressBar
name|prog
init|=
operator|new
name|JProgressBar
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|prog
operator|.
name|setIndeterminate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|prog
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
operator|=
operator|new
name|JDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please wait..."
argument_list|)
argument_list|,
literal|true
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
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
name|t
operator|.
name|stop
argument_list|()
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|JLabel
name|message
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Waiting for save operation to finish"
argument_list|)
operator|+
literal|"..."
argument_list|)
decl_stmt|;
name|message
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
name|message
argument_list|,
name|BorderLayout
operator|.
name|NORTH
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
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|prog
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
block|}
DECL|method|show ()
specifier|public
name|void
name|show
parameter_list|()
block|{
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|t
operator|.
name|start
argument_list|()
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
name|canceled
return|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent actionEvent)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|boolean
name|anySaving
init|=
literal|false
decl_stmt|;
for|for
control|(
name|BasePanel
name|basePanel
range|:
name|frame
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
if|if
condition|(
name|basePanel
operator|.
name|isSaving
argument_list|()
condition|)
block|{
name|anySaving
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|anySaving
condition|)
block|{
name|t
operator|.
name|stop
argument_list|()
expr_stmt|;
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
