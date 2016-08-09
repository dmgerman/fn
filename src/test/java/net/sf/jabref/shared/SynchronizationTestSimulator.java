begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.shared
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|Connection
import|;
end_import

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
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|Defaults
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
name|Globals
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
name|BibDatabaseMode
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
name|DatabaseLocation
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
name|entry
operator|.
name|BibEntry
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
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

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|SynchronizationTestSimulator
specifier|public
class|class
name|SynchronizationTestSimulator
block|{
DECL|field|connection
specifier|private
specifier|static
name|Connection
name|connection
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
annotation|@
name|Parameter
DECL|field|dbmsType
specifier|public
name|DBMSType
name|dbmsType
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|ClassNotFoundException
throws|,
name|SQLException
block|{
comment|// Get only one connection for each parameter
if|if
condition|(
name|TestConnector
operator|.
name|currentConnectionType
operator|!=
name|dbmsType
condition|)
block|{
name|connection
operator|=
name|TestConnector
operator|.
name|getTestConnection
argument_list|(
name|dbmsType
argument_list|)
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
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
argument_list|,
name|DatabaseLocation
operator|.
name|SHARED
argument_list|)
expr_stmt|;
name|clientContextA
operator|.
name|getDBSynchronizer
argument_list|()
operator|.
name|openSharedDatabase
argument_list|(
name|connection
argument_list|,
name|dbmsType
argument_list|,
literal|"A"
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
argument_list|,
name|DatabaseLocation
operator|.
name|SHARED
argument_list|)
expr_stmt|;
name|clientContextB
operator|.
name|getDBSynchronizer
argument_list|()
operator|.
name|openSharedDatabase
argument_list|(
name|connection
argument_list|,
name|dbmsType
argument_list|,
literal|"B"
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
name|getDBSynchronizer
argument_list|()
operator|.
name|registerListener
argument_list|(
name|eventListenerB
argument_list|)
expr_stmt|;
block|}
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
name|DBMSConnector
operator|.
name|getAvailableDBMSTypes
argument_list|()
return|;
block|}
annotation|@
name|Test
DECL|method|simulateEntryInsertionAndManualPull ()
specifier|public
name|void
name|simulateEntryInsertionAndManualPull
parameter_list|()
block|{
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
comment|// client A inserts an entry
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
comment|// client A inserts another entry
name|clientContextB
operator|.
name|getDBSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
comment|// client B pulls the changes
name|Assert
operator|.
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
comment|// client A inserts an entry
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"custom"
argument_list|,
literal|"custom value"
argument_list|)
expr_stmt|;
comment|// client A changes the entry
name|bibEntry
operator|.
name|clearField
argument_list|(
literal|"author"
argument_list|)
expr_stmt|;
name|clientContextB
operator|.
name|getDBSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
comment|// client B pulls the changes
name|Assert
operator|.
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
comment|// client A inserts an entry
name|clientContextB
operator|.
name|getDBSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
comment|// client B pulls the entry
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
comment|// client A removes the entry
name|clientContextB
operator|.
name|getDBSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
comment|// client B pulls the change
name|Assert
operator|.
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
name|Assert
operator|.
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
comment|// client A inserts an entry
name|clientContextB
operator|.
name|getDBSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
comment|// client B pulls the entry
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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
comment|// client A removes the entry
name|Assert
operator|.
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
name|Assert
operator|.
name|assertNull
argument_list|(
name|eventListenerB
operator|.
name|getSharedEntryNotPresentEvent
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
comment|// client B tries to update the entry
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
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|eventListenerB
operator|.
name|getSharedEntryNotPresentEvent
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
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
comment|// client A inserts an entry
name|clientContextB
operator|.
name|getDBSynchronizer
argument_list|()
operator|.
name|pullChanges
argument_list|()
expr_stmt|;
comment|// client B pulls the entry
name|bibEntryOfClientA
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2001"
argument_list|)
expr_stmt|;
comment|// A now increases the version number
comment|// B does nothing here, so there is no event occurrence
comment|// B now tries to update the entry
name|Assert
operator|.
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
name|Assert
operator|.
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
name|bibEntryOfClientB
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2016"
argument_list|)
expr_stmt|;
comment|// B also tries to change something
comment|// B now can not update the shared entry, due to optimistic offline lock.
comment|// In this case an BibEntry merge dialog pops up.
name|Assert
operator|.
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
DECL|method|escape (String expression)
specifier|private
name|String
name|escape
parameter_list|(
name|String
name|expression
parameter_list|)
block|{
return|return
name|DBMSProcessor
operator|.
name|getProcessorInstance
argument_list|(
name|connection
argument_list|,
name|dbmsType
argument_list|)
operator|.
name|escape
argument_list|(
name|expression
argument_list|)
return|;
block|}
annotation|@
name|After
DECL|method|clear ()
specifier|public
name|void
name|clear
parameter_list|()
throws|throws
name|SQLException
block|{
if|if
condition|(
operator|(
name|dbmsType
operator|==
name|DBMSType
operator|.
name|MYSQL
operator|)
operator|||
operator|(
name|dbmsType
operator|==
name|DBMSType
operator|.
name|POSTGRESQL
operator|)
condition|)
block|{
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS "
operator|+
name|escape
argument_list|(
literal|"FIELD"
argument_list|)
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS "
operator|+
name|escape
argument_list|(
literal|"ENTRY"
argument_list|)
argument_list|)
expr_stmt|;
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"DROP TABLE IF EXISTS "
operator|+
name|escape
argument_list|(
literal|"METADATA"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dbmsType
operator|==
name|DBMSType
operator|.
name|ORACLE
condition|)
block|{
name|connection
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"BEGIN\n"
operator|+
literal|"EXECUTE IMMEDIATE 'DROP TABLE "
operator|+
name|escape
argument_list|(
literal|"FIELD"
argument_list|)
operator|+
literal|"';\n"
operator|+
literal|"EXECUTE IMMEDIATE 'DROP TABLE "
operator|+
name|escape
argument_list|(
literal|"ENTRY"
argument_list|)
operator|+
literal|"';\n"
operator|+
literal|"EXECUTE IMMEDIATE 'DROP TABLE "
operator|+
name|escape
argument_list|(
literal|"METADATA"
argument_list|)
operator|+
literal|"';\n"
operator|+
literal|"EXECUTE IMMEDIATE 'DROP SEQUENCE "
operator|+
name|escape
argument_list|(
literal|"ENTRY_SEQ"
argument_list|)
operator|+
literal|"';\n"
operator|+
literal|"EXCEPTION\n"
operator|+
literal|"WHEN OTHERS THEN\n"
operator|+
literal|"IF SQLCODE != -942 THEN\n"
operator|+
literal|"RAISE;\n"
operator|+
literal|"END IF;\n"
operator|+
literal|"END;"
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

