begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|xmp
operator|.
name|XmpPreferences
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
name|model
operator|.
name|util
operator|.
name|FileUpdateMonitor
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
name|assertThrows
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
DECL|class|ImportFormatReaderTestParameterless
class|class
name|ImportFormatReaderTestParameterless
block|{
DECL|field|reader
specifier|private
name|ImportFormatReader
name|reader
decl_stmt|;
DECL|field|fileMonitor
specifier|private
specifier|final
name|FileUpdateMonitor
name|fileMonitor
init|=
operator|new
name|DummyFileUpdateMonitor
argument_list|()
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
name|void
name|setUp
parameter_list|()
block|{
name|reader
operator|=
operator|new
name|ImportFormatReader
argument_list|()
expr_stmt|;
name|ImportFormatPreferences
name|importFormatPreferences
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
name|when
argument_list|(
name|importFormatPreferences
operator|.
name|getEncoding
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
expr_stmt|;
name|reader
operator|.
name|resetImportFormats
argument_list|(
name|importFormatPreferences
argument_list|,
name|mock
argument_list|(
name|XmpPreferences
operator|.
name|class
argument_list|)
argument_list|,
name|fileMonitor
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|importUnknownFormatThrowsExceptionIfNoMatchingImporterWasFound ()
name|void
name|importUnknownFormatThrowsExceptionIfNoMatchingImporterWasFound
parameter_list|()
throws|throws
name|Exception
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|ImportFormatReaderTestParameterless
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"fileformat/emptyFile.xml"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|assertThrows
argument_list|(
name|ImportException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|reader
operator|.
name|importUnknownFormat
argument_list|(
name|file
argument_list|,
name|fileMonitor
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|importUnknownFormatThrowsExceptionIfPathIsNull ()
name|void
name|importUnknownFormatThrowsExceptionIfPathIsNull
parameter_list|()
throws|throws
name|Exception
block|{
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|reader
operator|.
name|importUnknownFormat
argument_list|(
literal|null
argument_list|,
name|fileMonitor
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|importUnknownFormatThrowsExceptionIfDataIsNull ()
name|void
name|importUnknownFormatThrowsExceptionIfDataIsNull
parameter_list|()
throws|throws
name|Exception
block|{
name|assertThrows
argument_list|(
name|NullPointerException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|reader
operator|.
name|importUnknownFormat
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|importFromFileWithUnknownFormatThrowsException ()
name|void
name|importFromFileWithUnknownFormatThrowsException
parameter_list|()
throws|throws
name|Exception
block|{
name|assertThrows
argument_list|(
name|ImportException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
name|reader
operator|.
name|importFromFile
argument_list|(
literal|"someunknownformat"
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
literal|"somepath"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

