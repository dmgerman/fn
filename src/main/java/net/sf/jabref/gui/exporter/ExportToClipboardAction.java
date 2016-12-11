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
name|Toolkit
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|datatransfer
operator|.
name|ClipboardOwner
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
name|io
operator|.
name|FileInputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStreamReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|Reader
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
name|LinkedList
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
name|Objects
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
name|JList
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
name|ListSelectionModel
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
name|BasePanel
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
DECL|class|ExportToClipboardAction
specifier|public
class|class
name|ExportToClipboardAction
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
name|ExportToClipboardAction
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
comment|/**      * written by run() and read by update()      */
DECL|field|message
specifier|private
name|String
name|message
decl_stmt|;
DECL|method|ExportToClipboardAction (JabRefFrame frame)
specifier|public
name|ExportToClipboardAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|frame
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
name|BasePanel
name|panel
init|=
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
decl_stmt|;
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires one or more entries to be selected."
argument_list|)
expr_stmt|;
name|getCallBack
argument_list|()
operator|.
name|update
argument_list|()
expr_stmt|;
return|return;
block|}
name|List
argument_list|<
name|IExportFormat
argument_list|>
name|exportFormats
init|=
operator|new
name|LinkedList
argument_list|<>
argument_list|(
name|ExportFormats
operator|.
name|getExportFormats
argument_list|()
operator|.
name|values
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|exportFormats
argument_list|,
parameter_list|(
name|e1
parameter_list|,
name|e2
parameter_list|)
lambda|->
name|e1
operator|.
name|getDisplayName
argument_list|()
operator|.
name|compareTo
argument_list|(
name|e2
operator|.
name|getDisplayName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|String
index|[]
name|exportFormatDisplayNames
init|=
operator|new
name|String
index|[
name|exportFormats
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|exportFormats
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|IExportFormat
name|exportFormat
init|=
name|exportFormats
operator|.
name|get
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|exportFormatDisplayNames
index|[
name|i
index|]
operator|=
name|exportFormat
operator|.
name|getDisplayName
argument_list|()
expr_stmt|;
block|}
name|JList
argument_list|<
name|String
argument_list|>
name|list
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|exportFormatDisplayNames
argument_list|)
decl_stmt|;
name|list
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectionInterval
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|frame
argument_list|,
name|list
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select export format"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
operator|new
name|String
index|[]
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|NO_OPTION
condition|)
block|{
return|return;
block|}
name|IExportFormat
name|format
init|=
name|exportFormats
operator|.
name|get
argument_list|(
name|list
operator|.
name|getSelectedIndex
argument_list|()
argument_list|)
decl_stmt|;
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
name|getFileDirectories
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|File
name|tmp
init|=
literal|null
decl_stmt|;
try|try
block|{
comment|// To simplify the exporter API we simply do a normal export to a temporary
comment|// file, and read the contents afterwards:
name|tmp
operator|=
name|File
operator|.
name|createTempFile
argument_list|(
literal|"jabrefCb"
argument_list|,
literal|".tmp"
argument_list|)
expr_stmt|;
name|tmp
operator|.
name|deleteOnExit
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
comment|// Write to file:
name|format
operator|.
name|performExport
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|tmp
operator|.
name|getPath
argument_list|()
argument_list|,
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
argument_list|,
name|entries
argument_list|)
expr_stmt|;
comment|// Read the file and put the contents on the clipboard:
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
try|try
init|(
name|Reader
name|reader
init|=
operator|new
name|InputStreamReader
argument_list|(
operator|new
name|FileInputStream
argument_list|(
name|tmp
argument_list|)
argument_list|,
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
init|)
block|{
name|int
name|s
decl_stmt|;
while|while
condition|(
operator|(
name|s
operator|=
name|reader
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|s
argument_list|)
expr_stmt|;
block|}
block|}
name|ClipboardOwner
name|owner
init|=
parameter_list|(
name|clipboard
parameter_list|,
name|content
parameter_list|)
lambda|->
block|{
comment|// Do nothing
block|}
decl_stmt|;
name|RtfSelection
name|rs
init|=
operator|new
name|RtfSelection
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getSystemClipboard
argument_list|()
operator|.
name|setContents
argument_list|(
name|rs
argument_list|,
name|owner
argument_list|)
expr_stmt|;
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Entries exported to clipboard"
argument_list|)
operator|+
literal|": "
operator|+
name|entries
operator|.
name|size
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error exporting to clipboard"
argument_list|,
name|e
argument_list|)
expr_stmt|;
comment|//To change body of catch statement use File | Settings | File Templates.
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error exporting to clipboard"
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
comment|// Clean up:
if|if
condition|(
operator|(
name|tmp
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|tmp
operator|.
name|delete
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Cannot delete temporary clipboard file"
argument_list|)
expr_stmt|;
block|}
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
name|frame
operator|.
name|output
argument_list|(
name|message
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

