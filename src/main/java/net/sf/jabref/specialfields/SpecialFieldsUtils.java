begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2012-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.specialfields
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|specialfields
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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
name|gui
operator|.
name|undo
operator|.
name|UndoableFieldChange
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
name|util
operator|.
name|UpdateField
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
name|FieldChange
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

begin_import
import|import static
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
operator|.
name|KEYWORDS_FIELD
import|;
end_import

begin_class
DECL|class|SpecialFieldsUtils
specifier|public
class|class
name|SpecialFieldsUtils
block|{
DECL|field|FIELDNAME_PRIORITY
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_PRIORITY
init|=
literal|"priority"
decl_stmt|;
DECL|field|FIELDNAME_RANKING
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_RANKING
init|=
literal|"ranking"
decl_stmt|;
DECL|field|FIELDNAME_RELEVANCE
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_RELEVANCE
init|=
literal|"relevance"
decl_stmt|;
DECL|field|FIELDNAME_QUALITY
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_QUALITY
init|=
literal|"qualityassured"
decl_stmt|;
DECL|field|FIELDNAME_READ
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_READ
init|=
literal|"readstatus"
decl_stmt|;
DECL|field|FIELDNAME_PRINTED
specifier|public
specifier|static
specifier|final
name|String
name|FIELDNAME_PRINTED
init|=
literal|"printed"
decl_stmt|;
DECL|field|PREF_SPECIALFIELDSENABLED
specifier|public
specifier|static
specifier|final
name|String
name|PREF_SPECIALFIELDSENABLED
init|=
literal|"specialFieldsEnabled"
decl_stmt|;
DECL|field|PREF_SPECIALFIELDSENABLED_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SPECIALFIELDSENABLED_DEFAULT
init|=
name|Boolean
operator|.
name|TRUE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_RANKING
specifier|public
specifier|static
specifier|final
name|String
name|PREF_SHOWCOLUMN_RANKING
init|=
literal|"showRankingColumn"
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_RANKING_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_RANKING_DEFAULT
init|=
name|Boolean
operator|.
name|TRUE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_PRIORITY
specifier|public
specifier|static
specifier|final
name|String
name|PREF_SHOWCOLUMN_PRIORITY
init|=
literal|"showPriorityColumn"
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_PRIORITY_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_PRIORITY_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_RELEVANCE
specifier|public
specifier|static
specifier|final
name|String
name|PREF_SHOWCOLUMN_RELEVANCE
init|=
literal|"showRelevanceColumn"
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_RELEVANCE_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_RELEVANCE_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_QUALITY
specifier|public
specifier|static
specifier|final
name|String
name|PREF_SHOWCOLUMN_QUALITY
init|=
literal|"showQualityColumn"
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_QUALITY_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_QUALITY_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_READ
specifier|public
specifier|static
specifier|final
name|String
name|PREF_SHOWCOLUMN_READ
init|=
literal|"showReadColumn"
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_READ_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_READ_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_PRINTED
specifier|public
specifier|static
specifier|final
name|String
name|PREF_SHOWCOLUMN_PRINTED
init|=
literal|"showPrintedColumn"
decl_stmt|;
DECL|field|PREF_SHOWCOLUMN_PRINTED_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SHOWCOLUMN_PRINTED_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
comment|// The choice between PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS and PREF_SERIALIZESPECIALFIELDS is mutually exclusive
comment|// At least in the settings, not in the implementation. But having both confused the users, therefore, having activated both options at the same time has been disabled
DECL|field|PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS
specifier|public
specifier|static
specifier|final
name|String
name|PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS
init|=
literal|"autoSyncSpecialFieldsToKeywords"
decl_stmt|;
DECL|field|PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS_DEFAULT
init|=
name|Boolean
operator|.
name|TRUE
decl_stmt|;
comment|// The choice between PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS and PREF_SERIALIZESPECIALFIELDS is mutually exclusive
DECL|field|PREF_SERIALIZESPECIALFIELDS
specifier|public
specifier|static
specifier|final
name|String
name|PREF_SERIALIZESPECIALFIELDS
init|=
literal|"serializeSpecialFields"
decl_stmt|;
DECL|field|PREF_SERIALIZESPECIALFIELDS_DEFAULT
specifier|public
specifier|static
specifier|final
name|Boolean
name|PREF_SERIALIZESPECIALFIELDS_DEFAULT
init|=
name|Boolean
operator|.
name|FALSE
decl_stmt|;
comment|/****************************************************/
comment|/** generic treatment                              **/
comment|/** no special treatment any more, thanks to enums **/
comment|/****************************************************/
comment|/**      * @param e - Field to be handled      * @param value - may be null to state that field should be emptied      * @param be - BibTeXEntry to be handled      * @param ce - Filled with undo info (if necessary)      * @param nullFieldIfValueIsTheSame - true: field is nulled if value is the same than the current value in be      */
DECL|method|updateField (SpecialField e, String value, BibEntry be, NamedCompound ce, boolean nullFieldIfValueIsTheSame)
specifier|public
specifier|static
name|void
name|updateField
parameter_list|(
name|SpecialField
name|e
parameter_list|,
name|String
name|value
parameter_list|,
name|BibEntry
name|be
parameter_list|,
name|NamedCompound
name|ce
parameter_list|,
name|boolean
name|nullFieldIfValueIsTheSame
parameter_list|)
block|{
name|UpdateField
operator|.
name|updateField
argument_list|(
name|be
argument_list|,
name|e
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|value
argument_list|,
name|nullFieldIfValueIsTheSame
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldChange
lambda|->
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|fieldChange
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
comment|// we cannot use "value" here as updateField has side effects: "nullFieldIfValueIsTheSame" nulls the field if value is the same
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|e
argument_list|,
name|be
operator|.
name|getField
argument_list|(
name|e
operator|.
name|getFieldName
argument_list|()
argument_list|)
argument_list|,
name|be
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
DECL|method|exportFieldToKeywords (SpecialField e, BibEntry be, NamedCompound ce)
specifier|private
specifier|static
name|void
name|exportFieldToKeywords
parameter_list|(
name|SpecialField
name|e
parameter_list|,
name|BibEntry
name|be
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|e
argument_list|,
name|be
operator|.
name|getField
argument_list|(
name|e
operator|.
name|getFieldName
argument_list|()
argument_list|)
argument_list|,
name|be
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
DECL|method|exportFieldToKeywords (SpecialField e, String newValue, BibEntry entry, NamedCompound ce)
specifier|private
specifier|static
name|void
name|exportFieldToKeywords
parameter_list|(
name|SpecialField
name|e
parameter_list|,
name|String
name|newValue
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
if|if
condition|(
operator|!
name|SpecialFieldsUtils
operator|.
name|keywordSyncEnabled
argument_list|()
condition|)
block|{
return|return;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|keywordList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|entry
operator|.
name|getKeywords
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|specialFieldsKeywords
init|=
name|e
operator|.
name|getKeyWords
argument_list|()
decl_stmt|;
name|int
name|foundPos
init|=
operator|-
literal|1
decl_stmt|;
comment|// cleanup keywords
for|for
control|(
name|Object
name|value
range|:
name|specialFieldsKeywords
control|)
block|{
name|int
name|pos
init|=
name|keywordList
operator|.
name|indexOf
argument_list|(
name|value
argument_list|)
decl_stmt|;
if|if
condition|(
name|pos
operator|>=
literal|0
condition|)
block|{
name|foundPos
operator|=
name|pos
expr_stmt|;
name|keywordList
operator|.
name|remove
argument_list|(
name|pos
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|newValue
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|foundPos
operator|==
operator|-
literal|1
condition|)
block|{
name|keywordList
operator|.
name|add
argument_list|(
name|newValue
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|keywordList
operator|.
name|add
argument_list|(
name|foundPos
argument_list|,
name|newValue
argument_list|)
expr_stmt|;
block|}
block|}
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|change
init|=
name|entry
operator|.
name|putKeywords
argument_list|(
name|keywordList
argument_list|)
decl_stmt|;
if|if
condition|(
name|ce
operator|!=
literal|null
operator|&&
name|change
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|change
operator|.
name|get
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Update keywords according to values of special fields      *      * @param nc indicates the undo named compound. May be null      */
DECL|method|syncKeywordsFromSpecialFields (BibEntry be, NamedCompound nc)
specifier|public
specifier|static
name|void
name|syncKeywordsFromSpecialFields
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|NamedCompound
name|nc
parameter_list|)
block|{
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|Priority
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|Rank
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|Relevance
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|Quality
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|ReadStatus
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|nc
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|Printed
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|nc
argument_list|)
expr_stmt|;
block|}
DECL|method|importKeywordsForField (Set<String> keywordList, SpecialField c, BibEntry be, NamedCompound nc)
specifier|private
specifier|static
name|void
name|importKeywordsForField
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|keywordList
parameter_list|,
name|SpecialField
name|c
parameter_list|,
name|BibEntry
name|be
parameter_list|,
name|NamedCompound
name|nc
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|values
init|=
name|c
operator|.
name|getKeyWords
argument_list|()
decl_stmt|;
name|String
name|newValue
init|=
literal|null
decl_stmt|;
for|for
control|(
name|String
name|val
range|:
name|values
control|)
block|{
if|if
condition|(
name|keywordList
operator|.
name|contains
argument_list|(
name|val
argument_list|)
condition|)
block|{
name|newValue
operator|=
name|val
expr_stmt|;
break|break;
block|}
block|}
name|UpdateField
operator|.
name|updateNonDisplayableField
argument_list|(
name|be
argument_list|,
name|c
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|newValue
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldChange
lambda|->
name|nc
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|fieldChange
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * updates field values according to keywords      *      * @param ce indicates the undo named compound. May be null      */
DECL|method|syncSpecialFieldsFromKeywords (BibEntry be, NamedCompound ce)
specifier|public
specifier|static
name|void
name|syncSpecialFieldsFromKeywords
parameter_list|(
name|BibEntry
name|be
parameter_list|,
name|NamedCompound
name|ce
parameter_list|)
block|{
if|if
condition|(
operator|!
name|be
operator|.
name|hasField
argument_list|(
name|KEYWORDS_FIELD
argument_list|)
condition|)
block|{
return|return;
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|keywordList
init|=
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
name|EntryUtil
operator|.
name|getSeparatedKeywords
argument_list|(
name|be
operator|.
name|getField
argument_list|(
name|KEYWORDS_FIELD
argument_list|)
argument_list|)
decl_stmt|;
name|SpecialFieldsUtils
operator|.
name|importKeywordsForField
argument_list|(
name|keywordList
argument_list|,
name|Priority
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|ce
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|importKeywordsForField
argument_list|(
name|keywordList
argument_list|,
name|Rank
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|ce
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|importKeywordsForField
argument_list|(
name|keywordList
argument_list|,
name|Quality
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|ce
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|importKeywordsForField
argument_list|(
name|keywordList
argument_list|,
name|Relevance
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|ce
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|importKeywordsForField
argument_list|(
name|keywordList
argument_list|,
name|ReadStatus
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|ce
argument_list|)
expr_stmt|;
name|SpecialFieldsUtils
operator|.
name|importKeywordsForField
argument_list|(
name|keywordList
argument_list|,
name|Printed
operator|.
name|getInstance
argument_list|()
argument_list|,
name|be
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
comment|/**      * @param fieldName the fieldName      * @return an instance of that field. The returned object is a singleton. null is returned if fieldName does not indicate a special field      */
DECL|method|getSpecialFieldInstanceFromFieldName (String fieldName)
specifier|public
specifier|static
name|SpecialField
name|getSpecialFieldInstanceFromFieldName
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRIORITY
argument_list|)
condition|)
block|{
return|return
name|Priority
operator|.
name|getInstance
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_QUALITY
argument_list|)
condition|)
block|{
return|return
name|Quality
operator|.
name|getInstance
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RANKING
argument_list|)
condition|)
block|{
return|return
name|Rank
operator|.
name|getInstance
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_RELEVANCE
argument_list|)
condition|)
block|{
return|return
name|Relevance
operator|.
name|getInstance
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_READ
argument_list|)
condition|)
block|{
return|return
name|ReadStatus
operator|.
name|getInstance
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFieldsUtils
operator|.
name|FIELDNAME_PRINTED
argument_list|)
condition|)
block|{
return|return
name|Printed
operator|.
name|getInstance
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
comment|/**      * @param fieldName the name of the field to check      * @return true if given field is a special field, false otherwise      */
DECL|method|isSpecialField (String fieldName)
specifier|public
specifier|static
name|boolean
name|isSpecialField
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
return|return
name|SpecialFieldsUtils
operator|.
name|getSpecialFieldInstanceFromFieldName
argument_list|(
name|fieldName
argument_list|)
operator|!=
literal|null
return|;
block|}
DECL|method|keywordSyncEnabled ()
specifier|public
specifier|static
name|boolean
name|keywordSyncEnabled
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_SPECIALFIELDSENABLED
argument_list|)
operator|&&
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|SpecialFieldsUtils
operator|.
name|PREF_AUTOSYNCSPECIALFIELDSTOKEYWORDS
argument_list|)
return|;
block|}
block|}
end_class

end_unit

