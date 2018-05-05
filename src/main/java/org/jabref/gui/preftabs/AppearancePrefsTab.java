begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.preftabs
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|preftabs
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|BorderLayout
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Font
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagLayout
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|BorderFactory
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JCheckBox
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JLabel
import|;
end_import

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
name|GUIGlobals
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|DefaultFormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_class
DECL|class|AppearancePrefsTab
class|class
name|AppearancePrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|AppearancePrefsTab
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|overrideFonts
specifier|private
specifier|final
name|JCheckBox
name|overrideFonts
decl_stmt|;
DECL|field|usedFont
specifier|private
specifier|final
name|Font
name|usedFont
init|=
name|GUIGlobals
operator|.
name|currentFont
decl_stmt|;
DECL|field|oldMenuFontSize
specifier|private
name|int
name|oldMenuFontSize
decl_stmt|;
DECL|field|oldSmallIconSize
specifier|private
name|int
name|oldSmallIconSize
decl_stmt|;
DECL|field|oldLargeIconSize
specifier|private
name|int
name|oldLargeIconSize
decl_stmt|;
DECL|field|oldOverrideFontSize
specifier|private
name|boolean
name|oldOverrideFontSize
decl_stmt|;
DECL|field|fontSize
specifier|private
specifier|final
name|JTextField
name|fontSize
decl_stmt|;
DECL|field|largeIconsTextField
specifier|private
specifier|final
name|JTextField
name|largeIconsTextField
decl_stmt|;
DECL|field|smallIconsTextField
specifier|private
specifier|final
name|JTextField
name|smallIconsTextField
decl_stmt|;
DECL|field|fxFontTweaksLAF
specifier|private
specifier|final
name|JCheckBox
name|fxFontTweaksLAF
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
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
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
comment|// Font sizes:
name|fontSize
operator|=
operator|new
name|JTextField
argument_list|(
literal|5
argument_list|)
expr_stmt|;
comment|// Icon sizes:
name|largeIconsTextField
operator|=
operator|new
name|JTextField
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|smallIconsTextField
operator|=
operator|new
name|JTextField
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|overrideFonts
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Override default font settings"
argument_list|)
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:pref, 4dlu, fill:60dlu, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
name|layout
argument_list|)
decl_stmt|;
name|fxFontTweaksLAF
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Tweak font rendering for entry editor on Linux"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Only list L&F which are available
comment|// only the default L&F shows the OSX specific first drop-down menu
name|builder
operator|.
name|append
argument_list|(
name|fxFontTweaksLAF
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|leadingColumnOffset
argument_list|(
literal|2
argument_list|)
expr_stmt|;
comment|// General appearance settings
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"General"
argument_list|)
argument_list|)
expr_stmt|;
name|FormBuilder
name|generalBuilder
init|=
name|FormBuilder
operator|.
name|create
argument_list|()
decl_stmt|;
name|JPanel
name|generalPanel
init|=
name|generalBuilder
operator|.
name|columns
argument_list|(
literal|"left:pref, left:pref, 3dlu, pref, 7dlu, right:pref, 3dlu, pref"
argument_list|)
operator|.
name|rows
argument_list|(
literal|"pref, 3dlu, pref, 3dlu, pref"
argument_list|)
operator|.
name|columnGroup
argument_list|(
literal|2
argument_list|,
literal|6
argument_list|)
operator|.
name|columnGroup
argument_list|(
literal|4
argument_list|,
literal|8
argument_list|)
operator|.
name|add
argument_list|(
name|overrideFonts
argument_list|)
operator|.
name|xyw
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|5
argument_list|)
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
literal|"    "
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Menu and label font size"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|3
argument_list|)
operator|.
name|add
argument_list|(
name|fontSize
argument_list|)
operator|.
name|xy
argument_list|(
literal|4
argument_list|,
literal|3
argument_list|)
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Size of large icons"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|2
argument_list|,
literal|5
argument_list|)
operator|.
name|add
argument_list|(
name|largeIconsTextField
argument_list|)
operator|.
name|xy
argument_list|(
literal|4
argument_list|,
literal|5
argument_list|)
operator|.
name|add
argument_list|(
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Size of small icons"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
operator|.
name|xy
argument_list|(
literal|6
argument_list|,
literal|5
argument_list|)
operator|.
name|add
argument_list|(
name|smallIconsTextField
argument_list|)
operator|.
name|xy
argument_list|(
literal|8
argument_list|,
literal|5
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|generalPanel
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|JPanel
name|upper
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|sort
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|namesp
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JPanel
name|iconCol
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|upper
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|sort
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|namesp
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|iconCol
operator|.
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|overrideFonts
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|fontSize
operator|.
name|setEnabled
argument_list|(
name|overrideFonts
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|overrideFonts
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|largeIconsTextField
operator|.
name|setEnabled
argument_list|(
name|overrideFonts
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|overrideFonts
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|smallIconsTextField
operator|.
name|setEnabled
argument_list|(
name|overrideFonts
operator|.
name|isSelected
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
name|builder
operator|.
name|getPanel
argument_list|()
decl_stmt|;
name|pan
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|pan
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
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
comment|// L&F
name|fxFontTweaksLAF
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
name|oldOverrideFontSize
operator|=
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERRIDE_DEFAULT_FONTS
argument_list|)
expr_stmt|;
name|oldMenuFontSize
operator|=
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|MENU_FONT_SIZE
argument_list|)
expr_stmt|;
name|oldLargeIconSize
operator|=
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_LARGE
argument_list|)
expr_stmt|;
name|oldSmallIconSize
operator|=
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_SMALL
argument_list|)
expr_stmt|;
name|overrideFonts
operator|.
name|setSelected
argument_list|(
name|oldOverrideFontSize
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
name|oldMenuFontSize
argument_list|)
argument_list|)
expr_stmt|;
name|smallIconsTextField
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|oldSmallIconSize
argument_list|)
argument_list|)
expr_stmt|;
name|largeIconsTextField
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|oldLargeIconSize
argument_list|)
argument_list|)
expr_stmt|;
name|fontSize
operator|.
name|setEnabled
argument_list|(
name|overrideFonts
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|smallIconsTextField
operator|.
name|setEnabled
argument_list|(
name|overrideFonts
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|largeIconsTextField
operator|.
name|setEnabled
argument_list|(
name|overrideFonts
operator|.
name|isSelected
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
name|boolean
name|isRestartRequired
init|=
literal|false
decl_stmt|;
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
name|fxFontTweaksLAF
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|isRestartRequired
operator||=
name|oldFxTweakValue
operator|!=
name|fxFontTweaksLAF
operator|.
name|isSelected
argument_list|()
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|FONT_FAMILY
argument_list|,
name|usedFont
operator|.
name|getFamily
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|FONT_STYLE
argument_list|,
name|usedFont
operator|.
name|getStyle
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|FONT_SIZE
argument_list|,
name|usedFont
operator|.
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|OVERRIDE_DEFAULT_FONTS
argument_list|,
name|overrideFonts
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|currentFont
operator|=
name|usedFont
expr_stmt|;
try|try
block|{
name|int
name|size
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
name|int
name|smallIconSize
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|smallIconsTextField
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|int
name|largeIconSize
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|largeIconsTextField
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|overrideFonts
operator|.
name|isSelected
argument_list|()
condition|)
block|{
if|if
condition|(
name|size
operator|!=
name|oldMenuFontSize
condition|)
block|{
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|MENU_FONT_SIZE
argument_list|,
name|size
argument_list|)
expr_stmt|;
name|isRestartRequired
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|smallIconSize
operator|!=
name|oldSmallIconSize
condition|)
block|{
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_SMALL
argument_list|,
name|smallIconSize
argument_list|)
expr_stmt|;
name|isRestartRequired
operator|=
literal|true
expr_stmt|;
block|}
if|if
condition|(
name|largeIconSize
operator|!=
name|oldLargeIconSize
condition|)
block|{
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_LARGE
argument_list|,
name|largeIconSize
argument_list|)
expr_stmt|;
name|isRestartRequired
operator|=
literal|true
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
name|overrideFonts
operator|.
name|isSelected
argument_list|()
operator|!=
name|oldOverrideFontSize
condition|)
block|{
name|prefs
operator|.
name|remove
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_SMALL
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|remove
argument_list|(
name|JabRefPreferences
operator|.
name|ICON_SIZE_LARGE
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|remove
argument_list|(
name|JabRefPreferences
operator|.
name|MENU_FONT_SIZE
argument_list|)
expr_stmt|;
name|isRestartRequired
operator|=
literal|true
expr_stmt|;
block|}
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
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
comment|// should not happen as values are checked beforehand
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Invalid data value, integer expected"
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|validateIntegerField (String fieldName, String fieldValue, String errorTitle)
specifier|private
name|boolean
name|validateIntegerField
parameter_list|(
name|String
name|fieldName
parameter_list|,
name|String
name|fieldValue
parameter_list|,
name|String
name|errorTitle
parameter_list|)
block|{
try|try
block|{
comment|// Test if the field value is a number:
name|Integer
operator|.
name|parseInt
argument_list|(
name|fieldValue
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|errorTitle
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must enter an integer value in the text field for"
argument_list|)
operator|+
literal|" '"
operator|+
name|fieldName
operator|+
literal|"'"
argument_list|)
expr_stmt|;
return|return
literal|false
return|;
block|}
return|return
literal|true
return|;
block|}
annotation|@
name|Override
DECL|method|validateSettings ()
specifier|public
name|boolean
name|validateSettings
parameter_list|()
block|{
comment|// Test if font size is a number:
if|if
condition|(
operator|!
name|validateIntegerField
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Menu and label font size"
argument_list|)
argument_list|,
name|fontSize
operator|.
name|getText
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid setting"
argument_list|)
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
if|if
condition|(
operator|!
name|validateIntegerField
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Size of large icons"
argument_list|)
argument_list|,
name|largeIconsTextField
operator|.
name|getText
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid setting"
argument_list|)
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
return|return
name|validateIntegerField
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Size of small icons"
argument_list|)
argument_list|,
name|smallIconsTextField
operator|.
name|getText
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid setting"
argument_list|)
argument_list|)
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

