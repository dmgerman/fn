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
name|assertThrows
import|;
end_import

begin_class
DECL|class|EprintTest
specifier|public
class|class
name|EprintTest
block|{
annotation|@
name|Test
DECL|method|acceptPlainEprint ()
specifier|public
name|void
name|acceptPlainEprint
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"0706.0001"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"0706.0001"
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptLegacyEprint ()
specifier|public
name|void
name|acceptLegacyEprint
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"astro-ph.GT/1234567"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"astro-ph.GT/1234567"
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
name|assertEquals
argument_list|(
literal|"math/1234567"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"math/1234567"
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptPlainEprintWithVersion ()
specifier|public
name|void
name|acceptPlainEprintWithVersion
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"0706.0001v1"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"0706.0001v1"
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|ignoreLeadingAndTrailingWhitespaces ()
specifier|public
name|void
name|ignoreLeadingAndTrailingWhitespaces
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"0706.0001v1"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"  0706.0001v1 "
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rejectEmbeddedEprint ()
specifier|public
name|void
name|rejectEmbeddedEprint
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|IllegalArgumentException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|Eprint
argument_list|(
literal|"other stuff 0706.0001v1 end"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|rejectInvalidEprint ()
specifier|public
name|void
name|rejectInvalidEprint
parameter_list|()
block|{
name|assertThrows
argument_list|(
name|IllegalArgumentException
operator|.
name|class
argument_list|,
parameter_list|()
lambda|->
operator|new
name|Eprint
argument_list|(
literal|"https://thisisnouri"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptArxivPrefix ()
specifier|public
name|void
name|acceptArxivPrefix
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"0706.0001v1"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"arXiv:0706.0001v1"
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|acceptURLEprint ()
specifier|public
name|void
name|acceptURLEprint
parameter_list|()
block|{
comment|// http
name|assertEquals
argument_list|(
literal|"0706.0001v1"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"http://arxiv.org/abs/0706.0001v1"
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
comment|// https
name|assertEquals
argument_list|(
literal|"0706.0001v1"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"https://arxiv.org/abs/0706.0001v1"
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
comment|// other domains
name|assertEquals
argument_list|(
literal|"0706.0001v1"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"https://asdf.org/abs/0706.0001v1"
argument_list|)
operator|.
name|getEprint
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
DECL|method|constructCorrectURLForEprint ()
specifier|public
name|void
name|constructCorrectURLForEprint
parameter_list|()
block|{
name|assertEquals
argument_list|(
literal|"https://arxiv.org/abs/0706.0001v1"
argument_list|,
operator|new
name|Eprint
argument_list|(
literal|"0706.0001v1"
argument_list|)
operator|.
name|getURIAsASCIIString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

