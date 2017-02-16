begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.gui.filelist
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|gui
operator|.
name|filelist
package|;
end_package

begin_comment
comment|/**  * An implementation of this interface is called to confirm whether a FileListEntryEditor  * is ready to close when Ok is pressed, or whether there is a problem that needs to be  * resolved first.  */
end_comment

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|ConfirmCloseFileListEntryEditor
specifier|public
interface|interface
name|ConfirmCloseFileListEntryEditor
block|{
DECL|method|confirmClose (FileListEntry entry)
name|boolean
name|confirmClose
parameter_list|(
name|FileListEntry
name|entry
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

