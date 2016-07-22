begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
operator|.
name|ParseException
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * This group contains all entries. Always. At any time!  */
end_comment

begin_class
DECL|class|AllEntriesGroup
specifier|public
class|class
name|AllEntriesGroup
extends|extends
name|AbstractGroup
block|{
DECL|field|ID
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"AllEntriesGroup:"
decl_stmt|;
DECL|method|AllEntriesGroup ()
specifier|public
name|AllEntriesGroup
parameter_list|()
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"All entries"
argument_list|)
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|)
expr_stmt|;
block|}
DECL|method|fromString (String s)
specifier|public
specifier|static
name|AbstractGroup
name|fromString
parameter_list|(
name|String
name|s
parameter_list|)
throws|throws
name|ParseException
block|{
if|if
condition|(
operator|!
name|s
operator|.
name|startsWith
argument_list|(
name|AllEntriesGroup
operator|.
name|ID
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|IllegalArgumentException
argument_list|(
literal|"AllEntriesGroup cannot be created from \""
operator|+
name|s
operator|+
literal|"\"."
argument_list|)
throw|;
block|}
return|return
operator|new
name|AllEntriesGroup
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|supportsAdd ()
specifier|public
name|boolean
name|supportsAdd
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|supportsRemove ()
specifier|public
name|boolean
name|supportsRemove
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|add (List<BibEntry> entriesToAdd)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|add
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToAdd
parameter_list|)
block|{
comment|// not supported -> ignore
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|remove (List<BibEntry> entriesToRemove)
specifier|public
name|Optional
argument_list|<
name|EntriesGroupChange
argument_list|>
name|remove
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToRemove
parameter_list|)
block|{
comment|// not supported -> ignore
return|return
literal|null
return|;
block|}
annotation|@
name|Override
DECL|method|deepCopy ()
specifier|public
name|AbstractGroup
name|deepCopy
parameter_list|()
block|{
return|return
operator|new
name|AllEntriesGroup
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
return|return
name|o
operator|instanceof
name|AllEntriesGroup
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
name|AllEntriesGroup
operator|.
name|ID
return|;
block|}
annotation|@
name|Override
DECL|method|contains (BibEntry entry)
specifier|public
name|boolean
name|contains
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|isDynamic ()
specifier|public
name|boolean
name|isDynamic
parameter_list|()
block|{
comment|// this is actually a special case; I define it as non-dynamic
return|return
literal|false
return|;
block|}
annotation|@
name|Override
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"This group contains all entries. It cannot be edited or removed."
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getShortDescription (boolean showDynamic)
specifier|public
name|String
name|getShortDescription
parameter_list|(
name|boolean
name|showDynamic
parameter_list|)
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"<b>All Entries</b> (this group cannot be edited or removed)"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getTypeId ()
specifier|public
name|String
name|getTypeId
parameter_list|()
block|{
return|return
name|AllEntriesGroup
operator|.
name|ID
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
comment|// TODO Auto-generated method stub
return|return
name|super
operator|.
name|hashCode
argument_list|()
return|;
block|}
block|}
end_class

end_unit

