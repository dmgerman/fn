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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_comment
comment|/**  * A JPanel that by default uses a BoxLayout.Y_AXIS  */
end_comment

begin_class
DECL|class|JPanelYBox
specifier|public
class|class
name|JPanelYBox
extends|extends
name|JPanel
block|{
comment|/** Create the panel and set BoxLayout.Y_AXIS */
DECL|method|JPanelYBox ()
specifier|public
name|JPanelYBox
parameter_list|()
block|{
name|setLayout
argument_list|(
operator|new
name|BoxLayout
argument_list|(
name|this
argument_list|,
name|BoxLayout
operator|.
name|Y_AXIS
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

