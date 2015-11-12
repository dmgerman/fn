begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.model.entry
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
package|;
end_package

begin_class
DECL|class|CompareUtil
specifier|public
class|class
name|CompareUtil
block|{
comment|/**      * Static equals that can also return the right result when one of the objects is null.      *      * @param one The object whose equals method is called if the first is not null.      * @param two The object passed to the first one if the first is not null.      * @return<code>one == null ? two == null : one.equals(two);</code>      */
DECL|method|equals (Object one, Object two)
specifier|public
specifier|static
name|boolean
name|equals
parameter_list|(
name|Object
name|one
parameter_list|,
name|Object
name|two
parameter_list|)
block|{
return|return
name|one
operator|==
literal|null
condition|?
name|two
operator|==
literal|null
else|:
name|one
operator|.
name|equals
argument_list|(
name|two
argument_list|)
return|;
block|}
block|}
end_class

end_unit

