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
name|Objects
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
name|java
operator|.
name|util
operator|.
name|Set
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
name|exporter
operator|.
name|BibDatabaseWriter
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
name|exporter
operator|.
name|MetaDataSerializer
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
name|importer
operator|.
name|ParseException
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
name|importer
operator|.
name|util
operator|.
name|MetaDataParser
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
name|bibtexkeypattern
operator|.
name|GlobalBibtexKeyPattern
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
name|BibDatabase
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
name|model
operator|.
name|database
operator|.
name|event
operator|.
name|EntryAddedEvent
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
name|event
operator|.
name|EntryRemovedEvent
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
name|model
operator|.
name|entry
operator|.
name|event
operator|.
name|EntryEvent
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
name|event
operator|.
name|EntryEventSource
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
name|event
operator|.
name|FieldChangedEvent
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
name|metadata
operator|.
name|MetaData
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
name|metadata
operator|.
name|event
operator|.
name|MetaDataChangedEvent
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
name|event
operator|.
name|ConnectionLostEvent
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
name|event
operator|.
name|SharedEntryNotPresentEvent
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
name|event
operator|.
name|UpdateRefusedEvent
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
name|DatabaseNotSupportedException
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
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|EventBus
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|eventbus
operator|.
name|Subscribe
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_comment
comment|/**  * Synchronizes the shared or local databases with their opposite side.  * Local changes are pushed by {@link EntryEvent} using Google's Guava EventBus.  */
end_comment

begin_class
DECL|class|DBMSSynchronizer
specifier|public
class|class
name|DBMSSynchronizer
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|DBMSSynchronizer
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|dbmsProcessor
specifier|private
name|DBMSProcessor
name|dbmsProcessor
decl_stmt|;
DECL|field|dbmsType
specifier|private
name|DBMSType
name|dbmsType
decl_stmt|;
DECL|field|dbName
specifier|private
name|String
name|dbName
decl_stmt|;
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|field|bibDatabase
specifier|private
specifier|final
name|BibDatabase
name|bibDatabase
decl_stmt|;
DECL|field|eventBus
specifier|private
specifier|final
name|EventBus
name|eventBus
decl_stmt|;
DECL|field|currentConnection
specifier|private
name|Connection
name|currentConnection
decl_stmt|;
DECL|field|keywordSeparator
specifier|private
specifier|final
name|Character
name|keywordSeparator
decl_stmt|;
DECL|field|globalCiteKeyPattern
specifier|private
name|GlobalBibtexKeyPattern
name|globalCiteKeyPattern
decl_stmt|;
DECL|method|DBMSSynchronizer (BibDatabaseContext bibDatabaseContext, Character keywordSeparator, GlobalBibtexKeyPattern globalCiteKeyPattern)
specifier|public
name|DBMSSynchronizer
parameter_list|(
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|Character
name|keywordSeparator
parameter_list|,
name|GlobalBibtexKeyPattern
name|globalCiteKeyPattern
parameter_list|)
block|{
name|this
operator|.
name|bibDatabaseContext
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|bibDatabase
operator|=
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|bibDatabaseContext
operator|.
name|getMetaData
argument_list|()
expr_stmt|;
name|this
operator|.
name|eventBus
operator|=
operator|new
name|EventBus
argument_list|()
expr_stmt|;
name|this
operator|.
name|keywordSeparator
operator|=
name|keywordSeparator
expr_stmt|;
name|this
operator|.
name|globalCiteKeyPattern
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|globalCiteKeyPattern
argument_list|)
expr_stmt|;
block|}
comment|/**      * Listening method. Inserts a new {@link BibEntry} into shared database.      *      * @param event {@link EntryAddedEvent} object      */
annotation|@
name|Subscribe
DECL|method|listen (EntryAddedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryAddedEvent
name|event
parameter_list|)
block|{
comment|// While synchronizing the local database (see synchronizeLocalDatabase() below), some EntryEvents may be posted.
comment|// In this case DBSynchronizer should not try to insert the bibEntry entry again (but it would not harm).
if|if
condition|(
name|isEventSourceAccepted
argument_list|(
name|event
argument_list|)
operator|&&
name|checkCurrentConnection
argument_list|()
condition|)
block|{
name|dbmsProcessor
operator|.
name|insertEntry
argument_list|(
name|event
operator|.
name|getBibEntry
argument_list|()
argument_list|)
expr_stmt|;
name|synchronizeLocalMetaData
argument_list|()
expr_stmt|;
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
comment|// Pull changes for the case that there were some
block|}
block|}
comment|/**      * Listening method. Updates an existing shared {@link BibEntry}.      *      * @param event {@link FieldChangedEvent} object      */
annotation|@
name|Subscribe
DECL|method|listen (FieldChangedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|FieldChangedEvent
name|event
parameter_list|)
block|{
comment|// While synchronizing the local database (see synchronizeLocalDatabase() below), some EntryEvents may be posted.
comment|// In this case DBSynchronizer should not try to update the bibEntry entry again (but it would not harm).
if|if
condition|(
name|isPresentLocalBibEntry
argument_list|(
name|event
operator|.
name|getBibEntry
argument_list|()
argument_list|)
operator|&&
name|isEventSourceAccepted
argument_list|(
name|event
argument_list|)
operator|&&
name|checkCurrentConnection
argument_list|()
condition|)
block|{
name|synchronizeLocalMetaData
argument_list|()
expr_stmt|;
name|BibEntry
name|bibEntry
init|=
name|event
operator|.
name|getBibEntry
argument_list|()
decl_stmt|;
name|synchronizeSharedEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
comment|// Pull changes for the case that there were some
block|}
block|}
comment|/**      * Listening method. Deletes the given {@link BibEntry} from shared database.      *      * @param event {@link EntryRemovedEvent} object      */
annotation|@
name|Subscribe
DECL|method|listen (EntryRemovedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryRemovedEvent
name|event
parameter_list|)
block|{
comment|// While synchronizing the local database (see synchronizeLocalDatabase() below), some EntryEvents may be posted.
comment|// In this case DBSynchronizer should not try to delete the bibEntry entry again (but it would not harm).
if|if
condition|(
name|isEventSourceAccepted
argument_list|(
name|event
argument_list|)
operator|&&
name|checkCurrentConnection
argument_list|()
condition|)
block|{
name|dbmsProcessor
operator|.
name|removeEntry
argument_list|(
name|event
operator|.
name|getBibEntry
argument_list|()
argument_list|)
expr_stmt|;
name|synchronizeLocalMetaData
argument_list|()
expr_stmt|;
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
comment|// Pull changes for the case that there where some
block|}
block|}
comment|/**      * Listening method. Synchronizes the shared {@link MetaData} and applies them locally.      *      * @param event      */
annotation|@
name|Subscribe
DECL|method|listen (MetaDataChangedEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|MetaDataChangedEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|checkCurrentConnection
argument_list|()
condition|)
block|{
name|synchronizeSharedMetaData
argument_list|(
name|event
operator|.
name|getMetaData
argument_list|()
argument_list|,
name|globalCiteKeyPattern
argument_list|)
expr_stmt|;
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
name|applyMetaData
argument_list|()
expr_stmt|;
name|dbmsProcessor
operator|.
name|notifyClients
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Subscribe
DECL|method|listen (EntryEvent event)
specifier|public
name|void
name|listen
parameter_list|(
name|EntryEvent
name|event
parameter_list|)
block|{
if|if
condition|(
name|isEventSourceAccepted
argument_list|(
name|event
argument_list|)
condition|)
block|{
name|dbmsProcessor
operator|.
name|notifyClients
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Sets the table structure of shared database if needed and pulls all shared entries      * to the new local database.      *      * @throws DatabaseNotSupportedException if the version of shared database does not match      *          the version of current shared database support ({@link DBMSProcessor}).      */
DECL|method|initializeDatabases ()
specifier|public
name|void
name|initializeDatabases
parameter_list|()
throws|throws
name|DatabaseNotSupportedException
throws|,
name|SQLException
block|{
if|if
condition|(
operator|!
name|dbmsProcessor
operator|.
name|checkBaseIntegrity
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Integrity check failed. Fixing..."
argument_list|)
expr_stmt|;
name|dbmsProcessor
operator|.
name|setupSharedDatabase
argument_list|()
expr_stmt|;
comment|// This check should only be performed once on initial database setup.
comment|// Calling dbmsProcessor.setupSharedDatabase() lets dbmsProcessor.checkBaseIntegrity() be true.
if|if
condition|(
name|dbmsProcessor
operator|.
name|checkForPre3Dot6Intergrity
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|DatabaseNotSupportedException
argument_list|()
throw|;
block|}
block|}
name|synchronizeLocalMetaData
argument_list|()
expr_stmt|;
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
name|dbmsProcessor
operator|.
name|startNotificationListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
comment|/**      * Synchronizes the local database with shared one.      * Possible update types are removal, update or insert of a {@link BibEntry}.      */
DECL|method|synchronizeLocalDatabase ()
specifier|public
name|void
name|synchronizeLocalDatabase
parameter_list|()
block|{
if|if
condition|(
operator|!
name|checkCurrentConnection
argument_list|()
condition|)
block|{
return|return;
block|}
name|List
argument_list|<
name|BibEntry
argument_list|>
name|localEntries
init|=
name|bibDatabase
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|Integer
argument_list|,
name|Integer
argument_list|>
name|idVersionMap
init|=
name|dbmsProcessor
operator|.
name|getSharedIDVersionMapping
argument_list|()
decl_stmt|;
comment|// remove old entries locally
name|removeNotSharedEntries
argument_list|(
name|localEntries
argument_list|,
name|idVersionMap
operator|.
name|keySet
argument_list|()
argument_list|)
expr_stmt|;
comment|// compare versions and update local entry if needed
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|Integer
argument_list|,
name|Integer
argument_list|>
name|idVersionEntry
range|:
name|idVersionMap
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|boolean
name|match
init|=
literal|false
decl_stmt|;
for|for
control|(
name|BibEntry
name|localEntry
range|:
name|localEntries
control|)
block|{
if|if
condition|(
name|idVersionEntry
operator|.
name|getKey
argument_list|()
operator|==
name|localEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getSharedID
argument_list|()
condition|)
block|{
name|match
operator|=
literal|true
expr_stmt|;
if|if
condition|(
name|idVersionEntry
operator|.
name|getValue
argument_list|()
operator|>
name|localEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getVersion
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|sharedEntry
init|=
name|dbmsProcessor
operator|.
name|getSharedEntry
argument_list|(
name|idVersionEntry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|sharedEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
comment|// update fields
name|localEntry
operator|.
name|setType
argument_list|(
name|sharedEntry
operator|.
name|get
argument_list|()
operator|.
name|getType
argument_list|()
argument_list|,
name|EntryEventSource
operator|.
name|SHARED
argument_list|)
expr_stmt|;
name|localEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|setVersion
argument_list|(
name|sharedEntry
operator|.
name|get
argument_list|()
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getVersion
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|field
range|:
name|sharedEntry
operator|.
name|get
argument_list|()
operator|.
name|getFieldNames
argument_list|()
control|)
block|{
name|localEntry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|sharedEntry
operator|.
name|get
argument_list|()
operator|.
name|getField
argument_list|(
name|field
argument_list|)
argument_list|,
name|EntryEventSource
operator|.
name|SHARED
argument_list|)
expr_stmt|;
block|}
name|Set
argument_list|<
name|String
argument_list|>
name|redundantLocalEntryFields
init|=
name|localEntry
operator|.
name|getFieldNames
argument_list|()
decl_stmt|;
name|redundantLocalEntryFields
operator|.
name|removeAll
argument_list|(
name|sharedEntry
operator|.
name|get
argument_list|()
operator|.
name|getFieldNames
argument_list|()
argument_list|)
expr_stmt|;
comment|// remove not existing fields
for|for
control|(
name|String
name|redundantField
range|:
name|redundantLocalEntryFields
control|)
block|{
name|localEntry
operator|.
name|clearField
argument_list|(
name|redundantField
argument_list|,
name|EntryEventSource
operator|.
name|SHARED
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
if|if
condition|(
operator|!
name|match
condition|)
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|bibEntry
init|=
name|dbmsProcessor
operator|.
name|getSharedEntry
argument_list|(
name|idVersionEntry
operator|.
name|getKey
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|bibEntry
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|bibDatabase
operator|.
name|insertEntry
argument_list|(
name|bibEntry
operator|.
name|get
argument_list|()
argument_list|,
name|EntryEventSource
operator|.
name|SHARED
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * Removes all local entries which are not present on shared database.      *      * @param localEntries List of {@link BibEntry} the entries should be removed from      * @param sharedIDs Set of all IDs which are present on shared database      */
DECL|method|removeNotSharedEntries (List<BibEntry> localEntries, Set<Integer> sharedIDs)
specifier|private
name|void
name|removeNotSharedEntries
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|localEntries
parameter_list|,
name|Set
argument_list|<
name|Integer
argument_list|>
name|sharedIDs
parameter_list|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|localEntries
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|BibEntry
name|localEntry
init|=
name|localEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|boolean
name|match
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|sharedID
range|:
name|sharedIDs
control|)
block|{
if|if
condition|(
name|localEntry
operator|.
name|getSharedBibEntryData
argument_list|()
operator|.
name|getSharedID
argument_list|()
operator|==
name|sharedID
condition|)
block|{
name|match
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|match
condition|)
block|{
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|SharedEntryNotPresentEvent
argument_list|(
name|localEntry
argument_list|)
argument_list|)
expr_stmt|;
name|bibDatabase
operator|.
name|removeEntry
argument_list|(
name|localEntry
argument_list|,
name|EntryEventSource
operator|.
name|SHARED
argument_list|)
expr_stmt|;
comment|// Should not reach the listeners above.
name|i
operator|--
expr_stmt|;
comment|// due to index shift on localEntries
block|}
block|}
block|}
comment|/**      * Synchronizes the shared {@link BibEntry} with the local one.      */
DECL|method|synchronizeSharedEntry (BibEntry bibEntry)
specifier|public
name|void
name|synchronizeSharedEntry
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
if|if
condition|(
operator|!
name|checkCurrentConnection
argument_list|()
condition|)
block|{
return|return;
block|}
try|try
block|{
name|BibDatabaseWriter
operator|.
name|applySaveActions
argument_list|(
name|bibEntry
argument_list|,
name|metaData
argument_list|)
expr_stmt|;
comment|// perform possibly existing save actions
name|dbmsProcessor
operator|.
name|updateEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OfflineLockException
name|exception
parameter_list|)
block|{
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|UpdateRefusedEvent
argument_list|(
name|bibDatabaseContext
argument_list|,
name|exception
operator|.
name|getLocalBibEntry
argument_list|()
argument_list|,
name|exception
operator|.
name|getSharedBibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SQL Error: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Synchronizes all meta data locally.      */
DECL|method|synchronizeLocalMetaData ()
specifier|public
name|void
name|synchronizeLocalMetaData
parameter_list|()
block|{
if|if
condition|(
operator|!
name|checkCurrentConnection
argument_list|()
condition|)
block|{
return|return;
block|}
try|try
block|{
name|metaData
operator|=
name|MetaDataParser
operator|.
name|parse
argument_list|(
name|dbmsProcessor
operator|.
name|getSharedMetaData
argument_list|()
argument_list|,
name|keywordSeparator
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Parse error"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Synchronizes all shared meta data.      */
DECL|method|synchronizeSharedMetaData (MetaData data, GlobalBibtexKeyPattern globalCiteKeyPattern)
specifier|private
name|void
name|synchronizeSharedMetaData
parameter_list|(
name|MetaData
name|data
parameter_list|,
name|GlobalBibtexKeyPattern
name|globalCiteKeyPattern
parameter_list|)
block|{
if|if
condition|(
operator|!
name|checkCurrentConnection
argument_list|()
condition|)
block|{
return|return;
block|}
try|try
block|{
name|dbmsProcessor
operator|.
name|setSharedMetaData
argument_list|(
name|MetaDataSerializer
operator|.
name|getSerializedStringMap
argument_list|(
name|data
argument_list|,
name|globalCiteKeyPattern
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SQL Error: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Applies the {@link MetaData} on all local and shared BibEntries.      */
DECL|method|applyMetaData ()
specifier|public
name|void
name|applyMetaData
parameter_list|()
block|{
if|if
condition|(
operator|!
name|checkCurrentConnection
argument_list|()
condition|)
block|{
return|return;
block|}
for|for
control|(
name|BibEntry
name|bibEntry
range|:
name|bibDatabase
operator|.
name|getEntries
argument_list|()
control|)
block|{
comment|// synchronize only if changes were present
if|if
condition|(
operator|!
name|BibDatabaseWriter
operator|.
name|applySaveActions
argument_list|(
name|bibEntry
argument_list|,
name|metaData
argument_list|)
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
try|try
block|{
name|dbmsProcessor
operator|.
name|updateEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|OfflineLockException
name|exception
parameter_list|)
block|{
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|UpdateRefusedEvent
argument_list|(
name|bibDatabaseContext
argument_list|,
name|exception
operator|.
name|getLocalBibEntry
argument_list|()
argument_list|,
name|exception
operator|.
name|getSharedBibEntry
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SQL Error: "
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * Synchronizes the local BibEntries and applies the fetched MetaData on them.      */
DECL|method|pullChanges ()
specifier|public
name|void
name|pullChanges
parameter_list|()
block|{
if|if
condition|(
operator|!
name|checkCurrentConnection
argument_list|()
condition|)
block|{
return|return;
block|}
name|synchronizeLocalDatabase
argument_list|()
expr_stmt|;
name|synchronizeLocalMetaData
argument_list|()
expr_stmt|;
block|}
comment|/**      *  Checks whether the current SQL connection is valid.      *  In case that the connection is not valid a new {@link ConnectionLostEvent} is going to be sent.      *      *  @return<code>true</code> if the connection is valid, else<code>false</code>.      */
DECL|method|checkCurrentConnection ()
specifier|public
name|boolean
name|checkCurrentConnection
parameter_list|()
block|{
try|try
block|{
name|boolean
name|isValid
init|=
name|currentConnection
operator|.
name|isValid
argument_list|(
literal|0
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|isValid
condition|)
block|{
name|eventBus
operator|.
name|post
argument_list|(
operator|new
name|ConnectionLostEvent
argument_list|(
name|bibDatabaseContext
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|isValid
return|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SQL Error:"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Checks whether the {@link EntryEventSource} of an {@link EntryEvent} is crucial for this class.      *      * @param event An {@link EntryEvent}      * @return<code>true</code> if the event is able to trigger operations in {@link DBMSSynchronizer}, else<code>false</code>      */
DECL|method|isEventSourceAccepted (EntryEvent event)
specifier|public
name|boolean
name|isEventSourceAccepted
parameter_list|(
name|EntryEvent
name|event
parameter_list|)
block|{
name|EntryEventSource
name|eventSource
init|=
name|event
operator|.
name|getEntryEventSource
argument_list|()
decl_stmt|;
return|return
operator|(
operator|(
name|eventSource
operator|==
name|EntryEventSource
operator|.
name|LOCAL
operator|)
operator|||
operator|(
name|eventSource
operator|==
name|EntryEventSource
operator|.
name|UNDO
operator|)
operator|)
return|;
block|}
DECL|method|openSharedDatabase (DBMSConnection connection)
specifier|public
name|void
name|openSharedDatabase
parameter_list|(
name|DBMSConnection
name|connection
parameter_list|)
throws|throws
name|DatabaseNotSupportedException
throws|,
name|SQLException
block|{
name|this
operator|.
name|dbmsType
operator|=
name|connection
operator|.
name|getProperties
argument_list|()
operator|.
name|getType
argument_list|()
expr_stmt|;
name|this
operator|.
name|dbName
operator|=
name|connection
operator|.
name|getProperties
argument_list|()
operator|.
name|getDatabase
argument_list|()
expr_stmt|;
name|this
operator|.
name|currentConnection
operator|=
name|connection
operator|.
name|getConnection
argument_list|()
expr_stmt|;
name|this
operator|.
name|dbmsProcessor
operator|=
name|DBMSProcessor
operator|.
name|getProcessorInstance
argument_list|(
name|connection
argument_list|)
expr_stmt|;
name|initializeDatabases
argument_list|()
expr_stmt|;
block|}
DECL|method|openSharedDatabase (DBMSConnectionProperties properties)
specifier|public
name|void
name|openSharedDatabase
parameter_list|(
name|DBMSConnectionProperties
name|properties
parameter_list|)
throws|throws
name|SQLException
throws|,
name|DatabaseNotSupportedException
block|{
name|openSharedDatabase
argument_list|(
operator|new
name|DBMSConnection
argument_list|(
name|properties
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|closeSharedDatabase ()
specifier|public
name|void
name|closeSharedDatabase
parameter_list|()
block|{
try|try
block|{
name|dbmsProcessor
operator|.
name|stopNotificationListener
argument_list|()
expr_stmt|;
name|currentConnection
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SQLException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"SQL Error:"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|isPresentLocalBibEntry (BibEntry bibEntry)
specifier|private
name|boolean
name|isPresentLocalBibEntry
parameter_list|(
name|BibEntry
name|bibEntry
parameter_list|)
block|{
return|return
name|bibDatabase
operator|.
name|getEntries
argument_list|()
operator|.
name|contains
argument_list|(
name|bibEntry
argument_list|)
return|;
block|}
DECL|method|getDBName ()
specifier|public
name|String
name|getDBName
parameter_list|()
block|{
return|return
name|dbName
return|;
block|}
DECL|method|getDBType ()
specifier|public
name|DBMSType
name|getDBType
parameter_list|()
block|{
return|return
name|this
operator|.
name|dbmsType
return|;
block|}
DECL|method|getDBProcessor ()
specifier|public
name|DBMSProcessor
name|getDBProcessor
parameter_list|()
block|{
return|return
name|dbmsProcessor
return|;
block|}
DECL|method|setMetaData (MetaData metaData)
specifier|public
name|void
name|setMetaData
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
block|}
DECL|method|registerListener (Object listener)
specifier|public
name|void
name|registerListener
parameter_list|(
name|Object
name|listener
parameter_list|)
block|{
name|eventBus
operator|.
name|register
argument_list|(
name|listener
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

