begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.autocompleter
package|package
name|org
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
name|Arrays
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
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|when
import|;
end_import

begin_class
DECL|class|DefaultAutoCompleterTest
specifier|public
class|class
name|DefaultAutoCompleterTest
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|initAutoCompleterWithNullPreferenceThrowsException ()
specifier|public
name|void
name|initAutoCompleterWithNullPreferenceThrowsException
parameter_list|()
block|{
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|initAutoCompleterWithNullFieldThrowsException ()
specifier|public
name|void
name|initAutoCompleterWithNullFieldThrowsException
parameter_list|()
block|{
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|null
argument_list|,
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
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
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"test"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
name|autoCompleter
operator|.
name|addBibtexEntry
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"test"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|autoCompleter
operator|.
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"test"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"test"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"testValue"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"test"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"testvalue"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
DECL|method|completeNullReturnsNothing ()
specifier|public
name|void
name|completeNullReturnsNothing
parameter_list|()
block|{
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
DECL|method|completeEmptyStringReturnsNothing ()
specifier|public
name|void
name|completeEmptyStringReturnsNothing
parameter_list|()
block|{
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
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
name|addBibtexEntry
argument_list|(
name|entryTwo
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"testValue"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
DECL|method|completeShortStringReturnsNothing ()
specifier|public
name|void
name|completeShortStringReturnsNothing
parameter_list|()
block|{
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"va"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
DECL|method|completeTooShortInputReturnsNothing ()
specifier|public
name|void
name|completeTooShortInputReturnsNothing
parameter_list|()
block|{
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|when
argument_list|(
name|preferences
operator|.
name|getShortestLengthToComplete
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"test"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
DECL|method|completeBeginnigOfSecondWordReturnsWord ()
specifier|public
name|void
name|completeBeginnigOfSecondWordReturnsWord
parameter_list|()
block|{
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"val"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
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
DECL|method|completePartOfWordReturnsNothing ()
specifier|public
name|void
name|completePartOfWordReturnsNothing
parameter_list|()
block|{
name|AutoCompletePreferences
name|preferences
init|=
name|mock
argument_list|(
name|AutoCompletePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|DefaultAutoCompleter
name|autoCompleter
init|=
operator|new
name|DefaultAutoCompleter
argument_list|(
literal|"field"
argument_list|,
name|preferences
argument_list|)
decl_stmt|;
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
name|addBibtexEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|autoCompleter
operator|.
name|complete
argument_list|(
literal|"lue"
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
block|}
end_class

end_unit

