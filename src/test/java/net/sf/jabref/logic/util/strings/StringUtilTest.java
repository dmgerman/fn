begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.util.strings
package|package
name|net
operator|.
name|sf
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
name|junit
operator|.
name|framework
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
name|*
import|;
end_import

begin_class
DECL|class|StringUtilTest
specifier|public
class|class
name|StringUtilTest
block|{
annotation|@
name|Test
DECL|method|testUnifyLineBreaks ()
specifier|public
name|void
name|testUnifyLineBreaks
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Mac< v9
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\n"
argument_list|,
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
literal|"\r"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Windows
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\n"
argument_list|,
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
literal|"\r\n"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Unix
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"\n"
argument_list|,
name|StringUtil
operator|.
name|unifyLineBreaks
argument_list|(
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

