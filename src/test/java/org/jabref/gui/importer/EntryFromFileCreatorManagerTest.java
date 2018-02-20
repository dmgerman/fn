begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
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
name|FileInputStream
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
name|io
operator|.
name|InputStreamReader
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
name|util
operator|.
name|ArrayList
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
name|importer
operator|.
name|ImportDataTest
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
name|importer
operator|.
name|ImportFormatPreferences
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
name|importer
operator|.
name|ParserResult
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
name|importer
operator|.
name|fileformat
operator|.
name|BibtexParser
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
name|BibDatabase
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
name|DummyFileUpdateMonitor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|testutils
operator|.
name|category
operator|.
name|GUITest
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
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertNotNull
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
name|assertNull
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
name|GUITest
DECL|class|EntryFromFileCreatorManagerTest
specifier|public
class|class
name|EntryFromFileCreatorManagerTest
block|{
DECL|field|prefs
specifier|private
specifier|final
name|ImportFormatPreferences
name|prefs
init|=
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
decl_stmt|;
DECL|field|externalFileTypes
specifier|private
name|ExternalFileTypes
name|externalFileTypes
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|externalFileTypes
operator|=
name|mock
argument_list|(
name|ExternalFileTypes
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|externalFileTypes
operator|.
name|getExternalFileTypeByExt
argument_list|(
literal|"pdf"
argument_list|)
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCreator ()
specifier|public
name|void
name|testGetCreator
parameter_list|()
block|{
name|EntryFromFileCreatorManager
name|manager
init|=
operator|new
name|EntryFromFileCreatorManager
argument_list|(
name|externalFileTypes
argument_list|)
decl_stmt|;
name|EntryFromFileCreator
name|creator
init|=
name|manager
operator|.
name|getEntryCreator
argument_list|(
name|ImportDataTest
operator|.
name|NOT_EXISTING_PDF
argument_list|)
decl_stmt|;
name|assertNull
argument_list|(
name|creator
argument_list|)
expr_stmt|;
name|creator
operator|=
name|manager
operator|.
name|getEntryCreator
argument_list|(
name|ImportDataTest
operator|.
name|FILE_IN_DATABASE
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
name|creator
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|creator
operator|.
name|accept
argument_list|(
name|ImportDataTest
operator|.
name|FILE_IN_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAddEntrysFromFiles ()
specifier|public
name|void
name|testAddEntrysFromFiles
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|FileInputStream
name|stream
init|=
operator|new
name|FileInputStream
argument_list|(
name|ImportDataTest
operator|.
name|UNLINKED_FILES_TEST_BIB
argument_list|)
init|;
name|InputStreamReader
name|reader
operator|=
operator|new
name|InputStreamReader
argument_list|(
name|stream
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
init|)
block|{
name|ParserResult
name|result
init|=
operator|new
name|BibtexParser
argument_list|(
name|prefs
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|)
operator|.
name|parse
argument_list|(
name|reader
argument_list|)
decl_stmt|;
name|BibDatabase
name|database
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|files
operator|.
name|add
argument_list|(
name|ImportDataTest
operator|.
name|FILE_NOT_IN_DATABASE
argument_list|)
expr_stmt|;
name|files
operator|.
name|add
argument_list|(
name|ImportDataTest
operator|.
name|NOT_EXISTING_PDF
argument_list|)
expr_stmt|;
name|EntryFromFileCreatorManager
name|manager
init|=
operator|new
name|EntryFromFileCreatorManager
argument_list|(
name|externalFileTypes
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|errors
init|=
name|manager
operator|.
name|addEntrysFromFiles
argument_list|(
name|files
argument_list|,
name|database
argument_list|,
literal|null
argument_list|,
literal|true
argument_list|)
decl_stmt|;
comment|/**              * One file doesn't exist, so adding it as an entry should lead to an error message.              */
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|errors
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|boolean
name|file1Found
init|=
literal|false
decl_stmt|;
name|boolean
name|file2Found
init|=
literal|false
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|database
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|String
name|filesInfo
init|=
name|entry
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
name|filesInfo
operator|.
name|contains
argument_list|(
name|files
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|file1Found
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|filesInfo
operator|.
name|contains
argument_list|(
name|files
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|file2Found
operator|=
literal|true
expr_stmt|;
block|}
block|}
name|assertTrue
argument_list|(
name|file1Found
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|file2Found
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

