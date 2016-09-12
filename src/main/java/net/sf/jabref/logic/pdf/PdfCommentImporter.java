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
name|awt
operator|.
name|geom
operator|.
name|Rectangle2D
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibDatabaseContext
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
name|pdf
operator|.
name|PdfComment
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
name|cos
operator|.
name|COSArray
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
name|cos
operator|.
name|COSFloat
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
name|cos
operator|.
name|COSName
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
name|common
operator|.
name|PDRectangle
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
name|fdf
operator|.
name|FDFAnnotationHighlight
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

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|pdfbox
operator|.
name|util
operator|.
name|PDFTextStripperByArea
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
comment|/**      * Imports the comments from a pdf specified by its URI      *      * @param document a PDDocument to get the annotations from      * @return a hashmap with the unique name as key and the notes content as value      */
DECL|method|importNotes (final PDDocument document)
specifier|public
name|HashMap
argument_list|<
name|String
argument_list|,
name|PdfComment
argument_list|>
name|importNotes
parameter_list|(
specifier|final
name|PDDocument
name|document
parameter_list|)
block|{
name|HashMap
argument_list|<
name|String
argument_list|,
name|PdfComment
argument_list|>
name|annotationsMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|pdfPages
operator|=
name|document
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
try|try
block|{
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
if|if
condition|(
name|annotation
operator|.
name|getSubtype
argument_list|()
operator|.
name|equals
argument_list|(
name|FDFAnnotationHighlight
operator|.
name|SUBTYPE
argument_list|)
condition|)
block|{
name|PDFTextStripperByArea
name|stripperByArea
init|=
operator|new
name|PDFTextStripperByArea
argument_list|()
decl_stmt|;
name|COSArray
name|quadsArray
init|=
operator|(
name|COSArray
operator|)
name|annotation
operator|.
name|getDictionary
argument_list|()
operator|.
name|getDictionaryObject
argument_list|(
name|COSName
operator|.
name|getPDFName
argument_list|(
literal|"QuadPoints"
argument_list|)
argument_list|)
decl_stmt|;
name|String
name|highlightedText
init|=
literal|null
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|1
init|,
name|k
init|=
literal|0
init|;
name|j
operator|<=
operator|(
name|quadsArray
operator|.
name|size
argument_list|()
operator|/
literal|8
operator|)
condition|;
name|j
operator|++
control|)
block|{
name|COSFloat
name|upperLeftX
init|=
operator|(
name|COSFloat
operator|)
name|quadsArray
operator|.
name|get
argument_list|(
literal|0
operator|+
name|k
argument_list|)
decl_stmt|;
name|COSFloat
name|upperLeftY
init|=
operator|(
name|COSFloat
operator|)
name|quadsArray
operator|.
name|get
argument_list|(
literal|1
operator|+
name|k
argument_list|)
decl_stmt|;
name|COSFloat
name|upperRightX
init|=
operator|(
name|COSFloat
operator|)
name|quadsArray
operator|.
name|get
argument_list|(
literal|2
operator|+
name|k
argument_list|)
decl_stmt|;
name|COSFloat
name|upperRightY
init|=
operator|(
name|COSFloat
operator|)
name|quadsArray
operator|.
name|get
argument_list|(
literal|3
operator|+
name|k
argument_list|)
decl_stmt|;
name|COSFloat
name|lowerLeftX
init|=
operator|(
name|COSFloat
operator|)
name|quadsArray
operator|.
name|get
argument_list|(
literal|4
operator|+
name|k
argument_list|)
decl_stmt|;
name|COSFloat
name|lowerLeftY
init|=
operator|(
name|COSFloat
operator|)
name|quadsArray
operator|.
name|get
argument_list|(
literal|5
operator|+
name|k
argument_list|)
decl_stmt|;
name|k
operator|+=
literal|8
expr_stmt|;
name|float
name|ulx
init|=
name|upperLeftX
operator|.
name|floatValue
argument_list|()
operator|-
literal|1
decl_stmt|;
name|float
name|uly
init|=
name|upperLeftY
operator|.
name|floatValue
argument_list|()
decl_stmt|;
name|float
name|width
init|=
name|upperRightX
operator|.
name|floatValue
argument_list|()
operator|-
name|lowerLeftX
operator|.
name|floatValue
argument_list|()
decl_stmt|;
name|float
name|height
init|=
name|upperRightY
operator|.
name|floatValue
argument_list|()
operator|-
name|lowerLeftY
operator|.
name|floatValue
argument_list|()
decl_stmt|;
name|PDRectangle
name|pageSize
init|=
name|page
operator|.
name|getMediaBox
argument_list|()
decl_stmt|;
name|uly
operator|=
name|pageSize
operator|.
name|getHeight
argument_list|()
operator|-
name|uly
expr_stmt|;
name|Rectangle2D
operator|.
name|Float
name|rectangle
init|=
operator|new
name|Rectangle2D
operator|.
name|Float
argument_list|(
name|ulx
argument_list|,
name|uly
argument_list|,
name|width
argument_list|,
name|height
argument_list|)
decl_stmt|;
name|stripperByArea
operator|.
name|addRegion
argument_list|(
literal|"highlightedRegion"
argument_list|,
name|rectangle
argument_list|)
expr_stmt|;
name|stripperByArea
operator|.
name|extractRegions
argument_list|(
name|page
argument_list|)
expr_stmt|;
name|String
name|highlightedTextInLine
init|=
name|stripperByArea
operator|.
name|getTextForRegion
argument_list|(
literal|"highlightedRegion"
argument_list|)
decl_stmt|;
if|if
condition|(
name|j
operator|>
literal|1
condition|)
block|{
name|highlightedText
operator|=
name|highlightedText
operator|.
name|concat
argument_list|(
name|highlightedTextInLine
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|highlightedText
operator|=
name|highlightedTextInLine
expr_stmt|;
block|}
block|}
name|annotation
operator|.
name|setContents
argument_list|(
name|highlightedText
argument_list|)
expr_stmt|;
block|}
name|annotationsMap
operator|.
name|put
argument_list|(
name|annotation
operator|.
name|getAnnotationName
argument_list|()
argument_list|,
operator|new
name|PdfComment
argument_list|(
name|annotation
argument_list|,
name|i
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e1
parameter_list|)
block|{
name|e1
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
try|try
block|{
name|document
operator|.
name|close
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
return|return
name|annotationsMap
return|;
block|}
DECL|method|importPdfFile (final List<BibEntry> entryList, final BibDatabaseContext bibDatabaseContext)
specifier|public
name|List
argument_list|<
name|PDDocument
argument_list|>
name|importPdfFile
parameter_list|(
specifier|final
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entryList
parameter_list|,
specifier|final
name|BibDatabaseContext
name|bibDatabaseContext
parameter_list|)
throws|throws
name|IOException
block|{
specifier|final
name|List
argument_list|<
name|File
argument_list|>
name|files
init|=
name|FileUtil
operator|.
name|getListOfLinkedFiles
argument_list|(
name|entryList
argument_list|,
name|bibDatabaseContext
operator|.
name|getFileDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getFileDirectoryPreferences
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|ArrayList
argument_list|<
name|PDDocument
argument_list|>
name|documents
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|File
name|linkedFile
range|:
name|files
control|)
block|{
if|if
condition|(
name|linkedFile
operator|.
name|getName
argument_list|()
operator|.
name|toLowerCase
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".pdf"
argument_list|)
condition|)
block|{
name|documents
operator|.
name|add
argument_list|(
name|PDDocument
operator|.
name|load
argument_list|(
name|linkedFile
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|documents
return|;
block|}
block|}
end_class

end_unit

