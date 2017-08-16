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

begin_class
DECL|class|EntryEditorTab
specifier|public
specifier|abstract
class|class
name|EntryEditorTab
extends|extends
name|Tab
block|{
comment|/**      * Used for lazy-loading of the tab content.      */
DECL|field|isInitialized
specifier|protected
name|boolean
name|isInitialized
init|=
literal|false
decl_stmt|;
DECL|method|shouldShow ()
specifier|public
specifier|abstract
name|boolean
name|shouldShow
parameter_list|()
function_decl|;
DECL|method|requestFocus ()
specifier|public
name|void
name|requestFocus
parameter_list|()
block|{      }
comment|/**      * This method is called when the user focuses this tab.      */
DECL|method|notifyAboutFocus ()
specifier|public
name|void
name|notifyAboutFocus
parameter_list|()
block|{
if|if
condition|(
operator|!
name|isInitialized
condition|)
block|{
name|initialize
argument_list|()
expr_stmt|;
name|isInitialized
operator|=
literal|true
expr_stmt|;
block|}
block|}
DECL|method|initialize ()
specifier|protected
specifier|abstract
name|void
name|initialize
parameter_list|()
function_decl|;
block|}
end_class

end_unit

