begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preferences
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
name|java
operator|.
name|util
operator|.
name|stream
operator|.
name|Collectors
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
name|ListProperty
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
name|ObjectProperty
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
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|SimpleListProperty
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
name|SimpleObjectProperty
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|collections
operator|.
name|FXCollections
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
name|entry
operator|.
name|field
operator|.
name|FieldFactory
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

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|FunctionBasedValidator
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|ValidationMessage
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|validation
operator|.
name|ValidationStatus
import|;
end_import

begin_class
DECL|class|XmpPrivacyTabViewModel
specifier|public
class|class
name|XmpPrivacyTabViewModel
implements|implements
name|PreferenceTabViewModel
block|{
DECL|field|xmpFilterEnabledProperty
specifier|private
specifier|final
name|BooleanProperty
name|xmpFilterEnabledProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|xmpFilterListProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|Field
argument_list|>
name|xmpFilterListProperty
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|availableFieldsProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|Field
argument_list|>
name|availableFieldsProperty
init|=
operator|new
name|SimpleListProperty
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|addFieldProperty
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|Field
argument_list|>
name|addFieldProperty
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|xmpFilterListValidator
specifier|private
name|FunctionBasedValidator
name|xmpFilterListValidator
decl_stmt|;
DECL|method|XmpPrivacyTabViewModel (DialogService dialogService, JabRefPreferences preferences)
name|XmpPrivacyTabViewModel
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|xmpFilterListValidator
operator|=
operator|new
name|FunctionBasedValidator
argument_list|<>
argument_list|(
name|xmpFilterListProperty
argument_list|,
name|input
lambda|->
name|input
operator|.
name|size
argument_list|()
operator|>
literal|0
argument_list|,
name|ValidationMessage
operator|.
name|error
argument_list|(
name|String
operator|.
name|format
argument_list|(
literal|"%s> %s %n %n %s"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"XMP-metadata"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Filter List"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"List must not be empty."
argument_list|)
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|xmpFilterEnabledProperty
operator|.
name|setValue
argument_list|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_XMP_PRIVACY_FILTER
argument_list|)
argument_list|)
expr_stmt|;
name|xmpFilterListProperty
operator|.
name|clear
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|Field
argument_list|>
name|xmpFilters
init|=
name|preferences
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|XMP_PRIVACY_FILTERS
argument_list|)
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|FieldFactory
operator|::
name|parseField
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
decl_stmt|;
name|xmpFilterListProperty
operator|.
name|addAll
argument_list|(
name|xmpFilters
argument_list|)
expr_stmt|;
name|availableFieldsProperty
operator|.
name|clear
argument_list|()
expr_stmt|;
name|availableFieldsProperty
operator|.
name|addAll
argument_list|(
name|FieldFactory
operator|.
name|getCommonFields
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_XMP_PRIVACY_FILTER
argument_list|,
name|xmpFilterEnabledProperty
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putStringList
argument_list|(
name|JabRefPreferences
operator|.
name|XMP_PRIVACY_FILTERS
argument_list|,
name|xmpFilterListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Field
operator|::
name|getName
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|addField ()
specifier|public
name|void
name|addField
parameter_list|()
block|{
if|if
condition|(
name|addFieldProperty
operator|.
name|getValue
argument_list|()
operator|==
literal|null
condition|)
block|{
return|return;
block|}
if|if
condition|(
name|xmpFilterListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|item
lambda|->
name|item
operator|.
name|equals
argument_list|(
name|addFieldProperty
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
operator|.
name|findAny
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|xmpFilterListProperty
operator|.
name|add
argument_list|(
name|addFieldProperty
operator|.
name|getValue
argument_list|()
argument_list|)
block|;
name|addFieldProperty
operator|.
name|setValue
argument_list|(
literal|null
argument_list|)
empty_stmt|;
block|}
block|}
end_class

begin_function
DECL|method|removeFilter (Field filter)
specifier|public
name|void
name|removeFilter
parameter_list|(
name|Field
name|filter
parameter_list|)
block|{
name|xmpFilterListProperty
operator|.
name|remove
argument_list|(
name|filter
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|xmpFilterListValidationStatus ()
specifier|public
name|ValidationStatus
name|xmpFilterListValidationStatus
parameter_list|()
block|{
return|return
name|xmpFilterListValidator
operator|.
name|getValidationStatus
argument_list|()
return|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
name|ValidationStatus
name|validationStatus
init|=
name|xmpFilterListValidationStatus
argument_list|()
decl_stmt|;
if|if
condition|(
name|xmpFilterEnabledProperty
operator|.
name|getValue
argument_list|()
operator|&&
operator|!
name|validationStatus
operator|.
name|isValid
argument_list|()
condition|)
block|{
name|validationStatus
operator|.
name|getHighestMessage
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|message
lambda|->
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|message
operator|.
name|getMessage
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|getRestartWarnings ()
specifier|public
name|List
argument_list|<
name|String
argument_list|>
name|getRestartWarnings
parameter_list|()
block|{
return|return
operator|new
name|ArrayList
argument_list|<>
argument_list|()
return|;
block|}
end_function

begin_function
DECL|method|xmpFilterEnabledProperty ()
specifier|public
name|BooleanProperty
name|xmpFilterEnabledProperty
parameter_list|()
block|{
return|return
name|xmpFilterEnabledProperty
return|;
block|}
end_function

begin_function
DECL|method|filterListProperty ()
specifier|public
name|ListProperty
argument_list|<
name|Field
argument_list|>
name|filterListProperty
parameter_list|()
block|{
return|return
name|xmpFilterListProperty
return|;
block|}
end_function

begin_function
DECL|method|availableFieldsProperty ()
specifier|public
name|ListProperty
argument_list|<
name|Field
argument_list|>
name|availableFieldsProperty
parameter_list|()
block|{
return|return
name|availableFieldsProperty
return|;
block|}
end_function

begin_function
DECL|method|addFieldNameProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|Field
argument_list|>
name|addFieldNameProperty
parameter_list|()
block|{
return|return
name|addFieldProperty
return|;
block|}
end_function

unit|}
end_unit

