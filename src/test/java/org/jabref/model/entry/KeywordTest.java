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
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
DECL|class|KeywordTest
specifier|public
class|class
name|KeywordTest
block|{
annotation|@
name|Test
DECL|method|getPathFromRootAsStringForSimpleChain ()
specifier|public
name|void
name|getPathFromRootAsStringForSimpleChain
parameter_list|()
throws|throws
name|Exception
block|{
name|Keyword
name|keywordChain
init|=
name|Keyword
operator|.
name|of
argument_list|(
literal|"A"
argument_list|,
literal|"B"
argument_list|,
literal|"C"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
literal|"A> B"
argument_list|,
name|keywordChain
operator|.
name|getChild
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getPathFromRootAsString
argument_list|(
literal|'>'
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|getAllSubchainsAsStringForSimpleChain ()
specifier|public
name|void
name|getAllSubchainsAsStringForSimpleChain
parameter_list|()
throws|throws
name|Exception
block|{
name|Keyword
name|keywordChain
init|=
name|Keyword
operator|.
name|of
argument_list|(
literal|"A"
argument_list|,
literal|"B"
argument_list|,
literal|"C"
argument_list|)
decl_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|expected
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
name|expected
operator|.
name|add
argument_list|(
literal|"A"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|add
argument_list|(
literal|"A> B"
argument_list|)
expr_stmt|;
name|expected
operator|.
name|add
argument_list|(
literal|"A> B> C"
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
name|expected
argument_list|,
name|keywordChain
operator|.
name|getAllSubchainsAsString
argument_list|(
literal|'>'
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

