begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.help
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|help
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|IOException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Objects
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
name|concurrent
operator|.
name|TimeUnit
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
name|DialogService
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
name|BackgroundTask
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
name|util
operator|.
name|Version
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
comment|/**  * This worker checks if there is a new version of JabRef available. If there is it will display a dialog to the user  * offering him multiple options to proceed (see changelog, go to the download page, ignore this version, and remind  * later).  *  * If the versions check is executed manually and this is the latest version it will also display a dialog to inform the  * user.  */
end_comment

begin_class
DECL|class|VersionWorker
specifier|public
class|class
name|VersionWorker
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
name|VersionWorker
operator|.
name|class
argument_list|)
decl_stmt|;
comment|/**      * The current version of the installed JabRef      */
DECL|field|installedVersion
specifier|private
specifier|final
name|Version
name|installedVersion
decl_stmt|;
comment|/**      * The version which was previously ignored by the user      */
DECL|field|toBeIgnored
specifier|private
specifier|final
name|Version
name|toBeIgnored
decl_stmt|;
DECL|field|dialogService
specifier|private
specifier|final
name|DialogService
name|dialogService
decl_stmt|;
DECL|field|taskExecutor
specifier|private
specifier|final
name|TaskExecutor
name|taskExecutor
decl_stmt|;
DECL|method|VersionWorker (Version installedVersion, Version toBeIgnored, DialogService dialogService, TaskExecutor taskExecutor)
specifier|public
name|VersionWorker
parameter_list|(
name|Version
name|installedVersion
parameter_list|,
name|Version
name|toBeIgnored
parameter_list|,
name|DialogService
name|dialogService
parameter_list|,
name|TaskExecutor
name|taskExecutor
parameter_list|)
block|{
name|this
operator|.
name|installedVersion
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|installedVersion
argument_list|)
expr_stmt|;
name|this
operator|.
name|toBeIgnored
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|toBeIgnored
argument_list|)
expr_stmt|;
name|this
operator|.
name|dialogService
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|dialogService
argument_list|)
expr_stmt|;
name|this
operator|.
name|taskExecutor
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
block|}
comment|/**      * Returns a newer version excluding any non-stable versions, except if the installed one is unstable too. If no      * newer version was found, then an empty optional is returned.      */
DECL|method|getNewVersion ()
specifier|private
name|Optional
argument_list|<
name|Version
argument_list|>
name|getNewVersion
parameter_list|()
throws|throws
name|IOException
block|{
name|List
argument_list|<
name|Version
argument_list|>
name|availableVersions
init|=
name|Version
operator|.
name|getAllAvailableVersions
argument_list|()
decl_stmt|;
return|return
name|installedVersion
operator|.
name|shouldBeUpdatedTo
argument_list|(
name|availableVersions
argument_list|)
return|;
block|}
DECL|method|checkForNewVersionAsync ()
specifier|public
name|void
name|checkForNewVersionAsync
parameter_list|()
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|this
operator|::
name|getNewVersion
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|version
lambda|->
name|showUpdateInfo
argument_list|(
name|version
argument_list|,
literal|true
argument_list|)
argument_list|)
operator|.
name|onFailure
argument_list|(
name|exception
lambda|->
name|showConnectionError
argument_list|(
name|exception
argument_list|,
literal|true
argument_list|)
argument_list|)
operator|.
name|executeWith
argument_list|(
name|taskExecutor
argument_list|)
expr_stmt|;
block|}
DECL|method|checkForNewVersionDelayed ()
specifier|public
name|void
name|checkForNewVersionDelayed
parameter_list|()
block|{
name|BackgroundTask
operator|.
name|wrap
argument_list|(
name|this
operator|::
name|getNewVersion
argument_list|)
operator|.
name|onSuccess
argument_list|(
name|version
lambda|->
name|showUpdateInfo
argument_list|(
name|version
argument_list|,
literal|false
argument_list|)
argument_list|)
operator|.
name|onFailure
argument_list|(
name|exception
lambda|->
name|showConnectionError
argument_list|(
name|exception
argument_list|,
literal|false
argument_list|)
argument_list|)
operator|.
name|scheduleWith
argument_list|(
name|taskExecutor
argument_list|,
literal|30
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|)
expr_stmt|;
block|}
comment|/**      * Prints the connection problem to the status bar and shows a dialog if it was executed manually      */
DECL|method|showConnectionError (Exception exception, boolean manualExecution)
specifier|private
name|void
name|showConnectionError
parameter_list|(
name|Exception
name|exception
parameter_list|,
name|boolean
name|manualExecution
parameter_list|)
block|{
name|String
name|couldNotConnect
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not connect to the update server."
argument_list|)
decl_stmt|;
name|String
name|tryLater
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"Please try again later and/or check your network connection."
argument_list|)
decl_stmt|;
if|if
condition|(
name|manualExecution
condition|)
block|{
name|dialogService
operator|.
name|showErrorDialogAndWait
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
argument_list|,
name|couldNotConnect
operator|+
literal|"\n"
operator|+
name|tryLater
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
name|LOGGER
operator|.
name|warn
argument_list|(
name|couldNotConnect
operator|+
literal|" "
operator|+
name|tryLater
argument_list|,
name|exception
argument_list|)
expr_stmt|;
block|}
comment|/**      * Prints up-to-date to the status bar (and shows a dialog it was executed manually) if there is now new version.      * Shows a "New Version" Dialog to the user if there is.      */
DECL|method|showUpdateInfo (Optional<Version> newerVersion, boolean manualExecution)
specifier|private
name|void
name|showUpdateInfo
parameter_list|(
name|Optional
argument_list|<
name|Version
argument_list|>
name|newerVersion
parameter_list|,
name|boolean
name|manualExecution
parameter_list|)
block|{
comment|// no new version could be found, only respect the ignored version on automated version checks
if|if
condition|(
operator|!
name|newerVersion
operator|.
name|isPresent
argument_list|()
operator|||
operator|(
name|newerVersion
operator|.
name|get
argument_list|()
operator|.
name|equals
argument_list|(
name|toBeIgnored
argument_list|)
operator|&&
operator|!
name|manualExecution
operator|)
condition|)
block|{
if|if
condition|(
name|manualExecution
condition|)
block|{
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef is up-to-date."
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
else|else
block|{
comment|// notify the user about a newer version
operator|new
name|NewVersionDialog
argument_list|(
name|installedVersion
argument_list|,
name|newerVersion
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|showAndWait
argument_list|()
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

