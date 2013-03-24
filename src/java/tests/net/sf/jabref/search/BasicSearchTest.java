begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref.search
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
package|;
end_package

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
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
name|BibtexEntry
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
name|BibtexEntryType
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
name|Util
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
name|search
operator|.
name|BasicSearch
import|;
end_import

begin_comment
comment|/**  * Test case for BasicSearch.  */
end_comment

begin_class
DECL|class|BasicSearchTest
specifier|public
class|class
name|BasicSearchTest
extends|extends
name|TestCase
block|{
DECL|method|testBasicSearchParsing ()
specifier|public
name|void
name|testBasicSearchParsing
parameter_list|()
block|{
name|BibtexEntry
name|be
init|=
name|makeBibtexEntry
argument_list|()
decl_stmt|;
name|BasicSearch
name|bsCaseSensitive
init|=
operator|new
name|BasicSearch
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|BasicSearch
name|bsCaseInsensitive
init|=
operator|new
name|BasicSearch
argument_list|(
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|BasicSearch
name|bsCaseSensitiveRegexp
init|=
operator|new
name|BasicSearch
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|BasicSearch
name|bsCaseInsensitiveRegexp
init|=
operator|new
name|BasicSearch
argument_list|(
literal|false
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|String
name|query
init|=
literal|"marine 2001 shields"
decl_stmt|;
name|assertEquals
argument_list|(
literal|0
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
literal|1
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
literal|0
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
literal|1
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
literal|0
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
literal|0
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
literal|0
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
literal|0
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
literal|"\"marine [A-Za-z]* larviculture\""
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
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
literal|0
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
literal|0
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
literal|1
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
name|BibtexEntry
name|makeBibtexEntry
parameter_list|()
block|{
name|BibtexEntry
name|e
init|=
operator|new
name|BibtexEntry
argument_list|(
name|Util
operator|.
name|createNeutralId
argument_list|()
argument_list|,
name|BibtexEntryType
operator|.
name|INCOLLECTION
argument_list|)
decl_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Marine finfish larviculture in Europe"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"shields01"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2001"
argument_list|)
expr_stmt|;
name|e
operator|.
name|setField
argument_list|(
literal|"author"
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

