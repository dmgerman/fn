begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.io
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|io
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
name|org
operator|.
name|junit
operator|.
name|Ignore
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
DECL|class|MimeTypeDetectorTest
specifier|public
class|class
name|MimeTypeDetectorTest
block|{
annotation|@
name|Test
DECL|method|beFalseForInvalidUrl ()
specifier|public
name|void
name|beFalseForInvalidUrl
parameter_list|()
block|{
name|String
name|invalidUrl
init|=
literal|"thisisnourl"
decl_stmt|;
name|assertFalse
argument_list|(
name|MimeTypeDetector
operator|.
name|isPdfContentType
argument_list|(
name|invalidUrl
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|beFalseForUnreachableUrl ()
specifier|public
name|void
name|beFalseForUnreachableUrl
parameter_list|()
block|{
name|String
name|invalidUrl
init|=
literal|"http://idontknowthisurlforsure.de"
decl_stmt|;
name|assertFalse
argument_list|(
name|MimeTypeDetector
operator|.
name|isPdfContentType
argument_list|(
name|invalidUrl
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|beTrueForPdfMimeType ()
specifier|public
name|void
name|beTrueForPdfMimeType
parameter_list|()
block|{
name|String
name|pdfUrl
init|=
literal|"http://docs.oasis-open.org/wsbpel/2.0/OS/wsbpel-v2.0-OS.pdf"
decl_stmt|;
name|assertTrue
argument_list|(
name|MimeTypeDetector
operator|.
name|isPdfContentType
argument_list|(
name|pdfUrl
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Ignore
annotation|@
name|Test
DECL|method|acceptPDFMimeTypeVariations ()
specifier|public
name|void
name|acceptPDFMimeTypeVariations
parameter_list|()
block|{
comment|// application/pdf;charset=ISO-8859-1
name|String
name|pdfUrl
init|=
literal|"http://drum.lib.umd.edu/bitstream/1903/19/2/CS-TR-3368.pdf"
decl_stmt|;
name|assertTrue
argument_list|(
name|MimeTypeDetector
operator|.
name|isPdfContentType
argument_list|(
name|pdfUrl
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Ignore
annotation|@
name|Test
DECL|method|useGetRequestIfHeadRequestHasNoContentType ()
specifier|public
name|void
name|useGetRequestIfHeadRequestHasNoContentType
parameter_list|()
block|{
name|String
name|pdfUrl
init|=
literal|"http://iopscience.iop.org/article/10.1088/1749-4699/8/1/014010/pdf"
decl_stmt|;
name|assertEquals
argument_list|(
literal|"application/pdf"
argument_list|,
name|MimeTypeDetector
operator|.
name|getMimeType
argument_list|(
name|pdfUrl
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

