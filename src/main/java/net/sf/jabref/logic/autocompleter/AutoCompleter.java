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
name|entry
operator|.
name|BibtexEntry
import|;
end_import

begin_comment
comment|/**  * Delivers possible completions for a given string.  */
end_comment

begin_interface
DECL|interface|AutoCompleter
specifier|public
interface|interface
name|AutoCompleter
parameter_list|<
name|E
parameter_list|>
block|{
comment|/**      * Formats the specified item. This method is called when an item is selected by the user and we need to determine      * the text to be inserted in the textbox.      *       * @param item the item to format      * @return formated string representation of the item      */
DECL|method|getAutoCompleteText (E item)
name|String
name|getAutoCompleteText
parameter_list|(
name|E
name|item
parameter_list|)
function_decl|;
comment|/**      * Add a BibtexEntry to this AutoCompleter. The AutoCompleter (respectively to the concrete implementations of      * {@link AutoCompleter}) itself decides which information should be stored for later completion.      */
DECL|method|addBibtexEntry (BibtexEntry entry)
name|void
name|addBibtexEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
comment|/**      * States whether the field consists of multiple values (false) or of a single value (true)      *      * Symptom: if false, net.sf.jabref.gui.AutoCompleteListener#getCurrentWord(JTextComponent comp) returns current      * word only, if true, it returns the text beginning from the buffer      */
DECL|method|isSingleUnitField ()
name|boolean
name|isSingleUnitField
parameter_list|()
function_decl|;
DECL|method|addWordToIndex (String word)
name|void
name|addWordToIndex
parameter_list|(
name|String
name|word
parameter_list|)
function_decl|;
DECL|method|getPrefix ()
name|String
name|getPrefix
parameter_list|()
function_decl|;
comment|/**      * Returns one or more possible completions for a given string. The returned completion depends on which      * informations were stored while adding BibtexEntries by the used implementation of {@link AutoCompleter}.      *       * @see AutoCompleter#addBibtexEntry(BibtexEntry)      */
DECL|method|complete (String toComplete)
name|E
index|[]
name|complete
parameter_list|(
name|String
name|toComplete
parameter_list|)
function_decl|;
DECL|method|indexContainsWord (String word)
name|boolean
name|indexContainsWord
parameter_list|(
name|String
name|word
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

