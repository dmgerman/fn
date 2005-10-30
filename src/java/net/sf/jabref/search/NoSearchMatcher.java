begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
package|;
end_package

begin_import
import|import
name|ca
operator|.
name|odell
operator|.
name|glazedlists
operator|.
name|matchers
operator|.
name|Matcher
import|;
end_import

begin_comment
comment|/**  * Matcher that accepts all entries. Used for filtering when so search is active.  */
end_comment

begin_class
DECL|class|NoSearchMatcher
specifier|public
class|class
name|NoSearchMatcher
implements|implements
name|Matcher
block|{
DECL|field|INSTANCE
specifier|public
specifier|static
specifier|final
name|Matcher
name|INSTANCE
init|=
operator|new
name|NoSearchMatcher
argument_list|()
decl_stmt|;
DECL|method|NoSearchMatcher ()
specifier|private
name|NoSearchMatcher
parameter_list|()
block|{              }
DECL|method|matches (Object object)
specifier|public
name|boolean
name|matches
parameter_list|(
name|Object
name|object
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

