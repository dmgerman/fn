begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util.strings
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|strings
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

begin_class
DECL|class|StringLengthComparatorTest
specifier|public
class|class
name|StringLengthComparatorTest
block|{
DECL|field|slc
specifier|private
name|StringLengthComparator
name|slc
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
block|{
name|slc
operator|=
operator|new
name|StringLengthComparator
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
operator|-
literal|1
argument_list|,
name|slc
operator|.
name|compare
argument_list|(
literal|"AAA"
argument_list|,
literal|"AA"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|0
argument_list|,
name|slc
operator|.
name|compare
argument_list|(
literal|"AA"
argument_list|,
literal|"AA"
argument_list|)
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|1
argument_list|,
name|slc
operator|.
name|compare
argument_list|(
literal|"AA"
argument_list|,
literal|"AAA"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

