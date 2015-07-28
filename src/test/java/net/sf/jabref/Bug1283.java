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
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
operator|.
name|MonthUtil
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
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|assertFalse
import|;
end_import

begin_class
DECL|class|Bug1283
specifier|public
class|class
name|Bug1283
block|{
annotation|@
name|Test
DECL|method|testBug1283 ()
specifier|public
name|void
name|testBug1283
parameter_list|()
throws|throws
name|IOException
block|{
name|Assert
operator|.
name|assertFalse
argument_list|(
name|MonthUtil
operator|.
name|getMonth
argument_list|(
literal|"8,"
argument_list|)
operator|.
name|isValid
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

