begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter.layout
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|Globals
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
name|JabRefPreferences
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
name|exporter
operator|.
name|LatexFieldFormatter
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
name|BeforeClass
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

begin_comment
comment|/**  * Created by joerg on 05.10.2015.  */
end_comment

begin_class
DECL|class|LatexFieldFormatterTests
specifier|public
class|class
name|LatexFieldFormatterTests
block|{
DECL|field|formatter
specifier|private
name|LatexFieldFormatter
name|formatter
decl_stmt|;
annotation|@
name|BeforeClass
DECL|method|setUpBeforeClass ()
specifier|public
specifier|static
name|void
name|setUpBeforeClass
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|this
operator|.
name|formatter
operator|=
operator|new
name|LatexFieldFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|preserveNewlineInAbstractField ()
specifier|public
name|void
name|preserveNewlineInAbstractField
parameter_list|()
block|{
name|String
name|fieldName
init|=
literal|"abstract"
decl_stmt|;
name|String
name|text
init|=
literal|"lorem ipsum lorem ipsum\nlorem ipsum lorem ipsum\n"
decl_stmt|;
comment|// The newlines are normalized according to the globally configured newline setting in the formatter
comment|// Therefore, "\n" has to be replaced by that
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|String
name|result
init|=
name|formatter
operator|.
name|format
argument_list|(
name|text
argument_list|,
name|fieldName
argument_list|)
decl_stmt|;
name|String
name|expected
init|=
literal|"{"
operator|+
name|text
operator|+
literal|"}"
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|preserveNewlineInReviewField ()
specifier|public
name|void
name|preserveNewlineInReviewField
parameter_list|()
block|{
name|String
name|fieldName
init|=
literal|"review"
decl_stmt|;
name|String
name|text
init|=
literal|"lorem ipsum lorem ipsum\nlorem ipsum lorem ipsum\n"
decl_stmt|;
comment|// The newlines are normalized according to the globally configured newline setting in the formatter
comment|// Therefore, "\n" has to be replaced by that
name|text
operator|=
name|text
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
name|Globals
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
name|String
name|result
init|=
name|formatter
operator|.
name|format
argument_list|(
name|text
argument_list|,
name|fieldName
argument_list|)
decl_stmt|;
name|String
name|expected
init|=
literal|"{"
operator|+
name|text
operator|+
literal|"}"
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removeWhitespaceFromNonMultiLineFields ()
specifier|public
name|void
name|removeWhitespaceFromNonMultiLineFields
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|original
init|=
literal|"I\nshould\nnot\ninclude\nadditional\nwhitespaces  \nor\n\ttabs."
decl_stmt|;
name|String
name|expected
init|=
literal|"{I should not include additional whitespaces or tabs.}"
decl_stmt|;
name|String
name|title
init|=
name|formatter
operator|.
name|format
argument_list|(
name|original
argument_list|,
literal|"title"
argument_list|)
decl_stmt|;
name|String
name|any
init|=
name|formatter
operator|.
name|format
argument_list|(
name|original
argument_list|,
literal|"anyotherfield"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|title
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|any
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

