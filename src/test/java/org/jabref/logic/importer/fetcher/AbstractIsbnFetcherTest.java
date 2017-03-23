begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fetcher
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|FetcherException
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
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_class
DECL|class|AbstractIsbnFetcherTest
specifier|public
specifier|abstract
class|class
name|AbstractIsbnFetcherTest
block|{
DECL|field|fetcher
specifier|protected
name|AbstractIsbnFetcher
name|fetcher
decl_stmt|;
DECL|field|bibEntry
specifier|protected
name|BibEntry
name|bibEntry
decl_stmt|;
DECL|method|testName ()
specifier|public
specifier|abstract
name|void
name|testName
parameter_list|()
function_decl|;
DECL|method|testHelpPage ()
specifier|public
specifier|abstract
name|void
name|testHelpPage
parameter_list|()
function_decl|;
DECL|method|authorsAreCorrectlyFormatted ()
specifier|public
specifier|abstract
name|void
name|authorsAreCorrectlyFormatted
parameter_list|()
throws|throws
name|Exception
function_decl|;
DECL|method|searchByIdSuccessfulWithShortISBN ()
specifier|public
specifier|abstract
name|void
name|searchByIdSuccessfulWithShortISBN
parameter_list|()
throws|throws
name|FetcherException
function_decl|;
annotation|@
name|Test
DECL|method|searchByIdSuccessfulWithLongISBN ()
specifier|public
name|void
name|searchByIdSuccessfulWithLongISBN
parameter_list|()
throws|throws
name|FetcherException
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"978-0321356680"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|bibEntry
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchByIdReturnsEmptyWithEmptyISBN ()
specifier|public
name|void
name|searchByIdReturnsEmptyWithEmptyISBN
parameter_list|()
throws|throws
name|FetcherException
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntry
init|=
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|FetcherException
operator|.
name|class
argument_list|)
DECL|method|searchByIdThrowsExceptionForShortInvalidISBN ()
specifier|public
name|void
name|searchByIdThrowsExceptionForShortInvalidISBN
parameter_list|()
throws|throws
name|FetcherException
block|{
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"123456789"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|FetcherException
operator|.
name|class
argument_list|)
DECL|method|searchByIdThrowsExceptionForLongInvalidISB ()
specifier|public
name|void
name|searchByIdThrowsExceptionForLongInvalidISB
parameter_list|()
throws|throws
name|FetcherException
block|{
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"012345678910"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|FetcherException
operator|.
name|class
argument_list|)
DECL|method|searchByIdThrowsExceptionForInvalidISBN ()
specifier|public
name|void
name|searchByIdThrowsExceptionForInvalidISBN
parameter_list|()
throws|throws
name|FetcherException
block|{
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"jabref-4-ever"
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

