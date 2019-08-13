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
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertFalse
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
DECL|class|MathSciNetTest
class|class
name|MathSciNetTest
block|{
DECL|field|fetcher
name|MathSciNet
name|fetcher
decl_stmt|;
DECL|field|ratiuEntry
specifier|private
name|BibEntry
name|ratiuEntry
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
name|MathSciNet
argument_list|(
name|importFormatPreferences
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|ratiuEntry
operator|.
name|setType
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setCiteKey
argument_list|(
literal|"MR3537908"
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Chechkin, Gregory A. and Ratiu, Tudor S. and Romanov, Maxim S. and Samokhin, Vyacheslav N."
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Existence and uniqueness theorems for the two-dimensional {E}ricksen-{L}eslie system"
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|,
literal|"Journal of Mathematical Fluid Mechanics"
argument_list|)
expr_stmt|;
name|ratiuEntry
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
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|NUMBER
argument_list|,
literal|"3"
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"571--589"
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
literal|"1422-6928"
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"76A15 (35A01 35A02 35K61 82D30)"
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MR_NUMBER
argument_list|,
literal|"3537908"
argument_list|)
expr_stmt|;
name|ratiuEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1007/s00021-016-0250-0"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchByEntryFindsEntry ()
name|void
name|searchByEntryFindsEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|searchEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|searchEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"existence"
argument_list|)
expr_stmt|;
name|searchEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Ratiu"
argument_list|)
expr_stmt|;
name|searchEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|,
literal|"fluid"
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
name|searchEntry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|ratiuEntry
argument_list|)
argument_list|,
name|fetchedEntries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|searchByIdInEntryFindsEntry ()
name|void
name|searchByIdInEntryFindsEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|searchEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|searchEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|MR_NUMBER
argument_list|,
literal|"3537908"
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
name|searchEntry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|ratiuEntry
argument_list|)
argument_list|,
name|fetchedEntries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|DisabledOnCIServer
argument_list|(
literal|"CI server has no subscription to MathSciNet and thus gets 401 response"
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
literal|"Existence and uniqueness theorems Two-Dimensional Ericksen Leslie System"
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|fetchedEntries
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ratiuEntry
argument_list|,
name|fetchedEntries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|DisabledOnCIServer
argument_list|(
literal|"CI server has no subscription to MathSciNet and thus gets 401 response"
argument_list|)
DECL|method|searchByIdFindsEntry ()
name|void
name|searchByIdFindsEntry
parameter_list|()
throws|throws
name|Exception
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
literal|"3537908"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|ratiuEntry
argument_list|)
argument_list|,
name|fetchedEntry
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

