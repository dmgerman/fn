begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/* Copyright (C) 2003 Morten O. Alver, Nizar N. Batada  All programs in this directory and subdirectories are published under the GNU General Public License as described below.  This program is free software; you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation; either version 2 of the License, or (at your option) any later version.  This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.  You should have received a copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA  Further information about the GNU GPL is available at: http://www.gnu.org/copyleft/gpl.ja.html  */
end_comment

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
name|io
operator|.
name|File
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
name|Color
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

begin_comment
comment|//import javax.swing.UIManager;
end_comment

begin_comment
comment|//import javax.swing.UIDefaults;
end_comment

begin_comment
comment|//import javax.swing.UnsupportedLookAndFeelException;
end_comment

begin_class
DECL|class|JabRef
specifier|public
class|class
name|JabRef
block|{
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
comment|//Font fnt = new Font("plain", Font.PLAIN, 12);
name|Object
name|fnt
init|=
operator|new
name|UIDefaults
operator|.
name|ProxyLazyValue
argument_list|(
literal|"javax.swing.plaf.FontUIResource"
argument_list|,
literal|null
argument_list|,
operator|new
name|Object
index|[]
block|{
literal|"plain"
block|,
operator|new
name|Integer
argument_list|(
name|Font
operator|.
name|PLAIN
argument_list|)
block|,
operator|new
name|Integer
argument_list|(
literal|12
argument_list|)
block|}
argument_list|)
decl_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"Button.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"ToggleButton.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"RadioButton.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"CheckBox.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"ColorChooser.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"ComboBox.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"Label.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"List.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"MenuBar.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"MenuItem.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"RadioButtonMenuItem.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"CheckBoxMenuItem.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"Menu.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"PopupMenu.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"OptionPane.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"Panel.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"ProgressBar.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"ScrollPane.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"Viewport.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"TabbedPane.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"Table.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"TableHeader.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"TextField.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"PasswordField.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"TextArea.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"TextPane.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"EditorPane.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"TitledBorder.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"ToolBar.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"ToolTip.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"Tree.font"
argument_list|,
name|fnt
argument_list|)
expr_stmt|;
name|String
name|osName
init|=
name|System
operator|.
name|getProperty
argument_list|(
literal|"os.name"
argument_list|,
literal|"def"
argument_list|)
decl_stmt|;
if|if
condition|(
name|osName
operator|.
name|equals
argument_list|(
literal|"Mac OS X"
argument_list|)
condition|)
block|{
name|Util
operator|.
name|pr
argument_list|(
literal|"Disabling Kunststoff look& feel on Mac OS X."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
try|try
block|{
name|LookAndFeel
name|lnf
init|=
operator|new
name|com
operator|.
name|incors
operator|.
name|plaf
operator|.
name|kunststoff
operator|.
name|KunststoffLookAndFeel
argument_list|()
decl_stmt|;
comment|//com.incors.plaf.kunststoff.KunststoffLookAndFeel.setCurrentTheme(new com.incors.plaf.kunststoff.themes.KunststoffDesktopTheme());
name|UIManager
operator|.
name|setLookAndFeel
argument_list|(
name|lnf
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|UnsupportedLookAndFeelException
name|ex
parameter_list|)
block|{}
block|}
name|JabRefPreferences
name|prefs
init|=
operator|new
name|JabRefPreferences
argument_list|()
decl_stmt|;
name|BibtexEntryType
operator|.
name|loadCustomEntryTypes
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|setLanguage
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
literal|"language"
argument_list|)
argument_list|,
literal|""
argument_list|)
expr_stmt|;
name|GUIGlobals
operator|.
name|CURRENTFONT
operator|=
operator|new
name|Font
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
literal|"fontFamily"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getInt
argument_list|(
literal|"fontStyle"
argument_list|)
argument_list|,
name|prefs
operator|.
name|getInt
argument_list|(
literal|"fontSize"
argument_list|)
argument_list|)
expr_stmt|;
name|JabRefFrame
name|jrf
init|=
operator|new
name|JabRefFrame
argument_list|()
decl_stmt|;
if|if
condition|(
name|args
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
literal|"Opening: "
operator|+
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|jrf
operator|.
name|output
argument_list|(
literal|"Opening: "
operator|+
name|args
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
comment|//verify the file
name|File
name|f
init|=
operator|new
name|File
argument_list|(
name|args
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|f
operator|.
name|exists
argument_list|()
operator|&&
name|f
operator|.
name|canRead
argument_list|()
operator|&&
name|f
operator|.
name|isFile
argument_list|()
condition|)
block|{
name|jrf
operator|.
name|fileToOpen
operator|=
name|f
expr_stmt|;
name|jrf
operator|.
name|openDatabaseAction
operator|.
name|openIt
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
literal|"Error"
operator|+
name|args
index|[
literal|0
index|]
operator|+
literal|" is not a valid file or is not readable"
argument_list|)
expr_stmt|;
comment|//JOptionPane...
block|}
block|}
else|else
block|{
comment|//no arguments (this will be for later and other command line switches)
comment|// ignore..
block|}
block|}
block|}
end_class

end_unit

