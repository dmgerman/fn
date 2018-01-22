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
name|IOException
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
name|Set
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
name|SwingConstants
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
name|externalfiletype
operator|.
name|ExternalFileType
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
name|UndoableFieldChange
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
name|FieldName
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
name|FileFieldWriter
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
name|LinkedFile
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
DECL|class|AutoSetLinks
specifier|public
class|class
name|AutoSetLinks
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
name|AutoSetLinks
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|AutoSetLinks ()
specifier|private
name|AutoSetLinks
parameter_list|()
block|{     }
comment|/**      * Shortcut method if links are set without using the GUI      *      * @param entries  the entries for which links should be set      * @param databaseContext the database for which links are set      */
DECL|method|autoSetLinks (List<BibEntry> entries, BibDatabaseContext databaseContext)
specifier|public
specifier|static
name|void
name|autoSetLinks
parameter_list|(
name|List
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
name|databaseContext
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
comment|/**      * Automatically add links for this set of entries, based on the globally stored list of external file types. The      * entries are modified, and corresponding UndoEdit elements added to the NamedCompound given as argument.      * Furthermore, all entries which are modified are added to the Set of entries given as an argument.      *<p>      * The entries' bibtex keys must have been set - entries lacking key are ignored. The operation is done in a new      * thread, which is returned for the caller to wait for if needed.      *      * @param entries          A collection of BibEntry objects to find links for.      * @param ce               A NamedCompound to add UndoEdit elements to.      * @param changedEntries   MODIFIED, optional. A Set of BibEntry objects to which all modified entries is added.       * @param databaseContext  The database providing the relevant file directory, if any.      * @param callback         An ActionListener that is notified (on the event dispatch thread) when the search is finished.      *                         The ActionEvent has id=0 if no new links were added, and id=1 if one or more links were added. This      *                         parameter can be null, which means that no callback will be notified.      * @param diag             An instantiated modal JDialog which will be used to display the progress of the automatically setting. This      *                         parameter can be null, which means that no progress update will be shown.      * @return the thread performing the automatically setting      */
DECL|method|autoSetLinks (final List<BibEntry> entries, final NamedCompound ce, final Set<BibEntry> changedEntries, final BibDatabaseContext databaseContext, final ActionListener callback, final JDialog diag)
specifier|public
specifier|static
name|Runnable
name|autoSetLinks
parameter_list|(
specifier|final
name|List
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
name|SwingConstants
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
parameter_list|()
lambda|->
block|{
name|boolean
name|foundAny
init|=
literal|false
decl_stmt|;
name|AutoSetFileLinksUtil
name|util
init|=
operator|new
name|AutoSetFileLinksUtil
argument_list|(
name|databaseContext
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getAutoLinkPreferences
argument_list|()
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
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
name|List
argument_list|<
name|LinkedFile
argument_list|>
name|linkedFiles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
block|{
name|linkedFiles
operator|=
name|util
operator|.
name|findAssociatedNotLinkedFiles
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem finding files"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|ce
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|LinkedFile
name|linkedFile
range|:
name|linkedFiles
control|)
block|{
comment|// store undo information
name|String
name|newVal
init|=
name|FileFieldWriter
operator|.
name|getStringRepresentation
argument_list|(
name|linkedFile
argument_list|)
decl_stmt|;
name|String
name|oldVal
init|=
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
decl_stmt|;
name|UndoableFieldChange
name|fieldChange
init|=
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|FieldName
operator|.
name|FILE
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
name|fieldChange
argument_list|)
expr_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|entry
operator|.
name|addFile
argument_list|(
name|linkedFile
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|foundAny
operator|=
literal|true
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
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
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
parameter_list|()
lambda|->
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
name|AutoSetLinks
operator|.
name|class
argument_list|,
name|id
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
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
comment|/**      * Automatically add links for this entry to the table model given as an argument, based on the globally stored list      * of external file types. The entry itself is not modified. The entry's bibtex key must have been set.      *      * @param entry            The BibEntry to find links for.       * @param databaseContext  The database providing the relevant file directory, if any.      * @param callback         An ActionListener that is notified (on the event dispatch thread) when the search is finished.      *                         The ActionEvent has id=0 if no new links were added, and id=1 if one or more links were added. This      *                         parameter can be null, which means that no callback will be notified. The passed ActionEvent is      *                         constructed with (this, id, ""), where id is 1 if something has been done and 0 if nothing has been      *                         done.      * @param diag             An instantiated modal JDialog which will be used to display the progress of the automatically setting. This      *                         parameter can be null, which means that no progress update will be shown.      * @return the runnable able to perform the automatically setting      */
DECL|method|autoSetLinks (final BibEntry entry, final BibDatabaseContext databaseContext, final ActionListener callback, final JDialog diag)
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

