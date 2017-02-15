begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.importer
package|package
name|net
operator|.
name|sf
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
name|Objects
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
name|FileDialog
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
name|actions
operator|.
name|MnemonicAwareAction
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
name|keyboard
operator|.
name|KeyBinding
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
name|Importer
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
name|FileExtensions
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
DECL|class|ImportFormats
specifier|public
class|class
name|ImportFormats
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
name|ImportFormats
operator|.
name|class
argument_list|)
decl_stmt|;
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
name|JabRefFrame
name|frame
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|newDatabase
decl_stmt|;
specifier|public
name|ImportAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|newDatabase
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
literal|"Import into new database"
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
literal|"Import into current database"
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
name|FileExtensions
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
name|getExtensions
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
name|FileDialog
name|dialog
init|=
operator|new
name|FileDialog
argument_list|(
name|frame
argument_list|,
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
decl_stmt|;
comment|// Add file filter for all supported types
name|FileChooser
operator|.
name|ExtensionFilter
name|allImports
init|=
name|ImportFileFilter
operator|.
name|convert
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Available import formats"
argument_list|)
argument_list|,
name|importers
argument_list|)
decl_stmt|;
name|dialog
operator|.
name|setFileFilter
argument_list|(
name|allImports
argument_list|)
expr_stmt|;
comment|// Add filters for extensions
name|dialog
operator|.
name|withExtensions
argument_list|(
name|extensions
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|selectedFile
init|=
name|dialog
operator|.
name|showDialogAndGetSelectedFile
argument_list|()
decl_stmt|;
name|selectedFile
operator|.
name|ifPresent
argument_list|(
name|sel
lambda|->
block|{
try|try
block|{
name|File
name|file
init|=
name|sel
operator|.
name|toFile
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
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
name|getName
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
name|importers
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|i
lambda|->
name|Objects
operator|.
name|equals
argument_list|(
name|i
operator|.
name|getExtensions
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|,
name|dialog
operator|.
name|getFileFilter
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
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
name|getAbsolutePath
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
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Cannot import file"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
return|return
operator|new
name|ImportAction
argument_list|(
name|frame
argument_list|,
name|openInNew
argument_list|)
return|;
block|}
block|}
end_class

end_unit
