begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
end_comment

begin_package
DECL|package|net.sf.jabref.exporter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|Defaults
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
name|model
operator|.
name|database
operator|.
name|BibDatabaseMode
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Timer
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TimerTask
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

begin_comment
comment|/**  * Background task and utilities for autosave feature.  */
end_comment

begin_class
DECL|class|AutoSaveManager
specifier|public
class|class
name|AutoSaveManager
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
name|AutoSaveManager
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|t
specifier|private
name|Timer
name|t
decl_stmt|;
DECL|method|AutoSaveManager (JabRefFrame frame)
specifier|public
name|AutoSaveManager
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
DECL|method|startAutoSaveTimer ()
specifier|public
name|void
name|startAutoSaveTimer
parameter_list|()
block|{
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
block|{
comment|// shut down any previously set timer to not leak any timers
name|t
operator|.
name|cancel
argument_list|()
expr_stmt|;
block|}
name|TimerTask
name|task
init|=
operator|new
name|AutoSaveTask
argument_list|()
decl_stmt|;
name|t
operator|=
operator|new
name|Timer
argument_list|()
expr_stmt|;
name|long
name|interval
init|=
operator|(
name|long
operator|)
literal|60000
operator|*
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|AUTO_SAVE_INTERVAL
argument_list|)
decl_stmt|;
name|t
operator|.
name|scheduleAtFixedRate
argument_list|(
name|task
argument_list|,
name|interval
argument_list|,
name|interval
argument_list|)
expr_stmt|;
block|}
DECL|method|stopAutoSaveTimer ()
specifier|public
name|void
name|stopAutoSaveTimer
parameter_list|()
block|{
name|t
operator|.
name|cancel
argument_list|()
expr_stmt|;
block|}
DECL|class|AutoSaveTask
specifier|private
class|class
name|AutoSaveTask
extends|extends
name|TimerTask
block|{
annotation|@
name|Override
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|// Since this method is running in the background, we must be prepared that
comment|// there could be changes done by the user while this method is running.
for|for
control|(
name|BasePanel
name|panel
range|:
name|frame
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
if|if
condition|(
name|panel
operator|.
name|isModified
argument_list|()
operator|&&
operator|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
name|AutoSaveManager
operator|.
name|autoSave
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
comment|/**      * Get a File object pointing to the autosave file corresponding to the given file.      * @param f The database file.      * @return its corresponding autosave file.      */
DECL|method|getAutoSaveFile (File f)
specifier|public
specifier|static
name|File
name|getAutoSaveFile
parameter_list|(
name|File
name|f
parameter_list|)
block|{
return|return
operator|new
name|File
argument_list|(
name|f
operator|.
name|getParentFile
argument_list|()
argument_list|,
literal|".$"
operator|+
name|f
operator|.
name|getName
argument_list|()
operator|+
literal|'$'
argument_list|)
return|;
block|}
comment|/**      * Perform an autosave.      * @param panel The BasePanel to autosave for.      * @return true if successful, false otherwise.      */
DECL|method|autoSave (BasePanel panel)
specifier|private
specifier|static
name|boolean
name|autoSave
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|File
name|databaseFile
init|=
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
decl_stmt|;
name|File
name|backupFile
init|=
name|AutoSaveManager
operator|.
name|getAutoSaveFile
argument_list|(
name|databaseFile
argument_list|)
decl_stmt|;
try|try
block|{
name|SavePreferences
name|prefs
init|=
name|SavePreferences
operator|.
name|loadForSaveFromPreferences
argument_list|(
name|Globals
operator|.
name|prefs
argument_list|)
operator|.
name|withMakeBackup
argument_list|(
literal|false
argument_list|)
operator|.
name|withEncoding
argument_list|(
name|panel
operator|.
name|getEncoding
argument_list|()
argument_list|)
decl_stmt|;
name|BibDatabaseWriter
name|databaseWriter
init|=
operator|new
name|BibDatabaseWriter
argument_list|()
decl_stmt|;
name|SaveSession
name|ss
init|=
name|databaseWriter
operator|.
name|saveDatabase
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|,
name|prefs
argument_list|)
decl_stmt|;
block|}
catch|catch
parameter_list|(
name|SaveException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem with automatic save"
argument_list|,
name|e
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
comment|/**      * Delete this BasePanel's autosave if it exists.      * @param panel The BasePanel in question.      * @return true if there was no autosave or if the autosave was successfully deleted, false otherwise.      */
DECL|method|deleteAutoSaveFile (BasePanel panel)
specifier|public
specifier|static
name|boolean
name|deleteAutoSaveFile
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
if|if
condition|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return
literal|true
return|;
block|}
name|File
name|backupFile
init|=
name|AutoSaveManager
operator|.
name|getAutoSaveFile
argument_list|(
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getDatabaseFile
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|backupFile
operator|.
name|exists
argument_list|()
condition|)
block|{
return|return
name|backupFile
operator|.
name|delete
argument_list|()
return|;
block|}
else|else
block|{
return|return
literal|true
return|;
block|}
block|}
comment|/**      * Clean up by deleting the autosave files corresponding to all open files,      * if they exist.      */
DECL|method|clearAutoSaves ()
specifier|public
name|void
name|clearAutoSaves
parameter_list|()
block|{
for|for
control|(
name|BasePanel
name|panel
range|:
name|frame
operator|.
name|getBasePanelList
argument_list|()
control|)
block|{
name|AutoSaveManager
operator|.
name|deleteAutoSaveFile
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Check if a newer autosave exists for the given file.      * @param f The file to check.      * @return true if an autosave is found, and if the autosave is newer      *   than the given file.      */
DECL|method|newerAutoSaveExists (File f)
specifier|public
specifier|static
name|boolean
name|newerAutoSaveExists
parameter_list|(
name|File
name|f
parameter_list|)
block|{
name|File
name|asFile
init|=
name|AutoSaveManager
operator|.
name|getAutoSaveFile
argument_list|(
name|f
argument_list|)
decl_stmt|;
return|return
name|asFile
operator|.
name|exists
argument_list|()
operator|&&
operator|(
name|asFile
operator|.
name|lastModified
argument_list|()
operator|>
name|f
operator|.
name|lastModified
argument_list|()
operator|)
return|;
block|}
block|}
end_class

end_unit

