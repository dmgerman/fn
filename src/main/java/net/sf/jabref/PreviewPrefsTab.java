begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2012 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
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
name|gui
operator|.
name|help
operator|.
name|HelpAction
import|;
end_import

begin_class
DECL|class|PreviewPrefsTab
specifier|public
class|class
name|PreviewPrefsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|_prefs
specifier|private
specifier|final
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|field|pan
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
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
DECL|field|help
name|JButton
name|help
decl_stmt|;
DECL|field|pdfPreview
specifier|private
specifier|final
name|JCheckBox
name|pdfPreview
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Enable PDF preview"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|entry
specifier|private
specifier|static
name|BibtexEntry
name|entry
decl_stmt|;
DECL|method|PreviewPrefsTab (JabRefPreferences prefs)
specifier|public
name|PreviewPrefsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|JPanel
name|p1
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
name|p1
operator|.
name|setLayout
argument_list|(
name|gbl
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
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|JLabel
name|lab
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
literal|"Preview"
argument_list|)
operator|+
literal|" 1"
argument_list|)
expr_stmt|;
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
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
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
comment|// p1.add(lab);
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|JScrollPane
name|sp1
init|=
operator|new
name|JScrollPane
argument_list|(
name|layout1
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|p1
operator|.
name|add
argument_list|(
name|sp1
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|WEST
expr_stmt|;
name|JButton
name|test1
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Test"
argument_list|)
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|test1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|p1
operator|.
name|add
argument_list|(
name|test1
argument_list|)
expr_stmt|;
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
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|def1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|p1
operator|.
name|add
argument_list|(
name|def1
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|pan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|p1
operator|.
name|add
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
literal|"Preview"
argument_list|)
operator|+
literal|" 2"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
comment|// p2.add(lab);
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|JScrollPane
name|sp2
init|=
operator|new
name|JScrollPane
argument_list|(
name|layout2
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp2
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|p2
operator|.
name|add
argument_list|(
name|sp2
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|NONE
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|JButton
name|test2
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Test"
argument_list|)
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|test2
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|p2
operator|.
name|add
argument_list|(
name|test2
argument_list|)
expr_stmt|;
name|JButton
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
name|gbl
operator|.
name|setConstraints
argument_list|(
name|def2
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|p2
operator|.
name|add
argument_list|(
name|def2
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|pan
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|pan
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|p2
operator|.
name|add
argument_list|(
name|pan
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|BOTH
expr_stmt|;
name|con
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Preview"
argument_list|)
operator|+
literal|" 1"
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|p1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|p1
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
literal|"Preview"
argument_list|)
operator|+
literal|" 2"
argument_list|)
expr_stmt|;
name|con
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
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sep
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|sep
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|p2
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|p2
argument_list|)
expr_stmt|;
comment|// PDF Preview button
name|JPanel
name|p3
init|=
operator|new
name|JPanel
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
decl_stmt|;
name|p3
operator|.
name|add
argument_list|(
name|pdfPreview
argument_list|,
name|BorderLayout
operator|.
name|WEST
argument_list|)
expr_stmt|;
block|{
comment|// Help Button
name|HelpAction
name|helpAction
init|=
operator|new
name|HelpAction
argument_list|(
name|Globals
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|previewHelp
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Help on Preview Settings"
argument_list|)
argument_list|,
name|GUIGlobals
operator|.
name|getIconUrl
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|help
init|=
name|helpAction
operator|.
name|getIconButton
argument_list|()
decl_stmt|;
name|p3
operator|.
name|add
argument_list|(
name|help
argument_list|,
name|BorderLayout
operator|.
name|EAST
argument_list|)
expr_stmt|;
block|}
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|p3
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|p3
argument_list|)
expr_stmt|;
name|def1
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
name|String
name|tmp
init|=
name|layout1
operator|.
name|getText
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
decl_stmt|;
name|_prefs
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
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_0
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|_prefs
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
block|}
argument_list|)
expr_stmt|;
name|def2
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
name|String
name|tmp
init|=
name|layout2
operator|.
name|getText
argument_list|()
operator|.
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
decl_stmt|;
name|_prefs
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
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_1
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|_prefs
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
block|}
argument_list|)
expr_stmt|;
name|test1
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
name|PreviewPrefsTab
operator|.
name|getTestEntry
argument_list|()
expr_stmt|;
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
name|PreviewPrefsTab
operator|.
name|entry
argument_list|,
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
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
name|Globals
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
name|ex
operator|.
name|printStackTrace
argument_list|()
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
literal|"Parsing error"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
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
operator|+
literal|'\n'
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Look at stderr for details"
argument_list|)
operator|+
literal|'.'
argument_list|,
name|Globals
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
block|}
argument_list|)
expr_stmt|;
name|test2
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
name|PreviewPrefsTab
operator|.
name|getTestEntry
argument_list|()
expr_stmt|;
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
name|PreviewPrefsTab
operator|.
name|entry
argument_list|,
literal|null
argument_list|,
operator|new
name|MetaData
argument_list|()
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
name|Globals
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
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
literal|"Parsing error: illegal backslash expression.\n"
operator|+
name|ex
operator|.
name|getMessage
argument_list|()
operator|+
literal|"\nLook at stderr for details."
argument_list|,
literal|"Parsing error"
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|getTestEntry ()
specifier|private
specifier|static
name|BibtexEntry
name|getTestEntry
parameter_list|()
block|{
if|if
condition|(
name|PreviewPrefsTab
operator|.
name|entry
operator|!=
literal|null
condition|)
block|{
return|return
name|PreviewPrefsTab
operator|.
name|entry
return|;
block|}
name|PreviewPrefsTab
operator|.
name|entry
operator|=
operator|new
name|BibtexEntry
argument_list|(
name|IdGenerator
operator|.
name|next
argument_list|()
argument_list|,
name|BibtexEntryType
operator|.
name|getType
argument_list|(
literal|"article"
argument_list|)
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
name|BibtexFields
operator|.
name|KEY_FIELD
argument_list|,
literal|"conceicao1997"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"author"
argument_list|,
literal|"Luis E. C. Conceic{\\~a}o and Terje van der Meeren and Johan A. J. Verreth and M S. Evjen and D. F. Houlihan and H. J. Fyhn"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"title"
argument_list|,
literal|"Amino acid metabolism and protein turnover in larval turbot (Scophthalmus maximus) fed natural zooplankton or Artemia"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"year"
argument_list|,
literal|"1997"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"journal"
argument_list|,
literal|"Marine Biology"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"month"
argument_list|,
literal|"January"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"number"
argument_list|,
literal|"2"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"volume"
argument_list|,
literal|"123"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"pdf"
argument_list|,
literal|"conceicao1997.pdf"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"pages"
argument_list|,
literal|"255--265"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"keywords"
argument_list|,
literal|"energetics, artemia, metabolism, amino acid, turbot"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"url"
argument_list|,
literal|"http://ejournals.ebsco.com/direct.asp?ArticleID=TYY4NT82XA9H7R8PFPPV"
argument_list|)
expr_stmt|;
name|PreviewPrefsTab
operator|.
name|entry
operator|.
name|setField
argument_list|(
literal|"abstract"
argument_list|,
literal|"Abstract The present paper studied the influence of different food regimes "
operator|+
literal|"on the free amino acid (FAA) pool, the rate of protein turnover, the flux of amino acids, and "
operator|+
literal|"their relation to growth of larval turbot (Scophthalmus maximus L.) from first feeding until "
operator|+
literal|"metamorphosis. The amino acid profile of protein was stable during the larval period although "
operator|+
literal|"some small, but significant, differences were found. Turbot larvae had proteins which were rich "
operator|+
literal|"in leucine and aspartate, and poor in glutamate, suggesting a high leucine requirement. The "
operator|+
literal|"profile of the FAA pool was highly variable and quite different from the amino acid profile in "
operator|+
literal|"protein. The proportion of essential FAA decreased with development. High contents of free tyrosine "
operator|+
literal|"and phenylalanine were found on Day 3, while free taurine was present at high levels throughout "
operator|+
literal|"the experimental period. Larval growth rates were positively correlated with taurine levels, "
operator|+
literal|"suggesting a dietary dependency for taurine and/or sulphur amino acids.\n\nReduced growth rates in "
operator|+
literal|"Artemia-fed larvae were associated with lower levels of free methionine, indicating that this diet "
operator|+
literal|"is deficient in methionine for turbot larvae. Leucine might also be limiting turbot growth as the "
operator|+
literal|"different diet organisms had lower levels of this amino acid in the free pool than was found in the "
operator|+
literal|"larval protein. A previously presented model was used to describe the flux of amino acids in growing "
operator|+
literal|"turbot larvae. The FAA pool was found to be small and variable. It was estimated that the daily dietary "
operator|+
literal|"amino acid intake might be up to ten times the larval FAA pool. In addition, protein synthesis and "
operator|+
literal|"protein degradation might daily remove and return, respectively, the equivalent of up to 20 and 10 "
operator|+
literal|"times the size of the FAA pool. In an early phase (Day 11) high growth rates were associated with a "
operator|+
literal|"relatively low protein turnover, while at a later stage (Day 17), a much higher turnover was observed."
argument_list|)
expr_stmt|;
return|return
name|PreviewPrefsTab
operator|.
name|entry
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
name|layout1
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_0
argument_list|)
operator|.
name|replaceAll
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
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREVIEW_1
argument_list|)
operator|.
name|replaceAll
argument_list|(
literal|"__NEWLINE__"
argument_list|,
literal|"\n"
argument_list|)
argument_list|)
expr_stmt|;
name|pdfPreview
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PDF_PREVIEW
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
name|_prefs
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
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
argument_list|)
expr_stmt|;
name|_prefs
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
name|replaceAll
argument_list|(
literal|"\n"
argument_list|,
literal|"__NEWLINE__"
argument_list|)
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PDF_PREVIEW
argument_list|,
name|pdfPreview
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|readyToClose ()
specifier|public
name|boolean
name|readyToClose
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
name|Globals
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

