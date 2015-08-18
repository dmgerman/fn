begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.imports
package|package
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
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
name|Ignore
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
name|File
import|;
end_import

begin_comment
comment|/**  * @version 11.11.2008 | 22:16  */
end_comment

begin_class
DECL|class|EntryFromPDFCreatorTest
specifier|public
class|class
name|EntryFromPDFCreatorTest
block|{
DECL|field|entryCreator
specifier|private
specifier|final
name|EntryFromPDFCreator
name|entryCreator
init|=
operator|new
name|EntryFromPDFCreator
argument_list|()
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
comment|// externalFileTypes are needed for the EntryFromPDFCreator
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
operator|.
name|updateExternalFileTypes
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPDFFileFilter ()
specifier|public
name|void
name|testPDFFileFilter
parameter_list|()
block|{
name|Assert
operator|.
name|assertTrue
argument_list|(
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
name|assertTrue
argument_list|(
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
name|assertFalse
argument_list|(
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
annotation|@
name|Test
annotation|@
name|Ignore
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
name|ImportDataTest
operator|.
name|NOT_EXISTING_PDF
argument_list|,
literal|false
argument_list|)
decl_stmt|;
name|Assert
operator|.
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
name|ImportDataTest
operator|.
name|FILE_NOT_IN_DATABASE
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
name|ImportDataTest
operator|.
name|FILE_NOT_IN_DATABASE
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

