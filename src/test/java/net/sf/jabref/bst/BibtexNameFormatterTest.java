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
name|model
operator|.
name|entry
operator|.
name|AuthorList
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
DECL|class|BibtexNameFormatterTest
specifier|public
class|class
name|BibtexNameFormatterTest
block|{
annotation|@
name|Test
DECL|method|testFormatName ()
specifier|public
name|void
name|testFormatName
parameter_list|()
block|{
block|{
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthors
argument_list|(
literal|"Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"de~laVall{\\'e}e~PoussinCharles Louis Xavier~Joseph"
argument_list|,
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|al
operator|.
name|getAuthor
argument_list|(
literal|0
argument_list|)
argument_list|,
literal|"{vv}{ll}{jj}{ff}"
argument_list|,
name|Assert
operator|::
name|fail
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthors
argument_list|(
literal|"Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"de~la Vall{\\'e}e~Poussin, C.~L. X.~J."
argument_list|,
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|al
operator|.
name|getAuthor
argument_list|(
literal|0
argument_list|)
argument_list|,
literal|"{vv~}{ll}{, jj}{, f.}"
argument_list|,
name|Assert
operator|::
name|fail
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|{
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthors
argument_list|(
literal|"Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"de~la Vall{\\'e}e~Poussin, C.~L. X.~J?"
argument_list|,
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|al
operator|.
name|getAuthor
argument_list|(
literal|0
argument_list|)
argument_list|,
literal|"{vv~}{ll}{, jj}{, f}?"
argument_list|,
name|Assert
operator|::
name|fail
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|AuthorList
name|al
init|=
name|AuthorList
operator|.
name|getAuthors
argument_list|(
literal|"Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"dlVP"
argument_list|,
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|al
operator|.
name|getAuthor
argument_list|(
literal|0
argument_list|)
argument_list|,
literal|"{v{}}{l{}}"
argument_list|,
name|Assert
operator|::
name|fail
argument_list|)
argument_list|)
expr_stmt|;
name|assertNameFormatA
argument_list|(
literal|"Meyer, J?"
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
name|assertNameFormatB
argument_list|(
literal|"J.~Meyer"
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
name|assertNameFormatC
argument_list|(
literal|"Jonathan Meyer"
argument_list|,
literal|"Jonathan Meyer and Charles Louis Xavier Joseph de la Vall{\\'e}e Poussin"
argument_list|)
expr_stmt|;
name|assertNameFormatA
argument_list|(
literal|"Masterly, {\\'{E}}?"
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertNameFormatB
argument_list|(
literal|"{\\'{E}}.~Masterly"
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertNameFormatC
argument_list|(
literal|"{\\'{E}}douard Masterly"
argument_list|,
literal|"{\\'{E}}douard Masterly"
argument_list|)
expr_stmt|;
name|assertNameFormatA
argument_list|(
literal|"{\\\"{U}}nderwood, U?"
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
name|assertNameFormatB
argument_list|(
literal|"U.~{\\\"{U}}nderwood"
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
name|assertNameFormatC
argument_list|(
literal|"Ulrich {\\\"{U}}nderwood"
argument_list|,
literal|"Ulrich {\\\"{U}}nderwood and Ned {\\~N}et and Paul {\\={P}}ot"
argument_list|)
expr_stmt|;
name|assertNameFormatA
argument_list|(
literal|"Victor, P.~{\\'E}?"
argument_list|,
literal|"Paul {\\'E}mile Victor and and de la Cierva y Codorn{\\â\\i}u, Juan"
argument_list|)
expr_stmt|;
name|assertNameFormatB
argument_list|(
literal|"P.~{\\'E}. Victor"
argument_list|,
literal|"Paul {\\'E}mile Victor and and de la Cierva y Codorn{\\â\\i}u, Juan"
argument_list|)
expr_stmt|;
name|assertNameFormatC
argument_list|(
literal|"Paul~{\\'E}mile Victor"
argument_list|,
literal|"Paul {\\'E}mile Victor and and de la Cierva y Codorn{\\â\\i}u, Juan"
argument_list|)
expr_stmt|;
block|}
DECL|method|assertNameFormat (String string, String string2, int which, String format)
specifier|private
name|void
name|assertNameFormat
parameter_list|(
name|String
name|string
parameter_list|,
name|String
name|string2
parameter_list|,
name|int
name|which
parameter_list|,
name|String
name|format
parameter_list|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|string
argument_list|,
name|BibtexNameFormatter
operator|.
name|formatName
argument_list|(
name|string2
argument_list|,
name|which
argument_list|,
name|format
argument_list|,
name|Assert
operator|::
name|fail
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|assertNameFormatC (String string, String string2)
specifier|private
name|void
name|assertNameFormatC
parameter_list|(
name|String
name|string
parameter_list|,
name|String
name|string2
parameter_list|)
block|{
name|assertNameFormat
argument_list|(
name|string
argument_list|,
name|string2
argument_list|,
literal|1
argument_list|,
literal|"{ff }{vv }{ll}{ jj}"
argument_list|)
expr_stmt|;
block|}
DECL|method|assertNameFormatB (String string, String string2)
specifier|private
name|void
name|assertNameFormatB
parameter_list|(
name|String
name|string
parameter_list|,
name|String
name|string2
parameter_list|)
block|{
name|assertNameFormat
argument_list|(
name|string
argument_list|,
name|string2
argument_list|,
literal|1
argument_list|,
literal|"{f.~}{vv~}{ll}{, jj}"
argument_list|)
expr_stmt|;
block|}
DECL|method|assertNameFormatA (String string, String string2)
specifier|private
name|void
name|assertNameFormatA
parameter_list|(
name|String
name|string
parameter_list|,
name|String
name|string2
parameter_list|)
block|{
name|assertNameFormat
argument_list|(
name|string
argument_list|,
name|string2
argument_list|,
literal|1
argument_list|,
literal|"{vv~}{ll}{, jj}{, f}?"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testConsumeToMatchingBrace ()
specifier|public
name|void
name|testConsumeToMatchingBrace
parameter_list|()
block|{
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|6
argument_list|,
name|BibtexNameFormatter
operator|.
name|consumeToMatchingBrace
argument_list|(
name|sb
argument_list|,
literal|"{HELLO} {WORLD}"
operator|.
name|toCharArray
argument_list|()
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{HELLO}"
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|18
argument_list|,
name|BibtexNameFormatter
operator|.
name|consumeToMatchingBrace
argument_list|(
name|sb
argument_list|,
literal|"{HE{L{}L}O} {WORLD}"
operator|.
name|toCharArray
argument_list|()
argument_list|,
literal|12
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{WORLD}"
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|10
argument_list|,
name|BibtexNameFormatter
operator|.
name|consumeToMatchingBrace
argument_list|(
name|sb
argument_list|,
literal|"{HE{L{}L}O} {WORLD}"
operator|.
name|toCharArray
argument_list|()
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{HE{L{}L}O}"
argument_list|,
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testGetFirstCharOfString ()
specifier|public
name|void
name|testGetFirstCharOfString
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"C"
argument_list|,
name|BibtexNameFormatter
operator|.
name|getFirstCharOfString
argument_list|(
literal|"Charles"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"V"
argument_list|,
name|BibtexNameFormatter
operator|.
name|getFirstCharOfString
argument_list|(
literal|"Vall{\\'e}e"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{\\'e}"
argument_list|,
name|BibtexNameFormatter
operator|.
name|getFirstCharOfString
argument_list|(
literal|"{\\'e}"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"{\\'e"
argument_list|,
name|BibtexNameFormatter
operator|.
name|getFirstCharOfString
argument_list|(
literal|"{\\'e"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"E"
argument_list|,
name|BibtexNameFormatter
operator|.
name|getFirstCharOfString
argument_list|(
literal|"{E"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNumberOfChars ()
specifier|public
name|void
name|testNumberOfChars
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|6
argument_list|,
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
literal|"Vall{\\'e}e"
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|2
argument_list|,
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
literal|"Vall{\\'e}e"
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
literal|"Vall{\\'e}e"
argument_list|,
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|6
argument_list|,
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
literal|"Vall{\\'e}e"
argument_list|,
literal|6
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|6
argument_list|,
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
literal|"Vall{\\'e}e"
argument_list|,
literal|7
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|8
argument_list|,
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
literal|"Vall{e}e"
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|6
argument_list|,
name|BibtexNameFormatter
operator|.
name|numberOfChars
argument_list|(
literal|"Vall{\\'e this will be skipped}e"
argument_list|,
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

