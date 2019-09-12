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
name|DialogService
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
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|NameFormatterTabViewModel
specifier|public
class|class
name|NameFormatterTabViewModel
implements|implements
name|PreferenceTabViewModel
block|{
DECL|field|formatterListProperty
specifier|private
specifier|final
name|ListProperty
argument_list|<
name|NameFormatterItemModel
argument_list|>
name|formatterListProperty
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
DECL|field|addFormatterNameProperty
specifier|private
specifier|final
name|StringProperty
name|addFormatterNameProperty
init|=
operator|new
name|SimpleStringProperty
argument_list|()
decl_stmt|;
DECL|field|addFormatterStringProperty
specifier|private
specifier|final
name|StringProperty
name|addFormatterStringProperty
init|=
operator|new
name|SimpleStringProperty
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
DECL|method|NameFormatterTabViewModel (DialogService dialogService, JabRefPreferences preferences)
name|NameFormatterTabViewModel
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
name|formatterListProperty
operator|.
name|clear
argument_list|()
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|names
init|=
name|preferences
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|NAME_FORMATER_KEY
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formats
init|=
name|preferences
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|NAME_FORMATTER_VALUE
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|names
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
if|if
condition|(
name|i
operator|<
name|formats
operator|.
name|size
argument_list|()
condition|)
block|{
name|formatterListProperty
operator|.
name|add
argument_list|(
operator|new
name|NameFormatterItemModel
argument_list|(
name|names
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|,
name|formats
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|formatterListProperty
operator|.
name|add
argument_list|(
operator|new
name|NameFormatterItemModel
argument_list|(
name|names
operator|.
name|get
argument_list|(
name|i
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|formatterListProperty
operator|.
name|removeIf
argument_list|(
name|formatter
lambda|->
name|formatter
operator|.
name|getName
argument_list|()
operator|.
name|isEmpty
argument_list|()
argument_list|)
expr_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|names
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|formatterListProperty
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
name|List
argument_list|<
name|String
argument_list|>
name|formats
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|(
name|formatterListProperty
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
name|NameFormatterItemModel
name|formatterListItem
range|:
name|formatterListProperty
control|)
block|{
name|names
operator|.
name|add
argument_list|(
name|formatterListItem
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
name|formats
operator|.
name|add
argument_list|(
name|formatterListItem
operator|.
name|getFormat
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|preferences
operator|.
name|putStringList
argument_list|(
name|JabRefPreferences
operator|.
name|NAME_FORMATER_KEY
argument_list|,
name|names
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putStringList
argument_list|(
name|JabRefPreferences
operator|.
name|NAME_FORMATTER_VALUE
argument_list|,
name|formats
argument_list|)
expr_stmt|;
block|}
DECL|method|addFormatter ()
specifier|public
name|void
name|addFormatter
parameter_list|()
block|{
if|if
condition|(
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|addFormatterNameProperty
operator|.
name|getValue
argument_list|()
argument_list|)
operator|&&
operator|!
name|StringUtil
operator|.
name|isNullOrEmpty
argument_list|(
name|addFormatterStringProperty
operator|.
name|getValue
argument_list|()
argument_list|)
condition|)
block|{
name|NameFormatterItemModel
name|newFormatter
init|=
operator|new
name|NameFormatterItemModel
argument_list|(
name|addFormatterNameProperty
operator|.
name|getValue
argument_list|()
argument_list|,
name|addFormatterStringProperty
operator|.
name|getValue
argument_list|()
argument_list|)
decl_stmt|;
name|addFormatterNameProperty
operator|.
name|setValue
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|addFormatterStringProperty
operator|.
name|setValue
argument_list|(
literal|""
argument_list|)
expr_stmt|;
name|formatterListProperty
operator|.
name|add
argument_list|(
name|newFormatter
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|removeFormatter (NameFormatterItemModel formatter)
specifier|public
name|void
name|removeFormatter
parameter_list|(
name|NameFormatterItemModel
name|formatter
parameter_list|)
block|{
name|formatterListProperty
operator|.
name|remove
argument_list|(
name|formatter
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
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
DECL|method|formatterListProperty ()
specifier|public
name|ListProperty
argument_list|<
name|NameFormatterItemModel
argument_list|>
name|formatterListProperty
parameter_list|()
block|{
return|return
name|formatterListProperty
return|;
block|}
DECL|method|addFormatterNameProperty ()
specifier|public
name|StringProperty
name|addFormatterNameProperty
parameter_list|()
block|{
return|return
name|addFormatterNameProperty
return|;
block|}
DECL|method|addFormatterStringProperty ()
specifier|public
name|StringProperty
name|addFormatterStringProperty
parameter_list|()
block|{
return|return
name|addFormatterStringProperty
return|;
block|}
block|}
end_class

end_unit
