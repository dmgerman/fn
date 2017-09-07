begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.autocompleter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|database
operator|.
name|BibDatabase
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
name|FieldProperty
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
name|InternalBibtexFields
import|;
end_import

begin_class
DECL|class|SuggestionProviders
specifier|public
class|class
name|SuggestionProviders
block|{
comment|/**      * key: field name      */
DECL|field|providers
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
argument_list|>
name|providers
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * Empty      */
DECL|method|SuggestionProviders ()
specifier|public
name|SuggestionProviders
parameter_list|()
block|{      }
DECL|method|SuggestionProviders (AutoCompletePreferences preferences, JournalAbbreviationLoader abbreviationLoader)
specifier|public
name|SuggestionProviders
parameter_list|(
name|AutoCompletePreferences
name|preferences
parameter_list|,
name|JournalAbbreviationLoader
name|abbreviationLoader
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|completeFields
init|=
name|preferences
operator|.
name|getCompleteFields
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|completeFields
control|)
block|{
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|autoCompleter
init|=
name|initalizeSuggestionProvider
argument_list|(
name|field
argument_list|,
name|preferences
argument_list|,
name|abbreviationLoader
argument_list|)
decl_stmt|;
name|providers
operator|.
name|put
argument_list|(
name|field
argument_list|,
name|autoCompleter
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getForField (String fieldName)
specifier|public
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|getForField
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
return|return
name|providers
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
return|;
block|}
DECL|method|indexDatabase (BibDatabase database)
specifier|public
name|void
name|indexDatabase
parameter_list|(
name|BibDatabase
name|database
parameter_list|)
block|{
for|for
control|(
name|BibEntry
name|entry
range|:
name|database
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * This methods assures all information in the given entry is included as suggestions.      */
DECL|method|indexEntry (BibEntry bibEntry)
specifier|public
name|void
name|indexEntry
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
for|for
control|(
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|autoCompleter
range|:
name|providers
operator|.
name|values
argument_list|()
control|)
block|{
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|initalizeSuggestionProvider (String fieldName, AutoCompletePreferences preferences, JournalAbbreviationLoader abbreviationLoader)
specifier|private
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|initalizeSuggestionProvider
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompletePreferences
name|preferences
parameter_list|,
name|JournalAbbreviationLoader
name|abbreviationLoader
parameter_list|)
block|{
if|if
condition|(
name|InternalBibtexFields
operator|.
name|getFieldProperties
argument_list|(
name|fieldName
argument_list|)
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|PERSON_NAMES
argument_list|)
condition|)
block|{
return|return
operator|new
name|PersonNameSuggestionProvider
argument_list|(
name|fieldName
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|InternalBibtexFields
operator|.
name|getFieldProperties
argument_list|(
name|fieldName
argument_list|)
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|SINGLE_ENTRY_LINK
argument_list|)
condition|)
block|{
return|return
operator|new
name|ParsedEntryLinkSuggestionProvider
argument_list|()
return|;
comment|//BibTexSuggestionProvider can't be used here, because it would insert the whole entry as string and it can't be used with the ParsedEntyLink type StringConverter together
block|}
elseif|else
if|if
condition|(
name|InternalBibtexFields
operator|.
name|getFieldProperties
argument_list|(
name|fieldName
argument_list|)
operator|.
name|contains
argument_list|(
name|FieldProperty
operator|.
name|JOURNAL_NAME
argument_list|)
operator|||
name|FieldName
operator|.
name|PUBLISHER
operator|.
name|equals
argument_list|(
name|fieldName
argument_list|)
condition|)
block|{
return|return
operator|new
name|JournalsSuggestionProvider
argument_list|(
name|fieldName
argument_list|,
name|preferences
argument_list|,
name|abbreviationLoader
argument_list|)
return|;
block|}
else|else
block|{
return|return
operator|new
name|WordSuggestionProvider
argument_list|(
name|fieldName
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

