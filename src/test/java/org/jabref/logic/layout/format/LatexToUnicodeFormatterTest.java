begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
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
name|jupiter
operator|.
name|api
operator|.
name|Disabled
import|;
end_import

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

begin_class
DECL|class|LatexToUnicodeFormatterTest
class|class
name|LatexToUnicodeFormatterTest
block|{
DECL|field|formatter
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
DECL|method|preserveUnknownCommand ()
name|void
name|preserveUnknownCommand
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\mbox{-}"
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
name|void
name|testFormatTextit
parameter_list|()
block|{
comment|// See #1464
name|assertEquals
argument_list|(
literal|"\uD835\uDC61\uD835\uDC52\uD835\uDC65\uD835\uDC61"
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
name|Disabled
argument_list|(
literal|"This is not a standard LaTeX command. It is debatable why we should convert this."
argument_list|)
annotation|@
name|Test
DECL|method|testCombiningAccentsCase2 ()
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
DECL|method|keepUnknownCommandWithoutArgument ()
name|void
name|keepUnknownCommandWithoutArgument
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\aaaa"
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
DECL|method|keepUnknownCommandWithArgument ()
name|void
name|keepUnknownCommandWithArgument
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\aaaa{bbbb}"
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
DECL|method|keepUnknownCommandWithEmptyArgument ()
name|void
name|keepUnknownCommandWithEmptyArgument
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\aaaa{}"
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
annotation|@
name|Test
DECL|method|testPreservationOfSingleUnderscore ()
name|void
name|testPreservationOfSingleUnderscore
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Lorem ipsum_lorem ipsum"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Lorem ipsum_lorem ipsum"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testConversionOfUnderscoreWithBraces ()
name|void
name|testConversionOfUnderscoreWithBraces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Lorem ipsum_(lorem ipsum)"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Lorem ipsum_{lorem ipsum}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testConversionOfOrdinal1st ()
name|void
name|testConversionOfOrdinal1st
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"1Ë¢áµ"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"1\\textsuperscript{st}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testConversionOfOrdinal2nd ()
name|void
name|testConversionOfOrdinal2nd
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"2â¿áµ"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"2\\textsuperscript{nd}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testConversionOfOrdinal3rd ()
name|void
name|testConversionOfOrdinal3rd
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"3Ê³áµ"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"3\\textsuperscript{rd}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testConversionOfOrdinal4th ()
name|void
name|testConversionOfOrdinal4th
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"4áµÊ°"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"4\\textsuperscript{th}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testConversionOfOrdinal9th ()
name|void
name|testConversionOfOrdinal9th
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"9áµÊ°"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"9\\textsuperscript{th}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

