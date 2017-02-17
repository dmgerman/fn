begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fileformat
package|package
name|org
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|FileExtensions
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
comment|/**      * Generates a List of all files in the package "/src/test/resources/org/jabref/logic/importer/fileformat"      * @return A list of Names      * @throws IOException      */
DECL|method|getTestFiles ()
specifier|public
name|List
argument_list|<
name|Path
argument_list|>
name|getTestFiles
parameter_list|()
throws|throws
name|Exception
block|{
try|try
init|(
name|Stream
argument_list|<
name|Path
argument_list|>
name|stream
init|=
name|Files
operator|.
name|list
argument_list|(
name|Paths
operator|.
name|get
argument_list|(
name|MedlineImporterTest
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|""
argument_list|)
operator|.
name|toURI
argument_list|()
argument_list|)
argument_list|)
init|)
block|{
return|return
name|stream
operator|.
name|filter
argument_list|(
name|p
lambda|->
operator|!
name|Files
operator|.
name|isDirectory
argument_list|(
name|p
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
return|;
block|}
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
DECL|method|testGetFormatName ()
specifier|public
name|void
name|testGetFormatName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Medline/PubMed"
argument_list|,
name|importer
operator|.
name|getName
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
name|assertEquals
argument_list|(
name|FileExtensions
operator|.
name|MEDLINE
argument_list|,
name|importer
operator|.
name|getExtensions
argument_list|()
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
name|Exception
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
