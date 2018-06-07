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
name|BasePanel
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
name|util
operator|.
name|BindingsHelper
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
comment|/**  * This wraps the old Swing commands so that they fit into the new infrastructure.  * In the long term, this class should be removed.  */
end_comment

begin_class
DECL|class|OldCommandWrapper
specifier|public
class|class
name|OldCommandWrapper
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
name|OldCommandWrapper
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
DECL|field|panel
specifier|private
specifier|final
name|BasePanel
name|panel
decl_stmt|;
DECL|method|OldCommandWrapper (Actions command, BasePanel panel)
specifier|public
name|OldCommandWrapper
parameter_list|(
name|Actions
name|command
parameter_list|,
name|BasePanel
name|panel
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
name|panel
operator|=
name|panel
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
try|try
block|{
name|panel
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
name|debug
argument_list|(
literal|"Cannot execute command "
operator|+
name|command
operator|+
literal|"."
argument_list|,
name|ex
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
DECL|method|setExecutable (boolean executable)
specifier|public
name|void
name|setExecutable
parameter_list|(
name|boolean
name|executable
parameter_list|)
block|{
name|this
operator|.
name|executable
operator|.
name|bind
argument_list|(
name|BindingsHelper
operator|.
name|constantOf
argument_list|(
name|executable
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit
