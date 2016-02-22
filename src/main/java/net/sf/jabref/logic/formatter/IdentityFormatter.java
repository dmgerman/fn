begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.logic.formatter
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|formatter
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

begin_comment
comment|/**  * It may seem useless, but is needed as a fallback option  */
end_comment

begin_class
DECL|class|IdentityFormatter
specifier|public
class|class
name|IdentityFormatter
implements|implements
name|Formatter
block|{
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|"IdentityFormatter"
return|;
block|}
annotation|@
name|Override
DECL|method|getKey ()
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|getName
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|format (String value)
specifier|public
name|String
name|format
parameter_list|(
name|String
name|value
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|value
argument_list|)
expr_stmt|;
return|return
name|value
return|;
block|}
block|}
end_class

end_unit

