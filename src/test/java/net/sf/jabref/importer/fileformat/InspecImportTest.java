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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|*
import|;
end_import

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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
operator|.
name|BibtexEntryAssert
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
name|BibtexEntryTypes
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
DECL|class|InspecImportTest
specifier|public
class|class
name|InspecImportTest
block|{
DECL|field|inspecImp
specifier|private
name|InspecImporter
name|inspecImp
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
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
name|this
operator|.
name|inspecImp
operator|=
operator|new
name|InspecImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormatAccept ()
specifier|public
name|void
name|testIsRecognizedFormatAccept
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|testList
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"InspecImportTest.txt"
argument_list|,
literal|"InspecImportTest2.txt"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|str
range|:
name|testList
control|)
block|{
try|try
init|(
name|InputStream
name|inStream
init|=
name|InspecImportTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|str
argument_list|)
init|)
block|{
name|assertTrue
argument_list|(
name|inspecImp
operator|.
name|isRecognizedFormat
argument_list|(
name|inStream
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormatReject ()
specifier|public
name|void
name|testIsRecognizedFormatReject
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|testList
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"CopacImporterTest1.txt"
argument_list|,
literal|"CopacImporterTest2.txt"
argument_list|,
literal|"IEEEImport1.txt"
argument_list|,
literal|"IsiImporterTest1.isi"
argument_list|,
literal|"IsiImporterTestInspec.isi"
argument_list|,
literal|"IsiImporterTestWOS.isi"
argument_list|,
literal|"IsiImporterTestMedline.isi"
argument_list|,
literal|"RisImporterTest1.ris"
argument_list|,
literal|"InspecImportTestFalse.txt"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|str
range|:
name|testList
control|)
block|{
try|try
init|(
name|InputStream
name|inStream
init|=
name|InspecImportTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|str
argument_list|)
init|)
block|{
name|assertFalse
argument_list|(
name|inspecImp
operator|.
name|isRecognizedFormat
argument_list|(
name|inStream
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
DECL|method|testCompleteBibtexEntryOnJournalPaperImport ()
specifier|public
name|void
name|testCompleteBibtexEntryOnJournalPaperImport
parameter_list|()
throws|throws
name|IOException
block|{
name|BibEntry
name|shouldBeEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|shouldBeEntry
operator|.
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|shouldBeEntry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"The SIS project : software reuse with a natural language approach"
argument_list|)
expr_stmt|;
name|shouldBeEntry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Prechelt, Lutz"
argument_list|)
expr_stmt|;
name|shouldBeEntry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"1992"
argument_list|)
expr_stmt|;
name|shouldBeEntry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
literal|"Abstrakt"
argument_list|)
expr_stmt|;
name|shouldBeEntry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"key"
argument_list|)
expr_stmt|;
name|shouldBeEntry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"10000"
argument_list|)
expr_stmt|;
name|shouldBeEntry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"20"
argument_list|)
expr_stmt|;
name|shouldBeEntry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"19"
argument_list|)
expr_stmt|;
try|try
init|(
name|InputStream
name|inStream
init|=
name|InspecImportTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"InspecImportTest2.txt"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|inspecImp
operator|.
name|importEntries
argument_list|(
name|inStream
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
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
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|shouldBeEntry
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|importConferencePaperGivesInproceedings ()
specifier|public
name|void
name|importConferencePaperGivesInproceedings
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|testInput
init|=
literal|"Record.*INSPEC.*\n"
operator|+
literal|"\n"
operator|+
literal|"RT ~ Conference-Paper"
decl_stmt|;
name|BibEntry
name|shouldBeEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|shouldBeEntry
operator|.
name|setType
argument_list|(
literal|"Inproceedings"
argument_list|)
expr_stmt|;
try|try
init|(
name|InputStream
name|inStream
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|testInput
operator|.
name|getBytes
argument_list|()
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|inspecImp
operator|.
name|importEntries
argument_list|(
name|inStream
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
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
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|shouldBeEntry
argument_list|,
name|entry
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|importMiscGivesMisc ()
specifier|public
name|void
name|importMiscGivesMisc
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|testInput
init|=
literal|"Record.*INSPEC.*\n"
operator|+
literal|"\n"
operator|+
literal|"RT ~ Misc"
decl_stmt|;
name|BibEntry
name|shouldBeEntry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|shouldBeEntry
operator|.
name|setType
argument_list|(
literal|"Misc"
argument_list|)
expr_stmt|;
try|try
init|(
name|InputStream
name|inStream
init|=
operator|new
name|ByteArrayInputStream
argument_list|(
name|testInput
operator|.
name|getBytes
argument_list|()
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|inspecImp
operator|.
name|importEntries
argument_list|(
name|inStream
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
decl_stmt|;
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
name|BibEntry
name|entry
init|=
name|entries
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|shouldBeEntry
argument_list|,
name|entry
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
name|assertEquals
argument_list|(
literal|"INSPEC"
argument_list|,
name|inspecImp
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
name|assertEquals
argument_list|(
literal|"inspec"
argument_list|,
name|inspecImp
operator|.
name|getCLIId
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

