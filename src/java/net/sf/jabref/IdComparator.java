begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
import|;
end_import

begin_comment
comment|/**  * Comparator for sorting BibtexEntry objects based on their ID. This  * can be used to sort entries back into the order they were created,  * provided the IDs given to entries are lexically monotonically increasing.  */
end_comment

begin_class
DECL|class|IdComparator
specifier|public
class|class
name|IdComparator
implements|implements
name|Comparator
argument_list|<
name|BibtexEntry
argument_list|>
block|{
DECL|method|compare (BibtexEntry one, BibtexEntry two)
specifier|public
name|int
name|compare
parameter_list|(
name|BibtexEntry
name|one
parameter_list|,
name|BibtexEntry
name|two
parameter_list|)
block|{
return|return
name|one
operator|.
name|getId
argument_list|()
operator|.
name|compareTo
argument_list|(
name|two
operator|.
name|getId
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

