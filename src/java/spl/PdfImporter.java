begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|spl
package|package
name|spl
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
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
name|Arrays
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
name|external
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
name|external
operator|.
name|ExternalFileType
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
name|FileListEditor
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
name|imports
operator|.
name|ImportMenuItem
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
name|imports
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
name|imports
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
name|labelPattern
operator|.
name|LabelPatternUtil
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
name|util
operator|.
name|XMPUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|sciplore
operator|.
name|beans
operator|.
name|Document
import|;
end_import

begin_import
import|import
name|spl
operator|.
name|filter
operator|.
name|PdfFileFilter
import|;
end_import

begin_import
import|import
name|spl
operator|.
name|gui
operator|.
name|ImportDialog
import|;
end_import

begin_import
import|import
name|spl
operator|.
name|gui
operator|.
name|MetaDataListDialog
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
name|Arrays
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

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: Christoph Arbeit  * Date: 08.09.2010  * Time: 14:49:08  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|PdfImporter
specifier|public
class|class
name|PdfImporter
block|{
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|entryTable
specifier|private
name|MainTable
name|entryTable
decl_stmt|;
DECL|field|dropRow
specifier|private
name|int
name|dropRow
decl_stmt|;
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
comment|/**      *       * Imports the PDF files given by fileNames      *       * @param fileNames states the names of the files to import      * @return list of non-PDF files      */
DECL|method|importPdfFiles (String[] fileNames, OutputPrinter status)
specifier|public
name|String
index|[]
name|importPdfFiles
parameter_list|(
name|String
index|[]
name|fileNames
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|fileNames
argument_list|)
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
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|PdfFileFilter
name|pdfFilter
init|=
operator|new
name|PdfFileFilter
argument_list|()
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
name|importPdfFiles
argument_list|(
name|files
argument_list|,
name|status
argument_list|)
expr_stmt|;
name|String
index|[]
name|noPdfFilesArray
init|=
operator|new
name|String
index|[
name|noPdfFiles
operator|.
name|size
argument_list|()
index|]
decl_stmt|;
name|noPdfFiles
operator|.
name|toArray
argument_list|(
name|noPdfFilesArray
argument_list|)
expr_stmt|;
return|return
name|noPdfFilesArray
return|;
block|}
comment|/**      * @param fileNames - PDF files to import      * @return true if the import succeeded, false otherwise      */
DECL|method|importPdfFiles (List<String> fileNames, OutputPrinter status)
specifier|private
name|boolean
name|importPdfFiles
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|,
name|OutputPrinter
name|status
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|==
literal|null
condition|)
return|return
literal|false
return|;
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
name|ImportSettingsTab
operator|.
name|PREF_IMPORT_ALWAYSUSE
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
name|ImportSettingsTab
operator|.
name|PREF_IMPORT_DEFAULT_PDF_IMPORT_STYLE
argument_list|)
decl_stmt|;
comment|// Get a list of file directories:
name|ArrayList
argument_list|<
name|File
argument_list|>
name|dirs
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
decl_stmt|;
name|String
index|[]
name|dirsS
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
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
name|dirsS
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|dirs
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|dirsS
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|fileName
range|:
name|fileNames
control|)
block|{
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|xmpEntriesInFile
init|=
name|readXmpEntries
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
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
argument_list|,
name|fileName
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|hasXmpEntries
argument_list|(
name|xmpEntriesInFile
argument_list|)
condition|)
block|{
name|importDialog
operator|.
name|disableXMPChoice
argument_list|()
expr_stmt|;
block|}
name|Tools
operator|.
name|centerRelativeToWindow
argument_list|(
name|importDialog
argument_list|,
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
name|getDoNotShowAgain
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
operator|(
name|neverShow
condition|?
name|globalChoice
else|:
name|importDialog
operator|.
name|getChoice
argument_list|()
operator|)
decl_stmt|;
name|DroppedFileHandler
name|dfh
decl_stmt|;
name|BibtexEntry
name|entry
decl_stmt|;
name|BibtexEntryType
name|type
decl_stmt|;
name|InputStream
name|in
init|=
literal|null
decl_stmt|;
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|res
init|=
literal|null
decl_stmt|;
name|MetaDataListDialog
name|metaDataListDialog
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
comment|//SplDatabaseChangeListener dataListener = new SplDatabaseChangeListener(frame, panel, entryTable, fileName);
comment|//panel.database().addDatabaseChangeListener(dataListener);
comment|//ImportMenuItem importer = new ImportMenuItem(frame, (entryTable == null));
name|PdfXmpImporter
name|importer
init|=
operator|new
name|PdfXmpImporter
argument_list|()
decl_stmt|;
try|try
block|{
name|in
operator|=
operator|new
name|FileInputStream
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
name|res
operator|=
name|importer
operator|.
name|importEntries
argument_list|(
name|in
argument_list|,
name|frame
argument_list|)
expr_stmt|;
comment|//importer.automatedImport(new String[]{ fileName });
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
finally|finally
block|{
try|try
block|{
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|f
parameter_list|)
block|{}
block|}
comment|// import failed -> generate default entry
if|if
condition|(
operator|(
name|res
operator|==
literal|null
operator|)
operator|||
operator|(
name|res
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|// only one entry is imported
name|entry
operator|=
name|res
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// insert entry to database and link file
name|panel
operator|.
name|database
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
name|FileListEditor
operator|.
name|relativizePath
argument_list|(
name|toLink
argument_list|,
name|dirs
argument_list|)
operator|.
name|getPath
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeByName
argument_list|(
literal|"pdf"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|,
name|tm
operator|.
name|getStringRepresentation
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|CONTENT
case|:
name|PdfContentImporter
name|contentImporter
init|=
operator|new
name|PdfContentImporter
argument_list|()
decl_stmt|;
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
try|try
block|{
name|in
operator|=
operator|new
name|FileInputStream
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// import failed -> generate default entry
name|Globals
operator|.
name|logger
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import failed"
argument_list|)
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
try|try
block|{
name|res
operator|=
name|contentImporter
operator|.
name|importEntries
argument_list|(
name|in
argument_list|,
name|status
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// import failed -> generate default entry
name|Globals
operator|.
name|logger
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import failed"
argument_list|)
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
finally|finally
block|{
try|try
block|{
name|in
operator|.
name|close
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|f
parameter_list|)
block|{}
block|}
comment|// import failed -> generate default entry
if|if
condition|(
operator|(
name|res
operator|==
literal|null
operator|)
operator|||
operator|(
name|res
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
comment|// only one entry is imported
name|entry
operator|=
name|res
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
comment|// insert entry to database and link file
name|panel
operator|.
name|database
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
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|panel
operator|.
name|metaData
argument_list|()
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|dfh
operator|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
expr_stmt|;
name|dfh
operator|.
name|linkPdfToEntry
argument_list|(
name|fileName
argument_list|,
name|entryTable
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
literal|"autoOpenForm"
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
name|panel
operator|.
name|adjustSplitter
argument_list|()
expr_stmt|;
block|}
break|break;
case|case
name|ImportDialog
operator|.
name|MRDLIB
case|:
name|metaDataListDialog
operator|=
operator|new
name|MetaDataListDialog
argument_list|(
name|fileName
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|Tools
operator|.
name|centerRelativeToWindow
argument_list|(
name|metaDataListDialog
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|metaDataListDialog
operator|.
name|showDialog
argument_list|()
expr_stmt|;
name|Document
name|document
init|=
name|metaDataListDialog
operator|.
name|getXmlDocuments
argument_list|()
decl_stmt|;
if|if
condition|(
name|document
operator|!=
literal|null
comment|/*&& documents.getDocuments() != null&& documents.getDocuments().size()> 0*/
operator|&&
name|metaDataListDialog
operator|.
name|getResult
argument_list|()
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|int
name|selected
init|=
name|metaDataListDialog
operator|.
name|getTableMetadata
argument_list|()
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|>
operator|-
literal|1
comment|/*&& selected< documents.getDocuments().size()*/
condition|)
block|{
comment|//Document document = documents/*.getDocuments().get(selected)*/;
name|String
name|id
init|=
name|Util
operator|.
name|createNeutralId
argument_list|()
decl_stmt|;
name|entry
operator|=
operator|new
name|BibtexEntry
argument_list|(
name|id
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldExists
argument_list|(
name|document
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|type
operator|=
name|BibtexEntryType
operator|.
name|getStandardType
argument_list|(
name|document
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|type
operator|==
literal|null
condition|)
block|{
name|type
operator|=
name|BibtexEntryType
operator|.
name|ARTICLE
expr_stmt|;
block|}
name|entry
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|entry
operator|.
name|setType
argument_list|(
name|BibtexEntryType
operator|.
name|ARTICLE
argument_list|)
expr_stmt|;
block|}
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Util
operator|.
name|setAutomaticFields
argument_list|(
name|list
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|//insertFields(entry.getRequiredFields(), entry, document);
name|insertFields
argument_list|(
name|BibtexFields
operator|.
name|getAllFieldNames
argument_list|()
argument_list|,
name|entry
argument_list|,
name|document
argument_list|)
expr_stmt|;
comment|//insertFields(entry.getOptionalFields(), entry, document);
name|panel
operator|.
name|database
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|dfh
operator|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
expr_stmt|;
name|dfh
operator|.
name|linkPdfToEntry
argument_list|(
name|fileName
argument_list|,
name|entryTable
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|LabelPatternUtil
operator|.
name|makeLabel
argument_list|(
name|panel
operator|.
name|metaData
argument_list|()
argument_list|,
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|metaDataListDialog
operator|.
name|getResult
argument_list|()
operator|==
name|JOptionPane
operator|.
name|CANCEL_OPTION
condition|)
block|{
continue|continue;
block|}
elseif|else
if|if
condition|(
name|metaDataListDialog
operator|.
name|getResult
argument_list|()
operator|==
name|JOptionPane
operator|.
name|NO_OPTION
condition|)
block|{
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|document
operator|==
literal|null
comment|/*|| document.getDocuments() == null || document.getDocuments().size()<= 0*/
operator|&&
name|metaDataListDialog
operator|.
name|getResult
argument_list|()
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
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
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|UPDATEEMPTYFIELDS
case|:
name|metaDataListDialog
operator|=
operator|new
name|MetaDataListDialog
argument_list|(
name|fileName
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|Tools
operator|.
name|centerRelativeToWindow
argument_list|(
name|metaDataListDialog
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|metaDataListDialog
operator|.
name|showDialog
argument_list|()
expr_stmt|;
name|document
operator|=
name|metaDataListDialog
operator|.
name|getXmlDocuments
argument_list|()
expr_stmt|;
if|if
condition|(
name|document
operator|!=
literal|null
comment|/*&& document.getDocuments() != null&& document.getDocuments().size()> 0*/
operator|&&
name|metaDataListDialog
operator|.
name|getResult
argument_list|()
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|int
name|selected
init|=
name|metaDataListDialog
operator|.
name|getTableMetadata
argument_list|()
operator|.
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|>
operator|-
literal|1
comment|/*&& selected< document.getDocuments().size()*/
condition|)
block|{
comment|//XmlDocument document = documents.getDocuments().get(selected);
name|entry
operator|=
name|entryTable
operator|.
name|getEntryAt
argument_list|(
name|dropRow
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldExists
argument_list|(
name|document
operator|.
name|getType
argument_list|()
argument_list|)
condition|)
block|{
name|type
operator|=
name|BibtexEntryType
operator|.
name|getStandardType
argument_list|(
name|document
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|type
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setType
argument_list|(
name|type
argument_list|)
expr_stmt|;
block|}
block|}
comment|//insertFields(entry.getRequiredFields(), entry, document);
name|insertFields
argument_list|(
name|BibtexFields
operator|.
name|getAllFieldNames
argument_list|()
argument_list|,
name|entry
argument_list|,
name|document
argument_list|)
expr_stmt|;
comment|//insertFields(entry.getOptionalFields(), entry, document);
name|dfh
operator|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
expr_stmt|;
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
block|}
block|}
break|break;
case|case
name|ImportDialog
operator|.
name|ONLYATTACH
case|:
name|dfh
operator|=
operator|new
name|DroppedFileHandler
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|)
expr_stmt|;
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
block|}
block|}
block|}
return|return
literal|true
return|;
block|}
DECL|method|createNewBlankEntry (String fileName)
specifier|private
name|void
name|createNewBlankEntry
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|BibtexEntry
name|newEntry
init|=
name|createNewEntry
argument_list|()
decl_stmt|;
if|if
condition|(
name|newEntry
operator|!=
literal|null
condition|)
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
name|entryTable
argument_list|,
name|newEntry
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|insertFields (String[] fields, BibtexEntry entry, Document xmlDocument)
specifier|private
name|void
name|insertFields
parameter_list|(
name|String
index|[]
name|fields
parameter_list|,
name|BibtexEntry
name|entry
parameter_list|,
name|Document
name|xmlDocument
parameter_list|)
block|{
name|DocumentWrapper
name|document
init|=
operator|new
name|DocumentWrapper
argument_list|(
name|xmlDocument
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
if|if
condition|(
name|entry
operator|.
name|getField
argument_list|(
name|field
argument_list|)
operator|!=
literal|null
condition|)
block|{
continue|continue;
block|}
if|if
condition|(
name|field
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"author"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|document
operator|.
name|getAuthors
argument_list|(
literal|"and"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|field
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"title"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|document
operator|.
name|getTitle
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|field
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"abstract"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|document
operator|.
name|getAbstract
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*if(field.equalsIgnoreCase("keywords")){                 entry.setField(field, document.getKeyWords());             }*/
if|if
condition|(
name|field
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"doi"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|document
operator|.
name|getDoi
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*if(field.equalsIgnoreCase("pages")){                 entry.setField(field, document.getPages());             }             if(field.equalsIgnoreCase("volume")){                 entry.setField(field, document.getVolume());             }             if(field.equalsIgnoreCase("number")){                 entry.setField(field, document.getNumber());             }*/
if|if
condition|(
name|field
operator|.
name|equalsIgnoreCase
argument_list|(
literal|"year"
argument_list|)
condition|)
block|{
name|entry
operator|.
name|setField
argument_list|(
name|field
argument_list|,
name|document
operator|.
name|getYear
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/*if(field.equalsIgnoreCase("month")){                 entry.setField(field, document.getMonth());             }             if(field.equalsIgnoreCase("day")){                 entry.setField(field, document.getDay());             }             if(field.equalsIgnoreCase("booktitle")){                 entry.setField(field, document.getVenue());             }             if(field.equalsIgnoreCase("journal")){                 entry.setField(field, document.getVenue());             }*/
block|}
block|}
DECL|method|fieldExists (String string)
specifier|private
name|boolean
name|fieldExists
parameter_list|(
name|String
name|string
parameter_list|)
block|{
return|return
name|string
operator|!=
literal|null
operator|&&
name|string
operator|.
name|length
argument_list|()
operator|>
literal|0
return|;
block|}
DECL|method|createNewEntry ()
specifier|private
name|BibtexEntry
name|createNewEntry
parameter_list|()
block|{
comment|// Find out what type is wanted.
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
name|Util
operator|.
name|placeDialog
argument_list|(
name|etd
argument_list|,
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
name|BibtexEntryType
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
comment|// Only if the dialog was not cancelled.
name|String
name|id
init|=
name|Util
operator|.
name|createNeutralId
argument_list|()
decl_stmt|;
specifier|final
name|BibtexEntry
name|be
init|=
operator|new
name|BibtexEntry
argument_list|(
name|id
argument_list|,
name|type
argument_list|)
decl_stmt|;
try|try
block|{
name|panel
operator|.
name|database
argument_list|()
operator|.
name|insertEntry
argument_list|(
name|be
argument_list|)
expr_stmt|;
comment|// Set owner/timestamp if options are enabled:
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|list
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|Util
operator|.
name|setAutomaticFields
argument_list|(
name|list
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
comment|// Create an UndoableInsertEntry object.
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|panel
operator|.
name|database
argument_list|()
argument_list|,
name|be
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Globals
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
name|Globals
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
name|panel
operator|.
name|SHOWING_EDITOR
condition|)
block|{
name|panel
operator|.
name|setMode
argument_list|(
name|panel
operator|.
name|WILL_SHOW_EDITOR
argument_list|)
expr_stmt|;
block|}
comment|/*int row = entryTable.findEntry(be);                 if (row>= 0)                     // Selects the entry. The selection listener will open the editor.                                            if (row>= 0) {                         try{                             entryTable.setRowSelectionInterval(row, row);                         }catch(IllegalArgumentException e){                             System.out.println("RowCount: " + entryTable.getRowCount());                         }                          //entryTable.setActiveRow(row);                         entryTable.ensureVisible(row);                      }                 else {                     // The entry is not visible in the table, perhaps due to a filtering search                     // or group selection. Show the entry editor anyway:                     panel.showEntry(be);                 }   */
name|panel
operator|.
name|showEntry
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
comment|// The database just changed.
operator|new
name|FocusRequester
argument_list|(
name|panel
operator|.
name|getEntryEditor
argument_list|(
name|be
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|be
return|;
block|}
catch|catch
parameter_list|(
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|Util
operator|.
name|pr
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
literal|null
return|;
block|}
DECL|method|readXmpEntries (String fileName)
specifier|private
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|readXmpEntries
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|xmpEntriesInFile
init|=
literal|null
decl_stmt|;
try|try
block|{
name|xmpEntriesInFile
operator|=
name|XMPUtil
operator|.
name|readXMP
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|e
parameter_list|)
block|{
comment|// Todo Logging
block|}
return|return
name|xmpEntriesInFile
return|;
block|}
DECL|method|hasXmpEntries (List<BibtexEntry> xmpEntriesInFile)
specifier|private
name|boolean
name|hasXmpEntries
parameter_list|(
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|xmpEntriesInFile
parameter_list|)
block|{
if|if
condition|(
operator|(
name|xmpEntriesInFile
operator|==
literal|null
operator|)
operator|||
operator|(
name|xmpEntriesInFile
operator|.
name|size
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
return|return
literal|false
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
DECL|method|getEntryTable ()
specifier|public
name|MainTable
name|getEntryTable
parameter_list|()
block|{
return|return
name|entryTable
return|;
block|}
DECL|method|setEntryTable (MainTable entryTable)
specifier|public
name|void
name|setEntryTable
parameter_list|(
name|MainTable
name|entryTable
parameter_list|)
block|{
name|this
operator|.
name|entryTable
operator|=
name|entryTable
expr_stmt|;
block|}
DECL|method|getDropRow ()
specifier|public
name|int
name|getDropRow
parameter_list|()
block|{
return|return
name|dropRow
return|;
block|}
DECL|method|setDropRow (int dropRow)
specifier|public
name|void
name|setDropRow
parameter_list|(
name|int
name|dropRow
parameter_list|)
block|{
name|this
operator|.
name|dropRow
operator|=
name|dropRow
expr_stmt|;
block|}
block|}
end_class

end_unit

