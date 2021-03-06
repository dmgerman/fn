begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.entry.identifier
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|identifier
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
DECL|class|ArXivIdentifierTest
class|class
name|ArXivIdentifierTest
block|{
annotation|@
name|Test
DECL|method|parse ()
name|void
name|parse
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parsed
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"0710.0994"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
literal|"0710.0994"
argument_list|)
argument_list|)
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseWithArXivPrefix ()
name|void
name|parseWithArXivPrefix
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parsed
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"arXiv:0710.0994"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
literal|"0710.0994"
argument_list|)
argument_list|)
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseWithArxivPrefix ()
name|void
name|parseWithArxivPrefix
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parsed
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"arxiv:0710.0994"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
literal|"0710.0994"
argument_list|)
argument_list|)
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseWithClassification ()
name|void
name|parseWithClassification
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parsed
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"0706.0001v1 [q-bio.CB]"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
literal|"0706.0001v1"
argument_list|,
literal|"q-bio.CB"
argument_list|)
argument_list|)
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseWithArXivPrefixAndClassification ()
name|void
name|parseWithArXivPrefixAndClassification
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parsed
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"arXiv:0706.0001v1 [q-bio.CB]"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
literal|"0706.0001v1"
argument_list|,
literal|"q-bio.CB"
argument_list|)
argument_list|)
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseOldIdentifier ()
name|void
name|parseOldIdentifier
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parsed
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"math.GT/0309136"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
literal|"math.GT/0309136"
argument_list|,
literal|"math.GT"
argument_list|)
argument_list|)
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseOldIdentifierWithArXivPrefix ()
name|void
name|parseOldIdentifierWithArXivPrefix
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parsed
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"arXiv:math.GT/0309136"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
literal|"math.GT/0309136"
argument_list|,
literal|"math.GT"
argument_list|)
argument_list|)
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|parseUrl ()
name|void
name|parseUrl
parameter_list|()
throws|throws
name|Exception
block|{
name|Optional
argument_list|<
name|ArXivIdentifier
argument_list|>
name|parsed
init|=
name|ArXivIdentifier
operator|.
name|parse
argument_list|(
literal|"http://arxiv.org/abs/1502.05795"
argument_list|)
decl_stmt|;
name|assertEquals
argument_list|(
name|Optional
operator|.
name|of
argument_list|(
operator|new
name|ArXivIdentifier
argument_list|(
literal|"1502.05795"
argument_list|,
literal|""
argument_list|)
argument_list|)
argument_list|,
name|parsed
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

