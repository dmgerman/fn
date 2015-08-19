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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_comment
comment|/**  * An ImportInspector can be passed to a EntryFetcher and will receive entries  * as they are fetched from somewhere.  *   * Currently there are two implementations: ImportInspectionDialog and  * ImportInspectionCommandLine  *   */
end_comment

begin_interface
DECL|interface|ImportInspector
specifier|public
interface|interface
name|ImportInspector
block|{
comment|/**      * Notify the ImportInspector about the progress of the operation.      *       * The Inspector for instance could display a progress bar with the given      * values.      *       * @param current      *            A number that is related to the work already done.      *       * @param max      *            A current estimate for the total amount of work to be done.      */
DECL|method|setProgress (int current, int max)
name|void
name|setProgress
parameter_list|(
name|int
name|current
parameter_list|,
name|int
name|max
parameter_list|)
function_decl|;
comment|/**      * Add the given entry to the list of entries managed by the inspector.      *       * @param entry      *            The entry to add.      */
DECL|method|addEntry (BibtexEntry entry)
name|void
name|addEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
comment|/**      * If this is a graphical dialog, bring it to the front.      */
DECL|method|toFront ()
name|void
name|toFront
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

