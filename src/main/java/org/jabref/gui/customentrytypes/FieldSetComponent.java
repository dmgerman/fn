begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.customentrytypes
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|customentrytypes
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Component
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
name|Rectangle
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
name|awt
operator|.
name|event
operator|.
name|FocusEvent
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
name|FocusListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Enumeration
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashSet
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|LinkedHashSet
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
name|Locale
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|Box
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|DefaultListModel
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
name|JList
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
name|JTextField
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JViewport
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ListSelectionModel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ScrollPaneConstants
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
name|ListDataListener
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
name|ListSelectionListener
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|IconTheme
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|bibtexkeypattern
operator|.
name|BibtexKeyPatternUtil
import|;
end_import

begin_import
import|import
name|org
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
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|JabRefPreferences
import|;
end_import

begin_comment
comment|/**  * @author alver  */
end_comment

begin_class
DECL|class|FieldSetComponent
class|class
name|FieldSetComponent
extends|extends
name|JPanel
block|{
DECL|field|list
specifier|protected
specifier|final
name|JList
argument_list|<
name|String
argument_list|>
name|list
decl_stmt|;
DECL|field|listModel
specifier|protected
name|DefaultListModel
argument_list|<
name|String
argument_list|>
name|listModel
decl_stmt|;
DECL|field|remove
specifier|protected
specifier|final
name|JButton
name|remove
decl_stmt|;
DECL|field|gbl
specifier|protected
specifier|final
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
specifier|protected
specifier|final
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|forceLowerCase
specifier|protected
specifier|final
name|boolean
name|forceLowerCase
decl_stmt|;
DECL|field|changesMade
specifier|protected
name|boolean
name|changesMade
decl_stmt|;
DECL|field|additionListeners
specifier|private
specifier|final
name|Set
argument_list|<
name|ActionListener
argument_list|>
name|additionListeners
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
DECL|field|sp
specifier|private
specifier|final
name|JScrollPane
name|sp
decl_stmt|;
DECL|field|sel
specifier|private
name|JComboBox
argument_list|<
name|String
argument_list|>
name|sel
decl_stmt|;
DECL|field|input
specifier|private
name|JTextField
name|input
decl_stmt|;
DECL|field|add
specifier|private
specifier|final
name|JButton
name|add
decl_stmt|;
DECL|field|up
specifier|private
name|JButton
name|up
decl_stmt|;
DECL|field|down
specifier|private
name|JButton
name|down
decl_stmt|;
DECL|field|modelListeners
specifier|private
specifier|final
name|Set
argument_list|<
name|ListDataListener
argument_list|>
name|modelListeners
init|=
operator|new
name|HashSet
argument_list|<>
argument_list|()
decl_stmt|;
comment|/**      * Creates a new instance of FieldSetComponent, with preset selection      * values. These are put into a JComboBox.      */
DECL|method|FieldSetComponent (String title, List<String> fields, List<String> preset, boolean arrows, boolean forceLowerCase)
specifier|public
name|FieldSetComponent
parameter_list|(
name|String
name|title
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|preset
parameter_list|,
name|boolean
name|arrows
parameter_list|,
name|boolean
name|forceLowerCase
parameter_list|)
block|{
name|this
argument_list|(
name|title
argument_list|,
name|fields
argument_list|,
name|preset
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|,
name|arrows
argument_list|,
name|forceLowerCase
argument_list|)
expr_stmt|;
block|}
comment|/**      * Creates a new instance of FieldSetComponent without preset selection      * values. Replaces the JComboBox with a JTextField.      */
DECL|method|FieldSetComponent (String title, List<String> fields, boolean arrows, boolean forceLowerCase)
name|FieldSetComponent
parameter_list|(
name|String
name|title
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|boolean
name|arrows
parameter_list|,
name|boolean
name|forceLowerCase
parameter_list|)
block|{
name|this
argument_list|(
name|title
argument_list|,
name|fields
argument_list|,
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Add"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Remove"
argument_list|)
argument_list|,
name|arrows
argument_list|,
name|forceLowerCase
argument_list|)
expr_stmt|;
block|}
DECL|method|FieldSetComponent (String title, List<String> fields, List<String> preset, String addText, String removeText, boolean arrows, boolean forceLowerCase)
specifier|private
name|FieldSetComponent
parameter_list|(
name|String
name|title
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|preset
parameter_list|,
name|String
name|addText
parameter_list|,
name|String
name|removeText
parameter_list|,
name|boolean
name|arrows
parameter_list|,
name|boolean
name|forceLowerCase
parameter_list|)
block|{
name|this
operator|.
name|forceLowerCase
operator|=
name|forceLowerCase
expr_stmt|;
name|add
operator|=
operator|new
name|JButton
argument_list|(
name|addText
argument_list|)
expr_stmt|;
name|remove
operator|=
operator|new
name|JButton
argument_list|(
name|removeText
argument_list|)
expr_stmt|;
name|listModel
operator|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
expr_stmt|;
name|JLabel
name|title1
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|title
operator|!=
literal|null
condition|)
block|{
name|title1
operator|=
operator|new
name|JLabel
argument_list|(
name|title
argument_list|)
expr_stmt|;
block|}
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|listModel
operator|.
name|addElement
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
name|list
operator|=
operator|new
name|JList
argument_list|<>
argument_list|(
name|listModel
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectionMode
argument_list|(
name|ListSelectionModel
operator|.
name|MULTIPLE_INTERVAL_SELECTION
argument_list|)
expr_stmt|;
comment|// Set up GUI:
name|add
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
block|{
comment|// Selection has been made, or add button pressed:
if|if
condition|(
operator|(
name|sel
operator|!=
literal|null
operator|)
operator|&&
operator|(
name|sel
operator|.
name|getSelectedItem
argument_list|()
operator|!=
literal|null
operator|)
condition|)
block|{
name|String
name|s
init|=
name|sel
operator|.
name|getSelectedItem
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|addField
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
operator|(
name|input
operator|!=
literal|null
operator|)
operator|&&
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|input
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
name|addField
argument_list|(
name|input
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|remove
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|removeSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|// Remove button pressed
name|setLayout
argument_list|(
name|gbl
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|)
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
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
if|if
condition|(
name|title1
operator|!=
literal|null
condition|)
block|{
name|gbl
operator|.
name|setConstraints
argument_list|(
name|title1
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|title1
argument_list|)
expr_stmt|;
block|}
name|con
operator|.
name|weighty
operator|=
literal|1
expr_stmt|;
name|sp
operator|=
operator|new
name|JScrollPane
argument_list|(
name|list
argument_list|,
name|ScrollPaneConstants
operator|.
name|VERTICAL_SCROLLBAR_AS_NEEDED
argument_list|,
name|ScrollPaneConstants
operator|.
name|HORIZONTAL_SCROLLBAR_NEVER
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sp
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|sp
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
if|if
condition|(
name|arrows
condition|)
block|{
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|up
operator|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|UP
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|down
operator|=
operator|new
name|JButton
argument_list|(
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|DOWN
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|up
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|move
argument_list|(
operator|-
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|down
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|move
argument_list|(
literal|1
argument_list|)
argument_list|)
expr_stmt|;
name|up
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move up"
argument_list|)
argument_list|)
expr_stmt|;
name|down
operator|.
name|setToolTipText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Move down"
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|up
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|up
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|down
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|down
argument_list|)
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
block|}
name|Component
name|strut
init|=
name|Box
operator|.
name|createHorizontalStrut
argument_list|(
literal|5
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|strut
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|strut
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
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
comment|//Component b = Box.createHorizontalGlue();
comment|//gbl.setConstraints(b, con);
comment|//add(b);
comment|//if (!arrows)
name|con
operator|.
name|gridwidth
operator|=
name|GridBagConstraints
operator|.
name|REMAINDER
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|remove
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|remove
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|3
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|1
expr_stmt|;
if|if
condition|(
name|preset
operator|==
literal|null
condition|)
block|{
name|input
operator|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
expr_stmt|;
name|input
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|addField
argument_list|(
name|input
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|input
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|input
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|sel
operator|=
operator|new
name|JComboBox
argument_list|<>
argument_list|(
name|preset
operator|.
name|toArray
argument_list|(
operator|new
name|String
index|[
name|preset
operator|.
name|size
argument_list|()
index|]
argument_list|)
argument_list|)
expr_stmt|;
name|sel
operator|.
name|setEditable
argument_list|(
literal|true
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|sel
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|sel
argument_list|)
expr_stmt|;
block|}
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
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weightx
operator|=
literal|0.5
expr_stmt|;
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|add
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|add
argument_list|)
expr_stmt|;
name|FieldListFocusListener
argument_list|<
name|String
argument_list|>
name|fieldListFocusListener
init|=
operator|new
name|FieldListFocusListener
argument_list|<>
argument_list|(
name|list
argument_list|)
decl_stmt|;
name|list
operator|.
name|addFocusListener
argument_list|(
name|fieldListFocusListener
argument_list|)
expr_stmt|;
block|}
DECL|method|setListSelectionMode (int mode)
specifier|public
name|void
name|setListSelectionMode
parameter_list|(
name|int
name|mode
parameter_list|)
block|{
name|list
operator|.
name|setSelectionMode
argument_list|(
name|mode
argument_list|)
expr_stmt|;
block|}
DECL|method|selectField (String fieldName)
specifier|public
name|void
name|selectField
parameter_list|(
name|String
name|fieldName
parameter_list|)
block|{
name|int
name|idx
init|=
name|listModel
operator|.
name|indexOf
argument_list|(
name|fieldName
argument_list|)
decl_stmt|;
if|if
condition|(
name|idx
operator|>=
literal|0
condition|)
block|{
name|list
operator|.
name|setSelectedIndex
argument_list|(
name|idx
argument_list|)
expr_stmt|;
block|}
comment|// Make sure it is visible:
name|JViewport
name|viewport
init|=
name|sp
operator|.
name|getViewport
argument_list|()
decl_stmt|;
name|Rectangle
name|rectangle
init|=
name|list
operator|.
name|getCellBounds
argument_list|(
name|idx
argument_list|,
name|idx
argument_list|)
decl_stmt|;
if|if
condition|(
name|rectangle
operator|!=
literal|null
condition|)
block|{
name|viewport
operator|.
name|scrollRectToVisible
argument_list|(
name|rectangle
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|getFirstSelected ()
specifier|public
name|String
name|getFirstSelected
parameter_list|()
block|{
return|return
name|list
operator|.
name|getSelectedValue
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|setEnabled (boolean en)
specifier|public
name|void
name|setEnabled
parameter_list|(
name|boolean
name|en
parameter_list|)
block|{
if|if
condition|(
name|input
operator|!=
literal|null
condition|)
block|{
name|input
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|sel
operator|!=
literal|null
condition|)
block|{
name|sel
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|up
operator|!=
literal|null
condition|)
block|{
name|up
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
name|down
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
block|}
name|add
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
name|remove
operator|.
name|setEnabled
argument_list|(
name|en
argument_list|)
expr_stmt|;
block|}
comment|/**      * Return the current list.      */
DECL|method|getFields ()
specifier|public
name|Set
argument_list|<
name|String
argument_list|>
name|getFields
parameter_list|()
block|{
name|Set
argument_list|<
name|String
argument_list|>
name|res
init|=
operator|new
name|LinkedHashSet
argument_list|<>
argument_list|(
name|listModel
operator|.
name|getSize
argument_list|()
argument_list|)
decl_stmt|;
name|Enumeration
argument_list|<
name|String
argument_list|>
name|elements
init|=
name|listModel
operator|.
name|elements
argument_list|()
decl_stmt|;
while|while
condition|(
name|elements
operator|.
name|hasMoreElements
argument_list|()
condition|)
block|{
name|res
operator|.
name|add
argument_list|(
name|elements
operator|.
name|nextElement
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
name|res
return|;
block|}
comment|/**      * This method is called when a new field should be added to the list. Performs validation of the      * field.      */
DECL|method|addField (String str)
specifier|protected
name|void
name|addField
parameter_list|(
name|String
name|str
parameter_list|)
block|{
name|String
name|s
init|=
name|str
operator|.
name|trim
argument_list|()
decl_stmt|;
if|if
condition|(
name|forceLowerCase
condition|)
block|{
name|s
operator|=
name|s
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
literal|""
operator|.
name|equals
argument_list|(
name|s
argument_list|)
operator|||
name|listModel
operator|.
name|contains
argument_list|(
name|s
argument_list|)
condition|)
block|{
return|return;
block|}
name|String
name|testString
init|=
name|BibtexKeyPatternUtil
operator|.
name|checkLegalKey
argument_list|(
name|s
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|ENFORCE_LEGAL_BIBTEX_KEY
argument_list|)
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|testString
operator|.
name|equals
argument_list|(
name|s
argument_list|)
operator|||
operator|(
name|s
operator|.
name|indexOf
argument_list|(
literal|'&'
argument_list|)
operator|>=
literal|0
operator|)
condition|)
block|{
comment|// Report error and exit.
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|this
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Field names are not allowed to contain white space or the following "
operator|+
literal|"characters"
argument_list|)
operator|+
literal|": # { } ~ , ^&"
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
return|return;
block|}
name|addFieldUncritically
argument_list|(
name|s
argument_list|)
expr_stmt|;
block|}
comment|/**      * This method adds a new field to the list, without any regard to validation. This method can be      * useful for classes that overrides addField(s) to provide different validation.      */
DECL|method|addFieldUncritically (String s)
specifier|protected
name|void
name|addFieldUncritically
parameter_list|(
name|String
name|s
parameter_list|)
block|{
name|listModel
operator|.
name|addElement
argument_list|(
name|s
argument_list|)
expr_stmt|;
name|changesMade
operator|=
literal|true
expr_stmt|;
for|for
control|(
name|ActionListener
name|additionListener
range|:
name|additionListeners
control|)
block|{
name|additionListener
operator|.
name|actionPerformed
argument_list|(
operator|new
name|ActionEvent
argument_list|(
name|this
argument_list|,
literal|0
argument_list|,
name|s
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|removeSelected ()
specifier|protected
name|void
name|removeSelected
parameter_list|()
block|{
name|int
index|[]
name|selected
init|=
name|list
operator|.
name|getSelectedIndices
argument_list|()
decl_stmt|;
if|if
condition|(
name|selected
operator|.
name|length
operator|>
literal|0
condition|)
block|{
name|changesMade
operator|=
literal|true
expr_stmt|;
block|}
for|for
control|(
name|int
name|i
init|=
literal|0
init|;
name|i
operator|<
name|selected
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|listModel
operator|.
name|removeElementAt
argument_list|(
name|selected
index|[
name|selected
operator|.
name|length
operator|-
literal|1
operator|-
name|i
index|]
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|setFields (Set<String> fields)
specifier|public
name|void
name|setFields
parameter_list|(
name|Set
argument_list|<
name|String
argument_list|>
name|fields
parameter_list|)
block|{
name|DefaultListModel
argument_list|<
name|String
argument_list|>
name|newListModel
init|=
operator|new
name|DefaultListModel
argument_list|<>
argument_list|()
decl_stmt|;
for|for
control|(
name|String
name|field
range|:
name|fields
control|)
block|{
name|newListModel
operator|.
name|addElement
argument_list|(
name|field
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|listModel
operator|=
name|newListModel
expr_stmt|;
for|for
control|(
name|ListDataListener
name|modelListener
range|:
name|modelListeners
control|)
block|{
name|newListModel
operator|.
name|addListDataListener
argument_list|(
name|modelListener
argument_list|)
expr_stmt|;
block|}
name|list
operator|.
name|setModel
argument_list|(
name|newListModel
argument_list|)
expr_stmt|;
block|}
comment|/**      * Add a ListSelectionListener to the JList component displayed as part of this component.      */
DECL|method|addListSelectionListener (ListSelectionListener l)
specifier|public
name|void
name|addListSelectionListener
parameter_list|(
name|ListSelectionListener
name|l
parameter_list|)
block|{
name|list
operator|.
name|addListSelectionListener
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
comment|/**      * Adds an ActionListener that will receive events each time a field is added. The ActionEvent      * will specify this component as source, and the added field as action command.      */
DECL|method|addAdditionActionListener (ActionListener l)
specifier|public
name|void
name|addAdditionActionListener
parameter_list|(
name|ActionListener
name|l
parameter_list|)
block|{
name|additionListeners
operator|.
name|add
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
DECL|method|addListDataListener (ListDataListener l)
specifier|public
name|void
name|addListDataListener
parameter_list|(
name|ListDataListener
name|l
parameter_list|)
block|{
name|listModel
operator|.
name|addListDataListener
argument_list|(
name|l
argument_list|)
expr_stmt|;
name|modelListeners
operator|.
name|add
argument_list|(
name|l
argument_list|)
expr_stmt|;
block|}
comment|/**      * If a field is selected in the list, move it dy positions.      */
DECL|method|move (int dy)
specifier|private
name|void
name|move
parameter_list|(
name|int
name|dy
parameter_list|)
block|{
name|int
name|oldIdx
init|=
name|list
operator|.
name|getSelectedIndex
argument_list|()
decl_stmt|;
if|if
condition|(
name|oldIdx
operator|<
literal|0
condition|)
block|{
return|return;
block|}
name|String
name|o
init|=
name|listModel
operator|.
name|get
argument_list|(
name|oldIdx
argument_list|)
decl_stmt|;
comment|// Compute the new index:
name|int
name|newInd
init|=
name|Math
operator|.
name|max
argument_list|(
literal|0
argument_list|,
name|Math
operator|.
name|min
argument_list|(
name|listModel
operator|.
name|size
argument_list|()
operator|-
literal|1
argument_list|,
name|oldIdx
operator|+
name|dy
argument_list|)
argument_list|)
decl_stmt|;
name|listModel
operator|.
name|remove
argument_list|(
name|oldIdx
argument_list|)
expr_stmt|;
name|listModel
operator|.
name|add
argument_list|(
name|newInd
argument_list|,
name|o
argument_list|)
expr_stmt|;
name|list
operator|.
name|setSelectedIndex
argument_list|(
name|newInd
argument_list|)
expr_stmt|;
block|}
comment|/**      * FocusListener to select the first entry in the list of fields when they are focused      */
DECL|class|FieldListFocusListener
specifier|protected
class|class
name|FieldListFocusListener
parameter_list|<
name|T
parameter_list|>
implements|implements
name|FocusListener
block|{
DECL|field|list
specifier|private
specifier|final
name|JList
argument_list|<
name|T
argument_list|>
name|list
decl_stmt|;
DECL|method|FieldListFocusListener (JList<T> list)
specifier|public
name|FieldListFocusListener
parameter_list|(
name|JList
argument_list|<
name|T
argument_list|>
name|list
parameter_list|)
block|{
name|this
operator|.
name|list
operator|=
name|list
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|focusGained (FocusEvent e)
specifier|public
name|void
name|focusGained
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|list
operator|.
name|getSelectedValue
argument_list|()
operator|==
literal|null
condition|)
block|{
name|list
operator|.
name|setSelectedIndex
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|focusLost (FocusEvent e)
specifier|public
name|void
name|focusLost
parameter_list|(
name|FocusEvent
name|e
parameter_list|)
block|{
comment|//focus should remain at the same position so nothing to do here
block|}
block|}
block|}
end_class

end_unit

