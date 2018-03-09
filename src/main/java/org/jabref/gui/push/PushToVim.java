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
name|io
operator|.
name|InputStream
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
name|JabRefExecutorService
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
name|IconTheme
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
name|JabRefIcon
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
DECL|class|PushToVim
specifier|public
class|class
name|PushToVim
extends|extends
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
name|PushToVim
operator|.
name|class
argument_list|)
decl_stmt|;
DECL|field|vimServer
specifier|private
specifier|final
name|JTextField
name|vimServer
init|=
operator|new
name|JTextField
argument_list|(
literal|30
argument_list|)
decl_stmt|;
DECL|method|PushToVim (DialogService dialogService)
specifier|public
name|PushToVim
parameter_list|(
name|DialogService
name|dialogService
parameter_list|)
block|{
name|super
argument_list|(
name|dialogService
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|getApplicationName ()
specifier|public
name|String
name|getApplicationName
parameter_list|()
block|{
return|return
literal|"Vim"
return|;
block|}
annotation|@
name|Override
DECL|method|getIcon ()
specifier|public
name|JabRefIcon
name|getIcon
parameter_list|()
block|{
return|return
name|IconTheme
operator|.
name|JabRefIcons
operator|.
name|APPLICATION_VIM
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
name|vimServer
operator|.
name|setText
argument_list|(
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|VIM_SERVER
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|super
operator|.
name|getSettingsPanel
argument_list|()
return|;
block|}
annotation|@
name|Override
DECL|method|storeSettings ()
specifier|public
name|void
name|storeSettings
parameter_list|()
block|{
name|super
operator|.
name|storeSettings
argument_list|()
expr_stmt|;
name|Globals
operator|.
name|prefs
operator|.
name|put
argument_list|(
name|JabRefPreferences
operator|.
name|VIM_SERVER
argument_list|,
name|vimServer
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
DECL|method|initSettingsPanel ()
specifier|protected
name|void
name|initSettingsPanel
parameter_list|()
block|{
name|super
operator|.
name|initSettingsPanel
argument_list|()
expr_stmt|;
name|builder
operator|.
name|appendRows
argument_list|(
literal|"2dlu, p"
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|Localization
operator|.
name|lang
argument_list|(
literal|"Vim server name"
argument_list|)
operator|+
literal|":"
argument_list|)
operator|.
name|xy
argument_list|(
literal|1
argument_list|,
literal|3
argument_list|)
expr_stmt|;
name|builder
operator|.
name|add
argument_list|(
name|vimServer
argument_list|)
operator|.
name|xy
argument_list|(
literal|3
argument_list|,
literal|3
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
DECL|method|pushEntries (BibDatabase database, List<BibEntry> entries, String keys, MetaData metaData)
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
name|keys
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
try|try
block|{
name|String
index|[]
name|com
init|=
operator|new
name|String
index|[]
block|{
name|commandPath
block|,
literal|"--servername"
block|,
name|Globals
operator|.
name|prefs
operator|.
name|get
argument_list|(
name|JabRefPreferences
operator|.
name|VIM_SERVER
argument_list|)
block|,
literal|"--remote-send"
block|,
literal|"<C-\\><C-N>a"
operator|+
name|getCiteCommand
argument_list|()
operator|+
literal|"{"
operator|+
name|keys
operator|+
literal|"}"
block|}
decl_stmt|;
specifier|final
name|Process
name|p
init|=
name|Runtime
operator|.
name|getRuntime
argument_list|()
operator|.
name|exec
argument_list|(
name|com
argument_list|)
decl_stmt|;
name|JabRefExecutorService
operator|.
name|INSTANCE
operator|.
name|executeAndWait
argument_list|(
parameter_list|()
lambda|->
block|{
try|try
init|(
name|InputStream
name|out
init|=
name|p
operator|.
name|getErrorStream
argument_list|()
init|)
block|{
name|int
name|c
decl_stmt|;
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
try|try
block|{
while|while
condition|(
operator|(
name|c
operator|=
name|out
operator|.
name|read
argument_list|()
operator|)
operator|!=
operator|-
literal|1
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
operator|(
name|char
operator|)
name|c
argument_list|)
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Could not read from stderr."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
comment|// Error stream has been closed. See if there were any errors:
if|if
condition|(
operator|!
name|sb
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"Push to Vim error: "
operator|+
name|sb
argument_list|)
expr_stmt|;
name|couldNotConnect
operator|=
literal|true
expr_stmt|;
block|}
block|}
catch|catch
parameter_list|(
name|IOException
name|e
parameter_list|)
block|{
name|LOGGER
operator|.
name|warn
argument_list|(
literal|"File problem."
argument_list|,
name|e
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
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
literal|"Problem pushing to Vim."
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
literal|"Could not connect to Vim server. Make sure that Vim is running with correct server name."
argument_list|)
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
literal|"Could not run the 'vim' program."
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|super
operator|.
name|operationCompleted
argument_list|(
name|panel
argument_list|)
expr_stmt|;
block|}
block|}
annotation|@
name|Override
DECL|method|initParameters ()
specifier|protected
name|void
name|initParameters
parameter_list|()
block|{
name|commandPathPreferenceKey
operator|=
name|JabRefPreferences
operator|.
name|VIM
expr_stmt|;
block|}
block|}
end_class

end_unit

