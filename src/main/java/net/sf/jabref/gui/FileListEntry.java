begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|external
operator|.
name|ExternalFileType
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_comment
comment|/**  * This class represents a file link for a Bibtex entry.  */
end_comment

begin_class
DECL|class|FileListEntry
specifier|public
class|class
name|FileListEntry
block|{
DECL|field|description
specifier|public
name|String
name|description
decl_stmt|;
DECL|field|link
specifier|public
name|String
name|link
decl_stmt|;
DECL|field|type
specifier|public
name|ExternalFileType
name|type
decl_stmt|;
DECL|method|FileListEntry (String description, String link)
specifier|public
name|FileListEntry
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|link
parameter_list|)
block|{
name|this
argument_list|(
name|description
argument_list|,
name|link
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
DECL|method|FileListEntry (String description, String link, ExternalFileType type)
specifier|public
name|FileListEntry
parameter_list|(
name|String
name|description
parameter_list|,
name|String
name|link
parameter_list|,
name|ExternalFileType
name|type
parameter_list|)
block|{
name|this
operator|.
name|description
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|this
operator|.
name|link
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|link
argument_list|)
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
comment|// may be null
block|}
DECL|method|getStringArrayRepresentation ()
specifier|public
name|String
index|[]
name|getStringArrayRepresentation
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
name|description
block|,
name|link
block|,
name|getTypeName
argument_list|()
block|}
return|;
block|}
DECL|method|getTypeName ()
specifier|private
name|String
name|getTypeName
parameter_list|()
block|{
return|return
name|this
operator|.
name|type
operator|==
literal|null
condition|?
literal|""
else|:
name|this
operator|.
name|type
operator|.
name|getName
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|description
operator|+
literal|" : "
operator|+
name|link
operator|+
literal|" : "
operator|+
name|type
return|;
block|}
DECL|method|switchLinkAndDescription ()
specifier|public
name|FileListEntry
name|switchLinkAndDescription
parameter_list|()
block|{
return|return
operator|new
name|FileListEntry
argument_list|(
name|link
argument_list|,
name|description
argument_list|,
name|type
argument_list|)
return|;
block|}
block|}
end_class

end_unit

