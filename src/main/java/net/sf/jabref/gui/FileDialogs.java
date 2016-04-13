begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

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
name|javax
operator|.
name|swing
operator|.
name|JComponent
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
name|logic
operator|.
name|util
operator|.
name|OS
import|;
end_import

begin_class
DECL|class|FileDialogs
specifier|public
class|class
name|FileDialogs
block|{
comment|/**      * Will return the names of multiple files selected in the given directory      * and the given extensions.      *      * Will return an empty String array if no entry is found.      *      * @param owner      * @param directory      * @param extensions      * @param updateWorkingdirectory      * @return an array of selected file paths, or an empty array if no selection is made.      */
DECL|method|getMultipleFiles (JFrame owner, File directory, List<String> extensions, boolean updateWorkingdirectory)
specifier|public
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|getMultipleFiles
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|boolean
name|updateWorkingdirectory
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|extensions
argument_list|)
expr_stmt|;
name|OpenFileFilter
name|off
decl_stmt|;
if|if
condition|(
name|extensions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|off
operator|=
operator|new
name|OpenFileFilter
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|off
operator|=
operator|new
name|OpenFileFilter
argument_list|(
name|extensions
argument_list|)
expr_stmt|;
block|}
name|Object
name|files
init|=
name|FileDialogs
operator|.
name|getNewFileImpl
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|extensions
argument_list|,
literal|null
argument_list|,
name|off
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
name|updateWorkingdirectory
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|files
operator|instanceof
name|String
index|[]
condition|)
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
index|[]
operator|)
name|files
argument_list|)
return|;
block|}
comment|// Fix for:
comment|// http://sourceforge.net/tracker/index.php?func=detail&aid=1538769&group_id=92314&atid=600306
if|if
condition|(
name|files
operator|!=
literal|null
condition|)
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|(
name|String
operator|)
name|files
argument_list|)
return|;
block|}
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
DECL|method|getNewFile (JFrame owner, File directory, List<String> extensions, int dialogType, boolean updateWorkingDirectory)
specifier|public
specifier|static
name|String
name|getNewFile
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|int
name|dialogType
parameter_list|,
name|boolean
name|updateWorkingDirectory
parameter_list|)
block|{
return|return
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|extensions
argument_list|,
literal|null
argument_list|,
name|dialogType
argument_list|,
name|updateWorkingDirectory
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|method|getNewFile (JFrame owner, File directory, List<String> extensions, int dialogType, boolean updateWorkingDirectory, JComponent accessory)
specifier|public
specifier|static
name|String
name|getNewFile
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|int
name|dialogType
parameter_list|,
name|boolean
name|updateWorkingDirectory
parameter_list|,
name|JComponent
name|accessory
parameter_list|)
block|{
return|return
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|extensions
argument_list|,
literal|null
argument_list|,
name|dialogType
argument_list|,
name|updateWorkingDirectory
argument_list|,
literal|false
argument_list|,
name|accessory
argument_list|)
return|;
block|}
DECL|method|getNewFile (JFrame owner, File directory, List<String> extensions, String description, int dialogType, boolean updateWorkingDirectory)
specifier|public
specifier|static
name|String
name|getNewFile
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|String
name|description
parameter_list|,
name|int
name|dialogType
parameter_list|,
name|boolean
name|updateWorkingDirectory
parameter_list|)
block|{
return|return
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|extensions
argument_list|,
name|description
argument_list|,
name|dialogType
argument_list|,
name|updateWorkingDirectory
argument_list|,
literal|false
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|method|getNewDir (JFrame owner, File directory, List<String> extensions, int dialogType, boolean updateWorkingDirectory)
specifier|public
specifier|static
name|String
name|getNewDir
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|int
name|dialogType
parameter_list|,
name|boolean
name|updateWorkingDirectory
parameter_list|)
block|{
return|return
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|extensions
argument_list|,
literal|null
argument_list|,
name|dialogType
argument_list|,
name|updateWorkingDirectory
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|method|getNewDir (JFrame owner, File directory, List<String> extensions, String description, int dialogType, boolean updateWorkingDirectory)
specifier|public
specifier|static
name|String
name|getNewDir
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|String
name|description
parameter_list|,
name|int
name|dialogType
parameter_list|,
name|boolean
name|updateWorkingDirectory
parameter_list|)
block|{
return|return
name|FileDialogs
operator|.
name|getNewFile
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|extensions
argument_list|,
name|description
argument_list|,
name|dialogType
argument_list|,
name|updateWorkingDirectory
argument_list|,
literal|true
argument_list|,
literal|null
argument_list|)
return|;
block|}
DECL|method|getNewFile (JFrame owner, File directory, List<String> extensions, String description, int dialogType, boolean updateWorkingDirectory, boolean dirOnly, JComponent accessory)
specifier|private
specifier|static
name|String
name|getNewFile
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|String
name|description
parameter_list|,
name|int
name|dialogType
parameter_list|,
name|boolean
name|updateWorkingDirectory
parameter_list|,
name|boolean
name|dirOnly
parameter_list|,
name|JComponent
name|accessory
parameter_list|)
block|{
name|OpenFileFilter
name|off
decl_stmt|;
if|if
condition|(
name|extensions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|off
operator|=
operator|new
name|OpenFileFilter
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|off
operator|=
operator|new
name|OpenFileFilter
argument_list|(
name|extensions
argument_list|)
expr_stmt|;
block|}
return|return
operator|(
name|String
operator|)
name|FileDialogs
operator|.
name|getNewFileImpl
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|extensions
argument_list|,
name|description
argument_list|,
name|off
argument_list|,
name|dialogType
argument_list|,
name|updateWorkingDirectory
argument_list|,
name|dirOnly
argument_list|,
literal|false
argument_list|,
name|accessory
argument_list|)
return|;
block|}
DECL|method|getNewFileImpl (JFrame owner, File directory, List<String> extensions, String description, OpenFileFilter off, int dialogType, boolean updateWorkingDirectory, boolean dirOnly, boolean multipleSelection, JComponent accessory)
specifier|private
specifier|static
name|Object
name|getNewFileImpl
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|extensions
parameter_list|,
name|String
name|description
parameter_list|,
name|OpenFileFilter
name|off
parameter_list|,
name|int
name|dialogType
parameter_list|,
name|boolean
name|updateWorkingDirectory
parameter_list|,
name|boolean
name|dirOnly
parameter_list|,
name|boolean
name|multipleSelection
parameter_list|,
name|JComponent
name|accessory
parameter_list|)
block|{
comment|// Added the !dirOnly condition below as a workaround to the native file dialog
comment|// not supporting directory selection:
if|if
condition|(
operator|!
name|dirOnly
operator|&&
name|OS
operator|.
name|OS_X
condition|)
block|{
return|return
name|FileDialogs
operator|.
name|getNewFileForMac
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|dialogType
argument_list|,
name|updateWorkingDirectory
argument_list|)
return|;
block|}
name|JFileChooser
name|fc
decl_stmt|;
try|try
block|{
name|fc
operator|=
operator|new
name|JFileChooser
argument_list|(
name|directory
argument_list|)
expr_stmt|;
comment|//JabRefFileChooser(directory);
if|if
condition|(
name|accessory
operator|!=
literal|null
condition|)
block|{
name|fc
operator|.
name|setAccessory
argument_list|(
name|accessory
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|InternalError
name|errl
parameter_list|)
block|{
comment|// This try/catch clause was added because a user reported an
comment|// InternalError getting thrown on WinNT, presumably because of a
comment|// bug in JGoodies Windows PLAF. This clause can be removed if the
comment|// bug is fixed, but for now we just resort to the native file
comment|// dialog, using the same method as is always used on Mac:
return|return
name|FileDialogs
operator|.
name|getNewFileForMac
argument_list|(
name|owner
argument_list|,
name|directory
argument_list|,
name|dialogType
argument_list|,
name|updateWorkingDirectory
argument_list|)
return|;
block|}
if|if
condition|(
name|dirOnly
condition|)
block|{
name|fc
operator|.
name|setFileSelectionMode
argument_list|(
name|JFileChooser
operator|.
name|DIRECTORIES_ONLY
argument_list|)
expr_stmt|;
block|}
name|fc
operator|.
name|setMultiSelectionEnabled
argument_list|(
name|multipleSelection
argument_list|)
expr_stmt|;
name|fc
operator|.
name|addChoosableFileFilter
argument_list|(
name|off
argument_list|)
expr_stmt|;
name|fc
operator|.
name|setDialogType
argument_list|(
name|dialogType
argument_list|)
expr_stmt|;
name|int
name|dialogResult
decl_stmt|;
if|if
condition|(
name|dialogType
operator|==
name|JFileChooser
operator|.
name|OPEN_DIALOG
condition|)
block|{
name|dialogResult
operator|=
name|fc
operator|.
name|showOpenDialog
argument_list|(
name|owner
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|dialogType
operator|==
name|JFileChooser
operator|.
name|SAVE_DIALOG
condition|)
block|{
name|dialogResult
operator|=
name|fc
operator|.
name|showSaveDialog
argument_list|(
name|owner
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogResult
operator|=
name|fc
operator|.
name|showDialog
argument_list|(
name|owner
argument_list|,
name|description
argument_list|)
expr_stmt|;
block|}
comment|// the getSelectedFile method returns a valid fileselection
comment|// (if something is selected) indepentently from dialog return status
if|if
condition|(
name|dialogResult
operator|!=
name|JFileChooser
operator|.
name|APPROVE_OPTION
condition|)
block|{
return|return
literal|null
return|;
block|}
comment|// okay button
name|File
name|selectedFile
init|=
name|fc
operator|.
name|getSelectedFile
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedFile
operator|==
literal|null
condition|)
block|{
comment|// cancel
return|return
literal|null
return|;
block|}
comment|// If this is a save dialog, and the user has not chosen "All files" as
comment|// filter
comment|// we enforce the given extension. But only if extension is not null.
if|if
condition|(
operator|(
operator|!
name|extensions
operator|.
name|isEmpty
argument_list|()
operator|)
operator|&&
operator|(
name|dialogType
operator|==
name|JFileChooser
operator|.
name|SAVE_DIALOG
operator|)
operator|&&
operator|(
name|fc
operator|.
name|getFileFilter
argument_list|()
operator|==
name|off
operator|)
operator|&&
operator|!
name|off
operator|.
name|accept
argument_list|(
name|selectedFile
argument_list|)
condition|)
block|{
comment|// add the first extension if there are multiple extensions
name|selectedFile
operator|=
operator|new
name|File
argument_list|(
name|selectedFile
operator|.
name|getPath
argument_list|()
operator|+
name|extensions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|updateWorkingDirectory
condition|)
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
name|selectedFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|multipleSelection
condition|)
block|{
name|File
index|[]
name|files
init|=
name|fc
operator|.
name|getSelectedFiles
argument_list|()
decl_stmt|;
name|String
index|[]
name|filenames
init|=
operator|new
name|String
index|[
name|files
operator|.
name|length
index|]
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|files
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|filenames
index|[
name|i
index|]
operator|=
name|files
index|[
name|i
index|]
operator|.
name|getAbsolutePath
argument_list|()
expr_stmt|;
block|}
return|return
name|filenames
return|;
block|}
else|else
block|{
return|return
name|selectedFile
operator|.
name|getAbsolutePath
argument_list|()
return|;
block|}
block|}
DECL|method|getNewFileForMac (JFrame owner, File directory, int dialogType, boolean updateWorkingDirectory)
specifier|private
specifier|static
name|String
name|getNewFileForMac
parameter_list|(
name|JFrame
name|owner
parameter_list|,
name|File
name|directory
parameter_list|,
name|int
name|dialogType
parameter_list|,
name|boolean
name|updateWorkingDirectory
parameter_list|)
block|{
name|java
operator|.
name|awt
operator|.
name|FileDialog
name|fc
init|=
operator|new
name|java
operator|.
name|awt
operator|.
name|FileDialog
argument_list|(
name|owner
argument_list|)
decl_stmt|;
if|if
condition|(
name|directory
operator|!=
literal|null
condition|)
block|{
name|fc
operator|.
name|setDirectory
argument_list|(
name|directory
operator|.
name|getParent
argument_list|()
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|dialogType
operator|==
name|JFileChooser
operator|.
name|OPEN_DIALOG
condition|)
block|{
name|fc
operator|.
name|setMode
argument_list|(
name|java
operator|.
name|awt
operator|.
name|FileDialog
operator|.
name|LOAD
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fc
operator|.
name|setMode
argument_list|(
name|java
operator|.
name|awt
operator|.
name|FileDialog
operator|.
name|SAVE
argument_list|)
expr_stmt|;
block|}
name|fc
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|fc
operator|.
name|getFile
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
if|if
condition|(
name|updateWorkingDirectory
condition|)
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
name|fc
operator|.
name|getDirectory
argument_list|()
operator|+
name|fc
operator|.
name|getFile
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|fc
operator|.
name|getDirectory
argument_list|()
operator|+
name|fc
operator|.
name|getFile
argument_list|()
return|;
block|}
block|}
block|}
end_class

end_unit

