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
name|geometry
operator|.
name|Insets
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
name|CheckBox
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
name|TextField
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
name|HBox
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
name|layout
operator|.
name|VBox
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
name|gui
operator|.
name|util
operator|.
name|ControlHelper
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
DECL|class|AppearancePrefsTab
class|class
name|AppearancePrefsTab
extends|extends
name|Pane
implements|implements
name|PrefsTab
block|{
DECL|field|BASE_CSS
specifier|public
specifier|static
specifier|final
name|String
name|BASE_CSS
init|=
literal|"Base.css"
decl_stmt|;
DECL|field|DARK_CSS
specifier|public
specifier|static
specifier|final
name|String
name|DARK_CSS
init|=
literal|"Dark.css"
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|fontTweaksLAF
specifier|private
specifier|final
name|CheckBox
name|fontTweaksLAF
decl_stmt|;
DECL|field|fontSize
specifier|private
specifier|final
name|TextField
name|fontSize
decl_stmt|;
DECL|field|overrideFonts
specifier|private
specifier|final
name|CheckBox
name|overrideFonts
decl_stmt|;
DECL|field|container
specifier|private
specifier|final
name|VBox
name|container
init|=
operator|new
name|VBox
argument_list|()
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|lightTheme
specifier|private
specifier|final
name|RadioButton
name|lightTheme
decl_stmt|;
DECL|field|darkTheme
specifier|private
specifier|final
name|RadioButton
name|darkTheme
decl_stmt|;
comment|/**      * Customization of appearance parameters.      *      * @param prefs a<code>JabRefPreferences</code> value      */
DECL|method|AppearancePrefsTab (DialogService dialogService, JabRefPreferences prefs)
specifier|public
name|AppearancePrefsTab
parameter_list|(
name|DialogService
name|dialogService
parameter_list|,
name|JabRefPreferences
name|prefs
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
name|prefs
operator|=
name|prefs
expr_stmt|;
name|overrideFonts
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Override default font settings"
argument_list|)
argument_list|)
expr_stmt|;
name|fontSize
operator|=
operator|new
name|TextField
argument_list|()
expr_stmt|;
name|fontSize
operator|.
name|setTextFormatter
argument_list|(
name|ControlHelper
operator|.
name|getIntegerTextFormatter
argument_list|()
argument_list|)
expr_stmt|;
name|Label
name|fontSizeLabel
init|=
operator|new
name|Label
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Font size:"
argument_list|)
argument_list|)
decl_stmt|;
name|HBox
name|fontSizeContainer
init|=
operator|new
name|HBox
argument_list|(
name|fontSizeLabel
argument_list|,
name|fontSize
argument_list|)
decl_stmt|;
name|VBox
operator|.
name|setMargin
argument_list|(
name|fontSizeContainer
argument_list|,
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|,
literal|35
argument_list|)
argument_list|)
expr_stmt|;
name|fontSizeContainer
operator|.
name|disableProperty
argument_list|()
operator|.
name|bind
argument_list|(
name|overrideFonts
operator|.
name|selectedProperty
argument_list|()
operator|.
name|not
argument_list|()
argument_list|)
expr_stmt|;
name|fontTweaksLAF
operator|=
operator|new
name|CheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Tweak font rendering for entry editor on Linux"
argument_list|)
argument_list|)
expr_stmt|;
name|ToggleGroup
name|themeGroup
init|=
operator|new
name|ToggleGroup
argument_list|()
decl_stmt|;
name|lightTheme
operator|=
operator|new
name|RadioButton
argument_list|(
literal|"Light theme"
argument_list|)
expr_stmt|;
name|lightTheme
operator|.
name|setToggleGroup
argument_list|(
name|themeGroup
argument_list|)
expr_stmt|;
name|darkTheme
operator|=
operator|new
name|RadioButton
argument_list|(
literal|"Dark theme"
argument_list|)
expr_stmt|;
name|darkTheme
operator|.
name|setToggleGroup
argument_list|(
name|themeGroup
argument_list|)
expr_stmt|;
if|if
condition|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|FX_THEME
argument_list|)
operator|.
name|equals
argument_list|(
name|BASE_CSS
argument_list|)
condition|)
name|lightTheme
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
elseif|else
if|if
condition|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|FX_THEME
argument_list|)
operator|.
name|equals
argument_list|(
name|DARK_CSS
argument_list|)
condition|)
name|darkTheme
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|container
operator|.
name|getChildren
argument_list|()
operator|.
name|addAll
argument_list|(
name|overrideFonts
argument_list|,
name|fontSizeContainer
argument_list|,
name|fontTweaksLAF
argument_list|,
name|lightTheme
argument_list|,
name|darkTheme
argument_list|)
expr_stmt|;
block|}
DECL|method|getBuilder ()
specifier|public
name|Node
name|getBuilder
parameter_list|()
block|{
return|return
name|container
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
name|fontTweaksLAF
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FX_FONT_RENDERING_TWEAK
argument_list|)
argument_list|)
expr_stmt|;
name|overrideFonts
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERRIDE_DEFAULT_FONT_SIZE
argument_list|)
argument_list|)
expr_stmt|;
name|fontSize
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|MAIN_FONT_SIZE
argument_list|)
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
comment|// Java FX font rendering tweak
specifier|final
name|boolean
name|oldFxTweakValue
init|=
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FX_FONT_RENDERING_TWEAK
argument_list|)
decl_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|FX_FONT_RENDERING_TWEAK
argument_list|,
name|fontTweaksLAF
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|boolean
name|oldOverrideDefaultFontSize
init|=
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERRIDE_DEFAULT_FONT_SIZE
argument_list|)
decl_stmt|;
specifier|final
name|int
name|oldFontSize
init|=
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|MAIN_FONT_SIZE
argument_list|)
decl_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERRIDE_DEFAULT_FONT_SIZE
argument_list|,
name|overrideFonts
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|newFontSize
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|fontSize
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|MAIN_FONT_SIZE
argument_list|,
name|newFontSize
argument_list|)
expr_stmt|;
name|boolean
name|isThemeChanged
init|=
literal|false
decl_stmt|;
if|if
condition|(
name|lightTheme
operator|.
name|isSelected
argument_list|()
operator|&&
operator|!
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|FX_THEME
argument_list|)
operator|.
name|equals
argument_list|(
name|BASE_CSS
argument_list|)
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|FX_THEME
argument_list|,
name|BASE_CSS
argument_list|)
expr_stmt|;
name|isThemeChanged
operator|=
literal|true
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|darkTheme
operator|.
name|isSelected
argument_list|()
operator|&&
operator|!
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|FX_THEME
argument_list|)
operator|.
name|equals
argument_list|(
name|DARK_CSS
argument_list|)
condition|)
block|{
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|FX_THEME
argument_list|,
name|DARK_CSS
argument_list|)
expr_stmt|;
name|isThemeChanged
operator|=
literal|true
expr_stmt|;
block|}
name|boolean
name|isRestartRequired
init|=
operator|(
name|oldFxTweakValue
operator|!=
name|fontTweaksLAF
operator|.
name|isSelected
argument_list|()
operator|)
operator|||
operator|(
name|oldOverrideDefaultFontSize
operator|!=
name|overrideFonts
operator|.
name|isSelected
argument_list|()
operator|)
operator|||
operator|(
name|oldFontSize
operator|!=
name|newFontSize
operator|)
operator|||
name|isThemeChanged
decl_stmt|;
if|if
condition|(
name|isRestartRequired
condition|)
block|{
name|dialogService
operator|.
name|showWarningDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Settings"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Some appearance settings you changed require to restart JabRef to come into effect."
argument_list|)
argument_list|)
expr_stmt|;
block|}
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
literal|"Appearance"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

