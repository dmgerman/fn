begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2016 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.openoffice
package|package
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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
name|javax
operator|.
name|swing
operator|.
name|*
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
name|awt
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
name|*
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
name|openoffice
operator|.
name|OpenOfficeFileSearch
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
name|openoffice
operator|.
name|OpenOfficePreferences
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
name|OS
import|;
end_import

begin_comment
comment|/**  * Tools for automatically detecting jar and executable paths to OpenOffice and/or LibreOffice.  */
end_comment

begin_class
DECL|class|AutoDetectPaths
specifier|public
class|class
name|AutoDetectPaths
extends|extends
name|AbstractWorker
block|{
DECL|field|preferences
specifier|private
specifier|final
name|OpenOfficePreferences
name|preferences
decl_stmt|;
DECL|field|foundPaths
specifier|private
name|boolean
name|foundPaths
decl_stmt|;
DECL|field|fileSearchCanceled
specifier|private
name|boolean
name|fileSearchCanceled
decl_stmt|;
DECL|field|prog
specifier|private
name|JDialog
name|prog
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|JDialog
name|parent
decl_stmt|;
DECL|field|fileSearch
specifier|private
specifier|final
name|OpenOfficeFileSearch
name|fileSearch
init|=
operator|new
name|OpenOfficeFileSearch
argument_list|()
decl_stmt|;
DECL|method|AutoDetectPaths (JDialog parent, OpenOfficePreferences preferences)
specifier|public
name|AutoDetectPaths
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
DECL|method|runAutodetection ()
specifier|public
name|boolean
name|runAutodetection
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
DECL|method|canceled ()
specifier|public
name|boolean
name|canceled
parameter_list|()
block|{
return|return
name|fileSearchCanceled
return|;
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|prog
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
argument_list|,
literal|true
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
name|prog
operator|.
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|autoDetectPaths ()
specifier|private
name|boolean
name|autoDetectPaths
parameter_list|()
block|{
name|fileSearch
operator|.
name|resetFileSearch
argument_list|()
expr_stmt|;
if|if
condition|(
name|OS
operator|.
name|WINDOWS
condition|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|progFiles
init|=
name|fileSearch
operator|.
name|findWindowsProgramFilesDir
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|sofficeFiles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|fileSearch
operator|.
name|findFileInDirs
argument_list|(
name|progFiles
argument_list|,
name|OpenOfficePreferences
operator|.
name|WINDOWS_EXECUTABLE
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCanceled
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|sofficeFiles
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
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
name|JFileChooser
name|fileChooser
init|=
operator|new
name|JFileChooser
argument_list|(
operator|new
name|File
argument_list|(
name|System
operator|.
name|getenv
argument_list|(
literal|"ProgramFiles"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
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
name|setFileFilter
argument_list|(
operator|new
name|javax
operator|.
name|swing
operator|.
name|filechooser
operator|.
name|FileFilter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|file
parameter_list|)
block|{
return|return
name|file
operator|.
name|isDirectory
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getDescription
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Directories"
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
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
name|showOpenDialog
argument_list|(
name|parent
argument_list|)
expr_stmt|;
if|if
condition|(
name|fileChooser
operator|.
name|getSelectedFile
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|sofficeFiles
operator|.
name|add
argument_list|(
name|fileChooser
operator|.
name|getSelectedFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
name|Optional
argument_list|<
name|File
argument_list|>
name|actualFile
init|=
name|checkAndSelectAmongMultipleInstalls
argument_list|(
name|sofficeFiles
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
name|setupPreferencesForOO
argument_list|(
name|actualFile
operator|.
name|get
argument_list|()
operator|.
name|getParentFile
argument_list|()
argument_list|,
name|actualFile
operator|.
name|get
argument_list|()
argument_list|,
name|OpenOfficePreferences
operator|.
name|WINDOWS_EXECUTABLE
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|List
argument_list|<
name|File
argument_list|>
name|dirList
init|=
name|fileSearch
operator|.
name|findOSXProgramFilesDir
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|sofficeFiles
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|fileSearch
operator|.
name|findFileInDirs
argument_list|(
name|dirList
argument_list|,
name|OpenOfficePreferences
operator|.
name|OSX_EXECUTABLE
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCanceled
condition|)
block|{
return|return
literal|false
return|;
block|}
name|Optional
argument_list|<
name|File
argument_list|>
name|actualFile
init|=
name|checkAndSelectAmongMultipleInstalls
argument_list|(
name|sofficeFiles
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
for|for
control|(
name|File
name|rootdir
range|:
name|dirList
control|)
block|{
if|if
condition|(
name|actualFile
operator|.
name|get
argument_list|()
operator|.
name|getPath
argument_list|()
operator|.
name|startsWith
argument_list|(
name|rootdir
operator|.
name|getPath
argument_list|()
argument_list|)
condition|)
block|{
return|return
name|setupPreferencesForOO
argument_list|(
name|rootdir
argument_list|,
name|actualFile
operator|.
name|get
argument_list|()
argument_list|,
name|OpenOfficePreferences
operator|.
name|OSX_EXECUTABLE
argument_list|)
return|;
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
else|else
block|{
comment|// Linux:
name|String
name|usrRoot
init|=
literal|"/usr/lib"
decl_stmt|;
name|Optional
argument_list|<
name|File
argument_list|>
name|inUsr
init|=
name|fileSearch
operator|.
name|findFileInDir
argument_list|(
operator|new
name|File
argument_list|(
name|usrRoot
argument_list|)
argument_list|,
name|OpenOfficePreferences
operator|.
name|LINUX_EXECUTABLE
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCanceled
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|inUsr
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|inUsr
operator|=
name|fileSearch
operator|.
name|findFileInDir
argument_list|(
operator|new
name|File
argument_list|(
literal|"/usr/lib64"
argument_list|)
argument_list|,
name|OpenOfficePreferences
operator|.
name|LINUX_EXECUTABLE
argument_list|)
expr_stmt|;
if|if
condition|(
name|inUsr
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|usrRoot
operator|=
literal|"/usr/lib64"
expr_stmt|;
block|}
block|}
if|if
condition|(
name|fileSearchCanceled
condition|)
block|{
return|return
literal|false
return|;
block|}
name|Optional
argument_list|<
name|File
argument_list|>
name|inOpt
init|=
name|fileSearch
operator|.
name|findFileInDir
argument_list|(
operator|new
name|File
argument_list|(
literal|"/opt"
argument_list|)
argument_list|,
name|OpenOfficePreferences
operator|.
name|LINUX_EXECUTABLE
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCanceled
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|(
name|inUsr
operator|.
name|isPresent
argument_list|()
operator|)
operator|&&
operator|(
operator|!
name|inOpt
operator|.
name|isPresent
argument_list|()
operator|)
condition|)
block|{
return|return
name|setupPreferencesForOO
argument_list|(
name|usrRoot
argument_list|,
name|inUsr
operator|.
name|get
argument_list|()
argument_list|,
name|OpenOfficePreferences
operator|.
name|LINUX_EXECUTABLE
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|inOpt
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|inUsr
operator|.
name|isPresent
argument_list|()
condition|)
block|{
return|return
name|setupPreferencesForOO
argument_list|(
literal|"/opt"
argument_list|,
name|inOpt
operator|.
name|get
argument_list|()
argument_list|,
name|OpenOfficePreferences
operator|.
name|LINUX_EXECUTABLE
argument_list|)
return|;
block|}
else|else
block|{
comment|// Found both
name|JRadioButton
name|optRB
init|=
operator|new
name|JRadioButton
argument_list|(
name|inOpt
operator|.
name|get
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|JRadioButton
name|usrRB
init|=
operator|new
name|JRadioButton
argument_list|(
name|inUsr
operator|.
name|get
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|optRB
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|usrRB
argument_list|)
expr_stmt|;
name|FormBuilder
name|b
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
literal|"left:pref"
argument_list|,
literal|"pref, 2dlu, pref, 2dlu, pref "
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Found more than one OpenOffice/LibreOffice executable."
argument_list|)
operator|+
literal|" "
operator|+
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
literal|1
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|optRB
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|add
argument_list|(
name|usrRB
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
name|b
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
literal|false
return|;
block|}
if|if
condition|(
name|optRB
operator|.
name|isSelected
argument_list|()
condition|)
block|{
return|return
name|setupPreferencesForOO
argument_list|(
literal|"/opt"
argument_list|,
name|inOpt
operator|.
name|get
argument_list|()
argument_list|,
name|OpenOfficePreferences
operator|.
name|LINUX_EXECUTABLE
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|setupPreferencesForOO
argument_list|(
name|usrRoot
argument_list|,
name|inUsr
operator|.
name|get
argument_list|()
argument_list|,
name|OpenOfficePreferences
operator|.
name|LINUX_EXECUTABLE
argument_list|)
return|;
block|}
block|}
block|}
block|}
return|return
literal|false
return|;
block|}
DECL|method|setupPreferencesForOO (String usrRoot, File inUsr, String sofficeName)
specifier|private
name|boolean
name|setupPreferencesForOO
parameter_list|(
name|String
name|usrRoot
parameter_list|,
name|File
name|inUsr
parameter_list|,
name|String
name|sofficeName
parameter_list|)
block|{
return|return
name|setupPreferencesForOO
argument_list|(
operator|new
name|File
argument_list|(
name|usrRoot
argument_list|)
argument_list|,
name|inUsr
argument_list|,
name|sofficeName
argument_list|)
return|;
block|}
DECL|method|setupPreferencesForOO (File rootDir, File inUsr, String sofficeName)
specifier|private
name|boolean
name|setupPreferencesForOO
parameter_list|(
name|File
name|rootDir
parameter_list|,
name|File
name|inUsr
parameter_list|,
name|String
name|sofficeName
parameter_list|)
block|{
name|preferences
operator|.
name|setExecutablePath
argument_list|(
operator|new
name|File
argument_list|(
name|inUsr
argument_list|,
name|sofficeName
argument_list|)
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|File
argument_list|>
name|jurt
init|=
name|fileSearch
operator|.
name|findFileInDir
argument_list|(
name|rootDir
argument_list|,
literal|"jurt.jar"
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCanceled
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|jurt
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|preferences
operator|.
name|setJarsPath
argument_list|(
name|jurt
operator|.
name|get
argument_list|()
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
DECL|method|checkAndSelectAmongMultipleInstalls (List<File> sofficeFiles)
specifier|private
name|Optional
argument_list|<
name|File
argument_list|>
name|checkAndSelectAmongMultipleInstalls
parameter_list|(
name|List
argument_list|<
name|File
argument_list|>
name|sofficeFiles
parameter_list|)
block|{
if|if
condition|(
name|sofficeFiles
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
elseif|else
if|if
condition|(
name|sofficeFiles
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
name|sofficeFiles
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
comment|// Otherwise more than one file found, select among them
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
name|File
name|tmpfile
range|:
name|sofficeFiles
control|)
block|{
name|mod
operator|.
name|addElement
argument_list|(
name|tmpfile
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
literal|"left:pref"
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
argument_list|)
return|;
block|}
block|}
DECL|method|showProgressDialog (JDialog progressParent, String title, String message, boolean includeCancelButton)
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
parameter_list|,
name|boolean
name|includeCancelButton
parameter_list|)
block|{
name|fileSearchCanceled
operator|=
literal|false
expr_stmt|;
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
if|if
condition|(
name|includeCancelButton
condition|)
block|{
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|event
lambda|->
block|{
name|fileSearchCanceled
operator|=
literal|true
expr_stmt|;
name|fileSearch
operator|.
name|cancelFileSearch
argument_list|()
expr_stmt|;
operator|(
operator|(
name|JButton
operator|)
name|event
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|progressDialog
operator|.
name|add
argument_list|(
name|cancel
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
block|}
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

