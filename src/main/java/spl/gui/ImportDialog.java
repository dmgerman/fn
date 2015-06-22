begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|spl.gui
package|package
name|spl
operator|.
name|gui
package|;
end_package

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
name|ImportSettingsTab
import|;
end_import

begin_import
import|import
name|spl
operator|.
name|listener
operator|.
name|LabelLinkListener
import|;
end_import

begin_import
import|import
name|spl
operator|.
name|localization
operator|.
name|LocalizationSupport
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
name|java
operator|.
name|io
operator|.
name|File
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
specifier|final
specifier|static
name|int
name|NOMETA
init|=
literal|0
decl_stmt|;
DECL|field|XMP
specifier|public
specifier|final
specifier|static
name|int
name|XMP
init|=
literal|1
decl_stmt|;
DECL|field|CONTENT
specifier|public
specifier|final
specifier|static
name|int
name|CONTENT
init|=
literal|2
decl_stmt|;
DECL|field|MRDLIB
specifier|public
specifier|final
specifier|static
name|int
name|MRDLIB
init|=
literal|3
decl_stmt|;
DECL|field|ONLYATTACH
specifier|public
specifier|final
specifier|static
name|int
name|ONLYATTACH
init|=
literal|4
decl_stmt|;
DECL|field|UPDATEEMPTYFIELDS
specifier|public
specifier|final
specifier|static
name|int
name|UPDATEEMPTYFIELDS
init|=
literal|5
decl_stmt|;
DECL|field|contentPane
specifier|private
specifier|final
name|JPanel
name|contentPane
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
DECL|field|radioButtonMrDlib
specifier|private
specifier|final
name|JRadioButton
name|radioButtonMrDlib
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
DECL|field|radioButtonUpdateEmptyFields
specifier|private
specifier|final
name|JRadioButton
name|radioButtonUpdateEmptyFields
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
name|contentPane
operator|=
operator|new
name|JPanel
argument_list|()
expr_stmt|;
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Import_Metadata_from:"
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Choose_the_source_for_the_metadata_import"
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
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Create_entry_based_on_content"
argument_list|)
argument_list|)
expr_stmt|;
name|radioButtonMrDlib
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Create_entry_based_on_data_fetched_from"
argument_list|)
argument_list|)
expr_stmt|;
name|radioButtononlyAttachPDF
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Only_attach_PDF"
argument_list|)
argument_list|)
expr_stmt|;
name|radioButtonUpdateEmptyFields
operator|=
operator|new
name|JRadioButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Update_empty_fields_with_data_fetched_from"
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|labelMrDlib1
init|=
operator|new
name|JLabel
argument_list|(
literal|"Mr._dLib"
argument_list|)
decl_stmt|;
name|labelMrDlib1
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
name|labelMrDlib1
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
literal|13
argument_list|)
argument_list|)
expr_stmt|;
name|labelMrDlib1
operator|.
name|setForeground
argument_list|(
operator|new
name|Color
argument_list|(
operator|-
literal|16776961
argument_list|)
argument_list|)
expr_stmt|;
name|JLabel
name|labelMrDlib2
init|=
operator|new
name|JLabel
argument_list|(
literal|"Mr._dLib"
argument_list|)
decl_stmt|;
name|labelMrDlib2
operator|.
name|setFont
argument_list|(
operator|new
name|Font
argument_list|(
name|labelMrDlib1
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
literal|13
argument_list|)
argument_list|)
expr_stmt|;
name|labelMrDlib2
operator|.
name|setForeground
argument_list|(
operator|new
name|Color
argument_list|(
operator|-
literal|16776961
argument_list|)
argument_list|)
expr_stmt|;
name|JButton
name|buttonOK
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|buttonCancel
init|=
operator|new
name|JButton
argument_list|(
name|Globals
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
name|Globals
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Create New Entry"
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
name|append
argument_list|(
name|radioButtonMrDlib
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|labelMrDlib1
argument_list|)
expr_stmt|;
name|b
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Update_Existing_Entry"
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
name|append
argument_list|(
name|radioButtonUpdateEmptyFields
argument_list|)
expr_stmt|;
name|b
operator|.
name|append
argument_list|(
name|labelMrDlib2
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
name|this
operator|.
name|radioButtonUpdateEmptyFields
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|labelMrDlib2
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|String
name|fileName1
init|=
name|fileName
decl_stmt|;
name|String
name|name
init|=
operator|new
name|File
argument_list|(
name|fileName1
argument_list|)
operator|.
name|getName
argument_list|()
decl_stmt|;
if|if
condition|(
name|name
operator|.
name|length
argument_list|()
operator|<
literal|34
condition|)
block|{
name|labelFileName
operator|.
name|setText
argument_list|(
name|name
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|labelFileName
operator|.
name|setText
argument_list|(
operator|new
name|File
argument_list|(
name|fileName1
argument_list|)
operator|.
name|getName
argument_list|()
operator|.
name|substring
argument_list|(
literal|0
argument_list|,
literal|33
argument_list|)
operator|+
literal|"..."
argument_list|)
expr_stmt|;
block|}
name|labelMrDlib1
operator|.
name|addMouseListener
argument_list|(
operator|new
name|LabelLinkListener
argument_list|(
name|labelMrDlib1
argument_list|,
literal|"www.mr-dlib.org/docs/pdf_metadata_extraction.php"
argument_list|)
argument_list|)
expr_stmt|;
name|labelMrDlib2
operator|.
name|addMouseListener
argument_list|(
operator|new
name|LabelLinkListener
argument_list|(
name|labelMrDlib2
argument_list|,
literal|"www.mr-dlib.org/docs/pdf_metadata_extraction.php"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|setTitle
argument_list|(
name|LocalizationSupport
operator|.
name|message
argument_list|(
literal|"Import_Metadata_From_PDF"
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
name|radioButtonMrDlib
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|radioButtononlyAttachPDF
argument_list|)
expr_stmt|;
name|bg
operator|.
name|add
argument_list|(
name|radioButtonUpdateEmptyFields
argument_list|)
expr_stmt|;
name|buttonOK
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
name|onOK
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|buttonCancel
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
name|onCancel
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|setDefaultCloseOperation
argument_list|(
name|DO_NOTHING_ON_CLOSE
argument_list|)
expr_stmt|;
name|addWindowListener
argument_list|(
operator|new
name|WindowAdapter
argument_list|()
block|{
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
name|onCancel
argument_list|()
expr_stmt|;
block|}
block|}
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
name|ImportSettingsTab
operator|.
name|PREF_IMPORT_DEFAULT_PDF_IMPORT_STYLE
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
name|MRDLIB
case|:
name|radioButtonMrDlib
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
case|case
name|UPDATEEMPTYFIELDS
case|:
name|radioButtonUpdateEmptyFields
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
name|ImportSettingsTab
operator|.
name|PREF_IMPORT_DEFAULT_PDF_IMPORT_STYLE
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
name|ImportSettingsTab
operator|.
name|PREF_IMPORT_ALWAYSUSE
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
return|return
name|XMP
return|;
elseif|else
if|if
condition|(
name|radioButtonPDFcontent
operator|.
name|isSelected
argument_list|()
condition|)
return|return
name|CONTENT
return|;
elseif|else
if|if
condition|(
name|radioButtonMrDlib
operator|.
name|isSelected
argument_list|()
condition|)
return|return
name|MRDLIB
return|;
elseif|else
if|if
condition|(
name|radioButtonNoMeta
operator|.
name|isSelected
argument_list|()
condition|)
return|return
name|NOMETA
return|;
elseif|else
if|if
condition|(
name|radioButtononlyAttachPDF
operator|.
name|isSelected
argument_list|()
condition|)
return|return
name|ONLYATTACH
return|;
elseif|else
if|if
condition|(
name|radioButtonUpdateEmptyFields
operator|.
name|isSelected
argument_list|()
condition|)
return|return
name|UPDATEEMPTYFIELDS
return|;
else|else
throw|throw
operator|new
name|IllegalStateException
argument_list|()
throw|;
block|}
DECL|method|getDoNotShowAgain ()
specifier|public
name|boolean
name|getDoNotShowAgain
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
DECL|method|$$$getRootComponent$$$ ()
specifier|public
name|JComponent
name|$$$getRootComponent$$$
parameter_list|()
block|{
return|return
name|contentPane
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

