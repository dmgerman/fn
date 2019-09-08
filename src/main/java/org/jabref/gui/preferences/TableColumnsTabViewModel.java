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
name|EnumSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
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
name|Map
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|SelectionModel
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
name|externalfiletype
operator|.
name|ExternalFileType
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
name|externalfiletype
operator|.
name|ExternalFileTypes
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
name|maintable
operator|.
name|ColumnPreferences
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
name|util
operator|.
name|FieldsUtil
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
name|util
operator|.
name|NoSelectionModel
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
name|model
operator|.
name|entry
operator|.
name|field
operator|.
name|InternalField
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
name|SpecialField
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
name|StandardField
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
DECL|class|TableColumnsTabViewModel
specifier|public
class|class
name|TableColumnsTabViewModel
implements|implements
name|PreferenceTabViewModel
block|{
DECL|field|columnsListProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|TableColumnsItemModel
argument_list|>
name|columnsListProperty
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
DECL|field|selectedColumnModelProperty
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|SelectionModel
argument_list|<
name|TableColumnsItemModel
argument_list|>
argument_list|>
name|selectedColumnModelProperty
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|(
operator|new
name|NoSelectionModel
argument_list|<>
argument_list|()
argument_list|)
decl_stmt|;
DECL|field|availableColumnsProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|Field
argument_list|>
name|availableColumnsProperty
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
DECL|field|addColumnProperty
specifier|private
specifier|final
name|ObjectProperty
argument_list|<
name|Field
argument_list|>
name|addColumnProperty
init|=
operator|new
name|SimpleObjectProperty
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|specialFieldsEnabledProperty
specifier|private
specifier|final
name|BooleanProperty
name|specialFieldsEnabledProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|specialFieldsSyncKeywordsProperty
specifier|private
specifier|final
name|BooleanProperty
name|specialFieldsSyncKeywordsProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|specialFieldsSerializeProperty
specifier|private
specifier|final
name|BooleanProperty
name|specialFieldsSerializeProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|showFileColumnProperty
specifier|private
specifier|final
name|BooleanProperty
name|showFileColumnProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|showUrlColumnProperty
specifier|private
specifier|final
name|BooleanProperty
name|showUrlColumnProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|preferUrlProperty
specifier|private
specifier|final
name|BooleanProperty
name|preferUrlProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|preferDoiProperty
specifier|private
specifier|final
name|BooleanProperty
name|preferDoiProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|showEPrintColumnProperty
specifier|private
specifier|final
name|BooleanProperty
name|showEPrintColumnProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|extraFileColumnsEnabledProperty
specifier|private
specifier|final
name|BooleanProperty
name|extraFileColumnsEnabledProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|columnsNotEmptyValidator
specifier|private
name|FunctionBasedValidator
name|columnsNotEmptyValidator
decl_stmt|;
DECL|field|restartWarnings
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|restartWarnings
init|=
operator|new
name|ArrayList
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
DECL|field|columnPreferences
specifier|private
specifier|final
name|ColumnPreferences
name|columnPreferences
decl_stmt|;
DECL|method|TableColumnsTabViewModel (DialogService dialogService, JabRefPreferences preferences)
specifier|public
name|TableColumnsTabViewModel
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
name|this
operator|.
name|columnPreferences
operator|=
name|preferences
operator|.
name|getColumnPreferences
argument_list|()
expr_stmt|;
name|specialFieldsEnabledProperty
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
block|{
if|if
condition|(
name|newValue
condition|)
block|{
name|insertSpecialFieldColumns
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|removeSpecialFieldColumns
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|extraFileColumnsEnabledProperty
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
block|{
if|if
condition|(
name|newValue
condition|)
block|{
name|insertExtraFileColumns
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|removeExtraFileColumns
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|columnsNotEmptyValidator
operator|=
operator|new
name|FunctionBasedValidator
argument_list|<>
argument_list|(
name|columnsListProperty
argument_list|,
name|list
lambda|->
name|list
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
literal|"Entry table columns"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Columns"
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
name|setValues
argument_list|()
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
name|showFileColumnProperty
operator|.
name|setValue
argument_list|(
name|columnPreferences
operator|.
name|showFileColumn
argument_list|()
argument_list|)
expr_stmt|;
name|showUrlColumnProperty
operator|.
name|setValue
argument_list|(
name|columnPreferences
operator|.
name|showUrlColumn
argument_list|()
argument_list|)
expr_stmt|;
name|preferUrlProperty
operator|.
name|setValue
argument_list|(
operator|!
name|columnPreferences
operator|.
name|preferDoiOverUrl
argument_list|()
argument_list|)
expr_stmt|;
name|preferDoiProperty
operator|.
name|setValue
argument_list|(
name|columnPreferences
operator|.
name|preferDoiOverUrl
argument_list|()
argument_list|)
expr_stmt|;
name|showEPrintColumnProperty
operator|.
name|setValue
argument_list|(
name|columnPreferences
operator|.
name|showEprintColumn
argument_list|()
argument_list|)
expr_stmt|;
name|specialFieldsEnabledProperty
operator|.
name|setValue
argument_list|(
name|columnPreferences
operator|.
name|getSpecialFieldsEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|specialFieldsSyncKeywordsProperty
operator|.
name|setValue
argument_list|(
name|columnPreferences
operator|.
name|getAutoSyncSpecialFieldsToKeyWords
argument_list|()
argument_list|)
expr_stmt|;
name|specialFieldsSerializeProperty
operator|.
name|setValue
argument_list|(
name|columnPreferences
operator|.
name|getSerializeSpecialFields
argument_list|()
argument_list|)
expr_stmt|;
name|extraFileColumnsEnabledProperty
operator|.
name|setValue
argument_list|(
name|columnPreferences
operator|.
name|getExtraFileColumnsEnabled
argument_list|()
argument_list|)
expr_stmt|;
name|fillColumnList
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|Field
argument_list|>
name|internalFields
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
name|internalFields
operator|.
name|add
argument_list|(
name|InternalField
operator|.
name|OWNER
argument_list|)
expr_stmt|;
name|internalFields
operator|.
name|add
argument_list|(
name|InternalField
operator|.
name|TIMESTAMP
argument_list|)
expr_stmt|;
name|internalFields
operator|.
name|add
argument_list|(
name|InternalField
operator|.
name|GROUPS
argument_list|)
expr_stmt|;
name|internalFields
operator|.
name|add
argument_list|(
name|InternalField
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|internalFields
operator|.
name|add
argument_list|(
name|InternalField
operator|.
name|TYPE_HEADER
argument_list|)
expr_stmt|;
name|internalFields
operator|.
name|forEach
argument_list|(
name|item
lambda|->
name|availableColumnsProperty
operator|.
name|getValue
argument_list|()
operator|.
name|add
argument_list|(
name|item
argument_list|)
argument_list|)
expr_stmt|;
name|EnumSet
operator|.
name|allOf
argument_list|(
name|StandardField
operator|.
name|class
argument_list|)
operator|.
name|forEach
argument_list|(
name|item
lambda|->
name|availableColumnsProperty
operator|.
name|getValue
argument_list|()
operator|.
name|add
argument_list|(
name|item
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|specialFieldsEnabledProperty
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|insertSpecialFieldColumns
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|extraFileColumnsEnabledProperty
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|insertExtraFileColumns
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|fillColumnList ()
specifier|public
name|void
name|fillColumnList
parameter_list|()
block|{
name|columnsListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|columnPreferences
operator|.
name|getColumnNames
argument_list|()
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
name|map
argument_list|(
name|field
lambda|->
operator|new
name|TableColumnsItemModel
argument_list|(
name|field
argument_list|,
name|columnPreferences
operator|.
name|getColumnWidth
argument_list|(
name|field
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
argument_list|)
operator|.
name|forEach
argument_list|(
name|columnsListProperty
operator|.
name|getValue
argument_list|()
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
DECL|method|insertSpecialFieldColumns ()
specifier|private
name|void
name|insertSpecialFieldColumns
parameter_list|()
block|{
name|EnumSet
operator|.
name|allOf
argument_list|(
name|SpecialField
operator|.
name|class
argument_list|)
operator|.
name|forEach
argument_list|(
name|item
lambda|->
name|availableColumnsProperty
operator|.
name|getValue
argument_list|()
operator|.
name|add
argument_list|(
literal|0
argument_list|,
name|item
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|removeSpecialFieldColumns ()
specifier|private
name|void
name|removeSpecialFieldColumns
parameter_list|()
block|{
name|columnsListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|removeIf
argument_list|(
name|column
lambda|->
name|column
operator|.
name|getField
argument_list|()
operator|instanceof
name|SpecialField
argument_list|)
expr_stmt|;
name|availableColumnsProperty
operator|.
name|getValue
argument_list|()
operator|.
name|removeIf
argument_list|(
name|field
lambda|->
name|field
operator|instanceof
name|SpecialField
argument_list|)
expr_stmt|;
block|}
DECL|method|insertExtraFileColumns ()
specifier|private
name|void
name|insertExtraFileColumns
parameter_list|()
block|{
name|ExternalFileTypes
operator|.
name|getInstance
argument_list|()
operator|.
name|getExternalFileTypeSelection
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|ExternalFileType
operator|::
name|getName
argument_list|)
operator|.
name|map
argument_list|(
name|FieldsUtil
operator|.
name|ExtraFilePseudoField
operator|::
operator|new
argument_list|)
operator|.
name|forEach
argument_list|(
name|availableColumnsProperty
operator|::
name|add
argument_list|)
expr_stmt|;
block|}
DECL|method|removeExtraFileColumns ()
specifier|private
name|void
name|removeExtraFileColumns
parameter_list|()
block|{
name|columnsListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|removeIf
argument_list|(
name|column
lambda|->
name|column
operator|.
name|getField
argument_list|()
operator|instanceof
name|FieldsUtil
operator|.
name|ExtraFilePseudoField
argument_list|)
expr_stmt|;
name|availableColumnsProperty
operator|.
name|getValue
argument_list|()
operator|.
name|removeIf
argument_list|(
name|field
lambda|->
name|field
operator|instanceof
name|FieldsUtil
operator|.
name|ExtraFilePseudoField
argument_list|)
expr_stmt|;
block|}
DECL|method|insertColumnInList ()
specifier|public
name|void
name|insertColumnInList
parameter_list|()
block|{
if|if
condition|(
name|addColumnProperty
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
name|columnsListProperty
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
name|getField
argument_list|()
operator|.
name|equals
argument_list|(
name|addColumnProperty
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
name|columnsListProperty
operator|.
name|add
argument_list|(
operator|new
name|TableColumnsItemModel
argument_list|(
name|addColumnProperty
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
block|;
name|addColumnProperty
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
DECL|method|removeColumn (TableColumnsItemModel column)
specifier|public
name|void
name|removeColumn
parameter_list|(
name|TableColumnsItemModel
name|column
parameter_list|)
block|{
name|columnsListProperty
operator|.
name|remove
argument_list|(
name|column
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|moveColumnUp ()
specifier|public
name|void
name|moveColumnUp
parameter_list|()
block|{
name|TableColumnsItemModel
name|selectedColumn
init|=
name|selectedColumnModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
name|int
name|row
init|=
name|columnsListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|indexOf
argument_list|(
name|selectedColumn
argument_list|)
decl_stmt|;
if|if
condition|(
name|selectedColumn
operator|==
literal|null
operator|||
name|row
operator|<
literal|1
condition|)
block|{
return|return;
block|}
name|columnsListProperty
operator|.
name|remove
argument_list|(
name|selectedColumn
argument_list|)
expr_stmt|;
name|columnsListProperty
operator|.
name|add
argument_list|(
name|row
operator|-
literal|1
argument_list|,
name|selectedColumn
argument_list|)
expr_stmt|;
name|selectedColumnModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|clearAndSelect
argument_list|(
name|row
operator|-
literal|1
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|moveColumnDown ()
specifier|public
name|void
name|moveColumnDown
parameter_list|()
block|{
name|TableColumnsItemModel
name|selectedColumn
init|=
name|selectedColumnModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
name|int
name|row
init|=
name|columnsListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|indexOf
argument_list|(
name|selectedColumn
argument_list|)
decl_stmt|;
if|if
condition|(
name|selectedColumn
operator|==
literal|null
operator|||
name|row
operator|>
name|columnsListProperty
operator|.
name|getValue
argument_list|()
operator|.
name|size
argument_list|()
operator|-
literal|2
condition|)
block|{
return|return;
block|}
name|columnsListProperty
operator|.
name|remove
argument_list|(
name|selectedColumn
argument_list|)
expr_stmt|;
name|columnsListProperty
operator|.
name|add
argument_list|(
name|row
operator|+
literal|1
argument_list|,
name|selectedColumn
argument_list|)
expr_stmt|;
name|selectedColumnModelProperty
operator|.
name|getValue
argument_list|()
operator|.
name|clearAndSelect
argument_list|(
name|row
operator|+
literal|1
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|columnNames
init|=
name|columnsListProperty
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|item
lambda|->
name|item
operator|.
name|getField
argument_list|()
operator|.
name|getName
argument_list|()
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
comment|// for each column get either actual width or - if it does not exist - default value
name|Map
argument_list|<
name|String
argument_list|,
name|Double
argument_list|>
name|columnWidths
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
name|columnNames
operator|.
name|forEach
argument_list|(
name|field
lambda|->
name|columnWidths
operator|.
name|put
argument_list|(
name|field
argument_list|,
name|columnPreferences
operator|.
name|getColumnWidth
argument_list|(
name|field
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|ColumnPreferences
name|newColumnPreferences
init|=
operator|new
name|ColumnPreferences
argument_list|(
name|showFileColumnProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|showUrlColumnProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|preferDoiProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|showEPrintColumnProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|columnNames
argument_list|,
name|specialFieldsEnabledProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|specialFieldsSyncKeywordsProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|specialFieldsSerializeProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|extraFileColumnsEnabledProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|columnWidths
argument_list|,
name|columnPreferences
operator|.
name|getSortTypesForColumns
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|columnPreferences
operator|.
name|getAutoSyncSpecialFieldsToKeyWords
argument_list|()
operator|!=
name|newColumnPreferences
operator|.
name|getAutoSyncSpecialFieldsToKeyWords
argument_list|()
condition|)
block|{
name|restartWarnings
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Synchronize special fields to keywords"
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|columnPreferences
operator|.
name|getSerializeSpecialFields
argument_list|()
operator|!=
name|newColumnPreferences
operator|.
name|getSerializeSpecialFields
argument_list|()
condition|)
block|{
name|restartWarnings
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Serialize special fields"
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|preferences
operator|.
name|storeColumnPreferences
argument_list|(
name|newColumnPreferences
argument_list|)
expr_stmt|;
block|}
end_function

begin_function
DECL|method|columnsListValidationStatus ()
name|ValidationStatus
name|columnsListValidationStatus
parameter_list|()
block|{
return|return
name|columnsNotEmptyValidator
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
name|status
init|=
name|columnsListValidationStatus
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|status
operator|.
name|isValid
argument_list|()
operator|&&
name|status
operator|.
name|getHighestMessage
argument_list|()
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|status
operator|.
name|getHighestMessage
argument_list|()
operator|.
name|get
argument_list|()
operator|.
name|getMessage
argument_list|()
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
name|restartWarnings
return|;
block|}
end_function

begin_function
DECL|method|columnsListProperty ()
specifier|public
name|ListProperty
argument_list|<
name|TableColumnsItemModel
argument_list|>
name|columnsListProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|columnsListProperty
return|;
block|}
end_function

begin_function
DECL|method|selectedColumnModelProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|SelectionModel
argument_list|<
name|TableColumnsItemModel
argument_list|>
argument_list|>
name|selectedColumnModelProperty
parameter_list|()
block|{
return|return
name|selectedColumnModelProperty
return|;
block|}
end_function

begin_function
DECL|method|availableColumnsProperty ()
specifier|public
name|ListProperty
argument_list|<
name|Field
argument_list|>
name|availableColumnsProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|availableColumnsProperty
return|;
block|}
end_function

begin_function
DECL|method|addColumnProperty ()
specifier|public
name|ObjectProperty
argument_list|<
name|Field
argument_list|>
name|addColumnProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|addColumnProperty
return|;
block|}
end_function

begin_function
DECL|method|showFileColumnProperty ()
specifier|public
name|BooleanProperty
name|showFileColumnProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|showFileColumnProperty
return|;
block|}
end_function

begin_function
DECL|method|showUrlColumnProperty ()
specifier|public
name|BooleanProperty
name|showUrlColumnProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|showUrlColumnProperty
return|;
block|}
end_function

begin_function
DECL|method|preferUrlProperty ()
specifier|public
name|BooleanProperty
name|preferUrlProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|preferUrlProperty
return|;
block|}
end_function

begin_function
DECL|method|preferDoiProperty ()
specifier|public
name|BooleanProperty
name|preferDoiProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|preferDoiProperty
return|;
block|}
end_function

begin_function
DECL|method|specialFieldsEnabledProperty ()
specifier|public
name|BooleanProperty
name|specialFieldsEnabledProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|specialFieldsEnabledProperty
return|;
block|}
end_function

begin_function
DECL|method|specialFieldsSyncKeywordsProperty ()
specifier|public
name|BooleanProperty
name|specialFieldsSyncKeywordsProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|specialFieldsSyncKeywordsProperty
return|;
block|}
end_function

begin_function
DECL|method|specialFieldsSerializeProperty ()
specifier|public
name|BooleanProperty
name|specialFieldsSerializeProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|specialFieldsSerializeProperty
return|;
block|}
end_function

begin_function
DECL|method|showEPrintColumnProperty ()
specifier|public
name|BooleanProperty
name|showEPrintColumnProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|showEPrintColumnProperty
return|;
block|}
end_function

begin_function
DECL|method|extraFileColumnsEnabledProperty ()
specifier|public
name|BooleanProperty
name|extraFileColumnsEnabledProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|extraFileColumnsEnabledProperty
return|;
block|}
end_function

unit|}
end_unit

