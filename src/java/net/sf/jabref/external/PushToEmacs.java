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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
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
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|OutputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Jan 14, 2006  * Time: 4:55:23 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|PushToEmacs
specifier|public
class|class
name|PushToEmacs
implements|implements
name|PushToApplication
block|{
DECL|field|couldNotConnect
DECL|field|couldNotRunClient
specifier|private
name|boolean
name|couldNotConnect
init|=
literal|false
decl_stmt|,
name|couldNotRunClient
init|=
literal|false
decl_stmt|;
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Insert selected citations into Emacs"
argument_list|)
return|;
block|}
DECL|method|getTooltip ()
specifier|public
name|String
name|getTooltip
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Push selection to Emacs"
argument_list|)
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
block|{
return|return
operator|new
name|ImageIcon
argument_list|(
name|GUIGlobals
operator|.
name|emacsIcon
argument_list|)
return|;
block|}
DECL|method|getKeyStrokeName ()
specifier|public
name|String
name|getKeyStrokeName
parameter_list|()
block|{
return|return
literal|"Push to Emacs"
return|;
block|}
DECL|method|pushEntries (BibtexEntry[] entries, String keys)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|,
name|String
name|keys
parameter_list|)
block|{
name|couldNotConnect
operator|=
literal|false
expr_stmt|;
name|couldNotRunClient
operator|=
literal|false
expr_stmt|;
name|StringBuffer
name|command
init|=
operator|new
name|StringBuffer
argument_list|(
literal|"(insert\"\\\\"
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"citeCommand"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"{"
argument_list|)
decl_stmt|;
try|try
block|{
name|command
operator|.
name|append
argument_list|(
name|keys
argument_list|)
expr_stmt|;
name|command
operator|.
name|append
argument_list|(
literal|"}\")"
argument_list|)
expr_stmt|;
name|String
index|[]
name|com
init|=
operator|new
name|String
index|[]
block|{
literal|"gnuclient"
block|,
literal|"-batch"
block|,
literal|"-eval"
block|,
name|command
operator|.
name|toString
argument_list|()
block|}
decl_stmt|;
specifier|final
name|Process
name|p
init|=
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|com
argument_list|)
decl_stmt|;
name|Runnable
name|errorListener
init|=
operator|new
name|Runnable
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|InputStream
name|out
init|=
name|p
operator|.
name|getErrorStream
argument_list|()
decl_stmt|;
name|int
name|c
decl_stmt|;
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
name|c
operator|=
name|out
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
comment|// Error stream has been closed. See if there were any errors:
if|if
condition|(
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|couldNotConnect
operator|=
literal|true
expr_stmt|;
return|return;
block|}
block|}
block|}
decl_stmt|;
name|Thread
name|t
init|=
operator|new
name|Thread
argument_list|(
name|errorListener
argument_list|)
decl_stmt|;
name|t
operator|.
name|start
argument_list|()
expr_stmt|;
name|t
operator|.
name|join
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|excep
parameter_list|)
block|{
name|couldNotRunClient
operator|=
literal|true
expr_stmt|;
return|return;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|operationCompleted (BasePanel panel)
specifier|public
name|void
name|operationCompleted
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
if|if
condition|(
name|couldNotConnect
condition|)
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
literal|"<HTML>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not connect to a running gnuserv process. Make sure that "
operator|+
literal|"Emacs or XEmacs is running,<BR>and that the server has been started "
operator|+
literal|"(by running the command 'gnuserv-start')."
argument_list|)
operator|+
literal|"</HTML>"
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|couldNotRunClient
condition|)
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not run the 'gnuclient' program. Make sure you have "
operator|+
literal|"the gnuserv/gnuclient programs installed."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
else|else
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Pushed citations to Emacs"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|requiresBibtexKeys ()
specifier|public
name|boolean
name|requiresBibtexKeys
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

