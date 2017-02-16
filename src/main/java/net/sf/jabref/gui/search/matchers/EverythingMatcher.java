begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.search.matchers
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|search
operator|.
name|matchers
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
comment|/**  * Matcher that accepts all entries. Used for filtering when so search is  * active.  */
end_comment

begin_class
DECL|class|EverythingMatcher
specifier|public
class|class
name|EverythingMatcher
implements|implements
name|Matcher
argument_list|<
name|BibEntry
argument_list|>
block|{
DECL|field|INSTANCE
specifier|public
specifier|static
specifier|final
name|Matcher
argument_list|<
name|BibEntry
argument_list|>
name|INSTANCE
init|=
operator|new
name|EverythingMatcher
argument_list|()
decl_stmt|;
annotation|@
name|Override
DECL|method|matches (BibEntry object)
specifier|public
name|boolean
name|matches
parameter_list|(
name|BibEntry
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

