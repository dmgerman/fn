begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter.bibtexfields
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
operator|.
name|bibtexfields
package|;
end_package

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

begin_comment
comment|/**  * Tests in addition to the general tests from {@link net.sf.jabref.logic.formatter.FormatterTest}  */
end_comment

begin_class
DECL|class|LatexCleanupFormatterTest
specifier|public
class|class
name|LatexCleanupFormatterTest
block|{
DECL|field|formatter
specifier|private
name|LatexCleanupFormatter
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
name|LatexCleanupFormatter
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|test ()
specifier|public
name|void
name|test
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"$\\alpha\\beta$"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"$\\alpha$$\\beta$"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{VLSI DSP}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"{VLSI} {DSP}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"\\textbf{VLSI} {DSP}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"\\textbf{VLSI} {DSP}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A ${\\Delta\\Sigma}$ modulator for {FPGA DSP}"
argument_list|,
name|formatter
operator|.
name|format
argument_list|(
literal|"A ${\\Delta}$${\\Sigma}$ modulator for {FPGA} {DSP}"
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
literal|"{VLSI DSP}"
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
block|}
end_class

end_unit

