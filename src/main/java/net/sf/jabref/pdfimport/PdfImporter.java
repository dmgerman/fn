begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.pdfimport
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|pdfimport
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
name|nio
operator|.
name|file
operator|.
name|Paths
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
name|Optional
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
name|BasePanelMode
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
name|EntryTypeDialog
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
name|entryeditor
operator|.
name|EntryEditor
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
name|externalfiles
operator|.
name|DroppedFileHandler
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|filelist
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
name|filelist
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
name|maintable
operator|.
name|MainTable
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
name|UndoableInsertEntry
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
name|bibtexkeypattern
operator|.
name|BibtexKeyPatternUtil
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
name|importer
operator|.
name|ParserResult
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
name|importer
operator|.
name|fileformat
operator|.
name|PdfContentImporter
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
name|importer
operator|.
name|fileformat
operator|.
name|PdfXmpImporter
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
name|UpdateField
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
name|logic
operator|.
name|xmp
operator|.
name|XMPUtil
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
name|model
operator|.
name|entry
operator|.
name|EntryType
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
name|FieldName
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
name|IdGenerator
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
DECL|class|PdfImporter
specifier|public
class|class
name|PdfImporter
block|{
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
DECL|field|entryTable
specifier|private
specifier|final
name|MainTable
name|entryTable
decl_stmt|;
DECL|field|dropRow
specifier|private
specifier|final
name|int
name|dropRow
decl_stmt|;
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
name|PdfImporter
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Creates the PdfImporter      *      * @param frame the JabRef frame      * @param panel the panel to use      * @param entryTable the entry table to work on      * @param dropRow the row the entry is dropped to. May be -1 to indicate that no row is selected.      */
DECL|method|PdfImporter (JabRefFrame frame, BasePanel panel, MainTable entryTable, int dropRow)
specifier|public
name|PdfImporter
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|MainTable
name|entryTable
parameter_list|,
name|int
name|dropRow
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
name|this
operator|.
name|entryTable
operator|=
name|entryTable
expr_stmt|;
name|this
operator|.
name|dropRow
operator|=
name|dropRow
expr_stmt|;
block|}
DECL|class|ImportPdfFilesResult
specifier|public
class|class
name|ImportPdfFilesResult
block|{
DECL|field|noPdfFiles
specifier|private
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|noPdfFiles
decl_stmt|;
DECL|field|entries
specifier|private
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
DECL|method|ImportPdfFilesResult (List<String> noPdfFiles, List<BibEntry> entries)
specifier|public
name|ImportPdfFilesResult
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|noPdfFiles
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|)
block|{
name|this
operator|.
name|noPdfFiles
operator|=
name|noPdfFiles
expr_stmt|;
name|this
operator|.
name|entries
operator|=
name|entries
expr_stmt|;
block|}
DECL|method|getNoPdfFiles ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getNoPdfFiles
parameter_list|()
block|{
return|return
name|noPdfFiles
return|;
block|}
DECL|method|getEntries ()
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|getEntries
parameter_list|()
block|{
return|return
name|entries
return|;
block|}
block|}
comment|/**      *      * Imports the PDF files given by fileNames      *      * @param fileNames states the names of the files to import      * @return list of successful created BibTeX entries and list of non-PDF files      */
DECL|method|importPdfFiles (List<String> fileNames)
specifier|public
name|ImportPdfFilesResult
name|importPdfFiles
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|)
block|{
comment|// sort fileNames in PDFfiles to import and other files
comment|// PDFfiles: variable files
comment|// other files: variable noPdfFiles
name|List
argument_list|<
name|String
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|fileNames
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|noPdfFiles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|PdfFileFilter
name|pdfFilter
init|=
name|PdfFileFilter
operator|.
name|INSTANCE
decl_stmt|;
for|for
control|(
name|String
name|file
range|:
name|files
control|)
block|{
if|if
condition|(
operator|!
name|pdfFilter
operator|.
name|accept
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|noPdfFiles
operator|.
name|add
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
block|}
name|files
operator|.
name|removeAll
argument_list|(
name|noPdfFiles
argument_list|)
expr_stmt|;
comment|// files and noPdfFiles correctly sorted
comment|// import the files
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importPdfFilesInternal
argument_list|(
name|files
argument_list|)
decl_stmt|;
return|return
operator|new
name|ImportPdfFilesResult
argument_list|(
name|noPdfFiles
argument_list|,
name|entries
argument_list|)
return|;
block|}
comment|/**      * @param fileNames - PDF files to import      * @return true if the import succeeded, false otherwise      */
DECL|method|importPdfFilesInternal (List<String> fileNames)
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
name|importPdfFilesInternal
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
block|{
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
name|ImportDialog
name|importDialog
init|=
literal|null
decl_stmt|;
name|boolean
name|doNotShowAgain
init|=
literal|false
decl_stmt|;
name|boolean
name|neverShow
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_ALWAYSUSE
argument_list|)
decl_stmt|;
name|int
name|globalChoice
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_DEFAULT_PDF_IMPORT_STYLE
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|res
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|fileName
range|:
name|fileNames
control|)
block|{
if|if
condition|(
operator|!
name|neverShow
operator|&&
operator|!
name|doNotShowAgain
condition|)
block|{
name|importDialog
operator|=
operator|new
name|ImportDialog
argument_list|(
name|dropRow
operator|>=
literal|0
argument_list|,
name|fileName
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|XMPUtil
operator|.
name|hasMetadata
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|fileName
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getXMPPreferences
argument_list|()
argument_list|)
condition|)
block|{
name|importDialog
operator|.
name|disableXMPChoice
argument_list|()
expr_stmt|;
block|}
name|importDialog
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|importDialog
operator|.
name|showDialog
argument_list|()
expr_stmt|;
name|doNotShowAgain
operator|=
name|importDialog
operator|.
name|isDoNotShowAgain
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|neverShow
operator|||
operator|(
name|importDialog
operator|.
name|getResult
argument_list|()
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
operator|)
condition|)
block|{
name|int
name|choice
init|=
name|neverShow
condition|?
name|globalChoice
else|:
name|importDialog
operator|.
name|getChoice
argument_list|()
decl_stmt|;
switch|switch
condition|(
name|choice
condition|)
block|{
case|case
name|ImportDialog
operator|.
name|XMP
case|:
name|doXMPImport
argument_list|(
name|fileName
argument_list|,
name|res
argument_list|)
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|CONTENT
case|:
name|doContentImport
argument_list|(
name|fileName
argument_list|,
name|res
argument_list|)
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|NOMETA
case|:
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|res
operator|::
name|add
argument_list|)
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|ONLYATTACH
case|:
name|DroppedFileHandler
name|dfh
init|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
decl_stmt|;
name|dfh
operator|.
name|linkPdfToEntry
argument_list|(
name|fileName
argument_list|,
name|entryTable
argument_list|,
name|dropRow
argument_list|)
expr_stmt|;
break|break;
default|default:
break|break;
block|}
block|}
block|}
return|return
name|res
return|;
block|}
DECL|method|doXMPImport (String fileName, List<BibEntry> res)
specifier|private
name|void
name|doXMPImport
parameter_list|(
name|String
name|fileName
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|res
parameter_list|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|localRes
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|PdfXmpImporter
name|importer
init|=
operator|new
name|PdfXmpImporter
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getXMPPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|Path
name|filePath
init|=
name|Paths
operator|.
name|get
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|filePath
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
name|frame
operator|.
name|showMessage
argument_list|(
name|result
operator|.
name|getErrorMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|localRes
operator|.
name|addAll
argument_list|(
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|entry
decl_stmt|;
if|if
condition|(
name|localRes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
comment|// import failed -> generate default entry
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Import failed"
argument_list|)
expr_stmt|;
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|res
operator|::
name|add
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// only one entry is imported
name|entry
operator|=
name|localRes
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// insert entry to database and link file
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|FileListTableModel
name|tm
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|File
name|toLink
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
comment|// Get a list of file directories:
name|List
argument_list|<
name|String
argument_list|>
name|dirsS
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|tm
operator|.
name|addEntry
argument_list|(
literal|0
argument_list|,
operator|new
name|FileListEntry
argument_list|(
name|toLink
operator|.
name|getName
argument_list|()
argument_list|,
name|FileUtil
operator|.
name|shortenFileName
argument_list|(
name|toLink
argument_list|,
name|dirsS
argument_list|)
operator|.
name|getPath
argument_list|()
argument_list|,
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByName
argument_list|(
literal|"PDF"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|,
name|tm
operator|.
name|getStringRepresentation
argument_list|()
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|createNewBlankEntry (String fileName)
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|createNewBlankEntry
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|newEntry
init|=
name|createNewEntry
argument_list|()
decl_stmt|;
name|newEntry
operator|.
name|ifPresent
argument_list|(
name|bibEntry
lambda|->
block|{
name|DroppedFileHandler
name|dfh
init|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
decl_stmt|;
name|dfh
operator|.
name|linkPdfToEntry
argument_list|(
name|fileName
argument_list|,
name|bibEntry
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
return|return
name|newEntry
return|;
block|}
DECL|method|doContentImport (String fileName, List<BibEntry> res)
specifier|private
name|void
name|doContentImport
parameter_list|(
name|String
name|fileName
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|res
parameter_list|)
block|{
name|PdfContentImporter
name|contentImporter
init|=
operator|new
name|PdfContentImporter
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|Path
name|filePath
init|=
name|Paths
operator|.
name|get
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
name|ParserResult
name|result
init|=
name|contentImporter
operator|.
name|importDatabase
argument_list|(
name|filePath
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|hasWarnings
argument_list|()
condition|)
block|{
name|frame
operator|.
name|showMessage
argument_list|(
name|result
operator|.
name|getErrorMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|hasEntries
argument_list|()
condition|)
block|{
comment|// import failed -> generate default entry
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|res
operator|::
name|add
argument_list|)
expr_stmt|;
return|return;
block|}
comment|// only one entry is imported
name|BibEntry
name|entry
init|=
name|result
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
comment|// insert entry to database and link file
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
name|BibtexKeyPatternUtil
operator|.
name|makeLabel
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
argument_list|,
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|entry
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBibtexKeyPatternPreferences
argument_list|()
argument_list|)
expr_stmt|;
name|DroppedFileHandler
name|dfh
init|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
decl_stmt|;
name|dfh
operator|.
name|linkPdfToEntry
argument_list|(
name|fileName
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|panel
operator|.
name|highlightEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
name|AUTO_OPEN_FORM
argument_list|)
condition|)
block|{
name|EntryEditor
name|editor
init|=
name|panel
operator|.
name|getEntryEditor
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|panel
operator|.
name|showEntryEditor
argument_list|(
name|editor
argument_list|)
expr_stmt|;
block|}
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|createNewEntry ()
specifier|private
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|createNewEntry
parameter_list|()
block|{
comment|// Find out what type is desired
name|EntryTypeDialog
name|etd
init|=
operator|new
name|EntryTypeDialog
argument_list|(
name|frame
argument_list|)
decl_stmt|;
comment|// We want to center the dialog, to make it look nicer.
name|etd
operator|.
name|setLocationRelativeTo
argument_list|(
name|frame
argument_list|)
expr_stmt|;
name|etd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|EntryType
name|type
init|=
name|etd
operator|.
name|getChoice
argument_list|()
decl_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
comment|// Only if the dialog was not canceled.
name|String
name|id
init|=
name|IdGenerator
operator|.
name|next
argument_list|()
decl_stmt|;
specifier|final
name|BibEntry
name|bibEntry
init|=
operator|new
name|BibEntry
argument_list|(
name|id
argument_list|,
name|type
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
try|try
block|{
name|panel
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
comment|// Set owner/timestamp if options are enabled:
name|List
argument_list|<
name|BibEntry
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
name|bibEntry
argument_list|)
expr_stmt|;
name|UpdateField
operator|.
name|setAutomaticFields
argument_list|(
name|list
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getUpdateFieldPreferences
argument_list|()
argument_list|)
expr_stmt|;
comment|// Create an UndoableInsertEntry object.
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|bibEntry
argument_list|,
name|panel
argument_list|)
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
literal|"Added new"
argument_list|)
operator|+
literal|" '"
operator|+
name|type
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|+
literal|"' "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"entry"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
comment|// We are going to select the new entry. Before that, make sure that we are in
comment|// show-entry mode. If we aren't already in that mode, enter the WILL_SHOW_EDITOR
comment|// mode which makes sure the selection will trigger display of the entry editor
comment|// and adjustment of the splitter.
if|if
condition|(
name|panel
operator|.
name|getMode
argument_list|()
operator|!=
name|BasePanelMode
operator|.
name|SHOWING_EDITOR
condition|)
block|{
name|panel
operator|.
name|setMode
argument_list|(
name|BasePanelMode
operator|.
name|WILL_SHOW_EDITOR
argument_list|)
expr_stmt|;
block|}
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|panel
operator|.
name|showEntry
argument_list|(
name|bibEntry
argument_list|)
argument_list|)
expr_stmt|;
comment|// The database just changed.
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
return|return
name|Optional
operator|.
name|of
argument_list|(
name|bibEntry
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Key collision occurred"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
block|}
end_class

end_unit

