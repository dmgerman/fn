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
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|nio
operator|.
name|file
operator|.
name|StandardOpenOption
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatterPreferences
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
name|util
operator|.
name|FileHelper
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
name|mockito
operator|.
name|Answers
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertFalse
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
name|assertNotEquals
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
name|assertTrue
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_class
DECL|class|FileUtilTest
specifier|public
class|class
name|FileUtilTest
block|{
DECL|field|nonExistingTestPath
specifier|private
specifier|final
name|Path
name|nonExistingTestPath
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"nonExistingTestPath"
argument_list|)
decl_stmt|;
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
DECL|field|otherTemporaryFolder
specifier|public
name|TemporaryFolder
name|otherTemporaryFolder
init|=
operator|new
name|TemporaryFolder
argument_list|()
decl_stmt|;
DECL|field|existingTestFile
specifier|private
name|Path
name|existingTestFile
decl_stmt|;
DECL|field|otherExistingTestFile
specifier|private
name|Path
name|otherExistingTestFile
decl_stmt|;
DECL|field|layoutFormatterPreferences
specifier|private
name|LayoutFormatterPreferences
name|layoutFormatterPreferences
decl_stmt|;
annotation|@
name|Before
DECL|method|setUpViewModel ()
specifier|public
name|void
name|setUpViewModel
parameter_list|()
throws|throws
name|IOException
block|{
name|existingTestFile
operator|=
name|createTemporaryTestFile
argument_list|(
literal|"existingTestFile.txt"
argument_list|)
expr_stmt|;
name|otherExistingTestFile
operator|=
name|createTemporaryTestFile
argument_list|(
literal|"otherExistingTestFile.txt"
argument_list|)
expr_stmt|;
name|otherTemporaryFolder
operator|.
name|create
argument_list|()
expr_stmt|;
name|layoutFormatterPreferences
operator|=
name|mock
argument_list|(
name|LayoutFormatterPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|extensionBakAddedCorrectly ()
specifier|public
name|void
name|extensionBakAddedCorrectly
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"demo.bib.bak"
argument_list|)
argument_list|,
name|FileUtil
operator|.
name|addExtension
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"demo.bib"
argument_list|)
argument_list|,
literal|".bak"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|extensionBakAddedCorrectlyToAFileContainedInTmpDirectory ()
specifier|public
name|void
name|extensionBakAddedCorrectlyToAFileContainedInTmpDirectory
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"tmp"
argument_list|,
literal|"demo.bib.bak"
argument_list|)
argument_list|,
name|FileUtil
operator|.
name|addExtension
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"tmp"
argument_list|,
literal|"demo.bib"
argument_list|)
argument_list|,
literal|".bak"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLinkedFileNameDefaultFullTitle ()
specifier|public
name|void
name|testGetLinkedFileNameDefaultFullTitle
parameter_list|()
block|{
comment|// bibkey - title
name|String
name|fileNamePattern
init|=
literal|"[bibtexkey] - [fulltitle]"
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"1234"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"mytitle"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1234 - mytitle"
argument_list|,
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLinkedFileNameDefaultWithLowercaseTitle ()
specifier|public
name|void
name|testGetLinkedFileNameDefaultWithLowercaseTitle
parameter_list|()
block|{
comment|// bibkey - title
name|String
name|fileNamePattern
init|=
literal|"[bibtexkey] - [title:lower]"
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"1234"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"mytitle"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1234 - mytitle"
argument_list|,
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLinkedFileNameBibTeXKey ()
specifier|public
name|void
name|testGetLinkedFileNameBibTeXKey
parameter_list|()
block|{
comment|// bibkey
name|String
name|fileNamePattern
init|=
literal|"[bibtexkey]"
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"1234"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"mytitle"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1234"
argument_list|,
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLinkedFileNameNoPattern ()
specifier|public
name|void
name|testGetLinkedFileNameNoPattern
parameter_list|()
block|{
name|String
name|fileNamePattern
init|=
literal|""
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"1234"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"mytitle"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1234"
argument_list|,
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDefaultFileNameNoPatternNoBibTeXKey ()
specifier|public
name|void
name|testGetDefaultFileNameNoPatternNoBibTeXKey
parameter_list|()
block|{
name|String
name|fileNamePattern
init|=
literal|""
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"mytitle"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"default"
argument_list|,
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLinkedFileNameGetKeyIfEmptyField ()
specifier|public
name|void
name|testGetLinkedFileNameGetKeyIfEmptyField
parameter_list|()
block|{
comment|// bibkey - title
name|String
name|fileNamePattern
init|=
literal|"[title]"
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"1234"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1234"
argument_list|,
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLinkedFileNameGetDefaultIfEmptyFieldNoKey ()
specifier|public
name|void
name|testGetLinkedFileNameGetDefaultIfEmptyFieldNoKey
parameter_list|()
block|{
comment|// bibkey - title
name|String
name|fileNamePattern
init|=
literal|"[title]"
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"default"
argument_list|,
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLinkedFileNameByYearAuthorFirstpage ()
specifier|public
name|void
name|testGetLinkedFileNameByYearAuthorFirstpage
parameter_list|()
block|{
comment|// bibkey - title
name|String
name|fileNamePattern
init|=
literal|"[year]_[auth]_[firstpage]"
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"O. Kitsune"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"1868"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"567-579"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"1868_Kitsune_567"
argument_list|,
name|FileUtil
operator|.
name|createFileNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|Paths
operator|.
name|get
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
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|Paths
operator|.
name|get
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
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|Paths
operator|.
name|get
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
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|Paths
operator|.
name|get
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
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
name|Paths
operator|.
name|get
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
DECL|method|getFileExtensionWithSimpleString ()
specifier|public
name|void
name|getFileExtensionWithSimpleString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileHelper
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
DECL|method|getFileExtensionTrimsAndReturnsInLowercase ()
specifier|public
name|void
name|getFileExtensionTrimsAndReturnsInLowercase
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileHelper
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
DECL|method|getFileExtensionWithMultipleDotsString ()
specifier|public
name|void
name|getFileExtensionWithMultipleDotsString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"pdf"
argument_list|,
name|FileHelper
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
DECL|method|getFileExtensionWithNoDotReturnsEmptyExtension ()
specifier|public
name|void
name|getFileExtensionWithNoDotReturnsEmptyExtension
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
literal|"JustTextNotASingleDot"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFileExtensionWithDotAtStartReturnsEmptyExtension ()
specifier|public
name|void
name|getFileExtensionWithDotAtStartReturnsEmptyExtension
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|FileHelper
operator|.
name|getFileExtension
argument_list|(
literal|".StartsWithADotIsNotAnExtension"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFileNameWithSimpleString ()
specifier|public
name|void
name|getFileNameWithSimpleString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test"
argument_list|,
name|FileUtil
operator|.
name|getBaseName
argument_list|(
literal|"test.pdf"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getFileNameWithMultipleDotsString ()
specifier|public
name|void
name|getFileNameWithMultipleDotsString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"te.st"
argument_list|,
name|FileUtil
operator|.
name|getBaseName
argument_list|(
literal|"te.st.PdF  "
argument_list|)
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
annotation|@
name|Test
DECL|method|testCopyFileFromEmptySourcePathToEmptyDestinationPathWithOverrideExistFile ()
specifier|public
name|void
name|testCopyFileFromEmptySourcePathToEmptyDestinationPathWithOverrideExistFile
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|nonExistingTestPath
argument_list|,
name|nonExistingTestPath
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileFromEmptySourcePathToEmptyDestinationPathWithoutOverrideExistFile ()
specifier|public
name|void
name|testCopyFileFromEmptySourcePathToEmptyDestinationPathWithoutOverrideExistFile
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|nonExistingTestPath
argument_list|,
name|nonExistingTestPath
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileFromEmptySourcePathToExistDestinationPathWithOverrideExistFile ()
specifier|public
name|void
name|testCopyFileFromEmptySourcePathToExistDestinationPathWithOverrideExistFile
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|nonExistingTestPath
argument_list|,
name|existingTestFile
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileFromEmptySourcePathToExistDestinationPathWithoutOverrideExistFile ()
specifier|public
name|void
name|testCopyFileFromEmptySourcePathToExistDestinationPathWithoutOverrideExistFile
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|nonExistingTestPath
argument_list|,
name|existingTestFile
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileFromExistSourcePathToExistDestinationPathWithOverrideExistFile ()
specifier|public
name|void
name|testCopyFileFromExistSourcePathToExistDestinationPathWithOverrideExistFile
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|existingTestFile
argument_list|,
name|existingTestFile
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileFromExistSourcePathToExistDestinationPathWithoutOverrideExistFile ()
specifier|public
name|void
name|testCopyFileFromExistSourcePathToExistDestinationPathWithoutOverrideExistFile
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|existingTestFile
argument_list|,
name|existingTestFile
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileFromExistSourcePathToOtherExistDestinationPathWithOverrideExistFile ()
specifier|public
name|void
name|testCopyFileFromExistSourcePathToOtherExistDestinationPathWithOverrideExistFile
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|existingTestFile
argument_list|,
name|otherExistingTestFile
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileFromExistSourcePathToOtherExistDestinationPathWithoutOverrideExistFile ()
specifier|public
name|void
name|testCopyFileFromExistSourcePathToOtherExistDestinationPathWithoutOverrideExistFile
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|existingTestFile
argument_list|,
name|otherExistingTestFile
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileSuccessfulWithOverrideExistFile ()
specifier|public
name|void
name|testCopyFileSuccessfulWithOverrideExistFile
parameter_list|()
throws|throws
name|IOException
block|{
name|Path
name|temp
init|=
name|otherTemporaryFolder
operator|.
name|newFile
argument_list|(
literal|"existingTestFile.txt"
argument_list|)
operator|.
name|toPath
argument_list|()
decl_stmt|;
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|existingTestFile
argument_list|,
name|temp
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Files
operator|.
name|readAllLines
argument_list|(
name|existingTestFile
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|,
name|Files
operator|.
name|readAllLines
argument_list|(
name|temp
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCopyFileSuccessfulWithoutOverrideExistFile ()
specifier|public
name|void
name|testCopyFileSuccessfulWithoutOverrideExistFile
parameter_list|()
throws|throws
name|IOException
block|{
name|Path
name|temp
init|=
name|otherTemporaryFolder
operator|.
name|newFile
argument_list|(
literal|"existingTestFile.txt"
argument_list|)
operator|.
name|toPath
argument_list|()
decl_stmt|;
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|existingTestFile
argument_list|,
name|temp
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|assertNotEquals
argument_list|(
name|Files
operator|.
name|readAllLines
argument_list|(
name|existingTestFile
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|,
name|Files
operator|.
name|readAllLines
argument_list|(
name|temp
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|testRenameFileWithFromFileIsNullAndToFileIsNull ()
specifier|public
name|void
name|testRenameFileWithFromFileIsNullAndToFileIsNull
parameter_list|()
block|{
name|FileUtil
operator|.
name|renameFile
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|testRenameFileWithFromFileExistAndToFileIsNull ()
specifier|public
name|void
name|testRenameFileWithFromFileExistAndToFileIsNull
parameter_list|()
block|{
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|existingTestFile
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|testRenameFileWithFromFileIsNullAndToFileExist ()
specifier|public
name|void
name|testRenameFileWithFromFileIsNullAndToFileExist
parameter_list|()
block|{
name|FileUtil
operator|.
name|renameFile
argument_list|(
literal|null
argument_list|,
name|existingTestFile
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRenameFileWithFromFileNotExistAndToFileNotExist ()
specifier|public
name|void
name|testRenameFileWithFromFileNotExistAndToFileNotExist
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|nonExistingTestPath
argument_list|,
name|nonExistingTestPath
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRenameFileWithFromFileNotExistAndToFileExist ()
specifier|public
name|void
name|testRenameFileWithFromFileNotExistAndToFileExist
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|nonExistingTestPath
argument_list|,
name|existingTestFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRenameFileWithFromFileExistAndToFileNotExist ()
specifier|public
name|void
name|testRenameFileWithFromFileExistAndToFileNotExist
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|existingTestFile
argument_list|,
name|nonExistingTestPath
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRenameFileWithFromFileExistAndToFileExist ()
specifier|public
name|void
name|testRenameFileWithFromFileExistAndToFileExist
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|existingTestFile
argument_list|,
name|existingTestFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRenameFileWithFromFileExistAndOtherToFileExist ()
specifier|public
name|void
name|testRenameFileWithFromFileExistAndOtherToFileExist
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|existingTestFile
argument_list|,
name|otherExistingTestFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRenameFileSuccessful ()
specifier|public
name|void
name|testRenameFileSuccessful
parameter_list|()
block|{
name|Path
name|temp
init|=
name|Paths
operator|.
name|get
argument_list|(
name|otherTemporaryFolder
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|temp
argument_list|)
expr_stmt|;
name|FileUtil
operator|.
name|renameFile
argument_list|(
name|existingTestFile
argument_list|,
name|temp
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|existingTestFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|validFilenameShouldNotAlterValidFilename ()
specifier|public
name|void
name|validFilenameShouldNotAlterValidFilename
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"somename.pdf"
argument_list|,
name|FileUtil
operator|.
name|getValidFileName
argument_list|(
literal|"somename.pdf"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|validFilenameWithoutExtension ()
specifier|public
name|void
name|validFilenameWithoutExtension
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"somename"
argument_list|,
name|FileUtil
operator|.
name|getValidFileName
argument_list|(
literal|"somename"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|validFilenameShouldBeMaximum255Chars ()
specifier|public
name|void
name|validFilenameShouldBeMaximum255Chars
parameter_list|()
block|{
name|String
name|longestValidFilename
init|=
name|Stream
operator|.
name|generate
argument_list|(
parameter_list|()
lambda|->
name|String
operator|.
name|valueOf
argument_list|(
literal|'1'
argument_list|)
argument_list|)
operator|.
name|limit
argument_list|(
name|FileUtil
operator|.
name|MAXIMUM_FILE_NAME_LENGTH
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|()
argument_list|)
operator|+
literal|".pdf"
decl_stmt|;
name|String
name|longerFilename
init|=
name|Stream
operator|.
name|generate
argument_list|(
parameter_list|()
lambda|->
name|String
operator|.
name|valueOf
argument_list|(
literal|'1'
argument_list|)
argument_list|)
operator|.
name|limit
argument_list|(
literal|260
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|()
argument_list|)
operator|+
literal|".pdf"
decl_stmt|;
name|assertEquals
argument_list|(
name|longestValidFilename
argument_list|,
name|FileUtil
operator|.
name|getValidFileName
argument_list|(
name|longerFilename
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|invalidFilenameWithoutExtension ()
specifier|public
name|void
name|invalidFilenameWithoutExtension
parameter_list|()
block|{
name|String
name|longestValidFilename
init|=
name|Stream
operator|.
name|generate
argument_list|(
parameter_list|()
lambda|->
name|String
operator|.
name|valueOf
argument_list|(
literal|'1'
argument_list|)
argument_list|)
operator|.
name|limit
argument_list|(
name|FileUtil
operator|.
name|MAXIMUM_FILE_NAME_LENGTH
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|()
argument_list|)
decl_stmt|;
name|String
name|longerFilename
init|=
name|Stream
operator|.
name|generate
argument_list|(
parameter_list|()
lambda|->
name|String
operator|.
name|valueOf
argument_list|(
literal|'1'
argument_list|)
argument_list|)
operator|.
name|limit
argument_list|(
literal|260
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|joining
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|longestValidFilename
argument_list|,
name|FileUtil
operator|.
name|getValidFileName
argument_list|(
name|longerFilename
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetLinkedDirNameDefaultFullTitle ()
specifier|public
name|void
name|testGetLinkedDirNameDefaultFullTitle
parameter_list|()
block|{
comment|// bibkey - title
name|String
name|fileNamePattern
init|=
literal|"PDF/[year]/[auth]/[bibtexkey] - [fulltitle]"
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"1234"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"mytitle"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"1998"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"A. ÃuthÃ¶r and Author, Bete"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"PDF/1998/ÃuthÃ¶r/1234 - mytitle"
argument_list|,
name|FileUtil
operator|.
name|createDirNameFromPattern
argument_list|(
literal|null
argument_list|,
name|entry
argument_list|,
name|fileNamePattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|createTemporaryTestFile (String name)
specifier|private
name|Path
name|createTemporaryTestFile
parameter_list|(
name|String
name|name
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|testFile
init|=
name|temporaryFolder
operator|.
name|newFile
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|Files
operator|.
name|write
argument_list|(
name|testFile
operator|.
name|toPath
argument_list|()
argument_list|,
name|name
operator|.
name|getBytes
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|,
name|StandardOpenOption
operator|.
name|APPEND
argument_list|)
expr_stmt|;
return|return
name|testFile
operator|.
name|toPath
argument_list|()
return|;
block|}
block|}
end_class

end_unit

