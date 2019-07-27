begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.copyfiles
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|copyfiles
package|;
end_package

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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|BiFunction
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
name|util
operator|.
name|DirectoryDialogConfiguration
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
name|LinkedFile
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
name|OptionalUtil
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

begin_class
DECL|class|CopySingleFileAction
specifier|public
class|class
name|CopySingleFileAction
block|{
DECL|field|linkedFile
specifier|private
name|LinkedFile
name|linkedFile
decl_stmt|;
DECL|field|dialogService
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|databaseContext
specifier|private
name|BibDatabaseContext
name|databaseContext
decl_stmt|;
DECL|field|resolvePathFilename
specifier|private
specifier|final
name|BiFunction
argument_list|<
name|Path
argument_list|,
name|Path
argument_list|,
name|Path
argument_list|>
name|resolvePathFilename
init|=
parameter_list|(
name|path
parameter_list|,
name|file
parameter_list|)
lambda|->
block|{
return|return
name|path
operator|.
name|resolve
argument_list|(
name|file
operator|.
name|getFileName
argument_list|()
argument_list|)
return|;
block|}
decl_stmt|;
DECL|method|CopySingleFileAction (LinkedFile linkedFile, DialogService dialogService, BibDatabaseContext databaseContext)
specifier|public
name|CopySingleFileAction
parameter_list|(
name|LinkedFile
name|linkedFile
parameter_list|,
name|DialogService
name|dialogService
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
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|databaseContext
operator|=
name|databaseContext
expr_stmt|;
block|}
DECL|method|copyFile ()
specifier|public
name|void
name|copyFile
parameter_list|()
block|{
name|DirectoryDialogConfiguration
name|dirDialogConfiguration
init|=
operator|new
name|DirectoryDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|Paths
operator|.
name|get
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
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|exportPath
init|=
name|dialogService
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|dirDialogConfiguration
argument_list|)
decl_stmt|;
name|exportPath
operator|.
name|ifPresent
argument_list|(
name|this
operator|::
name|copyFileToDestination
argument_list|)
expr_stmt|;
block|}
DECL|method|copyFileToDestination (Path exportPath)
specifier|private
name|void
name|copyFileToDestination
parameter_list|(
name|Path
name|exportPath
parameter_list|)
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|fileToExport
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
name|getFilePreferences
argument_list|()
argument_list|)
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|newPath
init|=
name|OptionalUtil
operator|.
name|combine
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|exportPath
argument_list|)
argument_list|,
name|fileToExport
argument_list|,
name|resolvePathFilename
argument_list|)
decl_stmt|;
if|if
condition|(
name|newPath
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|Path
name|newFile
init|=
name|newPath
operator|.
name|get
argument_list|()
decl_stmt|;
name|boolean
name|success
init|=
name|FileUtil
operator|.
name|copyFile
argument_list|(
name|fileToExport
operator|.
name|get
argument_list|()
argument_list|,
name|newFile
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|success
condition|)
block|{
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy linked file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Sucessfully copied file to %0"
argument_list|,
name|newPath
operator|.
name|map
argument_list|(
name|Path
operator|::
name|getParent
argument_list|)
operator|.
name|map
argument_list|(
name|Path
operator|::
name|toString
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
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
literal|"Copy linked file"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not copy file to %0, maybe the file is already existing?"
argument_list|,
name|newPath
operator|.
name|map
argument_list|(
name|Path
operator|::
name|getParent
argument_list|)
operator|.
name|map
argument_list|(
name|Path
operator|::
name|toString
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
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
literal|"Could not resolve the file %0"
argument_list|,
name|fileToExport
operator|.
name|map
argument_list|(
name|Path
operator|::
name|getParent
argument_list|)
operator|.
name|map
argument_list|(
name|Path
operator|::
name|toString
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

