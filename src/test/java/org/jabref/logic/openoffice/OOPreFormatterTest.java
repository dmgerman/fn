begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.openoffice
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|openoffice
package|;
end_package

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
DECL|class|OOPreFormatterTest
specifier|public
class|class
name|OOPreFormatterTest
block|{
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
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"aaa"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"$"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\$"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"%"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\%"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\\\"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormatAccents ()
specifier|public
name|void
name|testFormatAccents
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ã¤"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
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
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"{\\\"{A}}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ã"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"{\\c{C}}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSpecialCommands ()
specifier|public
name|void
name|testSpecialCommands
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"Ã¥"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"{\\aa}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"bb"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"{\\bb}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ã¥ a"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\aa a"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ã¥ a"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"{\\aa a}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Ã¥Ã"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\aa\\AA"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"bb a"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\bb a"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUnsupportedSpecialCommands ()
specifier|public
name|void
name|testUnsupportedSpecialCommands
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"ftmch"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\ftmch"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"ftmch"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"{\\ftmch}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"ftmchaaa"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"{\\ftmch\\aaa}"
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
name|assertEquals
argument_list|(
literal|"Î£"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"$\\Sigma$"
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
operator|new
name|OOPreFormatter
argument_list|()
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
DECL|method|testFormatting ()
specifier|public
name|void
name|testFormatting
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"<i>kkk</i>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\textit{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<i>kkk</i>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"{\\it kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<i>kkk</i>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\emph{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<b>kkk</b>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\textbf{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<smallcaps>kkk</smallcaps>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\textsc{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<s>kkk</s>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\sout{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<u>kkk</u>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\underline{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<tt>kkk</tt>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\texttt{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<sup>kkk</sup>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\textsuperscript{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"<sub>kkk</sub>"
argument_list|,
operator|new
name|OOPreFormatter
argument_list|()
operator|.
name|format
argument_list|(
literal|"\\textsubscript{kkk}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
