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
name|util
operator|.
name|DummyFileUpdateMonitor
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
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_class
DECL|class|BibDatabaseTestsWithFiles
specifier|public
class|class
name|BibDatabaseTestsWithFiles
block|{
DECL|field|importFormatPreferences
specifier|private
name|ImportFormatPreferences
name|importFormatPreferences
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|importFormatPreferences
operator|=
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
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|resolveStrings ()
specifier|public
name|void
name|resolveStrings
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
literal|"src/test/resources/org/jabref/util/twente.bib"
argument_list|)
init|;
name|InputStreamReader
name|fr
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
name|importFormatPreferences
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|)
operator|.
name|parse
argument_list|(
name|fr
argument_list|)
decl_stmt|;
name|BibDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Arvind"
argument_list|,
name|db
operator|.
name|resolveForStrings
argument_list|(
literal|"#Arvind#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Patterson, David"
argument_list|,
name|db
operator|.
name|resolveForStrings
argument_list|(
literal|"#Patterson#"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Arvind and Patterson, David"
argument_list|,
name|db
operator|.
name|resolveForStrings
argument_list|(
literal|"#Arvind# and #Patterson#"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Strings that are not found return just the given string.
name|assertEquals
argument_list|(
literal|"#unknown#"
argument_list|,
name|db
operator|.
name|resolveForStrings
argument_list|(
literal|"#unknown#"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

