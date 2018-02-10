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

begin_comment
comment|/**  * Such an error console can be  * useful in getting complete bug reports, especially from Windows users,  * without asking users to run JabRef in a command window to catch the error info.  *<p/>  * It offers a separate tab for the log output.  */
end_comment

begin_class
DECL|class|ErrorConsoleAction
specifier|public
class|class
name|ErrorConsoleAction
extends|extends
name|SimpleCommand
block|{
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
operator|new
name|ErrorConsoleView
argument_list|()
operator|.
name|show
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

