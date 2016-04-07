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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibDatabaseContext
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
name|BasePanel
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
name|JabRef
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
name|ParserResultWarningDialog
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
name|exporter
operator|.
name|AutoSaveManager
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

begin_comment
comment|/**  * Runnable task that prompts the user for what to do about files loaded at startup,  * where an autosave file was found. The task should be run on the EDT after startup.  */
end_comment

begin_class
DECL|class|AutosaveStartupPrompter
specifier|public
class|class
name|AutosaveStartupPrompter
implements|implements
name|Runnable
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|files
specifier|private
specifier|final
name|List
argument_list|<
name|File
argument_list|>
name|files
decl_stmt|;
DECL|method|AutosaveStartupPrompter (JabRefFrame frame, List<File> files)
specifier|public
name|AutosaveStartupPrompter
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|List
argument_list|<
name|File
argument_list|>
name|files
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
name|files
operator|=
name|files
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
name|boolean
name|first
init|=
name|frame
operator|.
name|getBasePanelCount
argument_list|()
operator|==
literal|0
decl_stmt|;
name|List
argument_list|<
name|ParserResult
argument_list|>
name|loaded
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|ParserResult
argument_list|,
name|Integer
argument_list|>
name|location
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|file
range|:
name|files
control|)
block|{
name|File
name|fileToLoad
init|=
name|file
decl_stmt|;
name|boolean
name|tryingAutosave
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PROMPT_BEFORE_USING_AUTOSAVE
argument_list|)
condition|)
block|{
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
literal|null
argument_list|,
literal|"<html>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"An autosave file was found for this database. This could indicate "
operator|+
literal|"that JabRef didn't shut down cleanly last time the file was used."
argument_list|)
operator|+
literal|"<br>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do you want to recover the database from the autosave file?"
argument_list|)
operator|+
literal|"</html>"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Autosave of file '%0'"
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_OPTION
argument_list|)
decl_stmt|;
name|tryingAutosave
operator|=
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
expr_stmt|;
block|}
else|else
block|{
name|tryingAutosave
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|tryingAutosave
condition|)
block|{
name|fileToLoad
operator|=
name|AutoSaveManager
operator|.
name|getAutoSaveFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
block|}
name|boolean
name|done
init|=
literal|false
decl_stmt|;
name|ParserResult
name|pr
init|=
literal|null
decl_stmt|;
while|while
condition|(
operator|!
name|done
condition|)
block|{
name|pr
operator|=
name|JabRef
operator|.
name|openBibFile
argument_list|(
name|fileToLoad
operator|.
name|getPath
argument_list|()
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|pr
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|pr
operator|.
name|isInvalid
argument_list|()
condition|)
block|{
name|loaded
operator|.
name|add
argument_list|(
name|pr
argument_list|)
expr_stmt|;
name|BibDatabaseContext
name|databaseContext
init|=
name|pr
operator|.
name|getDatabaseContext
argument_list|()
decl_stmt|;
name|databaseContext
operator|.
name|setDatabaseFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
name|BasePanel
name|panel
init|=
name|frame
operator|.
name|addTab
argument_list|(
name|databaseContext
argument_list|,
name|pr
operator|.
name|getEncoding
argument_list|()
argument_list|,
name|first
argument_list|)
decl_stmt|;
name|location
operator|.
name|put
argument_list|(
name|pr
argument_list|,
name|frame
operator|.
name|getBasePanelCount
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|tryingAutosave
condition|)
block|{
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
block|}
name|first
operator|=
literal|false
expr_stmt|;
name|done
operator|=
literal|true
expr_stmt|;
block|}
else|else
block|{
if|if
condition|(
name|tryingAutosave
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
literal|"Error opening autosave of '%0'. Trying to load '%0' instead."
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
literal|"Error opening file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|tryingAutosave
operator|=
literal|false
expr_stmt|;
name|fileToLoad
operator|=
name|file
expr_stmt|;
block|}
else|else
block|{
name|String
name|message
decl_stmt|;
if|if
condition|(
name|pr
operator|==
literal|null
condition|)
block|{
name|message
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file '%0'."
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|message
operator|=
literal|"<html>"
operator|+
name|pr
operator|.
name|getErrorMessage
argument_list|()
operator|+
literal|"<p>"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file '%0'."
argument_list|,
name|file
operator|.
name|getName
argument_list|()
argument_list|)
operator|+
literal|"</html>"
expr_stmt|;
block|}
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|message
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|done
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|(
name|pr
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|pr
operator|.
name|isInvalid
argument_list|()
operator|&&
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|DISPLAY_KEY_WARNING_DIALOG_AT_STARTUP
argument_list|)
condition|)
block|{
name|ParserResultWarningDialog
operator|.
name|showParserResultWarningDialog
argument_list|(
name|pr
argument_list|,
name|frame
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

