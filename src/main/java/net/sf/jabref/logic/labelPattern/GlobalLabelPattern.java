begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.labelPattern
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|labelPattern
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_class
DECL|class|GlobalLabelPattern
specifier|public
class|class
name|GlobalLabelPattern
extends|extends
name|AbstractLabelPattern
block|{
DECL|method|getValue (String key)
specifier|public
name|ArrayList
argument_list|<
name|String
argument_list|>
name|getValue
parameter_list|(
name|String
name|key
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|String
argument_list|>
name|result
init|=
name|data
operator|.
name|get
argument_list|(
name|key
argument_list|)
decl_stmt|;
comment|//  Test to see if we found anything
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// check default value
name|result
operator|=
name|getDefaultValue
argument_list|()
expr_stmt|;
if|if
condition|(
name|result
operator|==
literal|null
condition|)
block|{
comment|// we are the "last" to ask
comment|// we don't have anything left
comment|// return the global default pattern
return|return
name|LabelPatternUtil
operator|.
name|DEFAULT_LABELPATTERN
return|;
block|}
block|}
return|return
name|result
return|;
block|}
block|}
end_class

end_unit

