begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.importer
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|importer
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
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|EntryTypes
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
name|BibDatabaseMode
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
name|CustomEntryType
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
name|EntryType
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
DECL|class|ImportCustomEntryTypesDialogViewModel
specifier|public
class|class
name|ImportCustomEntryTypesDialogViewModel
block|{
DECL|field|mode
specifier|private
specifier|final
name|BibDatabaseMode
name|mode
decl_stmt|;
DECL|field|preferencesService
specifier|private
specifier|final
name|PreferencesService
name|preferencesService
decl_stmt|;
DECL|field|newTypes
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|EntryType
argument_list|>
name|newTypes
init|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
decl_stmt|;
DECL|field|differentCustomizationTypes
specifier|private
specifier|final
name|ObservableList
argument_list|<
name|EntryType
argument_list|>
name|differentCustomizationTypes
init|=
name|FXCollections
operator|.
name|observableArrayList
argument_list|()
decl_stmt|;
DECL|method|ImportCustomEntryTypesDialogViewModel (BibDatabaseMode mode, List<EntryType> customEntryTypes, PreferencesService preferencesService)
specifier|public
name|ImportCustomEntryTypesDialogViewModel
parameter_list|(
name|BibDatabaseMode
name|mode
parameter_list|,
name|List
argument_list|<
name|EntryType
argument_list|>
name|customEntryTypes
parameter_list|,
name|PreferencesService
name|preferencesService
parameter_list|)
block|{
name|this
operator|.
name|mode
operator|=
name|mode
expr_stmt|;
name|this
operator|.
name|preferencesService
operator|=
name|preferencesService
expr_stmt|;
for|for
control|(
name|EntryType
name|customType
range|:
name|customEntryTypes
control|)
block|{
if|if
condition|(
operator|!
name|EntryTypes
operator|.
name|getType
argument_list|(
name|customType
operator|.
name|getName
argument_list|()
argument_list|,
name|mode
argument_list|)
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|newTypes
operator|.
name|add
argument_list|(
name|customType
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|EntryType
name|currentlyStoredType
init|=
name|EntryTypes
operator|.
name|getType
argument_list|(
name|customType
operator|.
name|getName
argument_list|()
argument_list|,
name|mode
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|EntryTypes
operator|.
name|isEqualNameAndFieldBased
argument_list|(
name|customType
argument_list|,
name|currentlyStoredType
argument_list|)
condition|)
block|{
name|differentCustomizationTypes
operator|.
name|add
argument_list|(
name|customType
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
DECL|method|newTypes ()
specifier|public
name|ObservableList
argument_list|<
name|EntryType
argument_list|>
name|newTypes
parameter_list|()
block|{
return|return
name|this
operator|.
name|newTypes
return|;
block|}
DECL|method|differentCustomizations ()
specifier|public
name|ObservableList
argument_list|<
name|EntryType
argument_list|>
name|differentCustomizations
parameter_list|()
block|{
return|return
name|this
operator|.
name|differentCustomizationTypes
return|;
block|}
DECL|method|importCustomEntryTypes (List<EntryType> checkedUnknownEntryTypes, List<EntryType> checkedDifferentEntryTypes)
specifier|public
name|void
name|importCustomEntryTypes
parameter_list|(
name|List
argument_list|<
name|EntryType
argument_list|>
name|checkedUnknownEntryTypes
parameter_list|,
name|List
argument_list|<
name|EntryType
argument_list|>
name|checkedDifferentEntryTypes
parameter_list|)
block|{
if|if
condition|(
operator|!
name|checkedUnknownEntryTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|checkedUnknownEntryTypes
operator|.
name|forEach
argument_list|(
name|type
lambda|->
name|EntryTypes
operator|.
name|addOrModifyCustomEntryType
argument_list|(
operator|(
name|CustomEntryType
operator|)
name|type
argument_list|,
name|mode
argument_list|)
argument_list|)
expr_stmt|;
name|preferencesService
operator|.
name|saveCustomEntryTypes
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
operator|!
name|checkedDifferentEntryTypes
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|checkedUnknownEntryTypes
operator|.
name|forEach
argument_list|(
name|type
lambda|->
name|EntryTypes
operator|.
name|addOrModifyCustomEntryType
argument_list|(
operator|(
name|CustomEntryType
operator|)
name|type
argument_list|,
name|mode
argument_list|)
argument_list|)
expr_stmt|;
name|preferencesService
operator|.
name|saveCustomEntryTypes
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
