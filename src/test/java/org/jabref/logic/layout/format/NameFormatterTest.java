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
name|Test
import|;
end_import

begin_class
DECL|class|NameFormatterTest
specifier|public
class|class
name|NameFormatterTest
block|{
annotation|@
name|Test
DECL|method|testFormatStringStringBibtexEntry ()
specifier|public
name|void
name|testFormatStringStringBibtexEntry
parameter_list|()
block|{
name|NameFormatter
name|l
init|=
operator|new
name|NameFormatter
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Doe"
argument_list|,
name|l
operator|.
name|format
argument_list|(
literal|"Joe Doe"
argument_list|,
literal|"1@*@{ll}"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"moremoremoremore"
argument_list|,
name|l
operator|.
name|format
argument_list|(
literal|"Joe Doe and Mary Jane and Bruce Bar and Arthur Kay"
argument_list|,
literal|"1@*@{ll}@@2@1..1@{ff}{ll}@2..2@ and {ff}{last}@@*@*@more"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Doe"
argument_list|,
name|l
operator|.
name|format
argument_list|(
literal|"Joe Doe"
argument_list|,
literal|"1@*@{ll}@@2@1..1@{ff}{ll}@2..2@ and {ff}{last}@@*@*@more"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"JoeDoe and MaryJ"
argument_list|,
name|l
operator|.
name|format
argument_list|(
literal|"Joe Doe and Mary Jane"
argument_list|,
literal|"1@*@{ll}@@2@1..1@{ff}{ll}@2..2@ and {ff}{l}@@*@*@more"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Doe, Joe and Jane, M. and Kamp, J.~A."
argument_list|,
name|l
operator|.
name|format
argument_list|(
literal|"Joe Doe and Mary Jane and John Arthur van Kamp"
argument_list|,
literal|"1@*@{ll}, {ff}@@*@1@{ll}, {ff}@2..-1@ and {ll}, {f}."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Doe Joe and Jane, M. and Kamp, J.~A."
argument_list|,
name|l
operator|.
name|format
argument_list|(
literal|"Joe Doe and Mary Jane and John Arthur van Kamp"
argument_list|,
literal|"1@*@{ll}, {ff}@@*@1@{ll} {ff}@2..-1@ and {ll}, {f}."
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testFormat ()
specifier|public
name|void
name|testFormat
parameter_list|()
block|{
name|NameFormatter
name|a
init|=
operator|new
name|NameFormatter
argument_list|()
decl_stmt|;
comment|// Empty case
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|String
name|formatString
init|=
literal|"1@1@{vv }{ll}{ ff}@@2@1@{vv }{ll}{ ff}@2@ and {vv }{ll}{, ff}@@*@1@{vv }{ll}{ ff}@2..-2@, {vv }{ll}{, ff}@-1@ and {vv }{ll}{, ff}"
decl_stmt|;
comment|// Single Names
name|assertEquals
argument_list|(
literal|"Vandekamp Mary~Ann"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"Mary Ann Vandekamp"
argument_list|,
name|formatString
argument_list|)
argument_list|)
expr_stmt|;
comment|// Two names
name|assertEquals
argument_list|(
literal|"von Neumann John and Black~Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and Black Brown, Peter"
argument_list|,
name|formatString
argument_list|)
argument_list|)
expr_stmt|;
comment|// Three names
name|assertEquals
argument_list|(
literal|"von Neumann John, Smith, John and Black~Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"von Neumann, John and Smith, John and Black Brown, Peter"
argument_list|,
name|formatString
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"von Neumann John, Smith, John and Black~Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"John von Neumann and John Smith and Black Brown, Peter"
argument_list|,
name|formatString
argument_list|)
argument_list|)
expr_stmt|;
comment|// Four names
name|assertEquals
argument_list|(
literal|"von Neumann John, Smith, John, Vandekamp, Mary~Ann and Black~Brown, Peter"
argument_list|,
name|a
operator|.
name|format
argument_list|(
literal|"von Neumann, John and Smith, John and Vandekamp, Mary Ann and Black Brown, Peter"
argument_list|,
name|formatString
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

