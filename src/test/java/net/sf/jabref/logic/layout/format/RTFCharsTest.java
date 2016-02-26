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
name|Ignore
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
DECL|class|RTFCharsTest
specifier|public
class|class
name|RTFCharsTest
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
name|RTFChars
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
comment|// We should be able to replace the ? with e
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"R\\u233?flexions sur le timing de la quantit\\u233?"
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
literal|"h\\u225allo"
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
literal|"h\\u225allo"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"h\\'allo"
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
name|RTFChars
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{\\i hallo}"
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
literal|"{\\i hallo}"
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
literal|"{\\i hallo}"
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
literal|"{\\i hallo}"
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
literal|"{\\b hallo}"
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
literal|"{\\b hallo}"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"{\\textbf hallo}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testComplicated ()
specifier|public
name|void
name|testComplicated
parameter_list|()
block|{
name|LayoutFormatter
name|layout
init|=
operator|new
name|RTFChars
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"R\\u233eflexions sur le timing de la quantit\\u233e \\u230ae should be \\u230ae"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"RÃ©flexions sur le timing de la quantitÃ© \\ae should be Ã¦"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"h\\u225all{\\uc2\\u339oe}"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"h\\'all\\oe "
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
annotation|@
name|Ignore
DECL|method|testSpecialCharacters ()
specifier|public
name|void
name|testSpecialCharacters
parameter_list|()
block|{
name|LayoutFormatter
name|layout
init|=
operator|new
name|RTFChars
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\\u243o"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\'{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã³
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\\'f2"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\`{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã²
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\\'f4"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\^{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã´
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\\'f6"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\\"{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã¶
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\\u245o"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\~{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ãµ
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\\u333o"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\={o}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\\u334O"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\u{o}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\\u231c"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\c{c}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã§
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{\\uc2\\u339oe}"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\oe"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{\\uc2\\u338OE}"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\OE"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{\\uc2\\u230ae}"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\ae"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã¦
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{\\uc2\\u198AE}"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\AE"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã
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
literal|"\\.{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// ???
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
literal|"\\v{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// ???
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
literal|"\\H{a}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã£ // ???
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
literal|"\\t{oo}"
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"\\d{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// ???
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
literal|"\\b{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// ???
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
literal|"\\aa"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã¥
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
literal|"\\AA"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã
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
literal|"\\o"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã¸
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
literal|"\\O"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã
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
literal|"\\l"
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"\\L"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{\\uc2\\u223ss}"
argument_list|,
name|layout
operator|.
name|format
argument_list|(
literal|"\\ss"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã
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
literal|"?`"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â¿
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
literal|"!`"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â¡
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
literal|"\\dag"
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"\\ddag"
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"\\S"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â§
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
literal|"\\P"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â¶
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
literal|"\\copyright"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â©
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
literal|"\\pounds"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â£
block|}
block|}
end_class

end_unit

