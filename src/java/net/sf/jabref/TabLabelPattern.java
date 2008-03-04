begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/*  * Created on 09-Dec-2003  */
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
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Iterator
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
name|labelPattern
operator|.
name|LabelPattern
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
name|labelPattern
operator|.
name|LabelPatternUtil
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
comment|/**  * The Preferences panel for key generation.  */
end_comment

begin_class
DECL|class|TabLabelPattern
specifier|public
class|class
name|TabLabelPattern
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|def
specifier|private
name|String
name|def
init|=
name|Globals
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
decl_stmt|;
DECL|field|gbl
specifier|private
name|GridBagLayout
name|gbl
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|con
specifier|private
name|GridBagConstraints
name|con
init|=
operator|new
name|GridBagConstraints
argument_list|()
decl_stmt|;
DECL|field|textFields
specifier|private
name|HashMap
argument_list|<
name|String
argument_list|,
name|JTextField
argument_list|>
name|textFields
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|JTextField
argument_list|>
argument_list|()
decl_stmt|;
DECL|field|_prefs
specifier|private
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_keypatterns
specifier|private
name|LabelPattern
name|_keypatterns
init|=
literal|null
decl_stmt|;
DECL|field|dontOverwrite
specifier|private
name|JCheckBox
name|dontOverwrite
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Do not overwrite existing keys"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|warnBeforeOverwriting
name|warnBeforeOverwriting
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Warn before overwriting existing keys"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|generateOnSave
name|generateOnSave
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Generate keys before saving (for entries without a key)"
argument_list|)
argument_list|)
decl_stmt|,
DECL|field|autoGenerateOnImport
name|autoGenerateOnImport
init|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Generate keys for imported entries"
argument_list|)
argument_list|)
decl_stmt|;
DECL|field|lblEntryType
DECL|field|lblKeyPattern
specifier|private
name|JLabel
name|lblEntryType
decl_stmt|,
name|lblKeyPattern
decl_stmt|;
DECL|field|defaultPat
specifier|private
name|JTextField
name|defaultPat
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
comment|//private JTextField basenamePatternRegex = new JTextField(20);
comment|//private JTextField basenamePatternReplacement = new JTextField(20);
DECL|field|KeyPatternRegex
specifier|private
name|JTextField
name|KeyPatternRegex
init|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
decl_stmt|;
DECL|field|KeyPatternReplacement
specifier|private
name|JTextField
name|KeyPatternReplacement
init|=
operator|new
name|JTextField
argument_list|(
literal|20
argument_list|)
decl_stmt|;
DECL|field|btnDefaultAll
DECL|field|btnDefault
specifier|private
name|JButton
name|btnDefaultAll
decl_stmt|,
name|btnDefault
decl_stmt|;
DECL|field|help
specifier|private
name|HelpAction
name|help
decl_stmt|;
comment|/** 	 * The constructor 	 */
DECL|method|TabLabelPattern (JabRefPreferences prefs, HelpDialog helpDiag)
specifier|public
name|TabLabelPattern
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|HelpDialog
name|helpDiag
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
comment|//_keypatterns = _prefs.getKeyPattern();
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|labelPatternHelp
argument_list|,
literal|"Help on key patterns"
argument_list|)
expr_stmt|;
name|buildGUI
argument_list|()
expr_stmt|;
comment|//fillTextfields();
block|}
comment|/** 	 * Store changes to table preferences. This method is called when 	 * the user clicks Ok. 	 * 	 */
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
comment|// Set the default value:
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"defaultLabelPattern"
argument_list|,
name|defaultPat
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"warnBeforeOverwritingKey"
argument_list|,
name|warnBeforeOverwriting
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"avoidOverwritingKey"
argument_list|,
name|dontOverwrite
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
comment|//Globals.prefs.put("basenamePatternRegex", basenamePatternRegex.getText());
comment|//Globals.prefs.put("basenamePatternReplacement", basenamePatternReplacement.getText());
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"KeyPatternRegex"
argument_list|,
name|KeyPatternRegex
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"KeyPatternReplacement"
argument_list|,
name|KeyPatternReplacement
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"generateKeysAfterInspection"
argument_list|,
name|autoGenerateOnImport
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|putBoolean
argument_list|(
literal|"generateKeysBeforeSaving"
argument_list|,
name|generateOnSave
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|LabelPatternUtil
operator|.
name|updateDefaultPattern
argument_list|()
expr_stmt|;
name|LabelPattern
name|defKeyPattern
init|=
name|_keypatterns
operator|.
name|getParent
argument_list|()
decl_stmt|;
name|_keypatterns
operator|=
operator|new
name|LabelPattern
argument_list|(
name|defKeyPattern
argument_list|)
expr_stmt|;
comment|// then we rebuild...
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|textFields
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
comment|//String defa = (String)LabelPatternUtil.DEFAULT_LABELPATTERN.get(0);
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|s
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|,
name|text
init|=
name|textFields
operator|.
name|get
argument_list|(
name|s
argument_list|)
operator|.
name|getText
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
literal|""
operator|.
name|equals
argument_list|(
name|text
operator|.
name|trim
argument_list|()
argument_list|)
condition|)
comment|//(!defa.equals(text))
name|_keypatterns
operator|.
name|addLabelPattern
argument_list|(
name|s
argument_list|,
name|text
argument_list|)
expr_stmt|;
block|}
name|_prefs
operator|.
name|putKeyPattern
argument_list|(
name|_keypatterns
argument_list|)
expr_stmt|;
block|}
DECL|method|addEntryType (Container c, String name, int y)
specifier|private
name|JTextField
name|addEntryType
parameter_list|(
name|Container
name|c
parameter_list|,
name|String
name|name
parameter_list|,
name|int
name|y
parameter_list|)
block|{
name|JLabel
name|lab
init|=
operator|new
name|JLabel
argument_list|(
name|Util
operator|.
name|nCase
argument_list|(
name|name
argument_list|)
argument_list|)
decl_stmt|;
name|name
operator|=
name|name
operator|.
name|toLowerCase
argument_list|()
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
name|y
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
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
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
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
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
name|c
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|JTextField
name|tf
init|=
operator|new
name|JTextField
argument_list|()
decl_stmt|;
comment|//_keypatterns.getValue(name).get(0).toString());
name|tf
operator|.
name|setColumns
argument_list|(
literal|15
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
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
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|CENTER
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|tf
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|tf
argument_list|)
expr_stmt|;
name|JButton
name|but
init|=
operator|new
name|JButton
argument_list|(
name|def
argument_list|)
decl_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|2
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
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|CENTER
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|but
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|but
operator|.
name|setActionCommand
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|but
operator|.
name|addActionListener
argument_list|(
operator|new
name|buttonHandler
argument_list|()
argument_list|)
expr_stmt|;
name|c
operator|.
name|add
argument_list|(
name|but
argument_list|)
expr_stmt|;
return|return
name|tf
return|;
block|}
DECL|method|setValue (JTextField tf, String fieldName)
specifier|private
name|void
name|setValue
parameter_list|(
name|JTextField
name|tf
parameter_list|,
name|String
name|fieldName
parameter_list|)
block|{
if|if
condition|(
name|_keypatterns
operator|.
name|isDefaultValue
argument_list|(
name|fieldName
argument_list|)
condition|)
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
else|else
block|{
comment|//System.out.println(":: "+_keypatterns.getValue(fieldName).get(0).toString());
name|tf
operator|.
name|setText
argument_list|(
name|_keypatterns
operator|.
name|getValue
argument_list|(
name|fieldName
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
comment|/** 	 * Method to build GUI 	 * 	 */
DECL|method|buildGUI ()
specifier|private
name|void
name|buildGUI
parameter_list|()
block|{
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|JScrollPane
name|sp
init|=
operator|new
name|JScrollPane
argument_list|(
name|pan
argument_list|)
decl_stmt|;
name|sp
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|()
argument_list|)
expr_stmt|;
name|pan
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
comment|// The header - can be removed
name|lblEntryType
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Entry type"
argument_list|)
argument_list|)
expr_stmt|;
name|Font
name|f
init|=
operator|new
name|Font
argument_list|(
literal|"plain"
argument_list|,
name|Font
operator|.
name|BOLD
argument_list|,
literal|12
argument_list|)
decl_stmt|;
name|lblEntryType
operator|.
name|setFont
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
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
name|gridheight
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|VERTICAL
expr_stmt|;
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
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|,
literal|0
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lblEntryType
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|lblEntryType
argument_list|)
expr_stmt|;
name|lblKeyPattern
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Key pattern"
argument_list|)
argument_list|)
expr_stmt|;
name|lblKeyPattern
operator|.
name|setFont
argument_list|(
name|f
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|0
expr_stmt|;
comment|//con.gridwidth = 2;
name|con
operator|.
name|gridheight
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
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
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lblKeyPattern
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|lblKeyPattern
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|0
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
literal|"Default pattern"
argument_list|)
argument_list|)
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|lab
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|defaultPat
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|defaultPat
argument_list|)
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|10
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|btnDefault
operator|=
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
expr_stmt|;
name|btnDefault
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
name|event
parameter_list|)
block|{
name|defaultPat
operator|.
name|setText
argument_list|(
operator|(
name|String
operator|)
name|Globals
operator|.
name|prefs
operator|.
name|defaults
operator|.
name|get
argument_list|(
literal|"defaultLabelPattern"
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|int
name|y
init|=
literal|2
decl_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|btnDefault
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|pan
operator|.
name|add
argument_list|(
name|btnDefault
argument_list|)
expr_stmt|;
for|for
control|(
name|String
name|s
range|:
name|BibtexEntryType
operator|.
name|ALL_TYPES
operator|.
name|keySet
argument_list|()
control|)
block|{
name|textFields
operator|.
name|put
argument_list|(
name|s
argument_list|,
name|addEntryType
argument_list|(
name|pan
argument_list|,
name|s
argument_list|,
name|y
argument_list|)
argument_list|)
expr_stmt|;
name|y
operator|++
expr_stmt|;
block|}
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
name|gridx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|1
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
comment|// A help button
name|con
operator|.
name|gridwidth
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|fill
operator|=
name|GridBagConstraints
operator|.
name|HORIZONTAL
expr_stmt|;
comment|//
name|con
operator|.
name|weightx
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|weighty
operator|=
literal|0
expr_stmt|;
name|con
operator|.
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|SOUTHEAST
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|0
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|JButton
name|hlb
init|=
operator|new
name|JButton
argument_list|(
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"helpSmall"
argument_list|)
argument_list|)
decl_stmt|;
name|hlb
operator|.
name|setToolTipText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Help on key patterns"
argument_list|)
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|hlb
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|hlb
argument_list|)
expr_stmt|;
name|hlb
operator|.
name|addActionListener
argument_list|(
name|help
argument_list|)
expr_stmt|;
comment|// And finally a button to reset everything
name|btnDefaultAll
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Reset all"
argument_list|)
argument_list|)
expr_stmt|;
name|con
operator|.
name|gridx
operator|=
literal|2
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|2
expr_stmt|;
comment|//con.fill = GridBagConstraints.BOTH;
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
name|anchor
operator|=
name|GridBagConstraints
operator|.
name|SOUTHEAST
expr_stmt|;
name|con
operator|.
name|insets
operator|=
operator|new
name|Insets
argument_list|(
literal|20
argument_list|,
literal|5
argument_list|,
literal|0
argument_list|,
literal|5
argument_list|)
expr_stmt|;
name|gbl
operator|.
name|setConstraints
argument_list|(
name|btnDefaultAll
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|btnDefaultAll
operator|.
name|addActionListener
argument_list|(
operator|new
name|buttonHandler
argument_list|()
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|btnDefaultAll
argument_list|)
expr_stmt|;
comment|// Build a panel for checkbox settings:
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 8dlu, left:pref"
argument_list|,
literal|""
argument_list|)
decl_stmt|;
comment|//, 8dlu, 20dlu, 8dlu, fill:pref", "");
name|pan
operator|=
operator|new
name|JPanel
argument_list|()
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
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Key generator settings"
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
name|autoGenerateOnImport
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
name|warnBeforeOverwriting
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|dontOverwrite
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
name|generateOnSave
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Replace (regular expression)"
argument_list|)
operator|+
literal|":"
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
literal|"by"
argument_list|)
operator|+
literal|":"
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
name|KeyPatternRegex
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|KeyPatternReplacement
argument_list|)
expr_stmt|;
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
name|con
operator|.
name|gridx
operator|=
literal|1
expr_stmt|;
name|con
operator|.
name|gridy
operator|=
literal|3
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
name|weightx
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
name|gbl
operator|.
name|setConstraints
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|,
name|con
argument_list|)
expr_stmt|;
name|add
argument_list|(
name|builder
operator|.
name|getPanel
argument_list|()
argument_list|)
expr_stmt|;
name|dontOverwrite
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
name|event
parameter_list|)
block|{
comment|// Warning before overwriting is only relevant if overwriting can happen:
name|warnBeforeOverwriting
operator|.
name|setEnabled
argument_list|(
operator|!
name|dontOverwrite
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
comment|/*        Simon Fischer's patch for replacing a regexp in keys before converting to filename:  	layout = new FormLayout 	        ("left:pref, 8dlu, left:pref, left:pref", ""); 	builder = new DefaultFormBuilder(layout);         builder.appendSeparator(Globals.lang("Bibkey to filename conversion"));         builder.nextLine(); 	builder.append(Globals.lang("Replace"), basenamePatternRegex);         builder.nextLine(); 	builder.append(Globals.lang("by"), basenamePatternReplacement);         builder.nextLine();          builder.getPanel().setBorder(BorderFactory.createEmptyBorder(5,5,5,5));         con.gridx = 2;  	con.gridy = 3; 	con.gridwidth = GridBagConstraints.REMAINDER; 	con.weightx = 1; 	con.fill = GridBagConstraints.BOTH; 	gbl.setConstraints(builder.getPanel(), con);         add(builder.getPanel());         */
block|}
comment|/** 	 * Method for filling the text fields with user defined key patterns or default. 	 * The method used (<code>getValue(key)</code>) to get the ArrayList  	 * corrosponding to an entry type throws a<code>NullPointerException</code> 	 * and<code>?</code> if an entry cannot be found. It really shouln't be 	 * nessesary to catch those exceptions here...  	 */
comment|/*	private void fillTextfields(){ 		txtArticle.setText(_keypatterns.getValue("article").get(0).toString()); 		txtBook.setText(_keypatterns.getValue("book").get(0).toString()); 		txtBooklet.setText(_keypatterns.getValue("booklet").get(0).toString()); 		txtConference.setText(_keypatterns.getValue("conference").get(0).toString()); 		txtInbook.setText(_keypatterns.getValue("inbook").get(0).toString()); 		txtIncollection.setText(_keypatterns.getValue("incollection").get(0).toString()); 		txtInproceedings.setText(_keypatterns.getValue("inproceedings").get(0).toString()); 		txtManual.setText(_keypatterns.getValue("manual").get(0).toString()); 		txtMastersthesis.setText(_keypatterns.getValue("mastersthesis").get(0).toString()); 		txtMisc.setText(_keypatterns.getValue("misc").get(0).toString()); 		txtPhdthesis.setText(_keypatterns.getValue("phdthesis").get(0).toString()); 		txtProceedings.setText(_keypatterns.getValue("proceedings").get(0).toString()); 		txtTechreport.setText(_keypatterns.getValue("techreport").get(0).toString()); 		txtUnpublished.setText(_keypatterns.getValue("unpublished").get(0).toString()); 	}      */
comment|/** 	 * An inner class to handle button actions 	 * @author Ulrik Stervbo (ulriks AT ruc.dk) 	 */
DECL|class|buttonHandler
class|class
name|buttonHandler
implements|implements
name|ActionListener
block|{
DECL|method|actionPerformed (ActionEvent evt)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
if|if
condition|(
name|evt
operator|.
name|getSource
argument_list|()
operator|==
name|btnDefaultAll
condition|)
block|{
comment|// All to default
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|textFields
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
decl_stmt|;
while|while
condition|(
name|i
operator|.
name|hasNext
argument_list|()
condition|)
block|{
name|String
name|s
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
comment|//_keypatterns.removeLabelPattern(s);
name|JTextField
name|tf
init|=
name|textFields
operator|.
name|get
argument_list|(
name|s
argument_list|)
decl_stmt|;
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|/*tf.setText(_keypatterns.getParent() 				       .getValue(s).get(0).toString());*/
block|}
return|return;
block|}
comment|//_keypatterns.removeLabelPattern(evt.getActionCommand());
name|JTextField
name|tf
init|=
name|textFields
operator|.
name|get
argument_list|(
name|evt
operator|.
name|getActionCommand
argument_list|()
argument_list|)
decl_stmt|;
name|tf
operator|.
name|setText
argument_list|(
literal|""
argument_list|)
expr_stmt|;
comment|/*tf.setText(_keypatterns.getParent() 			       .getValue(evt.getActionCommand()).get(0).toString());*/
block|}
block|}
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
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|_keypatterns
operator|=
name|_prefs
operator|.
name|getKeyPattern
argument_list|()
expr_stmt|;
name|defaultPat
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"defaultLabelPattern"
argument_list|)
argument_list|)
expr_stmt|;
name|dontOverwrite
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"avoidOverwritingKey"
argument_list|)
argument_list|)
expr_stmt|;
name|generateOnSave
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"generateKeysBeforeSaving"
argument_list|)
argument_list|)
expr_stmt|;
name|autoGenerateOnImport
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"generateKeysAfterInspection"
argument_list|)
argument_list|)
expr_stmt|;
name|warnBeforeOverwriting
operator|.
name|setSelected
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"warnBeforeOverwritingKey"
argument_list|)
argument_list|)
expr_stmt|;
comment|// Warning before overwriting is only relevant if overwriting can happen:
name|warnBeforeOverwriting
operator|.
name|setEnabled
argument_list|(
operator|!
name|dontOverwrite
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
for|for
control|(
name|Iterator
argument_list|<
name|String
argument_list|>
name|i
init|=
name|textFields
operator|.
name|keySet
argument_list|()
operator|.
name|iterator
argument_list|()
init|;
name|i
operator|.
name|hasNext
argument_list|()
condition|;
control|)
block|{
name|String
name|name
init|=
name|i
operator|.
name|next
argument_list|()
decl_stmt|;
name|JTextField
name|tf
init|=
name|textFields
operator|.
name|get
argument_list|(
name|name
argument_list|)
decl_stmt|;
name|setValue
argument_list|(
name|tf
argument_list|,
name|name
argument_list|)
expr_stmt|;
block|}
name|KeyPatternRegex
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"KeyPatternRegex"
argument_list|)
argument_list|)
expr_stmt|;
name|KeyPatternReplacement
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"KeyPatternReplacement"
argument_list|)
argument_list|)
expr_stmt|;
comment|//basenamePatternRegex.setText(Globals.prefs.get("basenamePatternRegex"));
comment|//basenamePatternReplacement.setText(Globals.prefs.get("basenamePatternReplacement"));
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
literal|"BibTeX key generator"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

