begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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
DECL|class|FileFieldTest
specifier|public
class|class
name|FileFieldTest
block|{
annotation|@
name|Test
DECL|method|emptyListForEmptyInput ()
specifier|public
name|void
name|emptyListForEmptyInput
parameter_list|()
block|{
name|String
name|emptyInput
init|=
literal|""
decl_stmt|;
name|String
name|nullInput
init|=
literal|null
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|emptyInput
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|nullInput
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseCorrectInput ()
specifier|public
name|void
name|parseCorrectInput
parameter_list|()
block|{
name|String
name|input
init|=
literal|"Desc:File.PDF:PDF"
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|"Desc"
argument_list|,
literal|"File.PDF"
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ingoreMissingDescription ()
specifier|public
name|void
name|ingoreMissingDescription
parameter_list|()
block|{
name|String
name|input
init|=
literal|":wei2005ahp.pdf:PDF"
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"wei2005ahp.pdf"
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|interpreteLinkAsOnlyMandatoryField ()
specifier|public
name|void
name|interpreteLinkAsOnlyMandatoryField
parameter_list|()
block|{
name|String
name|single
init|=
literal|"wei2005ahp.pdf"
decl_stmt|;
name|String
name|multiple
init|=
literal|"wei2005ahp.pdf;other.pdf"
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"wei2005ahp.pdf"
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|single
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"wei2005ahp.pdf"
argument_list|,
literal|""
argument_list|)
argument_list|,
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"other.pdf"
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|multiple
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|escapedCharactersInDescription ()
specifier|public
name|void
name|escapedCharactersInDescription
parameter_list|()
block|{
name|String
name|input
init|=
literal|"test\\:\\;:wei2005ahp.pdf:PDF"
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|"test:;"
argument_list|,
literal|"wei2005ahp.pdf"
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|handleXmlCharacters ()
specifier|public
name|void
name|handleXmlCharacters
parameter_list|()
block|{
name|String
name|input
init|=
literal|"test&#44\\;st\\:\\;:wei2005ahp.pdf:PDF"
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|"test&#44;st:;"
argument_list|,
literal|"wei2005ahp.pdf"
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|handleEscapedFilePath ()
specifier|public
name|void
name|handleEscapedFilePath
parameter_list|()
block|{
name|String
name|input
init|=
literal|"desc:C\\:\\\\test.pdf:PDF"
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|"desc"
argument_list|,
literal|"C:\\test.pdf"
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|subsetOfFieldsResultsInFileLink ()
specifier|public
name|void
name|subsetOfFieldsResultsInFileLink
parameter_list|()
block|{
name|String
name|descOnly
init|=
literal|"file.pdf::"
decl_stmt|;
name|String
name|fileOnly
init|=
literal|":file.pdf"
decl_stmt|;
name|String
name|typeOnly
init|=
literal|"::file.pdf"
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"file.pdf"
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|descOnly
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"file.pdf"
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|fileOnly
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|""
argument_list|,
literal|"file.pdf"
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|typeOnly
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|tooManySeparators ()
specifier|public
name|void
name|tooManySeparators
parameter_list|()
block|{
name|String
name|input
init|=
literal|"desc:file.pdf:PDF:asdf"
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|ParsedFileField
argument_list|(
literal|"desc"
argument_list|,
literal|"file.pdf"
argument_list|,
literal|"PDF"
argument_list|)
argument_list|)
argument_list|,
name|FileField
operator|.
name|parse
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteStandard ()
specifier|public
name|void
name|testQuoteStandard
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"a"
argument_list|,
name|FileField
operator|.
name|quote
argument_list|(
literal|"a"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteAllCharacters ()
specifier|public
name|void
name|testQuoteAllCharacters
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"a\\:\\;\\\\"
argument_list|,
name|FileField
operator|.
name|quote
argument_list|(
literal|"a:;\\"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteEmpty ()
specifier|public
name|void
name|testQuoteEmpty
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|FileField
operator|.
name|quote
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testQuoteNull ()
specifier|public
name|void
name|testQuoteNull
parameter_list|()
block|{
name|assertNull
argument_list|(
name|FileField
operator|.
name|quote
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

