begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.groups
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|allEntriesGroup
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
block|}
end_class

end_unit

