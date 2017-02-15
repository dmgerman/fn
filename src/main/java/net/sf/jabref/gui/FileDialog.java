begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
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
name|Collection
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
name|concurrent
operator|.
name|Callable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|FutureTask
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
name|JFileChooser
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|application
operator|.
name|Platform
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|DirectoryChooser
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
name|util
operator|.
name|FileDialogConfiguration
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

begin_comment
comment|/**  * @deprecated use {@link DialogService} instead.  */
end_comment

begin_class
annotation|@
name|Deprecated
DECL|class|FileDialog
specifier|public
class|class
name|FileDialog
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
name|FileDialog
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|fileChooser
specifier|private
specifier|final
name|FileChooser
name|fileChooser
decl_stmt|;
DECL|field|configurationBuilder
specifier|private
name|FileDialogConfiguration
operator|.
name|Builder
name|configurationBuilder
decl_stmt|;
comment|/**      * Creates a new filedialog showing the current working dir {@link JabRefPreferences#WORKING_DIRECTORY}      * @param parent The parent frame associated with this dialog      */
DECL|method|FileDialog (Component parent)
specifier|public
name|FileDialog
parameter_list|(
name|Component
name|parent
parameter_list|)
block|{
name|this
argument_list|(
name|parent
argument_list|,
name|getWorkingDir
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new dialog in the given directory      * @param parent The parent frame associated with this dialog      * @param dir The starting directory to show in the dialog      */
DECL|method|FileDialog (Component parent, String dir)
specifier|public
name|FileDialog
parameter_list|(
name|Component
name|parent
parameter_list|,
name|String
name|dir
parameter_list|)
block|{
name|this
argument_list|(
name|parent
argument_list|,
name|Paths
operator|.
name|get
argument_list|(
name|dir
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|FileDialog (Component parent, Path dir)
specifier|public
name|FileDialog
parameter_list|(
name|Component
name|parent
parameter_list|,
name|Path
name|dir
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|dir
argument_list|,
literal|"Directory must not be null"
argument_list|)
expr_stmt|;
comment|//Dir must be a folder, not a file
if|if
condition|(
operator|!
name|Files
operator|.
name|isDirectory
argument_list|(
name|dir
argument_list|)
condition|)
block|{
name|dir
operator|=
name|dir
operator|.
name|getParent
argument_list|()
expr_stmt|;
block|}
comment|//The lines above work also if the dir does not exist at all!
comment|//NULL is accepted by the filechooser as no inital path
if|if
condition|(
operator|!
name|Files
operator|.
name|exists
argument_list|(
name|dir
argument_list|)
condition|)
block|{
name|dir
operator|=
literal|null
expr_stmt|;
block|}
name|fileChooser
operator|=
operator|new
name|FileChooser
argument_list|()
expr_stmt|;
name|configurationBuilder
operator|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
expr_stmt|;
name|configurationBuilder
operator|=
name|configurationBuilder
operator|.
name|withInitialDirectory
argument_list|(
name|dir
argument_list|)
expr_stmt|;
block|}
comment|/**      * Add a single extension as file filter      * @param singleExt The extension      * @return FileDialog      */
DECL|method|withExtension (FileExtensions singleExt)
specifier|public
name|FileDialog
name|withExtension
parameter_list|(
name|FileExtensions
name|singleExt
parameter_list|)
block|{
name|configurationBuilder
operator|=
name|configurationBuilder
operator|.
name|addExtensionFilter
argument_list|(
name|singleExt
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
comment|/**      * Add a multiple extensions as file filter      * @param fileExtensions The extensions      * @return FileDialog      */
DECL|method|withExtensions (Collection<FileExtensions> fileExtensions)
specifier|public
name|FileDialog
name|withExtensions
parameter_list|(
name|Collection
argument_list|<
name|FileExtensions
argument_list|>
name|fileExtensions
parameter_list|)
block|{
name|configurationBuilder
operator|=
name|configurationBuilder
operator|.
name|addExtensionFilters
argument_list|(
name|fileExtensions
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
comment|/**      * Sets the default file filter extension for the file dialog.      * If the desired extension is not found nothing is changed.      *      * @param extension the file extension      */
DECL|method|setDefaultExtension (FileExtensions extension)
specifier|public
name|void
name|setDefaultExtension
parameter_list|(
name|FileExtensions
name|extension
parameter_list|)
block|{
name|configurationBuilder
operator|=
name|configurationBuilder
operator|.
name|withDefaultExtension
argument_list|(
name|extension
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the currently selected file filter.      *      * @return FileFilter      */
DECL|method|getFileFilter ()
specifier|public
name|FileChooser
operator|.
name|ExtensionFilter
name|getFileFilter
parameter_list|()
block|{
return|return
name|fileChooser
operator|.
name|getSelectedExtensionFilter
argument_list|()
return|;
block|}
comment|/**      * Sets a custom file filter.      * Only use when withExtension() does not suffice.      *      * @param filter the custom file filter      */
DECL|method|setFileFilter (FileChooser.ExtensionFilter filter)
specifier|public
name|void
name|setFileFilter
parameter_list|(
name|FileChooser
operator|.
name|ExtensionFilter
name|filter
parameter_list|)
block|{
name|fileChooser
operator|.
name|getExtensionFilters
argument_list|()
operator|.
name|add
argument_list|(
name|filter
argument_list|)
expr_stmt|;
name|fileChooser
operator|.
name|setSelectedExtensionFilter
argument_list|(
name|filter
argument_list|)
expr_stmt|;
block|}
comment|/**      * Updates the working directory preference      * @return FileDialog      */
DECL|method|updateWorkingDirPref ()
specifier|public
name|FileDialog
name|updateWorkingDirPref
parameter_list|()
block|{
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
name|fileChooser
operator|.
name|getInitialDirectory
argument_list|()
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|this
return|;
block|}
comment|/**      * Shows an {@link JFileChooser#OPEN_DIALOG} and allows to select a single folder      * @return The path of the selected folder or {@link Optional#empty()} if dialog is aborted      */
DECL|method|showDialogAndGetSelectedDirectory ()
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|showDialogAndGetSelectedDirectory
parameter_list|()
block|{
name|FileDialogConfiguration
name|configuration
init|=
name|configurationBuilder
operator|.
name|build
argument_list|()
decl_stmt|;
name|DirectoryChooser
name|directoryChooser
init|=
operator|new
name|DirectoryChooser
argument_list|()
decl_stmt|;
name|directoryChooser
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select directory"
argument_list|)
argument_list|)
expr_stmt|;
name|configuration
operator|.
name|getInitialDirectory
argument_list|()
operator|.
name|map
argument_list|(
name|Path
operator|::
name|toFile
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|directoryChooser
operator|::
name|setInitialDirectory
argument_list|)
expr_stmt|;
return|return
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|Optional
operator|.
name|ofNullable
argument_list|(
name|directoryChooser
operator|.
name|showDialog
argument_list|(
literal|null
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|File
operator|::
name|toPath
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Shows an {@link JFileChooser#OPEN_DIALOG} and allows to select multiple files      * @return List containing the paths of all files or an empty list if dialog is canceled      */
DECL|method|showDialogAndGetMultipleFiles ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|showDialogAndGetMultipleFiles
parameter_list|()
block|{
name|configureFileChooser
argument_list|()
expr_stmt|;
return|return
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
block|{
name|List
argument_list|<
name|File
argument_list|>
name|files
init|=
name|fileChooser
operator|.
name|showOpenMultipleDialog
argument_list|(
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|files
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
else|else
block|{
return|return
name|files
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|File
operator|::
name|toString
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
block|}
argument_list|)
return|;
block|}
DECL|method|configureFileChooser ()
specifier|private
name|void
name|configureFileChooser
parameter_list|()
block|{
name|FileDialogConfiguration
name|configuration
init|=
name|configurationBuilder
operator|.
name|build
argument_list|()
decl_stmt|;
name|fileChooser
operator|.
name|getExtensionFilters
argument_list|()
operator|.
name|addAll
argument_list|(
name|configuration
operator|.
name|getExtensionFilters
argument_list|()
argument_list|)
expr_stmt|;
name|fileChooser
operator|.
name|setSelectedExtensionFilter
argument_list|(
name|configuration
operator|.
name|getDefaultExtension
argument_list|()
argument_list|)
expr_stmt|;
name|configuration
operator|.
name|getInitialDirectory
argument_list|()
operator|.
name|map
argument_list|(
name|Path
operator|::
name|toFile
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|fileChooser
operator|::
name|setInitialDirectory
argument_list|)
expr_stmt|;
block|}
comment|/**      * Shows an {@link JFileChooser#OPEN_DIALOG} and allows to select a single file/folder      * @return The path of the selected file/folder or {@link Optional#empty()} if dialog is aborted      */
DECL|method|showDialogAndGetSelectedFile ()
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|showDialogAndGetSelectedFile
parameter_list|()
block|{
name|configureFileChooser
argument_list|()
expr_stmt|;
return|return
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|Optional
operator|.
name|ofNullable
argument_list|(
name|fileChooser
operator|.
name|showOpenDialog
argument_list|(
literal|null
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|File
operator|::
name|toPath
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * Shows an {@link JFileChooser#SAVE_DIALOG} and allows to save a new file<br>      * If an extension is provided, adds the extension to the file<br>      * Selecting an existing file will show an overwrite dialog      * @return The path of the new file, or {@link Optional#empty()} if dialog is aborted      */
DECL|method|saveNewFile ()
specifier|public
name|Optional
argument_list|<
name|Path
argument_list|>
name|saveNewFile
parameter_list|()
block|{
name|configureFileChooser
argument_list|()
expr_stmt|;
return|return
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|Optional
operator|.
name|ofNullable
argument_list|(
name|fileChooser
operator|.
name|showSaveDialog
argument_list|(
literal|null
argument_list|)
argument_list|)
operator|.
name|map
argument_list|(
name|File
operator|::
name|toPath
argument_list|)
argument_list|)
return|;
block|}
comment|/**      * We need to be careful and run everything in the javafx thread      * TODO: Remove this work-around as soon as everything uses the javafx thread      */
DECL|method|runInJavaFXThread (Callable<V> callable)
specifier|private
parameter_list|<
name|V
parameter_list|>
name|V
name|runInJavaFXThread
parameter_list|(
name|Callable
argument_list|<
name|V
argument_list|>
name|callable
parameter_list|)
block|{
name|FutureTask
argument_list|<
name|V
argument_list|>
name|task
init|=
operator|new
name|FutureTask
argument_list|<>
argument_list|(
name|callable
argument_list|)
decl_stmt|;
name|Platform
operator|.
name|runLater
argument_list|(
name|task
argument_list|)
expr_stmt|;
try|try
block|{
return|return
name|task
operator|.
name|get
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
decl||
name|ExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
name|e
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
DECL|method|getWorkingDir ()
specifier|private
specifier|static
name|String
name|getWorkingDir
parameter_list|()
block|{
return|return
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
return|;
block|}
block|}
end_class

end_unit
