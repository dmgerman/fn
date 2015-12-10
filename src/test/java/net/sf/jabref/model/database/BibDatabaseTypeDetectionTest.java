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

begin_class
DECL|class|BibDatabaseTypeDetectionTest
specifier|public
class|class
name|BibDatabaseTypeDetectionTest
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
literal|"someid"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|MVBOOK
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseType
operator|.
name|BIBLATEX
argument_list|,
name|BibDatabaseTypeDetection
operator|.
name|inferType
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|detectBiblatexBasedOnFields ()
specifier|public
name|void
name|detectBiblatexBasedOnFields
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
literal|"someid"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|entry
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
name|entry
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseType
operator|.
name|BIBLATEX
argument_list|,
name|BibDatabaseTypeDetection
operator|.
name|inferType
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|detectBibtexBasedOnFields ()
specifier|public
name|void
name|detectBibtexBasedOnFields
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
literal|"someid"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"IEEE Trans. Services Computing"
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
name|BibDatabaseType
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseTypeDetection
operator|.
name|inferType
argument_list|(
name|entries
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
literal|"someid"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
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
name|BibDatabaseType
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseTypeDetection
operator|.
name|inferType
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|detectSingleUnknownTypeAsBibtex ()
specifier|public
name|void
name|detectSingleUnknownTypeAsBibtex
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|(
literal|"someid"
argument_list|,
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
name|BibDatabaseType
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseTypeDetection
operator|.
name|inferType
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ignoreUnknownTypesForDecision ()
specifier|public
name|void
name|ignoreUnknownTypesForDecision
parameter_list|()
block|{
comment|// BibTex
name|BibEntry
name|custom
init|=
operator|new
name|BibEntry
argument_list|(
literal|"someid"
argument_list|,
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
argument_list|)
decl_stmt|;
name|BibEntry
name|bibtex
init|=
operator|new
name|BibEntry
argument_list|(
literal|"someid"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
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
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseType
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseTypeDetection
operator|.
name|inferType
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
comment|// Biblatex
name|BibEntry
name|biblatex
init|=
operator|new
name|BibEntry
argument_list|(
literal|"someid"
argument_list|,
name|BibLatexEntryTypes
operator|.
name|MVBOOK
argument_list|)
decl_stmt|;
name|entries
operator|=
name|Arrays
operator|.
name|asList
argument_list|(
name|custom
argument_list|,
name|biblatex
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseType
operator|.
name|BIBLATEX
argument_list|,
name|BibDatabaseTypeDetection
operator|.
name|inferType
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|detectMixedTypesAsBiblatex ()
specifier|public
name|void
name|detectMixedTypesAsBiblatex
parameter_list|()
block|{
name|BibEntry
name|biblatex
init|=
operator|new
name|BibEntry
argument_list|(
literal|"someid"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|)
decl_stmt|;
name|BibEntry
name|bibtex
init|=
operator|new
name|BibEntry
argument_list|(
literal|"someid"
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
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
name|biblatex
argument_list|,
name|bibtex
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|BibDatabaseType
operator|.
name|BIBTEX
argument_list|,
name|BibDatabaseTypeDetection
operator|.
name|inferType
argument_list|(
name|entries
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

