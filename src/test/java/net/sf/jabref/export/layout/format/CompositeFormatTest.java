begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
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
name|export
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
DECL|class|CompositeFormatTest
specifier|public
class|class
name|CompositeFormatTest
block|{
annotation|@
name|Test
DECL|method|testComposite ()
specifier|public
name|void
name|testComposite
parameter_list|()
block|{
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
operator|new
name|LayoutFormatter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
return|return
name|fieldText
operator|+
name|fieldText
return|;
block|}
function|}
operator|,
function|new LayoutFormatter
parameter_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
return|return
literal|"A"
operator|+
name|fieldText
return|;
block|}
function|}
operator|,
function|new LayoutFormatter
parameter_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
return|return
literal|"B"
operator|+
name|fieldText
return|;
block|}
function|}}
block|)
function|;
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
function|}  }
end_class

end_unit

