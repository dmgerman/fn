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

begin_comment
comment|/**  * This subclass of ExternalFileType is used to mark types that are unknown.  * This can be the case when a database is loaded which contains links to files  * of a type that has not been defined on this JabRef instance.  */
end_comment

begin_class
DECL|class|UnknownExternalFileType
specifier|public
class|class
name|UnknownExternalFileType
extends|extends
name|ExternalFileType
block|{
DECL|method|UnknownExternalFileType (String name)
specifier|public
name|UnknownExternalFileType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|"unknown"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

