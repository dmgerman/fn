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
DECL|class|CompositeFormatTest
specifier|public
class|class
name|CompositeFormatTest
block|{
annotation|@
name|Test
DECL|method|testEmptyComposite ()
specifier|public
name|void
name|testEmptyComposite
parameter_list|()
block|{
name|LayoutFormatter
name|f
init|=
operator|new
name|CompositeFormat
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"No Change"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"No Change"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testArrayComposite ()
specifier|public
name|void
name|testArrayComposite
parameter_list|()
block|{
name|LayoutFormatter
name|f
init|=
operator|new
name|CompositeFormat
argument_list|(
operator|new
name|LayoutFormatter
index|[]
block|{
name|fieldText
lambda|->
name|fieldText
operator|+
name|fieldText
block|,
name|fieldText
lambda|->
literal|"A"
operator|+
name|fieldText
block|,
name|fieldText
lambda|->
literal|"B"
operator|+
name|fieldText
block|}
argument_list|)
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"BAff"
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"f"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testDoubleComposite ()
specifier|public
name|void
name|testDoubleComposite
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
name|LayoutFormatter
name|first
init|=
operator|new
name|AuthorOrgSci
argument_list|()
decl_stmt|;
name|LayoutFormatter
name|second
init|=
operator|new
name|NoSpaceBetweenAbbreviations
argument_list|()
decl_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|second
operator|.
name|format
argument_list|(
name|first
operator|.
name|format
argument_list|(
literal|"John Flynn and Sabine Gartska"
argument_list|)
argument_list|)
argument_list|,
name|f
operator|.
name|format
argument_list|(
literal|"John Flynn and Sabine Gartska"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
name|second
operator|.
name|format
argument_list|(
name|first
operator|.
name|format
argument_list|(
literal|"Sa Makridakis and Sa Ca Wheelwright and Va Ea McGee"
argument_list|)
argument_list|)
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
