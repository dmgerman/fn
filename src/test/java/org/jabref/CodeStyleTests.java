begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref
package|package
name|org
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|charset
operator|.
name|StandardCharsets
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Files
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Path
import|;
end_import

begin_import
import|import
name|java
operator|.
name|nio
operator|.
name|file
operator|.
name|Paths
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|strings
operator|.
name|StringUtil
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
name|assertTrue
import|;
end_import

begin_class
DECL|class|CodeStyleTests
specifier|public
class|class
name|CodeStyleTests
block|{
annotation|@
name|Test
DECL|method|StringUtilClassIsSmall ()
specifier|public
name|void
name|StringUtilClassIsSmall
parameter_list|()
throws|throws
name|Exception
block|{
name|Path
name|path
init|=
name|Paths
operator|.
name|get
argument_list|(
literal|"src"
argument_list|,
literal|"main"
argument_list|,
literal|"java"
argument_list|,
name|StringUtil
operator|.
name|class
operator|.
name|getName
argument_list|()
operator|.
name|replace
argument_list|(
literal|'.'
argument_list|,
literal|'/'
argument_list|)
operator|+
literal|".java"
argument_list|)
decl_stmt|;
name|int
name|lineCount
init|=
name|Files
operator|.
name|readAllLines
argument_list|(
name|path
argument_list|,
name|StandardCharsets
operator|.
name|UTF_8
argument_list|)
operator|.
name|size
argument_list|()
decl_stmt|;
name|assertTrue
argument_list|(
name|lineCount
operator|<=
literal|722
argument_list|,
literal|"StringUtil increased in size. "
operator|+
literal|"We try to keep this class as small as possible. "
operator|+
literal|"Thus think twice if you add something to StringUtil."
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

