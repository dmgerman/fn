begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fetcher
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|FetcherTests
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
name|junit
operator|.
name|Assert
operator|.
name|assertTrue
import|;
end_import

begin_class
annotation|@
name|Category
argument_list|(
name|FetcherTests
operator|.
name|class
argument_list|)
DECL|class|GvkFetcherTest
specifier|public
class|class
name|GvkFetcherTest
block|{
DECL|field|fetcher
specifier|private
name|GvkFetcher
name|fetcher
decl_stmt|;
DECL|field|bibEntryPPN591166003
specifier|private
name|BibEntry
name|bibEntryPPN591166003
decl_stmt|;
DECL|field|bibEntryPPN66391437X
specifier|private
name|BibEntry
name|bibEntryPPN66391437X
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|fetcher
operator|=
operator|new
name|GvkFetcher
argument_list|()
expr_stmt|;
name|bibEntryPPN591166003
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setType
argument_list|(
name|BiblatexEntryTypes
operator|.
name|BOOK
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Effective Java"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"Addison-Wesley"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2008"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Joshua Bloch"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"series"
argument_list|,
literal|"The @Java series"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"address"
argument_list|,
literal|"Upper Saddle River, NJ [u.a.]"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"edition"
argument_list|,
literal|"2. ed., 5. print."
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"note"
argument_list|,
literal|"Literaturverz. S. 321 - 325"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"isbn"
argument_list|,
literal|"9780321356680"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"pagetotal"
argument_list|,
literal|"XXI, 346"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"ppn_gvk"
argument_list|,
literal|"591166003"
argument_list|)
expr_stmt|;
name|bibEntryPPN591166003
operator|.
name|setField
argument_list|(
literal|"subtitle"
argument_list|,
literal|"[revised and updated for JAVA SE 6]"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setType
argument_list|(
name|BiblatexEntryTypes
operator|.
name|BOOK
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Effective unit testing"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"Manning"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2013"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Lasse Koskela"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"address"
argument_list|,
literal|"Shelter Island, NY"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"isbn"
argument_list|,
literal|"9781935182573"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"pagetotal"
argument_list|,
literal|"XXIV, 223"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"ppn_gvk"
argument_list|,
literal|"66391437X"
argument_list|)
expr_stmt|;
name|bibEntryPPN66391437X
operator|.
name|setField
argument_list|(
literal|"subtitle"
argument_list|,
literal|"A guide for Java developers"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetName ()
specifier|public
name|void
name|testGetName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"GVK"
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
DECL|method|testGetHelpPage ()
specifier|public
name|void
name|testGetHelpPage
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"GVK"
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
DECL|method|simpleSearchQueryStringCorrect ()
specifier|public
name|void
name|simpleSearchQueryStringCorrect
parameter_list|()
throws|throws
name|FetcherException
block|{
name|String
name|query
init|=
literal|"java jdk"
decl_stmt|;
name|String
name|result
init|=
name|fetcher
operator|.
name|getSearchQueryString
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"pica.all=java jdk"
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|simpleSearchQueryURLCorrect ()
specifier|public
name|void
name|simpleSearchQueryURLCorrect
parameter_list|()
throws|throws
name|MalformedURLException
throws|,
name|URISyntaxException
throws|,
name|FetcherException
block|{
name|String
name|query
init|=
literal|"java jdk"
decl_stmt|;
name|URL
name|url
init|=
name|fetcher
operator|.
name|getURLForQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"http://sru.gbv.de/gvk?version=1.1&operation=searchRetrieve&query=pica.all%3Djava+jdk&maximumRecords=50&recordSchema=picaxml&sortKeys=Year%2C%2C1"
argument_list|,
name|url
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|complexSearchQueryStringCorrect ()
specifier|public
name|void
name|complexSearchQueryStringCorrect
parameter_list|()
throws|throws
name|FetcherException
block|{
name|String
name|query
init|=
literal|"kon java tit jdk"
decl_stmt|;
name|String
name|result
init|=
name|fetcher
operator|.
name|getSearchQueryString
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"pica.kon=java and pica.tit=jdk"
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|complexSearchQueryURLCorrect ()
specifier|public
name|void
name|complexSearchQueryURLCorrect
parameter_list|()
throws|throws
name|MalformedURLException
throws|,
name|URISyntaxException
throws|,
name|FetcherException
block|{
name|String
name|query
init|=
literal|"kon java tit jdk"
decl_stmt|;
name|URL
name|url
init|=
name|fetcher
operator|.
name|getURLForQuery
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"http://sru.gbv.de/gvk?version=1.1&operation=searchRetrieve&query=pica.kon%3Djava+and+pica.tit%3Djdk&maximumRecords=50&recordSchema=picaxml&sortKeys=Year%2C%2C1"
argument_list|,
name|url
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchMatchingMultipleEntries ()
specifier|public
name|void
name|testPerformSearchMatchingMultipleEntries
parameter_list|()
throws|throws
name|FetcherException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|searchResult
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|"tit effective java"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|searchResult
operator|.
name|contains
argument_list|(
name|bibEntryPPN591166003
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|searchResult
operator|.
name|contains
argument_list|(
name|bibEntryPPN66391437X
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearch591166003 ()
specifier|public
name|void
name|testPerformSearch591166003
parameter_list|()
throws|throws
name|FetcherException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|searchResult
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|"ppn 591166003"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|bibEntryPPN591166003
argument_list|)
argument_list|,
name|searchResult
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearch66391437X ()
specifier|public
name|void
name|testPerformSearch66391437X
parameter_list|()
throws|throws
name|FetcherException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|searchResult
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|"ppn 66391437X"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|bibEntryPPN66391437X
argument_list|)
argument_list|,
name|searchResult
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchEmpty ()
specifier|public
name|void
name|testPerformSearchEmpty
parameter_list|()
throws|throws
name|FetcherException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|searchResult
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|searchResult
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

