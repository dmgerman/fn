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
name|net
operator|.
name|URISyntaxException
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
name|Charset
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
name|logic
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
DECL|class|OvidImporterTest
specifier|public
class|class
name|OvidImporterTest
block|{
DECL|field|importer
specifier|private
name|OvidImporter
name|importer
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
name|importer
operator|=
operator|new
name|OvidImporter
argument_list|()
expr_stmt|;
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
literal|"Ovid"
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
literal|"ovid"
argument_list|,
name|importer
operator|.
name|getId
argument_list|()
argument_list|)
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
throws|,
name|URISyntaxException
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
literal|"OvidImporterTest1.txt"
argument_list|,
literal|"OvidImporterTest3.txt"
argument_list|,
literal|"OvidImporterTest4.txt"
argument_list|,
literal|"OvidImporterTest5.txt"
argument_list|,
literal|"OvidImporterTest6.txt"
argument_list|,
literal|"OvidImporterTest7.txt"
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
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|OvidImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|str
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testIsRecognizedFormatRejected ()
specifier|public
name|void
name|testIsRecognizedFormatRejected
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
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
literal|"Empty.txt"
argument_list|,
literal|"OvidImporterTest2.txt"
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
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|OvidImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|str
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
name|importer
operator|.
name|isRecognizedFormat
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|testImportEmpty ()
specifier|public
name|void
name|testImportEmpty
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|OvidImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"Empty.txt"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
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
name|entries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportEntries1 ()
specifier|public
name|void
name|testImportEntries1
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|OvidImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"OvidImporterTest1.txt"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|5
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
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"misc"
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Mustermann and Musterfrau"
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
literal|"Short abstract"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"abstract"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Musterbuch"
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
literal|"Einleitung"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"chaptertitle"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
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
literal|"inproceedings"
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Max"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"editor"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Max the Editor"
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
literal|"Very Long Title"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"28"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"issue"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2015"
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
literal|"103--106"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
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
literal|"incollection"
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Max"
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
literal|"Test"
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
literal|"Very Long Title"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"journal"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"28"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"volume"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"issue"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"April"
argument_list|,
name|entry
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
literal|"2015"
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
literal|"103--106"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
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
literal|"book"
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Max"
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
literal|"2015"
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
literal|"Editor"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"editor"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Very Long Title"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"booktitle"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"103--106"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Address"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"address"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Publisher"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
name|entry
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
literal|"article"
argument_list|,
name|entry
operator|.
name|getType
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"2014"
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
literal|"58"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"pages"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Test"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"address"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNull
argument_list|(
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
literal|"TestPublisher"
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"publisher"
argument_list|)
argument_list|)
expr_stmt|;
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
throws|,
name|URISyntaxException
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|OvidImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"OvidImporterTest2.txt"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|importer
operator|.
name|importDatabase
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
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
name|entries
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testImportSingleEntries ()
specifier|public
name|void
name|testImportSingleEntries
parameter_list|()
throws|throws
name|IOException
throws|,
name|URISyntaxException
block|{
for|for
control|(
name|int
name|n
init|=
literal|3
init|;
name|n
operator|<=
literal|7
condition|;
name|n
operator|++
control|)
block|{
name|Path
name|file
init|=
name|Paths
operator|.
name|get
argument_list|(
name|OvidImporter
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"OvidImporterTest"
operator|+
name|n
operator|+
literal|".txt"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
decl_stmt|;
try|try
init|(
name|InputStream
name|nis
init|=
name|OvidImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
literal|"OvidImporterTestBib"
operator|+
name|n
operator|+
literal|".bib"
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
name|importDatabase
argument_list|(
name|file
argument_list|,
name|Charset
operator|.
name|defaultCharset
argument_list|()
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
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
block|}
block|}
end_class

end_unit

