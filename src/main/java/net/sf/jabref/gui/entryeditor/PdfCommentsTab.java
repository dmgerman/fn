begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.entryeditor
package|package
name|net
operator|.
name|sf
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
name|awt
operator|.
name|SystemColor
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
name|Optional
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BoxLayout
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
name|ClipBoardManager
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
name|pdf
operator|.
name|PdfCommentImporter
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
name|FieldName
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
name|pdf
operator|.
name|PdfComment
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
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|pdmodel
operator|.
name|PDDocument
import|;
end_import

begin_class
DECL|class|PdfCommentsTab
specifier|public
class|class
name|PdfCommentsTab
extends|extends
name|JPanel
block|{
DECL|field|commentList
specifier|private
specifier|final
name|JList
argument_list|<
name|PdfComment
argument_list|>
name|commentList
init|=
operator|new
name|JList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|commentScrollPane
specifier|private
specifier|final
name|JScrollPane
name|commentScrollPane
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|commentLabel
specifier|private
specifier|final
name|JLabel
name|commentLabel
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Comments"
argument_list|)
argument_list|,
name|JLabel
operator|.
name|LEFT
argument_list|)
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
DECL|field|filenameArea
specifier|private
specifier|final
name|JTextArea
name|filenameArea
init|=
operator|new
name|JTextArea
argument_list|(
literal|"filename"
argument_list|)
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
DECL|field|commentTxtLabel
specifier|private
specifier|final
name|JLabel
name|commentTxtLabel
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
DECL|field|commentTxtArea
specifier|private
specifier|final
name|JTextArea
name|commentTxtArea
init|=
operator|new
name|JTextArea
argument_list|(
literal|"comment content"
argument_list|)
decl_stmt|;
DECL|field|commentTxtScrollPane
specifier|private
specifier|final
name|JScrollPane
name|commentTxtScrollPane
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
DECL|field|openPdfButton
specifier|private
specifier|final
name|JButton
name|openPdfButton
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|listModel
name|DefaultListModel
argument_list|<
name|PdfComment
argument_list|>
name|listModel
decl_stmt|;
DECL|field|parent
specifier|private
specifier|final
name|EntryEditor
name|parent
decl_stmt|;
DECL|field|tabTitle
specifier|private
specifier|final
name|String
name|tabTitle
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|commentListSelectedIndex
specifier|private
name|int
name|commentListSelectedIndex
decl_stmt|;
DECL|field|importedNotes
name|HashMap
argument_list|<
name|String
argument_list|,
name|PdfComment
argument_list|>
name|importedNotes
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|method|PdfCommentsTab (EntryEditor parent, JabRefFrame frame, BasePanel basePanel)
specifier|public
name|PdfCommentsTab
parameter_list|(
name|EntryEditor
name|parent
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|,
name|BasePanel
name|basePanel
parameter_list|)
block|{
name|this
operator|.
name|parent
operator|=
name|parent
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|this
operator|.
name|tabTitle
operator|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"PDF comments"
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BoxLayout
argument_list|(
name|this
argument_list|,
name|BoxLayout
operator|.
name|X_AXIS
argument_list|)
argument_list|)
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
name|setUpPdfCommentsTab
argument_list|()
expr_stmt|;
name|this
operator|.
name|setUpInformationPanel
argument_list|()
expr_stmt|;
block|}
DECL|method|setUpPdfCommentsTab ()
specifier|private
name|void
name|setUpPdfCommentsTab
parameter_list|()
block|{
name|JPanel
name|commentListPanel
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
operator|.
name|columns
argument_list|(
literal|"fill:pref:grow"
argument_list|)
operator|.
name|rows
argument_list|(
literal|"pref, $lg, fill:pref:grow"
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
name|commentLabel
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
name|commentScrollPane
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|commentScrollPane
operator|.
name|setViewportView
argument_list|(
name|commentList
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|commentListPanel
argument_list|)
expr_stmt|;
block|}
DECL|method|addComments ()
specifier|public
name|void
name|addComments
parameter_list|()
throws|throws
name|IOException
block|{
name|Optional
argument_list|<
name|String
argument_list|>
name|field
init|=
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
decl_stmt|;
if|if
condition|(
name|field
operator|.
name|isPresent
argument_list|()
condition|)
block|{
if|if
condition|(
operator|!
name|commentList
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
name|commentList
operator|.
name|setModel
argument_list|(
name|listModel
argument_list|)
expr_stmt|;
name|commentList
operator|.
name|addListSelectionListener
argument_list|(
operator|new
name|CommentListSelectionListener
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|PdfCommentImporter
name|commentImporter
init|=
operator|new
name|PdfCommentImporter
argument_list|()
decl_stmt|;
name|ArrayList
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|entries
operator|.
name|add
argument_list|(
name|parent
operator|.
name|getEntry
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|PDDocument
argument_list|>
name|documents
init|=
name|commentImporter
operator|.
name|importPdfFile
argument_list|(
name|entries
argument_list|,
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|documents
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|listModel
operator|.
name|clear
argument_list|()
expr_stmt|;
name|listModel
operator|.
name|addElement
argument_list|(
operator|new
name|PdfComment
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|,
literal|""
argument_list|,
literal|0
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Attached_file_has_no_valid_path"
argument_list|)
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|importedNotes
operator|=
name|commentImporter
operator|.
name|importNotes
argument_list|(
name|documents
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|updateShownComments
argument_list|(
name|importedNotes
argument_list|)
expr_stmt|;
name|commentList
operator|.
name|setSelectedIndex
argument_list|(
name|commentListSelectedIndex
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * Updates the list model to show the given notes exclusive those with no content      * @param importedNotes value is the comments name and the value is a pdfComment object to add to the list model      */
DECL|method|updateShownComments (HashMap<String, PdfComment> importedNotes)
specifier|private
name|void
name|updateShownComments
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|PdfComment
argument_list|>
name|importedNotes
parameter_list|)
block|{
name|listModel
operator|.
name|clear
argument_list|()
expr_stmt|;
name|importedNotes
operator|.
name|values
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|comment
lambda|->
operator|!
operator|(
literal|null
operator|==
name|comment
operator|.
name|getContent
argument_list|()
operator|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
operator|.
name|forEach
argument_list|(
parameter_list|(
name|comment
parameter_list|)
lambda|->
name|listModel
operator|.
name|addElement
argument_list|(
name|comment
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|updateTextFields (PdfComment comment)
specifier|private
name|void
name|updateTextFields
parameter_list|(
name|PdfComment
name|comment
parameter_list|)
block|{
name|authorArea
operator|.
name|setText
argument_list|(
name|comment
operator|.
name|getAuthor
argument_list|()
argument_list|)
expr_stmt|;
name|dateArea
operator|.
name|setText
argument_list|(
name|comment
operator|.
name|getDate
argument_list|()
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
name|comment
operator|.
name|getPage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|commentTxtArea
operator|.
name|setText
argument_list|(
name|comment
operator|.
name|getContent
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|setUpInformationPanel ()
specifier|private
name|void
name|setUpInformationPanel
parameter_list|()
block|{
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
literal|"pref, $lg, pref, $lg, pref, $lg, pref, $lg, fill:pref:grow, $lg, pref"
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
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
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
name|commentTxtLabel
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
name|commentTxtScrollPane
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|9
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
name|xy
argument_list|(
literal|3
argument_list|,
literal|11
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|fileNameScrollPane
operator|.
name|setViewportView
argument_list|(
name|filenameArea
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
name|commentTxtScrollPane
operator|.
name|setViewportView
argument_list|(
name|commentTxtArea
argument_list|)
expr_stmt|;
name|authorArea
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|control
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
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|control
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
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|control
argument_list|)
expr_stmt|;
name|pageArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|commentTxtArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|filenameArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|filenameArea
operator|.
name|setBackground
argument_list|(
name|SystemColor
operator|.
name|control
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|informationPanel
argument_list|)
expr_stmt|;
block|}
DECL|class|CommentListSelectionListener
specifier|private
class|class
name|CommentListSelectionListener
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
name|index
decl_stmt|;
if|if
condition|(
name|commentList
operator|.
name|getSelectedIndex
argument_list|()
operator|>=
literal|0
condition|)
block|{
name|index
operator|=
name|commentList
operator|.
name|getSelectedIndex
argument_list|()
expr_stmt|;
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
name|commentListSelectedIndex
operator|=
name|index
expr_stmt|;
block|}
name|commentList
operator|.
name|setSelectedIndex
argument_list|(
name|commentListSelectedIndex
argument_list|)
expr_stmt|;
block|}
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
name|buttonConstraints
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|buttonConstraints
operator|.
name|gridy
operator|=
literal|0
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
literal|10
expr_stmt|;
name|openPdfButton
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Open PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|openPdfButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|openPdf
argument_list|()
argument_list|)
expr_stmt|;
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
literal|1
expr_stmt|;
name|buttonPanel
operator|.
name|add
argument_list|(
name|openPdfButton
argument_list|,
name|buttonConstraints
argument_list|)
expr_stmt|;
return|return
name|buttonPanel
return|;
block|}
DECL|method|copyToClipboard ()
specifier|private
name|void
name|copyToClipboard
parameter_list|()
block|{
operator|new
name|ClipBoardManager
argument_list|()
operator|.
name|setClipboardContents
argument_list|(
name|commentTxtArea
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|openPdf ()
specifier|private
name|void
name|openPdf
parameter_list|()
block|{      }
block|}
end_class

end_unit

