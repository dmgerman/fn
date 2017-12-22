begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
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
name|Files
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
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|Observable
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|BooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|DoubleProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleBooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleDoubleProperty
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
name|Alert
operator|.
name|AlertType
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
name|AbstractViewModel
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
name|FXDialogService
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
name|desktop
operator|.
name|JabRefDesktop
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
name|filelist
operator|.
name|FileListEntryEditor
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
name|cleanup
operator|.
name|MoveFilesCleanup
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
name|cleanup
operator|.
name|RenamePdfCleanup
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
name|entry
operator|.
name|LinkedFile
import|;
end_import

begin_import
import|import
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|GlyphIcons
import|;
end_import

begin_import
import|import
name|de
operator|.
name|jensd
operator|.
name|fx
operator|.
name|glyphs
operator|.
name|materialdesignicons
operator|.
name|MaterialDesignIcon
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
DECL|class|LinkedFileViewModel
specifier|public
class|class
name|LinkedFileViewModel
extends|extends
name|AbstractViewModel
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
name|LinkedFileViewModel
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|linkedFile
specifier|private
specifier|final
name|LinkedFile
name|linkedFile
decl_stmt|;
DECL|field|databaseContext
specifier|private
specifier|final
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|downloadProgress
specifier|private
specifier|final
name|DoubleProperty
name|downloadProgress
init|=
operator|new
name|SimpleDoubleProperty
argument_list|(
operator|-
literal|1
argument_list|)
decl_stmt|;
DECL|field|downloadOngoing
specifier|private
specifier|final
name|BooleanProperty
name|downloadOngoing
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
decl_stmt|;
DECL|field|isAutomaticallyFound
specifier|private
specifier|final
name|BooleanProperty
name|isAutomaticallyFound
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|false
argument_list|)
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
init|=
operator|new
name|FXDialogService
argument_list|()
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|method|LinkedFileViewModel (LinkedFile linkedFile, BibEntry entry, BibDatabaseContext databaseContext)
specifier|public
name|LinkedFileViewModel
parameter_list|(
name|LinkedFile
name|linkedFile
parameter_list|,
name|BibEntry
name|entry
parameter_list|,
name|BibDatabaseContext
name|databaseContext
parameter_list|)
block|{
name|this
operator|.
name|linkedFile
operator|=
name|linkedFile
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|downloadOngoing
operator|.
name|bind
argument_list|(
name|downloadProgress
operator|.
name|greaterThanOrEqualTo
argument_list|(
literal|0
argument_list|)
operator|.
name|and
argument_list|(
name|downloadProgress
operator|.
name|lessThan
argument_list|(
literal|100
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|isAutomaticallyFound ()
specifier|public
name|boolean
name|isAutomaticallyFound
parameter_list|()
block|{
return|return
name|isAutomaticallyFound
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|isAutomaticallyFoundProperty ()
specifier|public
name|BooleanProperty
name|isAutomaticallyFoundProperty
parameter_list|()
block|{
return|return
name|isAutomaticallyFound
return|;
block|}
DECL|method|downloadOngoingProperty ()
specifier|public
name|BooleanProperty
name|downloadOngoingProperty
parameter_list|()
block|{
return|return
name|downloadOngoing
return|;
block|}
DECL|method|downloadProgressProperty ()
specifier|public
name|DoubleProperty
name|downloadProgressProperty
parameter_list|()
block|{
return|return
name|downloadProgress
return|;
block|}
DECL|method|getFile ()
specifier|public
name|LinkedFile
name|getFile
parameter_list|()
block|{
return|return
name|linkedFile
return|;
block|}
DECL|method|getLink ()
specifier|public
name|String
name|getLink
parameter_list|()
block|{
return|return
name|linkedFile
operator|.
name|getLink
argument_list|()
return|;
block|}
DECL|method|getDescription ()
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|linkedFile
operator|.
name|getDescription
argument_list|()
return|;
block|}
DECL|method|findIn (List<Path> directories)
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|findIn
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|directories
parameter_list|)
block|{
return|return
name|linkedFile
operator|.
name|findIn
argument_list|(
name|directories
argument_list|)
return|;
block|}
comment|/**      * TODO: Be a bit smarter and try to infer correct icon, for example using {@link      * org.jabref.gui.externalfiletype.ExternalFileTypes#getExternalFileTypeByName(String)}      */
DECL|method|getTypeIcon ()
specifier|public
name|GlyphIcons
name|getTypeIcon
parameter_list|()
block|{
return|return
name|MaterialDesignIcon
operator|.
name|FILE_PDF
return|;
block|}
DECL|method|markAsAutomaticallyFound ()
specifier|public
name|void
name|markAsAutomaticallyFound
parameter_list|()
block|{
name|isAutomaticallyFound
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|acceptAsLinked ()
specifier|public
name|void
name|acceptAsLinked
parameter_list|()
block|{
name|isAutomaticallyFound
operator|.
name|setValue
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
DECL|method|getObservables ()
specifier|public
name|Observable
index|[]
name|getObservables
parameter_list|()
block|{
name|List
argument_list|<
name|Observable
argument_list|>
name|observables
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|linkedFile
operator|.
name|getObservables
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|observables
operator|.
name|add
argument_list|(
name|downloadOngoing
argument_list|)
expr_stmt|;
name|observables
operator|.
name|add
argument_list|(
name|downloadProgress
argument_list|)
expr_stmt|;
name|observables
operator|.
name|add
argument_list|(
name|isAutomaticallyFound
argument_list|)
expr_stmt|;
return|return
name|observables
operator|.
name|toArray
argument_list|(
operator|new
name|Observable
index|[
name|observables
operator|.
name|size
argument_list|()
index|]
argument_list|)
return|;
block|}
DECL|method|open ()
specifier|public
name|void
name|open
parameter_list|()
block|{
try|try
block|{
name|Optional
argument_list|<
name|ExternalFileType
argument_list|>
name|type
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|fromLinkedFile
argument_list|(
name|linkedFile
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|JabRefDesktop
operator|.
name|openExternalFileAnyFormat
argument_list|(
name|databaseContext
argument_list|,
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|,
name|type
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
literal|"Cannot open selected file."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openFolder ()
specifier|public
name|void
name|openFolder
parameter_list|()
block|{
try|try
block|{
name|Path
name|path
init|=
literal|null
decl_stmt|;
comment|// absolute path
if|if
condition|(
name|Paths
operator|.
name|get
argument_list|(
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
operator|.
name|isAbsolute
argument_list|()
condition|)
block|{
name|path
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// relative to file folder
for|for
control|(
name|Path
name|folder
range|:
name|databaseContext
operator|.
name|getFileDirectoriesAsPaths
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
control|)
block|{
name|Path
name|file
init|=
name|folder
operator|.
name|resolve
argument_list|(
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|Files
operator|.
name|exists
argument_list|(
name|file
argument_list|)
condition|)
block|{
name|path
operator|=
name|file
expr_stmt|;
break|break;
block|}
block|}
block|}
if|if
condition|(
name|path
operator|!=
literal|null
condition|)
block|{
name|JabRefDesktop
operator|.
name|openFolderAndSelectFile
argument_list|(
name|path
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Cannot open folder"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|rename ()
specifier|public
name|void
name|rename
parameter_list|()
block|{
if|if
condition|(
name|linkedFile
operator|.
name|isOnlineLink
argument_list|()
condition|)
block|{
comment|// Cannot rename remote links
return|return;
block|}
name|Optional
argument_list|<
name|Path
argument_list|>
name|fileDir
init|=
name|databaseContext
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
name|fileDir
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File directory is not set or does not exist!"
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
init|=
name|linkedFile
operator|.
name|findIn
argument_list|(
name|databaseContext
argument_list|,
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
operator|(
name|file
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
name|Files
operator|.
name|exists
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
name|RenamePdfCleanup
name|pdfCleanup
init|=
operator|new
name|RenamePdfCleanup
argument_list|(
literal|false
argument_list|,
name|databaseContext
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getCleanupPreferences
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
operator|.
name|getFileNamePattern
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getLayoutFormatterPreferences
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|,
name|linkedFile
argument_list|)
decl_stmt|;
name|String
name|targetFileName
init|=
name|pdfCleanup
operator|.
name|getTargetFileName
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|boolean
name|confirm
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename file to"
argument_list|)
operator|+
literal|" "
operator|+
name|targetFileName
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|confirm
condition|)
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|fileConflictCheck
init|=
name|pdfCleanup
operator|.
name|findExistingFile
argument_list|(
name|linkedFile
argument_list|,
name|entry
argument_list|)
decl_stmt|;
name|performRenameWithConflictCheck
argument_list|(
name|file
argument_list|,
name|pdfCleanup
argument_list|,
name|targetFileName
argument_list|,
name|fileConflictCheck
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not find file '%0'."
argument_list|,
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|performRenameWithConflictCheck (Optional<Path> file, RenamePdfCleanup pdfCleanup, String targetFileName, Optional<Path> fileConflictCheck)
specifier|private
name|void
name|performRenameWithConflictCheck
parameter_list|(
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
parameter_list|,
name|RenamePdfCleanup
name|pdfCleanup
parameter_list|,
name|String
name|targetFileName
parameter_list|,
name|Optional
argument_list|<
name|Path
argument_list|>
name|fileConflictCheck
parameter_list|)
block|{
name|boolean
name|confirm
decl_stmt|;
if|if
condition|(
operator|!
name|fileConflictCheck
operator|.
name|isPresent
argument_list|()
condition|)
block|{
try|try
block|{
name|pdfCleanup
operator|.
name|cleanupWithException
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
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename failed"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef cannot access the file because it is being used by another process."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|confirm
operator|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File exists"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"'%0' exists. Overwrite file?"
argument_list|,
name|targetFileName
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Overwrite"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|confirm
condition|)
block|{
try|try
block|{
name|FileUtil
operator|.
name|renameFileWithException
argument_list|(
name|fileConflictCheck
operator|.
name|get
argument_list|()
argument_list|,
name|file
operator|.
name|get
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|pdfCleanup
operator|.
name|cleanupWithException
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
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Rename failed"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef cannot access the file because it is being used by another process."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|moveToDefaultDirectory ()
specifier|public
name|void
name|moveToDefaultDirectory
parameter_list|()
block|{
if|if
condition|(
name|linkedFile
operator|.
name|isOnlineLink
argument_list|()
condition|)
block|{
comment|// Cannot move remote links
return|return;
block|}
comment|// Get target folder
name|Optional
argument_list|<
name|Path
argument_list|>
name|fileDir
init|=
name|databaseContext
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
name|fileDir
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File directory is not set or does not exist!"
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
init|=
name|linkedFile
operator|.
name|findIn
argument_list|(
name|databaseContext
argument_list|,
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
operator|(
name|file
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
name|Files
operator|.
name|exists
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
comment|// Linked file exists, so move it
name|MoveFilesCleanup
name|moveFiles
init|=
operator|new
name|MoveFilesCleanup
argument_list|(
name|databaseContext
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getCleanupPreferences
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
operator|.
name|getFileDirPattern
argument_list|()
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
name|getLayoutFormatterPreferences
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
argument_list|,
name|linkedFile
argument_list|)
decl_stmt|;
name|boolean
name|confirm
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file to file directory?"
argument_list|)
operator|+
literal|" "
operator|+
name|fileDir
operator|.
name|get
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|confirm
condition|)
block|{
name|moveFiles
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// File doesn't exist, so we can't move it.
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File not found"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not find file '%0'."
argument_list|,
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|delete ()
specifier|public
name|boolean
name|delete
parameter_list|()
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|file
init|=
name|linkedFile
operator|.
name|findIn
argument_list|(
name|databaseContext
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
decl_stmt|;
name|ButtonType
name|removeFromEntry
init|=
operator|new
name|ButtonType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove from entry"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|ButtonType
name|deleteFromEntry
init|=
operator|new
name|ButtonType
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete from disk"
argument_list|)
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|ButtonType
argument_list|>
name|buttonType
init|=
name|dialogService
operator|.
name|showCustomButtonDialogAndWait
argument_list|(
name|AlertType
operator|.
name|INFORMATION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete '%0'"
argument_list|,
name|file
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delete the selected file permanently from disk, or just remove the file from the entry? Pressing Delete will delete the file permanently from disk."
argument_list|)
argument_list|,
name|deleteFromEntry
argument_list|,
name|removeFromEntry
argument_list|,
name|ButtonType
operator|.
name|CANCEL
argument_list|)
decl_stmt|;
if|if
condition|(
name|buttonType
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
name|buttonType
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|removeFromEntry
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|buttonType
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|deleteFromEntry
argument_list|)
condition|)
block|{
try|try
block|{
name|Files
operator|.
name|delete
argument_list|(
name|file
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cannot delete file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File permission error"
argument_list|)
argument_list|)
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"File permission error while deleting: "
operator|+
name|linkedFile
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
block|}
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not find file "
operator|+
name|linkedFile
operator|.
name|getLink
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|true
return|;
block|}
DECL|method|edit ()
specifier|public
name|void
name|edit
parameter_list|()
block|{
name|FileListEntryEditor
name|editor
init|=
operator|new
name|FileListEntryEditor
argument_list|(
name|linkedFile
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|databaseContext
argument_list|)
decl_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

