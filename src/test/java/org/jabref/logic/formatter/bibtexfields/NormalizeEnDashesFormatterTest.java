begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
package|;
end_package

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

begin_comment
comment|/**  * Tests in addition to the general tests from {@link org.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|NormalizeEnDashesFormatterTest
specifier|public
class|class
name|NormalizeEnDashesFormatterTest
block|{
DECL|field|formatter
specifier|private
name|NormalizeEnDashesFormatter
name|formatter
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|NormalizeEnDashesFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatExample ()
specifier|public
name|void
name|formatExample
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Winery -- A Modeling Tool for TOSCA-based Cloud Applications"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|formatter
operator|.
name|getExampleInput
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatExampleOfChangelog ()
specifier|public
name|void
name|formatExampleOfChangelog
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Example -- illustrative"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Example - illustrative"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|dashesWithinWordsAreKept ()
specifier|public
name|void
name|dashesWithinWordsAreKept
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Example-illustrative"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Example-illustrative"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|dashesPreceededByASpaceAreKept ()
specifier|public
name|void
name|dashesPreceededByASpaceAreKept
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Example -illustrative"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Example -illustrative"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|dashesFollowedByASpaceAreKept ()
specifier|public
name|void
name|dashesFollowedByASpaceAreKept
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Example- illustrative"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Example- illustrative"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|dashAtTheBeginningIsKept ()
specifier|public
name|void
name|dashAtTheBeginningIsKept
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"- illustrative"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"- illustrative"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|dashAtTheEndIsKept ()
specifier|public
name|void
name|dashAtTheEndIsKept
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Example-"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Example-"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

