begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref.export.layout.format
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestSuite
import|;
end_import

begin_class
DECL|class|AllTests
specifier|public
class|class
name|AllTests
block|{
DECL|method|suite ()
specifier|public
specifier|static
name|Test
name|suite
parameter_list|()
block|{
name|TestSuite
name|suite
init|=
operator|new
name|TestSuite
argument_list|(
literal|"Test for test.net.sf.jabref"
argument_list|)
decl_stmt|;
comment|//$JUnit-BEGIN$
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorFirstAbbrLastOxfordCommasTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorFirstLastCommasTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorLastFirstAbbreviatorTester
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorLastFirstTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorAndsCommaReplacerTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorFirstFirstTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorFirstLastOxfordCommasTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|BibtexNameLayoutFormatterTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorOrgSciTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorAbbreviatorTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|CompositeFormatTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|ResolvePDFTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorAndsReplacerTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorLastFirstAbbrCommasTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|HTMLParagraphsTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorLastFirstOxfordCommasTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorFirstAbbrLastCommasTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorLastFirstCommasTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|AuthorLastFirstAbbrOxfordCommasTest
operator|.
name|class
argument_list|)
expr_stmt|;
name|suite
operator|.
name|addTestSuite
argument_list|(
name|NoSpaceBetweenAbbreviationsTest
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//$JUnit-END$
return|return
name|suite
return|;
block|}
block|}
end_class

end_unit

