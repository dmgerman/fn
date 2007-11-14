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
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|dnd
operator|.
name|DnDConstants
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|dnd
operator|.
name|DropTarget
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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|MalformedURLException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|net
operator|.
name|URL
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedList
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
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
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|xml
operator|.
name|transform
operator|.
name|TransformerException
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
name|BibtexFields
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
name|EntryEditor
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
name|FieldEditor
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
name|UrlDragDrop
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
name|Util
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
name|net
operator|.
name|URLDownload
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
name|XMPUtil
import|;
end_import

begin_comment
comment|/**  * Initial Version:  *   * @author alver  * @version Date: May 7, 2005 Time: 7:17:42 PM  *   * Current Version:  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|ExternalFilePanel
specifier|public
class|class
name|ExternalFilePanel
extends|extends
name|JPanel
block|{
DECL|field|serialVersionUID
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|3653290879640642718L
decl_stmt|;
DECL|field|browseBut
DECL|field|download
DECL|field|auto
DECL|field|xmp
specifier|private
name|JButton
name|browseBut
decl_stmt|,
name|download
decl_stmt|,
name|auto
decl_stmt|,
name|xmp
decl_stmt|;
DECL|field|entryEditor
specifier|private
name|EntryEditor
name|entryEditor
decl_stmt|;
DECL|field|frame
specifier|private
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|off
specifier|private
name|OpenFileFilter
name|off
decl_stmt|;
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
DECL|field|database
specifier|private
name|BibtexDatabase
name|database
decl_stmt|;
DECL|field|metaData
specifier|private
name|MetaData
name|metaData
decl_stmt|;
DECL|method|ExternalFilePanel (final String fieldName, final MetaData metaData, final BibtexEntry entry, final FieldEditor editor, final OpenFileFilter off)
specifier|public
name|ExternalFilePanel
parameter_list|(
specifier|final
name|String
name|fieldName
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
specifier|final
name|BibtexEntry
name|entry
parameter_list|,
specifier|final
name|FieldEditor
name|editor
parameter_list|,
specifier|final
name|OpenFileFilter
name|off
parameter_list|)
block|{
name|this
argument_list|(
literal|null
argument_list|,
name|metaData
argument_list|,
literal|null
argument_list|,
name|fieldName
argument_list|,
name|off
argument_list|,
name|editor
argument_list|)
expr_stmt|;
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|entryEditor
operator|=
literal|null
expr_stmt|;
block|}
DECL|method|ExternalFilePanel (final JabRefFrame frame, final MetaData metaData, final EntryEditor entryEditor, final String fieldName, final OpenFileFilter off, final FieldEditor editor)
specifier|public
name|ExternalFilePanel
parameter_list|(
specifier|final
name|JabRefFrame
name|frame
parameter_list|,
specifier|final
name|MetaData
name|metaData
parameter_list|,
specifier|final
name|EntryEditor
name|entryEditor
parameter_list|,
specifier|final
name|String
name|fieldName
parameter_list|,
specifier|final
name|OpenFileFilter
name|off
parameter_list|,
specifier|final
name|FieldEditor
name|editor
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
name|metaData
operator|=
name|metaData
expr_stmt|;
name|this
operator|.
name|off
operator|=
name|off
expr_stmt|;
name|this
operator|.
name|entryEditor
operator|=
name|entryEditor
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|GridLayout
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|browseBut
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
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
name|xmp
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Write XMP"
argument_list|)
argument_list|)
expr_stmt|;
name|xmp
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Write BibtexEntry as XMP-metadata to PDF."
argument_list|)
argument_list|)
expr_stmt|;
name|browseBut
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
name|browseFile
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|)
expr_stmt|;
comment|// editor.setText(chosenValue);
name|entryEditor
operator|.
name|storeFieldAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|editor
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
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
name|downLoadFile
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|,
name|frame
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
name|autoSetFile
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|xmp
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
name|pushXMP
argument_list|(
name|fieldName
argument_list|,
name|editor
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|browseBut
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|download
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|auto
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|xmp
argument_list|)
expr_stmt|;
comment|// Add drag and drop support to the field
if|if
condition|(
name|editor
operator|!=
literal|null
condition|)
operator|(
operator|(
name|JComponent
operator|)
name|editor
operator|)
operator|.
name|setDropTarget
argument_list|(
operator|new
name|DropTarget
argument_list|(
operator|(
name|Component
operator|)
name|editor
argument_list|,
name|DnDConstants
operator|.
name|ACTION_NONE
argument_list|,
operator|new
name|UrlDragDrop
argument_list|(
name|entryEditor
argument_list|,
name|frame
argument_list|,
name|editor
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/** 	 * Change which entry this panel is operating on. This is used only when 	 * this panel is not attached to an entry editor. 	 */
DECL|method|setEntry (BibtexEntry entry, BibtexDatabase database)
specifier|public
name|void
name|setEntry
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|,
name|BibtexDatabase
name|database
parameter_list|)
block|{
name|this
operator|.
name|entry
operator|=
name|entry
expr_stmt|;
name|this
operator|.
name|database
operator|=
name|database
expr_stmt|;
block|}
DECL|method|getDatabase ()
specifier|public
name|BibtexDatabase
name|getDatabase
parameter_list|()
block|{
return|return
operator|(
name|database
operator|!=
literal|null
condition|?
name|database
else|:
name|entryEditor
operator|.
name|getDatabase
argument_list|()
operator|)
return|;
block|}
DECL|method|getEntry ()
specifier|public
name|BibtexEntry
name|getEntry
parameter_list|()
block|{
return|return
operator|(
name|entry
operator|!=
literal|null
condition|?
name|entry
else|:
name|entryEditor
operator|.
name|getEntry
argument_list|()
operator|)
return|;
block|}
DECL|method|getKey ()
specifier|protected
name|Object
name|getKey
parameter_list|()
block|{
return|return
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|)
return|;
block|}
DECL|method|output (String s)
specifier|protected
name|void
name|output
parameter_list|(
name|String
name|s
parameter_list|)
block|{
if|if
condition|(
name|frame
operator|!=
literal|null
condition|)
name|frame
operator|.
name|output
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
DECL|method|pushXMP (final String fieldName, final FieldEditor editor)
specifier|public
name|void
name|pushXMP
parameter_list|(
specifier|final
name|String
name|fieldName
parameter_list|,
specifier|final
name|FieldEditor
name|editor
parameter_list|)
block|{
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Looking for pdf..."
argument_list|)
argument_list|)
expr_stmt|;
comment|// Find the default directory for this field type, if any:
name|String
name|dir
init|=
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
name|File
name|file
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|dir
operator|!=
literal|null
condition|)
block|{
name|File
name|tmp
init|=
name|Util
operator|.
name|expandFilename
argument_list|(
name|editor
operator|.
name|getText
argument_list|()
argument_list|,
operator|new
name|String
index|[]
block|{
name|dir
block|,
literal|"."
block|}
argument_list|)
decl_stmt|;
if|if
condition|(
name|tmp
operator|!=
literal|null
condition|)
name|file
operator|=
name|tmp
expr_stmt|;
block|}
if|if
condition|(
name|file
operator|==
literal|null
condition|)
block|{
name|file
operator|=
operator|new
name|File
argument_list|(
name|editor
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|final
name|File
name|finalFile
init|=
name|file
decl_stmt|;
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Writing XMP to '%0'..."
argument_list|,
name|finalFile
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
try|try
block|{
name|XMPUtil
operator|.
name|writeXMP
argument_list|(
name|finalFile
argument_list|,
name|getEntry
argument_list|()
argument_list|,
name|getDatabase
argument_list|()
argument_list|)
expr_stmt|;
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Wrote XMP to '%0'."
argument_list|,
name|finalFile
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|editor
operator|.
name|getParent
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error writing XMP to file: %0"
argument_list|,
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Writing XMP"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|logger
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error writing XMP to file: %0"
argument_list|,
name|finalFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error writing XMP to file: %0"
argument_list|,
name|finalFile
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|TransformerException
name|e
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|editor
operator|.
name|getParent
argument_list|()
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error converting Bibtex to XMP: %0"
argument_list|,
name|e
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Writing XMP"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|logger
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error while converting BibtexEntry to XMP %0"
argument_list|,
name|finalFile
operator|.
name|getAbsolutePath
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error converting XMP to '%0'..."
argument_list|,
name|finalFile
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
DECL|method|browseFile (final String fieldName, final FieldEditor editor)
specifier|public
name|void
name|browseFile
parameter_list|(
specifier|final
name|String
name|fieldName
parameter_list|,
specifier|final
name|FieldEditor
name|editor
parameter_list|)
block|{
name|String
name|directory
init|=
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|directory
operator|!=
literal|null
operator|)
operator|&&
name|directory
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
name|directory
operator|=
literal|null
expr_stmt|;
name|String
name|dir
init|=
name|editor
operator|.
name|getText
argument_list|()
decl_stmt|,
name|retVal
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|(
name|directory
operator|==
literal|null
operator|)
operator|||
operator|!
operator|(
operator|new
name|File
argument_list|(
name|dir
argument_list|)
operator|)
operator|.
name|isAbsolute
argument_list|()
condition|)
block|{
if|if
condition|(
name|directory
operator|!=
literal|null
condition|)
name|dir
operator|=
name|directory
expr_stmt|;
else|else
name|dir
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|fieldName
operator|+
name|Globals
operator|.
name|FILETYPE_PREFS_EXT
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
name|String
name|chosenFile
init|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
operator|new
name|File
argument_list|(
name|dir
argument_list|)
argument_list|,
literal|"."
operator|+
name|fieldName
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
decl_stmt|;
if|if
condition|(
name|chosenFile
operator|!=
literal|null
condition|)
block|{
name|File
name|newFile
init|=
operator|new
name|File
argument_list|(
name|chosenFile
argument_list|)
decl_stmt|;
name|String
name|position
init|=
name|newFile
operator|.
name|getParent
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|directory
operator|!=
literal|null
operator|)
operator|&&
name|position
operator|.
name|startsWith
argument_list|(
name|directory
argument_list|)
condition|)
block|{
comment|// Construct path relative to pdf base dir
name|String
name|relPath
init|=
name|position
operator|.
name|substring
argument_list|(
name|directory
operator|.
name|length
argument_list|()
argument_list|,
name|position
operator|.
name|length
argument_list|()
argument_list|)
operator|+
name|File
operator|.
name|separator
operator|+
name|newFile
operator|.
name|getName
argument_list|()
decl_stmt|;
comment|// Remove leading path separator
if|if
condition|(
name|relPath
operator|.
name|startsWith
argument_list|(
name|File
operator|.
name|separator
argument_list|)
condition|)
block|{
name|relPath
operator|=
name|relPath
operator|.
name|substring
argument_list|(
name|File
operator|.
name|separator
operator|.
name|length
argument_list|()
argument_list|,
name|relPath
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
comment|// Set relative path as field value
block|}
name|retVal
operator|=
name|relPath
expr_stmt|;
block|}
else|else
name|retVal
operator|=
name|newFile
operator|.
name|getPath
argument_list|()
expr_stmt|;
name|editor
operator|.
name|setText
argument_list|(
name|retVal
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|fieldName
operator|+
name|Globals
operator|.
name|FILETYPE_PREFS_EXT
argument_list|,
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|downLoadFile (final String fieldName, final FieldEditor fieldEditor, final Component parent)
specifier|public
name|void
name|downLoadFile
parameter_list|(
specifier|final
name|String
name|fieldName
parameter_list|,
specifier|final
name|FieldEditor
name|fieldEditor
parameter_list|,
specifier|final
name|Component
name|parent
parameter_list|)
block|{
specifier|final
name|String
name|res
init|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Enter URL to download"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|res
operator|==
literal|null
operator|||
name|res
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
condition|)
return|return;
comment|/* 		 * If this panel belongs in an entry editor, note which entry is 		 * currently shown: 		 */
specifier|final
name|BibtexEntry
name|targetEntry
decl_stmt|;
if|if
condition|(
name|entryEditor
operator|!=
literal|null
condition|)
name|targetEntry
operator|=
name|entryEditor
operator|.
name|getEntry
argument_list|()
expr_stmt|;
else|else
name|targetEntry
operator|=
name|entry
expr_stmt|;
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|String
name|getPlannedFileName
parameter_list|(
name|String
name|res
parameter_list|)
block|{
name|String
name|suffix
init|=
name|off
operator|.
name|getSuffix
argument_list|(
name|res
argument_list|)
decl_stmt|;
if|if
condition|(
name|suffix
operator|==
literal|null
condition|)
name|suffix
operator|=
literal|"."
operator|+
name|fieldName
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|String
name|plannedName
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|getKey
argument_list|()
operator|!=
literal|null
condition|)
name|plannedName
operator|=
name|getKey
argument_list|()
operator|+
name|suffix
expr_stmt|;
else|else
block|{
name|plannedName
operator|=
name|JOptionPane
operator|.
name|showInputDialog
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"BibTeX key not set. Enter a name for the downloaded file"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|plannedName
operator|!=
literal|null
operator|&&
operator|!
name|off
operator|.
name|accept
argument_list|(
name|plannedName
argument_list|)
condition|)
name|plannedName
operator|+=
name|suffix
expr_stmt|;
block|}
comment|/* 				 * [ 1548875 ] download pdf produces unsupported filename 				 *  				 * http://sourceforge.net/tracker/index.php?func=detail&aid=1548875&group_id=92314&atid=600306 				 *  				 */
if|if
condition|(
name|Globals
operator|.
name|ON_WIN
condition|)
block|{
name|plannedName
operator|=
name|plannedName
operator|.
name|replaceAll
argument_list|(
literal|"\\?|\\*|\\<|\\>|\\||\\\"|\\:|\\.$|\\[|\\]"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|Globals
operator|.
name|ON_MAC
condition|)
block|{
name|plannedName
operator|=
name|plannedName
operator|.
name|replaceAll
argument_list|(
literal|":"
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
return|return
name|plannedName
return|;
block|}
specifier|public
name|void
name|run
parameter_list|()
block|{
name|String
name|originalText
init|=
name|fieldEditor
operator|.
name|getText
argument_list|()
decl_stmt|;
name|fieldEditor
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|boolean
name|updateEditor
init|=
literal|true
decl_stmt|;
try|try
block|{
name|fieldEditor
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Downloading..."
argument_list|)
argument_list|)
expr_stmt|;
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Downloading..."
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|plannedName
init|=
name|getPlannedFileName
argument_list|(
name|res
argument_list|)
decl_stmt|;
comment|// Find the default directory for this field type:
name|String
name|directory
init|=
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
operator|new
name|File
argument_list|(
name|directory
argument_list|)
operator|.
name|exists
argument_list|()
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not find directory for %0-files: %1"
argument_list|,
name|fieldName
argument_list|,
name|directory
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
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|logger
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not find directory for %0-files: %1"
argument_list|,
name|fieldName
argument_list|,
name|directory
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|File
name|file
init|=
operator|new
name|File
argument_list|(
operator|new
name|File
argument_list|(
name|directory
argument_list|)
argument_list|,
name|plannedName
argument_list|)
decl_stmt|;
name|URL
name|url
init|=
operator|new
name|URL
argument_list|(
name|res
argument_list|)
decl_stmt|;
name|URLDownload
name|udl
init|=
operator|new
name|URLDownload
argument_list|(
name|parent
argument_list|,
name|url
argument_list|,
name|file
argument_list|)
decl_stmt|;
try|try
block|{
name|udl
operator|.
name|download
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e2
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Invalid URL"
argument_list|)
operator|+
literal|": "
operator|+
name|e2
operator|.
name|getMessage
argument_list|()
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
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|logger
argument_list|(
literal|"Error while downloading "
operator|+
name|url
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return;
block|}
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Download completed"
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|textToSet
init|=
name|file
operator|.
name|getPath
argument_list|()
decl_stmt|;
if|if
condition|(
name|textToSet
operator|.
name|startsWith
argument_list|(
name|directory
argument_list|)
condition|)
block|{
comment|// Construct path relative to pdf base dir
name|textToSet
operator|=
name|textToSet
operator|.
name|substring
argument_list|(
name|directory
operator|.
name|length
argument_list|()
argument_list|,
name|textToSet
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
comment|// Remove leading path separator
if|if
condition|(
name|textToSet
operator|.
name|startsWith
argument_list|(
name|File
operator|.
name|separator
argument_list|)
condition|)
block|{
name|textToSet
operator|=
name|textToSet
operator|.
name|substring
argument_list|(
name|File
operator|.
name|separator
operator|.
name|length
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/* 					 * Check if we should update the editor text field, or 					 * update the target entry directly: 					 */
if|if
condition|(
name|entryEditor
operator|==
literal|null
operator|||
name|entryEditor
operator|.
name|getEntry
argument_list|()
operator|!=
name|targetEntry
condition|)
block|{
comment|/* 						 * Editor has probably changed to show a different 						 * entry. So we must update the target entry directly 						 * and not set the text of the editor. 						 */
name|targetEntry
operator|.
name|setField
argument_list|(
name|fieldName
argument_list|,
name|textToSet
argument_list|)
expr_stmt|;
name|fieldEditor
operator|.
name|setText
argument_list|(
name|textToSet
argument_list|)
expr_stmt|;
name|fieldEditor
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|updateEditor
operator|=
literal|false
expr_stmt|;
block|}
else|else
block|{
comment|/* 						 * Need to set the fieldEditor first before running 						 * updateField-Action, because otherwise we might get a 						 * race condition. 						 *  						 * (Hopefully a) Fix for: [ 1545601 ] downloading pdf 						 * corrupts pdf field text 						 *  						 * http://sourceforge.net/tracker/index.php?func=detail&aid=1545601&group_id=92314&atid=600306 						 */
name|fieldEditor
operator|.
name|setText
argument_list|(
name|textToSet
argument_list|)
expr_stmt|;
name|fieldEditor
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|updateEditor
operator|=
literal|false
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
name|entryEditor
operator|.
name|updateField
argument_list|(
name|fieldEditor
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|MalformedURLException
name|e1
parameter_list|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Invalid URL"
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
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
finally|finally
block|{
comment|// If stuff goes wrong along the road, put back original
comment|// value
if|if
condition|(
name|updateEditor
condition|)
block|{
name|fieldEditor
operator|.
name|setText
argument_list|(
name|originalText
argument_list|)
expr_stmt|;
name|fieldEditor
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
operator|)
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
comment|/** 	 * Starts a thread that searches the external file directory for the given 	 * field name, including subdirectories, and looks for files named after the 	 * current entry's bibtex key. Returns a reference to the thread for callers 	 * that may want to wait for the thread to finish (using join()). 	 *  	 * @param fieldName 	 *            The field to set. 	 * @param editor 	 *            An EntryEditor instance where to set the value found. 	 * @return A reference to the Thread that performs the operation. 	 */
DECL|method|autoSetFile (final String fieldName, final FieldEditor editor)
specifier|public
name|Thread
name|autoSetFile
parameter_list|(
specifier|final
name|String
name|fieldName
parameter_list|,
specifier|final
name|FieldEditor
name|editor
parameter_list|)
block|{
name|Object
name|o
init|=
name|getKey
argument_list|()
decl_stmt|;
if|if
condition|(
operator|(
name|o
operator|==
literal|null
operator|)
operator|||
operator|(
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
operator|==
literal|null
operator|)
condition|)
block|{
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"You must set both BibTeX key and %0 directory"
argument_list|,
name|fieldName
operator|.
name|toUpperCase
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Searching for %0 file"
argument_list|,
name|fieldName
operator|.
name|toUpperCase
argument_list|()
argument_list|)
operator|+
literal|" '"
operator|+
name|o
operator|+
literal|"."
operator|+
name|fieldName
operator|+
literal|"'..."
argument_list|)
expr_stmt|;
name|Thread
name|t
init|=
operator|(
operator|new
name|Thread
argument_list|()
block|{
specifier|public
name|void
name|run
parameter_list|()
block|{
comment|/* 				 * Find the following directories to look in for: 				 *  				 * default directory for this field type. 				 *  				 * directory of bibtex-file. // NOT POSSIBLE at the moment. 				 *  				 * JabRef-directory. 				 */
name|LinkedList
argument_list|<
name|String
argument_list|>
name|list
init|=
operator|new
name|LinkedList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|list
operator|.
name|add
argument_list|(
name|metaData
operator|.
name|getFileDirectory
argument_list|(
name|fieldName
argument_list|)
argument_list|)
expr_stmt|;
comment|/* 				 * File fileOfDb = frame.basePanel().file(); if (fileOfDb != 				 * null){ list.add(fileOfDb.getParentFile().getPath()); } 				 */
name|list
operator|.
name|add
argument_list|(
literal|"."
argument_list|)
expr_stmt|;
name|String
name|found
init|=
name|Util
operator|.
name|findPdf
argument_list|(
name|getEntry
argument_list|()
argument_list|,
name|fieldName
argument_list|,
name|list
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|list
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
decl_stmt|;
comment|// , off);
comment|// To activate findFile:
comment|// String found = Util.findFile(getEntry(), null, dir,
comment|// ".*[bibtexkey].*");
if|if
condition|(
name|found
operator|!=
literal|null
condition|)
block|{
name|editor
operator|.
name|setText
argument_list|(
name|found
argument_list|)
expr_stmt|;
if|if
condition|(
name|entryEditor
operator|!=
literal|null
condition|)
name|entryEditor
operator|.
name|updateField
argument_list|(
name|editor
argument_list|)
expr_stmt|;
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"%0 field set"
argument_list|,
name|fieldName
operator|.
name|toUpperCase
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No %0 found"
argument_list|,
name|fieldName
operator|.
name|toUpperCase
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
block|}
block|}
operator|)
decl_stmt|;
name|t
operator|.
name|start
argument_list|()
expr_stmt|;
return|return
name|t
return|;
block|}
block|}
end_class

end_unit

