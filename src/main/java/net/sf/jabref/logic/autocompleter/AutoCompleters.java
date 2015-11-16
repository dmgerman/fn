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
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_class
DECL|class|AutoCompleters
class|class
name|AutoCompleters
block|{
DECL|field|autoCompleters
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|AutoCompleter
argument_list|<
name|String
argument_list|>
argument_list|>
name|autoCompleters
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
comment|// Hashtable that holds as keys the names of the fields where
comment|// autocomplete is active, and references to the autocompleter objects.
DECL|method|get (String fieldName)
specifier|public
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|get
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
return|return
name|autoCompleters
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
return|;
block|}
DECL|method|addDatabase (BibtexDatabase database)
name|void
name|addDatabase
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|)
block|{
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|database
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|addEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * This methods assures all words in the given entry are recorded in their      * respective Completers, if any.      */
DECL|method|addEntry (BibtexEntry bibtexEntry)
specifier|public
name|void
name|addEntry
parameter_list|(
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
for|for
control|(
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
range|:
name|autoCompleters
operator|.
name|values
argument_list|()
control|)
block|{
name|autoCompleter
operator|.
name|addBibtexEntry
argument_list|(
name|bibtexEntry
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|put (String field, AutoCompleter<String> autoCompleter)
name|void
name|put
parameter_list|(
name|String
name|field
parameter_list|,
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
parameter_list|)
block|{
name|autoCompleters
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
end_class

end_unit

