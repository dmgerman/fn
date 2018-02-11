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
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|bibtex
operator|.
name|BibEntryAssert
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
name|fileformat
operator|.
name|GvkParser
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
name|assertNotNull
import|;
end_import

begin_class
DECL|class|GvkParserTest
specifier|public
class|class
name|GvkParserTest
block|{
DECL|method|doTest (String xmlName, int expectedSize, List<String> resourceNames)
specifier|private
name|void
name|doTest
parameter_list|(
name|String
name|xmlName
parameter_list|,
name|int
name|expectedSize
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|resourceNames
parameter_list|)
throws|throws
name|Exception
block|{
try|try
init|(
name|InputStream
name|is
init|=
name|GvkParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|xmlName
argument_list|)
init|)
block|{
name|GvkParser
name|parser
init|=
operator|new
name|GvkParser
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|parser
operator|.
name|parseEntries
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|entries
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expectedSize
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
name|String
name|resourceName
range|:
name|resourceNames
control|)
block|{
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|GvkParserTest
operator|.
name|class
argument_list|,
name|resourceName
argument_list|,
name|entries
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|emptyResult ()
specifier|public
name|void
name|emptyResult
parameter_list|()
throws|throws
name|Exception
block|{
name|doTest
argument_list|(
literal|"gvk_empty_result_because_of_bad_query.xml"
argument_list|,
literal|0
argument_list|,
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|resultFor797485368 ()
specifier|public
name|void
name|resultFor797485368
parameter_list|()
throws|throws
name|Exception
block|{
name|doTest
argument_list|(
literal|"gvk_result_for_797485368.xml"
argument_list|,
literal|1
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"gvk_result_for_797485368.bib"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGMP ()
specifier|public
name|void
name|testGMP
parameter_list|()
throws|throws
name|Exception
block|{
name|doTest
argument_list|(
literal|"gvk_gmp.xml"
argument_list|,
literal|2
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
literal|"gvk_gmp.1.bib"
argument_list|,
literal|"gvk_gmp.2.bib"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|subTitleTest ()
specifier|public
name|void
name|subTitleTest
parameter_list|()
throws|throws
name|Exception
block|{
try|try
init|(
name|InputStream
name|is
init|=
name|GvkParserTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"gvk_artificial_subtitle_test.xml"
argument_list|)
init|)
block|{
name|GvkParser
name|parser
init|=
operator|new
name|GvkParser
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|parser
operator|.
name|parseEntries
argument_list|(
name|is
argument_list|)
decl_stmt|;
name|assertNotNull
argument_list|(
name|entries
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|5
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"subtitle"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"C"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"subtitle"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Word"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"subtitle"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Word1 word2"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"subtitle"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Word1 word2"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"subtitle"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

