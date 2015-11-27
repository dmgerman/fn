begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.     This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.     You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|java
operator|.
name|util
operator|.
name|SortedSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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

begin_comment
comment|/**  * An autocompleter delivers possible completions for a given string. There are different types of autocompleters for  * different use cases.  *   * Example: {@link NameFieldAutoCompleter}, {@link EntireFieldAutoCompleter}  *  * @author kahlert, cordes, olly98  * @see AutoCompleterFactory  */
end_comment

begin_class
DECL|class|AbstractAutoCompleter
specifier|public
specifier|abstract
class|class
name|AbstractAutoCompleter
implements|implements
name|AutoCompleter
argument_list|<
name|String
argument_list|>
block|{
DECL|field|SHORTEST_WORD
specifier|private
specifier|static
specifier|final
name|int
name|SHORTEST_WORD
init|=
literal|4
decl_stmt|;
comment|// stores the strings as is
DECL|field|indexCaseSensitive
specifier|private
specifier|final
name|TreeSet
argument_list|<
name|String
argument_list|>
name|indexCaseSensitive
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
comment|// stores strings in lowercase
DECL|field|indexCaseInsensitive
specifier|private
specifier|final
name|TreeSet
argument_list|<
name|String
argument_list|>
name|indexCaseInsensitive
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
comment|// stores for a lowercase string the possible expanded strings
DECL|field|possibleStringsForSearchString
specifier|private
specifier|final
name|HashMap
argument_list|<
name|String
argument_list|,
name|TreeSet
argument_list|<
name|String
argument_list|>
argument_list|>
name|possibleStringsForSearchString
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|addBibtexEntry (BibtexEntry entry)
specifier|public
specifier|abstract
name|void
name|addBibtexEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
function_decl|;
comment|/**      * Returns one or more possible completions for a given String. The returned      * completion depends on which informations were stored while adding      * BibtexEntries by the used implementation of {@link AbstractAutoCompleter}      * .      *      * @see AbstractAutoCompleter#addBibtexEntry(BibtexEntry)      */
annotation|@
name|Override
DECL|method|complete (String toComplete)
specifier|public
name|String
index|[]
name|complete
parameter_list|(
name|String
name|toComplete
parameter_list|)
block|{
if|if
condition|(
name|AbstractAutoCompleter
operator|.
name|stringMinLength
argument_list|(
name|toComplete
argument_list|)
condition|)
block|{
return|return
literal|null
return|;
block|}
name|String
name|lowerCase
init|=
name|toComplete
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
if|if
condition|(
name|lowerCase
operator|.
name|equals
argument_list|(
name|toComplete
argument_list|)
condition|)
block|{
comment|// user typed in lower case word -> we do an case-insenstive search
name|String
name|ender
init|=
name|AbstractAutoCompleter
operator|.
name|incrementLastCharacter
argument_list|(
name|lowerCase
argument_list|)
decl_stmt|;
name|SortedSet
argument_list|<
name|String
argument_list|>
name|subset
init|=
name|indexCaseInsensitive
operator|.
name|subSet
argument_list|(
name|lowerCase
argument_list|,
name|ender
argument_list|)
decl_stmt|;
comment|// As subset only contains lower case strings,
comment|// we have to to determine possible strings for each hit
name|ArrayList
argument_list|<
name|String
argument_list|>
name|result
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|subset
control|)
block|{
name|result
operator|.
name|addAll
argument_list|(
name|possibleStringsForSearchString
operator|.
name|get
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|result
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|result
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
else|else
block|{
comment|// user typed in a mix of upper case and lower case,
comment|// we assume user wants to have exact search
name|String
name|ender
init|=
name|AbstractAutoCompleter
operator|.
name|incrementLastCharacter
argument_list|(
name|toComplete
argument_list|)
decl_stmt|;
name|SortedSet
argument_list|<
name|String
argument_list|>
name|subset
init|=
name|indexCaseSensitive
operator|.
name|subSet
argument_list|(
name|toComplete
argument_list|,
name|ender
argument_list|)
decl_stmt|;
return|return
name|subset
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|subset
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
block|}
comment|/**      * Increments the last character of a string.      *      * Example: incrementLastCharacter("abc") returns "abd".      */
DECL|method|incrementLastCharacter (String toIncrement)
specifier|private
specifier|static
name|String
name|incrementLastCharacter
parameter_list|(
name|String
name|toIncrement
parameter_list|)
block|{
if|if
condition|(
name|toIncrement
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
literal|""
return|;
block|}
name|char
name|lastChar
init|=
name|toIncrement
operator|.
name|charAt
argument_list|(
name|toIncrement
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
return|return
name|toIncrement
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|toIncrement
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
operator|+
name|Character
operator|.
name|toString
argument_list|(
call|(
name|char
call|)
argument_list|(
name|lastChar
operator|+
literal|1
argument_list|)
argument_list|)
return|;
block|}
DECL|method|stringMinLength (String toCheck)
specifier|private
specifier|static
name|boolean
name|stringMinLength
parameter_list|(
name|String
name|toCheck
parameter_list|)
block|{
return|return
name|toCheck
operator|.
name|length
argument_list|()
operator|<
name|AutoCompleterFactory
operator|.
name|SHORTEST_TO_COMPLETE
return|;
block|}
annotation|@
name|Override
DECL|method|addWordToIndex (String word)
specifier|public
name|void
name|addWordToIndex
parameter_list|(
name|String
name|word
parameter_list|)
block|{
if|if
condition|(
name|word
operator|.
name|length
argument_list|()
operator|>=
name|AbstractAutoCompleter
operator|.
name|SHORTEST_WORD
condition|)
block|{
name|indexCaseSensitive
operator|.
name|add
argument_list|(
name|word
argument_list|)
expr_stmt|;
comment|// insensitive treatment
comment|// first, add the lower cased word to search index
comment|// second, add a mapping from the lower cased word to the real word
name|String
name|lowerCase
init|=
name|word
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|indexCaseInsensitive
operator|.
name|add
argument_list|(
name|lowerCase
argument_list|)
expr_stmt|;
name|TreeSet
argument_list|<
name|String
argument_list|>
name|set
init|=
name|possibleStringsForSearchString
operator|.
name|get
argument_list|(
name|lowerCase
argument_list|)
decl_stmt|;
if|if
condition|(
name|set
operator|==
literal|null
condition|)
block|{
name|set
operator|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
expr_stmt|;
block|}
name|set
operator|.
name|add
argument_list|(
name|word
argument_list|)
expr_stmt|;
name|possibleStringsForSearchString
operator|.
name|put
argument_list|(
name|lowerCase
argument_list|,
name|set
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|indexContainsWord (String word)
specifier|public
name|boolean
name|indexContainsWord
parameter_list|(
name|String
name|word
parameter_list|)
block|{
return|return
name|indexCaseInsensitive
operator|.
name|contains
argument_list|(
name|word
operator|.
name|toLowerCase
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|getAutoCompleteText (String item)
specifier|public
name|String
name|getAutoCompleteText
parameter_list|(
name|String
name|item
parameter_list|)
block|{
return|return
name|item
return|;
block|}
block|}
end_class

end_unit

