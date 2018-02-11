begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.cleanup
package|package
name|org
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
name|org
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|FieldName
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
name|BeforeEach
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

begin_class
DECL|class|ConvertToBibtexCleanupTest
specifier|public
class|class
name|ConvertToBibtexCleanupTest
block|{
DECL|field|worker
specifier|private
name|ConvertToBibtexCleanup
name|worker
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|worker
operator|=
operator|new
name|ConvertToBibtexCleanup
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupMovesDateToYearAndMonth ()
specifier|public
name|void
name|cleanupMovesDateToYearAndMonth
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"date"
argument_list|,
literal|"2011-01"
argument_list|)
decl_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|DATE
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2011"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"#jan#"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|MONTH
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupWithYearAlreadyPresentDoesNothing ()
specifier|public
name|void
name|cleanupWithYearAlreadyPresentDoesNothing
parameter_list|()
block|{
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
literal|"year"
argument_list|,
literal|"2011"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"date"
argument_list|,
literal|"2012"
argument_list|)
expr_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2011"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|YEAR
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"2012"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|DATE
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|cleanupMovesJournaltitleToJournal ()
specifier|public
name|void
name|cleanupMovesJournaltitleToJournal
parameter_list|()
block|{
name|BibEntry
name|entry
init|=
operator|new
name|BibEntry
argument_list|()
operator|.
name|withField
argument_list|(
literal|"journaltitle"
argument_list|,
literal|"Best of JabRef"
argument_list|)
decl_stmt|;
name|worker
operator|.
name|cleanup
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|JOURNALTITLE
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Best of JabRef"
argument_list|)
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|FieldName
operator|.
name|JOURNAL
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

