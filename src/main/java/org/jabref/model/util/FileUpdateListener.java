begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.model.util
package|package
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
package|;
end_package

begin_interface
DECL|interface|FileUpdateListener
specifier|public
interface|interface
name|FileUpdateListener
block|{
comment|/**      * The file has been updated. A new call will not result until the file has been modified again.      */
DECL|method|fileUpdated ()
name|void
name|fileUpdated
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

