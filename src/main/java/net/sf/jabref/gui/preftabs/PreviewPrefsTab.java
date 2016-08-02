begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|Dimension
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GridBagConstraints
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
name|Insets
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JButton
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
name|JScrollPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JSeparator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextArea
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingConstants
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
name|PreviewPanel
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
name|util
operator|.
name|TestEntry
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
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
import|;
end_import

begin_class
DECL|class|PreviewPrefsTab
class|class
name|PreviewPrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|PrefsTab
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
DECL|field|layout1
specifier|private
specifier|final
name|JTextArea
name|layout1
init|=
operator|new
name|JTextArea
argument_list|(
literal|""
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
decl_stmt|;
DECL|field|layout2
specifier|private
specifier|final
name|JTextArea
name|layout2
init|=
operator|new
name|JTextArea
argument_list|(
literal|""
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
decl_stmt|;
DECL|field|testButton
specifier|private
specifier|final
name|JButton
name|testButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Test"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|defaultButton
specifier|private
specifier|final
name|JButton
name|defaultButton
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|testButton2
specifier|private
specifier|final
name|JButton
name|testButton2
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Test"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|defaultButton2
specifier|private
specifier|final
name|JButton
name|defaultButton2
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|firstPanel
specifier|private
specifier|final
name|JPanel
name|firstPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|firstScrollPane
specifier|private
specifier|final
name|JScrollPane
name|firstScrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|layout1
argument_list|)
decl_stmt|;
DECL|field|secondPanel
specifier|private
specifier|final
name|JPanel
name|secondPanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|secondScrollPane
specifier|private
specifier|final
name|JScrollPane
name|secondScrollPane
init|=
operator|new
name|JScrollPane
argument_list|(
name|layout2
argument_list|)
decl_stmt|;
DECL|method|PreviewPrefsTab (JabRefPreferences prefs)
specifier|public
name|PreviewPrefsTab
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
name|GridBagLayout
name|layout
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
name|firstPanel
operator|.
name|setLayout
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|secondPanel
operator|.
name|setLayout
argument_list|(
name|layout
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
name|layout
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
literal|"Preview"
argument_list|)
operator|+
literal|" 1"
argument_list|)
decl_stmt|;
name|GridBagConstraints
name|layoutConstraints
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|layoutConstraints
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|layoutConstraints
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|layoutConstraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|layoutConstraints
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|layoutConstraints
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|firstScrollPane
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|firstPanel
operator|.
name|add
argument_list|(
name|firstScrollPane
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|layoutConstraints
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|layoutConstraints
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|layoutConstraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|layoutConstraints
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|testButton
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|firstPanel
operator|.
name|add
argument_list|(
name|testButton
argument_list|)
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|defaultButton
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|firstPanel
operator|.
name|add
argument_list|(
name|defaultButton
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|JPanel
name|newPan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|layoutConstraints
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|newPan
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|firstPanel
operator|.
name|add
argument_list|(
name|newPan
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
literal|"Preview"
argument_list|)
operator|+
literal|" 2"
argument_list|)
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
comment|// p2.add(lab);
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|layoutConstraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|secondScrollPane
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|secondPanel
operator|.
name|add
argument_list|(
name|secondScrollPane
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|layoutConstraints
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|layoutConstraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|layoutConstraints
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|testButton2
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|secondPanel
operator|.
name|add
argument_list|(
name|testButton2
argument_list|)
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|defaultButton2
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|secondPanel
operator|.
name|add
argument_list|(
name|defaultButton2
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|newPan
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|layoutConstraints
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|newPan
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|secondPanel
operator|.
name|add
argument_list|(
name|newPan
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|layoutConstraints
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|layoutConstraints
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
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
literal|"Preview"
argument_list|)
operator|+
literal|" 1"
argument_list|)
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|firstPanel
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|firstPanel
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
literal|"Preview"
argument_list|)
operator|+
literal|" 2"
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|JSeparator
name|sep
init|=
operator|new
name|JSeparator
argument_list|(
name|SwingConstants
operator|.
name|HORIZONTAL
argument_list|)
decl_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|sep
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|sep
argument_list|)
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|layout
operator|.
name|setConstraints
argument_list|(
name|secondPanel
argument_list|,
name|layoutConstraints
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|secondPanel
argument_list|)
expr_stmt|;
name|layoutConstraints
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|defaultButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|String
name|tmp
init|=
name|layout1
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
decl_stmt|;
name|PreviewPrefsTab
operator|.
name|this
operator|.
name|prefs
operator|.
name|remove
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_0
argument_list|)
expr_stmt|;
name|layout1
operator|.
name|setText
argument_list|(
name|PreviewPrefsTab
operator|.
name|this
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_0
argument_list|)
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|this
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_0
argument_list|,
name|tmp
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|defaultButton2
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
name|String
name|tmp
init|=
name|layout2
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
decl_stmt|;
name|PreviewPrefsTab
operator|.
name|this
operator|.
name|prefs
operator|.
name|remove
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_1
argument_list|)
expr_stmt|;
name|layout2
operator|.
name|setText
argument_list|(
name|PreviewPrefsTab
operator|.
name|this
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_1
argument_list|)
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|this
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_1
argument_list|,
name|tmp
argument_list|)
expr_stmt|;
block|}
argument_list|)
expr_stmt|;
name|testButton
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
try|try
block|{
name|PreviewPanel
name|testPanel
init|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
name|TestEntry
operator|.
name|getTestEntry
argument_list|()
argument_list|,
literal|null
argument_list|,
name|layout1
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|testPanel
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|800
argument_list|,
literal|350
argument_list|)
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|testPanel
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|PLAIN_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Parsing error."
argument_list|,
name|ex
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
literal|"Parsing error"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"illegal backslash expression"
argument_list|)
operator|+
literal|".\n"
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Parsing error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|testButton2
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
try|try
block|{
name|PreviewPanel
name|testPanel
init|=
operator|new
name|PreviewPanel
argument_list|(
literal|null
argument_list|,
name|TestEntry
operator|.
name|getTestEntry
argument_list|()
argument_list|,
literal|null
argument_list|,
name|layout2
operator|.
name|getText
argument_list|()
argument_list|)
decl_stmt|;
name|testPanel
operator|.
name|setPreferredSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|800
argument_list|,
literal|350
argument_list|)
argument_list|)
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
operator|new
name|JScrollPane
argument_list|(
name|testPanel
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|PLAIN_MESSAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|StringIndexOutOfBoundsException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Parsing error."
argument_list|,
name|ex
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
literal|"Parsing error"
argument_list|)
operator|+
literal|": "
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"illegal backslash expression"
argument_list|)
operator|+
literal|".\n"
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Parsing error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
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
name|layout1
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_0
argument_list|)
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|layout2
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_1
argument_list|)
operator|.
name|replace
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
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
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_0
argument_list|,
name|layout1
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_1
argument_list|,
name|layout2
operator|.
name|getText
argument_list|()
operator|.
name|replace
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
argument_list|)
expr_stmt|;
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
literal|"Entry preview"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

