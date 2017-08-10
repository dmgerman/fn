begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.exporter
package|package
name|org
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
name|javafx
operator|.
name|scene
operator|.
name|paint
operator|.
name|Color
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
name|groups
operator|.
name|AllEntriesGroup
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
name|groups
operator|.
name|AutomaticGroup
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
name|groups
operator|.
name|AutomaticKeywordGroup
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
name|groups
operator|.
name|AutomaticPersonsGroup
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
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|GroupHierarchyType
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
name|groups
operator|.
name|GroupTreeNode
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
name|groups
operator|.
name|GroupTreeNodeTest
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
name|groups
operator|.
name|KeywordGroup
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
name|groups
operator|.
name|RegexKeywordGroup
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
name|groups
operator|.
name|SearchGroup
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
name|groups
operator|.
name|WordKeywordGroup
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
literal|"0 StaticGroup:myExplicitGroup;0;1;;;;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleExplicitGroupWithIconAndDescription ()
specifier|public
name|void
name|serializeSingleExplicitGroupWithIconAndDescription
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
name|group
operator|.
name|setIconName
argument_list|(
literal|"test icon"
argument_list|)
expr_stmt|;
name|group
operator|.
name|setExpanded
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|group
operator|.
name|setColor
argument_list|(
name|Color
operator|.
name|ALICEBLUE
argument_list|)
expr_stmt|;
name|group
operator|.
name|setDescription
argument_list|(
literal|"test description"
argument_list|)
expr_stmt|;
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
literal|"0 StaticGroup:myExplicitGroup;0;1;0xf0f8ffff;test icon;test description;"
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
literal|"0 StaticGroup:B{\\\\\"{o}}hmer;0;1;;;;"
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
name|WordKeywordGroup
name|group
init|=
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
literal|"0 KeywordGroup:name;0;keywords;test;0;0;1;;;;"
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
literal|"0 KeywordGroup:myExplicitGroup;1;author;asdf;0;1;1;;;;"
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
literal|"0 SearchGroup:myExplicitGroup;0;author=harrer;1;1;1;;;;"
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
literal|"0 SearchGroup:myExplicitGroup;2;author=\"harrer\";1;0;1;;;;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleAutomaticKeywordGroup ()
specifier|public
name|void
name|serializeSingleAutomaticKeywordGroup
parameter_list|()
block|{
name|AutomaticGroup
name|group
init|=
operator|new
name|AutomaticKeywordGroup
argument_list|(
literal|"myAutomaticGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"keywords"
argument_list|,
literal|','
argument_list|,
literal|'>'
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
literal|"0 AutomaticKeywordGroup:myAutomaticGroup;0;keywords;,;>;1;;;;"
argument_list|)
argument_list|,
name|serialization
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|serializeSingleAutomaticPersonGroup ()
specifier|public
name|void
name|serializeSingleAutomaticPersonGroup
parameter_list|()
block|{
name|AutomaticPersonsGroup
name|group
init|=
operator|new
name|AutomaticPersonsGroup
argument_list|(
literal|"myAutomaticGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|"authors"
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
literal|"0 AutomaticPersonsGroup:myAutomaticGroup;0;authors;1;;;;"
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
literal|"1 StaticGroup:ExplicitA;2;1;;;;"
argument_list|,
literal|"1 StaticGroup:ExplicitParent;0;1;;;;"
argument_list|,
literal|"2 StaticGroup:ExplicitNode;1;1;;;;"
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
literal|"1 SearchGroup:SearchA;2;searchExpression;1;0;1;;;;"
argument_list|,
literal|"1 StaticGroup:ExplicitA;2;1;;;;"
argument_list|,
literal|"1 StaticGroup:ExplicitGrandParent;0;1;;;;"
argument_list|,
literal|"2 StaticGroup:ExplicitB;1;1;;;;"
argument_list|,
literal|"2 KeywordGroup:KeywordParent;0;searchField;searchExpression;1;0;1;;;;"
argument_list|,
literal|"3 KeywordGroup:KeywordNode;0;searchField;searchExpression;1;0;1;;;;"
argument_list|,
literal|"4 StaticGroup:ExplicitChild;1;1;;;;"
argument_list|,
literal|"3 SearchGroup:SearchC;2;searchExpression;1;0;1;;;;"
argument_list|,
literal|"3 StaticGroup:ExplicitC;1;1;;;;"
argument_list|,
literal|"3 KeywordGroup:KeywordC;0;searchField;searchExpression;1;0;1;;;;"
argument_list|,
literal|"2 SearchGroup:SearchB;2;searchExpression;1;0;1;;;;"
argument_list|,
literal|"2 KeywordGroup:KeywordB;0;searchField;searchExpression;1;0;1;;;;"
argument_list|,
literal|"1 KeywordGroup:KeywordA;0;searchField;searchExpression;1;0;1;;;;"
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

