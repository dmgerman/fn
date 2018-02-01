begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util.io
package|package
name|org
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
name|nio
operator|.
name|file
operator|.
name|Files
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
name|Path
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
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibtexEntryTypes
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
name|Rule
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
import|import
name|org
operator|.
name|junit
operator|.
name|rules
operator|.
name|TemporaryFolder
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|junit
operator|.
name|MockitoJUnitRunner
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
annotation|@
name|RunWith
argument_list|(
name|MockitoJUnitRunner
operator|.
name|class
argument_list|)
DECL|class|CiteKeyBasedFileFinderTest
specifier|public
class|class
name|CiteKeyBasedFileFinderTest
block|{
annotation|@
name|Rule
DECL|field|temporaryFolder
specifier|public
name|TemporaryFolder
name|temporaryFolder
init|=
operator|new
name|TemporaryFolder
argument_list|()
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
DECL|field|rootDir
specifier|private
name|Path
name|rootDir
decl_stmt|;
DECL|field|graphicsDir
specifier|private
name|Path
name|graphicsDir
decl_stmt|;
DECL|field|pdfsDir
specifier|private
name|Path
name|pdfsDir
decl_stmt|;
DECL|field|jpgFile
specifier|private
name|Path
name|jpgFile
decl_stmt|;
DECL|field|pdfFile
specifier|private
name|Path
name|pdfFile
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|IOException
block|{
name|entry
operator|=
operator|new
name|BibEntry
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"HipKro03"
argument_list|)
expr_stmt|;
name|rootDir
operator|=
name|temporaryFolder
operator|.
name|getRoot
argument_list|()
operator|.
name|toPath
argument_list|()
expr_stmt|;
name|Path
name|subDir
init|=
name|Files
operator|.
name|createDirectory
argument_list|(
name|rootDir
operator|.
name|resolve
argument_list|(
literal|"Organization Science"
argument_list|)
argument_list|)
decl_stmt|;
name|pdfsDir
operator|=
name|Files
operator|.
name|createDirectory
argument_list|(
name|rootDir
operator|.
name|resolve
argument_list|(
literal|"pdfs"
argument_list|)
argument_list|)
expr_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|subDir
operator|.
name|resolve
argument_list|(
literal|"HipKro03 - Hello.pdf"
argument_list|)
argument_list|)
expr_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|rootDir
operator|.
name|resolve
argument_list|(
literal|"HipKro03 - Hello.pdf"
argument_list|)
argument_list|)
expr_stmt|;
name|Path
name|pdfSubSubDir
init|=
name|Files
operator|.
name|createDirectory
argument_list|(
name|pdfsDir
operator|.
name|resolve
argument_list|(
literal|"sub"
argument_list|)
argument_list|)
decl_stmt|;
name|pdfFile
operator|=
name|Files
operator|.
name|createFile
argument_list|(
name|pdfSubSubDir
operator|.
name|resolve
argument_list|(
literal|"HipKro03-sub.pdf"
argument_list|)
argument_list|)
expr_stmt|;
name|Files
operator|.
name|createDirectory
argument_list|(
name|rootDir
operator|.
name|resolve
argument_list|(
literal|"2002"
argument_list|)
argument_list|)
expr_stmt|;
name|Path
name|dir2003
init|=
name|Files
operator|.
name|createDirectory
argument_list|(
name|rootDir
operator|.
name|resolve
argument_list|(
literal|"2003"
argument_list|)
argument_list|)
decl_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|dir2003
operator|.
name|resolve
argument_list|(
literal|"Paper by HipKro03.pdf"
argument_list|)
argument_list|)
expr_stmt|;
name|Path
name|dirTest
init|=
name|Files
operator|.
name|createDirectory
argument_list|(
name|rootDir
operator|.
name|resolve
argument_list|(
literal|"test"
argument_list|)
argument_list|)
decl_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|dirTest
operator|.
name|resolve
argument_list|(
literal|".TEST"
argument_list|)
argument_list|)
expr_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|dirTest
operator|.
name|resolve
argument_list|(
literal|"TEST["
argument_list|)
argument_list|)
expr_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|dirTest
operator|.
name|resolve
argument_list|(
literal|"TE.ST"
argument_list|)
argument_list|)
expr_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|dirTest
operator|.
name|resolve
argument_list|(
literal|"foo.dat"
argument_list|)
argument_list|)
expr_stmt|;
name|graphicsDir
operator|=
name|Files
operator|.
name|createDirectory
argument_list|(
name|rootDir
operator|.
name|resolve
argument_list|(
literal|"graphicsDir"
argument_list|)
argument_list|)
expr_stmt|;
name|Path
name|graphicsSubDir
init|=
name|Files
operator|.
name|createDirectories
argument_list|(
name|graphicsDir
operator|.
name|resolve
argument_list|(
literal|"subDir"
argument_list|)
argument_list|)
decl_stmt|;
name|jpgFile
operator|=
name|Files
operator|.
name|createFile
argument_list|(
name|graphicsSubDir
operator|.
name|resolve
argument_list|(
literal|"HipKro03 test.jpg"
argument_list|)
argument_list|)
expr_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|graphicsSubDir
operator|.
name|resolve
argument_list|(
literal|"HipKro03 test.png"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findAssociatedFilesInSubDirectories ()
specifier|public
name|void
name|findAssociatedFilesInSubDirectories
parameter_list|()
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|String
argument_list|>
name|extensions
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"jpg"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|dirs
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|graphicsDir
argument_list|,
name|pdfsDir
argument_list|)
decl_stmt|;
name|FileFinder
name|fileFinder
init|=
operator|new
name|CiteKeyBasedFileFinder
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|results
init|=
name|fileFinder
operator|.
name|findAssociatedFiles
argument_list|(
name|entry
argument_list|,
name|dirs
argument_list|,
name|extensions
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|jpgFile
argument_list|,
name|pdfFile
argument_list|)
argument_list|,
name|results
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findAssociatedFilesIgnoresFilesStartingWithKeyButContinueWithText ()
specifier|public
name|void
name|findAssociatedFilesIgnoresFilesStartingWithKeyButContinueWithText
parameter_list|()
throws|throws
name|Exception
block|{
name|Files
operator|.
name|createFile
argument_list|(
name|pdfsDir
operator|.
name|resolve
argument_list|(
literal|"HipKro03a - Hello second paper.pdf"
argument_list|)
argument_list|)
expr_stmt|;
name|FileFinder
name|fileFinder
init|=
operator|new
name|CiteKeyBasedFileFinder
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|results
init|=
name|fileFinder
operator|.
name|findAssociatedFiles
argument_list|(
name|entry
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|pdfsDir
argument_list|)
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"pdf"
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|pdfFile
argument_list|)
argument_list|,
name|results
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findAssociatedFilesFindsFilesStartingWithKey ()
specifier|public
name|void
name|findAssociatedFilesFindsFilesStartingWithKey
parameter_list|()
throws|throws
name|Exception
block|{
name|Path
name|secondPdfFile
init|=
name|Files
operator|.
name|createFile
argument_list|(
name|pdfsDir
operator|.
name|resolve
argument_list|(
literal|"HipKro03_Hello second paper.pdf"
argument_list|)
argument_list|)
decl_stmt|;
name|FileFinder
name|fileFinder
init|=
operator|new
name|CiteKeyBasedFileFinder
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|results
init|=
name|fileFinder
operator|.
name|findAssociatedFiles
argument_list|(
name|entry
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|pdfsDir
argument_list|)
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"pdf"
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|secondPdfFile
argument_list|,
name|pdfFile
argument_list|)
argument_list|,
name|results
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|findAssociatedFilesInNonExistingDirectoryFindsNothing ()
specifier|public
name|void
name|findAssociatedFilesInNonExistingDirectoryFindsNothing
parameter_list|()
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|String
argument_list|>
name|extensions
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"jpg"
argument_list|,
literal|"pdf"
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|dirs
init|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|rootDir
operator|.
name|resolve
argument_list|(
literal|"asdfasdf/asdfasdf"
argument_list|)
argument_list|)
decl_stmt|;
name|CiteKeyBasedFileFinder
name|fileFinder
init|=
operator|new
name|CiteKeyBasedFileFinder
argument_list|(
literal|false
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|results
init|=
name|fileFinder
operator|.
name|findAssociatedFiles
argument_list|(
name|entry
argument_list|,
name|dirs
argument_list|,
name|extensions
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|results
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

