begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.worker
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|worker
package|;
end_package

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|Log
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|commons
operator|.
name|logging
operator|.
name|LogFactory
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
name|help
operator|.
name|NewVersionDialog
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
name|javax
operator|.
name|swing
operator|.
name|*
import|;
end_import

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
name|Collections
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
name|ExecutionException
import|;
end_import

begin_comment
comment|/**  * This worker checks if there is a new version of JabRef available.  * If there is it will display a Dialog to the User offering him multiple Options to proceed  * (see changelog, go to the download page, ignore this version, and remind later).  *  * If the versions check is executed manually and this is the latest version it will also display a dialog to inform the user.  */
end_comment

begin_class
DECL|class|VersionWorker
specifier|public
class|class
name|VersionWorker
extends|extends
name|SwingWorker
argument_list|<
name|List
argument_list|<
name|Version
argument_list|>
argument_list|,
name|Void
argument_list|>
block|{
DECL|field|LOGGER
specifier|private
specifier|static
specifier|final
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
argument_list|(
name|VersionWorker
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|mainFrame
specifier|private
specifier|final
name|JabRefFrame
name|mainFrame
decl_stmt|;
comment|/** If this versions check is executed automatically (eg. on startup) or manually by the user */
DECL|field|manualExecution
specifier|private
specifier|final
name|boolean
name|manualExecution
decl_stmt|;
comment|/** The current version of the installed JabRef */
DECL|field|installedVersion
specifier|private
specifier|final
name|Version
name|installedVersion
decl_stmt|;
comment|/** The version which was previously ignored by the user */
DECL|field|toBeIgnored
specifier|private
specifier|final
name|Version
name|toBeIgnored
decl_stmt|;
DECL|method|VersionWorker (JabRefFrame mainFrame, boolean manualExecution, Version installedVersion, Version toBeIgnored)
specifier|public
name|VersionWorker
parameter_list|(
name|JabRefFrame
name|mainFrame
parameter_list|,
name|boolean
name|manualExecution
parameter_list|,
name|Version
name|installedVersion
parameter_list|,
name|Version
name|toBeIgnored
parameter_list|)
block|{
name|this
operator|.
name|mainFrame
operator|=
name|Objects
operator|.
name|requireNonNull
argument_list|(
name|mainFrame
argument_list|)
expr_stmt|;
name|this
operator|.
name|manualExecution
operator|=
name|manualExecution
expr_stmt|;
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
block|}
annotation|@
name|Override
DECL|method|doInBackground ()
specifier|protected
name|List
argument_list|<
name|Version
argument_list|>
name|doInBackground
parameter_list|()
throws|throws
name|Exception
block|{
try|try
block|{
return|return
name|Version
operator|.
name|getAllAvailableVersions
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|IOException
name|ioException
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not connect to the updateserver."
argument_list|,
name|ioException
argument_list|)
expr_stmt|;
return|return
name|Collections
operator|.
name|emptyList
argument_list|()
return|;
block|}
block|}
annotation|@
name|Override
DECL|method|done ()
specifier|public
name|void
name|done
parameter_list|()
block|{
if|if
condition|(
name|this
operator|.
name|isCancelled
argument_list|()
condition|)
block|{
return|return;
block|}
try|try
block|{
name|List
argument_list|<
name|Version
argument_list|>
name|availableVersions
init|=
name|this
operator|.
name|get
argument_list|()
decl_stmt|;
comment|// couldn't find any version, connection problems?
if|if
condition|(
name|availableVersions
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|showConnectionError
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|showUpdateInfo
argument_list|(
name|availableVersions
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|InterruptedException
decl||
name|ExecutionException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Error while checking for updates"
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
comment|/**      * prints the connection problem to the status bar and shows a dialog if it was executed manually      */
DECL|method|showConnectionError ()
specifier|private
name|void
name|showConnectionError
parameter_list|()
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
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|couldNotConnect
operator|+
literal|"\n"
operator|+
name|tryLater
argument_list|,
name|couldNotConnect
argument_list|,
name|JOptionPane
operator|.
name|ERROR_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|mainFrame
operator|.
name|output
argument_list|(
name|couldNotConnect
operator|+
literal|" "
operator|+
name|tryLater
argument_list|)
expr_stmt|;
block|}
comment|/**      * Prints up-to-date to the status bar (and shows a dialog it was executed manually) if there is now new version.      * Shows a "New Version" Dialog to the user if there is.      */
DECL|method|showUpdateInfo (List<Version> availableVersions)
specifier|private
name|void
name|showUpdateInfo
parameter_list|(
name|List
argument_list|<
name|Version
argument_list|>
name|availableVersions
parameter_list|)
block|{
comment|// the newer version, excluding any non-stable versions, except if the installed one is unstable too
name|Optional
argument_list|<
name|Version
argument_list|>
name|newerVersion
init|=
name|installedVersion
operator|.
name|shouldBeUpdatedTo
argument_list|(
name|availableVersions
argument_list|)
decl_stmt|;
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
name|String
name|upToDate
init|=
name|Localization
operator|.
name|lang
argument_list|(
literal|"JabRef is up-to-date."
argument_list|)
decl_stmt|;
if|if
condition|(
name|manualExecution
condition|)
block|{
name|JOptionPane
operator|.
name|showMessageDialog
argument_list|(
literal|null
argument_list|,
name|upToDate
argument_list|,
name|upToDate
argument_list|,
name|JOptionPane
operator|.
name|INFORMATION_MESSAGE
argument_list|)
expr_stmt|;
block|}
name|this
operator|.
name|mainFrame
operator|.
name|output
argument_list|(
name|upToDate
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// notify the user about a newer version
operator|new
name|NewVersionDialog
argument_list|(
literal|null
argument_list|,
name|installedVersion
argument_list|,
name|newerVersion
operator|.
name|get
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
end_class

end_unit

