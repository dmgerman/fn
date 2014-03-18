begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.bst
package|package
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
name|BibtexWidth
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

begin_comment
comment|/**  * How to create these test using Bibtex:  *   * Execute this charWidth.bst with the following charWidth.aux:  *   *   *<code>  ENTRY{}{}{}  FUNCTION{test}  {  "i" width$ int.to.str$ write$ newline$  "0I~ " width$ int.to.str$ write$ newline$  "Hi Hi " width$ int.to.str$ write$ newline$  "{\oe}" width$ int.to.str$ write$ newline$  "Hi {\oe   }Hi " width$ int.to.str$ write$ newline$  }  READ  EXECUTE{test}</code>  *   *<code>  \bibstyle{charWidth}  \citation{canh05}  \bibdata{test}  \bibcite{canh05}{CMM{$^{+}$}05}</code>  *   * @author $Author$  * @version $Revision$ ($Date$)  *   */
end_comment

begin_class
DECL|class|BibtexWidthTest
specifier|public
class|class
name|BibtexWidthTest
extends|extends
name|TestCase
block|{
DECL|method|assertBibtexWidth (final int i, final String string)
name|void
name|assertBibtexWidth
parameter_list|(
specifier|final
name|int
name|i
parameter_list|,
specifier|final
name|String
name|string
parameter_list|)
block|{
name|assertEquals
argument_list|(
name|i
argument_list|,
name|BibtexWidth
operator|.
name|width
argument_list|(
name|string
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
literal|"Should not Warn! Width should be "
operator|+
name|i
operator|+
literal|" for "
operator|+
name|string
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|testWidth ()
specifier|public
name|void
name|testWidth
parameter_list|()
block|{
name|assertBibtexWidth
argument_list|(
literal|278
argument_list|,
literal|"i"
argument_list|)
expr_stmt|;
name|assertBibtexWidth
argument_list|(
literal|1639
argument_list|,
literal|"0I~ "
argument_list|)
expr_stmt|;
name|assertBibtexWidth
argument_list|(
literal|2612
argument_list|,
literal|"Hi Hi "
argument_list|)
expr_stmt|;
name|assertBibtexWidth
argument_list|(
literal|778
argument_list|,
literal|"{\\oe}"
argument_list|)
expr_stmt|;
name|assertBibtexWidth
argument_list|(
literal|3390
argument_list|,
literal|"Hi {\\oe   }Hi "
argument_list|)
expr_stmt|;
name|assertBibtexWidth
argument_list|(
literal|444
argument_list|,
literal|"{\\'e}"
argument_list|)
expr_stmt|;
name|assertBibtexWidth
argument_list|(
literal|19762
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
name|assertBibtexWidth
argument_list|(
literal|7861
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertBibtexWidth
argument_list|(
literal|30514
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
block|}
DECL|method|testGetCharWidth ()
specifier|public
name|void
name|testGetCharWidth
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|500
argument_list|,
name|BibtexWidth
operator|.
name|getCharWidth
argument_list|(
literal|'0'
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|361
argument_list|,
name|BibtexWidth
operator|.
name|getCharWidth
argument_list|(
literal|'I'
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|500
argument_list|,
name|BibtexWidth
operator|.
name|getCharWidth
argument_list|(
literal|'~'
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|500
argument_list|,
name|BibtexWidth
operator|.
name|getCharWidth
argument_list|(
literal|'}'
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|278
argument_list|,
name|BibtexWidth
operator|.
name|getCharWidth
argument_list|(
literal|' '
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

