begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
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
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Calendar
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
name|net
operator|.
name|sf
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
name|PDDocumentInformation
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
name|pdfimport
operator|.
name|PdfImporter
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
name|pdfimport
operator|.
name|PdfImporter
operator|.
name|ImportPdfFilesResult
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
name|JabRef
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
name|ExternalFileTypes
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
name|xmp
operator|.
name|XMPUtil
import|;
end_import

begin_comment
comment|/**  * Uses XMPUtils to get one BibEntry for a PDF-File.  * Also imports the non-XMP Data (PDDocument-Information) using XMPUtil.getBibtexEntryFromDocumentInformation.  * If data from more than one entry is read by XMPUtil then this entys are merged into one.  * @author Dan  * @version 12.11.2008 | 22:12:48  *  */
end_comment

begin_class
DECL|class|EntryFromPDFCreator
specifier|public
class|class
name|EntryFromPDFCreator
extends|extends
name|EntryFromFileCreator
block|{
DECL|method|EntryFromPDFCreator ()
specifier|public
name|EntryFromPDFCreator
parameter_list|()
block|{
name|super
argument_list|(
name|EntryFromPDFCreator
operator|.
name|getPDFExternalFileType
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|getPDFExternalFileType ()
specifier|private
specifier|static
name|ExternalFileType
name|getPDFExternalFileType
parameter_list|()
block|{
name|ExternalFileType
name|pdfFileType
init|=
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeByExt
argument_list|(
literal|"pdf"
argument_list|)
decl_stmt|;
if|if
condition|(
name|pdfFileType
operator|==
literal|null
condition|)
block|{
return|return
operator|new
name|ExternalFileType
argument_list|(
literal|"PDF"
argument_list|,
literal|"pdf"
argument_list|,
literal|"application/pdf"
argument_list|,
literal|"evince"
argument_list|,
literal|"pdfSmall"
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PDF_FILE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
return|;
block|}
return|return
name|pdfFileType
return|;
block|}
comment|/*      * (non-Javadoc)      *      * @see net.sf.jabref.imports.EntryFromFileCreator#accept(java.io.File)      *      * Accepts all Files having as suffix ".PDF" (in ignore case mode).      */
annotation|@
name|Override
DECL|method|accept (File f)
specifier|public
name|boolean
name|accept
parameter_list|(
name|File
name|f
parameter_list|)
block|{
return|return
operator|(
name|f
operator|!=
literal|null
operator|)
operator|&&
name|f
operator|.
name|getName
argument_list|()
operator|.
name|toUpperCase
argument_list|()
operator|.
name|endsWith
argument_list|(
literal|".PDF"
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|createBibtexEntry (File pdfFile)
specifier|protected
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|createBibtexEntry
parameter_list|(
name|File
name|pdfFile
parameter_list|)
block|{
if|if
condition|(
operator|!
name|accept
argument_list|(
name|pdfFile
argument_list|)
condition|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
block|}
name|PdfImporter
name|pi
init|=
operator|new
name|PdfImporter
argument_list|(
name|JabRef
operator|.
name|jrf
argument_list|,
name|JabRef
operator|.
name|jrf
operator|.
name|getCurrentBasePanel
argument_list|()
argument_list|,
name|JabRef
operator|.
name|jrf
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|mainTable
argument_list|,
operator|-
literal|1
argument_list|)
decl_stmt|;
name|String
index|[]
name|fileNames
init|=
block|{
name|pdfFile
operator|.
name|toString
argument_list|()
block|}
decl_stmt|;
name|ImportPdfFilesResult
name|res
init|=
name|pi
operator|.
name|importPdfFiles
argument_list|(
name|fileNames
argument_list|)
decl_stmt|;
assert|assert
name|res
operator|.
name|getEntries
argument_list|()
operator|.
name|size
argument_list|()
operator|==
literal|1
assert|;
return|return
name|Optional
operator|.
name|of
argument_list|(
name|res
operator|.
name|getEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
comment|/*addEntryDataFromPDDocumentInformation(pdfFile, entry);         addEntryDataFromXMP(pdfFile, entry);          if (entry.getField("title") == null) {         	entry.setField("title", pdfFile.getName());         }          return entry;*/
block|}
comment|/** Adds entry data read from the PDDocument information of the file.      * @param pdfFile      * @param entry      */
DECL|method|addEntryDataFromPDDocumentInformation (File pdfFile, BibEntry entry)
specifier|private
name|void
name|addEntryDataFromPDDocumentInformation
parameter_list|(
name|File
name|pdfFile
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
try|try
init|(
name|PDDocument
name|document
init|=
name|PDDocument
operator|.
name|load
argument_list|(
name|pdfFile
operator|.
name|getAbsoluteFile
argument_list|()
argument_list|)
init|)
block|{
name|PDDocumentInformation
name|pdfDocInfo
init|=
name|document
operator|.
name|getDocumentInformation
argument_list|()
decl_stmt|;
if|if
condition|(
name|pdfDocInfo
operator|!=
literal|null
condition|)
block|{
name|Optional
argument_list|<
name|BibEntry
argument_list|>
name|entryDI
init|=
name|XMPUtil
operator|.
name|getBibtexEntryFromDocumentInformation
argument_list|(
name|document
operator|.
name|getDocumentInformation
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|entryDI
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|addEntryDataToEntry
argument_list|(
name|entry
argument_list|,
name|entryDI
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|Calendar
name|creationDate
init|=
name|pdfDocInfo
operator|.
name|getCreationDate
argument_list|()
decl_stmt|;
if|if
condition|(
name|creationDate
operator|!=
literal|null
condition|)
block|{
comment|// default time stamp follows ISO-8601. Reason: https://xkcd.com/1179/
name|String
name|date
init|=
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyy-MM-dd"
argument_list|)
operator|.
name|format
argument_list|(
name|creationDate
operator|.
name|getTime
argument_list|()
argument_list|)
decl_stmt|;
name|appendToField
argument_list|(
name|entry
argument_list|,
literal|"timestamp"
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|pdfDocInfo
operator|.
name|getCustomMetadataValue
argument_list|(
literal|"bibtex/bibtexkey"
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|entry
operator|.
name|setId
argument_list|(
name|pdfDocInfo
operator|.
name|getCustomMetadataValue
argument_list|(
literal|"bibtex/bibtexkey"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// no canceling here, just no data added.
block|}
block|}
comment|/**      * Adds all data Found in all the entrys of this XMP file to the given      * entry. This was implemented without having much knowledge of the XMP      * format.      *      * @param aFile      * @param entry      */
DECL|method|addEntryDataFromXMP (File aFile, BibEntry entry)
specifier|private
name|void
name|addEntryDataFromXMP
parameter_list|(
name|File
name|aFile
parameter_list|,
name|BibEntry
name|entry
parameter_list|)
block|{
try|try
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entrys
init|=
name|XMPUtil
operator|.
name|readXMP
argument_list|(
name|aFile
operator|.
name|getAbsoluteFile
argument_list|()
argument_list|)
decl_stmt|;
name|addEntrysToEntry
argument_list|(
name|entry
argument_list|,
name|entrys
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
comment|// no canceling here, just no data added.
block|}
block|}
annotation|@
name|Override
DECL|method|getFormatName ()
specifier|public
name|String
name|getFormatName
parameter_list|()
block|{
return|return
literal|"PDF"
return|;
block|}
block|}
end_class

end_unit

