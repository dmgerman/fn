begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|BufferedReader
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
name|Paths
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
name|Set
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
name|Globals
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
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|After
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|GroupsUtilTest
specifier|public
class|class
name|GroupsUtilTest
block|{
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
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
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
literal|null
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
name|IOException
block|{
try|try
init|(
name|BufferedReader
name|fr
init|=
name|Files
operator|.
name|newBufferedReader
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/testbib/testjabref.bib"
argument_list|)
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
name|BibtexParser
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
name|List
argument_list|<
name|String
argument_list|>
name|fieldList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|fieldList
operator|.
name|add
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|authorSet
init|=
name|GroupsUtil
operator|.
name|findAuthorLastNames
argument_list|(
name|db
argument_list|,
name|fieldList
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|authorSet
operator|.
name|contains
argument_list|(
literal|"Brewer"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|15
argument_list|,
name|authorSet
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|keywordSet
init|=
name|GroupsUtil
operator|.
name|findDeliminatedWordsInField
argument_list|(
name|db
argument_list|,
literal|"keywords"
argument_list|,
literal|";"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|keywordSet
operator|.
name|contains
argument_list|(
literal|"Brain"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|60
argument_list|,
name|keywordSet
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|wordSet
init|=
name|GroupsUtil
operator|.
name|findAllWordsInField
argument_list|(
name|db
argument_list|,
literal|"month"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|wordSet
operator|.
name|contains
argument_list|(
literal|"Feb"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|wordSet
operator|.
name|contains
argument_list|(
literal|"Mar"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|wordSet
operator|.
name|contains
argument_list|(
literal|"May"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|wordSet
operator|.
name|contains
argument_list|(
literal|"Jul"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|wordSet
operator|.
name|contains
argument_list|(
literal|"Dec"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|5
argument_list|,
name|wordSet
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

