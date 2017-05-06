begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.fieldeditors
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|fieldeditors
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|util
operator|.
name|StringConverter
import|;
end_import

begin_class
DECL|class|OptionEditorViewModel
specifier|public
specifier|abstract
class|class
name|OptionEditorViewModel
parameter_list|<
name|T
parameter_list|>
extends|extends
name|AbstractEditorViewModel
block|{
DECL|method|getStringConverter ()
specifier|public
specifier|abstract
name|StringConverter
argument_list|<
name|T
argument_list|>
name|getStringConverter
parameter_list|()
function_decl|;
DECL|method|getItems ()
specifier|public
specifier|abstract
name|List
argument_list|<
name|T
argument_list|>
name|getItems
parameter_list|()
function_decl|;
DECL|method|convertToDisplayText (T object)
specifier|public
specifier|abstract
name|String
name|convertToDisplayText
parameter_list|(
name|T
name|object
parameter_list|)
function_decl|;
block|}
end_class

end_unit

