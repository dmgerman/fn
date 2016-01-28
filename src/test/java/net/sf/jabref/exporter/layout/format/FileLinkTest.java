begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
operator|.
name|*
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

begin_class
DECL|class|FileLinkTest
specifier|public
class|class
name|FileLinkTest
block|{
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
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|==
literal|null
condition|)
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
argument_list|()
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
argument_list|()
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
argument_list|()
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
argument_list|()
operator|.
name|format
argument_list|(
literal|"paper:test.pdf:PDF"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

