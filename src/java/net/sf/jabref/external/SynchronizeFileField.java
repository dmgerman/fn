begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

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
name|ButtonBarBuilder
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
name|DefaultFormBuilder
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
name|gui
operator|.
name|FileListEditor
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
name|FileListEntry
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
name|FileListEntryEditor
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
name|FileListTableModel
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
name|undo
operator|.
name|UndoableFieldChange
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
name|ActionEvent
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
name|*
import|;
end_import

begin_comment
comment|/**  * This action goes through all selected entries in the BasePanel, and attempts to autoset the  * given external file (pdf, ps, ...) based on the same algorithm used for the "Auto" button in  * EntryEditor.  */
end_comment

begin_class
DECL|class|SynchronizeFileField
specifier|public
class|class
name|SynchronizeFileField
extends|extends
name|AbstractWorker
block|{
DECL|field|fieldName
specifier|private
name|String
name|fieldName
init|=
name|GUIGlobals
operator|.
name|FILE_FIELD
decl_stmt|;
DECL|field|panel
specifier|private
name|BasePanel
name|panel
decl_stmt|;
DECL|field|sel
specifier|private
name|BibtexEntry
index|[]
name|sel
init|=
literal|null
decl_stmt|;
DECL|field|optDiag
specifier|private
name|SynchronizeFileField
operator|.
name|OptionsDialog
name|optDiag
init|=
literal|null
decl_stmt|;
DECL|field|brokenLinkOptions
name|Object
index|[]
name|brokenLinkOptions
init|=
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ignore"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Assign new file"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove link"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remove all broken links"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Quit synchronization"
argument_list|)
block|}
decl_stmt|;
DECL|field|goOn
DECL|field|autoSet
DECL|field|checkExisting
specifier|private
name|boolean
name|goOn
init|=
literal|true
decl_stmt|,
name|autoSet
init|=
literal|true
decl_stmt|,
name|checkExisting
init|=
literal|true
decl_stmt|;
DECL|field|brokenLinks
DECL|field|entriesChangedCount
specifier|private
name|int
name|brokenLinks
init|=
literal|0
decl_stmt|,
name|entriesChangedCount
init|=
literal|0
decl_stmt|;
DECL|method|SynchronizeFileField (BasePanel panel)
specifier|public
name|SynchronizeFileField
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|col
init|=
name|panel
operator|.
name|database
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|goOn
operator|=
literal|true
expr_stmt|;
name|sel
operator|=
operator|new
name|BibtexEntry
index|[
name|col
operator|.
name|size
argument_list|()
index|]
expr_stmt|;
name|sel
operator|=
name|col
operator|.
name|toArray
argument_list|(
name|sel
argument_list|)
expr_stmt|;
comment|// Ask about rules for the operation:
if|if
condition|(
name|optDiag
operator|==
literal|null
condition|)
name|optDiag
operator|=
operator|new
name|SynchronizeFileField
operator|.
name|OptionsDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|panel
operator|.
name|metaData
argument_list|()
argument_list|,
name|fieldName
argument_list|)
expr_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|optDiag
argument_list|,
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
expr_stmt|;
name|optDiag
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|optDiag
operator|.
name|canceled
argument_list|()
condition|)
block|{
name|goOn
operator|=
literal|false
expr_stmt|;
return|return;
block|}
name|autoSet
operator|=
operator|!
name|optDiag
operator|.
name|autoSetNone
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|checkExisting
operator|=
name|optDiag
operator|.
name|checkLinks
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Synchronizing %0 links..."
argument_list|,
name|fieldName
operator|.
name|toUpperCase
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|run ()
specifier|public
name|void
name|run
parameter_list|()
block|{
if|if
condition|(
operator|!
name|goOn
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No entries selected."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarValue
argument_list|(
literal|0
argument_list|)
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|int
name|weightAutoSet
init|=
literal|10
decl_stmt|;
comment|// autoSet takes 10 (?) times longer than checkExisting
name|int
name|progressBarMax
init|=
operator|(
name|autoSet
condition|?
name|weightAutoSet
operator|*
name|sel
operator|.
name|length
else|:
literal|0
operator|)
operator|+
operator|(
name|checkExisting
condition|?
name|sel
operator|.
name|length
else|:
literal|0
operator|)
decl_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarMaximum
argument_list|(
name|progressBarMax
argument_list|)
expr_stmt|;
name|int
name|progress
init|=
literal|0
decl_stmt|;
name|brokenLinks
operator|=
literal|0
expr_stmt|;
specifier|final
name|NamedCompound
name|ce
init|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autoset %0 field"
argument_list|,
name|fieldName
argument_list|)
argument_list|)
decl_stmt|;
comment|//final OpenFileFilter off = Util.getFileFilterForField(fieldName);
comment|//ExternalFilePanel extPan = new ExternalFilePanel(fieldName, panel.metaData(), null, null, off);
comment|//FieldTextField editor = new FieldTextField(fieldName, "", false);
comment|// Find the default directory for this field type:
name|String
index|[]
name|dirsS
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|BibtexEntry
argument_list|>
name|changedEntries
init|=
operator|new
name|HashSet
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
comment|// First we try to autoset fields
if|if
condition|(
name|autoSet
condition|)
block|{
name|Collection
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
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
name|sel
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|entries
operator|.
name|add
argument_list|(
name|sel
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
comment|// We need to specify which directories to search in:
name|ArrayList
argument_list|<
name|File
argument_list|>
name|dirs
init|=
operator|new
name|ArrayList
argument_list|<
name|File
argument_list|>
argument_list|()
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
name|dirsS
operator|.
name|length
condition|;
name|i
operator|++
control|)
name|dirs
operator|.
name|add
argument_list|(
operator|new
name|File
argument_list|(
name|dirsS
index|[
name|i
index|]
argument_list|)
argument_list|)
expr_stmt|;
comment|// Start the autosetting process:
name|Thread
name|t
init|=
name|FileListEditor
operator|.
name|autoSetLinks
argument_list|(
name|entries
argument_list|,
name|ce
argument_list|,
name|changedEntries
argument_list|,
name|dirs
argument_list|)
decl_stmt|;
comment|// Wait for the autosetting to finish:
try|try
block|{
name|t
operator|.
name|join
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
comment|/*                 progress += weightAutoSet;                 panel.frame().setProgressBarValue(progress);                  Object old = sel[i].getField(fieldName);                 FileListTableModel tableModel = new FileListTableModel();                 if (old != null)                     tableModel.setContent((String)old);                 Thread t = FileListEditor.autoSetLinks(sel[i], tableModel, null, null);                  if (!tableModel.getStringRepresentation().equals(old)) {                     String toSet = tableModel.getStringRepresentation();                     if (toSet.length() == 0)                         toSet = null;                     ce.addEdit(new UndoableFieldChange(sel[i], fieldName, old, toSet));                     sel[i].setField(fieldName, toSet);                     entriesChanged++;                 }             }    */
block|}
name|progress
operator|+=
name|sel
operator|.
name|length
operator|*
name|weightAutoSet
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarValue
argument_list|(
name|progress
argument_list|)
expr_stmt|;
comment|//System.out.println("Done setting");
comment|// The following loop checks all external links that are already set.
if|if
condition|(
name|checkExisting
condition|)
block|{
name|boolean
name|removeAllBroken
init|=
literal|false
decl_stmt|;
name|mainLoop
label|:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|sel
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarValue
argument_list|(
name|progress
operator|++
argument_list|)
expr_stmt|;
specifier|final
name|String
name|old
init|=
name|sel
index|[
name|i
index|]
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
comment|// Check if a extension is set:
if|if
condition|(
operator|(
name|old
operator|!=
literal|null
operator|)
operator|&&
operator|!
name|old
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
name|FileListTableModel
name|tableModel
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|tableModel
operator|.
name|setContentDontGuessTypes
argument_list|(
name|old
argument_list|)
expr_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|tableModel
operator|.
name|getRowCount
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|FileListEntry
name|flEntry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|j
argument_list|)
decl_stmt|;
comment|// See if the link looks like an URL:
name|boolean
name|httpLink
init|=
name|flEntry
operator|.
name|getLink
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"http"
argument_list|)
decl_stmt|;
if|if
condition|(
name|httpLink
condition|)
continue|continue;
comment|// Don't check the remote file.
comment|// TODO: should there be an option to check remote links?
comment|// A variable to keep track of whether this link gets deleted:
name|boolean
name|deleted
init|=
literal|false
decl_stmt|;
comment|// Get an absolute path representation:
name|File
name|file
init|=
name|Util
operator|.
name|expandFilename
argument_list|(
name|flEntry
operator|.
name|getLink
argument_list|()
argument_list|,
name|dirsS
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|file
operator|==
literal|null
operator|)
operator|||
operator|!
name|file
operator|.
name|exists
argument_list|()
condition|)
block|{
name|int
name|answer
decl_stmt|;
if|if
condition|(
operator|!
name|removeAllBroken
condition|)
block|{
name|answer
operator|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"<HTML>Could not find file '%0'<BR>linked from entry '%1'</HTML>"
argument_list|,
operator|new
name|String
index|[]
block|{
name|flEntry
operator|.
name|getLink
argument_list|()
block|,
name|sel
index|[
name|i
index|]
operator|.
name|getCiteKey
argument_list|()
block|}
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Broken link"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
name|brokenLinkOptions
argument_list|,
name|brokenLinkOptions
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|answer
operator|=
literal|2
expr_stmt|;
comment|// We should delete this link.
block|}
switch|switch
condition|(
name|answer
condition|)
block|{
case|case
literal|1
case|:
comment|// Assign new file.
name|FileListEntryEditor
name|flEditor
init|=
operator|new
name|FileListEntryEditor
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|flEntry
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|panel
operator|.
name|metaData
argument_list|()
argument_list|)
decl_stmt|;
name|flEditor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
literal|2
case|:
comment|// Clear field:
name|tableModel
operator|.
name|removeEntry
argument_list|(
name|j
argument_list|)
expr_stmt|;
name|deleted
operator|=
literal|true
expr_stmt|;
comment|// Make sure we don't investigate this link further.
name|j
operator|--
expr_stmt|;
comment|// Step back in the iteration, because we removed an entry.
break|break;
case|case
literal|3
case|:
comment|// Clear field:
name|tableModel
operator|.
name|removeEntry
argument_list|(
name|j
argument_list|)
expr_stmt|;
name|deleted
operator|=
literal|true
expr_stmt|;
comment|// Make sure we don't investigate this link further.
name|j
operator|--
expr_stmt|;
comment|// Step back in the iteration, because we removed an entry.
name|removeAllBroken
operator|=
literal|true
expr_stmt|;
comment|// Notify for further cases.
break|break;
case|case
literal|4
case|:
comment|// Cancel
break|break
name|mainLoop
break|;
block|}
name|brokenLinks
operator|++
expr_stmt|;
block|}
comment|// Unless we deleted this link, see if its file type is recognized:
if|if
condition|(
operator|!
name|deleted
operator|&&
operator|(
name|flEntry
operator|.
name|getType
argument_list|()
operator|instanceof
name|UnknownExternalFileType
operator|)
condition|)
block|{
name|String
index|[]
name|options
init|=
operator|new
name|String
index|[]
block|{
name|Globals
operator|.
name|lang
argument_list|(
literal|"Define '%0'"
argument_list|,
name|flEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Change file type"
argument_list|)
block|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
block|}
decl_stmt|;
name|String
name|defOption
init|=
name|options
index|[
literal|0
index|]
decl_stmt|;
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showOptionDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"One or more file links are of the type '%0', which is undefined. What do you want to do?"
argument_list|,
name|flEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Undefined file type"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|YES_NO_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|,
literal|null
argument_list|,
name|options
argument_list|,
name|defOption
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
comment|// User doesn't want to handle this unknown link type.
block|}
elseif|else
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|YES_OPTION
condition|)
block|{
comment|// User wants to define the new file type. Show the dialog:
name|ExternalFileType
name|newType
init|=
operator|new
name|ExternalFileType
argument_list|(
name|flEntry
operator|.
name|getType
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|"new"
argument_list|)
decl_stmt|;
name|ExternalFileTypeEntryEditor
name|editor
init|=
operator|new
name|ExternalFileTypeEntryEditor
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|newType
argument_list|)
decl_stmt|;
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|editor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
comment|// Get the old list of types, add this one, and update the list in prefs:
name|java
operator|.
name|util
operator|.
name|List
argument_list|<
name|ExternalFileType
argument_list|>
name|fileTypes
init|=
operator|new
name|ArrayList
argument_list|<
name|ExternalFileType
argument_list|>
argument_list|()
decl_stmt|;
name|ExternalFileType
index|[]
name|oldTypes
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeSelection
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|k
init|=
literal|0
init|;
name|k
operator|<
name|oldTypes
operator|.
name|length
condition|;
name|k
operator|++
control|)
block|{
name|fileTypes
operator|.
name|add
argument_list|(
name|oldTypes
index|[
name|k
index|]
argument_list|)
expr_stmt|;
block|}
name|fileTypes
operator|.
name|add
argument_list|(
name|newType
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|setExternalFileTypes
argument_list|(
name|fileTypes
argument_list|)
expr_stmt|;
name|panel
operator|.
name|mainTable
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// User wants to change the type of this link.
comment|// First get a model of all file links for this entry:
name|FileListEntryEditor
name|editor
init|=
operator|new
name|FileListEntryEditor
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|flEntry
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|panel
operator|.
name|metaData
argument_list|()
argument_list|)
decl_stmt|;
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
operator|!
name|tableModel
operator|.
name|getStringRepresentation
argument_list|()
operator|.
name|equals
argument_list|(
name|old
argument_list|)
condition|)
block|{
comment|// The table has been modified. Store the change:
name|String
name|toSet
init|=
name|tableModel
operator|.
name|getStringRepresentation
argument_list|()
decl_stmt|;
if|if
condition|(
name|toSet
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
name|toSet
operator|=
literal|null
expr_stmt|;
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|sel
index|[
name|i
index|]
argument_list|,
name|fieldName
argument_list|,
name|old
argument_list|,
name|toSet
argument_list|)
argument_list|)
expr_stmt|;
name|sel
index|[
name|i
index|]
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|toSet
argument_list|)
expr_stmt|;
name|changedEntries
operator|.
name|add
argument_list|(
name|sel
index|[
name|i
index|]
argument_list|)
expr_stmt|;
comment|//System.out.println("Changed to: "+tableModel.getStringRepresentation());
block|}
block|}
block|}
block|}
name|entriesChangedCount
operator|=
name|changedEntries
operator|.
name|size
argument_list|()
expr_stmt|;
comment|//for (BibtexEntry entr : changedEntries)
comment|//    System.out.println(entr.getCiteKey());
if|if
condition|(
name|entriesChangedCount
operator|>
literal|0
condition|)
block|{
comment|// Add the undo edit:
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
block|}
block|}
DECL|method|update ()
specifier|public
name|void
name|update
parameter_list|()
block|{
if|if
condition|(
operator|!
name|goOn
condition|)
return|return;
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Finished synchronizing %0 links. Entries changed%c %1."
argument_list|,
operator|new
name|String
index|[]
block|{
name|fieldName
operator|.
name|toUpperCase
argument_list|()
block|,
name|String
operator|.
name|valueOf
argument_list|(
name|entriesChangedCount
argument_list|)
block|}
argument_list|)
argument_list|)
expr_stmt|;
name|panel
operator|.
name|frame
argument_list|()
operator|.
name|setProgressBarVisible
argument_list|(
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|entriesChangedCount
operator|>
literal|0
condition|)
block|{
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
DECL|class|OptionsDialog
specifier|static
class|class
name|OptionsDialog
extends|extends
name|JDialog
block|{
DECL|field|autoSetUnset
DECL|field|autoSetAll
DECL|field|autoSetNone
name|JRadioButton
name|autoSetUnset
decl_stmt|,
name|autoSetAll
decl_stmt|,
name|autoSetNone
decl_stmt|;
DECL|field|checkLinks
name|JCheckBox
name|checkLinks
decl_stmt|;
DECL|field|ok
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|cancel
name|cancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|description
name|JLabel
name|description
decl_stmt|;
DECL|field|canceled
specifier|private
name|boolean
name|canceled
init|=
literal|true
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|method|OptionsDialog (JFrame parent, MetaData metaData, String fieldName)
specifier|public
name|OptionsDialog
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|MetaData
name|metaData
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Synchronize %0 links"
argument_list|,
name|fieldName
operator|.
name|toUpperCase
argument_list|()
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|metaData
expr_stmt|;
specifier|final
name|String
name|fn
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"file"
argument_list|)
decl_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|canceled
operator|=
literal|false
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|Action
name|closeAction
init|=
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|closeAction
argument_list|)
expr_stmt|;
name|InputMap
name|im
init|=
name|cancel
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|ActionMap
name|am
init|=
name|cancel
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
name|autoSetUnset
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autoset %0 links. Do not overwrite existing links."
argument_list|,
name|fn
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|autoSetAll
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autoset %0 links. Allow overwriting existing links."
argument_list|,
name|fn
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|autoSetNone
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Do not autoset"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|checkLinks
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Check existing %0 links"
argument_list|,
name|fn
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
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
name|autoSetUnset
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|autoSetNone
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|autoSetAll
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|description
operator|=
operator|new
name|JLabel
argument_list|(
literal|"<HTML>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
comment|//"This function helps you keep your external %0 links up-to-date." +
literal|"Attempt to autoset %0 links for your entries. Autoset works if "
operator|+
literal|"a %0 file in your %0 directory or a subdirectory<BR>is named identically to an entry's BibTeX key, plus extension."
argument_list|,
name|fn
argument_list|)
operator|+
literal|"</HTML>"
argument_list|)
expr_stmt|;
comment|//            name.setVerticalAlignment(JLabel.TOP);
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Autoset"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoSetUnset
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoSetAll
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|autoSetNone
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Check links"
argument_list|)
argument_list|)
expr_stmt|;
name|description
operator|=
operator|new
name|JLabel
argument_list|(
literal|"<HTML>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"This makes JabRef look up each %0 extension and check if the file exists. If not, you will "
operator|+
literal|"be given options<BR>to resolve the problem."
argument_list|,
name|fn
argument_list|)
operator|+
literal|"</HTML>"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|checkLinks
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|()
expr_stmt|;
name|JPanel
name|main
init|=
name|builder
operator|.
name|getPanel
argument_list|()
decl_stmt|;
name|main
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|main
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|pack
argument_list|()
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|visible
condition|)
name|canceled
operator|=
literal|true
expr_stmt|;
name|String
index|[]
name|dirs
init|=
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
decl_stmt|;
if|if
condition|(
name|dirs
operator|.
name|length
operator|==
literal|0
condition|)
block|{
name|autoSetNone
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|autoSetNone
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|autoSetAll
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|autoSetUnset
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|autoSetNone
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|autoSetAll
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|autoSetUnset
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
operator|new
name|FocusRequester
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|super
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
DECL|method|canceled ()
specifier|public
name|boolean
name|canceled
parameter_list|()
block|{
return|return
name|canceled
return|;
block|}
block|}
block|}
end_class

end_unit

