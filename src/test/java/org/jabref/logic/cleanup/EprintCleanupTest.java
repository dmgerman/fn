begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.cleanup
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
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

begin_class
DECL|class|EprintCleanupTest
class|class
name|EprintCleanupTest
block|{
annotation|@
name|Test
DECL|method|cleanupCompleteEntry ()
name|void
name|cleanupCompleteEntry
parameter_list|()
block|{
name|BibEntry
name|input
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"journaltitle"
argument_list|,
literal|"arXiv:1502.05795 [math]"
argument_list|)
operator|.
name|withField
argument_list|(
literal|"note"
argument_list|,
literal|"arXiv: 1502.05795"
argument_list|)
operator|.
name|withField
argument_list|(
literal|"url"
argument_list|,
literal|"http://arxiv.org/abs/1502.05795"
argument_list|)
operator|.
name|withField
argument_list|(
literal|"urldate"
argument_list|,
literal|"2018-09-07TZ"
argument_list|)
decl_stmt|;
name|BibEntry
name|expected
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"eprint"
argument_list|,
literal|"1502.05795"
argument_list|)
operator|.
name|withField
argument_list|(
literal|"eprintclass"
argument_list|,
literal|"math"
argument_list|)
operator|.
name|withField
argument_list|(
literal|"eprinttype"
argument_list|,
literal|"arxiv"
argument_list|)
decl_stmt|;
name|EprintCleanup
name|cleanup
init|=
operator|new
name|EprintCleanup
argument_list|()
decl_stmt|;
name|cleanup
operator|.
name|cleanup
argument_list|(
name|input
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|input
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

