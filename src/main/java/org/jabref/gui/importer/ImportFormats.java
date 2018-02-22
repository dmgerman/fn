begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
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
name|java
operator|.
name|util
operator|.
name|SortedSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|FileChooser
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
name|JabRefFrame
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
name|actions
operator|.
name|MnemonicAwareAction
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
name|keyboard
operator|.
name|KeyBinding
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
name|DefaultTaskExecutor
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
name|FileDialogConfiguration
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
name|FileFilterConverter
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
name|Importer
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
name|FileType
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
DECL|class|ImportFormats
specifier|public
class|class
name|ImportFormats
block|{
DECL|method|ImportFormats ()
specifier|private
name|ImportFormats
parameter_list|()
block|{     }
comment|/**      * Create an AbstractAction for performing an Import operation.      * @param frame The JabRefFrame of this JabRef instance.      * @param openInNew Indicate whether the action should open into a new database or      *  into the currently open one.      * @return The action.      */
DECL|method|getImportAction (JabRefFrame frame, boolean openInNew)
specifier|public
specifier|static
name|AbstractAction
name|getImportAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|openInNew
parameter_list|)
block|{
class|class
name|ImportAction
extends|extends
name|MnemonicAwareAction
block|{
specifier|private
specifier|final
name|boolean
name|newDatabase
decl_stmt|;
specifier|public
name|ImportAction
parameter_list|(
name|boolean
name|newDatabase
parameter_list|)
block|{
name|this
operator|.
name|newDatabase
operator|=
name|newDatabase
expr_stmt|;
if|if
condition|(
name|newDatabase
condition|)
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Import into new library"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|IMPORT_INTO_NEW_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Import into current library"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|IMPORT_INTO_CURRENT_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
name|SortedSet
argument_list|<
name|Importer
argument_list|>
name|importers
init|=
name|Globals
operator|.
name|IMPORT_FORMAT_READER
operator|.
name|getImportFormats
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|FileType
argument_list|>
name|extensions
init|=
name|importers
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Importer
operator|::
name|getFileType
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|FileChooser
operator|.
name|ExtensionFilter
name|allImports
init|=
name|FileFilterConverter
operator|.
name|forAllImporters
argument_list|(
name|importers
argument_list|)
decl_stmt|;
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|addExtensionFilter
argument_list|(
name|allImports
argument_list|)
operator|.
name|addExtensionFilters
argument_list|(
name|extensions
argument_list|)
operator|.
name|withInitialDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|fileDialogConfiguration
operator|.
name|getExtensionFilters
argument_list|()
operator|.
name|add
argument_list|(
operator|new
name|FileChooser
operator|.
name|ExtensionFilter
argument_list|(
literal|"Any file"
argument_list|,
literal|"*.*"
argument_list|)
argument_list|)
expr_stmt|;
name|DialogService
name|dialogService
init|=
operator|new
name|FXDialogService
argument_list|()
decl_stmt|;
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|dialogService
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|path
lambda|->
name|doImport
argument_list|(
name|path
argument_list|,
name|importers
argument_list|,
name|fileDialogConfiguration
operator|.
name|getSelectedExtensionFilter
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|doImport
parameter_list|(
name|Path
name|file
parameter_list|,
name|SortedSet
argument_list|<
name|Importer
argument_list|>
name|importers
parameter_list|,
name|FileChooser
operator|.
name|ExtensionFilter
name|selectedExtensionFilter
parameter_list|)
block|{
if|if
condition|(
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|file
argument_list|)
condition|)
block|{
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
literal|"File not found"
argument_list|)
operator|+
literal|": '"
operator|+
name|file
operator|.
name|getFileName
argument_list|()
operator|+
literal|"'."
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|Optional
argument_list|<
name|Importer
argument_list|>
name|format
init|=
name|FileFilterConverter
operator|.
name|getImporter
argument_list|(
name|selectedExtensionFilter
argument_list|,
name|importers
argument_list|)
decl_stmt|;
name|ImportMenuItem
name|importMenu
init|=
operator|new
name|ImportMenuItem
argument_list|(
name|frame
argument_list|,
name|newDatabase
argument_list|,
name|format
operator|.
name|orElse
argument_list|(
literal|null
argument_list|)
argument_list|)
decl_stmt|;
name|importMenu
operator|.
name|automatedImport
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|file
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Set last working dir for import
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_WORKING_DIRECTORY
argument_list|,
name|file
operator|.
name|getParent
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|ImportAction
argument_list|(
name|openInNew
argument_list|)
return|;
block|}
block|}
end_class

end_unit

