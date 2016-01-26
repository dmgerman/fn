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
name|bibtex
operator|.
name|EntryTypes
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|model
operator|.
name|database
operator|.
name|BibDatabaseType
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_class
DECL|class|TypedBibEntry
specifier|public
class|class
name|TypedBibEntry
block|{
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|field|database
specifier|private
specifier|final
name|Optional
argument_list|<
name|BibDatabase
argument_list|>
name|database
decl_stmt|;
DECL|field|type
specifier|private
specifier|final
name|BibDatabaseType
name|type
decl_stmt|;
DECL|method|TypedBibEntry (BibEntry entry, BibDatabaseType type)
specifier|public
name|TypedBibEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseType
name|type
parameter_list|)
block|{
name|this
argument_list|(
name|entry
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
DECL|method|TypedBibEntry (BibEntry entry, Optional<BibDatabase> database, BibDatabaseType type)
specifier|public
name|TypedBibEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Optional
argument_list|<
name|BibDatabase
argument_list|>
name|database
parameter_list|,
name|BibDatabaseType
name|type
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|this
operator|.
name|type
operator|=
name|type
expr_stmt|;
block|}
comment|/**      * Returns true if this entry contains the fields it needs to be      * complete.      */
DECL|method|hasAllRequiredFields ()
specifier|public
name|boolean
name|hasAllRequiredFields
parameter_list|()
block|{
name|EntryType
name|type
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|this
operator|.
name|type
argument_list|)
decl_stmt|;
return|return
name|entry
operator|.
name|allFieldsPresent
argument_list|(
name|type
operator|.
name|getRequiredFields
argument_list|()
argument_list|,
name|database
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Gets the display name for the type of the entry.      */
DECL|method|getTypeForDisplay ()
specifier|public
name|String
name|getTypeForDisplay
parameter_list|()
block|{
name|EntryType
name|entryType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|,
name|type
argument_list|)
decl_stmt|;
if|if
condition|(
name|entryType
operator|!=
literal|null
condition|)
block|{
return|return
name|entryType
operator|.
name|getName
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

