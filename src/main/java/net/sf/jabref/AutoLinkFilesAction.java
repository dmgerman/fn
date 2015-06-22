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
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collections
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
name|JDialog
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
name|undo
operator|.
name|NamedCompound
import|;
end_import

begin_comment
comment|/**  * This Action may only be used in a menu or button.  * Never in the entry editor. FileListEditor and EntryEditor have other ways to update the file links  */
end_comment

begin_class
DECL|class|AutoLinkFilesAction
specifier|public
class|class
name|AutoLinkFilesAction
extends|extends
name|AbstractAction
block|{
DECL|method|AutoLinkFilesAction ()
specifier|public
name|AutoLinkFilesAction
parameter_list|()
block|{
name|putValue
argument_list|(
name|SMALL_ICON
argument_list|,
name|GUIGlobals
operator|.
name|getImage
argument_list|(
literal|"autoGroup"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|NAME
argument_list|,
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically set file links"
argument_list|)
argument_list|)
expr_stmt|;
name|putValue
argument_list|(
name|ACCELERATOR_KEY
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getKey
argument_list|(
literal|"Automatically link files"
argument_list|)
argument_list|)
expr_stmt|;
block|}
DECL|method|actionPerformed (ActionEvent event)
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|event
parameter_list|)
block|{
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
name|entries
init|=
operator|new
name|ArrayList
argument_list|<
name|BibtexEntry
argument_list|>
argument_list|()
decl_stmt|;
name|Collections
operator|.
name|addAll
argument_list|(
name|entries
argument_list|,
name|JabRef
operator|.
name|jrf
operator|.
name|basePanel
argument_list|()
operator|.
name|getSelectedEntries
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|entries
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|JabRef
operator|.
name|jrf
operator|.
name|basePanel
argument_list|()
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"No entries selected."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|JDialog
name|diag
init|=
operator|new
name|JDialog
argument_list|(
name|JabRef
operator|.
name|jrf
argument_list|,
literal|true
argument_list|)
decl_stmt|;
specifier|final
name|NamedCompound
name|nc
init|=
operator|new
name|NamedCompound
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Automatically set file links"
argument_list|)
argument_list|)
decl_stmt|;
name|Runnable
name|runnable
init|=
name|Util
operator|.
name|autoSetLinks
argument_list|(
name|entries
argument_list|,
name|nc
argument_list|,
literal|null
argument_list|,
literal|null
argument_list|,
name|JabRef
operator|.
name|jrf
operator|.
name|basePanel
argument_list|()
operator|.
name|metaData
argument_list|()
argument_list|,
operator|new
name|ActionListener
argument_list|()
block|{
specifier|public
name|void
name|actionPerformed
parameter_list|(
name|ActionEvent
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|.
name|getID
argument_list|()
operator|>
literal|0
condition|)
block|{
comment|// entry has been updated in Util.autoSetLinks, only treat nc and status message
if|if
condition|(
name|nc
operator|.
name|hasEdits
argument_list|()
condition|)
block|{
name|nc
operator|.
name|end
argument_list|()
expr_stmt|;
name|JabRef
operator|.
name|jrf
operator|.
name|basePanel
argument_list|()
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|nc
argument_list|)
expr_stmt|;
name|JabRef
operator|.
name|jrf
operator|.
name|basePanel
argument_list|()
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
name|JabRef
operator|.
name|jrf
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Finished autosetting external links."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
name|JabRef
operator|.
name|jrf
operator|.
name|output
argument_list|(
name|Globals
operator|.
name|lang
argument_list|(
literal|"Finished autosetting external links."
argument_list|)
operator|+
literal|" "
operator|+
name|Globals
operator|.
name|lang
argument_list|(
literal|"No files found."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|,
name|diag
argument_list|)
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|execute
argument_list|(
name|runnable
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

