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
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|ArXivIdentifier
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
DECL|class|ArXivTest
specifier|public
class|class
name|ArXivTest
block|{
DECL|field|finder
specifier|private
name|ArXiv
name|finder
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
DECL|field|sliceTheoremPaper
specifier|private
name|BibEntry
name|sliceTheoremPaper
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
name|getKeywordSeparator
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|finder
operator|=
operator|new
name|ArXiv
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
name|sliceTheoremPaper
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Tobias Diez"
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Slice theorem for FrÃ©chet group actions and covariant symplectic field theory"
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DATE
argument_list|,
literal|"2014-05-09"
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
literal|"A general slice theorem for the action of a Fr\\'echet Lie group on a Fr\\'echet manifolds is established. The Nash-Moser theorem provides the fundamental tool to generalize the result of Palais to this infinite-dimensional setting. The presented slice theorem is illustrated by its application to gauge theories: the action of the gauge transformation group admits smooth slices at every point and thus the gauge orbit space is stratified by Fr\\'echet manifolds. Furthermore, a covariant and symplectic formulation of classical field theory is proposed and extensively discussed. At the root of this novel framework is the incorporation of field degrees of freedom F and spacetime M into the product manifold F * M. The induced bigrading of differential forms is used in order to carry over the usual symplectic theory to this new setting. The examples of the Klein-Gordon field and general Yang-Mills theory illustrate that the presented approach conveniently handles the occurring symmetries."
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EPRINT
argument_list|,
literal|"1405.2249v1"
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|FILE
argument_list|,
literal|":http\\://arxiv.org/pdf/1405.2249v1:PDF"
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EPRINTTYPE
argument_list|,
literal|"arXiv"
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EPRINTCLASS
argument_list|,
literal|"math-ph"
argument_list|)
expr_stmt|;
name|sliceTheoremPaper
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"math-ph, math.DG, math.MP, math.SG, 58B99, 58Z05, 58B25, 22E65, 58D19, 53D20, 53D42"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findFullTextForEmptyEntryResultsEmptyOptional ()
specifier|public
name|void
name|findFullTextForEmptyEntryResultsEmptyOptional
parameter_list|()
throws|throws
name|IOException
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|finder
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
DECL|method|findFullTextRejectsNullParameter ()
specifier|public
name|void
name|findFullTextRejectsNullParameter
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|finder
operator|.
name|findFullText
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findFullTextByDOI ()
specifier|public
name|void
name|findFullTextByDOI
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
literal|"10.1529/biophysj.104.047340"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Pause Point Spectra in DNA Constant-Force Unzipping"
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
literal|"http://arxiv.org/pdf/cond-mat/0406246v1"
argument_list|)
argument_list|)
argument_list|,
name|finder
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
DECL|method|findFullTextByEprint ()
specifier|public
name|void
name|findFullTextByEprint
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
name|EPRINT
argument_list|,
literal|"1603.06570"
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
literal|"http://arxiv.org/pdf/1603.06570v1"
argument_list|)
argument_list|)
argument_list|,
name|finder
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
DECL|method|findFullTextByEprintWithPrefix ()
specifier|public
name|void
name|findFullTextByEprintWithPrefix
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
name|EPRINT
argument_list|,
literal|"arXiv:1603.06570"
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
literal|"http://arxiv.org/pdf/1603.06570v1"
argument_list|)
argument_list|)
argument_list|,
name|finder
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
DECL|method|findFullTextByEprintWithUnknownDOI ()
specifier|public
name|void
name|findFullTextByEprintWithUnknownDOI
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
literal|"10.1529/unknown"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EPRINT
argument_list|,
literal|"1603.06570"
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
literal|"http://arxiv.org/pdf/1603.06570v1"
argument_list|)
argument_list|)
argument_list|,
name|finder
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
DECL|method|findFullTextByTitle ()
specifier|public
name|void
name|findFullTextByTitle
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
name|TITLE
argument_list|,
literal|"Pause Point Spectra in DNA Constant-Force Unzipping"
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
literal|"http://arxiv.org/pdf/cond-mat/0406246v1"
argument_list|)
argument_list|)
argument_list|,
name|finder
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
DECL|method|findFullTextByTitleAndPartOfAuthor ()
specifier|public
name|void
name|findFullTextByTitleAndPartOfAuthor
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
name|TITLE
argument_list|,
literal|"Pause Point Spectra in DNA Constant-Force Unzipping"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Weeks and Lucks"
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
literal|"http://arxiv.org/pdf/cond-mat/0406246v1"
argument_list|)
argument_list|)
argument_list|,
name|finder
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
DECL|method|notFindFullTextByUnknownDOI ()
specifier|public
name|void
name|notFindFullTextByUnknownDOI
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
literal|"10.1529/unknown"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|finder
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
DECL|method|notFindFullTextByUnknownId ()
specifier|public
name|void
name|notFindFullTextByUnknownId
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
name|EPRINT
argument_list|,
literal|"1234.12345"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|finder
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
DECL|method|findFullTextByDOINotAvailableInCatalog ()
specifier|public
name|void
name|findFullTextByDOINotAvailableInCatalog
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
literal|"10.1016/0370-2693(77)90015-6"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Superspace formulation of supergravity"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|finder
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
DECL|method|searchEntryByPartOfTitle ()
specifier|public
name|void
name|searchEntryByPartOfTitle
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearch
argument_list|(
literal|"ti:\"slice theorem for Frechet\""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchEntryByPartOfTitleWithAcuteAccent ()
specifier|public
name|void
name|searchEntryByPartOfTitleWithAcuteAccent
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearch
argument_list|(
literal|"ti:\"slice theorem for FrÃ©chet\""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchEntryByOldId ()
specifier|public
name|void
name|searchEntryByOldId
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|expected
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|expected
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Article
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
literal|"H1 Collaboration"
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
literal|"Multi-Electron Production at High Transverse Momenta in ep Collisions at HERA"
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
literal|"2003-07-07"
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
literal|"Multi-electron production is studied at high electron transverse momentum in positron- and electron-proton collisions using the H1 detector at HERA. The data correspond to an integrated luminosity of 115 pb-1. Di-electron and tri-electron event yields are measured. Cross sections are derived in a restricted phase space region dominated by photon-photon collisions. In general good agreement is found with the Standard Model predictions. However, for electron pair invariant masses above 100 GeV, three di-electron events and three tri-electron events are observed, compared to Standard Model expectations of 0.30 \\pm 0.04 and 0.23 \\pm 0.04, respectively."
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EPRINT
argument_list|,
literal|"hep-ex/0307015v1"
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
literal|":http\\://arxiv.org/pdf/hep-ex/0307015v1:PDF"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EPRINTTYPE
argument_list|,
literal|"arXiv"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EPRINTCLASS
argument_list|,
literal|"hep-ex"
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
literal|"hep-ex"
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
literal|"10.1140/epjc/s2003-01326-x"
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
literal|"Eur.Phys.J.C31:17-29,2003"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|expected
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"hep-ex/0307015"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchEntryByIdWith4DigitsAndVersion ()
specifier|public
name|void
name|searchEntryByIdWith4DigitsAndVersion
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"1405.2249v1"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchEntryByIdWith4Digits ()
specifier|public
name|void
name|searchEntryByIdWith4Digits
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"1405.2249"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchEntryByIdWith4DigitsAndPrefix ()
specifier|public
name|void
name|searchEntryByIdWith4DigitsAndPrefix
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"arXiv:1405.2249"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchEntryByIdWith4DigitsAndPrefixAndNotTrimmed ()
specifier|public
name|void
name|searchEntryByIdWith4DigitsAndPrefixAndNotTrimmed
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"arXiv : 1405. 2249"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchEntryByIdWith5Digits ()
specifier|public
name|void
name|searchEntryByIdWith5Digits
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"An Optimal Convergence Theorem for Mean Curvature Flow of Arbitrary Codimension in Hyperbolic Spaces"
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"1503.06747"
argument_list|)
operator|.
name|flatMap
argument_list|(
name|entry
lambda|->
name|entry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchWithMalformedIdThrowsException ()
specifier|public
name|void
name|searchWithMalformedIdThrowsException
parameter_list|()
throws|throws
name|Exception
block|{
name|assertThrows
argument_list|(
name|FetcherException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"123412345"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchIdentifierForSlicePaper ()
specifier|public
name|void
name|searchIdentifierForSlicePaper
parameter_list|()
throws|throws
name|Exception
block|{
name|sliceTheoremPaper
operator|.
name|clearField
argument_list|(
name|StandardField
operator|.
name|EPRINT
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"1405.2249v1"
argument_list|)
argument_list|,
name|finder
operator|.
name|findIdentifier
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchEmptyId ()
specifier|public
name|void
name|searchEmptyId
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchWithHttpUrl ()
specifier|public
name|void
name|searchWithHttpUrl
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"http://arxiv.org/abs/1405.2249"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchWithHttpsUrl ()
specifier|public
name|void
name|searchWithHttpsUrl
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"https://arxiv.org/abs/1405.2249"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchWithHttpsUrlNotTrimmed ()
specifier|public
name|void
name|searchWithHttpsUrlNotTrimmed
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|sliceTheoremPaper
argument_list|)
argument_list|,
name|finder
operator|.
name|performSearchById
argument_list|(
literal|"https : // arxiv . org / abs / 1405 . 2249 "
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

