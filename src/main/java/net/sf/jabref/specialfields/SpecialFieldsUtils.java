begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|EntryUtil
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
name|FieldName
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
name|SpecialFields
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|SpecialFieldsUtils
specifier|public
class|class
name|SpecialFieldsUtils
block|{
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
name|getFieldOptional
argument_list|(
name|e
operator|.
name|getFieldName
argument_list|()
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
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
name|getFieldOptional
argument_list|(
name|e
operator|.
name|getFieldName
argument_list|()
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
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
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|KEYWORD_SEPARATOR
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|ce
operator|!=
literal|null
condition|)
block|{
name|change
operator|.
name|ifPresent
argument_list|(
name|changeValue
lambda|->
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|changeValue
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|syncKeywordsFromSpecialFields (BibEntry be)
specifier|public
specifier|static
name|void
name|syncKeywordsFromSpecialFields
parameter_list|(
name|BibEntry
name|be
parameter_list|)
block|{
name|syncKeywordsFromSpecialFields
argument_list|(
name|be
argument_list|,
literal|null
argument_list|)
expr_stmt|;
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
block|{
if|if
condition|(
name|nc
operator|!=
literal|null
condition|)
block|{
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
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|syncSpecialFieldsFromKeywords (BibEntry be)
specifier|public
specifier|static
name|void
name|syncSpecialFieldsFromKeywords
parameter_list|(
name|BibEntry
name|be
parameter_list|)
block|{
name|syncSpecialFieldsFromKeywords
argument_list|(
name|be
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**     * updates field values according to keywords     *     * @param ce indicates the undo named compound. May be null     */
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
name|FieldName
operator|.
name|KEYWORDS
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
name|EntryUtil
operator|.
name|getSeparatedKeywords
argument_list|(
name|be
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
name|Optional
argument_list|<
name|SpecialField
argument_list|>
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
name|SpecialFields
operator|.
name|FIELDNAME_PRIORITY
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Priority
operator|.
name|getInstance
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFields
operator|.
name|FIELDNAME_QUALITY
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Quality
operator|.
name|getInstance
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFields
operator|.
name|FIELDNAME_RANKING
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Rank
operator|.
name|getInstance
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFields
operator|.
name|FIELDNAME_RELEVANCE
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Relevance
operator|.
name|getInstance
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFields
operator|.
name|FIELDNAME_READ
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|ReadStatus
operator|.
name|getInstance
argument_list|()
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|fieldName
operator|.
name|equals
argument_list|(
name|SpecialFields
operator|.
name|FIELDNAME_PRINTED
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Printed
operator|.
name|getInstance
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
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
operator|.
name|isPresent
argument_list|()
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
name|JabRefPreferences
operator|.
name|SPECIALFIELDSENABLED
argument_list|)
operator|&&
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOSYNCSPECIALFIELDSTOKEYWORDS
argument_list|)
return|;
block|}
block|}
end_class

end_unit

