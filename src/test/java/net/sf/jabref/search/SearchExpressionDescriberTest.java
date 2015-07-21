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
name|antlr
operator|.
name|TokenStreamException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|AST
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
name|search
operator|.
name|rules
operator|.
name|SearchExpression
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
name|IOException
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
DECL|class|SearchExpressionDescriberTest
specifier|public
class|class
name|SearchExpressionDescriberTest
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
literal|"This group contains entries in which the field<b>&#97;</b> "
operator|+
literal|"doesn't contain the Regular Expression<b>&#98;</b> and (the field<b>&#99;</b> "
operator|+
literal|"contains the Regular Expression<b>&#101;</b> or the field<b>&#101;</b> "
operator|+
literal|"contains the Regular Expression<b>&#120;</b>). The search is case sensitive."
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
literal|"doesn't contain the term<b>&#98;</b> and (the field<b>&#99;</b> contains "
operator|+
literal|"the term<b>&#101;</b> or the field<b>&#101;</b> contains the term<b>&#120;</b>). "
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
literal|"doesn't contain the term<b>&#98;</b> and (the field<b>&#99;</b> contains "
operator|+
literal|"the term<b>&#101;</b> or the field<b>&#101;</b> contains the term<b>&#120;</b>). "
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
literal|"doesn't contain the Regular Expression<b>&#98;</b> and (the field<b>&#99;</b> "
operator|+
literal|"contains the Regular Expression<b>&#101;</b> or the field<b>&#101;</b> contains "
operator|+
literal|"the Regular Expression<b>&#120;</b>). The search is case insensitive."
argument_list|)
expr_stmt|;
block|}
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
throws|throws
name|IOException
throws|,
name|TokenStreamException
throws|,
name|antlr
operator|.
name|RecognitionException
block|{
name|SearchExpressionDescriber
name|describer
init|=
operator|new
name|SearchExpressionDescriber
argument_list|(
name|caseSensitive
argument_list|,
name|regex
argument_list|,
name|query
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|describer
operator|.
name|getDescriptionForPreview
argument_list|()
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
throws|throws
name|IOException
throws|,
name|TokenStreamException
throws|,
name|antlr
operator|.
name|RecognitionException
block|{
name|AST
name|ast
init|=
operator|new
name|SearchExpression
argument_list|(
name|caseSensitive
argument_list|,
name|regex
argument_list|,
name|query
argument_list|)
operator|.
name|getAst
argument_list|()
decl_stmt|;
name|SearchExpressionDescriber
name|describer
init|=
operator|new
name|SearchExpressionDescriber
argument_list|(
name|caseSensitive
argument_list|,
name|regex
argument_list|,
name|query
argument_list|,
name|ast
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|describer
operator|.
name|getDescriptionForPreview
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

