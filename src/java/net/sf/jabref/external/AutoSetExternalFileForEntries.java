begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|Vector
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
name|builder
operator|.
name|ButtonBarBuilder
import|;
end_import

begin_comment
comment|/**  * This action goes through all selected entries in the BasePanel, and attempts to autoset the  * given external file (pdf, ps, ...) based on the same algorithm used for the "Auto" button in  * EntryEditor.  */
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
name|String
name|fieldName
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
literal|"Clear field"
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
DECL|field|overWriteAllowed
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
name|overWriteAllowed
init|=
literal|true
decl_stmt|,
name|checkExisting
init|=
literal|true
decl_stmt|;
DECL|field|skipped
DECL|field|entriesChanged
DECL|field|brokenLinks
specifier|private
name|int
name|skipped
init|=
literal|0
decl_stmt|,
name|entriesChanged
init|=
literal|0
decl_stmt|,
name|brokenLinks
init|=
literal|0
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
DECL|method|init ()
specifier|public
name|void
name|init
parameter_list|()
block|{
comment|// Get all entries, and make sure there are selected entries:
name|panel
operator|.
name|database
argument_list|()
operator|.
name|getEntries
argument_list|()
expr_stmt|;
name|sel
operator|=
operator|(
name|BibtexEntry
index|[]
operator|)
name|panel
operator|.
name|database
argument_list|()
operator|.
name|getEntries
argument_list|()
operator|.
name|toArray
argument_list|(
operator|new
name|BibtexEntry
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
if|if
condition|(
name|sel
operator|.
name|length
operator|<
literal|1
condition|)
block|{
name|goOn
operator|=
literal|false
expr_stmt|;
return|return;
block|}
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
return|return;
name|skipped
operator|=
literal|0
expr_stmt|;
name|entriesChanged
operator|=
literal|0
expr_stmt|;
name|brokenLinks
operator|=
literal|0
expr_stmt|;
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
name|dir
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
specifier|final
name|Object
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
comment|// Check if a link is already set, and if so, if we are allowed to overwrite it:
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
operator|&&
operator|!
name|overWriteAllowed
condition|)
continue|continue;
name|extPan
operator|.
name|setEntry
argument_list|(
name|sel
index|[
name|i
index|]
argument_list|)
expr_stmt|;
name|editor
operator|.
name|setText
argument_list|(
operator|(
name|old
operator|!=
literal|null
operator|)
condition|?
operator|(
name|String
operator|)
name|old
else|:
literal|""
argument_list|)
expr_stmt|;
name|Thread
name|t
init|=
name|extPan
operator|.
name|autoSetFile
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|)
decl_stmt|;
comment|// Wait for the autoset process to finish:
if|if
condition|(
name|t
operator|!=
literal|null
condition|)
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
name|sel
index|[
name|i
index|]
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
name|sel
index|[
name|i
index|]
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
specifier|final
name|Object
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
comment|// Check if a link is set:
if|if
condition|(
operator|(
name|old
operator|!=
literal|null
operator|)
operator|&&
operator|!
operator|(
operator|(
name|String
operator|)
name|old
operator|)
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
name|Util
operator|.
name|expandFilename
argument_list|(
operator|(
name|String
operator|)
name|old
argument_list|,
name|dir
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
literal|"<HTML>Could not find file '%0'<BR>linked from entry '%1'</HTML>"
argument_list|,
operator|new
name|String
index|[]
block|{
operator|(
name|String
operator|)
name|old
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
name|sel
index|[
name|i
index|]
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
name|sel
index|[
name|i
index|]
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
name|sel
index|[
name|i
index|]
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
name|sel
index|[
name|i
index|]
argument_list|,
name|fieldName
argument_list|,
name|old
argument_list|,
literal|null
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
continue|continue;
block|}
block|}
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
name|entriesChanged
argument_list|)
block|}
argument_list|)
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
DECL|field|fieldName
specifier|private
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
name|fieldName
operator|=
name|fieldName
operator|.
name|toUpperCase
argument_list|()
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
comment|//            description.setVerticalAlignment(JLabel.TOP);
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
literal|"This makes JabRef look up each %0 link and check if the file exists. If not, you will "
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
name|dir
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|fieldName
operator|+
literal|"Directory"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|dir
operator|==
literal|null
operator|)
operator|||
operator|(
name|dir
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
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

