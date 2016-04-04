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
name|gui
operator|.
name|*
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
name|actions
operator|.
name|BaseAction
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
name|undo
operator|.
name|UndoableFieldChange
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
name|logic
operator|.
name|l10n
operator|.
name|Localization
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
name|model
operator|.
name|entry
operator|.
name|BibEntry
import|;
end_import

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: 5/24/12  * Time: 8:48 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|AttachFileAction
specifier|public
class|class
name|AttachFileAction
implements|implements
name|BaseAction
block|{
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|method|AttachFileAction (BasePanel panel)
specifier|public
name|AttachFileAction
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
name|this
operator|.
name|panel
operator|=
name|panel
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|action ()
specifier|public
name|void
name|action
parameter_list|()
block|{
if|if
condition|(
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|size
argument_list|()
operator|!=
literal|1
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"This operation requires exactly one item to be selected."
argument_list|)
argument_list|)
expr_stmt|;
return|return;
block|}
name|BibEntry
name|entry
init|=
name|panel
operator|.
name|getSelectedEntries
argument_list|()
operator|.
name|get
argument_list|(
literal|0
argument_list|)
decl_stmt|;
name|FileListEntry
name|flEntry
init|=
operator|new
name|FileListEntry
argument_list|(
literal|""
argument_list|,
literal|""
argument_list|)
decl_stmt|;
name|FileListEntryEditor
name|editor
init|=
operator|new
name|FileListEntryEditor
argument_list|(
name|panel
operator|.
name|frame
argument_list|()
argument_list|,
name|flEntry
argument_list|,
literal|false
argument_list|,
literal|true
argument_list|,
name|panel
operator|.
name|getBibDatabaseContext
argument_list|()
operator|.
name|getMetaData
argument_list|()
argument_list|)
decl_stmt|;
name|editor
operator|.
name|setVisible
argument_list|(
literal|true
argument_list|,
literal|true
argument_list|)
expr_stmt|;
if|if
condition|(
name|editor
operator|.
name|okPressed
argument_list|()
condition|)
block|{
name|FileListTableModel
name|model
init|=
operator|new
name|FileListTableModel
argument_list|()
decl_stmt|;
name|entry
operator|.
name|getFieldOptional
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|oldVal
lambda|->
name|model
operator|.
name|setContent
argument_list|(
name|oldVal
argument_list|)
argument_list|)
expr_stmt|;
name|model
operator|.
name|addEntry
argument_list|(
name|model
operator|.
name|getRowCount
argument_list|()
argument_list|,
name|flEntry
argument_list|)
expr_stmt|;
name|String
name|newVal
init|=
name|model
operator|.
name|getStringRepresentation
argument_list|()
decl_stmt|;
name|UndoableFieldChange
name|ce
init|=
operator|new
name|UndoableFieldChange
argument_list|(
name|entry
argument_list|,
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
name|entry
operator|.
name|getField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|)
argument_list|,
name|newVal
argument_list|)
decl_stmt|;
name|entry
operator|.
name|setField
argument_list|(
name|Globals
operator|.
name|FILE_FIELD
argument_list|,
name|newVal
argument_list|)
expr_stmt|;
name|panel
operator|.
name|undoManager
operator|.
name|addEdit
argument_list|(
name|ce
argument_list|)
expr_stmt|;
name|panel
operator|.
name|markBaseChanged
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

