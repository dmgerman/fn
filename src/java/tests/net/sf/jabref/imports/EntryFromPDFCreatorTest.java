begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref.imports
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|Assert
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
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
name|imports
operator|.
name|EntryFromPDFCreator
import|;
end_import

begin_comment
comment|/**  *   * @version 11.11.2008 | 22:16  *   */
end_comment

begin_class
DECL|class|EntryFromPDFCreatorTest
specifier|public
class|class
name|EntryFromPDFCreatorTest
extends|extends
name|TestCase
block|{
DECL|field|entryCreator
specifier|private
name|EntryFromPDFCreator
name|entryCreator
decl_stmt|;
DECL|field|existingPDF
specifier|private
name|File
name|existingPDF
decl_stmt|;
DECL|field|notExistingPDF
specifier|private
name|File
name|notExistingPDF
decl_stmt|;
DECL|method|setUp ()
specifier|protected
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
comment|// externalFileTypes are needed for the EntryFromPDFCreator
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|updateExternalFileTypes
argument_list|()
expr_stmt|;
name|entryCreator
operator|=
operator|new
name|EntryFromPDFCreator
argument_list|()
expr_stmt|;
name|existingPDF
operator|=
operator|new
name|File
argument_list|(
literal|"src/tests/net/sf/jabref/imports/unlinkedFilesTestFolder/pdfNotInDatabase.pdf"
argument_list|)
expr_stmt|;
name|notExistingPDF
operator|=
operator|new
name|File
argument_list|(
literal|"src/tests/net/sf/jabref/imports/unlinkedFilesTestFolder/null.pdf"
argument_list|)
expr_stmt|;
block|}
DECL|method|testPDFFileFilter ()
specifier|public
name|void
name|testPDFFileFilter
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|true
argument_list|,
name|entryCreator
operator|.
name|accept
argument_list|(
operator|new
name|File
argument_list|(
literal|"aPDF.pdf"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|true
argument_list|,
name|entryCreator
operator|.
name|accept
argument_list|(
operator|new
name|File
argument_list|(
literal|"aPDF.PDF"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|false
argument_list|,
name|entryCreator
operator|.
name|accept
argument_list|(
operator|new
name|File
argument_list|(
literal|"foo.jpg"
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testCreationOfEntry ()
specifier|public
name|void
name|testCreationOfEntry
parameter_list|()
block|{
name|BibtexEntry
name|entry
init|=
name|entryCreator
operator|.
name|createEntry
argument_list|(
name|notExistingPDF
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|assertNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|entry
operator|=
name|entryCreator
operator|.
name|createEntry
argument_list|(
name|existingPDF
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|entry
operator|.
name|getField
argument_list|(
literal|"file"
argument_list|)
operator|.
name|endsWith
argument_list|(
literal|":PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|existingPDF
operator|.
name|getName
argument_list|()
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
literal|"title"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

