begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.gui.preftabs
package|package
name|net
operator|.
name|sf
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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|*
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
name|layout
operator|.
name|FormLayout
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|ColorSetupPanel
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
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
name|net
operator|.
name|sf
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

begin_class
DECL|class|AppearancePrefsTab
class|class
name|AppearancePrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|colorCodes
specifier|private
specifier|final
name|JCheckBox
name|colorCodes
decl_stmt|;
DECL|field|overrideFonts
specifier|private
specifier|final
name|JCheckBox
name|overrideFonts
decl_stmt|;
DECL|field|showGrid
specifier|private
specifier|final
name|JCheckBox
name|showGrid
decl_stmt|;
DECL|field|colorPanel
specifier|private
specifier|final
name|ColorSetupPanel
name|colorPanel
init|=
operator|new
name|ColorSetupPanel
argument_list|()
decl_stmt|;
DECL|field|font
specifier|private
name|Font
name|font
init|=
name|GUIGlobals
operator|.
name|CURRENTFONT
decl_stmt|;
DECL|field|oldMenuFontSize
specifier|private
name|int
name|oldMenuFontSize
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
DECL|field|rowPadding
specifier|private
specifier|final
name|JTextField
name|rowPadding
decl_stmt|;
comment|/**      * Customization of appearance parameters.      *      * @param prefs a<code>JabRefPreferences</code> value      */
DECL|method|AppearancePrefsTab (JabRefPreferences prefs)
specifier|public
name|AppearancePrefsTab
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
comment|// Row padding size:
name|rowPadding
operator|=
operator|new
name|JTextField
argument_list|(
literal|5
argument_list|)
expr_stmt|;
name|colorCodes
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Color codes for required and optional fields"
argument_list|)
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
name|showGrid
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Show gridlines"
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
name|builder
operator|.
name|leadingColumnOffset
argument_list|(
literal|2
argument_list|)
expr_stmt|;
name|JLabel
name|lab
decl_stmt|;
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
name|JPanel
name|p1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|lab
operator|=
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
expr_stmt|;
name|p1
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|p1
operator|.
name|add
argument_list|(
name|fontSize
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|p1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|overrideFonts
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Table appearance"
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|p2
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|p2
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
literal|"Table row height padding"
argument_list|)
operator|+
literal|":"
argument_list|)
argument_list|)
expr_stmt|;
name|p2
operator|.
name|add
argument_list|(
name|rowPadding
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|p2
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|colorCodes
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|showGrid
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|JButton
name|fontButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set table font"
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|fontButton
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Table and entry editor colors"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|colorPanel
argument_list|)
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
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
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
block|}
block|}
argument_list|)
expr_stmt|;
name|fontButton
operator|.
name|addActionListener
argument_list|(
operator|new
name|ActionListener
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|Font
name|f
init|=
operator|new
name|FontSelectorDialog
argument_list|(
literal|null
argument_list|,
name|GUIGlobals
operator|.
name|CURRENTFONT
argument_list|)
operator|.
name|getSelectedFont
argument_list|()
decl_stmt|;
if|if
condition|(
name|f
operator|!=
literal|null
condition|)
block|{
name|font
operator|=
name|f
expr_stmt|;
block|}
block|}
block|}
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
name|colorCodes
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_COLOR_CODES_ON
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
name|MENU_FONT_SIZE
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|rowPadding
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
name|TABLE_ROW_PADDING
argument_list|)
argument_list|)
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
name|OVERRIDE_DEFAULT_FONTS
argument_list|)
argument_list|)
expr_stmt|;
name|oldOverrideFontSize
operator|=
name|overrideFonts
operator|.
name|isSelected
argument_list|()
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
name|showGrid
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_SHOW_GRID
argument_list|)
argument_list|)
expr_stmt|;
name|colorPanel
operator|.
name|setValues
argument_list|()
expr_stmt|;
block|}
comment|/**      * Store changes to table preferences. This method is called when      * the user clicks Ok.      *      */
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
name|TABLE_COLOR_CODES_ON
argument_list|,
name|colorCodes
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|FONT_FAMILY
argument_list|,
name|font
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
name|font
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
name|font
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
name|CURRENTFONT
operator|=
name|font
expr_stmt|;
name|colorPanel
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|TABLE_SHOW_GRID
argument_list|,
name|showGrid
operator|.
name|isSelected
argument_list|()
argument_list|)
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
if|if
condition|(
operator|(
name|overrideFonts
operator|.
name|isSelected
argument_list|()
operator|!=
name|oldOverrideFontSize
operator|)
operator|||
operator|(
name|size
operator|!=
name|oldMenuFontSize
operator|)
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"You have changed the menu and label font size."
argument_list|)
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
operator|.
name|concat
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Changed font settings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
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
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
try|try
block|{
name|int
name|padding
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|rowPadding
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
name|TABLE_ROW_PADDING
argument_list|,
name|padding
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
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
argument_list|,
name|errorTitle
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
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
literal|"Changed font settings"
argument_list|)
argument_list|)
condition|)
block|{
return|return
literal|false
return|;
block|}
comment|// Test if row padding is a number:
if|if
condition|(
operator|!
name|validateIntegerField
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Table row height padding"
argument_list|)
argument_list|,
name|rowPadding
operator|.
name|getText
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Changed table appearance settings"
argument_list|)
argument_list|)
condition|)
block|{
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

