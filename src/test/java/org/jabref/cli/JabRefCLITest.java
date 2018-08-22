begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.cli
package|package
name|org
operator|.
name|jabref
operator|.
name|cli
package|;
end_package

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
name|assertEquals
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
DECL|class|JabRefCLITest
class|class
name|JabRefCLITest
block|{
annotation|@
name|Test
DECL|method|parsingLongOptions ()
name|void
name|parsingLongOptions
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
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|cli
operator|.
name|getLeftOver
argument_list|()
argument_list|)
expr_stmt|;
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
name|assertTrue
argument_list|(
name|cli
operator|.
name|isDisableGui
argument_list|()
argument_list|)
expr_stmt|;
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
DECL|method|parsingShortOptions ()
name|void
name|parsingShortOptions
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
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|cli
operator|.
name|getLeftOver
argument_list|()
argument_list|)
expr_stmt|;
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
name|assertTrue
argument_list|(
name|cli
operator|.
name|isDisableGui
argument_list|()
argument_list|)
expr_stmt|;
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
DECL|method|preferencesExport ()
name|void
name|preferencesExport
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
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|cli
operator|.
name|getLeftOver
argument_list|()
argument_list|)
expr_stmt|;
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
name|assertTrue
argument_list|(
name|cli
operator|.
name|isDisableGui
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|recognizesImportBibtex ()
name|void
name|recognizesImportBibtex
parameter_list|()
block|{
name|String
name|bibtex
init|=
literal|"@article{test, title=\"test title\"}"
decl_stmt|;
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
literal|"-ib"
block|,
name|bibtex
block|}
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|cli
operator|.
name|getLeftOver
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|cli
operator|.
name|isBibtexImport
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|bibtex
argument_list|,
name|cli
operator|.
name|getBibtexImport
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|recognizesImportBibtexLong ()
name|void
name|recognizesImportBibtexLong
parameter_list|()
block|{
name|String
name|bibtex
init|=
literal|"@article{test, title=\"test title\"}"
decl_stmt|;
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
literal|"-importBibtex"
block|,
name|bibtex
block|}
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|emptyList
argument_list|()
argument_list|,
name|cli
operator|.
name|getLeftOver
argument_list|()
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|cli
operator|.
name|isBibtexImport
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|bibtex
argument_list|,
name|cli
operator|.
name|getBibtexImport
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

