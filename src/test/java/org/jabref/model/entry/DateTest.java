begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|LocalDate
import|;
end_import

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
DECL|class|DateTest
specifier|public
class|class
name|DateTest
block|{
annotation|@
name|Test
DECL|method|parseCorrectlyDayMonthYearDate ()
specifier|public
name|void
name|parseCorrectlyDayMonthYearDate
parameter_list|()
throws|throws
name|Exception
block|{
name|Date
name|expected
init|=
operator|new
name|Date
argument_list|(
name|LocalDate
operator|.
name|of
argument_list|(
literal|2014
argument_list|,
literal|6
argument_list|,
literal|19
argument_list|)
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
name|expected
argument_list|)
argument_list|,
name|Date
operator|.
name|parse
argument_list|(
literal|"19-06-2014"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
argument_list|(
name|expected
operator|=
name|NullPointerException
operator|.
name|class
argument_list|)
DECL|method|parseDateNull ()
specifier|public
name|void
name|parseDateNull
parameter_list|()
block|{
name|assertEquals
argument_list|(
name|Optional
operator|.
name|empty
argument_list|()
argument_list|,
name|Date
operator|.
name|parse
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

