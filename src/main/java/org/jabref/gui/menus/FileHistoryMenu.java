begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.menus
package|package
name|org
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
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Menu
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|MenuItem
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefExecutorService
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
name|JabRefFrame
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
name|org
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
name|org
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
name|Menu
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
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|method|FileHistoryMenu (JabRefPreferences preferences, JabRefFrame frame)
specifier|public
name|FileHistoryMenu
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|setText
argument_list|(
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Recent libraries"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|history
operator|=
name|preferences
operator|.
name|getFileHistory
argument_list|()
expr_stmt|;
if|if
condition|(
name|history
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|setDisable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|setItems
argument_list|()
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
name|setDisable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|setItems ()
specifier|private
name|void
name|setItems
parameter_list|()
block|{
name|getItems
argument_list|()
operator|.
name|clear
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
DECL|method|addItem (String fileName, int num)
specifier|private
name|void
name|addItem
parameter_list|(
name|String
name|fileName
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
name|MenuItem
name|item
init|=
operator|new
name|MenuItem
argument_list|(
name|number
operator|+
literal|". "
operator|+
name|fileName
argument_list|)
decl_stmt|;
name|item
operator|.
name|setOnAction
argument_list|(
name|event
lambda|->
name|openFile
argument_list|(
name|fileName
argument_list|)
argument_list|)
expr_stmt|;
name|getItems
argument_list|()
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
DECL|method|storeHistory ()
specifier|public
name|void
name|storeHistory
parameter_list|()
block|{
name|preferences
operator|.
name|storeFileHistory
argument_list|(
name|history
argument_list|)
expr_stmt|;
block|}
DECL|method|openFile (String fileName)
specifier|public
name|void
name|openFile
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
specifier|final
name|Path
name|fileToOpen
init|=
name|Paths
operator|.
name|get
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
comment|// the existence check has to be done here (and not in open.openIt) as we have to call "removeItem" if the file does not exist
if|if
condition|(
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|fileToOpen
argument_list|)
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
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
name|getFileName
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
name|fileName
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

