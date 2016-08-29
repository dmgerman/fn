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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|util
operator|.
name|ParseException
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
annotation|@
name|Test
DECL|method|testToString ()
specifier|public
name|void
name|testToString
parameter_list|()
throws|throws
name|ParseException
block|{
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
literal|"author"
argument_list|,
literal|"asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"KeywordGroup:myExplicitGroup;0;author;asdf;1;1;"
argument_list|,
name|group
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testToString2 ()
specifier|public
name|void
name|testToString2
parameter_list|()
throws|throws
name|ParseException
block|{
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
literal|"author"
argument_list|,
literal|"asdf"
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|GroupHierarchyType
operator|.
name|REFINING
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"KeywordGroup:myExplicitGroup;1;author;asdf;0;1;"
argument_list|,
name|group
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
throws|throws
name|Exception
block|{
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"name"
argument_list|,
literal|"keywords"
argument_list|,
literal|"test"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"keywords"
argument_list|,
literal|"test"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|isMatch
argument_list|(
name|entry
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
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"name"
argument_list|,
literal|"keywords"
argument_list|,
literal|"test"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some sentence containing test word"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|isMatch
argument_list|(
name|entry
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
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"name"
argument_list|,
literal|"keywords"
argument_list|,
literal|"test"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some,list,containing,test,word"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|isMatch
argument_list|(
name|entry
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
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"name"
argument_list|,
literal|"keywords"
argument_list|,
literal|"test"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some;list;containing;test;word"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|isMatch
argument_list|(
name|entry
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
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"name"
argument_list|,
literal|"keywords"
argument_list|,
literal|"\\H2O"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"keywords"
argument_list|,
literal|"\\H2O"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|isMatch
argument_list|(
name|entry
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
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"name"
argument_list|,
literal|"keywords"
argument_list|,
literal|"\\H2O"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some sentence containing \\H2O word"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|isMatch
argument_list|(
name|entry
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
name|KeywordGroup
name|group
init|=
operator|new
name|KeywordGroup
argument_list|(
literal|"name"
argument_list|,
literal|"keywords"
argument_list|,
literal|"test word"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|", "
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some sentence containing test word"
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|group
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

