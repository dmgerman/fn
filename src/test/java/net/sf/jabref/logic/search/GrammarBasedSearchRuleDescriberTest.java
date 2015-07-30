begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
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
name|logic
operator|.
name|search
operator|.
name|describer
operator|.
name|GrammarBasedSearchRuleDescriber
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
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|GrammarBasedSearchRule
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
name|*
import|;
end_import

begin_class
DECL|class|GrammarBasedSearchRuleDescriberTest
specifier|public
class|class
name|GrammarBasedSearchRuleDescriberTest
block|{
annotation|@
name|Test
DECL|method|testSimpleQuery ()
specifier|public
name|void
name|testSimpleQuery
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|query
init|=
literal|"a=b"
decl_stmt|;
name|evaluate
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|"This group contains entries in which the field<b>&#97;</b> "
operator|+
literal|"contains the Regular Expression<b>&#98;</b>. "
operator|+
literal|"The search is case sensitive."
argument_list|)
expr_stmt|;
name|evaluate
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
literal|"This group contains entries in which the field<b>&#97;</b> "
operator|+
literal|"contains the term<b>&#98;</b>. "
operator|+
literal|"The search is case sensitive."
argument_list|)
expr_stmt|;
name|evaluate
argument_list|(
name|query
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|"This group contains entries in which the field<b>&#97;</b> "
operator|+
literal|"contains the term<b>&#98;</b>. "
operator|+
literal|"The search is case insensitive."
argument_list|)
expr_stmt|;
name|evaluate
argument_list|(
name|query
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|"This group contains entries in which the field<b>&#97;</b> "
operator|+
literal|"contains the Regular Expression<b>&#98;</b>. "
operator|+
literal|"The search is case insensitive."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testComplexQuery ()
specifier|public
name|void
name|testComplexQuery
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|query
init|=
literal|"not a=b and c=e or e=\"x\""
decl_stmt|;
name|evaluate
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|"This group contains entries in which not ((the field<b>&#97;</b> "
operator|+
literal|"contains the Regular Expression<b>&#98;</b> and the field<b>&#99;</b> contains the "
operator|+
literal|"Regular Expression<b>&#101;</b>) or the field<b>&#101;</b> contains the Regular Expression "
operator|+
literal|"<b>&#120;</b>). The search is case sensitive."
argument_list|)
expr_stmt|;
name|evaluate
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
literal|"This group contains entries in which not ((the field<b>&#97;</b> "
operator|+
literal|"contains the term<b>&#98;</b> and the field<b>&#99;</b> contains the term<b>&#101;</b>) "
operator|+
literal|"or the field<b>&#101;</b> contains the term<b>&#120;</b>). The search is case sensitive."
argument_list|)
expr_stmt|;
name|evaluate
argument_list|(
name|query
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|"This group contains entries in which not ((the field<b>&#97;</b> "
operator|+
literal|"contains the term<b>&#98;</b> and the field<b>&#99;</b> contains the term<b>&#101;</b>) "
operator|+
literal|"or the field<b>&#101;</b> contains the term<b>&#120;</b>). The search is case insensitive."
argument_list|)
expr_stmt|;
name|evaluate
argument_list|(
name|query
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|"This group contains entries in which not ((the field<b>&#97;</b> "
operator|+
literal|"contains the Regular Expression<b>&#98;</b> and the field<b>&#99;</b> contains "
operator|+
literal|"the Regular Expression<b>&#101;</b>) or the field<b>&#101;</b> contains the Regular "
operator|+
literal|"Expression<b>&#120;</b>). The search is case insensitive."
argument_list|)
expr_stmt|;
block|}
DECL|method|evaluate (String query, boolean caseSensitive, boolean regex, String expected)
specifier|private
name|void
name|evaluate
parameter_list|(
name|String
name|query
parameter_list|,
name|boolean
name|caseSensitive
parameter_list|,
name|boolean
name|regex
parameter_list|,
name|String
name|expected
parameter_list|)
block|{
name|GrammarBasedSearchRule
name|grammarBasedSearchRule
init|=
operator|new
name|GrammarBasedSearchRule
argument_list|(
name|caseSensitive
argument_list|,
name|regex
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|grammarBasedSearchRule
operator|.
name|validateSearchStrings
argument_list|(
name|query
argument_list|)
argument_list|)
expr_stmt|;
name|GrammarBasedSearchRuleDescriber
name|describer
init|=
operator|new
name|GrammarBasedSearchRuleDescriber
argument_list|(
name|caseSensitive
argument_list|,
name|regex
argument_list|,
name|grammarBasedSearchRule
operator|.
name|getTree
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|describer
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

