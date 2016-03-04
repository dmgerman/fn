begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
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
name|logic
operator|.
name|layout
operator|.
name|LayoutFormatter
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
name|Test
import|;
end_import

begin_class
DECL|class|HTMLCharsTest
specifier|public
class|class
name|HTMLCharsTest
block|{
annotation|@
name|Test
DECL|method|testBasicFormat ()
specifier|public
name|void
name|testBasicFormat
parameter_list|()
block|{
name|LayoutFormatter
name|layout
init|=
operator|new
name|HTMLChars
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"hallo"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"hallo"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"RÃ©flexions sur le timing de la quantitÃ©"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"RÃ©flexions sur le timing de la quantitÃ©"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"h&aacute;llo"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"h\\'allo"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&imath;&imath;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\i \\i"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&imath;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\i"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&imath;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\{i}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&imath;&imath;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\i\\i"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&auml;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\\"{a}}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&auml;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\\"a}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&auml;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\\"a"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&Ccedil;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\c{C}}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&Lmidot;&imath;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\Lmidot\\i"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&ntilde;&ntilde;&iacute;&imath;&imath;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\~{n} \\~n \\'i \\i \\i"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testLaTeXHighlighting ()
specifier|public
name|void
name|testLaTeXHighlighting
parameter_list|()
block|{
name|LayoutFormatter
name|layout
init|=
operator|new
name|HTMLChars
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<em>hallo</em>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\emph{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<em>hallo</em>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\emph hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<em>hallo</em>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\em hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<i>hallo</i>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\textit{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<i>hallo</i>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\textit hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<i>hallo</i>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\it hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<b>hallo</b>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\textbf{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<b>hallo</b>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\textbf hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<b>hallo</b>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\bf hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<sup>hallo</sup>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\textsuperscript{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<sub>hallo</sub>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\textsubscript{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<u>hallo</u>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\underline{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<s>hallo</s>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\sout{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"<tt>hallo</tt>"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\texttt{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEquations ()
specifier|public
name|void
name|testEquations
parameter_list|()
block|{
name|LayoutFormatter
name|layout
init|=
operator|new
name|HTMLChars
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&dollar;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\$"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"&sigma;"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"$\\sigma$"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"A 32&nbsp;mA&Sigma;&Delta;-modulator"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"A 32~{mA} {$\\Sigma\\Delta$}-modulator"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNewLine ()
specifier|public
name|void
name|testNewLine
parameter_list|()
block|{
name|LayoutFormatter
name|layout
init|=
operator|new
name|HTMLChars
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"a<br>b"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"a\nb"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"a<p>b"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"a\n\nb"
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/*      * Is missing a lot of test cases for the individual chars...      */
block|}
end_class

end_unit

