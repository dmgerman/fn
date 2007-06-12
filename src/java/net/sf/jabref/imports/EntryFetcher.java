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
comment|/**  * Implement this interface to add another fetcher (something that grabs records  * from the Web for JabRef). Have a look at the existing implemenations  * OAI2Fetcher and IEEEXploreFetcher.  */
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
comment|/** 	 * Handle a query entered by the user. 	 *  	 * @param query 	 *            The query text. 	 * @param dialog 	 *            The dialog to add imported entries to. 	 * @param frame 	 *            The application frame. 	 */
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
comment|/** 	 * The title for this fetcher 	 *  	 * @return The title 	 */
DECL|method|getTitle ()
specifier|public
name|String
name|getTitle
parameter_list|()
function_decl|;
comment|/** 	 * Get the name of the key binding for this fetcher, if any. 	 *  	 * @return The name of the key binding 	 */
DECL|method|getKeyName ()
specifier|public
name|String
name|getKeyName
parameter_list|()
function_decl|;
comment|/** 	 * Get the appropriate icon URL for this fetcher. 	 *  	 * @return The icon URL 	 */
DECL|method|getIcon ()
specifier|public
name|URL
name|getIcon
parameter_list|()
function_decl|;
comment|/** 	 * Get the name of the help page for this fetcher. 	 *  	 * @return The name of the help file 	 */
DECL|method|getHelpPage ()
specifier|public
name|String
name|getHelpPage
parameter_list|()
function_decl|;
comment|/** 	 * If this fetcher requires additional options, a panel for setting up these 	 * should be returned in a JPanel by this method. This JPanel will be added 	 * to the side pane component automatically. 	 *  	 * @return Options panel for this fetcher 	 */
DECL|method|getOptionsPanel ()
specifier|public
name|JPanel
name|getOptionsPanel
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

