begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|openoffice
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
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
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultListModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
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
name|JProgressBar
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingConstants
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
name|desktop
operator|.
name|os
operator|.
name|NativeDesktop
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
name|DirectoryDialogConfiguration
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
name|worker
operator|.
name|AbstractWorker
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
name|openoffice
operator|.
name|OpenOfficeFileSearch
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
name|openoffice
operator|.
name|OpenOfficePreferences
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
name|OS
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_comment
comment|/**  * Tools for automatically detecting OpenOffice or LibreOffice installations.  */
end_comment

begin_class
DECL|class|DetectOpenOfficeInstallation
specifier|public
class|class
name|DetectOpenOfficeInstallation
extends|extends
name|AbstractWorker
block|{
DECL|field|preferences
specifier|private
specifier|final
name|OpenOfficePreferences
name|preferences
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|JDialog
name|parent
decl_stmt|;
DECL|field|foundPaths
specifier|private
name|boolean
name|foundPaths
decl_stmt|;
DECL|field|progressDialog
specifier|private
name|JDialog
name|progressDialog
decl_stmt|;
DECL|method|DetectOpenOfficeInstallation (JDialog parent, OpenOfficePreferences preferences)
specifier|public
name|DetectOpenOfficeInstallation
parameter_list|(
name|JDialog
name|parent
parameter_list|,
name|OpenOfficePreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
block|}
DECL|method|isInstalled ()
specifier|public
name|boolean
name|isInstalled
parameter_list|()
block|{
name|foundPaths
operator|=
literal|false
expr_stmt|;
if|if
condition|(
name|preferences
operator|.
name|checkAutoDetectedPaths
argument_list|()
condition|)
block|{
return|return
literal|true
return|;
block|}
name|init
argument_list|()
expr_stmt|;
name|getWorker
argument_list|()
operator|.
name|run
argument_list|()
expr_stmt|;
name|update
argument_list|()
expr_stmt|;
return|return
name|foundPaths
return|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|foundPaths
operator|=
name|autoDetectPaths
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|progressDialog
operator|=
name|showProgressDialog
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autodetecting paths..."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please wait..."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
name|progressDialog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|selectInstallationPath ()
specifier|private
name|Optional
argument_list|<
name|Path
argument_list|>
name|selectInstallationPath
parameter_list|()
block|{
specifier|final
name|NativeDesktop
name|nativeDesktop
init|=
name|JabRefDesktop
operator|.
name|getNativeDesktop
argument_list|()
decl_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to autodetect OpenOffice/LibreOffice installation. Please choose the installation directory manually."
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not find OpenOffice/LibreOffice installation"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
name|DialogService
name|ds
init|=
operator|new
name|FXDialogService
argument_list|()
decl_stmt|;
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
name|nativeDesktop
operator|.
name|getApplicationDirectory
argument_list|()
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|Optional
argument_list|<
name|Path
argument_list|>
name|path
init|=
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|ds
operator|.
name|showDirectorySelectionDialog
argument_list|(
name|dirDialogConfiguration
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|path
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|path
return|;
block|}
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
DECL|method|autoDetectPaths ()
specifier|private
name|boolean
name|autoDetectPaths
parameter_list|()
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|installations
init|=
name|OpenOfficeFileSearch
operator|.
name|detectInstallations
argument_list|()
decl_stmt|;
comment|// manually add installation path
if|if
condition|(
name|installations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|selectInstallationPath
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|installations
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
comment|// select among multiple installations
name|Optional
argument_list|<
name|Path
argument_list|>
name|actualFile
init|=
name|chooseAmongInstallations
argument_list|(
name|installations
argument_list|)
decl_stmt|;
if|if
condition|(
name|actualFile
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|setOpenOfficePreferences
argument_list|(
name|actualFile
operator|.
name|get
argument_list|()
argument_list|)
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|setOpenOfficePreferences (Path installDir)
specifier|private
name|boolean
name|setOpenOfficePreferences
parameter_list|(
name|Path
name|installDir
parameter_list|)
block|{
name|Optional
argument_list|<
name|Path
argument_list|>
name|execPath
init|=
name|Optional
operator|.
name|empty
argument_list|()
decl_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|execPath
operator|=
name|FileUtil
operator|.
name|find
argument_list|(
name|OpenOfficePreferences
operator|.
name|WINDOWS_EXECUTABLE
argument_list|,
name|installDir
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|execPath
operator|=
name|FileUtil
operator|.
name|find
argument_list|(
name|OpenOfficePreferences
operator|.
name|OSX_EXECUTABLE
argument_list|,
name|installDir
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|LINUX
condition|)
block|{
name|execPath
operator|=
name|FileUtil
operator|.
name|find
argument_list|(
name|OpenOfficePreferences
operator|.
name|LINUX_EXECUTABLE
argument_list|,
name|installDir
argument_list|)
expr_stmt|;
block|}
name|Optional
argument_list|<
name|Path
argument_list|>
name|jarFilePath
init|=
name|FileUtil
operator|.
name|find
argument_list|(
name|OpenOfficePreferences
operator|.
name|OO_JARS
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|installDir
argument_list|)
decl_stmt|;
if|if
condition|(
name|execPath
operator|.
name|isPresent
argument_list|()
operator|&&
name|jarFilePath
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|preferences
operator|.
name|setOOPath
argument_list|(
name|installDir
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|setExecutablePath
argument_list|(
name|execPath
operator|.
name|get
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|setJarsPath
argument_list|(
name|jarFilePath
operator|.
name|get
argument_list|()
operator|.
name|getParent
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
return|return
literal|false
return|;
block|}
DECL|method|chooseAmongInstallations (List<Path> installDirs)
specifier|private
name|Optional
argument_list|<
name|Path
argument_list|>
name|chooseAmongInstallations
parameter_list|(
name|List
argument_list|<
name|Path
argument_list|>
name|installDirs
parameter_list|)
block|{
if|if
condition|(
name|installDirs
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
if|if
condition|(
name|installDirs
operator|.
name|size
argument_list|()
operator|==
literal|1
condition|)
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|installDirs
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|toAbsolutePath
argument_list|()
argument_list|)
return|;
block|}
comment|// Otherwise more than one installation was found, select among them
name|DefaultListModel
argument_list|<
name|File
argument_list|>
name|mod
init|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|Path
name|tmpfile
range|:
name|installDirs
control|)
block|{
name|mod
operator|.
name|addElement
argument_list|(
name|tmpfile
operator|.
name|toFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|JList
argument_list|<
name|File
argument_list|>
name|fileList
init|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|mod
argument_list|)
decl_stmt|;
name|fileList
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|SINGLE_SELECTION
argument_list|)
expr_stmt|;
name|fileList
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|FormBuilder
name|builder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"pref:grow"
argument_list|,
literal|"pref, 2dlu, pref, 4dlu, pref"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Found more than one OpenOffice/LibreOffice executable."
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please choose which one to connect to:"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|fileList
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
literal|null
argument_list|,
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Choose OpenOffice/LibreOffice executable"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|CANCEL_OPTION
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
else|else
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|fileList
operator|.
name|getSelectedValue
argument_list|()
operator|.
name|toPath
argument_list|()
argument_list|)
return|;
block|}
block|}
DECL|method|showProgressDialog (JDialog progressParent, String title, String message)
specifier|public
name|JDialog
name|showProgressDialog
parameter_list|(
name|JDialog
name|progressParent
parameter_list|,
name|String
name|title
parameter_list|,
name|String
name|message
parameter_list|)
block|{
name|JProgressBar
name|bar
init|=
operator|new
name|JProgressBar
argument_list|(
name|SwingConstants
operator|.
name|HORIZONTAL
argument_list|)
decl_stmt|;
specifier|final
name|JDialog
name|progressDialog
init|=
operator|new
name|JDialog
argument_list|(
name|progressParent
argument_list|,
name|title
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|bar
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|10
argument_list|,
literal|10
argument_list|,
literal|10
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|bar
operator|.
name|setIndeterminate
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|progressDialog
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|message
argument_list|)
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|progressDialog
operator|.
name|add
argument_list|(
name|bar
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|progressDialog
operator|.
name|pack
argument_list|()
expr_stmt|;
name|progressDialog
operator|.
name|setLocationRelativeTo
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|progressDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
return|return
name|progressDialog
return|;
block|}
block|}
end_class

end_unit

