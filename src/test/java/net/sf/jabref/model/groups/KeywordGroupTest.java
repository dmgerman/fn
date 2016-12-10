begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|groups
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|entry
operator|.
name|BibEntry
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
name|entry
operator|.
name|FieldName
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
name|Ignore
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
DECL|class|KeywordGroupTest
specifier|public
class|class
name|KeywordGroupTest
block|{
DECL|field|keywordTestGroup
specifier|private
name|SimpleKeywordGroup
name|keywordTestGroup
decl_stmt|;
DECL|field|complexKeywordGroup
specifier|private
name|KeywordGroup
name|complexKeywordGroup
decl_stmt|;
DECL|field|emptyEntry
specifier|private
name|BibEntry
name|emptyEntry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|keywordTestGroup
operator|=
operator|new
name|SimpleKeywordGroup
argument_list|(
literal|"name"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"keywords"
argument_list|,
literal|"test"
argument_list|,
literal|false
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|complexKeywordGroup
operator|=
operator|new
name|SimpleKeywordGroup
argument_list|(
literal|"name"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"keywords"
argument_list|,
literal|"\\H2O"
argument_list|,
literal|false
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|emptyEntry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testToString ()
specifier|public
name|void
name|testToString
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"KeywordGroup:name;0;keywords;test;0;0;"
argument_list|,
name|keywordTestGroup
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testToString2 ()
specifier|public
name|void
name|testToString2
parameter_list|()
block|{
name|KeywordGroup
name|anotherGroup
init|=
operator|new
name|RegexKeywordGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|REFINING
argument_list|,
literal|"author"
argument_list|,
literal|"asdf"
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"KeywordGroup:myExplicitGroup;1;author;asdf;0;1;"
argument_list|,
name|anotherGroup
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsSimpleWord ()
specifier|public
name|void
name|containsSimpleWord
parameter_list|()
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"test"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|keywordTestGroup
operator|.
name|isMatch
argument_list|(
name|emptyEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsSimpleWordInSentence ()
specifier|public
name|void
name|containsSimpleWordInSentence
parameter_list|()
throws|throws
name|Exception
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some sentence containing test word"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|keywordTestGroup
operator|.
name|isMatch
argument_list|(
name|emptyEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsSimpleWordCommaSeparated ()
specifier|public
name|void
name|containsSimpleWordCommaSeparated
parameter_list|()
throws|throws
name|Exception
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some,list,containing,test,word"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|keywordTestGroup
operator|.
name|isMatch
argument_list|(
name|emptyEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsSimpleWordSemicolonSeparated ()
specifier|public
name|void
name|containsSimpleWordSemicolonSeparated
parameter_list|()
throws|throws
name|Exception
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some;list;containing;test;word"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|keywordTestGroup
operator|.
name|isMatch
argument_list|(
name|emptyEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsComplexWord ()
specifier|public
name|void
name|containsComplexWord
parameter_list|()
throws|throws
name|Exception
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"\\H2O"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|complexKeywordGroup
operator|.
name|isMatch
argument_list|(
name|emptyEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsComplexWordInSentence ()
specifier|public
name|void
name|containsComplexWordInSentence
parameter_list|()
throws|throws
name|Exception
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some sentence containing \\H2O word"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|complexKeywordGroup
operator|.
name|isMatch
argument_list|(
name|emptyEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsWordWithWhitespaceInSentence ()
specifier|public
name|void
name|containsWordWithWhitespaceInSentence
parameter_list|()
throws|throws
name|Exception
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some sentence containing test word"
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|keywordTestGroup
operator|.
name|isMatch
argument_list|(
name|emptyEntry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addGroupToBibEntrySuccessfullyIfEmptyBefore ()
specifier|public
name|void
name|addGroupToBibEntrySuccessfullyIfEmptyBefore
parameter_list|()
throws|throws
name|Exception
block|{
name|keywordTestGroup
operator|.
name|add
argument_list|(
name|emptyEntry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"test"
argument_list|)
argument_list|,
name|emptyEntry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addGroupToBibEntrySuccessfullyIfNotEmptyBefore ()
specifier|public
name|void
name|addGroupToBibEntrySuccessfullyIfNotEmptyBefore
parameter_list|()
throws|throws
name|Exception
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
literal|"bla, blubb"
argument_list|)
expr_stmt|;
name|keywordTestGroup
operator|.
name|add
argument_list|(
name|emptyEntry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"bla, blubb, test"
argument_list|)
argument_list|,
name|emptyEntry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|noDuplicateStoredIfAlreadyInGroup ()
specifier|public
name|void
name|noDuplicateStoredIfAlreadyInGroup
parameter_list|()
throws|throws
name|Exception
block|{
name|emptyEntry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
literal|"test, blubb"
argument_list|)
expr_stmt|;
name|keywordTestGroup
operator|.
name|add
argument_list|(
name|emptyEntry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"test, blubb"
argument_list|)
argument_list|,
name|emptyEntry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

