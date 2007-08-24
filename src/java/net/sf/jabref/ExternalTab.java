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
name|ItemEvent
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
name|ItemListener
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
name|external
operator|.
name|ExternalFileTypeEditor
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
DECL|class|ExternalTab
specifier|public
class|class
name|ExternalTab
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
DECL|field|pdfDir
DECL|field|regExpTextField
DECL|field|fileDir
DECL|field|psDir
DECL|field|pdf
DECL|field|ps
DECL|field|html
DECL|field|lyx
DECL|field|winEdt
DECL|field|led
name|JTextField
name|pdfDir
decl_stmt|,
name|regExpTextField
decl_stmt|,
name|fileDir
decl_stmt|,
name|psDir
decl_stmt|,
name|pdf
decl_stmt|,
name|ps
decl_stmt|,
name|html
decl_stmt|,
name|lyx
decl_stmt|,
name|winEdt
decl_stmt|,
name|led
decl_stmt|,
DECL|field|citeCommand
DECL|field|vim
DECL|field|vimServer
name|citeCommand
decl_stmt|,
name|vim
decl_stmt|,
name|vimServer
decl_stmt|;
DECL|field|editFileTypes
name|JButton
name|editFileTypes
decl_stmt|;
DECL|field|regExpListener
name|ItemListener
name|regExpListener
decl_stmt|;
DECL|field|useRegExpComboBox
name|JCheckBox
name|useRegExpComboBox
decl_stmt|;
DECL|method|ExternalTab (JabRefFrame frame, PrefsDialog3 prefsDiag, JabRefPreferences prefs, HelpDialog helpDialog)
specifier|public
name|ExternalTab
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|,
name|PrefsDialog3
name|prefsDiag
parameter_list|,
name|JabRefPreferences
name|prefs
parameter_list|,
name|HelpDialog
name|helpDialog
parameter_list|)
block|{
name|_prefs
operator|=
name|prefs
expr_stmt|;
name|_frame
operator|=
name|frame
expr_stmt|;
name|setLayout
argument_list|(
operator|new
name|BorderLayout
argument_list|()
argument_list|)
expr_stmt|;
name|psDir
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|pdfDir
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|fileDir
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|pdf
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|ps
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|html
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|lyx
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|winEdt
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|vim
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|vimServer
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|citeCommand
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|led
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|editFileTypes
operator|=
operator|new
name|JButton
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Manage external file types"
argument_list|)
argument_list|)
expr_stmt|;
name|regExpTextField
operator|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
expr_stmt|;
name|useRegExpComboBox
operator|=
operator|new
name|JCheckBox
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Use Regular Expression Search"
argument_list|)
argument_list|)
expr_stmt|;
name|regExpListener
operator|=
operator|new
name|ItemListener
argument_list|()
block|{
specifier|public
name|void
name|itemStateChanged
parameter_list|(
name|ItemEvent
name|e
parameter_list|)
block|{
name|regExpTextField
operator|.
name|setEditable
argument_list|(
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|regExpTextField
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|,
name|regExpTextField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|regExpTextField
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|DEFAULT_REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
expr_stmt|;
name|useRegExpComboBox
operator|.
name|addItemListener
argument_list|(
name|regExpListener
argument_list|)
expr_stmt|;
name|editFileTypes
operator|.
name|addActionListener
argument_list|(
name|ExternalFileTypeEditor
operator|.
name|getAction
argument_list|(
name|prefsDiag
argument_list|)
argument_list|)
expr_stmt|;
name|BrowseAction
name|browse
decl_stmt|;
name|FormLayout
name|layout
init|=
operator|new
name|FormLayout
argument_list|(
literal|"1dlu, 8dlu, left:pref, 4dlu, fill:200dlu, 4dlu, fill:pref"
argument_list|,
comment|// 4dlu,
comment|// left:pref,
comment|// 4dlu",
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
name|builder
operator|.
name|appendSeparator
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"External file links"
argument_list|)
argument_list|)
expr_stmt|;
name|JPanel
name|pan
init|=
operator|new
name|JPanel
argument_list|()
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|pan
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
literal|"Main %0 directory"
argument_list|,
name|GUIGlobals
operator|.
name|FILE_FIELD
argument_list|)
operator|+
literal|":"
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|lab
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|fileDir
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|fileDir
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|pan
operator|=
operator|new
name|JPanel
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
literal|"Main PDF directory"
argument_list|)
operator|+
literal|":"
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
name|append
argument_list|(
name|pdfDir
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|pdfDir
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
expr_stmt|;
name|pan
operator|=
operator|new
name|JPanel
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
literal|"Main PS directory"
argument_list|)
operator|+
literal|":"
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
name|append
argument_list|(
name|psDir
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|psDir
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
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
operator|new
name|JPanel
argument_list|()
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|useRegExpComboBox
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|regExpTextField
argument_list|)
expr_stmt|;
name|HelpAction
name|helpAction
init|=
operator|new
name|HelpAction
argument_list|(
name|helpDialog
argument_list|,
name|GUIGlobals
operator|.
name|regularExpressionSearchHelp
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Help on Regular Expression Search"
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
name|builder
operator|.
name|append
argument_list|(
name|helpAction
operator|.
name|getIconButton
argument_list|()
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
literal|"External programs"
argument_list|)
argument_list|)
expr_stmt|;
comment|/*builder.nextLine(); 		lab = new JLabel(Globals.lang("Path to PDF viewer") + ":"); 		builder.append(pan); 		builder.append(lab); 		builder.append(pdf); 		browse = new BrowseAction(_frame, pdf, false); 		if (Globals.ON_WIN) 			browse.setEnabled(false); 		builder.append(new JButton(browse)); 		builder.nextLine(); 		lab = new JLabel(Globals.lang("Path to PS viewer") + ":"); 		builder.append(pan); 		builder.append(lab); 		builder.append(ps); 		browse = new BrowseAction(_frame, ps, false); 		if (Globals.ON_WIN) 			browse.setEnabled(false); 		builder.append(new JButton(browse)); 		*/
name|builder
operator|.
name|nextLine
argument_list|()
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
literal|"Path to HTML viewer"
argument_list|)
operator|+
literal|":"
argument_list|)
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
name|append
argument_list|(
name|html
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|html
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|ON_WIN
condition|)
name|browse
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
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
literal|"Path to LyX pipe"
argument_list|)
operator|+
literal|":"
argument_list|)
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
name|append
argument_list|(
name|lyx
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|lyx
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
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
literal|"Path to WinEdt.exe"
argument_list|)
operator|+
literal|":"
argument_list|)
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
name|append
argument_list|(
name|winEdt
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|winEdt
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
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
literal|"Path to LatexEditor (LEd.exe)"
argument_list|)
operator|+
literal|":"
argument_list|)
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
name|append
argument_list|(
name|led
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|led
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
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
name|lab
operator|=
operator|new
name|JLabel
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Path to Vim"
argument_list|)
operator|+
literal|":"
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
name|append
argument_list|(
name|vim
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|vim
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|nextLine
argument_list|()
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
literal|"Vim Server Name"
argument_list|)
operator|+
literal|":"
argument_list|)
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
name|append
argument_list|(
name|vimServer
argument_list|)
expr_stmt|;
name|browse
operator|=
operator|new
name|BrowseAction
argument_list|(
name|_frame
argument_list|,
name|vimServer
argument_list|,
literal|false
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
operator|new
name|JButton
argument_list|(
name|browse
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
name|Globals
operator|.
name|lang
argument_list|(
literal|"Cite command (for Emacs/WinEdt)"
argument_list|)
operator|+
literal|":"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|citeCommand
argument_list|)
expr_stmt|;
comment|// builder.appendSeparator();
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
name|editFileTypes
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
DECL|method|setValues ()
specifier|public
name|void
name|setValues
parameter_list|()
block|{
name|pdfDir
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"pdfDirectory"
argument_list|)
argument_list|)
expr_stmt|;
name|psDir
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"psDirectory"
argument_list|)
argument_list|)
expr_stmt|;
name|fileDir
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|Globals
operator|.
name|ON_WIN
condition|)
block|{
name|pdf
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"pdfviewer"
argument_list|)
argument_list|)
expr_stmt|;
name|ps
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"psviewer"
argument_list|)
argument_list|)
expr_stmt|;
name|html
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"htmlviewer"
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|pdf
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Uses default application"
argument_list|)
argument_list|)
expr_stmt|;
name|ps
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Uses default application"
argument_list|)
argument_list|)
expr_stmt|;
name|html
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Uses default application"
argument_list|)
argument_list|)
expr_stmt|;
name|pdf
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|ps
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|html
operator|.
name|setEnabled
argument_list|(
literal|false
argument_list|)
expr_stmt|;
block|}
name|lyx
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"lyxpipe"
argument_list|)
argument_list|)
expr_stmt|;
name|winEdt
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"winEdtPath"
argument_list|)
argument_list|)
expr_stmt|;
name|vim
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"vim"
argument_list|)
argument_list|)
expr_stmt|;
name|vimServer
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"vimServer"
argument_list|)
argument_list|)
expr_stmt|;
name|led
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"latexEditorPath"
argument_list|)
argument_list|)
expr_stmt|;
name|citeCommand
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
literal|"citeCommand"
argument_list|)
argument_list|)
expr_stmt|;
name|regExpTextField
operator|.
name|setText
argument_list|(
name|_prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|useRegExpComboBox
operator|.
name|setSelected
argument_list|(
name|_prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|USE_REG_EXP_SEARCH_KEY
argument_list|)
argument_list|)
expr_stmt|;
name|regExpListener
operator|.
name|itemStateChanged
argument_list|(
literal|null
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
name|JabRefPreferences
operator|.
name|USE_REG_EXP_SEARCH_KEY
argument_list|,
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|useRegExpComboBox
operator|.
name|isSelected
argument_list|()
condition|)
block|{
name|_prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|REG_EXP_SEARCH_EXPRESSION_KEY
argument_list|,
name|regExpTextField
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|// We should maybe do some checking on the validity of the contents?
name|_prefs
operator|.
name|put
argument_list|(
literal|"pdfDirectory"
argument_list|,
name|pdfDir
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"psDirectory"
argument_list|,
name|psDir
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
name|GUIGlobals
operator|.
name|FILE_FIELD
operator|+
literal|"Directory"
argument_list|,
name|fileDir
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"pdfviewer"
argument_list|,
name|pdf
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"psviewer"
argument_list|,
name|ps
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"htmlviewer"
argument_list|,
name|html
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"lyxpipe"
argument_list|,
name|lyx
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"winEdtPath"
argument_list|,
name|winEdt
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"vim"
argument_list|,
name|vim
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"vimServer"
argument_list|,
name|vimServer
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"latexEditorPath"
argument_list|,
name|led
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|_prefs
operator|.
name|put
argument_list|(
literal|"citeCommand"
argument_list|,
name|citeCommand
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
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
literal|"External programs"
argument_list|)
return|;
block|}
block|}
end_class

end_unit

