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
name|Objects
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Stream
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
name|ChainNode
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
name|util
operator|.
name|OptionalUtil
import|;
end_import

begin_comment
comment|/**  * Represents a keyword in a chain of keywords.  * For example, "JabRef" in "Bibliographic manager> Awesome ones> JabRef"  */
end_comment

begin_class
DECL|class|Keyword
specifier|public
class|class
name|Keyword
extends|extends
name|ChainNode
argument_list|<
name|Keyword
argument_list|>
implements|implements
name|Comparable
argument_list|<
name|Keyword
argument_list|>
block|{
DECL|field|DEFAULT_HIERARCHICAL_DELIMITER
specifier|public
specifier|static
name|Character
name|DEFAULT_HIERARCHICAL_DELIMITER
init|=
literal|'>'
decl_stmt|;
DECL|field|keyword
specifier|private
specifier|final
name|String
name|keyword
decl_stmt|;
DECL|method|Keyword (String keyword)
specifier|public
name|Keyword
parameter_list|(
name|String
name|keyword
parameter_list|)
block|{
name|super
argument_list|(
name|Keyword
operator|.
name|class
argument_list|)
expr_stmt|;
name|this
operator|.
name|keyword
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|keyword
argument_list|)
operator|.
name|trim
argument_list|()
expr_stmt|;
block|}
comment|/**      * Connects all the given keywords into one chain and returns its root,      * e.g. "A", "B", "C" is transformed into "A> B> C".      */
DECL|method|of (String... keywords)
specifier|public
specifier|static
name|Keyword
name|of
parameter_list|(
name|String
modifier|...
name|keywords
parameter_list|)
block|{
if|if
condition|(
name|keywords
operator|.
name|length
operator|==
literal|0
condition|)
block|{
return|return
operator|new
name|Keyword
argument_list|(
literal|""
argument_list|)
return|;
block|}
name|Keyword
name|root
init|=
operator|new
name|Keyword
argument_list|(
name|keywords
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|1
init|;
name|i
operator|<
name|keywords
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|root
operator|.
name|addAtEnd
argument_list|(
name|keywords
index|[
name|i
index|]
argument_list|)
expr_stmt|;
block|}
return|return
name|root
return|;
block|}
annotation|@
name|Override
DECL|method|equals (Object o)
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|o
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|o
condition|)
block|{
return|return
literal|true
return|;
block|}
if|if
condition|(
name|o
operator|==
literal|null
operator|||
name|getClass
argument_list|()
operator|!=
name|o
operator|.
name|getClass
argument_list|()
condition|)
block|{
return|return
literal|false
return|;
block|}
name|Keyword
name|other
init|=
operator|(
name|Keyword
operator|)
name|o
decl_stmt|;
return|return
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|keyword
argument_list|,
name|other
operator|.
name|keyword
argument_list|)
comment|//&& Objects.equals(this.getParent(), other.getParent()) : we can't check the parents because then we would run in circles
operator|&&
name|Objects
operator|.
name|equals
argument_list|(
name|this
operator|.
name|getChild
argument_list|()
argument_list|,
name|other
operator|.
name|getChild
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|hashCode ()
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
return|return
name|Objects
operator|.
name|hash
argument_list|(
name|keyword
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|getSubchainAsString
argument_list|(
name|DEFAULT_HIERARCHICAL_DELIMITER
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|compareTo (Keyword o)
specifier|public
name|int
name|compareTo
parameter_list|(
name|Keyword
name|o
parameter_list|)
block|{
return|return
name|keyword
operator|.
name|compareTo
argument_list|(
name|o
operator|.
name|keyword
argument_list|)
return|;
block|}
comment|/**      * Adds the given keyword at the end of the chain.      * E.g., "A> B> C" + "D" -> "A> B> C> D".      */
DECL|method|addAtEnd (String keyword)
specifier|private
name|void
name|addAtEnd
parameter_list|(
name|String
name|keyword
parameter_list|)
block|{
name|addAtEnd
argument_list|(
operator|new
name|Keyword
argument_list|(
name|keyword
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a text representation of the subchain starting at this item.      * E.g., calling {@link #getSubchainAsString(Character)} on the node "B" in "A> B> C" returns "B> C".      */
DECL|method|getSubchainAsString (Character hierarchicalDelimiter)
specifier|private
name|String
name|getSubchainAsString
parameter_list|(
name|Character
name|hierarchicalDelimiter
parameter_list|)
block|{
return|return
name|keyword
operator|+
name|getChild
argument_list|()
operator|.
name|map
argument_list|(
name|child
lambda|->
literal|" "
operator|+
name|hierarchicalDelimiter
operator|+
literal|" "
operator|+
name|child
operator|.
name|getSubchainAsString
argument_list|(
name|hierarchicalDelimiter
argument_list|)
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
return|;
block|}
comment|/**      * Gets the keyword of this node in the chain.      */
DECL|method|get ()
specifier|public
name|String
name|get
parameter_list|()
block|{
return|return
name|keyword
return|;
block|}
comment|/**      * Returns a text representation of the path from the root to this item.      * E.g., calling {@link #getPathFromRootAsString(Character)} on the node "B" in "A> B> C" returns "A> B".      */
DECL|method|getPathFromRootAsString (Character hierarchicalDelimiter)
specifier|public
name|String
name|getPathFromRootAsString
parameter_list|(
name|Character
name|hierarchicalDelimiter
parameter_list|)
block|{
return|return
name|getParent
argument_list|()
operator|.
name|map
argument_list|(
name|parent
lambda|->
name|parent
operator|.
name|getPathFromRootAsString
argument_list|(
name|hierarchicalDelimiter
argument_list|)
operator|+
literal|" "
operator|+
name|hierarchicalDelimiter
operator|+
literal|" "
argument_list|)
operator|.
name|orElse
argument_list|(
literal|""
argument_list|)
operator|+
name|keyword
return|;
block|}
comment|/**      * Returns all nodes in this chain as separate keywords.      * E.g, for "A> B> C" we get {"A", "B", "C"}.      */
DECL|method|flatten ()
specifier|public
name|Set
argument_list|<
name|Keyword
argument_list|>
name|flatten
parameter_list|()
block|{
return|return
name|Stream
operator|.
name|concat
argument_list|(
name|Stream
operator|.
name|of
argument_list|(
name|this
argument_list|)
argument_list|,
name|OptionalUtil
operator|.
name|toStream
argument_list|(
name|getChild
argument_list|()
argument_list|)
operator|.
name|flatMap
argument_list|(
name|child
lambda|->
name|child
operator|.
name|flatten
argument_list|()
operator|.
name|stream
argument_list|()
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toSet
argument_list|()
argument_list|)
return|;
block|}
comment|/**      * Returns all subchains starting at this node.      * E.g., for the chain "A> B> C" the subchains {"A", "A> B", "A> B> C"} are returned.      */
DECL|method|getAllSubchainsAsString (Character hierarchicalDelimiter)
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getAllSubchainsAsString
parameter_list|(
name|Character
name|hierarchicalDelimiter
parameter_list|)
block|{
return|return
name|flatten
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|subchain
lambda|->
name|subchain
operator|.
name|getPathFromRootAsString
argument_list|(
name|hierarchicalDelimiter
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toSet
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

