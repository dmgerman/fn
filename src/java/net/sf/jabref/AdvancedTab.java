begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2011 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|awt
operator|.
name|BorderLayout
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
name|event
operator|.
name|ChangeEvent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|event
operator|.
name|ChangeListener
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
name|journals
operator|.
name|JournalAbbreviations
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
name|remote
operator|.
name|RemoteListener
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

begin_class
DECL|class|AdvancedTab
specifier|public
class|class
name|AdvancedTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|field|helpDiag
name|HelpDialog
name|helpDiag
decl_stmt|;
DECL|field|remoteHelp
name|HelpAction
name|remoteHelp
decl_stmt|;
DECL|field|pan
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|lnf
name|lnf
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|lab
name|JLabel
name|lab
decl_stmt|;
DECL|field|useDefault
DECL|field|useRemoteServer
DECL|field|useNativeFileDialogOnMac
DECL|field|filechooserDisableRename
name|JCheckBox
name|useDefault
decl_stmt|,
name|useRemoteServer
decl_stmt|,
name|useNativeFileDialogOnMac
decl_stmt|,
name|filechooserDisableRename
decl_stmt|,
DECL|field|useIEEEAbrv
DECL|field|biblatexMode
name|useIEEEAbrv
decl_stmt|,
name|biblatexMode
decl_stmt|;
DECL|field|className
DECL|field|remoteServerPort
name|JTextField
name|className
decl_stmt|,
name|remoteServerPort
decl_stmt|;
DECL|field|def1
name|JButton
name|def1
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|def2
name|def2
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|p1
name|JPanel
name|p1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|p2
name|p2
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|oldLnf
name|String
name|oldLnf
init|=
literal|""
decl_stmt|;
DECL|field|oldUseDef
DECL|field|oldBiblMode
DECL|field|oldConvertToEquation
DECL|field|oldCaseKeeperOnSearch
name|boolean
name|oldUseDef
decl_stmt|,
name|oldBiblMode
init|=
literal|false
decl_stmt|,
name|oldConvertToEquation
decl_stmt|,
name|oldCaseKeeperOnSearch
decl_stmt|;
DECL|field|oldPort
name|int
name|oldPort
init|=
operator|-
literal|1
decl_stmt|;
DECL|field|PREF_IMPORT_CONVERT_TO_EQUATION
specifier|public
specifier|final
specifier|static
name|String
name|PREF_IMPORT_CONVERT_TO_EQUATION
init|=
literal|"importFileConvertToEquation"
decl_stmt|;
DECL|field|PREF_IMPORT_FILENAMEPATTERN
specifier|public
specifier|final
specifier|static
name|String
name|PREF_IMPORT_FILENAMEPATTERN
init|=
literal|"importFileNamePattern"
decl_stmt|;
DECL|field|useConvertToEquation
specifier|private
name|JCheckBox
name|useConvertToEquation
decl_stmt|;
DECL|field|useCaseKeeperOnSearch
specifier|private
name|JCheckBox
name|useCaseKeeperOnSearch
decl_stmt|;
DECL|method|AdvancedTab (JabRefPreferences prefs, HelpDialog diag)
specifier|public
name|AdvancedTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|HelpDialog
name|diag
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|remoteHelp
operator|=
operator|new
name|HelpAction
argument_list|(
name|diag
argument_list|,
name|GUIGlobals
operator|.
name|remoteHelp
argument_list|,
literal|"Help"
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
expr_stmt|;
name|useDefault
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use other look and feel"
argument_list|)
argument_list|)
expr_stmt|;
name|useRemoteServer
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Listen for remote operation on port"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|useNativeFileDialogOnMac
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use native file dialog"
argument_list|)
argument_list|)
expr_stmt|;
name|filechooserDisableRename
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Disable file renaming in non-native file dialog"
argument_list|)
argument_list|)
expr_stmt|;
name|useIEEEAbrv
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use IEEE LaTeX abbreviations"
argument_list|)
argument_list|)
expr_stmt|;
name|biblatexMode
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"BibLaTeX mode"
argument_list|)
argument_list|)
expr_stmt|;
name|remoteServerPort
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|className
operator|=
operator|new
name|JTextField
argument_list|(
literal|50
argument_list|)
expr_stmt|;
specifier|final
name|JTextField
name|clName
init|=
name|className
decl_stmt|;
name|useDefault
operator|.
name|addChangeListener
argument_list|(
operator|new
name|ChangeListener
argument_list|()
block|{
specifier|public
name|void
name|stateChanged
parameter_list|(
name|ChangeEvent
name|e
parameter_list|)
block|{
name|clName
operator|.
name|setEnabled
argument_list|(
operator|(
operator|(
name|JCheckBox
operator|)
name|e
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|useConvertToEquation
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Prefer converting subscripts and superscripts to equations rather than text"
argument_list|)
argument_list|)
expr_stmt|;
name|useCaseKeeperOnSearch
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Add {} to specified title words on search to keep the correct case"
argument_list|)
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:3dlu"
argument_list|,
comment|//, 4dlu, fill:pref",// 4dlu, left:pref, 4dlu",
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
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|Globals
operator|.
name|ON_MAC
condition|)
block|{
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Look and feel"
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default look and feel"
argument_list|)
operator|+
literal|": "
operator|+
name|UIManager
operator|.
name|getSystemLookAndFeelClassName
argument_list|()
argument_list|)
decl_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lab
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
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useDefault
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
name|pan
argument_list|)
expr_stmt|;
name|JPanel
name|pan2
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
literal|"Class name"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|pan2
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|pan2
operator|.
name|add
argument_list|(
name|className
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan2
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
name|pan
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Note that you must specify the fully qualified class name for the look and feel,"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lab
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
name|pan
argument_list|)
expr_stmt|;
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"and the class must be available in your classpath next time you start JabRef."
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
block|}
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remote operation"
argument_list|)
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JLabel
argument_list|(
literal|"<html>"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"This feature lets new files be opened or imported into an "
operator|+
literal|"already running instance of JabRef<BR>instead of opening a new instance. For instance, this "
operator|+
literal|"is useful when you open a file in JabRef<br>from your web browser."
operator|+
literal|"<BR>Note that this will prevent you from running more than one instance of JabRef at a time."
argument_list|)
operator|+
literal|"</html>"
argument_list|)
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|JPanel
name|p
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|p
operator|.
name|add
argument_list|(
name|useRemoteServer
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
name|remoteServerPort
argument_list|)
expr_stmt|;
name|p
operator|.
name|add
argument_list|(
name|remoteHelp
operator|.
name|getIconButton
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|p
argument_list|)
expr_stmt|;
comment|//if (Globals.ON_MAC) {
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
literal|"File dialog"
argument_list|)
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useNativeFileDialogOnMac
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|filechooserDisableRename
argument_list|)
expr_stmt|;
comment|//}
comment|// IEEE
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
literal|"Search IEEEXplore"
argument_list|)
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useIEEEAbrv
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
literal|"BibLaTeX mode"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|biblatexMode
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
literal|"Import conversions"
argument_list|)
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useConvertToEquation
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
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useCaseKeeperOnSearch
argument_list|)
expr_stmt|;
name|pan
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
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
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
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
name|oldUseDef
operator|=
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"useDefaultLookAndFeel"
argument_list|)
expr_stmt|;
name|oldLnf
operator|=
name|_prefs
operator|.
name|get
argument_list|(
literal|"lookAndFeel"
argument_list|)
expr_stmt|;
name|useDefault
operator|.
name|setSelected
argument_list|(
operator|!
name|oldUseDef
argument_list|)
expr_stmt|;
name|className
operator|.
name|setText
argument_list|(
name|oldLnf
argument_list|)
expr_stmt|;
name|className
operator|.
name|setEnabled
argument_list|(
operator|!
name|oldUseDef
argument_list|)
expr_stmt|;
name|useRemoteServer
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"useRemoteServer"
argument_list|)
argument_list|)
expr_stmt|;
name|oldPort
operator|=
name|_prefs
operator|.
name|getInt
argument_list|(
literal|"remoteServerPort"
argument_list|)
expr_stmt|;
name|remoteServerPort
operator|.
name|setText
argument_list|(
name|String
operator|.
name|valueOf
argument_list|(
name|oldPort
argument_list|)
argument_list|)
expr_stmt|;
name|useNativeFileDialogOnMac
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useNativeFileDialogOnMac"
argument_list|)
argument_list|)
expr_stmt|;
name|filechooserDisableRename
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"filechooserDisableRename"
argument_list|)
argument_list|)
expr_stmt|;
name|useIEEEAbrv
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useIEEEAbrv"
argument_list|)
argument_list|)
expr_stmt|;
name|oldBiblMode
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"biblatexMode"
argument_list|)
expr_stmt|;
name|biblatexMode
operator|.
name|setSelected
argument_list|(
name|oldBiblMode
argument_list|)
expr_stmt|;
name|oldConvertToEquation
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useConvertToEquation"
argument_list|)
expr_stmt|;
name|useConvertToEquation
operator|.
name|setSelected
argument_list|(
name|oldConvertToEquation
argument_list|)
expr_stmt|;
name|oldCaseKeeperOnSearch
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"useCaseKeeperOnSearch"
argument_list|)
expr_stmt|;
name|useCaseKeeperOnSearch
operator|.
name|setSelected
argument_list|(
name|oldCaseKeeperOnSearch
argument_list|)
expr_stmt|;
block|}
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
literal|"useDefaultLookAndFeel"
argument_list|,
operator|!
name|useDefault
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"lookAndFeel"
argument_list|,
name|className
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useNativeFileDialogOnMac"
argument_list|,
name|useNativeFileDialogOnMac
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"filechooserDisableRename"
argument_list|,
name|filechooserDisableRename
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|UIManager
operator|.
name|put
argument_list|(
literal|"FileChooser.readOnly"
argument_list|,
name|filechooserDisableRename
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useIEEEAbrv"
argument_list|,
name|useIEEEAbrv
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|useIEEEAbrv
operator|.
name|isSelected
argument_list|()
condition|)
name|Globals
operator|.
name|journalAbbrev
operator|=
operator|new
name|JournalAbbreviations
argument_list|(
literal|"/resource/IEEEJournalList.txt"
argument_list|)
expr_stmt|;
try|try
block|{
name|int
name|port
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|remoteServerPort
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|port
operator|!=
name|oldPort
condition|)
block|{
name|_prefs
operator|.
name|putInt
argument_list|(
literal|"remoteServerPort"
argument_list|,
name|port
argument_list|)
expr_stmt|;
comment|/*JOptionPane.showMessageDialog(null, Glbals.lang("You have changed the menu and label font size. "                         + "You must restart JabRef for this to come into effect."), Globals.lang("Changed font settings"),                         JOptionPane.WARNING_MESSAGE);*/
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
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useRemoteServer"
argument_list|,
name|useRemoteServer
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|useRemoteServer
operator|.
name|isSelected
argument_list|()
operator|&&
operator|(
name|JabRef
operator|.
name|remoteListener
operator|==
literal|null
operator|)
condition|)
block|{
comment|// Start the listener now.
name|JabRef
operator|.
name|remoteListener
operator|=
name|RemoteListener
operator|.
name|openRemoteListener
argument_list|(
name|JabRef
operator|.
name|singleton
argument_list|)
expr_stmt|;
if|if
condition|(
name|JabRef
operator|.
name|remoteListener
operator|!=
literal|null
condition|)
block|{
name|JabRef
operator|.
name|remoteListener
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|useRemoteServer
operator|.
name|isSelected
argument_list|()
operator|&&
operator|(
name|JabRef
operator|.
name|remoteListener
operator|!=
literal|null
operator|)
condition|)
block|{
name|JabRef
operator|.
name|remoteListener
operator|.
name|disable
argument_list|()
expr_stmt|;
name|JabRef
operator|.
name|remoteListener
operator|=
literal|null
expr_stmt|;
block|}
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"biblatexMode"
argument_list|,
name|biblatexMode
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|useDefault
operator|.
name|isSelected
argument_list|()
operator|==
name|oldUseDef
operator|)
operator|||
operator|!
name|oldLnf
operator|.
name|equals
argument_list|(
name|className
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
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
literal|"You have changed the look and feel setting."
argument_list|)
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
operator|.
name|concat
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Changed look and feel settings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|biblatexMode
operator|.
name|isSelected
argument_list|()
operator|!=
name|oldBiblMode
condition|)
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
literal|"You have toggled the BibLaTeX mode."
argument_list|)
operator|.
name|concat
argument_list|(
literal|" "
argument_list|)
operator|.
name|concat
argument_list|(
literal|"You must restart JabRef for this change to come into effect."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"BibLaTeX mode"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useConvertToEquation"
argument_list|,
name|useConvertToEquation
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useCaseKeeperOnSearch"
argument_list|,
name|useCaseKeeperOnSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
parameter_list|()
block|{
try|try
block|{
name|int
name|portNumber
init|=
name|Integer
operator|.
name|parseInt
argument_list|(
name|remoteServerPort
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|portNumber
operator|>
literal|1024
operator|&&
name|portNumber
operator|<=
literal|65535
condition|)
return|return
literal|true
return|;
comment|// Ok, the number was legal.
else|else
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
literal|"You must enter an integer value in the interval 1025-65535 in the text field for"
argument_list|)
operator|+
literal|" '"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remote server port"
argument_list|)
operator|+
literal|"'"
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remote server port"
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
literal|"You must enter an integer value in the interval 1025-65535 in the text field for"
argument_list|)
operator|+
literal|" '"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remote server port"
argument_list|)
operator|+
literal|"'"
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Remote server port"
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
block|}
DECL|method|getTabName ()
specifier|public
name|String
name|getTabName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Advanced"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

