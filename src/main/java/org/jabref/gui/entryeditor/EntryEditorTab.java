begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Tab
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_class
DECL|class|EntryEditorTab
specifier|public
specifier|abstract
class|class
name|EntryEditorTab
extends|extends
name|Tab
block|{
DECL|field|currentEntry
specifier|protected
name|BibEntry
name|currentEntry
decl_stmt|;
comment|/**      * Decide whether to show this tab for the given entry.      */
DECL|method|shouldShow (BibEntry entry)
specifier|public
specifier|abstract
name|boolean
name|shouldShow
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
comment|/**      * Updates the view with the contents of the given entry.      */
DECL|method|bindToEntry (BibEntry entry)
specifier|protected
specifier|abstract
name|void
name|bindToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
comment|/**      * The tab just got the focus. Override this method if you want to perform a special action on focus (like selecting      * the first field in the editor)      */
DECL|method|handleFocus ()
specifier|protected
name|void
name|handleFocus
parameter_list|()
block|{
comment|// Do nothing by default
block|}
comment|/**      * Notifies the tab that it got focus and should display the given entry.      */
DECL|method|notifyAboutFocus (BibEntry entry)
specifier|public
name|void
name|notifyAboutFocus
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
operator|!
name|entry
operator|.
name|equals
argument_list|(
name|currentEntry
argument_list|)
condition|)
block|{
name|currentEntry
operator|=
name|entry
expr_stmt|;
name|bindToEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
name|handleFocus
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

