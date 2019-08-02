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
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|BooleanProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleBooleanProperty
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
name|DialogService
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
name|desktop
operator|.
name|JabRefDesktop
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
name|jabref
operator|.
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|Field
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
name|strings
operator|.
name|StringUtil
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_class
DECL|class|UrlEditorViewModel
specifier|public
class|class
name|UrlEditorViewModel
extends|extends
name|AbstractEditorViewModel
block|{
DECL|field|dialogService
specifier|private
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|validUrlIsNotPresent
specifier|private
name|BooleanProperty
name|validUrlIsNotPresent
init|=
operator|new
name|SimpleBooleanProperty
argument_list|(
literal|true
argument_list|)
decl_stmt|;
DECL|method|UrlEditorViewModel (Field field, AutoCompleteSuggestionProvider<?> suggestionProvider, DialogService dialogService, FieldCheckers fieldCheckers)
specifier|public
name|UrlEditorViewModel
parameter_list|(
name|Field
name|field
parameter_list|,
name|AutoCompleteSuggestionProvider
argument_list|<
name|?
argument_list|>
name|suggestionProvider
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|FieldCheckers
name|fieldCheckers
parameter_list|)
block|{
name|super
argument_list|(
name|field
argument_list|,
name|suggestionProvider
argument_list|,
name|fieldCheckers
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|validUrlIsNotPresent
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|text
argument_list|,
name|input
lambda|->
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|input
argument_list|)
operator|||
operator|!
name|URLUtil
operator|.
name|isURL
argument_list|(
name|input
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|isValidUrlIsNotPresent ()
specifier|public
name|boolean
name|isValidUrlIsNotPresent
parameter_list|()
block|{
return|return
name|validUrlIsNotPresent
operator|.
name|get
argument_list|()
return|;
block|}
DECL|method|validUrlIsNotPresentProperty ()
specifier|public
name|BooleanProperty
name|validUrlIsNotPresentProperty
parameter_list|()
block|{
return|return
name|validUrlIsNotPresent
return|;
block|}
DECL|method|openExternalLink ()
specifier|public
name|void
name|openExternalLink
parameter_list|()
block|{
if|if
condition|(
name|StringUtil
operator|.
name|isBlank
argument_list|(
name|text
operator|.
name|get
argument_list|()
argument_list|)
condition|)
block|{
return|return;
block|}
try|try
block|{
name|JabRefDesktop
operator|.
name|openBrowser
argument_list|(
name|text
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Unable to open link."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

