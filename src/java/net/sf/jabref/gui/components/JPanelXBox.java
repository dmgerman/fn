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
name|javax
operator|.
name|swing
operator|.
name|BoxLayout
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_comment
comment|/**  * A JPanel that by default uses a BoxLayout.X_AXIS  */
end_comment

begin_class
DECL|class|JPanelXBox
specifier|public
class|class
name|JPanelXBox
extends|extends
name|JPanel
block|{
comment|/** Create the panel and set BoxLayout.X_AXIS */
DECL|method|JPanelXBox ()
specifier|public
name|JPanelXBox
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BoxLayout
argument_list|(
name|this
argument_list|,
name|BoxLayout
operator|.
name|X_AXIS
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|JPanelXBox (Component comp)
specifier|public
name|JPanelXBox
parameter_list|(
name|Component
name|comp
parameter_list|)
block|{
name|this
argument_list|()
expr_stmt|;
name|add
argument_list|(
name|comp
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

