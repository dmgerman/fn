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
name|Collection
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
name|Comparator
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
name|org
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
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
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
comment|/**  * Delivers possible completions as a list of {@link Author}s.  */
end_comment

begin_class
DECL|class|PersonNameSuggestionProvider
specifier|public
class|class
name|PersonNameSuggestionProvider
extends|extends
name|SuggestionProvider
argument_list|<
name|Author
argument_list|>
implements|implements
name|AutoCompleteSuggestionProvider
argument_list|<
name|Author
argument_list|>
block|{
DECL|field|fields
specifier|private
specifier|final
name|Collection
argument_list|<
name|Field
argument_list|>
name|fields
decl_stmt|;
DECL|field|authorComparator
specifier|private
specifier|final
name|Comparator
argument_list|<
name|Author
argument_list|>
name|authorComparator
init|=
name|Comparator
operator|.
name|comparing
argument_list|(
name|Author
operator|::
name|getNameForAlphabetization
argument_list|)
decl_stmt|;
DECL|method|PersonNameSuggestionProvider (Field fieldName)
name|PersonNameSuggestionProvider
parameter_list|(
name|Field
name|fieldName
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
argument_list|)
expr_stmt|;
block|}
DECL|method|PersonNameSuggestionProvider (Collection<Field> fields)
specifier|public
name|PersonNameSuggestionProvider
parameter_list|(
name|Collection
argument_list|<
name|Field
argument_list|>
name|fields
parameter_list|)
block|{
name|super
argument_list|()
expr_stmt|;
name|this
operator|.
name|fields
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|fields
argument_list|)
expr_stmt|;
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
name|Field
name|field
range|:
name|fields
control|)
block|{
name|entry
operator|.
name|getField
argument_list|(
name|field
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
name|addPossibleSuggestions
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
annotation|@
name|Override
DECL|method|getComparator ()
specifier|protected
name|Comparator
argument_list|<
name|Author
argument_list|>
name|getComparator
parameter_list|()
block|{
return|return
name|authorComparator
return|;
block|}
annotation|@
name|Override
DECL|method|isMatch (Author suggestion, AutoCompletionBinding.ISuggestionRequest request)
specifier|protected
name|boolean
name|isMatch
parameter_list|(
name|Author
name|suggestion
parameter_list|,
name|AutoCompletionBinding
operator|.
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
name|getLastFirst
argument_list|(
literal|false
argument_list|)
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
end_class

end_unit

