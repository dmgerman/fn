begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
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
name|event
operator|.
name|ActionEvent
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
name|Action
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|text
operator|.
name|JTextComponent
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
name|gui
operator|.
name|ClipBoardManager
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
name|logic
operator|.
name|net
operator|.
name|URLUtil
import|;
end_import

begin_class
DECL|class|PasteAction
specifier|public
class|class
name|PasteAction
extends|extends
name|AbstractAction
block|{
DECL|field|target
specifier|private
specifier|final
name|Component
name|target
decl_stmt|;
DECL|method|PasteAction (Component target)
specifier|public
name|PasteAction
parameter_list|(
name|Component
name|target
parameter_list|)
block|{
name|this
operator|.
name|target
operator|=
name|target
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Paste from clipboard"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SHORT_DESCRIPTION
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Paste from clipboard"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|SMALL_ICON
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PASTE
operator|.
name|getSmallIcon
argument_list|()
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|LARGE_ICON_KEY
argument_list|,
name|IconTheme
operator|.
name|JabRefIcon
operator|.
name|PASTE
operator|.
name|getIcon
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent evt)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|evt
parameter_list|)
block|{
name|String
name|data
init|=
operator|new
name|ClipBoardManager
argument_list|()
operator|.
name|getClipboardContents
argument_list|()
decl_stmt|;
if|if
condition|(
name|data
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
return|return;
block|}
comment|// auto corrections
comment|// clean Google search URLs
name|data
operator|=
name|URLUtil
operator|.
name|cleanGoogleSearchURL
argument_list|(
name|data
argument_list|)
expr_stmt|;
comment|// caller
if|if
condition|(
name|target
operator|instanceof
name|JTextComponent
condition|)
block|{
name|JTextComponent
name|textField
init|=
operator|(
name|JTextComponent
operator|)
name|target
decl_stmt|;
comment|// replace text selection
name|textField
operator|.
name|replaceSelection
argument_list|(
name|data
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

