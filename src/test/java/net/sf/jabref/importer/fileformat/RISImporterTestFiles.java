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

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|runners
operator|.
name|Parameterized
operator|.
name|Parameters
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
name|Collection
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
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|RISImporterTestFiles
specifier|public
class|class
name|RISImporterTestFiles
block|{
DECL|field|risImporter
specifier|private
name|RisImporter
name|risImporter
decl_stmt|;
annotation|@
name|Parameter
DECL|field|fileName
specifier|public
name|String
name|fileName
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
name|risImporter
operator|=
operator|new
name|RisImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"{0}"
argument_list|)
DECL|method|fileNames ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|String
argument_list|>
name|fileNames
parameter_list|()
block|{
return|return
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"RisImporterTest1"
block|,
literal|"RisImporterTest3"
block|,
literal|"RisImporterTest4a"
block|,
literal|"RisImporterTest4b"
block|,
literal|"RisImporterTest4c"
block|,
literal|"RisImporterTest5a"
block|,
literal|"RisImporterTest5b"
block|,
literal|"RisImporterTest6"
block|}
argument_list|)
return|;
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
try|try
init|(
name|InputStream
name|stream
init|=
name|RISImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|fileName
operator|+
literal|".ris"
argument_list|)
init|)
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
name|risImporter
operator|.
name|isRecognizedFormat
argument_list|(
name|stream
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
try|try
init|(
name|InputStream
name|risStream
init|=
name|RISImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|fileName
operator|+
literal|".ris"
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|risEntries
init|=
name|risImporter
operator|.
name|importDatabase
argument_list|(
name|risStream
argument_list|)
operator|.
name|getDatabase
argument_list|()
operator|.
name|getEntries
argument_list|()
decl_stmt|;
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|RISImporterTest
operator|.
name|class
argument_list|,
name|fileName
operator|+
literal|".bib"
argument_list|,
name|risEntries
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

