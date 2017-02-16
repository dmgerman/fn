begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.importer.fileformat
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
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
name|StandardCharsets
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
name|logic
operator|.
name|bibtex
operator|.
name|BibEntryAssert
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
DECL|class|BiblioscapeImporterTestFiles
specifier|public
class|class
name|BiblioscapeImporterTestFiles
block|{
DECL|field|bsImporter
specifier|private
name|BiblioscapeImporter
name|bsImporter
decl_stmt|;
DECL|field|importFile
specifier|public
name|Path
name|importFile
decl_stmt|;
DECL|field|bibFile
specifier|public
name|String
name|bibFile
decl_stmt|;
DECL|method|BiblioscapeImporterTestFiles (String fileName)
specifier|public
name|BiblioscapeImporterTestFiles
parameter_list|(
name|String
name|fileName
parameter_list|)
throws|throws
name|URISyntaxException
block|{
name|importFile
operator|=
name|Paths
operator|.
name|get
argument_list|(
name|BiblioscapeImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
name|fileName
operator|+
literal|".txt"
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
expr_stmt|;
name|bibFile
operator|=
name|fileName
operator|+
literal|".bib"
expr_stmt|;
block|}
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
name|bsImporter
operator|=
operator|new
name|BiblioscapeImporter
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
literal|"BiblioscapeImporterTestOptionalFields"
argument_list|,
literal|"BiblioscapeImporterTestComments"
argument_list|,
literal|"BiblioscapeImporterTestUnknownFields"
argument_list|,
literal|"BiblioscapeImporterTestKeywords"
argument_list|,
literal|"BiblioscapeImporterTestJournalArticle"
argument_list|,
literal|"BiblioscapeImporterTestInbook"
argument_list|,
literal|"BiblioscapeImporterTestUnknownType"
argument_list|,
literal|"BiblioscapeImporterTestArticleST"
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
name|Assert
operator|.
name|assertTrue
argument_list|(
name|bsImporter
operator|.
name|isRecognizedFormat
argument_list|(
name|importFile
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
argument_list|)
expr_stmt|;
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
name|List
argument_list|<
name|BibEntry
argument_list|>
name|bsEntries
init|=
name|bsImporter
operator|.
name|importDatabase
argument_list|(
name|importFile
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
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
literal|1
argument_list|,
name|bsEntries
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|BibEntryAssert
operator|.
name|assertEquals
argument_list|(
name|BiblioscapeImporterTest
operator|.
name|class
argument_list|,
name|bibFile
argument_list|,
name|bsEntries
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

