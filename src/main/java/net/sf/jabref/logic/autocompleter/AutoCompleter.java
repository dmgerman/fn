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
name|BibtexEntry
import|;
end_import

begin_interface
DECL|interface|AutoCompleter
specifier|public
interface|interface
name|AutoCompleter
block|{
comment|/**      * Add a BibtexEntry to this autocompleter. The autocompleter (respectively      * to the concrete implementations of {@link AbstractAutoCompleter}) itself      * decides which information should be stored for later completion.      */
DECL|method|addBibtexEntry (BibtexEntry entry)
name|void
name|addBibtexEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
comment|/**      * States whether the field consists of multiple values (false) or of a single value (true)      *<p/>      * Symptom: if false, net.sf.jabref.gui.AutoCompleteListener#getCurrentWord(JTextComponent comp)      * returns current word only, if true, it returns the text beginning from the buffer      */
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
DECL|method|complete (String toComplete)
name|String
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

