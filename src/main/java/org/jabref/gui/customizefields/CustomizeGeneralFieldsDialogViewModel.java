begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.customizefields
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|customizefields
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
name|Map
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
name|bibtexkeypattern
operator|.
name|BibtexKeyGenerator
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
name|preferences
operator|.
name|JabRefPreferences
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
DECL|class|CustomizeGeneralFieldsDialogViewModel
specifier|public
class|class
name|CustomizeGeneralFieldsDialogViewModel
block|{
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|preferences
specifier|private
specifier|final
name|PreferencesService
name|preferences
decl_stmt|;
DECL|field|fieldsText
specifier|private
specifier|final
name|StringProperty
name|fieldsText
init|=
operator|new
name|SimpleStringProperty
argument_list|(
literal|""
argument_list|)
decl_stmt|;
DECL|method|CustomizeGeneralFieldsDialogViewModel (DialogService dialogService, PreferencesService preferences)
specifier|public
name|CustomizeGeneralFieldsDialogViewModel
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|PreferencesService
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
name|setInitialFieldsText
argument_list|()
expr_stmt|;
block|}
DECL|method|setInitialFieldsText ()
specifier|private
name|void
name|setInitialFieldsText
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|tab
range|:
name|preferences
operator|.
name|getEntryEditorTabList
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|tab
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|String
operator|.
name|join
argument_list|(
literal|";"
argument_list|,
name|tab
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|fieldsText
operator|.
name|set
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|fieldsTextProperty ()
specifier|public
name|StringProperty
name|fieldsTextProperty
parameter_list|()
block|{
return|return
name|fieldsText
return|;
block|}
DECL|method|saveFields ()
specifier|public
name|void
name|saveFields
parameter_list|()
block|{
name|String
index|[]
name|lines
init|=
name|fieldsText
operator|.
name|get
argument_list|()
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
init|;
name|i
operator|<
name|lines
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|parts
init|=
name|lines
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
if|if
condition|(
name|parts
operator|.
name|length
operator|!=
literal|2
condition|)
block|{
comment|// Report error and exit.
name|String
name|field
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"field"
argument_list|)
decl_stmt|;
name|String
name|title
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
decl_stmt|;
name|String
name|content
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Each line must be of the following form"
argument_list|)
operator|+
literal|" '"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Tabname"
argument_list|)
operator|+
literal|':'
operator|+
name|field
operator|+
literal|"1;"
operator|+
name|field
operator|+
literal|"2;...;"
operator|+
name|field
operator|+
literal|"N'"
decl_stmt|;
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|title
argument_list|,
name|content
argument_list|)
expr_stmt|;
return|return;
block|}
name|String
name|testString
init|=
name|BibtexKeyGenerator
operator|.
name|cleanKey
argument_list|(
name|parts
index|[
literal|1
index|]
argument_list|,
name|preferences
operator|.
name|getEnforceLegalKeys
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|testString
operator|.
name|equals
argument_list|(
name|parts
index|[
literal|1
index|]
argument_list|)
operator|||
operator|(
name|parts
index|[
literal|1
index|]
operator|.
name|indexOf
argument_list|(
literal|'&'
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
name|String
name|title
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
decl_stmt|;
name|String
name|content
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field names are not allowed to contain white space or the following "
operator|+
literal|"characters"
argument_list|)
operator|+
literal|": # { } ~ , ^&"
decl_stmt|;
name|dialogService
operator|.
name|showInformationDialogAndWait
argument_list|(
name|title
argument_list|,
name|content
argument_list|)
expr_stmt|;
return|return;
block|}
name|preferences
operator|.
name|setCustomTabsNameAndFields
argument_list|(
name|parts
index|[
literal|0
index|]
argument_list|,
name|parts
index|[
literal|1
index|]
argument_list|,
name|i
argument_list|)
expr_stmt|;
block|}
name|preferences
operator|.
name|purgeSeries
argument_list|(
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_NAME
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|purgeSeries
argument_list|(
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_FIELDS
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|updateEntryEditorTabList
argument_list|()
expr_stmt|;
block|}
DECL|method|resetFields ()
specifier|public
name|void
name|resetFields
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|customTabNamesFields
init|=
name|preferences
operator|.
name|getCustomTabsNamesAndFields
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|entry
range|:
name|customTabNamesFields
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|entry
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|entry
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|fieldsText
operator|.
name|set
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
