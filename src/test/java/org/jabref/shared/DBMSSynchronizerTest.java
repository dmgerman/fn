begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.shared
package|package
name|org
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
name|SQLException
import|;
end_import

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
name|Collection
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
name|java
operator|.
name|util
operator|.
name|Map
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
name|exporter
operator|.
name|MetaDataSerializer
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
name|casechanger
operator|.
name|LowerCaseFormatter
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
name|jabref
operator|.
name|model
operator|.
name|database
operator|.
name|BibDatabase
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
name|entry
operator|.
name|event
operator|.
name|EntryEventSource
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
name|metadata
operator|.
name|MetaData
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|shared
operator|.
name|exception
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
name|shared
operator|.
name|exception
operator|.
name|OfflineLockException
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
name|DatabaseTests
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
name|experimental
operator|.
name|categories
operator|.
name|Category
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
annotation|@
name|Category
argument_list|(
name|DatabaseTests
operator|.
name|class
argument_list|)
DECL|class|DBMSSynchronizerTest
specifier|public
class|class
name|DBMSSynchronizerTest
block|{
DECL|field|dbmsSynchronizer
specifier|private
name|DBMSSynchronizer
name|dbmsSynchronizer
decl_stmt|;
DECL|field|dbmsConnection
specifier|private
name|DBMSConnection
name|dbmsConnection
decl_stmt|;
DECL|field|dbmsProcessor
specifier|private
name|DBMSProcessor
name|dbmsProcessor
decl_stmt|;
DECL|field|bibDatabase
specifier|private
name|BibDatabase
name|bibDatabase
decl_stmt|;
DECL|field|pattern
specifier|private
name|GlobalBibtexKeyPattern
name|pattern
decl_stmt|;
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
name|SQLException
throws|,
name|DatabaseNotSupportedException
throws|,
name|InvalidDBMSConnectionPropertiesException
block|{
name|dbmsConnection
operator|=
name|TestConnector
operator|.
name|getTestDBMSConnection
argument_list|(
name|dbmsType
argument_list|)
expr_stmt|;
name|bibDatabase
operator|=
operator|new
name|BibDatabase
argument_list|()
expr_stmt|;
name|BibDatabaseContext
name|context
init|=
operator|new
name|BibDatabaseContext
argument_list|(
name|bibDatabase
argument_list|)
decl_stmt|;
name|pattern
operator|=
name|GlobalBibtexKeyPattern
operator|.
name|fromPattern
argument_list|(
literal|"[auth][year]"
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|=
operator|new
name|DBMSSynchronizer
argument_list|(
name|context
argument_list|,
literal|','
argument_list|,
name|pattern
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|=
name|DBMSProcessor
operator|.
name|getProcessorInstance
argument_list|(
name|dbmsConnection
argument_list|)
expr_stmt|;
name|bibDatabase
operator|.
name|registerListener
argument_list|(
name|dbmsSynchronizer
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|openSharedDatabase
argument_list|(
name|dbmsConnection
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
name|TestManager
operator|.
name|getDBMSTypeTestParameter
argument_list|()
return|;
block|}
annotation|@
name|Test
DECL|method|testEntryAddedEventListener ()
specifier|public
name|void
name|testEntryAddedEventListener
parameter_list|()
block|{
name|BibEntry
name|expectedEntry
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|BibEntry
name|furtherEntry
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|expectedEntry
argument_list|)
expr_stmt|;
comment|// should not add into shared database.
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|furtherEntry
argument_list|,
name|EntryEventSource
operator|.
name|SHARED
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
init|=
name|dbmsProcessor
operator|.
name|getSharedEntries
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|actualEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedEntry
argument_list|,
name|actualEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFieldChangedEventListener ()
specifier|public
name|void
name|testFieldChangedEventListener
parameter_list|()
block|{
name|BibEntry
name|expectedEntry
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|expectedEntry
operator|.
name|registerListener
argument_list|(
name|dbmsSynchronizer
argument_list|)
expr_stmt|;
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|expectedEntry
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Brad L and Gilson"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"The micro multiplexer"
argument_list|,
name|EntryEventSource
operator|.
name|SHARED
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
init|=
name|dbmsProcessor
operator|.
name|getSharedEntries
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|actualEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedEntry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|,
name|actualEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"The nano processor1"
argument_list|,
name|actualEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEntryRemovedEventListener ()
specifier|public
name|void
name|testEntryRemovedEventListener
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
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
init|=
name|dbmsProcessor
operator|.
name|getSharedEntries
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|actualEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|bibEntry
argument_list|,
name|actualEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|bibDatabase
operator|.
name|removeEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|actualEntries
operator|=
name|dbmsProcessor
operator|.
name|getSharedEntries
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|actualEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|bibDatabase
operator|.
name|removeEntry
argument_list|(
name|bibEntry
argument_list|,
name|EntryEventSource
operator|.
name|SHARED
argument_list|)
expr_stmt|;
name|actualEntries
operator|=
name|dbmsProcessor
operator|.
name|getSharedEntries
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|actualEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|bibEntry
argument_list|,
name|actualEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMetaDataChangedEventListener ()
specifier|public
name|void
name|testMetaDataChangedEventListener
parameter_list|()
block|{
name|MetaData
name|testMetaData
init|=
operator|new
name|MetaData
argument_list|()
decl_stmt|;
name|testMetaData
operator|.
name|registerListener
argument_list|(
name|dbmsSynchronizer
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|setMetaData
argument_list|(
name|testMetaData
argument_list|)
expr_stmt|;
name|testMetaData
operator|.
name|setMode
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|expectedMap
init|=
name|MetaDataSerializer
operator|.
name|getSerializedStringMap
argument_list|(
name|testMetaData
argument_list|,
name|pattern
argument_list|)
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|actualMap
init|=
name|dbmsProcessor
operator|.
name|getSharedMetaData
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedMap
argument_list|,
name|actualMap
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testInitializeDatabases ()
specifier|public
name|void
name|testInitializeDatabases
parameter_list|()
throws|throws
name|SQLException
throws|,
name|DatabaseNotSupportedException
block|{
name|clear
argument_list|()
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|initializeDatabases
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|dbmsProcessor
operator|.
name|checkBaseIntegrity
argument_list|()
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|initializeDatabases
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|dbmsProcessor
operator|.
name|checkBaseIntegrity
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSynchronizeLocalDatabaseWithEntryRemoval ()
specifier|public
name|void
name|testSynchronizeLocalDatabaseWithEntryRemoval
parameter_list|()
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|expectedBibEntries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
argument_list|,
name|getBibEntryExample
argument_list|(
literal|2
argument_list|)
argument_list|)
decl_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|expectedBibEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|expectedBibEntries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|bibDatabase
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedBibEntries
argument_list|,
name|bibDatabase
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|removeEntry
argument_list|(
name|expectedBibEntries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|removeEntry
argument_list|(
name|expectedBibEntries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|expectedBibEntries
operator|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedBibEntries
argument_list|,
name|bibDatabase
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSynchronizeLocalDatabaseWithEntryUpdate ()
specifier|public
name|void
name|testSynchronizeLocalDatabaseWithEntryUpdate
parameter_list|()
throws|throws
name|OfflineLockException
throws|,
name|SQLException
block|{
name|BibEntry
name|bibEntry
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|bibDatabase
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|modifiedBibEntry
init|=
name|getBibEntryExample
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|modifiedBibEntry
operator|.
name|setField
argument_list|(
literal|"custom"
argument_list|,
literal|"custom value"
argument_list|)
expr_stmt|;
name|modifiedBibEntry
operator|.
name|clearField
argument_list|(
literal|"title"
argument_list|)
expr_stmt|;
name|modifiedBibEntry
operator|.
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|updateEntry
argument_list|(
name|modifiedBibEntry
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
comment|// testing point
name|Assert
operator|.
name|assertEquals
argument_list|(
name|bibDatabase
operator|.
name|getEntries
argument_list|()
argument_list|,
name|dbmsProcessor
operator|.
name|getSharedEntries
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testApplyMetaData ()
specifier|public
name|void
name|testApplyMetaData
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
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|MetaData
name|testMetaData
init|=
operator|new
name|MetaData
argument_list|()
decl_stmt|;
name|testMetaData
operator|.
name|setSaveActions
argument_list|(
operator|new
name|FieldFormatterCleanups
argument_list|(
literal|true
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
operator|new
name|FieldFormatterCleanup
argument_list|(
literal|"author"
argument_list|,
operator|new
name|LowerCaseFormatter
argument_list|()
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|setMetaData
argument_list|(
name|testMetaData
argument_list|)
expr_stmt|;
name|dbmsSynchronizer
operator|.
name|applyMetaData
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"wirthlin, michael j1"
argument_list|,
name|bibEntry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
operator|.
name|get
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
literal|"book"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Wirthlin, Michael J"
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
literal|"The nano processor"
operator|+
name|index
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|setSharedID
argument_list|(
name|index
argument_list|)
expr_stmt|;
return|return
name|bibEntry
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

