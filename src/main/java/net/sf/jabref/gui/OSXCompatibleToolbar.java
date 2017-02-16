begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JToolBar
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|util
operator|.
name|OS
import|;
end_import

begin_class
DECL|class|OSXCompatibleToolbar
specifier|public
class|class
name|OSXCompatibleToolbar
extends|extends
name|JToolBar
block|{
DECL|method|OSXCompatibleToolbar ()
specifier|public
name|OSXCompatibleToolbar
parameter_list|()
block|{     }
DECL|method|OSXCompatibleToolbar (int orientation)
specifier|public
name|OSXCompatibleToolbar
parameter_list|(
name|int
name|orientation
parameter_list|)
block|{
name|super
argument_list|(
name|orientation
argument_list|)
expr_stmt|;
block|}
DECL|method|OSXCompatibleToolbar (String name)
specifier|public
name|OSXCompatibleToolbar
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
DECL|method|OSXCompatibleToolbar (String name, int orientation)
specifier|public
name|OSXCompatibleToolbar
parameter_list|(
name|String
name|name
parameter_list|,
name|int
name|orientation
parameter_list|)
block|{
name|super
argument_list|(
name|name
argument_list|,
name|orientation
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|add (Component a)
specifier|public
name|Component
name|add
parameter_list|(
name|Component
name|a
parameter_list|)
block|{
if|if
condition|(
name|a
operator|instanceof
name|JButton
condition|)
block|{
name|JButton
name|button
init|=
operator|(
name|JButton
operator|)
name|a
decl_stmt|;
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|button
operator|.
name|putClientProperty
argument_list|(
literal|"JButton.buttonType"
argument_list|,
literal|"toolbar"
argument_list|)
expr_stmt|;
block|}
block|}
return|return
name|super
operator|.
name|add
argument_list|(
name|a
argument_list|)
return|;
block|}
block|}
end_class

end_unit

