begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.journals
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|journals
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
name|Action
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|application
operator|.
name|Platform
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
name|actions
operator|.
name|MnemonicAwareAction
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

begin_class
DECL|class|ManageJournalsAction
specifier|public
class|class
name|ManageJournalsAction
extends|extends
name|MnemonicAwareAction
block|{
DECL|method|ManageJournalsAction ()
specifier|public
name|ManageJournalsAction
parameter_list|()
block|{
name|super
argument_list|()
expr_stmt|;
name|putValue
argument_list|(
name|Action
operator|.
name|NAME
argument_list|,
name|Localization
operator|.
name|menuTitle
argument_list|(
literal|"Manage journal abbreviations"
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|actionPerformed (ActionEvent actionEvent)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|Platform
operator|.
name|runLater
argument_list|(
parameter_list|()
lambda|->
operator|new
name|ManageJournalAbbreviationsView
argument_list|()
operator|.
name|show
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
