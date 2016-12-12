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
name|javafx
operator|.
name|fxml
operator|.
name|FXML
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|fxml
operator|.
name|FXMLLoader
import|;
end_import

begin_class
DECL|class|AbstractController
specifier|public
class|class
name|AbstractController
parameter_list|<
name|T
extends|extends
name|AbstractViewModel
parameter_list|>
block|{
DECL|field|viewModel
annotation|@
name|FXML
specifier|protected
name|T
name|viewModel
decl_stmt|;
comment|/**      * Gets the associated view model.      *      * Without this method the {@link FXMLLoader} is not able to resolve references in the fxml file of the form      * text="${controller.viewModel.someProperty}"      */
DECL|method|getViewModel ()
specifier|public
name|T
name|getViewModel
parameter_list|()
block|{
return|return
name|viewModel
return|;
block|}
block|}
end_class

end_unit

