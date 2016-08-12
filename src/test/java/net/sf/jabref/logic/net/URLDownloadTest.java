begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.net
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|net
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|Assert
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

begin_class
DECL|class|URLDownloadTest
specifier|public
class|class
name|URLDownloadTest
block|{
annotation|@
name|Test
DECL|method|testStringDownloadWithSetEncoding ()
specifier|public
name|void
name|testStringDownloadWithSetEncoding
parameter_list|()
throws|throws
name|IOException
block|{
name|URLDownload
name|dl
init|=
operator|new
name|URLDownload
argument_list|(
operator|new
name|URL
argument_list|(
literal|"http://www.google.com"
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"google.com should contain google"
argument_list|,
name|dl
operator|.
name|downloadToString
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
operator|.
name|contains
argument_list|(
literal|"Google"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testStringDownload ()
specifier|public
name|void
name|testStringDownload
parameter_list|()
throws|throws
name|IOException
block|{
name|URLDownload
name|dl
init|=
operator|new
name|URLDownload
argument_list|(
operator|new
name|URL
argument_list|(
literal|"http://www.google.com"
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"google.com should contain google"
argument_list|,
name|dl
operator|.
name|downloadToString
argument_list|(
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
literal|"Google"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFileDownload ()
specifier|public
name|void
name|testFileDownload
parameter_list|()
throws|throws
name|IOException
block|{
name|File
name|destination
init|=
name|File
operator|.
name|createTempFile
argument_list|(
literal|"jabref-test"
argument_list|,
literal|".html"
argument_list|)
decl_stmt|;
try|try
block|{
name|URLDownload
name|dl
init|=
operator|new
name|URLDownload
argument_list|(
operator|new
name|URL
argument_list|(
literal|"http://www.google.com"
argument_list|)
argument_list|)
decl_stmt|;
name|dl
operator|.
name|downloadToFile
argument_list|(
name|destination
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
literal|"file must exist"
argument_list|,
name|destination
operator|.
name|exists
argument_list|()
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
comment|// cleanup
if|if
condition|(
operator|!
name|destination
operator|.
name|delete
argument_list|()
condition|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Cannot delete downloaded file"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|testDetermineMimeType ()
specifier|public
name|void
name|testDetermineMimeType
parameter_list|()
throws|throws
name|IOException
block|{
name|URLDownload
name|dl
init|=
operator|new
name|URLDownload
argument_list|(
operator|new
name|URL
argument_list|(
literal|"http://www.google.com"
argument_list|)
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|dl
operator|.
name|determineMimeType
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"text/html"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

