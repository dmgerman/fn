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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|model
operator|.
name|entry
operator|.
name|Author
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
name|AuthorList
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
comment|/**  * Delivers possible completions for a given string.  * Interprets the given values as names and stores them in different  * permutations so we can complete by beginning with last name or first name.  *  * @author kahlert, cordes  */
end_comment

begin_class
DECL|class|NameFieldAutoCompleter
class|class
name|NameFieldAutoCompleter
extends|extends
name|AbstractAutoCompleter
block|{
DECL|field|fieldNames
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|fieldNames
decl_stmt|;
comment|/**      * true if only last names should be completed and there is NO separation by " and ", but by " "      */
DECL|field|lastNameOnlyAndSeparationBySpace
specifier|private
specifier|final
name|boolean
name|lastNameOnlyAndSeparationBySpace
decl_stmt|;
DECL|field|autoCompFF
specifier|private
specifier|final
name|boolean
name|autoCompFF
decl_stmt|;
DECL|field|autoCompLF
specifier|private
specifier|final
name|boolean
name|autoCompLF
decl_stmt|;
DECL|field|autoCompFirstnameMode
specifier|private
specifier|final
name|AutoCompleteFirstNameMode
name|autoCompFirstnameMode
decl_stmt|;
DECL|field|prefix
specifier|private
name|String
name|prefix
init|=
literal|""
decl_stmt|;
comment|/**      * @see AutoCompleterFactory      */
DECL|method|NameFieldAutoCompleter (String fieldName, AutoCompletePreferences preferences)
name|NameFieldAutoCompleter
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompletePreferences
name|preferences
parameter_list|)
block|{
name|this
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fieldName
argument_list|)
argument_list|)
argument_list|,
literal|false
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
block|}
DECL|method|NameFieldAutoCompleter (List<String> fieldNames, boolean lastNameOnlyAndSeparationBySpace, AutoCompletePreferences preferences)
specifier|public
name|NameFieldAutoCompleter
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fieldNames
parameter_list|,
name|boolean
name|lastNameOnlyAndSeparationBySpace
parameter_list|,
name|AutoCompletePreferences
name|preferences
parameter_list|)
block|{
name|super
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|fieldNames
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
name|this
operator|.
name|lastNameOnlyAndSeparationBySpace
operator|=
name|lastNameOnlyAndSeparationBySpace
expr_stmt|;
if|if
condition|(
name|preferences
operator|.
name|getOnlyCompleteFirstLast
argument_list|()
condition|)
block|{
name|autoCompFF
operator|=
literal|true
expr_stmt|;
name|autoCompLF
operator|=
literal|false
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|preferences
operator|.
name|getOnlyCompleteLastFirst
argument_list|()
condition|)
block|{
name|autoCompFF
operator|=
literal|false
expr_stmt|;
name|autoCompLF
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
name|autoCompFF
operator|=
literal|true
expr_stmt|;
name|autoCompLF
operator|=
literal|true
expr_stmt|;
block|}
name|autoCompFirstnameMode
operator|=
name|preferences
operator|.
name|getFirstnameMode
argument_list|()
operator|==
literal|null
condition|?
name|AutoCompleteFirstNameMode
operator|.
name|BOTH
else|:
name|preferences
operator|.
name|getFirstnameMode
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|isSingleUnitField ()
specifier|public
name|boolean
name|isSingleUnitField
parameter_list|()
block|{
comment|// quick hack
comment|// when used at entry fields (!this.lastNameOnlyAndSeparationBySpace), this is a single unit field
comment|// when used at the search form (this.lastNameOnlyAndSeparationBySpace), this is NOT a single unit field
comment|// reason: search keywords are separated by space.
comment|//    This is OK for last names without prefix. "Lastname" works perfectly.
comment|//    querying for "van der Lastname" can be interpreted as
comment|//      a) "van" "der" "Lastname"
comment|//      b) "van der Lastname" (autocompletion lastname)
return|return
operator|!
name|this
operator|.
name|lastNameOnlyAndSeparationBySpace
return|;
block|}
annotation|@
name|Override
DECL|method|addBibtexEntry (BibEntry entry)
specifier|public
name|void
name|addBibtexEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|entry
operator|==
literal|null
condition|)
block|{
return|return;
block|}
for|for
control|(
name|String
name|fieldName
range|:
name|fieldNames
control|)
block|{
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|fieldName
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fieldValue
lambda|->
block|{
name|AuthorList
name|authorList
init|=
name|AuthorList
operator|.
name|parse
argument_list|(
name|fieldValue
argument_list|)
decl_stmt|;
for|for
control|(
name|Author
name|author
range|:
name|authorList
operator|.
name|getAuthors
argument_list|()
control|)
block|{
name|handleAuthor
argument_list|(
name|author
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * SIDE EFFECT: sets class variable prefix      * Delimiter: " and " or " "      *      * @return String without prefix      */
DECL|method|determinePrefixAndReturnRemainder (String str, String delimiter)
specifier|private
name|String
name|determinePrefixAndReturnRemainder
parameter_list|(
name|String
name|str
parameter_list|,
name|String
name|delimiter
parameter_list|)
block|{
name|String
name|result
init|=
name|str
decl_stmt|;
name|int
name|index
init|=
name|result
operator|.
name|toLowerCase
argument_list|()
operator|.
name|lastIndexOf
argument_list|(
name|delimiter
argument_list|)
decl_stmt|;
if|if
condition|(
name|index
operator|>=
literal|0
condition|)
block|{
name|prefix
operator|=
name|result
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
name|index
operator|+
name|delimiter
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
name|result
operator|=
name|result
operator|.
name|substring
argument_list|(
name|index
operator|+
name|delimiter
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|prefix
operator|=
literal|""
expr_stmt|;
block|}
return|return
name|result
return|;
block|}
DECL|method|handleAuthor (Author author)
specifier|private
name|void
name|handleAuthor
parameter_list|(
name|Author
name|author
parameter_list|)
block|{
if|if
condition|(
name|lastNameOnlyAndSeparationBySpace
condition|)
block|{
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getLastOnly
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|autoCompLF
condition|)
block|{
switch|switch
condition|(
name|autoCompFirstnameMode
condition|)
block|{
case|case
name|ONLY_ABBREVIATED
case|:
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getLastFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|ONLY_FULL
case|:
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getLastFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|BOTH
case|:
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getLastFirst
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getLastFirst
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
if|if
condition|(
name|autoCompFF
condition|)
block|{
switch|switch
condition|(
name|autoCompFirstnameMode
condition|)
block|{
case|case
name|ONLY_ABBREVIATED
case|:
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getFirstLast
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|ONLY_FULL
case|:
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getFirstLast
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|BOTH
case|:
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getFirstLast
argument_list|(
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|addItemToIndex
argument_list|(
name|author
operator|.
name|getFirstLast
argument_list|(
literal|false
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|complete (String toComplete)
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|complete
parameter_list|(
name|String
name|toComplete
parameter_list|)
block|{
if|if
condition|(
name|toComplete
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
name|String
name|result
decl_stmt|;
comment|// Normally, one would implement that using
comment|// class inheritance. But this seemed overengineered
if|if
condition|(
name|this
operator|.
name|lastNameOnlyAndSeparationBySpace
condition|)
block|{
name|result
operator|=
name|determinePrefixAndReturnRemainder
argument_list|(
name|toComplete
argument_list|,
literal|" "
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|=
name|determinePrefixAndReturnRemainder
argument_list|(
name|toComplete
argument_list|,
literal|" and "
argument_list|)
expr_stmt|;
block|}
return|return
name|super
operator|.
name|complete
argument_list|(
name|result
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
name|prefix
return|;
block|}
annotation|@
name|Override
DECL|method|getLengthOfShortestWordToAdd ()
specifier|protected
name|int
name|getLengthOfShortestWordToAdd
parameter_list|()
block|{
return|return
literal|1
return|;
block|}
block|}
end_class

end_unit

