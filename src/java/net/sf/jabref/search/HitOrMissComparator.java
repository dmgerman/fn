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
name|java
operator|.
name|util
operator|.
name|Comparator
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
comment|/**  * This Comparator compares two objects based on whether none, one of them, or both  * match a given Matcher. It is used to "float" group and search hits in the main table.  */
end_comment

begin_class
DECL|class|HitOrMissComparator
specifier|public
class|class
name|HitOrMissComparator
implements|implements
name|Comparator
block|{
DECL|field|hitOrMiss
specifier|private
name|Matcher
name|hitOrMiss
decl_stmt|;
DECL|method|HitOrMissComparator (Matcher hitOrMiss)
specifier|public
name|HitOrMissComparator
parameter_list|(
name|Matcher
name|hitOrMiss
parameter_list|)
block|{
name|this
operator|.
name|hitOrMiss
operator|=
name|hitOrMiss
expr_stmt|;
block|}
DECL|method|compare (Object o1, Object o2)
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
if|if
condition|(
name|hitOrMiss
operator|==
literal|null
condition|)
return|return
literal|0
return|;
name|boolean
name|hit1
init|=
name|hitOrMiss
operator|.
name|matches
argument_list|(
name|o1
argument_list|)
decl_stmt|,
name|hit2
init|=
name|hitOrMiss
operator|.
name|matches
argument_list|(
name|o2
argument_list|)
decl_stmt|;
if|if
condition|(
name|hit1
operator|==
name|hit2
condition|)
return|return
literal|0
return|;
else|else
return|return
name|hit1
condition|?
operator|-
literal|1
else|:
literal|1
return|;
block|}
block|}
end_class

end_unit

