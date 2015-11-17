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
name|Arrays
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
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * This class is used to represent customized entry types.  */
end_comment

begin_class
DECL|class|CustomEntryType
specifier|public
class|class
name|CustomEntryType
implements|implements
name|EntryType
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|CustomEntryType
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|ENTRYTYPE_FLAG
specifier|public
specifier|static
specifier|final
name|String
name|ENTRYTYPE_FLAG
init|=
literal|"jabref-entrytype: "
decl_stmt|;
DECL|field|name
specifier|private
specifier|final
name|String
name|name
decl_stmt|;
DECL|field|required
specifier|private
name|String
index|[]
name|required
decl_stmt|;
DECL|field|optional
specifier|private
specifier|final
name|String
index|[]
name|optional
decl_stmt|;
DECL|field|priOpt
specifier|private
name|String
index|[]
name|priOpt
decl_stmt|;
DECL|field|reqSets
specifier|private
name|String
index|[]
index|[]
name|reqSets
decl_stmt|;
comment|// Sets of either-or required fields, if any
DECL|method|CustomEntryType (String name, List<String> required, List<String> priOpt, List<String> secOpt)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|required
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|priOpt
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|secOpt
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|required
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|required
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|,
name|priOpt
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|priOpt
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|,
name|secOpt
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|secOpt
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name, String[] required, String[] priOpt, String[] secOpt)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name
parameter_list|,
name|String
index|[]
name|required
parameter_list|,
name|String
index|[]
name|priOpt
parameter_list|,
name|String
index|[]
name|secOpt
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|parseRequiredFields
argument_list|(
name|required
argument_list|)
expr_stmt|;
name|this
operator|.
name|priOpt
operator|=
name|priOpt
expr_stmt|;
name|optional
operator|=
name|EntryUtil
operator|.
name|arrayConcat
argument_list|(
name|priOpt
argument_list|,
name|secOpt
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name, List<String> required, List<String> optional)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|required
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|optional
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|required
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|required
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|,
name|optional
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|optional
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name, String[] required, String[] optional)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name
parameter_list|,
name|String
index|[]
name|required
parameter_list|,
name|String
index|[]
name|optional
parameter_list|)
block|{
name|this
argument_list|(
name|name
argument_list|,
name|required
argument_list|,
name|optional
argument_list|,
operator|new
name|String
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
DECL|method|CustomEntryType (String name, String required, String optional)
specifier|public
name|CustomEntryType
parameter_list|(
name|String
name|name
parameter_list|,
name|String
name|required
parameter_list|,
name|String
name|optional
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|EntryUtil
operator|.
name|capitalizeFirst
argument_list|(
name|name
argument_list|)
expr_stmt|;
if|if
condition|(
name|required
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|required
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
block|}
else|else
block|{
name|parseRequiredFields
argument_list|(
name|required
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|optional
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|this
operator|.
name|optional
operator|=
operator|new
name|String
index|[
literal|0
index|]
expr_stmt|;
block|}
else|else
block|{
name|this
operator|.
name|optional
operator|=
name|optional
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
expr_stmt|;
block|}
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
name|BIBTEX
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
annotation|@
name|Override
DECL|method|isVisibleAtNewEntryDialog ()
specifier|public
name|boolean
name|isVisibleAtNewEntryDialog
parameter_list|()
block|{
return|return
literal|true
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
DECL|method|parseRequiredFields (String req)
specifier|private
name|void
name|parseRequiredFields
parameter_list|(
name|String
name|req
parameter_list|)
block|{
name|String
index|[]
name|parts
init|=
name|req
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
decl_stmt|;
name|parseRequiredFields
argument_list|(
name|parts
argument_list|)
expr_stmt|;
block|}
DECL|method|parseRequiredFields (String[] parts)
specifier|private
name|void
name|parseRequiredFields
parameter_list|(
name|String
index|[]
name|parts
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|fields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|String
index|[]
argument_list|>
name|sets
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|part
range|:
name|parts
control|)
block|{
name|String
index|[]
name|subParts
init|=
name|part
operator|.
name|split
argument_list|(
literal|"/"
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|addAll
argument_list|(
name|fields
argument_list|,
name|subParts
argument_list|)
expr_stmt|;
comment|// Check if we have either/or fields:
if|if
condition|(
name|subParts
operator|.
name|length
operator|>
literal|1
condition|)
block|{
name|sets
operator|.
name|add
argument_list|(
name|subParts
argument_list|)
expr_stmt|;
block|}
block|}
name|required
operator|=
name|fields
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|fields
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|sets
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|reqSets
operator|=
name|sets
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|sets
operator|.
name|size
argument_list|()
index|]
index|[]
argument_list|)
expr_stmt|;
block|}
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
name|Arrays
operator|.
name|asList
argument_list|(
name|optional
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
name|Arrays
operator|.
name|asList
argument_list|(
name|required
argument_list|)
return|;
block|}
annotation|@
name|Override
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
name|Arrays
operator|.
name|asList
argument_list|(
name|priOpt
argument_list|)
return|;
block|}
annotation|@
name|Override
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
name|Arrays
operator|.
name|asList
argument_list|(
name|EntryUtil
operator|.
name|getRemainder
argument_list|(
name|optional
argument_list|,
name|priOpt
argument_list|)
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
name|Arrays
operator|.
name|asList
argument_list|(
name|getRequiredFieldsString
argument_list|()
operator|.
name|split
argument_list|(
literal|";"
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Check whether this entry's required fields are set, taking crossreferenced entries and      * either-or fields into account:      *      * @param entry    The entry to check.      * @param database The entry's database.      * @return True if required fields are set, false otherwise.      */
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
comment|// First check if the bibtex key is set:
if|if
condition|(
name|entry
operator|.
name|getCiteKey
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// Then check other fields:
name|boolean
index|[]
name|isSet
init|=
operator|new
name|boolean
index|[
name|required
operator|.
name|length
index|]
decl_stmt|;
comment|// First check for all fields, whether they are set here or in a crossref'd entry:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|required
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|isSet
index|[
name|i
index|]
operator|=
name|BibtexDatabase
operator|.
name|getResolvedField
argument_list|(
name|required
index|[
name|i
index|]
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
operator|!=
literal|null
expr_stmt|;
block|}
comment|// Then go through all fields. If a field is not set, see if it is part of an either-or
comment|// set where another field is set. If not, return false:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|required
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|!
name|isSet
index|[
name|i
index|]
condition|)
block|{
if|if
condition|(
operator|!
name|isCoupledFieldSet
argument_list|(
name|required
index|[
name|i
index|]
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
block|}
block|}
comment|// Passed all fields, so return true:
return|return
literal|true
return|;
block|}
DECL|method|isCoupledFieldSet (String field, BibtexEntry entry, BibtexDatabase database)
specifier|private
name|boolean
name|isCoupledFieldSet
parameter_list|(
name|String
name|field
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
if|if
condition|(
name|reqSets
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
for|for
control|(
name|String
index|[]
name|reqSet
range|:
name|reqSets
control|)
block|{
name|boolean
name|takesPart
init|=
literal|false
decl_stmt|;
name|boolean
name|oneSet
init|=
literal|false
decl_stmt|;
for|for
control|(
name|String
name|aReqSet
range|:
name|reqSet
control|)
block|{
comment|// If this is the field we're looking for, note that the field is part of the set:
if|if
condition|(
name|aReqSet
operator|.
name|equalsIgnoreCase
argument_list|(
name|field
argument_list|)
condition|)
block|{
name|takesPart
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|BibtexDatabase
operator|.
name|getResolvedField
argument_list|(
name|aReqSet
argument_list|,
name|entry
argument_list|,
name|database
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|oneSet
operator|=
literal|true
expr_stmt|;
block|}
block|}
comment|// Ths the field is part of the set, and at least one other field is set, return true:
if|if
condition|(
name|takesPart
operator|&&
name|oneSet
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
comment|// No hits, so return false:
return|return
literal|false
return|;
block|}
comment|/**      * Get a String describing the required field set for this entry type.      *      * @return Description of required field set for storage in preferences or bib file.      */
DECL|method|getRequiredFieldsString ()
specifier|public
name|String
name|getRequiredFieldsString
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|int
name|reqSetsPiv
init|=
literal|0
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|required
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
operator|(
name|reqSets
operator|==
literal|null
operator|)
operator|||
operator|(
name|reqSetsPiv
operator|==
name|reqSets
operator|.
name|length
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|required
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|required
index|[
name|i
index|]
operator|.
name|equals
argument_list|(
name|reqSets
index|[
name|reqSetsPiv
index|]
index|[
literal|0
index|]
argument_list|)
condition|)
block|{
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|reqSets
index|[
name|reqSetsPiv
index|]
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|reqSets
index|[
name|reqSetsPiv
index|]
index|[
name|j
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|j
operator|<
operator|(
name|reqSets
index|[
name|reqSetsPiv
index|]
operator|.
name|length
operator|-
literal|1
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|'/'
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Skip next n-1 fields:
name|i
operator|+=
name|reqSets
index|[
name|reqSetsPiv
index|]
operator|.
name|length
operator|-
literal|1
expr_stmt|;
name|reqSetsPiv
operator|++
expr_stmt|;
block|}
else|else
block|{
name|sb
operator|.
name|append
argument_list|(
name|required
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|i
operator|<
operator|(
name|required
operator|.
name|length
operator|-
literal|1
operator|)
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|';'
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

