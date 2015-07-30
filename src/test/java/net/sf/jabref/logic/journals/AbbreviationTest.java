begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
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
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_class
DECL|class|AbbreviationTest
specifier|public
class|class
name|AbbreviationTest
block|{
annotation|@
name|Test
DECL|method|testAbbreviationsWithTrailingSpaces ()
specifier|public
name|void
name|testAbbreviationsWithTrailingSpaces
parameter_list|()
block|{
name|Abbreviation
name|abbreviation
init|=
operator|new
name|Abbreviation
argument_list|(
literal|" Long Name "
argument_list|,
literal|" L. N. "
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Long Name"
argument_list|,
name|abbreviation
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L. N."
argument_list|,
name|abbreviation
operator|.
name|getIsoAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L N"
argument_list|,
name|abbreviation
operator|.
name|getMedlineAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAbbreviationsWithUnusedElements ()
specifier|public
name|void
name|testAbbreviationsWithUnusedElements
parameter_list|()
block|{
name|Abbreviation
name|abbreviation
init|=
operator|new
name|Abbreviation
argument_list|(
literal|" Long Name "
argument_list|,
literal|" L. N.;LN;M"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Long Name"
argument_list|,
name|abbreviation
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L. N."
argument_list|,
name|abbreviation
operator|.
name|getIsoAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L N"
argument_list|,
name|abbreviation
operator|.
name|getMedlineAbbreviation
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetNextElement ()
specifier|public
name|void
name|testGetNextElement
parameter_list|()
block|{
name|Abbreviation
name|abbreviation
init|=
operator|new
name|Abbreviation
argument_list|(
literal|" Long Name "
argument_list|,
literal|" L. N.;LN;M"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"L. N."
argument_list|,
name|abbreviation
operator|.
name|getNext
argument_list|(
literal|"Long Name"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L N"
argument_list|,
name|abbreviation
operator|.
name|getNext
argument_list|(
literal|"L. N."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Long Name"
argument_list|,
name|abbreviation
operator|.
name|getNext
argument_list|(
literal|"L N"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetNextElementWithTrailingSpaces ()
specifier|public
name|void
name|testGetNextElementWithTrailingSpaces
parameter_list|()
block|{
name|Abbreviation
name|abbreviation
init|=
operator|new
name|Abbreviation
argument_list|(
literal|" Long Name "
argument_list|,
literal|" L. N.; LN ;M "
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"L. N."
argument_list|,
name|abbreviation
operator|.
name|getNext
argument_list|(
literal|" Long Name "
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"L N"
argument_list|,
name|abbreviation
operator|.
name|getNext
argument_list|(
literal|" L. N. "
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Long Name"
argument_list|,
name|abbreviation
operator|.
name|getNext
argument_list|(
literal|" L N "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIsoAndMedlineAbbreviationsAreSame ()
specifier|public
name|void
name|testIsoAndMedlineAbbreviationsAreSame
parameter_list|()
block|{
name|Abbreviation
name|abbreviation
init|=
operator|new
name|Abbreviation
argument_list|(
literal|" Long Name "
argument_list|,
literal|" L N "
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|abbreviation
operator|.
name|hasIsoAndMedlineAbbreviationsAreSame
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

