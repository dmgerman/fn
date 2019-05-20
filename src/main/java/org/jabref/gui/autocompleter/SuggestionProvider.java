begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  * Copyright (c) 2014, 2016 ControlsFX  * All rights reserved.  *  * Redistribution and use in source and binary forms, with or without  * modification, are permitted provided that the following conditions are met:  * * Redistributions of source code must retain the above copyright  * notice, this list of conditions and the following disclaimer.  * * Redistributions in binary form must reproduce the above copyright  * notice, this list of conditions and the following disclaimer in the  * documentation and/or other materials provided with the distribution.  * * Neither the name of ControlsFX, any associated website, nor the  * names of its contributors may be used to endorse or promote products  * derived from this software without specific prior written permission.  *  * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND  * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED  * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY  * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES  * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;  * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND  * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT  * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS  * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.  */
end_comment

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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
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
name|javafx
operator|.
name|util
operator|.
name|Callback
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|textfield
operator|.
name|AutoCompletionBinding
operator|.
name|ISuggestionRequest
import|;
end_import

begin_comment
comment|/**  * This is a simple implementation of a generic suggestion provider callback.  * The complexity of suggestion generation is O(n) where n is the number of possible suggestions.  *  * @param<T> Type of suggestions  *  * This class is a copy of {@link impl.org.controlsfx.autocompletion.SuggestionProvider} with the only difference that  *           we use a set instead of list to store the suggestions in order to eliminate duplicates.  */
end_comment

begin_class
DECL|class|SuggestionProvider
specifier|public
specifier|abstract
class|class
name|SuggestionProvider
parameter_list|<
name|T
parameter_list|>
implements|implements
name|Callback
argument_list|<
name|ISuggestionRequest
argument_list|,
name|Collection
argument_list|<
name|T
argument_list|>
argument_list|>
block|{
DECL|field|possibleSuggestions
specifier|private
specifier|final
name|Collection
argument_list|<
name|T
argument_list|>
name|possibleSuggestions
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|possibleSuggestionsLock
specifier|private
specifier|final
name|Object
name|possibleSuggestionsLock
init|=
operator|new
name|Object
argument_list|()
decl_stmt|;
comment|/**      * Create a default suggestion provider based on the toString() method of the generic objects      * @param possibleSuggestions All possible suggestions      */
DECL|method|create (Collection<T> possibleSuggestions)
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|SuggestionProvider
argument_list|<
name|T
argument_list|>
name|create
parameter_list|(
name|Collection
argument_list|<
name|T
argument_list|>
name|possibleSuggestions
parameter_list|)
block|{
return|return
name|create
argument_list|(
literal|null
argument_list|,
name|possibleSuggestions
argument_list|)
return|;
block|}
comment|/**      * Create a default suggestion provider based on the toString() method of the generic objects      * using the provided stringConverter      *      * @param stringConverter A stringConverter which converts generic T into a string      * @param possibleSuggestions All possible suggestions      */
DECL|method|create (Callback<T, String> stringConverter, Collection<T> possibleSuggestions)
specifier|public
specifier|static
parameter_list|<
name|T
parameter_list|>
name|SuggestionProvider
argument_list|<
name|T
argument_list|>
name|create
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|stringConverter
parameter_list|,
name|Collection
argument_list|<
name|T
argument_list|>
name|possibleSuggestions
parameter_list|)
block|{
name|SuggestionProviderString
argument_list|<
name|T
argument_list|>
name|suggestionProvider
init|=
operator|new
name|SuggestionProviderString
argument_list|<>
argument_list|(
name|stringConverter
argument_list|)
decl_stmt|;
name|suggestionProvider
operator|.
name|addPossibleSuggestions
argument_list|(
name|possibleSuggestions
argument_list|)
expr_stmt|;
return|return
name|suggestionProvider
return|;
block|}
comment|/**      * Add the given new possible suggestions to this  SuggestionProvider      */
DECL|method|addPossibleSuggestions (@uppressWarningsR) T... newPossible)
specifier|public
name|void
name|addPossibleSuggestions
parameter_list|(
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|T
modifier|...
name|newPossible
parameter_list|)
block|{
name|addPossibleSuggestions
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|newPossible
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Add the given new possible suggestions to this  SuggestionProvider      */
DECL|method|addPossibleSuggestions (Collection<T> newPossible)
specifier|public
name|void
name|addPossibleSuggestions
parameter_list|(
name|Collection
argument_list|<
name|T
argument_list|>
name|newPossible
parameter_list|)
block|{
synchronized|synchronized
init|(
name|possibleSuggestionsLock
init|)
block|{
name|possibleSuggestions
operator|.
name|addAll
argument_list|(
name|newPossible
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Remove all current possible suggestions      */
DECL|method|clearSuggestions ()
specifier|public
name|void
name|clearSuggestions
parameter_list|()
block|{
synchronized|synchronized
init|(
name|possibleSuggestionsLock
init|)
block|{
name|possibleSuggestions
operator|.
name|clear
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|call (final ISuggestionRequest request)
specifier|public
specifier|final
name|Collection
argument_list|<
name|T
argument_list|>
name|call
parameter_list|(
specifier|final
name|ISuggestionRequest
name|request
parameter_list|)
block|{
name|List
argument_list|<
name|T
argument_list|>
name|suggestions
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|request
operator|.
name|getUserText
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
synchronized|synchronized
init|(
name|possibleSuggestionsLock
init|)
block|{
for|for
control|(
name|T
name|possibleSuggestion
range|:
name|possibleSuggestions
control|)
block|{
if|if
condition|(
name|isMatch
argument_list|(
name|possibleSuggestion
argument_list|,
name|request
argument_list|)
condition|)
block|{
name|suggestions
operator|.
name|add
argument_list|(
name|possibleSuggestion
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|suggestions
operator|.
name|sort
argument_list|(
name|getComparator
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|suggestions
return|;
block|}
comment|/**      * Get the comparator to order the suggestions      */
DECL|method|getComparator ()
specifier|protected
specifier|abstract
name|Comparator
argument_list|<
name|T
argument_list|>
name|getComparator
parameter_list|()
function_decl|;
comment|/**      * Check the given possible suggestion is a match (is a valid suggestion)      */
DECL|method|isMatch (T suggestion, ISuggestionRequest request)
specifier|protected
specifier|abstract
name|boolean
name|isMatch
parameter_list|(
name|T
name|suggestion
parameter_list|,
name|ISuggestionRequest
name|request
parameter_list|)
function_decl|;
comment|/**      * This is a simple string based suggestion provider.      * All generic suggestions T are turned into strings for processing.      *      */
DECL|class|SuggestionProviderString
specifier|private
specifier|static
class|class
name|SuggestionProviderString
parameter_list|<
name|T
parameter_list|>
extends|extends
name|SuggestionProvider
argument_list|<
name|T
argument_list|>
block|{
DECL|field|stringConverter
specifier|private
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|stringConverter
decl_stmt|;
DECL|field|stringComparator
specifier|private
specifier|final
name|Comparator
argument_list|<
name|T
argument_list|>
name|stringComparator
init|=
operator|new
name|Comparator
argument_list|<
name|T
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|int
name|compare
parameter_list|(
name|T
name|o1
parameter_list|,
name|T
name|o2
parameter_list|)
block|{
name|String
name|o1str
init|=
name|stringConverter
operator|.
name|call
argument_list|(
name|o1
argument_list|)
decl_stmt|;
name|String
name|o2str
init|=
name|stringConverter
operator|.
name|call
argument_list|(
name|o2
argument_list|)
decl_stmt|;
return|return
name|o1str
operator|.
name|compareTo
argument_list|(
name|o2str
argument_list|)
return|;
block|}
block|}
decl_stmt|;
comment|/**          * Create a new SuggestionProviderString          */
DECL|method|SuggestionProviderString (Callback<T, String> stringConverter)
specifier|public
name|SuggestionProviderString
parameter_list|(
name|Callback
argument_list|<
name|T
argument_list|,
name|String
argument_list|>
name|stringConverter
parameter_list|)
block|{
name|this
operator|.
name|stringConverter
operator|=
name|stringConverter
expr_stmt|;
comment|// In case no stringConverter was provided, use the default strategy
if|if
condition|(
name|this
operator|.
name|stringConverter
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|stringConverter
operator|=
name|obj
lambda|->
block|{
return|return
name|obj
operator|!=
literal|null
condition|?
name|obj
operator|.
name|toString
argument_list|()
else|:
literal|""
return|;
comment|//$NON-NLS-1$
block|}
expr_stmt|;
block|}
block|}
comment|/**{@inheritDoc}*/
annotation|@
name|Override
DECL|method|getComparator ()
specifier|protected
name|Comparator
argument_list|<
name|T
argument_list|>
name|getComparator
parameter_list|()
block|{
return|return
name|stringComparator
return|;
block|}
comment|/**{@inheritDoc}*/
annotation|@
name|Override
DECL|method|isMatch (T suggestion, ISuggestionRequest request)
specifier|protected
name|boolean
name|isMatch
parameter_list|(
name|T
name|suggestion
parameter_list|,
name|ISuggestionRequest
name|request
parameter_list|)
block|{
name|String
name|userTextLower
init|=
name|request
operator|.
name|getUserText
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
name|String
name|suggestionStr
init|=
name|suggestion
operator|.
name|toString
argument_list|()
operator|.
name|toLowerCase
argument_list|()
decl_stmt|;
return|return
name|suggestionStr
operator|.
name|contains
argument_list|(
name|userTextLower
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

