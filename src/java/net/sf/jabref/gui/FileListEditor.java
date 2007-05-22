begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|external
operator|.
name|ExternalFileType
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
name|external
operator|.
name|DownloadExternalFile
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
name|*
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
name|beans
operator|.
name|PropertyChangeListener
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

begin_comment
comment|/**  * Created by Morten O. Alver 2007.02.22  */
end_comment

begin_class
DECL|class|FileListEditor
specifier|public
class|class
name|FileListEditor
extends|extends
name|JTable
implements|implements
name|FieldEditor
implements|,
name|DownloadExternalFile
operator|.
name|DownloadCallback
block|{
DECL|field|label
name|FieldNameLabel
name|label
decl_stmt|;
DECL|field|editor
name|FileListEntryEditor
name|editor
init|=
literal|null
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|fieldName
specifier|private
name|String
name|fieldName
decl_stmt|;
DECL|field|entryEditor
specifier|private
name|EntryEditor
name|entryEditor
decl_stmt|;
DECL|field|panel
specifier|private
name|JPanel
name|panel
decl_stmt|;
DECL|field|tableModel
specifier|private
name|FileListTableModel
name|tableModel
decl_stmt|;
DECL|field|sPane
specifier|private
name|JScrollPane
name|sPane
decl_stmt|;
DECL|field|add
DECL|field|remove
DECL|field|up
DECL|field|down
DECL|field|auto
DECL|field|download
specifier|private
name|JButton
name|add
decl_stmt|,
name|remove
decl_stmt|,
name|up
decl_stmt|,
name|down
decl_stmt|,
name|auto
decl_stmt|,
name|download
decl_stmt|;
DECL|method|FileListEditor (JabRefFrame frame, String fieldName, String content, EntryEditor entryEditor)
specifier|public
name|FileListEditor
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|String
name|content
parameter_list|,
name|EntryEditor
name|entryEditor
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
name|fieldName
operator|=
name|fieldName
expr_stmt|;
name|this
operator|.
name|entryEditor
operator|=
name|entryEditor
expr_stmt|;
name|label
operator|=
operator|new
name|FieldNameLabel
argument_list|(
literal|" "
operator|+
name|Util
operator|.
name|nCase
argument_list|(
name|fieldName
argument_list|)
operator|+
literal|" "
argument_list|)
expr_stmt|;
name|tableModel
operator|=
operator|new
name|FileListTableModel
argument_list|()
expr_stmt|;
name|setText
argument_list|(
name|content
argument_list|)
expr_stmt|;
name|setModel
argument_list|(
name|tableModel
argument_list|)
expr_stmt|;
name|sPane
operator|=
operator|new
name|JScrollPane
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|setTableHeader
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|addMouseListener
argument_list|(
operator|new
name|TableClickListener
argument_list|()
argument_list|)
expr_stmt|;
name|add
operator|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"add"
argument_list|)
argument_list|)
expr_stmt|;
name|remove
operator|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"remove"
argument_list|)
argument_list|)
expr_stmt|;
name|up
operator|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"up"
argument_list|)
argument_list|)
expr_stmt|;
name|down
operator|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"down"
argument_list|)
argument_list|)
expr_stmt|;
name|auto
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Auto"
argument_list|)
argument_list|)
expr_stmt|;
name|download
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download"
argument_list|)
argument_list|)
expr_stmt|;
name|add
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|up
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|down
operator|.
name|setMargin
argument_list|(
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|add
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
name|addEntry
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|remove
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
name|removeEntries
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|up
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
name|moveEntry
argument_list|(
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|down
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
name|moveEntry
argument_list|(
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|auto
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
name|autoSetLinks
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|download
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
name|downloadFile
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"fill:pref,1dlu,fill:pref,1dlu,fill:pref"
argument_list|,
literal|"fill:pref,fill:pref"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|up
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|add
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|auto
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|down
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|download
argument_list|)
expr_stmt|;
name|panel
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|panel
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|sPane
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|panel
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
comment|// Add an input/action pair for deleting entries:
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|"DELETE"
argument_list|)
argument_list|,
literal|"delete"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"delete"
argument_list|,
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|int
name|row
init|=
name|getSelectedRow
argument_list|()
decl_stmt|;
name|removeEntries
argument_list|()
expr_stmt|;
name|row
operator|=
name|Math
operator|.
name|min
argument_list|(
name|row
argument_list|,
name|getRowCount
argument_list|()
operator|-
literal|1
argument_list|)
expr_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
name|setRowSelectionInterval
argument_list|(
name|row
argument_list|,
name|row
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|// Add an input/action pair for inserting an entry:
name|getInputMap
argument_list|()
operator|.
name|put
argument_list|(
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
literal|"INSERT"
argument_list|)
argument_list|,
literal|"insert"
argument_list|)
expr_stmt|;
name|getActionMap
argument_list|()
operator|.
name|put
argument_list|(
literal|"insert"
argument_list|,
operator|new
name|AbstractAction
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|addEntry
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|getFieldName ()
specifier|public
name|String
name|getFieldName
parameter_list|()
block|{
return|return
name|fieldName
return|;
block|}
comment|/*       * Returns the component to be added to a container. Might be a JScrollPane     * or the component itself.     */
DECL|method|getPane ()
specifier|public
name|JComponent
name|getPane
parameter_list|()
block|{
return|return
name|panel
return|;
block|}
comment|/*      * Returns the text component itself.     */
DECL|method|getTextComponent ()
specifier|public
name|JComponent
name|getTextComponent
parameter_list|()
block|{
return|return
name|this
return|;
block|}
DECL|method|getLabel ()
specifier|public
name|JLabel
name|getLabel
parameter_list|()
block|{
return|return
name|label
return|;
block|}
DECL|method|setLabelColor (Color c)
specifier|public
name|void
name|setLabelColor
parameter_list|(
name|Color
name|c
parameter_list|)
block|{
name|label
operator|.
name|setForeground
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|getText ()
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|tableModel
operator|.
name|getStringRepresentation
argument_list|()
return|;
block|}
DECL|method|setText (String newText)
specifier|public
name|void
name|setText
parameter_list|(
name|String
name|newText
parameter_list|)
block|{
name|tableModel
operator|.
name|setContent
argument_list|(
name|newText
argument_list|)
expr_stmt|;
block|}
DECL|method|append (String text)
specifier|public
name|void
name|append
parameter_list|(
name|String
name|text
parameter_list|)
block|{      }
DECL|method|updateFont ()
specifier|public
name|void
name|updateFont
parameter_list|()
block|{      }
DECL|method|paste (String textToInsert)
specifier|public
name|void
name|paste
parameter_list|(
name|String
name|textToInsert
parameter_list|)
block|{      }
DECL|method|getSelectedText ()
specifier|public
name|String
name|getSelectedText
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
DECL|method|addEntry ()
specifier|private
name|void
name|addEntry
parameter_list|()
block|{
name|int
name|row
init|=
name|getSelectedRow
argument_list|()
decl_stmt|;
if|if
condition|(
name|row
operator|==
operator|-
literal|1
condition|)
name|row
operator|=
literal|0
expr_stmt|;
name|FileListEntry
name|entry
init|=
operator|new
name|FileListEntry
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|null
argument_list|)
decl_stmt|;
if|if
condition|(
name|editListEntry
argument_list|(
name|entry
argument_list|)
condition|)
name|tableModel
operator|.
name|addEntry
argument_list|(
name|row
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|removeEntries ()
specifier|private
name|void
name|removeEntries
parameter_list|()
block|{
name|int
index|[]
name|rows
init|=
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
name|rows
operator|!=
literal|null
condition|)
for|for
control|(
name|int
name|i
init|=
name|rows
operator|.
name|length
operator|-
literal|1
init|;
name|i
operator|>=
literal|0
condition|;
name|i
operator|--
control|)
block|{
name|tableModel
operator|.
name|removeEntry
argument_list|(
name|rows
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|method|moveEntry (int i)
specifier|private
name|void
name|moveEntry
parameter_list|(
name|int
name|i
parameter_list|)
block|{
name|int
index|[]
name|sel
init|=
name|getSelectedRows
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|sel
operator|.
name|length
operator|!=
literal|1
operator|)
operator|||
operator|(
name|tableModel
operator|.
name|getRowCount
argument_list|()
operator|<
literal|2
operator|)
condition|)
return|return;
name|int
name|toIdx
init|=
name|sel
index|[
literal|0
index|]
operator|+
name|i
decl_stmt|;
if|if
condition|(
name|toIdx
operator|>=
name|tableModel
operator|.
name|getRowCount
argument_list|()
condition|)
name|toIdx
operator|-=
name|tableModel
operator|.
name|getRowCount
argument_list|()
expr_stmt|;
if|if
condition|(
name|toIdx
operator|<
literal|0
condition|)
name|toIdx
operator|+=
name|tableModel
operator|.
name|getRowCount
argument_list|()
expr_stmt|;
name|FileListEntry
name|entry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|sel
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
name|tableModel
operator|.
name|removeEntry
argument_list|(
name|sel
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|tableModel
operator|.
name|addEntry
argument_list|(
name|toIdx
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
name|setRowSelectionInterval
argument_list|(
name|toIdx
argument_list|,
name|toIdx
argument_list|)
expr_stmt|;
block|}
DECL|method|editListEntry (FileListEntry entry)
specifier|private
name|boolean
name|editListEntry
parameter_list|(
name|FileListEntry
name|entry
parameter_list|)
block|{
if|if
condition|(
name|editor
operator|==
literal|null
condition|)
block|{
name|editor
operator|=
operator|new
name|FileListEntryEditor
argument_list|(
name|frame
argument_list|,
name|entry
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
else|else
name|editor
operator|.
name|setEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
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
name|tableModel
operator|.
name|fireTableDataChanged
argument_list|()
expr_stmt|;
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
return|return
name|editor
operator|.
name|okPressed
argument_list|()
return|;
block|}
DECL|method|autoSetLinks ()
specifier|private
name|void
name|autoSetLinks
parameter_list|()
block|{
name|BibtexEntry
name|entry
init|=
name|entryEditor
operator|.
name|getEntry
argument_list|()
decl_stmt|;
name|String
name|field
init|=
literal|null
decl_stmt|;
name|boolean
name|foundAny
init|=
literal|false
decl_stmt|;
name|ExternalFileType
index|[]
name|types
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getExternalFileTypeSelection
argument_list|()
decl_stmt|;
name|ArrayList
name|dirs
init|=
operator|new
name|ArrayList
argument_list|()
decl_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|)
condition|)
name|dirs
operator|.
name|add
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|)
argument_list|)
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
name|types
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|ExternalFileType
name|type
init|=
name|types
index|[
name|i
index|]
decl_stmt|;
comment|//System.out.println("Looking for "+type.getName());
name|String
name|found
init|=
name|Util
operator|.
name|findFile
argument_list|(
name|entry
argument_list|,
name|type
argument_list|,
name|dirs
argument_list|)
decl_stmt|;
if|if
condition|(
name|found
operator|!=
literal|null
condition|)
block|{
comment|//System.out.println("Found: "+found);
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|found
argument_list|)
decl_stmt|;
name|boolean
name|alreadyHas
init|=
literal|false
decl_stmt|;
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
name|existingEntry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|j
argument_list|)
decl_stmt|;
if|if
condition|(
operator|new
name|File
argument_list|(
name|existingEntry
operator|.
name|getLink
argument_list|()
argument_list|)
operator|.
name|equals
argument_list|(
name|f
argument_list|)
condition|)
block|{
name|alreadyHas
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|alreadyHas
condition|)
block|{
name|FileListEntry
name|flEntry
init|=
operator|new
name|FileListEntry
argument_list|(
name|f
operator|.
name|getName
argument_list|()
argument_list|,
name|found
argument_list|,
name|type
argument_list|)
decl_stmt|;
name|tableModel
operator|.
name|addEntry
argument_list|(
name|tableModel
operator|.
name|getRowCount
argument_list|()
argument_list|,
name|flEntry
argument_list|)
expr_stmt|;
name|foundAny
operator|=
literal|true
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|foundAny
condition|)
block|{
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Run a file download operation.      */
DECL|method|downloadFile ()
specifier|private
name|void
name|downloadFile
parameter_list|()
block|{
name|String
name|bibtexKey
init|=
name|entryEditor
operator|.
name|getEntry
argument_list|()
operator|.
name|getCiteKey
argument_list|()
decl_stmt|;
if|if
condition|(
name|bibtexKey
operator|==
literal|null
condition|)
block|{
name|int
name|answer
init|=
name|JOptionPane
operator|.
name|showConfirmDialog
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"This entry has no BibTeX key. Generate key now?"
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download file"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|OK_CANCEL_OPTION
argument_list|,
name|JOptionPane
operator|.
name|QUESTION_MESSAGE
argument_list|)
decl_stmt|;
if|if
condition|(
name|answer
operator|==
name|JOptionPane
operator|.
name|OK_OPTION
condition|)
block|{
name|ActionListener
name|l
init|=
name|entryEditor
operator|.
name|generateKeyAction
decl_stmt|;
name|l
operator|.
name|actionPerformed
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|bibtexKey
operator|=
name|entryEditor
operator|.
name|getEntry
argument_list|()
operator|.
name|getCiteKey
argument_list|()
expr_stmt|;
block|}
block|}
name|DownloadExternalFile
name|def
init|=
operator|new
name|DownloadExternalFile
argument_list|(
name|frame
argument_list|,
name|frame
operator|.
name|basePanel
argument_list|()
operator|.
name|metaData
argument_list|()
argument_list|,
name|bibtexKey
argument_list|)
decl_stmt|;
try|try
block|{
name|def
operator|.
name|download
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * This is the callback method that the DownloadExternalFile class uses to report the result      * of a download operation. This call may never come, if the user cancelled the operation.      * @param file The FileListEntry linking to the resulting local file.      */
DECL|method|downloadComplete (FileListEntry file)
specifier|public
name|void
name|downloadComplete
parameter_list|(
name|FileListEntry
name|file
parameter_list|)
block|{
name|tableModel
operator|.
name|addEntry
argument_list|(
name|tableModel
operator|.
name|getRowCount
argument_list|()
argument_list|,
name|file
argument_list|)
expr_stmt|;
name|entryEditor
operator|.
name|updateField
argument_list|(
name|this
argument_list|)
expr_stmt|;
block|}
DECL|class|TableClickListener
class|class
name|TableClickListener
extends|extends
name|MouseAdapter
block|{
DECL|method|mouseClicked (MouseEvent e)
specifier|public
name|void
name|mouseClicked
parameter_list|(
name|MouseEvent
name|e
parameter_list|)
block|{
if|if
condition|(
operator|(
name|e
operator|.
name|getButton
argument_list|()
operator|==
name|MouseEvent
operator|.
name|BUTTON1
operator|)
operator|&&
operator|(
name|e
operator|.
name|getClickCount
argument_list|()
operator|==
literal|2
operator|)
condition|)
block|{
name|int
name|row
init|=
name|rowAtPoint
argument_list|(
name|e
operator|.
name|getPoint
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|row
operator|>=
literal|0
condition|)
block|{
name|FileListEntry
name|entry
init|=
name|tableModel
operator|.
name|getEntry
argument_list|(
name|row
argument_list|)
decl_stmt|;
name|editListEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
block|}
end_class

end_unit

