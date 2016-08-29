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
name|Arrays
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
name|EnumSet
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
name|javax
operator|.
name|swing
operator|.
name|JFrame
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
name|filechooser
operator|.
name|FileFilter
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|filechooser
operator|.
name|FileNameExtensionFilter
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

begin_class
DECL|class|FileDialog
specifier|public
class|class
name|FileDialog
block|{
comment|/**      * Custom confirmation dialog      * http://stackoverflow.com/a/3729157      */
DECL|field|fileChooser
specifier|private
specifier|final
name|JFileChooser
name|fileChooser
init|=
operator|new
name|JFileChooser
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|approveSelection
parameter_list|()
block|{
name|File
name|file
init|=
name|getSelectedFile
argument_list|()
decl_stmt|;
if|if
condition|(
name|file
operator|.
name|exists
argument_list|()
operator|&&
operator|(
name|getDialogType
argument_list|()
operator|==
name|SAVE_DIALOG
operator|)
condition|)
block|{
name|int
name|result
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"'%0' exists. Overwrite file?"
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Existing file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|result
condition|)
block|{
case|case
name|JOptionPane
operator|.
name|YES_OPTION
case|:
name|super
operator|.
name|approveSelection
argument_list|()
expr_stmt|;
return|return;
case|case
name|JOptionPane
operator|.
name|NO_OPTION
case|:
return|return;
case|case
name|JOptionPane
operator|.
name|CLOSED_OPTION
case|:
return|return;
case|case
name|JOptionPane
operator|.
name|CANCEL_OPTION
case|:
name|cancelSelection
argument_list|()
expr_stmt|;
return|return;
default|default:
return|return;
block|}
block|}
name|super
operator|.
name|approveSelection
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|JFrame
name|parent
decl_stmt|;
DECL|field|directory
specifier|private
specifier|final
name|String
name|directory
decl_stmt|;
DECL|field|extensions
specifier|private
name|Collection
argument_list|<
name|FileExtensions
argument_list|>
name|extensions
init|=
name|EnumSet
operator|.
name|noneOf
argument_list|(
name|FileExtensions
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Creates a new filedialog showing the current working dir {@link JabRefPreferences#WORKING_DIRECTORY}      * @param parent The parent frame associated with this dialog      */
DECL|method|FileDialog (JFrame parent)
specifier|public
name|FileDialog
parameter_list|(
name|JFrame
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
DECL|method|FileDialog (JFrame parent, String dir)
specifier|public
name|FileDialog
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|String
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
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|directory
operator|=
name|dir
expr_stmt|;
name|fileChooser
operator|.
name|setCurrentDirectory
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|dir
argument_list|)
operator|.
name|toFile
argument_list|()
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
name|withExtensions
argument_list|(
name|EnumSet
operator|.
name|of
argument_list|(
name|singleExt
argument_list|)
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
name|this
operator|.
name|extensions
operator|=
name|fileExtensions
expr_stmt|;
for|for
control|(
name|FileExtensions
name|ext
range|:
name|fileExtensions
control|)
block|{
name|FileNameExtensionFilter
name|extFilter
init|=
operator|new
name|FileNameExtensionFilter
argument_list|(
name|ext
operator|.
name|getDescription
argument_list|()
argument_list|,
name|ext
operator|.
name|getExtensions
argument_list|()
argument_list|)
decl_stmt|;
name|fileChooser
operator|.
name|addChoosableFileFilter
argument_list|(
name|extFilter
argument_list|)
expr_stmt|;
block|}
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
name|Arrays
operator|.
name|stream
argument_list|(
name|fileChooser
operator|.
name|getChoosableFileFilters
argument_list|()
argument_list|)
operator|.
name|filter
argument_list|(
name|f
lambda|->
name|Objects
operator|.
name|equals
argument_list|(
name|f
operator|.
name|getDescription
argument_list|()
argument_list|,
name|extension
operator|.
name|getDescription
argument_list|()
argument_list|)
argument_list|)
operator|.
name|findFirst
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|fileChooser
operator|::
name|setFileFilter
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns the currently selected file filter.      *      * @return FileFilter      */
DECL|method|getFileFilter ()
specifier|public
name|FileFilter
name|getFileFilter
parameter_list|()
block|{
return|return
name|fileChooser
operator|.
name|getFileFilter
argument_list|()
return|;
block|}
comment|/**      * Sets a custom file filter.      * Only use when withExtension() does not suffice.      *      * @param filter the custom file filter      */
DECL|method|setFileFilter (FileFilter filter)
specifier|public
name|void
name|setFileFilter
parameter_list|(
name|FileFilter
name|filter
parameter_list|)
block|{
name|fileChooser
operator|.
name|setFileFilter
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
name|this
operator|.
name|directory
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
name|fileChooser
operator|.
name|setFileSelectionMode
argument_list|(
name|JFileChooser
operator|.
name|DIRECTORIES_ONLY
argument_list|)
expr_stmt|;
name|fileChooser
operator|.
name|setDialogTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select directory"
argument_list|)
argument_list|)
expr_stmt|;
name|fileChooser
operator|.
name|setApproveButtonText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select"
argument_list|)
argument_list|)
expr_stmt|;
name|fileChooser
operator|.
name|setApproveButtonToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Select directory"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|showDialogAndGetSelectedFile
argument_list|()
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
name|fileChooser
operator|.
name|setDialogType
argument_list|(
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|)
expr_stmt|;
name|fileChooser
operator|.
name|setMultiSelectionEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|showDialogAndIsAccepted
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|files
init|=
name|Arrays
operator|.
name|stream
argument_list|(
name|fileChooser
operator|.
name|getSelectedFiles
argument_list|()
argument_list|)
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
decl_stmt|;
return|return
name|files
return|;
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
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
name|fileChooser
operator|.
name|setDialogType
argument_list|(
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|)
expr_stmt|;
if|if
condition|(
name|showDialogAndIsAccepted
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fileChooser
operator|.
name|getSelectedFile
argument_list|()
operator|.
name|toPath
argument_list|()
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
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
name|fileChooser
operator|.
name|setDialogType
argument_list|(
name|JFileChooser
operator|.
name|SAVE_DIALOG
argument_list|)
expr_stmt|;
if|if
condition|(
name|showDialogAndIsAccepted
argument_list|()
condition|)
block|{
name|File
name|file
init|=
name|fileChooser
operator|.
name|getSelectedFile
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|extensions
operator|.
name|isEmpty
argument_list|()
operator|&&
operator|!
name|fileChooser
operator|.
name|accept
argument_list|(
name|file
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|file
operator|.
name|getPath
argument_list|()
operator|+
name|extensions
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
operator|.
name|getFirstExtensionWithDot
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|of
argument_list|(
name|file
operator|.
name|toPath
argument_list|()
argument_list|)
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|showDialogAndIsAccepted ()
specifier|private
name|boolean
name|showDialogAndIsAccepted
parameter_list|()
block|{
return|return
name|fileChooser
operator|.
name|showDialog
argument_list|(
name|parent
argument_list|,
literal|null
argument_list|)
operator|==
name|JFileChooser
operator|.
name|APPROVE_OPTION
return|;
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

