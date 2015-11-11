begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
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
name|HashMap
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
name|JMenuItem
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
name|gui
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
name|model
operator|.
name|database
operator|.
name|KeyCollisionException
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
name|importer
operator|.
name|fileformat
operator|.
name|ImportFormat
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
name|database
operator|.
name|BibtexDatabase
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
name|BibtexEntry
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
name|BibtexEntryType
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
name|BibtexString
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
name|util
operator|.
name|Util
import|;
end_import

begin_comment
comment|/*  * TODO: could separate the "menu item" functionality from the importing functionality  *  */
end_comment

begin_class
DECL|class|ImportMenuItem
specifier|public
class|class
name|ImportMenuItem
extends|extends
name|JMenuItem
implements|implements
name|ActionListener
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|openInNew
specifier|private
specifier|final
name|boolean
name|openInNew
decl_stmt|;
DECL|field|importer
specifier|private
specifier|final
name|ImportFormat
name|importer
decl_stmt|;
DECL|field|importError
specifier|private
name|IOException
name|importError
decl_stmt|;
DECL|method|ImportMenuItem (JabRefFrame frame, boolean openInNew)
specifier|public
name|ImportMenuItem
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|openInNew
parameter_list|)
block|{
name|this
argument_list|(
name|frame
argument_list|,
name|openInNew
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
DECL|method|ImportMenuItem (JabRefFrame frame, boolean openInNew, ImportFormat importer)
specifier|public
name|ImportMenuItem
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|openInNew
parameter_list|,
name|ImportFormat
name|importer
parameter_list|)
block|{
name|super
argument_list|(
name|importer
operator|!=
literal|null
condition|?
name|importer
operator|.
name|getFormatName
argument_list|()
else|:
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autodetect format"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|importer
operator|=
name|importer
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|openInNew
operator|=
name|openInNew
expr_stmt|;
name|addActionListener
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|MyWorker
name|worker
init|=
operator|new
name|MyWorker
argument_list|()
decl_stmt|;
name|worker
operator|.
name|init
argument_list|()
expr_stmt|;
name|worker
operator|.
name|getWorker
argument_list|()
operator|.
name|run
argument_list|()
expr_stmt|;
name|worker
operator|.
name|getCallBack
argument_list|()
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
comment|/**      * Automatically imports the files given as arguments      * @param filenames List of files to import      */
DECL|method|automatedImport (String[] filenames)
specifier|public
name|void
name|automatedImport
parameter_list|(
name|String
index|[]
name|filenames
parameter_list|)
block|{
comment|// replace the work of the init step:
name|MyWorker
name|worker
init|=
operator|new
name|MyWorker
argument_list|()
decl_stmt|;
name|worker
operator|.
name|fileOk
operator|=
literal|true
expr_stmt|;
name|worker
operator|.
name|filenames
operator|=
name|filenames
expr_stmt|;
name|worker
operator|.
name|getWorker
argument_list|()
operator|.
name|run
argument_list|()
expr_stmt|;
name|worker
operator|.
name|getCallBack
argument_list|()
operator|.
name|update
argument_list|()
expr_stmt|;
block|}
DECL|class|MyWorker
class|class
name|MyWorker
extends|extends
name|AbstractWorker
block|{
DECL|field|filenames
name|String
index|[]
name|filenames
decl_stmt|;
DECL|field|bibtexResult
name|ParserResult
name|bibtexResult
decl_stmt|;
comment|// Contains the merged import results
DECL|field|fileOk
name|boolean
name|fileOk
decl_stmt|;
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|importError
operator|=
literal|null
expr_stmt|;
name|filenames
operator|=
name|FileDialogs
operator|.
name|getMultipleFiles
argument_list|(
name|frame
argument_list|,
operator|new
name|File
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
argument_list|,
name|importer
operator|!=
literal|null
condition|?
name|importer
operator|.
name|getExtensions
argument_list|()
else|:
literal|null
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|filenames
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|filenames
operator|.
name|length
operator|>
literal|0
operator|)
condition|)
block|{
name|frame
operator|.
name|block
argument_list|()
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Starting import"
argument_list|)
argument_list|)
expr_stmt|;
name|fileOk
operator|=
literal|true
expr_stmt|;
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
name|filenames
index|[
literal|0
index|]
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
operator|!
name|fileOk
condition|)
block|{
return|return;
block|}
comment|// We import all files and collect their results:
name|List
argument_list|<
name|ImportFormatReader
operator|.
name|UnknownFormatImport
argument_list|>
name|imports
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|filename
range|:
name|filenames
control|)
block|{
try|try
block|{
if|if
condition|(
name|importer
operator|!=
literal|null
condition|)
block|{
comment|// Specific importer:
name|ParserResult
name|pr
init|=
operator|new
name|ParserResult
argument_list|(
name|Globals
operator|.
name|importFormatReader
operator|.
name|importFromFile
argument_list|(
name|importer
argument_list|,
name|filename
argument_list|,
name|frame
argument_list|)
argument_list|)
decl_stmt|;
name|imports
operator|.
name|add
argument_list|(
operator|new
name|ImportFormatReader
operator|.
name|UnknownFormatImport
argument_list|(
name|importer
operator|.
name|getFormatName
argument_list|()
argument_list|,
name|pr
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Unknown format:
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Importing in unknown format"
argument_list|)
operator|+
literal|"..."
argument_list|)
expr_stmt|;
comment|// This import method never throws an IOException:
name|imports
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|importFormatReader
operator|.
name|importUnknownFormat
argument_list|(
name|filename
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// This indicates that a specific importer was specified, and that
comment|// this importer has thrown an IOException. We store the exception,
comment|// so a relevant error message can be displayed.
name|importError
operator|=
name|e
expr_stmt|;
block|}
block|}
comment|// Ok, done. Then try to gather in all we have found. Since we might
comment|// have found
comment|// one or more bibtex results, it's best to gather them in a
comment|// BibtexDatabase.
name|bibtexResult
operator|=
name|mergeImportResults
argument_list|(
name|imports
argument_list|)
expr_stmt|;
comment|/* show parserwarnings, if any. */
for|for
control|(
name|ImportFormatReader
operator|.
name|UnknownFormatImport
name|p
range|:
name|imports
control|)
block|{
if|if
condition|(
name|p
operator|!=
literal|null
condition|)
block|{
name|ParserResult
name|pr
init|=
name|p
operator|.
name|parserResult
decl_stmt|;
if|if
condition|(
name|pr
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
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
name|DISPLAY_KEY_WARNING_DIALOG_AT_STARTUP
argument_list|)
operator|&&
name|pr
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
name|String
index|[]
name|wrns
init|=
name|pr
operator|.
name|warnings
argument_list|()
decl_stmt|;
name|StringBuilder
name|wrn
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|wrns
operator|.
name|length
condition|;
name|j
operator|++
control|)
block|{
name|wrn
operator|.
name|append
argument_list|(
name|j
operator|+
literal|1
argument_list|)
operator|.
name|append
argument_list|(
literal|". "
argument_list|)
operator|.
name|append
argument_list|(
name|wrns
index|[
name|j
index|]
argument_list|)
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|wrn
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|wrn
operator|.
name|deleteCharAt
argument_list|(
name|wrn
operator|.
name|length
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|wrn
operator|.
name|toString
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Warnings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
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
if|if
condition|(
operator|!
name|fileOk
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|bibtexResult
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
operator|!
name|openInNew
condition|)
block|{
specifier|final
name|BasePanel
name|panel
init|=
operator|(
name|BasePanel
operator|)
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getSelectedComponent
argument_list|()
decl_stmt|;
name|ImportInspectionDialog
name|diag
init|=
operator|new
name|ImportInspectionDialog
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
name|BibtexFields
operator|.
name|DEFAULT_INSPECTION_FIELDS
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import"
argument_list|)
argument_list|,
name|openInNew
argument_list|)
decl_stmt|;
name|diag
operator|.
name|addEntries
argument_list|(
name|bibtexResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
name|diag
operator|.
name|entryListComplete
argument_list|()
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|diag
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|diag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|diag
operator|.
name|toFront
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|frame
operator|.
name|addTab
argument_list|(
name|bibtexResult
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|bibtexResult
operator|.
name|getFile
argument_list|()
argument_list|,
name|bibtexResult
operator|.
name|getMetaData
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_ENCODING
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|frame
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Imported entries"
argument_list|)
operator|+
literal|": "
operator|+
name|bibtexResult
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
if|if
condition|(
name|importer
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
literal|"Could not find a suitable import format."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Import in a specific format was specified. Check if we have stored error information:
if|if
condition|(
name|importError
operator|!=
literal|null
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|importError
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import failed"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// @formatter:off
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
literal|"No entries found. Please make sure you are using the correct import filter."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import failed"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
comment|// @formatter:on
block|}
block|}
block|}
name|frame
operator|.
name|unblock
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|mergeImportResults (List<ImportFormatReader.UnknownFormatImport> imports)
specifier|private
name|ParserResult
name|mergeImportResults
parameter_list|(
name|List
argument_list|<
name|ImportFormatReader
operator|.
name|UnknownFormatImport
argument_list|>
name|imports
parameter_list|)
block|{
name|BibtexDatabase
name|database
init|=
operator|new
name|BibtexDatabase
argument_list|()
decl_stmt|;
name|ParserResult
name|directParserResult
init|=
literal|null
decl_stmt|;
name|boolean
name|anythingUseful
init|=
literal|false
decl_stmt|;
for|for
control|(
name|ImportFormatReader
operator|.
name|UnknownFormatImport
name|importResult
range|:
name|imports
control|)
block|{
if|if
condition|(
name|importResult
operator|==
literal|null
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|ImportFormatReader
operator|.
name|BIBTEX_FORMAT
operator|.
name|equals
argument_list|(
name|importResult
operator|.
name|format
argument_list|)
condition|)
block|{
comment|// Bibtex result. We must merge it into our main base.
name|ParserResult
name|pr
init|=
name|importResult
operator|.
name|parserResult
decl_stmt|;
name|anythingUseful
operator|=
name|anythingUseful
operator|||
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntryCount
argument_list|()
operator|>
literal|0
operator|)
operator|||
operator|(
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getStringCount
argument_list|()
operator|>
literal|0
operator|)
expr_stmt|;
comment|// Record the parserResult, as long as this is the first bibtex result:
if|if
condition|(
name|directParserResult
operator|==
literal|null
condition|)
block|{
name|directParserResult
operator|=
name|pr
expr_stmt|;
block|}
comment|// Merge entries:
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
comment|// Merge strings:
for|for
control|(
name|BibtexString
name|bs
range|:
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getStringValues
argument_list|()
control|)
block|{
try|try
block|{
name|database
operator|.
name|addString
argument_list|(
operator|(
name|BibtexString
operator|)
name|bs
operator|.
name|clone
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|e
parameter_list|)
block|{
comment|// TODO: This means a duplicate string name exists, so it's not
comment|// a very exceptional situation. We should maybe give a warning...?
block|}
block|}
block|}
else|else
block|{
name|ParserResult
name|pr
init|=
name|importResult
operator|.
name|parserResult
decl_stmt|;
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
name|pr
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|anythingUseful
operator|=
name|anythingUseful
operator||
operator|!
name|entries
operator|.
name|isEmpty
argument_list|()
expr_stmt|;
comment|// set timestamp and owner
name|Util
operator|.
name|setAutomaticFields
argument_list|(
name|entries
argument_list|,
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
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERWRITE_TIME_STAMP
argument_list|)
argument_list|,
operator|!
name|openInNew
operator|&&
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|MARK_IMPORTED_ENTRIES
argument_list|)
argument_list|)
expr_stmt|;
comment|// set timestamp and owner
for|for
control|(
name|BibtexEntry
name|entry
range|:
name|entries
control|)
block|{
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|anythingUseful
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
operator|(
name|imports
operator|.
name|size
argument_list|()
operator|==
literal|1
operator|)
operator|&&
operator|(
name|directParserResult
operator|!=
literal|null
operator|)
condition|)
block|{
return|return
name|directParserResult
return|;
block|}
else|else
block|{
return|return
operator|new
name|ParserResult
argument_list|(
name|database
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|BibtexEntryType
argument_list|>
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

