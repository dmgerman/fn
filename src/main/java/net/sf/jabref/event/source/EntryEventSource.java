begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.event.source
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|event
operator|.
name|source
package|;
end_package

begin_comment
comment|/**  * This enum represents the context EntryEvents were sent from.  */
end_comment

begin_enum
DECL|enum|EntryEventSource
specifier|public
enum|enum
name|EntryEventSource
block|{
DECL|enumConstant|LOCAL
name|LOCAL
block|,
DECL|enumConstant|SHARED
name|SHARED
block|,
DECL|enumConstant|UNDO
name|UNDO
block|,
DECL|enumConstant|SAVE_ACTION
name|SAVE_ACTION
block|}
end_enum

end_unit

