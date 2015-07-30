begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.util
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|util
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
name|logic
operator|.
name|util
operator|.
name|CaseChangers
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
DECL|class|CaseChangersTest
specifier|public
class|class
name|CaseChangersTest
block|{
annotation|@
name|Test
DECL|method|testNumberOfModes ()
specifier|public
name|void
name|testNumberOfModes
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"lower"
argument_list|,
name|CaseChangers
operator|.
name|LOWER
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"UPPER"
argument_list|,
name|CaseChangers
operator|.
name|UPPER
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper first"
argument_list|,
name|CaseChangers
operator|.
name|UPPER_FIRST
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First"
argument_list|,
name|CaseChangers
operator|.
name|UPPER_EACH_FIRST
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Title"
argument_list|,
name|CaseChangers
operator|.
name|TITLE
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseLower ()
specifier|public
name|void
name|testChangeCaseLower
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|CaseChangers
operator|.
name|LOWER
operator|.
name|changeCase
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"lower"
argument_list|,
name|CaseChangers
operator|.
name|LOWER
operator|.
name|changeCase
argument_list|(
literal|"LOWER"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseUpper ()
specifier|public
name|void
name|testChangeCaseUpper
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|CaseChangers
operator|.
name|UPPER
operator|.
name|changeCase
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"LOWER"
argument_list|,
name|CaseChangers
operator|.
name|UPPER
operator|.
name|changeCase
argument_list|(
literal|"LOWER"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"UPPER"
argument_list|,
name|CaseChangers
operator|.
name|UPPER
operator|.
name|changeCase
argument_list|(
literal|"upper"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"UPPER"
argument_list|,
name|CaseChangers
operator|.
name|UPPER
operator|.
name|changeCase
argument_list|(
literal|"UPPER"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseUpperFirst ()
specifier|public
name|void
name|testChangeCaseUpperFirst
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|CaseChangers
operator|.
name|UPPER_FIRST
operator|.
name|changeCase
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper first"
argument_list|,
name|CaseChangers
operator|.
name|UPPER_FIRST
operator|.
name|changeCase
argument_list|(
literal|"upper First"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseUpperEachFirst ()
specifier|public
name|void
name|testChangeCaseUpperEachFirst
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|CaseChangers
operator|.
name|UPPER_EACH_FIRST
operator|.
name|changeCase
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First"
argument_list|,
name|CaseChangers
operator|.
name|UPPER_EACH_FIRST
operator|.
name|changeCase
argument_list|(
literal|"upper each First"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testChangeCaseTitle ()
specifier|public
name|void
name|testChangeCaseTitle
parameter_list|()
block|{
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|""
argument_list|,
name|CaseChangers
operator|.
name|TITLE
operator|.
name|changeCase
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"Upper Each First"
argument_list|,
name|CaseChangers
operator|.
name|TITLE
operator|.
name|changeCase
argument_list|(
literal|"upper each first"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"An Upper Each First And"
argument_list|,
name|CaseChangers
operator|.
name|TITLE
operator|.
name|changeCase
argument_list|(
literal|"an upper each first and"
argument_list|)
argument_list|)
expr_stmt|;
name|Assert
operator|.
name|assertEquals
argument_list|(
literal|"An Upper Each of the and First And"
argument_list|,
name|CaseChangers
operator|.
name|TITLE
operator|.
name|changeCase
argument_list|(
literal|"an upper each of the and first and"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

