begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  Copyright (C) 2003-2015 JabRef contributors.     This program is free software; you can redistribute it and/or modify     it under the terms of the GNU General Public License as published by     the Free Software Foundation; either version 2 of the License, or     (at your option) any later version.      This program is distributed in the hope that it will be useful,     but WITHOUT ANY WARRANTY; without even the implied warranty of     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the     GNU General Public License for more details.      You should have received a copy of the GNU General Public License along     with this program; if not, write to the Free Software Foundation, Inc.,     51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA. */
end_comment

begin_package
DECL|package|net.sf.jabref.pdfimport
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|pdfimport
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
name|Color
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
name|GridLayout
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
name|KeyEvent
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
name|WindowAdapter
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
name|WindowEvent
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
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JDialog
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
name|JRadioButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|KeyStroke
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|WindowConstants
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
name|Globals
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
name|strings
operator|.
name|StringUtil
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|ButtonBarBuilder
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
DECL|class|ImportDialog
specifier|public
class|class
name|ImportDialog
extends|extends
name|JDialog
block|{
DECL|field|NOMETA
specifier|public
specifier|static
specifier|final
name|int
name|NOMETA
init|=
literal|0
decl_stmt|;
DECL|field|XMP
specifier|public
specifier|static
specifier|final
name|int
name|XMP
init|=
literal|1
decl_stmt|;
DECL|field|CONTENT
specifier|public
specifier|static
specifier|final
name|int
name|CONTENT
init|=
literal|2
decl_stmt|;
DECL|field|ONLYATTACH
specifier|public
specifier|static
specifier|final
name|int
name|ONLYATTACH
init|=
literal|4
decl_stmt|;
DECL|field|checkBoxDoNotShowAgain
specifier|private
specifier|final
name|JCheckBox
name|checkBoxDoNotShowAgain
decl_stmt|;
DECL|field|useDefaultPDFImportStyle
specifier|private
specifier|final
name|JCheckBox
name|useDefaultPDFImportStyle
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
DECL|field|result
specifier|private
name|int
name|result
decl_stmt|;
DECL|method|ImportDialog (boolean targetIsARow, String fileName)
specifier|public
name|ImportDialog
parameter_list|(
name|boolean
name|targetIsARow
parameter_list|,
name|String
name|fileName
parameter_list|)
block|{
name|Boolean
name|targetIsARow1
init|=
name|targetIsARow
decl_stmt|;
name|JPanel
name|contentPane
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|contentPane
operator|.
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|setContentPane
argument_list|(
name|contentPane
argument_list|)
expr_stmt|;
name|JPanel
name|panel3
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|panel3
operator|.
name|setBackground
argument_list|(
operator|new
name|Color
argument_list|(
operator|-
literal|1643275
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|labelHeadline
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import metadata from:"
argument_list|)
argument_list|)
decl_stmt|;
name|labelHeadline
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
name|labelHeadline
operator|.
name|getFont
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|Font
operator|.
name|BOLD
argument_list|,
literal|14
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|labelSubHeadline
init|=
operator|new
name|JLabel
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Choose the source for the metadata import"
argument_list|)
argument_list|)
decl_stmt|;
name|labelSubHeadline
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
name|labelSubHeadline
operator|.
name|getFont
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|labelSubHeadline
operator|.
name|getFont
argument_list|()
operator|.
name|getStyle
argument_list|()
argument_list|,
literal|13
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|labelFileName
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
name|labelFileName
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
name|labelHeadline
operator|.
name|getFont
argument_list|()
operator|.
name|getName
argument_list|()
argument_list|,
name|Font
operator|.
name|BOLD
argument_list|,
literal|14
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|headLinePanel
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|headLinePanel
operator|.
name|add
argument_list|(
name|labelHeadline
argument_list|)
expr_stmt|;
name|headLinePanel
operator|.
name|add
argument_list|(
name|labelFileName
argument_list|)
expr_stmt|;
name|headLinePanel
operator|.
name|setBackground
argument_list|(
operator|new
name|Color
argument_list|(
operator|-
literal|1643275
argument_list|)
argument_list|)
expr_stmt|;
name|GridLayout
name|gl
init|=
operator|new
name|GridLayout
argument_list|(
literal|2
argument_list|,
literal|1
argument_list|)
decl_stmt|;
name|gl
operator|.
name|setVgap
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|gl
operator|.
name|setHgap
argument_list|(
literal|10
argument_list|)
expr_stmt|;
name|panel3
operator|.
name|setLayout
argument_list|(
name|gl
argument_list|)
expr_stmt|;
name|panel3
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|10
argument_list|,
literal|10
argument_list|,
literal|10
argument_list|,
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|panel3
operator|.
name|add
argument_list|(
name|headLinePanel
argument_list|)
expr_stmt|;
name|panel3
operator|.
name|add
argument_list|(
name|labelSubHeadline
argument_list|)
expr_stmt|;
name|radioButtonNoMeta
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Create blank entry linking the PDF"
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
literal|"Create entry based on XMP data"
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
literal|"Create entry based on content"
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
literal|"Only attach PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|buttonOK
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|buttonCancel
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
decl_stmt|;
name|checkBoxDoNotShowAgain
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Do not show this box again for this import"
argument_list|)
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
name|DefaultFormBuilder
name|b
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 5dlu, left:pref:grow"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|b
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Create new entry"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|radioButtonNoMeta
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|radioButtonXmp
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|radioButtonPDFcontent
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|appendSeparator
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Update existing entry"
argument_list|)
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|radioButtononlyAttachPDF
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|b
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|checkBoxDoNotShowAgain
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|useDefaultPDFImportStyle
argument_list|)
expr_stmt|;
name|b
operator|.
name|getPanel
argument_list|()
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
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|()
decl_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|buttonOK
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|buttonCancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|getPanel
argument_list|()
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
name|contentPane
operator|.
name|add
argument_list|(
name|panel3
argument_list|,
name|BorderLayout
operator|.
name|NORTH
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|add
argument_list|(
name|b
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|add
argument_list|(
name|bb
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|targetIsARow1
condition|)
block|{
name|this
operator|.
name|radioButtononlyAttachPDF
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|String
name|name
init|=
operator|new
name|File
argument_list|(
name|fileName
argument_list|)
operator|.
name|getName
argument_list|()
decl_stmt|;
name|labelFileName
operator|.
name|setText
argument_list|(
name|StringUtil
operator|.
name|limitStringLength
argument_list|(
name|name
argument_list|,
literal|34
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Import metadata from PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|setModal
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|getRootPane
argument_list|()
operator|.
name|setDefaultButton
argument_list|(
name|buttonOK
argument_list|)
expr_stmt|;
comment|// only one of the radio buttons may be selected.
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
name|buttonOK
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|onOK
argument_list|()
argument_list|)
expr_stmt|;
name|buttonCancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|onCancel
argument_list|()
argument_list|)
expr_stmt|;
name|setDefaultCloseOperation
argument_list|(
name|WindowConstants
operator|.
name|DO_NOTHING_ON_CLOSE
argument_list|)
expr_stmt|;
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|windowClosing
parameter_list|(
name|WindowEvent
name|e
parameter_list|)
block|{
name|onCancel
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|contentPane
operator|.
name|registerKeyboardAction
argument_list|(
name|e
lambda|->
name|onCancel
argument_list|()
argument_list|,
name|KeyStroke
operator|.
name|getKeyStroke
argument_list|(
name|KeyEvent
operator|.
name|VK_ESCAPE
argument_list|,
literal|0
argument_list|)
argument_list|,
name|JComponent
operator|.
name|WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
argument_list|)
expr_stmt|;
switch|switch
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|getInt
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_DEFAULT_PDF_IMPORT_STYLE
argument_list|)
condition|)
block|{
case|case
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
name|this
operator|.
name|setSize
argument_list|(
literal|555
argument_list|,
literal|371
argument_list|)
expr_stmt|;
block|}
DECL|method|onOK ()
specifier|private
name|void
name|onOK
parameter_list|()
block|{
name|this
operator|.
name|result
operator|=
name|JOptionPane
operator|.
name|OK_OPTION
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putInt
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_DEFAULT_PDF_IMPORT_STYLE
argument_list|,
name|this
operator|.
name|getChoice
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|useDefaultPDFImportStyle
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|IMPORT_ALWAYSUSE
argument_list|,
literal|true
argument_list|)
expr_stmt|;
block|}
comment|// checkBoxDoNotShowAgain handled by local variable
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|onCancel ()
specifier|private
name|void
name|onCancel
parameter_list|()
block|{
name|this
operator|.
name|result
operator|=
name|JOptionPane
operator|.
name|CANCEL_OPTION
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|showDialog ()
specifier|public
name|void
name|showDialog
parameter_list|()
block|{
name|this
operator|.
name|pack
argument_list|()
expr_stmt|;
name|this
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
DECL|method|getChoice ()
specifier|public
name|int
name|getChoice
parameter_list|()
block|{
if|if
condition|(
name|radioButtonXmp
operator|.
name|isSelected
argument_list|()
condition|)
block|{
return|return
name|ImportDialog
operator|.
name|XMP
return|;
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
return|return
name|ImportDialog
operator|.
name|CONTENT
return|;
block|}
elseif|else
if|if
condition|(
name|radioButtonNoMeta
operator|.
name|isSelected
argument_list|()
condition|)
block|{
return|return
name|ImportDialog
operator|.
name|NOMETA
return|;
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
return|return
name|ImportDialog
operator|.
name|ONLYATTACH
return|;
block|}
else|else
block|{
throw|throw
operator|new
name|IllegalStateException
argument_list|()
throw|;
block|}
block|}
DECL|method|isDoNotShowAgain ()
specifier|public
name|boolean
name|isDoNotShowAgain
parameter_list|()
block|{
return|return
name|this
operator|.
name|checkBoxDoNotShowAgain
operator|.
name|isSelected
argument_list|()
return|;
block|}
DECL|method|getResult ()
specifier|public
name|int
name|getResult
parameter_list|()
block|{
return|return
name|result
return|;
block|}
DECL|method|disableXMPChoice ()
specifier|public
name|void
name|disableXMPChoice
parameter_list|()
block|{
name|this
operator|.
name|radioButtonXmp
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

