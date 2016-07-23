begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.cleanup
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|cleanup
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
name|BibDatabaseContext
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
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationLoader
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
name|preferences
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
name|Test
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
DECL|class|ISSNCleanupTest
specifier|public
class|class
name|ISSNCleanupTest
block|{
DECL|field|worker
specifier|private
name|CleanupWorker
name|worker
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|worker
operator|=
operator|new
name|CleanupWorker
argument_list|(
name|mock
argument_list|(
name|BibDatabaseContext
operator|.
name|class
argument_list|)
argument_list|,
name|mock
argument_list|(
name|JournalAbbreviationLoader
operator|.
name|class
argument_list|)
argument_list|,
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupISSNReturnsCorrectISSN ()
specifier|public
name|void
name|cleanupISSNReturnsCorrectISSN
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_ISSN
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"0123-4567"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"0123-4567"
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"issn"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupISSNAddsMissingDash ()
specifier|public
name|void
name|cleanupISSNAddsMissingDash
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_ISSN
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"01234567"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"0123-4567"
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"issn"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupISSNJunkStaysJunk ()
specifier|public
name|void
name|cleanupISSNJunkStaysJunk
parameter_list|()
block|{
name|CleanupPreset
name|preset
init|=
operator|new
name|CleanupPreset
argument_list|(
name|CleanupPreset
operator|.
name|CleanupStep
operator|.
name|CLEAN_UP_ISSN
argument_list|)
decl_stmt|;
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"issn"
argument_list|,
literal|"Banana"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|preset
argument_list|,
name|entry
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Banana"
argument_list|)
argument_list|,
name|entry
operator|.
name|getFieldOptional
argument_list|(
literal|"issn"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

