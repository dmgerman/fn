begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.journals
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
package|;
end_package

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
name|assertFalse
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
DECL|class|JournalAbbreviationRepositoryTest
specifier|public
class|class
name|JournalAbbreviationRepositoryTest
block|{
annotation|@
name|Test
DECL|method|empty ()
specifier|public
name|void
name|empty
parameter_list|()
block|{
name|JournalAbbreviationRepository
name|repository
init|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|repository
operator|.
name|getAbbreviations
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|oneElement ()
specifier|public
name|void
name|oneElement
parameter_list|()
block|{
name|JournalAbbreviationRepository
name|repository
init|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
decl_stmt|;
name|repository
operator|.
name|addEntry
argument_list|(
operator|new
name|Abbreviation
argument_list|(
literal|"Long Name"
argument_list|,
literal|"L. N."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|repository
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|repository
operator|.
name|getAbbreviations
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L. N."
argument_list|,
name|repository
operator|.
name|getIsoAbbreviation
argument_list|(
literal|"Long Name"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"WRONG"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"UNKNOWN"
argument_list|,
name|repository
operator|.
name|getIsoAbbreviation
argument_list|(
literal|"?"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"UNKNOWN"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L N"
argument_list|,
name|repository
operator|.
name|getMedlineAbbreviation
argument_list|(
literal|"Long Name"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"WRONG"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"UNKNOWN"
argument_list|,
name|repository
operator|.
name|getMedlineAbbreviation
argument_list|(
literal|"?"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"UNKNOWN"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L. N."
argument_list|,
name|repository
operator|.
name|getNextAbbreviation
argument_list|(
literal|"Long Name"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"WRONG"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L N"
argument_list|,
name|repository
operator|.
name|getNextAbbreviation
argument_list|(
literal|"L. N."
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"WRONG"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Long Name"
argument_list|,
name|repository
operator|.
name|getNextAbbreviation
argument_list|(
literal|"L N"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"WRONG"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"UNKNOWN"
argument_list|,
name|repository
operator|.
name|getNextAbbreviation
argument_list|(
literal|"?"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"UNKNOWN"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|repository
operator|.
name|isKnownName
argument_list|(
literal|"Long Name"
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|repository
operator|.
name|isKnownName
argument_list|(
literal|"L. N."
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|repository
operator|.
name|isKnownName
argument_list|(
literal|"L N"
argument_list|)
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
name|repository
operator|.
name|isKnownName
argument_list|(
literal|"?"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDuplicates ()
specifier|public
name|void
name|testDuplicates
parameter_list|()
block|{
name|JournalAbbreviationRepository
name|repository
init|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
decl_stmt|;
name|repository
operator|.
name|addEntry
argument_list|(
operator|new
name|Abbreviation
argument_list|(
literal|"Long Name"
argument_list|,
literal|"L. N."
argument_list|)
argument_list|)
expr_stmt|;
name|repository
operator|.
name|addEntry
argument_list|(
operator|new
name|Abbreviation
argument_list|(
literal|"Long Name"
argument_list|,
literal|"L. N."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|repository
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDuplicatesIsoOnly ()
specifier|public
name|void
name|testDuplicatesIsoOnly
parameter_list|()
block|{
name|JournalAbbreviationRepository
name|repository
init|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
decl_stmt|;
name|repository
operator|.
name|addEntry
argument_list|(
operator|new
name|Abbreviation
argument_list|(
literal|"Old Long Name"
argument_list|,
literal|"L. N."
argument_list|)
argument_list|)
expr_stmt|;
name|repository
operator|.
name|addEntry
argument_list|(
operator|new
name|Abbreviation
argument_list|(
literal|"New Long Name"
argument_list|,
literal|"L. N."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|repository
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDuplicateKeys ()
specifier|public
name|void
name|testDuplicateKeys
parameter_list|()
block|{
name|JournalAbbreviationRepository
name|repository
init|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
decl_stmt|;
name|repository
operator|.
name|addEntry
argument_list|(
operator|new
name|Abbreviation
argument_list|(
literal|"Long Name"
argument_list|,
literal|"L. N."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|repository
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L. N."
argument_list|,
name|repository
operator|.
name|getIsoAbbreviation
argument_list|(
literal|"Long Name"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"WRONG"
argument_list|)
argument_list|)
expr_stmt|;
name|repository
operator|.
name|addEntry
argument_list|(
operator|new
name|Abbreviation
argument_list|(
literal|"Long Name"
argument_list|,
literal|"LA. N."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|repository
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"LA. N."
argument_list|,
name|repository
operator|.
name|getIsoAbbreviation
argument_list|(
literal|"Long Name"
argument_list|)
operator|.
name|orElse
argument_list|(
literal|"WRONG"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

