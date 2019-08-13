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
name|Locale
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

begin_class
annotation|@
name|FetcherTest
DECL|class|CrossRefTest
specifier|public
class|class
name|CrossRefTest
block|{
DECL|field|fetcher
specifier|private
name|CrossRef
name|fetcher
decl_stmt|;
DECL|field|barrosEntry
specifier|private
name|BibEntry
name|barrosEntry
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|fetcher
operator|=
operator|new
name|CrossRef
argument_list|()
expr_stmt|;
name|barrosEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|barrosEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Service Interaction Patterns"
argument_list|)
expr_stmt|;
name|barrosEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Alistair Barros and Marlon Dumas and Arthur H. M. ter Hofstede"
argument_list|)
expr_stmt|;
name|barrosEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2005"
argument_list|)
expr_stmt|;
name|barrosEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1007/11538394_20"
argument_list|)
expr_stmt|;
name|barrosEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
literal|"0302-9743"
argument_list|)
expr_stmt|;
name|barrosEntry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"302-318"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findExactData ()
specifier|public
name|void
name|findExactData
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Service Interaction Patterns"
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
literal|"Barros, Alistair and Dumas, Marlon and Arthur H.M. ter Hofstede"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2005"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1007/11538394_20"
argument_list|,
name|fetcher
operator|.
name|findIdentifier
argument_list|(
name|entry
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findMissingAuthor ()
specifier|public
name|void
name|findMissingAuthor
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Towards Application Portability in Platform as a Service"
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
literal|"Stefan Kolb"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1109/sose.2014.26"
argument_list|,
name|fetcher
operator|.
name|findIdentifier
argument_list|(
name|entry
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findTitleOnly ()
specifier|public
name|void
name|findTitleOnly
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Towards Application Portability in Platform as a Service"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1109/sose.2014.26"
argument_list|,
name|fetcher
operator|.
name|findIdentifier
argument_list|(
name|entry
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|notFindIncompleteTitle ()
specifier|public
name|void
name|notFindIncompleteTitle
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Towards Application Portability"
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
literal|"Stefan Kolb and Guido Wirtz"
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
name|findIdentifier
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptTitleUnderThreshold ()
specifier|public
name|void
name|acceptTitleUnderThreshold
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Towards Application Portability in Platform as a Service----"
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
literal|"Stefan Kolb and Guido Wirtz"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1109/sose.2014.26"
argument_list|,
name|fetcher
operator|.
name|findIdentifier
argument_list|(
name|entry
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|notAcceptTitleOverThreshold ()
specifier|public
name|void
name|notAcceptTitleOverThreshold
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Towards Application Portability in Platform as a Service-----"
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
literal|"Stefan Kolb and Guido Wirtz"
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
name|findIdentifier
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findWrongAuthor ()
specifier|public
name|void
name|findWrongAuthor
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Towards Application Portability in Platform as a Service"
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
literal|"Stefan Kolb and Simon Harrer"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1109/sose.2014.26"
argument_list|,
name|fetcher
operator|.
name|findIdentifier
argument_list|(
name|entry
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findWithSubtitle ()
specifier|public
name|void
name|findWithSubtitle
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
comment|// CrossRef entry will only include { "title": "A break in the clouds", "subtitle": "towards a cloud definition" }
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"A break in the clouds: towards a cloud definition"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"10.1145/1496091.1496100"
argument_list|,
name|fetcher
operator|.
name|findIdentifier
argument_list|(
name|entry
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|getDOI
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ENGLISH
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findByDOI ()
specifier|public
name|void
name|findByDOI
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
name|barrosEntry
argument_list|)
argument_list|,
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.1007/11538394_20"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findByAuthors ()
specifier|public
name|void
name|findByAuthors
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
name|barrosEntry
argument_list|)
argument_list|,
name|fetcher
operator|.
name|performSearch
argument_list|(
literal|"Barros, Alistair and Dumas, Marlon and Arthur H.M. ter Hofstede"
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|findFirst
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findByEntry ()
specifier|public
name|void
name|findByEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Service Interaction Patterns"
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
literal|"Barros, Alistair and Dumas, Marlon and Arthur H.M. ter Hofstede"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"2005"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|barrosEntry
argument_list|)
argument_list|,
name|fetcher
operator|.
name|performSearch
argument_list|(
name|entry
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|findFirst
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|performSearchByIdFindsPaperWithoutTitle ()
specifier|public
name|void
name|performSearchByIdFindsPaperWithoutTitle
parameter_list|()
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Dominik Wujastyk"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1023/a:1003473214310"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
literal|"0019-7246"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"172-176"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|VOLUME
argument_list|,
literal|"42"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|YEAR
argument_list|,
literal|"1999"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|entry
argument_list|)
argument_list|,
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"10.1023/a:1003473214310"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|performSearchByEmptyId ()
specifier|public
name|void
name|performSearchByEmptyId
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
name|fetcher
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
DECL|method|performSearchByEmptyQuery ()
specifier|public
name|void
name|performSearchByEmptyQuery
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
block|}
end_class

end_unit

