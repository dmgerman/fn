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

begin_class
DECL|class|RepecNepImporterTest
specifier|public
class|class
name|RepecNepImporterTest
block|{
DECL|field|testImporter
specifier|private
name|RepecNepImporter
name|testImporter
decl_stmt|;
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
name|testImporter
operator|=
operator|new
name|RepecNepImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormat ()
specifier|public
specifier|final
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
name|accepted
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"RepecNepImporterTest1.txt"
argument_list|,
literal|"RepecNepImporterTest2.txt"
argument_list|,
literal|"RepecNepImporterTest3.txt"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|accepted
control|)
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|RepecNepImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|s
argument_list|)
init|)
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|testImporter
operator|.
name|isRecognizedFormat
argument_list|(
name|stream
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
specifier|final
name|void
name|testIsNotRecognizedFormat
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|String
argument_list|>
name|notAccepted
init|=
name|Arrays
operator|.
name|asList
argument_list|(
literal|"RepecNep1.xml"
argument_list|,
literal|"CopacImporterTest1.txt"
argument_list|,
literal|"RisImporterTest1.ris"
argument_list|,
literal|"CopacImporterTest2.txt"
argument_list|,
literal|"IEEEImport1.txt"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|s
range|:
name|notAccepted
control|)
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|RepecNepImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|s
argument_list|)
init|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|testImporter
operator|.
name|isRecognizedFormat
argument_list|(
name|stream
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|IOException
operator|.
name|class
argument_list|)
DECL|method|testImportEntriesNull ()
specifier|public
specifier|final
name|void
name|testImportEntriesNull
parameter_list|()
throws|throws
name|IOException
block|{
name|testImporter
operator|.
name|importEntries
argument_list|(
literal|null
argument_list|,
operator|new
name|OutputPrinterToNull
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntries1 ()
specifier|public
specifier|final
name|void
name|testImportEntries1
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|in
init|=
name|RepecNepImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"RepecNepImporterTest1.txt"
argument_list|)
init|;
name|InputStream
name|bibIn
operator|=
name|RepecNepImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"RepecNepImporterTest1.bib"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|testImporter
operator|.
name|importEntries
argument_list|(
name|in
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
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|bibIn
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
DECL|method|testImportEntries2 ()
specifier|public
specifier|final
name|void
name|testImportEntries2
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|in
init|=
name|RepecNepImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"RepecNepImporterTest2.txt"
argument_list|)
init|;
name|InputStream
name|bibIn
operator|=
name|RepecNepImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"RepecNepImporterTest2.bib"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|testImporter
operator|.
name|importEntries
argument_list|(
name|in
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
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|bibIn
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
DECL|method|testImportEntries3 ()
specifier|public
specifier|final
name|void
name|testImportEntries3
parameter_list|()
throws|throws
name|IOException
block|{
try|try
init|(
name|InputStream
name|in
init|=
name|RepecNepImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"RepecNepImporterTest3.txt"
argument_list|)
init|;
name|InputStream
name|bibIn
operator|=
name|RepecNepImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"RepecNepImporterTest3.bib"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|testImporter
operator|.
name|importEntries
argument_list|(
name|in
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
literal|1
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|bibIn
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
DECL|method|testGetFormatName ()
specifier|public
specifier|final
name|void
name|testGetFormatName
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"REPEC New Economic Papers (NEP)"
argument_list|,
name|testImporter
operator|.
name|getFormatName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetCliId ()
specifier|public
specifier|final
name|void
name|testGetCliId
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"repecnep"
argument_list|,
name|testImporter
operator|.
name|getCLIId
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDescription ()
specifier|public
specifier|final
name|void
name|testGetDescription
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Imports a New Economics Papers-Message (see http://nep.repec.org)\n"
operator|+
literal|"from the REPEC-NEP Service (see http://www.repec.org).\n"
operator|+
literal|"To import papers either save a NEP message as a text file and then import or\n"
operator|+
literal|"copy&paste the papers you want to import and make sure, one of the first lines\n"
operator|+
literal|"contains the line \"nep.repec.org\"."
argument_list|,
name|testImporter
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetExtensions ()
specifier|public
specifier|final
name|void
name|testGetExtensions
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|".txt"
argument_list|,
name|testImporter
operator|.
name|getExtensions
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

