begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.formatter.bibtexfields
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
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

begin_comment
comment|/**  * Tests in addition to the general tests from {@link org.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|NormalizeNamesFormatterTest
specifier|public
class|class
name|NormalizeNamesFormatterTest
block|{
DECL|field|formatter
specifier|private
name|NormalizeNamesFormatter
name|formatter
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|NormalizeNamesFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNormalizeAuthorList ()
specifier|public
name|void
name|testNormalizeAuthorList
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Bilbo, Staci D."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Staci D Bilbo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Bilbo, Staci D."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Staci D. Bilbo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Bilbo, Staci D. and Smith, S. H. and Schwarz, Jaclyn M."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Staci D Bilbo and Smith SH and Jaclyn M Schwarz"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ãlver, M. A."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Ãlver MA"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ãlver, M. A. and Ãie, G. G. and Ãie, G. G. and Alfredsen, J. Ã. Ã. and Alfredsen, Jo and Olsen, Y. Y. and Olsen, Y. Y."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Ãlver MA; GG Ãie; Ãie GG; Alfredsen JÃÃ; Jo Alfredsen; Olsen Y.Y. and Olsen YY."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ãlver, M. A. and Ãie, G. G. and Ãie, G. G. and Alfredsen, J. Ã. Ã. and Alfredsen, Jo and Olsen, Y. Y. and Olsen, Y. Y."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Ãlver MA; GG Ãie; Ãie GG; Alfredsen JÃÃ; Jo Alfredsen; Olsen Y.Y.; Olsen YY."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Alver, Morten and Alver, Morten O. and Alfredsen, J. A. and Olsen, Y. Y."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Alver, Morten and Alver, Morten O and Alfredsen, JA and Olsen, Y.Y."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Alver, M. A. and Alfredsen, J. A. and Olsen, Y. Y."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Alver, MA; Alfredsen, JA; Olsen Y.Y."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Kolb, Stefan and Lenhard, J{\\\"o}rg and Wirtz, Guido"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Kolb, Stefan and J{\\\"o}rg Lenhard and Wirtz, Guido"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|twoAuthorsSeperatedByColon ()
specifier|public
name|void
name|twoAuthorsSeperatedByColon
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Bilbo, Staci and Alver, Morten"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Staci Bilbo; Morten Alver"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|threeAuthorsSeperatedByColon ()
specifier|public
name|void
name|threeAuthorsSeperatedByColon
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Bilbo, Staci and Alver, Morten and Name, Test"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Staci Bilbo; Morten Alver; Test Name"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Test for https://github.com/JabRef/jabref/issues/318
annotation|@
name|Test
DECL|method|threeAuthorsSeperatedByAnd ()
specifier|public
name|void
name|threeAuthorsSeperatedByAnd
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Kolb, Stefan and Lenhard, J{\\\"o}rg and Wirtz, Guido"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Stefan Kolb and J{\\\"o}rg Lenhard and Guido Wirtz"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Test for https://github.com/JabRef/jabref/issues/318
annotation|@
name|Test
DECL|method|threeAuthorsSeperatedByAndWithDash ()
specifier|public
name|void
name|threeAuthorsSeperatedByAndWithDash
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Jian, Heng-Yu and Xu, Z. and Chang, M.-C. F."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Heng-Yu Jian and Xu, Z. and Chang, M.-C.F."
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Test for https://github.com/JabRef/jabref/issues/318
annotation|@
name|Test
DECL|method|threeAuthorsSeperatedByAndWithLatex ()
specifier|public
name|void
name|threeAuthorsSeperatedByAndWithLatex
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Gustafsson, Oscar and DeBrunner, Linda S. and DeBrunner, Victor and Johansson, H{\\aa}kan"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Oscar Gustafsson and Linda S. DeBrunner and Victor DeBrunner and H{\\aa}kan Johansson"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|lastThenInitial ()
specifier|public
name|void
name|lastThenInitial
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, S."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Smith S"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|lastThenInitials ()
specifier|public
name|void
name|lastThenInitials
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, S. H."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Smith SH"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|initialThenLast ()
specifier|public
name|void
name|initialThenLast
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, S."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"S Smith"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|initialDotThenLast ()
specifier|public
name|void
name|initialDotThenLast
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, S."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"S. Smith"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|initialsThenLast ()
specifier|public
name|void
name|initialsThenLast
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Smith, S. H."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"SH Smith"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|lastThenJuniorThenFirst ()
specifier|public
name|void
name|lastThenJuniorThenFirst
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Name, della, first"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Name, della, first"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testConcatenationOfAuthorsWithCommas ()
specifier|public
name|void
name|testConcatenationOfAuthorsWithCommas
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ali Babar, M. and DingsÃ¸yr, T. and Lago, P. and van der Vliet, H."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Ali Babar, M., DingsÃ¸yr, T., Lago, P., van der Vliet, H."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ali Babar, M."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Ali Babar, M."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testOddCountOfCommas ()
specifier|public
name|void
name|testOddCountOfCommas
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ali Babar, M., DingsÃ¸yr T. Lago P."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Ali Babar, M., DingsÃ¸yr, T., Lago P."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|formatExample ()
specifier|public
name|void
name|formatExample
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Einstein, Albert and Turing, Alan"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|formatter
operator|.
name|getExampleInput
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNameAffixe ()
specifier|public
name|void
name|testNameAffixe
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Surname, jr, First and Surname2, First2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Surname, jr, First, Surname2, First2"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAvoidSpecialCharacter ()
specifier|public
name|void
name|testAvoidSpecialCharacter
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Surname, {, First; Surname2, First2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Surname, {, First; Surname2, First2"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAndInName ()
specifier|public
name|void
name|testAndInName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Surname and , First, Surname2 First2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Surname, and , First, Surname2, First2"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMultipleNameAffixes ()
specifier|public
name|void
name|testMultipleNameAffixes
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Mair, Jr, Daniel and BrÃ¼hl, Sr, Daniel"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Mair, Jr, Daniel, BrÃ¼hl, Sr, Daniel"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCommaSeperatedNames ()
specifier|public
name|void
name|testCommaSeperatedNames
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Bosoi, Cristina and Oliveira, Mariana and Sanchez, Rafael Ochoa and Tremblay, MÃ©lanie and TenHave, Gabrie and Deutz, Nicoolas and Rose, Christopher F. and Bemeur, Chantal"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Cristina Bosoi, Mariana Oliveira, Rafael Ochoa Sanchez, MÃ©lanie Tremblay, Gabrie TenHave, Nicoolas Deutz, Christopher F. Rose, Chantal Bemeur"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMultipleSpaces ()
specifier|public
name|void
name|testMultipleSpaces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Bosoi, Cristina and Oliveira, Mariana and Sanchez, Rafael Ochoa and Tremblay, MÃ©lanie and TenHave, Gabrie and Deutz, Nicoolas and Rose, Christopher F. and Bemeur, Chantal"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Cristina    Bosoi,    Mariana Oliveira, Rafael Ochoa Sanchez   ,   MÃ©lanie Tremblay  , Gabrie TenHave, Nicoolas Deutz, Christopher F. Rose, Chantal Bemeur"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAvoidPreposition ()
specifier|public
name|void
name|testAvoidPreposition
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"von Zimmer, Hans and van Oberbergern, Michael and zu Berger, Kevin"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Hans von Zimmer, Michael van Oberbergern, Kevin zu Berger"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPreposition ()
specifier|public
name|void
name|testPreposition
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"von Zimmer, Hans and van Oberbergern, Michael and zu Berger, Kevin"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Hans von Zimmer, Michael van Oberbergern, Kevin zu Berger"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testOneCommaUntouched ()
specifier|public
name|void
name|testOneCommaUntouched
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Canon der Barbar, Alexander der GroÃe"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Canon der Barbar, Alexander der GroÃe"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAvoidNameAffixes ()
specifier|public
name|void
name|testAvoidNameAffixes
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"der Barbar, Canon and der GroÃe, Alexander and der Alexander, Peter"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Canon der Barbar, Alexander der GroÃe, Peter der Alexander"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUpperCaseSensitiveList ()
specifier|public
name|void
name|testUpperCaseSensitiveList
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"der Barbar, Canon and der GroÃe, Alexander"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Canon der Barbar AND Alexander der GroÃe"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"der Barbar, Canon and der GroÃe, Alexander"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Canon der Barbar aNd Alexander der GroÃe"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"der Barbar, Canon and der GroÃe, Alexander"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Canon der Barbar AnD Alexander der GroÃe"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSemiCorrectNamesWithSemicolon ()
specifier|public
name|void
name|testSemiCorrectNamesWithSemicolon
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Last, First and Last2, First2 and Last3, First3"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Last, First; Last2, First2; Last3, First3"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Last, Jr, First and Last2, First2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Last, Jr, First; Last2, First2"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Last, First and Last2, First2 and Last3, First3 and Last4, First4"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Last, First; Last2, First2; Last3, First3; First4 Last4"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Last and Last2, First2 and Last3, First3 and Last4, First4"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Last; Last2, First2; Last3, First3; Last4, First4"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

