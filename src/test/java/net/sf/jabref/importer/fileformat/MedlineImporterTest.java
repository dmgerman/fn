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
name|DirectoryStream
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
name|Files
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
name|ArrayList
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|preferences
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
name|mockito
operator|.
name|runners
operator|.
name|MockitoJUnitRunner
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|assertEquals
import|;
end_import

begin_comment
comment|/**  * Articles in the medline format can be downloaded from http://www.ncbi.nlm.nih.gov/pubmed/.  * 1. Search for a term and make sure you have selected the PubMed database  * 2. Select the results you want to export by checking their checkboxes  * 3. Press on the 'Send to' drop down menu on top of the search results  * 4. Select 'File' as Destination and 'XML' as Format  * 5. Press 'Create File' to download your search results in a medline xml file  *  * @author Daniel Mair/Bruehl  *  */
end_comment

begin_class
annotation|@
name|RunWith
argument_list|(
name|MockitoJUnitRunner
operator|.
name|class
argument_list|)
DECL|class|MedlineImporterTest
specifier|public
class|class
name|MedlineImporterTest
block|{
DECL|field|importer
specifier|private
name|MedlineImporter
name|importer
decl_stmt|;
DECL|field|FILEFORMAT_PATH
specifier|private
specifier|static
specifier|final
name|String
name|FILEFORMAT_PATH
init|=
literal|"src/test/resources/net/sf/jabref/importer/fileformat"
decl_stmt|;
comment|/**      * Generates a List of all files in the package "/src/test/resources/net/sf/jabref/importer/fileformat"      * @return A list of Names      * @throws IOException      */
DECL|method|getTestFiles ()
specifier|public
name|List
argument_list|<
name|Path
argument_list|>
name|getTestFiles
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|Path
argument_list|>
name|files
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
try|try
init|(
name|DirectoryStream
argument_list|<
name|Path
argument_list|>
name|stream
init|=
name|Files
operator|.
name|newDirectoryStream
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|FILEFORMAT_PATH
argument_list|)
argument_list|)
init|)
block|{
name|stream
operator|.
name|forEach
argument_list|(
name|files
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
return|return
name|files
return|;
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
name|importer
operator|=
operator|new
name|MedlineImporter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetItemsEmpty ()
specifier|public
name|void
name|testGetItemsEmpty
parameter_list|()
block|{
name|MedlineHandler
name|handler
init|=
operator|new
name|MedlineHandler
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|handler
operator|.
name|getItems
argument_list|()
argument_list|)
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
name|assertEquals
argument_list|(
literal|"Medline"
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
name|assertEquals
argument_list|(
literal|"medline"
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
DECL|method|testsGetExtensions ()
specifier|public
name|void
name|testsGetExtensions
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|extensions
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|extensions
operator|.
name|add
argument_list|(
literal|".nbib"
argument_list|)
expr_stmt|;
name|extensions
operator|.
name|add
argument_list|(
literal|".xml"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|extensions
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|,
name|importer
operator|.
name|getExtensions
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|extensions
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|,
name|importer
operator|.
name|getExtensions
argument_list|()
operator|.
name|get
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDescription ()
specifier|public
name|void
name|testGetDescription
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Importer for the Medline format."
argument_list|,
name|importer
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
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
name|Path
argument_list|>
name|list
init|=
name|getTestFiles
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|n
lambda|->
operator|!
name|n
operator|.
name|getFileName
argument_list|()
operator|.
name|toString
argument_list|()
operator|.
name|startsWith
argument_list|(
literal|"MedlineImporter"
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|Path
name|file
range|:
name|list
control|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|file
operator|.
name|toString
argument_list|()
argument_list|,
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
block|}
end_class

end_unit

