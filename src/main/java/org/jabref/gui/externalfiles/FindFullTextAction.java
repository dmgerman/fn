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
name|gui
operator|.
name|worker
operator|.
name|AbstractWorker
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
name|FulltextFetchers
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
name|javax
operator|.
name|swing
operator|.
name|*
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
name|net
operator|.
name|URL
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
name|concurrent
operator|.
name|ConcurrentHashMap
import|;
end_import

begin_comment
comment|/**  * Try to download fulltext PDF for selected entry(ies) by following URL or DOI link.  */
end_comment

begin_class
DECL|class|FindFullTextAction
specifier|public
class|class
name|FindFullTextAction
extends|extends
name|AbstractWorker
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
name|FindFullTextAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|WARNING_LIMIT
specifier|private
specifier|static
specifier|final
name|int
name|WARNING_LIMIT
init|=
literal|5
decl_stmt|;
comment|// The minimum number of selected entries to ask the user for confirmation
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|downloads
specifier|private
specifier|final
name|Map
argument_list|<
name|Optional
argument_list|<
name|URL
argument_list|>
argument_list|,
name|BibEntry
argument_list|>
name|downloads
init|=
operator|new
name|ConcurrentHashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|FindFullTextAction (BasePanel basePanel)
specifier|public
name|FindFullTextAction
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|Exception
block|{
if|if
condition|(
operator|!
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|basePanel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Looking for full text document..."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"No entry selected for fulltext download."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|size
argument_list|()
operator|>=
name|WARNING_LIMIT
condition|)
block|{
name|String
index|[]
name|options
init|=
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Look up full text documents"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You are about to look up full text documents for %0 entries."
argument_list|,
name|String
operator|.
name|valueOf
argument_list|(
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef will send at least one request per entry to a publisher."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you still want to continue?"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Look up full text documents"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|,
literal|null
argument_list|,
name|options
argument_list|,
name|options
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|!=
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|basePanel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Operation canceled."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
block|}
for|for
control|(
name|BibEntry
name|entry
range|:
name|basePanel
operator|.
name|getSelectedEntries
argument_list|()
control|)
block|{
name|FulltextFetchers
name|fft
init|=
operator|new
name|FulltextFetchers
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|downloads
operator|.
name|put
argument_list|(
name|fft
operator|.
name|findFullTextPDF
argument_list|(
name|entry
argument_list|)
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|List
argument_list|<
name|Optional
argument_list|<
name|URL
argument_list|>
argument_list|>
name|remove
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Entry
argument_list|<
name|Optional
argument_list|<
name|URL
argument_list|>
argument_list|,
name|BibEntry
argument_list|>
name|download
range|:
name|downloads
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|BibEntry
name|entry
init|=
name|download
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|URL
argument_list|>
name|result
init|=
name|download
operator|.
name|getKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|dir
init|=
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getFirstExistingFileDir
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|dir
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Main file directory not set!"
argument_list|)
operator|+
literal|" "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preferences"
argument_list|)
operator|+
literal|" -> "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"File"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Directory not found"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|DownloadExternalFile
name|def
init|=
operator|new
name|DownloadExternalFile
argument_list|(
name|basePanel
operator|.
name|frame
argument_list|()
argument_list|,
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|entry
argument_list|)
decl_stmt|;
try|try
block|{
name|def
operator|.
name|download
argument_list|(
name|result
operator|.
name|get
argument_list|()
argument_list|,
name|file
lambda|->
block|{
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|Optional
argument_list|<
name|FieldChange
argument_list|>
name|fieldChange
init|=
name|entry
operator|.
name|addFile
argument_list|(
name|file
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldChange
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|UndoableFieldChange
name|edit
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
argument_list|,
name|fieldChange
operator|.
name|get
argument_list|()
operator|.
name|getNewValue
argument_list|()
argument_list|)
decl_stmt|;
name|basePanel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|edit
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
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
name|warn
argument_list|(
literal|"Problem downloading file"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
name|basePanel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Finished downloading full text document for entry %0."
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"undefined"
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|String
name|title
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Full text document download failed"
argument_list|)
decl_stmt|;
name|String
name|message
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Full text document download failed for entry %0."
argument_list|,
name|entry
operator|.
name|getCiteKeyOptional
argument_list|()
operator|.
name|orElse
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"undefined"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|basePanel
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|message
argument_list|,
name|title
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|remove
operator|.
name|add
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|Optional
argument_list|<
name|URL
argument_list|>
name|result
range|:
name|remove
control|)
block|{
name|downloads
operator|.
name|remove
argument_list|(
name|result
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

