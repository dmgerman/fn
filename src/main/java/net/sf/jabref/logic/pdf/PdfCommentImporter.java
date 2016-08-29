begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.pdf
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|pdf
package|;
end_package

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
name|interactive
operator|.
name|annotation
operator|.
name|PDAnnotation
import|;
end_import

begin_class
DECL|class|PdfCommentImporter
specifier|public
class|class
name|PdfCommentImporter
block|{
DECL|field|pdfPages
specifier|private
name|List
name|pdfPages
decl_stmt|;
DECL|field|page
specifier|private
name|PDPage
name|page
decl_stmt|;
DECL|method|PdfCommentImporter ()
specifier|public
name|PdfCommentImporter
parameter_list|()
block|{      }
comment|/**      * Imports the comments from a pdf specified by its URI      * @param pathToPDF the URI specifying the document      * @return a hasmap with the unique name as key and the notes content as value      */
DECL|method|importNotes (final String pathToPDF)
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|importNotes
parameter_list|(
specifier|final
name|String
name|pathToPDF
parameter_list|)
block|{
name|PDDocument
name|pdf
decl_stmt|;
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|annotationsMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
try|try
block|{
name|pdf
operator|=
name|importPdfFile
argument_list|(
name|pathToPDF
argument_list|)
expr_stmt|;
name|pdfPages
operator|=
name|pdf
operator|.
name|getDocumentCatalog
argument_list|()
operator|.
name|getAllPages
argument_list|()
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
name|pdfPages
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|page
operator|=
operator|(
name|PDPage
operator|)
name|pdfPages
operator|.
name|get
argument_list|(
name|i
argument_list|)
expr_stmt|;
for|for
control|(
name|PDAnnotation
name|annotation
range|:
name|page
operator|.
name|getAnnotations
argument_list|()
control|)
block|{
name|annotationsMap
operator|.
name|put
argument_list|(
name|annotation
operator|.
name|getAnnotationName
argument_list|()
argument_list|,
name|annotation
operator|.
name|getContents
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
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
return|return
name|annotationsMap
return|;
block|}
DECL|method|importPdfFile (final String pathToPDF)
specifier|private
name|PDDocument
name|importPdfFile
parameter_list|(
specifier|final
name|String
name|pathToPDF
parameter_list|)
throws|throws
name|IOException
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|pathToPDF
argument_list|)
decl_stmt|;
return|return
name|PDDocument
operator|.
name|load
argument_list|(
name|file
argument_list|)
return|;
block|}
block|}
end_class

end_unit

