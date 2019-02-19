begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.contentselector
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|contentselector
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
name|Collections
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
name|HashSet
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
name|Set
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
name|binding
operator|.
name|Bindings
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|binding
operator|.
name|BooleanBinding
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
name|SimpleStringProperty
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
name|StringProperty
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
name|AbstractViewModel
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
name|BasePanel
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
name|FieldName
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
name|ContentSelector
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
import|import static
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|collect
operator|.
name|ImmutableList
operator|.
name|of
import|;
end_import

begin_class
DECL|class|ContentSelectorDialogViewModel
class|class
name|ContentSelectorDialogViewModel
extends|extends
name|AbstractViewModel
block|{
DECL|field|DEFAULT_FIELD_NAMES
specifier|private
specifier|static
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|DEFAULT_FIELD_NAMES
init|=
name|of
argument_list|(
name|FieldName
operator|.
name|AUTHOR
argument_list|,
name|FieldName
operator|.
name|JOURNAL
argument_list|,
name|FieldName
operator|.
name|KEYWORDS
argument_list|,
name|FieldName
operator|.
name|PUBLISHER
argument_list|)
decl_stmt|;
DECL|field|basePanel
specifier|private
specifier|final
name|BasePanel
name|basePanel
decl_stmt|;
DECL|field|metaData
specifier|private
specifier|final
name|MetaData
name|metaData
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|fieldNameKeywordsMap
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|fieldNameKeywordsMap
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|fieldNames
specifier|private
name|ListProperty
argument_list|<
name|String
argument_list|>
name|fieldNames
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
DECL|field|keywords
specifier|private
name|ListProperty
argument_list|<
name|String
argument_list|>
name|keywords
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
DECL|field|selectedFieldName
specifier|private
name|StringProperty
name|selectedFieldName
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|selectedKeyword
specifier|private
name|StringProperty
name|selectedKeyword
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|method|ContentSelectorDialogViewModel (BasePanel basePanel, DialogService dialogService)
name|ContentSelectorDialogViewModel
parameter_list|(
name|BasePanel
name|basePanel
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|this
operator|.
name|basePanel
operator|=
name|basePanel
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|basePanel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
name|populateFieldNameKeywordsMapWithExistingValues
argument_list|()
expr_stmt|;
name|populateFieldNamesListWithValues
argument_list|()
expr_stmt|;
block|}
DECL|method|populateFieldNamesListWithValues ()
specifier|private
name|void
name|populateFieldNamesListWithValues
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|existingFieldNames
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|fieldNameKeywordsMap
operator|.
name|keySet
argument_list|()
argument_list|)
decl_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|existingFieldNames
argument_list|)
expr_stmt|;
name|fieldNames
operator|.
name|addAll
argument_list|(
name|existingFieldNames
argument_list|)
expr_stmt|;
if|if
condition|(
name|fieldNames
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|DEFAULT_FIELD_NAMES
operator|.
name|forEach
argument_list|(
name|this
operator|::
name|addFieldNameIfUnique
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|populateFieldNameKeywordsMapWithExistingValues ()
specifier|private
name|void
name|populateFieldNameKeywordsMapWithExistingValues
parameter_list|()
block|{
name|metaData
operator|.
name|getContentSelectors
argument_list|()
operator|.
name|getContentSelectors
argument_list|()
operator|.
name|forEach
argument_list|(
name|existingContentSelector
lambda|->
name|fieldNameKeywordsMap
operator|.
name|put
argument_list|(
name|existingContentSelector
operator|.
name|getFieldName
argument_list|()
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|existingContentSelector
operator|.
name|getValues
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|getFieldNamesBackingList ()
name|ListProperty
argument_list|<
name|String
argument_list|>
name|getFieldNamesBackingList
parameter_list|()
block|{
return|return
name|fieldNames
return|;
block|}
DECL|method|selectedFieldNameProperty ()
name|StringProperty
name|selectedFieldNameProperty
parameter_list|()
block|{
return|return
name|selectedFieldName
return|;
block|}
DECL|method|isFieldNameListEmpty ()
name|BooleanBinding
name|isFieldNameListEmpty
parameter_list|()
block|{
return|return
name|Bindings
operator|.
name|isEmpty
argument_list|(
name|fieldNames
argument_list|)
return|;
block|}
DECL|method|isNoFieldNameSelected ()
name|BooleanBinding
name|isNoFieldNameSelected
parameter_list|()
block|{
return|return
name|Bindings
operator|.
name|isEmpty
argument_list|(
name|selectedFieldName
argument_list|)
return|;
block|}
DECL|method|getKeywordsBackingList ()
name|ListProperty
argument_list|<
name|String
argument_list|>
name|getKeywordsBackingList
parameter_list|()
block|{
return|return
name|keywords
return|;
block|}
DECL|method|selectedKeywordProperty ()
name|StringProperty
name|selectedKeywordProperty
parameter_list|()
block|{
return|return
name|selectedKeyword
return|;
block|}
DECL|method|isNoKeywordSelected ()
name|BooleanBinding
name|isNoKeywordSelected
parameter_list|()
block|{
return|return
name|Bindings
operator|.
name|isEmpty
argument_list|(
name|selectedKeyword
argument_list|)
return|;
block|}
DECL|method|showInputFieldNameDialog ()
name|void
name|showInputFieldNameDialog
parameter_list|()
block|{
name|dialogService
operator|.
name|showInputDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add new field name"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field name:"
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|this
operator|::
name|addFieldNameIfUnique
argument_list|)
expr_stmt|;
block|}
DECL|method|addFieldNameIfUnique (String fieldNameToAdd)
specifier|private
name|void
name|addFieldNameIfUnique
parameter_list|(
name|String
name|fieldNameToAdd
parameter_list|)
block|{
name|boolean
name|exists
init|=
name|fieldNameKeywordsMap
operator|.
name|containsKey
argument_list|(
name|fieldNameToAdd
argument_list|)
decl_stmt|;
if|if
condition|(
name|exists
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field name \"%0\" already exists"
argument_list|,
name|fieldNameToAdd
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|fieldNameKeywordsMap
operator|.
name|put
argument_list|(
name|fieldNameToAdd
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
expr_stmt|;
name|fieldNames
operator|.
name|add
argument_list|(
name|fieldNameToAdd
argument_list|)
expr_stmt|;
block|}
DECL|method|showRemoveFieldNameConfirmationDialog (String fieldNameToRemove)
name|void
name|showRemoveFieldNameConfirmationDialog
parameter_list|(
name|String
name|fieldNameToRemove
parameter_list|)
block|{
if|if
condition|(
name|fieldNameToRemove
operator|==
literal|null
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"No field name selected!"
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|boolean
name|deleteConfirmed
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove field name"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Are you sure you want to remove field name: \"%0\"?"
argument_list|,
name|fieldNameToRemove
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|deleteConfirmed
condition|)
block|{
name|removeFieldName
argument_list|(
name|fieldNameToRemove
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|removeFieldName (String fieldNameToRemove)
specifier|private
name|void
name|removeFieldName
parameter_list|(
name|String
name|fieldNameToRemove
parameter_list|)
block|{
name|fieldNameKeywordsMap
operator|.
name|remove
argument_list|(
name|fieldNameToRemove
argument_list|)
expr_stmt|;
name|fieldNames
operator|.
name|remove
argument_list|(
name|fieldNameToRemove
argument_list|)
expr_stmt|;
block|}
DECL|method|populateKeywords (String selectedFieldName)
name|void
name|populateKeywords
parameter_list|(
name|String
name|selectedFieldName
parameter_list|)
block|{
name|keywords
operator|.
name|clear
argument_list|()
expr_stmt|;
if|if
condition|(
name|selectedFieldName
operator|!=
literal|null
condition|)
block|{
name|keywords
operator|.
name|addAll
argument_list|(
name|fieldNameKeywordsMap
operator|.
name|get
argument_list|(
name|selectedFieldName
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|showInputKeywordDialog (String selectedFieldName)
name|void
name|showInputKeywordDialog
parameter_list|(
name|String
name|selectedFieldName
parameter_list|)
block|{
name|dialogService
operator|.
name|showInputDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add new keyword"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keyword:"
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|newKeyword
lambda|->
name|addKeywordIfUnique
argument_list|(
name|selectedFieldName
argument_list|,
name|newKeyword
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|addKeywordIfUnique (String fieldName, String keywordToAdd)
specifier|private
name|void
name|addKeywordIfUnique
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|keywordToAdd
parameter_list|)
block|{
name|boolean
name|exists
init|=
name|fieldNameKeywordsMap
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
operator|.
name|contains
argument_list|(
name|keywordToAdd
argument_list|)
decl_stmt|;
if|if
condition|(
name|exists
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Keyword \"%0\" already exists"
argument_list|,
name|keywordToAdd
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|List
argument_list|<
name|String
argument_list|>
name|existingKeywords
init|=
name|fieldNameKeywordsMap
operator|.
name|getOrDefault
argument_list|(
name|fieldName
argument_list|,
operator|new
name|ArrayList
argument_list|<>
argument_list|()
argument_list|)
decl_stmt|;
name|existingKeywords
operator|.
name|add
argument_list|(
name|keywordToAdd
argument_list|)
expr_stmt|;
name|fieldNameKeywordsMap
operator|.
name|put
argument_list|(
name|fieldName
argument_list|,
name|existingKeywords
argument_list|)
expr_stmt|;
name|keywords
operator|.
name|add
argument_list|(
name|keywordToAdd
argument_list|)
expr_stmt|;
name|populateKeywords
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
block|}
DECL|method|showRemoveKeywordConfirmationDialog (String fieldName, String keywordToRemove)
name|void
name|showRemoveKeywordConfirmationDialog
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|keywordToRemove
parameter_list|)
block|{
name|boolean
name|deleteConfirmed
init|=
name|dialogService
operator|.
name|showConfirmationDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove keyword"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Are you sure you want to remove keyword: \"%0\"?"
argument_list|,
name|keywordToRemove
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|deleteConfirmed
condition|)
block|{
name|removeKeyword
argument_list|(
name|fieldName
argument_list|,
name|keywordToRemove
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|removeKeyword (String fieldName, String keywordToRemove)
specifier|private
name|void
name|removeKeyword
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|keywordToRemove
parameter_list|)
block|{
name|fieldNameKeywordsMap
operator|.
name|get
argument_list|(
name|fieldName
argument_list|)
operator|.
name|remove
argument_list|(
name|keywordToRemove
argument_list|)
expr_stmt|;
name|keywords
operator|.
name|remove
argument_list|(
name|keywordToRemove
argument_list|)
expr_stmt|;
block|}
DECL|method|saveChanges ()
name|void
name|saveChanges
parameter_list|()
block|{
name|List
argument_list|<
name|String
argument_list|>
name|metaDataFieldNames
init|=
name|metaData
operator|.
name|getContentSelectors
argument_list|()
operator|.
name|getFieldNamesWithSelectors
argument_list|()
decl_stmt|;
name|fieldNameKeywordsMap
operator|.
name|forEach
argument_list|(
parameter_list|(
name|fieldName
parameter_list|,
name|keywords
parameter_list|)
lambda|->
name|updateMetaDataContentSelector
argument_list|(
name|metaDataFieldNames
argument_list|,
name|fieldName
argument_list|,
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fieldNamesToRemove
init|=
name|filterFieldNamesToRemove
argument_list|()
decl_stmt|;
name|fieldNamesToRemove
operator|.
name|forEach
argument_list|(
name|metaData
operator|::
name|clearContentSelectors
argument_list|)
expr_stmt|;
name|basePanel
operator|.
name|setupMainPanel
argument_list|()
expr_stmt|;
name|basePanel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
block|}
DECL|method|filterFieldNamesToRemove ()
specifier|private
name|List
argument_list|<
name|String
argument_list|>
name|filterFieldNamesToRemove
parameter_list|()
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|newlyAddedKeywords
init|=
name|fieldNameKeywordsMap
operator|.
name|keySet
argument_list|()
decl_stmt|;
return|return
name|metaData
operator|.
name|getContentSelectors
argument_list|()
operator|.
name|getFieldNamesWithSelectors
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|filter
argument_list|(
name|fieldName
lambda|->
operator|!
name|newlyAddedKeywords
operator|.
name|contains
argument_list|(
name|fieldName
argument_list|)
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toList
argument_list|()
argument_list|)
return|;
block|}
DECL|method|updateMetaDataContentSelector (List<String> existingFieldNames, String fieldName, List<String> keywords)
specifier|private
name|void
name|updateMetaDataContentSelector
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|existingFieldNames
parameter_list|,
name|String
name|fieldName
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|keywords
parameter_list|)
block|{
name|boolean
name|fieldNameDoNotExists
init|=
operator|!
name|existingFieldNames
operator|.
name|contains
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
name|fieldNameDoNotExists
condition|)
block|{
name|metaData
operator|.
name|addContentSelector
argument_list|(
operator|new
name|ContentSelector
argument_list|(
name|fieldName
argument_list|,
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|keywordsHaveChanged
argument_list|(
name|fieldName
argument_list|,
name|keywords
argument_list|)
condition|)
block|{
name|metaData
operator|.
name|clearContentSelectors
argument_list|(
name|fieldName
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|addContentSelector
argument_list|(
operator|new
name|ContentSelector
argument_list|(
name|fieldName
argument_list|,
name|keywords
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|keywordsHaveChanged (String fieldName, List<String> keywords)
specifier|private
name|boolean
name|keywordsHaveChanged
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|keywords
parameter_list|)
block|{
name|HashSet
argument_list|<
name|String
argument_list|>
name|keywordsSet
init|=
name|asHashSet
argument_list|(
name|keywords
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|existingKeywords
init|=
name|metaData
operator|.
name|getContentSelectorValuesForField
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|keywordsSet
operator|.
name|equals
argument_list|(
name|asHashSet
argument_list|(
name|existingKeywords
argument_list|)
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
block|}
return|return
operator|!
name|keywordsSet
operator|.
name|isEmpty
argument_list|()
operator|&&
name|existingKeywords
operator|.
name|isEmpty
argument_list|()
return|;
block|}
DECL|method|asHashSet (List<String> listToConvert)
specifier|private
name|HashSet
argument_list|<
name|String
argument_list|>
name|asHashSet
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|listToConvert
parameter_list|)
block|{
return|return
operator|new
name|HashSet
argument_list|<>
argument_list|(
name|listToConvert
argument_list|)
return|;
block|}
block|}
end_class

end_unit

