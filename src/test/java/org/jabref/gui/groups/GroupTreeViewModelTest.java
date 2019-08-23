begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.groups
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|DialogService
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
name|StateManager
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
name|util
operator|.
name|CurrentThreadTaskExecutor
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
name|util
operator|.
name|CustomLocalDragboard
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
name|util
operator|.
name|TaskExecutor
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
name|database
operator|.
name|BibDatabaseContext
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
name|InternalField
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
name|WordKeywordGroup
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
name|BeforeEach
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

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_class
DECL|class|GroupTreeViewModelTest
specifier|public
class|class
name|GroupTreeViewModelTest
block|{
DECL|field|stateManager
name|StateManager
name|stateManager
decl_stmt|;
DECL|field|groupTree
name|GroupTreeViewModel
name|groupTree
decl_stmt|;
DECL|field|databaseContext
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|taskExecutor
specifier|private
name|TaskExecutor
name|taskExecutor
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|databaseContext
operator|=
operator|new
name|BibDatabaseContext
argument_list|()
expr_stmt|;
name|stateManager
operator|=
operator|new
name|StateManager
argument_list|()
expr_stmt|;
name|stateManager
operator|.
name|activeDatabaseProperty
argument_list|()
operator|.
name|setValue
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|databaseContext
argument_list|)
argument_list|)
expr_stmt|;
name|taskExecutor
operator|=
operator|new
name|CurrentThreadTaskExecutor
argument_list|()
expr_stmt|;
name|groupTree
operator|=
operator|new
name|GroupTreeViewModel
argument_list|(
name|stateManager
argument_list|,
name|mock
argument_list|(
name|DialogService
operator|.
name|class
argument_list|)
argument_list|,
name|taskExecutor
argument_list|,
operator|new
name|CustomLocalDragboard
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rootGroupIsAllEntriesByDefault ()
specifier|public
name|void
name|rootGroupIsAllEntriesByDefault
parameter_list|()
throws|throws
name|Exception
block|{
name|AllEntriesGroup
name|allEntriesGroup
init|=
operator|new
name|AllEntriesGroup
argument_list|(
literal|"All entries"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
operator|new
name|GroupNodeViewModel
argument_list|(
name|databaseContext
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|allEntriesGroup
argument_list|,
operator|new
name|CustomLocalDragboard
argument_list|()
argument_list|)
argument_list|,
name|groupTree
operator|.
name|rootGroupProperty
argument_list|()
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|explicitGroupsAreRemovedFromEntriesOnDelete ()
specifier|public
name|void
name|explicitGroupsAreRemovedFromEntriesOnDelete
parameter_list|()
block|{
name|ExplicitGroup
name|group
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"group"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
literal|','
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|databaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|GroupNodeViewModel
name|model
init|=
operator|new
name|GroupNodeViewModel
argument_list|(
name|databaseContext
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|group
argument_list|,
operator|new
name|CustomLocalDragboard
argument_list|()
argument_list|)
decl_stmt|;
name|model
operator|.
name|addEntriesToGroup
argument_list|(
name|databaseContext
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
name|groupTree
operator|.
name|removeGroupsAndSubGroupsFromEntries
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|InternalField
operator|.
name|GROUPS
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|keywordGroupsAreNotRemovedFromEntriesOnDelete ()
specifier|public
name|void
name|keywordGroupsAreNotRemovedFromEntriesOnDelete
parameter_list|()
block|{
name|String
name|groupName
init|=
literal|"A"
decl_stmt|;
name|WordKeywordGroup
name|group
init|=
operator|new
name|WordKeywordGroup
argument_list|(
name|groupName
argument_list|,
name|GroupHierarchyType
operator|.
name|INCLUDING
argument_list|,
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
name|groupName
argument_list|,
literal|true
argument_list|,
literal|','
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|databaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|GroupNodeViewModel
name|model
init|=
operator|new
name|GroupNodeViewModel
argument_list|(
name|databaseContext
argument_list|,
name|stateManager
argument_list|,
name|taskExecutor
argument_list|,
name|group
argument_list|,
operator|new
name|CustomLocalDragboard
argument_list|()
argument_list|)
decl_stmt|;
name|model
operator|.
name|addEntriesToGroup
argument_list|(
name|databaseContext
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
name|groupTree
operator|.
name|removeGroupsAndSubGroupsFromEntries
argument_list|(
name|model
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|groupName
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

