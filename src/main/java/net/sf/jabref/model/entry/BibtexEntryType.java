begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 David Weitzman, Morten O. Alver  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  Note: Modified for use in JabRef.  */
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
name|java
operator|.
name|util
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_comment
comment|/**  * Abstract base class for all BibTex entry types.  */
end_comment

begin_class
DECL|class|BibtexEntryType
specifier|public
specifier|abstract
class|class
name|BibtexEntryType
implements|implements
name|EntryType
block|{
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
DECL|method|BibtexEntryType ()
specifier|public
name|BibtexEntryType
parameter_list|()
block|{
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
block|}
DECL|method|addAllOptional (String... fieldNames)
name|void
name|addAllOptional
parameter_list|(
name|String
modifier|...
name|fieldNames
parameter_list|)
block|{
name|optionalFields
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|fieldNames
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|addAllRequired (String... fieldNames)
name|void
name|addAllRequired
parameter_list|(
name|String
modifier|...
name|fieldNames
parameter_list|)
block|{
name|requiredFields
operator|.
name|addAll
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|fieldNames
argument_list|)
argument_list|)
expr_stmt|;
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
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
return|;
block|}
return|return
name|optionalFields
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|field
lambda|->
operator|!
name|isPrimary
argument_list|(
name|field
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|isPrimary (String field)
specifier|private
name|boolean
name|isPrimary
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|primaryFields
init|=
name|getPrimaryOptionalFields
argument_list|()
decl_stmt|;
if|if
condition|(
name|primaryFields
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|primaryFields
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

