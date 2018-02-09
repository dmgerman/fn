begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.l10n
package|package
name|org
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
name|jupiter
operator|.
name|api
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
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
name|jupiter
operator|.
name|api
operator|.
name|Assertions
operator|.
name|assertThrows
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
block|{
name|assertEquals
argument_list|(
literal|"biblatex mode"
argument_list|,
operator|new
name|LocalizationKeyParams
argument_list|(
literal|"biblatex mode"
argument_list|)
operator|.
name|replacePlaceholders
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"biblatex mode"
argument_list|,
operator|new
name|LocalizationKeyParams
argument_list|(
literal|"%0 mode"
argument_list|,
literal|"biblatex"
argument_list|)
operator|.
name|replacePlaceholders
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"C:\\bla mode"
argument_list|,
operator|new
name|LocalizationKeyParams
argument_list|(
literal|"%0 mode"
argument_list|,
literal|"C:\\bla"
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
literal|"What \n : %e %c %0 %1"
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
name|assertEquals
argument_list|(
literal|"What \n : %e %c_a b"
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
annotation|@
name|Test
DECL|method|testTooManyParams ()
specifier|public
name|void
name|testTooManyParams
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|IllegalStateException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|LocalizationKeyParams
argument_list|(
literal|""
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|,
literal|"0"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

