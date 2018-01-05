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
name|logic
operator|.
name|importer
operator|.
name|ImportFormatPreferences
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
name|BiblatexEntryTypes
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|FetcherTest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
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
import|import
name|org
operator|.
name|junit
operator|.
name|experimental
operator|.
name|categories
operator|.
name|Category
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|Answers
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

begin_class
annotation|@
name|Category
argument_list|(
name|FetcherTest
operator|.
name|class
argument_list|)
DECL|class|IsbnViaEbookDeFetcherTest
specifier|public
class|class
name|IsbnViaEbookDeFetcherTest
extends|extends
name|AbstractIsbnFetcherTest
block|{
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|bibEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|bibEntry
operator|.
name|setType
argument_list|(
name|BiblatexEntryTypes
operator|.
name|BOOK
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"9780321356680"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Effective Java"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"Addison Wesley"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2008"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Bloch, Joshua"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2008-05-08"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"ean"
argument_list|,
literal|"9780321356680"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"isbn"
argument_list|,
literal|"0321356683"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"pagetotal"
argument_list|,
literal|"384"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"http://www.ebook.de/de/product/6441328/joshua_bloch_effective_java.html"
argument_list|)
expr_stmt|;
name|fetcher
operator|=
operator|new
name|IsbnViaEbookDeFetcher
argument_list|(
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Override
DECL|method|testName ()
specifier|public
name|void
name|testName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"ISBN (ebook.de)"
argument_list|,
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Override
DECL|method|testHelpPage ()
specifier|public
name|void
name|testHelpPage
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"ISBNtoBibTeX"
argument_list|,
name|fetcher
operator|.
name|getHelpPage
argument_list|()
operator|.
name|getPageName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Override
DECL|method|searchByIdSuccessfulWithShortISBN ()
specifier|public
name|void
name|searchByIdSuccessfulWithShortISBN
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
literal|"0321356683"
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
annotation|@
name|Override
DECL|method|authorsAreCorrectlyFormatted ()
specifier|public
name|void
name|authorsAreCorrectlyFormatted
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|bibEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bibEntry
operator|.
name|setType
argument_list|(
name|BiblatexEntryTypes
operator|.
name|BOOK
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"9783642434730"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Fundamentals of Business Process Management"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"Springer Berlin Heidelberg"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2015"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Dumas, Marlon and Rosa, Marcello La and Mendling, Jan and Reijers, Hajo A."
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2015-04-12"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"ean"
argument_list|,
literal|"9783642434730"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"isbn"
argument_list|,
literal|"3642434738"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"pagetotal"
argument_list|,
literal|"428"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"http://www.ebook.de/de/product/23955263/marlon_dumas_marcello_la_rosa_jan_mendling_hajo_a_reijers_fundamentals_of_business_process_management.html"
argument_list|)
expr_stmt|;
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
literal|"3642434738"
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
comment|/**      * This test searches for a valid ISBN. See https://www.amazon.de/dp/3728128155/?tag=jabref-21      * However, this ISBN is not available on ebook.de. The fetcher should return nothing rather than throwing an exeption.      */
annotation|@
name|Test
DECL|method|searchForValidButNotFoundISBN ()
specifier|public
name|void
name|searchForValidButNotFoundISBN
parameter_list|()
throws|throws
name|Exception
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
literal|"3728128155"
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
block|}
end_class

end_unit

