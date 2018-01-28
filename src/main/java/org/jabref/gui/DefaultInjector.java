begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|function
operator|.
name|Function
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|Globals
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
name|keyboard
operator|.
name|KeyBindingRepository
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
name|DefaultFileUpdateMonitor
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
name|TaskExecutor
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|logic
operator|.
name|journals
operator|.
name|JournalAbbreviationLoader
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|preferences
operator|.
name|PreferencesService
import|;
end_import

begin_import
import|import
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|injection
operator|.
name|Injector
import|;
end_import

begin_import
import|import
name|com
operator|.
name|airhacks
operator|.
name|afterburner
operator|.
name|injection
operator|.
name|PresenterFactory
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

begin_class
DECL|class|DefaultInjector
specifier|public
class|class
name|DefaultInjector
implements|implements
name|PresenterFactory
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
name|DefaultInjector
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * This method takes care of creating dependencies.      * By default, it just creates a new instance of the class.      * Dependencies without default constructor are constructed by hand.      */
DECL|method|createDependency (Class<?> clazz)
specifier|private
specifier|static
name|Object
name|createDependency
parameter_list|(
name|Class
argument_list|<
name|?
argument_list|>
name|clazz
parameter_list|)
block|{
if|if
condition|(
name|clazz
operator|==
name|DialogService
operator|.
name|class
condition|)
block|{
return|return
operator|new
name|FXDialogService
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|clazz
operator|==
name|TaskExecutor
operator|.
name|class
condition|)
block|{
return|return
name|Globals
operator|.
name|TASK_EXECUTOR
return|;
block|}
elseif|else
if|if
condition|(
name|clazz
operator|==
name|PreferencesService
operator|.
name|class
condition|)
block|{
return|return
name|Globals
operator|.
name|prefs
return|;
block|}
elseif|else
if|if
condition|(
name|clazz
operator|==
name|KeyBindingRepository
operator|.
name|class
condition|)
block|{
return|return
name|Globals
operator|.
name|getKeyPrefs
argument_list|()
return|;
block|}
elseif|else
if|if
condition|(
name|clazz
operator|==
name|JournalAbbreviationLoader
operator|.
name|class
condition|)
block|{
return|return
name|Globals
operator|.
name|journalAbbreviationLoader
return|;
block|}
elseif|else
if|if
condition|(
name|clazz
operator|==
name|StateManager
operator|.
name|class
condition|)
block|{
return|return
name|Globals
operator|.
name|stateManager
return|;
block|}
elseif|else
if|if
condition|(
name|clazz
operator|==
name|DefaultFileUpdateMonitor
operator|.
name|class
condition|)
block|{
return|return
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
return|;
block|}
else|else
block|{
try|try
block|{
return|return
name|clazz
operator|.
name|newInstance
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InstantiationException
decl||
name|IllegalAccessException
name|ex
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Cannot instantiate dependency: "
operator|+
name|clazz
argument_list|,
name|ex
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
block|}
block|}
annotation|@
name|Override
DECL|method|instantiatePresenter (Class<T> clazz, Function<String, Object> injectionContext)
specifier|public
parameter_list|<
name|T
parameter_list|>
name|T
name|instantiatePresenter
parameter_list|(
name|Class
argument_list|<
name|T
argument_list|>
name|clazz
parameter_list|,
name|Function
argument_list|<
name|String
argument_list|,
name|Object
argument_list|>
name|injectionContext
parameter_list|)
block|{
name|LOGGER
operator|.
name|debug
argument_list|(
literal|"Instantiate "
operator|+
name|clazz
operator|.
name|getName
argument_list|()
argument_list|)
expr_stmt|;
comment|// Use our own method to construct dependencies
name|Injector
operator|.
name|setInstanceSupplier
argument_list|(
name|DefaultInjector
operator|::
name|createDependency
argument_list|)
expr_stmt|;
return|return
name|Injector
operator|.
name|instantiatePresenter
argument_list|(
name|clazz
argument_list|,
name|injectionContext
argument_list|)
return|;
block|}
block|}
end_class

end_unit

