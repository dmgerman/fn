begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
package|;
end_package

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
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Test
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertFalse
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertTrue
import|;
end_import

begin_class
DECL|class|ImportDataTest
specifier|public
class|class
name|ImportDataTest
block|{
DECL|field|FILE_IN_DATABASE
specifier|public
specifier|static
specifier|final
name|Path
name|FILE_IN_DATABASE
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/org/jabref/logic/importer/unlinkedFilesTestFolder/pdfInDatabase.pdf"
argument_list|)
decl_stmt|;
DECL|field|FILE_NOT_IN_DATABASE
specifier|public
specifier|static
specifier|final
name|Path
name|FILE_NOT_IN_DATABASE
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/org/jabref/logic/importer/unlinkedFilesTestFolder/pdfNotInDatabase.pdf"
argument_list|)
decl_stmt|;
DECL|field|EXISTING_FOLDER
specifier|public
specifier|static
specifier|final
name|Path
name|EXISTING_FOLDER
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/org/jabref/logic/importer/unlinkedFilesTestFolder"
argument_list|)
decl_stmt|;
DECL|field|NOT_EXISTING_FOLDER
specifier|public
specifier|static
specifier|final
name|Path
name|NOT_EXISTING_FOLDER
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"notexistingfolder"
argument_list|)
decl_stmt|;
DECL|field|NOT_EXISTING_PDF
specifier|public
specifier|static
specifier|final
name|Path
name|NOT_EXISTING_PDF
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/org/jabref/logic/importer/unlinkedFilesTestFolder/null.pdf"
argument_list|)
decl_stmt|;
DECL|field|UNLINKED_FILES_TEST_BIB
specifier|public
specifier|static
specifier|final
name|Path
name|UNLINKED_FILES_TEST_BIB
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"src/test/resources/org/jabref/util/unlinkedFilesTestBib.bib"
argument_list|)
decl_stmt|;
comment|/**      * Tests the testing environment.      */
annotation|@
name|Test
DECL|method|testTestingEnvironment ()
name|void
name|testTestingEnvironment
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|ImportDataTest
operator|.
name|EXISTING_FOLDER
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Files
operator|.
name|isDirectory
argument_list|(
name|ImportDataTest
operator|.
name|EXISTING_FOLDER
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|ImportDataTest
operator|.
name|FILE_IN_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Files
operator|.
name|isRegularFile
argument_list|(
name|ImportDataTest
operator|.
name|FILE_IN_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|ImportDataTest
operator|.
name|FILE_NOT_IN_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|Files
operator|.
name|isRegularFile
argument_list|(
name|ImportDataTest
operator|.
name|FILE_NOT_IN_DATABASE
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|ImportDataTest
operator|.
name|NOT_EXISTING_FOLDER
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|Files
operator|.
name|exists
argument_list|(
name|ImportDataTest
operator|.
name|NOT_EXISTING_PDF
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

