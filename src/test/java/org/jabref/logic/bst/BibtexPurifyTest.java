begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.bst
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bst
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
name|fail
import|;
end_import

begin_class
DECL|class|BibtexPurifyTest
specifier|public
class|class
name|BibtexPurifyTest
block|{
annotation|@
name|Test
DECL|method|testPurify ()
specifier|public
name|void
name|testPurify
parameter_list|()
block|{
name|assertPurify
argument_list|(
literal|"i"
argument_list|,
literal|"i"
argument_list|)
expr_stmt|;
name|assertPurify
argument_list|(
literal|"0I  "
argument_list|,
literal|"0I~ "
argument_list|)
expr_stmt|;
name|assertPurify
argument_list|(
literal|"Hi Hi "
argument_list|,
literal|"Hi Hi "
argument_list|)
expr_stmt|;
name|assertPurify
argument_list|(
literal|"oe"
argument_list|,
literal|"{\\oe}"
argument_list|)
expr_stmt|;
name|assertPurify
argument_list|(
literal|"Hi oeHi "
argument_list|,
literal|"Hi {\\oe   }Hi "
argument_list|)
expr_stmt|;
name|assertPurify
argument_list|(
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vallee Poussin"
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
name|assertPurify
argument_list|(
literal|"e"
argument_list|,
literal|"{\\'e}"
argument_list|)
expr_stmt|;
name|assertPurify
argument_list|(
literal|"Edouard Masterly"
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertPurify
argument_list|(
literal|"Ulrich Underwood and Ned Net and Paul Pot"
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
block|}
DECL|method|assertPurify (final String string, final String string2)
specifier|private
name|void
name|assertPurify
parameter_list|(
specifier|final
name|String
name|string
parameter_list|,
specifier|final
name|String
name|string2
parameter_list|)
block|{
name|assertEquals
argument_list|(
name|string
argument_list|,
name|BibtexPurify
operator|.
name|purify
argument_list|(
name|string2
argument_list|,
name|s
lambda|->
name|fail
argument_list|(
literal|"Should not Warn ("
operator|+
name|s
operator|+
literal|")! purify should be "
operator|+
name|string
operator|+
literal|" for "
operator|+
name|string2
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

