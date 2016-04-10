begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
package|package
name|net
operator|.
name|sf
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
name|After
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

begin_comment
comment|/**  * Tests in addition to the general tests from {@link net.sf.jabref.logic.formatter.FormatterTest}  */
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
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|formatter
operator|=
literal|null
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
name|expectCorrect
argument_list|(
literal|"Staci D Bilbo"
argument_list|,
literal|"Bilbo, Staci D."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Staci D. Bilbo"
argument_list|,
literal|"Bilbo, Staci D."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Staci D Bilbo and Smith SH and Jaclyn M Schwarz"
argument_list|,
literal|"Bilbo, Staci D. and Smith, S. H. and Schwarz, Jaclyn M."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Ãlver MA"
argument_list|,
literal|"Ãlver, M. A."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Ãlver MA; GG Ãie; Ãie GG; Alfredsen JÃÃ; Jo Alfredsen; Olsen Y.Y. and Olsen YY."
argument_list|,
literal|"Ãlver, M. A. and Ãie, G. G. and Ãie, G. G. and Alfredsen, J. Ã. Ã. and Alfredsen, Jo and Olsen, Y. Y. and Olsen, Y. Y."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Ãlver MA; GG Ãie; Ãie GG; Alfredsen JÃÃ; Jo Alfredsen; Olsen Y.Y.; Olsen YY."
argument_list|,
literal|"Ãlver, M. A. and Ãie, G. G. and Ãie, G. G. and Alfredsen, J. Ã. Ã. and Alfredsen, Jo and Olsen, Y. Y. and Olsen, Y. Y."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Alver, Morten and Alver, Morten O and Alfredsen, JA and Olsen, Y.Y."
argument_list|,
literal|"Alver, Morten and Alver, Morten O. and Alfredsen, J. A. and Olsen, Y. Y."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Alver, MA; Alfredsen, JA; Olsen Y.Y."
argument_list|,
literal|"Alver, M. A. and Alfredsen, J. A. and Olsen, Y. Y."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Kolb, Stefan and J{\\\"o}rg Lenhard and Wirtz, Guido"
argument_list|,
literal|"Kolb, Stefan and Lenhard, J{\\\"o}rg and Wirtz, Guido"
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
name|expectCorrect
argument_list|(
literal|"Staci Bilbo; Morten Alver"
argument_list|,
literal|"Bilbo, Staci and Alver, Morten"
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
name|expectCorrect
argument_list|(
literal|"Staci Bilbo; Morten Alver; Test Name"
argument_list|,
literal|"Bilbo, Staci and Alver, Morten and Name, Test"
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
name|expectCorrect
argument_list|(
literal|"Stefan Kolb and J{\\\"o}rg Lenhard and Guido Wirtz"
argument_list|,
literal|"Kolb, Stefan and Lenhard, J{\\\"o}rg and Wirtz, Guido"
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
name|expectCorrect
argument_list|(
literal|"Heng-Yu Jian and Xu, Z. and Chang, M.-C.F."
argument_list|,
literal|"Jian, Heng-Yu and Xu, Z. and Chang, M.-C. F."
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
name|expectCorrect
argument_list|(
literal|"Oscar Gustafsson and Linda S. DeBrunner and Victor DeBrunner and H{\\aa}kan Johansson"
argument_list|,
literal|"Gustafsson, Oscar and DeBrunner, Linda S. and DeBrunner, Victor and Johansson, H{\\aa}kan"
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
name|expectCorrect
argument_list|(
literal|"Smith S"
argument_list|,
literal|"Smith, S."
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
name|expectCorrect
argument_list|(
literal|"Smith SH"
argument_list|,
literal|"Smith, S. H."
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
name|expectCorrect
argument_list|(
literal|"S Smith"
argument_list|,
literal|"Smith, S."
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
name|expectCorrect
argument_list|(
literal|"S. Smith"
argument_list|,
literal|"Smith, S."
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
name|expectCorrect
argument_list|(
literal|"SH Smith"
argument_list|,
literal|"Smith, S. H."
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
name|expectCorrect
argument_list|(
literal|"Name, della, first"
argument_list|,
literal|"Name, della, first"
argument_list|)
expr_stmt|;
block|}
DECL|method|expectCorrect (String input, String expected)
specifier|private
name|void
name|expectCorrect
parameter_list|(
name|String
name|input
parameter_list|,
name|String
name|expected
parameter_list|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

