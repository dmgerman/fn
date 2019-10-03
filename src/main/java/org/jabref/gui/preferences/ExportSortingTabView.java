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
name|scene
operator|.
name|control
operator|.
name|Label
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
name|SaveOrderConfigDisplayView
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

begin_class
DECL|class|ExportSortingTabView
specifier|public
class|class
name|ExportSortingTabView
extends|extends
name|AbstractPreferenceTabView
implements|implements
name|PreferencesTab
block|{
DECL|field|exportOrderPanel
specifier|private
specifier|final
name|SaveOrderConfigDisplayView
name|exportOrderPanel
decl_stmt|;
DECL|method|ExportSortingTabView (JabRefPreferences preferences)
specifier|public
name|ExportSortingTabView
parameter_list|(
name|JabRefPreferences
name|preferences
parameter_list|)
block|{
name|this
operator|.
name|preferences
operator|=
name|preferences
expr_stmt|;
name|Label
name|title
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Export sorting"
argument_list|)
argument_list|)
decl_stmt|;
name|title
operator|.
name|getStyleClass
argument_list|()
operator|.
name|add
argument_list|(
literal|"titleHeader"
argument_list|)
expr_stmt|;
name|exportOrderPanel
operator|=
operator|new
name|SaveOrderConfigDisplayView
argument_list|()
expr_stmt|;
name|exportOrderPanel
operator|.
name|setValues
argument_list|(
name|preferences
operator|.
name|loadExportSaveOrder
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|setWidth
argument_list|(
literal|650.0
argument_list|)
expr_stmt|;
name|this
operator|.
name|setSpacing
argument_list|(
literal|10.0
argument_list|)
expr_stmt|;
name|this
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|title
argument_list|,
name|exportOrderPanel
argument_list|)
expr_stmt|;
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
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|exportOrderPanel
operator|.
name|setValues
argument_list|(
name|preferences
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
name|exportOrderPanel
operator|.
name|storeConfig
argument_list|()
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
block|}
end_class

end_unit
