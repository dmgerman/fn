begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.search
package|package
name|org
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
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
operator|.
name|rules
operator|.
name|describer
operator|.
name|ContainsAndRegexBasedSearchRuleDescriber
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

begin_class
DECL|class|ContainsAndRegexBasedSearchRuleDescriberTest
specifier|public
class|class
name|ContainsAndRegexBasedSearchRuleDescriberTest
block|{
annotation|@
name|Test
DECL|method|testNoAst ()
specifier|public
name|void
name|testNoAst
parameter_list|()
block|{
name|String
name|query
init|=
literal|"a b"
decl_stmt|;
name|evaluateNoAst
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|"This search contains entries in which any field contains the regular expression "
operator|+
literal|"<b>&#97;</b><b>&#98;</b> (case sensitive). "
operator|+
literal|"<p><br>Hint: To search specific fields only, "
operator|+
literal|"enter for example:<p><tt>author=smith and title=electrical</tt>"
argument_list|)
expr_stmt|;
name|evaluateNoAst
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|,
literal|"This search contains entries in which any field contains the term "
operator|+
literal|"<b>&#97;</b><b>&#98;</b> (case sensitive). "
operator|+
literal|"<p><br>Hint: To search specific fields only, enter for "
operator|+
literal|"example:<p><tt>author=smith and title=electrical</tt>"
argument_list|)
expr_stmt|;
name|evaluateNoAst
argument_list|(
name|query
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
literal|"This search contains entries in which any field contains the term "
operator|+
literal|"<b>&#97;</b><b>&#98;</b> (case insensitive). "
operator|+
literal|"<p><br>Hint: To search specific fields only, enter for "
operator|+
literal|"example:<p><tt>author=smith and title=electrical</tt>"
argument_list|)
expr_stmt|;
name|evaluateNoAst
argument_list|(
name|query
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|"This search contains entries in which any field contains the regular "
operator|+
literal|"expression<b>&#97;</b><b>&#98;</b> (case insensitive). "
operator|+
literal|"<p><br>Hint: To search specific fields only, enter for "
operator|+
literal|"example:<p><tt>author=smith and title=electrical</tt>"
argument_list|)
expr_stmt|;
block|}
DECL|method|evaluateNoAst (String query, boolean caseSensitive, boolean regex, String expected)
specifier|private
name|void
name|evaluateNoAst
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
name|assertEquals
argument_list|(
name|expected
argument_list|,
operator|new
name|ContainsAndRegexBasedSearchRuleDescriber
argument_list|(
name|caseSensitive
argument_list|,
name|regex
argument_list|,
name|query
argument_list|)
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
