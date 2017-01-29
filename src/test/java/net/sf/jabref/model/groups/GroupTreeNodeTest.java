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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|search
operator|.
name|matchers
operator|.
name|AndMatcher
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
name|search
operator|.
name|matchers
operator|.
name|OrMatcher
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
DECL|class|GroupTreeNodeTest
specifier|public
class|class
name|GroupTreeNodeTest
block|{
DECL|field|entries
specifier|private
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
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
throws|throws
name|Exception
block|{
name|entries
operator|.
name|clear
argument_list|()
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entries
operator|.
name|add
argument_list|(
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"author"
argument_list|,
literal|"author1 and author2"
argument_list|)
argument_list|)
expr_stmt|;
name|entries
operator|.
name|add
argument_list|(
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"author"
argument_list|,
literal|"author1"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Gets the marked node in the following tree of explicit groups:      * Root      *      A ExplicitA, Including      *      A ExplicitParent, Independent (= parent)      *          B ExplicitNode, Refining (<-- this)      */
DECL|method|getNodeInSimpleTree (GroupTreeNode root)
specifier|public
specifier|static
name|GroupTreeNode
name|getNodeInSimpleTree
parameter_list|(
name|GroupTreeNode
name|root
parameter_list|)
block|{
name|root
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"ExplicitA"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|parent
init|=
name|root
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"ExplicitParent"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|parent
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"ExplicitNode"
argument_list|,
name|GroupHierarchyType
operator|.
name|REFINING
argument_list|,
literal|','
argument_list|)
argument_list|)
return|;
block|}
DECL|method|getNodeInSimpleTree ()
specifier|private
name|GroupTreeNode
name|getNodeInSimpleTree
parameter_list|()
block|{
return|return
name|getNodeInSimpleTree
argument_list|(
name|getRoot
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Gets the marked node in the following tree:      * Root      *      A SearchA      *      A ExplicitA, Including      *      A ExplicitGrandParent (= grand parent)      *          B ExplicitB      *          B KeywordParent (= parent)      *              C KeywordNode (<-- this)      *                  D ExplicitChild (= child)      *              C SearchC      *              C ExplicitC      *              C KeywordC      *          B SearchB      *          B KeywordB      *      A KeywordA      */
DECL|method|getNodeInComplexTree (GroupTreeNode root)
specifier|public
specifier|static
name|GroupTreeNode
name|getNodeInComplexTree
parameter_list|(
name|GroupTreeNode
name|root
parameter_list|)
block|{
name|root
operator|.
name|addSubgroup
argument_list|(
name|getSearchGroup
argument_list|(
literal|"SearchA"
argument_list|)
argument_list|)
expr_stmt|;
name|root
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"ExplicitA"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|grandParent
init|=
name|root
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"ExplicitGrandParent"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
argument_list|)
decl_stmt|;
name|root
operator|.
name|addSubgroup
argument_list|(
name|getKeywordGroup
argument_list|(
literal|"KeywordA"
argument_list|)
argument_list|)
expr_stmt|;
name|grandParent
operator|.
name|addSubgroup
argument_list|(
name|getExplict
argument_list|(
literal|"ExplicitB"
argument_list|)
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|parent
init|=
name|grandParent
operator|.
name|addSubgroup
argument_list|(
name|getKeywordGroup
argument_list|(
literal|"KeywordParent"
argument_list|)
argument_list|)
decl_stmt|;
name|grandParent
operator|.
name|addSubgroup
argument_list|(
name|getSearchGroup
argument_list|(
literal|"SearchB"
argument_list|)
argument_list|)
expr_stmt|;
name|grandParent
operator|.
name|addSubgroup
argument_list|(
name|getKeywordGroup
argument_list|(
literal|"KeywordB"
argument_list|)
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|node
init|=
name|parent
operator|.
name|addSubgroup
argument_list|(
name|getKeywordGroup
argument_list|(
literal|"KeywordNode"
argument_list|)
argument_list|)
decl_stmt|;
name|parent
operator|.
name|addSubgroup
argument_list|(
name|getSearchGroup
argument_list|(
literal|"SearchC"
argument_list|)
argument_list|)
expr_stmt|;
name|parent
operator|.
name|addSubgroup
argument_list|(
name|getExplict
argument_list|(
literal|"ExplicitC"
argument_list|)
argument_list|)
expr_stmt|;
name|parent
operator|.
name|addSubgroup
argument_list|(
name|getKeywordGroup
argument_list|(
literal|"KeywordC"
argument_list|)
argument_list|)
expr_stmt|;
name|node
operator|.
name|addSubgroup
argument_list|(
name|getExplict
argument_list|(
literal|"ExplicitChild"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|node
return|;
block|}
DECL|method|getKeywordGroup (String name)
specifier|private
specifier|static
name|AbstractGroup
name|getKeywordGroup
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
operator|new
name|WordKeywordGroup
argument_list|(
name|name
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"searchField"
argument_list|,
literal|"searchExpression"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|false
argument_list|)
return|;
block|}
DECL|method|getSearchGroup (String name)
specifier|private
specifier|static
name|AbstractGroup
name|getSearchGroup
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
operator|new
name|SearchGroup
argument_list|(
name|name
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|"searchExpression"
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
return|;
block|}
DECL|method|getExplict (String name)
specifier|private
specifier|static
name|AbstractGroup
name|getExplict
parameter_list|(
name|String
name|name
parameter_list|)
block|{
return|return
operator|new
name|ExplicitGroup
argument_list|(
name|name
argument_list|,
name|GroupHierarchyType
operator|.
name|REFINING
argument_list|,
literal|','
argument_list|)
return|;
block|}
comment|/*     public GroupTreeNode getNodeInComplexTree() {         return getNodeInComplexTree(new TreeNodeMock());     }     */
comment|/**      * Gets the marked in the following tree:      * Root      *      A      *      A      *      A (<- this)      *      A      */
comment|/*     public GroupTreeNode getNodeAsChild(TreeNodeMock root) {         root.addChild(new TreeNodeMock());         root.addChild(new TreeNodeMock());         TreeNodeMock node = new TreeNodeMock();         root.addChild(node);         root.addChild(new TreeNodeMock());         return node;     }     */
DECL|method|getRoot ()
specifier|public
specifier|static
name|GroupTreeNode
name|getRoot
parameter_list|()
block|{
return|return
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
operator|new
name|AllEntriesGroup
argument_list|(
literal|"All entries"
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Test
DECL|method|getSearchRuleForIndependentGroupReturnsGroupAsMatcher ()
specifier|public
name|void
name|getSearchRuleForIndependentGroupReturnsGroupAsMatcher
parameter_list|()
block|{
name|GroupTreeNode
name|node
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|node
operator|.
name|getGroup
argument_list|()
argument_list|,
name|node
operator|.
name|getSearchMatcher
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getSearchRuleForRefiningGroupReturnsParentAndGroupAsMatcher ()
specifier|public
name|void
name|getSearchRuleForRefiningGroupReturnsParentAndGroupAsMatcher
parameter_list|()
block|{
name|GroupTreeNode
name|parent
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"parent"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|node
init|=
name|parent
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|REFINING
argument_list|,
literal|','
argument_list|)
argument_list|)
decl_stmt|;
name|AndMatcher
name|matcher
init|=
operator|new
name|AndMatcher
argument_list|()
decl_stmt|;
name|matcher
operator|.
name|addRule
argument_list|(
name|node
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
name|matcher
operator|.
name|addRule
argument_list|(
name|parent
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|matcher
argument_list|,
name|node
operator|.
name|getSearchMatcher
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getSearchRuleForIncludingGroupReturnsGroupOrSubgroupAsMatcher ()
specifier|public
name|void
name|getSearchRuleForIncludingGroupReturnsGroupOrSubgroupAsMatcher
parameter_list|()
block|{
name|GroupTreeNode
name|node
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|','
argument_list|)
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|child
init|=
name|node
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"child"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
argument_list|)
decl_stmt|;
name|OrMatcher
name|matcher
init|=
operator|new
name|OrMatcher
argument_list|()
decl_stmt|;
name|matcher
operator|.
name|addRule
argument_list|(
name|node
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
name|matcher
operator|.
name|addRule
argument_list|(
name|child
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|matcher
argument_list|,
name|node
operator|.
name|getSearchMatcher
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|numberOfHitsReturnsZeroForEmptyList ()
specifier|public
name|void
name|numberOfHitsReturnsZeroForEmptyList
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|getNodeInSimpleTree
argument_list|()
operator|.
name|calculateNumberOfMatches
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|numberOfHitsMatchesOneEntry ()
specifier|public
name|void
name|numberOfHitsMatchesOneEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|parent
init|=
name|getNodeInSimpleTree
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|node
init|=
name|parent
operator|.
name|addSubgroup
argument_list|(
operator|new
name|WordKeywordGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"author"
argument_list|,
literal|"author2"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|node
operator|.
name|calculateNumberOfMatches
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|numberOfHitsMatchesMultipleEntries ()
specifier|public
name|void
name|numberOfHitsMatchesMultipleEntries
parameter_list|()
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|parent
init|=
name|getNodeInSimpleTree
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|node
init|=
name|parent
operator|.
name|addSubgroup
argument_list|(
operator|new
name|WordKeywordGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"author"
argument_list|,
literal|"author1"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|node
operator|.
name|calculateNumberOfMatches
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|numberOfHitsWorksForRefiningGroups ()
specifier|public
name|void
name|numberOfHitsWorksForRefiningGroups
parameter_list|()
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|grandParent
init|=
name|getNodeInSimpleTree
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|parent
init|=
name|grandParent
operator|.
name|addSubgroup
argument_list|(
operator|new
name|WordKeywordGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"author"
argument_list|,
literal|"author2"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|node
init|=
name|parent
operator|.
name|addSubgroup
argument_list|(
operator|new
name|WordKeywordGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|REFINING
argument_list|,
literal|"author"
argument_list|,
literal|"author1"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|node
operator|.
name|calculateNumberOfMatches
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|numberOfHitsWorksForHierarchyOfIndependentGroups ()
specifier|public
name|void
name|numberOfHitsWorksForHierarchyOfIndependentGroups
parameter_list|()
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|grandParent
init|=
name|getNodeInSimpleTree
argument_list|()
decl_stmt|;
name|GroupTreeNode
name|parent
init|=
name|grandParent
operator|.
name|addSubgroup
argument_list|(
operator|new
name|WordKeywordGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"author"
argument_list|,
literal|"author2"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|GroupTreeNode
name|node
init|=
name|parent
operator|.
name|addSubgroup
argument_list|(
operator|new
name|WordKeywordGroup
argument_list|(
literal|"node"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"author"
argument_list|,
literal|"author1"
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|false
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|node
operator|.
name|calculateNumberOfMatches
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setGroupChangesUnderlyingGroup ()
specifier|public
name|void
name|setGroupChangesUnderlyingGroup
parameter_list|()
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|node
init|=
name|getNodeInSimpleTree
argument_list|()
decl_stmt|;
name|AbstractGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"NewGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|node
operator|.
name|setGroup
argument_list|(
name|newGroup
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|newGroup
argument_list|,
name|node
operator|.
name|getGroup
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|setGroupAddsPreviousAssignmentsExplicitToExplicit ()
specifier|public
name|void
name|setGroupAddsPreviousAssignmentsExplicitToExplicit
parameter_list|()
throws|throws
name|Exception
block|{
name|ExplicitGroup
name|oldGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"OldGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|oldGroup
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|node
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|oldGroup
argument_list|)
decl_stmt|;
name|AbstractGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"NewGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|node
operator|.
name|setGroup
argument_list|(
name|newGroup
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|newGroup
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
DECL|method|setGroupWithFalseDoesNotAddsPreviousAssignments ()
specifier|public
name|void
name|setGroupWithFalseDoesNotAddsPreviousAssignments
parameter_list|()
throws|throws
name|Exception
block|{
name|ExplicitGroup
name|oldGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"OldGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|oldGroup
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|node
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|oldGroup
argument_list|)
decl_stmt|;
name|AbstractGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"NewGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|node
operator|.
name|setGroup
argument_list|(
name|newGroup
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|newGroup
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
DECL|method|setGroupAddsOnlyPreviousAssignments ()
specifier|public
name|void
name|setGroupAddsOnlyPreviousAssignments
parameter_list|()
throws|throws
name|Exception
block|{
name|ExplicitGroup
name|oldGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"OldGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|oldGroup
operator|.
name|isMatch
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|node
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|oldGroup
argument_list|)
decl_stmt|;
name|AbstractGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"NewGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|node
operator|.
name|setGroup
argument_list|(
name|newGroup
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|newGroup
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
DECL|method|setGroupExplicitToSearchDoesNotKeepPreviousAssignments ()
specifier|public
name|void
name|setGroupExplicitToSearchDoesNotKeepPreviousAssignments
parameter_list|()
throws|throws
name|Exception
block|{
name|ExplicitGroup
name|oldGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"OldGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|oldGroup
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|node
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|oldGroup
argument_list|)
decl_stmt|;
name|AbstractGroup
name|newGroup
init|=
operator|new
name|SearchGroup
argument_list|(
literal|"NewGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"test"
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|node
operator|.
name|setGroup
argument_list|(
name|newGroup
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|newGroup
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
DECL|method|setGroupExplicitToExplicitIsRenameAndSoRemovesPreviousAssignment ()
specifier|public
name|void
name|setGroupExplicitToExplicitIsRenameAndSoRemovesPreviousAssignment
parameter_list|()
throws|throws
name|Exception
block|{
name|ExplicitGroup
name|oldGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"OldGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|oldGroup
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|GroupTreeNode
name|node
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|oldGroup
argument_list|)
decl_stmt|;
name|AbstractGroup
name|newGroup
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"NewGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|node
operator|.
name|setGroup
argument_list|(
name|newGroup
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|entries
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|oldGroup
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

