begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|importer
operator|.
name|fileformat
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|ByteArrayInputStream
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
name|io
operator|.
name|InputStream
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
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
name|Collections
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
name|JabRefPreferences
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
name|bibtex
operator|.
name|BibEntryAssert
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
name|importer
operator|.
name|OutputPrinterToNull
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
name|org
operator|.
name|junit
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Before
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
DECL|class|MedlinePlainImporterTest
specifier|public
class|class
name|MedlinePlainImporterTest
block|{
DECL|field|emptyFileStream
specifier|private
specifier|final
name|InputStream
name|emptyFileStream
init|=
name|streamForString
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|importer
specifier|private
name|MedlinePlainImporter
name|importer
decl_stmt|;
DECL|method|streamForString (String string)
specifier|private
name|InputStream
name|streamForString
parameter_list|(
name|String
name|string
parameter_list|)
block|{
return|return
operator|new
name|ByteArrayInputStream
argument_list|(
name|string
operator|.
name|getBytes
argument_list|(
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|importer
operator|=
operator|new
name|MedlinePlainImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormat ()
specifier|public
name|void
name|testIsRecognizedFormat
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|list
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"CopacImporterTest1.txt"
argument_list|,
literal|"CopacImporterTest2.txt"
argument_list|,
literal|"IsiImporterTest1.isi"
argument_list|,
literal|"IsiImporterTestInspec.isi"
argument_list|,
literal|"IsiImporterTestWOS.isi"
argument_list|,
literal|"IsiImporterTestMedline.isi"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|str
range|:
name|list
control|)
block|{
try|try
init|(
name|InputStream
name|is
init|=
name|MedlinePlainImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|str
argument_list|)
init|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|is
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|testIsNotRecognizedFormat ()
specifier|public
name|void
name|testIsNotRecognizedFormat
parameter_list|()
throws|throws
name|Exception
block|{
name|List
argument_list|<
name|String
argument_list|>
name|list
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"MedlinePlainImporterTestMultipleEntries.txt"
argument_list|,
literal|"MedlinePlainImporterTestCompleteEntry.txt"
argument_list|,
literal|"MedlinePlainImporterTestMultiAbstract.txt"
argument_list|,
literal|"MedlinePlainImporterTestMultiTitle.txt"
argument_list|,
literal|"MedlinePlainImporterTestDOI.txt"
argument_list|,
literal|"MedlinePlainImporterTestInproceeding.txt"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|str
range|:
name|list
control|)
block|{
try|try
init|(
name|InputStream
name|is
init|=
name|MedlinePlainImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|str
argument_list|)
init|)
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|is
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|testIsNotEmptyFileRecognizedFormat ()
specifier|public
name|void
name|testIsNotEmptyFileRecognizedFormat
parameter_list|()
throws|throws
name|IOException
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|emptyFileStream
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportMultipleEntriesInSingleFile ()
specifier|public
name|void
name|testImportMultipleEntriesInSingleFile
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|is
init|=
name|MedlinePlainImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"MedlinePlainImporterTestMultipleEntries.txt"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|is
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|7
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntry
name|testEntry
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"article"
argument_list|,
name|testEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|testEntry
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Long, Vicky and Marland, Hilary"
argument_list|,
name|testEntry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"From danger and motherhood to health and beauty: health advice for the factory girl in early twentieth-century Britain."
argument_list|,
name|testEntry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|testEntry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"conference"
argument_list|,
name|testEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"06"
argument_list|,
name|testEntry
operator|.
name|getField
argument_list|(
literal|"month"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|testEntry
operator|.
name|getField
argument_list|(
literal|"author"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
name|testEntry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|testEntry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"book"
argument_list|,
name|testEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"This is a Testtitle: This title should be appended: This title should also be appended. Another append to the Title? LastTitle"
argument_list|,
name|testEntry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|testEntry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|3
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"techreport"
argument_list|,
name|testEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|testEntry
operator|.
name|getField
argument_list|(
literal|"doi"
argument_list|)
argument_list|)
expr_stmt|;
name|testEntry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|4
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"inproceedings"
argument_list|,
name|testEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Inproceedings book title"
argument_list|,
name|testEntry
operator|.
name|getField
argument_list|(
literal|"booktitle"
argument_list|)
argument_list|)
expr_stmt|;
name|testEntry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"proceedings"
argument_list|,
name|testEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|testEntry
operator|=
name|entries
operator|.
name|get
argument_list|(
literal|6
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"misc"
argument_list|,
name|testEntry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testEmptyFileImport ()
specifier|public
name|void
name|testEmptyFileImport
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|emptyEntries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|emptyFileStream
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|emptyEntries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportSingleEntriesInSingleFiles ()
specifier|public
name|void
name|testImportSingleEntriesInSingleFiles
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|testFiles
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"MedlinePlainImporterTestCompleteEntry"
argument_list|,
literal|"MedlinePlainImporterTestMultiAbstract"
argument_list|,
literal|"MedlinePlainImporterTestMultiTitle"
argument_list|,
literal|"MedlinePlainImporterTestDOI"
argument_list|,
literal|"MedlinePlainImporterTestInproceeding"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|testFile
range|:
name|testFiles
control|)
block|{
name|String
name|medlineFile
init|=
name|testFile
operator|+
literal|".txt"
decl_stmt|;
name|String
name|bibtexFile
init|=
name|testFile
operator|+
literal|".bib"
decl_stmt|;
name|assertImportOfMedlineFileEqualsBibtexFile
argument_list|(
name|medlineFile
argument_list|,
name|bibtexFile
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|assertImportOfMedlineFileEqualsBibtexFile (String medlineFile, String bibtexFile)
specifier|private
name|void
name|assertImportOfMedlineFileEqualsBibtexFile
parameter_list|(
name|String
name|medlineFile
parameter_list|,
name|String
name|bibtexFile
parameter_list|)
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|is
init|=
name|MedlinePlainImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|medlineFile
argument_list|)
init|;
name|InputStream
name|nis
operator|=
name|MedlinePlainImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|bibtexFile
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|is
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|entries
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|nis
argument_list|,
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testMultiLineComments ()
specifier|public
name|void
name|testMultiLineComments
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|streamForString
argument_list|(
literal|"PMID-22664220"
operator|+
literal|"\n"
operator|+
literal|"CON - Comment1"
operator|+
literal|"\n"
operator|+
literal|"CIN - Comment2"
operator|+
literal|"\n"
operator|+
literal|"EIN - Comment3"
operator|+
literal|"\n"
operator|+
literal|"EFR - Comment4"
operator|+
literal|"\n"
operator|+
literal|"CRI - Comment5"
operator|+
literal|"\n"
operator|+
literal|"CRF - Comment6"
operator|+
literal|"\n"
operator|+
literal|"PRIN- Comment7"
operator|+
literal|"\n"
operator|+
literal|"PROF- Comment8"
operator|+
literal|"\n"
operator|+
literal|"RPI - Comment9"
operator|+
literal|"\n"
operator|+
literal|"RPF - Comment10"
operator|+
literal|"\n"
operator|+
literal|"RIN - Comment11"
operator|+
literal|"\n"
operator|+
literal|"ROF - Comment12"
operator|+
literal|"\n"
operator|+
literal|"UIN - Comment13"
operator|+
literal|"\n"
operator|+
literal|"UOF - Comment14"
operator|+
literal|"\n"
operator|+
literal|"SPIN- Comment15"
operator|+
literal|"\n"
operator|+
literal|"ORI - Comment16"
argument_list|)
init|;
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|stream
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|BibEntry
name|expectedEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
literal|"comment"
argument_list|,
literal|"Comment1"
operator|+
literal|"\n"
operator|+
literal|"Comment2"
operator|+
literal|"\n"
operator|+
literal|"Comment3"
operator|+
literal|"\n"
operator|+
literal|"Comment4"
operator|+
literal|"\n"
operator|+
literal|"Comment5"
operator|+
literal|"\n"
operator|+
literal|"Comment6"
operator|+
literal|"\n"
operator|+
literal|"Comment7"
operator|+
literal|"\n"
operator|+
literal|"Comment8"
operator|+
literal|"\n"
operator|+
literal|"Comment9"
operator|+
literal|"\n"
operator|+
literal|"Comment10"
operator|+
literal|"\n"
operator|+
literal|"Comment11"
operator|+
literal|"\n"
operator|+
literal|"Comment12"
operator|+
literal|"\n"
operator|+
literal|"Comment13"
operator|+
literal|"\n"
operator|+
literal|"Comment14"
operator|+
literal|"\n"
operator|+
literal|"Comment15"
operator|+
literal|"\n"
operator|+
literal|"Comment16"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|expectedEntry
argument_list|)
argument_list|,
name|actualEntries
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testKeyWords ()
specifier|public
name|void
name|testKeyWords
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|streamForString
argument_list|(
literal|"PMID-22664795"
operator|+
literal|"\n"
operator|+
literal|"MH  - Female"
operator|+
literal|"\n"
operator|+
literal|"OT  - Male"
argument_list|)
init|;
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|stream
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|BibEntry
name|expectedEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|expectedEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"Female, Male"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|expectedEntry
argument_list|)
argument_list|,
name|actualEntries
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testAllArticleTypes ()
specifier|public
name|void
name|testAllArticleTypes
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|streamForString
argument_list|(
literal|"PMID-22664795"
operator|+
literal|"\n"
operator|+
literal|"PT  - journal article"
operator|+
literal|"\n"
operator|+
literal|"PT  - classical article"
operator|+
literal|"\n"
operator|+
literal|"PT  - corrected and republished article"
operator|+
literal|"\n"
operator|+
literal|"PT  - introductory journal article"
operator|+
literal|"\n"
operator|+
literal|"PT  - newspaper article"
argument_list|)
init|;
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|actualEntries
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|stream
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
name|BibEntry
name|expectedEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|expectedEntry
operator|.
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
name|expectedEntry
argument_list|)
argument_list|,
name|actualEntries
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testGetFormatName ()
specifier|public
name|void
name|testGetFormatName
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"MedlinePlain"
argument_list|,
name|importer
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCLIId ()
specifier|public
name|void
name|testGetCLIId
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"medlineplain"
argument_list|,
name|importer
operator|.
name|getCLIId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

