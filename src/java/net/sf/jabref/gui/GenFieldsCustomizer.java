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
name|java
operator|.
name|awt
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
name|*
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|border
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

begin_comment
comment|/**  *<p>Title:</p>  *<p>Description:</p>  *<p>Copyright: Copyright (c) 2003</p>  *<p>Company:</p>  * @author not attributable  * @version 1.0  */
end_comment

begin_class
DECL|class|GenFieldsCustomizer
specifier|public
class|class
name|GenFieldsCustomizer
extends|extends
name|JDialog
block|{
DECL|field|jPanel2
name|JPanel
name|jPanel2
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|ok
name|JButton
name|ok
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|cancel
name|JButton
name|cancel
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|helpBut
name|JButton
name|helpBut
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
DECL|field|titledBorder1
name|TitledBorder
name|titledBorder1
decl_stmt|;
DECL|field|titledBorder2
name|TitledBorder
name|titledBorder2
decl_stmt|;
DECL|field|jLabel1
name|JLabel
name|jLabel1
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|jPanel3
name|JPanel
name|jPanel3
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|jPanel4
name|JPanel
name|jPanel4
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|gridBagLayout1
name|GridBagLayout
name|gridBagLayout1
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|jScrollPane1
name|JScrollPane
name|jScrollPane1
init|=
operator|new
name|JScrollPane
argument_list|()
decl_stmt|;
DECL|field|jLabel2
name|JLabel
name|jLabel2
init|=
operator|new
name|JLabel
argument_list|()
decl_stmt|;
DECL|field|fieldsArea
name|JTextArea
name|fieldsArea
init|=
operator|new
name|JTextArea
argument_list|()
decl_stmt|;
DECL|field|gridBagLayout2
name|GridBagLayout
name|gridBagLayout2
init|=
operator|new
name|GridBagLayout
argument_list|()
decl_stmt|;
DECL|field|parent
name|JabRefFrame
name|parent
decl_stmt|;
DECL|field|revert
name|JButton
name|revert
init|=
operator|new
name|JButton
argument_list|()
decl_stmt|;
comment|//EntryCustomizationDialog diag;
DECL|field|help
name|HelpAction
name|help
decl_stmt|;
DECL|method|GenFieldsCustomizer (JabRefFrame frame )
specifier|public
name|GenFieldsCustomizer
parameter_list|(
name|JabRefFrame
name|frame
comment|/*, EntryCustomizationDialog diag*/
parameter_list|)
block|{
name|super
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Set general fields"
argument_list|)
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|parent
operator|=
name|frame
expr_stmt|;
comment|//this.diag = diag;
name|help
operator|=
operator|new
name|HelpAction
argument_list|(
name|parent
operator|.
name|helpDiag
argument_list|,
name|GUIGlobals
operator|.
name|generalFieldsHelp
argument_list|,
literal|"Help"
argument_list|,
name|GUIGlobals
operator|.
name|helpSmallIconFile
argument_list|)
expr_stmt|;
name|helpBut
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Help"
argument_list|)
argument_list|)
expr_stmt|;
name|helpBut
operator|.
name|addActionListener
argument_list|(
name|help
argument_list|)
expr_stmt|;
try|try
block|{
name|jbInit
argument_list|()
expr_stmt|;
name|setSize
argument_list|(
operator|new
name|Dimension
argument_list|(
literal|400
argument_list|,
literal|200
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Exception
name|ex
parameter_list|)
block|{
name|ex
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|jbInit ()
specifier|private
name|void
name|jbInit
parameter_list|()
throws|throws
name|Exception
block|{
name|ok
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Ok"
argument_list|)
argument_list|)
expr_stmt|;
name|ok
operator|.
name|addActionListener
argument_list|(
operator|new
name|GenFieldsCustomizer_ok_actionAdapter
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|cancel
operator|.
name|setText
argument_list|(
name|Globals
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
operator|new
name|GenFieldsCustomizer_cancel_actionAdapter
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
comment|//jPanel2.setBackground(GUIGlobals.lightGray);
name|jLabel1
operator|.
name|setText
argument_list|(
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"General fields"
argument_list|)
argument_list|)
expr_stmt|;
comment|//    fieldsArea.setText(parent.prefs.get("generalFields"));
name|setFieldsText
argument_list|()
expr_stmt|;
comment|//jPanel3.setBackground(GUIGlobals.lightGray);
name|revert
operator|.
name|setText
argument_list|(
name|Globals
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
operator|new
name|GenFieldsCustomizer_revert_actionAdapter
argument_list|(
name|this
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|getContentPane
argument_list|()
operator|.
name|add
argument_list|(
name|jPanel2
argument_list|,
name|BorderLayout
operator|.
name|SOUTH
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|ok
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|revert
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|cancel
argument_list|,
literal|null
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|Box
operator|.
name|createHorizontalStrut
argument_list|(
literal|10
argument_list|)
argument_list|)
expr_stmt|;
name|jPanel2
operator|.
name|add
argument_list|(
name|helpBut
argument_list|,
literal|null
argument_list|)
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
name|jPanel2
operator|.
name|getActionMap
argument_list|()
decl_stmt|;
name|InputMap
name|im
init|=
name|jPanel2
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
comment|//diag.requestFocus();
block|}
block|}
argument_list|)
expr_stmt|;
block|}
DECL|method|ok_actionPerformed (ActionEvent e)
name|void
name|ok_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
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
name|Globals
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Each line must be on the following form"
argument_list|)
operator|+
literal|" '"
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Tabname"
argument_list|)
operator|+
literal|":"
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
name|Globals
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
name|Util
operator|.
name|checkLegalKey
argument_list|(
name|parts
index|[
literal|1
index|]
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
name|Globals
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
name|Globals
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
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|CUSTOM_TAB_NAME
operator|+
name|i
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
operator|(
name|Globals
operator|.
name|prefs
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
name|Globals
operator|.
name|prefs
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
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Globals
operator|.
name|prefs
operator|.
name|purgeSeries
argument_list|(
name|Globals
operator|.
name|prefs
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
name|Globals
operator|.
name|prefs
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
comment|/*     String delimStr = fieldsArea.getText().replaceAll("\\s+","")         .replaceAll("\\n+","").trim();     parent.prefs.putStringArray("generalFields", Util.delimToStringArray(delimStr, ";"));       */
name|parent
operator|.
name|removeCachedEntryEditors
argument_list|()
expr_stmt|;
name|dispose
argument_list|()
expr_stmt|;
comment|//diag.requestFocus();
block|}
DECL|method|cancel_actionPerformed (ActionEvent e)
name|void
name|cancel_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|dispose
argument_list|()
expr_stmt|;
comment|//diag.requestFocus();
block|}
DECL|method|setFieldsText ()
name|void
name|setFieldsText
parameter_list|()
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|String
name|name
init|=
literal|null
decl_stmt|,
name|fields
init|=
literal|null
decl_stmt|;
name|int
name|i
init|=
literal|0
decl_stmt|;
while|while
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|hasKey
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|CUSTOM_TAB_NAME
operator|+
name|i
argument_list|)
condition|)
block|{
name|name
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|CUSTOM_TAB_NAME
operator|+
name|i
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|fields
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|CUSTOM_TAB_FIELDS
operator|+
name|i
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|":"
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
literal|"\n"
argument_list|)
expr_stmt|;
name|System
operator|.
name|out
operator|.
name|println
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|CUSTOM_TAB_NAME
operator|+
name|i
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
DECL|method|revert_actionPerformed (ActionEvent e)
name|void
name|revert_actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|StringBuffer
name|sb
init|=
operator|new
name|StringBuffer
argument_list|()
decl_stmt|;
name|String
name|name
init|=
literal|null
decl_stmt|,
name|fields
init|=
literal|null
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
name|Globals
operator|.
name|prefs
operator|.
name|CUSTOM_TAB_NAME
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
name|Globals
operator|.
name|prefs
operator|.
name|CUSTOM_TAB_FIELDS
operator|+
name|i
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|":"
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
literal|"\n"
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
comment|//    fieldsArea.setText((String)parent.prefs.defaults.get("generalFields"));
block|}
block|}
end_class

begin_class
DECL|class|GenFieldsCustomizer_ok_actionAdapter
class|class
name|GenFieldsCustomizer_ok_actionAdapter
implements|implements
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
block|{
DECL|field|adaptee
name|GenFieldsCustomizer
name|adaptee
decl_stmt|;
DECL|method|GenFieldsCustomizer_ok_actionAdapter (GenFieldsCustomizer adaptee)
name|GenFieldsCustomizer_ok_actionAdapter
parameter_list|(
name|GenFieldsCustomizer
name|adaptee
parameter_list|)
block|{
name|this
operator|.
name|adaptee
operator|=
name|adaptee
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|adaptee
operator|.
name|ok_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
end_class

begin_class
DECL|class|GenFieldsCustomizer_cancel_actionAdapter
class|class
name|GenFieldsCustomizer_cancel_actionAdapter
implements|implements
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
block|{
DECL|field|adaptee
name|GenFieldsCustomizer
name|adaptee
decl_stmt|;
DECL|method|GenFieldsCustomizer_cancel_actionAdapter (GenFieldsCustomizer adaptee)
name|GenFieldsCustomizer_cancel_actionAdapter
parameter_list|(
name|GenFieldsCustomizer
name|adaptee
parameter_list|)
block|{
name|this
operator|.
name|adaptee
operator|=
name|adaptee
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|adaptee
operator|.
name|cancel_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
end_class

begin_class
DECL|class|GenFieldsCustomizer_revert_actionAdapter
class|class
name|GenFieldsCustomizer_revert_actionAdapter
implements|implements
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
block|{
DECL|field|adaptee
name|GenFieldsCustomizer
name|adaptee
decl_stmt|;
DECL|method|GenFieldsCustomizer_revert_actionAdapter (GenFieldsCustomizer adaptee)
name|GenFieldsCustomizer_revert_actionAdapter
parameter_list|(
name|GenFieldsCustomizer
name|adaptee
parameter_list|)
block|{
name|this
operator|.
name|adaptee
operator|=
name|adaptee
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|adaptee
operator|.
name|revert_actionPerformed
argument_list|(
name|e
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

