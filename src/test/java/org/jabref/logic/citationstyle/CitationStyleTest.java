begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.citationstyle
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|citationstyle
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|TestEntry
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
name|Disabled
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
annotation|@
name|Disabled
argument_list|(
literal|"For some reason, instead of vol and pp we get null. No idea about the origin of this problem."
argument_list|)
DECL|class|CitationStyleTest
class|class
name|CitationStyleTest
block|{
annotation|@
name|Test
DECL|method|getDefault ()
name|void
name|getDefault
parameter_list|()
throws|throws
name|Exception
block|{
name|assertNotNull
argument_list|(
name|CitationStyle
operator|.
name|getDefault
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDefaultCitation ()
name|void
name|testDefaultCitation
parameter_list|()
block|{
name|String
name|citation
init|=
name|CitationStyleGenerator
operator|.
name|generateCitation
argument_list|(
name|TestEntry
operator|.
name|getTestEntry
argument_list|()
argument_list|,
name|CitationStyle
operator|.
name|getDefault
argument_list|()
argument_list|)
decl_stmt|;
comment|// if the default citation style changes this has to be modified
name|String
name|expected
init|=
literal|"<div class=\"csl-entry\">\n"
operator|+
literal|"<div class=\"csl-left-margin\">[1]</div><div class=\"csl-right-inline\">"
operator|+
literal|"B. Smith, B. Jones, and J. Williams, âTitle of the test entry,â "
operator|+
literal|"<i>BibTeX Journal</i>, vol. 34, no. 3, pp. 45â67, Jul. 2016.</div>\n"
operator|+
literal|"</div>\n"
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|citation
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

