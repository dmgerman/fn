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
name|ArrayList
import|;
end_import

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

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompleteSuggestionProvider
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
name|integrity
operator|.
name|FieldCheckers
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|collect
operator|.
name|BiMap
import|;
end_import

begin_comment
comment|/**  * View model for a field editor that shows various options backed by a map.  */
end_comment

begin_class
DECL|class|MapBasedEditorViewModel
specifier|public
specifier|abstract
class|class
name|MapBasedEditorViewModel
parameter_list|<
name|T
parameter_list|>
extends|extends
name|OptionEditorViewModel
argument_list|<
name|T
argument_list|>
block|{
DECL|method|MapBasedEditorViewModel (String fieldName, AutoCompleteSuggestionProvider<?> suggestionProvider, FieldCheckers fieldCheckers)
specifier|public
name|MapBasedEditorViewModel
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|,
name|FieldCheckers
name|fieldCheckers
parameter_list|)
block|{
name|super
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|fieldCheckers
argument_list|)
expr_stmt|;
block|}
DECL|method|getItemMap ()
specifier|protected
specifier|abstract
name|BiMap
argument_list|<
name|String
argument_list|,
name|T
argument_list|>
name|getItemMap
parameter_list|()
function_decl|;
annotation|@
name|Override
DECL|method|getStringConverter ()
specifier|public
name|StringConverter
argument_list|<
name|T
argument_list|>
name|getStringConverter
parameter_list|()
block|{
return|return
operator|new
name|StringConverter
argument_list|<
name|T
argument_list|>
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|String
name|toString
parameter_list|(
name|T
name|object
parameter_list|)
block|{
if|if
condition|(
name|object
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
name|getItemMap
argument_list|()
operator|.
name|inverse
argument_list|()
operator|.
name|getOrDefault
argument_list|(
name|object
argument_list|,
literal|""
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|T
name|fromString
parameter_list|(
name|String
name|string
parameter_list|)
block|{
if|if
condition|(
name|string
operator|==
literal|null
condition|)
block|{
return|return
literal|null
return|;
block|}
else|else
block|{
return|return
name|getItemMap
argument_list|()
operator|.
name|getOrDefault
argument_list|(
name|string
argument_list|,
literal|null
argument_list|)
return|;
block|}
block|}
block|}
return|;
block|}
annotation|@
name|Override
DECL|method|getItems ()
specifier|public
name|List
argument_list|<
name|T
argument_list|>
name|getItems
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|getItemMap
argument_list|()
operator|.
name|values
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

