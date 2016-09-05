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
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
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
name|Optional
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

begin_class
DECL|class|PdfCommentsTab
specifier|public
class|class
name|PdfCommentsTab
extends|extends
name|JPanel
block|{
DECL|field|informationPanel
specifier|private
name|JPanel
name|informationPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|commentList
specifier|private
name|JList
argument_list|<
name|String
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
argument_list|(
name|commentList
argument_list|)
decl_stmt|;
DECL|field|authorLabel
specifier|private
name|JLabel
name|authorLabel
init|=
operator|new
name|JLabel
argument_list|(
literal|"author"
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|authorArea
specifier|private
name|JTextArea
name|authorArea
init|=
operator|new
name|JTextArea
argument_list|(
literal|"author"
argument_list|,
literal|2
argument_list|,
literal|25
argument_list|)
decl_stmt|;
DECL|field|authorScrollPane
specifier|private
name|JScrollPane
name|authorScrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|authorArea
argument_list|)
decl_stmt|;
DECL|field|dateLabel
specifier|private
name|JLabel
name|dateLabel
init|=
operator|new
name|JLabel
argument_list|(
literal|"date"
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|dateArea
specifier|private
name|JTextArea
name|dateArea
init|=
operator|new
name|JTextArea
argument_list|(
literal|"date"
argument_list|,
literal|2
argument_list|,
literal|25
argument_list|)
decl_stmt|;
DECL|field|dateScrollPane
specifier|private
name|JScrollPane
name|dateScrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|dateArea
argument_list|)
decl_stmt|;
DECL|field|siteLabel
specifier|private
name|JLabel
name|siteLabel
init|=
operator|new
name|JLabel
argument_list|(
literal|"site"
argument_list|,
name|JLabel
operator|.
name|CENTER
argument_list|)
decl_stmt|;
DECL|field|siteArea
specifier|private
name|JTextArea
name|siteArea
init|=
operator|new
name|JTextArea
argument_list|(
literal|"site"
argument_list|,
literal|2
argument_list|,
literal|25
argument_list|)
decl_stmt|;
DECL|field|siteScrollPane
specifier|private
name|JScrollPane
name|siteScrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|siteArea
argument_list|)
decl_stmt|;
DECL|field|listModel
name|DefaultListModel
argument_list|<
name|String
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
literal|"PDF comments"
expr_stmt|;
name|this
operator|.
name|setUpInformationPanel
argument_list|()
expr_stmt|;
name|listModel
operator|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
expr_stmt|;
try|try
block|{
name|this
operator|.
name|setUpPdfCommentsTab
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|setUpPdfCommentsTab ()
specifier|private
name|void
name|setUpPdfCommentsTab
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
name|commentList
operator|.
name|setModel
argument_list|(
name|listModel
argument_list|)
expr_stmt|;
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
expr_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|importedNotes
init|=
name|commentImporter
operator|.
name|importNotes
argument_list|(
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
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
decl_stmt|;
name|updateShownComments
argument_list|(
name|importedNotes
argument_list|)
expr_stmt|;
block|}
name|commentScrollPane
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|450
argument_list|,
literal|200
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|commentScrollPane
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
name|this
operator|.
name|add
argument_list|(
name|informationPanel
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
block|}
DECL|method|updateShownComments (HashMap<String, String> importedNotes)
specifier|private
name|void
name|updateShownComments
parameter_list|(
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|importedNotes
parameter_list|)
block|{
name|importedNotes
operator|.
name|values
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|forEach
argument_list|(
parameter_list|(
name|note
parameter_list|)
lambda|->
name|listModel
operator|.
name|addElement
argument_list|(
name|note
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setUpInformationPanel ()
specifier|private
name|void
name|setUpInformationPanel
parameter_list|()
block|{
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
name|siteArea
operator|.
name|setEditable
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|informationPanel
operator|.
name|add
argument_list|(
name|authorLabel
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|informationPanel
operator|.
name|add
argument_list|(
name|authorScrollPane
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|informationPanel
operator|.
name|add
argument_list|(
name|dateLabel
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|informationPanel
operator|.
name|add
argument_list|(
name|dateScrollPane
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|informationPanel
operator|.
name|add
argument_list|(
name|siteLabel
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|informationPanel
operator|.
name|add
argument_list|(
name|siteScrollPane
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

