begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|exporter
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|groups
operator|.
name|AllEntriesGroup
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
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|GroupHierarchyType
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
name|groups
operator|.
name|GroupTreeNode
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
name|groups
operator|.
name|GroupTreeNodeTest
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
name|groups
operator|.
name|KeywordGroup
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
name|groups
operator|.
name|RegexKeywordGroup
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
name|groups
operator|.
name|SearchGroup
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
name|groups
operator|.
name|SimpleKeywordGroup
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

begin_class
DECL|class|GroupSerializerTest
specifier|public
class|class
name|GroupSerializerTest
block|{
DECL|field|groupSerializer
specifier|private
name|GroupSerializer
name|groupSerializer
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
name|groupSerializer
operator|=
operator|new
name|GroupSerializer
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleAllEntriesGroup ()
specifier|public
name|void
name|serializeSingleAllEntriesGroup
parameter_list|()
block|{
name|AllEntriesGroup
name|group
init|=
operator|new
name|AllEntriesGroup
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|serialization
init|=
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"0 AllEntriesGroup:"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleExplicitGroup ()
specifier|public
name|void
name|serializeSingleExplicitGroup
parameter_list|()
block|{
name|ExplicitGroup
name|group
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|serialization
init|=
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"0 ExplicitGroup:myExplicitGroup;0;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
comment|// For https://github.com/JabRef/jabref/issues/1681
DECL|method|serializeSingleExplicitGroupWithEscapedSlash ()
specifier|public
name|void
name|serializeSingleExplicitGroupWithEscapedSlash
parameter_list|()
block|{
name|ExplicitGroup
name|group
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"B{\\\"{o}}hmer"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|serialization
init|=
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"0 ExplicitGroup:B{\\\\\"{o}}hmer;0;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleSimpleKeywordGroup ()
specifier|public
name|void
name|serializeSingleSimpleKeywordGroup
parameter_list|()
block|{
name|SimpleKeywordGroup
name|group
init|=
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
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|serialization
init|=
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"0 KeywordGroup:name;0;keywords;test;0;0;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleRegexKeywordGroup ()
specifier|public
name|void
name|serializeSingleRegexKeywordGroup
parameter_list|()
block|{
name|KeywordGroup
name|group
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
name|List
argument_list|<
name|String
argument_list|>
name|serialization
init|=
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"0 KeywordGroup:myExplicitGroup;1;author;asdf;0;1;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleSearchGroup ()
specifier|public
name|void
name|serializeSingleSearchGroup
parameter_list|()
block|{
name|SearchGroup
name|group
init|=
operator|new
name|SearchGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"author=harrer"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|serialization
init|=
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"0 SearchGroup:myExplicitGroup;0;author=harrer;1;1;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleSearchGroupWithRegex ()
specifier|public
name|void
name|serializeSingleSearchGroupWithRegex
parameter_list|()
block|{
name|SearchGroup
name|group
init|=
operator|new
name|SearchGroup
argument_list|(
literal|"myExplicitGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|"author=\"harrer\""
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|serialization
init|=
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"0 SearchGroup:myExplicitGroup;2;author=\"harrer\";1;0;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getTreeAsStringInSimpleTree ()
specifier|public
name|void
name|getTreeAsStringInSimpleTree
parameter_list|()
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|root
init|=
name|GroupTreeNodeTest
operator|.
name|getRoot
argument_list|()
decl_stmt|;
name|GroupTreeNodeTest
operator|.
name|getNodeInSimpleTree
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|expected
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"0 AllEntriesGroup:"
argument_list|,
literal|"1 ExplicitGroup:ExplicitA;2;"
argument_list|,
literal|"1 ExplicitGroup:ExplicitParent;0;"
argument_list|,
literal|"2 ExplicitGroup:ExplicitNode;1;"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|root
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getTreeAsStringInComplexTree ()
specifier|public
name|void
name|getTreeAsStringInComplexTree
parameter_list|()
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|root
init|=
name|GroupTreeNodeTest
operator|.
name|getRoot
argument_list|()
decl_stmt|;
name|GroupTreeNodeTest
operator|.
name|getNodeInComplexTree
argument_list|(
name|root
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|expected
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"0 AllEntriesGroup:"
argument_list|,
literal|"1 SearchGroup:SearchA;2;searchExpression;1;0;"
argument_list|,
literal|"1 ExplicitGroup:ExplicitA;2;"
argument_list|,
literal|"1 ExplicitGroup:ExplicitGrandParent;0;"
argument_list|,
literal|"2 ExplicitGroup:ExplicitB;1;"
argument_list|,
literal|"2 KeywordGroup:KeywordParent;0;searchField;searchExpression;1;0;"
argument_list|,
literal|"3 KeywordGroup:KeywordNode;0;searchField;searchExpression;1;0;"
argument_list|,
literal|"4 ExplicitGroup:ExplicitChild;1;"
argument_list|,
literal|"3 SearchGroup:SearchC;2;searchExpression;1;0;"
argument_list|,
literal|"3 ExplicitGroup:ExplicitC;1;"
argument_list|,
literal|"3 KeywordGroup:KeywordC;0;searchField;searchExpression;1;0;"
argument_list|,
literal|"2 SearchGroup:SearchB;2;searchExpression;1;0;"
argument_list|,
literal|"2 KeywordGroup:KeywordB;0;searchField;searchExpression;1;0;"
argument_list|,
literal|"1 KeywordGroup:KeywordA;0;searchField;searchExpression;1;0;"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|groupSerializer
operator|.
name|serializeTree
argument_list|(
name|root
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

