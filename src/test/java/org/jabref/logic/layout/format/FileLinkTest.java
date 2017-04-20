begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|ParamLayoutFormatter
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

begin_import
import|import static
name|org
operator|.
name|mockito
operator|.
name|Mockito
operator|.
name|mock
import|;
end_import

begin_class
DECL|class|FileLinkTest
specifier|public
class|class
name|FileLinkTest
block|{
DECL|field|prefs
specifier|private
name|FileLinkPreferences
name|prefs
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
name|prefs
operator|=
name|mock
argument_list|(
name|FileLinkPreferences
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEmpty ()
specifier|public
name|void
name|testEmpty
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
operator|new
name|FileLink
argument_list|(
name|prefs
argument_list|)
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNull ()
specifier|public
name|void
name|testNull
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
operator|new
name|FileLink
argument_list|(
name|prefs
argument_list|)
operator|.
name|format
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testOnlyFilename ()
specifier|public
name|void
name|testOnlyFilename
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test.pdf"
argument_list|,
operator|new
name|FileLink
argument_list|(
name|prefs
argument_list|)
operator|.
name|format
argument_list|(
literal|"test.pdf"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCompleteRecord ()
specifier|public
name|void
name|testCompleteRecord
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"test.pdf"
argument_list|,
operator|new
name|FileLink
argument_list|(
name|prefs
argument_list|)
operator|.
name|format
argument_list|(
literal|"paper:test.pdf:PDF"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMultipleFiles ()
specifier|public
name|void
name|testMultipleFiles
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|FileLink
argument_list|(
name|prefs
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"test.pdf"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"paper:test.pdf:PDF;presentation:pres.ppt:PPT"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMultipleFilesPick ()
specifier|public
name|void
name|testMultipleFilesPick
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|FileLink
argument_list|(
name|prefs
argument_list|)
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"ppt"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"pres.ppt"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"paper:test.pdf:PDF;presentation:pres.ppt:PPT"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMultipleFilesPickNonExistant ()
specifier|public
name|void
name|testMultipleFilesPickNonExistant
parameter_list|()
block|{
name|ParamLayoutFormatter
name|a
init|=
operator|new
name|FileLink
argument_list|(
name|prefs
argument_list|)
decl_stmt|;
name|a
operator|.
name|setArgument
argument_list|(
literal|"doc"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"paper:test.pdf:PDF;presentation:pres.ppt:PPT"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

