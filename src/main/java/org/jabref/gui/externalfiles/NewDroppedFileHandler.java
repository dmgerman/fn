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
name|io
operator|.
name|IOException
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
name|Optional
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonBar
operator|.
name|ButtonData
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|ButtonType
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|DialogPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TextArea
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|VBox
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|text
operator|.
name|Text
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
name|DialogService
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
name|logic
operator|.
name|externalfiles
operator|.
name|ExternalFilesContentImporter
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
name|ImportFormatPreferences
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
name|logic
operator|.
name|util
operator|.
name|UpdateField
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
name|util
operator|.
name|UpdateFieldPreferences
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
name|util
operator|.
name|io
operator|.
name|FileUtil
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
name|metadata
operator|.
name|FilePreferences
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
name|util
operator|.
name|FileUpdateMonitor
import|;
end_import

begin_import
import|import
name|org
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
DECL|class|NewDroppedFileHandler
specifier|public
class|class
name|NewDroppedFileHandler
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
name|NewDroppedFileHandler
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|bibDatabaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
decl_stmt|;
DECL|field|updateFieldPreferences
specifier|private
specifier|final
name|UpdateFieldPreferences
name|updateFieldPreferences
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|fileUpdateMonitor
specifier|private
specifier|final
name|FileUpdateMonitor
name|fileUpdateMonitor
decl_stmt|;
DECL|field|linker
specifier|private
specifier|final
name|ExternalFilesEntryLinker
name|linker
decl_stmt|;
DECL|field|contentImporter
specifier|private
specifier|final
name|ExternalFilesContentImporter
name|contentImporter
decl_stmt|;
DECL|method|NewDroppedFileHandler (DialogService dialogService, BibDatabaseContext bibDatabaseContext, ExternalFileTypes externalFileTypes, FilePreferences filePreferences, ImportFormatPreferences importFormatPreferences, UpdateFieldPreferences updateFieldPreferences, FileUpdateMonitor fileupdateMonitor)
specifier|public
name|NewDroppedFileHandler
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|,
name|ExternalFileTypes
name|externalFileTypes
parameter_list|,
name|FilePreferences
name|filePreferences
parameter_list|,
name|ImportFormatPreferences
name|importFormatPreferences
parameter_list|,
name|UpdateFieldPreferences
name|updateFieldPreferences
parameter_list|,
name|FileUpdateMonitor
name|fileupdateMonitor
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|bibDatabaseContext
operator|=
name|bibDatabaseContext
expr_stmt|;
name|this
operator|.
name|updateFieldPreferences
operator|=
name|updateFieldPreferences
expr_stmt|;
name|this
operator|.
name|fileUpdateMonitor
operator|=
name|fileupdateMonitor
expr_stmt|;
name|this
operator|.
name|linker
operator|=
operator|new
name|ExternalFilesEntryLinker
argument_list|(
name|externalFileTypes
argument_list|,
name|filePreferences
argument_list|,
name|bibDatabaseContext
argument_list|)
expr_stmt|;
name|this
operator|.
name|contentImporter
operator|=
operator|new
name|ExternalFilesContentImporter
argument_list|(
name|importFormatPreferences
argument_list|)
expr_stmt|;
block|}
DECL|method|addNewEntryFromXMPorPDFContent (BibEntry entry, List<Path> files)
specifier|public
name|void
name|addNewEntryFromXMPorPDFContent
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|files
parameter_list|)
block|{
for|for
control|(
name|Path
name|file
range|:
name|files
control|)
block|{
if|if
condition|(
name|FileUtil
operator|.
name|getFileExtension
argument_list|(
name|file
argument_list|)
operator|.
name|filter
argument_list|(
name|ext
lambda|->
literal|"pdf"
operator|.
name|equals
argument_list|(
name|ext
argument_list|)
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|pdfResult
init|=
name|contentImporter
operator|.
name|importPDFContent
argument_list|(
name|file
argument_list|)
decl_stmt|;
comment|//FIXME: Show merge dialog if working again properly
name|List
argument_list|<
name|BibEntry
argument_list|>
name|xmpEntriesInFile
init|=
name|contentImporter
operator|.
name|importXMPContent
argument_list|(
name|file
argument_list|)
decl_stmt|;
comment|//First try xmp import, if empty try pdf import, otherwise show dialog
if|if
condition|(
name|xmpEntriesInFile
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
if|if
condition|(
name|pdfResult
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|addToEntryRenameAndMoveToFileDir
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|showImportOrLinkFileDialog
argument_list|(
name|pdfResult
argument_list|,
name|file
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|showImportOrLinkFileDialog
argument_list|(
name|xmpEntriesInFile
argument_list|,
name|file
argument_list|,
name|entry
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
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem reading XMP"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|addToEntryRenameAndMoveToFileDir
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|importEntriesFromDroppedBibFiles (Path bibFile)
specifier|public
name|void
name|importEntriesFromDroppedBibFiles
parameter_list|(
name|Path
name|bibFile
parameter_list|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToImport
init|=
name|contentImporter
operator|.
name|importFromBibFile
argument_list|(
name|bibFile
argument_list|,
name|fileUpdateMonitor
argument_list|)
decl_stmt|;
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntries
argument_list|(
name|entriesToImport
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
name|USE_OWNER
argument_list|)
condition|)
block|{
comment|// Set owner field to default value
name|UpdateField
operator|.
name|setAutomaticFields
argument_list|(
name|entriesToImport
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|,
name|updateFieldPreferences
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|showImportOrLinkFileDialog (List<BibEntry> entriesToImport, Path fileName, BibEntry entryToLink)
specifier|private
name|void
name|showImportOrLinkFileDialog
parameter_list|(
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesToImport
parameter_list|,
name|Path
name|fileName
parameter_list|,
name|BibEntry
name|entryToLink
parameter_list|)
block|{
name|DialogPane
name|pane
init|=
operator|new
name|DialogPane
argument_list|()
decl_stmt|;
name|VBox
name|vbox
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
name|Text
name|text
init|=
operator|new
name|Text
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"The PDF contains one or several BibTeX-records."
argument_list|)
operator|+
literal|"\n"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you want to import these as new entries into the current library or do you want to link the file to the entry?"
argument_list|)
argument_list|)
decl_stmt|;
name|vbox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|entriesToImport
operator|.
name|forEach
argument_list|(
name|entry
lambda|->
block|{
name|TextArea
name|textArea
init|=
operator|new
name|TextArea
argument_list|(
name|entry
operator|.
name|toString
argument_list|()
argument_list|)
decl_stmt|;
name|textArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|vbox
operator|.
name|getChildren
argument_list|()
operator|.
name|add
argument_list|(
name|textArea
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|pane
operator|.
name|setContent
argument_list|(
name|vbox
argument_list|)
expr_stmt|;
name|ButtonType
name|importButton
init|=
operator|new
name|ButtonType
argument_list|(
literal|"Import into library"
argument_list|,
name|ButtonData
operator|.
name|OK_DONE
argument_list|)
decl_stmt|;
name|ButtonType
name|linkToEntry
init|=
operator|new
name|ButtonType
argument_list|(
literal|"Link file to entry"
argument_list|,
name|ButtonData
operator|.
name|OTHER
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|buttonPressed
init|=
name|dialogService
operator|.
name|showCustomDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"XMP-metadata found in PDF: %0"
argument_list|,
name|fileName
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|pane
argument_list|,
name|importButton
argument_list|,
name|linkToEntry
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
decl_stmt|;
if|if
condition|(
name|buttonPressed
operator|.
name|equals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|importButton
argument_list|)
argument_list|)
condition|)
block|{
name|bibDatabaseContext
operator|.
name|getDatabase
argument_list|()
operator|.
name|insertEntries
argument_list|(
name|entriesToImport
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|buttonPressed
operator|.
name|equals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|linkToEntry
argument_list|)
argument_list|)
condition|)
block|{
name|addToEntryRenameAndMoveToFileDir
argument_list|(
name|entryToLink
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|fileName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addToEntryRenameAndMoveToFileDir (BibEntry entry, List<Path> files)
specifier|public
name|void
name|addToEntryRenameAndMoveToFileDir
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|files
parameter_list|)
block|{
name|linker
operator|.
name|addFilesToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
name|linker
operator|.
name|moveLinkedFilesToFileDir
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|linker
operator|.
name|renameLinkedFilesToPattern
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
DECL|method|copyFilesToFileDirAndAddToEntry (BibEntry entry, List<Path> files)
specifier|public
name|void
name|copyFilesToFileDirAndAddToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|files
parameter_list|)
block|{
for|for
control|(
name|Path
name|file
range|:
name|files
control|)
block|{
name|linker
operator|.
name|copyFileToFileDir
argument_list|(
name|file
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|copiedFile
lambda|->
block|{
name|linker
operator|.
name|addFilesToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|addToEntry (BibEntry entry, List<Path> files)
specifier|public
name|void
name|addToEntry
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|List
argument_list|<
name|Path
argument_list|>
name|files
parameter_list|)
block|{
name|linker
operator|.
name|addFilesToEntry
argument_list|(
name|entry
argument_list|,
name|files
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

