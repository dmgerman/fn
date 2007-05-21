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
name|gui
operator|.
name|FileListEntry
import|;
end_import

begin_comment
comment|/**  * An implementation of this interface is called to confirm whether a FileListEntryEditor  * is ready to close when Ok is pressed, or whether there is a problem that needs to be  * resolved first.  */
end_comment

begin_interface
DECL|interface|ConfirmCloseFileListEntryEditor
specifier|public
interface|interface
name|ConfirmCloseFileListEntryEditor
block|{
DECL|method|confirmClose (FileListEntry entry)
specifier|public
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

