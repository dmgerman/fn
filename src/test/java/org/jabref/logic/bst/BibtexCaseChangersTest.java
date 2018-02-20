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
name|jabref
operator|.
name|logic
operator|.
name|bst
operator|.
name|BibtexCaseChanger
operator|.
name|FORMAT_MODE
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
DECL|class|BibtexCaseChangersTest
specifier|public
class|class
name|BibtexCaseChangersTest
block|{
annotation|@
name|Test
DECL|method|testChangeCase ()
specifier|public
name|void
name|testChangeCase
parameter_list|()
block|{
name|assertCaseChangerTitleLowers
argument_list|(
literal|"i"
argument_list|,
literal|"i"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"i"
argument_list|,
literal|"i"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"I"
argument_list|,
literal|"i"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"0i~ "
argument_list|,
literal|"0I~ "
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"0i~ "
argument_list|,
literal|"0I~ "
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"0I~ "
argument_list|,
literal|"0I~ "
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hi hi "
argument_list|,
literal|"Hi Hi "
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"hi hi "
argument_list|,
literal|"Hi Hi "
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"HI HI "
argument_list|,
literal|"Hi Hi "
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"{\\oe}"
argument_list|,
literal|"{\\oe}"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"{\\oe}"
argument_list|,
literal|"{\\oe}"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"{\\OE}"
argument_list|,
literal|"{\\oe}"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hi {\\oe   }hi "
argument_list|,
literal|"Hi {\\oe   }Hi "
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"hi {\\oe   }hi "
argument_list|,
literal|"Hi {\\oe   }Hi "
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"HI {\\OE   }HI "
argument_list|,
literal|"Hi {\\oe   }Hi "
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Jonathan meyer and charles louis xavier joseph de la vall{\\'e}e poussin"
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"jonathan meyer and charles louis xavier joseph de la vall{\\'e}e poussin"
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"JONATHAN MEYER AND CHARLES LOUIS XAVIER JOSEPH DE LA VALL{\\'E}E POUSSIN"
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"{\\'e}"
argument_list|,
literal|"{\\'e}"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"{\\'e}"
argument_list|,
literal|"{\\'e}"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"{\\'E}"
argument_list|,
literal|"{\\'e}"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"{\\'{E}}douard masterly"
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"{\\'{e}}douard masterly"
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"{\\'{E}}DOUARD MASTERLY"
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Ulrich {\\\"{u}}nderwood and ned {\\~n}et and paul {\\={p}}ot"
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"ulrich {\\\"{u}}nderwood and ned {\\~n}et and paul {\\={p}}ot"
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"ULRICH {\\\"{U}}NDERWOOD AND NED {\\~N}ET AND PAUL {\\={P}}OT"
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"An {$O(n \\log n / \\! \\log\\log n)$} sorting algorithm"
argument_list|,
literal|"An {$O(n \\log n / \\! \\log\\log n)$} Sorting Algorithm"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"an {$O(n \\log n / \\! \\log\\log n)$} sorting algorithm"
argument_list|,
literal|"An {$O(n \\log n / \\! \\log\\log n)$} Sorting Algorithm"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"AN {$O(n \\log n / \\! \\log\\log n)$} SORTING ALGORITHM"
argument_list|,
literal|"An {$O(n \\log n / \\! \\log\\log n)$} Sorting Algorithm"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"hallo"
argument_list|,
literal|"hallo"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo"
argument_list|,
literal|"HAllo"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world"
argument_list|,
literal|"HAllo World"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world. how"
argument_list|,
literal|"HAllo WORLD. HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo {WORLD}. how"
argument_list|,
literal|"HAllo {WORLD}. HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo {\\world}. how"
argument_list|,
literal|"HAllo {\\WORLD}. HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"hallo"
argument_list|,
literal|"hallo"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"hallo"
argument_list|,
literal|"HAllo"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"hallo world"
argument_list|,
literal|"HAllo World"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"hallo world. how"
argument_list|,
literal|"HAllo WORLD. HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"hallo {worLD}. how"
argument_list|,
literal|"HAllo {worLD}. HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"hallo {\\world}. how"
argument_list|,
literal|"HAllo {\\WORLD}. HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"HALLO"
argument_list|,
literal|"hallo"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"HALLO"
argument_list|,
literal|"HAllo"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"HALLO WORLD"
argument_list|,
literal|"HAllo World"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"HALLO WORLD. HOW"
argument_list|,
literal|"HAllo World. How"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"HALLO {worLD}. HOW"
argument_list|,
literal|"HAllo {worLD}. how"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllUppers
argument_list|(
literal|"HALLO {\\WORLD}. HOW"
argument_list|,
literal|"HAllo {\\woRld}. hoW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"On notions of information transfer in {VLSI} circuits"
argument_list|,
literal|"On Notions of Information Transfer in {VLSI} Circuits"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testColon ()
specifier|public
name|void
name|testColon
parameter_list|()
block|{
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world: How"
argument_list|,
literal|"HAllo WORLD: HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world! how"
argument_list|,
literal|"HAllo WORLD! HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world? how"
argument_list|,
literal|"HAllo WORLD? HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world. how"
argument_list|,
literal|"HAllo WORLD. HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world, how"
argument_list|,
literal|"HAllo WORLD, HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world; how"
argument_list|,
literal|"HAllo WORLD; HOW"
argument_list|)
expr_stmt|;
name|assertCaseChangerTitleLowers
argument_list|(
literal|"Hallo world- how"
argument_list|,
literal|"HAllo WORLD- HOW"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSpecialBracketPlacement ()
specifier|public
name|void
name|testSpecialBracketPlacement
parameter_list|()
block|{
comment|// area between brackets spanning multiple words
name|assertCaseChangerAllLowers
argument_list|(
literal|"this i{S REALLY CraZy ST}uff"
argument_list|,
literal|"tHIS I{S REALLY CraZy ST}UfF"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"this i{S R{\\'E}ALLY CraZy ST}uff"
argument_list|,
literal|"tHIS I{S R{\\'E}ALLY CraZy ST}UfF"
argument_list|)
expr_stmt|;
comment|// real use case: Formulas
name|assertCaseChangerAllUppers
argument_list|(
literal|"AN {$O(n \\log n)$} SORTING ALGORITHM"
argument_list|,
literal|"An {$O(n \\log n)$} Sorting Algorithm"
argument_list|)
expr_stmt|;
comment|// only one special character, no strange bracket placement
name|assertCaseChangerAllLowers
argument_list|(
literal|"this is r{\\'e}ally crazy stuff"
argument_list|,
literal|"tHIS IS R{\\'E}ALLY CraZy STUfF"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTitleCase ()
specifier|public
name|void
name|testTitleCase
parameter_list|()
block|{
comment|// CaseChangers.TITLE is good at keeping some words lower case
comment|// Here some modified test cases to show that escaping with BibtexCaseChanger also works
comment|// Examples taken from https://github.com/JabRef/jabref/pull/176#issuecomment-142723792
name|assertCaseChangerAllLowers
argument_list|(
literal|"this is a simple example {TITLE}"
argument_list|,
literal|"This is a simple example {TITLE}"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"this {IS} another simple example tit{LE}"
argument_list|,
literal|"This {IS} another simple example tit{LE}"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"{What ABOUT thIS} one?"
argument_list|,
literal|"{What ABOUT thIS} one?"
argument_list|)
expr_stmt|;
name|assertCaseChangerAllLowers
argument_list|(
literal|"{And {thIS} might {a{lso}} be possible}"
argument_list|,
literal|"{And {thIS} might {a{lso}} be possible}"
argument_list|)
expr_stmt|;
comment|/* the real test would look like as follows. Also from the comment of issue 176, order reversed as the "should be" comes first */
comment|// assertCaseChangerTitleUppers("This is a Simple Example {TITLE}", "This is a simple example {TITLE}");
comment|// assertCaseChangerTitleUppers("This {IS} Another Simple Example Tit{LE}", "This {IS} another simple example tit{LE}");
comment|// assertCaseChangerTitleUppers("{What ABOUT thIS} one?", "{What ABOUT thIS} one?");
comment|// assertCaseChangerTitleUppers("{And {thIS} might {a{lso}} be possible}", "{And {thIS} might {a{lso}} be possible}");
block|}
DECL|method|assertCaseChangerTitleLowers (final String string, final String string2)
specifier|private
name|void
name|assertCaseChangerTitleLowers
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
name|BibtexCaseChanger
operator|.
name|changeCase
argument_list|(
name|string2
argument_list|,
name|FORMAT_MODE
operator|.
name|TITLE_LOWERS
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|assertCaseChangerAllLowers (final String string, final String string2)
specifier|private
name|void
name|assertCaseChangerAllLowers
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
name|BibtexCaseChanger
operator|.
name|changeCase
argument_list|(
name|string2
argument_list|,
name|FORMAT_MODE
operator|.
name|ALL_LOWERS
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|assertCaseChangerAllUppers (final String string, final String string2)
specifier|private
name|void
name|assertCaseChangerAllUppers
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
name|BibtexCaseChanger
operator|.
name|changeCase
argument_list|(
name|string2
argument_list|,
name|FORMAT_MODE
operator|.
name|ALL_UPPERS
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

