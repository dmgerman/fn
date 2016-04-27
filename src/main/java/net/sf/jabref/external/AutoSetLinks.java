begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Map
operator|.
name|Entry
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JProgressBar
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
name|JabRefPreferences
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
name|gui
operator|.
name|FileListEntry
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
name|gui
operator|.
name|FileListTableModel
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
name|gui
operator|.
name|undo
operator|.
name|NamedCompound
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
name|gui
operator|.
name|undo
operator|.
name|UndoableFieldChange
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
name|l10n
operator|.
name|Localization
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
name|util
operator|.
name|io
operator|.
name|FileUtil
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

begin_class
DECL|class|AutoSetLinks
specifier|public
class|class
name|AutoSetLinks
block|{
comment|/**      * Shortcut method if links are set without using the GUI      *      * @param entries  the entries for which links should be set      * @param databaseContext the database for which links are set      */
DECL|method|autoSetLinks (Collection<BibEntry> entries, BibDatabaseContext databaseContext)
specifier|public
specifier|static
name|void
name|autoSetLinks
parameter_list|(
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|autoSetLinks
argument_list|(
name|entries
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|databaseContext
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Automatically add links for this set of entries, based on the globally stored list of external file types. The      * entries are modified, and corresponding UndoEdit elements added to the NamedCompound given as argument.      * Furthermore, all entries which are modified are added to the Set of entries given as an argument.      *<p>      * The entries' bibtex keys must have been set - entries lacking key are ignored. The operation is done in a new      * thread, which is returned for the caller to wait for if needed.      *      * @param entries          A collection of BibEntry objects to find links for.      * @param ce               A NamedCompound to add UndoEdit elements to.      * @param changedEntries   MODIFIED, optional. A Set of BibEntry objects to which all modified entries is added.      *                         This is used for status output and debugging      * @param singleTableModel UGLY HACK. The table model to insert links into. Already existing links are not      *                         duplicated or removed. This parameter has to be null if entries.count() != 1. The hack has been      *                         introduced as a bibtexentry does not (yet) support the function getListTableModel() and the      *                         FileListEntryEditor editor holds an instance of that table model and does not reconstruct it after the      *                         search has succeeded.      * @param databaseContext  The database providing the relevant file directory, if any.      * @param callback         An ActionListener that is notified (on the event dispatch thread) when the search is finished.      *                         The ActionEvent has id=0 if no new links were added, and id=1 if one or more links were added. This      *                         parameter can be null, which means that no callback will be notified.      * @param diag             An instantiated modal JDialog which will be used to display the progress of the automatically setting. This      *                         parameter can be null, which means that no progress update will be shown.      * @return the thread performing the automatically setting      */
DECL|method|autoSetLinks (final Collection<BibEntry> entries, final NamedCompound ce, final Set<BibEntry> changedEntries, final FileListTableModel singleTableModel, final BibDatabaseContext databaseContext, final ActionListener callback, final JDialog diag)
specifier|public
specifier|static
name|Runnable
name|autoSetLinks
parameter_list|(
specifier|final
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
specifier|final
name|NamedCompound
name|ce
parameter_list|,
specifier|final
name|Set
argument_list|<
name|BibEntry
argument_list|>
name|changedEntries
parameter_list|,
specifier|final
name|FileListTableModel
name|singleTableModel
parameter_list|,
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
specifier|final
name|ActionListener
name|callback
parameter_list|,
specifier|final
name|JDialog
name|diag
parameter_list|)
block|{
specifier|final
name|Collection
argument_list|<
name|ExternalFileType
argument_list|>
name|types
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeSelection
argument_list|()
decl_stmt|;
if|if
condition|(
name|diag
operator|!=
literal|null
condition|)
block|{
specifier|final
name|JProgressBar
name|prog
init|=
operator|new
name|JProgressBar
argument_list|(
name|JProgressBar
operator|.
name|HORIZONTAL
argument_list|,
literal|0
argument_list|,
name|types
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
specifier|final
name|JLabel
name|label
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Searching for files"
argument_list|)
argument_list|)
decl_stmt|;
name|prog
operator|.
name|setIndeterminate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|prog
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Automatically setting file links"
argument_list|)
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|prog
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|diag
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|label
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|diag
operator|.
name|pack
argument_list|()
expr_stmt|;
name|diag
operator|.
name|setLocationRelativeTo
argument_list|(
name|diag
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Runnable
name|r
init|=
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// determine directories to search in
name|List
argument_list|<
name|File
argument_list|>
name|dirs
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|dirsS
init|=
name|databaseContext
operator|.
name|getFileDirectory
argument_list|()
decl_stmt|;
name|dirs
operator|.
name|addAll
argument_list|(
name|dirsS
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|File
operator|::
operator|new
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// determine extensions
name|Collection
argument_list|<
name|String
argument_list|>
name|extensions
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
specifier|final
name|ExternalFileType
name|type
range|:
name|types
control|)
block|{
name|extensions
operator|.
name|add
argument_list|(
name|type
operator|.
name|getExtension
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Run the search operation:
name|Map
argument_list|<
name|BibEntry
argument_list|,
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
name|result
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|AUTOLINK_USE_REG_EXP_SEARCH_KEY
argument_list|)
condition|)
block|{
name|String
name|regExp
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
decl_stmt|;
name|result
operator|=
name|RegExpFileSearch
operator|.
name|findFilesForSet
argument_list|(
name|entries
argument_list|,
name|extensions
argument_list|,
name|dirs
argument_list|,
name|regExp
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|result
operator|=
name|FileUtil
operator|.
name|findAssociatedFiles
argument_list|(
name|entries
argument_list|,
name|extensions
argument_list|,
name|dirs
argument_list|)
expr_stmt|;
block|}
name|boolean
name|foundAny
init|=
literal|false
decl_stmt|;
comment|// Iterate over the entries:
for|for
control|(
name|Entry
argument_list|<
name|BibEntry
argument_list|,
name|List
argument_list|<
name|File
argument_list|>
argument_list|>
name|entryFilePair
range|:
name|result
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|FileListTableModel
name|tableModel
decl_stmt|;
name|String
name|oldVal
init|=
name|entryFilePair
operator|.
name|getKey
argument_list|()
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|singleTableModel
operator|==
literal|null
condition|)
block|{
name|tableModel
operator|=
operator|new
name|FileListTableModel
argument_list|()
expr_stmt|;
if|if
condition|(
name|oldVal
operator|!=
literal|null
condition|)
block|{
name|tableModel
operator|.
name|setContent
argument_list|(
name|oldVal
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
assert|assert
name|entries
operator|.
name|size
argument_list|()
operator|==
literal|1
assert|;
name|tableModel
operator|=
name|singleTableModel
expr_stmt|;
block|}
name|List
argument_list|<
name|File
argument_list|>
name|files
init|=
name|entryFilePair
operator|.
name|getValue
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|f
range|:
name|files
control|)
block|{
name|f
operator|=
name|FileUtil
operator|.
name|shortenFileName
argument_list|(
name|f
argument_list|,
name|dirsS
argument_list|)
expr_stmt|;
name|boolean
name|alreadyHas
init|=
literal|false
decl_stmt|;
comment|//System.out.println("File: "+f.getPath());
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|tableModel
operator|.
name|getRowCount
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|FileListEntry
name|existingEntry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|j
argument_list|)
decl_stmt|;
comment|//System.out.println("Comp: "+existingEntry.getLink());
if|if
condition|(
operator|new
name|File
argument_list|(
name|existingEntry
operator|.
name|link
argument_list|)
operator|.
name|equals
argument_list|(
name|f
argument_list|)
condition|)
block|{
name|alreadyHas
operator|=
literal|true
expr_stmt|;
name|foundAny
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|alreadyHas
condition|)
block|{
name|foundAny
operator|=
literal|true
expr_stmt|;
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|type
decl_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|extension
init|=
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
name|f
argument_list|)
decl_stmt|;
if|if
condition|(
name|extension
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|type
operator|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByExt
argument_list|(
name|extension
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|type
operator|=
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|UnknownExternalFileType
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|FileListEntry
name|flEntry
init|=
operator|new
name|FileListEntry
argument_list|(
name|f
operator|.
name|getName
argument_list|()
argument_list|,
name|f
operator|.
name|getPath
argument_list|()
argument_list|,
name|type
argument_list|)
decl_stmt|;
name|tableModel
operator|.
name|addEntry
argument_list|(
name|tableModel
operator|.
name|getRowCount
argument_list|()
argument_list|,
name|flEntry
argument_list|)
expr_stmt|;
name|String
name|newVal
init|=
name|tableModel
operator|.
name|getStringRepresentation
argument_list|()
decl_stmt|;
if|if
condition|(
name|newVal
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|newVal
operator|=
literal|null
expr_stmt|;
block|}
if|if
condition|(
name|ce
operator|!=
literal|null
condition|)
block|{
comment|// store undo information
name|UndoableFieldChange
name|change
init|=
operator|new
name|UndoableFieldChange
argument_list|(
name|entryFilePair
operator|.
name|getKey
argument_list|()
argument_list|,
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
name|oldVal
argument_list|,
name|newVal
argument_list|)
decl_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
name|change
argument_list|)
expr_stmt|;
block|}
comment|// hack: if table model is given, do NOT modify entry
if|if
condition|(
name|singleTableModel
operator|==
literal|null
condition|)
block|{
name|entryFilePair
operator|.
name|getKey
argument_list|()
operator|.
name|setField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
name|newVal
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|changedEntries
operator|!=
literal|null
condition|)
block|{
name|changedEntries
operator|.
name|add
argument_list|(
name|entryFilePair
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|// handle callbacks and dialog
comment|// FIXME: The ID signals if action was successful :/
specifier|final
name|int
name|id
init|=
name|foundAny
condition|?
literal|1
else|:
literal|0
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
name|diag
operator|!=
literal|null
condition|)
block|{
name|diag
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|callback
operator|!=
literal|null
condition|)
block|{
name|callback
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|this
argument_list|,
name|id
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
block|{
comment|// show dialog which will be hidden when the task is done
if|if
condition|(
name|diag
operator|!=
literal|null
condition|)
block|{
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
return|return
name|r
return|;
block|}
comment|/**      * Automatically add links for this entry to the table model given as an argument, based on the globally stored list      * of external file types. The entry itself is not modified. The entry's bibtex key must have been set.      *      * @param entry            The BibEntry to find links for.      * @param singleTableModel The table model to insert links into. Already existing links are not duplicated or      *                         removed.      * @param databaseContext  The database providing the relevant file directory, if any.      * @param callback         An ActionListener that is notified (on the event dispatch thread) when the search is finished.      *                         The ActionEvent has id=0 if no new links were added, and id=1 if one or more links were added. This      *                         parameter can be null, which means that no callback will be notified. The passed ActionEvent is      *                         constructed with (this, id, ""), where id is 1 if something has been done and 0 if nothing has been      *                         done.      * @param diag             An instantiated modal JDialog which will be used to display the progress of the automatically setting. This      *                         parameter can be null, which means that no progress update will be shown.      * @return the runnable able to perform the automatically setting      */
DECL|method|autoSetLinks (final BibEntry entry, final FileListTableModel singleTableModel, final BibDatabaseContext databaseContext, final ActionListener callback, final JDialog diag)
specifier|public
specifier|static
name|Runnable
name|autoSetLinks
parameter_list|(
specifier|final
name|BibEntry
name|entry
parameter_list|,
specifier|final
name|FileListTableModel
name|singleTableModel
parameter_list|,
specifier|final
name|BibDatabaseContext
name|databaseContext
parameter_list|,
specifier|final
name|ActionListener
name|callback
parameter_list|,
specifier|final
name|JDialog
name|diag
parameter_list|)
block|{
return|return
name|autoSetLinks
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|entry
argument_list|)
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|singleTableModel
argument_list|,
name|databaseContext
argument_list|,
name|callback
argument_list|,
name|diag
argument_list|)
return|;
block|}
block|}
end_class

end_unit

