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
name|Collections
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
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|BeforeEach
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompleterUtil
operator|.
name|getRequest
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertThrows
import|;
end_import

begin_class
DECL|class|DefaultAutoCompleterTest
specifier|public
class|class
name|DefaultAutoCompleterTest
block|{
DECL|field|autoCompleter
specifier|private
name|WordSuggestionProvider
name|autoCompleter
decl_stmt|;
DECL|method|initAutoCompleterWithNullFieldThrowsException ()
specifier|public
name|void
name|initAutoCompleterWithNullFieldThrowsException
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|WordSuggestionProvider
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|autoCompleter
operator|=
operator|new
name|WordSuggestionProvider
argument_list|(
literal|"field"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeWithoutAddingAnythingReturnsNothing ()
specifier|public
name|void
name|completeWithoutAddingAnythingReturnsNothing
parameter_list|()
block|{
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"test"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeAfterAddingNullReturnsNothing ()
specifier|public
name|void
name|completeAfterAddingNullReturnsNothing
parameter_list|()
block|{
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"test"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeAfterAddingEmptyEntryReturnsNothing ()
specifier|public
name|void
name|completeAfterAddingEmptyEntryReturnsNothing
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"test"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeAfterAddingEntryWithoutFieldReturnsNothing ()
specifier|public
name|void
name|completeAfterAddingEntryWithoutFieldReturnsNothing
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"testTitle"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"test"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeValueReturnsValue ()
specifier|public
name|void
name|completeValueReturnsValue
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"testValue"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"testValue"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"testValue"
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeBeginnigOfValueReturnsValue ()
specifier|public
name|void
name|completeBeginnigOfValueReturnsValue
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"testValue"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"test"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"testValue"
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeLowercaseValueReturnsValue ()
specifier|public
name|void
name|completeLowercaseValueReturnsValue
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"testValue"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"testvalue"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"testValue"
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeNullThrowsException ()
specifier|public
name|void
name|completeNullThrowsException
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"testKey"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|null
operator|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeEmptyStringReturnsNothing ()
specifier|public
name|void
name|completeEmptyStringReturnsNothing
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"testKey"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|""
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeReturnsMultipleResults ()
specifier|public
name|void
name|completeReturnsMultipleResults
parameter_list|()
block|{
name|BibEntry
name|entryOne
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entryOne
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"testValueOne"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entryOne
argument_list|)
expr_stmt|;
name|BibEntry
name|entryTwo
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entryTwo
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"testValueTwo"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entryTwo
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"testValue"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"testValueOne"
argument_list|,
literal|"testValueTwo"
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeShortStringReturnsValue ()
specifier|public
name|void
name|completeShortStringReturnsValue
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"val"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"va"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"val"
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completeBeginnigOfSecondWordReturnsWord ()
specifier|public
name|void
name|completeBeginnigOfSecondWordReturnsWord
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"test value"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"val"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"value"
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|completePartOfWordReturnsValue ()
specifier|public
name|void
name|completePartOfWordReturnsValue
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"field"
argument_list|,
literal|"test value"
argument_list|)
expr_stmt|;
name|autoCompleter
operator|.
name|indexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|call
argument_list|(
name|getRequest
argument_list|(
operator|(
literal|"lue"
operator|)
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"value"
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

