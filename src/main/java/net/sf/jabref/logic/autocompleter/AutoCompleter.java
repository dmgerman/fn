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
comment|/**      * Formats the specified item. This method is called when an item is selected by the user and we need to determine      * the text to be inserted in the textbox.      *      * @param item the item to format      * @return formated string representation of the item      */
DECL|method|getAutoCompleteText (E item)
name|String
name|getAutoCompleteText
parameter_list|(
name|E
name|item
parameter_list|)
function_decl|;
comment|/**      * Add a BibtexEntry to this AutoCompleter.      * @note The AutoCompleter itself decides which information should be stored for later completion.      */
DECL|method|addBibtexEntry (BibEntry entry)
name|void
name|addBibtexEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
function_decl|;
comment|/**      * States whether the field consists of multiple values (false) or of a single value (true)      *      * Symptom: if false, net.sf.jabref.gui.AutoCompleteListener#getCurrentWord(JTextComponent comp)      * returns current word only, if true, it returns the text beginning from the buffer.      */
DECL|method|isSingleUnitField ()
name|boolean
name|isSingleUnitField
parameter_list|()
function_decl|;
comment|/**      * Unclear what this method should do.      * TODO: Remove this method once the AutoCompleteListener is removed.      */
DECL|method|getPrefix ()
name|String
name|getPrefix
parameter_list|()
function_decl|;
comment|/**      * Returns one or more possible completions for a given string. The returned      * completion depends on which informations were stored while adding      * BibtexEntries. If no suggestions for completions are found, then an empty list is returned.      *      * @see AutoCompleter#addBibtexEntry(BibEntry)      */
DECL|method|complete (String toComplete)
name|List
argument_list|<
name|E
argument_list|>
name|complete
parameter_list|(
name|String
name|toComplete
parameter_list|)
function_decl|;
comment|/**      * Directly adds an item to the AutoCompleter.      * This method should be called only if the information does not comes directly from a BibtexEntry.      * Otherwise the {@link #addBibtexEntry(BibEntry)} is preferred.      * @param item item to add      */
DECL|method|addItemToIndex (E item)
name|void
name|addItemToIndex
parameter_list|(
name|E
name|item
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

