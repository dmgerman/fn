begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

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

begin_class
DECL|class|KeywordListTest
specifier|public
class|class
name|KeywordListTest
block|{
DECL|field|keywords
specifier|private
name|KeywordList
name|keywords
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|keywords
operator|=
operator|new
name|KeywordList
argument_list|()
expr_stmt|;
name|keywords
operator|.
name|add
argument_list|(
literal|"keywordOne"
argument_list|)
expr_stmt|;
name|keywords
operator|.
name|add
argument_list|(
literal|"keywordTwo"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseEmptyStringReturnsEmptyList ()
specifier|public
name|void
name|parseEmptyStringReturnsEmptyList
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|()
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|""
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseOneWordReturnsOneKeyword ()
specifier|public
name|void
name|parseOneWordReturnsOneKeyword
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
literal|"keywordOne"
argument_list|)
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|"keywordOne"
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseTwoWordReturnsTwoKeywords ()
specifier|public
name|void
name|parseTwoWordReturnsTwoKeywords
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
literal|"keywordOne"
argument_list|,
literal|"keywordTwo"
argument_list|)
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|"keywordOne, keywordTwo"
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseTwoWordReturnsTwoKeywordsWithoutSpace ()
specifier|public
name|void
name|parseTwoWordReturnsTwoKeywordsWithoutSpace
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
literal|"keywordOne"
argument_list|,
literal|"keywordTwo"
argument_list|)
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|"keywordOne,keywordTwo"
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseTwoWordReturnsTwoKeywordsWithDifferentDelimiter ()
specifier|public
name|void
name|parseTwoWordReturnsTwoKeywordsWithDifferentDelimiter
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
literal|"keywordOne"
argument_list|,
literal|"keywordTwo"
argument_list|)
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|"keywordOne| keywordTwo"
argument_list|,
literal|'|'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseWordsWithWhitespaceReturnsOneKeyword ()
specifier|public
name|void
name|parseWordsWithWhitespaceReturnsOneKeyword
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
literal|"keyword and one"
argument_list|)
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|"keyword and one"
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseWordsWithWhitespaceAndCommaReturnsTwoKeyword ()
specifier|public
name|void
name|parseWordsWithWhitespaceAndCommaReturnsTwoKeyword
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
literal|"keyword and one"
argument_list|,
literal|"and two"
argument_list|)
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|"keyword and one, and two"
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseIgnoresDuplicates ()
specifier|public
name|void
name|parseIgnoresDuplicates
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
literal|"keywordOne"
argument_list|,
literal|"keywordTwo"
argument_list|)
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|"keywordOne, keywordTwo, keywordOne"
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseWordsWithBracketsReturnsOneKeyword ()
specifier|public
name|void
name|parseWordsWithBracketsReturnsOneKeyword
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
operator|new
name|KeywordList
argument_list|(
literal|"[a] keyword"
argument_list|)
argument_list|,
name|KeywordList
operator|.
name|parse
argument_list|(
literal|"[a] keyword"
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|asStringAddsSpaceAfterDelimiter ()
specifier|public
name|void
name|asStringAddsSpaceAfterDelimiter
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|"keywordOne, keywordTwo"
argument_list|,
name|keywords
operator|.
name|getAsString
argument_list|(
literal|','
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
