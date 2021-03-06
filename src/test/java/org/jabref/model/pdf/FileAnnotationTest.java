begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.pdf
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|pdf
package|;
end_package

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|LocalDateTime
import|;
end_import

begin_import
import|import
name|java
operator|.
name|time
operator|.
name|temporal
operator|.
name|ChronoUnit
import|;
end_import

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
name|assertTrue
import|;
end_import

begin_class
DECL|class|FileAnnotationTest
specifier|public
class|class
name|FileAnnotationTest
block|{
annotation|@
name|Test
DECL|method|testParseDateMinusBeforeTimezone ()
specifier|public
name|void
name|testParseDateMinusBeforeTimezone
parameter_list|()
block|{
name|String
name|dateString
init|=
literal|"D:20170512224019-03'00'"
decl_stmt|;
name|LocalDateTime
name|date
init|=
name|FileAnnotation
operator|.
name|extractModifiedTime
argument_list|(
name|dateString
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|LocalDateTime
operator|.
name|of
argument_list|(
literal|2017
argument_list|,
literal|05
argument_list|,
literal|12
argument_list|,
literal|22
argument_list|,
literal|40
argument_list|,
literal|19
argument_list|)
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testParseDatePlusBeforeTimezone ()
specifier|public
name|void
name|testParseDatePlusBeforeTimezone
parameter_list|()
block|{
name|String
name|dateString
init|=
literal|"D:20170512224019+03'00'"
decl_stmt|;
name|LocalDateTime
name|date
init|=
name|FileAnnotation
operator|.
name|extractModifiedTime
argument_list|(
name|dateString
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|LocalDateTime
operator|.
name|of
argument_list|(
literal|2017
argument_list|,
literal|05
argument_list|,
literal|12
argument_list|,
literal|22
argument_list|,
literal|40
argument_list|,
literal|19
argument_list|)
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testParseDateNoTimezone ()
specifier|public
name|void
name|testParseDateNoTimezone
parameter_list|()
block|{
name|String
name|dateString
init|=
literal|"D:20170512224019"
decl_stmt|;
name|LocalDateTime
name|date
init|=
name|FileAnnotation
operator|.
name|extractModifiedTime
argument_list|(
name|dateString
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|LocalDateTime
operator|.
name|of
argument_list|(
literal|2017
argument_list|,
literal|05
argument_list|,
literal|12
argument_list|,
literal|22
argument_list|,
literal|40
argument_list|,
literal|19
argument_list|)
argument_list|,
name|date
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testParseNotADate ()
specifier|public
name|void
name|testParseNotADate
parameter_list|()
block|{
name|String
name|dateString
init|=
literal|"gsdfgwergsdf"
decl_stmt|;
name|LocalDateTime
name|date
init|=
name|FileAnnotation
operator|.
name|extractModifiedTime
argument_list|(
name|dateString
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|ChronoUnit
operator|.
name|SECONDS
operator|.
name|between
argument_list|(
name|LocalDateTime
operator|.
name|now
argument_list|()
argument_list|,
name|date
argument_list|)
operator|<=
literal|1
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

