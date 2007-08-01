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
name|Icon
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
name|BasePanel
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
name|BibtexEntry
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
name|GUIGlobals
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

begin_class
DECL|class|PushToWinEdt
specifier|public
class|class
name|PushToWinEdt
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
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|lang
argument_list|(
literal|"Insert selected citations into WinEdt"
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
literal|"WinEdt"
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
literal|"Push selection to WinEdt"
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
literal|"winedt"
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
literal|"Push to WinEdt"
return|;
block|}
DECL|method|pushEntries (BibtexEntry[] entries, String keyString)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibtexEntry
index|[]
name|entries
parameter_list|,
name|String
name|keyString
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
name|winEdt
init|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
literal|"winEdtPath"
argument_list|)
decl_stmt|;
if|if
condition|(
operator|(
name|winEdt
operator|==
literal|null
operator|)
operator|||
operator|(
name|winEdt
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
literal|"\"[InsText('\\"
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
literal|"citeCommand"
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
literal|"}');]\""
argument_list|)
decl_stmt|;
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|winEdt
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
literal|"winEdtPath"
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
literal|"Pushed citations to WinEdt"
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
block|}
end_class

end_unit

