begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|LinkedList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_class
DECL|class|FileHistory
class|class
name|FileHistory
extends|extends
name|JMenu
implements|implements
name|ActionListener
block|{
DECL|field|bound
name|int
name|bound
init|=
literal|5
decl_stmt|;
comment|//or user defined
DECL|field|prefs
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|history
name|LinkedList
name|history
init|=
operator|new
name|LinkedList
argument_list|()
decl_stmt|;
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|FileHistory (JabRefPreferences prefs, JabRefFrame frame)
specifier|public
name|FileHistory
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Recent files"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|String
index|[]
name|old
init|=
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"recentFiles"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|old
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|old
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|old
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|JMenuItem
name|item
init|=
operator|new
name|JMenuItem
argument_list|(
name|old
index|[
name|i
index|]
argument_list|)
decl_stmt|;
name|item
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
name|history
operator|.
name|addFirst
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
block|}
else|else
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds the file name to the top of the menu. If it already is in      * the menu, it is merely moved to the top.      *      * @param filename a<code>String</code> value      */
DECL|method|newFile (String filename)
specifier|public
name|void
name|newFile
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|JMenuItem
name|item
init|=
operator|new
name|JMenuItem
argument_list|(
name|filename
argument_list|)
decl_stmt|;
name|item
operator|.
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|i
operator|<
name|history
operator|.
name|size
argument_list|()
condition|)
block|{
if|if
condition|(
operator|(
operator|(
name|JMenuItem
operator|)
name|history
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
name|filename
argument_list|)
condition|)
name|history
operator|.
name|remove
argument_list|(
name|i
operator|--
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
name|history
operator|.
name|addFirst
argument_list|(
name|item
argument_list|)
expr_stmt|;
while|while
condition|(
name|history
operator|.
name|size
argument_list|()
operator|>
name|prefs
operator|.
name|getInt
argument_list|(
literal|"historySize"
argument_list|)
condition|)
block|{
name|history
operator|.
name|removeLast
argument_list|()
expr_stmt|;
block|}
name|setItems
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|isEnabled
argument_list|()
condition|)
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|setItems ()
specifier|private
name|void
name|setItems
parameter_list|()
block|{
name|removeAll
argument_list|()
expr_stmt|;
name|Iterator
name|i
init|=
name|history
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|add
argument_list|(
operator|(
name|JMenuItem
operator|)
name|i
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|storeHistory ()
specifier|public
name|void
name|storeHistory
parameter_list|()
block|{
if|if
condition|(
name|history
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
index|[]
name|names
init|=
operator|new
name|String
index|[
name|history
operator|.
name|size
argument_list|()
index|]
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
name|names
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|names
index|[
name|i
index|]
operator|=
operator|(
operator|(
name|JMenuItem
operator|)
name|history
operator|.
name|get
argument_list|(
name|i
argument_list|)
operator|)
operator|.
name|getText
argument_list|()
expr_stmt|;
name|prefs
operator|.
name|putStringArray
argument_list|(
literal|"recentFiles"
argument_list|,
name|names
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setFileHistory ()
specifier|public
name|void
name|setFileHistory
parameter_list|()
block|{      }
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
name|name
init|=
operator|(
operator|(
name|JMenuItem
operator|)
name|e
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|getText
argument_list|()
decl_stmt|;
name|frame
operator|.
name|fileToOpen
operator|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
expr_stmt|;
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|frame
operator|.
name|openDatabaseAction
operator|.
name|openIt
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

