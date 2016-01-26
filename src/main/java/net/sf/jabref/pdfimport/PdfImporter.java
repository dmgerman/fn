begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|awt
operator|.
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Point
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
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|InputStream
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
name|model
operator|.
name|entry
operator|.
name|EntryType
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
name|preftabs
operator|.
name|ImportSettingsTab
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
name|gui
operator|.
name|util
operator|.
name|FocusRequester
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
name|util
operator|.
name|PositionWindow
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
name|OutputPrinter
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
name|util
operator|.
name|Util
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
name|MainTable
name|entryTable
decl_stmt|;
DECL|field|dropRow
specifier|private
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
comment|/**      * Used nowhere else, will be removed at the JavaFX migration      */
DECL|method|centerRelativeToWindow (java.awt.Dialog diag, java.awt.Container win)
specifier|private
specifier|static
name|void
name|centerRelativeToWindow
parameter_list|(
name|java
operator|.
name|awt
operator|.
name|Dialog
name|diag
parameter_list|,
name|java
operator|.
name|awt
operator|.
name|Container
name|win
parameter_list|)
block|{
name|int
name|x
decl_stmt|;
name|int
name|y
decl_stmt|;
name|Point
name|topLeft
init|=
name|win
operator|.
name|getLocationOnScreen
argument_list|()
decl_stmt|;
name|Dimension
name|parentSize
init|=
name|win
operator|.
name|getSize
argument_list|()
decl_stmt|;
name|Dimension
name|mySize
init|=
name|diag
operator|.
name|getSize
argument_list|()
decl_stmt|;
if|if
condition|(
name|parentSize
operator|.
name|width
operator|>
name|mySize
operator|.
name|width
condition|)
block|{
name|x
operator|=
operator|(
operator|(
name|parentSize
operator|.
name|width
operator|-
name|mySize
operator|.
name|width
operator|)
operator|/
literal|2
operator|)
operator|+
name|topLeft
operator|.
name|x
expr_stmt|;
block|}
else|else
block|{
name|x
operator|=
name|topLeft
operator|.
name|x
expr_stmt|;
block|}
if|if
condition|(
name|parentSize
operator|.
name|height
operator|>
name|mySize
operator|.
name|height
condition|)
block|{
name|y
operator|=
operator|(
operator|(
name|parentSize
operator|.
name|height
operator|-
name|mySize
operator|.
name|height
operator|)
operator|/
literal|2
operator|)
operator|+
name|topLeft
operator|.
name|y
expr_stmt|;
block|}
else|else
block|{
name|y
operator|=
name|topLeft
operator|.
name|y
expr_stmt|;
block|}
name|diag
operator|.
name|setLocation
argument_list|(
name|x
argument_list|,
name|y
argument_list|)
expr_stmt|;
block|}
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
specifier|public
name|String
index|[]
name|noPdfFiles
decl_stmt|;
DECL|field|entries
specifier|public
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
decl_stmt|;
block|}
comment|/**      *      * Imports the PDF files given by fileNames      *      * @param fileNames states the names of the files to import      * @return list of successful created BibTeX entries and list of non-PDF files      */
DECL|method|importPdfFiles (String[] fileNames, OutputPrinter status)
specifier|public
name|ImportPdfFilesResult
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
name|importPdfFiles
argument_list|(
name|files
argument_list|,
name|status
argument_list|)
decl_stmt|;
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
name|ImportPdfFilesResult
name|res
init|=
operator|new
name|ImportPdfFilesResult
argument_list|()
decl_stmt|;
name|res
operator|.
name|noPdfFiles
operator|=
name|noPdfFilesArray
expr_stmt|;
name|res
operator|.
name|entries
operator|=
name|entries
expr_stmt|;
return|return
name|res
return|;
block|}
comment|/**      * @param fileNames - PDF files to import      * @return true if the import succeeded, false otherwise      */
DECL|method|importPdfFiles (List<String> fileNames, OutputPrinter status)
specifier|private
name|List
argument_list|<
name|BibEntry
argument_list|>
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
name|List
argument_list|<
name|String
argument_list|>
name|dirsS
init|=
name|panel
operator|.
name|loadedDatabase
operator|.
name|getMetaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
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
name|fileNameLoop
label|:
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
name|BibEntry
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
operator|>=
literal|0
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
name|neverShow
condition|?
name|globalChoice
else|:
name|importDialog
operator|.
name|getChoice
argument_list|()
decl_stmt|;
name|DroppedFileHandler
name|dfh
decl_stmt|;
name|BibEntry
name|entry
decl_stmt|;
name|InputStream
name|in
init|=
literal|null
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|localRes
init|=
literal|null
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
name|localRes
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
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
if|if
condition|(
operator|(
name|localRes
operator|==
literal|null
operator|)
operator|||
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
name|entry
operator|=
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
continue|continue
name|fileNameLoop
continue|;
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
literal|"pdf"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
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
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Import failed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|entry
operator|=
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
continue|continue
name|fileNameLoop
continue|;
block|}
try|try
block|{
name|localRes
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
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Import failed"
argument_list|,
name|e
argument_list|)
expr_stmt|;
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|entry
operator|=
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
continue|continue
name|fileNameLoop
continue|;
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
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
comment|// import failed -> generate default entry
if|if
condition|(
operator|(
name|localRes
operator|==
literal|null
operator|)
operator|||
name|localRes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|entry
operator|=
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
continue|continue
name|fileNameLoop
continue|;
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
name|loadedDatabase
operator|.
name|getMetaData
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
name|panel
operator|.
name|adjustSplitter
argument_list|()
expr_stmt|;
block|}
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|NOMETA
case|:
name|entry
operator|=
name|createNewBlankEntry
argument_list|(
name|fileName
argument_list|)
expr_stmt|;
name|res
operator|.
name|add
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
name|res
return|;
block|}
DECL|method|createNewBlankEntry (String fileName)
specifier|private
name|BibEntry
name|createNewBlankEntry
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|BibEntry
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
return|return
name|newEntry
return|;
block|}
DECL|method|createNewEntry ()
specifier|private
name|BibEntry
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
name|PositionWindow
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
comment|// Only if the dialog was not cancelled.
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
name|be
init|=
operator|new
name|BibEntry
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
name|BasePanel
operator|.
name|SHOWING_EDITOR
condition|)
block|{
name|panel
operator|.
name|setMode
argument_list|(
name|BasePanel
operator|.
name|WILL_SHOW_EDITOR
argument_list|)
expr_stmt|;
block|}
comment|/*int row = entryTable.findEntry(be);                 if (row>= 0)                     // Selects the entry. The selection listener will open the editor.                      if (row>= 0) {                         try{                             entryTable.setRowSelectionInterval(row, row);                         }catch(IllegalArgumentException e){                             System.out.println("RowCount: " + entryTable.getRowCount());                         }                          //entryTable.setActiveRow(row);                         entryTable.ensureVisible(row);                      }                 else {                     // The entry is not visible in the table, perhaps due to a filtering search                     // or group selection. Show the entry editor anyway:                     panel.showEntry(be);                 }   */
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
literal|null
return|;
block|}
DECL|method|readXmpEntries (String fileName)
specifier|private
specifier|static
name|List
argument_list|<
name|BibEntry
argument_list|>
name|readXmpEntries
parameter_list|(
name|String
name|fileName
parameter_list|)
block|{
name|List
argument_list|<
name|BibEntry
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
DECL|method|hasXmpEntries (List<BibEntry> xmpEntriesInFile)
specifier|private
specifier|static
name|boolean
name|hasXmpEntries
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|xmpEntriesInFile
parameter_list|)
block|{
return|return
operator|!
operator|(
operator|(
name|xmpEntriesInFile
operator|==
literal|null
operator|)
operator|||
name|xmpEntriesInFile
operator|.
name|isEmpty
argument_list|()
operator|)
return|;
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

