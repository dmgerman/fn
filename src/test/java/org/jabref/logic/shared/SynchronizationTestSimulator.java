begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.shared
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|shared
package|;
end_package

begin_import
import|import
name|java
operator|.
name|sql
operator|.
name|SQLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|shared
operator|.
name|exception
operator|.
name|InvalidDBMSConnectionPropertiesException
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
name|Defaults
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
name|bibtexkeypattern
operator|.
name|GlobalBibtexKeyPattern
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
name|database
operator|.
name|BibDatabaseMode
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
name|shared
operator|.
name|DBMSType
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
name|shared
operator|.
name|DatabaseNotSupportedException
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
name|DatabaseTest
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
name|AfterEach
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
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameters
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
name|assertNotNull
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
name|assertNull
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
name|DatabaseTest
DECL|class|SynchronizationTestSimulator
specifier|public
class|class
name|SynchronizationTestSimulator
block|{
annotation|@
name|Parameter
DECL|field|dbmsType
specifier|public
name|DBMSType
name|dbmsType
decl_stmt|;
DECL|field|clientContextA
specifier|private
name|BibDatabaseContext
name|clientContextA
decl_stmt|;
DECL|field|clientContextB
specifier|private
name|BibDatabaseContext
name|clientContextB
decl_stmt|;
DECL|field|eventListenerB
specifier|private
name|SynchronizationTestEventListener
name|eventListenerB
decl_stmt|;
comment|// used to monitor occurring events
DECL|field|dbmsConnection
specifier|private
name|DBMSConnection
name|dbmsConnection
decl_stmt|;
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"Test with {0} database system"
argument_list|)
DECL|method|getTestingDatabaseSystems ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|DBMSType
argument_list|>
name|getTestingDatabaseSystems
parameter_list|()
block|{
return|return
name|TestManager
operator|.
name|getDBMSTypeTestParameter
argument_list|()
return|;
block|}
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|SQLException
throws|,
name|DatabaseNotSupportedException
throws|,
name|InvalidDBMSConnectionPropertiesException
block|{
name|this
operator|.
name|dbmsConnection
operator|=
name|TestConnector
operator|.
name|getTestDBMSConnection
argument_list|(
name|dbmsType
argument_list|)
expr_stmt|;
name|GlobalBibtexKeyPattern
name|pattern
init|=
name|GlobalBibtexKeyPattern
operator|.
name|fromPattern
argument_list|(
literal|"[auth][year]"
argument_list|)
decl_stmt|;
name|clientContextA
operator|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|Defaults
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|DBMSSynchronizer
name|synchronizerA
init|=
operator|new
name|DBMSSynchronizer
argument_list|(
name|clientContextA
argument_list|,
literal|','
argument_list|,
name|pattern
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|)
decl_stmt|;
name|clientContextA
operator|.
name|convertToSharedDatabase
argument_list|(
name|synchronizerA
argument_list|)
expr_stmt|;
name|clientContextA
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|openSharedDatabase
argument_list|(
name|dbmsConnection
argument_list|)
expr_stmt|;
name|clientContextB
operator|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|Defaults
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
expr_stmt|;
name|DBMSSynchronizer
name|synchronizerB
init|=
operator|new
name|DBMSSynchronizer
argument_list|(
name|clientContextA
argument_list|,
literal|','
argument_list|,
name|pattern
argument_list|,
operator|new
name|DummyFileUpdateMonitor
argument_list|()
argument_list|)
decl_stmt|;
name|clientContextB
operator|.
name|convertToSharedDatabase
argument_list|(
name|synchronizerB
argument_list|)
expr_stmt|;
name|clientContextB
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|openSharedDatabase
argument_list|(
name|dbmsConnection
argument_list|)
expr_stmt|;
name|eventListenerB
operator|=
operator|new
name|SynchronizationTestEventListener
argument_list|()
expr_stmt|;
name|clientContextB
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|registerListener
argument_list|(
name|eventListenerB
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|simulateEntryInsertionAndManualPull ()
specifier|public
name|void
name|simulateEntryInsertionAndManualPull
parameter_list|()
block|{
comment|//client A inserts an entry
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
comment|//client A inserts another entry
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|getBibEntryExample
argument_list|(
literal|2
argument_list|)
argument_list|)
expr_stmt|;
comment|//client B pulls the changes
name|clientContextB
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
name|assertEquals
argument_list|(
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|,
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|simulateEntryUpdateAndManualPull ()
specifier|public
name|void
name|simulateEntryUpdateAndManualPull
parameter_list|()
block|{
name|BibEntry
name|bibEntry
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|//client A inserts an entry
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
comment|//client A changes the entry
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"custom"
argument_list|,
literal|"custom value"
argument_list|)
expr_stmt|;
comment|//client B pulls the changes
name|bibEntry
operator|.
name|clearField
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|clientContextB
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
name|assertEquals
argument_list|(
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|,
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|simulateEntryDelitionAndManualPull ()
specifier|public
name|void
name|simulateEntryDelitionAndManualPull
parameter_list|()
block|{
name|BibEntry
name|bibEntry
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|//client A inserts an entry
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
comment|//client B pulls the entry
name|clientContextB
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
name|assertFalse
argument_list|(
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|,
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
comment|//client A removes the entry
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
comment|//client B pulls the change
name|clientContextB
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
name|assertTrue
argument_list|(
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|simulateUpdateOnNoLongerExistingEntry ()
specifier|public
name|void
name|simulateUpdateOnNoLongerExistingEntry
parameter_list|()
block|{
name|BibEntry
name|bibEntryOfClientA
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|//client A inserts an entry
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|bibEntryOfClientA
argument_list|)
expr_stmt|;
comment|//client B pulls the entry
name|clientContextB
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
name|assertFalse
argument_list|(
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|,
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
comment|//client A removes the entry
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|removeEntry
argument_list|(
name|bibEntryOfClientA
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|eventListenerB
operator|.
name|getSharedEntryNotPresentEvent
argument_list|()
argument_list|)
expr_stmt|;
comment|//client B tries to update the entry
name|BibEntry
name|bibEntryOfClientB
init|=
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|bibEntryOfClientB
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2009"
argument_list|)
expr_stmt|;
comment|// here a new SharedEntryNotPresentEvent has been thrown. In this case the user B would get an pop-up window.
name|assertNotNull
argument_list|(
name|eventListenerB
operator|.
name|getSharedEntryNotPresentEvent
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|bibEntryOfClientB
argument_list|,
name|eventListenerB
operator|.
name|getSharedEntryNotPresentEvent
argument_list|()
operator|.
name|getBibEntry
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|simulateEntryChangeConflicts ()
specifier|public
name|void
name|simulateEntryChangeConflicts
parameter_list|()
block|{
name|BibEntry
name|bibEntryOfClientA
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
comment|//client A inserts an entry
name|clientContextA
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|bibEntryOfClientA
argument_list|)
expr_stmt|;
comment|//client B pulls the entry
name|clientContextB
operator|.
name|getDBMSSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
comment|//A now increases the version number
name|bibEntryOfClientA
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2001"
argument_list|)
expr_stmt|;
comment|// B does nothing here, so there is no event occurrence
comment|// B now tries to update the entry
name|assertFalse
argument_list|(
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|eventListenerB
operator|.
name|getUpdateRefusedEvent
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|bibEntryOfClientB
init|=
name|clientContextB
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
comment|//B also tries to change something
name|bibEntryOfClientB
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
comment|// B now cannot update the shared entry, due to optimistic offline lock.
comment|// In this case an BibEntry merge dialog pops up.
name|assertNotNull
argument_list|(
name|eventListenerB
operator|.
name|getUpdateRefusedEvent
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getBibEntryExample (int index)
specifier|private
name|BibEntry
name|getBibEntryExample
parameter_list|(
name|int
name|index
parameter_list|)
block|{
name|BibEntry
name|bibEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|bibEntry
operator|.
name|setType
argument_list|(
literal|"inproceedings"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Wirthlin, Michael J and Hutchings, Brad L and Gilson, Kent L "
operator|+
name|index
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"The nano processor: a low resource reconfigurable processor "
operator|+
name|index
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
literal|"FPGAs for Custom Computing Machines, 1994. Proceedings. IEEE Workshop on "
operator|+
name|index
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"199"
operator|+
name|index
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setCiteKey
argument_list|(
literal|"nanoproc199"
operator|+
name|index
argument_list|)
expr_stmt|;
return|return
name|bibEntry
return|;
block|}
annotation|@
name|AfterEach
DECL|method|clear ()
specifier|public
name|void
name|clear
parameter_list|()
throws|throws
name|SQLException
block|{
name|TestManager
operator|.
name|clearTables
argument_list|(
name|dbmsConnection
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

