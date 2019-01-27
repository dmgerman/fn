begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.util
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|assertFalse
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

begin_comment
comment|/**  * Tests for ensuring we can compare most appearing version strings  */
end_comment

begin_class
DECL|class|JavaVersionTest
specifier|public
class|class
name|JavaVersionTest
block|{
DECL|field|java
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|java
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|java9
specifier|private
specifier|static
name|List
argument_list|<
name|String
argument_list|>
name|java9
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
static|static
block|{
name|java
operator|.
name|add
argument_list|(
literal|"1.6.0_10"
argument_list|)
expr_stmt|;
comment|// Oracle
name|java
operator|.
name|add
argument_list|(
literal|"1.6.0_45"
argument_list|)
expr_stmt|;
comment|// Oracle
name|java
operator|.
name|add
argument_list|(
literal|"1.7.0_13"
argument_list|)
expr_stmt|;
comment|// Oracle
name|java
operator|.
name|add
argument_list|(
literal|"1.8.0_76-release"
argument_list|)
expr_stmt|;
comment|//openjdk
name|java
operator|.
name|add
argument_list|(
literal|"1.8.0_92"
argument_list|)
expr_stmt|;
comment|//Oracle
name|java
operator|.
name|add
argument_list|(
literal|"1.8.0_111"
argument_list|)
expr_stmt|;
comment|//Oracle
name|java
operator|.
name|add
argument_list|(
literal|"1.8.0_112-release"
argument_list|)
expr_stmt|;
comment|//openjdk
name|java
operator|.
name|add
argument_list|(
literal|"1.8.0_152-release"
argument_list|)
expr_stmt|;
comment|//openjdk
name|java
operator|.
name|add
argument_list|(
literal|"1.8.0_144"
argument_list|)
expr_stmt|;
comment|//Oracle
comment|// Examples http://openjdk.java.net/jeps/223
comment|// Note that it might be possible that java 9 versions are either 9.1.4+8 or new style 1.9.0_31-b08
name|java9
operator|.
name|add
argument_list|(
literal|"9-internal"
argument_list|)
expr_stmt|;
comment|// openjdk
name|java9
operator|.
name|add
argument_list|(
literal|"1.9.0_20-b62"
argument_list|)
expr_stmt|;
name|java9
operator|.
name|add
argument_list|(
literal|"1.9.0_20-b62"
argument_list|)
expr_stmt|;
name|java9
operator|.
name|add
argument_list|(
literal|"9.2.4+45"
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isJava9 ()
specifier|public
name|void
name|isJava9
parameter_list|()
throws|throws
name|Exception
block|{
comment|// Check that all valid java versions below 9 are recognized as not java 9
for|for
control|(
name|String
name|versionString
range|:
name|java
control|)
block|{
specifier|final
name|JavaVersion
name|java8
init|=
operator|new
name|JavaVersion
argument_list|(
name|versionString
argument_list|)
decl_stmt|;
name|assertFalse
argument_list|(
name|java8
operator|.
name|isJava9
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// Check if all valid version 9 strings are recognized as being version 9
for|for
control|(
name|String
name|version9String
range|:
name|java9
control|)
block|{
specifier|final
name|JavaVersion
name|java9
init|=
operator|new
name|JavaVersion
argument_list|(
name|version9String
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|java9
operator|.
name|isJava9
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// For impossible comparisons we assume it's not java 9
name|assertFalse
argument_list|(
operator|new
name|JavaVersion
argument_list|(
literal|null
argument_list|)
operator|.
name|isJava9
argument_list|()
argument_list|)
expr_stmt|;
name|assertFalse
argument_list|(
operator|new
name|JavaVersion
argument_list|(
literal|"U.N.K.N.O.W.N"
argument_list|)
operator|.
name|isJava9
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|isAtLeast ()
specifier|public
name|void
name|isAtLeast
parameter_list|()
throws|throws
name|Exception
block|{
specifier|final
name|JavaVersion
name|java8
init|=
operator|new
name|JavaVersion
argument_list|(
literal|"1.8"
argument_list|)
decl_stmt|;
for|for
control|(
name|String
name|version8
range|:
name|java
control|)
block|{
name|assertTrue
argument_list|(
name|java8
operator|.
name|isAtLeast
argument_list|(
name|version8
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|JavaVersion
name|java8Example
init|=
operator|new
name|JavaVersion
argument_list|(
name|version8
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|java8Example
operator|.
name|isAtLeast
argument_list|(
literal|"1.5"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check if we optimistically return true if we cannot determine the result
name|assertTrue
argument_list|(
name|java8Example
operator|.
name|isAtLeast
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|JavaVersion
argument_list|(
literal|null
argument_list|)
operator|.
name|isAtLeast
argument_list|(
name|version8
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
operator|new
name|JavaVersion
argument_list|(
literal|"U.N.K.N.O.W.N"
argument_list|)
operator|.
name|isAtLeast
argument_list|(
name|version8
argument_list|)
argument_list|)
expr_stmt|;
name|assertTrue
argument_list|(
name|java8Example
operator|.
name|isAtLeast
argument_list|(
literal|"useless"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Check against all java 9 entries in both directions
for|for
control|(
name|String
name|version9
range|:
name|java9
control|)
block|{
name|assertFalse
argument_list|(
name|java8Example
operator|.
name|isAtLeast
argument_list|(
name|version9
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|JavaVersion
name|java9
init|=
operator|new
name|JavaVersion
argument_list|(
name|version9
argument_list|)
decl_stmt|;
name|assertTrue
argument_list|(
name|java9
operator|.
name|isAtLeast
argument_list|(
name|version8
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

