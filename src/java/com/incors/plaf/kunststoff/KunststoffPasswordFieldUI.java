begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|com.incors.plaf.kunststoff
package|package
name|com
operator|.
name|incors
operator|.
name|plaf
operator|.
name|kunststoff
package|;
end_package

begin_comment
comment|/*  * This code was developed by INCORS GmbH (www.incors.com).  * It is published under the terms of the GNU Lesser General Public License.  */
end_comment

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|plaf
operator|.
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|*
import|;
end_import

begin_class
DECL|class|KunststoffPasswordFieldUI
specifier|public
class|class
name|KunststoffPasswordFieldUI
extends|extends
name|KunststoffTextFieldUI
block|{
DECL|method|KunststoffPasswordFieldUI (JComponent c)
name|KunststoffPasswordFieldUI
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
name|super
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|createUI (JComponent c)
specifier|public
specifier|static
name|ComponentUI
name|createUI
parameter_list|(
name|JComponent
name|c
parameter_list|)
block|{
return|return
operator|new
name|KunststoffPasswordFieldUI
argument_list|(
name|c
argument_list|)
return|;
block|}
DECL|method|getPropertyPrefix ()
specifier|protected
name|String
name|getPropertyPrefix
parameter_list|()
block|{
return|return
literal|"PasswordField"
return|;
block|}
DECL|method|create (Element elem)
specifier|public
name|View
name|create
parameter_list|(
name|Element
name|elem
parameter_list|)
block|{
return|return
operator|new
name|PasswordView
argument_list|(
name|elem
argument_list|)
return|;
block|}
block|}
end_class

end_unit

