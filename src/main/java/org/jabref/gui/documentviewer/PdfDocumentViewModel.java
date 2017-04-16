begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.documentviewer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|documentviewer
package|;
end_package

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|ObservableList
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
name|PDPage
import|;
end_import

begin_class
DECL|class|PdfDocumentViewModel
specifier|public
class|class
name|PdfDocumentViewModel
extends|extends
name|DocumentViewModel
block|{
DECL|field|document
specifier|private
specifier|final
name|PDDocument
name|document
decl_stmt|;
DECL|method|PdfDocumentViewModel (PDDocument document)
specifier|public
name|PdfDocumentViewModel
parameter_list|(
name|PDDocument
name|document
parameter_list|)
block|{
name|this
operator|.
name|document
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|document
argument_list|)
expr_stmt|;
name|this
operator|.
name|maxPagesProperty
argument_list|()
operator|.
name|set
argument_list|(
name|document
operator|.
name|getNumberOfPages
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getPages ()
specifier|public
name|ObservableList
argument_list|<
name|DocumentPageViewModel
argument_list|>
name|getPages
parameter_list|()
block|{
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
name|List
argument_list|<
name|PDPage
argument_list|>
name|pages
init|=
name|document
operator|.
name|getDocumentCatalog
argument_list|()
operator|.
name|getAllPages
argument_list|()
decl_stmt|;
comment|// There is apparently no neat way to get the page number from a PDPage...thus this old-style for loop
name|List
argument_list|<
name|PdfDocumentPageViewModel
argument_list|>
name|pdfPages
init|=
operator|new
name|ArrayList
argument_list|<>
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
name|pages
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|pdfPages
operator|.
name|add
argument_list|(
operator|new
name|PdfDocumentPageViewModel
argument_list|(
name|pages
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|i
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|pdfPages
argument_list|)
return|;
block|}
block|}
end_class

end_unit

