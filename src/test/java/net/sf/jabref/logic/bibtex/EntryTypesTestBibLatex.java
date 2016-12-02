begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtex
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
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
name|EntryTypes
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
name|BibLatexEntryTypes
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
DECL|class|EntryTypesTestBibLatex
specifier|public
class|class
name|EntryTypesTestBibLatex
block|{
annotation|@
name|Test
annotation|@
name|Ignore
argument_list|(
literal|"This fails of unknown reasons - will be fixed with https://github.com/JabRef/jabref/pull/2331"
argument_list|)
DECL|method|testBibLatexMode ()
specifier|public
name|void
name|testBibLatexMode
parameter_list|()
block|{
comment|// BibLatex mode
name|assertEquals
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|EntryTypes
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|EntryTypes
operator|.
name|getType
argument_list|(
literal|"aaaaarticle"
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|EntryTypes
operator|.
name|getStandardType
argument_list|(
literal|"aaaaarticle"
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|34
argument_list|,
name|EntryTypes
operator|.
name|getAllValues
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|34
argument_list|,
name|EntryTypes
operator|.
name|getAllTypes
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|EntryTypes
operator|.
name|removeType
argument_list|(
literal|"article"
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
expr_stmt|;
comment|// Should not be possible to remove a standard type
name|assertEquals
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|EntryTypes
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|defaultType ()
specifier|public
name|void
name|defaultType
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|BibLatexEntryTypes
operator|.
name|MISC
argument_list|,
name|EntryTypes
operator|.
name|getTypeOrDefault
argument_list|(
literal|"unknowntype"
argument_list|,
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

