begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|cleanup
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
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListDataListener
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
name|cleanup
operator|.
name|CleanupActionsListModel
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
name|cleanup
operator|.
name|Cleanups
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|ClearFormatter
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|cleanup
operator|.
name|FieldFormatterCleanups
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
name|mockito
operator|.
name|ArgumentCaptor
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

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|verify
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
name|verifyZeroInteractions
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
name|when
import|;
end_import

begin_class
DECL|class|CleanupActionsListModelTest
specifier|public
class|class
name|CleanupActionsListModelTest
block|{
annotation|@
name|Test
DECL|method|resetFiresItemsChanged ()
specifier|public
name|void
name|resetFiresItemsChanged
parameter_list|()
throws|throws
name|Exception
block|{
name|CleanupActionsListModel
name|model
init|=
operator|new
name|CleanupActionsListModel
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
decl_stmt|;
name|ListDataListener
name|listener
init|=
name|mock
argument_list|(
name|ListDataListener
operator|.
name|class
argument_list|)
decl_stmt|;
name|model
operator|.
name|addListDataListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|FieldFormatterCleanups
name|defaultFormatters
init|=
name|mock
argument_list|(
name|FieldFormatterCleanups
operator|.
name|class
argument_list|)
decl_stmt|;
name|model
operator|.
name|reset
argument_list|(
name|defaultFormatters
argument_list|)
expr_stmt|;
name|ArgumentCaptor
argument_list|<
name|ListDataEvent
argument_list|>
name|argument
init|=
name|ArgumentCaptor
operator|.
name|forClass
argument_list|(
name|ListDataEvent
operator|.
name|class
argument_list|)
decl_stmt|;
name|verify
argument_list|(
name|listener
argument_list|)
operator|.
name|contentsChanged
argument_list|(
name|argument
operator|.
name|capture
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ListDataEvent
operator|.
name|CONTENTS_CHANGED
argument_list|,
name|argument
operator|.
name|getValue
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|resetSetsFormattersToPassedList ()
specifier|public
name|void
name|resetSetsFormattersToPassedList
parameter_list|()
throws|throws
name|Exception
block|{
name|CleanupActionsListModel
name|model
init|=
operator|new
name|CleanupActionsListModel
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|)
decl_stmt|;
name|FieldFormatterCleanups
name|defaultFormatters
init|=
name|mock
argument_list|(
name|FieldFormatterCleanups
operator|.
name|class
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|formatters
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"test"
argument_list|,
operator|new
name|ClearFormatter
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|when
argument_list|(
name|defaultFormatters
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|formatters
argument_list|)
expr_stmt|;
name|model
operator|.
name|reset
argument_list|(
name|defaultFormatters
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|formatters
argument_list|,
name|model
operator|.
name|getAllActions
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getDefaultFieldFormatterCleanups ()
specifier|public
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|getDefaultFieldFormatterCleanups
parameter_list|()
block|{
name|FieldFormatterCleanups
name|formatters
init|=
name|Cleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
decl_stmt|;
comment|//new ArrayList because configured actions is an unmodifiable collection
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|formatters
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Test
DECL|method|removedAtIndexOkay ()
specifier|public
name|void
name|removedAtIndexOkay
parameter_list|()
block|{
name|CleanupActionsListModel
name|model
init|=
operator|new
name|CleanupActionsListModel
argument_list|(
name|getDefaultFieldFormatterCleanups
argument_list|()
argument_list|)
decl_stmt|;
name|ListDataListener
name|listener
init|=
name|mock
argument_list|(
name|ListDataListener
operator|.
name|class
argument_list|)
decl_stmt|;
name|model
operator|.
name|addListDataListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|model
operator|.
name|removeAtIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|ArgumentCaptor
argument_list|<
name|ListDataEvent
argument_list|>
name|argument
init|=
name|ArgumentCaptor
operator|.
name|forClass
argument_list|(
name|ListDataEvent
operator|.
name|class
argument_list|)
decl_stmt|;
name|verify
argument_list|(
name|listener
argument_list|)
operator|.
name|intervalRemoved
argument_list|(
name|argument
operator|.
name|capture
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|ListDataEvent
operator|.
name|INTERVAL_REMOVED
argument_list|,
name|argument
operator|.
name|getValue
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removedAtIndexMinus1DoesNothing ()
specifier|public
name|void
name|removedAtIndexMinus1DoesNothing
parameter_list|()
block|{
name|CleanupActionsListModel
name|model
init|=
operator|new
name|CleanupActionsListModel
argument_list|(
name|getDefaultFieldFormatterCleanups
argument_list|()
argument_list|)
decl_stmt|;
name|ListDataListener
name|listener
init|=
name|mock
argument_list|(
name|ListDataListener
operator|.
name|class
argument_list|)
decl_stmt|;
name|model
operator|.
name|addListDataListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|model
operator|.
name|removeAtIndex
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
name|verifyZeroInteractions
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|removedAtIndexgreaterListSizeDoesNothing ()
specifier|public
name|void
name|removedAtIndexgreaterListSizeDoesNothing
parameter_list|()
block|{
name|CleanupActionsListModel
name|model
init|=
operator|new
name|CleanupActionsListModel
argument_list|(
name|getDefaultFieldFormatterCleanups
argument_list|()
argument_list|)
decl_stmt|;
name|ListDataListener
name|listener
init|=
name|mock
argument_list|(
name|ListDataListener
operator|.
name|class
argument_list|)
decl_stmt|;
name|model
operator|.
name|addListDataListener
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|model
operator|.
name|removeAtIndex
argument_list|(
operator|(
name|getDefaultFieldFormatterCleanups
argument_list|()
operator|.
name|size
argument_list|()
operator|+
literal|1
operator|)
argument_list|)
expr_stmt|;
name|verifyZeroInteractions
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

