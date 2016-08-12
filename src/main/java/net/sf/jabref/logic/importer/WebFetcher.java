begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Copyright (C) 2003-2016 JabRef contributors.  * This program is free software; you can redistribute it and/or modify  * it under the terms of the GNU General Public License as published by  * the Free Software Foundation; either version 2 of the License, or  * (at your option) any later version.  *  * This program is distributed in the hope that it will be useful,  * but WITHOUT ANY WARRANTY; without even the implied warranty of  * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the  * GNU General Public License for more details.  *  * You should have received a copy of the GNU General Public License along  * with this program; if not, write to the Free Software Foundation, Inc.,  * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

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
name|help
operator|.
name|HelpFile
import|;
end_import

begin_comment
comment|/**  * Searches web resources for bibliographic information.  */
end_comment

begin_interface
DECL|interface|WebFetcher
specifier|public
interface|interface
name|WebFetcher
block|{
comment|/**      * Returns the localized name of this fetcher.      * The title can be used to display the fetcher in the menu and in the side pane.      *      * @return the localized name      */
DECL|method|getName ()
name|String
name|getName
parameter_list|()
function_decl|;
comment|/**      * Returns the help page for this fetcher.      *      * @return the {@link HelpFile} enum constant for the help page      */
DECL|method|getHelpPage ()
name|HelpFile
name|getHelpPage
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

