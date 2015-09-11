begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|ParserResult
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
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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
name|Before
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
name|java
operator|.
name|io
operator|.
name|FileNotFoundException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|FileReader
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

begin_class
DECL|class|BibtexDatabaseTest
specifier|public
class|class
name|BibtexDatabaseTest
block|{
annotation|@
name|Before
DECL|method|setup ()
specifier|public
name|void
name|setup
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
comment|// set preferences for this test
block|}
annotation|@
name|After
DECL|method|teardown ()
specifier|public
name|void
name|teardown
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
literal|null
expr_stmt|;
block|}
comment|/**      * Some basic test cases for resolving strings.      *      * @throws FileNotFoundException      * @throws IOException      */
annotation|@
name|Test
DECL|method|testResolveStrings ()
specifier|public
name|void
name|testResolveStrings
parameter_list|()
throws|throws
name|IOException
block|{
name|ParserResult
name|result
init|=
name|BibtexParser
operator|.
name|parse
argument_list|(
operator|new
name|FileReader
argument_list|(
literal|"src/test/resources/net/sf/jabref/util/twente.bib"
argument_list|)
argument_list|)
decl_stmt|;
name|BibtexDatabase
name|db
init|=
name|result
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
end_class

end_unit

