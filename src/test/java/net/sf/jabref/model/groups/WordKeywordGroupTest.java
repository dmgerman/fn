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
name|assertFalse
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
DECL|class|WordKeywordGroupTest
specifier|public
class|class
name|WordKeywordGroupTest
block|{
DECL|field|testGroup
specifier|private
name|WordKeywordGroup
name|testGroup
decl_stmt|;
DECL|field|testCaseSensitiveGroup
specifier|private
name|WordKeywordGroup
name|testCaseSensitiveGroup
decl_stmt|;
DECL|field|waterGroup
specifier|private
name|WordKeywordGroup
name|waterGroup
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|testGroup
operator|=
operator|new
name|WordKeywordGroup
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
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|testCaseSensitiveGroup
operator|=
operator|new
name|WordKeywordGroup
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
literal|true
argument_list|,
literal|','
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|waterGroup
operator|=
operator|new
name|WordKeywordGroup
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
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsFindsSameWord ()
specifier|public
name|void
name|containsFindsSameWord
parameter_list|()
block|{
name|entry
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
name|testGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsFindsWordInSentence ()
specifier|public
name|void
name|containsFindsWordInSentence
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
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
name|testGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsFindsWordInCommaSeparatedList ()
specifier|public
name|void
name|containsFindsWordInCommaSeparatedList
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
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
name|testGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsFindsWordInSemicolonSeparatedList ()
specifier|public
name|void
name|containsFindsWordInSemicolonSeparatedList
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
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
name|testGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsFindsSameComplexWord ()
specifier|public
name|void
name|containsFindsSameComplexWord
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
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
name|waterGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsFindsComplexWordInSentence ()
specifier|public
name|void
name|containsFindsComplexWordInSentence
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
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
name|waterGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsDoesNotFindWordIfCaseDiffers ()
specifier|public
name|void
name|containsDoesNotFindWordIfCaseDiffers
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Test"
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|testCaseSensitiveGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|containsDoesNotFindsWordInSentenceIfCaseDiffers ()
specifier|public
name|void
name|containsDoesNotFindsWordInSentenceIfCaseDiffers
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Some sentence containing Test word"
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|testCaseSensitiveGroup
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addChangesFieldIfEmptyBefore ()
specifier|public
name|void
name|addChangesFieldIfEmptyBefore
parameter_list|()
throws|throws
name|Exception
block|{
name|testGroup
operator|.
name|add
argument_list|(
name|entry
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
name|entry
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
DECL|method|addChangesFieldIfNotEmptyBefore ()
specifier|public
name|void
name|addChangesFieldIfNotEmptyBefore
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
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
name|testGroup
operator|.
name|add
argument_list|(
name|entry
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
name|entry
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
DECL|method|addDoesNotAddDuplicate ()
specifier|public
name|void
name|addDoesNotAddDuplicate
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
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
name|testGroup
operator|.
name|add
argument_list|(
name|entry
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
name|entry
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
DECL|method|removeDoesNothingIfEntryNotMatched ()
specifier|public
name|void
name|removeDoesNothingIfEntryNotMatched
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
literal|"something"
argument_list|)
expr_stmt|;
name|testGroup
operator|.
name|remove
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"something"
argument_list|)
argument_list|,
name|entry
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
DECL|method|removeRemovesNameFromField ()
specifier|public
name|void
name|removeRemovesNameFromField
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
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
name|testGroup
operator|.
name|remove
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"blubb"
argument_list|)
argument_list|,
name|entry
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
