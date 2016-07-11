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
name|util
operator|.
name|Objects
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
name|ButtonGroup
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
name|JMenuItem
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
name|JPopupMenu
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JRadioButton
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
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
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
name|pdfimport
operator|.
name|ImportDialog
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
DECL|class|ImportSettingsTab
specifier|public
class|class
name|ImportSettingsTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|DEFAULT_STYLE
specifier|public
specifier|static
specifier|final
name|int
name|DEFAULT_STYLE
init|=
name|ImportDialog
operator|.
name|CONTENT
decl_stmt|;
DECL|field|DEFAULT_FILENAMEPATTERNS_DISPLAY
specifier|private
specifier|static
specifier|final
name|String
index|[]
name|DEFAULT_FILENAMEPATTERNS_DISPLAY
init|=
operator|new
name|String
index|[]
block|{
literal|"bibtexkey"
block|,
literal|"bibtexkey - title"
block|,     }
decl_stmt|;
DECL|field|DEFAULT_FILENAMEPATTERNS
specifier|public
specifier|static
specifier|final
name|String
index|[]
name|DEFAULT_FILENAMEPATTERNS
init|=
operator|new
name|String
index|[]
block|{
literal|"\\bibtexkey"
block|,
literal|"\\bibtexkey\\begin{title} - \\format[RemoveBrackets]{\\title}\\end{title}"
block|}
decl_stmt|;
DECL|field|prefs
specifier|private
specifier|final
name|JabRefPreferences
name|prefs
decl_stmt|;
DECL|field|radioButtonXmp
specifier|private
specifier|final
name|JRadioButton
name|radioButtonXmp
decl_stmt|;
DECL|field|radioButtonPDFcontent
specifier|private
specifier|final
name|JRadioButton
name|radioButtonPDFcontent
decl_stmt|;
DECL|field|radioButtonNoMeta
specifier|private
specifier|final
name|JRadioButton
name|radioButtonNoMeta
decl_stmt|;
DECL|field|radioButtononlyAttachPDF
specifier|private
specifier|final
name|JRadioButton
name|radioButtononlyAttachPDF
decl_stmt|;
DECL|field|useDefaultPDFImportStyle
specifier|private
specifier|final
name|JCheckBox
name|useDefaultPDFImportStyle
decl_stmt|;
DECL|field|fileNamePattern
specifier|private
specifier|final
name|JTextField
name|fileNamePattern
decl_stmt|;
DECL|field|selectFileNamePattern
specifier|private
specifier|final
name|JButton
name|selectFileNamePattern
decl_stmt|;
DECL|method|ImportSettingsTab (JabRefPreferences prefs)
specifier|public
name|ImportSettingsTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|)
block|{
name|this
operator|.
name|prefs
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:3dlu"
argument_list|)
decl_stmt|;
name|radioButtonNoMeta
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Create_blank_entry_linking_the_PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|radioButtonXmp
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Create_entry_based_on_XMP_data"
argument_list|)
argument_list|)
expr_stmt|;
name|radioButtonPDFcontent
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Create_entry_based_on_content"
argument_list|)
argument_list|)
expr_stmt|;
name|radioButtononlyAttachPDF
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Only_attach_PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|ButtonGroup
name|bg
init|=
operator|new
name|ButtonGroup
argument_list|()
decl_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|radioButtonNoMeta
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|radioButtonXmp
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|radioButtonPDFcontent
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|radioButtononlyAttachPDF
argument_list|)
expr_stmt|;
name|useDefaultPDFImportStyle
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Always use this PDF import style (and do not ask for each import)"
argument_list|)
argument_list|)
expr_stmt|;
name|fileNamePattern
operator|=
operator|new
name|JTextField
argument_list|(
literal|50
argument_list|)
expr_stmt|;
name|selectFileNamePattern
operator|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Choose pattern"
argument_list|)
argument_list|)
expr_stmt|;
name|selectFileNamePattern
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|openFilePatternMenu
argument_list|()
argument_list|)
expr_stmt|;
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
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default import style for drag and drop of PDFs"
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
name|pan
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|radioButtonNoMeta
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
name|radioButtonXmp
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
name|radioButtonPDFcontent
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
name|radioButtononlyAttachPDF
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
name|useDefaultPDFImportStyle
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
literal|"Default PDF file link action"
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
literal|"Filename format pattern"
argument_list|)
operator|.
name|concat
argument_list|(
literal|":"
argument_list|)
argument_list|)
decl_stmt|;
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
name|fileNamePattern
argument_list|)
expr_stmt|;
name|pan2
operator|.
name|add
argument_list|(
name|selectFileNamePattern
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan2
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
name|useDefaultPDFImportStyle
operator|.
name|setSelected
argument_list|(
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PREF_IMPORT_ALWAYSUSE
argument_list|)
argument_list|)
expr_stmt|;
name|int
name|style
init|=
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|PREF_IMPORT_DEFAULT_PDF_IMPORT_STYLE
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|style
condition|)
block|{
case|case
name|ImportDialog
operator|.
name|NOMETA
case|:
name|radioButtonNoMeta
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|XMP
case|:
name|radioButtonXmp
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|CONTENT
case|:
name|radioButtonPDFcontent
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
case|case
name|ImportDialog
operator|.
name|ONLYATTACH
case|:
name|radioButtononlyAttachPDF
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
default|default:
comment|// fallback
name|radioButtonPDFcontent
operator|.
name|setSelected
argument_list|(
literal|true
argument_list|)
expr_stmt|;
break|break;
block|}
name|fileNamePattern
operator|.
name|setText
argument_list|(
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|PREF_IMPORT_FILENAMEPATTERN
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
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|PREF_IMPORT_ALWAYSUSE
argument_list|,
name|useDefaultPDFImportStyle
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|int
name|style
init|=
name|ImportSettingsTab
operator|.
name|DEFAULT_STYLE
decl_stmt|;
if|if
condition|(
name|radioButtonNoMeta
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|style
operator|=
name|ImportDialog
operator|.
name|NOMETA
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|radioButtonXmp
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|style
operator|=
name|ImportDialog
operator|.
name|XMP
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|radioButtonPDFcontent
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|style
operator|=
name|ImportDialog
operator|.
name|CONTENT
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|radioButtononlyAttachPDF
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|style
operator|=
name|ImportDialog
operator|.
name|ONLYATTACH
expr_stmt|;
block|}
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|PREF_IMPORT_DEFAULT_PDF_IMPORT_STYLE
argument_list|,
name|style
argument_list|)
expr_stmt|;
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|PREF_IMPORT_FILENAMEPATTERN
argument_list|,
name|fileNamePattern
operator|.
name|getText
argument_list|()
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
literal|"Import"
argument_list|)
return|;
block|}
DECL|method|openFilePatternMenu ()
specifier|private
name|void
name|openFilePatternMenu
parameter_list|()
block|{
name|JPopupMenu
name|popup
init|=
operator|new
name|JPopupMenu
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
name|ImportSettingsTab
operator|.
name|DEFAULT_FILENAMEPATTERNS
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
specifier|final
name|JMenuItem
name|item
init|=
operator|new
name|JMenuItem
argument_list|(
name|ImportSettingsTab
operator|.
name|DEFAULT_FILENAMEPATTERNS_DISPLAY
index|[
name|i
index|]
argument_list|)
decl_stmt|;
specifier|final
name|String
name|toSet
init|=
name|ImportSettingsTab
operator|.
name|DEFAULT_FILENAMEPATTERNS
index|[
name|i
index|]
decl_stmt|;
name|item
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|fileNamePattern
operator|.
name|setText
argument_list|(
name|toSet
argument_list|)
argument_list|)
expr_stmt|;
name|popup
operator|.
name|add
argument_list|(
name|item
argument_list|)
expr_stmt|;
block|}
name|popup
operator|.
name|show
argument_list|(
name|selectFileNamePattern
argument_list|,
literal|0
argument_list|,
name|selectFileNamePattern
operator|.
name|getHeight
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

