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
name|gnu
operator|.
name|dtools
operator|.
name|ritopt
operator|.
name|*
import|;
end_import

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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|imports
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
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
name|export
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
DECL|field|jrf
specifier|static
name|JabRefFrame
name|jrf
decl_stmt|;
comment|/*  class StringArrayOption extends ArrayOption {     public     public void modify(String value) {     }     public void modify(String[] value) {     }     public Object[] getObjectArray() {       return null;     }     public String getTypeName() {       return "Strings";     }     public String getStringValue() {       return "";     }     public Object getObject() {       return null;     }   }*/
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
name|boolean
name|graphicFailure
init|=
literal|false
decl_stmt|;
comment|// ----------------------------------------------------------------
comment|// First instantiate preferences and set language.
comment|// ----------------------------------------------------------------
name|JabRefPreferences
name|prefs
init|=
operator|new
name|JabRefPreferences
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
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
comment|// ----------------------------------------------------------------
comment|// Option processing using RitOpt
comment|// ----------------------------------------------------------------
name|Options
name|options
init|=
operator|new
name|Options
argument_list|(
literal|"JabRef "
argument_list|)
decl_stmt|;
comment|// Create an options repository.
name|options
operator|.
name|setVersion
argument_list|(
name|GUIGlobals
operator|.
name|version
argument_list|)
expr_stmt|;
name|StringOption
name|importFile
init|=
operator|new
name|StringOption
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|importFile
operator|.
name|setDescription
argument_list|(
literal|"imopoepuoeu"
argument_list|)
expr_stmt|;
comment|//Globals.lang);
name|StringOption
name|exportFile
init|=
operator|new
name|StringOption
argument_list|(
literal|""
argument_list|)
decl_stmt|;
name|BooleanOption
name|helpO
init|=
operator|new
name|BooleanOption
argument_list|()
decl_stmt|;
name|BooleanOption
name|disableGui
init|=
operator|new
name|BooleanOption
argument_list|()
decl_stmt|;
comment|//exportFile.setHelpDescriptionSize(80);
comment|//exportFile.setFileCompleteOptionSize(12);
name|BooleanOption
name|loadSess
init|=
operator|new
name|BooleanOption
argument_list|()
decl_stmt|;
name|StringOption
name|exportPrefs
init|=
operator|new
name|StringOption
argument_list|(
literal|"jabref_prefs.xml"
argument_list|)
decl_stmt|;
name|StringOption
name|importPrefs
init|=
operator|new
name|StringOption
argument_list|(
literal|"jabref_prefs.xml"
argument_list|)
decl_stmt|;
name|options
operator|.
name|register
argument_list|(
literal|"nogui"
argument_list|,
literal|'n'
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"No GUI. Only process command line options."
argument_list|)
argument_list|,
name|disableGui
argument_list|)
expr_stmt|;
name|options
operator|.
name|register
argument_list|(
literal|"import"
argument_list|,
literal|'i'
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import file"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"filename"
argument_list|)
operator|+
literal|"[,import format]"
argument_list|,
name|importFile
argument_list|)
expr_stmt|;
name|options
operator|.
name|register
argument_list|(
literal|"output"
argument_list|,
literal|'o'
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Output or export file"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"filename"
argument_list|)
operator|+
literal|"[,export format]"
argument_list|,
name|exportFile
argument_list|)
expr_stmt|;
name|options
operator|.
name|register
argument_list|(
literal|"help"
argument_list|,
literal|'h'
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Display help on command line options"
argument_list|)
argument_list|,
name|helpO
argument_list|)
expr_stmt|;
name|options
operator|.
name|register
argument_list|(
literal|"loads"
argument_list|,
literal|'l'
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Load session"
argument_list|)
argument_list|,
name|loadSess
argument_list|)
expr_stmt|;
name|options
operator|.
name|register
argument_list|(
literal|"prexp"
argument_list|,
literal|'x'
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Export preferences to file"
argument_list|)
argument_list|,
name|exportPrefs
argument_list|)
expr_stmt|;
name|options
operator|.
name|register
argument_list|(
literal|"primp"
argument_list|,
literal|'p'
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import preferences from file"
argument_list|)
argument_list|,
name|importPrefs
argument_list|)
expr_stmt|;
name|options
operator|.
name|setUseMenu
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|String
index|[]
name|leftOver
init|=
name|options
operator|.
name|process
argument_list|(
name|args
argument_list|)
decl_stmt|;
if|if
condition|(
name|helpO
operator|.
name|isInvoked
argument_list|()
condition|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|options
operator|.
name|getHelp
argument_list|()
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Available import formats"
argument_list|)
operator|+
literal|": biblioscape, bibtexml, endnote, inspec,\n\tisi, medline, ovid, ris, scifinder, sixpack, jstor."
argument_list|)
expr_stmt|;
comment|// To specify export formats, we need to take the custom export formats into account.
comment|// So we iterate through the custom formats and add them.
name|String
name|outFormats
init|=
literal|": bibtexml, docbook, html, simplehtml"
decl_stmt|;
name|int
name|length
init|=
name|outFormats
operator|.
name|length
argument_list|()
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|prefs
operator|.
name|customExports
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|format
init|=
name|prefs
operator|.
name|customExports
operator|.
name|getElementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|length
operator|+
name|format
index|[
literal|0
index|]
operator|.
name|length
argument_list|()
operator|>
literal|50
condition|)
block|{
name|outFormats
operator|=
name|outFormats
operator|+
literal|",\n\t"
operator|+
name|format
index|[
literal|0
index|]
expr_stmt|;
name|length
operator|=
name|format
index|[
literal|0
index|]
operator|.
name|length
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|outFormats
operator|=
name|outFormats
operator|+
literal|", "
operator|+
name|format
index|[
literal|0
index|]
expr_stmt|;
name|length
operator|+=
literal|1
operator|+
name|format
index|[
literal|0
index|]
operator|.
name|length
argument_list|()
expr_stmt|;
block|}
block|}
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Available export formats"
argument_list|)
operator|+
name|outFormats
operator|+
literal|"."
argument_list|)
expr_stmt|;
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
comment|// First we quickly scan the command line parameters for any that signal that the GUI
comment|// should not be opened. This is used to decide whether we should show the splash screen or not.
comment|/*boolean openGui = true;       if (args.length> 0) for (int i=0; i<args.length; i++) {         if (args[i].startsWith("-o"))           openGui = false;         if (args[i].equals("-h")) {           System.out.println("Help info goes here.");           System.exit(0);         }       }*/
name|SplashScreen
name|ss
init|=
literal|null
decl_stmt|;
if|if
condition|(
operator|!
name|disableGui
operator|.
name|isInvoked
argument_list|()
condition|)
block|{
try|try
block|{
name|ss
operator|=
operator|new
name|SplashScreen
argument_list|()
expr_stmt|;
name|ss
operator|.
name|show
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|graphicFailure
operator|=
literal|true
expr_stmt|;
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unable to create graphical interface"
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
block|}
comment|//Util.pr("JabRef "+GUIGlobals.version);
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
literal|"Arial"
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
comment|/* 	UIManager.put("Button.font", fnt); 	UIManager.put("ToggleButton.font", fnt); 	UIManager.put("RadioButton.font", fnt); 	UIManager.put("CheckBox.font", fnt); 	UIManager.put("ColorChooser.font", fnt); 	UIManager.put("ComboBox.font", fnt); 	UIManager.put("Label.font", fnt); 	UIManager.put("List.font", fnt); 	UIManager.put("MenuBar.font", fnt); 	UIManager.put("MenuItem.font", fnt); 	UIManager.put("RadioButtonMenuItem.font", fnt); 	UIManager.put("CheckBoxMenuItem.font", fnt); 	UIManager.put("Menu.font", fnt); 	UIManager.put("PopupMenu.font", fnt); 	UIManager.put("OptionPane.font", fnt); 	UIManager.put("Panel.font", fnt); 	UIManager.put("ProgressBar.font", fnt); 	UIManager.put("ScrollPane.font", fnt); 	UIManager.put("Viewport.font", fnt); 	UIManager.put("TabbedPane.font", fnt); 	UIManager.put("Table.font", fnt); 	UIManager.put("TableHeader.font", fnt); 	UIManager.put("TextField.font", fnt); 	UIManager.put("PasswordField.font", fnt); 	UIManager.put("TextArea.font", fnt); 	UIManager.put("TextPane.font", fnt); 	UIManager.put("EditorPane.font", fnt); 	UIManager.put("TitledBorder.font", fnt); 	UIManager.put("ToolBar.font", fnt); 	UIManager.put("ToolTip.font", fnt); 	UIManager.put("Tree.font", fnt); */
comment|// This property is set to make the Mac OSX Java VM move the menu bar to the top
comment|// of the screen, where Mac users expect it to be.
name|System
operator|.
name|setProperty
argument_list|(
literal|"apple.laf.useScreenMenuBar"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
comment|//String osName = System.getProperty("os.name", "def");
if|if
condition|(
name|Globals
operator|.
name|ON_WIN
condition|)
block|{
try|try
block|{
comment|//UIManager.setLookAndFeel(new com.sun.java.swing.plaf.windows.WindowsLookAndFeel());
name|UIManager
operator|.
name|setLookAndFeel
argument_list|(
operator|new
name|com
operator|.
name|jgoodies
operator|.
name|plaf
operator|.
name|windows
operator|.
name|ExtWindowsLookAndFeel
argument_list|()
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
elseif|else
if|if
condition|(
operator|!
name|Globals
operator|.
name|ON_MAC
condition|)
block|{
try|try
block|{
comment|//Class plastic = Class.forName("com.jgoodies.plaf.plastic.PlasticLookAndFeel");
name|LookAndFeel
name|lnf
init|=
operator|new
name|com
operator|.
name|jgoodies
operator|.
name|plaf
operator|.
name|plastic
operator|.
name|Plastic3DLookAndFeel
argument_list|()
decl_stmt|;
comment|//LookAndFeel lnf = new com.sun.java.swing.plaf.gtk.GTKLookAndFeel();
comment|//LookAndFeel lnf = new com.incors.plaf.kunststoff.KunststoffLookAndFeel();
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
comment|// Vector to put imported/loaded database(s) in.
name|Vector
name|loaded
init|=
operator|new
name|Vector
argument_list|()
decl_stmt|;
if|if
condition|(
name|leftOver
operator|.
name|length
operator|>
literal|0
condition|)
block|{
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|leftOver
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
comment|// Leftover arguments are interpreted as bib files to open.
name|ParserResult
name|pr
init|=
name|openBibFile
argument_list|(
name|leftOver
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|pr
operator|!=
literal|null
condition|)
name|loaded
operator|.
name|add
argument_list|(
name|pr
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|importFile
operator|.
name|isInvoked
argument_list|()
condition|)
block|{
name|String
index|[]
name|data
init|=
name|importFile
operator|.
name|getStringValue
argument_list|()
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
if|if
condition|(
name|data
operator|.
name|length
operator|==
literal|1
condition|)
block|{
comment|// Load a bibtex file:
name|ParserResult
name|pr
init|=
name|openBibFile
argument_list|(
name|data
index|[
literal|0
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|pr
operator|!=
literal|null
condition|)
name|loaded
operator|.
name|add
argument_list|(
name|pr
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|data
operator|.
name|length
operator|==
literal|2
condition|)
block|{
comment|// Import a database in a certain format.
try|try
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Importing"
argument_list|)
operator|+
literal|": "
operator|+
name|data
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|BibtexDatabase
name|base
init|=
name|ImportFormatReader
operator|.
name|importFile
argument_list|(
name|data
index|[
literal|1
index|]
argument_list|,
name|data
index|[
literal|0
index|]
operator|.
name|replaceAll
argument_list|(
literal|"~"
argument_list|,
name|System
operator|.
name|getProperty
argument_list|(
literal|"user.home"
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
operator|new
name|ParserResult
argument_list|(
name|base
argument_list|,
operator|new
name|HashMap
argument_list|()
argument_list|)
decl_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
operator|new
name|File
argument_list|(
name|data
index|[
literal|0
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|loaded
operator|.
name|add
argument_list|(
name|pr
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|" '"
operator|+
name|data
index|[
literal|0
index|]
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
if|if
condition|(
name|exportFile
operator|.
name|isInvoked
argument_list|()
condition|)
block|{
if|if
condition|(
name|loaded
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|String
index|[]
name|data
init|=
name|exportFile
operator|.
name|getStringValue
argument_list|()
operator|.
name|split
argument_list|(
literal|","
argument_list|)
decl_stmt|;
if|if
condition|(
name|data
operator|.
name|length
operator|==
literal|1
condition|)
block|{
comment|// This signals that the latest import should be stored in BibTeX format to the given file.
if|if
condition|(
name|loaded
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|ParserResult
name|pr
init|=
operator|(
name|ParserResult
operator|)
name|loaded
operator|.
name|elementAt
argument_list|(
name|loaded
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
try|try
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Saving"
argument_list|)
operator|+
literal|": "
operator|+
name|data
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|FileActions
operator|.
name|saveDatabase
argument_list|(
name|pr
operator|.
name|getDatabase
argument_list|()
argument_list|,
operator|new
name|MetaData
argument_list|(
name|pr
operator|.
name|getMetaData
argument_list|()
argument_list|)
argument_list|,
operator|new
name|File
argument_list|(
name|data
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|prefs
argument_list|,
literal|false
argument_list|,
literal|false
argument_list|,
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|SaveException
name|ex
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not save file"
argument_list|)
operator|+
literal|" '"
operator|+
name|data
index|[
literal|0
index|]
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
else|else
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"The output option depends on a valid import option."
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|data
operator|.
name|length
operator|==
literal|2
condition|)
block|{
comment|// This signals that the latest import should be stored in the given format to the given file.
name|ParserResult
name|pr
init|=
operator|(
name|ParserResult
operator|)
name|loaded
operator|.
name|elementAt
argument_list|(
name|loaded
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|)
decl_stmt|;
comment|// We first try to find a matching custom export format.
name|boolean
name|foundCustom
init|=
literal|false
decl_stmt|;
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|prefs
operator|.
name|customExports
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|format
init|=
name|prefs
operator|.
name|customExports
operator|.
name|getElementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
if|if
condition|(
name|format
index|[
literal|0
index|]
operator|.
name|equals
argument_list|(
name|data
index|[
literal|1
index|]
argument_list|)
condition|)
block|{
comment|// Found the correct export format here.
comment|//System.out.println(format[0]+" "+format[1]+" "+format[2]);
try|try
block|{
name|File
name|lfFile
init|=
operator|new
name|File
argument_list|(
name|format
index|[
literal|1
index|]
argument_list|)
decl_stmt|;
comment|//System.out.println(lfFile.getName());
name|String
name|fname
init|=
operator|(
name|lfFile
operator|.
name|getName
argument_list|()
operator|.
name|split
argument_list|(
literal|"\\."
argument_list|)
operator|)
index|[
literal|0
index|]
decl_stmt|;
name|FileActions
operator|.
name|exportDatabase
argument_list|(
name|pr
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|lfFile
operator|.
name|getParent
argument_list|()
operator|+
name|File
operator|.
name|separator
argument_list|,
name|fname
argument_list|,
operator|new
name|File
argument_list|(
name|data
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Exporting"
argument_list|)
operator|+
literal|": "
operator|+
name|data
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
comment|//ex.printStackTrace();
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not export file"
argument_list|)
operator|+
literal|" '"
operator|+
name|data
index|[
literal|0
index|]
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|foundCustom
operator|=
literal|true
expr_stmt|;
break|break;
block|}
block|}
if|if
condition|(
operator|!
name|foundCustom
condition|)
block|{
try|try
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Exporting"
argument_list|)
operator|+
literal|": "
operator|+
name|data
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|FileActions
operator|.
name|exportDatabase
argument_list|(
name|pr
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|data
index|[
literal|1
index|]
argument_list|,
operator|new
name|File
argument_list|(
name|data
index|[
literal|0
index|]
argument_list|)
argument_list|,
name|prefs
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NullPointerException
name|ex2
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Unknown export format"
argument_list|)
operator|+
literal|": "
operator|+
name|data
index|[
literal|1
index|]
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not export file"
argument_list|)
operator|+
literal|" '"
operator|+
name|data
index|[
literal|0
index|]
operator|+
literal|"': "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
block|}
else|else
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"The output option depends on a valid import option."
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|exportPrefs
operator|.
name|isInvoked
argument_list|()
condition|)
block|{
try|try
block|{
name|prefs
operator|.
name|exportPreferences
argument_list|(
name|exportPrefs
operator|.
name|getStringValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|Util
operator|.
name|pr
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|importPrefs
operator|.
name|isInvoked
argument_list|()
condition|)
block|{
try|try
block|{
name|prefs
operator|.
name|importPreferences
argument_list|(
name|importPrefs
operator|.
name|getStringValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
name|Util
operator|.
name|pr
argument_list|(
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/* 	if(args.length> 0) for (int i=0; i<args.length; i++) {           if (!args[i].startsWith("-")) {             // Load a bibtex file:             System.out.println(Globals.lang("Opening")+": " + args[i]);             try {               File file = new File(args[i]);               ParserResult pr = ImportFormatReader.loadDatabase(file);               pr.setFile(file);               loaded.add(pr);             } catch (IOException ex) {               System.err.println(Globals.lang("Error opening file")+" '"+ args[i]+"': "+ex.getMessage());             }              /*jrf.output("Opening: " + args[i]);             //verify the file             File f = new File (args[i]);             if( f.exists()&& f.canRead()&& f.isFile()) {               jrf.fileToOpen=f;               jrf.openDatabaseAction.openIt(true);               base = jrf.basePanel().database();             }else{               System.err.println("Error" + args[i] + " is not a valid file or is not readable");               //JOptionPane...             }/           } else {             // A command line switch.             if (args[i].startsWith("-i")&& (args[i].length()> 3)&& (args.length> i+1)) {               // Import a database in a certain format.               try {                 System.out.println(Globals.lang("Importing")+": " + args[i+1]);                 BibtexDatabase base = ImportFormatReader.importFile(args[i].substring(3), args[i+1]);                 ParserResult pr = new ParserResult(base, new HashMap());                 pr.setFile(new File(args[i+1]));                 loaded.add(pr);               } catch (IOException ex) {                 System.err.println(Globals.lang("Error opening file")+" '"+ args[i+1]+"': "+ex.getMessage());               }               i++;             }             else if (args[i].equals("-o")&& (args.length> i+1)) {               // This signals that the latest import should be stored in BibTeX format to the given file.                if (loaded.size()> 0) {                 ParserResult pr = (ParserResult)loaded.elementAt(loaded.size()-1);                 try {                   System.out.println(Globals.lang("Saving")+": "+args[i+1]);                   FileActions.saveDatabase(pr.getDatabase(),                                            new MetaData(pr.getMetaData()),                                            new File(args[i+1]),                                            prefs, false, false);                 } catch (SaveException ex) {                   System.err.println(Globals.lang("Could   not save file")+" '"+ args[i+1]+"': "+ex.getMessage());                 }               } else System.err.println(Globals.lang("The -o option must be preceded by an import option."));               i++;             }             else if (args[i].startsWith("-o")&& (args.length> i+1)) {               // The database should be exported to the named database in the format following "-o_"               if (loaded.size()> 0) {                 ParserResult pr = (ParserResult) loaded.elementAt(loaded.size() - 1);                 try {                   System.out.println(Globals.lang("Exporting")+": "+args[i+1]);                   FileActions.exportDatabase(pr.getDatabase(),                                              args[i].substring(3),                                              new File(args[i + 1]), prefs);                 }                 catch (IOException ex) {                   System.err.println(Globals.lang("Could not export file")+" '"+ args[i+1]+"': "+ex.getMessage());                 }                 catch (NullPointerException ex2) {                   System.err.println(Globals.lang("Unknown export format")+": "+args[i].substring(3));                 }                } else System.err.println(Globals.lang("The -o option must be preceded by an import option."));               i++;             }           }  	}else{//no arguments (this will be for later and other command line switches) 	    // ignore.. 	}*/
comment|//openGui = false;
if|if
condition|(
operator|!
name|graphicFailure
operator|&&
operator|!
name|disableGui
operator|.
name|isInvoked
argument_list|()
condition|)
block|{
comment|// If the option is enabled, open the last edited databases, if any.
if|if
condition|(
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"openLastEdited"
argument_list|)
operator|&&
operator|(
name|prefs
operator|.
name|get
argument_list|(
literal|"lastEdited"
argument_list|)
operator|!=
literal|null
operator|)
condition|)
block|{
comment|// How to handle errors in the databases to open?
name|String
index|[]
name|names
init|=
name|prefs
operator|.
name|getStringArray
argument_list|(
literal|"lastEdited"
argument_list|)
decl_stmt|;
name|lastEdLoop
label|:
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|names
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|File
name|fileToOpen
init|=
operator|new
name|File
argument_list|(
name|names
index|[
name|i
index|]
argument_list|)
decl_stmt|;
for|for
control|(
name|int
name|j
init|=
literal|0
init|;
name|j
operator|<
name|loaded
operator|.
name|size
argument_list|()
condition|;
name|j
operator|++
control|)
block|{
name|ParserResult
name|pr
init|=
operator|(
name|ParserResult
operator|)
name|loaded
operator|.
name|elementAt
argument_list|(
name|j
argument_list|)
decl_stmt|;
if|if
condition|(
name|pr
operator|.
name|getFile
argument_list|()
operator|.
name|equals
argument_list|(
name|fileToOpen
argument_list|)
condition|)
continue|continue
name|lastEdLoop
continue|;
block|}
if|if
condition|(
name|fileToOpen
operator|.
name|exists
argument_list|()
condition|)
block|{
name|ParserResult
name|pr
init|=
name|openBibFile
argument_list|(
name|names
index|[
name|i
index|]
argument_list|)
decl_stmt|;
if|if
condition|(
name|pr
operator|!=
literal|null
condition|)
name|loaded
operator|.
name|add
argument_list|(
name|pr
argument_list|)
expr_stmt|;
block|}
block|}
block|}
name|GUIGlobals
operator|.
name|init
argument_list|()
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
comment|//SwingUtilities.invokeLater(new Runnable() {
comment|//  public void run() {
name|jrf
operator|=
operator|new
name|JabRefFrame
argument_list|()
expr_stmt|;
comment|//  }
comment|//});
if|if
condition|(
name|loaded
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|loaded
operator|.
name|size
argument_list|()
condition|;
name|i
operator|++
control|)
block|{
name|ParserResult
name|pr
init|=
operator|(
name|ParserResult
operator|)
name|loaded
operator|.
name|elementAt
argument_list|(
name|i
argument_list|)
decl_stmt|;
name|jrf
operator|.
name|addTab
argument_list|(
name|pr
operator|.
name|getDatabase
argument_list|()
argument_list|,
name|pr
operator|.
name|getFile
argument_list|()
argument_list|,
name|pr
operator|.
name|getMetaData
argument_list|()
argument_list|,
operator|(
name|i
operator|==
literal|0
operator|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|loadSess
operator|.
name|isInvoked
argument_list|()
condition|)
block|{
name|jrf
operator|.
name|loadSessionAction
operator|.
name|actionPerformed
argument_list|(
operator|new
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionEvent
argument_list|(
name|jrf
argument_list|,
literal|0
argument_list|,
literal|""
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|ss
operator|.
name|dispose
argument_list|()
expr_stmt|;
name|jrf
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|loaded
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
name|jrf
operator|.
name|tabbedPane
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
operator|new
name|FocusRequester
argument_list|(
operator|(
operator|(
name|BasePanel
operator|)
name|jrf
operator|.
name|tabbedPane
operator|.
name|getComponentAt
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|entryTable
argument_list|)
expr_stmt|;
block|}
block|}
else|else
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
DECL|method|openBibFile (String name)
specifier|public
specifier|static
name|ParserResult
name|openBibFile
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Opening"
argument_list|)
operator|+
literal|": "
operator|+
name|name
argument_list|)
expr_stmt|;
try|try
block|{
name|File
name|file
init|=
operator|new
name|File
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|String
name|encoding
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultEncoding"
argument_list|)
decl_stmt|;
name|ParserResult
name|pr
init|=
name|ImportFormatReader
operator|.
name|loadDatabase
argument_list|(
name|file
argument_list|,
name|encoding
argument_list|)
decl_stmt|;
name|pr
operator|.
name|setFile
argument_list|(
name|file
argument_list|)
expr_stmt|;
return|return
name|pr
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ex
parameter_list|)
block|{
comment|//System.err.println(Globals.lang("Error opening file")+" '"+ name+"': "+ex.getMessage());
name|System
operator|.
name|err
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error opening file"
argument_list|)
operator|+
literal|": "
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

