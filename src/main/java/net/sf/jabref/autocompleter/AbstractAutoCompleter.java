begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|ArrayList
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

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|JTextComponent
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
name|BibtexEntry
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

begin_comment
comment|/**  * An autocompleter delivers possible completions for a given String. There are  * different types of autocompleters for different use cases.  *   * Example: {@link NameFieldAutoCompleter}, {@link EntireFieldAutoCompleter}  *   * @author kahlert, cordes, olly98  * @see AutoCompleterFactory  */
end_comment

begin_class
DECL|class|AbstractAutoCompleter
specifier|public
specifier|abstract
class|class
name|AbstractAutoCompleter
parameter_list|<
name|E
parameter_list|>
block|{
DECL|field|SHORTEST_TO_COMPLETE
specifier|public
specifier|static
name|int
name|SHORTEST_TO_COMPLETE
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|SHORTEST_TO_COMPLETE
argument_list|)
decl_stmt|;
comment|/** 	 * Add a BibtexEntry to this autocompleter. The autocompleter (respectively 	 * to the concrete implementations of {@link AbstractAutoCompleter}) itself 	 * decides which information should be stored for later completion. 	 *  	 */
DECL|method|addBibtexEntry (BibtexEntry entry)
specifier|abstract
specifier|public
name|void
name|addBibtexEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
comment|/** 	 * States whether the field consists of multiple values (false) or of a single value (true) 	 *  	 * Symptom: if false, {@link net.sf.jabref.gui.AutoCompleteListener#getCurrentWord(JTextComponent comp)}  	 * returns current word only, if true, it returns the text beginning from the buffer 	 */
DECL|method|isSingleUnitField ()
specifier|abstract
specifier|public
name|boolean
name|isSingleUnitField
parameter_list|()
function_decl|;
comment|/** 	 * Returns one or more possible completions for a given String. The returned 	 * completion depends on which informations were stored while adding 	 * BibtexEntries by the used implementation of {@link AbstractAutoCompleter} 	 * . 	 *  	 * @see AbstractAutoCompleter#addBibtexEntry(BibtexEntry) 	 */
DECL|method|complete (String str)
specifier|abstract
specifier|public
name|E
index|[]
name|complete
parameter_list|(
name|String
name|str
parameter_list|)
function_decl|;
DECL|method|indexContainsWord (String word)
specifier|abstract
specifier|public
name|boolean
name|indexContainsWord
parameter_list|(
name|String
name|word
parameter_list|)
function_decl|;
DECL|method|addWordToIndex (String word)
specifier|abstract
specifier|public
name|void
name|addWordToIndex
parameter_list|(
name|String
name|word
parameter_list|)
function_decl|;
DECL|method|getPrefix ()
specifier|public
name|String
name|getPrefix
parameter_list|()
block|{
return|return
literal|""
return|;
block|}
block|}
end_class

end_unit

