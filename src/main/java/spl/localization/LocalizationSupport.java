begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|spl.localization
package|package
name|spl
operator|.
name|localization
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
name|Globals
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: Christoph Arbeit  * Date: 08.09.2010  * Time: 09:56:31  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|LocalizationSupport
specifier|public
class|class
name|LocalizationSupport
block|{
DECL|method|message (String key)
specifier|public
specifier|static
name|String
name|message
parameter_list|(
name|String
name|key
parameter_list|)
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
name|key
argument_list|)
return|;
block|}
block|}
end_class

end_unit

