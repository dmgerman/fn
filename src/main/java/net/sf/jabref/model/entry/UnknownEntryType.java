begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
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
name|database
operator|.
name|BibtexDatabase
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_comment
comment|/**  * This class is used to represent an unknown entry type, e.g. encountered  * during bibtex parsing. The only known information is the type name.  * This is useful if the bibtex file contains type definitions that are used  * in the file - because the entries will be parsed before the type definitions  * are found. In the meantime, the entries will be assigned an  * UnknownEntryType giving the name.  */
end_comment

begin_class
DECL|class|UnknownEntryType
specifier|public
class|class
name|UnknownEntryType
implements|implements
name|EntryType
block|{
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|requiredFields
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|requiredFields
decl_stmt|;
DECL|field|optionalFields
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|optionalFields
decl_stmt|;
DECL|method|UnknownEntryType (String name)
specifier|public
name|UnknownEntryType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
name|requiredFields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|optionalFields
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
comment|// key is always required
name|requiredFields
operator|.
name|add
argument_list|(
literal|"bibtexkey"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|getEntryType ()
specifier|public
name|EntryTypes
name|getEntryType
parameter_list|()
block|{
return|return
name|EntryTypes
operator|.
name|UNKNOWN
return|;
block|}
annotation|@
name|Override
DECL|method|getOptionalFields ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getOptionalFields
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|optionalFields
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getRequiredFields ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getRequiredFields
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
name|requiredFields
argument_list|)
return|;
block|}
DECL|method|getPrimaryOptionalFields ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getPrimaryOptionalFields
parameter_list|()
block|{
return|return
name|getOptionalFields
argument_list|()
return|;
block|}
DECL|method|getSecondaryOptionalFields ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getSecondaryOptionalFields
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|unmodifiableList
argument_list|(
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (EntryType o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|EntryType
name|o
parameter_list|)
block|{
return|return
name|getName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getRequiredFieldsForCustomization ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getRequiredFieldsForCustomization
parameter_list|()
block|{
return|return
name|getRequiredFields
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|isRequired (String field)
specifier|public
name|boolean
name|isRequired
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|requiredFields
init|=
name|getRequiredFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|requiredFields
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|requiredFields
operator|.
name|contains
argument_list|(
name|field
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|isOptional (String field)
specifier|public
name|boolean
name|isOptional
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|optionalFields
init|=
name|getOptionalFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|optionalFields
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|optionalFields
operator|.
name|contains
argument_list|(
name|field
argument_list|)
return|;
block|}
block|}
end_class

end_unit

