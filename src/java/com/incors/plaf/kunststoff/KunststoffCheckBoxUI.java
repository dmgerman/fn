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
name|plaf
operator|.
name|metal
operator|.
name|*
import|;
end_import

begin_comment
comment|/*  * The only difference to the MetalCheckBoxUI is the icon, which has a gradient.  */
end_comment

begin_class
DECL|class|KunststoffCheckBoxUI
specifier|public
class|class
name|KunststoffCheckBoxUI
extends|extends
name|MetalCheckBoxUI
block|{
DECL|field|checkBoxUI
specifier|private
specifier|final
specifier|static
name|KunststoffCheckBoxUI
name|checkBoxUI
init|=
operator|new
name|KunststoffCheckBoxUI
argument_list|()
decl_stmt|;
DECL|method|KunststoffCheckBoxUI ()
specifier|public
name|KunststoffCheckBoxUI
parameter_list|()
block|{
name|icon
operator|=
operator|new
name|KunststoffCheckBoxIcon
argument_list|()
expr_stmt|;
block|}
DECL|method|createUI (JComponent b)
specifier|public
specifier|static
name|ComponentUI
name|createUI
parameter_list|(
name|JComponent
name|b
parameter_list|)
block|{
return|return
name|checkBoxUI
return|;
block|}
DECL|method|installDefaults (AbstractButton b)
specifier|public
name|void
name|installDefaults
parameter_list|(
name|AbstractButton
name|b
parameter_list|)
block|{
name|super
operator|.
name|installDefaults
argument_list|(
name|b
argument_list|)
expr_stmt|;
name|icon
operator|=
operator|new
name|KunststoffCheckBoxIcon
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

