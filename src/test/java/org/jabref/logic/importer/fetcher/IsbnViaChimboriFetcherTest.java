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
name|assertNotEquals
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
name|FetcherTest
DECL|class|IsbnViaChimboriFetcherTest
specifier|public
class|class
name|IsbnViaChimboriFetcherTest
extends|extends
name|AbstractIsbnFetcherTest
block|{
annotation|@
name|BeforeEach
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
literal|"Effective Java (2nd Edition)"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"Addison-Wesley"
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
literal|"Joshua Bloch"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"isbn"
argument_list|,
literal|"978-0321356680"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"https://www.amazon.com/Effective-Java-2nd-Joshua-Bloch/dp/0321356683?SubscriptionId=AKIAIOBINVZYXZQZ2U3A&tag=chimbori05-20&linkCode=xm2&camp=2025&creative=165953&creativeASIN=0321356683"
argument_list|)
expr_stmt|;
name|fetcher
operator|=
operator|new
name|IsbnViaChimboriFetcher
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
literal|"ISBN (Chimbori/Amazon)"
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
name|get
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
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"0321356683"
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
literal|"9780321356680"
argument_list|)
decl_stmt|;
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
literal|"isbn"
argument_list|,
literal|"9780321356680"
argument_list|)
expr_stmt|;
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
literal|"3642434738"
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
literal|"Springer"
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
literal|"Marlon Dumas and Marcello La Rosa and Jan Mendling and Hajo A. Reijers"
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
literal|"url"
argument_list|,
literal|"https://www.amazon.com/Fundamentals-Business-Process-Management-Marlon/dp/3642434738?SubscriptionId=AKIAIOBINVZYXZQZ2U3A&tag=chimbori05-20&linkCode=xm2&camp=2025&creative=165953&creativeASIN=3642434738"
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
annotation|@
name|Test
DECL|method|searchForIsbnAvailableAtChimboriButNonOnEbookDe ()
specifier|public
name|void
name|searchForIsbnAvailableAtChimboriButNonOnEbookDe
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
name|assertNotEquals
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

