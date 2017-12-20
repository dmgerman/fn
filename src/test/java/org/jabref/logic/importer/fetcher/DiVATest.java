begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.importer.fetcher
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|fetcher
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
name|logic
operator|.
name|help
operator|.
name|HelpFile
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|importer
operator|.
name|ImportFormatPreferences
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
name|testutils
operator|.
name|category
operator|.
name|FetcherTest
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
name|org
operator|.
name|junit
operator|.
name|experimental
operator|.
name|categories
operator|.
name|Category
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|Answers
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

begin_import
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
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
name|Assert
operator|.
name|assertTrue
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
annotation|@
name|Category
argument_list|(
name|FetcherTest
operator|.
name|class
argument_list|)
DECL|class|DiVATest
specifier|public
class|class
name|DiVATest
block|{
DECL|field|fetcher
specifier|private
name|DiVA
name|fetcher
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|fetcher
operator|=
operator|new
name|DiVA
argument_list|(
name|mock
argument_list|(
name|ImportFormatPreferences
operator|.
name|class
argument_list|,
name|Answers
operator|.
name|RETURNS_DEEP_STUBS
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetName ()
specifier|public
name|void
name|testGetName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"DiVA"
argument_list|,
name|fetcher
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetHelpPage ()
specifier|public
name|void
name|testGetHelpPage
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|HelpFile
operator|.
name|FETCHER_DIVA
argument_list|,
name|HelpFile
operator|.
name|FETCHER_DIVA
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPerformSearchById ()
specifier|public
name|void
name|testPerformSearchById
parameter_list|()
throws|throws
name|Exception
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
literal|"Gustafsson, Oscar"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"institution"
argument_list|,
literal|"LinkÃ¶ping University, The Institute of Technology"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setCiteKey
argument_list|(
literal|"Gustafsson260746"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"IEEE transactions on circuits and systems. 2, Analog and digital signal processing (Print)"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
literal|"11"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"974--978"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Lower bounds for constant multiplication problems"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"54"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"2007"
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
literal|"Lower bounds for problems related to realizing multiplication by constants with shifts, adders, and subtracters are presented. These lower bounds are straightforwardly calculated and have applications in proving the optimality of solutions obtained by heuristics. "
argument_list|)
expr_stmt|;
name|entry
operator|.
name|setField
argument_list|(
literal|"doi"
argument_list|,
literal|"10.1109/TCSII.2007.903212"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|entry
argument_list|)
argument_list|,
name|fetcher
operator|.
name|performSearchById
argument_list|(
literal|"diva2:260746"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testValidIdentifier ()
specifier|public
name|void
name|testValidIdentifier
parameter_list|()
block|{
name|assertTrue
argument_list|(
name|fetcher
operator|.
name|isValidId
argument_list|(
literal|"diva2:260746"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testInvalidIdentifier ()
specifier|public
name|void
name|testInvalidIdentifier
parameter_list|()
block|{
name|assertFalse
argument_list|(
name|fetcher
operator|.
name|isValidId
argument_list|(
literal|"banana"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

