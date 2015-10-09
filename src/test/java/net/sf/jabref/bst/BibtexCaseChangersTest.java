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
name|BibtexCaseChanger
operator|.
name|FORMAT_MODE
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
name|Assert
operator|.
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
name|Assert
operator|.
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
name|Assert
operator|.
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

