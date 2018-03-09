begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.collab
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|collab
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
name|util
operator|.
name|Comparator
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
name|Optional
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|tree
operator|.
name|DefaultMutableTreeNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefExecutorService
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
name|JabRefFrame
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
name|DuplicateCheck
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
name|comparator
operator|.
name|BibDatabaseDiff
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
name|comparator
operator|.
name|BibEntryDiff
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
name|comparator
operator|.
name|BibStringDiff
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
name|BibDatabaseWriter
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
name|BibtexDatabaseWriter
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
name|FileSaveSession
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
name|SaveException
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
name|SavePreferences
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
name|SaveSession
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
name|logic
operator|.
name|importer
operator|.
name|OpenDatabase
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
name|ParserResult
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
name|BibtexString
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|ChangeScanner
specifier|public
class|class
name|ChangeScanner
implements|implements
name|Runnable
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ChangeScanner
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|file
specifier|private
specifier|final
name|File
name|file
decl_stmt|;
DECL|field|tempFile
specifier|private
specifier|final
name|Path
name|tempFile
decl_stmt|;
DECL|field|databaseInMemory
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseInMemory
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|databaseInTemp
specifier|private
name|BibDatabaseContext
name|databaseInTemp
decl_stmt|;
comment|/**      * We create an ArrayList to hold the changes we find. These will be added in the form      * of UndoEdit objects. We instantiate these so that the changes found in the file on disk      * can be reproduced in memory by calling redo() on them. REDO, not UNDO!      */
DECL|field|changes
specifier|private
specifier|final
name|DefaultMutableTreeNode
name|changes
init|=
operator|new
name|DefaultMutableTreeNode
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"External changes"
argument_list|)
argument_list|)
decl_stmt|;
comment|//  NamedCompound edit = new NamedCompound("Merged external changes")
DECL|method|ChangeScanner (JabRefFrame frame, BasePanel bp, File file, Path tempFile)
specifier|public
name|ChangeScanner
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|bp
parameter_list|,
name|File
name|file
parameter_list|,
name|Path
name|tempFile
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|bp
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|databaseInMemory
operator|=
name|bp
operator|.
name|getDatabaseContext
argument_list|()
expr_stmt|;
name|this
operator|.
name|file
operator|=
name|file
expr_stmt|;
name|this
operator|.
name|tempFile
operator|=
name|tempFile
expr_stmt|;
block|}
DECL|method|changesFound ()
specifier|public
name|boolean
name|changesFound
parameter_list|()
block|{
return|return
name|changes
operator|.
name|getChildCount
argument_list|()
operator|>
literal|0
return|;
block|}
comment|/**      * Finds the entry in the list best fitting the specified entry. Even if no entries get a score above zero, an entry      * is still returned.      */
DECL|method|bestFit (BibEntry targetEntry, List<BibEntry> entries)
specifier|private
specifier|static
name|BibEntry
name|bestFit
parameter_list|(
name|BibEntry
name|targetEntry
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
return|return
name|entries
operator|.
name|stream
argument_list|()
operator|.
name|max
argument_list|(
name|Comparator
operator|.
name|comparingDouble
argument_list|(
name|candidate
lambda|->
name|DuplicateCheck
operator|.
name|compareEntriesStrictly
argument_list|(
name|targetEntry
argument_list|,
name|candidate
argument_list|)
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
return|;
block|}
DECL|method|displayResult (final DisplayResultCallback fup)
specifier|public
name|void
name|displayResult
parameter_list|(
specifier|final
name|DisplayResultCallback
name|fup
parameter_list|)
block|{
if|if
condition|(
name|changes
operator|.
name|getChildCount
argument_list|()
operator|>
literal|0
condition|)
block|{
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
name|ChangeDisplayDialog
name|changeDialog
init|=
operator|new
name|ChangeDisplayDialog
argument_list|(
literal|null
argument_list|,
name|panel
argument_list|,
name|databaseInTemp
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|changes
argument_list|)
decl_stmt|;
name|changeDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|fup
operator|.
name|scanResultsResolved
argument_list|(
name|changeDialog
operator|.
name|isOkPressed
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|changeDialog
operator|.
name|isOkPressed
argument_list|()
condition|)
block|{
comment|// Overwrite the temp database:
name|storeTempDatabase
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|frame
operator|.
name|getDialogService
argument_list|()
operator|.
name|showInformationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"External changes"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"No actual changes found."
argument_list|)
argument_list|)
expr_stmt|;
name|fup
operator|.
name|scanResultsResolved
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|storeTempDatabase ()
specifier|private
name|void
name|storeTempDatabase
parameter_list|()
block|{
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
block|{
try|try
block|{
name|SavePreferences
name|prefs
init|=
name|SavePreferences
operator|.
name|loadForSaveFromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
operator|.
name|withMakeBackup
argument_list|(
literal|false
argument_list|)
operator|.
name|withEncoding
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getEncoding
argument_list|()
operator|.
name|orElse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|BibDatabaseWriter
argument_list|<
name|SaveSession
argument_list|>
name|databaseWriter
init|=
operator|new
name|BibtexDatabaseWriter
argument_list|<>
argument_list|(
name|FileSaveSession
operator|::
operator|new
argument_list|)
decl_stmt|;
name|SaveSession
name|ss
init|=
name|databaseWriter
operator|.
name|saveDatabase
argument_list|(
name|databaseInTemp
argument_list|,
name|prefs
argument_list|)
decl_stmt|;
name|ss
operator|.
name|commit
argument_list|(
name|tempFile
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SaveException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem updating tmp file after accepting external changes"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
comment|// Parse the temporary file.
name|ImportFormatPreferences
name|importFormatPreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
decl_stmt|;
name|ParserResult
name|result
init|=
name|OpenDatabase
operator|.
name|loadDatabase
argument_list|(
name|tempFile
operator|.
name|toFile
argument_list|()
argument_list|,
name|importFormatPreferences
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
decl_stmt|;
name|databaseInTemp
operator|=
name|result
operator|.
name|getDatabaseContext
argument_list|()
expr_stmt|;
comment|// Parse the modified file.
name|result
operator|=
name|OpenDatabase
operator|.
name|loadDatabase
argument_list|(
name|file
argument_list|,
name|importFormatPreferences
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabaseContext
name|databaseOnDisk
init|=
name|result
operator|.
name|getDatabaseContext
argument_list|()
decl_stmt|;
comment|// Start looking at changes.
name|BibDatabaseDiff
name|differences
init|=
name|BibDatabaseDiff
operator|.
name|compare
argument_list|(
name|databaseInTemp
argument_list|,
name|databaseOnDisk
argument_list|)
decl_stmt|;
name|differences
operator|.
name|getMetaDataDifferences
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|diff
lambda|->
block|{
name|changes
operator|.
name|add
argument_list|(
operator|new
name|MetaDataChangeViewModel
argument_list|(
name|diff
argument_list|)
argument_list|)
expr_stmt|;
name|diff
operator|.
name|getGroupDifferences
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|groupDiff
lambda|->
name|changes
operator|.
name|add
argument_list|(
operator|new
name|GroupChangeViewModel
argument_list|(
name|groupDiff
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|differences
operator|.
name|getPreambleDifferences
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|diff
lambda|->
name|changes
operator|.
name|add
argument_list|(
operator|new
name|PreambleChangeViewModel
argument_list|(
name|databaseInMemory
operator|.
name|getDatabase
argument_list|()
operator|.
name|getPreamble
argument_list|()
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|,
name|diff
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|differences
operator|.
name|getBibStringDifferences
argument_list|()
operator|.
name|forEach
argument_list|(
name|diff
lambda|->
name|changes
operator|.
name|add
argument_list|(
name|createBibStringDiff
argument_list|(
name|diff
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|differences
operator|.
name|getEntryDifferences
argument_list|()
operator|.
name|forEach
argument_list|(
name|diff
lambda|->
name|changes
operator|.
name|add
argument_list|(
name|createBibEntryDiff
argument_list|(
name|diff
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem running"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|createBibStringDiff (BibStringDiff diff)
specifier|private
name|ChangeViewModel
name|createBibStringDiff
parameter_list|(
name|BibStringDiff
name|diff
parameter_list|)
block|{
if|if
condition|(
name|diff
operator|.
name|getOriginalString
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|StringAddChangeViewModel
argument_list|(
name|diff
operator|.
name|getNewString
argument_list|()
argument_list|)
return|;
block|}
if|if
condition|(
name|diff
operator|.
name|getNewString
argument_list|()
operator|==
literal|null
condition|)
block|{
name|Optional
argument_list|<
name|BibtexString
argument_list|>
name|current
init|=
name|databaseInMemory
operator|.
name|getDatabase
argument_list|()
operator|.
name|getStringByName
argument_list|(
name|diff
operator|.
name|getOriginalString
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|StringRemoveChangeViewModel
argument_list|(
name|diff
operator|.
name|getOriginalString
argument_list|()
argument_list|,
name|current
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
return|;
block|}
if|if
condition|(
name|diff
operator|.
name|getOriginalString
argument_list|()
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|diff
operator|.
name|getNewString
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|Optional
argument_list|<
name|BibtexString
argument_list|>
name|current
init|=
name|databaseInMemory
operator|.
name|getDatabase
argument_list|()
operator|.
name|getStringByName
argument_list|(
name|diff
operator|.
name|getOriginalString
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|StringChangeViewModel
argument_list|(
name|current
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|diff
operator|.
name|getOriginalString
argument_list|()
argument_list|,
name|diff
operator|.
name|getNewString
argument_list|()
operator|.
name|getContent
argument_list|()
argument_list|)
return|;
block|}
name|Optional
argument_list|<
name|BibtexString
argument_list|>
name|current
init|=
name|databaseInMemory
operator|.
name|getDatabase
argument_list|()
operator|.
name|getStringByName
argument_list|(
name|diff
operator|.
name|getOriginalString
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|StringNameChangeViewModel
argument_list|(
name|current
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|,
name|diff
operator|.
name|getOriginalString
argument_list|()
argument_list|,
name|current
operator|.
name|map
argument_list|(
name|BibtexString
operator|::
name|getName
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|,
name|diff
operator|.
name|getNewString
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
return|;
block|}
DECL|method|createBibEntryDiff (BibEntryDiff diff)
specifier|private
name|ChangeViewModel
name|createBibEntryDiff
parameter_list|(
name|BibEntryDiff
name|diff
parameter_list|)
block|{
if|if
condition|(
name|diff
operator|.
name|getOriginalEntry
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|EntryAddChangeViewModel
argument_list|(
name|diff
operator|.
name|getNewEntry
argument_list|()
argument_list|)
return|;
block|}
if|if
condition|(
name|diff
operator|.
name|getNewEntry
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|EntryDeleteChangeViewModel
argument_list|(
name|bestFit
argument_list|(
name|diff
operator|.
name|getOriginalEntry
argument_list|()
argument_list|,
name|databaseInMemory
operator|.
name|getEntries
argument_list|()
argument_list|)
argument_list|,
name|diff
operator|.
name|getOriginalEntry
argument_list|()
argument_list|)
return|;
block|}
return|return
operator|new
name|EntryChangeViewModel
argument_list|(
name|bestFit
argument_list|(
name|diff
operator|.
name|getOriginalEntry
argument_list|()
argument_list|,
name|databaseInMemory
operator|.
name|getEntries
argument_list|()
argument_list|)
argument_list|,
name|diff
operator|.
name|getOriginalEntry
argument_list|()
argument_list|,
name|diff
operator|.
name|getNewEntry
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|FunctionalInterface
DECL|interface|DisplayResultCallback
specifier|public
interface|interface
name|DisplayResultCallback
block|{
DECL|method|scanResultsResolved (boolean resolved)
name|void
name|scanResultsResolved
parameter_list|(
name|boolean
name|resolved
parameter_list|)
function_decl|;
block|}
block|}
end_class

end_unit

