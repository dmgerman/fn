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
name|Optional
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
name|javafx
operator|.
name|scene
operator|.
name|control
operator|.
name|TextArea
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|JabRefGUI
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
name|ClipBoardManager
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
name|model
operator|.
name|entry
operator|.
name|identifier
operator|.
name|DOI
import|;
end_import

begin_comment
comment|/**  * Copies the doi url to the clipboard  */
end_comment

begin_class
DECL|class|CopyDoiUrlAction
specifier|public
class|class
name|CopyDoiUrlAction
extends|extends
name|AbstractAction
block|{
DECL|field|component
specifier|private
name|TextArea
name|component
init|=
literal|null
decl_stmt|;
DECL|field|identifier
specifier|private
name|String
name|identifier
decl_stmt|;
DECL|method|CopyDoiUrlAction (String identifier)
specifier|public
name|CopyDoiUrlAction
parameter_list|(
name|String
name|identifier
parameter_list|)
block|{
name|super
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Copy DOI url"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|identifier
operator|=
name|identifier
expr_stmt|;
block|}
DECL|method|CopyDoiUrlAction (TextArea component)
specifier|public
name|CopyDoiUrlAction
parameter_list|(
name|TextArea
name|component
parameter_list|)
block|{
name|this
argument_list|(
name|component
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|component
operator|=
name|component
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|identifier
operator|=
name|component
operator|==
literal|null
condition|?
name|identifier
else|:
name|component
operator|.
name|getText
argument_list|()
expr_stmt|;
name|Optional
argument_list|<
name|String
argument_list|>
name|urlOptional
init|=
name|DOI
operator|.
name|parse
argument_list|(
name|identifier
argument_list|)
operator|.
name|map
argument_list|(
name|DOI
operator|::
name|getURIAsASCIIString
argument_list|)
decl_stmt|;
if|if
condition|(
name|urlOptional
operator|.
name|isPresent
argument_list|()
condition|)
block|{
name|ClipBoardManager
name|clipBoard
init|=
operator|new
name|ClipBoardManager
argument_list|()
decl_stmt|;
name|clipBoard
operator|.
name|setClipboardContents
argument_list|(
name|urlOptional
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"The link has been copied to the clipboard."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|JabRefGUI
operator|.
name|getMainFrame
argument_list|()
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Invalid DOI: '%0'."
argument_list|,
name|identifier
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

