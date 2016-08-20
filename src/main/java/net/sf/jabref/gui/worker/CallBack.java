begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui.worker
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
operator|.
name|worker
package|;
end_package

begin_comment
comment|/**  * Represents a task that is to be executed on the GUI thread  */
end_comment

begin_interface
annotation|@
name|FunctionalInterface
DECL|interface|CallBack
specifier|public
interface|interface
name|CallBack
block|{
DECL|method|update ()
name|void
name|update
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

