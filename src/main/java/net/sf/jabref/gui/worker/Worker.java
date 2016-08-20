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
comment|/**  * Represents a task that is not to be executed on the GUI thread  */
end_comment

begin_interface
DECL|interface|Worker
specifier|public
interface|interface
name|Worker
extends|extends
name|Runnable
block|{
comment|// Nothing
block|}
end_interface

end_unit

