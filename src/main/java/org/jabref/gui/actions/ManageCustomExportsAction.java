begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
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
name|exporter
operator|.
name|ExportCustomizationDialogView
import|;
end_import

begin_class
DECL|class|ManageCustomExportsAction
specifier|public
class|class
name|ManageCustomExportsAction
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
name|ExportCustomizationDialogView
argument_list|()
operator|.
name|show
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

