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
DECL|class|LatexToUnicodeFormatterTest
specifier|public
class|class
name|LatexToUnicodeFormatterTest
block|{
DECL|field|formatter
specifier|public
specifier|final
name|LatexToUnicodeFormatter
name|formatter
init|=
operator|new
name|LatexToUnicodeFormatter
argument_list|()
decl_stmt|;
annotation|@
name|Test
DECL|method|testPlainFormat ()
specifier|public
name|void
name|testPlainFormat
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aaa"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"aaa"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatUmlaut ()
specifier|public
name|void
name|testFormatUmlaut
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ã¤"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\\"{a}}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ã"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\\"{A}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatStripLatexCommands ()
specifier|public
name|void
name|testFormatStripLatexCommands
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"-"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\mbox{-}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatTextit ()
specifier|public
name|void
name|testFormatTextit
parameter_list|()
block|{
comment|// See #1464
name|assertEquals
argument_list|(
literal|"text"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\textit{text}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEscapedDollarSign ()
specifier|public
name|void
name|testEscapedDollarSign
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"$"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\$"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEquationsSingleSymbol ()
specifier|public
name|void
name|testEquationsSingleSymbol
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ï"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"$\\sigma$"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testEquationsMoreComplicatedFormatting ()
specifier|public
name|void
name|testEquationsMoreComplicatedFormatting
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"A 32 mA Î£Î-modulator"
argument_list|,
name|formatter
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
DECL|method|formatExample ()
specifier|public
name|void
name|formatExample
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"MÃ¶nch"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|formatter
operator|.
name|getExampleInput
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChi ()
specifier|public
name|void
name|testChi
parameter_list|()
block|{
comment|// See #1464
name|assertEquals
argument_list|(
literal|"Ï"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"$\\chi$"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSWithCaron ()
specifier|public
name|void
name|testSWithCaron
parameter_list|()
block|{
comment|// Bug #1264
name|assertEquals
argument_list|(
literal|"Å "
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\v{S}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIWithDiaresis ()
specifier|public
name|void
name|testIWithDiaresis
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ã¯"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"{i}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIWithDiaresisAndEscapedI ()
specifier|public
name|void
name|testIWithDiaresisAndEscapedI
parameter_list|()
block|{
comment|// this might look strange in the test, but is actually a correct translation and renders identically to the above example in the UI
name|assertEquals
argument_list|(
literal|"Ä±Ì"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"{\\i}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testIWithDiaresisAndUnnecessaryBraces ()
specifier|public
name|void
name|testIWithDiaresisAndUnnecessaryBraces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ã¯"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\\"{i}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUpperCaseIWithDiaresis ()
specifier|public
name|void
name|testUpperCaseIWithDiaresis
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ã"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"{I}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testPolishName ()
specifier|public
name|void
name|testPolishName
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"ÅÄski"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\L\\k{e}ski"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDoubleCombiningAccents ()
specifier|public
name|void
name|testDoubleCombiningAccents
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ï"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"$\\acute{\\omega}$"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testCombiningAccentsCase1 ()
specifier|public
name|void
name|testCombiningAccentsCase1
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"á¸©"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\c{h}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Ignore
argument_list|(
literal|"This is not a standard LaTeX command. It is debatable why we should convert this."
argument_list|)
annotation|@
name|Test
DECL|method|testCombiningAccentsCase2 ()
specifier|public
name|void
name|testCombiningAccentsCase2
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"aÍ"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\spreadlips{a}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownCommandIsIgnored ()
specifier|public
name|void
name|unknownCommandIsIgnored
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\aaaa"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownCommandKeepsArgument ()
specifier|public
name|void
name|unknownCommandKeepsArgument
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"bbbb"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\aaaa{bbbb}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|unknownCommandWithEmptyArgumentIsIgnored ()
specifier|public
name|void
name|unknownCommandWithEmptyArgumentIsIgnored
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\aaaa{}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTildeN ()
specifier|public
name|void
name|testTildeN
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"MontaÃ±a"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Monta\\~{n}a"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAcuteNLongVersion ()
specifier|public
name|void
name|testAcuteNLongVersion
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"MaliÅski"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Mali\\'{n}ski"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"MaliÅski"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Mali\\'{N}ski"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testAcuteNShortVersion ()
specifier|public
name|void
name|testAcuteNShortVersion
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"MaliÅski"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Mali\\'nski"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"MaliÅski"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Mali\\'Nski"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testApostrophN ()
specifier|public
name|void
name|testApostrophN
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Mali'nski"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Mali'nski"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Mali'Nski"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Mali'Nski"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testApostrophO ()
specifier|public
name|void
name|testApostrophO
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"L'oscillation"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"L'oscillation"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testApostrophC ()
specifier|public
name|void
name|testApostrophC
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"O'Connor"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"O'Connor"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

