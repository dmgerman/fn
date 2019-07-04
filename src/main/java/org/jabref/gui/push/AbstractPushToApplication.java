begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.gui.push
package|package
name|org
operator|.
name|jabref
operator|.
name|gui
operator|.
name|push
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
name|DialogService
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
name|OS
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
name|BibDatabaseContext
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
name|BibEntry
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
comment|/**  * Abstract class for pushing entries into different editors.  */
end_comment

begin_class
DECL|class|AbstractPushToApplication
specifier|public
specifier|abstract
class|class
name|AbstractPushToApplication
implements|implements
name|PushToApplication
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
name|AbstractPushToApplication
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|couldNotCall
specifier|protected
name|boolean
name|couldNotCall
decl_stmt|;
comment|// Set to true in case the command could not be executed, e.g., if the file is not found
DECL|field|couldNotConnect
specifier|protected
name|boolean
name|couldNotConnect
decl_stmt|;
comment|// Set to true in case the tunnel to the program (if one is used) does not operate
DECL|field|notDefined
specifier|protected
name|boolean
name|notDefined
decl_stmt|;
comment|// Set to true if the corresponding path is not defined in the preferences
DECL|field|commandPath
specifier|protected
name|String
name|commandPath
decl_stmt|;
DECL|field|commandPathPreferenceKey
specifier|protected
name|String
name|commandPathPreferenceKey
decl_stmt|;
DECL|field|dialogService
specifier|protected
name|DialogService
name|dialogService
decl_stmt|;
DECL|method|AbstractPushToApplication (DialogService dialogService)
specifier|public
name|AbstractPushToApplication
parameter_list|(
name|DialogService
name|dialogService
parameter_list|)
block|{
name|this
operator|.
name|dialogService
operator|=
name|dialogService
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Push entries to external application (%0)"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|getTooltip ()
specifier|public
name|String
name|getTooltip
parameter_list|()
block|{
return|return
name|Localization
operator|.
name|lang
argument_list|(
literal|"Push to %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
DECL|method|pushEntries (BibDatabaseContext database, List<BibEntry> entries, String keyString)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibDatabaseContext
name|database
parameter_list|,
name|List
argument_list|<
name|BibEntry
argument_list|>
name|entries
parameter_list|,
name|String
name|keyString
parameter_list|)
block|{
name|couldNotConnect
operator|=
literal|false
expr_stmt|;
name|couldNotCall
operator|=
literal|false
expr_stmt|;
name|notDefined
operator|=
literal|false
expr_stmt|;
name|initParameters
argument_list|()
expr_stmt|;
name|commandPath
operator|=
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|commandPathPreferenceKey
argument_list|)
expr_stmt|;
comment|// Check if a path to the command has been specified
if|if
condition|(
operator|(
name|commandPath
operator|==
literal|null
operator|)
operator|||
name|commandPath
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|notDefined
operator|=
literal|true
expr_stmt|;
return|return;
block|}
comment|// Execute command
try|try
block|{
if|if
condition|(
name|OS
operator|.
name|OS_X
condition|)
block|{
name|String
index|[]
name|commands
init|=
name|getCommandLine
argument_list|(
name|keyString
argument_list|)
decl_stmt|;
if|if
condition|(
name|commands
operator|.
name|length
operator|<
literal|3
condition|)
block|{
name|LOGGER
operator|.
name|error
argument_list|(
literal|"Commandline does not contain enough parameters to \"push to application\""
argument_list|)
expr_stmt|;
return|return;
block|}
name|ProcessBuilder
name|processBuilder
init|=
operator|new
name|ProcessBuilder
argument_list|(
literal|"open"
argument_list|,
literal|"-a"
argument_list|,
name|commands
index|[
literal|0
index|]
argument_list|,
literal|"-n"
argument_list|,
literal|"--args"
argument_list|,
name|commands
index|[
literal|1
index|]
argument_list|,
name|commands
index|[
literal|2
index|]
argument_list|)
decl_stmt|;
name|processBuilder
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|ProcessBuilder
name|processBuilder
init|=
operator|new
name|ProcessBuilder
argument_list|(
name|getCommandLine
argument_list|(
name|keyString
argument_list|)
argument_list|)
decl_stmt|;
name|processBuilder
operator|.
name|start
argument_list|()
expr_stmt|;
block|}
block|}
comment|// In case it did not work
catch|catch
parameter_list|(
name|IOException
name|excep
parameter_list|)
block|{
name|couldNotCall
operator|=
literal|true
expr_stmt|;
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Error: Could not call executable '"
operator|+
name|commandPath
operator|+
literal|"'."
argument_list|,
name|excep
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|operationCompleted ()
specifier|public
name|void
name|operationCompleted
parameter_list|()
block|{
if|if
condition|(
name|notDefined
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
literal|"Error pushing entries"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Path to %0 not defined"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|couldNotCall
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
literal|"Error pushing entries"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not call executable"
argument_list|)
operator|+
literal|" '"
operator|+
name|commandPath
operator|+
literal|"'."
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|couldNotConnect
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
literal|"Error pushing entries"
argument_list|)
argument_list|,
name|Localization
operator|.
name|lang
argument_list|(
literal|"Could not connect to %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|dialogService
operator|.
name|notify
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Pushed citations to %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
operator|+
literal|"."
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|requiresBibtexKeys ()
specifier|public
name|boolean
name|requiresBibtexKeys
parameter_list|()
block|{
return|return
literal|true
return|;
block|}
comment|/**      * Function to get the command to be executed for pushing keys to be cited      *      * @param keyString String containing the Bibtex keys to be pushed to the application      * @return String array with the command to call and its arguments      */
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
DECL|method|getCommandLine (String keyString)
specifier|protected
name|String
index|[]
name|getCommandLine
parameter_list|(
name|String
name|keyString
parameter_list|)
block|{
return|return
operator|new
name|String
index|[
literal|0
index|]
return|;
block|}
comment|/**      * Function to get the command name in case it is different from the application name      *      * @return String with the command name      */
DECL|method|getCommandName ()
specifier|protected
name|String
name|getCommandName
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
comment|/**      * Function to initialize parameters. Currently it is expected that commandPathPreferenceKey is set to the path of      * the application.      */
DECL|method|initParameters ()
specifier|protected
specifier|abstract
name|void
name|initParameters
parameter_list|()
function_decl|;
DECL|method|getCiteCommand ()
specifier|protected
name|String
name|getCiteCommand
parameter_list|()
block|{
return|return
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|CITE_COMMAND
argument_list|)
return|;
block|}
block|}
end_class

end_unit

