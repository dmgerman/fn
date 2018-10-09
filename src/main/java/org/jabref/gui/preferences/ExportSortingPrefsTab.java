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
name|javafx
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|event
operator|.
name|EventHandler
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|Node
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
name|RadioButton
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
name|Separator
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
name|ToggleGroup
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
name|Pane
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|scene
operator|.
name|shape
operator|.
name|Line
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
name|SaveOrderConfigDisplay
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

begin_comment
comment|/**  * Preference tab for file sorting options.  */
end_comment

begin_class
DECL|class|ExportSortingPrefsTab
class|class
name|ExportSortingPrefsTab
extends|extends
name|Pane
implements|implements
name|PrefsTab
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|exportInOriginalOrder
specifier|private
specifier|final
name|RadioButton
name|exportInOriginalOrder
decl_stmt|;
DECL|field|exportInTableOrder
specifier|private
specifier|final
name|RadioButton
name|exportInTableOrder
decl_stmt|;
DECL|field|exportInSpecifiedOrder
specifier|private
specifier|final
name|RadioButton
name|exportInSpecifiedOrder
decl_stmt|;
DECL|field|exportOrderPanel
specifier|private
specifier|final
name|SaveOrderConfigDisplay
name|exportOrderPanel
decl_stmt|;
DECL|field|builder
specifier|private
specifier|final
name|GridPane
name|builder
init|=
operator|new
name|GridPane
argument_list|()
decl_stmt|;
DECL|method|ExportSortingPrefsTab (JabRefPreferences prefs)
specifier|public
name|ExportSortingPrefsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
comment|// EXPORT SORT ORDER
comment|// create Components
name|exportInOriginalOrder
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export entries in their original order"
argument_list|)
argument_list|)
expr_stmt|;
name|exportInTableOrder
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export in current table sort order"
argument_list|)
argument_list|)
expr_stmt|;
name|exportInSpecifiedOrder
operator|=
operator|new
name|RadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export entries ordered as specified"
argument_list|)
argument_list|)
expr_stmt|;
specifier|final
name|ToggleGroup
name|group
init|=
operator|new
name|ToggleGroup
argument_list|()
decl_stmt|;
name|exportInOriginalOrder
operator|.
name|setToggleGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|exportInTableOrder
operator|.
name|setToggleGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|exportInSpecifiedOrder
operator|.
name|setToggleGroup
argument_list|(
name|group
argument_list|)
expr_stmt|;
name|exportOrderPanel
operator|=
operator|new
name|SaveOrderConfigDisplay
argument_list|()
expr_stmt|;
name|EventHandler
argument_list|<
name|ActionEvent
argument_list|>
name|listener
init|=
parameter_list|(
name|event
parameter_list|)
lambda|->
block|{
name|boolean
name|selected
init|=
name|event
operator|.
name|getSource
argument_list|()
operator|==
name|exportInSpecifiedOrder
decl_stmt|;
name|exportOrderPanel
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
block|}
decl_stmt|;
name|exportInOriginalOrder
operator|.
name|setOnAction
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|exportInTableOrder
operator|.
name|setOnAction
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|exportInSpecifiedOrder
operator|.
name|setOnAction
argument_list|(
name|listener
argument_list|)
expr_stmt|;
name|Label
name|exportSortOrder
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export sort order"
argument_list|)
argument_list|)
decl_stmt|;
name|exportSortOrder
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"sectionHeader"
argument_list|)
expr_stmt|;
comment|// create GUI
name|builder
operator|.
name|add
argument_list|(
name|exportSortOrder
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Separator
argument_list|()
argument_list|,
literal|2
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|exportInOriginalOrder
argument_list|,
literal|1
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|2
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|exportInTableOrder
argument_list|,
literal|1
argument_list|,
literal|4
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|2
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|exportInSpecifiedOrder
argument_list|,
literal|1
argument_list|,
literal|6
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|2
argument_list|,
literal|7
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|exportOrderPanel
operator|.
name|getJFXPanel
argument_list|()
argument_list|,
literal|1
argument_list|,
literal|8
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
operator|new
name|Line
argument_list|()
argument_list|,
literal|2
argument_list|,
literal|9
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getBuilder ()
specifier|public
name|Node
name|getBuilder
parameter_list|()
block|{
return|return
name|builder
return|;
block|}
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_ORIGINAL_ORDER
argument_list|)
condition|)
block|{
name|exportInOriginalOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|)
condition|)
block|{
name|exportInSpecifiedOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|exportInTableOrder
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
name|boolean
name|selected
init|=
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|)
decl_stmt|;
name|exportOrderPanel
operator|.
name|setEnabled
argument_list|(
name|selected
argument_list|)
expr_stmt|;
name|exportOrderPanel
operator|.
name|setSaveOrderConfig
argument_list|(
name|prefs
operator|.
name|loadExportSaveOrder
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
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_ORIGINAL_ORDER
argument_list|,
name|exportInOriginalOrder
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|EXPORT_IN_SPECIFIED_ORDER
argument_list|,
name|exportInSpecifiedOrder
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|storeExportSaveOrder
argument_list|(
name|exportOrderPanel
operator|.
name|getSaveOrderConfig
argument_list|()
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
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export sorting"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

