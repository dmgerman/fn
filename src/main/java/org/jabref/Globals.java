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
name|org
operator|.
name|jabref
operator|.
name|collab
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
name|gui
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
name|gui
operator|.
name|keyboard
operator|.
name|KeyBindingPreferences
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
name|preferences
operator|.
name|JabRefPreferences
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
comment|// Key binding preferences
DECL|field|keyPrefs
specifier|private
specifier|static
name|KeyBindingPreferences
name|keyPrefs
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
name|FileUpdateMonitor
name|fileUpdateMonitor
decl_stmt|;
comment|// Key binding preferences
DECL|method|getKeyPrefs ()
specifier|public
specifier|static
name|KeyBindingPreferences
name|getKeyPrefs
parameter_list|()
block|{
if|if
condition|(
name|keyPrefs
operator|==
literal|null
condition|)
block|{
name|keyPrefs
operator|=
operator|new
name|KeyBindingPreferences
argument_list|(
name|prefs
argument_list|)
expr_stmt|;
block|}
return|return
name|keyPrefs
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
name|FileUpdateMonitor
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
block|}
end_class

end_unit

