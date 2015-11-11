begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter
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
name|assertEquals
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
name|logic
operator|.
name|formatter
operator|.
name|bibtexfields
operator|.
name|AuthorsFormatter
import|;
end_import

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

begin_class
DECL|class|AuthorsFormatterTest
specifier|public
class|class
name|AuthorsFormatterTest
block|{
DECL|field|formatter
specifier|private
name|AuthorsFormatter
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
name|AuthorsFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|teardown ()
specifier|public
name|void
name|teardown
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
literal|"Staci D. Bilbo"
argument_list|)
expr_stmt|;
comment|// TODO strange behaviour
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
literal|"Ãlver MA, GG Ãie, Ãie GG, Alfredsen JÃÃ, Jo Alfredsen, Olsen Y.Y. and Olsen Y. Y."
argument_list|,
literal|"Ãlver, M. A. and Ãie, G. G. and Ãie, G. G. and Alfredsen, J. Ã. Ã. and Alfredsen, Jo and Olsen, Y. Y. and Olsen, Y. Y."
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"Ãlver MA, GG Ãie, Ãie GG, Alfredsen JÃÃ, Jo Alfredsen, Olsen Y.Y., Olsen Y. Y."
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
comment|// TODO: expectCorrect("Kolb, Stefan and J{\\\"o}rg Lenhard and Wirtz, Guido", "Kolb, Stefan and Lenhard, J{\\\"o}rg and Wirtz, Guido");
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

