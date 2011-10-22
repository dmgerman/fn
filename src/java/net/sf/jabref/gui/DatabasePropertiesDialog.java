begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
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

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Oct 31, 2005  * Time: 10:46:03 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|DatabasePropertiesDialog
specifier|public
class|class
name|DatabasePropertiesDialog
extends|extends
name|JDialog
block|{
DECL|field|metaData
name|MetaData
name|metaData
decl_stmt|;
DECL|field|panel
name|BasePanel
name|panel
init|=
literal|null
decl_stmt|;
DECL|field|encoding
name|JComboBox
name|encoding
decl_stmt|;
DECL|field|ok
DECL|field|cancel
name|JButton
name|ok
decl_stmt|,
name|cancel
decl_stmt|;
DECL|field|fileDir
name|JTextField
name|fileDir
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|,
DECL|field|fileDirIndv
name|fileDirIndv
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|,
DECL|field|pdfDir
name|pdfDir
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|,
DECL|field|psDir
name|psDir
init|=
operator|new
name|JTextField
argument_list|(
literal|40
argument_list|)
decl_stmt|;
DECL|field|oldFileVal
DECL|field|oldFileIndvVal
DECL|field|oldPdfVal
DECL|field|oldPsVal
name|String
name|oldFileVal
init|=
literal|""
decl_stmt|,
name|oldFileIndvVal
init|=
literal|""
decl_stmt|,
name|oldPdfVal
init|=
literal|""
decl_stmt|,
name|oldPsVal
init|=
literal|""
decl_stmt|;
comment|// Remember old values to see if they are changed.
DECL|field|protect
name|JCheckBox
name|protect
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Refuse to save the database before external changes have been reviewed."
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|oldProtectVal
name|boolean
name|oldProtectVal
init|=
literal|false
decl_stmt|;
DECL|method|DatabasePropertiesDialog (JFrame parent)
specifier|public
name|DatabasePropertiesDialog
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|super
argument_list|(
name|parent
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Database properties"
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|encoding
operator|=
operator|new
name|JComboBox
argument_list|(
name|Globals
operator|.
name|ENCODINGS
argument_list|)
expr_stmt|;
name|ok
operator|=
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
expr_stmt|;
name|cancel
operator|=
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
expr_stmt|;
name|init
argument_list|(
name|parent
argument_list|)
expr_stmt|;
block|}
DECL|method|setPanel (BasePanel panel)
specifier|public
name|void
name|setPanel
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
name|this
operator|.
name|metaData
operator|=
name|panel
operator|.
name|metaData
argument_list|()
expr_stmt|;
block|}
DECL|method|init (JFrame parent)
specifier|public
specifier|final
name|void
name|init
parameter_list|(
name|JFrame
name|parent
parameter_list|)
block|{
name|JButton
name|browseFile
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|browseFileIndv
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|browsePdf
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|JButton
name|browsePs
init|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|browseFile
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|parent
argument_list|,
name|fileDir
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|browseFileIndv
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|parent
argument_list|,
name|fileDirIndv
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|browsePdf
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|parent
argument_list|,
name|pdfDir
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|browsePs
operator|.
name|addActionListener
argument_list|(
operator|new
name|BrowseAction
argument_list|(
name|parent
argument_list|,
name|psDir
argument_list|,
literal|true
argument_list|)
argument_list|)
expr_stmt|;
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, left:pref, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|builder
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
name|builder
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Database encoding"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|encoding
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
literal|"Override default file directories"
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"General file directory"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|fileDir
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|browseFile
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"User-specific file directory"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|fileDirIndv
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|browseFileIndv
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"PDF directory"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pdfDir
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|browsePdf
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"PS directory"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|psDir
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|browsePs
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
literal|"Database protection"
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
name|protect
argument_list|,
literal|3
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
name|addGridded
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGridded
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|getContentPane
argument_list|()
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
name|pack
argument_list|()
expr_stmt|;
name|AbstractAction
name|closeAction
init|=
operator|new
name|AbstractAction
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
decl_stmt|;
name|ActionMap
name|am
init|=
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|builder
operator|.
name|getPanel
argument_list|()
operator|.
name|getInputMap
argument_list|(
name|JComponent
operator|.
name|WHEN_IN_FOCUSED_WINDOW
argument_list|)
decl_stmt|;
name|im
operator|.
name|put
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Close dialog"
argument_list|)
argument_list|,
literal|"close"
argument_list|)
expr_stmt|;
name|am
operator|.
name|put
argument_list|(
literal|"close"
argument_list|,
name|closeAction
argument_list|)
expr_stmt|;
name|ok
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
name|storeSettings
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|cancel
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|setVisible (boolean visible)
specifier|public
name|void
name|setVisible
parameter_list|(
name|boolean
name|visible
parameter_list|)
block|{
if|if
condition|(
name|visible
condition|)
name|setValues
argument_list|()
expr_stmt|;
name|super
operator|.
name|setVisible
argument_list|(
name|visible
argument_list|)
expr_stmt|;
block|}
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|encoding
operator|.
name|setSelectedItem
argument_list|(
name|panel
operator|.
name|getEncoding
argument_list|()
argument_list|)
expr_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|fileD
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"userFileDir"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileD
operator|==
literal|null
condition|)
name|fileDir
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
else|else
block|{
comment|// Better be a little careful about how many entries the Vector has:
if|if
condition|(
name|fileD
operator|.
name|size
argument_list|()
operator|>=
literal|1
condition|)
name|fileDir
operator|.
name|setText
argument_list|(
operator|(
name|fileD
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Vector
argument_list|<
name|String
argument_list|>
name|fileDI
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"userFileDirIndividual"
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
name|fileDI
operator|==
literal|null
condition|)
name|fileDirIndv
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
else|else
block|{
comment|// Better be a little careful about how many entries the Vector has:
if|if
condition|(
name|fileDI
operator|.
name|size
argument_list|()
operator|>=
literal|1
condition|)
name|fileDirIndv
operator|.
name|setText
argument_list|(
operator|(
name|fileDI
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Vector
argument_list|<
name|String
argument_list|>
name|pdfD
init|=
name|metaData
operator|.
name|getData
argument_list|(
literal|"pdfDirectory"
argument_list|)
decl_stmt|;
if|if
condition|(
name|pdfD
operator|==
literal|null
condition|)
name|pdfDir
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
else|else
block|{
comment|// Better be a little careful about how many entries the Vector has:
if|if
condition|(
name|pdfD
operator|.
name|size
argument_list|()
operator|>=
literal|1
condition|)
name|pdfDir
operator|.
name|setText
argument_list|(
operator|(
name|pdfD
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Vector
argument_list|<
name|String
argument_list|>
name|psD
init|=
name|metaData
operator|.
name|getData
argument_list|(
literal|"psDirectory"
argument_list|)
decl_stmt|;
if|if
condition|(
name|psD
operator|==
literal|null
condition|)
name|psDir
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
else|else
block|{
comment|// Better be a little careful about how many entries the Vector has:
if|if
condition|(
name|psD
operator|.
name|size
argument_list|()
operator|>=
literal|1
condition|)
name|psDir
operator|.
name|setText
argument_list|(
operator|(
name|psD
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|)
operator|.
name|trim
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Vector
argument_list|<
name|String
argument_list|>
name|prot
init|=
name|metaData
operator|.
name|getData
argument_list|(
name|Globals
operator|.
name|PROTECTED_FLAG_META
argument_list|)
decl_stmt|;
if|if
condition|(
name|prot
operator|==
literal|null
condition|)
name|protect
operator|.
name|setSelected
argument_list|(
literal|false
argument_list|)
expr_stmt|;
else|else
block|{
if|if
condition|(
name|prot
operator|.
name|size
argument_list|()
operator|>=
literal|1
condition|)
name|protect
operator|.
name|setSelected
argument_list|(
name|Boolean
operator|.
name|parseBoolean
argument_list|(
name|prot
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|// Store original values to see if they get changed:
name|oldFileVal
operator|=
name|fileDir
operator|.
name|getText
argument_list|()
expr_stmt|;
name|oldFileIndvVal
operator|=
name|fileDir
operator|.
name|getText
argument_list|()
expr_stmt|;
name|oldPdfVal
operator|=
name|pdfDir
operator|.
name|getText
argument_list|()
expr_stmt|;
name|oldPsVal
operator|=
name|psDir
operator|.
name|getText
argument_list|()
expr_stmt|;
name|oldProtectVal
operator|=
name|protect
operator|.
name|isSelected
argument_list|()
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|String
name|oldEncoding
init|=
name|panel
operator|.
name|getEncoding
argument_list|()
decl_stmt|;
name|String
name|newEncoding
init|=
operator|(
name|String
operator|)
name|encoding
operator|.
name|getSelectedItem
argument_list|()
decl_stmt|;
name|panel
operator|.
name|setEncoding
argument_list|(
name|newEncoding
argument_list|)
expr_stmt|;
name|Vector
argument_list|<
name|String
argument_list|>
name|dir
init|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1
argument_list|)
decl_stmt|;
name|String
name|text
init|=
name|fileDir
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|dir
operator|.
name|add
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|putData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"userFileDir"
argument_list|)
argument_list|,
name|dir
argument_list|)
expr_stmt|;
block|}
else|else
name|metaData
operator|.
name|remove
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"userFileDir"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Repeat for individual file dir - reuse 'text' and 'dir' vars
name|dir
operator|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|text
operator|=
name|fileDirIndv
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|dir
operator|.
name|add
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|putData
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"userFileDirIndividual"
argument_list|)
argument_list|,
name|dir
argument_list|)
expr_stmt|;
block|}
else|else
name|metaData
operator|.
name|remove
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"userFileDirIndividual"
argument_list|)
argument_list|)
expr_stmt|;
name|dir
operator|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|text
operator|=
name|pdfDir
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|dir
operator|.
name|add
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|putData
argument_list|(
literal|"pdfDirectory"
argument_list|,
name|dir
argument_list|)
expr_stmt|;
block|}
else|else
name|metaData
operator|.
name|remove
argument_list|(
literal|"pdfDirectory"
argument_list|)
expr_stmt|;
name|dir
operator|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|text
operator|=
name|psDir
operator|.
name|getText
argument_list|()
operator|.
name|trim
argument_list|()
expr_stmt|;
if|if
condition|(
name|text
operator|.
name|length
argument_list|()
operator|>
literal|0
condition|)
block|{
name|dir
operator|.
name|add
argument_list|(
name|text
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|putData
argument_list|(
literal|"psDirectory"
argument_list|,
name|dir
argument_list|)
expr_stmt|;
block|}
else|else
name|metaData
operator|.
name|remove
argument_list|(
literal|"psDirectory"
argument_list|)
expr_stmt|;
if|if
condition|(
name|protect
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|dir
operator|=
operator|new
name|Vector
argument_list|<
name|String
argument_list|>
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|dir
operator|.
name|add
argument_list|(
literal|"true"
argument_list|)
expr_stmt|;
name|metaData
operator|.
name|putData
argument_list|(
name|Globals
operator|.
name|PROTECTED_FLAG_META
argument_list|,
name|dir
argument_list|)
expr_stmt|;
block|}
else|else
name|metaData
operator|.
name|remove
argument_list|(
name|Globals
operator|.
name|PROTECTED_FLAG_META
argument_list|)
expr_stmt|;
comment|// See if any of the values have been modified:
name|boolean
name|changed
init|=
operator|!
name|newEncoding
operator|.
name|equals
argument_list|(
name|oldEncoding
argument_list|)
operator|||
operator|!
name|oldFileVal
operator|.
name|equals
argument_list|(
name|fileDir
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|!
name|oldFileIndvVal
operator|.
name|equals
argument_list|(
name|fileDirIndv
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|!
name|oldPdfVal
operator|.
name|equals
argument_list|(
name|pdfDir
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|!
name|oldPsVal
operator|.
name|equals
argument_list|(
name|psDir
operator|.
name|getText
argument_list|()
argument_list|)
operator|||
operator|(
name|oldProtectVal
operator|!=
name|protect
operator|.
name|isSelected
argument_list|()
operator|)
decl_stmt|;
comment|// ... if so, mark base changed. Prevent the Undo button from removing
comment|// change marking:
if|if
condition|(
name|changed
condition|)
name|panel
operator|.
name|markNonUndoableBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

