begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|Collection
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
name|org
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
import|;
end_import

begin_comment
comment|/**  * Enriches a suggestion provider by a given set of content selector values.  */
end_comment

begin_class
DECL|class|ContentSelectorSuggestionProvider
specifier|public
class|class
name|ContentSelectorSuggestionProvider
implements|implements
name|AutoCompleteSuggestionProvider
argument_list|<
name|String
argument_list|>
block|{
DECL|field|suggestionProvider
specifier|private
specifier|final
name|AutoCompleteSuggestionProvider
argument_list|<
name|String
argument_list|>
name|suggestionProvider
decl_stmt|;
DECL|field|contentSelectorValues
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|contentSelectorValues
decl_stmt|;
DECL|method|ContentSelectorSuggestionProvider (AutoCompleteSuggestionProvider<String> suggestionProvider, List<String> contentSelectorValues)
specifier|public
name|ContentSelectorSuggestionProvider
parameter_list|(
name|AutoCompleteSuggestionProvider
argument_list|<
name|String
argument_list|>
name|suggestionProvider
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|contentSelectorValues
parameter_list|)
block|{
name|this
operator|.
name|suggestionProvider
operator|=
name|suggestionProvider
expr_stmt|;
name|this
operator|.
name|contentSelectorValues
operator|=
name|contentSelectorValues
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|call (AutoCompletionBinding.ISuggestionRequest request)
specifier|public
name|Collection
argument_list|<
name|String
argument_list|>
name|call
parameter_list|(
name|AutoCompletionBinding
operator|.
name|ISuggestionRequest
name|request
parameter_list|)
block|{
name|List
argument_list|<
name|String
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
name|suggestionProvider
operator|!=
literal|null
condition|)
block|{
name|suggestions
operator|.
name|addAll
argument_list|(
name|suggestionProvider
operator|.
name|call
argument_list|(
name|request
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|suggestions
operator|.
name|addAll
argument_list|(
name|contentSelectorValues
argument_list|)
expr_stmt|;
return|return
name|suggestions
return|;
block|}
annotation|@
name|Override
DECL|method|indexEntry (BibEntry entry)
specifier|public
name|void
name|indexEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
name|suggestionProvider
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

