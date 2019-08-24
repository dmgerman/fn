begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.search
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|Pattern
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
name|StandardField
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
name|types
operator|.
name|StandardEntryType
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
name|assertFalse
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|SearchQueryTest
specifier|public
class|class
name|SearchQueryTest
block|{
annotation|@
name|Test
DECL|method|testToString ()
specifier|public
name|void
name|testToString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\"asdf\" (case sensitive, regular expression)"
argument_list|,
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\"asdf\" (case insensitive, plain text)"
argument_list|,
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsContainsBasedSearch ()
specifier|public
name|void
name|testIsContainsBasedSearch
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|isContainsBasedSearch
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isContainsBasedSearch
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"author=asdf"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|isContainsBasedSearch
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsGrammarBasedSearch ()
specifier|public
name|void
name|testIsGrammarBasedSearch
parameter_list|()
block|{
name|assertFalse
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|isGrammarBasedSearch
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isGrammarBasedSearch
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"author=asdf"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|isGrammarBasedSearch
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGrammarSearch ()
specifier|public
name|void
name|testGrammarSearch
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
name|addKeyword
argument_list|(
literal|"one two"
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"keywords=\"one two\""
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGrammarSearchFullEntryLastCharMissing ()
specifier|public
name|void
name|testGrammarSearchFullEntryLastCharMissing
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
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"systematic revie"
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"title=\"systematic review\""
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGrammarSearchFullEntry ()
specifier|public
name|void
name|testGrammarSearchFullEntry
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
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"systematic review"
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"title=\"systematic review\""
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchingForOpenBraketInBooktitle ()
specifier|public
name|void
name|testSearchingForOpenBraketInBooktitle
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|BOOKTITLE
argument_list|,
literal|"Super Conference (SC)"
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"booktitle=\"(\""
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchMatchesSingleKeywordNotPart ()
specifier|public
name|void
name|testSearchMatchesSingleKeywordNotPart
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"banana, pineapple, orange"
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"anykeyword==apple"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchMatchesSingleKeyword ()
specifier|public
name|void
name|testSearchMatchesSingleKeyword
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"banana, pineapple, orange"
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"anykeyword==pineapple"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchAllFields ()
specifier|public
name|void
name|testSearchAllFields
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Fruity features"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"banana, pineapple, orange"
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"anyfield==\"fruity features\""
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchAllFieldsNotForSpecificField ()
specifier|public
name|void
name|testSearchAllFieldsNotForSpecificField
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Fruity features"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"banana, pineapple, orange"
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"anyfield=fruit and keywords!=banana"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSearchAllFieldsAndSpecificField ()
specifier|public
name|void
name|testSearchAllFieldsAndSpecificField
parameter_list|()
block|{
name|BibEntry
name|e
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Fruity features"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"banana, pineapple, orange"
argument_list|)
expr_stmt|;
name|SearchQuery
name|searchQuery
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"anyfield=fruit and keywords=apple"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|searchQuery
operator|.
name|isMatch
argument_list|(
name|e
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsMatch ()
specifier|public
name|void
name|testIsMatch
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
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"asdf"
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"BiblatexEntryType"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"author=asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryNotAsRegEx ()
specifier|public
name|void
name|testIsValidQueryNotAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryContainsBracketNotAsRegEx ()
specifier|public
name|void
name|testIsValidQueryContainsBracketNotAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf["
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsNotValidQueryContainsBracketNotAsRegEx ()
specifier|public
name|void
name|testIsNotValidQueryContainsBracketNotAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf["
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryAsRegEx ()
specifier|public
name|void
name|testIsValidQueryAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryWithNumbersAsRegEx ()
specifier|public
name|void
name|testIsValidQueryWithNumbersAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"123"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryContainsBracketAsRegEx ()
specifier|public
name|void
name|testIsValidQueryContainsBracketAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"asdf["
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryWithEqualSignAsRegEx ()
specifier|public
name|void
name|testIsValidQueryWithEqualSignAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"author=asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryWithNumbersAndEqualSignAsRegEx ()
specifier|public
name|void
name|testIsValidQueryWithNumbersAndEqualSignAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"author=123"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryWithEqualSignNotAsRegEx ()
specifier|public
name|void
name|testIsValidQueryWithEqualSignNotAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"author=asdf"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsValidQueryWithNumbersAndEqualSignNotAsRegEx ()
specifier|public
name|void
name|testIsValidQueryWithNumbersAndEqualSignNotAsRegEx
parameter_list|()
block|{
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"author=123"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isMatchedForNormalAndFieldBasedSearchMixed ()
specifier|public
name|void
name|isMatchedForNormalAndFieldBasedSearchMixed
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
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"asdf"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
literal|"text"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"text AND author=asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSimpleTerm ()
specifier|public
name|void
name|testSimpleTerm
parameter_list|()
block|{
name|String
name|query
init|=
literal|"progress"
decl_stmt|;
name|SearchQuery
name|result
init|=
operator|new
name|SearchQuery
argument_list|(
name|query
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|result
operator|.
name|isGrammarBasedSearch
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetPattern ()
specifier|public
name|void
name|testGetPattern
parameter_list|()
block|{
name|String
name|query
init|=
literal|"progress"
decl_stmt|;
name|SearchQuery
name|result
init|=
operator|new
name|SearchQuery
argument_list|(
name|query
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|Pattern
name|pattern
init|=
name|Pattern
operator|.
name|compile
argument_list|(
literal|"(\\Qprogress\\E)"
argument_list|)
decl_stmt|;
comment|//We can't directly compare the pattern objects
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|pattern
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|result
operator|.
name|getPatternForWords
argument_list|()
operator|.
name|map
argument_list|(
name|Pattern
operator|::
name|toString
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

