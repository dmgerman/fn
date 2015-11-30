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
name|Enumeration
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
name|*
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
name|groups
operator|.
name|structure
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
name|gui
operator|.
name|*
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
name|groups
operator|.
name|structure
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
name|groups
operator|.
name|structure
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
name|gui
operator|.
name|util
operator|.
name|PositionWindow
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
name|model
operator|.
name|database
operator|.
name|BibtexDatabase
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
name|BibtexEntry
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
name|util
operator|.
name|Util
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
name|PositionWindow
operator|.
name|placeDialog
argument_list|(
name|md
argument_list|,
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
name|String
index|[]
name|chosen
init|=
name|FileDialogs
operator|.
name|getMultipleFiles
argument_list|(
name|frame
argument_list|,
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
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
argument_list|,
literal|null
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|//String chosenFile = Globals.getNewFile(frame, new File(Globals.prefs.get("workingDirectory")),
comment|//                                       null, JFileChooser.OPEN_DIALOG, false);
if|if
condition|(
name|chosen
operator|==
literal|null
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
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
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
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|//frame.getFileHistory().newFile(panel.fileToOpen.getPath());
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
name|Charset
operator|.
name|forName
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_ENCODING
argument_list|)
argument_list|)
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
name|Throwable
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
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
name|BibtexDatabase
name|fromDatabase
init|=
name|pr
operator|.
name|getDatabase
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|appendedEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|originalEntries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|BibtexDatabase
name|database
init|=
name|panel
operator|.
name|database
argument_list|()
decl_stmt|;
name|BibtexEntry
name|originalEntry
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
name|String
name|key
range|:
name|fromDatabase
operator|.
name|getKeySet
argument_list|()
control|)
block|{
name|originalEntry
operator|=
name|fromDatabase
operator|.
name|getEntryById
argument_list|(
name|key
argument_list|)
expr_stmt|;
name|BibtexEntry
name|be
init|=
operator|(
name|BibtexEntry
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
name|Util
operator|.
name|setAutomaticFields
argument_list|(
name|be
argument_list|,
name|overwriteOwner
argument_list|,
name|overwriteTimeStamp
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
operator|new
name|ExplicitGroup
argument_list|(
literal|"Imported"
argument_list|,
name|GroupHierarchyType
operator|.
name|INDEPENDENT
argument_list|)
decl_stmt|;
name|newGroups
operator|.
name|setGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
for|for
control|(
name|BibtexEntry
name|appendedEntry
range|:
name|appendedEntries
control|)
block|{
name|group
operator|.
name|addEntry
argument_list|(
name|appendedEntry
argument_list|)
expr_stmt|;
block|}
block|}
comment|// groupsSelector is always created, even when no groups
comment|// have been defined. therefore, no check for null is
comment|// required here
name|frame
operator|.
name|groupSelector
operator|.
name|addGroups
argument_list|(
name|newGroups
argument_list|,
name|ce
argument_list|)
expr_stmt|;
comment|// for explicit groups, the entries copied to the mother fromDatabase have to
comment|// be "reassigned", i.e. the old reference is removed and the reference
comment|// to the new fromDatabase is added.
name|GroupTreeNode
name|node
decl_stmt|;
name|ExplicitGroup
name|group
decl_stmt|;
name|BibtexEntry
name|entry
decl_stmt|;
for|for
control|(
name|Enumeration
argument_list|<
name|GroupTreeNode
argument_list|>
name|e
init|=
name|newGroups
operator|.
name|preorderEnumeration
argument_list|()
init|;
name|e
operator|.
name|hasMoreElements
argument_list|()
condition|;
control|)
block|{
name|node
operator|=
name|e
operator|.
name|nextElement
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
operator|(
name|node
operator|.
name|getGroup
argument_list|()
operator|instanceof
name|ExplicitGroup
operator|)
condition|)
block|{
continue|continue;
block|}
name|group
operator|=
operator|(
name|ExplicitGroup
operator|)
name|node
operator|.
name|getGroup
argument_list|()
expr_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|originalEntries
operator|.
name|size
argument_list|()
condition|;
operator|++
name|i
control|)
block|{
name|entry
operator|=
name|originalEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
if|if
condition|(
name|group
operator|.
name|contains
argument_list|(
name|entry
argument_list|)
condition|)
block|{
name|group
operator|.
name|removeEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|group
operator|.
name|addEntry
argument_list|(
name|appendedEntries
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|frame
operator|.
name|groupSelector
operator|.
name|revalidateGroups
argument_list|()
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
name|Globals
operator|.
name|SELECTOR_META_PREFIX
argument_list|)
condition|)
block|{
name|panel
operator|.
name|metaData
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
name|undoManager
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

