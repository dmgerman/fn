begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.xmp
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|xmp
package|;
end_package

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
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|Month
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
name|StandardEntryType
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
name|field
operator|.
name|InternalField
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
name|field
operator|.
name|StandardField
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
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|BeforeEach
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|io
operator|.
name|TempDir
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertEquals
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|when
import|;
end_import

begin_class
DECL|class|XmpUtilWriterTest
class|class
name|XmpUtilWriterTest
block|{
DECL|field|olly2018
specifier|private
specifier|static
name|BibEntry
name|olly2018
decl_stmt|;
DECL|field|toral2006
specifier|private
specifier|static
name|BibEntry
name|toral2006
decl_stmt|;
DECL|field|vapnik2000
specifier|private
specifier|static
name|BibEntry
name|vapnik2000
decl_stmt|;
DECL|field|xmpPreferences
specifier|private
name|XmpPreferences
name|xmpPreferences
decl_stmt|;
DECL|method|initBibEntries ()
specifier|private
name|void
name|initBibEntries
parameter_list|()
block|{
name|olly2018
operator|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Article
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setCiteKey
argument_list|(
literal|"Olly2018"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Olly and Johannes"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"Stefan's palace"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|JOURNAL
argument_list|,
literal|"Test Journal"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|VOLUME
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|NUMBER
argument_list|,
literal|"1"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"1-2"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setMonth
argument_list|(
name|Month
operator|.
name|MARCH
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ISSN
argument_list|,
literal|"978-123-123"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|NOTE
argument_list|,
literal|"NOTE"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|ABSTRACT
argument_list|,
literal|"ABSTRACT"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|COMMENT
argument_list|,
literal|"COMMENT"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10/3212.3123"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|FILE
argument_list|,
literal|":article_dublinCore.pdf:PDF"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|InternalField
operator|.
name|GROUPS
argument_list|,
literal|"NO"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|HOWPUBLISHED
argument_list|,
literal|"online"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|KEYWORDS
argument_list|,
literal|"k1, k2"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|InternalField
operator|.
name|OWNER
argument_list|,
literal|"me"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|REVIEW
argument_list|,
literal|"review"
argument_list|)
expr_stmt|;
name|olly2018
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
literal|"https://www.olly2018.edu"
argument_list|)
expr_stmt|;
name|toral2006
operator|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|InProceedings
argument_list|)
expr_stmt|;
name|toral2006
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Toral, Antonio and Munoz, Rafael"
argument_list|)
expr_stmt|;
name|toral2006
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"A proposal to automatically build and maintain gazetteers for Named Entity Recognition by using Wikipedia"
argument_list|)
expr_stmt|;
name|toral2006
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|BOOKTITLE
argument_list|,
literal|"Proceedings of EACL"
argument_list|)
expr_stmt|;
name|toral2006
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PAGES
argument_list|,
literal|"56--61"
argument_list|)
expr_stmt|;
name|toral2006
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|EPRINTTYPE
argument_list|,
literal|"asdf"
argument_list|)
expr_stmt|;
name|toral2006
operator|.
name|setField
argument_list|(
name|InternalField
operator|.
name|OWNER
argument_list|,
literal|"Ich"
argument_list|)
expr_stmt|;
name|toral2006
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|URL
argument_list|,
literal|"www.url.de"
argument_list|)
expr_stmt|;
name|vapnik2000
operator|=
operator|new
name|BibEntry
argument_list|(
name|StandardEntryType
operator|.
name|Book
argument_list|)
expr_stmt|;
name|vapnik2000
operator|.
name|setCiteKey
argument_list|(
literal|"vapnik2000"
argument_list|)
expr_stmt|;
name|vapnik2000
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|TITLE
argument_list|,
literal|"The Nature of Statistical Learning Theory"
argument_list|)
expr_stmt|;
name|vapnik2000
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|PUBLISHER
argument_list|,
literal|"Springer Science + Business Media"
argument_list|)
expr_stmt|;
name|vapnik2000
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|AUTHOR
argument_list|,
literal|"Vladimir N. Vapnik"
argument_list|)
expr_stmt|;
name|vapnik2000
operator|.
name|setField
argument_list|(
name|StandardField
operator|.
name|DOI
argument_list|,
literal|"10.1007/978-1-4757-3264-1"
argument_list|)
expr_stmt|;
name|vapnik2000
operator|.
name|setField
argument_list|(
name|InternalField
operator|.
name|OWNER
argument_list|,
literal|"Ich"
argument_list|)
expr_stmt|;
block|}
comment|/**      * Create a temporary PDF-file with a single empty page.      */
annotation|@
name|BeforeEach
DECL|method|setUp ()
name|void
name|setUp
parameter_list|()
block|{
name|xmpPreferences
operator|=
name|mock
argument_list|(
name|XmpPreferences
operator|.
name|class
argument_list|)
expr_stmt|;
comment|// The code assumes privacy filters to be off
name|when
argument_list|(
name|xmpPreferences
operator|.
name|isUseXMPPrivacyFilter
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|when
argument_list|(
name|xmpPreferences
operator|.
name|getKeywordSeparator
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|','
argument_list|)
expr_stmt|;
name|this
operator|.
name|initBibEntries
argument_list|()
expr_stmt|;
block|}
comment|/**      * Test for writing a PDF file with a single DublinCore metadata entry.      */
annotation|@
name|Test
DECL|method|testWriteXmp (@empDir Path tempDir)
name|void
name|testWriteXmp
parameter_list|(
annotation|@
name|TempDir
name|Path
name|tempDir
parameter_list|)
throws|throws
name|IOException
throws|,
name|TransformerException
block|{
name|Path
name|pdfFile
init|=
name|this
operator|.
name|createDefaultFile
argument_list|(
literal|"JabRef_writeSingle.pdf"
argument_list|,
name|tempDir
argument_list|)
decl_stmt|;
comment|// read a bib entry from the tests before
name|BibEntry
name|entry
init|=
name|vapnik2000
decl_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"WriteXMPTest"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setId
argument_list|(
literal|"ID4711"
argument_list|)
expr_stmt|;
comment|// write the changed bib entry to the PDF
name|XmpUtilWriter
operator|.
name|writeXmp
argument_list|(
name|pdfFile
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|entry
argument_list|,
literal|null
argument_list|,
name|xmpPreferences
argument_list|)
expr_stmt|;
comment|// read entry again
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entriesWritten
init|=
name|XmpUtilReader
operator|.
name|readXmp
argument_list|(
name|pdfFile
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|,
name|xmpPreferences
argument_list|)
decl_stmt|;
name|BibEntry
name|entryWritten
init|=
name|entriesWritten
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|entryWritten
operator|.
name|clearField
argument_list|(
name|StandardField
operator|.
name|FILE
argument_list|)
expr_stmt|;
comment|// compare the two entries
name|assertEquals
argument_list|(
name|entry
argument_list|,
name|entryWritten
argument_list|)
expr_stmt|;
block|}
comment|/**      * Test, which writes multiple metadata entries to a PDF and reads them again to test the size.      */
annotation|@
name|Test
DECL|method|testWriteMultipleBibEntries (@empDir Path tempDir)
name|void
name|testWriteMultipleBibEntries
parameter_list|(
annotation|@
name|TempDir
name|Path
name|tempDir
parameter_list|)
throws|throws
name|IOException
throws|,
name|TransformerException
block|{
name|Path
name|pdfFile
init|=
name|this
operator|.
name|createDefaultFile
argument_list|(
literal|"JabRef_writeMultiple.pdf"
argument_list|,
name|tempDir
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|olly2018
argument_list|,
name|vapnik2000
argument_list|,
name|toral2006
argument_list|)
decl_stmt|;
name|XmpUtilWriter
operator|.
name|writeXmp
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|pdfFile
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|entries
argument_list|,
literal|null
argument_list|,
name|xmpPreferences
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entryList
init|=
name|XmpUtilReader
operator|.
name|readXmp
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|pdfFile
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|xmpPreferences
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|3
argument_list|,
name|entryList
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|createDefaultFile (String fileName, Path tempDir)
specifier|private
name|Path
name|createDefaultFile
parameter_list|(
name|String
name|fileName
parameter_list|,
name|Path
name|tempDir
parameter_list|)
throws|throws
name|IOException
block|{
comment|// create a default PDF
name|Path
name|pdfFile
init|=
name|tempDir
operator|.
name|resolve
argument_list|(
name|fileName
argument_list|)
decl_stmt|;
try|try
init|(
name|PDDocument
name|pdf
init|=
operator|new
name|PDDocument
argument_list|()
init|)
block|{
comment|// Need a single page to open in Acrobat
name|pdf
operator|.
name|addPage
argument_list|(
operator|new
name|PDPage
argument_list|()
argument_list|)
expr_stmt|;
name|pdf
operator|.
name|save
argument_list|(
name|pdfFile
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|pdfFile
return|;
block|}
block|}
end_class

end_unit

