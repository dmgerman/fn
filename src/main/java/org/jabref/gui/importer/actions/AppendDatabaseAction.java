begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
operator|.
name|actions
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
name|ArrayList
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
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|CompoundEdit
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
name|MergeDialog
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
name|BaseAction
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
name|NamedCompound
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
name|UndoableInsertEntry
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
name|UndoableInsertString
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
name|util
operator|.
name|DefaultTaskExecutor
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
name|util
operator|.
name|FileDialogConfiguration
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
name|logic
operator|.
name|util
operator|.
name|FileType
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
name|util
operator|.
name|UpdateField
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
name|KeyCollisionException
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
name|jabref
operator|.
name|model
operator|.
name|groups
operator|.
name|AllEntriesGroup
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
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|GroupHierarchyType
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
name|groups
operator|.
name|GroupTreeNode
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
name|ContentSelector
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
name|preferences
operator|.
name|JabRefPreferences
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
DECL|class|AppendDatabaseAction
specifier|public
class|class
name|AppendDatabaseAction
implements|implements
name|BaseAction
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
name|AppendDatabaseAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|filesToOpen
specifier|private
specifier|final
name|List
argument_list|<
name|Path
argument_list|>
name|filesToOpen
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|AppendDatabaseAction (JabRefFrame frame, BasePanel panel)
specifier|public
name|AppendDatabaseAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|mergeFromBibtex (BasePanel panel, ParserResult parserResult, boolean importEntries, boolean importStrings, boolean importGroups, boolean importSelectorWords)
specifier|private
specifier|static
name|void
name|mergeFromBibtex
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|parserResult
parameter_list|,
name|boolean
name|importEntries
parameter_list|,
name|boolean
name|importStrings
parameter_list|,
name|boolean
name|importGroups
parameter_list|,
name|boolean
name|importSelectorWords
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
name|BibDatabase
name|fromDatabase
init|=
name|parserResult
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|appendedEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|originalEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|BibDatabase
name|database
init|=
name|panel
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Append library"
argument_list|)
argument_list|)
decl_stmt|;
name|MetaData
name|meta
init|=
name|parserResult
operator|.
name|getMetaData
argument_list|()
decl_stmt|;
if|if
condition|(
name|importEntries
condition|)
block|{
comment|// Add entries
name|boolean
name|overwriteOwner
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERWRITE_OWNER
argument_list|)
decl_stmt|;
name|boolean
name|overwriteTimeStamp
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getTimestampPreferences
argument_list|()
operator|.
name|overwriteTimestamp
argument_list|()
decl_stmt|;
for|for
control|(
name|BibEntry
name|originalEntry
range|:
name|fromDatabase
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|BibEntry
name|entry
init|=
operator|(
name|BibEntry
operator|)
name|originalEntry
operator|.
name|clone
argument_list|()
decl_stmt|;
name|UpdateField
operator|.
name|setAutomaticFields
argument_list|(
name|entry
argument_list|,
name|overwriteOwner
argument_list|,
name|overwriteTimeStamp
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getUpdateFieldPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|appendedEntries
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|originalEntries
operator|.
name|add
argument_list|(
name|originalEntry
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|database
argument_list|,
name|entry
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|importStrings
condition|)
block|{
for|for
control|(
name|BibtexString
name|bs
range|:
name|fromDatabase
operator|.
name|getStringValues
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|database
operator|.
name|hasStringLabel
argument_list|(
name|bs
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|database
operator|.
name|addString
argument_list|(
name|bs
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertString
argument_list|(
name|panel
argument_list|,
name|database
argument_list|,
name|bs
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|importGroups
condition|)
block|{
name|meta
operator|.
name|getGroups
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|newGroups
lambda|->
block|{
comment|// ensure that there is always only one AllEntriesGroup
if|if
condition|(
name|newGroups
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
condition|)
block|{
comment|// create a dummy group
try|try
block|{
name|ExplicitGroup
name|group
init|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"Imported"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKeywordDelimiter
argument_list|()
argument_list|)
decl_stmt|;
name|newGroups
operator|.
name|setGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|group
operator|.
name|add
argument_list|(
name|appendedEntries
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalArgumentException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem appending entries to group"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
name|addGroups
argument_list|(
name|newGroups
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|importSelectorWords
condition|)
block|{
for|for
control|(
name|ContentSelector
name|selector
range|:
name|meta
operator|.
name|getContentSelectorList
argument_list|()
control|)
block|{
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|addContentSelector
argument_list|(
name|selector
argument_list|)
expr_stmt|;
block|}
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
comment|/**      * Adds the specified node as a child of the current root. The group contained in<b>newGroups</b> must not be of      * type AllEntriesGroup, since every tree has exactly one AllEntriesGroup (its root). The<b>newGroups</b> are      * inserted directly, i.e. they are not deepCopy()'d.      */
DECL|method|addGroups (GroupTreeNode newGroups, CompoundEdit ce)
specifier|private
specifier|static
name|void
name|addGroups
parameter_list|(
name|GroupTreeNode
name|newGroups
parameter_list|,
name|CompoundEdit
name|ce
parameter_list|)
block|{
comment|// paranoia: ensure that there are never two instances of AllEntriesGroup
if|if
condition|(
name|newGroups
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
condition|)
block|{
return|return;
comment|// this should be impossible anyway
block|}
name|Globals
operator|.
name|stateManager
operator|.
name|getActiveDatabase
argument_list|()
operator|.
name|map
argument_list|(
name|BibDatabaseContext
operator|::
name|getMetaData
argument_list|)
operator|.
name|flatMap
argument_list|(
name|MetaData
operator|::
name|getGroups
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|newGroups
operator|::
name|moveTo
argument_list|)
expr_stmt|;
comment|//UndoableAddOrRemoveGroup undo = new UndoableAddOrRemoveGroup(groupsRoot,
comment|//        new GroupTreeNodeViewModel(newGroups), UndoableAddOrRemoveGroup.ADD_NODE);
comment|//ce.addEdit(undo);
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
name|filesToOpen
operator|.
name|clear
argument_list|()
expr_stmt|;
specifier|final
name|MergeDialog
name|dialog
init|=
operator|new
name|MergeDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Append library"
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|dialog
operator|.
name|isOkPressed
argument_list|()
condition|)
block|{
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withDefaultExtension
argument_list|(
name|FileType
operator|.
name|BIBTEX_DB
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|DialogService
name|dialogService
init|=
operator|new
name|FXDialogService
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|Path
argument_list|>
name|chosen
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|dialogService
operator|.
name|showFileOpenDialogAndGetMultipleFiles
argument_list|(
name|fileDialogConfiguration
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|chosen
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
name|filesToOpen
operator|.
name|addAll
argument_list|(
name|chosen
argument_list|)
expr_stmt|;
comment|// Run the actual open in a thread to prevent the program
comment|// locking until the file is loaded.
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
name|openIt
argument_list|(
name|dialog
operator|.
name|importEntries
argument_list|()
argument_list|,
name|dialog
operator|.
name|importStrings
argument_list|()
argument_list|,
name|dialog
operator|.
name|importGroups
argument_list|()
argument_list|,
name|dialog
operator|.
name|importSelectorWords
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openIt (boolean importEntries, boolean importStrings, boolean importGroups, boolean importSelectorWords)
specifier|private
name|void
name|openIt
parameter_list|(
name|boolean
name|importEntries
parameter_list|,
name|boolean
name|importStrings
parameter_list|,
name|boolean
name|importGroups
parameter_list|,
name|boolean
name|importSelectorWords
parameter_list|)
block|{
if|if
condition|(
name|filesToOpen
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
for|for
control|(
name|Path
name|file
range|:
name|filesToOpen
control|)
block|{
try|try
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|,
name|file
operator|.
name|getParent
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
comment|// Should this be done _after_ we know it was successfully opened?
name|ParserResult
name|parserResult
init|=
name|OpenDatabase
operator|.
name|loadDatabase
argument_list|(
name|file
operator|.
name|toFile
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
decl_stmt|;
name|AppendDatabaseAction
operator|.
name|mergeFromBibtex
argument_list|(
name|panel
argument_list|,
name|parserResult
argument_list|,
name|importEntries
argument_list|,
name|importStrings
argument_list|,
name|importGroups
argument_list|,
name|importSelectorWords
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Imported from library"
argument_list|)
operator|+
literal|" '"
operator|+
name|file
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not open database"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open library"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

