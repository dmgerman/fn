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
name|logic
operator|.
name|bibtex
operator|.
name|FieldContentParserPreferences
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
name|StandardEntryType
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
name|field
operator|.
name|UnknownField
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
annotation|@
name|FetcherTest
DECL|class|ACMPortalFetcherTest
class|class
name|ACMPortalFetcherTest
block|{
DECL|field|fetcher
name|ACMPortalFetcher
name|fetcher
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
name|void
name|setUp
parameter_list|()
block|{
name|ImportFormatPreferences
name|importFormatPreferences
init|=
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|)
decl_stmt|;
name|when
argument_list|(
name|importFormatPreferences
operator|.
name|getFieldContentParserPreferences
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|mock
argument_list|(
name|FieldContentParserPreferences
operator|.
name|class
argument_list|)
argument_list|)
expr_stmt|;
name|fetcher
operator|=
operator|new
name|ACMPortalFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchByQueryFindsEntry ()
name|void
name|searchByQueryFindsEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|expected
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
decl_stmt|;
name|expected
operator|.
name|setCiteKey
argument_list|(
literal|"Olsson:2017:RCC:3129790.3129810"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
operator|new
name|UnknownField
argument_list|(
literal|"acmid"
argument_list|)
argument_list|,
literal|"3129810"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ADDRESS
argument_list|,
literal|"New York, NY, USA"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Olsson, Tobias and Ericsson, Morgan and Wingkvist, Anna"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|BOOKTITLE
argument_list|,
literal|"Proceedings of the 11th European Conference on Software Architecture: Companion Proceedings"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1145/3129790.3129810"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISBN
argument_list|,
literal|"978-1-4503-5217-8"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"conformance checking, repository data mining, software architecture"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|LOCATION
argument_list|,
literal|"Canterbury, United Kingdom"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
operator|new
name|UnknownField
argument_list|(
literal|"numpages"
argument_list|)
argument_list|,
literal|"7"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"152--158"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PUBLISHER
argument_list|,
literal|"ACM"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|SERIES
argument_list|,
literal|"ECSA '17"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"The Relationship of Code Churn and Architectural Violations in the Open Source Software JabRef"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
literal|"http://doi.acm.org/10.1145/3129790.3129810"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2017"
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|fetchedEntries
init|=
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|"jabref architectural churn"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|expected
argument_list|)
argument_list|,
name|fetchedEntries
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

