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
name|assertNull
import|;
end_import

begin_class
DECL|class|ToLowerCaseTest
specifier|public
class|class
name|ToLowerCaseTest
block|{
annotation|@
name|Test
DECL|method|testEmpty ()
specifier|public
name|void
name|testEmpty
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
operator|new
name|ToLowerCase
argument_list|()
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNull ()
specifier|public
name|void
name|testNull
parameter_list|()
block|{
name|assertNull
argument_list|(
operator|new
name|ToLowerCase
argument_list|()
operator|.
name|format
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testLowerCase ()
specifier|public
name|void
name|testLowerCase
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"abcd efg"
argument_list|,
operator|new
name|ToLowerCase
argument_list|()
operator|.
name|format
argument_list|(
literal|"abcd efg"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testUpperCase ()
specifier|public
name|void
name|testUpperCase
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"abcd efg"
argument_list|,
operator|new
name|ToLowerCase
argument_list|()
operator|.
name|format
argument_list|(
literal|"ABCD EFG"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMixedCase ()
specifier|public
name|void
name|testMixedCase
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"abcd efg"
argument_list|,
operator|new
name|ToLowerCase
argument_list|()
operator|.
name|format
argument_list|(
literal|"abCD eFg"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

