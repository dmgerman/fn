begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|actions
package|;
end_package

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
name|Optional
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|BasePanel
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ParserResult
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
name|testutils
operator|.
name|category
operator|.
name|GUITests
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
import|import
name|org
operator|.
name|junit
operator|.
name|experimental
operator|.
name|categories
operator|.
name|Category
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|Mock
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
annotation|@
name|Category
argument_list|(
name|GUITests
operator|.
name|class
argument_list|)
DECL|class|ConvertLegacyExplicitGroupsTest
specifier|public
class|class
name|ConvertLegacyExplicitGroupsTest
block|{
DECL|field|action
specifier|private
name|ConvertLegacyExplicitGroups
name|action
decl_stmt|;
DECL|field|basePanel
annotation|@
name|Mock
specifier|private
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|entry
specifier|private
name|BibEntry
name|entry
decl_stmt|;
DECL|field|group
specifier|private
name|ExplicitGroup
name|group
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
name|action
operator|=
operator|new
name|ConvertLegacyExplicitGroups
argument_list|()
expr_stmt|;
name|entry
operator|=
operator|new
name|BibEntry
argument_list|()
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"Entry1"
argument_list|)
expr_stmt|;
name|group
operator|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"TestGroup"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|','
argument_list|)
expr_stmt|;
name|group
operator|.
name|addLegacyEntryKey
argument_list|(
literal|"Entry1"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|performActionWritesGroupMembershipInEntry ()
specifier|public
name|void
name|performActionWritesGroupMembershipInEntry
parameter_list|()
throws|throws
name|Exception
block|{
name|ParserResult
name|parserResult
init|=
name|generateParserResult
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|action
operator|.
name|performAction
argument_list|(
name|basePanel
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"TestGroup"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"groups"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|performActionClearsLegacyKeys ()
specifier|public
name|void
name|performActionClearsLegacyKeys
parameter_list|()
throws|throws
name|Exception
block|{
name|ParserResult
name|parserResult
init|=
name|generateParserResult
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|action
operator|.
name|performAction
argument_list|(
name|basePanel
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|group
operator|.
name|getLegacyEntryKeys
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|performActionWritesGroupMembershipInEntryForComplexGroupTree ()
specifier|public
name|void
name|performActionWritesGroupMembershipInEntryForComplexGroupTree
parameter_list|()
throws|throws
name|Exception
block|{
name|GroupTreeNode
name|root
init|=
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
operator|new
name|AllEntriesGroup
argument_list|(
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|root
operator|.
name|addSubgroup
argument_list|(
operator|new
name|ExplicitGroup
argument_list|(
literal|"TestGroup2"
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
literal|','
argument_list|)
argument_list|)
expr_stmt|;
name|root
operator|.
name|addSubgroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|ParserResult
name|parserResult
init|=
name|generateParserResult
argument_list|(
name|root
argument_list|)
decl_stmt|;
name|action
operator|.
name|performAction
argument_list|(
name|basePanel
argument_list|,
name|parserResult
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"TestGroup"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"groups"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isActionNecessaryReturnsTrueIfGroupContainsLegacyKeys ()
specifier|public
name|void
name|isActionNecessaryReturnsTrueIfGroupContainsLegacyKeys
parameter_list|()
throws|throws
name|Exception
block|{
name|ParserResult
name|parserResult
init|=
name|generateParserResult
argument_list|(
name|GroupTreeNode
operator|.
name|fromGroup
argument_list|(
name|group
argument_list|)
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|action
operator|.
name|isActionNecessary
argument_list|(
name|parserResult
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|generateParserResult (GroupTreeNode groupRoot)
specifier|private
name|ParserResult
name|generateParserResult
parameter_list|(
name|GroupTreeNode
name|groupRoot
parameter_list|)
block|{
name|ParserResult
name|parserResult
init|=
operator|new
name|ParserResult
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|entry
argument_list|)
argument_list|)
decl_stmt|;
name|parserResult
operator|.
name|getMetaData
argument_list|()
operator|.
name|setGroups
argument_list|(
name|groupRoot
argument_list|)
expr_stmt|;
return|return
name|parserResult
return|;
block|}
block|}
end_class

end_unit

