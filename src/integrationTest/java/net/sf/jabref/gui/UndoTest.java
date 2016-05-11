begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
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
name|JabRefMain
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
name|FrameFixture
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
name|JFileChooserFixture
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
name|assertj
operator|.
name|swing
operator|.
name|junit
operator|.
name|testcase
operator|.
name|AssertJSwingJUnitTestCase
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
name|assertj
operator|.
name|swing
operator|.
name|finder
operator|.
name|WindowFinder
operator|.
name|findFrame
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
name|launcher
operator|.
name|ApplicationLauncher
operator|.
name|application
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
DECL|class|UndoTest
specifier|public
class|class
name|UndoTest
extends|extends
name|AssertJSwingJUnitTestCase
block|{
DECL|field|awtExceptionHandler
specifier|private
name|AWTExceptionHandler
name|awtExceptionHandler
decl_stmt|;
annotation|@
name|Override
DECL|method|onSetUp ()
specifier|protected
name|void
name|onSetUp
parameter_list|()
block|{
name|awtExceptionHandler
operator|=
operator|new
name|AWTExceptionHandler
argument_list|()
expr_stmt|;
name|awtExceptionHandler
operator|.
name|installExceptionDetectionInEDT
argument_list|()
expr_stmt|;
name|application
argument_list|(
name|JabRefMain
operator|.
name|class
argument_list|)
operator|.
name|start
argument_list|()
expr_stmt|;
name|robot
argument_list|()
operator|.
name|waitForIdle
argument_list|()
expr_stmt|;
name|robot
argument_list|()
operator|.
name|settings
argument_list|()
operator|.
name|timeoutToFindSubMenu
argument_list|(
literal|1_000
argument_list|)
expr_stmt|;
name|robot
argument_list|()
operator|.
name|settings
argument_list|()
operator|.
name|delayBetweenEvents
argument_list|(
literal|50
argument_list|)
expr_stmt|;
block|}
DECL|method|exitJabRef (FrameFixture mainFrame)
specifier|private
name|void
name|exitJabRef
parameter_list|(
name|FrameFixture
name|mainFrame
parameter_list|)
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"Quit"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|awtExceptionHandler
operator|.
name|assertNoExceptions
argument_list|()
expr_stmt|;
block|}
DECL|method|getTestFilePath (String fileName)
specifier|private
name|String
name|getTestFilePath
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
return|return
operator|new
name|File
argument_list|(
name|this
operator|.
name|getClass
argument_list|()
operator|.
name|getClassLoader
argument_list|()
operator|.
name|getResource
argument_list|(
name|fileName
argument_list|)
operator|.
name|getFile
argument_list|()
argument_list|)
operator|.
name|getAbsolutePath
argument_list|()
return|;
block|}
DECL|method|importBibIntoNewDatabase (FrameFixture mainFrame, String path)
specifier|private
name|void
name|importBibIntoNewDatabase
parameter_list|(
name|FrameFixture
name|mainFrame
parameter_list|,
name|String
name|path
parameter_list|)
block|{
comment|// have to replace backslashes with normal slashes b/c assertJ can't type the former one on windows
name|path
operator|=
name|path
operator|.
name|replace
argument_list|(
literal|"\\"
argument_list|,
literal|"/"
argument_list|)
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"Import into new database"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|JFileChooserFixture
name|openFileDialog
init|=
name|mainFrame
operator|.
name|fileChooser
argument_list|()
decl_stmt|;
name|robot
argument_list|()
operator|.
name|settings
argument_list|()
operator|.
name|delayBetweenEvents
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|openFileDialog
operator|.
name|fileNameTextBox
argument_list|()
operator|.
name|enterText
argument_list|(
name|path
argument_list|)
expr_stmt|;
name|robot
argument_list|()
operator|.
name|settings
argument_list|()
operator|.
name|delayBetweenEvents
argument_list|(
literal|1_000
argument_list|)
expr_stmt|;
name|openFileDialog
operator|.
name|approve
argument_list|()
expr_stmt|;
name|robot
argument_list|()
operator|.
name|settings
argument_list|()
operator|.
name|delayBetweenEvents
argument_list|(
literal|50
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|undoCutOfMultipleEntries ()
specifier|public
name|void
name|undoCutOfMultipleEntries
parameter_list|()
block|{
name|FrameFixture
name|mainFrame
init|=
name|findFrame
argument_list|(
name|JabRefFrame
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
decl_stmt|;
name|importBibIntoNewDatabase
argument_list|(
name|mainFrame
argument_list|,
name|getTestFilePath
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
literal|"The database must have at least 2 entries for the test to begin!"
argument_list|,
name|entryTable
operator|.
name|rowCount
argument_list|()
operator|>=
literal|2
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
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"Close database"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"Close database"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|exitJabRef
argument_list|(
name|mainFrame
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

