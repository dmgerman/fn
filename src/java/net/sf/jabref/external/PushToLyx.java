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
name|java
operator|.
name|io
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
name|*
import|;
end_import

begin_class
DECL|class|PushToLyx
specifier|public
class|class
name|PushToLyx
extends|extends
name|BaseAction
block|{
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|method|PushToLyx (BasePanel panel)
specifier|public
name|PushToLyx
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
specifier|final
name|BibtexEntry
index|[]
name|entries
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
if|if
condition|(
name|entries
operator|==
literal|null
condition|)
return|return;
specifier|final
name|int
name|numSelected
init|=
name|entries
operator|.
name|length
decl_stmt|;
comment|// Globals.logger("Pushing " +numSelected+(numSelected>1? " entries" : "entry") + " to LyX");
comment|// check if lyxpipe is defined
specifier|final
name|File
name|lyxpipe
init|=
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
operator|+
literal|".in"
argument_list|)
decl_stmt|;
comment|// this needs to fixed because it gives "asdf" when going prefs.get("lyxpipe")
if|if
condition|(
operator|!
name|lyxpipe
operator|.
name|exists
argument_list|()
operator|||
operator|!
name|lyxpipe
operator|.
name|canWrite
argument_list|()
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"verify that LyX is running and that the lyxpipe is valid"
argument_list|)
operator|+
literal|". ["
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
operator|+
literal|"]"
argument_list|)
expr_stmt|;
return|return;
block|}
comment|//Util.pr("tre");
if|if
condition|(
name|numSelected
operator|>
literal|0
condition|)
block|{
name|Thread
name|pushThread
init|=
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|FileWriter
name|fw
init|=
operator|new
name|FileWriter
argument_list|(
name|lyxpipe
argument_list|)
decl_stmt|;
name|BufferedWriter
name|lyx_out
init|=
operator|new
name|BufferedWriter
argument_list|(
name|fw
argument_list|)
decl_stmt|;
name|String
name|citeStr
init|=
literal|""
decl_stmt|,
name|citeKey
init|=
literal|""
decl_stmt|,
name|message
init|=
literal|""
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
name|numSelected
condition|;
name|i
operator|++
control|)
block|{
name|BibtexEntry
name|bes
init|=
name|entries
index|[
name|i
index|]
decl_stmt|;
comment|//database.getEntryById(tableModel.getNameFromNumber(rows[
comment|//													      i]));
name|citeKey
operator|=
operator|(
name|String
operator|)
name|bes
operator|.
name|getField
argument_list|(
name|GUIGlobals
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
comment|// if the key is empty we give a warning and ignore this entry
if|if
condition|(
name|citeKey
operator|==
literal|null
operator|||
name|citeKey
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
continue|continue;
if|if
condition|(
name|citeStr
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|citeStr
operator|=
name|citeKey
expr_stmt|;
else|else
name|citeStr
operator|+=
literal|","
operator|+
name|citeKey
expr_stmt|;
if|if
condition|(
name|i
operator|>
literal|0
condition|)
name|message
operator|+=
literal|", "
expr_stmt|;
comment|//message += (1 + rows[i]);
block|}
if|if
condition|(
name|citeStr
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Please define BibTeX key first"
argument_list|)
argument_list|)
expr_stmt|;
else|else
block|{
name|citeStr
operator|=
literal|"LYXCMD:sampleclient:citation-insert:"
operator|+
name|citeStr
expr_stmt|;
name|lyx_out
operator|.
name|write
argument_list|(
name|citeStr
operator|+
literal|"\n"
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Pushed the citations for the following rows to"
argument_list|)
operator|+
literal|" Lyx: "
operator|+
name|message
argument_list|)
expr_stmt|;
block|}
name|lyx_out
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|excep
parameter_list|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"unable to write to"
argument_list|)
operator|+
literal|" "
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
operator|+
literal|".in"
argument_list|)
expr_stmt|;
block|}
comment|// catch (InterruptedException e2) {}
block|}
block|}
decl_stmt|;
name|pushThread
operator|.
name|start
argument_list|()
expr_stmt|;
name|Timeout
name|t
init|=
operator|new
name|Timeout
argument_list|(
literal|2000
argument_list|,
name|pushThread
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"unable to access LyX-pipe"
argument_list|)
argument_list|)
decl_stmt|;
name|t
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|Timeout
class|class
name|Timeout
extends|extends
name|javax
operator|.
name|swing
operator|.
name|Timer
block|{
DECL|method|Timeout (int timeout, final Thread toStop, final String message)
specifier|public
name|Timeout
parameter_list|(
name|int
name|timeout
parameter_list|,
specifier|final
name|Thread
name|toStop
parameter_list|,
specifier|final
name|String
name|message
parameter_list|)
block|{
name|super
argument_list|(
name|timeout
argument_list|,
operator|new
name|ActionListener
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
name|toStop
operator|.
name|stop
argument_list|()
expr_stmt|;
comment|// !!!<- deprecated
comment|// toStop.interrupt(); // better ?, interrupts wait and IO
comment|//stop();
comment|//output(message);
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

