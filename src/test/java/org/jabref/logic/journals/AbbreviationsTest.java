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
name|runner
operator|.
name|RunWith
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|Mock
import|;
end_import

begin_import
import|import
name|org
operator|.
name|mockito
operator|.
name|runners
operator|.
name|MockitoJUnitRunner
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
name|mockito
operator|.
name|Mockito
operator|.
name|when
import|;
end_import

begin_class
annotation|@
name|RunWith
argument_list|(
name|MockitoJUnitRunner
operator|.
name|class
argument_list|)
DECL|class|AbbreviationsTest
specifier|public
class|class
name|AbbreviationsTest
block|{
annotation|@
name|Mock
DECL|field|prefs
specifier|private
name|JournalAbbreviationPreferences
name|prefs
decl_stmt|;
DECL|field|abbreviations
specifier|private
name|JournalAbbreviationLoader
name|abbreviations
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
name|abbreviations
operator|=
operator|new
name|JournalAbbreviationLoader
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getNextAbbreviationAbbreviatesIEEEJournalTitle ()
specifier|public
name|void
name|getNextAbbreviationAbbreviatesIEEEJournalTitle
parameter_list|()
block|{
name|when
argument_list|(
name|prefs
operator|.
name|isUseIEEEAbbreviations
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"#IEEE_J_PROC#"
argument_list|,
name|abbreviations
operator|.
name|getRepository
argument_list|(
name|prefs
argument_list|)
operator|.
name|getNextAbbreviation
argument_list|(
literal|"Proceedings of the IEEE"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getNextAbbreviationExpandsIEEEAbbreviation ()
specifier|public
name|void
name|getNextAbbreviationExpandsIEEEAbbreviation
parameter_list|()
block|{
name|when
argument_list|(
name|prefs
operator|.
name|isUseIEEEAbbreviations
argument_list|()
argument_list|)
operator|.
name|thenReturn
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Proceedings of the IEEE"
argument_list|,
name|abbreviations
operator|.
name|getRepository
argument_list|(
name|prefs
argument_list|)
operator|.
name|getNextAbbreviation
argument_list|(
literal|"#IEEE_J_PROC#"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getNextAbbreviationAbbreviatesJournalTitle ()
specifier|public
name|void
name|getNextAbbreviationAbbreviatesJournalTitle
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Proc. IEEE"
argument_list|,
name|abbreviations
operator|.
name|getRepository
argument_list|(
name|prefs
argument_list|)
operator|.
name|getNextAbbreviation
argument_list|(
literal|"Proceedings of the IEEE"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getNextAbbreviationRemovesPoint ()
specifier|public
name|void
name|getNextAbbreviationRemovesPoint
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Proc IEEE"
argument_list|,
name|abbreviations
operator|.
name|getRepository
argument_list|(
name|prefs
argument_list|)
operator|.
name|getNextAbbreviation
argument_list|(
literal|"Proc. IEEE"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getNextAbbreviationExpandsAbbreviation ()
specifier|public
name|void
name|getNextAbbreviationExpandsAbbreviation
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Proceedings of the IEEE"
argument_list|,
name|abbreviations
operator|.
name|getRepository
argument_list|(
name|prefs
argument_list|)
operator|.
name|getNextAbbreviation
argument_list|(
literal|"Proc IEEE"
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

