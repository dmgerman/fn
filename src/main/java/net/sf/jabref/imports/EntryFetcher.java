begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|OutputPrinter
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
comment|/**  * Implement this interface to add another activeFetcher (something that grabs records  * from the Web for JabRef). Have a look at the existing implemenations  * OAI2Fetcher, IEEEXploreFetcher, MedlineFetcher, JStorFetcher and  * CiteSeerEntryFetcher.  *   * Note: You also need to implement the method stopFetching from  * ImportInspectionDialog.Callback  *   * A Fetcher should not execute any GUI Operations, because it might be run in  * headless mode, but rather use the OutputPrinter for talking to the user.  */
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
comment|/**      * Handle a query entered by the user.      *       * The method is expected to block the caller until all entries have been      * reported to the inspector.      *       * @param query      *            The query text.      * @param inspector      *            The dialog to add imported entries to.      * @param status      *            An OutputPrinter passed to the activeFetcher for reporting about the      *            status of the fetching.      *       * @return True if the query was completed successfully, false if an error      *         occurred.      */
DECL|method|processQuery (String query, ImportInspector inspector, OutputPrinter status)
name|boolean
name|processQuery
parameter_list|(
name|String
name|query
parameter_list|,
name|ImportInspector
name|inspector
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
function_decl|;
comment|/**      * The title for this activeFetcher, displayed in the menu and in the side pane.      *       * @return The title      */
DECL|method|getTitle ()
name|String
name|getTitle
parameter_list|()
function_decl|;
comment|/**      * Get the name of the key binding for this activeFetcher, if any.      *       * @return The name of the key binding or null, if no keybinding should be      *         created.      */
DECL|method|getKeyName ()
name|String
name|getKeyName
parameter_list|()
function_decl|;
comment|/**      * Get the name of the help page for this activeFetcher.      *       * If given, a question mark is displayed in the side pane which leads to      * the help page.      *       * @return The name of the help file or null if this activeFetcher does not have      *         any help.      */
DECL|method|getHelpPage ()
name|String
name|getHelpPage
parameter_list|()
function_decl|;
comment|/**      * If this activeFetcher requires additional options, a panel for setting up these      * should be returned in a JPanel by this method. This JPanel will be added      * to the side pane component automatically.      *       * @return Options panel for this activeFetcher or null if this activeFetcher does not      *         have any options.      */
DECL|method|getOptionsPanel ()
name|JPanel
name|getOptionsPanel
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

