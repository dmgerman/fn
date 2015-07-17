begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
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
name|gui
operator|.
name|FileListEntry
import|;
end_import

begin_comment
comment|/**  * An implementation of this interface is called to confirm whether a FileListEntryEditor  * is ready to close when Ok is pressed, or whether there is a problem that needs to be  * resolved first.  */
end_comment

begin_interface
DECL|interface|ConfirmCloseFileListEntryEditor
specifier|public
interface|interface
name|ConfirmCloseFileListEntryEditor
block|{
DECL|method|confirmClose (FileListEntry entry)
name|boolean
name|confirmClose
parameter_list|(
name|FileListEntry
name|entry
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

