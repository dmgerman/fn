begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.database
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|database
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|BibtexEntryTypes
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
name|CustomEntryType
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
DECL|class|BibDatabaseModeDetectionTest
specifier|public
class|class
name|BibDatabaseModeDetectionTest
block|{
annotation|@
name|Test
DECL|method|detectBiblatex ()
specifier|public
name|void
name|detectBiblatex
parameter_list|()
block|{
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
operator|new
name|BibEntry
argument_list|(
name|BibLatexEntryTypes
operator|.
name|MVBOOK
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|,
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|BibDatabases
operator|.
name|createDatabase
argument_list|(
name|entries
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|detectUndistinguishableAsBibtex ()
specifier|public
name|void
name|detectUndistinguishableAsBibtex
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"My cool paper"
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|BibDatabases
operator|.
name|createDatabase
argument_list|(
name|entries
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|detectMixedModeAsBiblatex ()
specifier|public
name|void
name|detectMixedModeAsBiblatex
parameter_list|()
block|{
name|BibEntry
name|bibtex
init|=
operator|new
name|BibEntry
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|bibtex
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"IEEE Trans. Services Computing"
argument_list|)
expr_stmt|;
name|BibEntry
name|biblatex
init|=
operator|new
name|BibEntry
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|biblatex
operator|.
name|setField
argument_list|(
literal|"translator"
argument_list|,
literal|"Stefan Kolb"
argument_list|)
expr_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|bibtex
argument_list|,
name|biblatex
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|BibDatabases
operator|.
name|createDatabase
argument_list|(
name|entries
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|detectUnknownTypeAsBibtex ()
specifier|public
name|void
name|detectUnknownTypeAsBibtex
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
operator|new
name|CustomEntryType
argument_list|(
literal|"unknowntype"
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|BibDatabases
operator|.
name|createDatabase
argument_list|(
name|entries
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ignoreUnknownTypesForBibtexDecision ()
specifier|public
name|void
name|ignoreUnknownTypesForBibtexDecision
parameter_list|()
block|{
name|BibEntry
name|custom
init|=
operator|new
name|BibEntry
argument_list|(
operator|new
name|CustomEntryType
argument_list|(
literal|"unknowntype"
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|BibEntry
name|bibtex
init|=
operator|new
name|BibEntry
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|BibEntry
name|biblatex
init|=
operator|new
name|BibEntry
argument_list|(
name|BibLatexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|custom
argument_list|,
name|bibtex
argument_list|,
name|biblatex
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|BibDatabases
operator|.
name|createDatabase
argument_list|(
name|entries
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ignoreUnknownTypesForBiblatexDecision ()
specifier|public
name|void
name|ignoreUnknownTypesForBiblatexDecision
parameter_list|()
block|{
name|BibEntry
name|custom
init|=
operator|new
name|BibEntry
argument_list|(
operator|new
name|CustomEntryType
argument_list|(
literal|"unknowntype"
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
literal|0
argument_list|)
argument_list|)
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|BibEntry
name|bibtex
init|=
operator|new
name|BibEntry
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|BibEntry
name|biblatex
init|=
operator|new
name|BibEntry
argument_list|(
name|BibLatexEntryTypes
operator|.
name|MVBOOK
operator|.
name|getName
argument_list|()
argument_list|)
decl_stmt|;
name|Collection
argument_list|<
name|BibEntry
argument_list|>
name|entries
init|=
name|Arrays
operator|.
name|asList
argument_list|(
name|custom
argument_list|,
name|bibtex
argument_list|,
name|biblatex
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|,
name|BibDatabaseModeDetection
operator|.
name|inferMode
argument_list|(
name|BibDatabases
operator|.
name|createDatabase
argument_list|(
name|entries
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

