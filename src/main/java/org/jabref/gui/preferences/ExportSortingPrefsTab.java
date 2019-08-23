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
name|Node
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
name|PreferencesTab
block|{
DECL|field|exportOrderPanel
specifier|private
specifier|final
name|SaveOrderConfigDisplayView
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
name|exportOrderPanel
operator|=
operator|new
name|SaveOrderConfigDisplayView
argument_list|(
name|prefs
operator|.
name|loadExportSaveOrder
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|exportOrderPanel
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
comment|//empty
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

