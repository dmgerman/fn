begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.externalfiles
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiles
package|;
end_package

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
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|util
operator|.
name|io
operator|.
name|AutoLinkPreferences
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
name|database
operator|.
name|BibDatabaseContext
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|LinkedFile
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
name|metadata
operator|.
name|FilePreferences
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
name|BeforeEach
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
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|extension
operator|.
name|ExtendWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junitpioneer
operator|.
name|jupiter
operator|.
name|TempDirectory
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

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|ArgumentMatchers
operator|.
name|any
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

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|when
import|;
end_import

begin_class
annotation|@
name|ExtendWith
argument_list|(
name|TempDirectory
operator|.
name|class
argument_list|)
DECL|class|AutoSetFileLinksUtilTest
specifier|public
class|class
name|AutoSetFileLinksUtilTest
block|{
DECL|field|fileDirPrefs
specifier|private
specifier|final
name|FilePreferences
name|fileDirPrefs
init|=
name|mock
argument_list|(
name|FilePreferences
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|autoLinkPrefs
specifier|private
specifier|final
name|AutoLinkPreferences
name|autoLinkPrefs
init|=
operator|new
name|AutoLinkPreferences
argument_list|(
literal|false
argument_list|,
literal|""
argument_list|,
literal|true
argument_list|,
literal|';'
argument_list|)
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
init|=
name|mock
argument_list|(
name|BibDatabaseContext
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|externalFileTypes
specifier|private
specifier|final
name|ExternalFileTypes
name|externalFileTypes
init|=
name|mock
argument_list|(
name|ExternalFileTypes
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp (@empDirectory.TempDir Path folder)
specifier|public
name|void
name|setUp
parameter_list|(
annotation|@
name|TempDirectory
operator|.
name|TempDir
name|Path
name|folder
parameter_list|)
throws|throws
name|Exception
block|{
name|Path
name|path
init|=
name|folder
operator|.
name|resolve
argument_list|(
literal|"CiteKey.pdf"
argument_list|)
decl_stmt|;
name|Files
operator|.
name|createFile
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"CiteKey"
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|databaseContext
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|any
argument_list|()
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|path
operator|.
name|getParent
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|externalFileTypes
operator|.
name|getExternalFileTypeSelection
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
operator|new
name|TreeSet
argument_list|<>
argument_list|(
name|ExternalFileTypes
operator|.
name|getDefaultExternalFileTypes
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|test ()
specifier|public
name|void
name|test
parameter_list|()
throws|throws
name|Exception
block|{
comment|//Due to mocking the externalFileType class, the file extension will not be found
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|expected
init|=
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|LinkedFile
argument_list|(
literal|""
argument_list|,
literal|"CiteKey.pdf"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|AutoSetFileLinksUtil
name|util
init|=
operator|new
name|AutoSetFileLinksUtil
argument_list|(
name|databaseContext
argument_list|,
name|fileDirPrefs
argument_list|,
name|autoLinkPrefs
argument_list|,
name|externalFileTypes
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|actual
init|=
name|util
operator|.
name|findAssociatedNotLinkedFiles
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|actual
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

