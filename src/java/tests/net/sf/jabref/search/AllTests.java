begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|tests.net.sf.jabref.search
package|package
name|tests
operator|.
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
package|;
end_package

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|junit
operator|.
name|framework
operator|.
name|TestSuite
import|;
end_import

begin_class
DECL|class|AllTests
specifier|public
class|class
name|AllTests
block|{
DECL|method|suite ()
specifier|public
specifier|static
name|Test
name|suite
parameter_list|()
block|{
name|TestSuite
name|suite
init|=
operator|new
name|TestSuite
argument_list|(
literal|"Test for tests.net.sf.jabref.search"
argument_list|)
decl_stmt|;
comment|//$JUnit-BEGIN$
name|suite
operator|.
name|addTestSuite
argument_list|(
name|BasicSearchTest
operator|.
name|class
argument_list|)
expr_stmt|;
comment|//$JUnit-END$
return|return
name|suite
return|;
block|}
block|}
end_class

end_unit

