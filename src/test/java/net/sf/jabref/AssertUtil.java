begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|assertEquals
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
name|assertNotNull
import|;
end_import

begin_class
DECL|class|AssertUtil
specifier|public
class|class
name|AssertUtil
block|{
comment|/**      * Will check if two paths are the same.      */
DECL|method|assertEqualPaths (String path1, String path2)
specifier|public
specifier|static
name|void
name|assertEqualPaths
parameter_list|(
name|String
name|path1
parameter_list|,
name|String
name|path2
parameter_list|)
block|{
name|assertNotNull
argument_list|(
literal|"first path must not be null"
argument_list|,
name|path1
argument_list|)
expr_stmt|;
name|assertNotNull
argument_list|(
literal|"second path must not be null"
argument_list|,
name|path2
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|path1
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"/"
argument_list|)
argument_list|,
name|path2
operator|.
name|replaceAll
argument_list|(
literal|"\\\\"
argument_list|,
literal|"/"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

