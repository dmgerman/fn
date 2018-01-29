begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.util
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|util
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|AbstractView
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
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|ControlHelper
specifier|public
class|class
name|ControlHelper
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|ControlHelper
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * Loads the FXML file associated to the passed control.      * The FMXL file should have the same name as the control with ending ".fxml" appended      */
DECL|method|loadFXMLForControl (Parent control)
specifier|public
specifier|static
name|void
name|loadFXMLForControl
parameter_list|(
name|Parent
name|control
parameter_list|)
block|{
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
init|=
name|control
operator|.
name|getClass
argument_list|()
decl_stmt|;
name|String
name|clazzName
init|=
name|clazz
operator|.
name|getSimpleName
argument_list|()
decl_stmt|;
name|FXMLLoader
name|fxmlLoader
init|=
operator|new
name|FXMLLoader
argument_list|(
name|clazz
operator|.
name|getResource
argument_list|(
name|clazzName
operator|+
literal|".fxml"
argument_list|)
argument_list|,
name|Localization
operator|.
name|getMessages
argument_list|()
argument_list|)
decl_stmt|;
name|fxmlLoader
operator|.
name|setController
argument_list|(
name|control
argument_list|)
expr_stmt|;
name|fxmlLoader
operator|.
name|setRoot
argument_list|(
name|control
argument_list|)
expr_stmt|;
try|try
block|{
name|fxmlLoader
operator|.
name|load
argument_list|()
expr_stmt|;
comment|// Add our base css file
name|control
operator|.
name|getStylesheets
argument_list|()
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|AbstractView
operator|.
name|class
operator|.
name|getResource
argument_list|(
literal|"Main.css"
argument_list|)
operator|.
name|toExternalForm
argument_list|()
argument_list|)
expr_stmt|;
comment|// Add language resource
block|}
catch|catch
parameter_list|(
name|IOException
name|exception
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem loading fxml for control"
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

