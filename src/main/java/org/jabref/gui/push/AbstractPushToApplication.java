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
name|javax
operator|.
name|swing
operator|.
name|JButton
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JPanel
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|swing
operator|.
name|JTextField
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
name|FXDialogService
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
name|FileDialogConfiguration
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
name|BibDatabase
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
name|model
operator|.
name|metadata
operator|.
name|MetaData
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
name|jgoodies
operator|.
name|forms
operator|.
name|builder
operator|.
name|FormBuilder
import|;
end_import

begin_import
import|import
name|com
operator|.
name|jgoodies
operator|.
name|forms
operator|.
name|layout
operator|.
name|FormLayout
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
name|Log
name|LOGGER
init|=
name|LogFactory
operator|.
name|getLog
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
DECL|field|settings
specifier|protected
name|JPanel
name|settings
decl_stmt|;
DECL|field|path
specifier|protected
specifier|final
name|JTextField
name|path
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
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
DECL|field|builder
specifier|protected
name|FormBuilder
name|builder
decl_stmt|;
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
name|menuTitle
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
DECL|method|pushEntries (BibDatabase database, List<BibEntry> entries, String keyString, MetaData metaData)
specifier|public
name|void
name|pushEntries
parameter_list|(
name|BibDatabase
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
parameter_list|,
name|MetaData
name|metaData
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
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
literal|"open -a "
operator|+
name|commands
index|[
literal|0
index|]
operator|+
literal|" -n --args "
operator|+
name|commands
index|[
literal|1
index|]
operator|+
literal|" "
operator|+
name|commands
index|[
literal|2
index|]
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|getCommandLine
argument_list|(
name|keyString
argument_list|)
argument_list|)
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
DECL|method|operationCompleted (BasePanel panel)
specifier|public
name|void
name|operationCompleted
parameter_list|(
name|BasePanel
name|panel
parameter_list|)
block|{
if|if
condition|(
name|notDefined
condition|)
block|{
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
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
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
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
name|panel
operator|.
name|output
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Error"
argument_list|)
operator|+
literal|": "
operator|+
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
name|panel
operator|.
name|output
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
annotation|@
name|Override
DECL|method|getSettingsPanel ()
specifier|public
name|JPanel
name|getSettingsPanel
parameter_list|()
block|{
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
if|if
condition|(
name|settings
operator|==
literal|null
condition|)
block|{
name|initSettingsPanel
argument_list|()
expr_stmt|;
block|}
name|path
operator|.
name|setText
argument_list|(
name|commandPath
argument_list|)
expr_stmt|;
return|return
name|settings
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
comment|/**      * Create a FormBuilder, fill it with a textbox for the path and store the JPanel in settings      */
DECL|method|initSettingsPanel ()
specifier|protected
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|builder
operator|=
name|FormBuilder
operator|.
name|create
argument_list|()
expr_stmt|;
name|builder
operator|.
name|layout
argument_list|(
operator|new
name|FormLayout
argument_list|(
literal|"left:pref, 4dlu, fill:pref:grow, 4dlu, fill:pref"
argument_list|,
literal|"p"
argument_list|)
argument_list|)
expr_stmt|;
name|StringBuilder
name|label
init|=
operator|new
name|StringBuilder
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Path to %0"
argument_list|,
name|getApplicationName
argument_list|()
argument_list|)
argument_list|)
decl_stmt|;
comment|// In case the application name and the actual command is not the same, add the command in brackets
if|if
condition|(
name|getCommandName
argument_list|()
operator|==
literal|null
condition|)
block|{
name|label
operator|.
name|append
argument_list|(
literal|':'
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|label
operator|.
name|append
argument_list|(
literal|" ("
argument_list|)
operator|.
name|append
argument_list|(
name|getCommandName
argument_list|()
argument_list|)
operator|.
name|append
argument_list|(
literal|"):"
argument_list|)
expr_stmt|;
block|}
name|builder
operator|.
name|add
argument_list|(
name|label
operator|.
name|toString
argument_list|()
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|path
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|JButton
name|browse
init|=
operator|new
name|JButton
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Browse"
argument_list|)
argument_list|)
decl_stmt|;
name|FileDialogConfiguration
name|fileDialogConfiguration
init|=
operator|new
name|FileDialogConfiguration
operator|.
name|Builder
argument_list|()
operator|.
name|withInitialDirectory
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|WORKING_DIRECTORY
argument_list|)
argument_list|)
operator|.
name|build
argument_list|()
decl_stmt|;
name|DialogService
name|ds
init|=
operator|new
name|FXDialogService
argument_list|()
decl_stmt|;
name|browse
operator|.
name|addActionListener
argument_list|(
name|e
lambda|->
name|DefaultTaskExecutor
operator|.
name|runInJavaFXThread
argument_list|(
parameter_list|()
lambda|->
name|ds
operator|.
name|showFileOpenDialog
argument_list|(
name|fileDialogConfiguration
argument_list|)
argument_list|)
operator|.
name|ifPresent
argument_list|(
name|f
lambda|->
name|path
operator|.
name|setText
argument_list|(
name|f
operator|.
name|toAbsolutePath
argument_list|()
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|browse
argument_list|)
operator|.
name|xy
argument_list|(
literal|5
argument_list|,
literal|1
argument_list|)
expr_stmt|;
name|settings
operator|=
name|builder
operator|.
name|build
argument_list|()
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|commandPathPreferenceKey
argument_list|,
name|path
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
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

