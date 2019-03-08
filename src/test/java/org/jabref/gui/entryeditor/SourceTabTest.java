begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
package|;
end_package

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Scene
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Label
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Tab
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TabPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Stage
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
name|FXDialogService
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
name|undo
operator|.
name|CountingUndoManager
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
name|bibtex
operator|.
name|LatexFieldFormatterPreferences
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
name|ImportFormatPreferences
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
name|util
operator|.
name|DummyFileUpdateMonitor
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
name|GUITest
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|richtext
operator|.
name|CodeArea
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
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|extension
operator|.
name|ExtendWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|testfx
operator|.
name|api
operator|.
name|FxRobot
import|;
end_import

begin_import
import|import
name|org
operator|.
name|testfx
operator|.
name|framework
operator|.
name|junit5
operator|.
name|ApplicationExtension
import|;
end_import

begin_import
import|import
name|org
operator|.
name|testfx
operator|.
name|framework
operator|.
name|junit5
operator|.
name|Start
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
annotation|@
name|GUITest
annotation|@
name|ExtendWith
argument_list|(
name|ApplicationExtension
operator|.
name|class
argument_list|)
DECL|class|SourceTabTest
specifier|public
class|class
name|SourceTabTest
block|{
DECL|field|stage
specifier|private
name|Stage
name|stage
decl_stmt|;
DECL|field|scene
specifier|private
name|Scene
name|scene
decl_stmt|;
DECL|field|area
specifier|private
name|CodeArea
name|area
decl_stmt|;
DECL|field|pane
specifier|private
name|TabPane
name|pane
decl_stmt|;
DECL|field|sourceTab
specifier|private
name|SourceTab
name|sourceTab
decl_stmt|;
annotation|@
name|Start
DECL|method|onStart (Stage stage)
specifier|public
name|void
name|onStart
parameter_list|(
name|Stage
name|stage
parameter_list|)
block|{
name|area
operator|=
operator|new
name|CodeArea
argument_list|()
expr_stmt|;
name|area
operator|.
name|appendText
argument_list|(
literal|"some example\n text to go here\n across a couple of \n lines...."
argument_list|)
expr_stmt|;
name|sourceTab
operator|=
operator|new
name|SourceTab
argument_list|(
operator|new
name|BibDatabaseContext
argument_list|()
argument_list|,
operator|new
name|CountingUndoManager
argument_list|()
argument_list|,
operator|new
name|LatexFieldFormatterPreferences
argument_list|()
argument_list|,
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|)
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|,
operator|new
name|FXDialogService
argument_list|()
argument_list|)
expr_stmt|;
name|pane
operator|=
operator|new
name|TabPane
argument_list|(
operator|new
name|Tab
argument_list|(
literal|"main area"
argument_list|,
name|area
argument_list|)
argument_list|,
operator|new
name|Tab
argument_list|(
literal|"other tab"
argument_list|,
operator|new
name|Label
argument_list|(
literal|"some text"
argument_list|)
argument_list|)
argument_list|,
name|sourceTab
argument_list|)
expr_stmt|;
name|scene
operator|=
operator|new
name|Scene
argument_list|(
name|pane
argument_list|)
expr_stmt|;
name|this
operator|.
name|stage
operator|=
name|stage
expr_stmt|;
name|stage
operator|.
name|setScene
argument_list|(
name|scene
argument_list|)
expr_stmt|;
name|stage
operator|.
name|setWidth
argument_list|(
literal|400
argument_list|)
expr_stmt|;
name|stage
operator|.
name|setHeight
argument_list|(
literal|400
argument_list|)
expr_stmt|;
name|stage
operator|.
name|show
argument_list|()
expr_stmt|;
comment|// select the area's tab
name|pane
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|switchingFromSourceTabDoesNotThrowException (FxRobot robot)
name|void
name|switchingFromSourceTabDoesNotThrowException
parameter_list|(
name|FxRobot
name|robot
parameter_list|)
throws|throws
name|Exception
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"test"
argument_list|,
literal|"testvalue"
argument_list|)
expr_stmt|;
comment|// Update source editor
name|robot
operator|.
name|interact
argument_list|(
parameter_list|()
lambda|->
name|pane
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|robot
operator|.
name|interact
argument_list|(
parameter_list|()
lambda|->
name|sourceTab
operator|.
name|bindToEntry
argument_list|(
name|entry
argument_list|)
argument_list|)
expr_stmt|;
name|robot
operator|.
name|clickOn
argument_list|(
literal|1200
argument_list|,
literal|500
argument_list|)
expr_stmt|;
name|robot
operator|.
name|interrupt
argument_list|(
literal|100
argument_list|)
expr_stmt|;
comment|// Switch to different tab& update entry
name|robot
operator|.
name|interact
argument_list|(
parameter_list|()
lambda|->
name|pane
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|select
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|robot
operator|.
name|interact
argument_list|(
parameter_list|()
lambda|->
name|stage
operator|.
name|setWidth
argument_list|(
literal|600
argument_list|)
argument_list|)
expr_stmt|;
name|robot
operator|.
name|interact
argument_list|(
parameter_list|()
lambda|->
name|entry
operator|.
name|setField
argument_list|(
literal|"test"
argument_list|,
literal|"new value"
argument_list|)
argument_list|)
expr_stmt|;
comment|// No exception should be thrown
name|robot
operator|.
name|interrupt
argument_list|(
literal|100
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

