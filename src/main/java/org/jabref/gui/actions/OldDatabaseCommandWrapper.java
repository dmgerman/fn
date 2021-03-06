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

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Optional
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|beans
operator|.
name|property
operator|.
name|ReadOnlyDoubleProperty
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|JabRefFrame
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|StateManager
import|;
end_import

begin_import
import|import
name|de
operator|.
name|saxsys
operator|.
name|mvvmfx
operator|.
name|utils
operator|.
name|commands
operator|.
name|CommandBase
import|;
end_import

begin_import
import|import
name|org
operator|.
name|fxmisc
operator|.
name|easybind
operator|.
name|EasyBind
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|org
operator|.
name|slf4j
operator|.
name|LoggerFactory
import|;
end_import

begin_comment
comment|/**  * A command that is only executable if a database is open.  * Deprecated use instead  * @see org.jabref.gui.actions.SimpleCommand  */
end_comment

begin_class
annotation|@
name|Deprecated
DECL|class|OldDatabaseCommandWrapper
specifier|public
class|class
name|OldDatabaseCommandWrapper
extends|extends
name|CommandBase
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Logger
name|LOGGER
init|=
name|LoggerFactory
operator|.
name|getLogger
argument_list|(
name|OldDatabaseCommandWrapper
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|command
specifier|private
specifier|final
name|Actions
name|command
decl_stmt|;
DECL|field|frame
specifier|private
specifier|final
name|JabRefFrame
name|frame
decl_stmt|;
DECL|method|OldDatabaseCommandWrapper (Actions command, JabRefFrame frame, StateManager stateManager)
specifier|public
name|OldDatabaseCommandWrapper
parameter_list|(
name|Actions
name|command
parameter_list|,
name|JabRefFrame
name|frame
parameter_list|,
name|StateManager
name|stateManager
parameter_list|)
block|{
name|this
operator|.
name|command
operator|=
name|command
expr_stmt|;
name|this
operator|.
name|frame
operator|=
name|frame
expr_stmt|;
name|this
operator|.
name|executable
operator|.
name|bind
argument_list|(
name|EasyBind
operator|.
name|map
argument_list|(
name|stateManager
operator|.
name|activeDatabaseProperty
argument_list|()
argument_list|,
name|Optional
operator|::
name|isPresent
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|execute ()
specifier|public
name|void
name|execute
parameter_list|()
block|{
if|if
condition|(
name|frame
operator|.
name|getTabbedPane
argument_list|()
operator|.
name|getTabs
argument_list|()
operator|.
name|size
argument_list|()
operator|>
literal|0
condition|)
block|{
try|try
block|{
name|frame
operator|.
name|getCurrentBasePanel
argument_list|()
operator|.
name|runCommand
argument_list|(
name|command
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|Throwable
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Problem with executing command: "
operator|+
name|command
argument_list|,
name|ex
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
name|LOGGER
operator|.
name|info
argument_list|(
literal|"Action '"
operator|+
name|command
operator|+
literal|"' must be disabled when no database is open."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|getProgress ()
specifier|public
name|double
name|getProgress
parameter_list|()
block|{
return|return
literal|0
return|;
block|}
annotation|@
name|Override
DECL|method|toString ()
specifier|public
name|String
name|toString
parameter_list|()
block|{
return|return
name|this
operator|.
name|command
operator|.
name|toString
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|progressProperty ()
specifier|public
name|ReadOnlyDoubleProperty
name|progressProperty
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

