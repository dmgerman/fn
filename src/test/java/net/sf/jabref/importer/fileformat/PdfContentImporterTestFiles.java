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
name|org
operator|.
name|junit
operator|.
name|BeforeClass
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
DECL|class|PdfContentImporterTestFiles
specifier|public
class|class
name|PdfContentImporterTestFiles
block|{
annotation|@
name|Parameter
DECL|field|fileName
specifier|public
name|String
name|fileName
decl_stmt|;
annotation|@
name|BeforeClass
DECL|method|setUp ()
specifier|public
specifier|static
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
comment|// The test folder contains pairs of PDFs and BibTeX files. We check each pair.
comment|// This method returns the basenames of the available pairs
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
literal|"LNCS-minimal"
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
DECL|method|correctContent ()
specifier|public
name|void
name|correctContent
parameter_list|()
throws|throws
name|IOException
block|{
name|String
name|pdfFileName
init|=
name|fileName
operator|+
literal|".pdf"
decl_stmt|;
name|String
name|bibFileName
init|=
name|fileName
operator|+
literal|".bib"
decl_stmt|;
name|PdfContentImporter
name|importer
init|=
operator|new
name|PdfContentImporter
argument_list|()
decl_stmt|;
try|try
init|(
name|InputStream
name|is
init|=
name|PdfContentImporter
operator|.
name|class
operator|.
name|getResourceAsStream
argument_list|(
name|pdfFileName
argument_list|)
init|)
block|{
name|List
argument_list|<
name|BibEntry
argument_list|>
name|result
init|=
name|importer
operator|.
name|importEntries
argument_list|(
name|is
argument_list|,
literal|null
argument_list|)
decl_stmt|;
name|BibtexEntryAssert
operator|.
name|assertEquals
argument_list|(
name|PdfContentImporterTest
operator|.
name|class
argument_list|,
name|bibFileName
argument_list|,
name|result
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

