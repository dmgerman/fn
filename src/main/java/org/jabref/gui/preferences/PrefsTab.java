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
DECL|interface|PrefsTab
interface|interface
name|PrefsTab
block|{
DECL|method|getBuilder ()
name|Node
name|getBuilder
parameter_list|()
function_decl|;
comment|/**      * This method is called when the dialog is opened, or if it is made      * visible after being hidden. The tab should update all its values.      *      * This is the ONLY PLACE to set values for the fields in the tab. It      * is ILLEGAL to set values only at construction time, because the dialog      * will be reused and updated.      */
DECL|method|setValues ()
name|void
name|setValues
parameter_list|()
function_decl|;
comment|/**      * This method is called when the user presses OK in the      * Preferences dialog. Implementing classes must make sure all      * settings presented get stored in JabRefPreferences.      */
DECL|method|storeSettings ()
name|void
name|storeSettings
parameter_list|()
function_decl|;
comment|/**      * This method is called before the {@link #storeSettings()} method,      * to check if there are illegal settings in the tab, or if is ready      * to be closed.      * If the tab is *not* ready, it should display a message to the user      * informing about the illegal setting.      */
DECL|method|validateSettings ()
name|boolean
name|validateSettings
parameter_list|()
function_decl|;
comment|/**      * Should return the localized identifier to use for the tab.      *      * @return Identifier for the tab (for instance "General", "Appearance" or "External Files").      */
DECL|method|getTabName ()
name|String
name|getTabName
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

