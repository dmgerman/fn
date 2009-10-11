begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.export.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|format
package|;
end_package

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|export
operator|.
name|layout
operator|.
name|ParamLayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Layout formatter that puts in a default value (given as argument) if the field is empty.  * Empty means null or an empty string.  */
end_comment

begin_class
DECL|class|Default
specifier|public
class|class
name|Default
implements|implements
name|ParamLayoutFormatter
block|{
DECL|field|defValue
name|String
name|defValue
init|=
literal|""
decl_stmt|;
DECL|method|setArgument (String arg)
specifier|public
name|void
name|setArgument
parameter_list|(
name|String
name|arg
parameter_list|)
block|{
name|this
operator|.
name|defValue
operator|=
name|arg
expr_stmt|;
block|}
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
return|return
name|fieldText
operator|!=
literal|null
operator|&&
operator|(
name|fieldText
operator|.
name|length
argument_list|()
operator|>
literal|0
operator|)
condition|?
name|fieldText
else|:
name|defValue
return|;
block|}
block|}
end_class

end_unit

