begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.util
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
package|;
end_package

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
DECL|class|FileHelperTest
class|class
name|FileHelperTest
block|{
annotation|@
name|Test
DECL|method|extractFileExtension ()
specifier|public
name|void
name|extractFileExtension
parameter_list|()
block|{
specifier|final
name|String
name|filePath
init|=
name|FileHelperTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"pdffile.pdf"
argument_list|)
operator|.
name|getPath
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"pdf"
argument_list|)
argument_list|,
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|filePath
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|fileExtensionFromUrl ()
specifier|public
name|void
name|fileExtensionFromUrl
parameter_list|()
block|{
specifier|final
name|String
name|filePath
init|=
literal|"https://link.springer.com/content/pdf/10.1007%2Fs40955-018-0121-9.pdf"
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"pdf"
argument_list|)
argument_list|,
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|filePath
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

