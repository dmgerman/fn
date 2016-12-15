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
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Parent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Stage
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|FXMLView
import|;
end_import

begin_class
DECL|class|AbstractDialogView
specifier|public
specifier|abstract
class|class
name|AbstractDialogView
extends|extends
name|FXMLView
block|{
DECL|method|AbstractDialogView ()
specifier|public
name|AbstractDialogView
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
comment|// Set resource bundle to internal localizations
name|bundle
operator|=
name|Localization
operator|.
name|getMessages
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getView ()
specifier|public
name|Parent
name|getView
parameter_list|()
block|{
name|Parent
name|view
init|=
name|super
operator|.
name|getView
argument_list|()
decl_stmt|;
comment|// Notify controller about the stage, where it is displayed
name|view
operator|.
name|sceneProperty
argument_list|()
operator|.
name|addListener
argument_list|(
parameter_list|(
name|observable
parameter_list|,
name|oldValue
parameter_list|,
name|newValue
parameter_list|)
lambda|->
name|getController
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|controller
lambda|->
name|controller
operator|.
name|setStage
argument_list|(
operator|(
name|Stage
operator|)
name|newValue
operator|.
name|getWindow
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|view
return|;
block|}
DECL|method|getController ()
specifier|private
name|Optional
argument_list|<
name|AbstractController
argument_list|>
name|getController
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|presenterProperty
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|map
argument_list|(
name|presenter
lambda|->
operator|(
name|AbstractController
operator|)
name|presenter
argument_list|)
return|;
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

