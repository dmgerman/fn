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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|JabRefPreferences
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

begin_comment
comment|/**  * Provides a list of known entry types  *<p>  * The list of optional and required fields is derived from http://en.wikipedia.org/wiki/BibTeX#Entry_types  */
end_comment

begin_class
DECL|class|BibtexEntryType
specifier|public
specifier|abstract
class|class
name|BibtexEntryType
implements|implements
name|Comparable
argument_list|<
name|BibtexEntryType
argument_list|>
block|{
DECL|method|getName ()
specifier|public
specifier|abstract
name|String
name|getName
parameter_list|()
function_decl|;
DECL|field|ALL_TYPES
specifier|private
specifier|static
specifier|final
name|TreeMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
name|ALL_TYPES
init|=
operator|new
name|TreeMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|STANDARD_TYPES
specifier|private
specifier|static
specifier|final
name|TreeMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
name|STANDARD_TYPES
decl_stmt|;
static|static
block|{
comment|// Put the standard entry types into the type map.
if|if
condition|(
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_MODE
argument_list|)
condition|)
block|{
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"article"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"inbook"
argument_list|,
name|BibtexEntryTypes
operator|.
name|INBOOK
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"book"
argument_list|,
name|BibtexEntryTypes
operator|.
name|BOOK
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"booklet"
argument_list|,
name|BibtexEntryTypes
operator|.
name|BOOKLET
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"incollection"
argument_list|,
name|BibtexEntryTypes
operator|.
name|INCOLLECTION
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"conference"
argument_list|,
name|BibtexEntryTypes
operator|.
name|CONFERENCE
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"inproceedings"
argument_list|,
name|BibtexEntryTypes
operator|.
name|INPROCEEDINGS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"proceedings"
argument_list|,
name|BibtexEntryTypes
operator|.
name|PROCEEDINGS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"manual"
argument_list|,
name|BibtexEntryTypes
operator|.
name|MANUAL
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"mastersthesis"
argument_list|,
name|BibtexEntryTypes
operator|.
name|MASTERSTHESIS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"phdthesis"
argument_list|,
name|BibtexEntryTypes
operator|.
name|PHDTHESIS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"techreport"
argument_list|,
name|BibtexEntryTypes
operator|.
name|TECHREPORT
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"unpublished"
argument_list|,
name|BibtexEntryTypes
operator|.
name|UNPUBLISHED
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"patent"
argument_list|,
name|BibtexEntryTypes
operator|.
name|PATENT
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"standard"
argument_list|,
name|BibtexEntryTypes
operator|.
name|STANDARD
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"electronic"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ELECTRONIC
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"periodical"
argument_list|,
name|BibtexEntryTypes
operator|.
name|PERIODICAL
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"misc"
argument_list|,
name|BibtexEntryTypes
operator|.
name|MISC
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"other"
argument_list|,
name|BibtexEntryTypes
operator|.
name|OTHER
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"ieeetranbstctl"
argument_list|,
name|BibtexEntryTypes
operator|.
name|IEEETRANBSTCTL
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"article"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"book"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|BOOK
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"inbook"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|INBOOK
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"bookinbook"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|BOOKINBOOK
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"suppbook"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|SUPPBOOK
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"booklet"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|BOOKLET
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"collection"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|COLLECTION
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"incollection"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|INCOLLECTION
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"suppcollection"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|SUPPCOLLECTION
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"manual"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|MANUAL
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"misc"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|MISC
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"online"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|ONLINE
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"patent"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|PATENT
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"periodical"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|PERIODICAL
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"suppperiodical"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|SUPPPERIODICAL
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"proceedings"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|PROCEEDINGS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"inproceedings"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|INPROCEEDINGS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"reference"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|REFERENCE
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"inreference"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|INREFERENCE
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"report"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|REPORT
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"set"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|SET
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"thesis"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|THESIS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"unpublished"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|UNPUBLISHED
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"conference"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|CONFERENCE
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"electronic"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|ELECTRONIC
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"mastersthesis"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|MASTERSTHESIS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"phdthesis"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|PHDTHESIS
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"techreport"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|TECHREPORT
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"www"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|WWW
argument_list|)
expr_stmt|;
name|ALL_TYPES
operator|.
name|put
argument_list|(
literal|"ieeetranbstctl"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|IEEETRANBSTCTL
argument_list|)
expr_stmt|;
block|}
comment|// We need a record of the standard types, in case the user wants
comment|// to remove a customized version. Therefore we clone the map.
name|STANDARD_TYPES
operator|=
operator|new
name|TreeMap
argument_list|<>
argument_list|(
name|ALL_TYPES
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|compareTo (BibtexEntryType o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|BibtexEntryType
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
DECL|method|getOptionalFields ()
specifier|public
specifier|abstract
name|String
index|[]
name|getOptionalFields
parameter_list|()
function_decl|;
DECL|method|getRequiredFields ()
specifier|public
specifier|abstract
name|String
index|[]
name|getRequiredFields
parameter_list|()
function_decl|;
DECL|method|getPrimaryOptionalFields ()
specifier|public
name|String
index|[]
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
name|String
index|[]
name|getSecondaryOptionalFields
parameter_list|()
block|{
name|String
index|[]
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
name|String
index|[
literal|0
index|]
return|;
block|}
return|return
name|Arrays
operator|.
name|stream
argument_list|(
name|optionalFields
argument_list|)
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
name|toArray
argument_list|(
name|String
index|[]
operator|::
operator|new
argument_list|)
return|;
block|}
DECL|method|describeRequiredFields ()
specifier|public
specifier|abstract
name|String
name|describeRequiredFields
parameter_list|()
function_decl|;
DECL|method|hasAllRequiredFields (BibtexEntry entry, BibtexDatabase database)
specifier|public
specifier|abstract
name|boolean
name|hasAllRequiredFields
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
function_decl|;
DECL|method|getUtilityFields ()
specifier|public
name|String
index|[]
name|getUtilityFields
parameter_list|()
block|{
return|return
operator|new
name|String
index|[]
block|{
literal|"search"
block|}
return|;
block|}
DECL|method|isRequired (String field)
specifier|public
name|boolean
name|isRequired
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|String
index|[]
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
for|for
control|(
name|String
name|requiredField
range|:
name|requiredFields
control|)
block|{
if|if
condition|(
name|requiredField
operator|.
name|equals
argument_list|(
name|field
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
block|}
return|return
literal|false
return|;
block|}
DECL|method|isOptional (String field)
specifier|public
name|boolean
name|isOptional
parameter_list|(
name|String
name|field
parameter_list|)
block|{
name|String
index|[]
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
name|Arrays
operator|.
name|asList
argument_list|(
name|optionalFields
argument_list|)
operator|.
name|contains
argument_list|(
name|field
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
name|String
index|[]
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
name|Arrays
operator|.
name|asList
argument_list|(
name|primaryFields
argument_list|)
operator|.
name|contains
argument_list|(
name|field
argument_list|)
return|;
block|}
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
comment|/**      * This method returns the BibtexEntryType for the name of a type,      * or null if it does not exist.      */
DECL|method|getType (String name)
specifier|public
specifier|static
name|BibtexEntryType
name|getType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|BibtexEntryType
name|entryType
init|=
name|ALL_TYPES
operator|.
name|get
argument_list|(
name|name
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|entryType
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
name|entryType
return|;
block|}
block|}
comment|/**      * This method returns the standard BibtexEntryType for the      * name of a type, or null if it does not exist.      */
DECL|method|getStandardType (String name)
specifier|public
specifier|static
name|BibtexEntryType
name|getStandardType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|BibtexEntryType
name|entryType
init|=
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|name
operator|.
name|toLowerCase
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|entryType
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
name|entryType
return|;
block|}
block|}
DECL|method|addOrModifyCustomEntryType (CustomEntryType type)
specifier|public
specifier|static
name|void
name|addOrModifyCustomEntryType
parameter_list|(
name|CustomEntryType
name|type
parameter_list|)
block|{
name|ALL_TYPES
operator|.
name|put
argument_list|(
name|type
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|US
argument_list|)
argument_list|,
name|type
argument_list|)
expr_stmt|;
block|}
DECL|method|getAllTypes ()
specifier|public
specifier|static
name|Set
argument_list|<
name|String
argument_list|>
name|getAllTypes
parameter_list|()
block|{
return|return
name|ALL_TYPES
operator|.
name|keySet
argument_list|()
return|;
block|}
DECL|method|getAllValues ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|BibtexEntryType
argument_list|>
name|getAllValues
parameter_list|()
block|{
return|return
name|ALL_TYPES
operator|.
name|values
argument_list|()
return|;
block|}
comment|/**      * Removes a customized entry type from the type map. If this type      * overrode a standard type, we reinstate the standard one.      *      * @param name The customized entry type to remove.      */
DECL|method|removeType (String name)
specifier|public
specifier|static
name|void
name|removeType
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|String
name|toLowerCase
init|=
name|name
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|ALL_TYPES
operator|.
name|remove
argument_list|(
name|toLowerCase
argument_list|)
expr_stmt|;
if|if
condition|(
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|toLowerCase
argument_list|)
operator|!=
literal|null
condition|)
block|{
comment|// In this case the user has removed a customized version
comment|// of a standard type. We reinstate the standard type.
name|addOrModifyCustomEntryType
argument_list|(
operator|(
name|CustomEntryType
operator|)
name|STANDARD_TYPES
operator|.
name|get
argument_list|(
name|toLowerCase
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Load all custom entry types from preferences. This method is      * called from JabRef when the program starts.      */
DECL|method|loadCustomEntryTypes (JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|loadCustomEntryTypes
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|int
name|number
init|=
literal|0
decl_stmt|;
name|CustomEntryType
name|type
decl_stmt|;
while|while
condition|(
operator|(
name|type
operator|=
name|prefs
operator|.
name|getCustomEntryType
argument_list|(
name|number
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|addOrModifyCustomEntryType
argument_list|(
name|type
argument_list|)
expr_stmt|;
name|number
operator|++
expr_stmt|;
block|}
block|}
comment|/**      * Iterate through all entry types, and store those that are      * custom defined to preferences. This method is called from      * JabRefFrame when the program closes.      */
DECL|method|saveCustomEntryTypes (JabRefPreferences prefs)
specifier|public
specifier|static
name|void
name|saveCustomEntryTypes
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|Iterator
argument_list|<
name|String
argument_list|>
name|iterator
init|=
name|ALL_TYPES
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
name|int
name|number
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|iterator
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|Object
name|o
init|=
name|ALL_TYPES
operator|.
name|get
argument_list|(
name|iterator
operator|.
name|next
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|CustomEntryType
condition|)
block|{
comment|// Store this entry type.
name|prefs
operator|.
name|storeCustomEntryType
argument_list|(
operator|(
name|CustomEntryType
operator|)
name|o
argument_list|,
name|number
argument_list|)
expr_stmt|;
name|number
operator|++
expr_stmt|;
block|}
block|}
comment|// Then, if there are more 'old' custom types defined, remove these
comment|// from preferences. This is necessary if the number of custom types
comment|// has decreased.
name|prefs
operator|.
name|purgeCustomEntryTypes
argument_list|(
name|number
argument_list|)
expr_stmt|;
block|}
comment|/**      * Get an array of the required fields in a form appropriate for the entry customization      * dialog - that is, the either-or fields together and separated by slashes.      *      * @return Array of the required fields in a form appropriate for the entry customization dialog.      */
DECL|method|getRequiredFieldsForCustomization ()
specifier|public
name|String
index|[]
name|getRequiredFieldsForCustomization
parameter_list|()
block|{
return|return
name|getRequiredFields
argument_list|()
return|;
block|}
block|}
end_class

end_unit

