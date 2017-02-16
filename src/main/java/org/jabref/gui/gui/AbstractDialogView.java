begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|gui
package|;
end_package

begin_class
DECL|class|AbstractDialogView
specifier|public
specifier|abstract
class|class
name|AbstractDialogView
extends|extends
name|AbstractView
block|{
DECL|method|AbstractDialogView ()
specifier|public
name|AbstractDialogView
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
block|}
DECL|method|show ()
specifier|public
specifier|abstract
name|void
name|show
parameter_list|()
function_decl|;
block|}
end_class

end_unit

