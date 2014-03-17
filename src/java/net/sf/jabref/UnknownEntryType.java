begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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

begin_comment
comment|/**  * This class is used to represent an unknown entry type, e.g. encountered  * during bibtex parsing. The only known information is the type name.  * This is useful if the bibtex file contains type definitions that are used  * in the file - because the entries will be parsed before the type definitions  * are found. In the meantime, the entries will be assigned an   * UnknownEntryType giving the name.  */
end_comment

begin_class
DECL|class|UnknownEntryType
specifier|public
class|class
name|UnknownEntryType
extends|extends
name|BibtexEntryType
block|{
DECL|field|name
specifier|private
name|String
name|name
decl_stmt|;
DECL|field|fields
specifier|private
name|String
index|[]
name|fields
init|=
operator|new
name|String
index|[
literal|0
index|]
decl_stmt|;
DECL|method|UnknownEntryType (String name_)
specifier|public
name|UnknownEntryType
parameter_list|(
name|String
name|name_
parameter_list|)
block|{
name|name
operator|=
name|name_
expr_stmt|;
block|}
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
DECL|method|getOptionalFields ()
specifier|public
name|String
index|[]
name|getOptionalFields
parameter_list|()
block|{
return|return
name|fields
return|;
block|}
DECL|method|getRequiredFields ()
specifier|public
name|String
index|[]
name|getRequiredFields
parameter_list|()
block|{
return|return
name|fields
return|;
block|}
DECL|method|describeRequiredFields ()
specifier|public
name|String
name|describeRequiredFields
parameter_list|()
block|{
return|return
literal|"unknown"
return|;
block|}
DECL|method|describeOptionalFields ()
specifier|public
name|String
name|describeOptionalFields
parameter_list|()
block|{
return|return
literal|"unknown"
return|;
block|}
DECL|method|hasAllRequiredFields (BibtexEntry entry, BibtexDatabase database)
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
DECL|method|save (Writer out)
specifier|public
name|void
name|save
parameter_list|(
name|Writer
name|out
parameter_list|)
block|{     }
block|}
end_class

end_unit

