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
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|*
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

begin_class
DECL|class|AutoCompleterFactoryTest
specifier|public
class|class
name|AutoCompleterFactoryTest
block|{
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|initFactoryWithNullPreferenceThrowsException ()
specifier|public
name|void
name|initFactoryWithNullPreferenceThrowsException
parameter_list|()
block|{
operator|new
name|AutoCompleterFactory
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getForUnknownFieldReturnsDefaultAutoCompleter ()
specifier|public
name|void
name|getForUnknownFieldReturnsDefaultAutoCompleter
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
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|autoCompleterFactory
operator|.
name|getFor
argument_list|(
literal|"unknownField"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|autoCompleter
operator|instanceof
name|DefaultAutoCompleter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|getForNullThrowsException ()
specifier|public
name|void
name|getForNullThrowsException
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
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|autoCompleterFactory
operator|.
name|getFor
argument_list|(
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getForAuthorReturnsNameFieldAutoCompleter ()
specifier|public
name|void
name|getForAuthorReturnsNameFieldAutoCompleter
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
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|autoCompleterFactory
operator|.
name|getFor
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|autoCompleter
operator|instanceof
name|NameFieldAutoCompleter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getForEditorReturnsNameFieldAutoCompleter ()
specifier|public
name|void
name|getForEditorReturnsNameFieldAutoCompleter
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
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|autoCompleterFactory
operator|.
name|getFor
argument_list|(
literal|"editor"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|autoCompleter
operator|instanceof
name|NameFieldAutoCompleter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getForCrossrefReturnsBibtexKeyAutoCompleter ()
specifier|public
name|void
name|getForCrossrefReturnsBibtexKeyAutoCompleter
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
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|autoCompleterFactory
operator|.
name|getFor
argument_list|(
literal|"crossref"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|autoCompleter
operator|instanceof
name|BibtexKeyAutoCompleter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getForJournalReturnsEntireFieldAutoCompleter ()
specifier|public
name|void
name|getForJournalReturnsEntireFieldAutoCompleter
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
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|autoCompleterFactory
operator|.
name|getFor
argument_list|(
literal|"journal"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|autoCompleter
operator|instanceof
name|EntireFieldAutoCompleter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getForPublisherReturnsEntireFieldAutoCompleter ()
specifier|public
name|void
name|getForPublisherReturnsEntireFieldAutoCompleter
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
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|autoCompleterFactory
operator|.
name|getFor
argument_list|(
literal|"publisher"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|autoCompleter
operator|instanceof
name|EntireFieldAutoCompleter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getPersonAutoCompleterReturnsNameFieldAutoCompleter ()
specifier|public
name|void
name|getPersonAutoCompleterReturnsNameFieldAutoCompleter
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
name|AutoCompleterFactory
name|autoCompleterFactory
init|=
operator|new
name|AutoCompleterFactory
argument_list|(
name|preferences
argument_list|)
decl_stmt|;
name|AutoCompleter
argument_list|<
name|String
argument_list|>
name|autoCompleter
init|=
name|autoCompleterFactory
operator|.
name|getPersonAutoCompleter
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|autoCompleter
operator|instanceof
name|NameFieldAutoCompleter
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

