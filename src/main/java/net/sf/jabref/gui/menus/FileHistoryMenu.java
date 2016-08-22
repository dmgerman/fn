begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.menus
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|menus
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
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JMenuItem
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefExecutorService
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
name|gui
operator|.
name|JabRefFrame
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
name|util
operator|.
name|io
operator|.
name|FileHistory
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|FileHistoryMenu
specifier|public
class|class
name|FileHistoryMenu
extends|extends
name|JMenu
implements|implements
name|ActionListener
block|{
DECL|field|history
specifier|private
specifier|final
name|FileHistory
name|history
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|FileHistoryMenu (JabRefPreferences prefs, JabRefFrame frame)
specifier|public
name|FileHistoryMenu
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|String
name|name
init|=
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Recent files"
argument_list|)
decl_stmt|;
name|int
name|i
init|=
name|name
operator|.
name|indexOf
argument_list|(
literal|'&'
argument_list|)
decl_stmt|;
if|if
condition|(
name|i
operator|>=
literal|0
condition|)
block|{
name|setText
argument_list|(
name|name
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|i
argument_list|)
operator|+
name|name
operator|.
name|substring
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|char
name|mnemonic
init|=
name|Character
operator|.
name|toUpperCase
argument_list|(
name|name
operator|.
name|charAt
argument_list|(
name|i
operator|+
literal|1
argument_list|)
argument_list|)
decl_stmt|;
name|setMnemonic
argument_list|(
operator|(
name|int
operator|)
name|mnemonic
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setText
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|history
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getFileHistory
argument_list|()
expr_stmt|;
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
name|setItems
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Adds the filename to the top of the menu. If it already is in      * the menu, it is merely moved to the top.      *      * @param filename a<code>String</code> value      */
DECL|method|newFile (String filename)
specifier|public
name|void
name|newFile
parameter_list|(
name|String
name|filename
parameter_list|)
block|{
name|history
operator|.
name|newFile
argument_list|(
name|filename
argument_list|)
expr_stmt|;
name|setItems
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|isEnabled
argument_list|()
condition|)
block|{
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
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
for|for
control|(
name|int
name|count
init|=
literal|0
init|;
name|count
operator|<
name|history
operator|.
name|size
argument_list|()
condition|;
name|count
operator|++
control|)
block|{
name|addItem
argument_list|(
name|history
operator|.
name|getFileName
argument_list|(
name|count
argument_list|)
argument_list|,
name|count
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addItem (String filename, int num)
specifier|private
name|void
name|addItem
parameter_list|(
name|String
name|filename
parameter_list|,
name|int
name|num
parameter_list|)
block|{
name|String
name|number
init|=
name|Integer
operator|.
name|toString
argument_list|(
name|num
argument_list|)
decl_stmt|;
name|JMenuItem
name|item
init|=
operator|new
name|JMenuItem
argument_list|(
name|number
operator|+
literal|". "
operator|+
name|filename
argument_list|)
decl_stmt|;
name|char
name|mnemonic
init|=
name|Character
operator|.
name|toUpperCase
argument_list|(
name|number
operator|.
name|charAt
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|item
operator|.
name|setMnemonic
argument_list|(
operator|(
name|int
operator|)
name|mnemonic
argument_list|)
expr_stmt|;
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
comment|//history.addFirst(item);
block|}
DECL|method|storeHistory ()
specifier|public
name|void
name|storeHistory
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|storeFileHistory
argument_list|(
name|history
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
name|int
name|pos
init|=
name|name
operator|.
name|indexOf
argument_list|(
literal|' '
argument_list|)
decl_stmt|;
name|name
operator|=
name|name
operator|.
name|substring
argument_list|(
name|pos
operator|+
literal|1
argument_list|)
expr_stmt|;
specifier|final
name|File
name|fileToOpen
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
comment|// the existence check has to be done here (and not in open.openIt) as we have to call "removeItem" if the file does not exist
if|if
condition|(
operator|!
name|fileToOpen
operator|.
name|exists
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
operator|+
literal|": "
operator|+
name|fileToOpen
operator|.
name|getName
argument_list|()
argument_list|,
name|Localization
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
name|history
operator|.
name|removeItem
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|setItems
argument_list|()
expr_stmt|;
return|return;
block|}
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
name|frame
operator|.
name|getOpenDatabaseAction
argument_list|()
operator|.
name|openFile
argument_list|(
name|fileToOpen
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

