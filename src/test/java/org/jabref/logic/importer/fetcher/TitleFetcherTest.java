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
DECL|class|TitleFetcherTest
specifier|public
class|class
name|TitleFetcherTest
block|{
DECL|field|fetcher
specifier|private
name|TitleFetcher
name|fetcher
decl_stmt|;
DECL|field|bibEntryBischof2009
specifier|private
name|BibEntry
name|bibEntryBischof2009
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|fetcher
operator|=
operator|new
name|TitleFetcher
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
name|bibEntryBischof2009
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|bibEntryBischof2009
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
expr_stmt|;
name|bibEntryBischof2009
operator|.
name|setCiteKey
argument_list|(
literal|"Bischof_2009"
argument_list|)
expr_stmt|;
name|bibEntryBischof2009
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Marc Bischof and Oliver Kopp and Tammo van Lessen and Frank Leymann"
argument_list|)
expr_stmt|;
name|bibEntryBischof2009
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|BOOKTITLE
argument_list|,
literal|"2009 35th Euromicro Conference on Software Engineering and Advanced Applications"
argument_list|)
expr_stmt|;
name|bibEntryBischof2009
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PUBLISHER
argument_list|,
literal|"{IEEE}"
argument_list|)
expr_stmt|;
name|bibEntryBischof2009
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"{BPELscript}: A Simplified Script Syntax for {WS}-{BPEL} 2.0"
argument_list|)
expr_stmt|;
name|bibEntryBischof2009
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2009"
argument_list|)
expr_stmt|;
name|bibEntryBischof2009
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1109/seaa.2009.21"
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
literal|"Title"
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
literal|"TitleToBibTeX"
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
DECL|method|testPerformSearchKopp2007 ()
specifier|public
name|void
name|testPerformSearchKopp2007
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
literal|"BPELscript: A simplified script syntax for WS-BPEL 2.0"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|bibEntryBischof2009
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchEmptyTitle ()
specifier|public
name|void
name|testPerformSearchEmptyTitle
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
DECL|method|testPerformSearchInvalidTitle ()
specifier|public
name|void
name|testPerformSearchInvalidTitle
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
literal|"An unknown title where noi DOI can be determined"
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

