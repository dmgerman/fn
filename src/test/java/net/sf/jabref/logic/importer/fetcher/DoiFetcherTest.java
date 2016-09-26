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
name|util
operator|.
name|Optional
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
name|BibLatexEntryTypes
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
name|preferences
operator|.
name|JabRefPreferences
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
name|fail
import|;
end_import

begin_class
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
name|DoiFetcher
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getImportFormatPreferences
argument_list|()
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
name|BibLatexEntryTypes
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
literal|"Wiley-Blackwell"
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
name|bibEntryBurd2011
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"http://dx.doi.org/10.1002/9781118257517"
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
name|BibLatexEntryTypes
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
literal|"Institute of Electrical and Electronics Engineers ({IEEE})"
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
literal|"url"
argument_list|,
literal|"http://dx.doi.org/10.1109/ICWS.2007.59"
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
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|FetcherException
operator|.
name|class
argument_list|)
DECL|method|testPerformSearchEmptyDOI ()
specifier|public
name|void
name|testPerformSearchEmptyDOI
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
DECL|method|testPerformSearchInvalidDOI ()
specifier|public
name|void
name|testPerformSearchInvalidDOI
parameter_list|()
throws|throws
name|FetcherException
block|{
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.1002/9781118257517F"
argument_list|)
expr_stmt|;
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

