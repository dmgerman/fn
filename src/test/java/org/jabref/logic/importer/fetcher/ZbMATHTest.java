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
name|jabref
operator|.
name|support
operator|.
name|DisabledOnCIServer
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
DECL|class|ZbMATHTest
class|class
name|ZbMATHTest
block|{
DECL|field|fetcher
specifier|private
name|ZbMATH
name|fetcher
decl_stmt|;
DECL|field|donaldsonEntry
specifier|private
name|BibEntry
name|donaldsonEntry
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
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
name|ZbMATH
argument_list|(
name|importFormatPreferences
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|donaldsonEntry
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setCiteKey
argument_list|(
literal|"zbMATH03800580"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"S.K. {Donaldson}"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|,
literal|"Journal of Differential Geometry"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
literal|"0022-040X; 1945-743X/e"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|LANGUAGE
argument_list|,
literal|"English"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"57N13 57R10 53C05 58J99 57R65"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"279--315"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PUBLISHER
argument_list|,
literal|"International Press of Boston, Somerville, MA"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"An application of gauge theory to four dimensional topology."
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|VOLUME
argument_list|,
literal|"18"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"1983"
argument_list|)
expr_stmt|;
name|donaldsonEntry
operator|.
name|setField
argument_list|(
operator|new
name|UnknownField
argument_list|(
literal|"zbl"
argument_list|)
argument_list|,
literal|"0507.57010"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|DisabledOnCIServer
argument_list|(
literal|"CI server has no subscription to zbMath and thus gets 401 response"
argument_list|)
DECL|method|searchByQueryFindsEntry ()
name|void
name|searchByQueryFindsEntry
parameter_list|()
throws|throws
name|Exception
block|{
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
literal|"an:0507.57010"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|donaldsonEntry
argument_list|)
argument_list|,
name|fetchedEntries
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

