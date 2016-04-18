begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.cli
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|cli
package|;
end_package

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
name|Test
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

begin_class
DECL|class|JabRefCLITest
specifier|public
class|class
name|JabRefCLITest
block|{
annotation|@
name|Test
DECL|method|testCLIParsingLongOptions ()
specifier|public
name|void
name|testCLIParsingLongOptions
parameter_list|()
block|{
name|JabRefCLI
name|cli
init|=
operator|new
name|JabRefCLI
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"--nogui"
block|,
literal|"--import=some/file"
block|,
literal|"--output=some/export/file"
block|}
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"[]"
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|cli
operator|.
name|getLeftOver
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"some/file"
argument_list|,
name|cli
operator|.
name|getFileImport
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|cli
operator|.
name|isDisableGui
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"some/export/file"
argument_list|,
name|cli
operator|.
name|getFileExport
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCLIParsingShortOptions ()
specifier|public
name|void
name|testCLIParsingShortOptions
parameter_list|()
block|{
name|JabRefCLI
name|cli
init|=
operator|new
name|JabRefCLI
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"-n"
block|,
literal|"-i=some/file"
block|,
literal|"-o=some/export/file"
block|}
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"[]"
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|cli
operator|.
name|getLeftOver
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"some/file"
argument_list|,
name|cli
operator|.
name|getFileImport
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|cli
operator|.
name|isDisableGui
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"some/export/file"
argument_list|,
name|cli
operator|.
name|getFileExport
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPreferencesExport ()
specifier|public
name|void
name|testPreferencesExport
parameter_list|()
block|{
name|JabRefCLI
name|cli
init|=
operator|new
name|JabRefCLI
argument_list|(
operator|new
name|String
index|[]
block|{
literal|"-n"
block|,
literal|"-x=some/file"
block|}
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"[]"
argument_list|,
name|Arrays
operator|.
name|toString
argument_list|(
name|cli
operator|.
name|getLeftOver
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"some/file"
argument_list|,
name|cli
operator|.
name|getPreferencesExport
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertTrue
argument_list|(
name|cli
operator|.
name|isDisableGui
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
