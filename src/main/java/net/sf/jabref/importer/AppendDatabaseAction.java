begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|Charset
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
name|List
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
name|JabRefExecutorService
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
name|MetaData
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
name|MergeDialog
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
name|NewFileDialogs
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
name|BaseAction
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
name|undo
operator|.
name|NamedCompound
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
name|undo
operator|.
name|UndoableInsertEntry
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
name|undo
operator|.
name|UndoableInsertString
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
name|ParseException
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
name|groups
operator|.
name|AllEntriesGroup
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
name|groups
operator|.
name|ExplicitGroup
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
name|groups
operator|.
name|GroupHierarchyType
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
name|groups
operator|.
name|GroupTreeNode
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
name|UpdateField
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
name|BibDatabase
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
name|KeyCollisionException
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
name|entry
operator|.
name|BibEntry
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
name|entry
operator|.
name|BibtexString
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
name|entry
operator|.
name|IdGenerator
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
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: May 18, 2006  * Time: 9:49:02 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|AppendDatabaseAction
specifier|public
class|class
name|AppendDatabaseAction
implements|implements
name|BaseAction
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|filesToOpen
specifier|private
specifier|final
name|List
argument_list|<
name|File
argument_list|>
name|filesToOpen
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
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
name|AppendDatabaseAction
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|method|AppendDatabaseAction (JabRefFrame frame, BasePanel panel)
specifier|public
name|AppendDatabaseAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
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
name|panel
operator|=
name|panel
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
name|filesToOpen
operator|.
name|clear
argument_list|()
expr_stmt|;
specifier|final
name|MergeDialog
name|md
init|=
operator|new
name|MergeDialog
argument_list|(
name|frame
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Append database"
argument_list|)
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|md
operator|.
name|setLocationRelativeTo
argument_list|(
name|panel
argument_list|)
expr_stmt|;
name|md
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|md
operator|.
name|isOkPressed
argument_list|()
condition|)
block|{
name|List
argument_list|<
name|String
argument_list|>
name|chosen
init|=
operator|new
name|NewFileDialogs
argument_list|(
name|frame
argument_list|)
operator|.
name|updateWorkingDirPref
argument_list|()
operator|.
name|showDlgAndGetMultipleFiles
argument_list|()
decl_stmt|;
if|if
condition|(
name|chosen
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
for|for
control|(
name|String
name|aChosen
range|:
name|chosen
control|)
block|{
name|filesToOpen
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|aChosen
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Run the actual open in a thread to prevent the program
comment|// locking until the file is loaded.
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
parameter_list|()
lambda|->
name|openIt
argument_list|(
name|md
operator|.
name|importEntries
argument_list|()
argument_list|,
name|md
operator|.
name|importStrings
argument_list|()
argument_list|,
name|md
operator|.
name|importGroups
argument_list|()
argument_list|,
name|md
operator|.
name|importSelectorWords
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|openIt (boolean importEntries, boolean importStrings, boolean importGroups, boolean importSelectorWords)
specifier|private
name|void
name|openIt
parameter_list|(
name|boolean
name|importEntries
parameter_list|,
name|boolean
name|importStrings
parameter_list|,
name|boolean
name|importGroups
parameter_list|,
name|boolean
name|importSelectorWords
parameter_list|)
block|{
if|if
condition|(
name|filesToOpen
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
for|for
control|(
name|File
name|file
range|:
name|filesToOpen
control|)
block|{
try|try
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
name|file
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
comment|// Should this be done _after_ we know it was successfully opened?
name|Charset
name|encoding
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getDefaultEncoding
argument_list|()
decl_stmt|;
name|ParserResult
name|pr
init|=
name|OpenDatabaseAction
operator|.
name|loadDatabase
argument_list|(
name|file
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
name|AppendDatabaseAction
operator|.
name|mergeFromBibtex
argument_list|(
name|frame
argument_list|,
name|panel
argument_list|,
name|pr
argument_list|,
name|importEntries
argument_list|,
name|importStrings
argument_list|,
name|importGroups
argument_list|,
name|importSelectorWords
argument_list|)
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Imported from database"
argument_list|)
operator|+
literal|" '"
operator|+
name|file
operator|.
name|getPath
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
decl||
name|KeyCollisionException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not open database"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|panel
argument_list|,
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open database"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|mergeFromBibtex (JabRefFrame frame, BasePanel panel, ParserResult pr, boolean importEntries, boolean importStrings, boolean importGroups, boolean importSelectorWords)
specifier|private
specifier|static
name|void
name|mergeFromBibtex
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|panel
parameter_list|,
name|ParserResult
name|pr
parameter_list|,
name|boolean
name|importEntries
parameter_list|,
name|boolean
name|importStrings
parameter_list|,
name|boolean
name|importGroups
parameter_list|,
name|boolean
name|importSelectorWords
parameter_list|)
throws|throws
name|KeyCollisionException
block|{
name|BibDatabase
name|fromDatabase
init|=
name|pr
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|appendedEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|originalEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|BibDatabase
name|database
init|=
name|panel
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Append database"
argument_list|)
argument_list|)
decl_stmt|;
name|MetaData
name|meta
init|=
name|pr
operator|.
name|getMetaData
argument_list|()
decl_stmt|;
if|if
condition|(
name|importEntries
condition|)
block|{
comment|// Add entries
name|boolean
name|overwriteOwner
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERWRITE_OWNER
argument_list|)
decl_stmt|;
name|boolean
name|overwriteTimeStamp
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERWRITE_TIME_STAMP
argument_list|)
decl_stmt|;
for|for
control|(
name|BibEntry
name|originalEntry
range|:
name|fromDatabase
operator|.
name|getEntries
argument_list|()
control|)
block|{
name|BibEntry
name|be
init|=
operator|(
name|BibEntry
operator|)
name|originalEntry
operator|.
name|clone
argument_list|()
decl_stmt|;
name|be
operator|.
name|setId
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
name|UpdateField
operator|.
name|setAutomaticFields
argument_list|(
name|be
argument_list|,
name|overwriteOwner
argument_list|,
name|overwriteTimeStamp
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|appendedEntries
operator|.
name|add
argument_list|(
name|be
argument_list|)
expr_stmt|;
name|originalEntries
operator|.
name|add
argument_list|(
name|originalEntry
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertEntry
argument_list|(
name|database
argument_list|,
name|be
argument_list|,
name|panel
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|importStrings
condition|)
block|{
for|for
control|(
name|BibtexString
name|bs
range|:
name|fromDatabase
operator|.
name|getStringValues
argument_list|()
control|)
block|{
if|if
condition|(
operator|!
name|database
operator|.
name|hasStringLabel
argument_list|(
name|bs
operator|.
name|getName
argument_list|()
argument_list|)
condition|)
block|{
name|database
operator|.
name|addString
argument_list|(
name|bs
argument_list|)
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableInsertString
argument_list|(
name|panel
argument_list|,
name|database
argument_list|,
name|bs
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|importGroups
condition|)
block|{
name|GroupTreeNode
name|newGroups
init|=
name|meta
operator|.
name|getGroups
argument_list|()
decl_stmt|;
if|if
condition|(
name|newGroups
operator|!=
literal|null
condition|)
block|{
comment|// ensure that there is always only one AllEntriesGroup
if|if
condition|(
name|newGroups
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|AllEntriesGroup
condition|)
block|{
comment|// create a dummy group
name|ExplicitGroup
name|group
init|=
literal|null
decl_stmt|;
try|try
block|{
name|group
operator|=
operator|new
name|ExplicitGroup
argument_list|(
literal|"Imported"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ParseException
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
block|}
name|newGroups
operator|.
name|setGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|group
operator|.
name|add
argument_list|(
name|appendedEntries
argument_list|)
expr_stmt|;
block|}
comment|// groupsSelector is always created, even when no groups
comment|// have been defined. therefore, no check for null is
comment|// required here
name|frame
operator|.
name|getGroupSelector
argument_list|()
operator|.
name|addGroups
argument_list|(
name|newGroups
argument_list|,
name|ce
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|importSelectorWords
condition|)
block|{
for|for
control|(
name|String
name|s
range|:
name|meta
control|)
block|{
if|if
condition|(
name|s
operator|.
name|startsWith
argument_list|(
name|MetaData
operator|.
name|SELECTOR_META_PREFIX
argument_list|)
condition|)
block|{
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
operator|.
name|putData
argument_list|(
name|s
argument_list|,
name|meta
operator|.
name|getData
argument_list|(
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|ce
operator|.
name|end
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getUndoManager
argument_list|()
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

