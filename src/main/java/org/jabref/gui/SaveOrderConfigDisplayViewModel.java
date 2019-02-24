begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

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
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
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
name|InternalBibtexFields
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
name|SaveOrderConfig
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
name|SaveOrderConfig
operator|.
name|SortCriterion
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
name|PreferencesService
import|;
end_import

begin_class
DECL|class|SaveOrderConfigDisplayViewModel
specifier|public
class|class
name|SaveOrderConfigDisplayViewModel
block|{
DECL|field|priSortFieldsProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|String
argument_list|>
name|priSortFieldsProperty
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
DECL|field|secSortFieldsProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|String
argument_list|>
name|secSortFieldsProperty
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
DECL|field|terSortFieldsProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|String
argument_list|>
name|terSortFieldsProperty
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
DECL|field|savePriDescPropertySelected
specifier|private
specifier|final
name|BooleanProperty
name|savePriDescPropertySelected
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|saveSecDescPropertySelected
specifier|private
specifier|final
name|BooleanProperty
name|saveSecDescPropertySelected
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|saveTerDescPropertySelected
specifier|private
specifier|final
name|BooleanProperty
name|saveTerDescPropertySelected
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|savePriSortSelectedValueProperty
specifier|private
specifier|final
name|StringProperty
name|savePriSortSelectedValueProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|saveSecSortSelectedValueProperty
specifier|private
specifier|final
name|StringProperty
name|saveSecSortSelectedValueProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|saveTerSortSelectedValueProperty
specifier|private
specifier|final
name|StringProperty
name|saveTerSortSelectedValueProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|field|saveInOriginalProperty
specifier|private
specifier|final
name|BooleanProperty
name|saveInOriginalProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|saveInTableOrderProperty
specifier|private
specifier|final
name|BooleanProperty
name|saveInTableOrderProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|saveInSpecifiedOrderProperty
specifier|private
specifier|final
name|BooleanProperty
name|saveInSpecifiedOrderProperty
init|=
operator|new
name|SimpleBooleanProperty
argument_list|()
decl_stmt|;
DECL|field|config
specifier|private
specifier|final
name|SaveOrderConfig
name|config
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|PreferencesService
name|prefs
decl_stmt|;
DECL|method|SaveOrderConfigDisplayViewModel (SaveOrderConfig config, PreferencesService prefs)
specifier|public
name|SaveOrderConfigDisplayViewModel
parameter_list|(
name|SaveOrderConfig
name|config
parameter_list|,
name|PreferencesService
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|config
operator|=
name|config
expr_stmt|;
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
name|SaveOrderConfig
name|storedSaveOrderConfig
init|=
name|config
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|fieldNames
init|=
name|InternalBibtexFields
operator|.
name|getAllPublicFieldNames
argument_list|()
decl_stmt|;
name|fieldNames
operator|.
name|add
argument_list|(
name|BibEntry
operator|.
name|KEY_FIELD
argument_list|)
expr_stmt|;
name|Collections
operator|.
name|sort
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
name|priSortFieldsProperty
operator|.
name|addAll
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
name|secSortFieldsProperty
operator|.
name|addAll
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
name|terSortFieldsProperty
operator|.
name|addAll
argument_list|(
name|fieldNames
argument_list|)
expr_stmt|;
if|if
condition|(
name|config
operator|.
name|saveInOriginalOrder
argument_list|()
condition|)
block|{
name|saveInOriginalProperty
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|config
operator|.
name|saveInSpecifiedOrder
argument_list|()
condition|)
block|{
name|saveInSpecifiedOrderProperty
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|saveInTableOrderProperty
operator|.
name|setValue
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|setSaveOrderConfig
argument_list|(
name|config
argument_list|)
expr_stmt|;
block|}
DECL|method|priSortFieldsProperty ()
specifier|public
name|ListProperty
argument_list|<
name|String
argument_list|>
name|priSortFieldsProperty
parameter_list|()
block|{
return|return
name|priSortFieldsProperty
return|;
block|}
DECL|method|secSortFieldsProperty ()
specifier|public
name|ListProperty
argument_list|<
name|String
argument_list|>
name|secSortFieldsProperty
parameter_list|()
block|{
return|return
name|secSortFieldsProperty
return|;
block|}
DECL|method|terSortFieldsProperty ()
specifier|public
name|ListProperty
argument_list|<
name|String
argument_list|>
name|terSortFieldsProperty
parameter_list|()
block|{
return|return
name|terSortFieldsProperty
return|;
block|}
DECL|method|getSaveOrderConfig ()
specifier|public
name|SaveOrderConfig
name|getSaveOrderConfig
parameter_list|()
block|{
name|SortCriterion
name|primary
init|=
operator|new
name|SortCriterion
argument_list|(
name|getSelectedItemAsLowerCaseTrim
argument_list|(
name|savePriSortSelectedValueProperty
argument_list|)
argument_list|,
name|savePriDescPropertySelected
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|SortCriterion
name|secondary
init|=
operator|new
name|SortCriterion
argument_list|(
name|getSelectedItemAsLowerCaseTrim
argument_list|(
name|saveSecSortSelectedValueProperty
argument_list|)
argument_list|,
name|saveSecDescPropertySelected
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|SortCriterion
name|tertiary
init|=
operator|new
name|SortCriterion
argument_list|(
name|getSelectedItemAsLowerCaseTrim
argument_list|(
name|saveTerSortSelectedValueProperty
argument_list|)
argument_list|,
name|saveTerDescPropertySelected
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|SaveOrderConfig
name|saveOrderConfig
init|=
operator|new
name|SaveOrderConfig
argument_list|(
name|saveInOriginalProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|primary
argument_list|,
name|secondary
argument_list|,
name|tertiary
argument_list|)
decl_stmt|;
return|return
name|saveOrderConfig
return|;
block|}
DECL|method|setSaveOrderConfig (SaveOrderConfig saveOrderConfig)
specifier|public
name|void
name|setSaveOrderConfig
parameter_list|(
name|SaveOrderConfig
name|saveOrderConfig
parameter_list|)
block|{
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|saveOrderConfig
argument_list|)
expr_stmt|;
name|savePriSortSelectedValueProperty
operator|.
name|setValue
argument_list|(
name|saveOrderConfig
operator|.
name|getSortCriteria
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|field
argument_list|)
expr_stmt|;
name|savePriDescPropertySelected
operator|.
name|setValue
argument_list|(
name|saveOrderConfig
operator|.
name|getSortCriteria
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|descending
argument_list|)
expr_stmt|;
name|saveSecSortSelectedValueProperty
operator|.
name|setValue
argument_list|(
name|saveOrderConfig
operator|.
name|getSortCriteria
argument_list|()
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|field
argument_list|)
expr_stmt|;
name|saveSecDescPropertySelected
operator|.
name|setValue
argument_list|(
name|saveOrderConfig
operator|.
name|getSortCriteria
argument_list|()
operator|.
name|get
argument_list|(
literal|1
argument_list|)
operator|.
name|descending
argument_list|)
expr_stmt|;
name|saveTerSortSelectedValueProperty
operator|.
name|setValue
argument_list|(
name|saveOrderConfig
operator|.
name|getSortCriteria
argument_list|()
operator|.
name|get
argument_list|(
literal|2
argument_list|)
operator|.
name|field
argument_list|)
expr_stmt|;
name|saveTerDescPropertySelected
operator|.
name|setValue
argument_list|(
name|saveOrderConfig
operator|.
name|getSortCriteria
argument_list|()
operator|.
name|get
argument_list|(
literal|2
argument_list|)
operator|.
name|descending
argument_list|)
expr_stmt|;
if|if
condition|(
name|saveInOriginalProperty
operator|.
name|getValue
argument_list|()
condition|)
block|{
name|saveOrderConfig
operator|.
name|setSaveInOriginalOrder
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|saveOrderConfig
operator|.
name|setSaveInSpecifiedOrder
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|getSelectedItemAsLowerCaseTrim (StringProperty string)
specifier|private
name|String
name|getSelectedItemAsLowerCaseTrim
parameter_list|(
name|StringProperty
name|string
parameter_list|)
block|{
return|return
name|string
operator|.
name|getValue
argument_list|()
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
operator|.
name|trim
argument_list|()
return|;
block|}
DECL|method|savePriDescPropertySelected ()
specifier|public
name|BooleanProperty
name|savePriDescPropertySelected
parameter_list|()
block|{
return|return
name|savePriDescPropertySelected
return|;
block|}
DECL|method|saveSecDescPropertySelected ()
specifier|public
name|BooleanProperty
name|saveSecDescPropertySelected
parameter_list|()
block|{
return|return
name|saveSecDescPropertySelected
return|;
block|}
DECL|method|saveTerDescPropertySelected ()
specifier|public
name|BooleanProperty
name|saveTerDescPropertySelected
parameter_list|()
block|{
return|return
name|saveTerDescPropertySelected
return|;
block|}
DECL|method|savePriSortSelectedValueProperty ()
specifier|public
name|StringProperty
name|savePriSortSelectedValueProperty
parameter_list|()
block|{
return|return
name|savePriSortSelectedValueProperty
return|;
block|}
DECL|method|saveSecSortSelectedValueProperty ()
specifier|public
name|StringProperty
name|saveSecSortSelectedValueProperty
parameter_list|()
block|{
return|return
name|saveSecSortSelectedValueProperty
return|;
block|}
DECL|method|saveTerSortSelectedValueProperty ()
specifier|public
name|StringProperty
name|saveTerSortSelectedValueProperty
parameter_list|()
block|{
return|return
name|saveTerSortSelectedValueProperty
return|;
block|}
DECL|method|storeConfig ()
specifier|public
name|void
name|storeConfig
parameter_list|()
block|{
name|prefs
operator|.
name|storeExportSaveOrder
argument_list|(
name|this
operator|.
name|getSaveOrderConfig
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|saveInOriginalProperty ()
specifier|public
name|BooleanProperty
name|saveInOriginalProperty
parameter_list|()
block|{
return|return
name|saveInOriginalProperty
return|;
block|}
DECL|method|saveInTableOrderProperty ()
specifier|public
name|BooleanProperty
name|saveInTableOrderProperty
parameter_list|()
block|{
return|return
name|saveInTableOrderProperty
return|;
block|}
DECL|method|saveInSpecifiedOrderProperty ()
specifier|public
name|BooleanProperty
name|saveInSpecifiedOrderProperty
parameter_list|()
block|{
return|return
name|this
operator|.
name|saveInSpecifiedOrderProperty
return|;
block|}
block|}
end_class

end_unit

