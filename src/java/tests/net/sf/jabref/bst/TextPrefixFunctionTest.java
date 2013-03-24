begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref.bst
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bst
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|bst
operator|.
name|BibtexTextPrefix
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
name|bst
operator|.
name|Warn
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestCase
import|;
end_import

begin_class
DECL|class|TextPrefixFunctionTest
specifier|public
class|class
name|TextPrefixFunctionTest
extends|extends
name|TestCase
block|{
DECL|method|testPrefix ()
specifier|public
name|void
name|testPrefix
parameter_list|()
block|{
name|assertPrefix
argument_list|(
literal|"i"
argument_list|,
literal|"i"
argument_list|)
expr_stmt|;
name|assertPrefix
argument_list|(
literal|"0I~ "
argument_list|,
literal|"0I~ "
argument_list|)
expr_stmt|;
name|assertPrefix
argument_list|(
literal|"Hi Hi"
argument_list|,
literal|"Hi Hi "
argument_list|)
expr_stmt|;
name|assertPrefix
argument_list|(
literal|"{\\oe}"
argument_list|,
literal|"{\\oe}"
argument_list|)
expr_stmt|;
name|assertPrefix
argument_list|(
literal|"Hi {\\oe   }H"
argument_list|,
literal|"Hi {\\oe   }Hi "
argument_list|)
expr_stmt|;
name|assertPrefix
argument_list|(
literal|"Jonat"
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
name|assertPrefix
argument_list|(
literal|"{\\'e}"
argument_list|,
literal|"{\\'e}"
argument_list|)
expr_stmt|;
name|assertPrefix
argument_list|(
literal|"{\\'{E}}doua"
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertPrefix
argument_list|(
literal|"Ulric"
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
block|}
DECL|method|assertPrefix (final String string, final String string2)
specifier|private
name|void
name|assertPrefix
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
name|BibtexTextPrefix
operator|.
name|textPrefix
argument_list|(
literal|5
argument_list|,
name|string2
argument_list|,
operator|new
name|Warn
argument_list|()
block|{
specifier|public
name|void
name|warn
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|fail
argument_list|(
literal|"Should not Warn! text.prefix$ should be "
operator|+
name|string
operator|+
literal|" for (5) "
operator|+
name|string2
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

