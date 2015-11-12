begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.autocompleter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|BibtexDatabase
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
name|journals
operator|.
name|logic
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
DECL|method|ContentAutoCompleters ()
specifier|public
name|ContentAutoCompleters
parameter_list|()
block|{
comment|// Empty AutoCompleter completes nothing
block|}
DECL|method|ContentAutoCompleters (BibtexDatabase database, MetaData metaData)
specifier|public
name|ContentAutoCompleters
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|String
index|[]
name|completeFields
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getStringArray
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_COMPLETE_FIELDS
argument_list|)
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
name|autoCompleter
init|=
name|AutoCompleterFactory
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
name|Vector
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
name|addWordToIndex
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
name|Globals
operator|.
name|journalAbbrev
operator|.
name|getAbbreviations
argument_list|()
control|)
block|{
name|autoCompleter
operator|.
name|addWordToIndex
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

