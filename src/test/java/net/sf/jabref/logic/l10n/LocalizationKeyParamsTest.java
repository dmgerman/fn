begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.l10n
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
package|;
end_package

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

begin_class
DECL|class|LocalizationKeyParamsTest
specifier|public
class|class
name|LocalizationKeyParamsTest
block|{
annotation|@
name|Test
DECL|method|testReplacePlaceholders ()
specifier|public
name|void
name|testReplacePlaceholders
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
literal|"BibLaTeX mode"
argument_list|,
operator|new
name|LocalizationKeyParams
argument_list|(
literal|"BibLaTeX mode"
argument_list|)
operator|.
name|replacePlaceholders
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"BibLaTeX mode"
argument_list|,
operator|new
name|LocalizationKeyParams
argument_list|(
literal|"%0 mode"
argument_list|,
literal|"BibLaTeX"
argument_list|)
operator|.
name|replacePlaceholders
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"What \n : %e %c a b"
argument_list|,
operator|new
name|LocalizationKeyParams
argument_list|(
literal|"What \n : %e %c_%0 %1"
argument_list|,
literal|"a"
argument_list|,
literal|"b"
argument_list|)
operator|.
name|replacePlaceholders
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

