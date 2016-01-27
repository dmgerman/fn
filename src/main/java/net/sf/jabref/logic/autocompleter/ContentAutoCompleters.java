begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.autocompleter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
operator|.
name|Abbreviations
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
name|MetaData
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
name|journals
operator|.
name|Abbreviation
import|;
end_import

begin_class
DECL|class|ContentAutoCompleters
specifier|public
class|class
name|ContentAutoCompleters
extends|extends
name|AutoCompleters
block|{
DECL|field|preferences
name|AutoCompletePreferences
name|preferences
decl_stmt|;
DECL|method|ContentAutoCompleters (AutoCompletePreferences preferences)
specifier|public
name|ContentAutoCompleters
parameter_list|(
name|AutoCompletePreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
DECL|method|ContentAutoCompleters (BibDatabase database, MetaData metaData, AutoCompletePreferences preferences)
specifier|public
name|ContentAutoCompleters
parameter_list|(
name|BibDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|AutoCompletePreferences
name|preferences
parameter_list|)
block|{
name|this
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|completeFields
init|=
name|preferences
operator|.
name|getCompleteNames
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
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|autoCompleterFactory
operator|.
name|getFor
argument_list|(
name|field
argument_list|)
decl_stmt|;
name|put
argument_list|(
name|field
argument_list|,
name|autoCompleter
argument_list|)
expr_stmt|;
block|}
name|addDatabase
argument_list|(
name|database
argument_list|)
expr_stmt|;
name|addJournalListToAutoCompleter
argument_list|()
expr_stmt|;
name|addContentSelectorValuesToAutoCompleters
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
block|}
comment|/**      * For all fields with both autocompletion and content selector, add content selector      * values to the autocompleter list:      */
DECL|method|addContentSelectorValuesToAutoCompleters (MetaData metaData)
specifier|public
name|void
name|addContentSelectorValuesToAutoCompleters
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|AutoCompleter
argument_list|<
name|String
argument_list|>
argument_list|>
name|entry
range|:
name|this
operator|.
name|autoCompleters
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|ac
init|=
name|entry
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|items
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|SELECTOR_META_PREFIX
operator|+
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|items
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|String
name|item
range|:
name|items
control|)
block|{
name|ac
operator|.
name|addItemToIndex
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
comment|/**      * If an autocompleter exists for the "journal" field, add all      * journal names in the journal abbreviation list to this autocompleter.      */
DECL|method|addJournalListToAutoCompleter ()
specifier|public
name|void
name|addJournalListToAutoCompleter
parameter_list|()
block|{
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|get
argument_list|(
literal|"journal"
argument_list|)
decl_stmt|;
if|if
condition|(
name|autoCompleter
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|Abbreviations
operator|.
name|journalAbbrev
operator|.
name|getAbbreviations
argument_list|()
control|)
block|{
name|autoCompleter
operator|.
name|addItemToIndex
argument_list|(
name|abbreviation
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

