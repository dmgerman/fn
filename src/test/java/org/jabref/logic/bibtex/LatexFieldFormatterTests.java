begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bibtex
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
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
name|OS
import|;
end_import

begin_import
import|import
name|org
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
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getLatexFieldFormatterPreferences
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|normalizeNewlineInAbstractField ()
specifier|public
name|void
name|normalizeNewlineInAbstractField
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
literal|"lorem"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" ipsum lorem ipsum\nlorem ipsum \rlorem ipsum\r\ntest"
decl_stmt|;
comment|// The newlines are normalized according to the globally configured newline setting in the formatter
name|String
name|expected
init|=
literal|"{"
operator|+
literal|"lorem"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|" ipsum lorem ipsum"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"lorem ipsum "
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"lorem ipsum"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"test"
operator|+
literal|"}"
decl_stmt|;
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
comment|// The newlines are normalized according to the globally configured newline setting in the formatter
name|String
name|text
init|=
literal|"lorem ipsum lorem ipsum"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"lorem ipsum lorem ipsum"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
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
DECL|method|preserveMultipleNewlinesInAbstractField ()
specifier|public
name|void
name|preserveMultipleNewlinesInAbstractField
parameter_list|()
block|{
name|String
name|fieldName
init|=
literal|"abstract"
decl_stmt|;
comment|// The newlines are normalized according to the globally configured newline setting in the formatter
name|String
name|text
init|=
literal|"lorem ipsum lorem ipsum"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"lorem ipsum lorem ipsum"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
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
comment|// The newlines are normalized according to the globally configured newline setting in the formatter
name|String
name|text
init|=
literal|"lorem ipsum lorem ipsum"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"lorem ipsum lorem ipsum"
operator|+
name|OS
operator|.
name|NEWLINE
decl_stmt|;
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

