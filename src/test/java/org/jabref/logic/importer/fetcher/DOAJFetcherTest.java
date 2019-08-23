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
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|utils
operator|.
name|URIBuilder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|json
operator|.
name|JSONObject
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
DECL|class|DOAJFetcherTest
class|class
name|DOAJFetcherTest
block|{
DECL|field|fetcher
name|DOAJFetcher
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
name|getKeywordSeparator
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|fetcher
operator|=
operator|new
name|DOAJFetcher
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
name|Article
argument_list|)
decl_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Wei Wang and Yun He and Tong Li and Jiajun Zhu and Jinzhuo Liu"
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
literal|"10.1155/2018/5913634"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
literal|"1875-919X"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|,
literal|"Scientific Programming"
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
literal|"Hindawi Limited"
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
literal|"An Integrated Model for Information Retrieval Based Change Impact Analysis"
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
literal|"http://dx.doi.org/10.1155/2018/5913634"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|VOLUME
argument_list|,
literal|"2018"
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
literal|"2018"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
literal|"The paper presents an approach to combine multiple existing information retrieval (IR) techniques to support change impact analysis, which seeks to identify the possible outcomes of a change or determine the necessary modifications for affecting a desired change. The approach integrates a bag-of-words based IR technique, where each class or method is abstracted as a set of words, and a neural network based IR technique to derive conceptual couplings from the source code of a software system. We report rigorous empirical assessments of the changes of three open source systems: jEdit, muCommander, and JabRef. The impact sets obtained are evaluated at the method level of granularity, and the results show that our integrated approach provides statistically significant improvements in accuracy across several cut points relative to the accuracies provided by the individual methods employed independently. Improvements in F-score values of up to 7.3%, 10.9%, and 17.3% are obtained over a baseline technique for jEdit, muCommander, and JabRef, respectively."
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
literal|"JabRef impact"
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
annotation|@
name|Test
DECL|method|testBibJSONConverter ()
name|void
name|testBibJSONConverter
parameter_list|()
block|{
name|String
name|jsonString
init|=
literal|"{\n\"title\": \"Design of Finite Word Length Linear-Phase FIR Filters in the Logarithmic Number System Domain\",\n"
operator|+
literal|"\"journal\": {\n\"publisher\": \"Hindawi Publishing Corporation\",\n\"language\": ["
operator|+
literal|"\"English\"],\n\"title\": \"VLSI Design\",\"country\": \"US\",\"volume\": \"2014\""
operator|+
literal|"},\"author\":[{\"name\": \"Syed Asad Alam\"},{\"name\": \"Oscar Gustafsson\""
operator|+
literal|"}\n],\n\"link\":[{\"url\": \"http://dx.doi.org/10.1155/2014/217495\","
operator|+
literal|"\"type\": \"fulltext\"}],\"year\":\"2014\",\"identifier\":[{"
operator|+
literal|"\"type\": \"pissn\",\"id\": \"1065-514X\"},\n{\"type\": \"eissn\","
operator|+
literal|"\"id\": \"1563-5171\"},{\"type\": \"doi\",\"id\": \"10.1155/2014/217495\""
operator|+
literal|"}],\"created_date\":\"2014-05-09T19:38:31Z\"}\""
decl_stmt|;
name|JSONObject
name|jsonObject
init|=
operator|new
name|JSONObject
argument_list|(
name|jsonString
argument_list|)
decl_stmt|;
name|BibEntry
name|bibEntry
init|=
name|DOAJFetcher
operator|.
name|parseBibJSONtoBibtex
argument_list|(
name|jsonObject
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|,
name|bibEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"VLSI Design"
argument_list|)
argument_list|,
name|bibEntry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"10.1155/2014/217495"
argument_list|)
argument_list|,
name|bibEntry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Syed Asad Alam and Oscar Gustafsson"
argument_list|)
argument_list|,
name|bibEntry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Design of Finite Word Length Linear-Phase FIR Filters in the Logarithmic Number System Domain"
argument_list|)
argument_list|,
name|bibEntry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2014"
argument_list|)
argument_list|,
name|bibEntry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchByEmptyQuery ()
specifier|public
name|void
name|searchByEmptyQuery
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|appendSingleWord ()
name|void
name|appendSingleWord
parameter_list|()
throws|throws
name|Exception
block|{
name|URIBuilder
name|builder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"http://example.com/test"
argument_list|)
decl_stmt|;
name|DOAJFetcher
operator|.
name|addPath
argument_list|(
name|builder
argument_list|,
literal|"/example"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"http://example.com/test/example"
argument_list|,
name|builder
operator|.
name|build
argument_list|()
operator|.
name|toASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|appendSingleWordWithSlash ()
name|void
name|appendSingleWordWithSlash
parameter_list|()
throws|throws
name|Exception
block|{
name|URIBuilder
name|builder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"http://example.com/test"
argument_list|)
decl_stmt|;
name|DOAJFetcher
operator|.
name|addPath
argument_list|(
name|builder
argument_list|,
literal|"/example"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"http://example.com/test/example"
argument_list|,
name|builder
operator|.
name|build
argument_list|()
operator|.
name|toASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|appendSlash ()
name|void
name|appendSlash
parameter_list|()
throws|throws
name|Exception
block|{
name|URIBuilder
name|builder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"http://example.com/test"
argument_list|)
decl_stmt|;
name|DOAJFetcher
operator|.
name|addPath
argument_list|(
name|builder
argument_list|,
literal|"/"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"http://example.com/test"
argument_list|,
name|builder
operator|.
name|build
argument_list|()
operator|.
name|toASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|appendTwoWords ()
name|void
name|appendTwoWords
parameter_list|()
throws|throws
name|Exception
block|{
name|URIBuilder
name|builder
init|=
operator|new
name|URIBuilder
argument_list|(
literal|"http://example.com/test"
argument_list|)
decl_stmt|;
name|DOAJFetcher
operator|.
name|addPath
argument_list|(
name|builder
argument_list|,
literal|"example two"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"http://example.com/test/example%20two"
argument_list|,
name|builder
operator|.
name|build
argument_list|()
operator|.
name|toASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

