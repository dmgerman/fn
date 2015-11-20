begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.exporter.layout.format
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|exporter
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
name|exporter
operator|.
name|layout
operator|.
name|LayoutFormatter
import|;
end_import

begin_comment
comment|/**  * Remove brackets formatter.  */
end_comment

begin_class
DECL|class|RemoveBracketsAddComma
specifier|public
class|class
name|RemoveBracketsAddComma
implements|implements
name|LayoutFormatter
block|{
annotation|@
name|Override
DECL|method|format (String fieldText)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|fieldText
parameter_list|)
block|{
name|StringBuilder
name|builder
init|=
operator|new
name|StringBuilder
argument_list|(
name|fieldText
operator|.
name|length
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|char
name|c
range|:
name|fieldText
operator|.
name|toCharArray
argument_list|()
control|)
block|{
if|if
condition|(
name|c
operator|!=
literal|'{'
operator|&&
name|c
operator|!=
literal|'}'
condition|)
block|{
name|builder
operator|.
name|append
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|c
operator|==
literal|'}'
condition|)
block|{
name|builder
operator|.
name|append
argument_list|(
literal|','
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|builder
operator|.
name|toString
argument_list|()
return|;
block|}
block|}
end_class

end_unit

