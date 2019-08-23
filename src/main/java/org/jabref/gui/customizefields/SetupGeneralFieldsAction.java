begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.customizefields
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|customizefields
package|;
end_package

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
operator|.
name|SimpleCommand
import|;
end_import

begin_class
DECL|class|SetupGeneralFieldsAction
specifier|public
class|class
name|SetupGeneralFieldsAction
extends|extends
name|SimpleCommand
block|{
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
operator|new
name|CustomizeGeneralFieldsDialogView
argument_list|()
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

