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
name|javax
operator|.
name|swing
operator|.
name|*
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
name|java
operator|.
name|awt
operator|.
name|event
operator|.
name|ActionListener
import|;
end_import

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|File
import|;
end_import

begin_comment
comment|/**  * Action used to produce a "Browse" button for one of the text fields.  */
end_comment

begin_class
DECL|class|BrowseAction
specifier|public
class|class
name|BrowseAction
extends|extends
name|AbstractAction
implements|implements
name|ActionListener
block|{
DECL|field|frame
name|JFrame
name|frame
decl_stmt|;
DECL|field|comp
name|JTextField
name|comp
decl_stmt|;
DECL|field|dir
name|boolean
name|dir
decl_stmt|;
DECL|method|BrowseAction (JFrame frame, JTextField tc, boolean dir)
specifier|public
name|BrowseAction
parameter_list|(
name|JFrame
name|frame
parameter_list|,
name|JTextField
name|tc
parameter_list|,
name|boolean
name|dir
parameter_list|)
block|{
name|super
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
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
name|dir
operator|=
name|dir
expr_stmt|;
name|comp
operator|=
name|tc
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent e)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
name|String
name|chosen
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|dir
condition|)
name|chosen
operator|=
name|Globals
operator|.
name|getNewDir
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
else|else
name|chosen
operator|=
name|Globals
operator|.
name|getNewFile
argument_list|(
name|frame
argument_list|,
name|Globals
operator|.
name|prefs
argument_list|,
operator|new
name|File
argument_list|(
name|comp
operator|.
name|getText
argument_list|()
argument_list|)
argument_list|,
name|Globals
operator|.
name|NONE
argument_list|,
name|JFileChooser
operator|.
name|OPEN_DIALOG
argument_list|,
literal|false
argument_list|)
expr_stmt|;
if|if
condition|(
name|chosen
operator|!=
literal|null
condition|)
block|{
name|File
name|newFile
init|=
operator|new
name|File
argument_list|(
name|chosen
argument_list|)
decl_stmt|;
name|comp
operator|.
name|setText
argument_list|(
name|newFile
operator|.
name|getPath
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

