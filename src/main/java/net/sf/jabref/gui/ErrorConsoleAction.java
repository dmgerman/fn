begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|error
operator|.
name|StreamEavesdropper
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|logging
operator|.
name|CacheableHandler
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
name|JFrame
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTabbedPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextArea
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

begin_comment
comment|/**  * Such an error console can be  * useful in getting complete bug reports, especially from Windows users,  * without asking users to run JabRef in a command window to catch the error info.  *<p/>  * It offers a separate tab for the log output.  */
end_comment

begin_class
DECL|class|ErrorConsoleAction
specifier|public
class|class
name|ErrorConsoleAction
extends|extends
name|AbstractAction
block|{
DECL|field|frame
specifier|private
specifier|final
name|JFrame
name|frame
decl_stmt|;
DECL|field|streamEavesdropper
specifier|private
specifier|final
name|StreamEavesdropper
name|streamEavesdropper
decl_stmt|;
DECL|field|logHandler
specifier|private
specifier|final
name|CacheableHandler
name|logHandler
decl_stmt|;
DECL|method|ErrorConsoleAction (JFrame frame, StreamEavesdropper streamEavesdropper, CacheableHandler logHandler)
specifier|public
name|ErrorConsoleAction
parameter_list|(
name|JFrame
name|frame
parameter_list|,
name|StreamEavesdropper
name|streamEavesdropper
parameter_list|,
name|CacheableHandler
name|logHandler
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Show error console"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|streamEavesdropper
operator|=
name|streamEavesdropper
expr_stmt|;
name|this
operator|.
name|logHandler
operator|=
name|logHandler
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Display all error messages"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
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
name|displayErrorConsole
argument_list|(
name|frame
argument_list|)
expr_stmt|;
block|}
DECL|method|displayErrorConsole (JFrame parent)
specifier|private
name|void
name|displayErrorConsole
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|JTabbedPane
name|tabbed
init|=
operator|new
name|JTabbedPane
argument_list|()
decl_stmt|;
name|addTextArea
argument_list|(
name|tabbed
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Output"
argument_list|)
argument_list|,
name|streamEavesdropper
operator|.
name|getOutput
argument_list|()
argument_list|)
expr_stmt|;
name|addTextArea
argument_list|(
name|tabbed
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Exceptions"
argument_list|)
argument_list|,
name|streamEavesdropper
operator|.
name|getErrorMessages
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No exceptions have ocurred."
argument_list|)
argument_list|)
expr_stmt|;
name|addTextArea
argument_list|(
name|tabbed
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Log"
argument_list|)
argument_list|,
name|logHandler
operator|.
name|getLog
argument_list|()
argument_list|)
expr_stmt|;
name|tabbed
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|500
argument_list|,
literal|500
argument_list|)
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
name|tabbed
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Program output"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param tabbed  the tabbed pane to add the tab to      * @param output  the text to display in the tab      * @param ifEmpty Text to output if textbox is emtpy. may be null      */
DECL|method|addTextArea (JTabbedPane tabbed, String title, String output, String ifEmpty)
specifier|private
name|void
name|addTextArea
parameter_list|(
name|JTabbedPane
name|tabbed
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|output
parameter_list|,
name|String
name|ifEmpty
parameter_list|)
block|{
name|JTextArea
name|ta
init|=
operator|new
name|JTextArea
argument_list|(
name|output
argument_list|)
decl_stmt|;
name|ta
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|ifEmpty
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|ta
operator|.
name|getText
argument_list|()
operator|.
name|isEmpty
argument_list|()
operator|)
condition|)
block|{
name|ta
operator|.
name|setText
argument_list|(
name|ifEmpty
argument_list|)
expr_stmt|;
block|}
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|ta
argument_list|)
decl_stmt|;
name|tabbed
operator|.
name|addTab
argument_list|(
name|title
argument_list|,
name|sp
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param tabbed the tabbed pane to add the tab to      * @param output the text to display in the tab      */
DECL|method|addTextArea (JTabbedPane tabbed, String title, String output)
specifier|private
name|void
name|addTextArea
parameter_list|(
name|JTabbedPane
name|tabbed
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|output
parameter_list|)
block|{
name|addTextArea
argument_list|(
name|tabbed
argument_list|,
name|title
argument_list|,
name|output
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

