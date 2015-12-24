begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util.io
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|List
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
DECL|class|FileUtilTest
specifier|public
class|class
name|FileUtilTest
block|{
annotation|@
name|Test
DECL|method|testGetFileExtensionSimpleFile ()
specifier|public
name|void
name|testGetFileExtensionSimpleFile
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
operator|new
name|File
argument_list|(
literal|"test.pdf"
argument_list|)
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionLowerCaseAndTrimmingFile ()
specifier|public
name|void
name|testGetFileExtensionLowerCaseAndTrimmingFile
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
operator|new
name|File
argument_list|(
literal|"test.PdF  "
argument_list|)
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionMultipleDotsFile ()
specifier|public
name|void
name|testGetFileExtensionMultipleDotsFile
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
operator|new
name|File
argument_list|(
literal|"te.st.PdF  "
argument_list|)
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionNoExtensionFile ()
specifier|public
name|void
name|testGetFileExtensionNoExtensionFile
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
operator|new
name|File
argument_list|(
literal|"JustTextNotASingleDot"
argument_list|)
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionNoExtension2File ()
specifier|public
name|void
name|testGetFileExtensionNoExtension2File
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
operator|new
name|File
argument_list|(
literal|".StartsWithADotIsNotAnExtension"
argument_list|)
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionSimpleString ()
specifier|public
name|void
name|testGetFileExtensionSimpleString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
literal|"test.pdf"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionLowerCaseAndTrimmingString ()
specifier|public
name|void
name|testGetFileExtensionLowerCaseAndTrimmingString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
literal|"test.PdF  "
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionMultipleDotsString ()
specifier|public
name|void
name|testGetFileExtensionMultipleDotsString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
literal|"te.st.PdF  "
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionNoExtensionString ()
specifier|public
name|void
name|testGetFileExtensionNoExtensionString
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
literal|"JustTextNotASingleDot"
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFileExtensionNoExtension2String ()
specifier|public
name|void
name|testGetFileExtensionNoExtension2String
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
literal|".StartsWithADotIsNotAnExtension"
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|uniquePathSubstrings ()
specifier|public
name|void
name|uniquePathSubstrings
parameter_list|()
block|{
name|String
index|[]
name|pathArr
init|=
block|{
name|Paths
operator|.
name|get
argument_list|(
literal|"C:/uniquefile.bib"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
name|Paths
operator|.
name|get
argument_list|(
literal|"C:/downloads/filename.bib"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
name|Paths
operator|.
name|get
argument_list|(
literal|"C:/mypaper/bib/filename.bib"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
name|Paths
operator|.
name|get
argument_list|(
literal|"C:/external/mypaper/bib/filename.bib"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
literal|""
block|}
decl_stmt|;
name|String
index|[]
name|uniqArr
init|=
block|{
name|Paths
operator|.
name|get
argument_list|(
literal|"uniquefile.bib"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
name|Paths
operator|.
name|get
argument_list|(
literal|"downloads/filename.bib"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
name|Paths
operator|.
name|get
argument_list|(
literal|"C:/mypaper/bib/filename.bib"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
name|Paths
operator|.
name|get
argument_list|(
literal|"external/mypaper/bib/filename.bib"
argument_list|)
operator|.
name|toString
argument_list|()
block|,
literal|""
block|}
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|paths
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|pathArr
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|uniqPath
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|uniqArr
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|result
init|=
name|FileUtil
operator|.
name|uniquePathSubstrings
argument_list|(
name|paths
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|uniqPath
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

