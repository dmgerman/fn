begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.  */
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
name|java
operator|.
name|util
operator|.
name|Optional
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
name|JComboBox
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
name|JOptionPane
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
name|javax
operator|.
name|swing
operator|.
name|UIManager
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|help
operator|.
name|AboutDialog
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
name|help
operator|.
name|HelpFiles
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
name|help
operator|.
name|HelpAction
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|remote
operator|.
name|RemotePreferences
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
name|remote
operator|.
name|RemoteUtil
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
name|remote
operator|.
name|JabRefMessageHandler
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
name|util
operator|.
name|OS
import|;
end_import

begin_class
DECL|class|AdvancedTab
class|class
name|AdvancedTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|preferences
specifier|private
specifier|final
name|JabRefPreferences
name|preferences
decl_stmt|;
DECL|field|useDefault
specifier|private
specifier|final
name|JCheckBox
name|useDefault
decl_stmt|;
DECL|field|useRemoteServer
specifier|private
specifier|final
name|JCheckBox
name|useRemoteServer
decl_stmt|;
DECL|field|useIEEEAbrv
specifier|private
specifier|final
name|JCheckBox
name|useIEEEAbrv
decl_stmt|;
DECL|field|biblatexMode
specifier|private
specifier|final
name|JCheckBox
name|biblatexMode
decl_stmt|;
DECL|field|className
specifier|private
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|className
decl_stmt|;
DECL|field|remoteServerPort
specifier|private
specifier|final
name|JTextField
name|remoteServerPort
decl_stmt|;
DECL|field|oldLnf
specifier|private
name|String
name|oldLnf
init|=
literal|""
decl_stmt|;
DECL|field|oldUseDef
specifier|private
name|boolean
name|oldUseDef
decl_stmt|;
DECL|field|oldBiblMode
specifier|private
name|boolean
name|oldBiblMode
decl_stmt|;
DECL|field|useConvertToEquation
specifier|private
specifier|final
name|JCheckBox
name|useConvertToEquation
decl_stmt|;
DECL|field|useCaseKeeperOnSearch
specifier|private
specifier|final
name|JCheckBox
name|useCaseKeeperOnSearch
decl_stmt|;
DECL|field|useUnitFormatterOnSearch
specifier|private
specifier|final
name|JCheckBox
name|useUnitFormatterOnSearch
decl_stmt|;
DECL|field|jabRef
specifier|private
specifier|final
name|JabRef
name|jabRef
decl_stmt|;
DECL|field|remotePreferences
specifier|private
specifier|final
name|RemotePreferences
name|remotePreferences
decl_stmt|;
DECL|method|AdvancedTab (JabRefPreferences prefs, AboutDialog diag, JabRef jabRef)
specifier|public
name|AdvancedTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|AboutDialog
name|diag
parameter_list|,
name|JabRef
name|jabRef
parameter_list|)
block|{
name|this
operator|.
name|jabRef
operator|=
name|jabRef
expr_stmt|;
name|preferences
operator|=
name|prefs
expr_stmt|;
name|this
operator|.
name|remotePreferences
operator|=
operator|new
name|RemotePreferences
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
name|useDefault
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Listen for remote operation on port"
argument_list|)
operator|+
literal|':'
argument_list|)
expr_stmt|;
name|useIEEEAbrv
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 mode"
argument_list|,
literal|"BibLaTeX"
argument_list|)
argument_list|)
expr_stmt|;
name|remoteServerPort
operator|=
operator|new
name|JTextField
argument_list|()
expr_stmt|;
name|String
index|[]
name|possibleLookAndFeels
init|=
block|{
name|UIManager
operator|.
name|getSystemLookAndFeelClassName
argument_list|()
block|,
name|UIManager
operator|.
name|getCrossPlatformLookAndFeelClassName
argument_list|()
block|,
literal|"com.jgoodies.looks.plastic.Plastic3DLookAndFeel"
block|,
literal|"com.jgoodies.looks.windows.WindowsLookAndFeel"
block|}
decl_stmt|;
comment|// Only list L&F which are available
name|List
argument_list|<
name|String
argument_list|>
name|lookAndFeels
init|=
operator|new
name|ArrayList
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|lf
range|:
name|possibleLookAndFeels
control|)
block|{
try|try
block|{
comment|// Try to find L&F, throws exception if not successful
name|Class
operator|.
name|forName
argument_list|(
name|lf
argument_list|)
expr_stmt|;
name|lookAndFeels
operator|.
name|add
argument_list|(
name|lf
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|ignored
parameter_list|)
block|{
comment|// Ignored
block|}
block|}
name|className
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|lookAndFeels
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|lookAndFeels
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|className
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
specifier|final
name|JComboBox
argument_list|<
name|String
argument_list|>
name|clName
init|=
name|className
decl_stmt|;
name|useDefault
operator|.
name|addChangeListener
argument_list|(
name|e
lambda|->
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
argument_list|)
expr_stmt|;
name|useConvertToEquation
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add {} to specified title words on search to keep the correct case"
argument_list|)
argument_list|)
expr_stmt|;
name|useUnitFormatterOnSearch
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Format units by adding non-breaking separators and keeping the correct case on search"
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
name|OS
operator|.
name|OS_X
condition|)
block|{
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
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
name|Localization
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Class name"
argument_list|)
operator|+
literal|':'
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
name|Localization
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
name|Localization
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
name|Localization
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
name|Localization
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
operator|new
name|HelpAction
argument_list|(
name|HelpFiles
operator|.
name|remoteHelp
argument_list|)
operator|.
name|getHelpButton
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"Search %0"
argument_list|,
literal|"IEEEXplorer"
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"%0 mode"
argument_list|,
literal|"BibLaTeX"
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
name|Localization
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
name|useUnitFormatterOnSearch
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
annotation|@
name|Override
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|oldUseDef
operator|=
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_LOOK_AND_FEEL
argument_list|)
expr_stmt|;
name|oldLnf
operator|=
name|preferences
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WIN_LOOK_AND_FEEL
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
name|setSelectedItem
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
name|remotePreferences
operator|.
name|useRemoteServer
argument_list|()
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
name|remotePreferences
operator|.
name|getPort
argument_list|()
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
name|JabRefPreferences
operator|.
name|USE_IEEE_ABRV
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
name|JabRefPreferences
operator|.
name|BIBLATEX_MODE
argument_list|)
expr_stmt|;
name|biblatexMode
operator|.
name|setSelected
argument_list|(
name|oldBiblMode
argument_list|)
expr_stmt|;
name|useConvertToEquation
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_CONVERT_TO_EQUATION
argument_list|)
argument_list|)
expr_stmt|;
name|useCaseKeeperOnSearch
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_CASE_KEEPER_ON_SEARCH
argument_list|)
argument_list|)
expr_stmt|;
name|useUnitFormatterOnSearch
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_UNIT_FORMATTER_ON_SEARCH
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
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_DEFAULT_LOOK_AND_FEEL
argument_list|,
operator|!
name|useDefault
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|WIN_LOOK_AND_FEEL
argument_list|,
name|className
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|preferences
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_IEEE_ABRV
argument_list|)
operator|!=
name|useIEEEAbrv
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_IEEE_ABRV
argument_list|,
name|useIEEEAbrv
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|journalAbbreviationLoader
operator|.
name|update
argument_list|(
name|preferences
argument_list|)
expr_stmt|;
block|}
name|storeRemoteSettings
argument_list|()
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|BIBLATEX_MODE
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
name|getSelectedItem
argument_list|()
operator|.
name|toString
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
name|Localization
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
literal|"Changed look and feel settings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_CONVERT_TO_EQUATION
argument_list|,
name|useConvertToEquation
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_CASE_KEEPER_ON_SEARCH
argument_list|,
name|useCaseKeeperOnSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|preferences
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_UNIT_FORMATTER_ON_SEARCH
argument_list|,
name|useUnitFormatterOnSearch
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|storeRemoteSettings ()
specifier|public
name|void
name|storeRemoteSettings
parameter_list|()
block|{
name|getPortAsInt
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|newPort
lambda|->
block|{
if|if
condition|(
name|remotePreferences
operator|.
name|isDifferentPort
argument_list|(
name|newPort
argument_list|)
condition|)
block|{
name|remotePreferences
operator|.
name|setPort
argument_list|(
name|newPort
argument_list|)
expr_stmt|;
if|if
condition|(
name|remotePreferences
operator|.
name|useRemoteServer
argument_list|()
condition|)
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
literal|"Remote server port"
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
literal|"Remote server port"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
name|remotePreferences
operator|.
name|setUseRemoteServer
argument_list|(
name|useRemoteServer
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|remotePreferences
operator|.
name|useRemoteServer
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|remoteListener
operator|.
name|openAndStart
argument_list|(
operator|new
name|JabRefMessageHandler
argument_list|(
name|jabRef
argument_list|)
argument_list|,
name|remotePreferences
operator|.
name|getPort
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Globals
operator|.
name|remoteListener
operator|.
name|stop
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|getPortAsInt ()
specifier|public
name|Optional
argument_list|<
name|Integer
argument_list|>
name|getPortAsInt
parameter_list|()
block|{
try|try
block|{
return|return
name|Optional
operator|.
name|of
argument_list|(
name|Integer
operator|.
name|parseInt
argument_list|(
name|remoteServerPort
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|NumberFormatException
name|ex
parameter_list|)
block|{
return|return
name|Optional
operator|.
name|empty
argument_list|()
return|;
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
name|RemoteUtil
operator|.
name|isUserPort
argument_list|(
name|portNumber
argument_list|)
condition|)
block|{
return|return
literal|true
return|;
comment|// Ok, the number was legal.
block|}
else|else
block|{
throw|throw
operator|new
name|NumberFormatException
argument_list|()
throw|;
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
name|Localization
operator|.
name|lang
argument_list|(
literal|"You must enter an integer value in the interval 1025-65535 in the text field for"
argument_list|)
operator|+
literal|" '"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remote server port"
argument_list|)
operator|+
literal|'\''
argument_list|,
name|Localization
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
literal|"Advanced"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

