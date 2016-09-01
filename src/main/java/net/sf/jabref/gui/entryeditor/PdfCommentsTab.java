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
DECL|field|scrollPane
specifier|private
specifier|final
name|JScrollPane
name|scrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|commentList
argument_list|)
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
name|setUpPdfCommentsTab
argument_list|()
expr_stmt|;
block|}
DECL|method|setUpPdfCommentsTab ()
specifier|private
name|void
name|setUpPdfCommentsTab
parameter_list|()
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"isPresent"
argument_list|)
expr_stmt|;
name|DefaultListModel
argument_list|<
name|String
argument_list|>
name|listModel
init|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
decl_stmt|;
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
name|field
operator|.
name|get
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|note
range|:
name|importedNotes
operator|.
name|values
argument_list|()
control|)
name|listModel
operator|.
name|addElement
argument_list|(
name|note
argument_list|)
expr_stmt|;
block|}
name|scrollPane
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
name|scrollPane
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

