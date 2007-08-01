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
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
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
name|*
import|;
end_import

begin_comment
comment|/**  * An Action class representing the process of invoking a PushToApplication operation.  */
end_comment

begin_class
DECL|class|PushToApplicationAction
specifier|public
class|class
name|PushToApplicationAction
extends|extends
name|AbstractAction
implements|implements
name|Runnable
block|{
DECL|field|operation
specifier|private
name|PushToApplication
name|operation
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|entries
specifier|private
name|BibtexEntry
index|[]
name|entries
decl_stmt|;
DECL|method|PushToApplicationAction (JabRefFrame frame, PushToApplication operation)
specifier|public
name|PushToApplicationAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|PushToApplication
name|operation
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|putValue
argument_list|(
name|SMALL_ICON
argument_list|,
name|operation
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
name|operation
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|SHORT_DESCRIPTION
argument_list|,
name|operation
operator|.
name|getTooltip
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|operation
operator|.
name|getKeyStrokeName
argument_list|()
operator|!=
literal|null
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
name|operation
operator|.
name|getKeyStrokeName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|operation
operator|=
name|operation
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
name|panel
operator|=
name|frame
operator|.
name|basePanel
argument_list|()
expr_stmt|;
comment|// Check if a BasePanel exists:
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
return|return;
comment|// Check if any entries are selected:
name|entries
operator|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"This operation requires one or more entries to be selected."
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|getValue
argument_list|(
name|NAME
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// If required, check that all entries have BibTeX keys defined:
if|if
condition|(
name|operation
operator|.
name|requiresBibtexKeys
argument_list|()
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|entries
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|entries
index|[
name|i
index|]
operator|.
name|getCiteKey
argument_list|()
operator|==
literal|null
operator|)
operator|||
operator|(
name|entries
index|[
name|i
index|]
operator|.
name|getCiteKey
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"This operation requires all selected entries to have BibTex keys defined."
argument_list|)
argument_list|,
operator|(
name|String
operator|)
name|getValue
argument_list|(
name|NAME
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
comment|// All set, call the operation in a new thread:
name|Thread
name|t
init|=
operator|new
name|Thread
argument_list|(
name|this
argument_list|)
decl_stmt|;
name|t
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// Do the operation:
name|operation
operator|.
name|pushEntries
argument_list|(
name|entries
argument_list|,
name|getKeyString
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
comment|// Call the operationCompleted() method on the event dispatch thread:
name|SwingUtilities
operator|.
name|invokeLater
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
name|operation
operator|.
name|operationCompleted
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|getKeyString (BibtexEntry[] entries)
specifier|protected
name|String
name|getKeyString
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|)
block|{
name|StringBuffer
name|result
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|String
name|citeKey
init|=
literal|""
decl_stmt|;
comment|//, message = "";
name|boolean
name|first
init|=
literal|true
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
name|entries
operator|.
name|length
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
name|citeKey
operator|=
operator|(
name|String
operator|)
name|bes
operator|.
name|getField
argument_list|(
name|BibtexFields
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
name|first
condition|)
block|{
name|result
operator|.
name|append
argument_list|(
name|citeKey
argument_list|)
expr_stmt|;
name|first
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
name|result
operator|.
name|append
argument_list|(
literal|","
argument_list|)
operator|.
name|append
argument_list|(
name|citeKey
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|result
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

