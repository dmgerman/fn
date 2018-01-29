begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.specialfields
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
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
name|org
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
name|org
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
name|org
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
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Keyword
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|KeywordList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|specialfields
operator|.
name|SpecialField
import|;
end_import

begin_comment
comment|/**  * @deprecated the class should be refactored and partly integrated into BibEntry  * instead of synchronizing special fields with the keyword field, the BibEntry class should have a method  * setSpecialField(field, newValue, syncToKeyword) which directly performs the correct action  * i.e.sets the field to newValue(in the case syncToKeyword=false)or adds newValue to keywords(sync=true)  */
end_comment

begin_class
annotation|@
name|Deprecated
DECL|class|SpecialFieldsUtils
specifier|public
class|class
name|SpecialFieldsUtils
block|{
comment|/**      * @param field                         - Field to be handled      * @param value                     - may be null to state that field should be emptied      * @param entry                        - BibTeXEntry to be handled      * @param nullFieldIfValueIsTheSame - true: field is nulled if value is the same than the current value in be      */
DECL|method|updateField (SpecialField field, String value, BibEntry entry, boolean nullFieldIfValueIsTheSame, boolean isKeywordSyncEnabled, Character keywordDelimiter)
specifier|public
specifier|static
name|List
argument_list|<
name|FieldChange
argument_list|>
name|updateField
parameter_list|(
name|SpecialField
name|field
parameter_list|,
name|String
name|value
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|boolean
name|nullFieldIfValueIsTheSame
parameter_list|,
name|boolean
name|isKeywordSyncEnabled
parameter_list|,
name|Character
name|keywordDelimiter
parameter_list|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|fieldChanges
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|UpdateField
operator|.
name|updateField
argument_list|(
name|entry
argument_list|,
name|field
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
name|fieldChanges
operator|::
name|add
argument_list|)
expr_stmt|;
comment|// we cannot use "value" here as updateField has side effects: "nullFieldIfValueIsTheSame" nulls the field if value is the same
if|if
condition|(
name|isKeywordSyncEnabled
condition|)
block|{
name|fieldChanges
operator|.
name|addAll
argument_list|(
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|field
argument_list|,
name|entry
argument_list|,
name|keywordDelimiter
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|fieldChanges
return|;
block|}
DECL|method|exportFieldToKeywords (SpecialField specialField, BibEntry entry, Character keywordDelimiter)
specifier|private
specifier|static
name|List
argument_list|<
name|FieldChange
argument_list|>
name|exportFieldToKeywords
parameter_list|(
name|SpecialField
name|specialField
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|Character
name|keywordDelimiter
parameter_list|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|fieldChanges
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|KeywordList
name|keyWords
init|=
name|specialField
operator|.
name|getKeyWords
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Keyword
argument_list|>
name|newValue
init|=
name|entry
operator|.
name|getField
argument_list|(
name|specialField
operator|.
name|getFieldName
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|Keyword
operator|::
operator|new
argument_list|)
decl_stmt|;
name|newValue
operator|.
name|map
argument_list|(
name|value
lambda|->
name|entry
operator|.
name|replaceKeywords
argument_list|(
name|keyWords
argument_list|,
name|newValue
operator|.
name|get
argument_list|()
argument_list|,
name|keywordDelimiter
argument_list|)
argument_list|)
operator|.
name|orElseGet
argument_list|(
parameter_list|()
lambda|->
name|entry
operator|.
name|removeKeywords
argument_list|(
name|keyWords
argument_list|,
name|keywordDelimiter
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|changeValue
lambda|->
name|fieldChanges
operator|.
name|add
argument_list|(
name|changeValue
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|fieldChanges
return|;
block|}
comment|/**      * Update keywords according to values of special fields      */
DECL|method|syncKeywordsFromSpecialFields (BibEntry entry, Character keywordDelimiter)
specifier|public
specifier|static
name|List
argument_list|<
name|FieldChange
argument_list|>
name|syncKeywordsFromSpecialFields
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Character
name|keywordDelimiter
parameter_list|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|fieldChanges
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|SpecialField
name|field
range|:
name|SpecialField
operator|.
name|values
argument_list|()
control|)
block|{
name|fieldChanges
operator|.
name|addAll
argument_list|(
name|SpecialFieldsUtils
operator|.
name|exportFieldToKeywords
argument_list|(
name|field
argument_list|,
name|entry
argument_list|,
name|keywordDelimiter
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|fieldChanges
return|;
block|}
DECL|method|importKeywordsForField (KeywordList keywordList, SpecialField field, BibEntry entry)
specifier|private
specifier|static
name|List
argument_list|<
name|FieldChange
argument_list|>
name|importKeywordsForField
parameter_list|(
name|KeywordList
name|keywordList
parameter_list|,
name|SpecialField
name|field
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|fieldChanges
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|KeywordList
name|values
init|=
name|field
operator|.
name|getKeyWords
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Keyword
argument_list|>
name|newValue
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
for|for
control|(
name|Keyword
name|keyword
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
name|keyword
argument_list|)
condition|)
block|{
name|newValue
operator|=
name|Optional
operator|.
name|of
argument_list|(
name|keyword
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
name|UpdateField
operator|.
name|updateNonDisplayableField
argument_list|(
name|entry
argument_list|,
name|field
operator|.
name|getFieldName
argument_list|()
argument_list|,
name|newValue
operator|.
name|map
argument_list|(
name|Keyword
operator|::
name|toString
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldChanges
operator|::
name|add
argument_list|)
expr_stmt|;
return|return
name|fieldChanges
return|;
block|}
comment|/**      * Updates special field values according to keywords      */
DECL|method|syncSpecialFieldsFromKeywords (BibEntry entry, Character keywordDelimiter)
specifier|public
specifier|static
name|List
argument_list|<
name|FieldChange
argument_list|>
name|syncSpecialFieldsFromKeywords
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|Character
name|keywordDelimiter
parameter_list|)
block|{
name|List
argument_list|<
name|FieldChange
argument_list|>
name|fieldChanges
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|entry
operator|.
name|hasField
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
condition|)
block|{
return|return
name|fieldChanges
return|;
block|}
name|KeywordList
name|keywordList
init|=
name|entry
operator|.
name|getKeywords
argument_list|(
name|keywordDelimiter
argument_list|)
decl_stmt|;
for|for
control|(
name|SpecialField
name|field
range|:
name|SpecialField
operator|.
name|values
argument_list|()
control|)
block|{
name|fieldChanges
operator|.
name|addAll
argument_list|(
name|SpecialFieldsUtils
operator|.
name|importKeywordsForField
argument_list|(
name|keywordList
argument_list|,
name|field
argument_list|,
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|fieldChanges
return|;
block|}
DECL|method|synchronizeSpecialFields (KeywordList keywordsToAdd, KeywordList keywordsToRemove)
specifier|public
specifier|static
name|void
name|synchronizeSpecialFields
parameter_list|(
name|KeywordList
name|keywordsToAdd
parameter_list|,
name|KeywordList
name|keywordsToRemove
parameter_list|)
block|{
comment|// we need to check whether a special field is added
comment|// for each field:
comment|//   check if something is added
comment|//   if yes, add all keywords of that special fields to the keywords to be removed
name|KeywordList
name|clone
decl_stmt|;
comment|// Priority
name|clone
operator|=
name|keywordsToAdd
operator|.
name|createClone
argument_list|()
expr_stmt|;
for|for
control|(
name|SpecialField
name|field
range|:
name|SpecialField
operator|.
name|values
argument_list|()
control|)
block|{
name|clone
operator|.
name|retainAll
argument_list|(
name|field
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|clone
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|keywordsToRemove
operator|.
name|addAll
argument_list|(
name|field
operator|.
name|getKeyWords
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

