begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

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
name|Action
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|application
operator|.
name|Platform
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
name|errorconsole
operator|.
name|ErrorConsoleView
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
DECL|method|ErrorConsoleAction ()
specifier|public
name|ErrorConsoleAction
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"View event log"
argument_list|)
argument_list|)
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
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
operator|new
name|ErrorConsoleView
argument_list|()
operator|.
name|show
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

