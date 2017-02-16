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
name|Assert
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
DECL|class|ShippedJournalAbbreviationDuplicateTest
specifier|public
class|class
name|ShippedJournalAbbreviationDuplicateTest
block|{
annotation|@
name|Test
DECL|method|noDuplicatesInShippedIEEEOfficialJournalAbbreviations ()
specifier|public
name|void
name|noDuplicatesInShippedIEEEOfficialJournalAbbreviations
parameter_list|()
block|{
name|JournalAbbreviationRepository
name|repoBuiltIn
init|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
decl_stmt|;
name|repoBuiltIn
operator|.
name|addEntries
argument_list|(
name|JournalAbbreviationLoader
operator|.
name|getBuiltInAbbreviations
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|JournalAbbreviationLoader
operator|.
name|getOfficialIEEEAbbreviations
argument_list|()
control|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"duplicate name "
operator|+
name|abbreviation
operator|.
name|toString
argument_list|()
argument_list|,
name|repoBuiltIn
operator|.
name|getAbbreviation
argument_list|(
name|abbreviation
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"duplicate iso "
operator|+
name|abbreviation
operator|.
name|toString
argument_list|()
argument_list|,
name|repoBuiltIn
operator|.
name|getAbbreviation
argument_list|(
name|abbreviation
operator|.
name|getIsoAbbreviation
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"duplicate medline "
operator|+
name|abbreviation
operator|.
name|toString
argument_list|()
argument_list|,
name|repoBuiltIn
operator|.
name|getAbbreviation
argument_list|(
name|abbreviation
operator|.
name|getMedlineAbbreviation
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Test
DECL|method|noDuplicatesInShippedIEEEStandardJournalAbbreviations ()
specifier|public
name|void
name|noDuplicatesInShippedIEEEStandardJournalAbbreviations
parameter_list|()
block|{
name|JournalAbbreviationRepository
name|repoBuiltIn
init|=
operator|new
name|JournalAbbreviationRepository
argument_list|()
decl_stmt|;
name|repoBuiltIn
operator|.
name|addEntries
argument_list|(
name|JournalAbbreviationLoader
operator|.
name|getBuiltInAbbreviations
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Abbreviation
name|abbreviation
range|:
name|JournalAbbreviationLoader
operator|.
name|getStandardIEEEAbbreviations
argument_list|()
control|)
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"duplicate name "
operator|+
name|abbreviation
operator|.
name|toString
argument_list|()
argument_list|,
name|repoBuiltIn
operator|.
name|getAbbreviation
argument_list|(
name|abbreviation
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"duplicate iso "
operator|+
name|abbreviation
operator|.
name|toString
argument_list|()
argument_list|,
name|repoBuiltIn
operator|.
name|getAbbreviation
argument_list|(
name|abbreviation
operator|.
name|getIsoAbbreviation
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertFalse
argument_list|(
literal|"duplicate medline "
operator|+
name|abbreviation
operator|.
name|toString
argument_list|()
argument_list|,
name|repoBuiltIn
operator|.
name|getAbbreviation
argument_list|(
name|abbreviation
operator|.
name|getMedlineAbbreviation
argument_list|()
argument_list|)
operator|.
name|isPresent
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

