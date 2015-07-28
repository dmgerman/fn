begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.journals.logic
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|journals
operator|.
name|logic
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
name|journals
operator|.
name|logic
operator|.
name|Abbreviation
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
name|journals
operator|.
name|logic
operator|.
name|JournalAbbreviationRepository
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

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
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
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|repository
operator|.
name|size
argument_list|()
argument_list|)
expr_stmt|;
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
name|or
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
name|or
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
name|or
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
name|or
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
name|or
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
name|or
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
name|or
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
name|or
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
name|assertEquals
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"Long Name = L. N.%n"
argument_list|)
argument_list|,
name|repository
operator|.
name|toPropertiesString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSorting ()
specifier|public
name|void
name|testSorting
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
literal|"A Long Name"
argument_list|,
literal|"AL. N."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A Long Name"
argument_list|,
name|repository
operator|.
name|getAbbreviations
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Long Name"
argument_list|,
name|repository
operator|.
name|getAbbreviations
argument_list|()
operator|.
name|last
argument_list|()
operator|.
name|getName
argument_list|()
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
name|or
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
name|or
argument_list|(
literal|"WRONG"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Long Name = LA. N."
argument_list|,
name|repository
operator|.
name|getAbbreviations
argument_list|()
operator|.
name|first
argument_list|()
operator|.
name|toPropertiesLine
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
comment|//@Ignore(value = "only used for checking the parse logic")
DECL|method|testParsing ()
specifier|public
name|void
name|testParsing
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
name|readJournalListFromResource
argument_list|(
name|Globals
operator|.
name|JOURNALS_FILE_BUILTIN
argument_list|)
expr_stmt|;
comment|//repository.readJournalListFromResource(Globals.JOURNALS_IEEE_INTERNAL_LIST);
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|repository
operator|.
name|toPropertiesString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

