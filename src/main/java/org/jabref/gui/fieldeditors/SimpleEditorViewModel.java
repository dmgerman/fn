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
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|autocompleter
operator|.
name|AppendWordsStrategy
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

begin_class
DECL|class|SimpleEditorViewModel
specifier|public
class|class
name|SimpleEditorViewModel
extends|extends
name|AbstractEditorViewModel
block|{
DECL|method|SimpleEditorViewModel (String fieldName, AutoCompleteSuggestionProvider<?> suggestionProvider)
specifier|public
name|SimpleEditorViewModel
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|)
block|{
name|super
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|)
expr_stmt|;
block|}
DECL|method|getAutoCompletionStrategy ()
specifier|public
name|AutoCompletionStrategy
name|getAutoCompletionStrategy
parameter_list|()
block|{
return|return
operator|new
name|AppendWordsStrategy
argument_list|()
return|;
block|}
block|}
end_class

end_unit

