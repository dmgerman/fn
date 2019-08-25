begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.cleanup
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|cleanup
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
name|java
operator|.
name|util
operator|.
name|Objects
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|TreeSet
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
name|value
operator|.
name|ChangeListener
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|value
operator|.
name|ObservableValue
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
name|collections
operator|.
name|ObservableList
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
name|Button
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
name|CheckBox
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
name|ComboBox
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
name|Label
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
name|ListView
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
name|SelectionMode
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|ColumnConstraints
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|GridPane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|layout
operator|.
name|RowConstraints
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefGUI
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
name|ViewModelListCellFactory
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
name|cleanup
operator|.
name|Cleanups
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
name|formatter
operator|.
name|casechanger
operator|.
name|ProtectTermsFormatter
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
name|cleanup
operator|.
name|FieldFormatterCleanup
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
name|cleanup
operator|.
name|FieldFormatterCleanups
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
name|cleanup
operator|.
name|Formatter
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
name|database
operator|.
name|BibDatabaseContext
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
name|metadata
operator|.
name|MetaData
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
DECL|class|FieldFormatterCleanupsPanel
specifier|public
class|class
name|FieldFormatterCleanupsPanel
extends|extends
name|GridPane
block|{
DECL|field|DESCRIPTION
specifier|private
specifier|static
specifier|final
name|String
name|DESCRIPTION
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Description"
argument_list|)
operator|+
literal|": "
decl_stmt|;
DECL|field|cleanupEnabled
specifier|private
specifier|final
name|CheckBox
name|cleanupEnabled
decl_stmt|;
DECL|field|defaultFormatters
specifier|private
specifier|final
name|FieldFormatterCleanups
name|defaultFormatters
decl_stmt|;
DECL|field|availableFormatters
specifier|private
specifier|final
name|List
argument_list|<
name|Formatter
argument_list|>
name|availableFormatters
decl_stmt|;
DECL|field|fieldFormatterCleanups
specifier|private
name|FieldFormatterCleanups
name|fieldFormatterCleanups
decl_stmt|;
DECL|field|actionsList
specifier|private
name|ListView
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actionsList
decl_stmt|;
DECL|field|formattersCombobox
specifier|private
name|ComboBox
argument_list|<
name|Formatter
argument_list|>
name|formattersCombobox
decl_stmt|;
DECL|field|selectFieldCombobox
specifier|private
name|ComboBox
argument_list|<
name|String
argument_list|>
name|selectFieldCombobox
decl_stmt|;
DECL|field|addButton
specifier|private
name|Button
name|addButton
decl_stmt|;
DECL|field|descriptionAreaText
specifier|private
name|Label
name|descriptionAreaText
decl_stmt|;
DECL|field|removeButton
specifier|private
name|Button
name|removeButton
decl_stmt|;
DECL|field|resetButton
specifier|private
name|Button
name|resetButton
decl_stmt|;
DECL|field|recommendButton
specifier|private
name|Button
name|recommendButton
decl_stmt|;
DECL|field|actions
specifier|private
name|ObservableList
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|actions
decl_stmt|;
DECL|method|FieldFormatterCleanupsPanel (String description, FieldFormatterCleanups defaultFormatters)
specifier|public
name|FieldFormatterCleanupsPanel
parameter_list|(
name|String
name|description
parameter_list|,
name|FieldFormatterCleanups
name|defaultFormatters
parameter_list|)
block|{
name|this
operator|.
name|defaultFormatters
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|defaultFormatters
argument_list|)
expr_stmt|;
name|cleanupEnabled
operator|=
operator|new
name|CheckBox
argument_list|(
name|description
argument_list|)
expr_stmt|;
name|availableFormatters
operator|=
name|Cleanups
operator|.
name|getBuiltInFormatters
argument_list|()
expr_stmt|;
name|availableFormatters
operator|.
name|add
argument_list|(
operator|new
name|ProtectTermsFormatter
argument_list|(
name|Globals
operator|.
name|protectedTermsLoader
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues (MetaData metaData)
specifier|public
name|void
name|setValues
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|Optional
argument_list|<
name|FieldFormatterCleanups
argument_list|>
name|saveActions
init|=
name|metaData
operator|.
name|getSaveActions
argument_list|()
decl_stmt|;
name|setValues
argument_list|(
name|saveActions
operator|.
name|orElse
argument_list|(
name|Cleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues (FieldFormatterCleanups formatterCleanups)
specifier|public
name|void
name|setValues
parameter_list|(
name|FieldFormatterCleanups
name|formatterCleanups
parameter_list|)
block|{
name|fieldFormatterCleanups
operator|=
name|formatterCleanups
expr_stmt|;
comment|// first clear existing content
name|this
operator|.
name|getChildren
argument_list|()
operator|.
name|clear
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|FieldFormatterCleanup
argument_list|>
name|configuredActions
init|=
name|fieldFormatterCleanups
operator|.
name|getConfiguredActions
argument_list|()
decl_stmt|;
name|actions
operator|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|configuredActions
argument_list|)
expr_stmt|;
name|buildLayout
argument_list|()
expr_stmt|;
block|}
DECL|method|buildLayout ()
specifier|private
name|void
name|buildLayout
parameter_list|()
block|{
name|ColumnConstraints
name|first
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|first
operator|.
name|setPrefWidth
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|ColumnConstraints
name|second
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|second
operator|.
name|setPrefWidth
argument_list|(
literal|175
argument_list|)
expr_stmt|;
name|ColumnConstraints
name|third
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|third
operator|.
name|setPrefWidth
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|ColumnConstraints
name|fourth
init|=
operator|new
name|ColumnConstraints
argument_list|()
decl_stmt|;
name|fourth
operator|.
name|setPrefWidth
argument_list|(
literal|200
argument_list|)
expr_stmt|;
name|getColumnConstraints
argument_list|()
operator|.
name|addAll
argument_list|(
name|first
argument_list|,
name|second
argument_list|,
name|third
argument_list|,
name|fourth
argument_list|)
expr_stmt|;
name|RowConstraints
name|firstR
init|=
operator|new
name|RowConstraints
argument_list|()
decl_stmt|;
name|firstR
operator|.
name|setPrefHeight
argument_list|(
literal|25
argument_list|)
expr_stmt|;
name|RowConstraints
name|secondR
init|=
operator|new
name|RowConstraints
argument_list|()
decl_stmt|;
name|secondR
operator|.
name|setPrefHeight
argument_list|(
literal|100
argument_list|)
expr_stmt|;
name|RowConstraints
name|thirdR
init|=
operator|new
name|RowConstraints
argument_list|()
decl_stmt|;
name|thirdR
operator|.
name|setPrefHeight
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|RowConstraints
name|fourthR
init|=
operator|new
name|RowConstraints
argument_list|()
decl_stmt|;
name|fourthR
operator|.
name|setPrefHeight
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|RowConstraints
name|fifthR
init|=
operator|new
name|RowConstraints
argument_list|()
decl_stmt|;
name|fifthR
operator|.
name|setPrefHeight
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|getRowConstraints
argument_list|()
operator|.
name|addAll
argument_list|(
name|firstR
argument_list|,
name|secondR
argument_list|,
name|thirdR
argument_list|,
name|fourthR
argument_list|,
name|fifthR
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|cleanupEnabled
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|4
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|actionsList
operator|=
operator|new
name|ListView
argument_list|<>
argument_list|(
name|actions
argument_list|)
expr_stmt|;
name|actionsList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|setSelectionMode
argument_list|(
name|SelectionMode
operator|.
name|SINGLE
argument_list|)
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|FieldFormatterCleanup
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|action
lambda|->
name|action
operator|.
name|getField
argument_list|()
operator|.
name|getDisplayName
argument_list|()
operator|+
literal|": "
operator|+
name|action
operator|.
name|getFormatter
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|withStringTooltip
argument_list|(
name|action
lambda|->
name|action
operator|.
name|getFormatter
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
operator|.
name|install
argument_list|(
name|actionsList
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|actionsList
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|resetButton
operator|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Reset"
argument_list|)
argument_list|)
expr_stmt|;
name|resetButton
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|actions
operator|.
name|setAll
argument_list|(
name|defaultFormatters
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|BibDatabaseContext
name|databaseContext
init|=
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|getBibDatabaseContext
argument_list|()
decl_stmt|;
name|recommendButton
operator|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Recommended for %0"
argument_list|,
name|databaseContext
operator|.
name|getMode
argument_list|()
operator|.
name|getFormattedName
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|boolean
name|isBiblatex
init|=
name|databaseContext
operator|.
name|isBiblatexMode
argument_list|()
decl_stmt|;
name|recommendButton
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
block|{
if|if
condition|(
name|isBiblatex
condition|)
block|{
name|actions
operator|.
name|setAll
argument_list|(
name|Cleanups
operator|.
name|RECOMMEND_BIBLATEX_ACTIONS
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|actions
operator|.
name|setAll
argument_list|(
name|Cleanups
operator|.
name|RECOMMEND_BIBTEX_ACTIONS
operator|.
name|getConfiguredActions
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|removeButton
operator|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove selected"
argument_list|)
argument_list|)
expr_stmt|;
name|removeButton
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
name|actions
operator|.
name|remove
argument_list|(
name|actionsList
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|=
operator|new
name|Label
argument_list|(
name|DESCRIPTION
argument_list|)
expr_stmt|;
name|descriptionAreaText
operator|.
name|setWrapText
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|removeButton
argument_list|,
literal|3
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|resetButton
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|recommendButton
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|getSelectorPanel
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|3
argument_list|,
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|descriptionAreaText
argument_list|,
literal|1
argument_list|,
literal|4
argument_list|,
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|updateDescription
argument_list|()
expr_stmt|;
comment|// make sure the layout is set according to the checkbox
name|cleanupEnabled
operator|.
name|selectedProperty
argument_list|()
operator|.
name|addListener
argument_list|(
operator|new
name|EnablementStatusListener
argument_list|<>
argument_list|(
name|fieldFormatterCleanups
operator|.
name|isEnabled
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|cleanupEnabled
operator|.
name|setSelected
argument_list|(
name|fieldFormatterCleanups
operator|.
name|isEnabled
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|updateDescription ()
specifier|private
name|void
name|updateDescription
parameter_list|()
block|{
name|FieldFormatterCleanup
name|formatterCleanup
init|=
name|getFieldFormatterCleanup
argument_list|()
decl_stmt|;
if|if
condition|(
name|formatterCleanup
operator|.
name|getFormatter
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|descriptionAreaText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
operator|+
name|formatterCleanup
operator|.
name|getFormatter
argument_list|()
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Formatter
name|selectedFormatter
init|=
name|formattersCombobox
operator|.
name|getValue
argument_list|()
decl_stmt|;
if|if
condition|(
name|selectedFormatter
operator|!=
literal|null
condition|)
block|{
name|descriptionAreaText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
operator|+
name|selectedFormatter
operator|.
name|getDescription
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|descriptionAreaText
operator|.
name|setText
argument_list|(
name|DESCRIPTION
argument_list|)
expr_stmt|;
block|}
block|}
block|}
comment|/**      * This panel contains the two comboboxes and the Add button      */
DECL|method|getSelectorPanel ()
specifier|private
name|GridPane
name|getSelectorPanel
parameter_list|()
block|{
name|GridPane
name|builder
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
name|Set
argument_list|<
name|Field
argument_list|>
name|fields
init|=
name|FieldFactory
operator|.
name|getCommonFields
argument_list|()
decl_stmt|;
name|fields
operator|.
name|add
argument_list|(
name|InternalField
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|Set
argument_list|<
name|String
argument_list|>
name|fieldsString
init|=
name|fields
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|Field
operator|::
name|getDisplayName
argument_list|)
operator|.
name|sorted
argument_list|()
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toCollection
argument_list|(
name|TreeSet
operator|::
operator|new
argument_list|)
argument_list|)
decl_stmt|;
name|selectFieldCombobox
operator|=
operator|new
name|ComboBox
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|fieldsString
argument_list|)
argument_list|)
expr_stmt|;
name|selectFieldCombobox
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|selectFieldCombobox
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|formattersCombobox
operator|=
operator|new
name|ComboBox
argument_list|<>
argument_list|(
name|FXCollections
operator|.
name|observableArrayList
argument_list|(
name|availableFormatters
argument_list|)
argument_list|)
expr_stmt|;
operator|new
name|ViewModelListCellFactory
argument_list|<
name|Formatter
argument_list|>
argument_list|()
operator|.
name|withText
argument_list|(
name|Formatter
operator|::
name|getName
argument_list|)
operator|.
name|withStringTooltip
argument_list|(
name|Formatter
operator|::
name|getDescription
argument_list|)
operator|.
name|install
argument_list|(
name|formattersCombobox
argument_list|)
expr_stmt|;
name|EasyBind
operator|.
name|subscribe
argument_list|(
name|formattersCombobox
operator|.
name|valueProperty
argument_list|()
argument_list|,
name|e
lambda|->
name|updateDescription
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|formattersCombobox
argument_list|,
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|addButton
operator|=
operator|new
name|Button
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add"
argument_list|)
argument_list|)
expr_stmt|;
name|addButton
operator|.
name|setOnAction
argument_list|(
name|e
lambda|->
block|{
name|FieldFormatterCleanup
name|newAction
init|=
name|getFieldFormatterCleanup
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|actions
operator|.
name|contains
argument_list|(
name|newAction
argument_list|)
condition|)
block|{
name|actions
operator|.
name|add
argument_list|(
name|newAction
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|addButton
argument_list|,
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
return|return
name|builder
return|;
block|}
DECL|method|storeSettings (MetaData metaData)
specifier|public
name|void
name|storeSettings
parameter_list|(
name|MetaData
name|metaData
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|metaData
argument_list|)
expr_stmt|;
name|FieldFormatterCleanups
name|formatterCleanups
init|=
name|getFormatterCleanups
argument_list|()
decl_stmt|;
comment|// if all actions have been removed, remove the save actions from the MetaData
if|if
condition|(
name|formatterCleanups
operator|.
name|getConfiguredActions
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|metaData
operator|.
name|clearSaveActions
argument_list|()
expr_stmt|;
return|return;
block|}
name|metaData
operator|.
name|setSaveActions
argument_list|(
name|formatterCleanups
argument_list|)
expr_stmt|;
block|}
DECL|method|getFormatterCleanups ()
specifier|public
name|FieldFormatterCleanups
name|getFormatterCleanups
parameter_list|()
block|{
return|return
operator|new
name|FieldFormatterCleanups
argument_list|(
name|cleanupEnabled
operator|.
name|isSelected
argument_list|()
argument_list|,
name|actions
argument_list|)
return|;
block|}
DECL|method|hasChanged ()
specifier|public
name|boolean
name|hasChanged
parameter_list|()
block|{
return|return
operator|!
name|fieldFormatterCleanups
operator|.
name|equals
argument_list|(
name|getFormatterCleanups
argument_list|()
argument_list|)
return|;
block|}
DECL|method|isDefaultSaveActions ()
specifier|public
name|boolean
name|isDefaultSaveActions
parameter_list|()
block|{
return|return
name|Cleanups
operator|.
name|DEFAULT_SAVE_ACTIONS
operator|.
name|equals
argument_list|(
name|getFormatterCleanups
argument_list|()
argument_list|)
return|;
block|}
DECL|method|getFieldFormatterCleanup ()
specifier|private
name|FieldFormatterCleanup
name|getFieldFormatterCleanup
parameter_list|()
block|{
name|Formatter
name|selectedFormatter
init|=
name|formattersCombobox
operator|.
name|getValue
argument_list|()
decl_stmt|;
name|Field
name|field
init|=
name|FieldFactory
operator|.
name|parseField
argument_list|(
name|selectFieldCombobox
operator|.
name|getSelectionModel
argument_list|()
operator|.
name|getSelectedItem
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|FieldFormatterCleanup
argument_list|(
name|field
argument_list|,
name|selectedFormatter
argument_list|)
return|;
block|}
DECL|class|EnablementStatusListener
class|class
name|EnablementStatusListener
parameter_list|<
name|T
parameter_list|>
implements|implements
name|ChangeListener
argument_list|<
name|T
argument_list|>
block|{
DECL|method|EnablementStatusListener (boolean initialStatus)
specifier|public
name|EnablementStatusListener
parameter_list|(
name|boolean
name|initialStatus
parameter_list|)
block|{
name|setStatus
argument_list|(
name|initialStatus
argument_list|)
expr_stmt|;
block|}
DECL|method|setStatus (boolean status)
specifier|private
name|void
name|setStatus
parameter_list|(
name|boolean
name|status
parameter_list|)
block|{
name|actionsList
operator|.
name|setDisable
argument_list|(
operator|!
name|status
argument_list|)
expr_stmt|;
name|selectFieldCombobox
operator|.
name|setDisable
argument_list|(
operator|!
name|status
argument_list|)
expr_stmt|;
name|formattersCombobox
operator|.
name|setDisable
argument_list|(
operator|!
name|status
argument_list|)
expr_stmt|;
name|addButton
operator|.
name|setDisable
argument_list|(
operator|!
name|status
argument_list|)
expr_stmt|;
name|removeButton
operator|.
name|setDisable
argument_list|(
operator|!
name|status
argument_list|)
expr_stmt|;
name|resetButton
operator|.
name|setDisable
argument_list|(
operator|!
name|status
argument_list|)
expr_stmt|;
name|recommendButton
operator|.
name|setDisable
argument_list|(
operator|!
name|status
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|changed (ObservableValue<? extends T> observable, T oldValue, T newValue)
specifier|public
name|void
name|changed
parameter_list|(
name|ObservableValue
argument_list|<
name|?
extends|extends
name|T
argument_list|>
name|observable
parameter_list|,
name|T
name|oldValue
parameter_list|,
name|T
name|newValue
parameter_list|)
block|{
name|setStatus
argument_list|(
name|cleanupEnabled
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

