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

begin_class
annotation|@
name|RunWith
argument_list|(
name|Parameterized
operator|.
name|class
argument_list|)
DECL|class|SilverPlatterImporterTest
specifier|public
class|class
name|SilverPlatterImporterTest
block|{
DECL|field|testImporter
specifier|private
name|SilverPlatterImporter
name|testImporter
decl_stmt|;
annotation|@
name|Parameter
DECL|field|filename
specifier|public
name|String
name|filename
decl_stmt|;
DECL|field|txtName
specifier|public
name|String
name|txtName
decl_stmt|;
DECL|field|bibName
specifier|public
name|String
name|bibName
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
name|testImporter
operator|=
operator|new
name|SilverPlatterImporter
argument_list|()
expr_stmt|;
name|txtName
operator|=
name|filename
operator|+
literal|".txt"
expr_stmt|;
name|bibName
operator|=
name|filename
operator|+
literal|".bib"
expr_stmt|;
block|}
annotation|@
name|Parameters
argument_list|(
name|name
operator|=
literal|"{index}: {0}"
argument_list|)
DECL|method|fileNames ()
specifier|public
specifier|static
name|Collection
argument_list|<
name|Object
index|[]
argument_list|>
name|fileNames
parameter_list|()
block|{
name|Object
index|[]
index|[]
name|data
init|=
operator|new
name|Object
index|[]
index|[]
block|{
block|{
literal|"SilverPlatterImporterTest1"
block|}
block|,
block|{
literal|"SilverPlatterImporterTest2"
block|}
block|}
decl_stmt|;
return|return
name|Arrays
operator|.
name|asList
argument_list|(
name|data
argument_list|)
return|;
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
name|Exception
block|{
try|try
init|(
name|InputStream
name|stream
init|=
name|SilverPlatterImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|txtName
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
annotation|@
name|Test
DECL|method|testImportEntries ()
specifier|public
specifier|final
name|void
name|testImportEntries
parameter_list|()
throws|throws
name|Exception
block|{
try|try
init|(
name|InputStream
name|in
init|=
name|SilverPlatterImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|txtName
argument_list|)
init|;
name|InputStream
name|bibIn
operator|=
name|SilverPlatterImporterTest
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|bibName
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
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|bibIn
argument_list|,
name|entries
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

