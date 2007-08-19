begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.groups
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|groups
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntry
import|;
end_import

begin_comment
comment|/**  * Matcher for filtering or sorting the table according to whether entries  * are tagged as group matches.  */
end_comment

begin_class
DECL|class|GroupMatcher
specifier|public
class|class
name|GroupMatcher
implements|implements
name|Matcher
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|field|INSTANCE
specifier|public
specifier|static
name|GroupMatcher
name|INSTANCE
init|=
operator|new
name|GroupMatcher
argument_list|()
decl_stmt|;
DECL|method|matches (BibtexEntry entry)
specifier|public
name|boolean
name|matches
parameter_list|(
name|BibtexEntry
name|entry
parameter_list|)
block|{
return|return
name|entry
operator|.
name|isGroupHit
argument_list|()
return|;
block|}
block|}
end_class

end_unit

