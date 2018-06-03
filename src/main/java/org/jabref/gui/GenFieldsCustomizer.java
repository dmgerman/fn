begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
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
name|Map
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|ActionMap
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
name|InputMap
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
name|JComponent
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
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
name|JTextArea
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
name|help
operator|.
name|HelpAction
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
name|keyboard
operator|.
name|KeyBinding
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
name|BibtexKeyGenerator
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
name|help
operator|.
name|HelpFile
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
name|layout
operator|.
name|Sizes
import|;
end_import

begin_class
DECL|class|GenFieldsCustomizer
specifier|public
class|class
name|GenFieldsCustomizer
extends|extends
name|JabRefDialog
block|{
DECL|field|buttons
specifier|private
specifier|final
name|JPanel
name|buttons
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|ok
specifier|private
specifier|final
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|cancel
specifier|private
specifier|final
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|helpBut
specifier|private
specifier|final
name|JButton
name|helpBut
decl_stmt|;
DECL|field|jLabel1
specifier|private
specifier|final
name|JLabel
name|jLabel1
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|jPanel3
specifier|private
specifier|final
name|JPanel
name|jPanel3
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|jPanel4
specifier|private
specifier|final
name|JPanel
name|jPanel4
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|gridBagLayout1
specifier|private
specifier|final
name|GridBagLayout
name|gridBagLayout1
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|jScrollPane1
specifier|private
specifier|final
name|JScrollPane
name|jScrollPane1
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|jLabel2
specifier|private
specifier|final
name|JLabel
name|jLabel2
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|fieldsArea
specifier|private
specifier|final
name|JTextArea
name|fieldsArea
init|=
operator|new
name|JTextArea
argument_list|()
decl_stmt|;
DECL|field|gridBagLayout2
specifier|private
specifier|final
name|GridBagLayout
name|gridBagLayout2
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|revert
specifier|private
specifier|final
name|JButton
name|revert
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|method|GenFieldsCustomizer (JabRefFrame frame)
specifier|public
name|GenFieldsCustomizer
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
operator|(
name|JFrame
operator|)
literal|null
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Set general fields"
argument_list|)
argument_list|,
literal|false
argument_list|,
name|GenFieldsCustomizer
operator|.
name|class
argument_list|)
expr_stmt|;
name|helpBut
operator|=
operator|new
name|HelpAction
argument_list|(
name|HelpFile
operator|.
name|GENERAL_FIELDS
argument_list|)
operator|.
name|getHelpButton
argument_list|()
expr_stmt|;
name|jbInit
argument_list|()
expr_stmt|;
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|650
argument_list|,
literal|300
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|jbInit ()
specifier|private
name|void
name|jbInit
parameter_list|()
block|{
name|ok
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"OK"
argument_list|)
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|okActionPerformed
argument_list|()
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Cancel"
argument_list|)
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|dispose
argument_list|()
argument_list|)
expr_stmt|;
name|jLabel1
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Delimit fields with semicolon, ex."
argument_list|)
operator|+
literal|": url;pdf;note"
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|setLayout
argument_list|(
name|gridBagLayout2
argument_list|)
expr_stmt|;
name|jPanel4
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEtchedBorder
argument_list|()
argument_list|)
expr_stmt|;
name|jPanel4
operator|.
name|setLayout
argument_list|(
name|gridBagLayout1
argument_list|)
expr_stmt|;
name|jLabel2
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"General fields"
argument_list|)
argument_list|)
expr_stmt|;
name|setFieldsText
argument_list|()
expr_stmt|;
name|revert
operator|.
name|setText
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Default"
argument_list|)
argument_list|)
expr_stmt|;
name|revert
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|revertActionPerformed
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|buttons
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|ButtonBarBuilder
name|bb
init|=
operator|new
name|ButtonBarBuilder
argument_list|(
name|buttons
argument_list|)
decl_stmt|;
name|buttons
operator|.
name|setBorder
argument_list|(
name|BorderFactory
operator|.
name|createEmptyBorder
argument_list|(
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|,
literal|2
argument_list|)
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|ok
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|revert
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|cancel
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addStrut
argument_list|(
name|Sizes
operator|.
name|DLUX5
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addButton
argument_list|(
name|helpBut
argument_list|)
expr_stmt|;
name|bb
operator|.
name|addGlue
argument_list|()
expr_stmt|;
name|this
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|jPanel3
argument_list|,
name|BorderLayout
operator|.
name|CENTER
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|add
argument_list|(
name|jLabel1
argument_list|,
operator|new
name|GridBagConstraints
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1.0
argument_list|,
literal|0.0
argument_list|,
name|GridBagConstraints
operator|.
name|WEST
argument_list|,
name|GridBagConstraints
operator|.
name|NONE
argument_list|,
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel3
operator|.
name|add
argument_list|(
name|jPanel4
argument_list|,
operator|new
name|GridBagConstraints
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1.0
argument_list|,
literal|1.0
argument_list|,
name|GridBagConstraints
operator|.
name|CENTER
argument_list|,
name|GridBagConstraints
operator|.
name|BOTH
argument_list|,
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|,
literal|318
argument_list|,
literal|193
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel4
operator|.
name|add
argument_list|(
name|jScrollPane1
argument_list|,
operator|new
name|GridBagConstraints
argument_list|(
literal|0
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|1.0
argument_list|,
literal|1.0
argument_list|,
name|GridBagConstraints
operator|.
name|CENTER
argument_list|,
name|GridBagConstraints
operator|.
name|BOTH
argument_list|,
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
name|jScrollPane1
operator|.
name|getViewport
argument_list|()
operator|.
name|add
argument_list|(
name|fieldsArea
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel4
operator|.
name|add
argument_list|(
name|jLabel2
argument_list|,
operator|new
name|GridBagConstraints
argument_list|(
literal|0
argument_list|,
literal|0
argument_list|,
literal|1
argument_list|,
literal|1
argument_list|,
literal|0.0
argument_list|,
literal|0.0
argument_list|,
name|GridBagConstraints
operator|.
name|WEST
argument_list|,
name|GridBagConstraints
operator|.
name|NONE
argument_list|,
operator|new
name|Insets
argument_list|(
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|,
literal|5
argument_list|)
argument_list|,
literal|0
argument_list|,
literal|0
argument_list|)
argument_list|)
expr_stmt|;
comment|// Key bindings:
name|ActionMap
name|am
init|=
name|buttons
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|buttons
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
name|getKeyPrefs
argument_list|()
operator|.
name|getKey
argument_list|(
name|KeyBinding
operator|.
name|CLOSE
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
operator|new
name|AbstractAction
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
name|dispose
argument_list|()
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|okActionPerformed ()
specifier|private
name|void
name|okActionPerformed
parameter_list|()
block|{
name|String
index|[]
name|lines
init|=
name|fieldsArea
operator|.
name|getText
argument_list|()
operator|.
name|split
argument_list|(
literal|"\n"
argument_list|)
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
for|for
control|(
init|;
name|i
operator|<
name|lines
operator|.
name|length
condition|;
name|i
operator|++
control|)
block|{
name|String
index|[]
name|parts
init|=
name|lines
index|[
name|i
index|]
operator|.
name|split
argument_list|(
literal|":"
argument_list|)
decl_stmt|;
if|if
condition|(
name|parts
operator|.
name|length
operator|!=
literal|2
condition|)
block|{
comment|// Report error and exit.
name|String
name|field
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"field"
argument_list|)
decl_stmt|;
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
literal|"Each line must be on the following form"
argument_list|)
operator|+
literal|" '"
operator|+
name|Localization
operator|.
name|lang
argument_list|(
literal|"Tabname"
argument_list|)
operator|+
literal|':'
operator|+
name|field
operator|+
literal|"1;"
operator|+
name|field
operator|+
literal|"2;...;"
operator|+
name|field
operator|+
literal|"N'"
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
name|String
name|testString
init|=
name|BibtexKeyGenerator
operator|.
name|cleanKey
argument_list|(
name|parts
index|[
literal|1
index|]
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
name|parts
index|[
literal|1
index|]
argument_list|)
operator|||
operator|(
name|parts
index|[
literal|1
index|]
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
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
operator|(
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_NAME
operator|+
name|i
operator|)
argument_list|,
name|parts
index|[
literal|0
index|]
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
operator|(
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_FIELDS
operator|+
name|i
operator|)
argument_list|,
name|parts
index|[
literal|1
index|]
operator|.
name|toLowerCase
argument_list|(
name|Locale
operator|.
name|ROOT
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|purgeSeries
argument_list|(
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_NAME
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|purgeSeries
argument_list|(
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_FIELDS
argument_list|,
name|i
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|updateEntryEditorTabList
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
block|}
DECL|method|setFieldsText ()
specifier|private
name|void
name|setFieldsText
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
name|tab
range|:
name|Globals
operator|.
name|prefs
operator|.
name|getEntryEditorTabList
argument_list|()
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|tab
operator|.
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|String
operator|.
name|join
argument_list|(
literal|";"
argument_list|,
name|tab
operator|.
name|getValue
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
block|}
name|fieldsArea
operator|.
name|setText
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
DECL|method|revertActionPerformed ()
specifier|private
name|void
name|revertActionPerformed
parameter_list|()
block|{
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
name|String
name|name
decl_stmt|;
name|String
name|fields
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
operator|(
name|name
operator|=
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
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_NAME
operator|+
literal|"_def"
operator|+
name|i
argument_list|)
operator|)
operator|!=
literal|null
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|fields
operator|=
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
name|JabRefPreferences
operator|.
name|CUSTOM_TAB_FIELDS
operator|+
literal|"_def"
operator|+
name|i
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|fields
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|'\n'
argument_list|)
expr_stmt|;
name|i
operator|++
expr_stmt|;
block|}
name|fieldsArea
operator|.
name|setText
argument_list|(
name|sb
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

