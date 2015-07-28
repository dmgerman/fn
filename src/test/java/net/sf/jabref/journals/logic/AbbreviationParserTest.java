begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.journals.logic
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|journals
operator|.
name|logic
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
name|journals
operator|.
name|logic
operator|.
name|AbbreviationParser
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

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
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
throws|throws
name|Exception
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
name|Globals
operator|.
name|JOURNALS_FILE_BUILTIN
argument_list|)
expr_stmt|;
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|ap
operator|.
name|getAbbreviations
argument_list|()
control|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|abbreviation
operator|.
name|toPropertiesLine
argument_list|()
argument_list|)
expr_stmt|;
block|}
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

