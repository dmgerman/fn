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
name|io
operator|.
name|IOException
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
DECL|class|IEEETest
class|class
name|IEEETest
block|{
DECL|field|fetcher
specifier|private
name|IEEE
name|fetcher
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
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
name|IEEE
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
block|}
annotation|@
name|Test
DECL|method|findByDOI ()
name|void
name|findByDOI
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1109/ACCESS.2016.2535486"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
literal|"https://ieeexplore.ieee.org/ielx7/6287639/7419931/07421926.pdf?tp=&arnumber=7421926&isnumber=7419931&ref="
argument_list|)
argument_list|)
argument_list|,
name|fetcher
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findByDocumentUrl ()
name|void
name|findByDocumentUrl
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
literal|"https://ieeexplore.ieee.org/document/7421926/"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
literal|"https://ieeexplore.ieee.org/ielx7/6287639/7419931/07421926.pdf?tp=&arnumber=7421926&isnumber=7419931&ref="
argument_list|)
argument_list|)
argument_list|,
name|fetcher
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findByURL ()
name|void
name|findByURL
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
literal|"https://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=7421926&ref="
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
literal|"https://ieeexplore.ieee.org/ielx7/6287639/7419931/07421926.pdf?tp=&arnumber=7421926&isnumber=7419931&ref="
argument_list|)
argument_list|)
argument_list|,
name|fetcher
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findByOldURL ()
name|void
name|findByOldURL
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
literal|"https://ieeexplore.ieee.org/stamp/stamp.jsp?arnumber=7421926"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
literal|"https://ieeexplore.ieee.org/ielx7/6287639/7419931/07421926.pdf?tp=&arnumber=7421926&isnumber=7419931&ref="
argument_list|)
argument_list|)
argument_list|,
name|fetcher
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findByDOIButNotURL ()
name|void
name|findByDOIButNotURL
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1109/ACCESS.2016.2535486"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
literal|"http://dx.doi.org/10.1109/ACCESS.2016.2535486"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|URL
argument_list|(
literal|"https://ieeexplore.ieee.org/ielx7/6287639/7419931/07421926.pdf?tp=&arnumber=7421926&isnumber=7419931&ref="
argument_list|)
argument_list|)
argument_list|,
name|fetcher
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|DisabledOnCIServer
argument_list|(
literal|"CI server is unreliable"
argument_list|)
DECL|method|notFoundByURL ()
name|void
name|notFoundByURL
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
literal|"http://dx.doi.org/10.1109/ACCESS.2016.2535486"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|fetcher
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|notFoundByDOI ()
name|void
name|notFoundByDOI
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1021/bk-2006-WWW.ch014"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|fetcher
operator|.
name|findFullText
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchResultHasNoKeywordTerms ()
name|void
name|searchResultHasNoKeywordTerms
parameter_list|()
throws|throws
name|FetcherException
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
literal|"Shatakshi Jha and Ikhlaq Hussain and Bhim Singh and Sukumar Mishra"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DATE
argument_list|,
literal|"25 2 2019"
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
literal|"10.1049/iet-rpg.2018.5648"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|FILE
argument_list|,
literal|":https\\://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=8636659:PDF"
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
literal|"1752-1416"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSUE
argument_list|,
literal|"3"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNALTITLE
argument_list|,
literal|"IET Renewable Power Generation"
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
literal|"418--426"
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
literal|"IET"
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
literal|"Optimal operation of PV-DG-battery based microgrid with power quality conditioner"
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
literal|"13"
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
literal|"8636659"
argument_list|)
decl_stmt|;
comment|//article number
name|fetchedEntries
operator|.
name|forEach
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|clearField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|)
argument_list|)
expr_stmt|;
comment|//Remove abstract due to copyright);
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
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Igor Steinmacher and Tayana Uchoa Conte and Christoph Treude and Marco AurÃ©lio Gerosa"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DATE
argument_list|,
literal|"14-22 May 2016"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EVENTDATE
argument_list|,
literal|"14-22 May 2016"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EVENTTITLEADDON
argument_list|,
literal|"Austin, TX"
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
literal|"Austin, TX"
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
literal|"10.1145/2884781.2884806"
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
literal|"978-1-4503-3900-1"
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
literal|"1558-1225"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNALTITLE
argument_list|,
literal|"2016 IEEE/ACM 38th International Conference on Software Engineering (ICSE)"
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
literal|"273--284"
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
literal|"IEEE"
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
literal|"Portals, Documentation, Computer bugs, Joining processes, Industries, Open source software, Newcomers, Newbies, Novices, Beginners, Open Source Software, Barriers, Obstacles, Onboarding, Joining Process"
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
literal|"Overcoming Open Source Project Entry Barriers with a Portal for Newcomers"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|FILE
argument_list|,
literal|":https\\://ieeexplore.ieee.org/stamp/stamp.jsp?tp=&arnumber=7886910:PDF"
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
literal|"Community-based Open Source Software (OSS) projects are usually self-organized and dynamic, receiving contributions from distributed volunteers. Newcomer are important to the survival, long-term success, and continuity of these communities. However, newcomers face many barriers when making their first contribution to an OSS project, leading in many cases to dropouts. Therefore, a major challenge for OSS projects is to provide ways to support newcomers during their first contribution. In this paper, we propose and evaluate FLOSScoach, a portal created to support newcomers to OSS projects. FLOSScoach was designed based on a conceptual model of barriers created in our previous work. To evaluate the portal, we conducted a study with 65 students, relying on qualitative data from diaries, self-efficacy questionnaires, and the Technology Acceptance Model. The results indicate that FLOSScoach played an important role in guiding newcomers and in lowering barriers related to the orientation and contribution process, whereas it was not effective in lowering technical barriers. We also found that FLOSScoach is useful, easy to use, and increased newcomers' confidence to contribute. Our results can help project maintainers on deciding the points that need more attention in order to help OSS project newcomers overcome entry barriers."
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
literal|"Overcoming Open Source Project Entry Barriers with a Portal for Newcomers"
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

