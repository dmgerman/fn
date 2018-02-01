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
name|assertThrows
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
DECL|class|DoiFetcherTest
specifier|public
class|class
name|DoiFetcherTest
block|{
DECL|field|fetcher
specifier|private
name|DoiFetcher
name|fetcher
decl_stmt|;
DECL|field|bibEntryBurd2011
DECL|field|bibEntryDecker2007
specifier|private
name|BibEntry
name|bibEntryBurd2011
decl_stmt|,
name|bibEntryDecker2007
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
name|DoiFetcher
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
name|bibEntryBurd2011
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|bibEntryBurd2011
operator|.
name|setType
argument_list|(
name|BiblatexEntryTypes
operator|.
name|BOOK
argument_list|)
expr_stmt|;
name|bibEntryBurd2011
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"Burd_2011"
argument_list|)
expr_stmt|;
name|bibEntryBurd2011
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Java{\\textregistered} For Dummies{\\textregistered}"
argument_list|)
expr_stmt|;
name|bibEntryBurd2011
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"Wiley Publishing, Inc."
argument_list|)
expr_stmt|;
name|bibEntryBurd2011
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2011"
argument_list|)
expr_stmt|;
name|bibEntryBurd2011
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Barry Burd"
argument_list|)
expr_stmt|;
name|bibEntryBurd2011
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"jul"
argument_list|)
expr_stmt|;
name|bibEntryBurd2011
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1002/9781118257517"
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setType
argument_list|(
name|BiblatexEntryTypes
operator|.
name|INPROCEEDINGS
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setField
argument_list|(
literal|"bibtexkey"
argument_list|,
literal|"Decker_2007"
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Gero Decker and Oliver Kopp and Frank Leymann and Mathias Weske"
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
literal|"{IEEE} International Conference on Web Services ({ICWS} 2007)"
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"jul"
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setField
argument_list|(
literal|"publisher"
argument_list|,
literal|"{IEEE}"
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"{BPEL}4Chor: Extending {BPEL} for Modeling Choreographies"
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2007"
argument_list|)
expr_stmt|;
name|bibEntryDecker2007
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1109/icws.2007.59"
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
literal|"DOI"
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
literal|"DOItoBibTeX"
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
DECL|method|testPerformSearchBurd2011 ()
specifier|public
name|void
name|testPerformSearchBurd2011
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
literal|"10.1002/9781118257517"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|bibEntryBurd2011
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchDecker2007 ()
specifier|public
name|void
name|testPerformSearchDecker2007
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
literal|"10.1109/ICWS.2007.59"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|bibEntryDecker2007
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
DECL|method|testPerformSearchEmptyDOI ()
specifier|public
name|void
name|testPerformSearchEmptyDOI
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|FetcherException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testPerformSearchInvalidDOI ()
specifier|public
name|void
name|testPerformSearchInvalidDOI
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|FetcherException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.1002/9781118257517F"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

