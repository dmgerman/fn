begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
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
name|*
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
name|*
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
name|plugin
operator|.
name|PluginCore
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
name|plugin
operator|.
name|core
operator|.
name|JabRefPlugin
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
name|plugin
operator|.
name|core
operator|.
name|generated
operator|.
name|_JabRefPlugin
operator|.
name|ExportFormatExtension
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
name|plugin
operator|.
name|core
operator|.
name|generated
operator|.
name|_JabRefPlugin
operator|.
name|ExportFormatProviderExtension
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
name|plugin
operator|.
name|core
operator|.
name|generated
operator|.
name|_JabRefPlugin
operator|.
name|ExportFormatTemplateExtension
import|;
end_import

begin_comment
comment|/**  * User: alver  *   * Date: Oct 18, 2006   *   * Time: 9:35:08 PM   */
end_comment

begin_class
DECL|class|ExportFormats
specifier|public
class|class
name|ExportFormats
block|{
DECL|field|exportFormats
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|IExportFormat
argument_list|>
name|exportFormats
init|=
operator|new
name|TreeMap
argument_list|<
name|String
argument_list|,
name|IExportFormat
argument_list|>
argument_list|()
decl_stmt|;
DECL|method|initAllExports ()
specifier|public
specifier|static
name|void
name|initAllExports
parameter_list|()
block|{
name|exportFormats
operator|.
name|clear
argument_list|()
expr_stmt|;
comment|// Initialize Build-In Export Formats
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"HTML"
argument_list|)
argument_list|,
literal|"html"
argument_list|,
literal|"html"
argument_list|,
literal|null
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Simple HTML"
argument_list|)
argument_list|,
literal|"simplehtml"
argument_list|,
literal|"simplehtml"
argument_list|,
literal|null
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Docbook"
argument_list|)
argument_list|,
literal|"docbook"
argument_list|,
literal|"docbook"
argument_list|,
literal|null
argument_list|,
literal|".xml"
argument_list|)
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"BibTeXML"
argument_list|)
argument_list|,
literal|"bibtexml"
argument_list|,
literal|"bibtexml"
argument_list|,
literal|null
argument_list|,
literal|".xml"
argument_list|)
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|ModsExportFormat
argument_list|()
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"HTML table"
argument_list|)
argument_list|,
literal|"tablerefs"
argument_list|,
literal|"tablerefs"
argument_list|,
literal|"tablerefs"
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"HTML table (with Abstract& BibTeX)"
argument_list|)
argument_list|,
literal|"tablerefsabsbib"
argument_list|,
literal|"tablerefsabsbib"
argument_list|,
literal|"tablerefsabsbib"
argument_list|,
literal|".html"
argument_list|)
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Harvard RTF"
argument_list|)
argument_list|,
literal|"harvard"
argument_list|,
literal|"harvard"
argument_list|,
literal|"harvard"
argument_list|,
literal|".rtf"
argument_list|)
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|ExportFormat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Endnote"
argument_list|)
argument_list|,
literal|"endnote"
argument_list|,
literal|"EndNote"
argument_list|,
literal|"endnote"
argument_list|,
literal|".txt"
argument_list|)
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|OpenOfficeDocumentCreator
argument_list|()
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|OpenDocumentSpreadsheetCreator
argument_list|()
argument_list|)
expr_stmt|;
name|putFormat
argument_list|(
operator|new
name|MSBibExportFormat
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add Export Formats contributed by Plugins
name|JabRefPlugin
name|plugin
init|=
name|JabRefPlugin
operator|.
name|getInstance
argument_list|(
name|PluginCore
operator|.
name|getManager
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|plugin
operator|!=
literal|null
condition|)
block|{
comment|// 1. ExportFormats based on Templates
for|for
control|(
name|ExportFormatTemplateExtension
name|e
range|:
name|plugin
operator|.
name|getExportFormatTemplateExtensions
argument_list|()
control|)
block|{
name|ExportFormat
name|format
init|=
name|PluginBasedExportFormat
operator|.
name|getFormat
argument_list|(
name|e
argument_list|)
decl_stmt|;
if|if
condition|(
name|format
operator|!=
literal|null
condition|)
block|{
name|putFormat
argument_list|(
name|format
argument_list|)
expr_stmt|;
block|}
block|}
comment|// 2. ExportFormat classed
for|for
control|(
specifier|final
name|ExportFormatExtension
name|e
range|:
name|plugin
operator|.
name|getExportFormatExtensions
argument_list|()
control|)
block|{
name|putFormat
argument_list|(
operator|new
name|IExportFormat
argument_list|()
block|{
specifier|public
name|String
name|getConsoleName
parameter_list|()
block|{
return|return
name|e
operator|.
name|getConsoleName
argument_list|()
return|;
block|}
specifier|public
name|String
name|getDisplayName
parameter_list|()
block|{
return|return
name|e
operator|.
name|getDisplayName
argument_list|()
return|;
block|}
specifier|public
name|FileFilter
name|getFileFilter
parameter_list|()
block|{
return|return
operator|new
name|ExportFileFilter
argument_list|(
name|this
argument_list|,
name|e
operator|.
name|getExtension
argument_list|()
argument_list|)
return|;
block|}
name|IExportFormat
name|wrapped
decl_stmt|;
specifier|public
name|void
name|performExport
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|String
name|file
parameter_list|,
name|String
name|encoding
parameter_list|,
name|Set
argument_list|<
name|String
argument_list|>
name|entryIds
parameter_list|)
throws|throws
name|Exception
block|{
if|if
condition|(
name|wrapped
operator|==
literal|null
condition|)
name|wrapped
operator|=
name|e
operator|.
name|getExportFormat
argument_list|()
expr_stmt|;
name|wrapped
operator|.
name|performExport
argument_list|(
name|database
argument_list|,
name|file
argument_list|,
name|encoding
argument_list|,
name|entryIds
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
comment|// 3. Formatters provided by Export Format Providers
for|for
control|(
name|ExportFormatProviderExtension
name|e
range|:
name|plugin
operator|.
name|getExportFormatProviderExtensions
argument_list|()
control|)
block|{
name|IExportFormatProvider
name|formatProvider
init|=
name|e
operator|.
name|getFormatProvider
argument_list|()
decl_stmt|;
for|for
control|(
name|IExportFormat
name|exportFormat
range|:
name|formatProvider
operator|.
name|getExportFormats
argument_list|()
control|)
block|{
name|putFormat
argument_list|(
name|exportFormat
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|// Now add custom export formats
for|for
control|(
name|IExportFormat
name|format
range|:
name|Globals
operator|.
name|prefs
operator|.
name|customExports
operator|.
name|getCustomExportFormats
argument_list|()
operator|.
name|values
argument_list|()
control|)
block|{
name|putFormat
argument_list|(
name|format
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Build a string listing of all available export formats. 	 *  	 * @param maxLineLength 	 *            The max line length before a line break must be added. 	 * @param linePrefix 	 *            If a line break is added, this prefix will be inserted at the 	 *            beginning of the next line. 	 * @return The string describing available formats. 	 */
DECL|method|getConsoleExportList (int maxLineLength, int firstLineSubtr, String linePrefix)
specifier|public
specifier|static
name|String
name|getConsoleExportList
parameter_list|(
name|int
name|maxLineLength
parameter_list|,
name|int
name|firstLineSubtr
parameter_list|,
name|String
name|linePrefix
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|int
name|lastBreak
init|=
operator|-
name|firstLineSubtr
decl_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|exportFormats
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|name
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|+
literal|2
operator|+
name|name
operator|.
name|length
argument_list|()
operator|-
name|lastBreak
operator|>
name|maxLineLength
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|",\n"
argument_list|)
expr_stmt|;
name|lastBreak
operator|=
name|sb
operator|.
name|length
argument_list|()
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|linePrefix
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|sb
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
name|sb
operator|.
name|append
argument_list|(
literal|", "
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
comment|/**      * Get a Map of all export formats.      * @return A Map containing all export formats, mapped to their console names.      */
DECL|method|getExportFormats ()
specifier|public
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|IExportFormat
argument_list|>
name|getExportFormats
parameter_list|()
block|{
comment|// It is perhaps overly paranoid to make a defensive copy in this case:
return|return
name|Collections
operator|.
name|unmodifiableMap
argument_list|(
name|exportFormats
argument_list|)
return|;
block|}
comment|/** 	 * Look up the named export format. 	 *  	 * @param consoleName 	 *            The export name given in the JabRef console help information. 	 * @return The ExportFormat, or null if no exportformat with that name is 	 *         registered. 	 */
DECL|method|getExportFormat (String consoleName)
specifier|public
specifier|static
name|IExportFormat
name|getExportFormat
parameter_list|(
name|String
name|consoleName
parameter_list|)
block|{
return|return
name|exportFormats
operator|.
name|get
argument_list|(
name|consoleName
argument_list|)
return|;
block|}
comment|/** 	 * Create an AbstractAction for performing an export operation. 	 *  	 * @param frame 	 *            The JabRefFrame of this JabRef instance. 	 * @param selectedOnly 	 *            true indicates that only selected entries should be exported, 	 *            false indicates that all entries should be exported. 	 * @return The action. 	 */
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
name|ExportAction
extends|extends
name|MnemonicAwareAction
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|639463604530580554L
decl_stmt|;
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
specifier|private
name|boolean
name|selectedOnly
decl_stmt|;
specifier|public
name|ExportAction
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
name|NAME
argument_list|,
name|selectedOnly
condition|?
literal|"Export selected entries"
else|:
literal|"Export"
argument_list|)
expr_stmt|;
block|}
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
name|ExportFormats
operator|.
name|createExportFileChooser
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"exportWorkingDirectory"
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
return|return;
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
name|path
operator|=
name|path
operator|+
name|eff
operator|.
name|getExtension
argument_list|()
expr_stmt|;
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
literal|"'"
operator|+
name|file
operator|.
name|getName
argument_list|()
operator|+
literal|"' "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"exists. Overwrite file?"
argument_list|)
argument_list|,
name|Globals
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
return|return;
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
name|Set
argument_list|<
name|String
argument_list|>
name|entryIds
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|selectedOnly
condition|)
block|{
name|BibtexEntry
index|[]
name|selected
init|=
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|getSelectedEntries
argument_list|()
decl_stmt|;
name|entryIds
operator|=
operator|new
name|HashSet
argument_list|<
name|String
argument_list|>
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|selected
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|BibtexEntry
name|bibtexEntry
init|=
name|selected
index|[
name|i
index|]
decl_stmt|;
name|entryIds
operator|.
name|add
argument_list|(
name|bibtexEntry
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Make sure we remember which filter was used, to set
comment|// the default for next time:
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"lastUsedExport"
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
literal|"exportWorkingDirectory"
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
name|Set
argument_list|<
name|String
argument_list|>
name|finEntryIDs
init|=
name|entryIds
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
init|=
literal|null
decl_stmt|;
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
name|basePanel
argument_list|()
operator|.
name|database
argument_list|()
argument_list|,
name|finFile
operator|.
name|getPath
argument_list|()
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|getEncoding
argument_list|()
argument_list|,
name|finEntryIDs
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
comment|//ex.printStackTrace();
name|errorMessage
operator|=
name|ex
operator|.
name|getMessage
argument_list|()
expr_stmt|;
block|}
block|}
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not save file"
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not save file"
argument_list|)
operator|+
literal|".\n"
operator|+
name|errorMessage
argument_list|,
name|Globals
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
operator|(
name|exportWorker
operator|.
name|getWorker
argument_list|()
operator|)
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
name|ExportAction
argument_list|(
name|frame
argument_list|,
name|selectedOnly
argument_list|)
return|;
block|}
DECL|method|createExportFileChooser (String currentDir)
specifier|public
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
literal|"lastUsedExport"
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
name|TreeSet
argument_list|<
name|FileFilter
argument_list|>
name|filters
init|=
operator|new
name|TreeSet
argument_list|<
name|FileFilter
argument_list|>
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
name|exportFormats
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
name|filters
operator|.
name|add
argument_list|(
name|format
operator|.
name|getFileFilter
argument_list|()
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
name|defaultFilter
operator|=
name|format
operator|.
name|getFileFilter
argument_list|()
expr_stmt|;
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
name|fc
operator|.
name|setFileFilter
argument_list|(
name|defaultFilter
argument_list|)
expr_stmt|;
return|return
name|fc
return|;
block|}
DECL|method|putFormat (IExportFormat format)
specifier|private
specifier|static
name|void
name|putFormat
parameter_list|(
name|IExportFormat
name|format
parameter_list|)
block|{
name|exportFormats
operator|.
name|put
argument_list|(
name|format
operator|.
name|getConsoleName
argument_list|()
argument_list|,
name|format
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

