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
name|After
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
name|*
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
name|BeforeEach
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

begin_class
DECL|class|RTFCharsTest
specifier|public
class|class
name|RTFCharsTest
block|{
DECL|field|formatter
specifier|private
name|LayoutFormatter
name|formatter
decl_stmt|;
annotation|@
name|BeforeEach
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|RTFChars
argument_list|()
expr_stmt|;
block|}
annotation|@
name|After
DECL|method|tearDown ()
specifier|public
name|void
name|tearDown
parameter_list|()
block|{
name|formatter
operator|=
literal|null
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testBasicFormat ()
specifier|public
name|void
name|testBasicFormat
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
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"hallo"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"hallo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"R\\u233eflexions sur le timing de la quantit\\u233e"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"RÃ©flexions sur le timing de la quantitÃ©"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"h\\'e1llo"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"h\\'allo"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"h\\'e1llo"
argument_list|,
name|formatter
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
name|assertEquals
argument_list|(
literal|"{\\i hallo}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\emph{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\i hallo}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\emph hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"An article title with {\\i a book title} emphasized"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"An article title with \\emph{a book title} emphasized"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\i hallo}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\textit{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\i hallo}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\textit hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\b hallo}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\textbf{hallo}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\b hallo}"
argument_list|,
name|formatter
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
DECL|method|testComplicated ()
specifier|public
name|void
name|testComplicated
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"R\\u233eflexions sur le timing de la quantit\\u233e {\\u230ae} should be \\u230ae"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"RÃ©flexions sur le timing de la quantitÃ© {\\ae} should be Ã¦"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testComplicated2 ()
specifier|public
name|void
name|testComplicated2
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"h\\'e1ll{\\u339oe}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"h\\'all{\\oe}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testComplicated3 ()
specifier|public
name|void
name|testComplicated3
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Le c\\u339oeur d\\u233e\\u231cu mais l'\\u226ame plut\\u244ot na\\u239ive, Lou\\u255ys r"
operator|+
literal|"\\u234eva de crapa\\u252?ter en cano\\u235e au del\\u224a des \\u238iles, pr\\u232es du m\\u228alstr"
operator|+
literal|"\\u246om o\\u249u br\\u251ulent les nov\\u230ae."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"Le cÅur dÃ©Ã§u mais l'Ã¢me plutÃ´t "
operator|+
literal|"naÃ¯ve, LouÃ¿s rÃªva de crapaÃ¼ter en canoÃ« au delÃ  des Ã®les, prÃ¨s du mÃ¤lstrÃ¶m oÃ¹ brÃ»lent les novÃ¦."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testComplicated4 ()
specifier|public
name|void
name|testComplicated4
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"l'\\u238ile exigu\\u235e\n"
operator|+
literal|"  O\\u249u l'ob\\u232ese jury m\\u251ur\n"
operator|+
literal|"  F\\u234ete l'ha\\u239i volap\\u252?k,\n"
operator|+
literal|"  \\u194Ane ex a\\u233equo au whist,\n"
operator|+
literal|"  \\u212Otez ce v\\u339oeu d\\u233e\\u231cu."
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"l'Ã®le exiguÃ«\n"
operator|+
literal|"  OÃ¹ l'obÃ¨se jury mÃ»r\n"
operator|+
literal|"  FÃªte l'haÃ¯ volapÃ¼k,\n"
operator|+
literal|"  Ãne ex aÃ©quo au whist,\n"
operator|+
literal|"  Ãtez ce vÅu dÃ©Ã§u."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testComplicated5 ()
specifier|public
name|void
name|testComplicated5
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\u193Arv\\u237izt\\u369?r\\u337? t\\u252?k\\u246orf\\u250ur\\u243og\\u233ep"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"ÃrvÃ­ztÅ±rÅ tÃ¼kÃ¶rfÃºrÃ³gÃ©p"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testComplicated6 ()
specifier|public
name|void
name|testComplicated6
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Pchn\\u261a\\u263c w t\\u281e \\u322l\\u243od\\u378z je\\u380za lub o\\u347sm skrzy\\u324n fig"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"PchnÄÄ w tÄ ÅÃ³dÅº jeÅ¼a lub oÅm skrzyÅ fig"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSpecialCharacters ()
specifier|public
name|void
name|testSpecialCharacters
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\'f3"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã³
name|assertEquals
argument_list|(
literal|"\\'f2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã²
name|assertEquals
argument_list|(
literal|"\\'f4"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã´
name|assertEquals
argument_list|(
literal|"\\'f6"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã¶
name|assertEquals
argument_list|(
literal|"\\u245o"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\~{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ãµ
name|assertEquals
argument_list|(
literal|"\\u333o"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\={o}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\u335o"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\uo}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\u231c"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\cc}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã§
name|assertEquals
argument_list|(
literal|"{\\u339oe}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\oe}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\u338OE}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\OE}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\u230ae}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\ae}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã¦
name|assertEquals
argument_list|(
literal|"{\\u198AE}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\AE}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\.{o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// ???
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\vo"
argument_list|)
argument_list|)
expr_stmt|;
comment|// ???
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\Ha"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã£ // ???
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\too"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\do"
argument_list|)
argument_list|)
expr_stmt|;
comment|// ???
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\bo"
argument_list|)
argument_list|)
expr_stmt|;
comment|// ???
name|assertEquals
argument_list|(
literal|"\\u229a"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\aa}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã¥
name|assertEquals
argument_list|(
literal|"\\u197A"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\AA}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã
name|assertEquals
argument_list|(
literal|"\\u248o"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\o}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã¸
name|assertEquals
argument_list|(
literal|"\\u216O"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\O}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã
name|assertEquals
argument_list|(
literal|"\\u322l"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\l}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\u321L"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\L}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\u223ss"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\ss}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Ã
name|assertEquals
argument_list|(
literal|"\\u191?"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`?"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â¿
name|assertEquals
argument_list|(
literal|"\\u161!"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`!"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â¡
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\dag"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\ddag"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\u167S"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\S}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â§
name|assertEquals
argument_list|(
literal|"\\u182P"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\P}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â¶
name|assertEquals
argument_list|(
literal|"\\u169?"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\copyright}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â©
name|assertEquals
argument_list|(
literal|"\\u163?"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{\\pounds}"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Â£
block|}
annotation|@
name|Test
DECL|method|testRTFCharacters ()
specifier|public
name|void
name|testRTFCharacters
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\'e0"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`{a}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'e8"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`{e}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'ec"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`{i}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'f2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`{o}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'f9"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`{u}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'e1"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'a"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'e9"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'e"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'ed"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'i"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'f3"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'o"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'fa"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'u"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'e2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^a"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'ea"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^e"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'ee"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^i"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'f4"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^o"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'fa"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^u"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'e4"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"a"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'eb"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"e"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'ef"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"i"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'f6"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"o"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\u252u"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"u"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'f1"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\~n"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testRTFCharactersCapital ()
specifier|public
name|void
name|testRTFCharactersCapital
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"\\'c0"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`A"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'c8"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`E"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'cc"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`I"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'d2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`O"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'d9"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\`U"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'c1"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'A"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'c9"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'E"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'cd"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'I"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'d3"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'O"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'da"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\'U"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'c2"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^A"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'ca"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^E"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'ce"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^I"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'d4"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^O"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'db"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\^U"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'c4"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"A"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'cb"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"E"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'cf"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"I"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'d6"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"O"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\'dc"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\\"U"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

