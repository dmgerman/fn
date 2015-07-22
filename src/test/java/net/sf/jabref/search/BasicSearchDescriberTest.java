begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|search
operator|.
name|describer
operator|.
name|BasicSearchDescriber
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
DECL|class|BasicSearchDescriberTest
specifier|public
class|class
name|BasicSearchDescriberTest
block|{
annotation|@
name|Test
DECL|method|testNoAst ()
specifier|public
name|void
name|testNoAst
parameter_list|()
throws|throws
name|Exception
block|{
name|String
name|query
init|=
literal|"a=b"
decl_stmt|;
name|evaluateNoAst
argument_list|(
name|query
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|"This group contains entries in which any field contains the regular expression "
operator|+
literal|"<b>&#97;&#61;&#98;</b> (case sensitive). Entries cannot be manually assigned to or removed "
operator|+
literal|"from this group.<p><br>Hint: To search specific fields only, "
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
literal|"This group contains entries in which any field contains the term "
operator|+
literal|"<b>&#97;&#61;&#98;</b> (case sensitive). Entries cannot be manually assigned to or removed from "
operator|+
literal|"this group.<p><br>Hint: To search specific fields only, enter for "
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
literal|"This group contains entries in which any field contains the term "
operator|+
literal|"<b>&#97;&#61;&#98;</b> (case insensitive). Entries cannot be manually assigned to or removed "
operator|+
literal|"from this group.<p><br>Hint: To search specific fields only, enter for "
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
literal|"This group contains entries in which any field contains the regular "
operator|+
literal|"expression<b>&#97;&#61;&#98;</b> (case insensitive). Entries cannot be manually assigned "
operator|+
literal|"to or removed from this group.<p><br>Hint: To search specific fields only, enter for "
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
name|BasicSearchDescriber
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

