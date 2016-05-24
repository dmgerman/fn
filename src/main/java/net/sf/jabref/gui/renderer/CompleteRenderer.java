begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.renderer
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|renderer
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Color
import|;
end_import

begin_class
DECL|class|CompleteRenderer
specifier|public
class|class
name|CompleteRenderer
extends|extends
name|GeneralRenderer
block|{
DECL|method|CompleteRenderer (Color color)
specifier|public
name|CompleteRenderer
parameter_list|(
name|Color
name|color
parameter_list|)
block|{
name|super
argument_list|(
name|color
argument_list|)
expr_stmt|;
block|}
DECL|method|setNumber (int number)
specifier|public
name|void
name|setNumber
parameter_list|(
name|int
name|number
parameter_list|)
block|{
name|super
operator|.
name|setValue
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|number
operator|+
literal|1
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setValue (Object value)
specifier|protected
name|void
name|setValue
parameter_list|(
name|Object
name|value
parameter_list|)
block|{
comment|// do not support normal behaviour
block|}
block|}
end_class

end_unit

