begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|core
operator|.
name|GenericTypeMatcher
import|;
end_import

begin_import
import|import
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|dependency
operator|.
name|jsr305
operator|.
name|Nonnull
import|;
end_import

begin_import
import|import
name|org
operator|.
name|assertj
operator|.
name|swing
operator|.
name|fixture
operator|.
name|JTableFixture
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
name|Tag
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
name|assertj
operator|.
name|swing
operator|.
name|finder
operator|.
name|WindowFinder
operator|.
name|findDialog
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
name|assertFalse
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
name|assertTrue
import|;
end_import

begin_class
annotation|@
name|Tag
argument_list|(
literal|"GUITest"
argument_list|)
DECL|class|UndoTest
specifier|public
class|class
name|UndoTest
extends|extends
name|AbstractUITest
block|{
annotation|@
name|Test
DECL|method|undoCutOfMultipleEntries ()
specifier|public
name|void
name|undoCutOfMultipleEntries
parameter_list|()
block|{
name|importBibIntoNewDatabase
argument_list|(
name|getAbsolutePath
argument_list|(
literal|"testbib/testjabref.bib"
argument_list|)
argument_list|)
expr_stmt|;
name|JTableFixture
name|entryTable
init|=
name|mainFrame
operator|.
name|table
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|entryTable
operator|.
name|rowCount
argument_list|()
operator|>=
literal|2
argument_list|,
literal|"The database must have at least 2 entries for the test to begin!"
argument_list|)
expr_stmt|;
name|entryTable
operator|.
name|selectRows
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|entryTable
operator|.
name|requireSelectedRows
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|int
name|oldRowCount
init|=
name|entryTable
operator|.
name|rowCount
argument_list|()
decl_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Cut"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Undo"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
name|oldRowCount
argument_list|)
expr_stmt|;
name|closeDatabase
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|undoRedoUpdatedCorrectly ()
specifier|public
name|void
name|undoRedoUpdatedCorrectly
parameter_list|()
block|{
name|newDatabase
argument_list|()
expr_stmt|;
name|assertFalse
argument_list|(
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Undo"
argument_list|)
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Redo"
argument_list|)
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|JTableFixture
name|entryTable
init|=
name|mainFrame
operator|.
name|table
argument_list|()
decl_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"BibTeX"
argument_list|,
literal|"New entry..."
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|findDialog
argument_list|(
name|EntryTypeDialog
operator|.
name|class
argument_list|)
operator|.
name|withTimeout
argument_list|(
literal|10_000
argument_list|)
operator|.
name|using
argument_list|(
name|robot
argument_list|()
argument_list|)
operator|.
name|button
argument_list|(
operator|new
name|GenericTypeMatcher
argument_list|<
name|JButton
argument_list|>
argument_list|(
name|JButton
operator|.
name|class
argument_list|)
block|{
annotation|@
name|Override
specifier|protected
name|boolean
name|isMatching
parameter_list|(
annotation|@
name|Nonnull
name|JButton
name|jButton
parameter_list|)
block|{
return|return
literal|"Book"
operator|.
name|equals
argument_list|(
name|jButton
operator|.
name|getText
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Undo"
argument_list|)
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Redo"
argument_list|)
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Undo"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|assertFalse
argument_list|(
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Undo"
argument_list|)
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"Edit"
argument_list|,
literal|"Redo"
argument_list|)
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|entryTable
operator|.
name|requireRowCount
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|closeDatabase
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

