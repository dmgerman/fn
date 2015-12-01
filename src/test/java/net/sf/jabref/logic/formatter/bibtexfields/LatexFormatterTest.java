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
import|import static
name|org
operator|.
name|junit
operator|.
name|Assert
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
name|BibtexEntry
import|;
end_import

begin_class
DECL|class|LatexFormatterTest
specifier|public
class|class
name|LatexFormatterTest
block|{
DECL|field|entry
specifier|private
name|BibtexEntry
name|entry
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|entry
operator|=
operator|new
name|BibtexEntry
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
name|LatexFormatter
name|lf
init|=
operator|new
name|LatexFormatter
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"$\\alpha\\beta$"
argument_list|,
name|lf
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
name|lf
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
name|lf
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
name|lf
operator|.
name|format
argument_list|(
literal|"A ${\\Delta}$${\\Sigma}$ modulator for {FPGA} {DSP}"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

