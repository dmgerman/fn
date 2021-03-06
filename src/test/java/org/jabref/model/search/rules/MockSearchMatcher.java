begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.search.rules
package|package
name|org
operator|.
name|jabref
operator|.
name|model
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|search
operator|.
name|SearchMatcher
import|;
end_import

begin_comment
comment|/**  * Mock search rule that returns the values passed. Useful for testing.  */
end_comment

begin_class
DECL|class|MockSearchMatcher
specifier|public
class|class
name|MockSearchMatcher
implements|implements
name|SearchMatcher
block|{
DECL|field|result
specifier|private
specifier|final
name|boolean
name|result
decl_stmt|;
DECL|method|MockSearchMatcher (boolean result)
specifier|public
name|MockSearchMatcher
parameter_list|(
name|boolean
name|result
parameter_list|)
block|{
name|this
operator|.
name|result
operator|=
name|result
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|isMatch (BibEntry entry)
specifier|public
name|boolean
name|isMatch
parameter_list|(
name|BibEntry
name|entry
parameter_list|)
block|{
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

