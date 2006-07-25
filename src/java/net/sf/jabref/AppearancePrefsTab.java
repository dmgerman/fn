begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Vector
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
name|javax
operator|.
name|swing
operator|.
name|table
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|*
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
name|layout
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
name|factories
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
name|gui
operator|.
name|ColorSetupPanel
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
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|colorCodes
DECL|field|antialias
DECL|field|overrideFonts
specifier|private
name|JCheckBox
name|colorCodes
decl_stmt|,
name|antialias
decl_stmt|,
name|overrideFonts
decl_stmt|;
comment|//, useCustomIconTheme;
DECL|field|gbl
specifier|private
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|fontButton
specifier|private
name|JButton
name|fontButton
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Set table font"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|colorPanel
specifier|private
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
name|JTextField
name|fontSize
decl_stmt|;
comment|//, customIconThemeFile;
comment|/**      * Customization of appearance parameters.      *      * @param prefs a<code>JabRefPreferences</code> value      */
DECL|method|AppearancePrefsTab (JabRefPreferences prefs)
specifier|public
name|AppearancePrefsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|_prefs
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
name|colorCodes
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Color codes for required and optional fields"
argument_list|)
argument_list|)
expr_stmt|;
name|antialias
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use antialiasing font"
argument_list|)
argument_list|)
expr_stmt|;
name|overrideFonts
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Override default font settings"
argument_list|)
argument_list|)
expr_stmt|;
comment|//useCustomIconTheme = new JCheckBox(Globals.lang("Use custom icon theme"));
comment|//customIconThemeFile = new JTextField();
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
name|setLeadingColumnOffset
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
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Table appearance"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|antialias
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
name|append
argument_list|(
name|colorPanel
argument_list|)
expr_stmt|;
comment|/*builder.appendSeparator(Globals.lang("Custom icon theme"));         builder.append(useCustomIconTheme);         builder.nextLine();         JPanel p2 = new JPanel();         lab = new JLabel(Globals.lang("Custom icon theme file")+":");         p2.add(lab);         p2.add(customIconThemeFile);         BrowseAction browse = new BrowseAction(null, customIconThemeFile, false);         JButton browseBut = new JButton(Globals.lang("Browse"));         browseBut.addActionListener(browse);         p2.add(browseBut);         builder.append(p2);           */
name|JPanel
name|upper
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|sort
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|namesp
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
name|iconCol
init|=
operator|new
name|JPanel
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
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
comment|// JDialog dl = new EntryCustomizationDialog(ths);
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
operator|==
literal|null
condition|)
return|return;
else|else
name|font
operator|=
name|f
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|/*menuFontButton.addActionListener(new ActionListener() {          public void actionPerformed(ActionEvent e) {              Font f=new FontSelectorDialog                  (null, menuFont).getSelectedFont();              if(f==null)                  return;              else                  menuFont = f;          }          });*/
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
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"tableColorCodesOn"
argument_list|)
argument_list|)
expr_stmt|;
name|antialias
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"antialias"
argument_list|)
argument_list|)
expr_stmt|;
name|fontSize
operator|.
name|setText
argument_list|(
literal|""
operator|+
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"menuFontSize"
argument_list|)
argument_list|)
expr_stmt|;
name|oldMenuFontSize
operator|=
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"menuFontSize"
argument_list|)
expr_stmt|;
name|overrideFonts
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"overrideDefaultFonts"
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
comment|//useCustomIconTheme.setSelected(_prefs.getBoolean("useCustomIconTheme"));
comment|//customIconThemeFile.setText(_prefs.get("customIconThemeFile"));
name|colorPanel
operator|.
name|setValues
argument_list|()
expr_stmt|;
block|}
comment|/**      * Store changes to table preferences. This method is called when      * the user clicks Ok.      *      */
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"tableColorCodesOn"
argument_list|,
name|colorCodes
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"antialias"
argument_list|,
name|antialias
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"fontFamily"
argument_list|,
name|font
operator|.
name|getFamily
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"fontStyle"
argument_list|,
name|font
operator|.
name|getStyle
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"fontSize"
argument_list|,
name|font
operator|.
name|getSize
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"overrideDefaultFonts"
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
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"menuFontSize"
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"You have changed the menu and label font size. "
operator|+
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|,
name|Globals
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
block|}
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
parameter_list|()
block|{
try|try
block|{
comment|// Test if font size is a number:
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"You must enter an integer value in the text field for"
argument_list|)
operator|+
literal|" '"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Menu and label font size"
argument_list|)
operator|+
literal|"'"
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Changed font settings"
argument_list|)
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
block|}
end_class

end_unit

