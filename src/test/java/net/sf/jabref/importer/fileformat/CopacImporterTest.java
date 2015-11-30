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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
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
name|BibtexEntry
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
name|List
import|;
end_import

begin_class
DECL|class|CopacImporterTest
specifier|public
class|class
name|CopacImporterTest
block|{
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
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|==
literal|null
condition|)
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
block|}
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
name|CopacImporter
name|importer
init|=
operator|new
name|CopacImporter
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStream
name|stream1
init|=
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"CopacImporterTest1.txt"
argument_list|)
init|;
name|InputStream
name|stream2
operator|=
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"CopacImporterTest2.txt"
argument_list|)
init|;
name|InputStream
name|stream3
operator|=
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTest1.isi"
argument_list|)
init|;
name|InputStream
name|stream4
operator|=
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestInspec.isi"
argument_list|)
init|;
name|InputStream
name|stream5
operator|=
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestWOS.isi"
argument_list|)
init|;
name|InputStream
name|stream6
operator|=
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"IsiImporterTestMedline.isi"
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
name|stream1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|stream2
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|stream3
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|stream4
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|stream5
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|stream6
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testImportEntries ()
specifier|public
name|void
name|testImportEntries
parameter_list|()
throws|throws
name|IOException
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"defaultEncoding"
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
operator|.
name|name
argument_list|()
argument_list|)
expr_stmt|;
name|CopacImporter
name|importer
init|=
operator|new
name|CopacImporter
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStream
name|stream
init|=
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"CopacImporterTest1.txt"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
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
name|BibtexEntry
name|entry
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
literal|"The SIS project : software reuse with a natural language approach"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Prechelt, Lutz and UniversitÃ¤t Karlsruhe. FakultÃ¤t fÃ¼r Informatik"
argument_list|,
name|entry
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
literal|"Interner Bericht ; Nr.2/92"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"series"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"1992"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"year"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Karlsruhe :  Universitat Karlsruhe, Fakultat fur Informatik"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|BOOK
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testImportEntries2 ()
specifier|public
name|void
name|testImportEntries2
parameter_list|()
throws|throws
name|IOException
block|{
name|CopacImporter
name|importer
init|=
operator|new
name|CopacImporter
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStream
name|stream
init|=
name|CopacImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"CopacImporterTest2.txt"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|entries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibtexEntry
name|one
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
literal|"Computing and operational research at the London Hospital"
argument_list|,
name|one
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
name|BibtexEntry
name|two
init|=
name|entries
operator|.
name|get
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Real time systems : management and design"
argument_list|,
name|two
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

