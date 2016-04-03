begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
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
name|formatter
operator|.
name|bibtexfields
operator|.
name|OrdinalsToSuperscriptFormatter
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
name|Before
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
DECL|class|OrdinalsToSuperscriptFormatterTest
specifier|public
class|class
name|OrdinalsToSuperscriptFormatterTest
block|{
DECL|field|formatter
specifier|private
name|OrdinalsToSuperscriptFormatter
name|formatter
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|formatter
operator|=
operator|new
name|OrdinalsToSuperscriptFormatter
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
DECL|method|returnsFormatterName ()
specifier|public
name|void
name|returnsFormatterName
parameter_list|()
block|{
name|Assert
operator|.
name|assertNotNull
argument_list|(
name|formatter
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertNotEquals
argument_list|(
literal|""
argument_list|,
name|formatter
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|replacesSuperscript ()
specifier|public
name|void
name|replacesSuperscript
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|"1st"
argument_list|,
literal|"1\\textsuperscript{st}"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"2nd"
argument_list|,
literal|"2\\textsuperscript{nd}"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"3rd"
argument_list|,
literal|"3\\textsuperscript{rd}"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"4th"
argument_list|,
literal|"4\\textsuperscript{th}"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"21th"
argument_list|,
literal|"21\\textsuperscript{th}"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|replaceSuperscriptsEmptyFields ()
specifier|public
name|void
name|replaceSuperscriptsEmptyFields
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|replaceSuperscriptsIgnoresCase ()
specifier|public
name|void
name|replaceSuperscriptsIgnoresCase
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|"1st"
argument_list|,
literal|"1\\textsuperscript{st}"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"1ST"
argument_list|,
literal|"1\\textsuperscript{ST}"
argument_list|)
expr_stmt|;
name|expectCorrect
argument_list|(
literal|"1sT"
argument_list|,
literal|"1\\textsuperscript{sT}"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|replaceSuperscriptsInMultilineStrings ()
specifier|public
name|void
name|replaceSuperscriptsInMultilineStrings
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|"replace on 1st line\nand on 2nd line."
argument_list|,
literal|"replace on 1\\textsuperscript{st} line\nand on 2\\textsuperscript{nd} line."
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|replaceAllSuperscripts ()
specifier|public
name|void
name|replaceAllSuperscripts
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|"1st 2nd 3rd 4th"
argument_list|,
literal|"1\\textsuperscript{st} 2\\textsuperscript{nd} 3\\textsuperscript{rd} 4\\textsuperscript{th}"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ignoreSuperscriptsInsideWords ()
specifier|public
name|void
name|ignoreSuperscriptsInsideWords
parameter_list|()
block|{
name|expectCorrect
argument_list|(
literal|"1st 1stword words1st inside1stwords"
argument_list|,
literal|"1\\textsuperscript{st} 1stword words1st inside1stwords"
argument_list|)
expr_stmt|;
block|}
DECL|method|expectCorrect (String input, String expected)
specifier|private
name|void
name|expectCorrect
parameter_list|(
name|String
name|input
parameter_list|,
name|String
name|expected
parameter_list|)
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
name|input
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
