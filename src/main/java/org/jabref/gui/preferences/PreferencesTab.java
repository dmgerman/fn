begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
package|;
end_package

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
name|javafx
operator|.
name|scene
operator|.
name|Node
import|;
end_import

begin_comment
comment|/**  * A prefsTab is a component displayed in the PreferenceDialog.  *  * It needs to extend from Component.  */
end_comment

begin_interface
DECL|interface|PreferencesTab
interface|interface
name|PreferencesTab
block|{
DECL|method|getBuilder ()
name|Node
name|getBuilder
parameter_list|()
function_decl|;
comment|/**      * Should return the localized identifier to use for the tab.      *      * @return Identifier for the tab (for instance "General", "Appearance" or "External Files").      */
DECL|method|getTabName ()
name|String
name|getTabName
parameter_list|()
function_decl|;
comment|/**      * This method is called when the dialog is opened, or if it is made      * visible after being hidden. This calls the appropriate method in the      * ViewModel.      */
DECL|method|setValues ()
name|void
name|setValues
parameter_list|()
function_decl|;
comment|/**      * This method is called when the user presses OK in the Preferences      * dialog. This calls the appropriate method in the ViewModel.      */
DECL|method|storeSettings ()
name|void
name|storeSettings
parameter_list|()
function_decl|;
comment|/**      * This method is called before the {@link #storeSettings()} method,      * to check if there are illegal settings in the tab, or if is ready      * to be closed. This calls the appropriate method in the ViewModel.      */
DECL|method|validateSettings ()
name|boolean
name|validateSettings
parameter_list|()
function_decl|;
comment|/**      * This method should be called after storing the preferences, This      * calls the appropriate method in the ViewModel.      *      * @return The messages for the changed properties (e. g. "Changed language: English")      */
DECL|method|getRestartWarnings ()
name|List
argument_list|<
name|String
argument_list|>
name|getRestartWarnings
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

