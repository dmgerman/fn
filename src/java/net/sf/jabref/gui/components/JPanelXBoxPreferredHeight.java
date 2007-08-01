begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.components
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|components
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Dimension
import|;
end_import

begin_class
DECL|class|JPanelXBoxPreferredHeight
specifier|public
class|class
name|JPanelXBoxPreferredHeight
extends|extends
name|JPanelXBox
block|{
DECL|method|JPanelXBoxPreferredHeight ()
specifier|public
name|JPanelXBoxPreferredHeight
parameter_list|()
block|{
comment|// nothing special
block|}
DECL|method|JPanelXBoxPreferredHeight (Component c)
specifier|public
name|JPanelXBoxPreferredHeight
parameter_list|(
name|Component
name|c
parameter_list|)
block|{
name|add
argument_list|(
name|c
argument_list|)
expr_stmt|;
block|}
DECL|method|getMaximumSize ()
specifier|public
name|Dimension
name|getMaximumSize
parameter_list|()
block|{
name|Dimension
name|pref
init|=
name|getPreferredSize
argument_list|()
decl_stmt|;
name|pref
operator|.
name|width
operator|=
name|super
operator|.
name|getMaximumSize
argument_list|()
operator|.
name|width
expr_stmt|;
return|return
name|pref
return|;
block|}
block|}
end_class

end_unit

