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
name|Collection
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
name|FocusRequester
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
name|OpenFileFilter
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
operator|.
name|FieldTextField
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
name|entry
operator|.
name|BibtexEntry
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
name|AttachFileDialog
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
name|UndoableFieldChange
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
comment|/**  * This action goes through all selected entries in the BasePanel, and attempts to autoset the given external file (pdf,  * ps, ...) based on the same algorithm used for the "Auto" button in EntryEditor.  */
end_comment

begin_class
DECL|class|AutoSetExternalFileForEntries
specifier|public
class|class
name|AutoSetExternalFileForEntries
extends|extends
name|AbstractWorker
block|{
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|field|sel
specifier|private
name|BibtexEntry
index|[]
name|sel
decl_stmt|;
DECL|field|optDiag
specifier|private
name|OptionsDialog
name|optDiag
decl_stmt|;
DECL|field|log
specifier|private
specifier|static
specifier|final
name|Log
name|log
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|AutoSetExternalFileForEntries
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|brokenLinkOptions
specifier|private
specifier|final
name|Object
index|[]
name|brokenLinkOptions
init|=
block|{
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ignore"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Assign new file"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Clear field"
argument_list|)
block|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Quit synchronization"
argument_list|)
block|}
decl_stmt|;
DECL|field|goOn
specifier|private
name|boolean
name|goOn
init|=
literal|true
decl_stmt|;
DECL|field|autoSet
specifier|private
name|boolean
name|autoSet
init|=
literal|true
decl_stmt|;
DECL|field|overWriteAllowed
specifier|private
name|boolean
name|overWriteAllowed
init|=
literal|true
decl_stmt|;
DECL|field|checkExisting
specifier|private
name|boolean
name|checkExisting
init|=
literal|true
decl_stmt|;
DECL|field|entriesChanged
specifier|private
name|int
name|entriesChanged
decl_stmt|;
DECL|method|AutoSetExternalFileForEntries (BasePanel panel, String fieldName)
specifier|public
name|AutoSetExternalFileForEntries
parameter_list|(
name|BasePanel
name|panel
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|this
operator|.
name|fieldName
operator|=
name|fieldName
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
name|sel
operator|=
name|col
operator|.
name|toArray
argument_list|(
operator|new
name|BibtexEntry
index|[
name|col
operator|.
name|size
argument_list|()
index|]
argument_list|)
expr_stmt|;
comment|// Ask about rules for the operation:
if|if
condition|(
name|optDiag
operator|==
literal|null
condition|)
block|{
name|optDiag
operator|=
operator|new
name|OptionsDialog
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|fieldName
argument_list|)
expr_stmt|;
block|}
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
name|overWriteAllowed
operator|=
name|optDiag
operator|.
name|autoSetAll
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
name|Localization
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
annotation|@
name|Override
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
name|Localization
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
name|entriesChanged
operator|=
literal|0
expr_stmt|;
name|int
name|brokenLinks
init|=
literal|0
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
literal|"Autoset %0 field"
argument_list|,
name|fieldName
argument_list|)
argument_list|)
decl_stmt|;
specifier|final
name|OpenFileFilter
name|off
init|=
name|Util
operator|.
name|getFileFilterForField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
name|ExternalFilePanel
name|extPan
init|=
operator|new
name|ExternalFilePanel
argument_list|(
name|fieldName
argument_list|,
name|panel
operator|.
name|metaData
argument_list|()
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|off
argument_list|)
decl_stmt|;
name|FieldTextField
name|editor
init|=
operator|new
name|FieldTextField
argument_list|(
name|fieldName
argument_list|,
literal|""
argument_list|,
literal|false
argument_list|)
decl_stmt|;
comment|// Find the default directory for this field type:
name|String
index|[]
name|dirs
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
comment|// First we try to autoset fields
if|if
condition|(
name|autoSet
condition|)
block|{
for|for
control|(
name|BibtexEntry
name|aSel
range|:
name|sel
control|)
block|{
name|progress
operator|+=
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
specifier|final
name|String
name|old
init|=
name|aSel
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
comment|// Check if a extension is already set, and if so, if we are allowed to overwrite it:
if|if
condition|(
name|old
operator|!=
literal|null
operator|&&
operator|!
name|old
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|&&
operator|!
name|overWriteAllowed
condition|)
block|{
continue|continue;
block|}
name|extPan
operator|.
name|setEntry
argument_list|(
name|aSel
argument_list|,
name|panel
operator|.
name|getDatabase
argument_list|()
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setText
argument_list|(
name|old
operator|!=
literal|null
condition|?
name|old
else|:
literal|""
argument_list|)
expr_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeAndWait
argument_list|(
name|extPan
operator|.
name|autoSetFile
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|)
argument_list|)
expr_stmt|;
comment|// If something was found, entriesChanged it:
if|if
condition|(
operator|!
name|editor
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
operator|&&
operator|!
name|editor
operator|.
name|getText
argument_list|()
operator|.
name|equals
argument_list|(
name|old
argument_list|)
condition|)
block|{
comment|// Store an undo edit:
comment|//System.out.println("Setting: "+sel[i].getCiteKey()+" "+editor.getText());
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|aSel
argument_list|,
name|fieldName
argument_list|,
name|old
argument_list|,
name|editor
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|aSel
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|editor
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|entriesChanged
operator|++
expr_stmt|;
block|}
block|}
block|}
comment|//System.out.println("Done setting");
comment|// The following loop checks all external links that are already set.
if|if
condition|(
name|checkExisting
condition|)
block|{
name|mainLoop
label|:
for|for
control|(
name|BibtexEntry
name|aSel
range|:
name|sel
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
name|aSel
operator|.
name|getField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
comment|// Check if a extension is set:
if|if
condition|(
name|old
operator|!=
literal|null
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
comment|// Get an absolute path representation:
name|File
name|file
init|=
name|FileUtil
operator|.
name|expandFilename
argument_list|(
name|old
argument_list|,
name|dirs
argument_list|)
decl_stmt|;
if|if
condition|(
name|file
operator|==
literal|null
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"<HTML>Could not find file '%0'<BR>linked from entry '%1'</HTML>"
argument_list|,
operator|new
name|String
index|[]
block|{
name|old
block|,
name|aSel
operator|.
name|getCiteKey
argument_list|()
block|}
argument_list|)
argument_list|,
name|Localization
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
decl_stmt|;
switch|switch
condition|(
name|answer
condition|)
block|{
case|case
literal|1
case|:
comment|// Assign new file.
name|AttachFileDialog
name|afd
init|=
operator|new
name|AttachFileDialog
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
name|aSel
argument_list|,
name|fieldName
argument_list|)
decl_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|afd
argument_list|,
name|panel
operator|.
name|frame
argument_list|()
argument_list|)
expr_stmt|;
name|afd
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|afd
operator|.
name|cancelled
argument_list|()
condition|)
block|{
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|aSel
argument_list|,
name|fieldName
argument_list|,
name|old
argument_list|,
name|afd
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|aSel
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|afd
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|entriesChanged
operator|++
expr_stmt|;
block|}
break|break;
case|case
literal|2
case|:
comment|// Clear field
name|ce
operator|.
name|addEdit
argument_list|(
operator|new
name|UndoableFieldChange
argument_list|(
name|aSel
argument_list|,
name|fieldName
argument_list|,
name|old
argument_list|,
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|aSel
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|entriesChanged
operator|++
expr_stmt|;
break|break;
case|case
literal|3
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
block|}
block|}
block|}
comment|//log brokenLinks if some were found
if|if
condition|(
name|brokenLinks
operator|>
literal|0
condition|)
block|{
name|log
operator|.
name|warn
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Found %0 broken links"
argument_list|,
name|brokenLinks
operator|+
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|entriesChanged
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
annotation|@
name|Override
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
block|{
return|return;
block|}
name|panel
operator|.
name|output
argument_list|(
name|Localization
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
name|entriesChanged
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
name|entriesChanged
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
class|class
name|OptionsDialog
extends|extends
name|JDialog
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
DECL|field|autoSetUnset
specifier|final
name|JRadioButton
name|autoSetUnset
decl_stmt|;
DECL|field|autoSetAll
specifier|final
name|JRadioButton
name|autoSetAll
decl_stmt|;
DECL|field|autoSetNone
specifier|final
name|JRadioButton
name|autoSetNone
decl_stmt|;
DECL|field|checkLinks
specifier|final
name|JCheckBox
name|checkLinks
decl_stmt|;
DECL|field|ok
specifier|final
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|cancel
specifier|final
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
DECL|field|fieldName
specifier|private
specifier|final
name|String
name|fieldName
decl_stmt|;
DECL|method|OptionsDialog (JFrame parent, String fieldName)
specifier|public
name|OptionsDialog
parameter_list|(
name|JFrame
name|parent
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Localization
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
specifier|final
name|String
name|fn
init|=
name|fieldName
operator|.
name|toUpperCase
argument_list|()
decl_stmt|;
name|this
operator|.
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|ok
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
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
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
name|Localization
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
name|Localization
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
name|Localization
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
name|Localization
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
name|Localization
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
name|Localization
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
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"This makes JabRef look up each %0 link and check if the file exists. If not, you will be given options<BR>to resolve the problem."
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
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
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
annotation|@
name|Override
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
block|{
name|canceled
operator|=
literal|true
expr_stmt|;
block|}
name|String
index|[]
name|dirs
init|=
name|panel
operator|.
name|metaData
argument_list|()
operator|.
name|getFileDirectory
argument_list|(
name|fieldName
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

