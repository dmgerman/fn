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

begin_comment
comment|/**  * BaseAction is used to define actions that are called from the  * base frame through runCommand(). runCommand() finds the  * appropriate BaseAction object, and runs its action() method.  */
end_comment

begin_class
DECL|class|BaseAction
specifier|public
specifier|abstract
class|class
name|BaseAction
block|{
comment|//implements Runnable {
DECL|method|action ()
specifier|public
specifier|abstract
name|void
name|action
parameter_list|()
throws|throws
name|Throwable
function_decl|;
block|}
end_class

end_unit

