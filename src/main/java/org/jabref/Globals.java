begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref
package|package
name|org
operator|.
name|jabref
package|;
end_package

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|GraphicsEnvironment
import|;
end_import

begin_import
import|import
name|java
operator|.
name|awt
operator|.
name|Toolkit
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|UUID
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
name|ClipBoardManager
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
name|GlobalFocusListener
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
name|DefaultTaskExecutor
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
name|gui
operator|.
name|util
operator|.
name|ThemeLoader
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
name|exporter
operator|.
name|ExporterFactory
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
name|importer
operator|.
name|ImportFormatReader
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
name|logic
operator|.
name|protectedterms
operator|.
name|ProtectedTermsLoader
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
name|remote
operator|.
name|server
operator|.
name|RemoteListenerServerLifecycle
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
name|util
operator|.
name|BuildInfo
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|model
operator|.
name|util
operator|.
name|FileUpdateMonitor
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
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|com
operator|.
name|google
operator|.
name|common
operator|.
name|base
operator|.
name|StandardSystemProperty
import|;
end_import

begin_import
import|import
name|com
operator|.
name|microsoft
operator|.
name|applicationinsights
operator|.
name|TelemetryClient
import|;
end_import

begin_import
import|import
name|com
operator|.
name|microsoft
operator|.
name|applicationinsights
operator|.
name|TelemetryConfiguration
import|;
end_import

begin_import
import|import
name|com
operator|.
name|microsoft
operator|.
name|applicationinsights
operator|.
name|telemetry
operator|.
name|SessionState
import|;
end_import

begin_class
DECL|class|Globals
specifier|public
class|class
name|Globals
block|{
comment|// JabRef version info
DECL|field|BUILD_INFO
specifier|public
specifier|static
specifier|final
name|BuildInfo
name|BUILD_INFO
init|=
operator|new
name|BuildInfo
argument_list|()
decl_stmt|;
comment|// Remote listener
DECL|field|REMOTE_LISTENER
specifier|public
specifier|static
specifier|final
name|RemoteListenerServerLifecycle
name|REMOTE_LISTENER
init|=
operator|new
name|RemoteListenerServerLifecycle
argument_list|()
decl_stmt|;
DECL|field|IMPORT_FORMAT_READER
specifier|public
specifier|static
specifier|final
name|ImportFormatReader
name|IMPORT_FORMAT_READER
init|=
operator|new
name|ImportFormatReader
argument_list|()
decl_stmt|;
DECL|field|TASK_EXECUTOR
specifier|public
specifier|static
specifier|final
name|TaskExecutor
name|TASK_EXECUTOR
init|=
operator|new
name|DefaultTaskExecutor
argument_list|()
decl_stmt|;
comment|// In the main program, this field is initialized in JabRef.java
comment|// Each test case initializes this field if required
DECL|field|prefs
specifier|public
specifier|static
name|JabRefPreferences
name|prefs
decl_stmt|;
comment|/**      * This field is initialized upon startup.      * Only GUI code is allowed to access it, logic code should use dependency injection.      */
DECL|field|journalAbbreviationLoader
specifier|public
specifier|static
name|JournalAbbreviationLoader
name|journalAbbreviationLoader
decl_stmt|;
comment|/**      * This field is initialized upon startup.      * Only GUI code is allowed to access it, logic code should use dependency injection.      */
DECL|field|protectedTermsLoader
specifier|public
specifier|static
name|ProtectedTermsLoader
name|protectedTermsLoader
decl_stmt|;
comment|/**      * Manager for the state of the GUI.      */
DECL|field|clipboardManager
specifier|public
specifier|static
name|ClipBoardManager
name|clipboardManager
init|=
operator|new
name|ClipBoardManager
argument_list|()
decl_stmt|;
DECL|field|stateManager
specifier|public
specifier|static
name|StateManager
name|stateManager
init|=
operator|new
name|StateManager
argument_list|()
decl_stmt|;
DECL|field|exportFactory
specifier|public
specifier|static
name|ExporterFactory
name|exportFactory
decl_stmt|;
comment|// Key binding preferences
DECL|field|keyBindingRepository
specifier|private
specifier|static
name|KeyBindingRepository
name|keyBindingRepository
decl_stmt|;
comment|// Background tasks
DECL|field|focusListener
specifier|private
specifier|static
name|GlobalFocusListener
name|focusListener
decl_stmt|;
DECL|field|fileUpdateMonitor
specifier|private
specifier|static
name|DefaultFileUpdateMonitor
name|fileUpdateMonitor
decl_stmt|;
DECL|field|themeLoader
specifier|private
specifier|static
name|ThemeLoader
name|themeLoader
decl_stmt|;
DECL|field|telemetryClient
specifier|private
specifier|static
name|TelemetryClient
name|telemetryClient
decl_stmt|;
DECL|method|Globals ()
specifier|private
name|Globals
parameter_list|()
block|{     }
comment|// Key binding preferences
DECL|method|getKeyPrefs ()
specifier|public
specifier|static
specifier|synchronized
name|KeyBindingRepository
name|getKeyPrefs
parameter_list|()
block|{
if|if
condition|(
name|keyBindingRepository
operator|==
literal|null
condition|)
block|{
name|keyBindingRepository
operator|=
name|prefs
operator|.
name|getKeyBindingRepository
argument_list|()
expr_stmt|;
block|}
return|return
name|keyBindingRepository
return|;
block|}
comment|// Background tasks
DECL|method|startBackgroundTasks ()
specifier|public
specifier|static
name|void
name|startBackgroundTasks
parameter_list|()
block|{
name|Globals
operator|.
name|focusListener
operator|=
operator|new
name|GlobalFocusListener
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|fileUpdateMonitor
operator|=
operator|new
name|DefaultFileUpdateMonitor
argument_list|()
expr_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeInterruptableTask
argument_list|(
name|Globals
operator|.
name|fileUpdateMonitor
argument_list|,
literal|"FileUpdateMonitor"
argument_list|)
expr_stmt|;
name|themeLoader
operator|=
operator|new
name|ThemeLoader
argument_list|(
name|fileUpdateMonitor
argument_list|)
expr_stmt|;
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|shouldCollectTelemetry
argument_list|()
operator|&&
operator|!
name|GraphicsEnvironment
operator|.
name|isHeadless
argument_list|()
condition|)
block|{
name|startTelemetryClient
argument_list|()
expr_stmt|;
block|}
block|}
DECL|method|stopTelemetryClient ()
specifier|private
specifier|static
name|void
name|stopTelemetryClient
parameter_list|()
block|{
if|if
condition|(
name|Globals
operator|.
name|prefs
operator|.
name|shouldCollectTelemetry
argument_list|()
condition|)
block|{
name|getTelemetryClient
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|client
lambda|->
name|client
operator|.
name|trackSessionState
argument_list|(
name|SessionState
operator|.
name|End
argument_list|)
argument_list|)
expr_stmt|;
name|getTelemetryClient
argument_list|()
operator|.
name|ifPresent
argument_list|(
name|client
lambda|->
name|client
operator|.
name|flush
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
DECL|method|startTelemetryClient ()
specifier|private
specifier|static
name|void
name|startTelemetryClient
parameter_list|()
block|{
name|TelemetryConfiguration
name|telemetryConfiguration
init|=
name|TelemetryConfiguration
operator|.
name|getActive
argument_list|()
decl_stmt|;
name|telemetryConfiguration
operator|.
name|setInstrumentationKey
argument_list|(
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getAzureInstrumentationKey
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryConfiguration
operator|.
name|setTrackingIsDisabled
argument_list|(
operator|!
name|Globals
operator|.
name|prefs
operator|.
name|shouldCollectTelemetry
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryClient
operator|=
operator|new
name|TelemetryClient
argument_list|(
name|telemetryConfiguration
argument_list|)
expr_stmt|;
name|telemetryClient
operator|.
name|getContext
argument_list|()
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"JabRef version"
argument_list|,
name|Globals
operator|.
name|BUILD_INFO
operator|.
name|getVersion
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryClient
operator|.
name|getContext
argument_list|()
operator|.
name|getProperties
argument_list|()
operator|.
name|put
argument_list|(
literal|"Java version"
argument_list|,
name|StandardSystemProperty
operator|.
name|JAVA_VERSION
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryClient
operator|.
name|getContext
argument_list|()
operator|.
name|getUser
argument_list|()
operator|.
name|setId
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getOrCreateUserId
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryClient
operator|.
name|getContext
argument_list|()
operator|.
name|getSession
argument_list|()
operator|.
name|setId
argument_list|(
name|UUID
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryClient
operator|.
name|getContext
argument_list|()
operator|.
name|getDevice
argument_list|()
operator|.
name|setOperatingSystem
argument_list|(
name|StandardSystemProperty
operator|.
name|OS_NAME
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryClient
operator|.
name|getContext
argument_list|()
operator|.
name|getDevice
argument_list|()
operator|.
name|setOperatingSystemVersion
argument_list|(
name|StandardSystemProperty
operator|.
name|OS_VERSION
operator|.
name|value
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryClient
operator|.
name|getContext
argument_list|()
operator|.
name|getDevice
argument_list|()
operator|.
name|setScreenResolution
argument_list|(
name|Toolkit
operator|.
name|getDefaultToolkit
argument_list|()
operator|.
name|getScreenSize
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
name|telemetryClient
operator|.
name|trackSessionState
argument_list|(
name|SessionState
operator|.
name|Start
argument_list|)
expr_stmt|;
block|}
DECL|method|getFocusListener ()
specifier|public
specifier|static
name|GlobalFocusListener
name|getFocusListener
parameter_list|()
block|{
return|return
name|focusListener
return|;
block|}
DECL|method|getFileUpdateMonitor ()
specifier|public
specifier|static
name|FileUpdateMonitor
name|getFileUpdateMonitor
parameter_list|()
block|{
return|return
name|fileUpdateMonitor
return|;
block|}
DECL|method|shutdownThreadPools ()
specifier|public
specifier|static
name|void
name|shutdownThreadPools
parameter_list|()
block|{
name|TASK_EXECUTOR
operator|.
name|shutdown
argument_list|()
expr_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|shutdownEverything
argument_list|()
expr_stmt|;
block|}
DECL|method|stopBackgroundTasks ()
specifier|public
specifier|static
name|void
name|stopBackgroundTasks
parameter_list|()
block|{
name|stopTelemetryClient
argument_list|()
expr_stmt|;
block|}
DECL|method|getTelemetryClient ()
specifier|public
specifier|static
name|Optional
argument_list|<
name|TelemetryClient
argument_list|>
name|getTelemetryClient
parameter_list|()
block|{
return|return
name|Optional
operator|.
name|ofNullable
argument_list|(
name|telemetryClient
argument_list|)
return|;
block|}
DECL|method|getThemeLoader ()
specifier|public
specifier|static
name|ThemeLoader
name|getThemeLoader
parameter_list|()
block|{
return|return
name|themeLoader
return|;
block|}
block|}
end_class

end_unit

