begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.exporter
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|exporter
package|;
end_package

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
name|JabRefFrame
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
name|actions
operator|.
name|Actions
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
name|l10n
operator|.
name|Localization
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
name|mockito
operator|.
name|Mockito
operator|.
name|anyString
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
name|doNothing
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
name|times
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
DECL|class|SaveAllActionTest
specifier|public
class|class
name|SaveAllActionTest
block|{
DECL|field|firstPanel
specifier|private
name|BasePanel
name|firstPanel
init|=
name|mock
argument_list|(
name|BasePanel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|secondPanel
specifier|private
name|BasePanel
name|secondPanel
init|=
name|mock
argument_list|(
name|BasePanel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|jabRefFrame
specifier|private
name|JabRefFrame
name|jabRefFrame
init|=
name|mock
argument_list|(
name|JabRefFrame
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|dialogService
specifier|private
name|DialogService
name|dialogService
init|=
name|mock
argument_list|(
name|DialogService
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|bibDatabaseContext
specifier|private
name|BibDatabaseContext
name|bibDatabaseContext
init|=
name|mock
argument_list|(
name|BibDatabaseContext
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|databasePath
specifier|private
name|Optional
argument_list|<
name|Path
argument_list|>
name|databasePath
init|=
name|Optional
operator|.
name|of
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
literal|"C:\\Users\\John_Doe\\Jabref"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|saveAllAction
specifier|private
name|SaveAllAction
name|saveAllAction
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|when
argument_list|(
name|firstPanel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|secondPanel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|bibDatabaseContext
operator|.
name|getDatabasePath
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|databasePath
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|jabRefFrame
operator|.
name|getBasePanelList
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|firstPanel
argument_list|,
name|secondPanel
argument_list|)
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|jabRefFrame
operator|.
name|getDialogService
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|dialogService
argument_list|)
expr_stmt|;
name|saveAllAction
operator|=
operator|new
name|SaveAllAction
argument_list|(
name|jabRefFrame
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|executeShouldRunSaveCommandInEveryPanel ()
specifier|public
name|void
name|executeShouldRunSaveCommandInEveryPanel
parameter_list|()
block|{
name|doNothing
argument_list|()
operator|.
name|when
argument_list|(
name|dialogService
argument_list|)
operator|.
name|notify
argument_list|(
name|anyString
argument_list|()
argument_list|)
expr_stmt|;
name|saveAllAction
operator|.
name|execute
argument_list|()
expr_stmt|;
name|verify
argument_list|(
name|firstPanel
argument_list|,
name|times
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|SAVE
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|secondPanel
argument_list|,
name|times
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|SAVE
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|executeShouldNotifyAboutSavingProcess ()
specifier|public
name|void
name|executeShouldNotifyAboutSavingProcess
parameter_list|()
block|{
name|when
argument_list|(
name|bibDatabaseContext
operator|.
name|getDatabasePath
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|databasePath
argument_list|)
expr_stmt|;
name|saveAllAction
operator|.
name|execute
argument_list|()
expr_stmt|;
name|verify
argument_list|(
name|dialogService
argument_list|,
name|times
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Saving all libraries..."
argument_list|)
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|dialogService
argument_list|,
name|times
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save all finished."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|executeShouldShowSaveAsWindowIfDatabaseNotSelected ()
specifier|public
name|void
name|executeShouldShowSaveAsWindowIfDatabaseNotSelected
parameter_list|()
block|{
name|when
argument_list|(
name|bibDatabaseContext
operator|.
name|getDatabasePath
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
expr_stmt|;
name|doNothing
argument_list|()
operator|.
name|when
argument_list|(
name|dialogService
argument_list|)
operator|.
name|notify
argument_list|(
name|anyString
argument_list|()
argument_list|)
expr_stmt|;
name|saveAllAction
operator|.
name|execute
argument_list|()
expr_stmt|;
name|verify
argument_list|(
name|firstPanel
argument_list|,
name|times
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|SAVE_AS
argument_list|)
expr_stmt|;
name|verify
argument_list|(
name|secondPanel
argument_list|,
name|times
argument_list|(
literal|1
argument_list|)
argument_list|)
operator|.
name|runCommand
argument_list|(
name|Actions
operator|.
name|SAVE_AS
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

