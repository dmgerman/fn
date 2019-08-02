begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.groups
package|package
name|org
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
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|StandardField
import|;
end_import

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
name|assertEquals
import|;
end_import

begin_class
DECL|class|AutomaticKeywordGroupTest
specifier|public
class|class
name|AutomaticKeywordGroupTest
block|{
annotation|@
name|Test
DECL|method|createSubgroupsForTwoKeywords ()
specifier|public
name|void
name|createSubgroupsForTwoKeywords
parameter_list|()
throws|throws
name|Exception
block|{
name|AutomaticKeywordGroup
name|keywordsGroup
init|=
operator|new
name|AutomaticKeywordGroup
argument_list|(
literal|"Keywords"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|','
argument_list|,
literal|'>'
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
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"A, B"
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|GroupTreeNode
argument_list|>
name|expected
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
name|expected
operator|.
name|add
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
operator|new
name|WordKeywordGroup
argument_list|(
literal|"A"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"A"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|expected
operator|.
name|add
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
operator|new
name|WordKeywordGroup
argument_list|(
literal|"B"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"B"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|true
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|keywordsGroup
operator|.
name|createSubgroups
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

