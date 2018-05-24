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
name|net
operator|.
name|Authenticator
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JFrame
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JOptionPane
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|SwingUtilities
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|application
operator|.
name|Application
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|application
operator|.
name|Platform
import|;
end_import

begin_import
import|import
name|javafx
operator|.
name|stage
operator|.
name|Stage
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|cli
operator|.
name|ArgumentProcessor
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
name|remote
operator|.
name|JabRefMessageHandler
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
name|l10n
operator|.
name|Localization
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
name|net
operator|.
name|ProxyAuthenticator
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
name|net
operator|.
name|ProxyPreferences
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
name|net
operator|.
name|ProxyRegisterer
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
name|RemotePreferences
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
name|client
operator|.
name|RemoteClient
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
name|logic
operator|.
name|util
operator|.
name|JavaVersion
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
name|OS
import|;
end_import

begin_import
import|import
name|org
operator|.
name|jabref
operator|.
name|migrations
operator|.
name|PreferencesMigrations
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
name|EntryTypes
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
name|database
operator|.
name|BibDatabaseMode
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
name|entry
operator|.
name|InternalBibtexFields
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
comment|/**  * JabRef MainClass  */
end_comment

begin_class
DECL|class|JabRefMain
specifier|public
class|class
name|JabRefMain
extends|extends
name|Application
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
name|JabRefMain
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|arguments
specifier|private
specifier|static
name|String
index|[]
name|arguments
decl_stmt|;
DECL|method|main (String[] args)
specifier|public
specifier|static
name|void
name|main
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
name|arguments
operator|=
name|args
expr_stmt|;
name|launch
argument_list|(
name|arguments
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|start (Stage mainStage)
specifier|public
name|void
name|start
parameter_list|(
name|Stage
name|mainStage
parameter_list|)
throws|throws
name|Exception
block|{
name|Platform
operator|.
name|setImplicitExit
argument_list|(
literal|false
argument_list|)
expr_stmt|;
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
name|start
argument_list|(
name|arguments
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Tests if we are running an acceptable Java and terminates JabRef when we are sure the version is not supported.      * This test uses the requirements for the Java version as specified in<code>gradle.build</code>. It is possible to      * define a minimum version including the built number and to indicate whether Java 9 can be use (which it currently      * can't). It tries to compare this version number to the version of the currently running JVM. The check is      * optimistic and will rather return true even if we could not exactly determine the version.      *<p>      * Note: Users with an very old version like 1.6 will not profit from this since class versions are incompatible and      * JabRef won't even start. Currently, JabRef won't start with Java 9 either, but the warning that it cannot be used      * with this version is helpful anyway to prevent users to update from an old 1.8 directly to version 9. Additionally,      * we soon might have a JabRef that does start with Java 9 but is not perfectly compatible. Therefore, we should leave      * the Java 9 check alive.      */
DECL|method|ensureCorrectJavaVersion ()
specifier|private
specifier|static
name|void
name|ensureCorrectJavaVersion
parameter_list|()
block|{
comment|// Check if we are running an acceptable version of Java
specifier|final
name|BuildInfo
name|buildInfo
init|=
name|Globals
operator|.
name|BUILD_INFO
decl_stmt|;
name|JavaVersion
name|checker
init|=
operator|new
name|JavaVersion
argument_list|()
decl_stmt|;
specifier|final
name|boolean
name|java9Fail
init|=
operator|!
name|buildInfo
operator|.
name|isAllowJava9
argument_list|()
operator|&&
name|checker
operator|.
name|isJava9
argument_list|()
decl_stmt|;
specifier|final
name|boolean
name|versionFail
init|=
operator|!
name|checker
operator|.
name|isAtLeast
argument_list|(
name|buildInfo
operator|.
name|getMinRequiredJavaVersion
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|java9Fail
operator|||
name|versionFail
condition|)
block|{
name|StringBuilder
name|versionError
init|=
operator|new
name|StringBuilder
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Your current Java version (%0) is not supported. Please install version %1 or higher."
argument_list|,
name|checker
operator|.
name|getJavaVersion
argument_list|()
argument_list|,
name|buildInfo
operator|.
name|getMinRequiredJavaVersion
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
name|versionError
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|versionError
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Your Java Runtime Environment is located at %0."
argument_list|,
name|checker
operator|.
name|getJavaInstallationDirectory
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|buildInfo
operator|.
name|isAllowJava9
argument_list|()
condition|)
block|{
name|versionError
operator|.
name|append
argument_list|(
literal|"\n"
argument_list|)
expr_stmt|;
name|versionError
operator|.
name|append
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Note that currently, JabRef does not run with Java 9."
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|final
name|JFrame
name|frame
init|=
operator|new
name|JFrame
argument_list|()
decl_stmt|;
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
name|frame
argument_list|,
name|versionError
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
name|frame
operator|.
name|dispose
argument_list|()
expr_stmt|;
comment|// We exit on Java 9 error since this will definitely not work
if|if
condition|(
name|java9Fail
condition|)
block|{
name|System
operator|.
name|exit
argument_list|(
literal|0
argument_list|)
expr_stmt|;
block|}
block|}
block|}
DECL|method|start (String[] args)
specifier|private
specifier|static
name|void
name|start
parameter_list|(
name|String
index|[]
name|args
parameter_list|)
block|{
comment|// Init preferences
name|JabRefPreferences
name|preferences
init|=
name|JabRefPreferences
operator|.
name|getInstance
argument_list|()
decl_stmt|;
name|Globals
operator|.
name|prefs
operator|=
name|preferences
expr_stmt|;
comment|// Perform Migrations
name|migratePreferences
argument_list|()
expr_stmt|;
name|FallbackExceptionHandler
operator|.
name|installExceptionHandler
argument_list|()
expr_stmt|;
name|ensureCorrectJavaVersion
argument_list|()
expr_stmt|;
name|ProxyPreferences
name|proxyPreferences
init|=
name|preferences
operator|.
name|getProxyPreferences
argument_list|()
decl_stmt|;
name|ProxyRegisterer
operator|.
name|register
argument_list|(
name|proxyPreferences
argument_list|)
expr_stmt|;
if|if
condition|(
name|proxyPreferences
operator|.
name|isUseProxy
argument_list|()
operator|&&
name|proxyPreferences
operator|.
name|isUseAuthentication
argument_list|()
condition|)
block|{
name|Authenticator
operator|.
name|setDefault
argument_list|(
operator|new
name|ProxyAuthenticator
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Globals
operator|.
name|startBackgroundTasks
argument_list|()
expr_stmt|;
comment|// Update handling of special fields based on preferences
name|InternalBibtexFields
operator|.
name|updateSpecialFields
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
name|JabRefPreferences
operator|.
name|SERIALIZESPECIALFIELDS
argument_list|)
argument_list|)
expr_stmt|;
comment|// Update name of the time stamp field based on preferences
name|InternalBibtexFields
operator|.
name|updateTimeStampField
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getTimestampPreferences
argument_list|()
operator|.
name|getTimestampField
argument_list|()
argument_list|)
expr_stmt|;
comment|// Update which fields should be treated as numeric, based on preferences:
name|InternalBibtexFields
operator|.
name|setNumericFields
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getStringList
argument_list|(
name|JabRefPreferences
operator|.
name|NUMERIC_FIELDS
argument_list|)
argument_list|)
expr_stmt|;
comment|// Read list(s) of journal names and abbreviations
name|Globals
operator|.
name|journalAbbreviationLoader
operator|=
operator|new
name|JournalAbbreviationLoader
argument_list|()
expr_stmt|;
comment|/* Build list of Import and Export formats */
name|Globals
operator|.
name|IMPORT_FORMAT_READER
operator|.
name|resetImportFormats
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getImportFormatPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|prefs
operator|.
name|getXMPPreferences
argument_list|()
argument_list|,
name|Globals
operator|.
name|getFileUpdateMonitor
argument_list|()
argument_list|)
expr_stmt|;
name|EntryTypes
operator|.
name|loadCustomEntryTypes
argument_list|(
name|preferences
operator|.
name|loadCustomEntryTypes
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBTEX
argument_list|)
argument_list|,
name|preferences
operator|.
name|loadCustomEntryTypes
argument_list|(
name|BibDatabaseMode
operator|.
name|BIBLATEX
argument_list|)
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|exportFactory
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|getExporterFactory
argument_list|(
name|Globals
operator|.
name|journalAbbreviationLoader
argument_list|)
expr_stmt|;
comment|// Initialize protected terms loader
name|Globals
operator|.
name|protectedTermsLoader
operator|=
operator|new
name|ProtectedTermsLoader
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|getProtectedTermsPreferences
argument_list|()
argument_list|)
expr_stmt|;
comment|// Process arguments
name|ArgumentProcessor
name|argumentProcessor
init|=
operator|new
name|ArgumentProcessor
argument_list|(
name|args
argument_list|,
name|ArgumentProcessor
operator|.
name|Mode
operator|.
name|INITIAL_START
argument_list|)
decl_stmt|;
comment|// Check for running JabRef
name|RemotePreferences
name|remotePreferences
init|=
name|Globals
operator|.
name|prefs
operator|.
name|getRemotePreferences
argument_list|()
decl_stmt|;
if|if
condition|(
name|remotePreferences
operator|.
name|useRemoteServer
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|REMOTE_LISTENER
operator|.
name|open
argument_list|(
operator|new
name|JabRefMessageHandler
argument_list|()
argument_list|,
name|remotePreferences
operator|.
name|getPort
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
operator|!
name|Globals
operator|.
name|REMOTE_LISTENER
operator|.
name|isOpen
argument_list|()
condition|)
block|{
comment|// we are not alone, there is already a server out there, try to contact already running JabRef:
if|if
condition|(
operator|new
name|RemoteClient
argument_list|(
name|remotePreferences
operator|.
name|getPort
argument_list|()
argument_list|)
operator|.
name|sendCommandLineArguments
argument_list|(
name|args
argument_list|)
condition|)
block|{
comment|// We have successfully sent our command line options through the socket to another JabRef instance.
comment|// So we assume it's all taken care of, and quit.
name|LOGGER
operator|.
name|info
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Arguments passed on to running JabRef instance. Shutting down."
argument_list|)
argument_list|)
expr_stmt|;
name|Globals
operator|.
name|shutdownThreadPools
argument_list|()
expr_stmt|;
comment|// needed to tell JavaFx to stop
name|Platform
operator|.
name|exit
argument_list|()
expr_stmt|;
return|return;
block|}
block|}
comment|// we are alone, we start the server
name|Globals
operator|.
name|REMOTE_LISTENER
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
comment|// override used newline character with the one stored in the preferences
comment|// The preferences return the system newline character sequence as default
name|OS
operator|.
name|NEWLINE
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|NEWLINE
argument_list|)
expr_stmt|;
comment|// See if we should shut down now
if|if
condition|(
name|argumentProcessor
operator|.
name|shouldShutDown
argument_list|()
condition|)
block|{
name|Globals
operator|.
name|shutdownThreadPools
argument_list|()
expr_stmt|;
name|Platform
operator|.
name|exit
argument_list|()
expr_stmt|;
return|return;
block|}
comment|// If not, start GUI
name|SwingUtilities
operator|.
name|invokeLater
argument_list|(
parameter_list|()
lambda|->
operator|new
name|JabRefGUI
argument_list|(
name|argumentProcessor
operator|.
name|getParserResults
argument_list|()
argument_list|,
name|argumentProcessor
operator|.
name|isBlank
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
comment|/**      * Perform checks and changes for users with a preference set from an older JabRef version.      */
DECL|method|migratePreferences ()
specifier|private
specifier|static
name|void
name|migratePreferences
parameter_list|()
block|{
name|PreferencesMigrations
operator|.
name|upgradePrefsToOrgJabRef
argument_list|()
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeSortOrder
argument_list|()
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeFaultyEncodingStrings
argument_list|()
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeLabelPatternToBibtexKeyPattern
argument_list|()
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeImportFileAndDirePatterns
argument_list|()
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeStoredCustomEntryTypes
argument_list|()
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeKeyBindingsToJavaFX
argument_list|()
expr_stmt|;
name|PreferencesMigrations
operator|.
name|addCrossRefRelatedFieldsForAutoComplete
argument_list|()
expr_stmt|;
name|PreferencesMigrations
operator|.
name|upgradeObsoleteLookAndFeels
argument_list|()
expr_stmt|;
block|}
block|}
end_class

end_unit

