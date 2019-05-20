begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.actions
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|actions
package|;
end_package

begin_comment
comment|/**  * BaseAction is used to define actions that are called from the  * base frame through runCommand(). runCommand() finds the  * appropriate BaseAction object, and runs its action() method.  *  * @deprecated use {@link SimpleCommand} instead  */
end_comment

begin_interface
annotation|@
name|FunctionalInterface
annotation|@
name|Deprecated
DECL|interface|BaseAction
specifier|public
interface|interface
name|BaseAction
block|{
DECL|method|action ()
name|void
name|action
parameter_list|()
throws|throws
name|Exception
function_decl|;
block|}
end_interface

end_unit

