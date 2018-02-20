begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
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
DECL|class|AuthorTest
specifier|public
class|class
name|AuthorTest
block|{
annotation|@
name|Test
DECL|method|addDotIfAbbreviationAddDot ()
specifier|public
name|void
name|addDotIfAbbreviationAddDot
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"O"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"AO"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"AO."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A.O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A.-O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A-O"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|addDotIfAbbreviationDoNotAddDot ()
specifier|public
name|void
name|addDotIfAbbreviationDoNotAddDot
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A. O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A.-O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A.-O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"O. Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"O. Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A. O. Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A. O. Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"O. von Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"O. von Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"A.-O. Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"A.-O. Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, O., Jr."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, O., Jr."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, A. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, A. O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, A.-O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, A.-O."
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"MEmre"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"MEmre"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"{\\'{E}}douard"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"{\\'{E}}douard"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"J{\\\"o}rg"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"J{\\\"o}rg"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, O. and O. Moore"
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, O. and O. Moore"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Moore, O. and O. Moore and Moore, O. O."
argument_list|,
name|Author
operator|.
name|addDotIfAbbreviation
argument_list|(
literal|"Moore, O. and O. Moore and Moore, O. O."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

