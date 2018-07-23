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
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
DECL|class|PushToEmacsSettings
specifier|public
class|class
name|PushToEmacsSettings
extends|extends
name|PushToApplicationSettings
block|{
DECL|field|additionalParams
specifier|private
specifier|final
name|JTextField
name|additionalParams
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
annotation|@
name|Override
DECL|method|getSettingsPanel ()
specifier|public
name|JPanel
name|getSettingsPanel
parameter_list|()
block|{
name|additionalParams
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
name|EMACS_ADDITIONAL_PARAMETERS
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|super
operator|.
name|getSettingsPanel
argument_list|()
return|;
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
name|EMACS_ADDITIONAL_PARAMETERS
argument_list|,
name|additionalParams
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|initSettingsPanel ()
specifier|protected
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|super
operator|.
name|initSettingsPanel
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendRows
argument_list|(
literal|"2dlu, p, 2dlu, p"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Additional parameters"
argument_list|)
operator|+
literal|":"
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|additionalParams
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|settings
operator|=
name|builder
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

