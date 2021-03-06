begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.push
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|push
package|;
end_package

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
name|TextField
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_class
DECL|class|PushToVimSettings
specifier|public
class|class
name|PushToVimSettings
extends|extends
name|PushToApplicationSettings
block|{
DECL|field|vimServer
specifier|private
specifier|final
name|TextField
name|vimServer
init|=
operator|new
name|TextField
argument_list|()
decl_stmt|;
DECL|method|PushToVimSettings (PushToApplication application, DialogService dialogService)
specifier|public
name|PushToVimSettings
parameter_list|(
name|PushToApplication
name|application
parameter_list|,
name|DialogService
name|dialogService
parameter_list|)
block|{
name|super
argument_list|(
name|application
argument_list|,
name|dialogService
argument_list|)
expr_stmt|;
name|settingsPane
operator|.
name|add
argument_list|(
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Vim server name"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|settingsPane
operator|.
name|add
argument_list|(
name|vimServer
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|vimServer
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|VIM_SERVER
argument_list|)
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
name|super
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|VIM_SERVER
argument_list|,
name|vimServer
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

