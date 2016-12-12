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
name|logic
operator|.
name|l10n
operator|.
name|Localization
import|;
end_import

begin_import
import|import
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|views
operator|.
name|FXMLView
import|;
end_import

begin_class
DECL|class|AbstractDialogView
specifier|public
specifier|abstract
class|class
name|AbstractDialogView
extends|extends
name|FXMLView
block|{
DECL|method|AbstractDialogView ()
specifier|public
name|AbstractDialogView
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
comment|// Set resource bundle to internal localizations
name|bundle
operator|=
name|Localization
operator|.
name|getMessages
argument_list|()
expr_stmt|;
block|}
DECL|method|show ()
specifier|public
specifier|abstract
name|void
name|show
parameter_list|()
function_decl|;
block|}
end_class

end_unit

