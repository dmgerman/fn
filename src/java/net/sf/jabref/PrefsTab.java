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

begin_interface
DECL|interface|PrefsTab
interface|interface
name|PrefsTab
block|{
comment|/**      * This method is called when the user presses OK in the      * Preferences dialog. Implementing classes must make sure all      * settings presented get stored in JabRefPreferences.      *      */
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
function_decl|;
comment|/**      * This method is called before the {@ling storeSettings()} method,       * to check if there are illegal settings in the tab, or if is ready      * to be closed.      * If the tab is *not* ready, it should display a message to the user       * informing about the illegal setting.      */
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

