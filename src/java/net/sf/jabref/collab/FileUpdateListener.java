begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.collab
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|collab
package|;
end_package

begin_interface
DECL|interface|FileUpdateListener
specifier|public
interface|interface
name|FileUpdateListener
block|{
comment|/**    * The file has been updated. A new call will not result until the file has been modified again.    */
DECL|method|fileUpdated ()
specifier|public
name|void
name|fileUpdated
parameter_list|()
function_decl|;
comment|/**    * The file does no longer exist.    */
DECL|method|fileRemoved ()
specifier|public
name|void
name|fileRemoved
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

