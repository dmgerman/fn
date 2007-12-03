begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
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
name|gui
operator|.
name|ImportInspectionDialog
import|;
end_import

begin_comment
comment|/**  * Implement this interface to add another fetcher (something that grabs records  * from the Web for JabRef). Have a look at the existing implemenations  * OAI2Fetcher, IEEEXploreFetcher, MedlineFetcher, JStorFetcher and  * CiteSeerEntryFetcher.  *   * Note: You also need to implement the method stopFetching from  * ImportInspectionDialog.Callback  */
end_comment

begin_interface
DECL|interface|EntryFetcher
specifier|public
interface|interface
name|EntryFetcher
extends|extends
name|ImportInspectionDialog
operator|.
name|CallBack
block|{
comment|/**      * Handle a query entered by the user.      *       * The method may block the caller if query takes some time.      *       * The caller may not assume that the query is done when the call returns,      * but should rather wait for the call "entryListComplete" to the import      * inspection dialog.      *       * @param query      *            The query text.      * @param dialog      *            The dialog to add imported entries to.      * @param frame      *            The application frame.      */
DECL|method|processQuery (String query, ImportInspectionDialog dialog, JabRefFrame frame)
specifier|public
name|void
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspectionDialog
name|dialog
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
function_decl|;
comment|/**      * The title for this fetcher, displayed in the menu and in the side pane.      *       * @return The title      */
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
function_decl|;
comment|/**      * Get the name of the key binding for this fetcher, if any.      *       * @return The name of the key binding or null, if no keybinding should be      *         created.      */
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
function_decl|;
comment|/**      * Get the appropriate icon URL for this fetcher.      *       * @return The icon URL      */
DECL|method|getIcon ()
specifier|public
name|URL
name|getIcon
parameter_list|()
function_decl|;
comment|/**      * Get the name of the help page for this fetcher.      *       * If given, a question mark is displayed in the side pane which leads to      * the help page.      *       * @return The name of the help file or null if this fetcher does not have      *         any help.      */
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
function_decl|;
comment|/**      * If this fetcher requires additional options, a panel for setting up these      * should be returned in a JPanel by this method. This JPanel will be added      * to the side pane component automatically.      *       * @return Options panel for this fetcher or null if this fetcher does not      *         have any options.      */
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

