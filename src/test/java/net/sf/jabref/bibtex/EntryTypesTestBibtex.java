begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.bibtex
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bibtex
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
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
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

begin_class
DECL|class|EntryTypesTestBibtex
specifier|public
class|class
name|EntryTypesTestBibtex
block|{
DECL|field|backup
specifier|private
name|JabRefPreferences
name|backup
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
name|Globals
operator|.
name|prefs
operator|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
expr_stmt|;
name|backup
operator|=
name|Globals
operator|.
name|prefs
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_MODE
argument_list|,
literal|false
argument_list|)
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
throws|throws
name|Exception
block|{
name|Globals
operator|.
name|prefs
operator|.
name|overwritePreferences
argument_list|(
name|backup
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBibtexMode ()
specifier|public
name|void
name|testBibtexMode
parameter_list|()
block|{
comment|// Bibtex mode
name|EntryTypes
name|bibtexentrytypes
init|=
operator|new
name|EntryTypes
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|bibtexentrytypes
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|)
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|bibtexentrytypes
operator|.
name|getType
argument_list|(
literal|"aaaaarticle"
argument_list|)
argument_list|)
expr_stmt|;
name|assertNull
argument_list|(
name|bibtexentrytypes
operator|.
name|getStandardType
argument_list|(
literal|"aaaaarticle"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|19
argument_list|,
name|bibtexentrytypes
operator|.
name|getAllValues
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|19
argument_list|,
name|bibtexentrytypes
operator|.
name|getAllTypes
argument_list|()
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|MISC
argument_list|,
name|bibtexentrytypes
operator|.
name|getBibtexEntryType
argument_list|(
literal|"aaaaarticle"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Edit the "article" entry type
name|ArrayList
argument_list|<
name|String
argument_list|>
name|requiredFields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getRequiredFields
argument_list|()
argument_list|)
decl_stmt|;
name|requiredFields
operator|.
name|add
argument_list|(
literal|"specialfield"
argument_list|)
expr_stmt|;
name|CustomEntryType
name|newArticle
init|=
operator|new
name|CustomEntryType
argument_list|(
literal|"article"
argument_list|,
name|requiredFields
argument_list|,
name|BibtexEntryTypes
operator|.
name|ARTICLE
operator|.
name|getOptionalFields
argument_list|()
argument_list|)
decl_stmt|;
name|bibtexentrytypes
operator|.
name|addOrModifyCustomEntryType
argument_list|(
name|newArticle
argument_list|)
expr_stmt|;
comment|// Should not be the same any more
name|assertNotEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|bibtexentrytypes
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Remove the custom "article" entry type, which should restore the original
name|bibtexentrytypes
operator|.
name|removeType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
comment|// Should not be possible to remove a standard type
name|assertEquals
argument_list|(
name|BibtexEntryTypes
operator|.
name|ARTICLE
argument_list|,
name|bibtexentrytypes
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsThisBibtex ()
specifier|public
name|void
name|testIsThisBibtex
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_MODE
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

