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
DECL|class|AuthorOrgSciTest
specifier|public
class|class
name|AuthorOrgSciTest
block|{
annotation|@
name|Test
DECL|method|testOrgSci ()
specifier|public
name|void
name|testOrgSci
parameter_list|()
block|{
name|LayoutFormatter
name|f
init|=
operator|new
name|AuthorOrgSci
argument_list|()
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Flynn, J., S. Gartska"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"John Flynn and Sabine Gartska"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Garvin, D. A."
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"David A. Garvin"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Makridakis, S., S. C. Wheelwright, V. E. McGee"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"Sa Makridakis and Sa Ca Wheelwright and Va Ea McGee"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testOrgSciPlusAbbreviation ()
specifier|public
name|void
name|testOrgSciPlusAbbreviation
parameter_list|()
block|{
name|LayoutFormatter
name|f
init|=
operator|new
name|CompositeFormat
argument_list|(
operator|new
name|AuthorOrgSci
argument_list|()
argument_list|,
operator|new
name|NoSpaceBetweenAbbreviations
argument_list|()
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"Flynn, J., S. Gartska"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"John Flynn and Sabine Gartska"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Garvin, D.A."
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"David A. Garvin"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"Makridakis, S., S.C. Wheelwright, V.E. McGee"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"Sa Makridakis and Sa Ca Wheelwright and Va Ea McGee"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

