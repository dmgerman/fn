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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URISyntaxException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|AbstractWindowFixture
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
name|image
operator|.
name|ScreenshotTaker
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
name|assertj
operator|.
name|swing
operator|.
name|timing
operator|.
name|Pause
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Assert
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

begin_class
DECL|class|AbstractUITest
specifier|public
specifier|abstract
class|class
name|AbstractUITest
extends|extends
name|AssertJSwingJUnitTestCase
block|{
DECL|field|SPEED_NORMAL
specifier|protected
specifier|final
specifier|static
name|int
name|SPEED_NORMAL
init|=
literal|50
decl_stmt|;
DECL|field|awtExceptionHandler
specifier|protected
name|AWTExceptionHandler
name|awtExceptionHandler
decl_stmt|;
DECL|field|mainFrame
specifier|protected
name|FrameFixture
name|mainFrame
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
name|SPEED_NORMAL
argument_list|)
expr_stmt|;
name|mainFrame
operator|=
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
expr_stmt|;
name|robot
argument_list|()
operator|.
name|waitForIdle
argument_list|()
expr_stmt|;
block|}
comment|/**      * Returns the absolute Path of the given relative Path      * The backlashes are replaced with forwardslashes b/c assertJ can't type the former one on windows      * @param relativePath the relative path to the resource database      */
DECL|method|getAbsolutePath (String relativePath)
specifier|protected
name|String
name|getAbsolutePath
parameter_list|(
name|String
name|relativePath
parameter_list|)
block|{
specifier|final
name|URL
name|resource
init|=
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
name|relativePath
argument_list|)
decl_stmt|;
try|try
block|{
return|return
name|Paths
operator|.
name|get
argument_list|(
name|resource
operator|.
name|toURI
argument_list|()
argument_list|)
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\\"
argument_list|,
literal|"/"
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|URISyntaxException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
comment|/**      * opens a database and gives JabRef a second to open it before proceeding      */
DECL|method|importBibIntoNewDatabase (String path)
specifier|protected
name|void
name|importBibIntoNewDatabase
parameter_list|(
name|String
name|path
parameter_list|)
block|{
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
name|openFileDialog
operator|.
name|approve
argument_list|()
expr_stmt|;
name|Pause
operator|.
name|pause
argument_list|(
literal|1_000
argument_list|)
expr_stmt|;
block|}
DECL|method|exitJabRef ()
specifier|protected
name|void
name|exitJabRef
parameter_list|()
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
DECL|method|newDatabase ()
specifier|protected
name|void
name|newDatabase
parameter_list|()
block|{
name|mainFrame
operator|.
name|menuItemWithPath
argument_list|(
literal|"File"
argument_list|,
literal|"New BibTeX database"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
block|}
DECL|method|closeDatabase ()
specifier|protected
name|void
name|closeDatabase
parameter_list|()
block|{
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
block|}
DECL|method|takeScreenshot (AbstractWindowFixture<?, ?, ?> dialog, String filename)
specifier|protected
name|void
name|takeScreenshot
parameter_list|(
name|AbstractWindowFixture
argument_list|<
name|?
argument_list|,
name|?
argument_list|,
name|?
argument_list|>
name|dialog
parameter_list|,
name|String
name|filename
parameter_list|)
throws|throws
name|IOException
block|{
name|ScreenshotTaker
name|screenshotTaker
init|=
operator|new
name|ScreenshotTaker
argument_list|()
decl_stmt|;
name|Path
name|folder
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"build"
argument_list|,
literal|"screenshots"
argument_list|)
decl_stmt|;
comment|// Create build/srceenshots folder if not present
if|if
condition|(
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|folder
argument_list|)
condition|)
block|{
name|Files
operator|.
name|createDirectory
argument_list|(
name|folder
argument_list|)
expr_stmt|;
block|}
name|Path
name|file
init|=
name|folder
operator|.
name|resolve
argument_list|(
name|filename
operator|+
literal|".png"
argument_list|)
operator|.
name|toAbsolutePath
argument_list|()
decl_stmt|;
comment|// Delete already present file
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|Files
operator|.
name|delete
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
name|screenshotTaker
operator|.
name|saveComponentAsPng
argument_list|(
name|dialog
operator|.
name|target
argument_list|()
argument_list|,
name|file
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|assertColumnValue (JTableFixture table, int rowIndex, int columnIndex, String selectionValue)
specifier|protected
name|void
name|assertColumnValue
parameter_list|(
name|JTableFixture
name|table
parameter_list|,
name|int
name|rowIndex
parameter_list|,
name|int
name|columnIndex
parameter_list|,
name|String
name|selectionValue
parameter_list|)
block|{
name|String
index|[]
index|[]
name|tableContent
decl_stmt|;
name|tableContent
operator|=
name|table
operator|.
name|contents
argument_list|()
expr_stmt|;
name|String
name|value
init|=
name|tableContent
index|[
name|rowIndex
index|]
index|[
name|columnIndex
index|]
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|value
argument_list|,
name|selectionValue
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

