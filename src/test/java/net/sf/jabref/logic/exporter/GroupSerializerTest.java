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
operator|new
name|GroupSerializer
argument_list|()
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
operator|new
name|GroupSerializer
argument_list|()
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

