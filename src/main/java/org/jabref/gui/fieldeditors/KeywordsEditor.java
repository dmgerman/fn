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
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|KeywordsEditor
specifier|public
class|class
name|KeywordsEditor
extends|extends
name|SimpleEditor
implements|implements
name|FieldEditorFX
block|{
DECL|method|KeywordsEditor (String fieldName, AutoCompleteSuggestionProvider<?> suggestionProvider, FieldCheckers fieldCheckers, JabRefPreferences preferences)
specifier|public
name|KeywordsEditor
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
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|super
argument_list|(
name|fieldName
argument_list|,
name|suggestionProvider
argument_list|,
name|fieldCheckers
argument_list|,
name|preferences
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getWeight ()
specifier|public
name|double
name|getWeight
parameter_list|()
block|{
return|return
literal|2
return|;
block|}
block|}
end_class

end_unit
