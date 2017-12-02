begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.journals
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
package|;
end_package

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

begin_class
DECL|class|AbbreviationParserTest
specifier|public
class|class
name|AbbreviationParserTest
block|{
annotation|@
name|Test
DECL|method|testReadJournalListFromResource ()
specifier|public
name|void
name|testReadJournalListFromResource
parameter_list|()
block|{
name|AbbreviationParser
name|ap
init|=
operator|new
name|AbbreviationParser
argument_list|()
decl_stmt|;
name|ap
operator|.
name|readJournalListFromResource
argument_list|(
literal|"/journals/journalList.txt"
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|ap
operator|.
name|getAbbreviations
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

