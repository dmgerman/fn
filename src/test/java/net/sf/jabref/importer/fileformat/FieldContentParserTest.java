begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
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
name|*
import|;
end_import

begin_class
DECL|class|FieldContentParserTest
specifier|public
class|class
name|FieldContentParserTest
block|{
DECL|field|parser
specifier|private
name|FieldContentParser
name|parser
decl_stmt|;
annotation|@
name|BeforeClass
DECL|method|loadPreferences ()
specifier|public
specifier|static
name|void
name|loadPreferences
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
throws|throws
name|Exception
block|{
name|parser
operator|=
operator|new
name|FieldContentParser
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unifiesLineBreaks ()
specifier|public
name|void
name|unifiesLineBreaks
parameter_list|()
block|{
name|String
name|original
init|=
literal|"I\r\nunify\nline\rbreaks."
decl_stmt|;
name|String
name|expected
init|=
literal|"I\nunify\nline\nbreaks."
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
name|Globals
operator|.
name|NEWLINE
argument_list|)
decl_stmt|;
name|String
name|processed
init|=
name|parser
operator|.
name|format
argument_list|(
operator|new
name|StringBuilder
argument_list|(
name|original
argument_list|)
argument_list|,
literal|"abstract"
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|processed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|retainsWhitespaceForMultiLineFields ()
specifier|public
name|void
name|retainsWhitespaceForMultiLineFields
parameter_list|()
block|{
name|String
name|original
init|=
literal|"I\nkeep\nline\nbreaks\nand\n\ttabs."
decl_stmt|;
name|String
name|formatted
init|=
name|original
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
name|Globals
operator|.
name|NEWLINE
argument_list|)
decl_stmt|;
name|String
name|abstrakt
init|=
name|parser
operator|.
name|format
argument_list|(
operator|new
name|StringBuilder
argument_list|(
name|original
argument_list|)
argument_list|,
literal|"abstract"
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|review
init|=
name|parser
operator|.
name|format
argument_list|(
operator|new
name|StringBuilder
argument_list|(
name|original
argument_list|)
argument_list|,
literal|"review"
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|formatted
argument_list|,
name|abstrakt
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|formatted
argument_list|,
name|review
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
block|{
name|String
name|original
init|=
literal|"I\nshould\nnot\ninclude\nadditional\nwhitespaces  \nor\n\ttabs."
decl_stmt|;
name|String
name|expected
init|=
literal|"I should not include additional whitespaces or tabs."
decl_stmt|;
name|String
name|abstrakt
init|=
name|parser
operator|.
name|format
argument_list|(
operator|new
name|StringBuilder
argument_list|(
name|original
argument_list|)
argument_list|,
literal|"title"
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|String
name|any
init|=
name|parser
operator|.
name|format
argument_list|(
operator|new
name|StringBuilder
argument_list|(
name|original
argument_list|)
argument_list|,
literal|"anyotherfield"
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|abstrakt
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

