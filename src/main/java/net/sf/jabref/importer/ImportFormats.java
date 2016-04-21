begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|Set
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
name|TreeSet
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
name|JFileChooser
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
name|importer
operator|.
name|fileformat
operator|.
name|ImportFormat
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
DECL|method|createImportFileChooser (String currentDir)
specifier|private
specifier|static
name|JFileChooser
name|createImportFileChooser
parameter_list|(
name|String
name|currentDir
parameter_list|)
block|{
name|SortedSet
argument_list|<
name|ImportFormat
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
name|String
name|lastUsedFormat
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|LAST_USED_IMPORT
argument_list|)
decl_stmt|;
name|FileFilter
name|defaultFilter
init|=
literal|null
decl_stmt|;
name|JFileChooser
name|fc
init|=
operator|new
name|JFileChooser
argument_list|(
name|currentDir
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|ImportFileFilter
argument_list|>
name|filters
init|=
operator|new
name|TreeSet
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|ImportFormat
name|format
range|:
name|importers
control|)
block|{
name|ImportFileFilter
name|filter
init|=
operator|new
name|ImportFileFilter
argument_list|(
name|format
argument_list|)
decl_stmt|;
name|filters
operator|.
name|add
argument_list|(
name|filter
argument_list|)
expr_stmt|;
if|if
condition|(
name|format
operator|.
name|getFormatName
argument_list|()
operator|.
name|equals
argument_list|(
name|lastUsedFormat
argument_list|)
condition|)
block|{
name|defaultFilter
operator|=
name|filter
expr_stmt|;
block|}
block|}
for|for
control|(
name|ImportFileFilter
name|filter
range|:
name|filters
control|)
block|{
name|fc
operator|.
name|addChoosableFileFilter
argument_list|(
name|filter
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|defaultFilter
operator|==
literal|null
condition|)
block|{
name|fc
operator|.
name|setFileFilter
argument_list|(
name|fc
operator|.
name|getAcceptAllFileFilter
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|fc
operator|.
name|setFileFilter
argument_list|(
name|defaultFilter
argument_list|)
expr_stmt|;
block|}
return|return
name|fc
return|;
block|}
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
name|openInNew
decl_stmt|;
specifier|public
name|ImportAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|boolean
name|openInNew
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
name|openInNew
operator|=
name|openInNew
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|openInNew
condition|?
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Import into new database"
argument_list|)
else|:
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
name|openInNew
condition|?
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
else|:
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
name|JFileChooser
name|fileChooser
init|=
name|createImportFileChooser
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
decl_stmt|;
name|int
name|result
init|=
name|fileChooser
operator|.
name|showOpenDialog
argument_list|(
name|frame
argument_list|)
decl_stmt|;
if|if
condition|(
name|result
operator|!=
name|JFileChooser
operator|.
name|APPROVE_OPTION
condition|)
block|{
return|return;
block|}
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
name|file
operator|==
literal|null
condition|)
block|{
return|return;
block|}
name|FileFilter
name|ff
init|=
name|fileChooser
operator|.
name|getFileFilter
argument_list|()
decl_stmt|;
name|ImportFormat
name|format
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|ff
operator|instanceof
name|ImportFileFilter
condition|)
block|{
name|format
operator|=
operator|(
operator|(
name|ImportFileFilter
operator|)
name|ff
operator|)
operator|.
name|getImportFormat
argument_list|()
expr_stmt|;
block|}
try|try
block|{
if|if
condition|(
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
comment|// Warn that the file doesn't exists:
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
name|ImportMenuItem
name|imi
init|=
operator|new
name|ImportMenuItem
argument_list|(
name|frame
argument_list|,
name|openInNew
argument_list|,
name|format
argument_list|)
decl_stmt|;
name|imi
operator|.
name|automatedImport
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
name|file
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|// Make sure we remember which filter was used, to set the default
comment|// for next time:
if|if
condition|(
name|format
operator|==
literal|null
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
name|LAST_USED_IMPORT
argument_list|,
literal|"__all"
argument_list|)
expr_stmt|;
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
name|LAST_USED_IMPORT
argument_list|,
name|format
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
block|}
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
literal|"Problem with import format"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
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

