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
name|ResultSet
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
name|HashMap
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
name|java
operator|.
name|util
operator|.
name|Optional
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
name|shared
operator|.
name|exception
operator|.
name|InvalidDBMSConnectionPropertiesException
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
DECL|class|DBMSProcessorTest
specifier|public
class|class
name|DBMSProcessorTest
block|{
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
name|dbmsProcessor
operator|=
name|DBMSProcessor
operator|.
name|getProcessorInstance
argument_list|(
name|dbmsConnection
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|setupSharedDatabase
argument_list|()
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
DECL|method|testCheckBaseIntegrity ()
specifier|public
name|void
name|testCheckBaseIntegrity
parameter_list|()
throws|throws
name|SQLException
block|{
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
name|clear
argument_list|()
expr_stmt|;
name|Assert
operator|.
name|assertFalse
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
DECL|method|testSetUpSharedDatabase ()
specifier|public
name|void
name|testSetUpSharedDatabase
parameter_list|()
throws|throws
name|SQLException
block|{
name|clear
argument_list|()
expr_stmt|;
name|dbmsProcessor
operator|.
name|setupSharedDatabase
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
DECL|method|testInsertEntry ()
specifier|public
name|void
name|testInsertEntry
parameter_list|()
throws|throws
name|SQLException
block|{
name|BibEntry
name|expectedEntry
init|=
name|getBibEntryExample
argument_list|()
decl_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|expectedEntry
argument_list|)
expr_stmt|;
name|BibEntry
name|emptyEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|emptyEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|setSharedID
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|emptyEntry
argument_list|)
expr_stmt|;
comment|// does not insert, due to same sharedID.
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|actualFieldMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|ResultSet
name|entryResultSet
init|=
name|selectFrom
argument_list|(
literal|"ENTRY"
argument_list|)
init|)
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|entryResultSet
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entryResultSet
operator|.
name|getInt
argument_list|(
literal|"SHARED_ID"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"inproceedings"
argument_list|,
name|entryResultSet
operator|.
name|getString
argument_list|(
literal|"TYPE"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entryResultSet
operator|.
name|getInt
argument_list|(
literal|"VERSION"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|entryResultSet
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
try|try
init|(
name|ResultSet
name|fieldResultSet
init|=
name|selectFrom
argument_list|(
literal|"FIELD"
argument_list|)
init|)
block|{
while|while
condition|(
name|fieldResultSet
operator|.
name|next
argument_list|()
condition|)
block|{
name|actualFieldMap
operator|.
name|put
argument_list|(
name|fieldResultSet
operator|.
name|getString
argument_list|(
literal|"NAME"
argument_list|)
argument_list|,
name|fieldResultSet
operator|.
name|getString
argument_list|(
literal|"VALUE"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|expectedFieldMap
init|=
name|expectedEntry
operator|.
name|getFieldMap
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedFieldMap
argument_list|,
name|actualFieldMap
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUpdateEntry ()
specifier|public
name|void
name|testUpdateEntry
parameter_list|()
throws|throws
name|OfflineLockException
throws|,
name|SQLException
block|{
name|BibEntry
name|expectedEntry
init|=
name|getBibEntryExample
argument_list|()
decl_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|expectedEntry
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setType
argument_list|(
literal|"book"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Michael J and Hutchings"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
literal|"customField"
argument_list|,
literal|"custom value"
argument_list|)
expr_stmt|;
name|expectedEntry
operator|.
name|clearField
argument_list|(
literal|"booktitle"
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|updateEntry
argument_list|(
name|expectedEntry
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|actualEntryOptional
init|=
name|dbmsProcessor
operator|.
name|getSharedEntry
argument_list|(
name|expectedEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getSharedID
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|actualEntryOptional
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedEntry
argument_list|,
name|actualEntryOptional
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|OfflineLockException
operator|.
name|class
argument_list|)
DECL|method|testUpdateNewerEntry ()
specifier|public
name|void
name|testUpdateNewerEntry
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
argument_list|()
decl_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|setVersion
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// simulate older version
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"1993"
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|updateEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUpdateEqualEntry ()
specifier|public
name|void
name|testUpdateEqualEntry
parameter_list|()
throws|throws
name|OfflineLockException
throws|,
name|SQLException
block|{
name|BibEntry
name|expectedBibEntry
init|=
name|getBibEntryExample
argument_list|()
decl_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|expectedBibEntry
argument_list|)
expr_stmt|;
name|expectedBibEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|setVersion
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// simulate older version
name|dbmsProcessor
operator|.
name|updateEntry
argument_list|(
name|expectedBibEntry
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|actualBibEntryOptional
init|=
name|dbmsProcessor
operator|.
name|getSharedEntry
argument_list|(
name|expectedBibEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getSharedID
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|actualBibEntryOptional
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedBibEntry
argument_list|,
name|actualBibEntryOptional
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testRemoveEntry ()
specifier|public
name|void
name|testRemoveEntry
parameter_list|()
throws|throws
name|SQLException
block|{
name|BibEntry
name|bibEntry
init|=
name|getBibEntryExample
argument_list|()
decl_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|removeEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
try|try
init|(
name|ResultSet
name|resultSet
init|=
name|selectFrom
argument_list|(
literal|"ENTRY"
argument_list|)
init|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|resultSet
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testGetSharedEntries ()
specifier|public
name|void
name|testGetSharedEntries
parameter_list|()
block|{
name|BibEntry
name|bibEntry
init|=
name|getBibEntryExampleWithEmptyFields
argument_list|()
decl_stmt|;
name|dbmsProcessor
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
name|expectedEntries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|bibEntry
argument_list|)
decl_stmt|;
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
name|expectedEntries
argument_list|,
name|actualEntries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSharedEntry ()
specifier|public
name|void
name|testGetSharedEntry
parameter_list|()
block|{
name|BibEntry
name|expectedBibEntry
init|=
name|getBibEntryExampleWithEmptyFields
argument_list|()
decl_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|expectedBibEntry
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|actualBibEntryOptional
init|=
name|dbmsProcessor
operator|.
name|getSharedEntry
argument_list|(
name|expectedBibEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getSharedID
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|actualBibEntryOptional
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedBibEntry
argument_list|,
name|actualBibEntryOptional
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Assert
operator|.
name|fail
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testGetNotExistingSharedEntry ()
specifier|public
name|void
name|testGetNotExistingSharedEntry
parameter_list|()
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|actualBibEntryOptional
init|=
name|dbmsProcessor
operator|.
name|getSharedEntry
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|actualBibEntryOptional
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSharedIDVersionMapping ()
specifier|public
name|void
name|testGetSharedIDVersionMapping
parameter_list|()
throws|throws
name|OfflineLockException
throws|,
name|SQLException
block|{
name|BibEntry
name|firstEntry
init|=
name|getBibEntryExample
argument_list|()
decl_stmt|;
name|BibEntry
name|secondEntry
init|=
name|getBibEntryExample
argument_list|()
decl_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|firstEntry
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|secondEntry
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|updateEntry
argument_list|(
name|secondEntry
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Integer
argument_list|,
name|Integer
argument_list|>
name|expectedIDVersionMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|expectedIDVersionMap
operator|.
name|put
argument_list|(
name|firstEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getSharedID
argument_list|()
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|expectedIDVersionMap
operator|.
name|put
argument_list|(
name|secondEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getSharedID
argument_list|()
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|Integer
argument_list|,
name|Integer
argument_list|>
name|actualIDVersionMap
init|=
name|dbmsProcessor
operator|.
name|getSharedIDVersionMapping
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expectedIDVersionMap
argument_list|,
name|actualIDVersionMap
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetSharedMetaData ()
specifier|public
name|void
name|testGetSharedMetaData
parameter_list|()
block|{
name|insertMetaData
argument_list|(
literal|"databaseType"
argument_list|,
literal|"bibtex;"
argument_list|)
expr_stmt|;
name|insertMetaData
argument_list|(
literal|"protectedFlag"
argument_list|,
literal|"true;"
argument_list|)
expr_stmt|;
name|insertMetaData
argument_list|(
literal|"saveActions"
argument_list|,
literal|"enabled;\nauthor[capitalize,html_to_latex]\ntitle[title_case]\n;"
argument_list|)
expr_stmt|;
name|insertMetaData
argument_list|(
literal|"saveOrderConfig"
argument_list|,
literal|"specified;author;false;title;false;year;true;"
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|expectedMetaData
init|=
name|getMetaDataExample
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|actualMetaData
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
name|expectedMetaData
argument_list|,
name|actualMetaData
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSetSharedMetaData ()
specifier|public
name|void
name|testSetSharedMetaData
parameter_list|()
throws|throws
name|SQLException
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|expectedMetaData
init|=
name|getMetaDataExample
argument_list|()
decl_stmt|;
name|dbmsProcessor
operator|.
name|setSharedMetaData
argument_list|(
name|expectedMetaData
argument_list|)
expr_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|actualMetaData
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
name|expectedMetaData
argument_list|,
name|actualMetaData
argument_list|)
expr_stmt|;
block|}
DECL|method|getMetaDataExample ()
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getMetaDataExample
parameter_list|()
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|expectedMetaData
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|expectedMetaData
operator|.
name|put
argument_list|(
literal|"databaseType"
argument_list|,
literal|"bibtex;"
argument_list|)
expr_stmt|;
name|expectedMetaData
operator|.
name|put
argument_list|(
literal|"protectedFlag"
argument_list|,
literal|"true;"
argument_list|)
expr_stmt|;
name|expectedMetaData
operator|.
name|put
argument_list|(
literal|"saveActions"
argument_list|,
literal|"enabled;\nauthor[capitalize,html_to_latex]\ntitle[title_case]\n;"
argument_list|)
expr_stmt|;
name|expectedMetaData
operator|.
name|put
argument_list|(
literal|"saveOrderConfig"
argument_list|,
literal|"specified;author;false;title;false;year;true;"
argument_list|)
expr_stmt|;
return|return
name|expectedMetaData
return|;
block|}
DECL|method|getBibEntryExampleWithEmptyFields ()
specifier|private
name|BibEntry
name|getBibEntryExampleWithEmptyFields
parameter_list|()
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
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Author"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|setSharedID
argument_list|(
literal|1
argument_list|)
expr_stmt|;
return|return
name|bibEntry
return|;
block|}
DECL|method|getBibEntryExample ()
specifier|private
name|BibEntry
name|getBibEntryExample
parameter_list|()
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
literal|"Wirthlin, Michael J and Hutchings, Brad L and Gilson, Kent L"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"The nano processor: a low resource reconfigurable processor"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"booktitle"
argument_list|,
literal|"FPGAs for Custom Computing Machines, 1994. Proceedings. IEEE Workshop on"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"1994"
argument_list|)
expr_stmt|;
name|bibEntry
operator|.
name|setCiteKey
argument_list|(
literal|"nanoproc1994"
argument_list|)
expr_stmt|;
return|return
name|bibEntry
return|;
block|}
DECL|method|selectFrom (String table)
specifier|private
name|ResultSet
name|selectFrom
parameter_list|(
name|String
name|table
parameter_list|)
block|{
try|try
block|{
return|return
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeQuery
argument_list|(
literal|"SELECT * FROM "
operator|+
name|escape
argument_list|(
name|table
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
comment|// Oracle does not support multiple tuple insertion in one INSERT INTO command.
comment|// Therefore this function was defined to improve the readability and to keep the code short.
DECL|method|insertMetaData (String key, String value)
specifier|private
name|void
name|insertMetaData
parameter_list|(
name|String
name|key
parameter_list|,
name|String
name|value
parameter_list|)
block|{
try|try
block|{
name|dbmsConnection
operator|.
name|getConnection
argument_list|()
operator|.
name|createStatement
argument_list|()
operator|.
name|executeUpdate
argument_list|(
literal|"INSERT INTO "
operator|+
name|escape
argument_list|(
literal|"METADATA"
argument_list|)
operator|+
literal|"("
operator|+
name|escape
argument_list|(
literal|"KEY"
argument_list|)
operator|+
literal|", "
operator|+
name|escape
argument_list|(
literal|"VALUE"
argument_list|)
operator|+
literal|") VALUES("
operator|+
name|escapeValue
argument_list|(
name|key
argument_list|)
operator|+
literal|", "
operator|+
name|escapeValue
argument_list|(
name|value
argument_list|)
operator|+
literal|")"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|Assert
operator|.
name|fail
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
name|dbmsProcessor
operator|.
name|escape
argument_list|(
name|expression
argument_list|)
return|;
block|}
DECL|method|escapeValue (String value)
specifier|private
name|String
name|escapeValue
parameter_list|(
name|String
name|value
parameter_list|)
block|{
return|return
literal|"'"
operator|+
name|value
operator|+
literal|"'"
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

