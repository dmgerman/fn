begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.logic.layout.format
package|package
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
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

begin_class
DECL|class|RisKeywordsTest
specifier|public
class|class
name|RisKeywordsTest
block|{
annotation|@
name|Test
DECL|method|testEmpty ()
specifier|public
name|void
name|testEmpty
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
operator|new
name|RisKeywords
argument_list|()
operator|.
name|format
argument_list|(
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testNull ()
specifier|public
name|void
name|testNull
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|""
argument_list|,
operator|new
name|RisKeywords
argument_list|()
operator|.
name|format
argument_list|(
literal|null
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testSingleKeyword ()
specifier|public
name|void
name|testSingleKeyword
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"KW  - abcd"
argument_list|,
operator|new
name|RisKeywords
argument_list|()
operator|.
name|format
argument_list|(
literal|"abcd"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testTwoKeywords ()
specifier|public
name|void
name|testTwoKeywords
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"KW  - abcd"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"KW  - efg"
argument_list|,
operator|new
name|RisKeywords
argument_list|()
operator|.
name|format
argument_list|(
literal|"abcd, efg"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|testMultipleKeywords ()
specifier|public
name|void
name|testMultipleKeywords
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"KW  - abcd"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"KW  - efg"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"KW  - hij"
operator|+
name|OS
operator|.
name|NEWLINE
operator|+
literal|"KW  - klm"
argument_list|,
operator|new
name|RisKeywords
argument_list|()
operator|.
name|format
argument_list|(
literal|"abcd, efg, hij, klm"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

