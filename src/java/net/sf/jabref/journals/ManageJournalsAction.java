begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.journals
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
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
name|AbstractAction
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

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefFrame
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
name|Util
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Sep 22, 2005  * Time: 10:45:02 PM  * To browseOld this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|ManageJournalsAction
specifier|public
class|class
name|ManageJournalsAction
extends|extends
name|AbstractAction
block|{
DECL|field|frame
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|ManageJournalsAction (JabRefFrame frame)
specifier|public
name|ManageJournalsAction
parameter_list|(
name|JabRefFrame
name|frame
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Manage journal abbreviations"
argument_list|)
argument_list|)
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent actionEvent)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|actionEvent
parameter_list|)
block|{
name|ManageJournalsPanel
name|panel
init|=
operator|new
name|ManageJournalsPanel
argument_list|(
name|frame
argument_list|)
decl_stmt|;
name|Util
operator|.
name|placeDialog
argument_list|(
name|panel
operator|.
name|getDialog
argument_list|()
argument_list|,
name|frame
argument_list|)
expr_stmt|;
name|panel
operator|.
name|setValues
argument_list|()
expr_stmt|;
name|panel
operator|.
name|getDialog
argument_list|()
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

