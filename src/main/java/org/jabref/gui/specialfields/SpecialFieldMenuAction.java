begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.specialfields
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|specialfields
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
name|javax
operator|.
name|swing
operator|.
name|AbstractAction
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
name|JabRefFrame
import|;
end_import

begin_class
DECL|class|SpecialFieldMenuAction
specifier|public
class|class
name|SpecialFieldMenuAction
extends|extends
name|AbstractAction
block|{
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|field|actionName
specifier|private
specifier|final
name|String
name|actionName
decl_stmt|;
DECL|method|SpecialFieldMenuAction (SpecialFieldValueViewModel val, JabRefFrame frame)
specifier|public
name|SpecialFieldMenuAction
parameter_list|(
name|SpecialFieldValueViewModel
name|val
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|val
operator|.
name|getMenuString
argument_list|()
argument_list|,
name|val
operator|.
name|getSpecialFieldValueIcon
argument_list|()
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|actionName
operator|=
name|val
operator|.
name|getActionName
argument_list|()
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
if|if
condition|(
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|runCommand
argument_list|(
name|actionName
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit
