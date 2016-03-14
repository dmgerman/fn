begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.dbproperties
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|dbproperties
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|FieldFormatterCleanups
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
name|logic
operator|.
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|EraseFormatter
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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|*
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
name|when
import|;
end_import

begin_class
DECL|class|SaveActionsListModelTest
specifier|public
class|class
name|SaveActionsListModelTest
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
name|SaveActionsListModel
name|model
init|=
operator|new
name|SaveActionsListModel
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
name|SaveActionsListModel
name|model
init|=
operator|new
name|SaveActionsListModel
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
name|EraseFormatter
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
block|}
end_class

end_unit
