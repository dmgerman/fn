begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|exporter
package|;
end_package

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
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFileChooser
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
name|filechooser
operator|.
name|FileFilter
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
name|gui
operator|.
name|JabRefFrame
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
name|actions
operator|.
name|MnemonicAwareAction
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
name|worker
operator|.
name|AbstractWorker
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
name|ExportFormats
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
name|IExportFormat
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

begin_class
DECL|class|ExportAction
specifier|public
class|class
name|ExportAction
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
name|ExportAction
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Create an AbstractAction for performing an export operation.      *      * @param frame      *            The JabRefFrame of this JabRef instance.      * @param selectedOnly      *            true indicates that only selected entries should be exported,      *            false indicates that all entries should be exported.      * @return The action.      */
DECL|method|getExportAction (JabRefFrame frame, boolean selectedOnly)
specifier|public
specifier|static
name|AbstractAction
name|getExportAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|selectedOnly
parameter_list|)
block|{
class|class
name|InternalExportAction
extends|extends
name|MnemonicAwareAction
block|{
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|selectedOnly
decl_stmt|;
specifier|public
name|InternalExportAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|selectedOnly
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
name|selectedOnly
operator|=
name|selectedOnly
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|selectedOnly
condition|?
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Export selected entries"
argument_list|)
else|:
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Export"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|ExportFormats
operator|.
name|initAllExports
argument_list|()
expr_stmt|;
name|JFileChooser
name|fc
init|=
name|ExportAction
operator|.
name|createExportFileChooser
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_WORKING_DIRECTORY
argument_list|)
argument_list|)
decl_stmt|;
name|fc
operator|.
name|showSaveDialog
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|File
name|file
init|=
name|fc
operator|.
name|getSelectedFile
argument_list|()
decl_stmt|;
if|if
condition|(
name|file
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|FileFilter
name|ff
init|=
name|fc
operator|.
name|getFileFilter
argument_list|()
decl_stmt|;
if|if
condition|(
name|ff
operator|instanceof
name|ExportFileFilter
condition|)
block|{
name|ExportFileFilter
name|eff
init|=
operator|(
name|ExportFileFilter
operator|)
name|ff
decl_stmt|;
name|String
name|path
init|=
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|path
operator|.
name|endsWith
argument_list|(
name|eff
operator|.
name|getExtension
argument_list|()
argument_list|)
condition|)
block|{
name|path
operator|=
name|path
operator|+
name|eff
operator|.
name|getExtension
argument_list|()
expr_stmt|;
block|}
name|file
operator|=
operator|new
name|File
argument_list|(
name|path
argument_list|)
expr_stmt|;
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|// Warn that the file exists:
if|if
condition|(
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"'%0' exists. Overwrite file?"
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
operator|!=
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
return|return;
block|}
block|}
specifier|final
name|IExportFormat
name|format
init|=
name|eff
operator|.
name|getExportFormat
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
if|if
condition|(
name|selectedOnly
condition|)
block|{
comment|// Selected entries
name|entries
operator|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getSelectedEntries
argument_list|()
expr_stmt|;
block|}
else|else
block|{
comment|// All entries
name|entries
operator|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
expr_stmt|;
block|}
comment|// Set the global variable for this database's file directory before exporting,
comment|// so formatters can resolve linked files correctly.
comment|// (This is an ugly hack!)
name|Globals
operator|.
name|prefs
operator|.
name|fileDirForDatabase
operator|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getFileDirectory
argument_list|()
expr_stmt|;
comment|// Make sure we remember which filter was used, to set
comment|// the default for next time:
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|LAST_USED_EXPORT
argument_list|,
name|format
operator|.
name|getConsoleName
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_WORKING_DIRECTORY
argument_list|,
name|file
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|File
name|finFile
init|=
name|file
decl_stmt|;
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|finEntries
init|=
name|entries
decl_stmt|;
name|AbstractWorker
name|exportWorker
init|=
operator|new
name|AbstractWorker
argument_list|()
block|{
name|String
name|errorMessage
decl_stmt|;
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
try|try
block|{
name|format
operator|.
name|performExport
argument_list|(
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|finFile
operator|.
name|getPath
argument_list|()
argument_list|,
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|getEncoding
argument_list|()
argument_list|,
name|finEntries
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem exporting"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
if|if
condition|(
name|ex
operator|.
name|getMessage
argument_list|()
operator|==
literal|null
condition|)
block|{
name|errorMessage
operator|=
name|ex
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|errorMessage
operator|=
name|ex
operator|.
name|getMessage
argument_list|()
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|public
name|void
name|update
parameter_list|()
block|{
comment|// No error message. Report success:
if|if
condition|(
name|errorMessage
operator|==
literal|null
condition|)
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 export successful"
argument_list|,
name|format
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// ... or show an error dialog:
else|else
block|{
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save file."
argument_list|)
operator|+
literal|" - "
operator|+
name|errorMessage
argument_list|)
expr_stmt|;
comment|// Need to warn the user that saving failed!
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not save file."
argument_list|)
operator|+
literal|"\n"
operator|+
name|errorMessage
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Save database"
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
decl_stmt|;
comment|// Run the export action in a background thread:
name|exportWorker
operator|.
name|getWorker
argument_list|()
operator|.
name|run
argument_list|()
expr_stmt|;
comment|// Run the update method:
name|exportWorker
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
block|}
block|}
return|return
operator|new
name|InternalExportAction
argument_list|(
name|frame
argument_list|,
name|selectedOnly
argument_list|)
return|;
block|}
DECL|method|createExportFileChooser (String currentDir)
specifier|private
specifier|static
name|JFileChooser
name|createExportFileChooser
parameter_list|(
name|String
name|currentDir
parameter_list|)
block|{
name|String
name|lastUsedFormat
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LAST_USED_EXPORT
argument_list|)
decl_stmt|;
name|FileFilter
name|defaultFilter
init|=
literal|null
decl_stmt|;
name|JFileChooser
name|fc
init|=
operator|new
name|JFileChooser
argument_list|(
name|currentDir
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|FileFilter
argument_list|>
name|filters
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|IExportFormat
argument_list|>
name|e
range|:
name|ExportFormats
operator|.
name|getExportFormats
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|String
name|formatName
init|=
name|e
operator|.
name|getKey
argument_list|()
decl_stmt|;
name|IExportFormat
name|format
init|=
name|e
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|ExportFileFilter
name|exportFileFilter
init|=
operator|new
name|ExportFileFilter
argument_list|(
name|format
argument_list|)
decl_stmt|;
name|filters
operator|.
name|add
argument_list|(
name|exportFileFilter
argument_list|)
expr_stmt|;
if|if
condition|(
name|formatName
operator|.
name|equals
argument_list|(
name|lastUsedFormat
argument_list|)
condition|)
block|{
name|defaultFilter
operator|=
name|exportFileFilter
expr_stmt|;
block|}
block|}
for|for
control|(
name|FileFilter
name|ff
range|:
name|filters
control|)
block|{
name|fc
operator|.
name|addChoosableFileFilter
argument_list|(
name|ff
argument_list|)
expr_stmt|;
block|}
name|fc
operator|.
name|setAcceptAllFileFilterUsed
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|defaultFilter
operator|!=
literal|null
condition|)
block|{
name|fc
operator|.
name|setFileFilter
argument_list|(
name|defaultFilter
argument_list|)
expr_stmt|;
block|}
return|return
name|fc
return|;
block|}
block|}
end_class

end_unit

