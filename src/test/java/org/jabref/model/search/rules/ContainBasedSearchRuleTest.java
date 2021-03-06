begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.search.rules
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|search
operator|.
name|rules
package|;
end_package

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

begin_comment
comment|/**  * Test case for ContainBasedSearchRule.  */
end_comment

begin_class
DECL|class|ContainBasedSearchRuleTest
specifier|public
class|class
name|ContainBasedSearchRuleTest
block|{
annotation|@
name|Test
DECL|method|testBasicSearchParsing ()
specifier|public
name|void
name|testBasicSearchParsing
parameter_list|()
block|{
name|BibEntry
name|be
init|=
name|makeBibtexEntry
argument_list|()
decl_stmt|;
name|ContainBasedSearchRule
name|bsCaseSensitive
init|=
operator|new
name|ContainBasedSearchRule
argument_list|(
literal|true
argument_list|)
decl_stmt|;
name|ContainBasedSearchRule
name|bsCaseInsensitive
init|=
operator|new
name|ContainBasedSearchRule
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|RegexBasedSearchRule
name|bsCaseSensitiveRegexp
init|=
operator|new
name|RegexBasedSearchRule
argument_list|(
literal|true
argument_list|)
decl_stmt|;
name|RegexBasedSearchRule
name|bsCaseInsensitiveRegexp
init|=
operator|new
name|RegexBasedSearchRule
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|String
name|query
init|=
literal|"marine 2001 shields"
decl_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseSensitive
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|true
argument_list|,
name|bsCaseInsensitive
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseSensitiveRegexp
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseInsensitiveRegexp
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|query
operator|=
literal|"\"marine larviculture\""
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseSensitive
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseInsensitive
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseSensitiveRegexp
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseInsensitiveRegexp
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|query
operator|=
literal|"marine [A-Za-z]* larviculture"
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseSensitive
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseInsensitive
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|bsCaseSensitiveRegexp
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|true
argument_list|,
name|bsCaseInsensitiveRegexp
operator|.
name|applyRule
argument_list|(
name|query
argument_list|,
name|be
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|makeBibtexEntry ()
specifier|public
name|BibEntry
name|makeBibtexEntry
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
name|InCollection
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
literal|"Marine finfish larviculture in Europe"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setCiteKey
argument_list|(
literal|"shields01"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2001"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Kevin Shields"
argument_list|)
expr_stmt|;
return|return
name|e
return|;
block|}
block|}
end_class

end_unit

