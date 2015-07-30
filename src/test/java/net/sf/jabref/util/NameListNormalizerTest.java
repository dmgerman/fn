begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
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
name|util
operator|.
name|NameListNormalizer
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
DECL|class|NameListNormalizerTest
specifier|public
class|class
name|NameListNormalizerTest
block|{
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
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
literal|"Staci D Bilbo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Staci D. Bilbo"
argument_list|,
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
literal|"Staci D. Bilbo"
argument_list|)
argument_list|)
expr_stmt|;
comment|// TODO strange behaviour
name|assertEquals
argument_list|(
literal|"Bilbo, Staci D. and Smith, S. H. and Schwarz, Jaclyn M."
argument_list|,
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
literal|"Staci D Bilbo and Smith SH and Jaclyn M Schwarz"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ãlver, M. A."
argument_list|,
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
literal|"Ãlver MA"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ãlver, M. A. and Ãie, G. G. and Ãie, G. G. and Alfredsen, J. Ã. Ã. and Alfredsen, Jo and Olsen, Y. Y. and Olsen, Y. Y."
argument_list|,
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
literal|"Ãlver MA, GG Ãie, Ãie GG, Alfredsen JÃÃ, Jo Alfredsen, Olsen Y.Y. and Olsen Y. Y."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ãlver, M. A. and Ãie, G. G. and Ãie, G. G. and Alfredsen, J. Ã. Ã. and Alfredsen, Jo and Olsen, Y. Y. and Olsen, Y. Y."
argument_list|,
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
literal|"Ãlver MA, GG Ãie, Ãie GG, Alfredsen JÃÃ, Jo Alfredsen, Olsen Y.Y., Olsen Y. Y."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Alver, Morten and Alver, Morten O. and Alfredsen, J. A. and Olsen, Y. Y."
argument_list|,
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
literal|"Alver, Morten and Alver, Morten O and Alfredsen, JA and Olsen, Y.Y."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Alver, M. A. and Alfredsen, J. A. and Olsen, Y. Y."
argument_list|,
name|NameListNormalizer
operator|.
name|normalizeAuthorList
argument_list|(
literal|"Alver, MA; Alfredsen, JA; Olsen Y.Y."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

