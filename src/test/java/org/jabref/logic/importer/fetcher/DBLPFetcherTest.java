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
name|BibtexEntryTypes
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
name|FieldName
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
DECL|class|DBLPFetcherTest
specifier|public
class|class
name|DBLPFetcherTest
block|{
DECL|field|dblpFetcher
specifier|private
name|DBLPFetcher
name|dblpFetcher
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
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
name|dblpFetcher
operator|=
operator|new
name|DBLPFetcher
argument_list|(
name|importFormatPreferences
argument_list|)
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setType
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"DBLP:journals/stt/GeigerHL16"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|TITLE
argument_list|,
literal|"Process Engine Benchmarking with Betsy in the Context of {ISO/IEC} Quality Standards"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
literal|"Matthias Geiger and Simon Harrer and J{\\\"{o}}rg Lenhard"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|,
literal|"Softwaretechnik-Trends"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|VOLUME
argument_list|,
literal|"36"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|NUMBER
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|URL
argument_list|,
literal|"http://pi.informatik.uni-siegen.de/stt/36_2/./03_Technische_Beitraege/ZEUS2016/beitrag_2.pdf"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"biburl"
argument_list|,
literal|"https://dblp.org/rec/bib/journals/stt/GeigerHL16"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"bibsource"
argument_list|,
literal|"dblp computer science bibliography, https://dblp.org"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findSingleEntry ()
specifier|public
name|void
name|findSingleEntry
parameter_list|()
throws|throws
name|FetcherException
block|{
name|String
name|query
init|=
literal|"Process Engine Benchmarking with Betsy in the Context of {ISO/IEC} Quality Standards"
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
name|dblpFetcher
operator|.
name|performSearch
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findSingleEntryUsingComplexOperators ()
specifier|public
name|void
name|findSingleEntryUsingComplexOperators
parameter_list|()
throws|throws
name|FetcherException
block|{
name|String
name|query
init|=
literal|"geiger harrer betsy$ softw.trends"
decl_stmt|;
comment|//-wirtz Negative operators do no longer work,  see issue https://github.com/JabRef/jabref/issues/2890
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
name|dblpFetcher
operator|.
name|performSearch
argument_list|(
name|query
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|entry
argument_list|)
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

