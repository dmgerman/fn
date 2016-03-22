begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|search
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
name|*
import|;
end_import

begin_class
DECL|class|DatabaseSearcherTest
specifier|public
class|class
name|DatabaseSearcherTest
block|{
DECL|field|INVALID_SEARCH_QUERY
specifier|public
specifier|static
specifier|final
name|SearchQuery
name|INVALID_SEARCH_QUERY
init|=
operator|new
name|SearchQuery
argument_list|(
literal|"\\asd123{}asdf"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
decl_stmt|;
annotation|@
name|Test
DECL|method|testGetDatabaseFromMatchesEmptyDatabase ()
specifier|public
name|void
name|testGetDatabaseFromMatchesEmptyDatabase
parameter_list|()
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|BibDatabase
name|newDatabase
init|=
operator|new
name|DatabaseSearcher
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"whatever"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
argument_list|,
name|database
argument_list|)
operator|.
name|getDatabaseFromMatches
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|newDatabase
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDatabaseFromMatchesEmptyDatabaseInvalidSearchExpression ()
specifier|public
name|void
name|testGetDatabaseFromMatchesEmptyDatabaseInvalidSearchExpression
parameter_list|()
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|BibDatabase
name|newDatabase
init|=
operator|new
name|DatabaseSearcher
argument_list|(
name|INVALID_SEARCH_QUERY
argument_list|,
name|database
argument_list|)
operator|.
name|getDatabaseFromMatches
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|newDatabase
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDatabaseFromMatchesDatabaseWithEmptyEntries ()
specifier|public
name|void
name|testGetDatabaseFromMatchesDatabaseWithEmptyEntries
parameter_list|()
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
decl_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
operator|new
name|BibEntry
argument_list|()
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDatabase
init|=
operator|new
name|DatabaseSearcher
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"whatever"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
argument_list|,
name|database
argument_list|)
operator|.
name|getDatabaseFromMatches
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|newDatabase
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDatabaseFromMatchesDatabaseWithEntries ()
specifier|public
name|void
name|testGetDatabaseFromMatchesDatabaseWithEntries
parameter_list|()
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
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
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"harrer"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDatabase
init|=
operator|new
name|DatabaseSearcher
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"whatever"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
argument_list|,
name|database
argument_list|)
operator|.
name|getDatabaseFromMatches
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|newDatabase
operator|.
name|getEntries
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetDatabaseFromMatchesDatabaseWithEntriesWithCorrectMatch ()
specifier|public
name|void
name|testGetDatabaseFromMatchesDatabaseWithEntriesWithCorrectMatch
parameter_list|()
block|{
name|BibDatabase
name|database
init|=
operator|new
name|BibDatabase
argument_list|()
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
name|setType
argument_list|(
literal|"article"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"harrer"
argument_list|)
expr_stmt|;
name|database
operator|.
name|insertEntry
argument_list|(
name|entry
argument_list|)
expr_stmt|;
name|BibDatabase
name|newDatabase
init|=
operator|new
name|DatabaseSearcher
argument_list|(
operator|new
name|SearchQuery
argument_list|(
literal|"harrer"
argument_list|,
literal|true
argument_list|,
literal|true
argument_list|)
argument_list|,
name|database
argument_list|)
operator|.
name|getDatabaseFromMatches
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|newDatabase
operator|.
name|getEntryCount
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|entry
argument_list|,
name|newDatabase
operator|.
name|getEntries
argument_list|()
operator|.
name|iterator
argument_list|()
operator|.
name|next
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

