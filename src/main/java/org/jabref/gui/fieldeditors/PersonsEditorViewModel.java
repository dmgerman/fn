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
name|Collection
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
name|AppendPersonNamesStrategy
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
name|AutoCompletePreferences
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
name|gui
operator|.
name|autocompleter
operator|.
name|AutoCompletionStrategy
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
name|PersonNameStringConverter
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|Author
import|;
end_import

begin_import
import|import
name|org
operator|.
name|controlsfx
operator|.
name|control
operator|.
name|textfield
operator|.
name|AutoCompletionBinding
import|;
end_import

begin_class
DECL|class|PersonsEditorViewModel
specifier|public
class|class
name|PersonsEditorViewModel
extends|extends
name|AbstractEditorViewModel
block|{
DECL|field|preferences
specifier|private
specifier|final
name|AutoCompletePreferences
name|preferences
decl_stmt|;
DECL|method|PersonsEditorViewModel (String fieldName, AutoCompleteSuggestionProvider<?> suggestionProvider, AutoCompletePreferences preferences)
specifier|public
name|PersonsEditorViewModel
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
name|AutoCompletePreferences
name|preferences
parameter_list|)
block|{
name|super
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|)
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
block|}
DECL|method|getAutoCompletionConverter ()
specifier|public
name|StringConverter
argument_list|<
name|Author
argument_list|>
name|getAutoCompletionConverter
parameter_list|()
block|{
return|return
operator|new
name|PersonNameStringConverter
argument_list|(
name|preferences
argument_list|)
return|;
block|}
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unchecked"
argument_list|)
DECL|method|complete (AutoCompletionBinding.ISuggestionRequest request)
specifier|public
name|Collection
argument_list|<
name|Author
argument_list|>
name|complete
parameter_list|(
name|AutoCompletionBinding
operator|.
name|ISuggestionRequest
name|request
parameter_list|)
block|{
return|return
operator|(
name|Collection
argument_list|<
name|Author
argument_list|>
operator|)
name|super
operator|.
name|complete
argument_list|(
name|request
argument_list|)
return|;
block|}
DECL|method|getAutoCompletionStrategy ()
specifier|public
name|AutoCompletionStrategy
name|getAutoCompletionStrategy
parameter_list|()
block|{
return|return
operator|new
name|AppendPersonNamesStrategy
argument_list|()
return|;
block|}
block|}
end_class

end_unit
