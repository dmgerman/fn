begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
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
name|Globals
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
name|io
operator|.
name|PrintStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayOutputStream
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

begin_comment
comment|/**  * This class redirects the System.err stream so it goes both the way it normally  * goes, and into a ByteArrayOutputStream. We can use this stream to display any  * error messages and stack traces to the user. Such an error console can be  * useful in getting complete bug reports, especially from Windows users,  * without asking users to run JabRef in a command window to catch the error info.  *  * User: alver  * Date: Mar 1, 2006  * Time: 11:13:03 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|ErrorConsole
specifier|public
class|class
name|ErrorConsole
block|{
DECL|field|byteStream
name|ByteArrayOutputStream
name|byteStream
init|=
operator|new
name|ByteArrayOutputStream
argument_list|()
decl_stmt|;
DECL|field|instance
specifier|private
specifier|static
name|ErrorConsole
name|instance
init|=
literal|null
decl_stmt|;
DECL|method|getInstance ()
specifier|public
specifier|static
name|ErrorConsole
name|getInstance
parameter_list|()
block|{
if|if
condition|(
name|instance
operator|==
literal|null
condition|)
name|instance
operator|=
operator|new
name|ErrorConsole
argument_list|()
expr_stmt|;
return|return
name|instance
return|;
block|}
DECL|method|ErrorConsole ()
specifier|private
name|ErrorConsole
parameter_list|()
block|{
name|PrintStream
name|myErr
init|=
operator|new
name|PrintStream
argument_list|(
name|byteStream
argument_list|)
decl_stmt|;
name|PrintStream
name|tee
init|=
operator|new
name|TeeStream
argument_list|(
name|System
operator|.
name|out
argument_list|,
name|myErr
argument_list|)
decl_stmt|;
name|System
operator|.
name|setErr
argument_list|(
name|tee
argument_list|)
expr_stmt|;
block|}
DECL|method|getErrorMessages ()
specifier|public
name|String
name|getErrorMessages
parameter_list|()
block|{
return|return
name|byteStream
operator|.
name|toString
argument_list|()
return|;
block|}
DECL|method|displayErrorConsole (JFrame parent)
specifier|public
name|void
name|displayErrorConsole
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|JTextArea
name|ta
init|=
operator|new
name|JTextArea
argument_list|(
name|getErrorMessages
argument_list|()
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
name|ta
operator|.
name|getText
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
block|{
name|ta
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No exceptions have ocurred."
argument_list|)
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
name|sp
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
name|sp
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error messages"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
DECL|class|ErrorConsoleAction
class|class
name|ErrorConsoleAction
extends|extends
name|AbstractAction
block|{
DECL|field|frame
name|JFrame
name|frame
decl_stmt|;
DECL|method|ErrorConsoleAction (JFrame frame)
specifier|public
name|ErrorConsoleAction
parameter_list|(
name|JFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|menuTitle
argument_list|(
literal|"Show error console"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|Globals
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
block|}
DECL|method|getAction (JFrame parent)
specifier|public
name|AbstractAction
name|getAction
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
return|return
operator|new
name|ErrorConsoleAction
argument_list|(
name|parent
argument_list|)
return|;
block|}
comment|// All writes to this print stream are copied to two print streams
DECL|class|TeeStream
specifier|public
class|class
name|TeeStream
extends|extends
name|PrintStream
block|{
DECL|field|out
name|PrintStream
name|out
decl_stmt|;
DECL|method|TeeStream (PrintStream out1, PrintStream out2)
specifier|public
name|TeeStream
parameter_list|(
name|PrintStream
name|out1
parameter_list|,
name|PrintStream
name|out2
parameter_list|)
block|{
name|super
argument_list|(
name|out1
argument_list|)
expr_stmt|;
name|this
operator|.
name|out
operator|=
name|out2
expr_stmt|;
block|}
DECL|method|write (byte buf[], int off, int len)
specifier|public
name|void
name|write
parameter_list|(
name|byte
name|buf
index|[]
parameter_list|,
name|int
name|off
parameter_list|,
name|int
name|len
parameter_list|)
block|{
try|try
block|{
name|super
operator|.
name|write
argument_list|(
name|buf
argument_list|,
name|off
argument_list|,
name|len
argument_list|)
expr_stmt|;
name|out
operator|.
name|write
argument_list|(
name|buf
argument_list|,
name|off
argument_list|,
name|len
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{             }
block|}
DECL|method|flush ()
specifier|public
name|void
name|flush
parameter_list|()
block|{
name|super
operator|.
name|flush
argument_list|()
expr_stmt|;
name|out
operator|.
name|flush
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

