begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.externalfiles
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|externalfiles
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
name|javax
operator|.
name|swing
operator|.
name|undo
operator|.
name|UndoManager
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
name|StateManager
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|logic
operator|.
name|bibtexkeypattern
operator|.
name|BibtexKeyGenerator
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
name|externalfiles
operator|.
name|ExternalFilesContentImporter
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
name|logic
operator|.
name|util
operator|.
name|UpdateFieldPreferences
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
name|io
operator|.
name|FileUtil
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
name|FieldChange
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
name|field
operator|.
name|StandardField
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
name|GroupEntryChanger
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
name|FilePreferences
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
name|FileUpdateMonitor
import|;
end_import

begin_class
DECL|class|ImportHandler
specifier|public
class|class
name|ImportHandler
block|{
DECL|field|database
specifier|private
specifier|final
name|BibDatabaseContext
name|database
decl_stmt|;
DECL|field|updateFieldPreferences
specifier|private
specifier|final
name|UpdateFieldPreferences
name|updateFieldPreferences
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|fileUpdateMonitor
specifier|private
specifier|final
name|FileUpdateMonitor
name|fileUpdateMonitor
decl_stmt|;
DECL|field|linker
specifier|private
specifier|final
name|ExternalFilesEntryLinker
name|linker
decl_stmt|;
DECL|field|contentImporter
specifier|private
specifier|final
name|ExternalFilesContentImporter
name|contentImporter
decl_stmt|;
DECL|field|undoManager
specifier|private
specifier|final
name|UndoManager
name|undoManager
decl_stmt|;
DECL|field|stateManager
specifier|private
specifier|final
name|StateManager
name|stateManager
decl_stmt|;
DECL|method|ImportHandler (DialogService dialogService, BibDatabaseContext database, ExternalFileTypes externalFileTypes, FilePreferences filePreferences, ImportFormatPreferences importFormatPreferences, UpdateFieldPreferences updateFieldPreferences, FileUpdateMonitor fileupdateMonitor, UndoManager undoManager, StateManager stateManager)
specifier|public
name|ImportHandler
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|BibDatabaseContext
name|database
parameter_list|,
name|ExternalFileTypes
name|externalFileTypes
parameter_list|,
name|FilePreferences
name|filePreferences
parameter_list|,
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|,
name|UpdateFieldPreferences
name|updateFieldPreferences
parameter_list|,
name|FileUpdateMonitor
name|fileupdateMonitor
parameter_list|,
name|UndoManager
name|undoManager
parameter_list|,
name|StateManager
name|stateManager
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
name|this
operator|.
name|updateFieldPreferences
operator|=
name|updateFieldPreferences
expr_stmt|;
name|this
operator|.
name|fileUpdateMonitor
operator|=
name|fileupdateMonitor
expr_stmt|;
name|this
operator|.
name|stateManager
operator|=
name|stateManager
expr_stmt|;
name|this
operator|.
name|linker
operator|=
operator|new
name|ExternalFilesEntryLinker
argument_list|(
name|externalFileTypes
argument_list|,
name|filePreferences
argument_list|,
name|database
argument_list|)
expr_stmt|;
name|this
operator|.
name|contentImporter
operator|=
operator|new
name|ExternalFilesContentImporter
argument_list|(
name|importFormatPreferences
argument_list|)
expr_stmt|;
name|this
operator|.
name|undoManager
operator|=
name|undoManager
expr_stmt|;
block|}
DECL|method|getLinker ()
specifier|public
name|ExternalFilesEntryLinker
name|getLinker
parameter_list|()
block|{
return|return
name|linker
return|;
block|}
DECL|method|importAsNewEntries (List<Path> files)
specifier|public
name|void
name|importAsNewEntries
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|files
parameter_list|)
block|{
name|CompoundEdit
name|ce
init|=
operator|new
name|CompoundEdit
argument_list|()
decl_stmt|;
for|for
control|(
name|Path
name|file
range|:
name|files
control|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToAdd
decl_stmt|;
if|if
condition|(
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
name|file
argument_list|)
operator|.
name|filter
argument_list|(
literal|"pdf"
operator|::
name|equals
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|pdfResult
init|=
name|contentImporter
operator|.
name|importPDFContent
argument_list|(
name|file
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|xmpEntriesInFile
init|=
name|contentImporter
operator|.
name|importXMPContent
argument_list|(
name|file
argument_list|)
decl_stmt|;
comment|// First try xmp import, if empty try pdf import, otherwise create empty entry
if|if
condition|(
operator|!
name|xmpEntriesInFile
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|pdfResult
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|//FIXME: Show merge dialog?
name|entriesToAdd
operator|=
name|xmpEntriesInFile
expr_stmt|;
block|}
else|else
block|{
name|entriesToAdd
operator|=
name|xmpEntriesInFile
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
operator|!
name|pdfResult
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|entriesToAdd
operator|=
name|pdfResult
expr_stmt|;
block|}
else|else
block|{
name|entriesToAdd
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|createEmptyEntryWithLink
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
elseif|else
if|if
condition|(
name|FileUtil
operator|.
name|isBibFile
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|entriesToAdd
operator|=
name|contentImporter
operator|.
name|importFromBibFile
argument_list|(
name|file
argument_list|,
name|fileUpdateMonitor
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entriesToAdd
operator|=
name|Collections
operator|.
name|singletonList
argument_list|(
name|createEmptyEntryWithLink
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|importEntries
argument_list|(
name|entriesToAdd
argument_list|)
expr_stmt|;
name|entriesToAdd
operator|.
name|forEach
argument_list|(
name|entry
lambda|->
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
block|}
DECL|method|createEmptyEntryWithLink (Path file)
specifier|private
name|BibEntry
name|createEmptyEntryWithLink
parameter_list|(
name|Path
name|file
parameter_list|)
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
name|StandardField
operator|.
name|TITLE
argument_list|,
name|file
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|linker
operator|.
name|addFilesToEntry
argument_list|(
name|entry
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|file
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|entry
return|;
block|}
DECL|method|importEntries (List<BibEntry> entries)
specifier|public
name|void
name|importEntries
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
comment|//TODO: Add undo/redo
comment|//ce.addEdit(new UndoableInsertEntry(panel.getDatabase(), entry));
name|database
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntries
argument_list|(
name|entries
argument_list|)
expr_stmt|;
comment|// Set owner/timestamp
name|UpdateField
operator|.
name|setAutomaticFields
argument_list|(
name|entries
argument_list|,
name|updateFieldPreferences
argument_list|)
expr_stmt|;
comment|// Generate bibtex keys
name|generateKeys
argument_list|(
name|entries
argument_list|)
expr_stmt|;
comment|// Add to group
name|addToGroups
argument_list|(
name|entries
argument_list|,
name|stateManager
operator|.
name|getSelectedGroup
argument_list|(
name|database
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|addToGroups (List<BibEntry> entries, Collection<GroupTreeNode> groups)
specifier|private
name|void
name|addToGroups
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|Collection
argument_list|<
name|GroupTreeNode
argument_list|>
name|groups
parameter_list|)
block|{
for|for
control|(
name|GroupTreeNode
name|node
range|:
name|groups
control|)
block|{
if|if
condition|(
name|node
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|GroupEntryChanger
condition|)
block|{
name|GroupEntryChanger
name|entryChanger
init|=
operator|(
name|GroupEntryChanger
operator|)
name|node
operator|.
name|getGroup
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|FieldChange
argument_list|>
name|undo
init|=
name|entryChanger
operator|.
name|add
argument_list|(
name|entries
argument_list|)
decl_stmt|;
comment|// TODO: Add undo
comment|//if (!undo.isEmpty()) {
comment|//    ce.addEdit(UndoableChangeEntriesOfGroup.getUndoableEdit(new GroupTreeNodeViewModel(node),
comment|//            undo));
comment|//}
block|}
block|}
block|}
comment|/**      * Generate keys for given entries.      *      * @param entries entries to generate keys for      */
DECL|method|generateKeys (List<BibEntry> entries)
specifier|private
name|void
name|generateKeys
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|BibtexKeyGenerator
name|keyGenerator
init|=
operator|new
name|BibtexKeyGenerator
argument_list|(
name|database
operator|.
name|getMetaData
argument_list|()
operator|.
name|getCiteKeyPattern
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBibtexKeyPatternPreferences
argument_list|()
operator|.
name|getKeyPattern
argument_list|()
argument_list|)
argument_list|,
name|database
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBibtexKeyPatternPreferences
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|entry
range|:
name|entries
control|)
block|{
name|keyGenerator
operator|.
name|generateAndSetKey
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

