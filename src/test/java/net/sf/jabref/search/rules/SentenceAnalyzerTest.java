begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.search.rules
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
operator|.
name|rules
package|;
end_package

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
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
DECL|class|SentenceAnalyzerTest
specifier|public
class|class
name|SentenceAnalyzerTest
block|{
annotation|@
name|Test
DECL|method|testGetWords ()
specifier|public
name|void
name|testGetWords
parameter_list|()
throws|throws
name|Exception
block|{
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"a"
argument_list|,
literal|"b"
argument_list|)
argument_list|,
operator|new
name|BasicSearchRule
operator|.
name|SentenceAnalyzer
argument_list|(
literal|"a b"
argument_list|)
operator|.
name|getWords
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Arrays
operator|.
name|asList
argument_list|(
literal|"a"
argument_list|,
literal|"b"
argument_list|)
argument_list|,
operator|new
name|BasicSearchRule
operator|.
name|SentenceAnalyzer
argument_list|(
literal|" a b "
argument_list|)
operator|.
name|getWords
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|"b "
argument_list|)
argument_list|,
operator|new
name|BasicSearchRule
operator|.
name|SentenceAnalyzer
argument_list|(
literal|"\"b \" "
argument_list|)
operator|.
name|getWords
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|Collections
operator|.
name|singletonList
argument_list|(
literal|" a"
argument_list|)
argument_list|,
operator|new
name|BasicSearchRule
operator|.
name|SentenceAnalyzer
argument_list|(
literal|" \\ a"
argument_list|)
operator|.
name|getWords
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

