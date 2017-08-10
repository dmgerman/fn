begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.entryeditor
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|entryeditor
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
name|GridBagConstraints
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|LocalDateTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|DateTimeFormatter
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|format
operator|.
name|FormatStyle
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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
name|Locale
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
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|StringJoiner
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultListCellRenderer
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultListModel
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
name|JComboBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JList
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
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextArea
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
name|swing
operator|.
name|event
operator|.
name|ListSelectionEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ListSelectionListener
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|embed
operator|.
name|swing
operator|.
name|SwingNode
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|Tooltip
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|ClipBoardManager
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|GUIGlobals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|org
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
name|jabref
operator|.
name|logic
operator|.
name|pdf
operator|.
name|FileAnnotationCache
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|pdf
operator|.
name|FileAnnotation
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
name|FormBuilder
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
name|factories
operator|.
name|Paddings
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|pdf
operator|.
name|FileAnnotationType
operator|.
name|NONE
import|;
end_import

begin_class
DECL|class|FileAnnotationTab
class|class
name|FileAnnotationTab
extends|extends
name|EntryEditorTab
block|{
DECL|field|annotationList
specifier|private
specifier|final
name|JList
argument_list|<
name|FileAnnotation
argument_list|>
name|annotationList
init|=
operator|new
name|JList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|annotationScrollPane
specifier|private
specifier|final
name|JScrollPane
name|annotationScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|fileNameLabel
specifier|private
specifier|final
name|JLabel
name|fileNameLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Filename"
argument_list|)
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|fileNameComboBox
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|fileNameComboBox
init|=
operator|new
name|JComboBox
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|fileNameScrollPane
specifier|private
specifier|final
name|JScrollPane
name|fileNameScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|authorLabel
specifier|private
specifier|final
name|JLabel
name|authorLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Author"
argument_list|)
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|authorArea
specifier|private
specifier|final
name|JTextArea
name|authorArea
init|=
operator|new
name|JTextArea
argument_list|(
literal|"author"
argument_list|)
decl_stmt|;
DECL|field|authorScrollPane
specifier|private
specifier|final
name|JScrollPane
name|authorScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|dateLabel
specifier|private
specifier|final
name|JLabel
name|dateLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Date"
argument_list|)
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|dateArea
specifier|private
specifier|final
name|JTextArea
name|dateArea
init|=
operator|new
name|JTextArea
argument_list|(
literal|"date"
argument_list|)
decl_stmt|;
DECL|field|dateScrollPane
specifier|private
specifier|final
name|JScrollPane
name|dateScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|pageLabel
specifier|private
specifier|final
name|JLabel
name|pageLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Page"
argument_list|)
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|pageArea
specifier|private
specifier|final
name|JTextArea
name|pageArea
init|=
operator|new
name|JTextArea
argument_list|(
literal|"page"
argument_list|)
decl_stmt|;
DECL|field|pageScrollPane
specifier|private
specifier|final
name|JScrollPane
name|pageScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|annotationTextLabel
specifier|private
specifier|final
name|JLabel
name|annotationTextLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Content"
argument_list|)
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|contentTxtArea
specifier|private
specifier|final
name|JTextArea
name|contentTxtArea
init|=
operator|new
name|JTextArea
argument_list|()
decl_stmt|;
DECL|field|markedTextLabel
specifier|private
specifier|final
name|JLabel
name|markedTextLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marking"
argument_list|)
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|markedTxtArea
specifier|private
specifier|final
name|JTextArea
name|markedTxtArea
init|=
operator|new
name|JTextArea
argument_list|()
decl_stmt|;
DECL|field|annotationTextScrollPane
specifier|private
specifier|final
name|JScrollPane
name|annotationTextScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|markedTextScrollPane
specifier|private
specifier|final
name|JScrollPane
name|markedTextScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|copyToClipboardButton
specifier|private
specifier|final
name|JButton
name|copyToClipboardButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|reloadAnnotationsButton
specifier|private
specifier|final
name|JButton
name|reloadAnnotationsButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|entry
specifier|private
specifier|final
name|BibEntry
name|entry
decl_stmt|;
DECL|field|fileAnnotationCache
specifier|private
specifier|final
name|FileAnnotationCache
name|fileAnnotationCache
decl_stmt|;
DECL|field|swingNode
specifier|private
specifier|final
name|SwingNode
name|swingNode
init|=
operator|new
name|SwingNode
argument_list|()
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|EntryEditor
name|parent
decl_stmt|;
DECL|field|listModel
specifier|private
name|DefaultListModel
argument_list|<
name|FileAnnotation
argument_list|>
name|listModel
decl_stmt|;
DECL|method|FileAnnotationTab (BibEntry entry, EntryEditor parent, FileAnnotationCache cache)
name|FileAnnotationTab
parameter_list|(
name|BibEntry
name|entry
parameter_list|,
name|EntryEditor
name|parent
parameter_list|,
name|FileAnnotationCache
name|cache
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
name|fileAnnotationCache
operator|=
name|cache
expr_stmt|;
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|listModel
operator|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
expr_stmt|;
name|this
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"File annotations"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTooltip
argument_list|(
operator|new
name|Tooltip
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show file annotations"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setContent
argument_list|(
name|swingNode
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds pdf annotations from all attached pdf files belonging to the entry selected in the main table and      * shows those from the first file in the file annotations tab      */
DECL|method|addAnnotations ()
specifier|private
name|void
name|addAnnotations
parameter_list|()
block|{
if|if
condition|(
name|parent
operator|.
name|getEntry
argument_list|()
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|annotationList
operator|.
name|getModel
argument_list|()
operator|.
name|equals
argument_list|(
name|listModel
argument_list|)
condition|)
block|{
name|annotationList
operator|.
name|setModel
argument_list|(
name|listModel
argument_list|)
expr_stmt|;
name|annotationList
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|AnnotationListSelectionListener
argument_list|()
argument_list|)
expr_stmt|;
name|annotationList
operator|.
name|setCellRenderer
argument_list|(
operator|new
name|AnnotationListCellRenderer
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|//set up the comboBox for representing the selected file
name|fileNameComboBox
operator|.
name|removeAllItems
argument_list|()
expr_stmt|;
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|fileAnnotations
init|=
name|fileAnnotationCache
operator|.
name|getFromCache
argument_list|(
name|parent
operator|.
name|getEntry
argument_list|()
argument_list|)
decl_stmt|;
name|fileAnnotations
operator|.
name|keySet
argument_list|()
operator|.
name|forEach
argument_list|(
name|fileNameComboBox
operator|::
name|addItem
argument_list|)
expr_stmt|;
comment|//show the annotationsOfFiles attached to the selected file
name|updateShownAnnotations
argument_list|(
name|fileAnnotations
operator|.
name|get
argument_list|(
name|fileNameComboBox
operator|.
name|getSelectedItem
argument_list|()
operator|==
literal|null
condition|?
name|fileNameComboBox
operator|.
name|getItemAt
argument_list|(
literal|0
argument_list|)
else|:
name|fileNameComboBox
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
comment|//select the first annotation
if|if
condition|(
name|annotationList
operator|.
name|isSelectionEmpty
argument_list|()
condition|)
block|{
name|annotationList
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Updates the list model to show the given notes without those with no content      *      * @param annotations value is the annotation name and the value is a pdfAnnotation object to add to the list model      */
DECL|method|updateShownAnnotations (List<FileAnnotation> annotations)
specifier|private
name|void
name|updateShownAnnotations
parameter_list|(
name|List
argument_list|<
name|FileAnnotation
argument_list|>
name|annotations
parameter_list|)
block|{
name|listModel
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|annotations
operator|==
literal|null
operator|||
name|annotations
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|listModel
operator|.
name|addElement
argument_list|(
operator|new
name|FileAnnotation
argument_list|(
literal|""
argument_list|,
name|LocalDateTime
operator|.
name|now
argument_list|()
argument_list|,
literal|0
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"File has no attached annotations"
argument_list|)
argument_list|,
name|NONE
argument_list|,
name|Optional
operator|.
name|empty
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Comparator
argument_list|<
name|FileAnnotation
argument_list|>
name|byPage
init|=
name|Comparator
operator|.
name|comparingInt
argument_list|(
name|FileAnnotation
operator|::
name|getPage
argument_list|)
decl_stmt|;
name|annotations
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|annotation
lambda|->
operator|(
literal|null
operator|!=
name|annotation
operator|.
name|getContent
argument_list|()
operator|)
argument_list|)
operator|.
name|sorted
argument_list|(
name|byPage
argument_list|)
operator|.
name|forEach
argument_list|(
name|annotation
lambda|->
name|listModel
operator|.
name|addElement
argument_list|(
operator|new
name|FileAnnotationViewModel
argument_list|(
name|annotation
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * Updates the text fields showing meta data and the content from the selected annotation      *      * @param annotation pdf annotation which data should be shown in the text fields      */
DECL|method|updateTextFields (FileAnnotation annotation)
specifier|private
name|void
name|updateTextFields
parameter_list|(
name|FileAnnotation
name|annotation
parameter_list|)
block|{
name|authorArea
operator|.
name|setText
argument_list|(
name|annotation
operator|.
name|getAuthor
argument_list|()
argument_list|)
expr_stmt|;
name|dateArea
operator|.
name|setText
argument_list|(
name|annotation
operator|.
name|getTimeModified
argument_list|()
operator|.
name|format
argument_list|(
name|DateTimeFormatter
operator|.
name|ofLocalizedDateTime
argument_list|(
name|FormatStyle
operator|.
name|MEDIUM
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|pageArea
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|annotation
operator|.
name|getPage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|updateContentAndMarkedTextAreas
argument_list|(
name|annotation
argument_list|)
expr_stmt|;
block|}
comment|/**      * Updates the selection of files that are attached to the pdf file      */
DECL|method|updateFileNameComboBox ()
specifier|private
name|void
name|updateFileNameComboBox
parameter_list|()
block|{
name|int
name|indexSelectedByComboBox
decl_stmt|;
if|if
condition|(
name|fileNameComboBox
operator|.
name|getItemCount
argument_list|()
operator|==
literal|0
condition|)
block|{
name|indexSelectedByComboBox
operator|=
literal|0
expr_stmt|;
block|}
else|else
block|{
name|indexSelectedByComboBox
operator|=
name|fileNameComboBox
operator|.
name|getSelectedIndex
argument_list|()
expr_stmt|;
block|}
name|fileNameComboBox
operator|.
name|removeAllItems
argument_list|()
expr_stmt|;
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|FileAnnotation
argument_list|>
argument_list|>
name|fileAnnotations
init|=
name|fileAnnotationCache
operator|.
name|getFromCache
argument_list|(
name|parent
operator|.
name|getEntry
argument_list|()
argument_list|)
decl_stmt|;
name|fileAnnotations
operator|.
name|keySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|filename
lambda|->
name|filename
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
operator|.
name|endsWith
argument_list|(
literal|".pdf"
argument_list|)
argument_list|)
operator|.
name|forEach
argument_list|(
operator|(
name|fileNameComboBox
operator|::
name|addItem
operator|)
argument_list|)
expr_stmt|;
name|fileNameComboBox
operator|.
name|setSelectedIndex
argument_list|(
name|indexSelectedByComboBox
argument_list|)
expr_stmt|;
name|updateShownAnnotations
argument_list|(
name|fileAnnotations
operator|.
name|get
argument_list|(
name|fileNameComboBox
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setUpGui ()
specifier|private
name|void
name|setUpGui
parameter_list|()
block|{
name|JPanel
name|annotationPanel
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|columns
argument_list|(
literal|"pref, $lcgap, pref:grow"
argument_list|)
operator|.
name|rows
argument_list|(
literal|"pref, $lg, fill:pref:grow, $lg, pref"
argument_list|)
operator|.
name|padding
argument_list|(
name|Paddings
operator|.
name|DIALOG
argument_list|)
operator|.
name|add
argument_list|(
name|fileNameLabel
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|"left, top"
argument_list|)
operator|.
name|add
argument_list|(
name|fileNameScrollPane
argument_list|)
operator|.
name|xyw
argument_list|(
literal|2
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
operator|.
name|add
argument_list|(
name|annotationScrollPane
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|annotationScrollPane
operator|.
name|setViewportView
argument_list|(
name|annotationList
argument_list|)
expr_stmt|;
name|JPanel
name|informationPanel
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|columns
argument_list|(
literal|"pref, $lcgap, pref:grow"
argument_list|)
operator|.
name|rows
argument_list|(
literal|"pref, $lg, pref, $lg, pref, $lg, pref, $lg, pref:grow, $lg, pref:grow, $lg, fill:pref"
argument_list|)
operator|.
name|padding
argument_list|(
name|Paddings
operator|.
name|DIALOG
argument_list|)
operator|.
name|add
argument_list|(
name|authorLabel
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|,
literal|"left, top"
argument_list|)
operator|.
name|add
argument_list|(
name|authorScrollPane
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
operator|.
name|add
argument_list|(
name|dateLabel
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|5
argument_list|,
literal|"left, top"
argument_list|)
operator|.
name|add
argument_list|(
name|dateScrollPane
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|5
argument_list|)
operator|.
name|add
argument_list|(
name|pageLabel
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|7
argument_list|,
literal|"left, top"
argument_list|)
operator|.
name|add
argument_list|(
name|pageScrollPane
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|7
argument_list|)
operator|.
name|add
argument_list|(
name|annotationTextLabel
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|9
argument_list|,
literal|"left, top"
argument_list|)
operator|.
name|add
argument_list|(
name|annotationTextScrollPane
argument_list|)
operator|.
name|xywh
argument_list|(
literal|3
argument_list|,
literal|9
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
operator|.
name|add
argument_list|(
name|markedTextLabel
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|11
argument_list|,
literal|"left, top"
argument_list|)
operator|.
name|add
argument_list|(
name|markedTextScrollPane
argument_list|)
operator|.
name|xywh
argument_list|(
literal|3
argument_list|,
literal|11
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
operator|.
name|add
argument_list|(
name|this
operator|.
name|setUpButtons
argument_list|()
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|13
argument_list|,
literal|3
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|fileNameScrollPane
operator|.
name|setViewportView
argument_list|(
name|fileNameComboBox
argument_list|)
expr_stmt|;
name|fileNameLabel
operator|.
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|ENTRY_EDITOR_LABEL_COLOR
argument_list|)
expr_stmt|;
name|authorLabel
operator|.
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|ENTRY_EDITOR_LABEL_COLOR
argument_list|)
expr_stmt|;
name|dateLabel
operator|.
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|ENTRY_EDITOR_LABEL_COLOR
argument_list|)
expr_stmt|;
name|pageLabel
operator|.
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|ENTRY_EDITOR_LABEL_COLOR
argument_list|)
expr_stmt|;
name|annotationTextLabel
operator|.
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|ENTRY_EDITOR_LABEL_COLOR
argument_list|)
expr_stmt|;
name|markedTextLabel
operator|.
name|setForeground
argument_list|(
name|GUIGlobals
operator|.
name|ENTRY_EDITOR_LABEL_COLOR
argument_list|)
expr_stmt|;
name|fileNameScrollPane
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|authorScrollPane
operator|.
name|setViewportView
argument_list|(
name|authorArea
argument_list|)
expr_stmt|;
name|authorScrollPane
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|dateScrollPane
operator|.
name|setViewportView
argument_list|(
name|dateArea
argument_list|)
expr_stmt|;
name|dateScrollPane
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|pageScrollPane
operator|.
name|setViewportView
argument_list|(
name|pageArea
argument_list|)
expr_stmt|;
name|pageScrollPane
operator|.
name|setBorder
argument_list|(
literal|null
argument_list|)
expr_stmt|;
name|annotationTextScrollPane
operator|.
name|setViewportView
argument_list|(
name|contentTxtArea
argument_list|)
expr_stmt|;
name|markedTextScrollPane
operator|.
name|setViewportView
argument_list|(
name|markedTxtArea
argument_list|)
expr_stmt|;
name|authorArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|dateArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pageArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|contentTxtArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|contentTxtArea
operator|.
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|markedTxtArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|markedTxtArea
operator|.
name|setLineWrap
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|fileNameComboBox
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|fileNameComboBox
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|updateFileNameComboBox
argument_list|()
argument_list|)
expr_stmt|;
name|swingNode
operator|.
name|setContent
argument_list|(
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|columns
argument_list|(
literal|"0:grow, $lcgap, 0:grow"
argument_list|)
operator|.
name|rows
argument_list|(
literal|"fill:pref:grow"
argument_list|)
operator|.
name|add
argument_list|(
name|annotationPanel
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
operator|.
name|add
argument_list|(
name|informationPanel
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
operator|.
name|build
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setUpButtons ()
specifier|private
name|JPanel
name|setUpButtons
parameter_list|()
block|{
name|JPanel
name|buttonPanel
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|GridBagLayout
argument_list|()
argument_list|)
decl_stmt|;
name|GridBagConstraints
name|buttonConstraints
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|copyToClipboardButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy to clipboard"
argument_list|)
argument_list|)
expr_stmt|;
name|copyToClipboardButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|copyToClipboard
argument_list|()
argument_list|)
expr_stmt|;
name|reloadAnnotationsButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reload annotations"
argument_list|)
argument_list|)
expr_stmt|;
name|reloadAnnotationsButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|reloadAnnotations
argument_list|()
argument_list|)
expr_stmt|;
name|buttonConstraints
operator|.
name|gridy
operator|=
literal|10
expr_stmt|;
name|buttonConstraints
operator|.
name|gridx
operator|=
literal|3
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|copyToClipboardButton
argument_list|,
name|buttonConstraints
argument_list|)
expr_stmt|;
name|buttonConstraints
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|buttonConstraints
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|reloadAnnotationsButton
argument_list|,
name|buttonConstraints
argument_list|)
expr_stmt|;
return|return
name|buttonPanel
return|;
block|}
comment|/**      * Copies the meta and content information of the pdf annotation to the clipboard      */
DECL|method|copyToClipboard ()
specifier|private
name|void
name|copyToClipboard
parameter_list|()
block|{
name|StringJoiner
name|sj
init|=
operator|new
name|StringJoiner
argument_list|(
name|System
operator|.
name|getProperty
argument_list|(
literal|"line.separator"
argument_list|)
argument_list|)
decl_stmt|;
name|sj
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Author"
argument_list|)
operator|+
literal|": "
operator|+
name|authorArea
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|sj
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Date"
argument_list|)
operator|+
literal|": "
operator|+
name|dateArea
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|sj
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Page"
argument_list|)
operator|+
literal|": "
operator|+
name|pageArea
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|sj
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Content"
argument_list|)
operator|+
literal|": "
operator|+
name|contentTxtArea
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|sj
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marking"
argument_list|)
operator|+
literal|": "
operator|+
name|markedTxtArea
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
operator|new
name|ClipBoardManager
argument_list|()
operator|.
name|setClipboardContents
argument_list|(
name|sj
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|reloadAnnotations ()
specifier|private
name|void
name|reloadAnnotations
parameter_list|()
block|{
name|fileAnnotationCache
operator|.
name|remove
argument_list|(
name|parent
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
name|initialize
argument_list|()
expr_stmt|;
block|}
comment|/**      * Fills the TextAreas of the content and the highlighted or underlined text with the corresponding text and also      * changes the label accordingly.      *      * @param annotation either a text annotation or a marking from a PDF      */
DECL|method|updateContentAndMarkedTextAreas (final FileAnnotation annotation)
specifier|private
name|void
name|updateContentAndMarkedTextAreas
parameter_list|(
specifier|final
name|FileAnnotation
name|annotation
parameter_list|)
block|{
name|updateMarkingType
argument_list|(
name|annotation
argument_list|)
expr_stmt|;
if|if
condition|(
name|annotation
operator|.
name|hasLinkedAnnotation
argument_list|()
condition|)
block|{
comment|// isPresent() of the optional is already checked in annotation.hasLinkedAnnotation()
if|if
condition|(
operator|!
name|annotation
operator|.
name|getLinkedFileAnnotation
argument_list|()
operator|.
name|getContent
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|contentTxtArea
operator|.
name|setText
argument_list|(
name|annotation
operator|.
name|getLinkedFileAnnotation
argument_list|()
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
name|contentTxtArea
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|contentTxtArea
operator|.
name|setText
argument_list|(
literal|"N/A"
argument_list|)
expr_stmt|;
name|contentTxtArea
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|annotation
operator|.
name|getContent
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|markedTxtArea
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|markedTxtArea
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"The marked area does not contain any legible text!"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|markedTxtArea
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|markedTxtArea
operator|.
name|setText
argument_list|(
name|annotation
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|contentTxtArea
operator|.
name|setEnabled
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
literal|"File has no attached annotations."
operator|.
name|equals
argument_list|(
name|annotation
operator|.
name|getContent
argument_list|()
argument_list|)
condition|)
block|{
name|authorArea
operator|.
name|setText
argument_list|(
literal|"N/A"
argument_list|)
expr_stmt|;
name|authorArea
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|dateArea
operator|.
name|setText
argument_list|(
literal|"N/A"
argument_list|)
expr_stmt|;
name|dateArea
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|pageArea
operator|.
name|setText
argument_list|(
literal|"N/A"
argument_list|)
expr_stmt|;
name|pageArea
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|contentTxtArea
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|contentTxtArea
operator|.
name|setText
argument_list|(
name|annotation
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
name|markedTxtArea
operator|.
name|setText
argument_list|(
literal|"N/A"
argument_list|)
expr_stmt|;
name|markedTxtArea
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|updateMarkingType (FileAnnotation annotation)
specifier|private
name|void
name|updateMarkingType
parameter_list|(
name|FileAnnotation
name|annotation
parameter_list|)
block|{
switch|switch
condition|(
name|annotation
operator|.
name|getAnnotationType
argument_list|()
condition|)
block|{
case|case
name|UNDERLINE
case|:
name|markedTextLabel
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Underline"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
case|case
name|HIGHLIGHT
case|:
name|markedTextLabel
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Highlight"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
default|default:
name|markedTextLabel
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Marking"
argument_list|)
argument_list|)
expr_stmt|;
break|break;
block|}
block|}
annotation|@
name|Override
DECL|method|shouldShow ()
specifier|public
name|boolean
name|shouldShow
parameter_list|()
block|{
return|return
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|FILE
argument_list|)
operator|.
name|isPresent
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|initialize ()
specifier|protected
name|void
name|initialize
parameter_list|()
block|{
name|addAnnotations
argument_list|()
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
name|this
operator|::
name|setUpGui
argument_list|)
expr_stmt|;
name|this
operator|.
name|parent
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
DECL|class|AnnotationListSelectionListener
specifier|private
class|class
name|AnnotationListSelectionListener
implements|implements
name|ListSelectionListener
block|{
annotation|@
name|Override
DECL|method|valueChanged (ListSelectionEvent e)
specifier|public
name|void
name|valueChanged
parameter_list|(
name|ListSelectionEvent
name|e
parameter_list|)
block|{
name|int
name|annotationListSelectedIndex
init|=
literal|0
decl_stmt|;
if|if
condition|(
name|annotationList
operator|.
name|getSelectedIndex
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|int
name|index
init|=
name|annotationList
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
name|updateTextFields
argument_list|(
name|listModel
operator|.
name|get
argument_list|(
name|index
argument_list|)
argument_list|)
expr_stmt|;
name|annotationListSelectedIndex
operator|=
name|index
expr_stmt|;
block|}
name|annotationList
operator|.
name|setSelectedIndex
argument_list|(
name|annotationListSelectedIndex
argument_list|)
expr_stmt|;
comment|//repaint the list to refresh the linked annotation
name|annotationList
operator|.
name|repaint
argument_list|()
expr_stmt|;
block|}
block|}
comment|/**      * Cell renderer that shows different icons dependent on the annotation subtype      */
DECL|class|AnnotationListCellRenderer
class|class
name|AnnotationListCellRenderer
extends|extends
name|DefaultListCellRenderer
block|{
DECL|field|label
name|JLabel
name|label
decl_stmt|;
DECL|method|AnnotationListCellRenderer ()
name|AnnotationListCellRenderer
parameter_list|()
block|{
name|this
operator|.
name|label
operator|=
operator|new
name|JLabel
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getListCellRendererComponent (JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
specifier|public
name|Component
name|getListCellRendererComponent
parameter_list|(
name|JList
argument_list|<
name|?
argument_list|>
name|list
parameter_list|,
name|Object
name|value
parameter_list|,
name|int
name|index
parameter_list|,
name|boolean
name|isSelected
parameter_list|,
name|boolean
name|cellHasFocus
parameter_list|)
block|{
name|FileAnnotation
name|annotation
init|=
operator|(
name|FileAnnotation
operator|)
name|value
decl_stmt|;
comment|//call the super method so that the cell selection is done as usual
name|label
operator|=
operator|(
name|JLabel
operator|)
name|super
operator|.
name|getListCellRendererComponent
argument_list|(
name|list
argument_list|,
name|value
argument_list|,
name|index
argument_list|,
name|isSelected
argument_list|,
name|cellHasFocus
argument_list|)
expr_stmt|;
comment|//If more different annotation types should be reflected by icons in the list, add them here
switch|switch
condition|(
name|annotation
operator|.
name|getAnnotationType
argument_list|()
condition|)
block|{
case|case
name|HIGHLIGHT
case|:
name|label
operator|.
name|setIcon
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|MARKER
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
break|break;
case|case
name|UNDERLINE
case|:
name|label
operator|.
name|setIcon
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|MARKER
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
break|break;
default|default:
name|label
operator|.
name|setIcon
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|OPTIONAL
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
break|break;
block|}
name|label
operator|.
name|setToolTipText
argument_list|(
name|annotation
operator|.
name|getAnnotationType
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|label
operator|.
name|setText
argument_list|(
name|annotation
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|label
return|;
block|}
block|}
block|}
end_class

end_unit

