begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.openoffice
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

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
name|AutoDetectPaths
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|foundPaths
specifier|private
name|boolean
name|foundPaths
decl_stmt|;
DECL|field|fileSearchCancelled
specifier|private
name|boolean
name|fileSearchCancelled
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
DECL|method|AutoDetectPaths (JDialog parent)
specifier|public
name|AutoDetectPaths
parameter_list|(
name|JDialog
name|parent
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
block|}
DECL|method|runAutodetection ()
specifier|public
name|boolean
name|runAutodetection
parameter_list|()
block|{
try|try
block|{
if|if
condition|(
name|AutoDetectPaths
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
catch|catch
parameter_list|(
name|Throwable
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Problem when auto-detecting paths"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
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
DECL|method|cancelled ()
specifier|public
name|boolean
name|cancelled
parameter_list|()
block|{
return|return
name|fileSearchCancelled
return|;
block|}
annotation|@
name|Override
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
throws|throws
name|Throwable
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
name|findProgramFilesDir
argument_list|()
decl_stmt|;
name|File
name|sOffice
init|=
literal|null
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
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|dir
range|:
name|progFiles
control|)
block|{
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
return|return
literal|false
return|;
block|}
name|sOffice
operator|=
name|findFileDir
argument_list|(
name|dir
argument_list|,
literal|"soffice.exe"
argument_list|)
expr_stmt|;
if|if
condition|(
name|sOffice
operator|!=
literal|null
condition|)
block|{
name|sofficeFiles
operator|.
name|add
argument_list|(
name|sOffice
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sOffice
operator|==
literal|null
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
name|jfc
init|=
operator|new
name|JFileChooser
argument_list|(
operator|new
name|File
argument_list|(
literal|"C:\\"
argument_list|)
argument_list|)
decl_stmt|;
name|jfc
operator|.
name|setDialogType
argument_list|(
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|)
expr_stmt|;
name|jfc
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
name|jfc
operator|.
name|setFileSelectionMode
argument_list|(
name|JFileChooser
operator|.
name|DIRECTORIES_ONLY
argument_list|)
expr_stmt|;
name|jfc
operator|.
name|showOpenDialog
argument_list|(
name|parent
argument_list|)
expr_stmt|;
if|if
condition|(
name|jfc
operator|.
name|getSelectedFile
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|sOffice
operator|=
name|jfc
operator|.
name|getSelectedFile
argument_list|()
expr_stmt|;
block|}
block|}
if|if
condition|(
name|sOffice
operator|==
literal|null
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
name|size
argument_list|()
operator|>
literal|1
condition|)
block|{
comment|// More than one file found
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
literal|"pref, 2dlu, pref, 4dlu, pref"
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
name|b
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
else|else
block|{
name|sOffice
operator|=
name|fileList
operator|.
name|getSelectedValue
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
name|sOffice
operator|=
name|sofficeFiles
operator|.
name|get
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
return|return
name|setupPreferencesForOO
argument_list|(
name|sOffice
operator|.
name|getParentFile
argument_list|()
argument_list|,
name|sOffice
argument_list|,
literal|"soffice.exe"
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|File
name|rootDir
init|=
operator|new
name|File
argument_list|(
literal|"/Applications"
argument_list|)
decl_stmt|;
name|File
index|[]
name|files
init|=
name|rootDir
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|files
operator|!=
literal|null
condition|)
block|{
for|for
control|(
name|File
name|file
range|:
name|files
control|)
block|{
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
operator|&&
operator|(
literal|"OpenOffice.org.app"
operator|.
name|equals
argument_list|(
name|file
operator|.
name|getName
argument_list|()
argument_list|)
operator|||
literal|"LibreOffice.app"
operator|.
name|equals
argument_list|(
name|file
operator|.
name|getName
argument_list|()
argument_list|)
operator|)
condition|)
block|{
name|rootDir
operator|=
name|file
expr_stmt|;
break|break;
block|}
block|}
block|}
name|File
name|sOffice
init|=
name|findFileDir
argument_list|(
name|rootDir
argument_list|,
literal|"soffice.bin"
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|sOffice
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
else|else
block|{
return|return
name|setupPreferencesForOO
argument_list|(
name|rootDir
argument_list|,
name|sOffice
argument_list|,
literal|"soffice.bin"
argument_list|)
return|;
block|}
block|}
else|else
block|{
comment|// Linux:
name|String
name|usrRoot
init|=
literal|"/usr/lib"
decl_stmt|;
name|File
name|inUsr
init|=
name|findFileDir
argument_list|(
operator|new
name|File
argument_list|(
name|usrRoot
argument_list|)
argument_list|,
literal|"soffice"
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|inUsr
operator|==
literal|null
condition|)
block|{
name|inUsr
operator|=
name|findFileDir
argument_list|(
operator|new
name|File
argument_list|(
literal|"/usr/lib64"
argument_list|)
argument_list|,
literal|"soffice"
argument_list|)
expr_stmt|;
if|if
condition|(
name|inUsr
operator|!=
literal|null
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
name|fileSearchCancelled
condition|)
block|{
return|return
literal|false
return|;
block|}
name|File
name|inOpt
init|=
name|findFileDir
argument_list|(
operator|new
name|File
argument_list|(
literal|"/opt"
argument_list|)
argument_list|,
literal|"soffice"
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCancelled
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
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|inOpt
operator|==
literal|null
operator|)
condition|)
block|{
return|return
name|setupPreferencesForOO
argument_list|(
name|usrRoot
argument_list|,
name|inUsr
argument_list|,
literal|"soffice.bin"
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|inOpt
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|inUsr
operator|==
literal|null
condition|)
block|{
return|return
name|setupPreferencesForOO
argument_list|(
literal|"/opt"
argument_list|,
name|inOpt
argument_list|,
literal|"soffice.bin"
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
literal|"Found more than one OpenOffice/LibreOffice executable. Please choose which one to connect to:"
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
argument_list|,
literal|"soffice.bin"
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
argument_list|,
literal|"soffice.bin"
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
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|OO_EXECUTABLE_PATH
argument_list|,
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
name|File
name|jurt
init|=
name|findFileDir
argument_list|(
name|rootDir
argument_list|,
literal|"jurt.jar"
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
name|jurt
operator|==
literal|null
condition|)
block|{
return|return
literal|false
return|;
block|}
else|else
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|OO_JARS_PATH
argument_list|,
name|jurt
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
return|return
literal|true
return|;
block|}
block|}
comment|/**      * Search for Program files directory.      * @return the File pointing to the Program files directory, or null if not found.      *   Since we are not including a library for Windows integration, this method can't      *   find the Program files dir in localized Windows installations.      */
DECL|method|findProgramFilesDir ()
specifier|private
specifier|static
name|List
argument_list|<
name|File
argument_list|>
name|findProgramFilesDir
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|sourceList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|File
argument_list|>
name|dirList
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
comment|// 64-bits first
name|String
name|progFiles
init|=
name|System
operator|.
name|getenv
argument_list|(
literal|"ProgramFiles"
argument_list|)
decl_stmt|;
if|if
condition|(
name|progFiles
operator|!=
literal|null
condition|)
block|{
name|sourceList
operator|.
name|add
argument_list|(
name|progFiles
argument_list|)
expr_stmt|;
block|}
comment|// Then 32-bits
name|progFiles
operator|=
name|System
operator|.
name|getenv
argument_list|(
literal|"ProgramFiles(x86)"
argument_list|)
expr_stmt|;
if|if
condition|(
name|progFiles
operator|!=
literal|null
condition|)
block|{
name|sourceList
operator|.
name|add
argument_list|(
name|progFiles
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|rootPath
range|:
name|sourceList
control|)
block|{
name|File
name|root
init|=
operator|new
name|File
argument_list|(
name|rootPath
argument_list|)
decl_stmt|;
name|File
index|[]
name|dirs
init|=
name|root
operator|.
name|listFiles
argument_list|(
name|File
operator|::
name|isDirectory
argument_list|)
decl_stmt|;
if|if
condition|(
name|dirs
operator|!=
literal|null
condition|)
block|{
name|Collections
operator|.
name|addAll
argument_list|(
name|dirList
argument_list|,
name|dirs
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|dirList
return|;
block|}
DECL|method|checkAutoDetectedPaths ()
specifier|private
specifier|static
name|boolean
name|checkAutoDetectedPaths
parameter_list|()
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
name|JabRefPreferences
operator|.
name|OO_JARS_PATH
argument_list|)
operator|&&
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
name|JabRefPreferences
operator|.
name|OO_EXECUTABLE_PATH
argument_list|)
condition|)
block|{
return|return
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|OO_JARS_PATH
argument_list|)
argument_list|,
literal|"jurt.jar"
argument_list|)
operator|.
name|exists
argument_list|()
operator|&&
operator|new
name|File
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|OO_EXECUTABLE_PATH
argument_list|)
argument_list|)
operator|.
name|exists
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|false
return|;
block|}
block|}
comment|/**      * Search for a file, starting at the given directory.      * @param startDir The starting point.      * @param filename The name of the file to search for.      * @return The directory where the file was first found, or null if not found.      */
DECL|method|findFileDir (File startDir, String filename)
specifier|private
name|File
name|findFileDir
parameter_list|(
name|File
name|startDir
parameter_list|,
name|String
name|filename
parameter_list|)
block|{
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
return|return
literal|null
return|;
block|}
name|File
index|[]
name|files
init|=
name|startDir
operator|.
name|listFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|files
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
name|File
name|result
init|=
literal|null
decl_stmt|;
for|for
control|(
name|File
name|file
range|:
name|files
control|)
block|{
if|if
condition|(
name|fileSearchCancelled
condition|)
block|{
return|return
literal|null
return|;
block|}
if|if
condition|(
name|file
operator|.
name|isDirectory
argument_list|()
condition|)
block|{
name|result
operator|=
name|findFileDir
argument_list|(
name|file
argument_list|,
name|filename
argument_list|)
expr_stmt|;
if|if
condition|(
name|result
operator|!=
literal|null
condition|)
block|{
break|break;
block|}
block|}
elseif|else
if|if
condition|(
name|file
operator|.
name|getName
argument_list|()
operator|.
name|equals
argument_list|(
name|filename
argument_list|)
condition|)
block|{
name|result
operator|=
name|startDir
expr_stmt|;
break|break;
block|}
block|}
return|return
name|result
return|;
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
name|fileSearchCancelled
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
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|fileSearchCancelled
operator|=
literal|true
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
block|}
argument_list|)
expr_stmt|;
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
comment|//parent);
comment|//SwingUtilities.invokeLater(new Runnable() {
comment|//    public void run() {
name|progressDialog
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
comment|//    }
comment|//});
return|return
name|progressDialog
return|;
block|}
block|}
end_class

end_unit

