begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.integrity
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|integrity
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

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
DECL|class|PersonNamesCheckerTest
specifier|public
class|class
name|PersonNamesCheckerTest
block|{
DECL|field|checker
name|PersonNamesChecker
name|checker
decl_stmt|;
annotation|@
name|Before
DECL|method|setUp ()
specifier|public
name|void
name|setUp
parameter_list|()
throws|throws
name|Exception
block|{
name|checker
operator|=
operator|new
name|PersonNamesChecker
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|complainAboutPersonStringWithTwoManyCommas ()
specifier|public
name|void
name|complainAboutPersonStringWithTwoManyCommas
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
literal|"Names are not in the standard BibTeX format."
argument_list|)
argument_list|,
name|checker
operator|.
name|checkValue
argument_list|(
literal|"Test1, Test2, Test3, Test4, Test5, Test6"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|doNotComplainAboutSecondNameInFront ()
specifier|public
name|void
name|doNotComplainAboutSecondNameInFront
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|checker
operator|.
name|checkValue
argument_list|(
literal|"M. J. Gotay"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

