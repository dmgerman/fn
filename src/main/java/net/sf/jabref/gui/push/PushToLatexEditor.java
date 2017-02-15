begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.push
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|push
package|;
end_package

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
name|gui
operator|.
name|IconTheme
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
name|preferences
operator|.
name|JabRefPreferences
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
extends|extends
name|AbstractPushToApplication
implements|implements
name|PushToApplication
block|{
annotation|@
name|Override
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
annotation|@
name|Override
DECL|method|getIcon ()
specifier|public
name|Icon
name|getIcon
parameter_list|()
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|EDIT
operator|.
name|getSmallIcon
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|getCommandLine (String keyString)
specifier|protected
name|String
index|[]
name|getCommandLine
parameter_list|(
name|String
name|keyString
parameter_list|)
block|{
return|return
operator|new
name|String
index|[]
block|{
name|commandPath
block|,
literal|"-i"
block|,
name|getCiteCommand
argument_list|()
operator|+
literal|"{"
operator|+
name|keyString
operator|+
literal|"}"
block|}
return|;
block|}
annotation|@
name|Override
DECL|method|getCommandName ()
specifier|protected
name|String
name|getCommandName
parameter_list|()
block|{
return|return
literal|"LEd.exe"
return|;
block|}
annotation|@
name|Override
DECL|method|initParameters ()
specifier|protected
name|void
name|initParameters
parameter_list|()
block|{
name|commandPathPreferenceKey
operator|=
name|JabRefPreferences
operator|.
name|LATEX_EDITOR_PATH
expr_stmt|;
block|}
block|}
end_class

end_unit

