begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|ChangeListener
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
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
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
name|factories
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
name|*
import|;
end_import

begin_class
DECL|class|AdvancedTab
specifier|public
class|class
name|AdvancedTab
extends|extends
name|JPanel
implements|implements
name|PrefsTab
block|{
DECL|field|_prefs
name|JabRefPreferences
name|_prefs
decl_stmt|;
DECL|field|_frame
name|JabRefFrame
name|_frame
decl_stmt|;
DECL|field|helpDiag
name|HelpDialog
name|helpDiag
decl_stmt|;
DECL|field|pan
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|lnf
name|lnf
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|lab
name|JLabel
name|lab
decl_stmt|;
DECL|field|useDefault
name|JCheckBox
name|useDefault
decl_stmt|;
DECL|field|className
name|JTextField
name|className
decl_stmt|;
DECL|field|def1
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
decl_stmt|,
DECL|field|def2
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
DECL|field|p1
name|JPanel
name|p1
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|,
DECL|field|p2
name|p2
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
DECL|field|oldLnf
name|String
name|oldLnf
init|=
literal|""
decl_stmt|;
DECL|field|oldUseDef
name|boolean
name|oldUseDef
decl_stmt|;
DECL|method|AdvancedTab (JabRefPreferences prefs, HelpDialog diag)
specifier|public
name|AdvancedTab
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|HelpDialog
name|diag
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|useDefault
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use other look and feel"
argument_list|)
argument_list|)
expr_stmt|;
name|className
operator|=
operator|new
name|JTextField
argument_list|(
literal|50
argument_list|)
expr_stmt|;
specifier|final
name|JTextField
name|clName
init|=
name|className
decl_stmt|;
name|useDefault
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
name|e
parameter_list|)
block|{
name|clName
operator|.
name|setEnabled
argument_list|(
operator|(
operator|(
name|JCheckBox
operator|)
name|e
operator|.
name|getSource
argument_list|()
operator|)
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:3dlu"
argument_list|,
comment|//, 4dlu, fill:pref",// 4dlu, left:pref, 4dlu",
literal|""
argument_list|)
decl_stmt|;
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Look and feel"
argument_list|)
argument_list|)
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
literal|"Default look and feel"
argument_list|)
operator|+
literal|": "
operator|+
operator|(
name|Globals
operator|.
name|ON_WIN
condition|?
name|GUIGlobals
operator|.
name|windowsDefaultLookAndFeel
else|:
name|GUIGlobals
operator|.
name|linuxDefaultLookAndFeel
operator|)
argument_list|)
decl_stmt|;
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
name|lab
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
name|useDefault
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
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Class name"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
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
name|className
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan2
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
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Note that you must specify the fully qualified class name for the look and feel,"
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lab
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
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"and the class must be available in your classpath next time you start JabRef."
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
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
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
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
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|oldUseDef
operator|=
name|_prefs
operator|.
name|getBoolean
argument_list|(
literal|"useDefaultLookAndFeel"
argument_list|)
expr_stmt|;
name|oldLnf
operator|=
name|_prefs
operator|.
name|get
argument_list|(
literal|"lookAndFeel"
argument_list|)
expr_stmt|;
name|useDefault
operator|.
name|setSelected
argument_list|(
operator|!
name|oldUseDef
argument_list|)
expr_stmt|;
name|className
operator|.
name|setText
argument_list|(
name|oldLnf
argument_list|)
expr_stmt|;
name|className
operator|.
name|setEnabled
argument_list|(
operator|!
name|oldUseDef
argument_list|)
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|_prefs
operator|.
name|putBoolean
argument_list|(
literal|"useDefaultLookAndFeel"
argument_list|,
operator|!
name|useDefault
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"lookAndFeel"
argument_list|,
name|className
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|(
name|useDefault
operator|.
name|isSelected
argument_list|()
operator|==
name|oldUseDef
operator|)
operator|||
operator|!
name|oldLnf
operator|.
name|equals
argument_list|(
name|className
operator|.
name|getText
argument_list|()
argument_list|)
condition|)
block|{
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
literal|"You have changed the look and feel setting. "
operator|+
literal|"You must restart JabRef for this to come into effect."
argument_list|)
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Changed look and feel settings"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|WARNING_MESSAGE
argument_list|)
expr_stmt|;
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
block|}
end_class

end_unit

