begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.external
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|external
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
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
comment|/**  * Class for pushing entries into LatexEditor.  */
end_comment

begin_class
DECL|class|PushToLatexEditor
specifier|public
class|class
name|PushToLatexEditor
implements|implements
name|PushToApplication
block|{
DECL|field|couldNotCall
specifier|private
name|boolean
name|couldNotCall
init|=
literal|false
decl_stmt|;
DECL|field|notDefined
specifier|private
name|boolean
name|notDefined
init|=
literal|false
decl_stmt|;
DECL|field|settings
specifier|private
name|JPanel
name|settings
init|=
literal|null
decl_stmt|;
DECL|field|ledPath
specifier|private
name|JTextField
name|ledPath
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|,
DECL|field|citeCommand
name|citeCommand
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|menuTitle
argument_list|(
literal|"Insert selected citations into LatexEditor"
argument_list|)
return|;
block|}
DECL|method|getApplicationName ()
specifier|public
name|String
name|getApplicationName
parameter_list|()
block|{
return|return
literal|"LatexEditor"
return|;
block|}
DECL|method|getTooltip ()
specifier|public
name|String
name|getTooltip
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Push to LatexEditor"
argument_list|)
return|;
block|}
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
block|{
return|return
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"edit"
argument_list|)
return|;
block|}
DECL|method|getKeyStrokeName ()
specifier|public
name|String
name|getKeyStrokeName
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
DECL|method|pushEntries (BibtexDatabase database, BibtexEntry[] entries, String keyString, MetaData metaData)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibtexDatabase
name|database
parameter_list|,
name|BibtexEntry
index|[]
name|entries
parameter_list|,
name|String
name|keyString
parameter_list|,
name|MetaData
name|metaData
parameter_list|)
block|{
name|couldNotCall
operator|=
literal|false
expr_stmt|;
name|notDefined
operator|=
literal|false
expr_stmt|;
name|String
name|led
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"latexEditorPath"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|led
operator|==
literal|null
operator|)
operator|||
operator|(
name|led
operator|.
name|trim
argument_list|()
operator|.
name|length
argument_list|()
operator|==
literal|0
operator|)
condition|)
block|{
name|notDefined
operator|=
literal|true
expr_stmt|;
return|return;
block|}
try|try
block|{
name|StringBuffer
name|toSend
init|=
operator|new
name|StringBuffer
argument_list|(
literal|"-i "
argument_list|)
operator|.
name|append
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"citeCommandLed"
argument_list|)
argument_list|)
operator|.
name|append
argument_list|(
literal|"{"
argument_list|)
operator|.
name|append
argument_list|(
name|keyString
argument_list|)
operator|.
name|append
argument_list|(
literal|"}"
argument_list|)
decl_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|led
operator|+
literal|" "
operator|+
name|toSend
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IOException
name|excep
parameter_list|)
block|{
name|couldNotCall
operator|=
literal|true
expr_stmt|;
name|excep
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|operationCompleted (BasePanel panel)
specifier|public
name|void
name|operationCompleted
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
if|if
condition|(
name|notDefined
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Path to %0 not defined"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|couldNotCall
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"Could not call executable"
argument_list|)
operator|+
literal|" '"
operator|+
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"latexEditorPath"
argument_list|)
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
else|else
name|Globals
operator|.
name|lang
argument_list|(
literal|"Pushed citations to %0"
argument_list|,
literal|"LatexEditor"
argument_list|)
expr_stmt|;
block|}
DECL|method|requiresBibtexKeys ()
specifier|public
name|boolean
name|requiresBibtexKeys
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
DECL|method|getSettingsPanel ()
specifier|public
name|JPanel
name|getSettingsPanel
parameter_list|()
block|{
if|if
condition|(
name|settings
operator|==
literal|null
condition|)
name|initSettingsPanel
argument_list|()
expr_stmt|;
name|ledPath
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
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
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"citeCommandLed"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|settings
return|;
block|}
DECL|method|initSettingsPanel ()
specifier|private
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|DefaultFormBuilder
name|builder
init|=
operator|new
name|DefaultFormBuilder
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref"
argument_list|,
literal|""
argument_list|)
argument_list|)
decl_stmt|;
name|builder
operator|.
name|append
argument_list|(
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
argument_list|)
expr_stmt|;
name|builder
operator|.
name|append
argument_list|(
name|ledPath
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
literal|"Cite command"
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
name|settings
operator|=
name|builder
operator|.
name|getPanel
argument_list|()
expr_stmt|;
block|}
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
literal|"latexEditorPath"
argument_list|,
name|ledPath
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
literal|"citeCommandLed"
argument_list|,
name|citeCommand
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

