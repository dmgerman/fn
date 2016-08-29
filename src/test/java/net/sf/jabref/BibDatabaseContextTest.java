begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|database
operator|.
name|BibDatabase
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
name|database
operator|.
name|BibDatabaseMode
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

begin_class
DECL|class|BibDatabaseContextTest
specifier|public
class|class
name|BibDatabaseContextTest
block|{
annotation|@
name|Test
DECL|method|testTypeBasedOnDefaultBibtex ()
specifier|public
name|void
name|testTypeBasedOnDefaultBibtex
parameter_list|()
block|{
name|BibDatabaseContext
name|bibDatabaseContext
init|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
operator|new
name|Defaults
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
name|bibDatabaseContext
operator|.
name|setMode
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTypeBasedOnDefaultBiblatex ()
specifier|public
name|void
name|testTypeBasedOnDefaultBiblatex
parameter_list|()
block|{
name|BibDatabaseContext
name|bibDatabaseContext
init|=
operator|new
name|BibDatabaseContext
argument_list|(
operator|new
name|BibDatabase
argument_list|()
argument_list|,
operator|new
name|MetaData
argument_list|()
argument_list|,
operator|new
name|Defaults
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
name|bibDatabaseContext
operator|.
name|setMode
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTypeBasedOnInferredModeBibTeX ()
specifier|public
name|void
name|testTypeBasedOnInferredModeBibTeX
parameter_list|()
block|{
name|BibDatabase
name|db
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|(
literal|"1"
argument_list|)
decl_stmt|;
name|db
operator|.
name|insertEntry
argument_list|(
name|e1
argument_list|)
expr_stmt|;
name|BibDatabaseContext
name|bibDatabaseContext
init|=
operator|new
name|BibDatabaseContext
argument_list|(
name|db
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTypeBasedOnInferredModeBiblatex ()
specifier|public
name|void
name|testTypeBasedOnInferredModeBiblatex
parameter_list|()
block|{
name|BibDatabase
name|db
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|BibEntry
name|e1
init|=
operator|new
name|BibEntry
argument_list|(
literal|"1"
argument_list|,
literal|"electronic"
argument_list|)
decl_stmt|;
name|db
operator|.
name|insertEntry
argument_list|(
name|e1
argument_list|)
expr_stmt|;
name|BibDatabaseContext
name|bibDatabaseContext
init|=
operator|new
name|BibDatabaseContext
argument_list|(
name|db
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|,
name|bibDatabaseContext
operator|.
name|getMode
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

